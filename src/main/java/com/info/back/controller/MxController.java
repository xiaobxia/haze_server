package com.info.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.back.pojo.mxreport.*;
import com.info.risk.utils.GzipUtil;
import com.info.web.controller.BaseController;
import com.info.web.pojo.UserContacts;
import com.info.web.service.UserContactsService;
import com.info.web.service.UserService;
import com.info.web.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("mx/")
public class MxController extends BaseController {

	@Autowired
	private UserContactsService userContactsService;

	@Autowired

	@RequestMapping("getAllInfo")
	public String getUserPage(HttpServletRequest request, Model model) throws IOException {

		String userId = request.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			//查询用户的通讯录
			HashMap<String, Object> contactSqlMap = new HashMap<>();
			contactSqlMap.put("userId", userId);
			List<UserContacts> userContactsList = userContactsService.selectUserContacts(contactSqlMap);

			Map<String, String> userContactsMap = userContactsList.stream().collect(Collectors.toMap(UserContacts::getContactPhone, UserContacts::getContactName));

			String Authorization = "token c98ee41f2cdf43c3be8318748545a3de";
			String host = "https://api.51datakey.com/carrier/v3/mobiles/18685170194/mxreport?task_id=d1188780-9006-11e9-84fc-00163e10becc";

			InputStream authorization = HttpUtil.MxGet(host, Authorization);

			String uncompress = GzipUtil.uncompress(authorization);

			ObjectMapper objectMapper = new ObjectMapper();

			try {
				MXReport mxReport = objectMapper.readValue(uncompress, MXReport.class);


				Map<String, Object> reportBasic = new HashMap();
				Map<String, String> report = mxReport.getReport().stream().collect(Collectors.toMap(MXBasicInfo::getKey, MXBasicInfo::getValue));
				Map<String, String> userBasicInfo = mxReport.getUserBasic().stream().collect(Collectors.toMap(MXBasicInfo::getKey, MXBasicInfo::getValue));
				Map<String, String> cellPhone = mxReport.getCellPhone().stream().collect(Collectors.toMap(MXBasicInfo::getKey, MXBasicInfo::getValue));
				Map<String, String> basicInfoCheckItemMap = mxReport.getBasicCheckItems().stream().collect(Collectors.toMap(MXBasicCheckItem::getCheckItem, MXBasicCheckItem::getResult));
				Map<String, Object> friendCircle = mxReport.getFriendCircle().getSummary().stream().collect(Collectors.toMap(MXBasicInfo::getKey, MXBasicInfo::getValue));

				Map<String, List<MXPeerNumTopItem>> peerNumTop = mxReport.getFriendCircle().getPeerNumTopList().stream().collect(Collectors.toMap(MXPeerNumTopList::getKey, MXPeerNumTopList::getTopItem));
				Map<String, List<MXLocationTopItem>> locationTop = mxReport.getFriendCircle().getLocationTopList().stream().collect(Collectors.toMap(MXLocationTopList::getKey, MXLocationTopList::getTopItem));


				Map<String, List<MXDuration>> callDurationDetail = mxReport.getCallDurationDetail().stream().collect(Collectors.toMap(MXCallDurationDetail::getKey, MXCallDurationDetail::getDurationList));
				Map<String, List<MXRegionItem>> contactRegion = mxReport.getContactRegion().stream().collect(Collectors.toMap(MXContactRegion::getKey, MXContactRegion::getRegionList));

				MXUserInfoCheck mxUserInfoCheck = mxReport.getUserInfoCheck().get(0);
				List<MXApplicationPoint> activeDegree = mxReport.getActiveDegree();
				List<MXApplicationPoint> consumptionDetail = mxReport.getConsumptionDetail();

				List<MXCellBehavior> cellBehavior = mxReport.getCellBehavior();
				List<MXRoamAnalysis> roamAnalysis = mxReport.getRoamAnalysis();
				List<MXCallContactDetail> callContactDetail = mxReport.getCallContactDetail();

				List<MXApplicationPoint> callTimeDetail = mxReport.getCallTimeDetail();
				List<MXCallAnalysis> callServiceAnalysis = mxReport.getCallServiceAnalysis();
				List<MXMainService> mainService = mxReport.getMainService();
				List<MXRoamDetail> roamDetail = mxReport.getRoamDetail();
				List<MXTripInfo> tripInfo = mxReport.getTripInfo();


				userBasicInfo.putAll(report);
				userBasicInfo.putAll(cellPhone);
				reportBasic.put("userBasicInfo", userBasicInfo);

				friendCircle.putAll(peerNumTop);
				friendCircle.putAll(locationTop);
				friendCircle.putAll(callDurationDetail);
				friendCircle.putAll(contactRegion);
				friendCircle.put("activeDegree", activeDegree);
				friendCircle.put("consumptionDetail", consumptionDetail);
				friendCircle.put("roamAnalysis", roamAnalysis);
				friendCircle.put("callContactDetail", callContactDetail);
				friendCircle.put("callTimeDetail", callTimeDetail);
				friendCircle.put("callServiceAnalysis", callServiceAnalysis);
				friendCircle.put("mainService", mainService);
				friendCircle.put("roamDetail", roamDetail);
				friendCircle.put("tripInfo", tripInfo);


				reportBasic.put("friendCircle", friendCircle);
				reportBasic.put("mxUserInfoCheck", mxUserInfoCheck);
				reportBasic.put("callRiskAnalysis", mxReport.getCallRiskAnalysis());
				reportBasic.put("callFamilyDetail", mxReport.getCallFamilyDetail());
				reportBasic.put("cellBehavior", cellBehavior);



				model.addAttribute("reportBasic", reportBasic);
				model.addAttribute("basicInfoCheckItemMap", basicInfoCheckItemMap);
				model.addAttribute("userContactsMap", userContactsMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "carrierMxreport";
	}

}
