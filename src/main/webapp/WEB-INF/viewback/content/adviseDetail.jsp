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
	<style>
		.pageFormContent .clearfix {
			margin-bottom: 20px;
		}
	</style>
</head>
<body>
	<div class="pageContent">
			<div class="pageFormContent" layoutH="106">
				<div class="clearfix">
					<label>反馈ID：</label>
					<input type="text" value="${advise.id}"  disabled="disabled"/>
				</div>
				<div class="clearfix">
					<label>用户手机号码：</label>
					<input type="text" value="${advise.userPhone}" disabled="disabled"/>
				</div>
				<div class="clearfix">
					<label>反馈内容：</label>
					<textarea name="aboutIntroduce" cols="100" rows="6" disabled="disabled" maxlength="420">${advise.adviseContent}</textarea>
				</div>
				<div class="clearfix">
					<label>反馈时间：</label>
					<input type="text" value="<fmt:formatDate value="${advise.adviseAddtime}" pattern="yyyy-MM-dd HH:mm" />" disabled="disabled"/>
				</div>
				<div class="clearfix">
					<label>反馈设备号：</label>
					<textarea name="aboutIntroduce"  cols="100" rows="2" disabled="disabled" maxlength="420">${advise.adviseAddip}</textarea>
				</div>
				<c:if test="${advise.adviseStatus==0}">
				<div class="clearfix">
					<label>处理状态：</label>
					<input type="text" value="未处理" disabled="disabled"/>
				</div>
				</c:if>
				
				<c:if test="${advise.adviseStatus==1}">
					<div class="clearfix">
						<label>处理状态：</label>
						<input type="text" value="已处理" disabled="disabled"/>
					</div>
					<div class="clearfix">
						<label>内容提要：</label>
						<textarea name="aboutIntroduce" cols="100" rows="6" disabled="disabled" maxlength="420">${advise.adviseConnectInfo}</textarea>
					</div>
					<div class="clearfix">
						<label>处理内容：</label>
						<textarea name="aboutIntroduce" cols="100" rows="6" disabled="disabled" maxlength="420">${advise.adviseFeedback}</textarea>
					</div>
					<div class="clearfix">
						<label>处理时间：</label>
						<input type="text"  value="<fmt:formatDate value="${advise.feedDate}" pattern="yyyy-MM-dd HH:mm" />" disabled="disabled"/>
					</div>
					<div class="clearfix">
						<label>处理IP：</label>
						<input type="text" value="${advise.feedIp}" disabled="disabled"/>
					</div>
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
