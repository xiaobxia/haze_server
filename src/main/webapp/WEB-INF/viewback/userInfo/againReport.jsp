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
		<form method="post" action="channel/toAgainChannelReport?&parentId=${params.parentId }"
			onsubmit="return validateCallbackCrpBack(this, dialogAjaxDone);"
			class="pageForm required-validate">
			<div class="pageFormContent" layoutH="58">
				<p>
					<label>添加时间：</label>
					<input type="text" name="nowTime" id="nowTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
				</p>
				<div class="divider"></div>
				 
			</div>

			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" onsubmit="">保存</button>
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
	<script type="text/javascript">
		function validateCallbackCrpBack(ctx, callback) {
			var lastDo = localStorage.getItem('lastDoCrpBack')
			if (lastDo) {
				lastDo = parseInt(lastDo)
				if (Date.now() - lastDo < 1000 * 60 * 5) {
					var m = parseInt((1000 * 60 * 5 - Date.now() + lastDo)/(1000 * 60))
					alertMsg && alertMsg.info('请'+(m || 1)+'分钟后再尝试刷新')
					return false
				}
			}
			function newCallback (json) {
				localStorage.setItem('lastDoCrpBack', Date.now())
				callback(json)
			}
			return validateCallback(ctx, newCallback)
		}
	</script>
	
</body>
</html>
