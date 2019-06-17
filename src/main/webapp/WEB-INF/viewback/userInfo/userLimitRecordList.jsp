<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="backBorrowOrder/getUserLimitPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					 		<td>
						用户Id:
						<input type="text" name="userId"
							value="${params.userId }" />
					</td>
					<td>
						用户姓名:
						<input type="text" name="realname"
							value="${params.realname }" />
					</td>
					<td>
						手机:
						<input type="text" name="userPhone"
							value="${params.userPhone }" />
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
			<table class="table" style="width: 100%;" layoutH="160" nowrapTD="false">
				<thead>
					<tr >
						<th align="center"  >
							序号
						</th>
						<th align="left"  >
							用户Id
						</th>
						<th align="center"  >
							姓名
						</th>
						<th align="center" >
							手机号
						</th>
						<th align="center"  >
							提额后总额度
						</th>
							<th align="center"  >
							本次提升额度
						</th>
						<th align="center" >
							成功还款次数
						</th>
						<th align="center"  >
								正常还款次数
						</th>
						<th align="center" >
								成功还款金额
						</th>
						<th align="center" >
								正常还款金额
						</th>
						<th align="center" >
							上次提额时间
						</th>
					 
						<th align="center" >
							创建时间
						</th>
				 
						 
						<th align="center" >
							更新时间
						</th>
						
						 
						<th align="center">
								状态
						</th>
						<th align="center"  >
							操作人
						</th>
						<th align="center"  >
 					备注
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="borrow" items="${pm.items }" varStatus="status">
						<tr  target="sid_support" rel="${borrow.id }"  >
							<td>
								${status.count}
							</td>
							<td>
							${borrow.userId }
							</td>
							<td>
							${borrow.realname}
							</td>
							<td>
							${borrow.userPhone }
 
							</td>
							<td>
								<fmt:formatNumber type="number" value="${borrow.newAmountMax/100}" pattern="0.00" maxFractionDigits="0"/> 
							</td>
										<td>
								<fmt:formatNumber type="number" value="${borrow.addAmount/100}" pattern="0.00" maxFractionDigits="0"/> 
							</td>
							<td>
							${borrow.repaymentSuccCount}
 </td>
									<td>
									
								${borrow.repaymentNormCount }	
 
							</td>
								<td>
								 
 				<fmt:formatNumber type="number" value="${borrow.repaymentSuccAmount/100}" pattern="0.00" maxFractionDigits="0"/>	</td>
 		<td>
							<fmt:formatNumber type="number" value="${borrow.repaymentNormAmount/100}" pattern="0.00" maxFractionDigits="0"/>
							</td>
									<td>
 							<fmt:formatDate value="${borrow.lastApplyAt }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						 
						<td>
						<fmt:formatDate value="${borrow.createAt }" pattern="yyyy-MM-dd HH:mm:ss"/>
						 
						</td>
				 
							 
					<td>
 							<fmt:formatDate value="${borrow.updatedAt }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						
						 
									<td class="loanStatusName">
 						${borrow.statusName }
							</td>
									<td>
 ${borrow.auditUser }	
							</td>
 <td>
 ${borrow.remark }
 </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>
	 
<script type="text/javascript">
	if("${message}"){
		alertMsg.error(${message});
	}
	if (renderLoanSuccessCount) {
		setTimeout(function () {
			renderLoanSuccessCount()
		}, 200)
	}

	if (renderLoanStatusName) {
		setTimeout(function () {
			renderLoanStatusName()
		}, 200)
	}
</script>