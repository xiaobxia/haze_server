<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>


<style>
	.popLayer {
		display: none;
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%,-50%);
		z-index: 100;
		width: 500px;
		background: grey;
		background-color: #fff;
		border-radius: 2px;
		box-shadow: 1px 1px 50px rgba(0,0,0,.3);
	}
	.popLayer .layer-title {
		position: relative;
		padding: 0 80px 0 20px;
		height: 42px;
		line-height: 42px;
		border-bottom: 1px solid #eee;
		font-size: 14px;
		color: #333;
		overflow: hidden;
		background-color: #F8F8F8;
		border-radius: 2px 2px 0 0;
	}
	.popLayer .layer-title .layer-close {
		position: absolute;
		right: 8px;
		top: 8px;
		font-size: 20px;
		padding: 2px 7px;
		color: #666;
		font-weight: 100;
		text-decoration: none;
	}
	.popLayer .layer-title .layer-close:hover {
		color: #999;
	}
	.popLayer .layer-content {
		min-height: 200px;
	}
	/*  转派内容样式  */
	.send-custom {
		padding: 60px 50px;
		text-align: center;
	}
	.send-custom span {
		font-size: 15px;
		margin-right: 10px;
	}
	/*  添加备注内容样式  */
	.add-remark {
		padding: 20px;
	}
	.add-remark ul li {
		margin-bottom: 20px;
	}
	.add-remark ul li>label {
		font-size: 16px;
		vertical-align: top;
		margin-right: 10px;
	}
	.add-remark ul li>textarea {
		vertical-align: top;
	}
	.add-remark ul li span >label {
		font-size: 14px;
	}
	.add-remark ul li > p >span {
		display: inline-block;
		margin-bottom: 8px;
	}
	.buttonActive {
		margin-right: 20px;
	}
	.add-remark table {
		width: 100%;
	}
	.add-remark table {
		border: 1px solid #e0e0e0;
	}
	.add-remark table tr th:last-child,
	.add-remark table tr td:last-child{
		border-right: 0;
	}
	.add-remark table tr:last-child td {
		border-bottom: 0;
	}
	.add-remark table tr td,
	.add-remark table tr th{
		border-right: 1px solid #e0e0e0;
		border-bottom: 1px solid #e0e0e0;
	}
	.add-remark table tr th {
		background: #f8f8f8;
	}
	.add-remark table tr td,
	.add-remark table tr th{
		padding: 10px 5px;
	}
</style>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="assign/assignStatistics?bType=${bType}&myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						开始日期:
						<input type="text" name="dateBefore" id="dateBefore"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.dateBefore}" />
					</td>
					<td>
						截止日期:
						<input type="text" name="dateEnd" id="dateEnd"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.dateEnd}" />
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
		<c:set var="all_assign_count" value="0"></c:set>
		<c:set var="all_payback_count" value="0"></c:set>
		<c:set var="all_sys_assign_count" value="0"></c:set>
		<c:set var="all_man_assign_count" value="0"></c:set>
		<!--系统派单count-->
		<c:set var="all_sys_payback_count" value="0"></c:set>
		<!--人工派单count-->
		<c:set var="all_man_payback_count" value="0"></c:set>
		<table class="table" style="width: 100%;" layoutH="170" nowrapTD="false">
			<thead>
			<tr >
				<th align="center"  >
					序号
				</th>
				<th align="center"  >
					日期
				</th>
				<th align="center" >
					早班派单数
				</th>
				<th align="center"  >
					早班回款订单数
				</th>
				<th align="center" >
					早班回款率
				</th>
				<th align="center" >
					晚班派单数
				</th>
				<th align="center"  >
					晚班回款订单数
				</th>
				<th align="center" >
					晚班回款率
				</th>
				<th align="center" >
					总回款率
				</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${pm.items }" var="assign" varStatus="varStatus">
				<c:set var="all_assign_count" value="${all_assign_count + assign.assignCount1 +assign.assignCount2}"></c:set>
				<c:set var="all_sys_assign_count" value="${all_sys_assign_count + assign.assignCount1}"></c:set>
				<c:set var="all_sys_payback_count" value="${all_sys_payback_count + assign.payBackCount1}"></c:set>
				<c:set var="all_man_payback_count" value="${all_man_payback_count + assign.payBackCount2}"></c:set>
				<c:set var="all_man_assign_count" value="${all_man_assign_count + assign.assignCount2}"></c:set>
				<c:set var="all_payback_count" value="${all_payback_count + assign.payBackCount1+assign.payBackCount2}"></c:set>

				<c:set var="all_payback_count_1" value="${assign.payBackCount2 + assign.payBackCount1}"></c:set>

				<tr target="sid_support" rel="${assign.createDate}">
					<td>${varStatus.index + 1}</td><!--序号-->
					<td>
							${assign.createDate}
					</td>

					<td>
						${assign.assignCount1}<!--派单数-->
					</td>
					<td>
						${assign.payBackCount1}<!--已还订单数-->
					</td>
					<td>
						<c:if test="${assign.payBackCount1>0}">

							<fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${assign.payBackCount1.doubleValue() / assign.assignCount1.doubleValue()}" />
						</c:if>

						<c:if test="${assign.payBackCount1==0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="0.00"/>
						</c:if>
					</td>
					<td>
						${assign.assignCount2}<!--派单数-->
					</td>
					<td>
						${assign.payBackCount2}<!--已还订单数-->
					</td>
					<td>
						<c:if test="${assign.payBackCount2>0}">

							<fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${assign.payBackCount2.doubleValue() / assign.assignCount2.doubleValue()}" />
						</c:if>
						<c:if test="${assign.payBackCount2==0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="0.00"/>
						</c:if>
					</td>
					<td>
						<c:if test="${assign.payBackCount2 + assign.payBackCount1 >0 && assign.assignCount1>0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${all_payback_count_1.doubleValue() / assign.assignCount1.doubleValue()}" />
						</c:if>
						<c:if test="${assign.payBackCount2+ assign.payBackCount1==0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="0.00"/>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="divider"></div>
		<div class="pageFoot">
			<div class="searchBar">
				<table class="searchContent">
					<tbody>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;汇总：</td>
					<td>派单数</td>
					<td style="color: red;">${all_assign_count}</td>
					<td>已还订单数</td>
					<td style="color: red;">${all_payback_count}</td>
					<td>系统派单回款率</td>
					<c:if test="${all_sys_assign_count > 0}">
						<td style="color: red;"><fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${all_sys_payback_count.doubleValue() / all_sys_assign_count.doubleValue()}" /></td>
					</c:if>
					<c:if test="${all_sys_assign_count == 0}">
						<td style="color: red;"><fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="0.00" /></td>
					</c:if>
					<td>人工派单回款率</td>
					<c:if test="${all_man_assign_count > 0}">
						<td style="color: red;"><fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${all_man_payback_count.doubleValue() / all_man_assign_count.doubleValue()}" /></td>
					</c:if>
					<c:if test="${all_man_assign_count == 0}">
						<td style="color: red;"><fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="0.00" /></td>
					</c:if>
					</tbody>
				</table>
			</div>
		</div>

		<c:set var="page" value="${pm }"/>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>


<script type="text/javascript">
    $(function(){

    })
</script>