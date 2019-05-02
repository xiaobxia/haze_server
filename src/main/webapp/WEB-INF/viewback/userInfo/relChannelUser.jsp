<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分配推广员</title>
</head>
<body>
	<div class="pageContent">
		<form id="frm" method="post" action="redenvelope/${PRE_PATH_VIEW}saveOrUpdateRedenvelope" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
			<input type="hidden" name="right_id" value="${right_id}" /> 
			<input type="hidden" name="id" id="id" value="${redenvelope.id}">
			<input type="hidden" name="releaseUserIds" id="releaseUserIds" value="" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>渠道商名称:</label>
					<input type="text" name="channelName"  value="${channelInfo.channelName}"  
					size="30" alt="请输入活动名称" <c:if test="${grant}">readonly="readonly"</c:if>/>
				</p>
				<div class="divider"></div>
				<p>
					<label>渠道商编码:</label>
					<input type="text" name="channelCode"  value="${channelInfo.channelCode}" 
					size="30" alt="请输入红包金额" <c:if test="${grant}">readonly="readonly"</c:if>/>
				</p>
				<div class="divider"></div>
				<p>
					<label>一级佣金费率(%):</label>
					<input type="text" name="apr"  value="${channelInfo.apr }"  
					size="30" alt="投资大于等于此金额才可使用" <c:if test="${grant}">readonly="readonly"</c:if>/>
				</p>
				<div class="divider"></div>
				<p ><label>负责人:</label>
					<input type="text"  name="operatorName"  id="operatorName" size="30"
							value="${channelInfo.operatorName }"  
							<c:if test="${grant}">readonly="readonly"</c:if>/>
					</p>
				<div class="divider"></div>
				<p><label>联系方式:</label>
				<input type="text"  name="channelTel"  id="channelTel" size="30"
						 value="${channelInfo.channelTel }"   
						<c:if test="${grant}">readonly="readonly"</c:if>/>
				<div class="divider"></div>
				 
					<p>
						<label>分配推广员名单：</label>
						<span id="showCheckUserName" style="color: red; float: left;"></span>
						<a class="btnLook" width="380" lookupgroup="user" rel="page2" href="channel/getAllFrontUser?type=user&isSystem=${USER_TYPE_GENERAL},${USER_TYPE_ENTERPRISE}">所有用户</a>
					</p>
					<div class="divider"></div>
				 
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button id="releaseBtn" type="submit">分配</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
<script language="JavaScript" type="text/javascript">

</script>
</html>