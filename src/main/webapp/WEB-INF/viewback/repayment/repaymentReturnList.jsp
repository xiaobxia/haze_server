<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="return/getRepaymentReturnPage?myId=${params.myId}" method="post">
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
						还款时间:
						<input type="text" name="createdAt" id="createdAt"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.createdAt}" />-
						<input type="text" name="createdAtEnd" id="createdAtEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.createdAtEnd}" />
					</td>
					<td>
						还款方式:
						<select title="还款方式" name="repaymentType">
						<option value="">全部</option>
						<c:forEach items="${ALL_REPAY_TYPE}" var="type">
						   
							<option value="${type.key}" <c:if test="${params.repaymentType eq type.key}">selected="selected"</c:if>>${type.value}</option>
						</c:forEach>
					</select>
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
						详情ID
					</th>
					<th align="center"  >
						姓名
					</th>
					<th align="center" >
						手机号
					</th>
				<%--	<th align="center"  >
						是否是老用户
					</th>--%>
					<th>
						成功放款次数
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
						实还金额
					</th>
					<th align="center" >
						放款时间
					</th>
					<th align="center" >
						预期还款时间
					</th>
					<th align="center" >
						还款时间
					</th>
					<th align="center"  >
						还款方式
					</th>
					<th align="center"  >
						实际还款时间
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="detail" items="${pm.items }" varStatus="status">
					<tr target="detailId" rel="${detail.id }">
						<td>
							${status.count}
						</td>
						<td>
							${detail.id }
						</td>
						<td>
							${detail.realname}
						</td>
						<td>
							${detail.userPhone }
						</td>
						<%--<td>
							<c:if test="${detail.customerType == '0'}">新用户</c:if>
							<c:if test="${detail.customerType == '1'}">老用户</c:if>
						</td>--%>
						<td>
							${detail.loanCount}
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${detail.repaymentPrincipal / 100.00}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${detail.repaymentInterest / 100.00}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${detail.repaymentAmount / 100.00}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${detail.repaymentedAmount / 100.00}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${detail.trueRepaymentMoney / 100.00}"/>
						</td>
						<td>
							<fmt:formatDate value="${detail.creditRepaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<fmt:formatDate value="${detail.repaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<fmt:formatDate value="${detail.repaymentRealTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${ALL_REPAY_TYPE[detail.repaymentType]}
						</td>
						<td>
							<fmt:formatDate value="${detail.createdAt }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
	}
</script>