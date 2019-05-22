<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="borrowFeeToBePaid/getBorrowFreeToBePaidCPage?bType=${bType}&myId=${params.myId}"
	method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户ID: <input type="text" name="userId" id="userId"
						value="${params.userId }" />
					</td>
					<td>状态： <select name="status" id="borrowStatu">
							<option value="1" selected="selected" >待打款</option>
					</select>
					</td> 
					<td>
							创建时间：
							<input type="text" name="fromTime" id="fromTime" value="${params.fromTime}" class="date textInput " datefmt="yyyy-MM-dd" />
							到<input type="text" name="toTime" id="toTime" value="${params.toTime}" class="date textInput " datefmt="yyyy-MM-dd"  />
						</td>
						<td>
							放款账户：
							<select name="capitalType" id="capitalType" >
								<option value="">全部</option>
								<c:forEach var="capitalType" items="${LOAN_ACCOUNTMap}">
					 
									<option value="${capitalType.key}" <c:if test="${capitalType.key eq params.capitalType}">selected="selected"</c:if> >
									${fn:substring(capitalType.value, 0, fn:indexOf(capitalType.value, ";;") )}
									</option>
								 </c:forEach>
								</select>
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
		<table class="table" style="width: 100%;" layoutH="170"
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
						<td><fmt:parseNumber type="number"
								value="${borrow.loanInterests/100}" integerOnly="true"   />
								</td>
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


		<div class="divider"></div>
		<div class="pageFoot">
			<div class="searchBar">
				<table class="searchContent">
					<tbody>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;总计：</td>

						<td>放款总额</td>
						<td style="color: red;">${moneyAmountSum }</td>
						<td>元</td>
					</tbody>
				</table>
			</div>
		</div>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>

<script type="text/javascript">
	if("${message}"){
		alertMsg.error(${message});
	}
	
</script>