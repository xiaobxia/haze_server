<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>编辑内容</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="content/updateAdvise?parentId=${params.parentId }"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${advise.id}">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>用户手机号码：</label>
					<input  type="text"   value="${advise.userPhone}"  disabled="disabled"/>
				</p>
				<div class="divider"></div>
				
				<p style="height: 160px; width: 950px;">
					<label>反馈内容：</label>
					<textarea name="aboutIntroduce"  cols="100" rows="6" disabled="disabled" maxlength="420">${advise.adviseContent}</textarea>
				</p>
				<div class="divider"></div>
				
				<p>
					<label>反馈时间：</label>
					<input type="text"  size="30" value="<fmt:formatDate value="${advise.adviseAddtime}" pattern="yyyy-MM-dd HH:mm" />" maxlength="50" disabled="disabled"/>
				</p>
				<div class="divider"></div>
				<c:if test="${not empty advise.adviseConnectInfo}">
					<p style="height: 160px; width: 950px;">
						<label>内容提要：</label>
						<textarea name="adviseConnectInfo" cols="100" rows="6" maxlength="420" disabled="disabled">${advise.adviseConnectInfo}</textarea>
					</p>
					<div class="divider"></div>
				</c:if>
				<c:if test="${empty advise.adviseConnectInfo}">
				<p style="height: 160px; width: 950px;">
						<label>内容提要：</label>
						<textarea class="required" name="adviseConnectInfo" cols="100" rows="6" maxlength="420"></textarea>
					</p>
				<div class="divider"></div>
				</c:if>
			</div>

			<div class="formBar">
				<ul>
				<c:if test="${empty advise.adviseConnectInfo}">
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div>
					</li>
				</c:if>
					
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
</html>
