<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="content/gotoAdviseList?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>反馈ID：<input type="text" name="id" value="${params.id }" />
					</td>
					<td>用户联系方式: <input type="text" name="userPhone" value="${params.userPhone }" />
					</td>
					<td>是否已处理:
					<select name="adviseStatus">
							<option value="">不限</option>
							<option value="0" name="adviseStatus" value="${params.adviseStatus }">否</option>
							<option value="1" name="adviseStatus" value="${params.adviseStatus }">是</option>
					</select>
					</td>
					<td>反馈时间: <input type="text" name="adviseAddtime"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						value="${params.adviseAddtime }" readonly="readonly" onfocus="WdatePicker({isShowClear:true,readOnly:false,maxDate:'#F{$dp.$D(\'createTime')||\'%y-%M-%d\'}'})"  />
					</td>
					<!-- <td>&nbsp;&nbsp;&nbsp;&nbsp;</td> -->
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
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
		<table class="list" width="100%" layoutH="114">
			<thead>
				<tr>
					<th align="center">反馈序号</th>
					<th align="center">用户联系方式</th>
					<th align="center">反馈内容</th>
					<th align="center">反馈时间</th>
					<th align="center">处理时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="advise" items="${adviseList.items }" varStatus="status" >
					<tr target="id" rel="${advise.id }">
						<td align="center">${advise.id }</td>
						<td align="center">${advise.user_phone }</td>
						<td align="center">${advise.advise_content}</td>
						<td align="center"><fmt:formatDate value="${advise.advise_addtime }" pattern="yyyy-MM-dd HH:mm" /></td>
						<td align="center"><fmt:formatDate value="${advise.feed_date }" pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${adviseList }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>