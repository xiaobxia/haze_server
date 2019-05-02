<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/userCertificationList?myId=${params.myId}" method="post">
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
						姓名:
						<input type="text" name="realname"
							   value="${params.realname }" />
					</td>
					<td>
						手机号:
						<input type="text" name="userPhone" value="${params.userPhone }" />
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
						序列
					</th>
					<th align="center"  >
						用户ID
					</th>
					<th align="center" >
						用户姓名
					</th>
					<th align="center"  >
						手机号
					</th>
					<th align="center"  >
						支付密码
					</th>
					<th align="center"  >
						身份认证
					</th>
					<th align="center"  >
						紧急联系人
					</th>
					<th align="center"  >
						银行卡
					</th>
					<th align="center"  >
                        淘宝认证
					</th>
					<th align="center"  >
						同盾手机运营商
					</th>
					<th align="center"  >
						工作信息
					</th>
					<th align="center"  >
						认证更多
					</th>
					<th align="center"  >
						支付宝
					</th>
					<th align="center"  >
						淘宝
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="realName" items="${pm.items }" varStatus="status">
					<tr target="index" rel="${bankCard.bankId }">
						<td>
							${status.index+1}
						</td>
						<td>
							${realName.userId}
						</td>
						<td>
							${realName.realname }
						</td>
						<td>
							${realName.userPhone }
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.payPassWord!=null}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.realNameStatus==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.emergencyInfo==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>

						<td>
							<c:choose>
								<c:when test="${realName.bankId==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.zmStatus==2}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.tdStatus==2}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.companyInfo==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.qq!=null&&realName.email!=null&&realName.wechat_account!=null&&realName.taobaoAccount!=null}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.myhbtime!=null}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${realName.zfbStatus==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>