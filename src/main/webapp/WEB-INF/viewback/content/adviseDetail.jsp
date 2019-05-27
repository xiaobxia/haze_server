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
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>反馈ID：</label>
					<input type="text" value="${advise.id}"  disabled="disabled"/>
				</p>
				<div class="divider"></div>
				
				<p>
					<label>用户手机号码：</label>
					<input type="text" value="${advise.userPhone}" disabled="disabled"/>
				</p>
				<div class="divider"></div>
				
				<p style="height: 160px; width: 950px;">
					<label>反馈内容：</label>
					<textarea name="aboutIntroduce" cols="100" rows="6" disabled="disabled" maxlength="420">${advise.adviseContent}</textarea>
				</p>
				<div class="divider"></div>
				
				<p>
					<label>反馈时间：</label>
					<input type="text" value="<fmt:formatDate value="${advise.adviseAddtime}" pattern="yyyy-MM-dd HH:mm" />" disabled="disabled"/>
				</p>
				<div class="divider"></div>
				
				<p style="height: 35px; width: 950px;">
					<label>反馈设备号：</label>
					<textarea name="aboutIntroduce"  cols="100" rows="2" disabled="disabled" maxlength="420">${advise.adviseAddip}</textarea>
				</p>
				<div class="divider"></div>
				<c:if test="${advise.adviseStatus==0}">
				<p>
					<label>处理状态：</label>
					<input type="text" value="未处理" disabled="disabled"/>
				</p>
				<div class="divider"></div>
				</c:if>
				
				<c:if test="${advise.adviseStatus==1}">
					<p>
						<label>处理状态：</label>
						<input type="text" value="已处理" disabled="disabled"/>
					</p>
					<div class="divider"></div>
					
					<p style="height: 160px; width: 950px;">
						<label>内容提要：</label>
						<textarea name="aboutIntroduce" cols="100" rows="6" disabled="disabled" maxlength="420">${advise.adviseConnectInfo}</textarea>
					</p>
					<div class="divider"></div>
					
					<p style="height: 160px; width: 950px;">
						<label>处理内容：</label>
						<textarea name="aboutIntroduce" cols="100" rows="6" disabled="disabled" maxlength="420">${advise.adviseFeedback}</textarea>
					</p>
					<div class="divider"></div>
					
					<p>
						<label>处理时间：</label>
						<input type="text"  value="<fmt:formatDate value="${advise.feedDate}" pattern="yyyy-MM-dd HH:mm" />" disabled="disabled"/>
					</p>
					<div class="divider"></div>
					
					<p>
						<label>处理IP：</label>
						<input type="text" value="${advise.feedIp}" disabled="disabled"/>
					</p>
					<div class="divider"></div>
				</c:if>
				
			</div>

			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	</div>
</body>
</html>
