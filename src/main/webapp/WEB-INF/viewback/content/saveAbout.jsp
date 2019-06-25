<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String backbasePath = path + "/common/back";
%>
<c:set var="backbasePath" value="<%=backbasePath%>"></c:set>
<c:set var="path" value="<%=path%>"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<script src="${backbasePath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${backbasePath }/js/crm.upload.js" type="text/javascript"></script>
<script type="text/javascript" src="${backbasePath }/uploadify/scripts/swfobject.js"></script>
<script type="text/javascript" src="${backbasePath}/uploadify/scripts/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${backbasePath }/uploadify/scripts/jquery.uploadify.v2.1.0.js"></script>
<title>修改/添加内容</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="content/saveAbout?parentId=${params.parentId }"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${content.id}">
			<div class="pageFormContent" layoutH="106">
				<p>
					<label>客服电话：</label><input name="aboutServiceTel" class="required"
						type="text" alt="请输入内容标题" size="30" value="${content.aboutServiceTel }" maxlength="50"/>
				</p>
				<div class="divider"></div>
				<p style="height: 100px;">
					<label>官方QQ：</label>
					<input name="aboutOfficialQqGroup" class="required" type="text" alt="请输入内容标题" size="30" value="${content.aboutOfficialQqGroup }" maxlength="50"/>
				</p>
				<div class="divider"></div>
				<p style="height: 340px; width: 950px;">
					<label>公司简介：</label>
					<textarea name="aboutIntroduce" cols="100" rows="6" class="required" maxlength="420">${content.aboutIntroduce }</textarea>
				</p>
				<div class="divider"></div>
				<p style="height: 340px; width: 950px;">
					<label>关于我们内容：</label>
					<textarea name="aboutContent" cols="100" rows="6" class="required" maxlength="420">${content.aboutContent }</textarea>
				</p>
			</div>

			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div>

					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>

		</form>
	</div>
	
</body>
</html>
