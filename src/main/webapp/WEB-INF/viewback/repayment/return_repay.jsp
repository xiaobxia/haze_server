<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<form id="frm" method="post" 
		  action="return/returnRepay" enctype="multipart/form-data"
		  onsubmit="return validateCallback(this, dialogAjaxDone);"
		  class="pageForm required-validate">
		<input type="hidden" name="parentId" value="${params.parentId}" />
		<input type="hidden" name="assetRepaymentDetailId" id="id" value="${detailId }">
		<input type="hidden" name="returnSource" id="returnSource" value="${rs}">
		<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<dl style="width: 100%;">
				<dt style="width: 80px;">
					<label>
						退款金额(元):
					</label>
				</dt>
				<dd style="width:500px;">
					<input id="repaymentReturnMoney" name="repaymentReturnMoney" class="number required" type="text" alt="请输入退款金额" size="30"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					可退金额￥ ${returnMoney} 元
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width: 80px;">
					<label>
						退款时间:
					</label>
				</dt>
				<dd>
					<input datefmt="yyyy-MM-dd HH:mm:ss" class="date textInput readonly required" type="text" size="30" readonly="readonly" name="returnTimeStr"/>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width: 80px;">
					<label>
						退款类型:
					</label>
				</dt>
				<dd>
					<select title="退款类型" name="returnType" id="returnType">
						<c:forEach items="${RETURN_TYPE}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width: 80px;">
					<label>
						退款订单号:
					</label>
				</dt>
				<dd>
					<input id="returnOrderId" name="returnOrderId" class="required" type="text" alt="请输入退款订单号" size="60"/>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width: 80px;">
					<label>
						备注:
					</label>
				</dt>
				<dd>
					<textarea title="备注" name="remark" rows="5" cols="60" maxlength="50">${backUser.remark }</textarea>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitFrm()">
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
	function submitFrm(){
		var trueRepaymentMoney = parseFloat($("#repaymentReturnMoney").val());
		if(trueRepaymentMoney <= 0){
			alertMsg.error('请输入有效的退款金额！')
		}
		else if(parseFloat("${returnMoney}") < trueRepaymentMoney){
			alertMsg.error('退款金额不能大于剩余待还金额！')
		}
		else{
			$("#frm").submit();
		}
	}
</script>
</html>