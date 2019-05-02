<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>修改/添加内容</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" method="post" enctype="multipart/form-data"
			action="zbUser/saveVip?parentId=${params.parentId}" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input name="id" value="${id }" type="hidden" />
				<div class="pageFormContent" layoutH="56">
					<p>
						<label>
							金额(元)：
						</label>
						<input type="text" name="money" style="width: 50%"  class="required number" min="0"/>
					</p>
					<div class="divider"></div>
					<p>
						<label>
							期限：
						</label>
						<input type="text" name="limit" style="width: 50%"  class="required digits" min="1"/>
					</p>
					<div class="divider"></div>
					<p>
						<label>
							期限单位：
						</label>
						<input type="text" name="period" style="width: 50%"  class="required digits" min="1" max="3"/>1.年；2.月；3.天
					</p>
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
