<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="backBorrowOrder/getBorrowOrderCheckPage?myId=${params.myId}"
	method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户姓名: <input type="text" name="realname" id="realname"
						value="${params.realname }" />
					</td>
					<td>手机: <input type="text" name="userPhone" id="userPhone"
						value="${params.userPhone }" />
					</td>

					<td>状态： <select name="status" id="borrowStatus">
							<option value="">全部</option>
							<c:forEach var="borrowStatus" items="${allstatus}">
								<option value="${borrowStatus.key}"
									<c:if test="${borrowStatus.key eq params.status}">selected="selected"</c:if>>${borrowStatus.value}</option>
							</c:forEach>

					</select>
					</td>
					<td>资产所属： <select name="capitalType" id="capitalType">
							<option value="">全部</option>
							<c:forEach var="capitalTypeMap" items="${capitalTypeMap}">
								<option value="${capitalTypeMap.key}"
									<c:if test="${capitalTypeMap.key eq params.capitalType}">selected="selected"</c:if>>${capitalTypeMap.value}</option>
							</c:forEach>

					</select>
					</td>
						<td>
							放款时间：
							<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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


		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId" />
		</jsp:include>
		<table class="table" style="width: 100%;" layoutH="112"
			nowrapTD="false">
			<thead>
				<tr>
					<th align="center">序号</th>
					<th align="left">订单号</th>
					<th align="center">姓名</th>
					<th align="center">手机号</th>
					<%--<th align="center">是否是老用户</th>--%>
					<th>成功还款次数</th>
					<th align="center">借款金额(元)</th>
					<th align="center">借款期限</th>
					<th align="center">服务费利率(万分之一)</th>
					<th align="center">服务费(元)</th>
					<th align="center">下单时间</th>
					<th align="center">放款时间</th>
					<th align="center">更新时间</th>
					<th align="center">资产所属</th>

					<th align="center">状态</th>
					<!-- 						<th align="center"  > -->
					<!-- 							操作 -->
					<!-- 						</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach var="borrow" items="${pm.items }" varStatus="status">
					<tr target="sid_support" rel="${borrow.id }">
						<td>${status.count}</td>
						<td>${borrow.assetOrderId}</td>
						<td>${borrow.realname}</td>
						<td>${borrow.userPhone }</td>
						<%--<td><c:if test="${borrow.customerType ==0}">新用户</c:if> <c:if
								test="${borrow.customerType ==1}">老用户</c:if></td>
						<td>--%>
						<td class="loanSuccessCount">${borrow.loanCount}</td>
							<%--  							${borrow.moneyAmount/100 } --%> <fmt:formatNumber
								type="number" value="${borrow.moneyAmount/100}" pattern="0.00"
								maxFractionDigits="0" />
						</td>
						<td>${borrow.loanTerm }</td>
						<td>${borrow.apr}</td>
						<td><fmt:formatNumber type="number"
								value="${borrow.loanInterests/100}" pattern="0.00"
								maxFractionDigits="2" /></td>
						<td><fmt:formatDate value="${borrow.orderTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatDate value="${borrow.loanTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${borrow.updatedAt }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><c:forEach var="capitalTypeMap" items="${capitalTypeMap}">
								<c:if test="${capitalTypeMap.key eq borrow.capitalType}">${capitalTypeMap.value}</c:if>
								</option>
							</c:forEach></td>

						<td><c:forEach var="borrowStatus" items="${allstatus}">
								<c:if test="${borrowStatus.key eq borrow.status}">${borrowStatus.value}</c:if>
								</option>
							</c:forEach></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>

<script type="text/javascript">
	if("${message}"){
		alertMsg.error(${message});
	}
	
	function downLoadExcel(obj){
 
		var href=$(obj).attr("href");
	 
		var realname=$("#realname").val();
		var userPhone=$("#userPhone").val();
		var borrowStatus=$("#borrowStatus").val();
		var capitalType=$("#capitalType").val();
		var companyName=$("#companyName").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		var toHref=href+"&realname="+realname+"&userPhone="+userPhone+"&status="+borrowStatus+"&capitalType="+capitalType+"&beginTime="+beginTime+"&endTime="+endTime;
 
		$(obj).attr("href",toHref);
	}
	if (renderLoanSuccessCount) {
		renderLoanSuccessCount()
	}
</script>