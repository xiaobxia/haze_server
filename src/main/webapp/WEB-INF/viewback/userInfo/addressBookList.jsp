<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/addressBookList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						用户ID:
						<input type="text" name="userId"
							   value="${params.userId }" />
					</td>
					<td>
						联系人手机:
						<input type="text" name="userPhone"
							   value="${params.userPhone }" />
					</td>
					<td>
						联系人姓名:
						<input type="text" name="contactName"
							   value="${params.contactName}" />
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
<%-- 		<jsp:include page="${BACK_URL}/rightSubList"> --%>
<%-- 			<jsp:param value="${params.myId}" name="parentId"/> --%>
<%-- 		</jsp:include> --%>
		<table class="table" style="width: 100%;" layoutH="120" nowrapTD="false">
			<thead>
				<tr>
					<th align="center"  >
						ID
					</th>
					<th align="center"  >
						用户ID
					</th>
					<th align="center" >
						用户姓名/手机
					</th>
					<th align="center" >
						联系人
					</th>
					<th align="center" >
						联系人手机
					</th>
					<th align="center"  >
						上传时间
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="contact" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${contact.id }">
						<td>
							${contact.id }
						</td>
						<td>
							${contact.userId }
						</td>
						<td>
							${contact.userName }
						</td>
						<td>
							${contact.contactName }
						</td>
						<td>
							${contact.contactPhone }
						</td>
						<td>
							<fmt:formatDate value="${contact.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>