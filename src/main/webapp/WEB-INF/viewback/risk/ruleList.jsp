<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="risk/findList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						类型:
						<select name="ruleType">
							<option value="">全部</option>
							<c:forEach items="${ALL_RISK_TYPE}" var="rule">
								<option value="${rule.key }"
								<c:if test="${rule.key eq params.ruleType }">selected="selected"</c:if>>${rule.value }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						状态:
						<select name="status">
							<option value="">全部</option>
							<c:forEach items="${ALL_RISK_STATUS}" var="status">
								<option value="${status.key }"
								<c:if test="${status.key eq params.status }">selected="selected"</c:if>>${status.value }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						名称:
						<input type="text" name="ruleName"	value="${params.ruleName }" />
					</td>
					<td>
						描述:
						<input type="text" name="ruleDesc"	value="${params.ruleDesc }" />
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
					<th align="center" width="50">ID号</th>
					<th align="center" width="100">名称</th>
					<th align="center" width="80">类型</th>
					<th align="center" width="80">依赖关系</th>
					<th align="center" width="250" >规则</th>
					<th align="center" width="120" >描述</th>
					<th align="center" width="50">状态</th>
					<th align="center" width="100">添加时间</th>
					<th align="center" width="100">最后更新时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="rule" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${rule.id }"
							<c:if test="${rule.rootType==3 }">style="background:green;"</c:if>
						 <c:if test="${rule.rootType==2 }">style="background:red;"</c:if>
						 <c:if test="${rule.rootType==4 }">style="background:green;"</c:if>
						>
							<td >${rule.id}</td>
							<td >${rule.ruleName }</td>
							<td >${rule.ruleTypeView }</td>
							<td ><a target="navTab" rel="tree${rule.id }" href="<%=path %>${BACK_URL }risk/viewTree2?id=${rule.id}&type=toJsp">查看</a></td>
							<td >${rule.formula }</td>
							<td >${rule.ruleDesc }</td>
							<td >${rule.statusView }</td>
							<td ><fmt:formatDate value="${rule.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td ><fmt:formatDate value="${rule.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
	</c:if>
</form>