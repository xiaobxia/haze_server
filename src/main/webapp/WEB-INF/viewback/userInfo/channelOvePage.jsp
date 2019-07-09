<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getOveChannelCount?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						渠道商：
						<select name="superChannelId" id="superChannelId">
							<option value="">全部</option>
							<c:forEach var="channelSuperInfo" items="${channelSuperInfos}">
								<option value="${channelSuperInfo.id}" <c:if test="${channelSuperInfo.id eq params.superChannelId}">selected="selected"</c:if>>${channelSuperInfo.channelSuperName}</option>
							</c:forEach>
						</select>
					</td>

					<td>
						渠道名称:
						<input type="text" name="channelName"
							   value="${params.channelName}" />
					</td>
					<td>
						还款时间：
						<input type="text" name="beginTime" id="beginTime" value="${beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
		<table class="table" layoutH="160" nowrapTD="false">
			<thead>
			<tr>
				<th align="center" >
					渠道名称
				</th>
				<th align="center"  >
					还款日期
				</th>
				<th align="center">
					新用户到期数
				</th>
				<th align="center" >
					新用户已还数
				</th>
				<th align="center" >
					新用户逾期率
				</th>
				<th align="center" >
					老用户到期数
				</th>
				<th align="center" >
					老用户已还数
				</th>
				<th align="center">
					老用户逾期率
				</th>
				<th align="center" >
					展期数量
				</th>
				<th align="center" >
					总到期量
				</th>
				<th align="center">
					总还量
				</th>
				<th align="center">
					总逾期率
				</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="channel" items="${pm.items }" varStatus="status">
				<tr target="channelId" rel="${channel.channelId }">
					<td>${channel.channelName}</td>
					<td>${channel.repayTime }</td>
					<td>${channel.newLoanCount }</td>
					<td>${channel.newRepayCount }</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${channel.newOveRate / 100.00}"/>%
					</td>
					<td>${channel.oldLoanCount }</td>
					<td>${channel.oldRepayCount }</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${channel.oldOveRate / 100.00}"/>%
					</td>
					<td>
						${channel.extendCount}
					</td>
					<td>
						${channel.allLoanCount}
					</td>
					<td>
						${channel.allRepayCount}
					</td>
					<td>
						<fmt:formatNumber pattern='###,###,##0.00' value="${channel.allOveRate / 100.00}"/>%
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
	$('#c-o-p-r-btn').click(function () {
		var lastDo = localStorage.getItem('lastDoFChannelOverPage')
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
			url : 'channel/getOveChannelCount',
			success : function(ret) {
				localStorage.setItem('lastDoFChannelOverPage', Date.now())
				alertMsg && alertMsg.correct('操作成功，请一小时后再尝试刷新')
				setTimeout(function () {
					$('#pagerForm-alc').submit()
				}, 100)
			},
			error:DWZ.ajaxError
		})
	})
</script>
