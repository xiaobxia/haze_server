package com.info.back.service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.PushAppException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.info.back.pojo.GeTuiJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 个推发送消息服务
 *
 * @author tgy
 * @version [版本号, 2018年3月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Slf4j
@Service("geTuiService")
@Configuration
@PropertySource("classpath:/config.properties")
public class GeTuiService implements IGeTuiService {

	/**
	 * app应用ID
	 */
	@Value("${app_id}")
	private String appId;

	/**
	 * app应用key
	 */
	@Value("${app_key}")
	private String appKey;

	/**
	 * app应用密钥
	 */
	@Value("${master_secret}")
	private String masterSecret;

	/**
	 * 个推服务URL
	 */
	@Value("${host_url}")
	private String host;

	/**
	 * 渗透消息推送
	 *
	 * @param json
	 */
	@Override
	public void pushToAPP(GeTuiJson json) {

		String jsonVal = json.toJson();
		log.info("getuyi json is:{}",jsonVal);
		if (json == null) {
			return;
		}
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		TransmissionTemplate tem = getTransmissionTemplate(json);
		AppMessage message = new AppMessage();
		message.setData(tem);
		message.setOffline(true);
		//24小时都不过期
		message.setOfflineExpireTime(24 * 1000 * 3600);
		message.setOffline(true);
		List<String> appIdList = new ArrayList<String>();
		//只给一个应用推送 可多个
		appIdList.add(appId);

		//手机平台条件
		List<String> phoneTypeList = new ArrayList<String>();
		//省市条件
		List<String> provinceList = new ArrayList<String>();
		//TAG条件
		List<String> tagList = new ArrayList<String>();

		//安卓 IOS
		phoneTypeList.add("ANDROID");
		phoneTypeList.add("IOS");

		//provinceList.add("湖南省");

		//tagList.add("tag1");
		//tagList.add("tag2");

		message.setAppIdList(appIdList);
		message.setOffline(true);

		// 设置省市平台tag的新方式
		AppConditions cdt = new AppConditions();
		cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList, AppConditions.OptType.or);
		//cdt.addCondition(AppConditions.REGION, provinceList, OptType.or);
		//cdt.addCondition(AppConditions.TAG, tagList, OptType.or);
		message.setConditions(cdt);
		try {
			IPushResult ret = push.pushMessageToApp(message);
			log.info("getui msg success:{}",ret.getResponse().toString());
		} catch (PushAppException e) {
			log.error("getui msg error:{}",e);
		}

	}

	public TransmissionTemplate getTransmissionTemplate(GeTuiJson json) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(2);
		template.setTransmissionContent(json.toJson());
		APNPayload payload = new APNPayload();
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		payload.setContentAvailable(1);
		payload.addCustomMsg("payload",json.toJson());
		alertMsg.setBody(json.getSummary());
		alertMsg.setTitle(json.getTitle());
		//简单模式
		//payload.setAlertMsg(new APNPayload.SimpleAlertMsg(json.toJson()));
		//字典模式
		payload.setAlertMsg(alertMsg);
		template.setAPNInfo(payload);
		return template;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
