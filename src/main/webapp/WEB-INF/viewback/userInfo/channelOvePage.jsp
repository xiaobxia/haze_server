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
				<%--	<td>
						还款日期：
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  />
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd" />
					</td>--%>
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
		<table class="table" layoutH="160" nowrapTD="false" ifScrollTable="true">
			<thead>
			<tr>
				<%--<th align="center" >
					渠道id
				</th>--%>
				<th align="center" >
					渠道商名称
				</th>
				<th align="center" >
					渠道名称
				</th>
				<th align="center"  >
					还款日期
				</th>
				<th align="center">
					新用户放款量
				</th>
				<th align="center" >
					新用户已还数量
				</th>
				<th align="center" >
					新用户逾期率
				</th>
				<th align="center" >
					老用户放款数量
				</th>
				<th align="center" >
					老用户已还数量
				</th>
				<th align="center">
					老用户逾期率
				</th>
				<th align="center" >
					展期数量
				</th>
				<th align="center" >
					总放量
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
					<%--<td>${channel.channelId}</td>--%>
					<td>${channel.channelSuperName}</td>
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
