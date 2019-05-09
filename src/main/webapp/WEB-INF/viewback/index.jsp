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
	<link href="${basePath }/themes/default/style.css" rel="stylesheet"
		  type="text/css" media="screen" />
	<link href="${basePath }/themes/css/core.css" rel="stylesheet"
		  type="text/css" media="screen" />
	<link href="${basePath }/themes/css/print.css" rel="stylesheet"
		  type="text/css" media="print" />

	<link href="${basePath }/uploadify/css/uploadify.css" rel="stylesheet"
		  type="text/css" media="screen" />
	<link rel='icon' href='<%=path %>/admin-favicon.ico' type=‘image/x-ico’ />
	<!--[if IE]>
	<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->
	<style type="text/css">
		#header {
			height: 85px
		}

		#leftside,#container,#splitBar,#splitBarProxy {
			top: 90px
		}
	</style>
	<style>
		.index-page-wrap {
			position: relative;
			margin: auto;
			width: 1100px;
			padding: 26px 20px;
		}
		.index-page-wrap *{
			font-size: 16px;
		}
		.index-page-wrap .left-main {
			display: inline-block;
			width:700px;
			vertical-align: top;
			margin-right: 60px;
		}
		.index-page-wrap .right-main {
			display: inline-block;
			width:330px;
			vertical-align: top;
		}
		.main-title {
			margin-bottom: 26px;
			font-size: 18px;
		}
		.main-title img{
			height: 18px;
			width: auto;
			margin-right: 10px;
			vertical-align: bottom;
		}
		.circle-card {
			display: inline-block;
			margin-bottom: 26px;
			box-sizing: border-box;
			padding: 13px 20px;
			background-color: #fff;
			box-shadow: 0 0 20px rgb(223, 223, 223);
			border-radius: 4px;
			width:330px;
			height:100px;
		}
		.circle-card.left {
			margin-right: 33px;
		}
		.circle-card .circle-wrap {
			display: inline-block;
			width: 74px;
			height: 74px;
		}
		.circle-card .detail-wrap {
			display: inline-block;
			text-align: right;
			width: 210px;
			vertical-align: top;
		}
		.circle-card .detail-wrap .number{
			font-size: 30px;
			font-weight:400;
			margin-bottom: 5px;
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
			font-size: 18px;
			color: #7D7D7D;
		}

		.info-card {
			box-sizing: border-box;
			padding: 0 20px;
			background-color: #fff;
			box-shadow: 0 0 20px rgb(223, 223, 223);
			border-radius: 5px;
			width:700px;
			height:172px;
			font-size: 16px;
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
			padding: 15px 0;
			width: 300px;
			height: 58px;
			position: relative;
		}
		.info-card .info-item span:nth-of-type(3) {
			float: right;
		}
		.info-card .info-item .fix {
			position: absolute;
			left: 80px;
		}
		.info-card .info-item span:nth-of-type(2) {
			margin-left: 30px;
		}
		.text-tag {
			color: #fff;
			padding: 3px 10px;
			border-radius:3px;
		}
		.text-tag.yellow-tag {
			background-color: rgba(255,150,0,1);
		}
		.text-tag.red-tag {
			background-color: #F85252;
		}
		.info-card .info-item.left{
			margin-right: 50px;
		}
		.info-card .text-tag {

		}
		.notice-wrap {
			border: 2px solid #528DFF;
			border-radius: 8px;
		}
		.notice-wrap .title.title-active{
			background-color: #528DFF;
			border-radius: 6px 6px 0 0;
		}
		.notice-wrap .title.title-active-2{
			background-color: #75A4FF;
			border-radius: 0;
		}
		.notice-wrap .title{
			padding: 20px 26px;
			background-color: #89B1FF;
			color: #fff;
			font-size: 15px;
			border-radius: 0 0 6px 6px;
			position: relative;
		}
		.notice-wrap .title .left{
			display: inline-block;
		}
		.notice-wrap .title .right{
			display: inline-block;
			float: right;
		}
		.notice-wrap .title.title-active span:nth-of-type(3){
			float: right;
		}
		.notice-wrap .title.title-active span:nth-of-type(2){
			position: absolute;
			left: 180px;
		}
		.notice-wrap .main-wrap {
			padding: 0 24px 50px 24px;
			box-sizing: border-box;
			color: #4C4C4C;
		}
		.notice-wrap .item {
			box-sizing: border-box;
			padding: 12px 0;
			display: inline-block;
			width: 116px;
			border-bottom: 1px solid rgb(233,233,233);
			color: #4C4C4C;
			font-size: 12px;
		}
		.notice-wrap .sub-item {
			box-sizing: border-box;
			padding: 12px 0;
			display: inline-block;
			width: 116px;
			border-bottom: 1px solid rgb(233,233,233);
			color: #888888;
			font-size: 12px;
		}
		.notice-wrap .item.left {
			margin-right: 40px;
		}
		.notice-wrap .sub-item.left {
			margin-right: 40px;
		}
		.notice-wrap .item .icon {
			display: inline-block;
			margin-right: 8px;
			vertical-align: baseline;
		}
		.notice-wrap .item .icon img{
			width: 14px;
			height: 14px;
		}
		.notice-wrap .item .text {
			display: inline-block;
		}
		.notice-wrap .item .text .up-down-icon{
			margin-top: 4px;
			float: right;
		}
		.notice-wrap .sub-item .text {
			margin-left: 40px;
		}
		.up-down-icon {
			margin-left: 8px;
			width: 9px;
			height: 9px;
		}
	</style>
	<script src="${basePath }/js/dwz.ui.js" type="text/javascript"></script>
	<script src="${basePath }/js/speedup.js" type="text/javascript"></script>
	<script src="${basePath }/js/jquery-1.7.2.js" type="text/javascript"></script>

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

	<script type="text/javascript">
        $(function(){
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

            $.getJSON("summary/summaryBorrow?type=riskToday", function(data) {
                console.log(data)

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
						var canvasWH = 37
						var canvasHH = 37
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
						context.font="14px Arial";
						context.fillStyle = '#333333';
						context.textBaseline = 'middle';
						var text=numText;
						var tw=context.measureText(text).width;
						context.fillText(text,canvasWH-tw/2,canvasHH + 2);
						// 绘制背景圆
						context.save();
						context.beginPath();
						context.strokeStyle = "#e7e7e7";
						context.lineWidth = "5";
						context.arc(canvasWH, canvasHH, canvasWH - 5, 0, Math.PI * 2, false);
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
						context.lineWidth = "5";
						context.arc(canvasWH, canvasHH, canvasWH - 5, startArc, ((endArc+startArc)), false);
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
						zyhlImg = '<img class="up-down-icon" src="./img/箭头2.png">'
					}
					if (indexInfo.allRegistPercentage < 0) {
						zyhlImg = '<img class="up-down-icon" src="./img/箭头1.png">'
					}
					$('#zyhl-b').html(indexInfo.allRegistPercentage + '%' + zyhlImg)
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
					$('#qehkje').text('¥ ' + formatNumToThree(indexInfo.repyMoney))
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
						drfklImg = '<img class="up-down-icon" src="./img/箭头2.png">'
					}
					if (indexInfo.loanPercentage < 0) {
						drfklImg = '<img class="up-down-icon" src="./img/箭头1.png">'
					}
					$('#drfkl').html(indexInfo.loanPercentage + '%' + drfklImg)

					// 当日通过率
					var drtglImg = ''
					if (indexInfo.passPercentage > 0) {
						drtglImg = '<img class="up-down-icon" src="./img/箭头2.png">'
					}
					if (indexInfo.passPercentage < 0) {
						drtglImg = '<img class="up-down-icon" src="./img/箭头1.png">'
					}
					$('#drtgl').html(indexInfo.passPercentage + '%' + drtglImg)
					// 当日回款率
					var drhklImg = ''
					if (indexInfo.repayPercentage > 0) {
						drhklImg = '<img class="up-down-icon" src="./img/箭头2.png">'
					}
					if (indexInfo.repayPercentage < 0) {
						drhklImg = '<img class="up-down-icon" src="./img/箭头1.png">'
					}
					$('#drhkl').html(indexInfo.repayPercentage + '%' + drhklImg)
				}

				renderNumber(data.indexInfo)

				renderChart(data.indexInfo)
            });
        });


	</script>
</head>
<body scroll="no">
<div id="layout">
	<div id="header">
		<div class="headerNav">
			<a class="logo" href="javascript:void(0)"></a>
			<ul class="nav">
				<li id="switchEnvBox"><a href="javascript:"
										 style="color: black;">${BACK_USER.userAccount},您好!
					欢迎登录后台管理平台</a></li>
				<c:if test="${BACK_USER.id eq 10000}">
					<li><a style="color: black;"
						   href="updateCache" target="ajaxTodo">刷新系统缓存</a></li>
				</c:if>
				<li><a style="color: black;"
					   href="user/updateUserPassword?type=toJsp" target="dialog" mask="true"
					   width="600">修改密码</a></li>
				<li><a style="color: black;" href="logout">退出</a></li>

			</ul>
			<ul class="themeList" id="themeList">
				<li theme="default"><div class="selected">蓝色</div></li>
				<li theme="green"><div>绿色</div></li>
				<li theme="purple"><div>紫色</div></li>
				<li theme="silver"><div>银色</div></li>
				<li theme="azure"><div>天蓝</div></li>
			</ul>
		</div>

		<div id="navMenu">
			<ul>
				<c:forEach items="${menuModuleList}" var="item" varStatus="count">
					<li <c:if test="${count.count==1}">  class="selected" </c:if> >
						<a href="${item.moduleUrl}?myId=${item.id}">
							<span>${item.moduleName}</span>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse">
					<div></div>
				</div>
			</div>
		</div>

		<div id="sidebar">
			<div class="accordion" fillSpace="sideBar">
				<c:forEach items="${subMenu}" var="item" varStatus="count">
					<div class="accordionHeader">
						<c:if test="${ count.index==0}">
							<h2 class="collapsable">
								<span>icon</span>${item.moduleName }
							</h2>
						</c:if>
						<c:if test="${ count.index>0}">
							<h2 class="">
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
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent">
					<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:;"><span><span
								class="home_icon">我的主页</span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft">left</div>
				<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight">right</div>
				<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:;">我的主页</a></li>
			</ul>
			<div style="height: 767px; overflow: scroll" class="navTab-panel tabsPageContent layoutBox">
				<div style="display: block;" class="page unitBox">
					<div class="accountInfo" style="height: 35px;">
						<div class="right">
							<p><%=date%></p>
						</div>
						<p>
							<span>欢迎,${BACK_USER.userAccount}</span>
						</p>
					</div>
					<c:if test="${isShow==true}">
						<div style="font-size: 16px" class="index-page-wrap">
							<div class="left-main">
								<div class="main-title">
									<img src="${basePath }/images/shouyeimg/总体概况.png" alt="">
									<span>总体概况</span>
								</div>
								<div class="circle-card left">
									<div class="circle-wrap">
										<canvas class="myChart" id="ljfkje-c" width="74px" height="74px"></canvas>
									</div>
									<div class="detail-wrap">
										<div id="ljfkje-n" class="number blue number-text">--</div>
										<div class="text">累计放款金额/笔数</div>
									</div>
								</div>
								<div class="circle-card">
									<div class="circle-wrap">
										<canvas class="myChart" id="yhkje-c" width="74px" height="74px"></canvas>
									</div>
									<div class="detail-wrap">
										<div id="yhkje-n" class="number green number-text">--</div>
										<div class="text">已回款金额/笔数</div>
									</div>
								</div>
								<div class="circle-card left">
									<div class="circle-wrap">
										<canvas class="myChart" id="dhkje-c" width="74px" height="74px"></canvas>
									</div>
									<div class="detail-wrap">
										<div id="dhkje-n" class="number yellow number-text">--</div>
										<div class="text">待回款金额/笔数</div>
									</div>
								</div>
								<div class="circle-card">
									<div class="circle-wrap">
										<canvas class="myChart" id="wyqdsje-c" width="74px" height="74px"></canvas>
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
											<span>逾期3~7天</span>
											<span id="uqsdqt-n" class="fix number-text">--</span>
											<span class="red-tag text-tag">逾期</span>
										</div>
									</div>
									<div class="row">
										<div class="info-item left">
											<span>逾期7~15天</span>
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
											<div id="drfkdd" class="number-text">223</div>
										</div>
									</div>
									<div class="main-wrap">
										<div class="item left">
											<div class="icon">
												<img src="${basePath }/images/shouyeimg/当日放款金额.png" alt="">
											</div>
											<div class="text">
												<div>当日复借金额</div>
												<div id="drfjje-n" class="number-text">¥ 12344</div>
											</div>
										</div>
										<div class="item">
											<div class="icon">
												<img src="${basePath }/images/shouyeimg/当日放款订单.png" alt="">
											</div>
											<div class="text">
												<div>当日复借订单</div>
												<div id="drfjdd" class="number-text">12344</div>
											</div>
										</div>
										<div class="item left">
											<div class="icon">
												<img src="${basePath }/images/shouyeimg/当日到期金额.png" alt="">
											</div>
											<div class="text">
												<div>当日到期金额</div>
												<div id="drdqje" class="number-text">¥ 12344</div>
											</div>
										</div>
										<div class="item">
											<div class="icon">
												<img src="${basePath }/images/shouyeimg/当日到期订单.png" alt="">
											</div>
											<div class="text">
												<div>当日到期订单</div>
												<div id="drdqdd" class="number-text">12344</div>
											</div>
										</div>
										<div class="sub-item left">
											<div class="text">
												<div>全额还款金额</div>
												<div id="qehkje" class="number-text">12344</div>
											</div>
										</div>
										<div class="sub-item">
											<div class="text">
												<div>全额还款订单</div>
												<div id="eqhkdd" class="number-text">12344</div>
											</div>
										</div>
										<div class="sub-item left">
											<div class="text">
												<div>当日展期金额</div>
												<div id="drzqje" class="number-text">12344</div>
											</div>
										</div>
										<div class="sub-item">
											<div class="text">
												<div>当日展期订单</div>
												<div id="drzqdd" class="number-text">12344</div>
											</div>
										</div>
										<div class="sub-item left">
											<div class="text">
												<div>当日待收金额</div>
												<div id="drdsje" class="number-text">12344</div>
											</div>
										</div>
										<div class="sub-item">
											<div class="text">
												<div>当日待收订单</div>
												<div id="drdsdd" class="number-text">12344</div>
											</div>
										</div>
										<div class="item left">
											<div class="icon">
												<img src="${basePath }/images/shouyeimg/当日放款率.png" alt="">
											</div>
											<div class="text">
												<div>当日放款率</div>
												<div id="drfkl" class="number-text">12344</div>
											</div>
										</div>
										<div class="item">
											<div class="icon">
												<img src="${basePath }/images/shouyeimg/当日通过率.png" alt="">
											</div>
											<div class="text">
												<div>当日通过率</div>
												<div id="drtgl" class="number-text">12344</div>
											</div>
										</div>
										<div class="item left">
											<div class="icon">
												<img src="${basePath }/images/shouyeimg/当日回款率.png" alt="">
											</div>
											<div class="text">
												<div>当日回款率</div>
												<div id="drhkl" class="number-text">12344</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>

</div>

<div id="footer" s>
	Copyright &copy; 2019-2020  技术支持：小鱼儿<a href="#" target="_blank"></a>
</div>
<script type="text/javascript">

</script>
</body>
</html>
