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
			action="risk/${params.url }?parentId=${params.parentId}" onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
				<input name="id" value="${rule.id }" type="hidden" />
				<div class="pageFormContent" layoutH="56">
					<dl>
						<dt>
							<label>名称：</label>
						</dt>
						<dd>
							<input type="text" name="ruleName" value="${rule.ruleName }" style="width: 100%"  class="required" maxlength="50"/>
						</dd>
					</dl>
					<c:choose>
						<c:when test="${not empty rule and rule.rootType !=1}">
							<dl>
								<dt>
									<label>状态：</label>
								</dt>
								<dd>
									<select name="status">
										<c:forEach items="${ALL_RISK_STATUS}" var="status">
											<option value="${status.key }"
											<c:if test="${status.key eq rule.status }">selected="selected"</c:if>>${status.value }</option>
										</c:forEach>
									</select>
								</dd>
							</dl>
						</c:when>
						<c:otherwise>
							<input name="status" value="1" type="hidden" />
						</c:otherwise>
					</c:choose>
					<div class="divider"></div>
					<dl title="基础规则的表达式只能是具体的数值，相当于默认值；当用户无法得到对应的数据时，以这个值为准；&#10;扩展规则的表达式可以是一个计算公式也可以是一段逻辑代码">
						<dt>
							<label>规则类型：</label>
						</dt>
						<dd style="width: 60%;">
							<select name="ruleType" id="ruleType">
								<c:forEach items="${ALL_RISK_TYPE}" var="r">
									<option value="${r.key }"
									<c:if test="${r.key eq rule.ruleType }">selected="selected"</c:if>>${r.value }</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl title="基础规则的表达式只能是具体的数值，相当于默认值；当用户无法得到对应的数据时，以这个值为准；&#10;扩展规则的表达式可以是一个计算公式也可以是一段逻辑代码">
						<dt>
							<label>关注程度：</label>
						</dt>
						<dd style="width: 60%;">
							<select name="attentionType" id="attentionType">
								<c:forEach items="${ALL_ATTENTION_TYPE}" var="r">
									<option value="${r.key }"
									<c:if test="${r.key eq rule.attentionType }">selected="selected"</c:if>>${r.value }</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl class="nowrap" style="width: 100%;">
					 <dt>
							<label>节点类型：</label>
					</dt>
					<dd style="width: 80%;">
						<select name="rootType" id="rootType">
							<c:forEach items="${ALL_ROOT}" var="r">
								<option value="${r.key }"
								<c:if test="${r.key eq rule.rootType }">selected="selected"</c:if>>${r.value }</option>
							</c:forEach>
						</select>
							<label style="color:red;width: 350px;">各类型根节点各自最多只能有一个</label>
					</dd>
					</dl>
					<div class="divider"></div>
					<dl class="nowrap">
						<dt>
							<label>规则</label>
						</dt>
						<dd>
							<textarea id="formula"  name="formula" rows="12" cols="80" class="required" onkeyup="checkRule();">${rule.formula }</textarea>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl class="nowrap">
						<dt>
							<label>备注:</label>
						</dt>
						<dd>
							<textarea  name="ruleDesc" rows="5" cols="60" maxlength="250">${rule.ruleDesc }</textarea>
						</dd>
					</dl>
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
		function checkRule(){
			var ruleType  = $("#ruleType option:selected").val();
			if(ruleType == 1){
				var value = $("#formula").val();
				if(isNaN(value)){
					$("#formula").val('');
				}
			}
		}
	</script>
</html>
