<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/getPlatformPage?myId=${params.myId}"
	method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						开始时间:
						<input type="text" id="beginTime" name="beginTime" value="<fmt:formatDate value="${params.beginTime }" pattern="yyyy-MM-dd" />"  
						onclick="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'})"/>
					</td>
					<td>
						结束时间:
						<input type="text" id="endTime" name="endTime" value="<fmt:formatDate value="${params.endTime }" pattern="yyyy-MM-dd" />" 
						 onclick="WdatePicker({readOnly:true,maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginTime\')}'})"
						 />
					</td>
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
			<jsp:param value="${params.myId}" name="parentId" />
		</jsp:include>
		<table class="table" style="width: 100%;" layoutH="112"
			nowrapTD="false">
			<thead>
				<tr>
					<th align="center">序号</th>
					<th align="left">日期</th>
					<th align="left">注册人数</th>
					<th align="center">实名认证人数</th>
					<th align="center">实名认证次数</th>
					<th align="center">实名费用(元)</th>
					<th align="center">运营商认证数</th>
					<th align="center">生成报告人数</th>
					<th align="center">运营商费用</th>
					<th align="center">绑卡人数</th>
					<th align="center">芝麻认证人数</th>
					<th align="center">芝麻认证费用</th>
					<th align="center">认证工作人数</th>
					<th align="center">支付宝认证人数</th>
					<th align="center">借款申请总数</th>
					<th align="center">通过审核总数</th>
					<th align="center">订单通过率</th>
					<th align="center">应放款总额</th>
					<th align="center">放款成功总额</th>
					<th align="center">放款成功笔数</th>
					<th align="center">未到账笔数</th>
					<th align="center">打款失败总订单数</th>
					<th align="center">通过用户总数</th>
					<th align="center">用户通过率</th>
					<th align="center">反欺诈人数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="pr" items="${pm.items }" varStatus="status">
					<tr target="sid_support" rel="${pr.id }">
						<td>${status.count}</td>
						<td>${pr.reportDate}</td>
						<td>${pr.registerNum}</td>
						<td>${pr.realPersonNum}</td>
						<td>${pr.realNum}</td>
						<td>${pr.realMoney}</td>
						<td>${pr.yysNum}</td>
						<td>${pr.yysSucNum}</td>
						<td>${pr.yysMoney}</td>
						<td>${pr.bankNum}</td>
						<td>${pr.zmNum}</td>
						<td>${pr.zmMoney}</td>
						<td>${pr.workNum}</td>
						<td>${pr.alipayNum}</td>
						
						<td>${pr.borrowNum}</td>
						<td>${pr.borrowSucNum}</td>
						<td>${pr.borrowRate}</td>
						<td>${pr.shouldPayMoney}</td>
						<td>${pr.sucPayMoney}</td>
						<td>${pr.sucPayNum}</td>
						<td>${pr.noPayNum}</td>
						<td>${pr.failPayNum}</td>
						<td>${pr.borrowUserSucNum}</td>
						<td>${pr.borrowUserRate}</td>
						<td>${pr.fqz}</td>
<%-- 						<td><fmt:formatDate value="${borrow.orderTime }" --%>
<%-- 								pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
<%-- 						<td><fmt:formatDate value="${borrow.loanTime }" --%>
<%-- 								pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
<%-- 						<td><c:forEach var="capitalTypeMap" items="${capitalTypeMap}"> --%>
<%-- 								<c:if test="${capitalTypeMap.key eq borrow.capitalType}">${capitalTypeMap.value}</c:if> --%>
<!-- 								</option> -->
<%-- 							</c:forEach></td> --%>

<%-- 						<td><c:forEach var="borrowStatus" items="${allstatus}"> --%>
<%-- 								<c:if test="${borrowStatus.key eq borrow.status}">${borrowStatus.value}</c:if> --%>
<!-- 								</option> -->
<%-- 							</c:forEach></td> --%>

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
	
	function downLoadExcel(obj){
 
		var href=$(obj).attr("href");
	 
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		var toHref=href+"&beginTime="+beginTime+"&endTime="+endTime;
 
		$(obj).attr("href",toHref);
	}
</script>