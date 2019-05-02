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
	<input datefmt="yyyy-MM-dd HH:mm:ss" type="hidden" id= "repaymentTime" name ="repaymentTime" value="${repaymentTime}"/>

	<form id="frm" method="post" enctype="multipart/form-data"
		  action="repayment/renewal"
		  onsubmit="return validateCallback(this, dialogAjaxDone);"
		  class="pageForm required-validate">
		<input type="hidden" name="parentId" value="${params.parentId}" />
		<input type="hidden" name="assetRepaymentId" id="id" value="${assetRepaymentId }">
		<input type="hidden" name="feeLateApr" id="feeLateApr" value="${feeLateApr /100}">

		<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<dl>
				<dt style="width: 80px;">
					<label>
						续期类型:
					</label>
				</dt>
				<dd class="selectParenet">
					<select name="renewalKind" id="renewalKind" class="select required">
						<option value="">请选择</option>
						<c:forEach items="${RENEW_KIND}" var="type">
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
					<select name="renewalType" id="renewalType" class="select required">
						<option value="">请选择</option>
						<c:forEach items="${ALL_REPAY_TYPE}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<span class="select-danger">*</span>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl style="width: 100%;">
				<dt style="width: 80px;">
					<label>
						总服务费:
					</label>
				</dt>
				<dd style="width:500px;">
					<input id="sumFee" name="sumFeeBig" readonly="readonly" type="text" alt="请输入总服务费" size="30" value="${allCount / 100.00}"/>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl style="width: 100%;">
				<dt style="width: 80px;">
					<label>
						服务费:
					</label>
				</dt>
				<dd style="width:500px;">
					<input id="repaymentInterest" name="repaymentInterestBig" readonly="readonly" type="text" alt="请输入服务费" size="30" value="${loanApr / 100.00}"/>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl style="width: 100%;">
				<dt style="width: 80px;">
					<label>
						滞纳金:
					</label>
				</dt>
				<dd style="width:500px;">
					<input id="planLateFee" name="planLateFeeBig" readonly="readonly" type="text" alt="请输入滞纳金" size="30" value="${waitLate / 100.00}"/>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl style="width: 100%;">
				<dt style="width: 80px;">
					<label>
						续期费:
					</label>
				</dt>
				<dd style="width:500px;">
					<input id="renewalFee" name="renewalFeeBig" readonly="readonly"  type="text" alt="请输入续期费" size="30" value="${renewalFee / 100.00}"/>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl style="width: 100%;">
				<dt style="width: 80px;">
					<label>
						支付宝订单号:
					</label>
				</dt>
				<dd style="width:500px;">
					<input id="orderId" name="orderId" class="textInput" type="text" alt="请输入支付宝订单号" size="30" value=""/>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt style="width: 80px;">
					<label>
						实际收款时间:
					</label>
				</dt>
				<dd>
					<input id="realRenewalDate" name="realRenewalDate" class="required" type="text" size="30"  value=""
						   pattern="yyyy-MM-dd HH:mm:ss" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'%y-%M-{%d-7}',maxDate:'%y-%M-%d'})" onchange="autoCalc()"/>

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
					<textarea title="备注" class="required" id="remark" name="remark" rows="5" cols="60" maxlength="50"></textarea>
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
	function autoCalc(){
        var repaymentTime =$("input[name='repaymentTime']").val();
        var realTime =new Date($("input[name='realRenewalDate']").val()).format("yyyy-MM-dd");
        //续期费
        var renewalFee = $('#renewalFee').val();
        //服务费
        var repaymentInterestFee =$('#repaymentInterest').val();
        //总服务费
        var totalFee;
        //应还款时间前还款，则不算逾期
        if(repaymentTime >= realTime){
            //滞纳金
			$('#planLateFee').val('0.0');

            totalFee = parseFloat(renewalFee)+parseFloat(repaymentInterestFee);
			//总服务费
			$('#sumFee').val(totalFee)
		}
		//逾期还款
		if(repaymentTime < realTime){

			var days = dateDiff(realTime,repaymentTime);
			var feeLateApr = $('#feeLateApr').val();
			var planLateFee = (days * parseInt(feeLateApr));
			//滞纳金
			$('#planLateFee').val(planLateFee);
			//总服务费
			totalFee = parseFloat(planLateFee) +parseFloat(renewalFee)+parseFloat(repaymentInterestFee);
			$('#sumFee').val(totalFee);

        }
	}
    $(function(){
        var ali = 'XX号收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var wechat = 'XX号收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var bankwithdraw = 'XX银行XX号收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var employee = '员工XX收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';
        var other = 'XX收款XX(附加其他情况：打款未备注，记录收款平台订单号/多收XX元,作什么款处理/分X次打款,X月X日XX+X月X日XX+…/...）';

        $('#renewalType').change(function(){
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
    function  dateDiff(sDate1,  sDate2){
        //sDate1和sDate2是2006-12-18格式
        var  aDate,  oDate1,  oDate2,  iDays
        aDate  =  sDate1.split("-")
        //调用Date的构造函数，转换为12-18-2006格式
        oDate1 = new Date(aDate[0] , aDate[1]- 1 ,aDate[2]) ;
        aDate = sDate2.split("-") ;

        oDate2  =  oDate2 = new Date(aDate[0] , aDate[1] - 1, aDate[2]) ;
        //把相差的毫秒数转换为天数
        iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)
        return  iDays
    }

    Date.prototype.Format = function(fmt)
    {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }

    function submitFrm(){
        $("#frm").submit();
    }
</script>
</html>