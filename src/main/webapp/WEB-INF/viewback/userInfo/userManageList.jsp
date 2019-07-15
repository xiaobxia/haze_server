<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

 <form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/gotoUserManage?myId=${searchParams.myId}&jsp=1" method="post">
	 <input type="hidden" id="jsp" value="1">
	 <div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>ID：<input type="text" name="id"
						value="${searchParams.id }" />
					</td>
					<!-- <td>用户类型: <select name="userType">
							<option value="">不限</option>
							<c:forEach items="${USER_TYPE }" var="type">
								<c:if test="${type.key ne USER_TYPE_SYSTEM }">
									<option value="${type.key }"
										<c:if test="${searchParams.userSystem eq type.key }">selected="selected"</c:if>>${type.value
										}</option>
								</c:if>
							</c:forEach>
					</select>
					</td> -->
					<td>真实姓名: <input type="text" name="realname"
									 value="${searchParams.realname }" />
					</td>
					<td>手机: <input type="text" name="userPhone"
								   value="${searchParams.userPhone }" />
					</td>
					<td>证件号码: <input type="text" name="idNumber"
									 value="${searchParams.idNumber }" />
					</td>
					<td>是否黑名单:
						<select name="status">
							<option value="">不限</option>
							<option value="1" name="status" <c:if test="${searchParams.status eq 1}">selected</c:if>>否</option>
							<option value="2" name="status" <c:if test="${searchParams.status eq 2}">selected</c:if>>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>渠道商：
						<select name="channelSuperCode">
							<option value="">不限</option>
							<option value="-999" name="channelSuperCode"
									<c:if test="${searchParams.channelSuperCode == '-999'}">selected="selected"</c:if> >自然流量</option>
							<c:forEach items="${channel}" var="channel">
								<option value="${channel.channelSuperCode}" name="channelSuperCode"
										<c:if test="${channel.channelSuperCode eq searchParams.channelSuperCode}">selected="selected"</c:if> >${channel.channelSuperName}</option>
							</c:forEach>
						</select>
					</td>
					<td>渠道名称：
						<input type="text" name="channelName" value="${searchParams.channelName}">
					</td>
					<td>开始时间: <input type="text" name="createTime"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						value="${searchParams.createTime }" readonly="readonly" />
					</td>
					<td>结束时间: <input type="text" name="beginTime"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						value="${searchParams.beginTime }" readonly="readonly" />
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
	<div class="pageContent" id="u-m-l-t">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${searchParams.myId}" name="parentId"/>
		</jsp:include>
		<table class="table" width="100%" layoutH="200" nowrapTD="false">
			<thead>
				<tr>
					<th align="center">用户ID</th>
					<th align="center">渠道商</th>
					<th align="center">所属渠道</th>
					<th align="center">姓名</th>
					<%--<th align="center">公司名称</th>--%>
					<th align="center">联系方式</th>
					<%--<th align="center">生日</th>--%>
					<th align="center">性别</th>
					<th align="center">身份证号</th>
					<!-- <th align="center">类型</th>
					<th align="center">状态</th>
					<th align="center">可再借时间</th> -->
					<th align="center">进件状态</th>
					<th align="center">是否黑名单</th>
					<%--<th align="center">注册来源</th>--%>
					<th align="center">创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${pm.items }" varStatus="status" >
					<tr target="userId" rel="${user.id }">
						<td align="center">${user.id }</td>
						<td align="center">${user.channelSuperName}</td>
						<td align="center">${user.channelName}</td>
						<td class="userName" align="center">${user.realname}</td>
						<%--<td align="center">${user.company_name }</td>--%>
						<td class="userPhone" align="center">${user.user_phone }</td>
						<%--<td align="center">
							<c:choose>
								<c:when test="${user.id_number!=null && user.id_number!=''}">${fn:substring(user.id_number, 6, 10)}年${fn:substring(user.id_number, 10, 12)}月${fn:substring(user.id_number, 12, 14)}日</c:when>
								<c:otherwise></c:otherwise>
							</c:choose> 
						</td>--%>
						<td align="center">${user.user_sex}</td>
						<td align="center">${user.idNumber}</td>
						<%-- <td align="center">${user.user_type }</td>
						<td align="center"></td> --%>
						<td align="center">
							<c:choose>
								<c:when test="${user.realNameStatus==1}">
									<span class="trueBtn">身份认证</span>
								</c:when>
								<c:otherwise>
									<span class="nullBtn">身份认证</span>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${user.bankId==1}">
									<span class="trueBtn">银行卡认证</span>
								</c:when>
								<c:otherwise>
									<span class="nullBtn">银行卡认证</span>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${user.tdStatus==2}">
									<span class="trueBtn">运营商认证</span>
								</c:when>
								<c:otherwise>
									<span class="nullBtn">运营商认证</span>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${user.emergencyInfo==1}">
									<span class="trueBtn">通讯录认证</span>
								</c:when>
								<c:otherwise>
									<span class="nullBtn">通讯录认证</span>
								</c:otherwise>
							</c:choose>
						</td>
					<c:if test="${user.status==2}">
						<td align="center">
							<span class="falseBtn">是</span>
						</td>
					</c:if>
					<c:if test="${user.status!=2}">
						<td align="center">
							<span class="trueBtn">否</span>
						</td>
					</c:if>
						<%--<td align="center">
							<c:if test ="${user.qqWechat==1}">
                              qq
							</c:if>
							<c:if test ="${user.qqWechat==2}">
								微信
							</c:if>
							<c:if test ="${user.qqWechat==0}">
								正常
							</c:if>
						</td>--%>

						<td class="time" align="center">
						<fmt:formatDate value="${user.create_time }" pattern="yyyy-MM-dd HH:mm" />
							${ALL_ORIGIN_TYPE[user.originType] }
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<script type="text/javascript">
		DWZ.renderRM && DWZ.renderRM()
	</script>
</form>
