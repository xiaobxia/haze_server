<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
				action="channel/saveUpdateChannelInfo"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${channelInfo.id }">
				<div class="pageFormContent" layoutH="60" style="overflow: auto;">
					<div class="form-wrap">
						<div class="form-item">
							<label>
								渠道商：
							</label>
							<select name="channelSuperId" class="required">
								<option value="">--请选择--</option>
								<c:forEach var="channelSuperList" items="${channelSuperList}">
									<option value="${channelSuperList.id}" <c:if test="${channelSuperList.id eq channelInfo.channelSuperId}">selected="selected"</c:if>>${channelSuperList.channelSuperName}</option>
								</c:forEach>
							</select>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label>
								负责人:
							</label>
							<input name="operatorName" value="${channelInfo.operatorName }" type="text"  class="required"
								   alt="请输入用户名" size="30" />
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label>
								渠道名称:
							</label>
							<input name="channelName" value="${channelInfo.channelName}"
								   class="required"	type="text" alt="请输入名称" size="30"/>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label>
								联系方式:
							</label>
							<input name="channelTel" value="${channelInfo.channelTel }" type="text"  class="required"
								   alt="请输入手机号" size="30" />
							<label style="color: red;" class="required-label">*</label>
						</div>
						<c:if test = "${flag == true}">
							<div class="form-item">
								<label>
									渠道编码:
								</label>
								<input name="channelCode" value="${channelInfo.channelCode}"
									   class="required disabled"	type="text" readonly size="18"/> 此项不可修改
								<label style="color: red;" class="required-label">*</label>
							</div>
						</c:if>
						<c:if test ="${flag == false}">
							<div class="form-item">
								<label>
									渠道编码:
								</label>
								<input name="channelCode" value="${channelInfo.channelCode}"
									   class="required"	type="text" alt="请输入编码" size="30"/>
								<label style="color: red;" class="required-label">*</label>
							</div>
						</c:if>
						<div class="form-item">
							<label>
								微信注册口子:
							</label>
							<select name="wechatStatus" class="required">
								<option value="">--请选择--</option>
								<option value="0" <c:if test="${channelInfo.wechatStatus == 0}">selected="selected"</c:if>>开启</option>
								<option value="1" <c:if test="${channelInfo.wechatStatus == 1}">selected="selected"</c:if>>关闭</option>
							</select>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item" style="display: none">
							<label>
								登录密码:
							</label>
							<input name="channelPassword" style="display: none" value="123456" type="password" alt="请输入编码" size="30"/>
						</div>
						<div class="form-item">
							<label>
								渠道计费方式：
							</label>
							<select name="rateId" class="required">
								<option value="">--请选择--</option>
								<c:forEach var="channelRateList" items="${channelRateList}">
									<option value="${channelRateList.id}" <c:if test="${channelRateList.id eq channelInfo.rateId}">selected="selected"</c:if>>${channelRateList.channelRateName}</option>
								</c:forEach>
							</select>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label>
								qq注册口子:
							</label>
							<select name="qqStatus" class="required">
								<option value="">--请选择--</option>
								<option value="0" <c:if test="${channelInfo.qqStatus == 0}">selected="selected"</c:if>>开启</option>
								<option value="1" <c:if test="${channelInfo.qqStatus == 1}">selected="selected"</c:if>>关闭</option>
							</select>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label style="width: 100px;">
								地区:
							</label>
							<select name="channelProvince" id="channelProvince" style="width: 180px;"></select>
							<select name="channelCity" id="channelCity" style="width: 180px;"></select>
							<select name="channelArea" id="channelArea" style="width: 180px;"></select>
						</div>
						<div class="form-item">
							<label style="width: 100px;">备注:</label>
							<textarea  name="remark" rows="5" cols="60" maxlength="50">${channelInfo.remark }</textarea>
						</div>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
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
	<script type="text/javascript">
	new PCAS("channelProvince","channelCity","channelArea",'${channelInfo.channelProvince}','${channelInfo.channelCity}','${channelInfo.channelArea}');
</script>
</html>