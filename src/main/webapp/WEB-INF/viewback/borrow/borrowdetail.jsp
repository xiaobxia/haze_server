<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/common/back";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<title>用户详情</title>

	<script type="text/javascript" src="${path}/resources/js/maps.js"></script>
	<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
	<style type="text/css">
		.userTable td {
			border-bottom: 1px solid #928989;
			border-right: 1px solid #928989;
			line-height: 31px;
			overflow: hidden;
			padding: 0 3px;
			vertical-align: middle;
			font-size: 14px;
		}
		.userTable  table {
			border: none;
		}
		.userTable td table {
			border-bottom: 0;
			width: 100%;
		}
		.userTable td a {
			color: #5dacd0;
		}
		.userTable tr.identity {
			text-align: center;
		}
		.userTable tr.identity img {
			margin: 10px 0 0;
			width: 320px;
		}
		.userTable tr.identity td:last-child img{
			height: 210px;
			width: auto;
		}
		.userTable {
			padding: 0;
			border: solid 1px #928989;
			width: 100%;
			line-height: 21px;
		}
		.tdGround {
			background: #f5f5f5 none repeat scroll 0 0;
			border: 1px solid darkgray;
			color: #555;
			font: 12px "Lucida Grande", Verdana, Lucida, Helvetica, Arial, 'Simsun',
			sans-serif;
			font-weight: bold;
		}

		.detailB th {
			border: 1px solid darkgray;
			color: #555;
			background: #f5ecec none repeat scroll 0 0;
			font-weight: bold;
			width: 100px;
			line-height: 21px;
		}
		/*   图片大小设置  */
		.tdGround img{
			width: 100px;
			transition:all 0.3s
		}
		/*   图片鼠标移动放大  */
		.tdGround img:hover{
			width:550px;
		}
		.m-jlxBook tr td {
			padding: 3px 0;
			font-size: 14px;
		}
		/*  弹窗  */
		.popContent {
			position: fixed;
			top: 50%;
			left: 50%;
			transform: translate(0,-50%);
			background: #f0eff0;
			border-radius: 8px;
			padding: 20px;
		}
		.new-popup {
			display: block;
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background: rgba(0,0,0,.6);
			z-index: 150;
		}
		.contact-content {
			margin-left: -440px;
			transform: translate(0,-50%);
		}
		.common-popContent{
			width: 800px;
			max-height: 520px;
			overflow-y: scroll;
			color: #333;
			padding: 10px 20px;
			z-index: 100;
			padding-bottom: 15px;
			border-radius: 10px;
		}
		#cajlContactPop table,
		.new-popup .common-popContent table{
			width: 800px;
		}
		#cajlContactPop table,
		.new-popup .common-popContent table{
			border-left: 1px solid #c3c3c3;
			border-top: 1px solid #c3c3c3;
		}
		.new-popup table tr td {
			line-height: 28px;
			text-indent: 20px;
		}
		#cajlContactPop table td {
			border-bottom: 1px solid #d5dbdf;
			border-right: 1px solid #d5dbdf;
			text-indent: 20px;
			line-height: 22px;
			font-size: 13px;
		}
		.closeBtn {
			width: 800px;
			display: block;
			color: red;
			font-size: 25px;
			text-align: right;
			margin: 0 auto 15px;
		}
		.closeBtn span {
			font-size: 18px;
			color: #333;
			margin-right: 300px;
		}
		.closeBtn:hover {
			text-decoration: none;
		}
		.contact_num a{
			padding: 5px 10px;
			border: 1px solid #ddd;
			border-radius: 5px;
			background: #eee;
			margin-left: 15%;
		}
		.contact_num td > span {
			font-size: 14px;
			color: #484848;
			margin-left: 15%;
		}
		/*图片资料-查看大图弹窗*/
		.view-larger{
			display: none;
		}
		.view-larger .overlay{
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background: rgba(0,0,0,.6);
			z-index: 999;
		}
		.view-larger .o-header{
			margin: 8px 0;
			background: #000;
			padding: 8px 40px 13px;
			text-align: center;
		}
		.view-larger .o-header p{
			color: #eee;
			font-size: 14px;
			font-weight: bold;
			letter-spacing: 2px;
			margin-top: 5px;
		}
		.view-larger .o-header p span{
			font-size: 14px;
			color: #fff;
		}
		.view-larger .o-header a.close{
			color: #fff;
			font-size: 28px;
			position: absolute;
			right: 40px;
			top: 15px;
		}
		.view-larger .o-middle img{
			position: fixed;
			top: 50%;
			margin-top: -20px;
			z-index: 100000;
			cursor: pointer;
		}
		.view-larger .o-middle img.left {
			left: 10%;
		}
		.view-larger .o-middle img.right {
			right: 10%;
		}
		.view-larger .img-group{
			position: fixed;
			top: 50%;
			left: 50%;
			z-index: 100000;
			display: none;
		}
		.view-larger .img-group img{
			border: 3px solid #DDDDDD;
			float: left;
			width: auto;
			height:auto;
			max-height: 550px;
			max-width: 670px;
		}
		/*同盾运营商数据弹窗-new*/
		.table-box {
			margin-top: 12px;
			border: 1px solid #d0d0d0;
			margin: 20px;
		}
		.table-title {
			background-color: #e4e4e4;
			text-align: center;
			font-size: 15px;
			color: #222;
			line-height: 38px;
		}
		.table-box table {
			width: 100%;
			text-align: left;
			table-layout: fixed;
		}
		.table-box table tbody tr td {
			height: 19px;
			border: 1px solid #f5f5f5;
			line-height: 20px;
			padding: 8px 0;
			font-size: 12px;
			overflow: hidden;
			word-wrap: break-word;
			word-break: break-all;
			text-align: center;
		}
		.u-operator table tbody tr td {
			text-align: left;
		}
		.table-box table thead tr th {
			line-height: 30px;
			background: #f5f5f5;
		}
		.table-box table tbody tr td:first-child,
		.table-box table thead tr:first-child th{
			text-indent: 15px;
		}
		.table-box table thead tr {
			height: 21px;
			line-height: 21px;
			font-size: 12px;
			text-align: center;
		}
		.tdyys-content {
			width: 1160px;
			background: #fff;
			max-height: 600px;
			margin: 0 auto;
			overflow-y: scroll;
			margin-left: -600px;
		}
		.tdyys-content .closeBtn {
			width: 1160px;
		}
		.tdyys-content .closeBtn span {
			margin-right: 470px;
		}
		/*  中智诚等级  */
		.early {color: #529af5;}
		.in {color: #0674ff;}
		.high{color: #0559c3;}
		.zxinTable tr td {
			padding-left: 10px;
			line-height: 35px;
		}
		.zxinTable tr td:first-child,
		.zxinTable tr td:nth-child(4) {
			width: 80px;
		}


	</style>
	<script type="text/javascript">
		$("#openMoney").click(
			function() {
				if(confirm("确定仍要放款吗？"))
				{
					$.ajax({
						url:"${pageContext.request.contextPath}/back/backBorrowOrder/insistlending",
						method:'get',
						data:{
							"type": 0
						},
						success:function(status,data){
							alert("已完成");
						}
					});
				}
			}
		)
	</script>
</head>
<body>
<div class="pageContent">
	<div class="pageFormContent" layoutH="50" style="overflow: auto;">
		<!-- 借款信息 -->
		<fieldset>
			<legend>借款信息</legend>
			<table class="userTable">
				<tbody>
				<tr>
					<td style="font-weight: bold">借款详情</td>
					<td>
						<table class="userTable">
							<tr>
								<td class="tdGround" style="width: 180px;">用户ID:</td>
								<td>${user.id}</td>
								<td class="tdGround" colspan="2">订单号:</td>
								<td colspan="2">${borrow.outTradeNo}</td>
							</tr>
							<tr>
								<td class="tdGround" style="width: 180px;">借款期限:</td>
								<td>${borrow.loanTerm }天</td>
								<td class="tdGround" colspan="2">当前状态:</td>
								<td colspan="2">${borrow.statusName }</td>
							</tr>
							<tr>
								<td class="tdGround">服务费:</td>
								<td><fmt:formatNumber type="number"
													  value="${borrow.loanInterests/100}" pattern="0.00"
													  maxFractionDigits="2" />元</td>
								<td class="tdGround" colspan="2">服务费费率‰:</td>
								<td colspan="2"><fmt:formatNumber type="number"
																  value="${borrow.apr}" pattern="0.00" maxFractionDigits="2" /></td>
							</tr>
							<tr>

								<td class="tdGround" >申请时间:</td>
								<td ><fmt:formatDate
										value="${borrow.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td class="tdGround" colspan="2">还款时间:</td>
								<td colspan="2"><fmt:formatDate value="${borrow.loanEndTime}"
																pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							<tr>
								<td class="tdGround">总额度:</td>
								<td><fmt:formatNumber type="number"
													  value="${user.amountMax/100}" pattern="0.00"
													  maxFractionDigits="2" />元</td>
								<td class="tdGround" colspan="2">剩余额度</td>
								<td colspan="2"><fmt:formatNumber type="number"
																  value="${user.amountAvailable/100}" pattern="0.00"
																  maxFractionDigits="2" />元</td>
								<!-- 										<td class="tdGround">剩余额度</td> -->
								<%-- 										<td>${user.amountAvailable}</td> --%>
							</tr>
							<tr>
								<td class="tdGround">开户行</td>
								<td>${bankCard.bankName}</td>
								<td class="tdGround" colspan="2">借款银行卡卡号</td>
								<td>${borrow.cardNo}</td>
							</tr>
							<tr>
								<td class="tdGround">预留手机号码</td>
								<td colspan="4">${bankCard.phone}</td>
							</tr>
						</table>
					</td>
				</tr>
				</tbody>
			</table>
		</fieldset>
		<!-- 个人信息 -->
		<fieldset>
			<legend>个人信息</legend>
			<table class="userTable">
				<tbody>
				<tr>
					<td style="font-weight: bold">个人详情</td>
					<td>
						<table class="userTable">
							<tr>
								<td class="tdGround" style="width: 180px;">用户ID</td>
								<td>${user.id}</td>
								<td class="tdGround">姓名</td>
								<td>${user.realname}</td>
								<td class="tdGround">年龄</td>
								<td>${age}</td>
								<td class="tdGround">性别</td>
								<td>${user.userSex}</td>
							</tr>
							<tr>
								<td class="tdGround" style="width: 180px;">出生日期</td>
								<td>${birthday}</td>
								<td class="tdGround">身份证号</td>
								<td>${user.idNumber}</td>
								<td class="tdGround">联系方式</td>
								<td>${user.userPhone}</td>
								<td class="tdGround">婚姻</td>
								<td>${user.maritalStatus}</td>
							</tr>
							<tr>
								<td class="tdGround">常住地址</td>
								<td colspan="3">${user.presentAddress}
									${user.presentAddressDistinct}</td>
								<%--<td class="tdGround">学历：</td>--%>
								<%--<td>${user.education}</td>--%>

								<td class="tdGround">居住时长：</td>
								<td>${user.presentPeriod}</td>
								<td class="tdGround">账号创建时间</td>
								<td><fmt:formatDate value="${user.createTime}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							<tr>
								<td class="tdGround">QQ</td>
								<td>${user.qq}</td>
								<td class="tdGround">邮箱</td>
								<td>${user.email}</td>
								<td class="tdGround">行业</td>
								<td>${user.workIndustry}</td>
								<td class="tdGround">公司</td>
								<td>${user.companyName}</td>
							</tr>
							<tr>
								<td class="tdGround">公司地址</td>
								<td colspan="7">${user.companyAddress}</td>
							</tr>
							<tr class="identity">
								<%--<td class="tdGround" style="height: 100px; width: 180px;">学历证明:--%>
								<%--</td>--%>
								<%--<td><img src="" /></td>--%>
								<td class="tdGround" style="height: 100px;">身份证</td>
								<td colspan="2">
									<c:if test="${user.idcardImgZ!=null}">
										<img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgZ}"/>
									</c:if>
									<%--<img src="http://oyd863ahw.bkt.clouddn.com///mnt/img/qbm_04/04_77/04_77_868/20171026/20171026103514_1nbnx54bys_appTh.png " alt=""><br/>身份证正面--%>
								</td>
								<td colspan="2">
									<c:if test="${user.idcardImgF!=null}">
										<img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgF}" />
									</c:if>
									<%--<img src="http://oyd863ahw.bkt.clouddn.com///mnt/img/qbm_04/04_77/04_77_868/20171026/20171026103514_1nbnx54bys_appTh.png " alt=""><br/>身份证反面--%>
								</td>
								<td colspan="3">
									<c:if test="${user.headPortrait!=null}">
										<img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.headPortrait}"/>
									</c:if>
									<%--<img src="http://oyd863ahw.bkt.clouddn.com///mnt/img/qbm_04/04_77/04_77_868/20171026/20171026103514_1nbnx54bys_appTh.png " alt=""><br/>用户活体检测照片--%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="font-weight: bold">联系人</td>
					<td>
						<table class="userTable">
							<tr>
								<td class="tdGround" style="width: 180px;">关系</td>
								<td>${user.fristContactRelation}</td>
								<td class="tdGround">姓名</td>
								<td>${user.firstContactName}</td>
								<td class="tdGround">电话</td>
								<td>${user.firstContactPhone}</td>
								<td class="tdGround">来源</td>
								<td>系统上传</td>
							</tr>
							<tr>
								<td class="tdGround" style="width: 180px;">关系</td>
								<td>${user.secondContactRelation}</td>
								<td class="tdGround">姓名</td>
								<td>${user.secondContactName}</td>
								<td class="tdGround">电话</td>
								<td>${user.secondContactPhone}</td>
								<td class="tdGround">来源</td>
								<td>系统上传</td>
							</tr>
							<tr class="contact_num">
								<td class="tdGround" style="width: 180px;">通讯录分析</td>
								<td colspan="11" style="padding: 8px 0;">
									<a rel="tree161" id="contact-btn" class="contactBtn" href="javascript:;">手机通讯录联系人总个数：&nbsp;<span id="contactNum">${fn:length(contactList)}</span></a>
									<%--<a rel="tree161" href="javascript:;" id="tdYunYingShang-btn">同盾运营商数据</a>--%>
									<%--<span>运营商数据与通讯录号码撞库率：<b>--%>
											<%--${SJMHModel.sameRate}%--%>
									<%--</b></span>--%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="font-weight: bold">用户来源</td>
					<td>
						<table>
							<tr>
								<td style="text-indent: 10px;line-height: 38px;width: 50%">认证时地理位置：
									<span id="gaode_address"></span>
								</td>
								<td style="text-indent: 10px;">渠道商名称：
									<c:choose>
										<c:when test="${channelName != null and channelName != ''}">${channelName}</c:when>
										<c:when test="${inviteUser != null}">
											${inviteUser.realname}-${inviteUser.userPhone}
										</c:when>
										<c:otherwise>自然流量用户</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</tbody>
			</table>
			</table>
		</fieldset>
		<fieldset>
			<legend>征信查询</legend>
			<table class="userTable">
				<tbody>
				<%--<tr>--%>
					<%--<td style="font-weight: bold">征信详情</td>--%>
					<%--<td>--%>
						<%--<table class="zxinTable">--%>
							<%--<tr>--%>
								<%--<td>征信平台</td>--%>
								<%--<td>分数/结果</td>--%>
								<%--<td>命中/建议</td>--%>
								<%--<td></td>--%>
							<%--</tr>--%>
							<%--<%@include file="creditCommonPage.jsp"%>--%>
						<%--</table>--%>
					<%--</td>--%>
				<%--</tr>--%>
				<%@include file="historyList.jsp" %>
				</tbody>
			</table>
		</fieldset>
		<%--<%@include file="riskAdvice.jsp"%>--%>
		<fieldset>
			<legend>审核信息</legend>
			<table class="userTable">
				<tbody>
				<tr>
					<td style="font-weight:bold">初审</td>
					<td>
						<table class="userTable">
							<tr>
								<td class="tdGround" style="width:180px;">审核人:</td>
								<td style="width:180px;">${borrow.verifyTrialUser}</td>
								<td class="tdGround" style="width:180px;">审核时间:</td>
								<td style="width:180px;"><fmt:formatDate
										value="${borrow.verifyTrialTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							<tr>

								<td class="tdGround" style="width:180px;">审核备注:</td>
								<td colspan="3">
									<textarea class="textInput valid" rows="5" cols="100%"
											  readonly="readonly">${borrow.verifyTrialRemark}</textarea>
								</td>
							</tr>

						</table>
					</td>
				</tr>
				<tr>
					<td style="font-weight:bold">复审</td>
					<td>
						<table class="userTable">
							<tr>
								<td class="tdGround" style="width:180px;">审核人:</td>
								<td style="width:180px;">${borrow.verifyReviewUser}</td>
								<td class="tdGround" style="width:180px;">审核时间:</td>
								<td style="width:180px;"><fmt:formatDate
										value="${borrow.verifyReviewTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							<tr>

								<td class="tdGround" style="width:180px;">审核备注:</td>
								<td colspan="3">
									<textarea class="textInput valid" rows="5" cols="100%"
											  readonly="readonly">${borrow.verifyReviewRemark}</textarea>
								</td>
							</tr>

						</table>
					</td>
				</tr>
				<tr>
					<td style="font-weight:bold">放款审核</td>
					<td>
						<table class="userTable">
							<tr>
								<td class="tdGround" style="width:180px;">审核人:</td>
								<td style="width:180px;">${borrow.verifyLoanUser}</td>
								<td class="tdGround" style="width:180px;">审核时间:</td>
								<td style="width:180px;"><fmt:formatDate
										value="${borrow.verifyLoanTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							<tr>

								<td class="tdGround" style="width:180px;">审核备注:</td>
								<td colspan="3">
									<textarea class="textInput valid" rows="5" cols="100%"
											  readonly="readonly">${borrow.verifyLoanRemark}</textarea>
								</td>
							</tr>

						</table>
					</td>
				</tr>
				<tr>
					<td class="tdGround" style="width:180px;">排序风控返回系统分值:
					<c:if test="${score == null}">
						暂无分数
					</c:if>
						<c:if test="${score != null}">
							${score}
						</c:if>
					</td>
					<td style="width:180px;">
					<div class="buttonActive">
						<div class="buttonContent">
							<button id="openMoney">仍要放款</button>
						</div>
					</div>
				</td>
				</tr>
				</tbody>
			</table>
		</fieldset>
	</div>
</div>
<%@include file="popUpWindow.jsp"%>
</body>
</html>