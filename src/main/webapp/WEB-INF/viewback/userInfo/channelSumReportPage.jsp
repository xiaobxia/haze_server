<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getChannelSumReportPage?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					 
					 <td>
							渠道商：
							<select name="superChannelId">
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
<%--
                    <td>
							添加时间：
							<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						</td>--%>
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
		<table class="table" style="width: 100%;" layoutH="160" nowrapTD="false" ifScrollTable="true">
			<thead>
				<tr>
					<th align="center"  >
						序列
					</th>
					<th align="center" >
						渠道
					</th>
					<th align="center"  >
						注册量
					</th>
					<th align="center" >
						实名认证
					</th>
					<th align="center" >
						实名率
					</th>
					<th align="center" >
						绑卡人数
					</th>
					<th align="center" >
						绑卡率
					</th>
					<th align="center" >
						紧急联系人
					</th>
					<th align="center" >
						紧急联系人上传率
					</th>
					<th align="center" >
						运营商认证
					</th>
					<th align="center" >
						运营商认证率
					</th>
					<th align="center" >
						淘宝认证人数
					</th>
					<th align="center" >
						淘宝认证率
					</th>
					<%--<th align="center" >--%>
						<%--工作信息--%>
					<%--</th>--%>
					<th align="center" >
						黑名单人数
					</th>
					<th align="center" >
						黑名单率
					</th>
					<th align="center" >
						申请借款人数
					</th>
					<th align="center" >
						申请借款率
					</th>
					<th align="center" >
						申请成功人数
					</th>
					<th align="center" >
						申请成功率
					</th>

					<th align="center" >
						放款金额
					</th>
					<th align="center" >
						逾期人数
					</th>
					<th align="center" >
						逾期率
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
							${channel.channelName}
						</td>
						<td>
							${channel.registerCount}
						</td>
						<td>
							${channel.attestationRealnameCount}
						</td>
						<td>
							<c:if test="${channel.attestationRealnameCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.attestationRealnameCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.attestationRealnameCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.attestationBankCount}
						</td>
						<td>
							<c:if test="${channel.attestationBankCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.attestationBankCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.attestationBankCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.contactCount}
						</td>
						<td>
							<c:if test="${channel.contactCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.contactCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.contactCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.jxlCount}
						</td>
						<td>
							<c:if test="${channel.jxlCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.jxlCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.jxlCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.zhimaCount}
						</td>
						<td>
							<c:if test="${channel.zhimaCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.zhimaCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.zhimaCount  eq 0}">
								0.00
							</c:if>
						</td>
						<%--<td>--%>
							<%--${channel.companyCount}--%>
						<%--</td>--%>
						<td>
							${channel.blackUserCount}
						</td>
						<td>
							<c:if test="${channel.blackUserCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.blackUserCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.blackUserCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.borrowApplyCount}
						</td>
						<td>
							<c:if test="${channel.borrowApplyCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.borrowApplyCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.borrowApplyCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.borrowSucCount}
						</td>
						<td>
							<c:if test="${channel.borrowSucCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.borrowSucCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.borrowSucCount  eq 0}">
								0.00
							</c:if>
						</td>
						<td>
							${channel.intoMoney/100}
						</td>
						<td>
							${channel.lateDayCount}
						</td>
						<td>
							<c:if test="${channel.lateDayCount gt 0}">
								<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.lateDayCount / channel.registerCount}" />
							</c:if>
							<c:if test="${channel.lateDayCount  eq 0}">
								0.00
							</c:if>
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