<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/userBankCardList?myId=${params.myId}" method="post">
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
						用户名称:
						<input type="text" name="userName"
							   value="${params.userName }" />
					</td>
					<td>
						手机号码:
						<input type="text" name="userPhone"
							   value="${params.userPhone }" />
					</td>
					<td>
						银行卡号:
						<input type="text" name="cardNo"
							   value="${params.cardNo }" />
					</td>
				</tr>
				<tr>
					<td>
						状态
						<select name="status">
						  <option value ="">所有状态</option>
							<option value ="0" <c:if test="${params.status eq 0}">selected</c:if>>未生效</option>
						  <option value="1" <c:if test="${params.status eq 1}">selected</c:if>>已生效</option>
						</select>
					</td>
					<td>
						添加日期:
						<input name="addStartTime" id="addStartTime" value="${params.addStartTime }" type="text"
																onfocus="WdatePicker({isShowClear:true,readOnly:false,maxDate:'#F{$dp.$D(\'addStartTime\')||\'%y-%M-%d\'}'})" 
																title="" size="30" />
					</td>
					<td>
						至:
						<input name="addEndTiem" id="addEndTiem" value="${params.addEndTiem }" type="text"
																onfocus="WdatePicker({isShowClear:true,readOnly:false,maxDate:'#F{$dp.$D(\'addEndTiem\')||\'%y-%M-%d\'}'})" 
																title="" size="30" />
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
						ID
					</th>
					<th align="center"  >
						借款人ID
					</th>
					<th align="center" >
						持卡人姓名
					</th>
					<th align="center" >
						手机号
					</th>
					<th align="center" >
						银行名称
					</th>
					<th align="center" >
						银行卡号
					</th>
					<th align="center" >
						卡片类型
					</th>
					<th align="center" >
						状态
					</th>
					<th align="center"  >
						添加时间
					</th>
					<th align="center">
						默认卡(是/否)
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bankCard" items="${pm.items }" varStatus="status">
					<tr target="bankId" rel="${bankCard.bankId }">
						<td>
							${bankCard.bankId}
						</td>
						<td>
							${bankCard.userId}
						</td>
						<td>
							${bankCard.openName }
						</td>
						<td>
							${bankCard.phone }
						</td>
						<td>
							${bankCard.bankName }
						</td>
						<td>
							${bankCard.cardNo }
						</td>
						<td>
							<c:if test="${bankCard.type==1}">信用卡</c:if>
							<c:if test="${bankCard.type==2}">借记卡</c:if>
						</td>
						<td>
							<c:choose>
								<c:when test="${bankCard.status==1}">生效</c:when>
								<c:otherwise>未生效</c:otherwise>
							</c:choose>
						</td>
						<td>
							<fmt:formatDate value="${bankCard.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
								${bankCard.cardDefault}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>