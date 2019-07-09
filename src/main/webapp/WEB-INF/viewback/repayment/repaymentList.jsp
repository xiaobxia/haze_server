<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="repayment/getRepaymentPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						用户名称:
						<input type="text" name="userAccountLike" id="userAccountLike"
							   value="${params.userAccountLike }" />
					</td>
					<td>
						手机:
						<input type="text" name="userMobileLike" id="userMobileLike"
							   value="${params.userMobileLike }" />
					</td>
					<td>
						预期还款时间:
						<input type="text" name="repaymentTimeStart" id="repaymentTimeStart"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentTimeStart }" />
					</td>
					<td>
						至
						<input type="text" name="repaymentTimeEnd" id="repaymentTimeEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentTimeEnd }" />
					</td>
					<td>
						状态:
						<select id="statuses" name="statuses">
							<option value="">全部</option>
							<c:if test="${statusesType == 'ALL'}">
								<option value="21">还款中</option>
								<option value="23">部分还款</option>
								<option value="-11">已逾期</option>
								<option value="-20">已坏账</option>
							</c:if>
							<c:if test="${statusesType != 'ALL'}">
								<option <c:if test="${'21' == params.statuses[0]}"> selected </c:if> value="21">还款中</option>
								<option <c:if test="${'23' == params.statuses[0]}"> selected </c:if>  value="23">部分还款</option>
								<option <c:if test="${'-11' == params.statuses[0]}"> selected </c:if>  value="-11">已逾期</option>
								<option <c:if test="${'-20' == params.statuses[0]}"> selected </c:if>  value="-20">已坏账</option>
							</c:if>
						</select>
					</td>
					<td id="overdueStatusTd">
						逾期时长:
						<select id="overdueStatus" name="overdueStatus">
							<option value="">全部</option>
							<option <c:if test="${'S1' == params.overdueStatus}"> selected </c:if> value="S1">S1</option>
							<option <c:if test="${'S2' == params.overdueStatus}"> selected </c:if> value="S2">S2</option>
							<option <c:if test="${'S3' == params.overdueStatus}"> selected </c:if> value="S3">S3</option>
							<option <c:if test="${'M1-M2' == params.overdueStatus}"> selected </c:if> value="M1-M2">M1-M2</option>
						</select>
					</td>
					<td>
						借款金额:
						<select id = "productAmount" name = "productAmount"></select>
						<input type="hidden" value="${params.productAmount}" id="product_amount_choosed"/>
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
	<div class="pageContent" id="r-l-r">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
		<table class="table" style="width: 100%;" layoutH="155" nowrapTD="false">
			<thead>
			<tr>
				<th align="center"  >
					序号
				</th>
				<th align="center"  >
					姓名
				</th>
				<th align="center" >
					手机号
				</th>
				<th align="center">
					成功还款次数
				</th>
				<th align="center" >
					借款到账金额
				</th>
				<th align="center" >
					服务费
				</th>
				<th align="center" >
					总需要还款金额
				</th>
				<th align="center" >
					已还金额
				</th>
				<th align="center" >
					放款时间
				</th>
				<th align="center" >
					预期还款时间
				</th>
				<th align="center" >
					逾期天数
				</th>
				<th align="center" class="loanStatusTitle">
					状态
				</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="repayment" items="${pm.items }" varStatus="status">
				<tr target="repaymentId" rel="${repayment.id }">
					<td>
							${status.count}
					</td>
					<td class="userName">
							${repayment.realname}
					</td>
					<td class="userPhone">
							${repayment.userPhone }
					</td>
					<td class="loanSuccessCount">
							${repayment.loanCount}
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentPrincipal / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentInterest / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentAmount / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentedAmount / 100.00}"/>
					</td>
					<td class="time">
						<fmt:formatDate value="${repayment.creditRepaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${repayment.repaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
							${repayment.lateDay }
					</td>
					<td class="loanStatusName">
							${BORROW_STATUS_ALL[repayment.status]}
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>

<script type="text/javascript">
	DWZ.renderR()
	$(function(){
		var $statuses = $("#statuses");
		$statuses.bind("change", function(){
			if($statuses.val() == '-11'){
				$("#overdueStatusTd").show();
			}
			else{
				$("#overdueStatusTd").hide();
				$("#overdueStatus").val("");
			}
		});
		$statuses.trigger("change");
	});
	function changeDHExcel(obj){
		var statuses = "";
		<c:forEach items="${params.statuses}" var="status">
		statuses += "&statuses=" + ${status};
		</c:forEach>
		var href=$(obj).attr("href");
		var userAccountLike=$("#userAccountLike").val();
		var userMobileLike=$("#userMobileLike").val();
		var overdueStatus=$("#overdueStatus").val();
		var toHref = href + "&userAccountLike=" + userAccountLike + "&userMobileLike=" + userMobileLike + "&overdueStatus=" + overdueStatus + statuses;
		$(obj).attr("href",toHref);
		return true;
	}
	if (renderLoanSuccessCount) {
		renderLoanSuccessCount()
	}
	if (renderLoanStatusName) {
		renderLoanStatusName()
	}
</script>