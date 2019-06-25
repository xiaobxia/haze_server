<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/common/back";
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");
	java.util.Date currentTime = new java.util.Date();
	String date = formatter.format(currentTime);
%>
<c:set var="path" value="<%=path%>"></c:set>
<c:set var="basePath" value="<%=basePath%>"></c:set>
<!DOCTYPE html >
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- <meta http-equiv="X-UA-Compatible" content="IE=7" /> -->
	<!-- <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" /> -->
	<title>${APP_NAME}-运营管理系统</title>
	<link href="${basePath }/bootstrapdist/css/bootstrap.min.css" rel="stylesheet"
		  type="text/css" media="screen" />
	<link href="${basePath }/themes/default/style.css" rel="stylesheet"
		  type="text/css" media="screen" />
	<link href="${basePath }/themes/css/core.css" rel="stylesheet"
		  type="text/css" media="screen" />
	<link href="${basePath }/themes/css/print.css" rel="stylesheet"
		  type="text/css" media="print" />

	<link href="${basePath }/uploadify/css/uploadify.css" rel="stylesheet"
		  type="text/css" media="screen" />
	<link rel='icon' href='${basePath }/themes/default/images/admin.ico' type=‘image/x-ico’ />
	<!--[if IE]>
	<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->
	<style type="text/css">
		#header {
			height: 50px
		}

		#leftside,#container,#splitBar,#splitBarProxy {
			top: 0
		}
	</style>
	<style>
		.index-page-wrap div, .index-page-wrap span{
			line-height: 1.35;
		}
		.index-page-wrap {
			position: relative;
			padding: 1.2rem 4rem;
		}
		.index-page-wrap .left-main {
			display: inline-block;
			width: 59rem;
			vertical-align: top;
			margin-right: 10rem;
		}
		.index-page-wrap .right-main {
			display: inline-block;
			width: 24.6rem;
			vertical-align: top;
		}
		.main-title {
			margin-bottom: 2rem;
			font-size: 1.3rem;
		}
		.main-title span{
			font-size: 1.3rem;
		}
		.main-title img{
			height: 1.35rem;
			width: auto;
			margin-right: 1rem;
			vertical-align: bottom;
		}
		.circle-card {
			display: inline-block;
			margin-bottom: 2rem;
			box-sizing: border-box;
			padding: 1rem 1.5rem;
			background-color: #fff;
			box-shadow: 0 0 20px rgb(223, 223, 223);
			border-radius: 0.3rem;
			width: 28rem;
			height: 7.5rem;
		}
		.circle-card.left {
			margin-right: 2.5rem;
		}
		.circle-card .circle-wrap {
			display: inline-block;
			width: 5.5rem;
			height: 5.5rem;
		}
		.circle-card .circle-wrap canvas {
		}
		.circle-card .detail-wrap {
			display: inline-block;
			text-align: right;
			width: 19rem;
			vertical-align: top;
		}
		.circle-card .detail-wrap .number{
			font-size: 2.5rem;
			font-weight: 400;
			margin-bottom: 0.2rem;
		}
		.number.blue {
			color: #528DFF;
		}
		.number.green {
			color: #28C189;
		}
		.number.yellow {
			color: #FF9600;
		}
		.number.red {
			color: #F05D5D;
		}
		.circle-card .detail-wrap .text{
			font-size: 1.3rem;
			color: #7D7D7D;
		}

		.info-card {
			box-sizing: border-box;
			padding: 0.5rem 1.5rem;
			background-color: #fff;
			box-shadow: 0 0 20px rgb(223, 223, 223);
			border-radius: 0.4rem;
			width: 58.5rem;
			height: 13rem;
			font-size: 1.2rem;
			color: #888888;
		}
		.info-card .row{
			border-bottom: 1px solid rgba(223, 223, 223, 1);
		}
		.info-card .row:last-child{
			border-bottom: 0;
		}
		.info-card .info-item{
			box-sizing: border-box;
			display: inline-block;
			width: 23.5rem;
			height: 4rem;
			line-height: 4rem;
			position: relative;
		}
		.info-card .info-item span {
			font-size: 1.2rem;
			vertical-align: middle;
		}
		.info-card .info-item span:nth-of-type(3) {
			margin-top: 1rem;
			float: right;
		}
		.info-card .info-item .fix {
			position: absolute;
			left: 7.5rem;
			top: 1.3rem;
		}
		.info-card .info-item span:nth-of-type(2) {
			margin-left: 1.5rem;
		}
		.text-tag {
			color: #fff;
			padding: 0.25rem 0.5rem;
			border-radius: 0.2rem;
		}
		.text-tag.yellow-tag {
			background-color: rgba(255,150,0,1);
		}
		.text-tag.red-tag {
			background-color: #F85252;
		}
		.info-card .info-item.left{
			margin-right: 6rem;
		}
		.info-card .text-tag {

		}
		.notice-wrap {
			border: 0.15rem solid #528DFF;
			border-radius: 1rem;
		}
		.notice-wrap .title.title-active{
			background-color: #528DFF;
			border-radius: 0.5rem 0.8rem 0 0;
			padding: 1.2rem 1.5rem 1.2rem 3rem;
		}
		.notice-wrap .title.title-active-2{
			background-color: #75A4FF;
			border-radius: 0;
		}
		.notice-wrap .title{
			padding: 0.7rem 1.5rem 0.7rem 3rem;
			background-color: #89B1FF;
			color: #fff;
			font-size: 1.1rem;
			border-radius: 0 0 0.5rem 0.5rem;
			position: relative;
		}
		.notice-wrap .title .left{
			display: inline-block;
		}
		.notice-wrap .title .right{
			display: inline-block;
			float: right;
		}
		.notice-wrap .title * {
			font-size: 1.1rem;
		}
		.notice-wrap .title.title-active span:nth-of-type(3){
			float: right;
		}
		.notice-wrap .title.title-active span:nth-of-type(2){
			position: absolute;
			left: 12rem;
		}
		.notice-wrap .main-wrap {
			padding: 0 1.65rem 0 1.65rem;
			box-sizing: border-box;
			color: #4C4C4C;
		}
		.notice-wrap .item {
			box-sizing: border-box;
			padding: 0.5rem 0 0.5rem 0;
			display: inline-block;
			width: 8.5rem;
			border-bottom: 1px solid rgb(233,233,233);
			color: #4C4C4C;
			font-size: 1rem;
		}
		.notice-wrap .sub-item {
			box-sizing: border-box;
			padding: 0.8rem 0;
			display: inline-block;
			width: 8.5rem;
			border-bottom: 1px solid rgb(233,233,233);
			color: #888888;
			font-size: 0.9rem;
		}
		.notice-wrap .item.left {
			margin-right: 3rem;
		}
		.notice-wrap .sub-item.left {
			margin-right: 3rem;
		}
		.notice-wrap .item .icon {
			display: inline-block;
			margin-right: 0.5rem;
			vertical-align: top;
			height: 2.5rem;
			line-height: 2.5rem;
		}
		.notice-wrap .item .icon img{
			width: 1.1rem;
			height: 1.1rem;
			vertical-align: middle;
		}
		.notice-wrap .item .text {
			font-size: 1rem;
			display: inline-block;
		}
		.notice-wrap .item .text div {
			font-size: 1rem;
		}
		.notice-wrap .item .text .up-down-icon{
			margin-top: 0.3rem;
			float: right;
		}
		.notice-wrap .sub-item .text {
			margin-left: 2rem;
			font-size: 0.9rem;
		}
		.notice-wrap .sub-item .text div{
			font-size: 0.9rem;
		}
		.up-down-icon {
			margin-left: 0.6rem;
			width: 0.6rem;
			height: 0.6rem;
		}
	</style>
	<style>
		.logo-wrap {
			height: 56px;
			width: 100%;
			padding-left: 15px;
			line-height: 56px;
			box-sizing: border-box;
			color: #5a5e66;
            background-color: rgb(48, 65, 86)
		}
		.logo-wrap img {
			display: inline-block;
			width: auto;
			height: 50px;
			vertical-align: middle;
		}
		.header-wrap {
			height: 50px;
			width: 100%;
			box-sizing: border-box;
		}
		.aways-h {
			display: none !important;
		}
        .user-img {
            position: absolute;
            right: 140px;
            height: 40px;
            width: 40px;
            overflow: hidden;
            border-radius: 6px;
            top: 5px;
        }
        .user-img img {
            width: 100%;
            height: 100%;
        }
	</style>
	<script src="${basePath }/js/dwz.ui.js" type="text/javascript"></script>
	<script src="${basePath }/js/speedup.js" type="text/javascript"></script>
	<script src="${basePath }/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
	</script>
	<script src="${basePath }/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="${basePath }/js/jquery.validate.js" type="text/javascript"></script>
	<script src="${basePath }/js/jquery.bgiframe.js" type="text/javascript"></script>
	<script src="${basePath }/js/crm.upload.js" type="text/javascript"></script>
	<script src="${basePath }/js/dwz.min.js" type="text/javascript"></script>
	<script src="${basePath }/js/dwz.regional.zh.js" type="text/javascript"></script>
	<script src="${basePath }/js/util.js" type="text/javascript"></script>
	<script type="text/javascript"
			src="${basePath }/uploadify/scripts/swfobject.js"></script>
	<script type="text/javascript"
			src="${basePath }/uploadify/scripts/jquery.uploadify.min.js"></script>
	<script type="text/javascript"
			src="${basePath }/uploadify/scripts/jquery.uploadify.v2.1.0.js"></script>
	<script src="${basePath }/xheditor/xheditor-1.1.14-zh-cn.min.js"
			type="text/javascript"></script>
	<script src="${basePath }/js/user/dwzUtil.js" type="text/javascript"></script>
	<script type="text/javascript" src="${basePath }/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${basePath }/js/user/city.js"></script>
	<script type="text/javascript" src="${basePath }/bootstrapdist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".logo-wrap").click(function () {
				$('.navTab-tab .main').trigger('click')
			});
			$(document).bind("ajaxComplete", function(e,jqXHR, status){
				var sessionstatus =jqXHR.getResponseHeader("statusCode");// 通过XMLHttpRequest取得响应头，sessionstatus，
				todo(sessionstatus);
			});
			DWZ.init("<%=path%>/resources/dwz.frag.xml",
					{
						loginUrl : "login_dialog.html",
						loginTitle : "登录", // 弹出登录对话框
						//		loginUrl:"login.html",	// 跳到登录页面
						statusCode : {
							ok : 200,
							error : 300,
							timeout : 301
						}, //【可选】
						pageInfo : {
							pageNum : "pageNum",
							numPerPage : "numPerPage",
							orderField : "orderField",
							orderDirection : "orderDirection"
						}, //【可选】
						debug : false, // 调试模式 【true|false】
						callback : function() {
							initEnv();
							$("#themeList").theme({
								themeBase : "${basePath }/themes"});
						}
					});
			function initIndexPage(data) {
				function setAdaptive () {
					var baseFontSize = 20
					// 和width有关
					var winWidth = $('.index-page-wrap').innerWidth()
					var fontScale = 0.9 * winWidth / 1920
					document.documentElement.style.fontSize = (baseFontSize * fontScale) + 'px'
					window.adaptive = {
						winWidth: winWidth,
						fontSize: baseFontSize * fontScale
					}
				}
				setAdaptive()

				$('.myChart').attr('width', window.adaptive.fontSize * 110 /20)
				$('.myChart').attr('height', window.adaptive.fontSize * 110 /20)
				function formatNumToThree(str) {
					str = '' + (str|| 0)
					var newStr = "";
					var count = 0;
					// 当数字是整数
					if (str.indexOf(".") === -1) {
						for (var i = str.length - 1; i >= 0; i--) {
							if (count % 3 === 0 && count !== 0) {
								newStr = str.charAt(i) + "," + newStr;
							} else {
								newStr = str.charAt(i) + newStr;
							}
							count++;
						}
						str = newStr;
						return str;
					}
					// 当数字带有小数
					else {
						for (var j = str.indexOf(".") - 1; j >= 0; j--) {
							if (count % 3 === 0 && count !== 0) {
								newStr = str.charAt(j) + "," + newStr;
							} else {
								newStr = str.charAt(j) + newStr; //逐个字符相接起来
							}
							count++;
						}
						str = newStr + (str + "00").substr((str + "00").indexOf("."), 3);
						return str;
					}
				}

				(function($, window, undefined) {
					$.fn.ringChart = function(curr, total, numText, color) {
						var zoom = window.adaptive.fontSize / 20
						var canvasWH = zoom * 110/2
						var canvasHH = zoom * 110/2
						var lineW = zoom * 7
						var canvas = $(this).get(0);
						var constrast = parseFloat(curr/total).toFixed(2); //比例
						var context = null;
						if ( !canvas.getContext) {
							return;
						}
						// 定义开始点的大小
						var startArc = Math.PI*1.5;
						// 根据占的比例画圆弧
						var endArc = (Math.PI * 2) * constrast;
						context = canvas.getContext("2d");
						// 圆心文字
						context.font= (zoom * 26) + "px Arial";
						context.fillStyle = '#333333';
						context.textBaseline = 'middle';
						var text=numText;
						var tw=context.measureText(text).width;
						context.fillText(text,canvasWH-tw/2, canvasHH + (2 * zoom));
						// 绘制背景圆
						context.save();
						context.beginPath();
						context.strokeStyle = "#e7e7e7";
						context.lineWidth = lineW;
						context.arc(canvasWH, canvasHH, canvasWH - lineW, 0, Math.PI * 2, false);
						context.closePath();
						context.stroke();
						context.restore();
						// 若为百分零则不必再绘制比例圆
						if ( curr / total === 0) {
							return;
						}
						// 绘制比例圆
						context.save();
						context.beginPath();
						context.strokeStyle = color;
						context.lineWidth = lineW;
						context.arc(canvasWH, canvasHH, canvasWH - lineW, startArc, ((endArc+startArc)), false);
						context.stroke();
						context.restore();
					}
				})($, window);


				function renderChart (indexInfo) {
					// 累计放款笔数
					$('#ljfkje-c').ringChart(100, 100, indexInfo.allLoanCount, '#528DFF');
					// 已回款笔数
					$("#yhkje-c").ringChart(indexInfo.allRepayMoney, indexInfo.allLoanMoney, indexInfo.allRepayCount, '#28C189');
// 待回款笔数
					$('#dhkje-c').ringChart(indexInfo.allPendingRepayMoney, indexInfo.allLoanMoney, indexInfo.allRepaymentCount, '#FF9600');
// 未逾期待收笔数
					$('#wyqdsje-c').ringChart(indexInfo.allOverMoney, indexInfo.allLoanMoney, indexInfo.allOverCount, '#F05D5D');
				}

				function renderNumber(indexInfo) {
					// 累计放款金额
					$('#ljfkje-n').text('¥ ' + formatNumToThree(indexInfo.allLoanMoney))
					// 已回款金额
					$('#yhkje-n').text('¥ ' + formatNumToThree(indexInfo.allRepayMoney))
					// 待回款金额
					$('#dhkje-n').text('¥ ' + formatNumToThree(indexInfo.allPendingRepayMoney))
					// 未逾期待收金额
					$('#wyqdsje-n').text('¥ ' + formatNumToThree(indexInfo.allOverMoney))
					// 3天内到期
					$('#stndq-n').text('¥ ' + formatNumToThree(indexInfo.threeExpireMoney))
					// 放款失败金额
					$('#fksbje-n').text('¥ ' + formatNumToThree(indexInfo.failLoanMoney))
					// 放款失败笔数
					$('#fksbbs-n').text(formatNumToThree(indexInfo.failLoanCount))
					// 逾期1~3天
					$('#uqydst-n').text('¥ ' + formatNumToThree(indexInfo.s1Money))
					// 逾期3~7天
					$('#uqsdqt-n').text('¥ ' + formatNumToThree(indexInfo.s2Money))
					// 逾期7~15天
					$('#uqqdswt-n').text('¥ ' + formatNumToThree(indexInfo.s3Money))
					// 逾期15天以上
					$('#uqswtys-n').text('¥ ' + formatNumToThree(indexInfo.s4Money))


					// 总用户量
					$('#zyhl').text(formatNumToThree(indexInfo.allRegistCount))
					// 涨幅
					var zyhlImg = ''
					if (indexInfo.allRegistPercentage > 0) {
						zyhlImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头2.png">'
					}
					if (indexInfo.allRegistPercentage < 0) {
						zyhlImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头1.png">'
					}
					$('#zyhl-b').html((indexInfo.allRegistPercentage * 100).toFixed(2) + '%' + zyhlImg)
					// 当日注册用户数
					$('#drzcyhs').text(formatNumToThree(indexInfo.registCount))
					// 当日申请用户数
					$('#drsqyhs').text(formatNumToThree(indexInfo.applyCount))
					// 当日放款金额
					$('#drfkje').text('¥ ' + formatNumToThree(indexInfo.loanMoney))
					// 当日放款订单
					$('#drfkdd').text(formatNumToThree(indexInfo.loanCount))
					// 当日复借金额
					$('#drfjje-n').text('¥ ' + formatNumToThree(indexInfo.reBorrowMoney))
					// 当日复借订单
					$('#drfjdd').text(formatNumToThree(indexInfo.reBorrowCount))
					// 当日到期金额
					$('#drdqje').text('¥ ' + formatNumToThree(indexInfo.pendingRepayMoney))
					// 当日到期订单
					$('#drdqdd').text(formatNumToThree(indexInfo.pendingRepayCount))
					// 全额还款金额
					/*$('#qehkje').text('¥ ' + formatNumToThree(indexInfo.repyMoney))*/
					$('#qehkje').text('¥ ' + formatNumToThree(indexInfo.repayedMoney))
					// 全额还款订单
					$('#eqhkdd').text(formatNumToThree(indexInfo.repyCount))
					// 当日展期金额
					$('#drzqje').text('¥ ' + formatNumToThree(indexInfo.extendMoney))
					// 当日展期订单
					$('#drzqdd').text(formatNumToThree(indexInfo.extendCount))
					// 当日待收金额
					$('#drdsje').text('¥ ' + formatNumToThree(indexInfo.pendingMoney))
					// 当日待收订单
					$('#drdsdd').text(formatNumToThree(indexInfo.pendingCount))
					// 当日放款率
					var drfklImg = ''
					if (indexInfo.loanPercentage > 0) {
						drfklImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头2.png">'
					}
					if (indexInfo.loanPercentage < 0) {
						drfklImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头1.png">'
					}
					$('#drfkl').html((indexInfo.loanPercentage * 100).toFixed(2) + '%' + drfklImg)

					// 当日通过率
					var drtglImg = ''
					if (indexInfo.passPercentage > 0) {
						drtglImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头2.png">'
					}
					if (indexInfo.passPercentage < 0) {
						drtglImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头1.png">'
					}
					$('#drtgl').html((indexInfo.passPercentage * 100).toFixed(2) + '%' + drtglImg)
					// 当日回款率
					var drhklImg = ''
					if (indexInfo.repayPercentage > 0) {
						drhklImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头2.png">'
					}
					if (indexInfo.repayPercentage < 0) {
						drhklImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头1.png">'
					}
					$('#drhkl').html((indexInfo.repayPercentage * 100).toFixed(2) + '%' + drhklImg)
					var drfjlImg = ''
					if (indexInfo.repayPercentage > 0) {
						drfjlImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头2.png">'
					}
					if (indexInfo.repayPercentage < 0) {
						drfjlImg = '<img class="up-down-icon" src="${basePath }/images/shouyeimg/箭头1.png">'
					}
					$('#drfjlImg').html((indexInfo.reBorrowReate * 100).toFixed(2) + '%' + drfjlImg)
				}

				renderNumber(data.indexInfo || {})

				renderChart(data.indexInfo || {})
			}
			$.getJSON("summary/summaryBorrow?type=riskToday", function(data) {
				console.log(data)
				setTimeout(function () {
					initIndexPage(data)
					$(window).resize(function () {
						console.log('窗口大小变化')
						initIndexPage(data)
					})
				}, 100)
			});
		});
		function renderLoanStatusName() {
			$('.loanStatusName').each(function () {
				var text = $(this).text().trim();
				var color = ''
				var textColorMap = {
					'正常已还款': '#1E6BFF',
					'逾期已还款': '#6298FF',
					'初审通过待复审': '#28C189',
					'复审通过待还款审核': '#10AA72',
					'待AI验证': '#02945E',
					'放款中': '#008554',
					'扣款中': '#7899D9',
					'待机审': '#FF9600',
					'已逾期': '#FF9600'
				}
				if (text.indexOf('驳回') !== -1) {
					color = '#313743'
				} else if (text.indexOf('失败') !== -1) {
					color = '#F85252'
				} else if (text.indexOf('成功') !== -1) {
					color = '#008554'
				} else if (text.indexOf('通过') !== -1) {
					color = '#02945E'
				} else if (text.indexOf('待还款') !== -1) {
					color = '#008554'
				} else {
					color = textColorMap[text]
				}
				color = color || '#aaa'
				$(this).html('<div class="status-tag" style="background-color:' + color + ';"><span>' + text + '</span></div>')
				$(this).attr('style', '')
			})
			$('.loanStatusTitle').each(function () {
				$(this).attr('style', '')
			})
		}

		function renderLoanSuccessCount() {
			$('.loanSuccessCount').each(function () {
				var text = $(this).text().trim();
				var color = '#FF9000'
				if (text.indexOf('首借') !== -1) {
					color = '#28C189'
				}
				$(this).html('<div class="status-tag" style="background-color:' + color + ';"><span>' + text + '</span></div>')
				$(this).attr('style', '')
			})
			$('.loanSuccessCountTitle').each(function () {
				$(this).attr('style', '')
			})
		}
	</script>
</head>
<body scroll="no">
<div id="layout">
	<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse">
					<div></div>
				</div>
			</div>
		</div>

		<div id="sidebar">
			<div class="logo-wrap">
				<img src="${basePath }/images/logo.png" alt="">
			</div>
			<div class="accordion" fillSpace="sideBar">
				<c:forEach items="${subMenu}" var="item" varStatus="count">
					<div class="accordionHeader">
						<c:if test="${ count.index==0}">
							<h2 class="">
								<img src="${basePath }/images/menu/${item.moduleName }.png" alt="">
								<span>icon</span>${item.moduleName }
							</h2>
						</c:if>
						<c:if test="${ count.index>0}">
							<h2 class="">
								<img src="${basePath }/images/menu/${item.moduleName }.png" alt="">
								<span>icon</span>${item.moduleName }
							</h2>
						</c:if>
					</div>

					<div class="accordionContent">
						<ul class="tree treeFolder">
							<c:forEach items="${item.moduleList}" var="rightSubList">
								<c:if test="${fn:contains(rightSubList.moduleUrl,'?')}">
									<c:if test="${fn:contains(rightSubList.moduleUrl,'myId')}">
										<c:set var="startIndx"
											   value="${fn:indexOf(rightSubList.moduleUrl,'myId')}"></c:set>
										<c:set var="urlLength"
											   value="${fn:length(rightSubList.moduleUrl)}"></c:set>
										<li><a href="${rightSubList.moduleUrl}" target="navTab"
											   rel="${fn:substring(rightSubList.moduleUrl,startIndx+5,urlLength)}">${rightSubList.moduleName}</a></li>
									</c:if>
									<c:if
											test="${!fn:contains(rightSubList.moduleUrl,'myId')}">
										<li><a
												href="${rightSubList.moduleUrl}&myId=${rightSubList.id}"
												target="navTab" rel="${rightSubList.id}">${rightSubList.moduleName}</a></li>
									</c:if>
								</c:if>
								<c:if test="${!fn:contains(rightSubList.moduleUrl,'?')}">
									<li><a
											href="${rightSubList.moduleUrl}?myId=${rightSubList.id}"
											target="navTab" rel="${rightSubList.id}">${rightSubList.moduleName}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<div id="container">
		<div id="header">
			<div class="header-wrap">
				<div class="headerNav">
					<div class="user-img">
						<img src="${basePath }/images/f778738c-e4f8-4870-b634-56703b4acafe.gif" alt="">
					</div>
					<ul class="nav nav-pills">
						<li role="presentation" class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
								${BACK_USER.userAccount} <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<c:if test="${BACK_USER.id eq 10000}">
									<li><a
											href="updateCache" target="ajaxTodo">刷新系统缓存</a></li>
								</c:if>
								<li><a
										href="user/updateUserPassword?type=toJsp" target="dialog" mask="true"
										width="600">修改密码</a></li>
								<li><a href="logout">退出</a></li>
							</ul>
						</li>
					</ul>
				</div>
				<%--<div id="navMenu">
                    <ul>
                        <c:forEach items="${menuModuleList}" var="item" varStatus="count">
                            <li <c:if test="${count.count==1}">  class="selected" </c:if> >
                                <a href="${item.moduleUrl}?myId=${item.id}">
                                    <span>${item.moduleName}</span>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>--%>
			</div>
		</div>
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent">
					<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:;"><span><span
								class="home_icon"><span class="glyphicon glyphicon-home"></span></span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft"><span class="glyphicon glyphicon-menu-left"></span></div>
				<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight"><span class="glyphicon glyphicon-menu-right"></span></div>
				<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:;"><span class="glyphicon glyphicon-home"></span></a></li>
			</ul>
			<div style="overflow: scroll" class="navTab-panel tabsPageContent layoutBox">
				<div style="display: block;" class="page unitBox">
					<div class="accountInfo" style="height: 35px;">
						<div class="right">
							<p><%=date%></p>
						</div>
						<p>
							<span>欢迎,${BACK_USER.userAccount}</span>
						</p>
					</div>
					<div style="font-size: 16px" class="index-page-wrap pageFormContent">
						<div class="left-main">
							<div class="main-title">
								<img src="${basePath }/images/shouyeimg/总体概况.png" alt="">
								<span>总体概况</span>
							</div>
							<div class="circle-card left">
								<div class="circle-wrap">
									<canvas class="myChart" id="ljfkje-c"></canvas>
								</div>
								<div class="detail-wrap">
									<div id="ljfkje-n" class="number blue number-text">--</div>
									<div class="text">累计放款金额/笔数</div>
								</div>
							</div>
							<div class="circle-card">
								<div class="circle-wrap">
									<canvas class="myChart" id="yhkje-c"></canvas>
								</div>
								<div class="detail-wrap">
									<div id="yhkje-n" class="number green number-text">--</div>
									<div class="text">已回款金额/笔数</div>
								</div>
							</div>
							<div class="circle-card left">
								<div class="circle-wrap">
									<canvas class="myChart" id="dhkje-c"></canvas>
								</div>
								<div class="detail-wrap">
									<div id="dhkje-n" class="number yellow number-text">--</div>
									<div class="text">待回款金额/笔数</div>
								</div>
							</div>
							<div class="circle-card">
								<div class="circle-wrap">
									<canvas class="myChart" id="wyqdsje-c"></canvas>
								</div>
								<div class="detail-wrap">
									<div id="wyqdsje-n" class="number red number-text">--</div>
									<div class="text">未逾期待收金额/笔数</div>
								</div>
							</div>
							<div class="main-title" style="margin-top: 30px">
								<img src="${basePath }/images/shouyeimg/逾期数据.png" alt="">
								<span>逾期数据</span>
							</div>
							<div class="info-card">
								<div class="row">
									<div class="info-item left">
										<span>3天内到期</span>
										<span id="stndq-n" class="fix number-text">--</span>
										<span class="yellow-tag text-tag">即将到期</span>
									</div>
									<div class="info-item">
										<span>放款失败金额/笔数</span>
										<span id="fksbje-n" class="number-text">--</span>
										<span id="fksbbs-n" class="number-text">--</span>
									</div>
								</div>
								<div class="row">
									<div class="info-item left">
										<span>逾期1~3天</span>
										<span id="uqydst-n" class="fix number-text">--</span>
										<span class="red-tag text-tag">逾期</span>
									</div>
									<div class="info-item">
										<span>逾期4~7天</span>
										<span id="uqsdqt-n" class="fix number-text">--</span>
										<span class="red-tag text-tag">逾期</span>
									</div>
								</div>
								<div class="row">
									<div class="info-item left">
										<span>逾期8~15天</span>
										<span id="uqqdswt-n" class="fix number-text">--</span>
										<span class="red-tag text-tag">逾期</span>
									</div>
									<div class="info-item">
										<span>逾期15天以上</span>
										<span id="uqswtys-n" class="fix number-text">--</span>
										<span class="red-tag text-tag">逾期</span>
									</div>
								</div>
							</div>
						</div>
						<div class="right-main">
							<div class="main-title">
								<img src="${basePath }/images/shouyeimg/当日运营数据.png" alt="">
								<span>当日运营数据</span>
							</div>
							<div class="notice-wrap">
								<div class="title title-active">
									<span>总用户量</span>
									<span id="zyhl" class="number-text">--</span>
									<span id="zyhl-b">--</span>
								</div>
								<div class="title title-active-2">
									<div class="left">
										<div>当日注册用户数</div>
										<div id="drzcyhs" class="number-text">--</div>
									</div>
									<div class="right">
										<div>当日申请用户数</div>
										<div id="drsqyhs" class="number-text">--</div>
									</div>
								</div>
								<div class="title">
									<div class="left">
										<div>当日放款金额</div>
										<div id="drfkje" class="number-text">--</div>
									</div>
									<div class="right">
										<div>当日放款订单</div>
										<div id="drfkdd" class="number-text">--</div>
									</div>
								</div>
								<div class="main-wrap">
									<div class="item left">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日放款金额.png" alt="">
										</div>
										<div class="text">
											<div>当日复借金额</div>
											<div id="drfjje-n" class="number-text">--</div>
										</div>
									</div>
									<div class="item">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日放款订单.png" alt="">
										</div>
										<div class="text">
											<div>当日复借订单</div>
											<div id="drfjdd" class="number-text">--</div>
										</div>
									</div>
									<div class="item left">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日到期金额.png" alt="">
										</div>
										<div class="text">
											<div>当日到期金额</div>
											<div id="drdqje" class="number-text">--</div>
										</div>
									</div>
									<div class="item">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日到期订单.png" alt="">
										</div>
										<div class="text">
											<div>当日到期订单</div>
											<div id="drdqdd" class="number-text">--</div>
										</div>
									</div>
									<div class="sub-item left">
										<div class="text">
											<div>全额还款金额</div>
											<div id="qehkje" class="number-text">--</div>
										</div>
									</div>
									<div class="sub-item">
										<div class="text">
											<div>全额还款订单</div>
											<div id="eqhkdd" class="number-text">--</div>
										</div>
									</div>
									<div class="sub-item left">
										<div class="text">
											<div>展期金额</div>
											<div id="drzqje" class="number-text">--</div>
										</div>
									</div>
									<div class="sub-item">
										<div class="text">
											<div>展期订单</div>
											<div id="drzqdd" class="number-text">--</div>
										</div>
									</div>
									<div class="sub-item left">
										<div class="text">
											<div>待收金额</div>
											<div id="drdsje" class="number-text">--</div>
										</div>
									</div>
									<div class="sub-item">
										<div class="text">
											<div>待收订单</div>
											<div id="drdsdd" class="number-text">--</div>
										</div>
									</div>
									<div class="item left">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日放款率.png" alt="">
										</div>
										<div class="text">
											<div>当日放款率</div>
											<div id="drfkl" class="number-text">--</div>
										</div>
									</div>
									<div class="item">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日通过率.png" alt="">
										</div>
										<div class="text">
											<div>当日通过率</div>
											<div id="drtgl" class="number-text">--</div>
										</div>
									</div>
									<div class="item left">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日回款率.png" alt="">
										</div>
										<div class="text">
											<div>当日回款率</div>
											<div id="drhkl" class="number-text">--</div>
										</div>
									</div>
									<div class="item">
										<div class="icon">
											<img src="${basePath }/images/shouyeimg/当日复借率.png" alt="">
										</div>
										<div class="text">
											<div>当日复借率</div>
											<div id="drfjlImg" class="number-text">--</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<script type="text/javascript">

</script>
</body>
</html>
