<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="user/findVipRecord?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						用户名称:
						<input type="text" name="userAccount"
							value="${params.userAccount }" />
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
			<table class="table" style="width: 100%;" layoutH="112"
				nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="50">
							序号
						</th>
						<th align="center" width="100">
							用户名
						</th>
						<th align="center" width="100">
							充值金额
						</th>
						<th align="center" width="300">
							VIP有效期
						</th>
						<th align="center" width="100">
							添加时间
						</th>
						<th align="center" width="100">
							IP
						</th>
						<th align="center" width="100">
							最后更新时间
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="vip" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${vip.id }">
							<td>
								${status.count}
							</td>
							<td>
								${vip.user.userAccount}
							</td>
							<td>
								${vip.money }
							</td>
							<td>
								<fmt:formatDate value="${vip.beginTime }"	pattern="yyyy-MM-dd HH:mm:ss" />至
								<fmt:formatDate value="${vip.endTime }"	pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<fmt:formatDate value="${vip.addTime }"	pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								${vip.addIp }
							</td>
							<td>
								<fmt:formatDate value="${vip.updateTime }"	pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>