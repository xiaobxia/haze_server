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
	<title>Insert user</title>
	<style>
		.paiban-box{
			border: 1px solid #ddd;
		}
		.paiban-box tr td{
			line-height: 25px;
			padding: 10px;

		}
		.paiban-box tr:not(:first-child) td {
			text-align: left;
		}
		.paiban-box tr td:first-child {
			min-width: 60px;
		}
		.paiban-box tr th{
			line-height: 25px;
			height: 35px;
		}
		.paiban-box tr td,
		.paiban-box tr th {
			border-bottom: 1px solid #eee;
			text-align: center;
		}
		.paiban-box tr td,
		.paiban-box tr th {
			border-right: 1px solid #eee;
		}
		.paiban-box td div {
			white-space: normal;
			height: auto;
			line-height: 25px;
		}
		.paiban-box td .input-box {
			margin-right: 5px;
			display: inline-block;
			width: 90px;
		}
	</style>
</head>
<body>
<div class="pageContent">
	<form id="frm" method="post"  enctype="multipart/form-data" action="#"
		  class="pageForm required-validate">
		<input type="hidden" name="parentId" value="${params.parentId}" />
		<input type="hidden" name = "date" value="${params.date}"/>
		<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<p><label>日期:${params.date}</label></p>
			<table class="paiban-box" style="width: 100%">
				 <tr>
					 <th>
						 班次
					 </th>
					 <th>
						 客服
					 </th>
				 </tr>
				<tr>
					<td>早班</td>
					<td>
						<c:forEach items="${morCustomerList }" var="userName" varStatus="varStatus">
							<span class="input-box"> ${userName}</span>
								<%--<c:if test="${not empty user}">--%>
								<%--<script type="text/javascript">--%>
								<%--$("input[name='customerName'][value='${user.id}']").attr("checked",true);--%>
								<%--</script>--%>
								<%--</c:if>--%>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>晚班</td>
					<td>
						<c:forEach items="${nigCustomerList }" var="userName" varStatus="varStatus">
							<span class="input-box"> ${userName}</span>
							<%--<c:if test="${not empty user}">--%>
							<%--<script type="text/javascript">--%>
							<%--$("input[name='customerName'][value='${user.id}']").attr("checked",true);--%>
							<%--</script>--%>
							<%--</c:if>--%>
						</c:forEach>
					</td>
				</tr>

			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								确认
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