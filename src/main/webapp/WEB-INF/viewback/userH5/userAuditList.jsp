<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="user/getUserPage?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						用户名称:
						<input type="text" name="userAccountLike"
							value="${params.userAccountLike }" />
					</td>
					<td>
						手机:
						<input type="text" name="userMobileLike"
							value="${params.userMobileLike }" />
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
						<th align="center" width="50">
							姓名
						</th>
						<th align="center" width="30">
							性别
						</th>
						<th align="center" width="100">
							身份证号码
						</th>
						<th align="center" width="100">
							手机
						</th>
						<th align="center" width="120">
							添加时间
						</th>
						<th align="center" width="120">
							添加IP
						</th>
						<th align="center" width="120">
							备注
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${pm.items }" varStatus="status">
						<tr target="userId" rel="${user.id }">
							<td>
								${status.count}
							</td>
							<td>
								${user.user_name}
							</td>
							<td>
								${user.realname }
							</td>
							<td>
								${user.user_sex}
							</td>
							<td>
								${user.id_number }
							</td>
							<td>
								${user.user_phone }
							</td>
							<td>
								<fmt:formatDate value="${user.createDate }"	pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								${user.addIp }
							</td>
							<td>
								${user.remark }
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