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
			<form id="frm" method="post" action="user/configOrderLimit"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input type="hidden" name="id" id="id" value="${backUser.id }">
				<input type="hidden" name="parentId" value="${params.parentId}" />

				<div class="pageFormContent" layoutH="56">
					<p>
						<label>
							客服姓名:
						</label>
						<span>
							<c:if test="${empty backUser.id }">
								<input name="userAccount" value="${backUser.userAccount }"
									   minlength="6" maxlength="16" class="required" type="text"
									   alt="请输入用户名" size="30" />
							</c:if>
							<c:if test="${not empty backUser.id }">
								${backUser.userName }
							</c:if>
						</span>
					</p>

					<div class="divider"></div>
					<p>
						<label>
							派单限制:
						</label>
						<span>

							<input name="orderLimitFlag"  type="radio" checked="checked"
								   value="1" />
										是
							<input name="orderLimitFlag"  type="radio" value="2" />
										否
							<c:if test="${not empty backUser}">
								<script type="text/javascript">
									$("input[name='orderLimitFlag'][value='${backUser.orderLimitFlag}']").attr("checked",true);
								</script>
							</c:if>
						</span>
					</p>
					<div class="divider"></div>
					<p>
						<label>
							早班派单上限:
						</label>
						<span>

							<input name="orderLimitMor"  type="text" value="${backUser.orderLimitMor}" />  单位:条

						</span>
					</p>
					<div class="divider"></div>
					<p>
						<label>
							晚班派单上限:
						</label>
						<span>

							<input name="orderLimitNig"  type="text" value="${backUser.orderLimitNig}" />

						</span>
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