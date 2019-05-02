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
<title>用户通讯录</title>
</head>
<body>
 <form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/gotoUserContacts?userId=${searchParams.userId}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>联系人名称:
						 <input type="text" name="contactName" value="${searchParams.contactName }" />
					</td>
					<td>联系人号码:
						<input type="text" name="userPhone" value="${searchParams.userPhone }" />
					</td>
					<td>
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
					<th align="center">用户名称/手机</th>
					<th align="center">联系人名称</th>
					<th align="center">联系人号码</th>
					<th align="center">上传时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="userContacts" items="${pm.items }" varStatus="status" >
					<tr target="id" rel="${userContacts.id }">
						<td align="center">${userContacts.id }</td>
						<td align="center">${userContacts.userId}</td>
						<td align="center">${userContacts.userName }</td>
						<td align="center">${userContacts.contactName }</td>
						<td align="center">${userContacts.contactPhone }</td>
						<td align="center"><fmt:formatDate value="${userContacts.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
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
