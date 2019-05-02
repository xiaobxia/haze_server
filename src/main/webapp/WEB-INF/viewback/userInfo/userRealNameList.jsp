<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/userRealNameList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						借款人id:
						<input type="text" name="userId"
							   value="${params.userId }" />
					</td>
					<td>
						姓名名称:
						<input type="text" name="realname"
							   value="${params.realname }" />
					</td>
					<td>
						身份证:
						<input type="text" name="idNumber" value="${params.idNumber }" />
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
		<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
			<thead>
				<tr>
					<th align="center"  >
						用户ID
					</th>
					<th align="center"  >
						真实姓名
					</th>
					<th align="center" >
						身证号
					</th>
					<th align="center"  >
						添加时间
					</th>
					<th align="center"  >
						修改时间
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="realName" items="${pm.items }" varStatus="status">
					<tr target="userId" rel="${realName.userId }">
						<td>
							${realName.userId}
						</td>
						<td>
							${realName.realname}
						</td>
						<td>
							${realName.idNumber }
						</td>
						<td>
							<fmt:formatDate value="${realName.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<fmt:formatDate value="${realName.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>