<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="renewal/getRenewalPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						用户名称:
						<input type="text" name="userName"  id="ren_userName"
							   value="${params.userName }" />
					</td>
					<td>
						手机:
						<input type="text" name="phone"  id="phone"
							   value="${params.phone }" />
					</td>
					<td>
						续期后应还款时间:
						<input type="text" name="repaymentTime" id="ren_repaymentTime"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentTime}" />-
						<input type="text" name="repaymentTimeEnd" id="ren_repaymentTimeEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.repaymentTimeEnd}" />	   
					</td>
					<td>
						状态:
						<select  name="statuses" id="ren_statuses">
							<option value="">全部</option>
							<c:if test="${statusesType == 'ALL'}">
								<option value="0">待审核</option>
								<option value="1">审核成功</option>
								<option value="2">审核失败</option>
								<option value="5">待处理</option>
							</c:if>
							<c:if test="${statusesType != 'ALL'}">
								<option <c:if test="${'0' == params.statuses[0]}"> selected </c:if> value="0">待审核</option>
								<option <c:if test="${'1' == params.statuses[0]}"> selected </c:if>  value="1">审核成功</option>
								<option <c:if test="${'2' == params.statuses[0]}"> selected </c:if>  value="2">审核失败</option>
								<option <c:if test="${'5' == params.statuses[0]}"> selected </c:if>  value="5">待处理</option>
							</c:if>
					<%-- 		<c:forEach items="${RENEWAL_STATUS}" var="type">
							<option value="${type.key}" <c:if test="${params.status eq type.key}">selected="selected"</c:if>>${type.value}</option>
						</c:forEach> --%>
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
		<div style="font-size:16px; line-height:24px;">数据统计：成功续期${renewalTotal}件；续期费总计${renewalFeeTotal / 100.00}元；服务费总计：${interestTotal / 100.00} 元；续期总额总计：${sumFeeTotal / 100.00}元</div>
		<table class="table" style="width: 100%; text-an" layoutH="130" nowrapTD="false">
			<thead>
				<tr>
					<th align="center"  >
						序号
					</th>
					<th align="center"  >
						订单号
					</th>
					<th align="center"  >
						姓名
					</th>
					<th align="center" >
						联系方式
					</th>
					<th align="center"  >
						续期类型
					</th>
					<th align="center" >
						续期总额
					</th>
					<th align="center" >
						服务费
					</th>
					<th align="center" >
						续期费
					</th>
					<th align="center" >
						续期期限
					</th>

					<th align="center" >
						续期状态
					</th>
					<th align="center" >
						续期到期还款时间
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
							${renewal.orderId}
						</td>
						<td>
							${renewal.realname }
						</td>
						<td>
							${renewal.userPhone }
						</td>
						<td>
							<c:choose>
							   <c:when test="${renewal.renewalType eq 1 }">自动申请</c:when>
							   <c:otherwise>手动申请</c:otherwise>
							</c:choose>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${renewal.sumFee / 100.00}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${renewal.repaymentInterest / 100.00}"/>
						</td>
						<td>
							<fmt:formatNumber pattern='###,###,##0.00' value="${renewal.renewalFee / 100.00}"/>
						</td>
						<td>
							${renewal.renewalDay}
						</td>
						<td>
							${RENEWAL_STATUS[renewal.status]}
						</td>
						<td>
							<fmt:formatDate value="${renewal.repaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
function reportExcel(obj){
	var reTimeEnd = $("#ren_repaymentTimeEnd").val();
	var reTime=$("#ren_repaymentTime").val();
	if(!reTimeEnd || !reTime){
		alertMsg.warn("请选择导出时间");
		return;
	} 
	var href=$(obj).attr("href");
	var phone=$("#ren_phone").val();
	var userName=$("#ren_userName").val();
	var statuses=$("#ren_statuses").val();
	var toHref = href + "&phone=" + phone + "&repaymentTime=" + reTime + "&repaymentTimeEnd="+reTimeEnd+ "&userName=" + userName+"&statuses="+statuses;
	$(obj).attr("href",toHref);
	return true;
}
</script>