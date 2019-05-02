package com.info.risk.service;
import java.util.*;
import com.info.risk.dao.IRiskCreditUserDao;
import com.info.web.dao.IBorrowOrderDao;
import com.info.web.pojo.*;
import com.info.web.service.IRepaymentService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.back.utils.ServiceResult;
import com.info.back.utils.SysCacheUtils;
import com.info.back.utils.WebClient;
import com.info.risk.pojo.RiskOrders;
import com.info.risk.utils.ConstantRisk;
import com.info.web.util.OrderNoUtil;


@Slf4j
@Service
public class TdService implements ITdService {
	@Autowired
	private IRiskOrdersService riskOrdersService;
	@Autowired
	private IRiskCreditUserDao riskCreditUserDao;
	@Autowired
	private IRepaymentService repaymentService;
	@Autowired
	private IBorrowOrderDao borrowOrderDao;

	/*@Override
	public ServiceResult getReport(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		try {
			Object userName = params.get("userName");
			Object cardNum = params.get("cardNum");
			Object userId = params.get("userId");
			Object userPhone = params.get("userPhone");

			if (userName != null && cardNum != null && userId != null
					&& userPhone != null) {
				LinkedHashMap<String, String> map2 = SysCacheUtils
						.getConfigParams(BackConfigParams.TD);
				String url = map2.get("TD_URL2") + "?partner_code="
						+ map2.get("TD_BZM") + "&partner_key="
						+ map2.get("TD_KEY") + "&app_name="
						+ map2.get("TD_APP_NAME")+"&biz_code=jiexiangweb";
				Map<String, String> map = new HashMap<String, String>();
				map.put("account_name", userName + "");
				map.put("id_number", cardNum + "");
				map.put("account_mobile", userPhone + "");
				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.TD);
				orders.setAct(ConstantRisk.TD_PRELOAN_APPLY);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams("url=" + url + ",params=" + map);
				logger.info("td card"+cardNum);
				logger.info("td  getReport send  " + orders.toString());
				riskOrdersService.insert(orders);
				String result = WebClient.getInstance().doPost(url, map);
				logger.info("td  getReport return " + result);
				if (StringUtils.isNotBlank(result)) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject.getBoolean("success")) {
						serviceResult = new ServiceResult(
								ServiceResult.SUCCESS, jsonObject
										.getString("id"));
						orders.setStatus(RiskOrders.STATUS_SUC);
					} else {
						serviceResult = new ServiceResult("300", jsonObject
								.getString("reason_code")
								+ jsonObject.getString("reason_desc"));
					}
					orders.setReturnParams(result);
					riskOrdersService.update(orders);
				} else {
					orders.setReturnParams("td return null");
					serviceResult = new ServiceResult("100", "同盾返回空或请求报错");
				}
				riskOrdersService.update(orders);

			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			logger.error("getReport error ,params=" + params, e);
		}
		return serviceResult;
	}
*/





	@Override
	public ServiceResult queryReport(HashMap<String, Object> params) {
		ServiceResult serviceResult = new ServiceResult("500", "未知异常，请稍后重试！");
		/*
		* 姓    名: 皮晴晴  身份证号: 370404199006301915 手机号码: 13333333333  893939741@qq.com  银行卡 6230901807030310952
		* */
		try {
			Object userId = params.get("userId");
			Object reportId = params.get("reportId");
			Object userName = params.get("userName");
			Object idNum = params.get("cardNum");
			Object userPhone = params.get("userPhone");
			String assetId = params.get("assetId").toString();

			String backCardNum = " ";
			Map<String,String> backCardMap = null;
			//根据用户id 查询  银行卡信息
			backCardMap =  repaymentService.findCardNo((Integer) userId);
			backCardNum = backCardMap.get("cardNo");
			log.info("repaymentService:{} ",repaymentService);
			String bizCode = null,appName = null,partnerKey = null;
			Map<String, String> map = new HashMap<>();
			//查询订单的信息
			BorrowOrder borrowOrder = borrowOrderDao.selectByPrimaryKey(Integer.parseInt(assetId));
			//查询td的设备指纹
			HashMap<String,Object> paramTd = new HashMap<>();
			paramTd.put("userId",userId);
			paramTd.put("assetBorrowOrderId",assetId);
			BorrowOrderTdDevice borrowOrderTdDevice = borrowOrderDao.selectTdFqzByUserIdAndAssetBorrowId(paramTd);
			String clientType = borrowOrder.getClientType();
			String deviceContent = borrowOrderTdDevice.getDeviceContent();
			if (clientType == null) {
				clientType = "1";
			}
			if (clientType.equals("1")) { //安卓
				bizCode = "jiexiangand";
				appName = "jiexiang_and";
				partnerKey = "02e2504c3e494da3a56923dc0c5a0470";
				map.put("black_box",borrowOrderTdDevice.getDeviceContent());
			}else if (clientType.equals("2")) { //IOS
				bizCode = "jiexiangios";
				appName = "jiexiang_ios";
				partnerKey = "e93781840822406b961d9324071f4be3";
				map.put("black_box",borrowOrderTdDevice.getDeviceContent());
			}else if (clientType.equals("3")) { //web
				bizCode = "jiexiangweb";
				appName = "jiexiang_web";
				partnerKey = "ba87b788ca46452188c00b039c0cac77";
				map.put("token_id",borrowOrderTdDevice.getDeviceContent());
			}
			if (reportId != null && userId != null) {
				LinkedHashMap<String, String> map2 = SysCacheUtils
						.getConfigParams(BackConfigParams.TD);
				String url = map2.get("TD_URL3") + "?partner_code="
						+ map2.get("TD_BZM") + "&partner_key="
						+ partnerKey + "&app_name="
						+ appName + "&biz_code="+bizCode;
				map.put("account_name", userName + "");
				map.put("id_number", idNum + "");
				map.put("account_mobile", userPhone + "");
				//银行卡信息
				map.put("card_number",backCardNum);
				//邮箱信息
				//map.put("account_email","1725369601@qq.com");
				//设备指纹信息
				RiskOrders orders = new RiskOrders();
				orders.setUserId(userId + "");
				orders.setOrderType(ConstantRisk.TD);
				orders.setAct(ConstantRisk.TD_PRELOAN_REPORT);
				orders.setOrderNo(OrderNoUtil.getInstance().getUUID());
				orders.setReqParams("url=" + url+"map"+map);
				log.info("queryReport send:{}  " ,orders.toString());
				riskOrdersService.insert(orders);

				//更新同盾设置指纹以及反欺诈数据
				params.put("id",borrowOrderTdDevice.getId());
				params.put("requestParams","url=" + url+"map"+map);
				borrowOrderDao.updateTdDeviceFqzData(params);
				String result = WebClient.getInstance().doPost(url, map);
				//返回报文
				params.put("returnParams",result);
				borrowOrderDao.updateTdDeviceFqzData(params);

				log.info("queryReport return :{}" ,result);
				if (StringUtils.isNotBlank(result)) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject.getBoolean("success")) {
						//添加 同盾反欺诈数据
						HashMap<String,Object> tdFqz = new HashMap<>();
						tdFqz.put("userId",userId);
						tdFqz.put("tdFqzData",result);
						riskCreditUserDao.updateTdFqzData(tdFqz);

						params.put("fqzContent",result);
						borrowOrderDao.updateTdDeviceFqzData(params);
						serviceResult = new ServiceResult(
								ServiceResult.SUCCESS, result);
						orders.setStatus(RiskOrders.STATUS_SUC);
					} else {
						if ("100".equals(jsonObject.getString("reason_code"))) {
							serviceResult = new ServiceResult("600", jsonObject
									.getString("reason_code")
									+ jsonObject.getString("reason_desc"));
						} else {
							serviceResult = new ServiceResult("300", jsonObject
									.getString("reason_code")
									+ jsonObject.getString("reason_desc"));
						}
					}
					orders.setReturnParams(result);
					riskOrdersService.update(orders);
				} else {
					orders.setReturnParams("td return null");
					serviceResult = new ServiceResult("100", "同盾返回空或请求报错");
				}
				riskOrdersService.update(orders);

			} else {
				serviceResult = new ServiceResult("400", "必要参数不足！");
			}
		} catch (Exception e) {
			log.error("queryReport error ,params=:{}error:{}",params, e);
		}
		return serviceResult;
	}










//	public static void main(String[] args) throws java.text.ParseException {
//		String startTime = "2017-11-30 13:57:24";
//		String endTime = "2017-11-02 09:01:06";
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
//		Calendar startCalendar = Calendar.getInstance();
//		startCalendar.setTime(simpleDateFormat.parse(startTime)); //当前最近时间
//		Calendar endCalendar = Calendar.getInstance();
//		endCalendar.setTime(simpleDateFormat.parse(endTime));
//
//
//		Date date = new Date();
//		String cc  = simpleDateFormat.format(date);
//		System.out.println(cc);
//
//
//		List<Integer> l1 = new ArrayList<>();
//		l1.add(1);
//		l1.add(2);
//		l1.add(3);
//		l1.add(4);
//		List<Integer> l2 = new ArrayList<>();
//		l2.add(5);
//		l2.add(6);
//		l2.add(7);
//		l2.add(8);
//
//		Stream.of(l1, l2).flatMap(n -> n.stream()).filter(n->n%2==0).forEach(System.out::println);
//
//		int a = Collections.max(l1);
//		System.out.println("aa = " + a);
//
//		boolean aa = "13330673838".equals("13330673838");
//		System.out.println(aa);
//	}
}
