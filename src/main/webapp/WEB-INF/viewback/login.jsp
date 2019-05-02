<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String path = request.getContextPath()+"";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${APP_NAME}-运营管理系统</title>
<link rel='icon' href='<%=path %>/admin-favicon.ico' type=‘image/x-ico’ />
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
	background:#070f29 url(<%=path %>/common/back/images/bg.jpg) top center no-repeat;
	color:#fff;
	background-size: cover;
}
.m-formTable{
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
}
.m-loginTable tr {
	text-align: center;
}
.input {
	width: 250px;
	height: 35px;
	line-height: 35px;
	padding: 0 6px;
	margin-bottom: 8px;
	background: #fff;
	outline: medium none;
	padding-left: 15px;
	color: #333;
	border: 1px solid #e0e0e0;
	border-radius: 3px;
	outline: medium none;
}
.u-title {
	font-size: 26px;
	font-weight: normal;
	border-bottom: 1px solid #eee;
	margin: 25px 80px;
	padding-bottom: 25px;
	margin-bottom: 25px;
}
.u-getCode{
	display: inline-block;
	background: #fff;
	color: #ff0000;
	width: 80px;
	font-size: 12px;
	text-align: center;
	border-radius: 3px;
	line-height: 32px;
	vertical-align: middle;
	padding: 0 5px;
	margin-left: 26px;
	text-decoration: none;
}
.u-login {
	width: 275px;
	line-height: 50px;
	height: 50px;
	background: #0c1131;
	border: none;
	color: #fff;
	font-size: 19px;
	letter-spacing: 26px;
	text-align: center;
	margin-left: 14px;
	border-radius: 6px;
	text-indent: 25px;
}
</style>
</head>
<body>
<div class="container">
	<form id="jvForm" name="jvForm" action="login" method="post" onsubmit="return login();">
		<table width="550" border="0" align="center" cellpadding="0" class="m-formTable" cellspacing="0">
			<tr>
				<td><table width="100%" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td valign="top" style="width:362px;heigth:340px;" >
								<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background: rgba(250,250,250,.15); margin: 8px;">
									<tr>
										<td align="center" >
											<h4 class="u-title">运营管理系统</h4>
										</td>
									</tr>
									<tr>
										<td>
											
											<table width="100%" class="m-loginTable" border="0" align="center" cellpadding="0" cellspacing="5">
												<tr>
													<td width="211"><input type="text" id="userMobile"  placeholder="手机号码" name="userMobile"  maxlength="100" class="input" />
													</td>
												</tr>
												<tr>
													<td><input name="userPassword" type="password" placeholder="密  码" id="userPassword" maxlength="32"  class="input" /></td>
												</tr>
												<tr>

													<td>
													<input maxlength="4" style="width:130px; " name="captcha" type="text" placeholder="验证码" id="captcha" class="input"  />
													<img id="imgCap" style="width: 90px; vertical-align: -12px; margin-left: 26px;" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />
													<%-- <span style="width:100px; color:red; display:none;">${msg}</span> --%>
													</td>
												</tr>
												<tr>
													<td>
														<input name="smsCode" type="text" placeholder="短信验证码" id="smsCode" maxlength="32"  class="input" style="width: 130px;" />
														<a href="javascript:;" id="sendCode" onclick="sendCode();" class="u-getCode">获取验证码</a>
													</td>
												</tr>
												<tr>
													<td height="120" colspan="2" align="center" class="sub">
														<input type="submit" name="submit" value="登录" class="u-login" />&nbsp; &nbsp;&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table></td>
						</tr>
					</table></td>
			</tr>
		</table>
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
		setTimeout(function() {
			time(htmlId, click);
		}, 1000);
	}
}
	function sendCode() {
		 if (jvForm.userMobile.value==""){
	        alert("手机号码不能为空！");
	        return false;
	    }else if (jvForm.userPassword.value==""){
	        alert("密码不能为空！");
	        return false;
	    }else if (jvForm.captcha.value==""){
	        alert("验证码不能为空！");
	        return false;
	    }else{
			 $.ajax({
				type : "post",
				data:{
					"userMobile":$("[name='userMobile']").val(),
					"userPassword":$("[name='userPassword']").val(),
					"RCaptchaKey":$("[name='RCaptchaKey']").val(),
					"captcha":$("[name='captcha']").val()
				},			
				url : "sendSmsBack",
				success : function(ret) {
					if(ret.code == '200'){
						time('sendCode','sendCode()');
						alert(ret.msg);
					}else{
						$("#imgCap").trigger("click");
						alert(ret.msg);
					}
				},
				error:function(ret){
					$("#imgCap").trigger("click");
				}
			});
    	}
	}
function login(){
    if (jvForm.userMobile.value==""){
        alert("手机号码不能为空！");
        return false;
    }else if (jvForm.userPassword.value==""){
        alert("密码不能为空！");
        return false;
    }else if (jvForm.captcha.value==""){
        alert("验证码不能为空！");
        return false;
    }
	/*else if (jvForm.smsCode.value==""){
        alert("短信验证码不能为空！");
        return false;
    }*/
}
var msg="${message}";
if(msg){
	alert(msg);
}
</script>
</html>
