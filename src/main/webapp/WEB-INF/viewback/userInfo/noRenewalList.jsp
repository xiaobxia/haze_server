<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

 <form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/noRenewal?myId=${searchParams.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>ID：<input type="text" name="id"
						value="${searchParams.id }" />
					</td>
					<!-- <td>用户类型: <select name="userType">
							<option value="">不限</option>
							<c:forEach items="${USER_TYPE }" var="type">
								<c:if test="${type.key ne USER_TYPE_SYSTEM }">
									<option value="${type.key }"
										<c:if test="${searchParams.userSystem eq type.key }">selected="selected"</c:if>>${type.value
										}</option>
								</c:if>
							</c:forEach>
					</select>
					</td> -->
					<td>超过<input type="text" name="renewalCount" value="${searchParams.renewalCount}" class="mid">(天)无续借</td>
					<td>真实姓名: <input type="text" name="realname"
						value="${searchParams.realname }" />
					</td>
					<td>手机: <input type="text" name="userPhone"
								   value="${searchParams.userPhone }" />
					</td>
					<!-- <td>&nbsp;&nbsp;&nbsp;&nbsp;</td> -->
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
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${searchParams.myId}" name="parentId"/>
		</jsp:include>
		<table class="table" width="100%" layoutH="160">
			<thead>
				<tr>
					<th align="center">用户ID</th>
					<th align="center">真实姓名</th>
					<th align="center">联系方式</th>
					<th align="center">性别</th>
					<th align="center">借款次数</th>
					<th align="center">成功还款次数</th>
					<th align="center">逾期次数</th>
					<th align="center">最大逾期天数</th>
					<th align="center">最后还款时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${pm.items }" varStatus="status" >
					<tr target="userId" rel="${user.id }">
						<td align="center">${user.id }</td>
						<td align="center">${user.realName}</td>
						<td align="center">${user.userPhone}</td>
						<td align="center">${user.userSex}</td>
						<td align="center">${user.borrowCount}</td>
						<td align="center">${user.repayCount}</td>
						<td align="center">${user.oveCount }</td>
						<td align="center">${user.lateLong}</td>
						<td align="center">
						<fmt:formatDate value="${user.realTime }" pattern="yyyy-MM-dd HH:mm" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<script type="text/javascript">
		
	</script>
</form>
