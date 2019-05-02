<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="repayment/getReportRepaymentAll?myId=${params.myId}" method="post">
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
		<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
			<thead>
			<tr>
				<th align="center"  >
					日期
				</th>
				<th align="center"  >
					当前借款总数量
				</th>
				<th align="center" >
					当前借款总额
				</th>
				<th align="center"  >
					已经还款总数量
				</th>
				<th align="center" >
					已经还款总额
				</th>
				<th align="center" >
					逾期中数量
				</th>
				<th align="center" >
					逾期中总额
				</th>
				<th align="center" >
					S1级逾期率(按金额)
				</th>
				<th align="center" >
					S2级逾期率(按金额)
				</th>
				<th align="center" >
					S3级逾期率(按金额)
				</th>
				<th align="center" >
					M3级逾期率(按金额)
				</th>
				<th align="center" >
					S1级逾期率(按单数)
				</th>
				<th align="center" >
					S2级逾期率(按单数)
				</th>
				<th align="center" >
					S3级逾期率(按单数)
				</th>
				<th align="center" >
					M3级逾期率(按单数)
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
						${report.allBorrowCount}
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.allBorrowAmount / 100.00}"/>
					</td>
					<td>
						${report.allRepayCount}
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.allRepayAmount / 100.00}"/>
					</td>
					<td>
						${report.allOverdueCount}
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.allOverdueAmount / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateS1Amount / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateS2Amount / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateS3Amount / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateM3Amount / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateS1Count / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateS2Count / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateS3Count / 100.00}"/>%
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${report.overdueRateM3Count / 100.00}"/>%
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>