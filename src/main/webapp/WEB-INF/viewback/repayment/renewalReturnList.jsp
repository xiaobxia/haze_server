<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="return/getRenewalReturnPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<%-- <td>
						用户名称:
						<input type="text" name="userAccountLike" id="userAccountLike"
							   value="${params.userAccountLike }" />
					</td> --%>
					<td>
						手机:
						<input type="text" name="phone" id="phone"
							   value="${params.phone }" />
					</td>
					<td>
						续期时间:
						<input type="text" name="orderTime" id="orderTime"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.orderTime}" />-
						<input type="text" name="orderTimeEnd" id="orderTimeEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.orderTimeEnd}" />
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
						序号
					</th>
					<th align="center"  >
						续期ID
					</th>
					<th align="center"  >
						姓名
					</th>
					<th align="center" >
						手机号
					</th>
					<%--<th align="center"  >
						是否是老用户
					</th>--%>
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
						续期前还款时间
					</th>
					<th align="center" >
						预期还款时间
					</th>
					<th align="center" >
						续期次数
					</th>
					<th align="center" >
						续期天数
					</th>
					<th align="center" >
						续期金额
					</th>
					<th align="center"  >
						续期方式
					</th>
					<th align="center"  >
						续期时间
					</th>
					<th align="center"  >
						还款状态
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="renewal" items="${pm.items }" varStatus="status">
					<tr target="renewalId" rel="${renewal.id }">
						<td>
							${status.count}
						</td>
						<td>
						${renewal.id }
						</td>
						<td>
							${renewal.realname}
						</td>
						<td>
							${renewal.userPhone }
						</td>
						<%--<td>
							<c:if test="${renewal.customerType == '0'}">新用户</c:if>
							<c:if test="${renewal.customerType == '1'}">老用户</c:if>
						</td>--%>
						<td class="loanSuccessCount">
							${renewal.loanCount}
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${renewal.repaymentPrincipal / 100.00}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${renewal.repaymentInterest / 100.00}"/>
						</td>
						<td>
							<fmt:formatDate value="${renewal.oldRepaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<fmt:formatDate value="${renewal.repaymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${renewal.renewalCount}
						</td>
						<td>
							${renewal.renewalDay}
						</td>
						
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${renewal.sumFee / 100.00}"/>
						</td>
						<td>
							<%--${ALL_REPAY_TYPE[renewal.renewalType]}--%>
								<c:choose>
									<c:when test="${renewal.remark == 'RONGBAO'}">融宝</c:when>
									<c:when test="${renewal.remark == 'BAOFOO'}">宝付</c:when>
								</c:choose>
						</td>
						
						<td>
							<fmt:formatDate value="${renewal.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${BORROW_STATUS_ALL[renewal.repayStatus]}
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
/* 	$(function(){
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
	}); */
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
	}

if (renderLoanSuccessCount) {
	setTimeout(function () {
		renderLoanSuccessCount()
	}, 200)
}
</script>