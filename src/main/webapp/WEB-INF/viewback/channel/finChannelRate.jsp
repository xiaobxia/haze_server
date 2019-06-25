<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert saveUpdateUser.jspr</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" enctype="multipart/form-data"
				action=""
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${channelRate.id}">
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<p><label>渠道商名称:</label><label style="width: 230px;">${channelInfo.channelName}</label></p>
					<div class="divider"></div>
					<p><label>费率名称:</label><label style="width: 230px;">${channelRate.channelRateName}</label></p>
					<div class="divider"></div>
					<p><label>注册人数:</label><label style="width: 230px;">${channelReport.registerCount}</label></p>
					<div class="divider"></div>
					<p><label>注册人数费用系数:</label><label style="width: 230px;">${channelRate.channelRegisterRate}</label></p>
					<div class="divider"></div>
					<p><label>新用户放款金额:</label><label style="width: 230px;">${channelReport.newIntoMoney/100}</label></p>
					<div class="divider"></div>
					<p><label style="width: 160px;">新用户放款费用系数:</label><label style="width: 230px;">${channelRate.channelNewloanRate}</label></p>
					<div class="divider"></div>
					 
					<p style="width: 480px;">
						<label style="width: 100px;">计算公式:</label>
						<label style="width: 300px;">日消耗=日注册量*CPA+日新用户放款总额*CPS</label>
					</p>
					<div class="divider"></div>
					<p style="width: 480px;">
						<label style="width: 100px;">日消耗金额:</label>
						<label style="width: 300px;">${money}</label>
					</p>
					<div class="divider"></div>
					
					
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
	
</html>