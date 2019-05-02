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
		<form method="post" action="thirdChannel/toAgainThirdUser?&parentId=${params.parentId }"
			onsubmit="return validateCallback(this, dialogAjaxDone);"
			class="pageForm required-validate">
			<div class="pageFormContent" layoutH="48">
				<p>
					<label>添加时间：</label>
					<input type="text" name="nowTime" id="nowTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
				</p>
				<div class="divider"></div>
				
				<p><label>----:</label><label style="width: 230px;">用户补推。</label></p>
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
