package com.info.back.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.info.back.dao.IBackUserDao;
import com.info.back.service.IBackUserService;
import com.info.back.service.ITaskJob;
import com.info.back.utils.PropertiesUtil;
import com.info.back.utils.ServiceResult;
import com.info.back.utils.SpringUtils;
import com.info.risk.service.*;
import com.info.web.controller.BaseController;
import com.info.web.dao.IBorrowOrderDao;
import com.info.web.pojo.BackUser;
import com.info.web.pojo.BorrowOrder;
import com.info.web.pojo.User;
import com.info.web.service.ILabelCountService;
import com.info.web.service.IUserService;
import com.info.web.util.DateUtil;
import com.info.web.util.SendSmsUtil;
import com.info.web.util.encrypt.AESUtil;
import com.info.web.util.encrypt.MD5coding;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 登录Controller
 * 
 */
@Slf4j
@Controller
public class LoginController extends BaseController {
	private static final String PROCESS_URL = "processUrl";
	private static final String RETURN_URL = "returnUrl";
	private final static String SMS_SEND_IN_ONE_MINUTE = "SMS_SEND_IN_ONE_MINUTE_";// Redis某个手机号1分钟内已发送验证码key前缀
	private final static String SMS_REGISTER_PREFIX = "newPhoneCode_";// Redis注册key前缀
	private final static int INFECTIVE_SMS_TIME = 300;// 短信默认有效时间300秒
	@Autowired
	private IBackUserService backUserService;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private ITaskJob taskJob;
	@Autowired
	public IUserService userService;
	@Resource
	private IBorrowOrderDao borrowOrderDao;
	@Autowired
	private ITdService tdService;
	@Autowired
	private IYxService yxService;
	@Autowired
	private IBackUserDao backUserDao;
	@Autowired
	private IRiskOrdersService riskOrdersService;
	@Autowired
	private ILabelCountService labelCountService;

	/**
	 * 获得地址
	 * 
	 * @param processUrl 进程url
	 * @param returnUrl 回退url
	 * @return 地址
	 */
	private String getView(String processUrl, String returnUrl) {
		if (!StringUtils.isBlank(processUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(processUrl);
			if (!StringUtils.isBlank(returnUrl)) {
				sb.append("?").append(RETURN_URL).append("=").append(returnUrl);
			}
			return sb.toString();
		} else if (!StringUtils.isBlank(returnUrl)) {
			return "redirect:"+returnUrl;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/err", method = RequestMethod.GET)
	public String err() {
		return "err";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,Model model) {
		try {
			String processUrl = request.getParameter(PROCESS_URL);
			String returnUrl = request.getParameter(RETURN_URL);
			String message = request.getParameter(MESSAGE);
			BackUser backUser = getSessionUser(request);
			if (backUser != null) {
				String view = getView(processUrl, returnUrl);
				if (view != null) {
					return view;
				} else {

					return "redirect:/";
				}
			}
			if (!StringUtils.isBlank(processUrl)) {
				model.addAttribute(PROCESS_URL, processUrl);
			}
			if (!StringUtils.isBlank(returnUrl)) {
				model.addAttribute(RETURN_URL, returnUrl);
			}
			if (!StringUtils.isBlank(message)) {
				model.addAttribute(MESSAGE, message);
			}
			model.addAttribute("APP_NAME", PropertiesUtil.get("APP_NAME"));

		} catch (Exception e) {
			log.error("back login error:{}", e);
		}
		return "login";
	}

	@RequestMapping(value = "/sendSmsBack", method = RequestMethod.POST)
	public void sendSmsBack(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		ServiceResult serviceResult = new ServiceResult("500", "未知异常");
		try {
			//验证验证码
			if (validateSubmit(request, response)) {
				params.put("status", BackUser.STATUS_USE);
				//通过手机号查找用户
				BackUser backUser = backUserService.findOneUser(params);
				if (backUser != null) {
					//获取请求登录密码
					Object tmpPwd = params.get("userPassword");
					if (tmpPwd != null) {
						//验证登录密码
						if (backUser.getUserPassword().equals(MD5coding.MD5(AESUtil.encrypt(tmpPwd.toString(), "")))) {
							//用户手机号
							String userPhone = backUser.getUserMobile();
							//发送验证码操作的redis存储标识
							String hasSendOneMinKey = SMS_SEND_IN_ONE_MINUTE + userPhone;
							if (jedisCluster.get(hasSendOneMinKey) != null) {
								serviceResult.setMsg("请一分钟后再尝试发送");
							} else {
								//生成验证码
								// 6位固定长度
								String rand = String.valueOf(Math.random()).substring(2).substring(0, 6);
								//发送验证码
								if (SendSmsUtil.sendSmsCL(userPhone, rand)) {
									//保存用户手机号1分钟，避免1分钟内重复请求
									jedisCluster.setex(hasSendOneMinKey, 60, userPhone);
									// 将验证码存入redis，期限是300秒
									jedisCluster.setex(SMS_REGISTER_PREFIX + userPhone, INFECTIVE_SMS_TIME, rand);

									log.info("phone :{},sms code:{}",params.get("userMobile"),rand);
									serviceResult = new ServiceResult(ServiceResult.SUCCESS, "发送成功！");
								} else {
									serviceResult.setMsg("短信发送失败,请联系技术！");
								}
							}

						} else {
							serviceResult.setMsg("登录密码错误！");
						}
					} else {
						serviceResult.setMsg("密码不能为空！");
					}
				} else {
					serviceResult.setMsg("该用户不存在！");
				}
			} else {
				serviceResult.setMsg("图形验证码错误");
			}
		} catch (Exception e) {
			log.error("sendSmsBack error params=:{},{}",params.get("phone"), e);
		}
		SpringUtils.renderJson(response, serviceResult);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submit(HttpServletRequest request, HttpServletResponse response, Model model) {
		HashMap<String, Object> params = this.getParametersO(request);
		String errMsg = null;
		//获取登录手机号
		String userPhone = params.get("userMobile") + "";
		String key = SMS_REGISTER_PREFIX + userPhone;
		try {
				//获取短信验证码
            String smsCode = params.get("smsCode") + "";
				//666666 ，测试环境下登录注掉下边这行，取消上边一行的注释
            String code = jedisCluster.get(key);
            if (code != null) {
                if (code.equals(smsCode)) {
					params.put("status", BackUser.STATUS_USE);
					//按条件查找用户
					BackUser backUser = backUserService.findOneUser(params);
					if (backUser != null) {
						//获取登录密码
						Object tmpPwd = params.get("userPassword");
						//验证登录密码
						if (backUser.getUserPassword().equals(MD5coding.MD5(AESUtil.encrypt(tmpPwd.toString(), "")))) {
							//复审发送短信
							backUser.setMsgFlag(BackUser.MSG_ON);
							// 保存session
							setSessionUser(response, backUser);
							//删除redis存储的验证码信息
							jedisCluster.del(key);
						} else {
							errMsg = "密码错误！";
						}
					} else {
						errMsg = "该用户不存在！";
					}
                } else {
                    errMsg = "短信验证码错误！";
                }
            } else {
                errMsg = "验证码失效或不存在！";
            }

		} catch (Exception e) {
			//删除redis存储的验证码信息
			jedisCluster.del(key);
			errMsg = "服务器异常，稍后重试！";
			log.error("post login error params=:{},{}",params, e);
		}
		if (errMsg != null) {
			model.addAttribute(MESSAGE, errMsg);
			model.addAttribute("APP_NAME", PropertiesUtil.get("APP_NAME"));
			return "login";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		removeSessionUser(request, response);
		return "redirect:login";
	}

	@RequestMapping(value = "/test" , method = RequestMethod.GET)
	public void test(HttpServletRequest request) {
		String type = request.getParameter("type");
		if("simgle".equals(type)){
			taskJob.updateLoanTermForSimgle();//测试单笔代付
		}else{
			taskJob.updateLoanTermForBatch();//测试批量代付
		}
	}

	@RequestMapping(value = "/overdue")
	public void testOverdue(){
		taskJob.overdue();
	}



	@RequestMapping(value = "/repayVoiceRemindTomorrow")
	public void testRepayVoiceRemindTomorrow(){
		taskJob.repayVoiceRemindTomorrow();
	}


	@RequestMapping(value = "repayVoiceRemindTheDayAfterTomorrow")
	public void testRepayVoiceRemindTheDayAfterTomorrow(){
		taskJob.repayVoiceRemindTheDayAfterTomorrow();
	}


	@RequestMapping(value = "/repayVoiceRemindToday")
	public void testrepayVoiceRemindToday(){
		taskJob.repayVoiceRemindToday();
	}

	@RequestMapping(value = "/withhold")
	public void testWithhold(){
		taskJob.withhold();
	}
	@RequestMapping(value = "/report")
	public void testChannelReport(){taskJob.channelReport();}

	@RequestMapping(value = "/tdFqz" , method = RequestMethod.GET)
	public void testTdFqz(HttpServletRequest request){
		log.info("tdFqz");
		HashMap<String,Object> params = new HashMap<>();
		//根据 订单id
		String assetId = request.getParameter("assetId");
		BorrowOrder borrowOrder = borrowOrderDao.selectByPrimaryKey(Integer.parseInt(assetId));
		Integer userId = borrowOrder.getUserId();
		User user =  userService.searchByUserid(userId);
		params.put("userId",borrowOrder.getUserId());
		params.put("userName",user.getUserName());
		params.put("reportId","reportId");
		params.put("userPhone",user.getUserPhone());
		params.put("cardNum",user.getIdNumber());
		params.put("assetId",assetId);
		tdService.queryReport(params);
	}

	@RequestMapping(value = "/feedback/zcaf",method = RequestMethod.POST)
	@ResponseBody
	public String yxRequest(HttpServletRequest request, HttpServletResponse response) {
		//设置响应头
		response.setContentType("application/json;charset=utf-8");
		return yxService.shareYxData(request);
	}


	@RequestMapping(value = "/creditCount",method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public void countCredit(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		/* 通过率=放款数/复审总数，
           逾期率=逾期三天以上单数/复审总数
           状态：0:待初审(待机审);-3:初审驳回;1:初审通过;-4:复审驳回;20:复审通过,待放款;-5:放款驳回;22:放款中;-10:放款失败;
           21已放款，还款中;23:部分还款;30:已还款;-11:已逾期;-20:已坏账，34逾期已还款；
           loan_end_time 需要还款时间
        * */
		String verifyEndTime = request.getParameter("endTime");
		String verifyBeginTime = request.getParameter("beginTime");
		//已经放过款的状态
		Set<Integer> loanSet = new HashSet<>();
		loanSet.add(22);loanSet.add(-10);loanSet.add(21);loanSet.add(23);
		loanSet.add(30);loanSet.add(-11);loanSet.add(-20);loanSet.add(34);
		//  逾期状态
		Set<Integer> overdueSet = new HashSet<>();
		overdueSet.add(-11);overdueSet.add(-20);overdueSet.add(34);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BackUser> backUserList = backUserDao.findAll(new HashMap<>());
		List<HashMap<String,Object>> allCountDataList = new ArrayList<>();
		try {
			if (verifyBeginTime != null && verifyEndTime != null) {
				verifyBeginTime = timeStampFormat.format(simpleDateFormat.parse(verifyBeginTime));
				verifyEndTime = timeStampFormat.format(simpleDateFormat.parse(verifyEndTime));
				HashMap<String,Object> hashMap = new HashMap<>();
				hashMap.put("endTime", Timestamp.valueOf(verifyEndTime));
				hashMap.put("beginTime",Timestamp.valueOf(verifyBeginTime));
				for (BackUser backUser : backUserList) { //用户表
					hashMap.put("verifyReviewUser",backUser.getUserAccount());
					List<BorrowOrder> borrowOrderList = borrowOrderDao.selectBorrowOrderByTime(hashMap);
					if (borrowOrderList == null) {
						continue;
					}
					double count = borrowOrderList.size(),loanCount = 0,overdueCount = 0;
					if (count == 0) {
						continue;
					}
					HashMap<String,Object> personMap = new HashMap<>();
					String userName;
					String userMobile;
					for (BorrowOrder borrowOrder:borrowOrderList) { //订单
						Integer status =  borrowOrder.getStatus();
						if (loanSet.contains(status)) {
							loanCount += 1;
							if (overdueSet.contains(status)) {
								overdueCount += 1;
							}
						}
					}
					if (count != 0) {
						DecimalFormat df = new DecimalFormat("######0.00");
						userMobile = backUser.getUserMobile();
						userName = backUser.getUserName();
						String passRate = df.format(loanCount/count);
						String overdueRate = df.format(overdueCount/count);
						personMap.put("userName",userName);
						personMap.put("userMobile",userMobile);
						personMap.put("passRate",passRate);
						personMap.put("overdueRate",overdueRate);
						personMap.put("endTime",verifyEndTime);
						personMap.put("beginTime",verifyBeginTime);
						personMap.put("count",(int)count);
						allCountDataList.add(personMap);
					}
				}
			}
			log.info("allData =:{}",allCountDataList.toString());
		} catch (Exception e) {
			log.error("countCredit error:{}",e);
		}
		/*String result = gson.toJson(allCountDataList);;
		return result;*/
		try {
			PrintWriter printWriter = response.getWriter();
			printWriter.write(style(allCountDataList));
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			log.error("countCredit error:{}",e);
		}
	}

	public String style (List<HashMap<String,Object>> listMap) {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>");
		html.append("<body>");
		html.append("<table border='1'><tr>");
		html.append("<td>姓名</td><td>手机号</td><td>通过率</td><td>逾期率</td><td>开始时间</td><td>结束时间</td><td>订单数</td>");
		html.append("</tr>");
		for (HashMap<String,Object> hashMap : listMap) {
			html.append("<tr>");
			html.append("<td>");
			html.append(hashMap.get("userName"));
			html.append("</td>");
			html.append("<td>");
			html.append(hashMap.get("userMobile"));
			html.append("</td>");
			html.append("<td>");
			html.append(hashMap.get("passRate"));
			html.append("</td>");
			html.append("<td>");
			html.append(hashMap.get("overdueRate"));
			html.append("</td>");
			html.append("<td>");
			html.append(hashMap.get("beginTime"));
			html.append("</td>");
			html.append("<td>");
			html.append(hashMap.get("endTime"));
			html.append("</td>");
			html.append("<td>");
			html.append(hashMap.get("count"));
			html.append("</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		html.append("</body></html>");
		return html.toString();
	}



	@RequestMapping(value = "/tbRefuseCount")
	@ResponseBody
	public String getTbRefuseCount(HttpServletResponse response){
		log.info("进入统计");
		Gson gson = new Gson();
		//设置响应头
		response.setContentType("application/json;charset=utf-8");
		Integer num = riskOrdersService.getTbRefuseCount();
		Map<String,Object> hashMap = new HashMap<>();
		hashMap.put("num",num);
		return gson.toJson(hashMap);
	}

	@RequestMapping(value = "/testLabelCount")
	public void countTest(HttpServletRequest request) {
		String type = request.getParameter("type");
		Date now = new Date();
		if(type != null) {
			String startTime = "2018-10-17";
			Date startDate = DateUtil.getDate(startTime, DateUtil.YMD);
			while (startDate.getTime() < now.getTime()) {
				labelCountService.labelCount(DateUtil.getDateFormat(startDate, DateUtil.YMD));
				startDate = DateUtil.addDay(startDate, 1);
			}
		} else {
			labelCountService.labelCount(request.getParameter("countDate"));
		}
	}

}
