<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" action="user/updateUserPassword"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<div class="pageFormContent" layoutH="56">
					<p>
						<label>
							旧密码:
						</label>
						<input name="oldPassword" class="required" minlength="6"
							maxlength="20" type="password" size="30" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							新密码:
						</label>
						<input name="newPassword" id="cp_newPassword"
							class="required alphanumeric textInput valid" minlength="6"
							maxlength="20" type="password" size="30" />
					</p>
					<div class="divider"></div>
					<p>
						<label>
							重复输入新密码:
						</label>
						<input name="moduleName" equalto="#cp_newPassword"
							class="required alphanumeric textInput valid" type="password"
							size="30" />
					</p>
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
</html>