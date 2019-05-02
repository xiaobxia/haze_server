<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="customService/findJxl/${params.interval }?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td title="全匹配,不支持模糊查询">
						手机:
						<input type="text" name="userPhone"	value="${params.userPhone }" />
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
					<tr></tr>
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
							ID号
						</th>
						<th align="center" width="50">
							姓名
						</th>
						<th align="center" width="100">
							手机
						</th>
						<th align="center" width="100">
							身份证号
						</th>
						<th align="center" width="100">
							聚信立token
						</th>
						<th align="center" width="120">
							token生成时间
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${user.id }">
							<td>
								${status.count}
							</td>
							<td>
								${user.id}
							</td>
							<td>
								${user.realname }
							</td>
							<td>
								${user.userPhone }
							</td>
							<td>
								${user.idNumber }
							</td>
							<td>
								${user.jxlToken }
							</td>
							<td>
								${user.jxlTokenTime }
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