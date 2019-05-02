package com.info.robot;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.TextMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zed
 */
public class DingTalk {
    private static final DingtalkChatbotClient DINGTALK_CHATBOT_CLIENT = new DingtalkChatbotClient();
    private static final String                WEBHOOK                 = "https://oapi.dingtalk.com/robot/send?access_token=2be271b1d29bbd87fb13372624f058da1d20f64a7e35fe9295d6279960062008";
    private static final List<String>          TEAM_RISK_DEV           = Arrays.asList();

    public static void warnRetryFailureOrders(int summary) {
        String briefText = "";
        if (summary <= 10) {
            briefText += "紧急程度: 低 [0,10]";
        } else if (summary <= 30) {
            briefText += "紧急程度: 中 [10,30]";
        } else if (summary <= 60) {
            briefText += "紧急程度: 高 [30,60]";
        } else {
            briefText += "紧急程度: 立即 [60,100]";
        }
        briefText += "\n";
        briefText += "卡机审订单数量:" + summary + "笔!";

        TextMessage message = new TextMessage("Hi 今日数据报告来啦，" + briefText);
//        message.setAtMobiles(TEAM_RISK_DEV);
        try {
            DINGTALK_CHATBOT_CLIENT.send(WEBHOOK, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TextMessage message = new TextMessage("Hi 今日数据报告来啦，" );
        try {
            DINGTALK_CHATBOT_CLIENT.send(WEBHOOK, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
