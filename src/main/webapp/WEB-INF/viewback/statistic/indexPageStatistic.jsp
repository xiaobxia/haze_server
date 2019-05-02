<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						订单号:<input type="text" name="outTradeNo" value="${params.outTradeNo }" />
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
					<tr >
						<th align="center"  >
							序号
						</th>
						<th align="left"  >
							订单号
						</th>
						<th align="center"  >
							姓名
						</th>
						<th align="center" >
							手机号
						</th>
						<th align="center"  >
							是否是老用户
						</th>
						<th align="center" >
							借款金额(元)
						</th>
						<th align="center"  >
								借款期限
						</th>
						<th align="center" >
								服务费利率(万分之一)
						</th>
						<th align="center" >
								服务费(元)
						</th>
						<th align="center" >
							申请时间
						</th>
					 
						<th align="center" >
							公司名称
						</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>
	 
<script type="text/javascript">
</script>