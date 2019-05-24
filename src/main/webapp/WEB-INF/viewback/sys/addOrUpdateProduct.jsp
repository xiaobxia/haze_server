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
    <style>
    </style>
</head>
<body>
<div class="pageContent">
    <div class="pageForm required-validate">
        <input type="hidden" name="p-id" id="p-id"/>
        <div class="form-item">
            <span class="label">产品名称：</span>
            <input type="text" name="p-productName" id="productName"/>
        </div>
        <div class="form-item">
            <span class="label">产品金额：</span>
            <input type="text" name="p-borrowAmount" id="borrowAmount"/>
        </div>
        <div class="form-item">
            <span class="label">到账金额：</span>
            <input type="text" name="p-daozhang" id="daozhang"/>
        </div>
        <div class="form-item">
            <span class="label">滞纳金：</span>
            <input type="text" name="p-lateFee" id="lateFee"/>
        </div>
        <div class="form-item">
            <span class="label">借款期限：</span>
            <input type="text" name="p-borrowDay" id="borrowDay"/>
        </div>
        <div class="form-item">
            <span class="label">备注：</span>
            <textarea  rows="10" cols="80" name="p-remark" id="remark"/>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive">
                    <div class="buttonContent">
                        <button type="submit" id="p-submit">提交</button>
                    </div>
                </div></li>
                <li><div class="button">
                    <div class="buttonContent">
                        <button type="button" class="close">取消</button>
                    </div>
                </div></li>
            </ul>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    <c:if test="${not empty id}">
        var postUrl = "configParams/updateProduct"
    </c:if>
    <c:if test="${empty id}">
        var postUrl = "configParams/addProduct"
    </c:if>
    $('#p-submit').click(function () {
        $.ajax({
            type : "post",
            data:{
                "id":$("[name='p-id']").val(),
                "productName":$("[name='p-productName']").val(),
                "borrowAmount":$("[name='p-borrowAmount']").val() * 100,
                "lateFee":$("[name='p-lateFee']").val() * 100,
                "borrowDay":$("[name='p-borrowDay']").val(),
                "remark":$("[name='p-remark']").val(),
                "totalFeeRate":($("[name='p-borrowAmount']").val() - $("[name='p-daozhang']").val()) * 100
            },
            url : postUrl,
            success : function(ret) {
                if(ret.code == '200'){
                }else{
                }
            },
            error:function(ret){
            }
        })
    })
</script>
</html>