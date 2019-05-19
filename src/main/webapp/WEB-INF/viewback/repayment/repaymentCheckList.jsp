<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="repayment/getRepaymentCheckPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						手机:
						<input type="text" name="phone" id="rcl_phone"
							   value="${params.phone }" />
					</td>
					<td>
						还款时间:
						<input type="text" name="createdAt" id="rcl_createdAt"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.createdAt}" />-
						<input type="text" name="createdAtEnd" id="rcl_createdAtEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.createdAtEnd}" />
					</td>
					<td>
						放款账户:
						<select id="rcl_capitalType" name="capitalType">
							<option value="">全部</option>
							<option <c:if test="${params.capitalType == 1}"> selected </c:if> value="1">多米优</option>
							<%--<option <c:if test="${params.capitalType == 2}"> selected </c:if> value="2">杭州招财猫网络科技有限公司</option>--%>
						</select>
					</td>
					<%-- <td>
						类型:
						<select id="statusType" name="statusType">
							<option value="">全部</option>
							<option <c:if test="${'0' == params.repaymentType}"> selected </c:if> value="0">还款</option>
							<option <c:if test="${'1' == params.repaymentType}"> selected </c:if> value="1">退款</option>
						</select>
					</td> --%>
					<td>
						还款方式:
						<select id="rcl_repaymentType" name="repaymentType">
							<option value="">全部</option>
							<option <c:if test="${'1' == params.repaymentType}"> selected </c:if> value="1">支付宝还款</option>
							<option <c:if test="${'2' == params.repaymentType}"> selected </c:if> value="2">银行卡主动还款</option>
							<option <c:if test="${'3' == params.repaymentType}"> selected </c:if> value="3">银行卡自动扣款</option>
							<option <c:if test="${'4' == params.repaymentType}"> selected </c:if> value="4">对公银行卡转账</option>
							<option <c:if test="${'5' == params.repaymentType}"> selected </c:if> value="5">线下还款</option>
							<option<c:if test="${'6' == params.repaymentType}"> selected </c:if>  value="6">线下协商免还款</option>
						</select>
					</td>
					<td>
						订单号:
						<input type="text" name="orderId" id="rcl_orderId"  value="${params.orderId }" />
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
					用户ID
				</th>
				<th align="center" >
					订单Id
				</th>
				<th align="center" >
					还款Id
				</th>
				<th align="center" >
					用户姓名
				</th>
				<th align="center"  >
					手机号
				</th>
				<th align="center" >
					总还款金额
				</th>
				<th align="center" >
					已还款金额
				</th>
				<th align="center" >
					实还金额
				</th>
				<th align="center" >
					退款金额
				</th>
				<th align="center" >
					还款方式
				</th>
				<th align="center"  >
					还款状态
				</th>
				<th align="center"  >
					还款详情状态
				</th>
				<th align="center"  >
					还款时间
				</th>
				<th align="center"  >
					放款账户
				</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="repayment" items="${pm.items }" varStatus="status">
				<tr target="repaymentId" rel="">
					<td>
							${status.count}
					</td>
					<td>
							${repayment.userId}
					</td>
					<td>${repayment.orderId}</td>
					<td>
							${repayment.reapymentId }
					</td>
					<td>
							${repayment.realname}
					</td>
					<td>
							${repayment.phone }
					</td>

					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentAmount / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.repaymentedAmount / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.trueRepaymentMoney / 100.00}"/>
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${repayment.backOrderId / 100.00}"/>
					</td>

					<td>
							${ALL_REPAY_TYPE[repayment.repaymentType]}
					</td>
					<td>
							${BORROW_STATUS_ALL[repayment.reapymentStatus]}
					</td>
					<td>
							${REPAYMENT_STATUS[repayment.status]}
							${BACK_REPAY_STATUS[repayment.status]}
					</td>
					<td>
						<fmt:formatDate value="${repayment.createdAt }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
							${LOAN_ACCOUNTMap[repayment.capitalType]}
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
    /* $(function(){
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
    function changeHKExcel(obj){
        var href=$(obj).attr("href");
        var createdAt=$("#rcl_createdAt").val();
        var createdAtEnd =$("#rcl_createdAtEnd").val();
        /* 		if(!createdAt || !createdAtEnd){
                    alertMsg.warn("请选择导出时间");
                    $(obj).attr("href",href.substring(0,href.indexOf("&")));
                    return;
                }
                 */
        var phone=$("#rcl_phone").val();
        var capitalType=$("#rcl_capitalType").val();
        var repaymentType=$("#rcl_repaymentType").val();
        var orderId  = $("#rcl_orderId").val();
        var toHref = href + "&phone=" + phone + "&createdAt=" + createdAt
            + "&createdAtEnd="  + createdAtEnd  +"&capitalType="
            + capitalType+"&repaymentType="+repaymentType
            + "&orderId="+orderId;
        $(obj).attr("href",toHref);
    }
</script>