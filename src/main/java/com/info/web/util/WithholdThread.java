package com.info.web.util;

import com.alibaba.fastjson.JSONObject;
import com.info.back.utils.PropertiesUtil;
import com.info.web.pojo.*;
import com.info.web.service.*;

import org.slf4j.Logger;

import redis.clients.jedis.JedisCluster;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User : zhangsh
 * Date : 2017/2/17 0017
 * Time : 13:00
 */
public class WithholdThread extends Thread{
    private Integer repaymentId;
    private IRepaymentService repaymentService;
    private IRepaymentDetailService repaymentDetailService;
    private IBorrowOrderService borrowOrderService;
    private IUserSendMessageService userSendMessageService;
    private SimpleDateFormat dateFormat;
    private String repaymentTime;
    private Logger logger;
    private IUserService userService;
    private JedisCluster jedisCluster;


    public WithholdThread(Logger logger, Integer repaymentId, String repaymentTime, SimpleDateFormat dateFormat, IUserService userService, IRepaymentService repaymentService, IRepaymentDetailService repaymentDetailService, IBorrowOrderService borrowOrderService,JedisCluster jedisCluster)
    {
        this.logger = logger;
        this.repaymentId = repaymentId;
        this.repaymentTime = repaymentTime;
        this.dateFormat = dateFormat;
        this.userService = userService;
        this.repaymentService = repaymentService;
        this.repaymentDetailService = repaymentDetailService;
        this.borrowOrderService = borrowOrderService;
        this.jedisCluster = jedisCluster;
    }

    public WithholdThread(Logger logger, Integer repaymentId, String repaymentTime, SimpleDateFormat dateFormat, IUserService userService, IRepaymentService repaymentService, IRepaymentDetailService repaymentDetailService, IBorrowOrderService borrowOrderService, IUserSendMessageService userSendMessageService, JedisCluster jedisCluster)
    {
        this.logger = logger;
        this.repaymentId = repaymentId;
        this.repaymentTime = repaymentTime;
        this.dateFormat = dateFormat;
        this.userService = userService;
        this.repaymentService = repaymentService;
        this.repaymentDetailService = repaymentDetailService;
        this.borrowOrderService = borrowOrderService;
        this.userSendMessageService = userSendMessageService;
        this.jedisCluster = jedisCluster;
    }
    @Override
    public void run()
    {
        try {
            logger.info("自动代扣开始 withhold repaymentId =:{} ",repaymentId);
            String repayStatus = jedisCluster.get("REPAYMENT_REPAY_WITHHOLD_" + repaymentId);
            if("true".equals(repayStatus)){
                logger.info("自动代扣开始 withhold repaymentId =:{} 正在处理中",repaymentId);
                return;
            }

            Repayment re = repaymentService.selectByPrimaryKey(repaymentId);
            if(dateFormat.format(re.getRepaymentTime()).equals(repaymentTime) && re.getStatus().equals(BorrowOrder.STATUS_HKZ)) {
                User user = userService.searchByUserid(re.getUserId());
                Map<String, String> card = repaymentService.findCardNo(re.getUserId());
                String cardNo = card.get("cardNo");

                String api_url = PropertiesUtil.get("APP_HOST_API")+ "/"+PropertiesUtil.get("AUTO_WITHDRAW_CHANNEL")+"/auto-withhold?id=" + re.getId();
                String resultStr = HttpUtil.getHttpMess(api_url,"","POST","UTF-8");

                JSONObject jsonObject = JSONObject.parseObject(resultStr);

//                ServiceResult result = repaymentService.withhold(re, user, Repayment.TASK_WITHHOLD);

//                SendSmsUtil.sendSmsDiyCL("18357005066,13761552523,15901947771","今日还款订单号" + re.getId() + "("+user.getRealname()+")自动扣款结果：" + jsonObject.getString("code") + " ,信息说明：" +jsonObject.getString("msg"));

                logger.info("自动扣款结果 ==:{} ",resultStr);
                //扣款成功
                if("0".equals(jsonObject.getString("code"))){

//                    String content = "尊敬的"+user.getRealname()+"：您的" + (re.getRepaymentAmount() / 100) + "元借款已经自动还款成功，您的该笔交易将计入您的信用记录，好的记录将有助于提升您的可用额度。";
//                    //发送提醒短信
//                    SendSmsUtil.sendSmsDiyCL(user.getUserPhone(), content);
//                    UserSendMessage message = new UserSendMessage();
//                    message.setPhone(user.getUserPhone());// 接收的用户
//                    message.setMessageCreateTime(new Date());
//                    message.setMessageContent(content);
//                    message.setMessageStatus(UserSendMessage.STATUS_SUCCESS);// 发送成功
//                    // 2
//                    userSendMessageService.saveUserSendMsg(message);// 添加短息记录
//                    logger.info("自动扣款成功：" + content);

                }else{//如果自动扣款没有成功
                    Calendar cal = Calendar.getInstance();
                    //获取当前小时数
                    int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                    if(currentHour >= 13 && currentHour < 18){
                        String content = user.getRealname() + "##" + (re.getRepaymentAmount() / 100) + "##"
                                    + cardNo.substring(cardNo.length() - 4);
                        //发送提醒短信
                        SendSmsUtil.sendSmsDiyCL(user.getUserPhone(), SendSmsUtil.templateld44638, content);

                        UserSendMessage message = new UserSendMessage();
                        // 接收的用户
                        message.setPhone(user.getUserPhone());
                        message.setMessageCreateTime(new Date());
                        message.setMessageContent(content);
                        // 发送成功
                        message.setMessageStatus(UserSendMessage.STATUS_SUCCESS);
                        // 添加短息记录
                        userSendMessageService.saveUserSendMsg(message);

                        logger.info("自动扣款失败：{}",content);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("repay withhold error repaymentId = :{} error:{}", repaymentId, e);
        }
    }
}
