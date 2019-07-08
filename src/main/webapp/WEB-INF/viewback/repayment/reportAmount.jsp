<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="repayment/getReportRepaymentAmount?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						时间从:
						<input type="text" name="reportDateStart"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.reportDateStart }" />
					</td>
					<td>
						到:
						<input type="text" name="reportDateEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.reportDateEnd }" />
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
		<table class="table" layoutH="160" nowrapTD="false">
			<thead>
			<tr>
				<th align="center"  >
					日期
				</th>
				<th align="center"  >
					到期金额
				</th>
				<th align="center" >
					逾期金额
				</th>
				<th align="center"  >
					逾期率
				</th>
				<th align="center" >
					还款率
				</th>
				<th align="center" style="display: none;">
					14天期限逾期率
				</th>
				<th align="center" >
					老用户到期金额
				</th>
				<th align="center" >
					老用户逾期金额
				</th>
				<th align="center" >
					老用户逾期率
				</th>
				<th align="center" >
					老用户还款率
				</th>
				<th align="center" >
					新用户到期金额
				</th>
				<th align="center" >
					新用户逾期金额
				</th>
				<th align="center" >
					新用户逾期率
				</th>
				<th align="center" >
					新用户还款率
				</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="report" items="${pm.items }" varStatus="status">
				<tr target="reportId" rel="${report.id }">
					<td>
						${report.reportDate}
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.expireAmount / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueAmount / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateAmount / 100.00}"/>%
					</td>
					<td>
						<%--<fmt:formatNumber pattern='###,###,##0.00' value="${report.repayRateAmount / 100.00}"/>%--%>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.repaySjRate / 100.00}"/>%
					</td>
					<td style="display: none;">
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateFourteenAmount / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.expireAmountOld / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueAmountOld / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateOldAmount / 100.00}"/>%
					</td>
					<td>
						<%--<fmt:formatNumber pattern='###,###,##0.00' value="${report.repayRateOldAmount / 100.00}"/>%--%>
							<fmt:formatNumber pattern='###,###,##0.00' value="${report.repayOldSjRate / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.expireAmountNew / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueAmountNew / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateNewAmount / 100.00}"/>%
					</td>
					<td>
<%--
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.repayRateNewAmount / 100.00}"/>%
--%>
						<fmt:formatNumber pattern="###,###,##0.00" value="${report.repayNewSjRate / 100.00}"/>%
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>