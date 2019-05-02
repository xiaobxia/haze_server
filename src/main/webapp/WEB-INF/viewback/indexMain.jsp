<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/common/back";
%>
<c:set var="path" value="<%=path%>"></c:set>
<c:set var="basePath" value="<%=basePath%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>数据统计</title>
		<script src="${basePath }/js/jquery-1.7.2.js" type="text/javascript"></script>
		<script src="${basePath }/js/util.js" type="text/javascript"></script>
		<script type="text/javascript">
	$(function() {
		$.getJSON("summary/summaryBorrow?type=riskToday", function(data) {
			$.each(data, function(val, item) {
				$("#" + val).html(addCommas(item, false));
			});
		});
		//$.getJSON("summary/summaryBorrow?type=riskUser", function(data) {
		//$.each(data, function(val, item) {
		//$("#"+val).html(addCommas(item,false));
		//});
		//});
		$.getJSON("summary/summaryUser?type=regUser", function(data) {
			$.each(data, function(val, item) {
				$("#" + val).html(addCommas(item, false));
			});
		});
		$.getJSON("summary/summaryUser?type=approve&secondType=allApprove", function(data) {
			$.each(data, function(val, item) {
				$("#"+val).html(addCommas(item,false));
			});
		});
		$.getJSON("summary/summaryUser?type=approve&secondType=realName", function(data) {
			$.each(data, function(val, item) {
				$("#"+val).html(addCommas(item,false));
			});
		});
		$.getJSON("summary/summaryUser?type=approve&secondType=bank", function(data) {
			$.each(data, function(val, item) {
				$("#"+val).html(addCommas(item,false));
			});
		});
		$.getJSON("summary/summaryUser?type=approve&secondType=zm", function(data) {
			$.each(data, function(val, item) {
				$("#"+val).html(addCommas(item,false));
			});
		});
		$.getJSON("summary/summaryUser?type=approve&secondType=mobile", function(data) {
			$.each(data, function(val, item) {
				$("#"+val).html(addCommas(item,false));
			});
		});
		$.getJSON("summary/summaryUser?type=approve&secondType=contacat", function(data) {
			$.each(data, function(val, item) {
				$("#"+val).html(addCommas(item,false));
			});
		});
		$.getJSON("summary/summaryBorrow?type=borrow", function(data) {
			$.each(data, function(val, item) {
				$("#" + val).html(addCommas(item, false));
			});
		});
		$.getJSON("summary/summaryBorrow?type=loan", function(data) {
			$.each(data, function(val, item) {
				$("#" + val).html(addCommas(item, false));
			});
		});
		$.getJSON("summary/summaryBorrow?type=risk", function(data) {
			$.each(data, function(val, item) {
				$("#" + val).html(addCommas(item, false));
			});
		});
		$.getJSON("summary/summaryBorrow?type=old&customerType=1", function(
				data) {
			$.each(data, function(val, item) {
				$("#" + val).html(addCommas(item, false));
			});
		});
		$.getJSON("summary/summaryBorrow?type=old&customerType=0", function(
				data) {
			$.each(data, function(val, item) {
				$("#" + val).html(addCommas(item, false));
			});
		});
	});
</script>
	</head>
	<body>
		<div style="overflow: scroll;" class="pageFormContent" layoutH="20">
			<fieldset name="message" style="padding-bottom: 30px;">
				<legend>
					用戶统计
				</legend>
				<table>
					<tr>
						<td>
							总用户数：
						</td>
						<td id="sumCount"></td>
						<td>
							今日注册用户数：
						</td>
						<td id="todayRegCount"></td>
					</tr>
					<tr>
						<td>
							全要素认证用户数：
						</td>
						<td id="allApprove"></td>
						<td>
							实名认证用户总数：
						</td>
						<td id="realName"></td>
					</tr>
					<tr>
						<td>
							绑卡用户总数：
						</td>
						<td id="bank"></td>
						<td>
							芝麻认证用户总数：
						</td>
						<td id="zm"></td>
					</tr>
					<tr>
						<td>
							运营商认证总数：
						</td>
						<td id="mobile"></td>
						<td>
							紧急联系人认证总数：
						</td>
						<td id="contacat"></td>
					</tr>
				</table>
			</fieldset>
			<fieldset name="backlog" style="padding-bottom: 30px;">
				<legend>
					放款统计
				</legend>
				<table>
					<tr>
						<td>
							累计放款金额：
						</td>
						<td id="borrowMoney"></td>
						<td>
							累计放款笔数：
						</td>
						<td id="borrowCount"></td>
					</tr>
					<tr>
						<td>
							当日放款金额：
						</td>
						<td id="borrowMoneyToday"></td>
						<td>
							当日放款笔数：
						</td>
						<td id="borrowCountToday"></td>
					</tr>
					<tr>
						<td>
							放款中总金额：
						</td>
						<td id="loaningMoney"></td>
						<td>
							放款中笔数：
						</td>
						<td id="loaningCount"></td>
					</tr>
					<tr>
						<td>
							放款失败金额：
						</td>
						<td id="loanFailMoney"></td>
						<td>
							放款失败笔数：
						</td>
						<td id="loanFailCount"></td>
					</tr>
				</table>
			</fieldset>
			<fieldset name="backlog" style="padding-bottom: 30px;">
				<legend>
					风控统计
				</legend>
				<table>
					<tr>
						<td>
							机审累计订单数：
						</td>
						<td id="riskTotalOrders"></td>
						<td>
							机审通过累计订单数：
						</td>
						<td id="riskPassOrders"></td>
						<td>
							机审订单通过率：
						</td>
						<td id="riskPassOrdersRate"></td>
					</tr>
					<tr>
						<td>
							今日机审订单数：
						</td>
						<td id="riskOrdersTotalToday"></td>
						<td>
							今日机审通过订单数：
						</td>
						<td id="riskOrdersPassToday"></td>
						<td>
							今日机审通过率：
						</td>
						<td id="riskOrdersPassRateToday"></td>
					</tr>
					<tr>
						<td>
							今日老用户已审：
						</td>
						<td id="todayOldOrderReview1"></td>
						<td>
							今日老用户通过：
						</td>
						<td id="todayOldOrderSuc1"></td>
						<td>
							今日老用户通过率：
						</td>
						<td id="todayOldOrderRate1"></td>
					</tr>
					<tr>
						<td>
							今日新用户已审：
						</td>
						<td id="todayOldOrderReview0"></td>
						<td>
							今日新用户通过：
						</td>
						<td id="todayOldOrderSuc0"></td>
						<td>
							今日新用户通过率：
						</td>
						<td id="todayOldOrderRate0"></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</body>
</html>
