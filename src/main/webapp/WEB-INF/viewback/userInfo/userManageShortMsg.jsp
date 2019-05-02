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
<title>用户短信列表</title>
</head>
<body>
 <form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/gotoUserShortMsg?userId=${searchParams.userId}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>短信内容:
						 <input type="text" name="messageContent" value="${searchParams.messageContent }" />
					</td>
					<td>短信号码:
						<input type="text" name="phone" value="${searchParams.phone }" />
					</td>
					<!--<td>接收时间:
						<input type="text" name="messageDate" value="${searchParams.messageDate }" />
					</td>
					--><td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<table class="list" width="100%" layoutH="114">
			<thead>
				<tr>
					<th align="center">序号</th>
					<th align="center">用户ID</th>
					<th align="center">短信内容</th>
					<th align="center">短信号码</th>
					<th align="center">接收时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="userShortMsg" items="${pm.items }" varStatus="status" >
					<tr target="id" rel="${userShortMsg.id }">
						<td align="center">${userShortMsg.id }</td>
						<td align="center">${userShortMsg.userId}</td>
						<td align="center">${userShortMsg.messageContent }</td>
						<td align="center">${userShortMsg.phone }</td>
						<td align="center">${userShortMsg.messageDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</form>
</body>
</html>
