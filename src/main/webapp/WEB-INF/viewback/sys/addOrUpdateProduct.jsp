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
        <c:if test="${not empty id}">
            <input type="hidden" name="p-id" id="p-id" value="${id}"/>
            <div class="form-item">
                <span class="label">产品名称：</span>
                <input type="text" name="p-productName" id="productName" value="${productDetail.productName}"/>
            </div>
            <div class="form-item">
                <span class="label">产品金额：</span>
                <input type="text" name="p-borrowAmount" id="borrowAmount" value="<fmt:formatNumber type="number" value="${productDetail.borrowAmount/100}" pattern="0.00"
                                          maxFractionDigits="0"/>"/>
            </div>
            <div class="form-item">
                <span class="label">到账金额：</span>
                <input type="text" name="p-daozhang" id="daozhang" value="<fmt:formatNumber type="number" value="${productDetail.borrowAmount * (1 - productDetail.totalFeeRate/100000)/100}" pattern="0.00"
                                          maxFractionDigits="0"/>"/>
            </div>
            <div class="form-item">
                <span class="label">滞纳金：</span>
                <input type="text" name="p-lateFee" id="lateFee" value="<fmt:formatNumber type="number" value="${productDetail.lateFee/100}" pattern="0.00"
                                          maxFractionDigits="0"/>"/>
            </div>
            <div class="form-item">
                <span class="label">借款期限：</span>
                <input type="text" name="p-borrowDay" id="borrowDay" value="${productDetail.borrowDay}"/>
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
        </c:if>
        <c:if test="${empty id}">
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
        </c:if>
    </div>
</div>
</body>
<script type="text/javascript">
    <c:if test="${not empty id}">
        var postUrl = "${pageContext.request.contextPath}/back/channel/updateProduct"
    </c:if>
    <c:if test="${empty id}">
        var postUrl = "${pageContext.request.contextPath}/back/channel/addProduct"
    </c:if>
    $('#p-submit').click(function () {
        $.ajax({
            type : "POST",
            url : postUrl,
            dataType: 'json',
            data:{
                "id":$("[name='p-id']").val(),
                "productName":$("[name='p-productName']").val(),
                "borrowAmount":$("[name='p-borrowAmount']").val() * 100,
                "lateFee":$("[name='p-lateFee']").val() * 100,
                "borrowDay":$("[name='p-borrowDay']").val(),
                "totalFeeRate":($("[name='p-borrowAmount']").val() - $("[name='p-daozhang']").val()) * 100
            },
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