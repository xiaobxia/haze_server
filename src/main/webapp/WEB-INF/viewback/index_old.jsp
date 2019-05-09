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
                console.log("123"+data);
                $.each(data, function(val, item) {
                    $("#"+val).html(addCommas(item,false));
                    console.log(data);
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
            //$.getJSON("summary/summaryBorrow?type=riskUser", function(data) {
            //$.each(data, function(val, item) {
            //$("#"+val).html(addCommas(item,false));
            //});
            //});
            $.getJSON("summary/summaryUser?type=regUser", function(data) {
                $.each(data, function(val, item) {
                    $("#"+val).html(addCommas(item,false));
                });
            });
            $.getJSON("summary/summaryBorrow?type=borrow", function(data) {
                $.each(data, function(val, item) {
                    $("#"+val).html(addCommas(item,false));
                });
            });
            $.getJSON("summary/summaryBorrow?type=loan", function(data) {
                $.each(data, function(val, item) {
                    $("#"+val).html(addCommas(item,false));
                });
            });
            $.getJSON("summary/summaryBorrow?type=risk", function(data) {
                $.each(data, function(val, item) {
                    $("#"+val).html(addCommas(item,false));
                });
            });
            $.getJSON("summary/summaryBorrow?type=old&customerType=1", function(data) {
                $.each(data, function(val, item) {
                    $("#"+val).html(addCommas(item,false));
                });
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
			<div style="height: 767px;" class="navTab-panel tabsPageContent layoutBox">
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
					<div style="height: 747px; overflow: auto;" class="pageFormContent" layoutH="20">
						<fieldset name="message" style="padding-bottom: 30px;">
							<legend>用戶统计</legend>
							<dl>
								<dt class="pfc">总用户数：</dt>
								<dd class="pfc">
									<span class="unit" id="sumCount"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">今日注册用户数：</dt>
								<dd class="pfc">
									<span class="unit loginCount" id="todayRegCount"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">全要素认证用户数：</dt>
								<dd class="pfc">
									<span class="unit registerCount" id="allApprove"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">实名认证用户总数：</dt>
								<dd class="pfc">
									<span class="unit registerAllCount" id="realName"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">绑卡用户总数：</dt>
								<dd class="pfc">
									<span class="unit recharge" id="bank"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">芝麻认证用户总数：</dt>
								<dd class="pfc">
									<span class="unit rechargeAll" id="zm"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">运营商认证总数：</dt>
								<dd class="pfc">
									<span class="unit cash" id="mobile"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">紧急联系人认证总数：</dt>
								<dd class="pfc">
									<span class="unit cashAll" id="contacat"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">人</span>
								</dd>
							</dl>
								<%--<dl>
                                    <dt class="pfc">累计平台总收益：</dt>
                                    <dd class="pfc">
                                        <span class="unit platformEarnings"></span>
                                    </dd>
                                    <dd id="xxtj" >
                                        <span  class="unit1">元</span>
                                    </dd>
                                </dl>
                                <dl>
                                    <dt class="pfc">累计用户总收益：</dt>
                                    <dd class="pfc">
                                        <span class="unit userEarnings"></span>
                                    </dd>
                                    <dd id="xxtj" >
                                        <span  class="unit1">元</span>
                                    </dd>
                                </dl>
                            --%></fieldset>
						<fieldset name="backlog" style="padding-bottom: 30px;">
							<legend>放款统计</legend>
							<dl title="放款成功">
								<dt class="pfc">累计放款金额：</dt>
								<dd class="pfc">
									<span class="unit check" id="borrowMoney"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">元</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">累计放款笔数：</dt>
								<dd class="pfc">
									<span class="unit dispose" id="borrowCount"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">当日放款金额：</dt>
								<dd class="pfc">
									<span class="unit publish" id="borrowMoneyToday"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">元</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">当日放款笔数：</dt>
								<dd class="pfc">
									<span class="unit credit" id="borrowCountToday"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">放款中总金额：</dt>
								<dd class="pfc">
									<span class="unit userCheck" id="loaningMoney"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">元</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">放款中笔数：</dt>
								<dd class="pfc">
									<span class="unit cashCheck" id="loaningCount"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>

							<dl>
								<dt class="pfc">放款失败金额：</dt>
								<dd class="pfc">
									<span class="unit cashChechMoney" id="loanFailMoney"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">元</span>
								</dd>
							</dl>
							<dl>
								<dt class="pfc">放款失败笔数：</dt>
								<dd class="pfc">
									<span class="unit rechargeCheck" id="loanFailCount"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
						</fieldset>
						<fieldset name="backlog" style="padding-bottom: 30px;">
							<legend>风控统计</legend>
							<dl style="width: 33%" title="按照申请的订单数(非用户数)">
								<dt class="pfc">机审累计订单数：</dt>
								<dd class="pfc">
									<span class="unit check" id="riskTotalOrders"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl style="width: 33%"  title="仅统计机审通过的订单">
								<dt class="pfc">机审通过累计订单数：</dt>
								<dd class="pfc">
									<span class="unit dispose" id="riskPassOrders"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl style="width: 33%">
								<dt class="pfc">机审订单通过率：</dt>
								<dd class="pfc">
									<span class="unit dispose" id="riskPassOrdersRate"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">%</span>
								</dd>
							</dl>
							<dl style="width: 33%" title="按照用户数进行统计">
								<dt class="pfc">今日机审订单数：</dt>
								<dd class="pfc">
									<span class="unit publish" id="riskOrdersTotalToday"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl style="width: 33%"  title="机审推到人工复审不在此统计范围">
								<dt class="pfc">今日机审通过订单数：</dt>
								<dd class="pfc">
									<span class="unit credit" id="riskOrdersPassToday"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl style="width: 33%">
								<dt class="pfc">今日机审通过率：</dt>
								<dd class="pfc">
									<span class="unit credit" id="riskOrdersPassRateToday"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">%</span>
								</dd>
							</dl>
								<%--<dl style="width: 33%">
                                    <dt class="pfc">今日老用户订单数：</dt>
                                    <dd class="pfc">
                                        <span class="unit publish" id="todayOldOrder"></span>
                                    </dd>
                                    <dd id="xxtj" >
                                        <span  class="unit1">笔</span>
                                    </dd>
                                </dl>
                                --%><dl style="width: 33%">
							<dt class="pfc">今日老用户已审：</dt>
							<dd class="pfc">
								<span class="unit publish" id="todayOldOrderReview1"></span>
							</dd>
							<dd id="xxtj" >
								<span  class="unit1">笔</span>
							</dd>
						</dl>
							<dl style="width: 33%">
								<dt class="pfc">今日老用户通过：</dt>
								<dd class="pfc">
									<span class="unit publish" id="todayOldOrderSuc1"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl style="width: 33%">
								<dt class="pfc">今日老用户通过率：</dt>
								<dd class="pfc">
									<span class="unit publish" id="todayOldOrderRate1"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">%</span>
								</dd>
							</dl>
							<dl style="width: 33%">
								<dt class="pfc">今日新用户已审：</dt>
								<dd class="pfc">
									<span class="unit publish" id="todayOldOrderReview0"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl style="width: 33%">
								<dt class="pfc">今日新用户通过：</dt>
								<dd class="pfc">
									<span class="unit publish" id="todayOldOrderSuc0"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">笔</span>
								</dd>
							</dl>
							<dl style="width: 33%">
								<dt class="pfc">今日新用户通过率：</dt>
								<dd class="pfc">
									<span class="unit publish" id="todayOldOrderRate0"></span>
								</dd>
								<dd id="xxtj" >
									<span  class="unit1">%</span>
								</dd>
							</dl>
							<!--
                            <dl style="width: 33%" title="按照用户数进行统计">
                                <dt class="pfc">机审用户数：</dt>
                                <dd class="pfc">
                                    <span class="unit publish" id="riskTotalUser"></span>
                                </dd>
                                <dd id="xxtj" >
                                    <span  class="unit1">人</span>
                                </dd>
                            </dl>
                            <dl style="width: 33%"  title="机审推到人工复审不在此统计范围">
                                <dt class="pfc">机审通过用户数：</dt>
                                <dd class="pfc">
                                    <span class="unit credit" id="riskPassUser"></span>
                                </dd>
                                <dd id="xxtj" >
                                    <span  class="unit1">人</span>
                                </dd>
                            </dl>
                            <dl style="width: 33%">
                                <dt class="pfc">机审用户通过率：</dt>
                                <dd class="pfc">
                                    <span class="unit credit" id="riskPassUserRate"></span>
                                </dd>
                                <dd id="xxtj" >
                                    <span  class="unit1">%</span>
                                </dd>
                            </dl>
                            --><%--<dl>
									<dt class="pfc">放款中总金额：</dt>
									<dd class="pfc">
										<span class="unit userCheck" id="loaningMoney"></span>
									</dd>
									<dd id="xxtj" >
                                    	<span  class="unit1">元</span>
                                    </dd>
								</dl>
								<dl>
									<dt class="pfc">放款中笔数：</dt>
									<dd class="pfc">
										<span class="unit cashCheck" id="loaningCount"></span>
									</dd>
									<dd id="xxtj" >
                                    	<span  class="unit1">笔</span>
                                    </dd>
								</dl>
								<dl>
									<dt class="pfc">放款失败金额：</dt>
									<dd class="pfc">
										<span class="unit cashChechMoney" id="loanFailMoney"></span>
									</dd>
									<dd id="xxtj" >
                                    	<span  class="unit1">元</span>
                                    </dd>
								</dl>
								<dl>
									<dt class="pfc">放款失败笔数：</dt>
									<dd class="pfc">
										<span class="unit rechargeCheck" id="loanFailCount"></span>
									</dd>
									<dd id="xxtj" >
                                    	<span  class="unit1">笔</span>
                                    </dd>
								</dl>
							--%></fieldset>

						<!-- <fieldset>
                            <legend>使用帮助</legend>
                            <dl style="width:100%;">
                                <dt>官方交流网站：</dt>
                                <dd style="width:80%;">
                                    <span class="unit"><a style="color:#008bed;" href="http://www.irongbao.com"
                                        target="_blank"><u>http://www.irongbao.com</u></a></span>
                                </dd>
                            </dl>
                        </fieldset> -->
					</div>
				</div>
				</c:if>
				<c:if test="${isShow==true}">
					<div style="height: 747px; overflow: auto;" class="pageFormContent" layoutH="20">
					<fieldset name="backlog" style="padding-bottom: 30px;">
						<legend>放款统计</legend>
						<dl>
							<dt class="pfc">放款失败金额：</dt>
							<dd class="pfc">
								<span class="unit cashChechMoney" id="loanFailMoney"></span>
							</dd>
							<dd id="xxtj" >
								<span  class="unit1">元</span>
							</dd>
						</dl>
						<dl>
							<dt class="pfc">放款失败笔数：</dt>
							<dd class="pfc">
								<span class="unit rechargeCheck" id="loanFailCount"></span>
							</dd>
							<dd id="xxtj" >
								<span  class="unit1">笔</span>
							</dd>
						</dl>
					</fieldset>
					</div>
				</c:if>
			</div>
		</div>
	</div>

</div>

<div id="footer" s>
	Copyright &copy; 2019-2020  技术支持：小鱼儿<a href="#" target="_blank"></a>
</div>

</body>
</html>
