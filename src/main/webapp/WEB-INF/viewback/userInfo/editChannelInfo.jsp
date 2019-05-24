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
				action="channel/saveUpdateChannelInfo"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="id" id="id" value="${channelInfo.id }">
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
					<dl style="width: 380px;">
						<dt style="width: 100px;">
							<label>
								渠道商：
							</label>
						</dt>
						<dd>
							<select name="channelSuperId" class="required">
							<option value="">--请选择--</option>
							<c:forEach var="channelSuperList" items="${channelSuperList}">
									<option value="${channelSuperList.id}" <c:if test="${channelSuperList.id eq channelInfo.channelSuperId}">selected="selected"</c:if>>${channelSuperList.channelSuperName}</option>
							</c:forEach>
					</select>
					<label style="color: red;">*</label>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl style="width: 380px;">
						<dt style="width: 100px;">
							<label>
								渠道名称:
							</label>
						</dt>
						<dd>
							<input name="channelName" value="${channelInfo.channelName}"
							 class="required"	type="text" alt="请输入名称" size="30"/>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 100px;">
							<label>
								渠道商编码:
							</label>
						</dt>
						<dd>
							<input name="channelCode" value="${channelInfo.channelCode}"
							 class="required"	type="text" alt="请输入编码" size="30"/>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 100px;">
							<label>
								登录密码:
							</label>
						</dt>
						<dd>
							<input name="channelPassword" value=""
							 class="required"	type="password" alt="请输入编码" size="30"/>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl style="width: 380px;">
						<dt style="width: 100px;">
							<label>
								计费方式：
							</label>
						</dt>
						<dd>
							<select name="rateId" class="required">
							<option value="">--请选择--</option>
							<c:forEach var="channelRateList" items="${channelRateList}">
									<option value="${channelRateList.id}" <c:if test="${channelRateList.id eq channelInfo.rateId}">selected="selected"</c:if>>${channelRateList.channelRateName}</option>
							</c:forEach>
					</select>
							<label style="color: red;">*</label>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt style="width: 100px;">
							<label>
								负责人:
							</label>
						</dt>
						<dd>
							<input name="operatorName" value="${channelInfo.operatorName }" type="text"
								  alt="请输入用户名" size="30" />
						</dd>
					</dl>
					<div class="divider"></div>
					
					<p>
							<label style="width: 100px;">
								地区:
							</label>
								 <select name="channelProvince" id="channelProvince"></select>
                                 <select name="channelCity" id="channelCity"></select>
                                 <select name="channelArea" id="channelArea"></select>
					</p>
								
					<div class="divider"></div> 
					<dl>
						<dt style="width: 100px;">
							<label>
								联系方式:
							</label>
						</dt>
						<dd>
							<input name="channelTel" value="${channelInfo.channelTel }" type="text"
								  alt="请输入手机号" size="30" />
						</dd>
					</dl>
					<div class="divider"></div>
					<p >
						<label style="width: 100px;">备注:</label>
						<textarea  name="remark" rows="5" cols="60" maxlength="50">${channelInfo.remark }</textarea>
					</p>
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