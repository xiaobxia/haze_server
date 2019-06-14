<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="loanreport/getLoanReportPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					 
					<td>
							添加时间：
							<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTimeChangeEnd" id="endTimeChangeEnd" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
		<table class="table" layoutH="220" nowrapTD="false" ifScrollTable="true">
			<thead>
				<tr>
					<th align="center"  >
						序列
					</th>
					<th align="center"  >
						日期
					</th>
					<th align="center"  >
						注册人数
					</th>
					<th align="center"  >
						借款人数
					</th>
					<th align="center"  >
						成功借款人数
					</th>
					<th align="center"  >
						放款单数
					</th>
					<th align="center"  >
						7天期限放款单数
					</th>
					<th align="center" style="display: none">
						14天期限放款单数
					</th>
					<th align="center" >
						放款总额
					</th>
					<th align="center" >
						7天期限放款总额
					</th>
					<th align="center" style="display: none">
						14天期限放款总额
					</th>
					<th align="center" >
						老用户放款单数
					</th>
					<th align="center" >
						老用户放款总额
					</th>
					<th align="center" >
						新用户放款单数
					</th>
					<th align="center" >
						新用户放款总额
					</th>
					<th align="center" >
						更新时间
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="loan" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${loan.id }">
						<td>
							${status.index+1}
						</td>
						<td>
						<fmt:formatDate value="${loan.reportDate}" pattern="yyyy-MM-dd"/>
							
						</td>
						<td>
							${loan.registerCount}
						</td>
						<td>
							${loan.borrowApplyCount}
						</td>
						<td>
							${loan.borrowSucCount}
						</td>
						<td>
							${loan.loanOrderCount}
						</td>
						<td>
							${loan.loanSevendayCount}
						</td>
						<td style="display: none">
							${loan.loanFourdayCount}
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${loan.moneyAmountCount}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${loan.sevendayMoenyCount}"/>
						</td>
						<td style="display: none">
							<fmt:formatNumber pattern='###,###,##0.00' value="${loan.fourdayMoneyCount}"/>
						</td>
						<td>
							${loan.oldLoanOrderCount}
						</td>
						 <td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${loan.oldLoanMoneyCount}"/>
						</td>
						  <td>
							${loan.newLoanOrderCount}
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${loan.newLoanMoneyCount}"/>
						</td>
						<td>
						<fmt:formatDate value="${loan.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="divider"></div>
		<div class="pageFoot">
			<div class="searchBar">
				<table class="searchContent" >
					<tbody>
						<td>
								&nbsp;&nbsp;&nbsp;&nbsp;总计：
						</td>

						 <td>
								放款总额
						</td>
						<td style="color:red;">
								<fmt:formatNumber pattern='###,###,##0.00' value="${moneyCount}"/>
						</td>
						<td>
								元
						</td>
					</tbody>
					<tbody>
						<td>

						</td>
						<td>
								放款单数
						</td>
						 <td style="color:red;">
								${loanCount}
						</td>
						<td>
								笔
						</td>
					</tbody>
				</table>
			</div>
		</div>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
	
</form>