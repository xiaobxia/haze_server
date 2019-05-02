<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert user</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" enctype="multipart/form-data" action="channel/saveDynamic"
				onsubmit="return iframeCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="parentId" value="${params.parentId}" />
				<input type="hidden" name="channelId" value="${channelInfo.id}" />
				<input type="hidden" name="channelCode" value="${channelInfo.channelCode}" />
<%-- 				<input type="hidden" name="id" id="id" value="${user.id}"> --%>
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">

					<p style="width: 630px;">
						<label>渠道:</label><label style="width: 170px;">${channelInfo.channelSuperName}</label>
						<label>渠道编码:</label><label style="width: 170px;">${channelInfo.channelSuperCode }</label>
					</p>
					<p style="width: 630px;">
						<label>渠道商名称:</label><label style="width: 170px;">${channelInfo.channelName}</label>
						<label>渠道商编码:</label><label style="width: 170px;">${channelInfo.channelCode}</label>
					</p>
					<div class="divider"></div>
					<p><label>手机:</label><input name="userPhone" value="${user.userPhone }"  type="text" class="required phone" alt="请输入手机" size="30"  /></p>
					<div class="divider"></div>

					<p><label>是否动态连接:</label>
						<input name="channelNew" type="radio" checked="checked" value="1" />否
						<input name="channelNew" type="radio" value="2" /> 是     &nbsp;(注：选择动态链接请联系技术创建页面)
					</p>
					<div class="divider"></div>

					<p><label>默认密码:</label><label style="width: 230px;">${DEFAULT_PWD }，登录后请尽快修改密码。</label></p>
					<div class="divider"></div>
					<p>
					<label>真实姓名:</label><input name="realname" value="${user.realname }"
								class="required" type="text" alt="请输入用户真实姓名" size="30" /></p>
					<div class="divider"></div>

					<p><label>性别:</label>
						<input name="userSex" type="radio" checked="checked" value="男" />男
						<input name="userSex" type="radio" value="女" /> 女
						<c:if test="${not empty user}">
							<script type="text/javascript">
								 $("input[name='userSex'][value='${user.userSex}']").attr("checked",true);
							</script>
						</c:if>
					</p>
					<div class="divider"></div>


					<p><label>邮箱:</label><input name="email" value="${user.email }" type="text" 	 alt="请输入邮箱" size="30" /></p>
					<div class="divider"></div>

					<p><label>QQ:</label><input name="qq" value="${user.qq }" class="digits" type="text" alt="请输入qq" size="30" /></p>
					<div class="divider"></div>


					<p><label>证件号码:</label><input name="idNumber" value="${user.idNumber }" type="text" minlength="8" maxlength="18"  type="text"   alt="请输入证件号码" size="30" /></p>
					<div class="divider"></div>

					<p><label>注册页图片：</label><input type="file" name="registerPic" id="registerPic" /></p>
					<%--<p><input type="input" name="registerPicUrl" id="registerPicUrl" /></p>--%>
					<div class="divider"></div>

					<p><label>下载页图片：</label><input type="file" name="downloadPic" id="downloadPic"/></p>
					<%--<p><input type="input" name="downloadPicUrl" id="downloadPicUrl" /></p>--%>
					<div class="divider"></div>

					<p><label>APK URL:</label><input name="apkUrl" value="${user.apkUrl }" type="text" alt="请输入APK URL" size="30" /></p>
					<div class="divider"></div>

					<p><label>图形验证码次数:</label><input name="picCodeNum" value="${user.picCodeNum }" type="text" alt="请输入图形验证码次数" size="30" /></p>
					<div class="divider"></div>

					<p><label>今日头条转化id:</label><input name="toutiaoConvertId" value="${user.toutiaoConvertId }" type="text" alt="请输入今日头条转化id" size="30" /></p>
					<div class="divider"></div>

					<p><label>渠道标签:</label><input name="channelTag" value="${user.channelTag }" type="text" alt="请输入渠道标签" size="30" /></p>
					<div class="divider"></div>
					</div>

				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
//		function preview(file) {
//
//			alert(file.files[0]);
//			var reader = new FileReader();
//			var bb = reader.readAsBinaryString(file.files[0]);
//
//			//判断是否有选择上传文件
//			var imgPath = $("#registerPic").val();
//			alert(imgPath);
//			if (imgPath == "") {
//				alert("请选择上传图片！");
//				return;
//			}
//			//判断上传文件的后缀名
////			var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
////			if (strExtension != 'jpg' && strExtension != 'gif'
////					&& strExtension != 'png' && strExtension != 'bmp') {
////				alert("请选择图片文件");
////				return;
////			}
////			var formData = new FormData($('#frm')[0]);
//			$.ajax({
//				url: "channel/pic",
//				type: "POST",
//				data: "f=" + file.files[0],
//				async: false,
//				cache: false,
//				contentType: false,
//				processData: false,
//				success: function(data) {
//					alert("上传成功" + data);
//					$("#registerPicUrl").val(data);
////					$("#imgDiv").empty();
////					$("#imgDiv").html(data);
////					$("#imgDiv").show();
//				},
//				error: function(XMLHttpRequest, textStatus, errorThrown) {
//					alert("上传失败，请检查网络后重试");
//				}
//			});
//
//		}
	</script>
</html>