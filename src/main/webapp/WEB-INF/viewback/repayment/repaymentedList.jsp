<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="repayment/getRepaymentedPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						用户手机:
						<input type="text" name="userMobileLike" id="userMobileLike"
							   value="${params.userMobileLike }" />
					</td>
					<td>
						用户名称:    <%--175 29--%>
						<input type="text" name="userAccountLike" id="userAccountLike"
							   value="${params.userAccountLike }" />
					</td>
					<td>
						还款时间:
						<input type="text" name="repaymentedTimeStart" id="repaymentedTimeStart"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentedTimeStart }" />
						至
						<input type="text" name="repaymentedTimeEnd" id="repaymentedTimeEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentedTimeEnd }" />
					</td>
					<td>
						渠道商：
						<select name="superChannelId" id="superChannelId">
							<option value="">全部</option>
							<option value="-999" <c:if test="${searchParams == '-999'}">selected="selected"</c:if> >自然流量</option>
							<c:forEach var="channelSuperInfo" items="${channelSuperInfos}">
								<option value="${channelSuperInfo.id}" <c:if test="${channelSuperInfo.id eq params.superChannelId}">selected="selected"</c:if>>${channelSuperInfo.channelSuperName}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						渠道名称：
						<input type="text" name="channelName" id="channelName"
							   value = "${params.channelName}">
					</td>
				</tr>
				<tr>
					<td>
						订单状态:
						<select id="statuses" name="statuses">
							<option value="">全部</option>
							<c:if test="${statusesType == 'ALL'}">
								<option value="30">正常已还款</option>
								<option value="34">逾期已还款</option>
							</c:if>
							<c:if test="${statusesType != 'ALL'}">
								<option <c:if test="${'30' == params.statuses[0]}"> selected </c:if> value="30">正常已还款</option>
								<option <c:if test="${'34' == params.statuses[0]}"> selected </c:if>  value="34">逾期已还款</option>
							</c:if>
						</select>
					</td>
					<td>
						借款金额:
						<select id = "productAmount" name = "productAmount"></select>
						<input type="hidden" value="${params.productAmount}" id="product_amount_choosed"/>
					</td>
					<td>
						应还时间:
						<input type="text" name="repaymentTimeStart" id="repaymentTimeStart"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentTimeStart }" />
						至
						<input type="text" name="repaymentTimeEnd" id="repaymentTimeEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentTimeEnd }" />
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
	<div class="pageContent" id="r-b-t-c">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
		<table class="table" style="width: 100%;" layoutH="155" nowrapTD="false">
			<thead>
				<tr>
					<th align="center"  >
						序号
					</th>
					<th align="center">
						渠道名称
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
						总需要还款金额
					</th>
					<th align="center" >
						已还金额
					</th>
					<th align="center" >
						放款时间
					</th>
					<th align="center" >
						还款时间
					</th>
					<th align="center" >
						预期还款时间
					</th>
					<th align="center"  >
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
						<td>
							${repayment.channelName}
						</td>
						<td class="userName">
							${repayment.realname}
						</td>
						<td class="userPhone">
							${repayment.userPhone }
						</td>
						<%--<td>
							<c:if test="${repayment.customerType == '0'}">新用户</c:if>
							<c:if test="${repayment.customerType == '1'}">老用户</c:if>
						</td>--%>
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
						<td>
							<fmt:formatDate value="${repayment.creditRepaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td class="time">
							<fmt:formatDate value="${repayment.repaymentRealTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<fmt:formatDate value="${repayment.repaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
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
	DWZ.renderB()
	function changeYHExcel(obj){
		var statuses = "";
		<c:forEach items="${params.statuses}" var="status">
		statuses += "&statuses=" + ${status};
		</c:forEach>
		var href=$(obj).attr("href");
		var userAccountLike=$("#userAccountLike").val();
		var userMobileLike=$("#userMobileLike").val();
		var repaymentedTimeStart=$("#repaymentedTimeStart").val();
		var repaymentedTimeEnd=$("#repaymentedTimeEnd").val();
		var toHref = href + "&userAccountLike=" + userAccountLike + "&userMobileLike=" + userMobileLike + "&repaymentedTimeStart=" + repaymentedTimeStart + "&repaymentedTimeEnd=" + repaymentedTimeEnd + statuses;
		$(obj).attr("href",toHref);
		return true;
	}
	if (renderLoanSuccessCount) {
		renderLoanSuccessCount()
	}
</script>