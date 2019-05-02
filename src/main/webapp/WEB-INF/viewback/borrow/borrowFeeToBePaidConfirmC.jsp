<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
			<form id="frm" method="post" enctype="multipart/form-data"
				action="borrowFeeToBePaid/saveUpdateBorrowFeePerson"
				onsubmit="return validateCallback(this, dialogAjaxDone);"
				class="pageForm required-validate">
 				<input type="hidden" name="parentId" value="${params.parentId}" /> 
 				
				<div class="pageFormContent" layoutH="50" style="overflow: auto;">
				
				<dl >
				
					<dt style="width: 80px;">
						<label>
							开始时间:
						</label>
					</dt>
					<dd>
						<input dateFmt="yyyy-MM-dd" class="date textInput  required" type="text" size="35" name="fromTime"/>
						
					</dd>
			
			    </dl>
			    <dl >
			      <span style="color:red">注:开始结束时间按创建时间筛选</span>
			    </dl>
			
			    <div class="divider"></div>
			    <dl>
					<dt style="width: 80px;">
						<label>
							结束时间:
						</label>
					</dt>
					<dd>
						<input dateFmt="yyyy-MM-dd" class="date textInput  required" type="text" size="35"  name="toTime"/>
					</dd>
			    </dl>
			    <div class="divider"></div>
				<dl>
				<dt style="width: 80px;">
					<label>
						打款账户:
					</label>
				</dt>
				<dd>
							<select name="capitalTypeCondition" id="loanAccount" >
								<c:forEach var="loanAccount" items="${LOAN_ACCOUNTMap}">
									<option value="${loanAccount.key}" <c:if test="${loanAccount.key eq params.capitalType}">selected="selected"</c:if> >
									${fn:substring(loanAccount.value, 0, fn:indexOf(loanAccount.value, ";;") )}
									</option>
								 </c:forEach>
							</select>
				</dd>
				</dl>
			    <div class="divider" style="border-color:blue"></div>
				<dl style="width: 380px;">
						<dt style="width: 80px;">
							<label>
								订单号:
							</label>
						</dt>
						<dd>
							<input name="yurref" value="${yurref}"
							 class="required"	type="text" alt="请输入订单号" size="35"/>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
					<dt style="width: 80px;">
						<label>
							打款时间:
						</label>
					</dt>
					<dd>
						<input datefmt="yyyy-MM-dd" class="date textInput required" type="text" size="35" name="loanTime"/>
					</dd>
			    </dl>
			    
			<div class="divider"></div>
					<dl>
						<dt style="width: 80px;">
							<label>
								状态:
							</label>
						</dt>
						<dd>
							<select name="status" id="borrowStatu">
							<option value="4" selected="selected" >打款成功</option>
							</select>
						</dd>
					</dl>
					<div class="divider"></div>
					<p >
						<label style="width: 80px;">备注:</label>
						<textarea  name="payRemark" rows="4" cols="60" maxlength="50" alt="请输入名称" >手动打款成功</textarea>
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
	<script type="text/javascript">
	
</script>
</html>