<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getChannelPrReportPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					 
<!-- 					 <td> -->
<!-- 							渠道商： -->
<!-- 							<select name="channelid"> -->
<!-- 							<option value="">全部</option> -->
<%-- 							<c:forEach var="channelList" items="${channelList}"> --%>
								 
<%-- 									<option value="${channelList.id}" <c:if test="${channelList.id eq params.channelid}">selected="selected"</c:if>>${channelList.channelName}</option> --%>
								 
<%-- 							</c:forEach> --%>
<!-- 					</select> -->
<!-- 					</td> -->

					<td>
								省份：<select name="channelProvince" id="channelProvince">${params.channelProvince}</select>
                                                                                               城市：<select name="channelCity" id="channelCity"></select>
                                 <select name="channelArea" id="channelArea" style="display:none"></select>
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
						日期
					</th>
					<th align="center" >
						省份
					</th>
					<th align="center" >
						城市
					</th>
					<th align="center"  >
						注册量
					</th>
					<th align="center" >
						实名认证
					</th>
					<th align="center" >
						绑卡人数
					</th>
					<th align="center" >
						紧急联系人
					</th>
					<th align="center" >
						运营商认证
					</th>
					<th align="center" >
						支付宝认证人数
					</th>
					<th align="center" >
						芝麻认证人数
					</th>
					<th align="center" >
						工作信息
					</th>
					<th align="center" >
						申请借款人数
					</th>
					<th align="center" >
						申请成功人数
					</th>
					<th align="center" >
						通过率%
					</th>
					<th align="center" >
						放款金额
					</th>
					<th align="center" >
						黑名单人数
					</th>
					<th align="center" >
						逾期人数
					</th>
<!-- 					<th align="center" > -->
<!-- 						授信失败人数 -->
<!-- 					</th> -->
					
					 
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${pm.items }" varStatus="status">
					<tr target="id" rel="${channel.id }">
						<td>
							${status.index+1}
						</td>
						<td>
						<fmt:formatDate value="${channel.reportDate}" pattern="yyyy-MM-dd" />
						</td>
						<td>
							${channel.channelProvince}
						</td>
						<td>
							${channel.channelCity}
						</td>
						<td>
							${channel.registerCount}【android:${channel.androidCount}，ios:${channel.iosCount}】
						</td>
						<td>
							${channel.attestationRealnameCount}
						</td>
						<td>
							${channel.attestationBankCount}
						</td>
						<td>
							${channel.contactCount}
						</td>
						<td>
							${channel.jxlCount}
						</td>
						<td>
							${channel.alipayCount}
						</td>
						<td>
							${channel.zhimaCount}
						</td>
						<td>
							${channel.companyCount}
						</td>
						<td>
							${channel.borrowApplyCount}
						</td>
						<td>
							${channel.borrowSucCount}
						</td>
						<td>
							<c:if test="${channel.borrowSucCount gt 0}">
								<fmt:formatNumber type="number"
									value="${channel.borrowSucCount/channel.borrowApplyCount*100}" pattern="0.00"
									maxFractionDigits="2" />
							</c:if>
							<c:if test="${channel.borrowSucCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.intoMoney/100}
						</td>
						<td>
							${channel.blackUserCount}
						</td>
						<td>
							${channel.lateDayCount}
						</td>
<!-- 						<td> -->
<%-- 							${channel.approveErrorCount} --%>
<!-- 						</td> -->
						
<!-- 						<td> -->
<%-- 							<fmt:formatDate value="${channel.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/> --%>
<!-- 						</td> -->
					</tr>
				</c:forEach>
				
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>
<script type="text/javascript">
	 
	function toChannelExcel(obj){
		 
		var href=$(obj).attr("href");
		var channelProvince=$("#channelProvince").val();
		var channelCity=$("#channelCity").val();
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		var toHref=href+"&channelProvince="+channelProvince+"&channelCity="+channelCity+"&beginTime="+beginTime+"&endTime="+endTime;
 
		$(obj).attr("href",toHref);
	}
</script>

<script type="text/javascript">

 
   	new PCAS("channelProvince","channelCity","channelArea",'${params.channelProvince}','${params.channelCity}','${params.channelArea}');
</script>