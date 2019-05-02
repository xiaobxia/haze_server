<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript">
var agent=$("#agentId_select").val();
var agentPhone=$("#agentPhoneLike").val();
var type=$("#type").val();
</script>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="noticeRelease/listPage?parentId=${params.myId}" method="post">

	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
				<table class="table" style="width: 100%;" layoutH="110" nowrapTD="false">
				<thead>
					<tr>
					    <th align="center" width="50">序号</th>
						<th align="center" width="50">
							渠道类型
						</th>
						<th align="center" width="100">
							频道类型
						</th>
						<th align="center" width="50">
							栏目类型
						</th>
						<th align="center" width="30">
							内容标题
						</th>
						<th align="center" width="100">
							是否生成动态链接
						</th>
						<th align="center" width="100">
							发送条件
						</th>
						<th align="center" width="100">
							序号
						</th>
						<th align="center" width="100">
							发送内容
						</th>
						<th align="center" width="100">
							推送方式
						</th>
						<th align="center" width="100">
							创建时间
						</th>
						<th align="center" width="100">
							备注
						</th>						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${pm.items }" varStatus="status">
						<tr target="listId" rel="${list.id }">
							<td>
								${status.index+1}
							</td>
							<td>
								<c:if test="${list.source eq 1 }">APP</c:if>
								<c:if test="${list.source eq 2 }">微信</c:if>
								<c:if test="${list.source eq 3 }">PC端</c:if>
								<c:if test="${list.source eq 4 }">其他</c:if>
							</td>
							<td>
								<c:if test="${list.channel eq 1 }">首页</c:if>
								<c:if test="${list.channel eq 2 }">关于我们</c:if>
								<c:if test="${list.channel eq 3 }">其他</c:if>
							</td>
							<td>
								<c:if test="${list.column_type eq 1 }">消息滚动设置</c:if>
								<c:if test="${list.column_type eq 2 }">banner轮播页</c:if>
								<c:if test="${list.column_type eq 3 }">消息中心</c:if>
							</td>
							<td>
								${list.title}
							</td>
							<td>
							    <c:if test="${list.dynamic_link eq false }">否</c:if>
								<c:if test="${list.dynamic_link eq true }">是</c:if>
							</td>
							<td>
								<c:if test="${list.send_condition eq 1 }">注册成功</c:if>
								<c:if test="${list.send_condition eq 2 }">申请成功</c:if>
								<c:if test="${list.send_condition eq 3 }">放款成功</c:if>
								<c:if test="${list.send_condition eq 4 }">还款成功</c:if>
								<c:if test="${list.send_condition eq 5 }">逾期还款</c:if>
								<c:if test="${list.send_condition eq 6 }">其他</c:if>
							</td>
							<td>
								${list.sort}
							</td>
							<td>
								${list.send_content}
							</td>
							<td>
								<c:if test="${list.send_type eq 1 }">站内信</c:if>
								<c:if test="${list.send_type eq 2 }">PUSH</c:if>
								<c:if test="${list.send_type eq 3 }">第三方接口</c:if>
							</td>
							<td>
							    <fmt:parseDate var="dateObj" value="${list.create_time}" type="DATE" pattern="yyyy-MM-dd HH:mm:ss"/>
								<fmt:formatDate value="${dateObj}"	pattern="yyyy-MM-dd HH:mm:ss"/>								
							</td>
							<td>
								${list.remark}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	
</form>