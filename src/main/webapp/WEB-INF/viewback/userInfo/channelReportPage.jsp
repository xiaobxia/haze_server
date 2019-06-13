<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getChannelReportPage?myId=${params.myId}" method="post">
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
					实名率/总
				</th>
				<%--<th align="center" >--%>
					<%--绑卡人数--%>
				<%--</th>--%>
				<th align="center" >
					通讯录认证率/总
				</th>
				<th align="center" >
					运营商认证率/总
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
				<th align="center">申请笔数/总</th>
				<th align="center" >
					申请借款率/总
				</th>
				<th align="center" >
					申请成功率/总
				</th>
				<th align="center">续借率</th>
				<th align="center">放款笔数/总</th>
				<th align="center">放款率/总</th>
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
					uv数量/转化率
				</th>
				<%--<th>
					uv转化率
				</th>--%>
				<th>
					qq/微信占比
				</th>
				<%--<th>
					微信占比
				</th>--%>
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
			<tbody>
			<c:forEach var="channel" items="${pm.items }" varStatus="status">
				<tr target="id" rel="${channel.id }">
					<td>
							${status.index+1}
					</td>
					<td>
						<c:choose>
							<c:when test="${channel.channelSuperName != null}">
								${channel.channelSuperName}
							</c:when>
							<c:otherwise>
								自然流量
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${channel.channelName != null}">
								${channel.channelName}
							</c:when>
							<c:otherwise>
								自然流量
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<fmt:formatDate value="${channel.reportDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td>
							${channel.registerCount}【android:${channel.androidCount}，ios:${channel.iosCount}】
					</td>
					<td>
						<!--实名率=当日注册用户中实名人数/当日注册数-->
						<c:if test = "${channel.dayRealnameCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.dayRealnameCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.dayRealnameCount eq 0 or channel.registerCount eq 0 }">
							0.00%
						</c:if>
						/
						<!--总=当日实名人数/当日注册数-->
						<c:if test="${channel.attestationRealnameCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.attestationRealnameCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.attestationRealnameCount  eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
					</td>
					<%--<td>--%>
							<%--${channel.attestationBankCount}--%>
					<%--</td>--%>
					<%--<td>--%>
							<%--${channel.contactCount}--%>
					<%--</td>--%>
					<td>
						<!--通讯录认证率=当日注册用户中通讯录认证数/当日注册数-->
						<c:if test = "${channel.dayContactCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.dayContactCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.dayContactCount eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
						/
						<!--总=当日通讯录认证数/当日注册数-->
						<c:if test="${channel.contactCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.contactCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.contactCount  eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
					</td>
					<%--<td>--%>
							<%--${channel.jxlCount}--%>
					<%--</td>--%>
					<td>
						<!--运营商认证率=当日注册用户中运营商认证数/当日注册数-->
						<c:if test="${channel.dayTdCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.dayTdCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.dayTdCount eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
						/
						<!--总=当日运营商认证数/当日注册数-->
						<c:if test="${channel.jxlCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.jxlCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.jxlCount  eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
					</td>
					<%--<td>
						<c:if test="${channel.zhimaCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.zhimaCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.zhimaCount  eq 0 or channel.registerCount eq 0}">
							0.00
						</c:if>
					</td>--%>
					<%--<td>--%>
							<%--${channel.alipayCount}--%>
					<%--</td>--%>
						<%--<td>--%>
						<%--${channel.zhimaCount}--%>
						<%--</td>--%>
					<%--<td>--%>
							<%--${channel.companyCount}--%>
					<%--</td>--%>
					<%--<td>--%>
							<%--${channel.blackUserCount}--%>
					<%--</td>--%>
					<%--<td>--%>
							<%--${channel.borrowApplyCount}--%>
					<%--</td>--%>
					<td>
						<!--申请借款数=当日总申请借款数-续借人数 -->
							${channel.borrowApplyCount-channel.xujieCount}
						/
							${channel.borrowApplyCount}
					</td>
					<td>
						<!--当日借款率=当日新用户申请数/当日注册数-->
						<c:if test="${channel.borrowApplyCount gt 0 and channel.registerCount gt 0 and channel.borrowApplyCount gt channel.xujieCount }">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${(channel.borrowApplyCount - channel.xujieCount) / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.dayBorrowApplyCount eq 0 or channel.registerCount eq 0 }">
							0.00%
						</c:if>
						/
						<!--总=当日借款数/当日注册数-->
						<c:if test="${channel.borrowApplyCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.borrowApplyCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.borrowApplyCount  eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
					</td>
					<%--<td>
							${channel.borrowSucCount}
					</td>--%>
					<td>
						<!--申请成功率=当日新用户放款数/当日申请数-->
						<c:if test="${channel.loanCount gt 0 and channel.borrowApplyCount eq 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.loanCount / channel.borrowApplyCount}" />
						</c:if>
						<c:if test="${channel.loanCount eq 0 or channel.borrowApplyCount eq 0}">
							0.00%
						</c:if>
						/
						<!--总=当日放款数/当日申请数-->
						<c:if test="${channel.borrowSucCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.borrowSucCount / channel.registerCount}" />
						</c:if>
						<c:if test="${channel.borrowSucCount  eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
					</td>
					<td>
						<!--续借率=当日复借数/当日回全款数-->
						<c:if test="${channel.xujieCount gt 0 and channel.allRepayCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.xujieCount / channel.allRepayCount}" />
						</c:if>
						<c:if test="${channel.xujieCount  eq 0 or channel.allRepayCount eq 0}">
							0.00%
						</c:if>
					</td>
					<td>
						<!--新用户放款笔数/总-->
							${channel.loanCount}
						    /
						   ${channel.borrowSucCount}
					</td>
					<td>
						<!--放款率=新用户放款数/当日注册数-->
						<c:if test = "${channel.loanCount  gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.loanCount / channel.registerCount}" />
						</c:if>
						<c:if test = "${channel.loanCount eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
						/
						<!--总=当日放款数/当日注册数-->
						<c:if test ="${channel.borrowSucCount gt 0 and channel.registerCount gt 0}">
							<fmt:formatNumber type="percent" maxFractionDigits="2"  pattern="0.00%" value="${channel.borrowSucCount / channel.registerCount}" />
						</c:if>
						<c:if test = "${channel.borrowSucCount eq 0 or channel.registerCount eq 0}">
							0.00%
						</c:if>
					</td>
					<%--	<c:if test = "${channel.loanCount gt 0}"> 续借率
                        <c:if test = "${channel.loanCount - channel.dayBorrowSucCount >0 and }">

						</c:if>
						</c:if>--%>
						<%--	${channel.loanRatio}--%>
					</td>
					<%--<td>--%>
							<%--${channel.borrowRate}--%>
					<%--</td>--%>
					<%--<td>--%>
							<%--${channel.passRate}--%>
					<%--</td>--%>
					<%--<td>--%>
							<%--${channel.newIntoMoney/100}--%>
					<%--</td>--%>
					<td>
							${channel.dayOverdueCount}
					</td>
					<%--<td>--%>
							<%--${channel.oldIntoMoney/100}--%>
					<%--</td>--%>
					<td>
							${channel.lateDayCount}
					</td>
					<%--<td>--%>
						<%--<fmt:formatNumber pattern='###,###,##0.00' value="${channel.channelMoney}"/>--%>

					<%--</td>--%>

					<td>
								${channel.uvCount} / ${channel.uvRate}
					</td>
					<%--<td>
						${channel.uvRate}
					</td>--%>
					<td>
						${channel.qqRate} / ${channel.wechatRate}
					</td>
					<%--<td>
						${channel.wechatRate}
					</td>--%>
					<%--<td>
							${channel.borrowApplyCount}
						</td>
						<td>
							${channel.loanCount}
						</td>
						<td>
							 ${channel.repaymentCount}
						</td>
						<td>
							 ${channel.registRatio}
						</td>
						<td>
						    ${channel.loanRatio}
						</td>
						<td>
							 ${channel.repayRatio}
						</td>--%>
					<td>
						<fmt:formatDate value="${channel.createdAt}"
										pattern="yyyy-MM-dd HH:mm" />
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
    /*$(function(){
        $.ajax({
            global: false,
            type: "POST",
            url: "task/channelReportLoad",
            dataType: "json",
            success: function (data) {

            },
            error: function () {

            }
        });
    });*/
    function toChannelReportExcel(obj){

        var href=$(obj).attr("href");
        var a=$("#channelid").val();
        var beginTime=$("#beginTime").val();
        var endTime=$("#endTime").val();
        var toHref=href+"&channelid="+a+"&beginTime="+beginTime+"&endTime="+endTime;

        $(obj).attr("href",toHref);
    }
</script>