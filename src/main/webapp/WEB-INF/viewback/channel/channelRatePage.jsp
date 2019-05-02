<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getChannelRatePage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						费率名称:
						<input type="text" name="channelRateName"
							   value="${params.channelRateName}" />
					</td>
					<td>
							添加时间：
							<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
					<th align="center"  >
						序列
					</th>
					<th align="center"  >
						名称
					</th>
					<th align="center"  >
						注册量费用系数
					</th>
					<th align="center"  >
						新用户放款费用系数
					</th>
					<th align="center" >
						创建时间
					</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${channel.id }">
						<td>
							${status.index+1}
						</td>
						<td>
							${channel.channelRateName}
						</td>
						<td>
							${channel.channelRegisterRate}
						</td>
						<td>
							${channel.channelNewloanRate}
						</td>
						<td>
							<fmt:formatDate value="${channel.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>