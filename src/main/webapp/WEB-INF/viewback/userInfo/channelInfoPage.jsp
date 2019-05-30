<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm-c" onsubmit="return navTabSearch(this);" action="channel/getChannelInfoPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						渠道名称:
						<input type="text" name="channelName"
							   value="${params.channelName}" />
					</td>
					<td>
						负责人:
						<input type="text" name="operatorName"
							   value="${params.operatorName}" />
					</td>
					 <td>
						联系方式:
						<input type="text" name="channelTel"
							   value="${params.channelTel}" />
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
						渠道ID
					</th>
					<th align="center"  >
						渠道商
					</th>
					<th align="center"  >
						渠道名称
					</th>
					<th align="center"  >
						渠道编码
					</th>
					<th align="center" >
						负责人
					</th>
					<th align="center" >
						联系方式
					</th>
					<th align="center" >
						省份
					</th>
					<th align="center" >
						城市
					</th>
					<th align="center" >
						地区
					</th>
					<th align="center" >
						计费方式
					</th>
					<th align="center">
						渠道链接
					</th>
					<th align="center">
						推广链接
					</th>
					<th align="center">
						qq注册口子
					</th>
					<th align="center">
						微信注册口子
					</th>
					<th align="center" >
						状态
					</th>
					<th align="center" >
						创建时间
					</th>
					<th align="center">
					 	操作
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
							${channel.id}
						</td>
						<td>
							${channel.channelSuperName}
						</td>
						<td>
							${channel.channelName}
						</td>
						<td>
							${channel.channelCode}
						</td>
						<td>
							${channel.operatorName}
						</td>
						<td>
							${channel.channelTel}
						</td>
						<td>
							${channel.channelProvince}
						</td>
						<td>
							${channel.channelCity}
						</td>
						<td>
							${channel.channelArea}
						</td>
						<td>
							${channel.channelRateName}
						</td>
						<td>
							<c:if test="${not empty channel.channelUrl}">
								<a href="${channel.channelUrl}" target="_blank" >查看渠道</a>
							</c:if>
						</td>
						<td>
							<c:if test="${not empty channel.promotionUrl}">
								<a href="${channel.promotionUrl}" target="_blank" >查看URL</a>
							</c:if>
						</td>
						<td>
							<c:if test="${channel.qqStatus == 0}">
                               开启
							</c:if>
							<c:if test="${channel.qqStatus == 1}">
								关闭
							</c:if>
						</td>
						<td>
							<c:if test="${channel.wechatStatus == 0}">
                               开启
							</c:if>
							<c:if test="${channel.wechatStatus ==1}">
								关闭
							</c:if>
						</td>
						<td>
							<c:if test="${channel.status == 1 }">
								开启
							</c:if>
							<c:if test="${channel.status == 2 }">
								关闭
							</c:if>
						</td>
						<td>
							<fmt:formatDate value="${channel.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<c:if test="${channel.status == 1}">
								<button class="tag-button close-s" onclick="channelUpdateStatus('${channel.channelCode}',2)">关闭</button>
							</c:if>
							<c:if test="${channel.status == 2}">
								<button class="tag-button open-s" onclick="channelUpdateStatus('${channel.channelCode}',1)">开启</button>
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

<script type="text/javascript">
	function channelUpdateStatus(channelCode, status) {
		$.getJSON("${pageContext.request.contextPath}/back/channel/updateStatus?channleCode="+channelCode+"&status="+status, function(data) {
			setTimeout(function () {
				$('#pagerForm-c').submit()
			}, 100)
		});
	}
</script>