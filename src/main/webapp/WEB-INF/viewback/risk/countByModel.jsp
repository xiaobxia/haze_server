<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="risk/countByModel?myId=${params.myId}&bType=${params.bType}"
	method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>对象： <select name="reviewWay"  id="reviewWay">
							<option value="">全部</option>
							<c:forEach var="reviewWay" items="${sourceMap}">
                                <option value="${reviewWay.key}"
                                        <c:if test="${reviewWay.key eq params.reviewWay}">selected="selected"</c:if>>${reviewWay.value}</option>
							</c:forEach>
					</select>
					</td>
					<td>观察时间：
					 <input type="text" name="sDate" id="sDate" value="${params.sDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
					  到<input type="text" name="eDate" id="eDate" value="${params.eDate}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
					</td>
					<td>
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
		<table class="table" style="width: 100%;" layoutH="150"
			nowrapTD="false">
			<thead>
				<tr>
					<th align="center">序号</th>
					<th align="center">对象</th>
					<th align="center">日期</th>
					<th align="center">件数</th>
					<th align="center">过件率(%)</th>
					<th align="center">逾期率(%)</th>
					<th align="center">坏账率15天(%)</th>
					<th align="center">坏账率30天(%)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="overdue" items="${pm.items}" varStatus="status">
				 
					<tr>
						<td>${status.count}</td>
						<td>${sourceMap[overdue.reviewWay]}</td>
						<td><fmt:formatDate value="${overdue.pointDate}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td></td>
						<td>${overdue.borrowNum}</td>
						<td>${overdue.passRate}</td>
						<td>${overdue.overdueRate}</td>
						<td>${overdue.dirtyRate15}</td>
						<td>${overdue.dirtyUserRateM1}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
        <div class="divider"></div>
        <div class="pageFoot">
            <div class="searchBar">
                <table class="searchContent" >

                    <tbody>
                    <td>
                        &nbsp;&nbsp;&nbsp;&nbsp;特殊情况件数：
                    </td>
                    <td style="color:red;">
                        ${extraNum}
                    </td>
                    </tbody>
                </table>
            </div>
        </div>
		<c:set var="page" value="${pm}"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>

<script type="text/javascript">
	if("${message}"){
		alertMsg.error(${message});
	}

</script>