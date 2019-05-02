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
		<form method="post" action="userManage/updateBankCard?parentId=${params.parentId }"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="userId" value="${params.id}">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>所属银行：</label>
					<select name="bank_id">
						<c:forEach items="${bankList}" var="bank">
							<option value="${bank.id }" 
							<c:if test="${bank.id eq bankIndo.bank_id}">selected="selected"</c:if>>${bank.bankName }</option>
						</c:forEach>
					</select>
				</p>
				<div class="divider"></div>
				<p>
					<label>支持卡人姓名：</label>
					<input name="realName" class="required"
						type="text" alt="请输入内容标题" size="30" value="${bankIndo.openName}" maxlength="50" disabled="disabled"/>
				<div class="divider"></div>
				<p>
					<label>卡号：</label>
					<input name="card_no" class="required"
						type="text" alt="请输入内容标题" size="30" value="${bankIndo.card_no}" maxlength="20"/>
				</p>
				<div class="divider"></div>
				<p>
					<label>预留手机号码：</label>
					<input name="phone" class="required phone"
						type="text" alt="请输入内容标题" size="30" value="${bankIndo.phone}" maxlength="11"/>
				</p>
				<div class="divider"></div>
				<p>
					<label>添加时间：</label>
					<input name="createTime" 
						type="text" alt="请输入内容标题" size="30" value="<fmt:formatDate value="${bankIndo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" maxlength="50" disabled="disabled"/>
					
				</p>
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
