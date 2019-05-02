<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="borrowStatistics/getBorrowStatisticsPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
							开始 时间：
							<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					<td>
							 结束时间：
							<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
					</td>
					<td> 借款次数: <input type="text" name="borrowCount"
						value="${params.borrowCount }" />
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
		<table class="table" style="width: 100%;" layoutH="170" nowrapTD="false">
			<thead>
				<tr>
					<th align="center"  rowspan="2">
						序列
					</th>
					<th align="center"  rowspan="2">
						日期
					</th>
					<th align="center"  rowspan="2">
						借款次数
					</th>
					<th align="center"  rowspan="2">
						当前用户数
					</th>
					<th align="center"  rowspan="2">
						占总借款人数比例
					</th>
					<th align="center"  colspan="4">
						升迁率
					</th>
					<th align="center"  colspan="2">
						逾期率(按单数)
					</th>
					<th align="center" colspan="2">
						逾期率(按金额)
					</th>
					<th align="center" colspan="2">
						M3坏账率(按单数)
					</th>
					<th align="center" colspan="2">
						M3坏账率(按金额)
					</th>
<!-- 					<th align="center" rowspan="2"> -->
<!-- 						更新时间 -->
<!-- 					</th> -->
				</tr>
				<tr>
					<th align="center"  rowspan="2">
						 当日升迁率
					</th>
					<th align="center"  rowspan="2">
						 当周升迁率
					</th>
					<th align="center"  rowspan="2">
						  当月升迁率
					</th>
					<th align="center"  >
						  平均升迁率
					</th>
					<th align="center"  >
						 当日逾期率
					</th>
					<th align="center"  >
						平均逾期率
					</th>
					<th align="center"  >
						当日逾期率
					</th>
					<th align="center" >
						平均逾期率
					</th>
					<th align="center" >
						当日坏账率
					</th>
					<th align="center" >
						平均坏账率
					</th>
					<th align="center" >
						当日坏账率
					</th>
					<th align="center" >
						平均坏账率
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${list}" varStatus="status">
					<tr target="id" rel="${list.id }">
						<td>
							${status.index+1}
						</td>
						<td>
						<fmt:formatDate value="${list.reportDate}" pattern="yyyy-MM-dd"/>
							
						</td>
						<td>
							<c:if test="${list.borrowCount>10}">
												10以上
							</c:if>
							<c:if test="${list.borrowCount<=10}">
												${list.borrowCount}
							</c:if>
							
						</td>
						<td>
							${list.currentUsercount}
						</td>
						<td >
							${list.allBorrowRate}
						</td>
						<td>
							${list.dayPromotionRate}
						</td>
						<td>
							${list.weekPromotionRate}
						</td>
						<td>
							${list.monthPromotionRate}
						</td>
						<td>
							${list.avgPromotionRate}
						</td>
						<td>
							${list.danDayOverduerate}
						</td>
						<td>
							${list.danAvgOverduerate}
						</td>
						<td>
							${list.moneyDayOverduerate}
						</td>
						 <td>
							${list.moneyAvgOverduerate}
						</td>
						  <td>
							${list.danDayBaddebtrate}
						</td>
						 <td>
							${list.danAvgBaddebtrate}
						</td>
						 <td>
							${list.moneyDayBaddebtrate}
						</td>
						<td>
							${list.moneyAvgBaddebtrate}
						</td>
<!-- 						<td> -->
<%-- 						<fmt:formatDate value="${list.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/> --%>
							
<!-- 						</td> -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
	
</form>