<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<%-- 								action="${ctx}/xianjin/sysXianjinCard/jsLoanPersonList" method="post"  --%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="channel/getChannelInfoPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td style="width: 2%;">用户名称： <input id="name" name="name"
						style="width: 170px; margin-top: 12px" class="form-control m-b"
						value="${jsLoanPerson.name}">
					</td>
					<td style="width: 2%;">手机号码： <input id="phone" name="phone"
						style="width: 170px; margin-top: 12px" class="form-control m-b"
						value="${jsLoanPerson.phone}">
					</td>
					<td style="width: 4%;">邀请人手机号码： <input id="inviteCode"
						name="inviteCode" style="width: 170px; margin-top: 12px"
						class="form-control m-b" value="${jsLoanPerson.inviteCode}">
					</td>

				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId" />
		</jsp:include>
		<table id="contentTable"
			class="footable table table-stripped toggle-arrow-tiny">
			<thead>
				<tr>
					<th>姓名</th>
					<th>手机号</th>
					<th>身份证</th>
					<th>注册时间</th>
					<th>邀请人名称</th>
					<th>邀请人手机号</th>
					<th>操作</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="jsLoanPerson">
					<tr>


						<td>${jsLoanPerson.name}</td>
						<td>${jsLoanPerson.phone}</td>
						<%-- 										 <c:if test="${not empty jsStepRecord.effectiveCount}"> --%>
						<%--                                             	<td>${jsStepRecord.effectiveCount}</td> --%>
						<%--                                          </c:if> --%>
						<%--                                          <c:if test="${empty jsStepRecord.effectiveCount}"> --%>
						<!--                                             	<td>0</td> -->
						<%--                                          </c:if> --%>
						<td>${jsLoanPerson.idNumber}</td>
						<td><fmt:formatDate value="${jsLoanPerson.createdAt}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${jsLoanPerson.inviteName}</td>
						<td>${jsLoanPerson.inviteCode}</td>
						<td>
							<div class="btn-group">
								<button data-toggle="dropdown"
									class="btn btn-success btn-sm dropdown-toggle">
									操作 <span class="caret"></span>
								</button>
								<ul class="dropdown-menu">

									<li><a
										href="${ctx}/xianjin/sysXianjinCard/jsLoanPersonEditor?id=${jsLoanPerson.id}"
										class="btn btn-white btn-sm"><i class="fa fa-user "></i>
											用户修改</a></li>
									<li><a
										href="${ctx}/xianjin/sysXianjinCard/jsLoanPersonAdd?userName=${jsLoanPerson.name}&cardNum=${jsLoanPerson.idNumber}&userPhone=${jsLoanPerson.phone}&awardType=APPROVE&invitePhone=${jsLoanPerson.inviteCode}"
										class="btn btn-white btn-sm"><i class="fa fa-pencil"></i>
											行为补录-认证成功</a></li>
									<li><a
										href="${ctx}/xianjin/sysXianjinCard/jsLoanPersonAdd?userPhone=${jsLoanPerson.phone}&awardType=BORROW"
										class="btn btn-white btn-sm"><i class="fa fa-pencil"></i>
											行为补录-借款成功</a></li>
									<li><a
										href="${ctx}/xianjin/sysXianjinCard/jsLoanPersonAdd?userPhone=${jsLoanPerson.phone}&awardType=REPAYMENT"
										class="btn btn-white btn-sm"><i class="fa fa-pencil"></i>
											行为补录-还款成功</a></li>

								</ul>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>