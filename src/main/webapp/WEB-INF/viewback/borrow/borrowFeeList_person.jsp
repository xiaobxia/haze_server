<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="backBorrowOrder/getBorrowFreeCPage?bType=${bType}&myId=${params.myId}"
	method="post">
		<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>招行订单号: <input type="text" name="yurref" id="yurref1"
						value="${params.yurref }" />
					</td>
					<td>用户ID: <input type="text" name="userId" id="userId"
						value="${params.userId }" />
					</td>
<!-- 					<td>手机: <input type="text" name="userPhone" -->
<%-- 						value="${params.userPhone }" /> --%>
<!-- 					</td> -->
<!-- 					<td>公司名称: <input type="text" name="companyName" -->
<%-- 						value="${params.companyName }" /> --%>
<!-- 					</td> -->
					<td>状态： <select name="status" id="borrowStatu">
							<option value="">全部</option>
 							<c:forEach var="borrowStatus" items="${allstatus}">
							<option value="${borrowStatus.key}"  <c:if test="${borrowStatus.key eq params.status}">selected="selected"</c:if>>${borrowStatus.value}</option>
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
					<th align="left">用户Id</th>
					<th align="center">借款订单号</th>
					<th align="center">招行订单号</th>
					<th align="center">金额(元)</th>
					<th align="center">创建时间</th>
					<th align="center">状态</th>
					<th align="center">放款备注</th>
					<th align="center">更新时间</th>
					<th align="center">放款时间</th>



				</tr>
			</thead>
			<tbody>
				<c:forEach var="borrow" items="${pm.items }" varStatus="status">
					<tr target="yurref" rel="${borrow.yurref }">
						<td>${status.count}</td>
						<td>${borrow.userId}</td>
						<td>${borrow.assetOrderId}</td>
						<td>${borrow.yurref}</td>
						<td><fmt:formatNumber type="number"
								value="${borrow.loanInterests/100}" pattern="0.00"
								maxFractionDigits="0" /></td>
						<td><fmt:formatDate value="${borrow.createdAt }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>

						<td>${borrow.statusShow}</td>
						<td>${borrow.payRemark }</td>



						<td><fmt:formatDate value="${borrow.updatedAt }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatDate value="${borrow.loanTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>

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
	
	
	function changeFeeCExcel(obj){
	 
		var href=$(obj).attr("href");
		var yurref=$("#yurref1").val();
		var userId=$("#userId").val();
		var borrowStatu=$("#borrowStatu").val();
		var companyName=$("#companyName").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		var toHref=href+"&yurref="+yurref+"&userId="+userId+"&status="+borrowStatu+"&beginTime="+beginTime+"&endTime="+endTime;
	 
		$(obj).attr("href",toHref);
	}
</script>