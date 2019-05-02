<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/ChannelUserInfoPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					
					<td>
							渠道商：
							<select name="channelid">
							<option value="">全部</option>
							<c:forEach var="channelList" items="${channelList}">
								 
									<option value="${channelList.id}" <c:if test="${channelList.id eq params.channelid}">selected="selected"</c:if>>${channelList.channelName}</option>
								 
							</c:forEach>
					</select>
					</td>
					<td>
						渠道商名称:
						<input type="text" name="channelName"
							   value="${params.channelName}" />
					</td>
					<td>
						推广员姓名:
						<input type="text" name="realname"
							   value="${params.realname}" />
					</td>
					<td>
						推广员电话:
						<input type="text" name="userPhone"
							   value="${params.userPhone}" />
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
						推广员姓名
					</th>
					<th align="center"  >
						推广员电话
					</th>
					<th align="center"  >
						渠道商名称
					</th>
					<th align="center" >
						负责人
					</th>
					<th align="center" >
						联系方式
					</th>
					<th align="center" >
						创建时间
					</th>
					<th align="center" >
						推广二维码
					</th>
					<th align="center" >
						推广上传图片
					</th>
					<th align="center" >
						推广下载图片
					</th>
					<th align="center" >
						推广链接
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
							${channel.realname}
						</td>
						<td>
							${channel.userPhone}
						</td>
						<td>
							${channel.channelName}
						</td>
						<td>
							${channel.operatorName}
						</td>
						<td>
							${channel.channelTel}
						</td>
						<td>
							<fmt:formatDate value="${channel.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<c:if test="${not empty channel.relPath}">
								<a href="${bpath}${channel.relPath}" target="_blank" >查看二维码</a>
							</c:if>
						</td>
						<td>
							<c:if test="${not empty channel.registerPicUrl}">
								<a href="${channel.registerPicUrl}" target="_blank" >查看上传图片</a>
							</c:if>
						</td>
						<td>
							<c:if test="${not empty channel.downloadPicUrl}">
								<a href="${channel.downloadPicUrl}" target="_blank" >查看下载图片</a>
							</c:if>
						</td>
						<td>
							<c:if test="${not empty channel.remark}">
								<a href="${channel.remark}" target="_blank" >查看URL</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>