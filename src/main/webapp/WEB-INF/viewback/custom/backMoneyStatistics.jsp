<%@ page language="java" pageEncoding="UTF-8"%>
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
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="assign/assignBackMoneyStatistics?bType=${bType}&myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						开始日期:
						<input type="text" name="startDate" id="startDate"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.startDate}" />
					</td>
					<td>
						截止日期:
						<input type="text" name="endDate" id="endDate"
							   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
							   value="${params.endDate}" />
					</td>
					<td>
						客服:<input type="text" name="jobName" value="${params.jobName}"/>
					</td>
					<td>
						手机号:<input type="text" name="mobile" value="${params.mobile}"/>
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
		<c:set var="all_assign_count" value="0"></c:set><!--客服派单总数-->
		<c:set var="all_payback_count" value="0"></c:set><!--客服回款总数-->
		<c:set var="all_sys_assign_count" value="0"></c:set>
		<c:set var="all_man_assign_count" value="0"></c:set>
		<!--系统派单count-->
		<c:set var="all_sys_payback_count" value="0"></c:set>
		<!--人工派单count-->
		<c:set var="all_man_payback_count" value="0"></c:set>
		<table class="table" style="width: 100%;" layoutH="230" nowrapTD="false">
			<thead>
			<tr >
				<th align="center"  >
					<input type="checkbox" id="checkAlls" onclick="checkAll(this);"/>
				</th>
				<th align="center"  >
					序号
				</th>
				<th align="center"  >
					客服
				</th>
				<th align="center"  >
					手机号
				</th>
				<th align="center"  >
					早班派单总件数
				</th>
				<th align="center" >
					早班回款总件数
				</th>
				<th align="center"  >
					早班回款率
				</th>
				<th align="center" >
					晚班派单总数
				</th>
				<th align="center" >
					晚班回款总数
				</th>
				<th align="center" >
					晚班回款率
				</th>
				<th align="center" >
					派单总件数
				</th>
				<th align="center" >
					回款总件数
				</th>
				<th align="center" >
					回款率
				</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${pm.items }" var="assign" varStatus="varStatus">
				<c:set var="all_assign_count" value="${assign.assignCount1 +assign.assignCount2}"></c:set>
				<%--<c:if test="${assign.assignType=='晚班'}">--%>
					<%--<c:set var="all_sys_assign_count" value="${all_sys_assign_count + assign.assignCount}"></c:set>--%>
					<%--<c:set var="all_sys_payback_count" value="${all_sys_payback_count + assign.payBackCount}"></c:set>--%>
				<%--</c:if>--%>
				<%--<c:if test="${assign.assignType=='早班'}">--%>
					<%--<c:set var="all_man_payback_count" value="${all_man_payback_count + assign.payBackCount}"></c:set>--%>
					<%--<c:set var="all_man_assign_count" value="${all_man_assign_count + assign.assignCount}"></c:set>--%>
				<%--</c:if>--%>
				<c:set var="all_payback_count" value="${assign.payBackCount1 + assign.payBackCount2}"></c:set>
				<tr target="sid_support" rel="${assign.createDate}">
					<td>
						<input type="checkbox" name="checkItem" value="${assign.jobId}"/>
					</td>
					<td>${varStatus.index + 1}</td><!--序号-->

					<td>
							${assign.jobName}
					</td>
					<td>
							${assign.phone}
					</td>
					<td>
						${assign.assignCount1}<!--早班派件总件数-->
					</td>
					<td>
						${assign.payBackCount1}<!--早班回款总件数-->
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
						${assign.assignCount2}<!--晚班派件总件数-->
					</td>
					<td>
						${assign.payBackCount2}<!--晚班回款总件数-->
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
						${all_assign_count}
					</td>
					<td>
						${all_payback_count}
					</td>
					<td>
						<c:if test="${all_payback_count>0}">

							<fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${all_payback_count.doubleValue() / all_assign_count.doubleValue()}" />
						</c:if>
						<c:if test="${all_payback_count==0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="0.00"/>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="divider"></div>
		<div class="pageFoot">
			<div class="pageContent">
				<table class="table" style="width: 100%;height: 10%"  nowrapTD="false">
					<thead>
					<tr >
						<th align="center"  >
							派单总件数
						</th>
						<th align="center"  >
							回款订单总件数
						</th>
						<th align="center"  >
							回款率
						</th>
						<th align="center"  >
							早班派单总件数
						</th>
						<th align="center" >
							早班回款总件数
						</th>
						<th align="center"  >
							早班回款率
						</th>
						<th align="center" >
							晚班派单总件数
						</th>
						<th align="center" >
							晚班回款总件数
						</th>
						<th align="center" >
							晚班回款率
						</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${assign }" var="in" varStatus="varStatus">
						<tr>
							<td style="color: red;">${in.totalAssignCount}</td>
							<td style="color: red;">${in.totalPayBackCount}</td>
							<td style="color: red;">
								<c:if test="${in.totalPayBackCount >0}">

									<fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${in.totalPayBackCount.doubleValue() / in.totalAssignCount.doubleValue()}" />
								</c:if>
								<c:if test="${in.userPayBackCount ==0}">
									<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="0.00"/>
								</c:if>
							</td>
							<td style="color: red;">${in.sysAssignCount}</td>
							<td style="color: red;">${in.sysPayBackCount}</td>
							<td style="color: red;">
								<c:if test="${in.sysPayBackCount >0}">

									<fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${in.sysPayBackCount.doubleValue() / in.sysAssignCount.doubleValue()}" />
								</c:if>
								<c:if test="${in.userPayBackCount ==0}">
									<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="0.00"/>
								</c:if>
							</td>
							<td style="color: red;">${in.userAssignCount}</td>
							<td style="color: red;">${in.userPayBackCount} </td>
							<td style="color: red;">
								<c:if test="${in.userPayBackCount >0}">

									<fmt:formatNumber type="percent" maxFractionDigits="2" pattern="0.00%"  value="${in.userPayBackCount.doubleValue() / in.userAssignCount.doubleValue()}" />
								</c:if>
								<c:if test="${in.userPayBackCount ==0}">
									<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="0.00"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
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
    function checkAll(obj){
        var bol = $(obj).is(':checked');
        $("input[name='checkItem']").attr("checked",bol);
    }
    function customerExcel(obj) {
        var jobIds = "";
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        $("input[name='checkItem']:checked").each(function () {
            jobIds = jobIds + "," + $(this).val();
        });
        if(jobIds.length ==0){
            alert("请选择导出内容");
		}else{
            var href=$(obj).attr("href");
            var toHref = href + "&jobIds="+jobIds+"&startDate="+startDate+"&endDate="+endDate;
            $(obj).attr("href",toHref);
		}
    }

</script>