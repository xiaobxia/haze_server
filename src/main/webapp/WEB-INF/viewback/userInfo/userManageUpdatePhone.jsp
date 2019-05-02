<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>修改/添加内容</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="userManage/operation?option=3&id=${id }&parentId=${params.parentId }"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${id}">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>原手机号码：</label>
					<input class="required"
						type="text"  size="30" value="${userPhone}" maxlength="50" disabled="disabled"/>
				</p>
				<div class="divider"></div>
				<p>
					<label>新手机号码：</label>
					<input name="userPhone" class="required phone"
						type="text" alt="请输入新手机号码" size="30" value="" maxlength="50"/>
				<div class="divider"></div>
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
