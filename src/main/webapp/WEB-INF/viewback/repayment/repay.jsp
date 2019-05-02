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
	<form id="frm" method="post" enctype="multipart/form-data"
		  action="repayment/repay"
		  onsubmit="return validateCallback(this, dialogAjaxDone);"
		  class="pageForm required-validate">
		<input type="hidden" name="parentId" value="${params.parentId}" />
		<input type="hidden" name="assetRepaymentId" id="id" value="${assetRepaymentId }">
		<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<dl style="width: 100%;">
				<dt style="width: 80px;">
					<label>
						还款金额(元):
					</label>
				</dt>
				<dd style="width:500px;">
					<input id="trueRepaymentMoney" name="trueRepaymentMoneyBig" class="number required" type="text" alt="请输入还款金额" size="30"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					剩余待还金额￥ ${remainMoney} 元
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width: 80px;">
					<label>
						还款类型:
					</label>
				</dt>
				<dd class="selectParenet">
					<select name="repaymentChannel" id="repaymentChannel" class="select required">
						<option value="">请选择</option>
						<c:forEach items="${ALL_REPAY_CHANNEL}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<span class="select-danger">*</span>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width:80px;">
					<label>
						还款渠道:
					</label>
				</dt>
				<dd class="selectParenet">
					<select name="repaymentType" id="repaymentType" class="select required">
						<option value="">请选择</option>
						<c:forEach items="${ALL_REPAY_TYPE}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<span class="select-danger">*</span>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width: 80px;">
					<label>
						实际还款时间:
					</label>
				</dt>
				<dd>
					<input datefmt="yyyy-MM-dd HH:mm:ss" class="date textInput readonly required" type="text" size="30" readonly="readonly" name="createdAtStr"/>
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
					<textarea title="备注" class="required" name="remark" id="remark" rows="5" cols="60" maxlength="50"  placeholder="" >${backUser.remark }</textarea>
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

    $(function(){
        var ali = 'XX号收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var wechat = 'XX号收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var bankwithdraw = 'XX银行XX号收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var employee = '员工XX收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var other = 'XX收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';

        $('#repaymentType').change(function(){
            var opt = $(this).children('option:selected');
            if(9 == $(opt).val()|| 10 ==  $(opt).val()){
                $('#remark').attr('placeholder',ali)
            }
            else if(11 == $(opt).val()){
                $('#remark').attr('placeholder',bankwithdraw)
            }
            else if(12 == $(opt).val()){
                $('#remark').attr('placeholder',employee)
            }
            else if(13 == $(opt).val()){
                $('#remark').attr('placeholder',other)
            }else{
                $('#remark').attr('placeholder','')
            }

        });

    });


    function submitFrm(){
        var trueRepaymentMoney = parseFloat($("#trueRepaymentMoney").val());
        if(trueRepaymentMoney <= 0){
            alertMsg.error('请输入有效的还款金额！')
        }
        else if(parseFloat("${remainMoney}") < trueRepaymentMoney){
            alertMsg.error('还款金额不能大于剩余待还金额！')
        }
        else{
            $("#frm").submit();
        }
    }
</script>
</html>