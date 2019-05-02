<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getAppMarketReportPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						应用市场：
						<select name="appMarket">
							<option value="">全部</option>
							<c:forEach var="appMarketType" items="${appTypeList}">
								<option value="${appMarketType.appType}" <c:if test="${appMarketType.appType eq params.appMarket}">selected="selected"</c:if>>${appMarketType.appType}</option>
							</c:forEach>
						</select>
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
				<th align="center" >
					应用市场
				</th>
				<th align="center"  >
					日期
				</th>
				<th align="center" >
					注册量
				</th>
				<th align="center" >
					注册转化率
				</th>
				<th align="center" >
					实名认证
				</th>
				<th align="center" >
					绑卡用户数
				</th>
				<th align="center" >
					申请借款笔数
				</th>
				<th align="center" >
					放款成功笔数
				</th>
				<th align="center" >
					过件率
				</th>
				<th align="center" >
					逾期人数
				</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="market" items="${marketList}" varStatus="status">
				<tr target="id" rel="${market.id }">
					<td>
							${status.count}
					</td>
					<td>
							${market.appMarket}
					</td>
					<td>
						<fmt:formatDate value="${market.installTime}"
										pattern="yyyy-MM-dd" />
					</td>
					<td>
							${market.registerCunt}
					</td>
					<td>
						<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${market.registerTransRate}" />
					</td>
					<td>
							${market.realnameAuthCunt}
					</td>
					<td>
							${market.bindCardCunt}
					</td>
					<td>
							${market.relyLoanCunt}
					</td>
					<td>
							${market.yesLoanCunt}
					</td>
					<td>
						<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${market.crossRate}" />
					</td>
					<td>
							${market.delayCunt}
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>