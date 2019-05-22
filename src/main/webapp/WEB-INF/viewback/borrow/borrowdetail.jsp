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
	<style type="text/css">
		.detail-card {
			position: relative;
			margin-bottom: 2.4444444444444446rem;
			padding: 1.6666666666666667rem 1.1111111111111112rem;
			border-radius:0.5555555555555556rem;
			box-shadow: 0 0 1.1111111111111112rem rgba(215,230,245,1);
		}
		.detail-card .title-wrap {
			height: 2.7777777777777777rem;
			line-height: 2.7777777777777777rem;
			margin-bottom: 1.6666666666666667rem;
		}
		.detail-card .title {
			display: inline-block;
			border-bottom: 1px solid #1E6BFF;
			vertical-align: middle;
			height: 2.7777777777777777rem;
			line-height: 2.7777777777777777rem;
		}
		.detail-card .title .main-text {
			font-size: 1.2222222222222223rem;
			color: #1E6BFF;
			margin: 0 0 0 0.5555555555555556rem;
		}
		.detail-card .title .sub-text {
			font-size: 1rem;
			color: #151926;
			margin-left: 1.9444444444444444rem;
		}
		.detail-card .title img {
			width: 2.2222222222222223rem;
			height: 2.2222222222222223rem;
			display: inline-block;
			vertical-align: middle;
		}
		.detail-card .sub-title {
			margin: 1.6666666666666667rem 0;
			color: #1E6BFF;
			font-size: 1.2222222222222223rem;
		}
		.detail-card .sub-block {
		}
		.detail-card .force-loan {
			float: right;
			height: 2.7777777777777777rem;
			line-height: 2.7777777777777777rem;
		}
		.detail-card .force-loan .text {
			color: #FF0000;
			font-size: 1rem;
		}
		.detail-card .force-loan .btn {
			margin-left: 1.6666666666666667rem;
			padding: 0.5555555555555556rem 1.1111111111111112rem;
			color: #FF0000;
			background:rgba(250,189,189,1);
			border:1px solid rgba(255,0,0,1);
			border-radius:0.2222222222222222rem;
			cursor: pointer;
		}
		.detail-card .block-row {
			padding-bottom: 2.2222222222222223rem;
			border-bottom: 1px solid #C4C4C4;
			position: relative;
		}
		.view-detail-btn {
			padding: 0.5555555555555556rem 1.1111111111111112rem;
			color: #00832E;
			background:#A5D3B5;
			border:1px solid #00832E;
			border-radius:0.2222222222222222rem;
			cursor: pointer;
		}
		.blue-click-btn {
			padding: 0.5555555555555556rem 1.1111111111111112rem;
			color: #1E6BFF!important;
			background:#C8DEF4;
			border:1px solid #1E6BFF;
			border-radius:0.2222222222222222rem;
			cursor: pointer;
		}
		.blue-click-btn a {
			color: #1E6BFF!important;
		}
		.sh-card {
			display: inline-block;
		}
		.sh-card .text-block {
			display: inline-block;
			box-sizing: border-box;
			vertical-align: top;
			text-align: center;
			width: 10rem;
			margin-right: 2.2222222222222223rem;
		}
		.sh-card .bz-card {
			display: inline-block;
			box-sizing: border-box;
			padding: 1rem 1.1111111111111112rem;
			width:22.22222222222222rem;
			background:rgba(227,237,248,1);
			border-radius:0.5555555555555556rem;
			vertical-align: top;
		}
		.sh-card .bz-card h4 {
			margin: 0 0 0.5555555555555556rem 0;
			font-size: 0.8888888888888888rem;
			color: #000;
			font-weight: 600;
		}
		.sh-card .bz-card p {
			margin: 0;
			min-height: 1em;
			font-size: 0.8888888888888888rem;
		}
		.sh-card .text-block .text-title {
			color: #000;
			font-size: 0.8888888888888888rem;
			margin-bottom: 2.2222222222222223rem;
		}
		.sh-card .text-block .text-text {
			color: #001E71;
			font-size: 0.8888888888888888rem;
		}
		.sh-card.right {
			position: absolute;
			right: 0;
		}
		.imgs-block {
			margin-top: 1.6666666666666667rem;
			text-align: center;
		}
		.id-img-wrap {
			display: inline-block;
			margin-right: 11.11111111111111rem;
			width:20rem;
			height:9.277777777777779rem;
			background:rgba(227,237,248,1);
			border:1px solid rgba(215,230,245,1);
			border-radius:0.5555555555555556rem;
			overflow: hidden;
		}
		.id-img-wrap:last-child {
			margin-right: 0;
		}
		.id-img-wrap img {
			width: 100%;
			height: 100%;
		}

		.detail-table {
			width: 100%;
		}
		.detail-table th, .detail-table td{
			font-size: 0.8888888888888888rem;
			padding: 0.6666666666666666rem 0;
			min-width: 0;
			box-sizing: border-box;
			text-overflow: ellipsis;
			vertical-align: middle;
			position: relative;
			text-align: center;
		}
		.detail-table td{
			padding: 0.6666666666666666rem 0;
			min-width: 0;
			box-sizing: border-box;
			text-overflow: ellipsis;
			vertical-align: middle;
			position: relative;
			text-align: center;
			color: #001E71;
		}
		.half-block {
			width: 49%;
			display: inline-block;
			vertical-align: top;
		}
	</style>
	<script type="text/javascript">
		var borrowId =document.getElementById('borrowId').value;
		console.log(borrowId);
		$("#openMoney").click(function() {
				if(confirm("确定仍要放款吗？"))
				{
					$.ajax({
						url:"${pageContext.request.contextPath}/back/backBorrowOrder/insistlending",
						method:'get',
						data:{
							"type": 0,
							"borrowId":borrowId
						},
						success:function(status,data){
							if (status == 'success')
								alert("已完成");
							else
								alert(status);
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
		<input input="text" style="display:none"  value="${borrow.id}" id="borrowId">
		<div style="position: relative">
			<div class="detail-card">
				<div class="title-wrap">
					<div class="title">
						<img src="${basePath }/images/loandetail/借款信息.png" alt="">
						<span class="main-text">借款信息</span>
						<span class="sub-text">(订单号:${borrow.outTradeNo})</span>
					</div>
					<div class="force-loan">
						<span class="text">风控系统返回分值：<c:if test="${score == null}">
							暂无分数
						</c:if>
						<c:if test="${score != null}">
							${score}
						</c:if></span>
						<span class="btn" id="openMoney">仍要放款</span>
					</div>
				</div>
				<div class="content">
					<table class="detail-table">
						<tr>
							<th>借款期限</th>
							<th>总额度</th>
							<th>剩余额度</th>
							<th>服务费(元)</th>
							<th>服务费费率‰</th>
							<th>当前状态</th>
							<th>申请时间</th>
							<th>还款时间</th>
							<th>开户行</th>
							<th>借款银行卡卡号</th>
							<th>预留手机号码</th>
						</tr>
						<tr>
							<td>${borrow.loanTerm }天</td>
							<td><fmt:formatNumber type="number"
												  value="${user.amountMax/100}" pattern="0.00"
												  maxFractionDigits="2" />元</td>
							<td><fmt:formatNumber type="number"
												  value="${user.amountAvailable/100}" pattern="0.00"
												  maxFractionDigits="2" />元</td>
							<td><fmt:formatNumber type="number"
												  value="${borrow.loanInterests/100}" pattern="0.00"
												  maxFractionDigits="2" />元</td>
							<td><fmt:formatNumber type="number"
												  value="${borrow.apr}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>${borrow.statusName}</td>
							<td><fmt:formatDate
									value="${borrow.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${borrow.loanEndTime}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${bankCard.bankName}</td>
							<td>${borrow.cardNo}</td>
							<td>${bankCard.phone}</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="detail-card">
				<div class="title-wrap">
					<div class="title">
						<img src="${basePath }/images/loandetail/个人信息.png" alt="">
						<span class="main-text">个人信息</span>
						<span class="sub-text">(账号创建时间:<fmt:formatDate value="${user.createTime}"
																	   pattern="yyyy-MM-dd HH:mm:ss" />)</span>
					</div>
				</div>
				<div class="block-row">
					<div class="sub-block">
						<div class="sub-title">
							1）个人详情
						</div>
						<div class="content">
							<table class="detail-table">
								<tr>
									<th>用户ID</th>
									<th>姓名</th>
									<th>年龄</th>
									<th>性别</th>
									<th>出生日期</th>
									<th>身份证号</th>
									<th>联系方式</th>
									<th>婚姻</th>
									<th>行业</th>
									<th>QQ</th>
									<th>邮箱</th>
								</tr>
								<tr>
									<td>${user.id}</td>
									<td>${user.realname}</td>
									<td>${age}</td>
									<td>${user.userSex}</td>
									<td>${birthday}</td>
									<td>${user.idNumber}</td>
									<td>${user.userPhone}</td>
									<td>${user.maritalStatus}</td>
									<td>${user.workIndustry}</td>
									<td>${user.qq}</td>
									<td>${user.email}</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="block-row">
					<div class="sub-block">
						<div class="sub-title">
							2）地址身份证
						</div>
						<div class="content">
							<table class="detail-table">
								<tr>
									<th>常住地址</th>
									<th>居住时长</th>
									<th>公司</th>
									<th>公司地址</th>
								</tr>
								<tr>
									<td>${user.presentAddress}
										${user.presentAddressDistinct}</td>
									<td>${user.presentPeriod}</td>
									<td>${user.companyName}</td>
									<td>${user.companyAddress}</td>
								</tr>
							</table>
							<div class="imgs-block">
								<div class="id-img-wrap">
									<c:if test="${user.idcardImgZ!=null}">
										<img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgZ}"/>
									</c:if>
								</div>
								<div class="id-img-wrap">
									<c:if test="${user.idcardImgF!=null}">
										<img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgF}" />
									</c:if>
								</div>
								<div class="id-img-wrap">
									<c:if test="${user.headPortrait!=null}">
										<img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.headPortrait}"/>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="block-row">
					<div class="half-block">
						<div class="sub-block">
							<div class="sub-title">
								3）联系人
							</div>
							<div class="content">
								<table class="detail-table">
									<tr>
										<th>关系</th>
										<th>姓名</th>
										<th>电话</th>
										<th>来源</th>
										<th></th>
									</tr>
									<tr>
										<td>${user.fristContactRelation}</td>
										<td>${user.firstContactName}</td>
										<td>${user.firstContactPhone}</td>
										<td>系统上传</td>
										<td><a rel="tree161" id="contact-btn" class="contactBtn blue-click-btn" href="javascript:;">手机通讯录联系人总个数：&nbsp;<span id="contactNum">${fn:length(contactList)}</span></a></td>
									</tr>
									<tr>
										<td>${user.secondContactRelation}</td>
										<td>${user.secondContactName}</td>
										<td>${user.secondContactPhone}</td>
										<td>系统上传</td>
										<td></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="half-block">
						<div class="sub-block">
							<div class="sub-title">
								4）用户来源
							</div>
							<div class="content">
								<table class="detail-table left-text">
									<tr>
										<th>认证时地理位置</th>
										<th>渠道商名称</th>
									</tr>
									<tr>
										<td><span class="blue-click-btn" id="gaode_address"></span></td>
										<td><c:choose>
											<c:when test="${channelName != null and channelName != ''}">${channelName}</c:when>
											<c:when test="${inviteUser != null}">
												${inviteUser.realname}-${inviteUser.userPhone}
											</c:when>
											<c:otherwise>自然流量用户</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="detail-card">
				<div class="title-wrap">
					<div class="title">
						<img src="${basePath }/images/loandetail/征信查询.png" alt="">
						<span class="main-text">征信查询</span>
						<span class="sub-text">(订单号:${borrow.outTradeNo})</span>
					</div>
				</div>
				<div class="content">
					<%@include file="historyList_new.jsp" %>
				</div>
			</div>
			<div class="detail-card">
				<div class="title-wrap">
					<div class="title">
						<img src="${basePath }/images/loandetail/审核信息.png" alt="">
						<span class="main-text">审核信息</span>
					</div>
				</div>
				<div class="block-row">
					<div class="sh-card right">
						<div class="sub-block">
							<div class="sub-title">
								2）复审
							</div>
							<div class="content">
								<div class="text-block">
									<div class="text-title">审核人</div>
									<div class="text-text">${borrow.verifyReviewUser}</div>
								</div>
								<div class="text-block">
									<div class="text-title">审核时间</div>
									<div class="text-text"><fmt:formatDate
											value="${borrow.verifyReviewTime}"
											pattern="yyyy-MM-dd HH:mm:ss" /></div>
								</div>
								<div class="bz-card">
									<h4>审核备注:</h4>
									<p>${borrow.verifyReviewRemark}</p>
								</div>
							</div>
						</div>
					</div>
					<div class="sh-card">
						<div class="sub-block">
							<div class="sub-title">
								1）初审
							</div>
							<div class="content">
								<div class="text-block">
									<div class="text-title">审核人</div>
									<div class="text-text">${borrow.verifyTrialUser}</div>
								</div>
								<div class="text-block">
									<div class="text-title">审核时间</div>
									<div class="text-text"><fmt:formatDate
											value="${borrow.verifyTrialTime}"
											pattern="yyyy-MM-dd HH:mm:ss" /></div>
								</div>
								<div class="bz-card">
									<h4>审核备注:</h4>
									<p>${borrow.verifyTrialRemark}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="block-row">
					<div class="sh-card">
						<div class="sub-block">
							<div class="sub-title">
								3）放款审核
							</div>
							<div class="content">
								<div class="text-block">
									<div class="text-title">审核人</div>
									<div class="text-text">${borrow.verifyLoanUser}</div>
								</div>
								<div class="text-block">
									<div class="text-title">审核时间</div>
									<div class="text-text"><fmt:formatDate
											value="${borrow.verifyLoanTime}"
											pattern="yyyy-MM-dd HH:mm:ss" /></div>
								</div>
								<div class="bz-card">
									<h4>审核备注:</h4>
									<p>${borrow.verifyLoanRemark}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="popUpWindow.jsp"%>
</body>
</html>