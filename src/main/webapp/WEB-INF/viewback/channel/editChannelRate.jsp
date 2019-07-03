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
	<style>
		.form-item span.error {
			left: 160px;
		}
	</style>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" enctype="multipart/form-data"
				action="channel/saveUpdateChannelRate"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${channelRate.id }">
				<div class="pageFormContent" layoutH="60" style="overflow: auto;">
					<div class="form-wrap">
						<div class="form-item">
							<label style="width: 160px;">
								费率名称:
							</label>
							<input name="channelRateName" value="${channelRate.channelRateName}"
								   class="required"	type="text" alt="请输入名称" size="30"/>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label style="width: 160px;">
								注册人数费用系数:
							</label>
							<input name="channelRegisterRate" value="${channelRate.channelRegisterRate}"
								   class="required"	type="text" alt="请输入系数" size="30"/>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label style="width: 160px;">
								新用户放款费用系数:
							</label>
							<input name="channelNewloanRate" value="${channelRate.channelNewloanRate}"
								   class="required"	type="text" alt="请输入系数" size="30"/>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label style="width: 160px;">费率类型:</label>
							<select name="channelRateType" class="required">
								<option value="">--请选择--</option>
								<option value="2" <c:if test="${channelRate.channelRateType == 2}">selected="selected"</c:if>>UV</option>
								<option value="3" <c:if test="${channelRate.channelRateType == 3}">selected="selected"</c:if>>PV</option>
								<option value="0" <c:if test="${channelRate.channelRateType == 0}">selected="selected"</c:if>>CPA</option>
								<option value="1" <c:if test="${channelRate.channelRateType == 1}">selected="selected"</c:if>>CPS</option>
								<option value="4" <c:if test="${channelRate.channelRateType == 4}">selected="selected"</c:if>>CPL</option>
							</select>
							<label style="color: red;" class="required-label">*</label>
						</div>
						<div class="form-item">
							<label  style="width: 160px;">计算公式:</label>
							<label style="width: 300px;">日消耗=日注册量*CPA+日新用户放款总额*CPS</label>
						</div>
						<div class="form-item">
							<label  style="width: 160px;">备注:</label>
							<textarea  name="remark" rows="5" cols="60" maxlength="50">${channelRate.remark}</textarea>
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
	
</html>