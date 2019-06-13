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

					<%--<td>
						添加时间：
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
		<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
			<thead>
			<tr>
				<th align="center" >
					渠道id
				</th>
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
					首放数量
				</th>
				<th align="center" >
					首放已还数量
				</th>
				<th align="center" >
					首放逾期率
				</th>
				<th align="center" >
					复借放款数量
				</th>
				<th align="center" >
					复借已还数量
				</th>
				<th align="center">
					复借率
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
				<%--<th align="center" >
					渠道状态
				</th>--%>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="channel" items="${pm.items }" varStatus="status">
				<tr target="channelId" rel="${channel.channelId }">
					<td>${channel.channelId}</td>
					<td>${channel.channelSuperName}</td>
					<td>${channel.channelName}</td>
					<td>${channel.loanTime }</td>
					<td>${channel.firstLoanCount }</td>
					<td>${channel.firstRepayCount }</td>
					<td>${channel.firstOveRate }</td>
					<td>${channel.reLoanCount }</td>
					<td>${channel.reRepayCount }</td>
					<td>
						${channel.reOveRate}
					</td>
					<td>${channel.extendCount }</td>
					<td>${channel.allLoanCount }</td>
					<td>${channel.allRepayCount }</td>
					<td>
						${channel.allOveRate}
					</td>
<%--
					<td>${channel.channelStatus }</td>
--%>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>
<script type="text/javascript">

    function toChannelReportExcel(obj){

        var href=$(obj).attr("href");
        var a=$("#channelid").val();
        var beginTime=$("#beginTime").val();
        var endTime=$("#endTime").val();
        var toHref=href+"&channelid="+a+"&beginTime="+beginTime+"&endTime="+endTime;

        $(obj).attr("href",toHref);
    }
</script>