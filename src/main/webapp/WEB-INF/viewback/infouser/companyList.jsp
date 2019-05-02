<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="zbUser/findUserList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						用户名:
						<input type="text" name="userAccount"
							value="${params.userAccount }" />
					</td>
					<td>
						公司名:
						<input type="text" name="companyName"
							value="${params.companyName }" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									查询
								</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
			<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
				<thead>
					<tr>
					<th align="center" width="50">序号</th>
					<th align="center" width="100">用户名</th>
					<th align="center" width="100">手机</th>
					<th align="center" width="120" >邮箱</th>
					<th align="center" width="120" >注册时间</th>
					<th align="center" width="150">企业/机构名称</th>
					<th align="center" width="150">别名</th>
					<th align="center" width="150">企业/机构类型</th>
					<th align="center" width="100">法人</th>
<!--					<th align="center" width="100">企业注册时间</th>-->
					<th align="center" width="100">注册资金</th>
<!--					<th align="center" width="150">注册地址</th>-->
					<th align="center" width="150">现地址</th>
<!--					<th align="center" width="120">组织机构代码</th>-->
					<th align="center" width="120">营业执照号</th>
<!--					<th align="center" width="120">税务登记号</th>-->
					<th align="center" width="120">经营范围</th>
					<th align="center" width="120">认证状态</th>
					<th align="center" width="120">用户状态</th>
					<th align="center" width="120">附件</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="company" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${company.user.id }">
							<td >${status.count}</td>
							<td >${company.user.userAccount }</td>
							<td >${company.user.userMobile }</td>
							<td >${company.user.userEmail }</td>
							<td ><fmt:formatDate value="${company.user.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td >${company.companyName }</td>
							<td >${company.otherName }</td>
							<td >${company.companyType }</td>
							<td >${company.companyUserName }</td>
<!--							<td ><fmt:formatDate value="${company.registerTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>-->
							<td ><fmt:formatNumber value="${company.registerCapital }" pattern="###,###,###,##0.00"></fmt:formatNumber></td>
<!--							<td >${company.registerAddress }</td>-->
							<td >${company.presentAddress }</td>
<!--							<td >${company.organizationCode }</td>-->
							<td >${company.businessLicenseId }</td>
<!--							<td >${company.taxId  }</td>-->
							<td >${company.businessScope  }</td>
							<td >${company.viewStatus  }</td>
							<td >${company.user.statusView  }</td>
							<td>
							 <c:set value="${ fn:split(company.otherPics, '@@') }" var="pics" />
				            	<c:forEach var="pic" items="${pics}" varStatus="count">
					            	<c:if test="${not empty pic}">
					            	 <a href="${path }${pic}" target="blank">附件${count.count }</a>
					            	</c:if>
				            	</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
	</c:if>
</form>