<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

 <form id="pagerForm-alc" onsubmit="return navTabSearch(this);" action="customService/backLoanCensusResult?myId=${params.myId}" method="post">
	 <input type="hidden" id="jsp" value="1">
	 <div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>开始时间: <input type="text" name="startTime"
									 class="date textInput readonly" datefmt="yyyy-MM-dd"
									 value="${params.startTime }" readonly="readonly" />
					</td>
					<td>结束时间: <input type="text" name="endTime"
									 class="date textInput readonly" datefmt="yyyy-MM-dd"
									 value="${params.endTime }" readonly="readonly" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li class="">
					<a href="customService/toBackCensusLoan?myId=525&parentId=498" class="edit" target="dialog" width="410" height="210" rel="jbsxBox" mask="true">
						<span>回算统计</span> </a>
				</li>
				<li class="">
					<a id="a-l-c-r-btn"><span>刷新</span> </a>
				</li>
			</ul>
		</div>
		<table class="table" width="100%" layoutH="200" nowrapTD="false" ifScrollTable="true">
			<thead>
				<tr>
					<th align="center">ID</th>
					<th align="center">还款日期</th>
					<th align="center">到期笔数</th>
					<th align="center">到期金额</th>
					<th align="center">还款笔数</th>
					<th align="center">还款金额</th>
					<th align="center">展期笔数</th>
					<th align="center">展期金额</th>
					<th align="center">部分（分期）还款笔数</th>
					<th align="center">部分（分期）还款金额</th>
					<th align="center">逾期已还笔数</th>
					<th align="center">逾期已还金额</th>
					<th align="center">逾期未还笔数</th>
					<th align="center">逾期未还金额</th>
					<th align="center">首借回款率</th>
					<th align="center">复借回款率</th>
					<th align="center">首逾</th>
					<th align="center">2天逾期率</th>
					<th align="center">3天逾期率</th>
					<th align="center">7天逾期率</th>
					<th align="center">逾期占比</th>
					<th align="center">更新时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="loanInfo" items="${pageConfig.items }" varStatus="status" >
					<tr target="id" rel="${loanInfo.id }">
						<td align="center">${loanInfo.id }</td>
						<td align="center">${loanInfo.repayDate}</td>
						<td align="center">${loanInfo.expireCount}</td>
						<td align="center">${loanInfo.expireMoney}</td>
						<td align="center">${loanInfo.repayCount }</td>
						<td align="center">${loanInfo.repayMoney}</td>
						<td align="center">${loanInfo.extendCount}</td>
						<td align="center">${loanInfo.extendMoney }</td>
						<td align="center">${loanInfo.amortizationLoanCount}</td>
						<td align="center">${loanInfo.amortizationLoanMoney}</td>
						<td align="center">${loanInfo.oveRepayCount}</td>
						<td align="center">${loanInfo.oveRepayMoney }</td>
						<td align="center">${loanInfo.oveWaitCount}</td>
						<td align="center">${loanInfo.oveWaitMoney}</td>
						<td align="center"><fmt:formatNumber pattern='###,###,##0.00' value="${loanInfo.repayRate / 100.00}"/>%</td>
						<td align="center"><fmt:formatNumber pattern='###,###,##0.00' value="${loanInfo.reRepayRate / 100.00}"/>%</td>
						<td align="center"><fmt:formatNumber pattern='###,###,##0.00' value="${loanInfo.oveFirstRate / 100.00}"/>%</td>
						<td align="center"><fmt:formatNumber pattern='###,###,##0.00' value="${loanInfo.twoRate / 100.00}"/>%</td>
						<td align="center"><fmt:formatNumber pattern='###,###,##0.00' value="${loanInfo.threeRate / 100.00}"/>%</td>
						<td align="center"><fmt:formatNumber pattern='###,###,##0.00' value="${loanInfo.sevenRate / 100.00}"/>%</td>
						<td align="center"><fmt:formatNumber pattern='###,###,##0.00' value="${loanInfo.oveRate / 100.00}"/>%</td>
						<td align="center"><fmt:formatDate value="${loanInfo.updateDate}"
														   pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pageConfig}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<script type="text/javascript">
		$('#a-l-c-r-btn').click(function () {
			var lastDo = localStorage.getItem('lastDoFreshenLoanCensusResult')
			if (lastDo) {
				lastDo = parseInt(lastDo)
				if (Date.now() - lastDo < 1000 * 60 * 60) {
					var m = parseInt((1000 * 60 * 60 - Date.now() + lastDo)/(1000 * 60))
					alertMsg && alertMsg.info('请'+(m || 1)+'分钟后再尝试刷新')
					return
				}
			}
			$.ajax({
				type : "post",
				url : 'customService/freshenLoanCensusResult',
				success : function(ret) {
					localStorage.setItem('lastDoFreshenLoanCensusResult', Date.now())
					alertMsg && alertMsg.correct('操作成功，请一小时后再尝试刷新')
					setTimeout(function () {
						$('#pagerForm-alc').submit()
					}, 100)
				},
				error:DWZ.ajaxError
			})
		})
	</script>
</form>
