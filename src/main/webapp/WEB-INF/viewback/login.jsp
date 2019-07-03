<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath() + "";
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>${APP_NAME}-运营管理系统</title>
	<link rel='icon' href='${basePath }/themes/default/images/admin.ico' type=‘image/x-ico’/>
	<script type="text/javascript" src="<%=path %>/common/back/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=path %>/resources/js/DD_belatedPNG.js"></script>
	<script src="${path }/common/back/js/util.js" type="text/javascript"></script>

	<script>
		DD_belatedPNG.fix('.container,.sub,background');
	</script>

	<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			font-size: 12px;
			background: #070f29 url(/common/back/images/bg.png) no-repeat;
			background-size: 100% 100%;
			min-height: 100vh;
			color: #fff;
		}
		.login-wrap {
			position: absolute;
			width: 500px;
			height: 500px;
			top: 0;
			bottom: 0;
			margin: auto;
			left: 60%;
			background:rgba(255,255,255,0.05);
			padding: 0 50px;
			box-sizing: border-box;
		}
		.login-wrap {
		}
		.login-item {
			margin-bottom: 25px;
		}
		.input {
			box-sizing: border-box;
			width:400px;
			height:50px;
			border:1px solid rgba(0,210,255,1);
			border-radius:6px;
			line-height: 50px;
			font-size: 18px;
			background-color:rgba(255,255,255,0.03);
			padding: 0 10px;
			color: #fff;
		}
		input:-internal-autofill-selected {
			background:rgba(255,255,255,0.03); !important;
			background-image: none !important;
			color: #fff !important;
		}
		.input:focus {
			outline: none;
		}
		.u-title {
			font-size: 30px;
			font-weight: 400;
			margin: 25px 0;
			text-align: center;
		}
		.jiao-1 {
			position: absolute;
			top: 0;
			left: 0;
			width: 40px;
			height: 40px;
			border-top: 4px solid rgb(0, 181, 255);
			border-left: 4px solid rgb(0, 181, 255);
			border-right: 4px solid transparent;
			border-bottom: 4px solid transparent;
		}
		.jiao-2 {
			position: absolute;
			top: 0;
			right: 0;
			width: 40px;
			height: 40px;
			border-top: 4px solid rgb(0, 181, 255);
			border-right: 4px solid rgb(0, 181, 255);
			border-left: 4px solid transparent;
			border-bottom: 4px solid transparent;
		}
		.jiao-3 {
			position: absolute;
			bottom: 0;
			right: 0;
			width: 40px;
			height: 40px;
			border-bottom: 4px solid rgb(0, 181, 255);
			border-right: 4px solid rgb(0, 181, 255);
			border-left: 4px solid transparent;
			border-top: 4px solid transparent;
		}
		.jiao-4 {
			position: absolute;
			left: 0;
			bottom: 0;
			width: 40px;
			height: 40px;
			border-bottom: 4px solid rgb(0, 181, 255);
			border-left: 4px solid rgb(0, 181, 255);
			border-right: 4px solid transparent;
			border-top: 4px solid transparent;
		}
		.u-getCode {
			display: inline-block;
			background:rgba(255,255,255,0.03);
			color: #fff;
			width: 115px;
			height: 50px;
			font-size: 15px;
			text-align: center;
			border-radius: 3px;
			line-height:50px;
			vertical-align: middle;
			padding: 0 5px;
			margin-left: 26px;
			text-decoration: none;
			letter-spacing: 0.2em;
		}
		.u-login {
			display: block;
			width: 399px;
			line-height: 50px;
			height: 50px;
			background: rgb(0, 150,240);
			border: none;
			color: #fff;
			font-size: 19px;
			letter-spacing: 26px;
			text-align: center;
			border-radius: 6px;
			text-align: center;
		}
		.loginLogo {
			position: absolute;
			top: 60px;
			left: 100px;
			height: 60px;
		}
	</style>
</head>
<body>
<div class="container">
	<img src="/common/back/images/loginLogo.png" alt="" class="loginLogo">
	<form id="jvForm" name="jvForm" action="login" method="post" onsubmit="return login();">
		<div class="login-wrap">
			<div class="jiao-1"></div>
			<div class="jiao-2"></div>
			<div class="jiao-3"></div>
			<div class="jiao-4"></div>
			<h4 class="u-title">用户登录</h4>
			<div class="login-item">
				<input type="text" id="userMobile" placeholder="手机号码" name="userMobile" class="input"/>
			</div>
			<div class="login-item">
				<input name="userPassword" type="password" placeholder="密  码"
					   id="userPassword" class="input"/></td>
			</div>
			<div class="login-item">
				<input maxlength="4" style="width:240px; " name="captcha"
					   type="text" placeholder="验证码" id="captcha"
					   class="input"/>
				<img id="imgCap"
					 style="height: 50px; margin-left: 26px;"
					 src="<%=path %>/captcha.svl"
					 onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1"
					 valign="bottom" alt="点击更新" title="点击更新"/>
			</div>
			<div class="login-item">
				<input name="smsCode" type="text" placeholder="短信验证码"
					   id="smsCode" maxlength="32" class="input"
					   style="width: 240px;"/>
				<a href="javascript:;" id="sendCode" onclick="sendCode();"
				   class="u-getCode">获取验证码</a>
			</div>
			<div class="login-item">
				<input type="submit" name="submit" value="登录" class="u-login"/>
			</div>
		</div>
	</form>
</div>
</body>
<script>
	var wait = 60;

	function time(htmlId, click) {
		if (wait == 0) {
			$("#" + htmlId).attr("onclick", click);
			$("#" + htmlId).text("获取验证码");
			wait = 60;
		} else {
			$("#" + htmlId).text(wait + "秒后重试");
			$("#" + htmlId).removeAttr("onclick");
			wait--;
			setTimeout(function () {
				time(htmlId, click);
			}, 1000);
		}
	}

	function sendCode() {
		if (jvForm.userMobile.value == "") {
			alert("手机号码不能为空！");
			return false;
		} else if (jvForm.userPassword.value == "") {
			alert("密码不能为空！");
			return false;
		} else if (jvForm.captcha.value == "") {
			alert("验证码不能为空！");
			return false;
		} else {
			$.ajax({
				type: "post",
				data: {
					"userMobile": $("[name='userMobile']").val(),
					"userPassword": $("[name='userPassword']").val(),
					"RCaptchaKey": $("[name='RCaptchaKey']").val(),
					"captcha": $("[name='captcha']").val()
				},
				url: "sendSmsBack",
				success: function (ret) {
					if (ret.code == '200') {
						time('sendCode', 'sendCode()');
						alert(ret.msg);
					} else {
						$("#imgCap").trigger("click");
						alert(ret.msg);
					}
				},
				error: function (ret) {
					$("#imgCap").trigger("click");
				}
			});
		}
	}

	function login() {
		if (jvForm.userMobile.value == "") {
			alert("手机号码不能为空！");
			return false;
		} else if (jvForm.userPassword.value == "") {
			alert("密码不能为空！");
			return false;
		} else if (jvForm.captcha.value == "") {
			alert("验证码不能为空！");
			return false;
		}
		/*else if (jvForm.smsCode.value==""){
            alert("短信验证码不能为空！");
            return false;
        }*/
	}

	var msg = "${message}";
	if (msg) {
		alert(msg);
	}
</script>
</html>
