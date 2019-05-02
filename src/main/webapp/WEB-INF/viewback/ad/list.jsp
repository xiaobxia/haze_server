<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="ad/list?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						渠道：
						<select name="showType">
							<option>全部</option>
							<option value="0" <c:if test='${params.showType==0}'>selected="selected"</c:if>>PC端</option>
							<option value="1" <c:if test='${params.showType==1}'>selected="selected"</c:if>>APP端</option>
							<option value="2" <c:if test='${params.showType==2}'>selected="selected"</c:if>>其他</option>
						</select>
					</td>
					<%-- <td>
						频道：
						<select name="channelType">
							<option>全部</option>
							<option value="0" <c:if test='${params.channelType==0 }'>selected="selected"</c:if>>首页</option>
							<option value="1" <c:if test='${params.channelType==1 }'>selected="selected"</c:if>>关于我们</option>
							<option value="2" <c:if test='${params.channelType==2 }'>selected="selected"</c:if>>帮助中心</option>
						</select>
					</td>
					<td>
						栏目：
						<select name="columnType">
							<option>全部</option>
							<option value="0" <c:if test='${params.columnType==0 }'>selected="selected"</c:if>>banner轮播页面</option>
							<option value="1" <c:if test='${params.columnType==1 }'>selected="selected"</c:if>>动态弹层</option>
						</select>
					</td>
					<td>
						用户群体：
						<select name="userLevel">
							<option>全部</option>
							<option value="1" <c:if test="${params.userLevel == 1 }">selected='selected'</c:if>>V1</option>
							<option value="2" <c:if test="${params.userLevel == 2 }">selected='selected'</c:if>>V2</option>
							<option value="3" <c:if test="${params.userLevel == 3 }">selected='selected'</c:if>>V3</option>
						</select>
					</td> --%>
					<td>
						排序：
						<select name="sortType">
							<option>无排序</option>
							<option value="0" <c:if test="${params.sortType == 0 }">selected='selected'</c:if>>升序</option>
							<option value="1" <c:if test="${params.sortType == 1 }">selected='selected'</c:if>>降序</option>
						</select>
					</td>
					<td>
						弹窗：
						<select name="status">
							<option>全部</option>
							<option value="0" <c:if test="${params.status == 0 }">selected='selected'</c:if>>弹窗</option>
							<option value="1" <c:if test="${params.status == 1 }">selected='selected'</c:if>>无弹窗</option>
						</select>
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
					<th align="center" width="50">渠道类型</th>
					<th align="center" width="100">频道类型</th>
					<th align="center" width="100">栏目类型</th>
					<th align="center" width="100">用户群体</th>
					<th align="center" width="120" >标题</th>
					<th align="center" width="150">链接地址</th>
					<th align="center" width="150">发布方式</th>
					<th align="center" width="150">弹窗</th>
					<th align="center" width="150">序号</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ad" items="${pm.items }">
						<tr target="id" rel="${ad.id }">
							<td>
								<c:if test="${ad.showType == 0 }">
									PC端
								</c:if>
								<c:if test="${ad.showType == 1 }">
									APP端
								</c:if>
								<c:if test="${ad.showType == 3 }">
									其他
								</c:if>
							</td>
							<td>	
								<c:if test="${ad.channelType == 0 }">
									首页
								</c:if>
								<c:if test="${ad.channelType == 1 }">
									关于我们
								</c:if>
								<c:if test="${ad.channelType == 2 }">
									帮助中心
								</c:if>
							</td>
							<td>
								<c:if test="${ad.columnType == 0 }">
									banner轮播页
								</c:if>
								<c:if test="${ad.columnType == 1 }">
									动态弹层
								</c:if>
							</td>
							<td>
								<c:if test="${ad.userLevel == 0}">
									全部
								</c:if>
								<c:if test="${ad.userLevel == 1}">
									V1
								</c:if>
								<c:if test="${ad.userLevel == 2}">
									V2
								</c:if>
								<c:if test="${ad.userLevel == 3}">
									V3
								</c:if>
							</td>
							<td>${ad.title }</td>
							<td>${ad.reurl }</td>
							<td>
								<c:if test="${ad.presentWay == 0 }">
									立即发布
								</c:if>
								<c:if test="${ad.presentWay == 1 }">
									定时发布
								</c:if>
							</td>
							<td>
								<c:if test="${ad.status == 0 }">
									弹窗
								</c:if>
								<c:if test="${ad.status == 1 }">
									不弹窗
								</c:if>
							</td>
							<td>
								${ad.sort }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
	</c:if>
</form>