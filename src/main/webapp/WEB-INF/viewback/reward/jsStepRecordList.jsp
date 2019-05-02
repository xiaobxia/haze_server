<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="channel/getChannelInfoPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td style="width: 12%;">手机号码： <input id="phone" name="phone"
						style="width: 170px; margin-top: 12px" class="form-control m-b"
						value="${jsStepRecord.phone}">
					</td>
					<td style="width: 12%;">行为名称： <form:select path="stepId"
							class="form-control m-b">
							<form:option value="" label="全部" />
							<form:option value="1" label="注册" />
							<form:option value="2" label="认证成功" />
							<form:option value="3" label="借款成功" />
							<form:option value="4" label="还款成功" />
							<form:option value="5" label="邀请注册成功" />

						</form:select>
					</td>
					<td class="date" style="width: 26%;">更新日期：
						<div class="input-group date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i>
							</span> <input readonly="" class="form-control" name="beginDate"
								id="beginDate" value="" type="text" style="width: 130px;">
						</div> 至
						<div class="input-group date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i>
							</span> <input readonly="" class="form-control" name="endDate"
								id="endDate" value="" type="text" style="width: 130px;">
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<jsp:include page="${BACK_URL}/rightSubList">
				<jsp:param value="${params.myId}" name="parentId" />
			</jsp:include>
			<table class="table" style="width: 100%;" layoutH="112"
				nowrapTD="false">
				<thead>
					<tr>
						<th>姓名</th>
						<th>手机号</th>
						<th>抽奖次数</th>
						<th>行为名称</th>
						<th>添加时间</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="jsStepRecord">
						<tr>


							<td>${jsStepRecord.jsLoanPerson.name}</td>
							<td>${jsStepRecord.jsLoanPerson.phone}</td>
							<c:if test="${not empty jsStepRecord.effectiveCount}">
								<td>${jsStepRecord.effectiveCount}</td>
							</c:if>
							<c:if test="${empty jsStepRecord.effectiveCount}">
								<td>0</td>
							</c:if>
							<td>${jsStepRecord.jsStepConfig.stepName}</td>
							<td><fmt:formatDate value="${jsStepRecord.addTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${jsStepRecord.remark}</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../page.jsp"%>
		</div>
</form>