<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getOveChannelCount?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						渠道商：
						<select name="superChannelId" id="superChannelId">
							<option value="">全部</option>
							<c:forEach var="channelSuperInfo" items="${channelSuperInfos}">
								<option value="${channelSuperInfo.id}" <c:if test="${channelSuperInfo.id eq params.superChannelId}">selected="selected"</c:if>>${channelSuperInfo.channelSuperName}</option>
							</c:forEach>
						</select>
					</td>

					<td>
						渠道名称:
						<input type="text" name="channelName"
							   value="${params.channelName}" />
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
					渠道商
				</th>
				<th align="center" >
					渠道名称
				</th>
				<th align="center"  >
					日期
				</th>
				<th align="center">
					注册量
				</th>

				<th align="center" >
					实名率
				</th>
				<%--<th align="center" >--%>
				<%--绑卡人数--%>
				<%--</th>--%>
				<th align="center" >
					紧急联系人认证率
				</th>
				<th align="center" >
					运营商认证率
				</th>
				<%--<th align="center" >
					淘宝认证人数率
				</th>--%>
				<%--<th align="center" >--%>
				<%--芝麻认证人数--%>
				<%--</th>--%>
				<%--<th align="center" >--%>
				<%--工作信息--%>
				<%--</th>--%>
				<%--<th align="center" >--%>
				<%--黑名单人数--%>
				<%--</th>--%>
				<th align="center">申请笔数</th>
				<th align="center" >
					申请借款率
				</th>
				<th align="center" >
					申请成功率
				</th>
				<th align="center">放款笔数</th>
				<th align="center">放款率</th>
				<%--<th align="center" >--%>
				<%--借款率%--%>
				<%--</th>--%>
				<%--<th align="center" >--%>
				<%--通过率%--%>
				<%--</th>--%>
				<%--<th align="center" orderField="newIntoMoneyDesc" class="asc" >--%>
				<%--新用户放款金额--%>
				<%--</th>--%>
				<th align="center" >
					新用户首逾量
				</th>
				<%--<th align="center" >--%>
				<%--老用户放款金额--%>
				<%--</th>--%>
				<th align="center" >
					逾期人数
				</th>
				<%--<th align="center" >--%>
				<%--费用--%>
				<%--</th>--%>

				<th>
					uv数量
				</th>
				<th>
					uv转化率
				</th>
				<th>
					qq占比
				</th>
				<th>
					微信占比
				</th>
				<%--<th>
					申请笔数
				</th>
				<th>
					放款笔数
				</th>
				<th>
					还款笔数
				</th>
				<th>
					注册率
				</th>
				<th>
					放款率
				</th>
				<th>
					回款率
				</th>--%>
				<th align="center" >
					更新时间
				</th>
				<!-- 					<th align="center" > -->
				<!-- 						授信失败人数 -->
				<!-- 					</th> -->


			</tr>
			</thead>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>
<script type="text/javascript">

    function toChannelReportExcel(obj){

        var href=$(obj).attr("href");
        var a=$("#channelid").val();
        var beginTime=$("#beginTime").val();
        var endTime=$("#endTime").val();
        var toHref=href+"&channelid="+a+"&beginTime="+beginTime+"&endTime="+endTime;

        $(obj).attr("href",toHref);
    }
</script>