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
        .form-item .label {
            float: left;
        }
    </style>
</head>
<body>
<div class="pageContent">
    <form id="aup" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent form-wrap" layoutH="60" style="overflow: auto;">
        <c:if test="${not empty id}">
            <input type="hidden" name="p-id" id="p-id" value="${id}"/>
            <div class="form-item">
                <span class="label">产品名称：</span>
                <input class="required"  type="text" name="p-productName" id="productName" value="${productDetail.productName}"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">产品金额：</span>
                <input class="required"  type="text" name="p-borrowAmount" id="borrowAmount" value="<fmt:formatNumber type="number" value="${productDetail.borrowAmount/100}" pattern="0.00"
                                          maxFractionDigits="0"/>"/>
                <label style="color: red;" class="required-label">*</label>

            </div>
            <div class="form-item">
                <span class="label">到账金额：</span>
                <input class="required"  type="text" name="p-daozhang" id="daozhang" value="<fmt:formatNumber type="number" value="${(productDetail.borrowAmount - productDetail.totalFeeRate) /100}" pattern="0.00"
                                          maxFractionDigits="0"/>"/>
                <label style="color: red;" class="required-label">*</label>

            </div>
            <div class="form-item">
                <span class="label">滞纳金：</span>
                <input class="required"  type="text" name="p-lateFee" id="lateFee" value="<fmt:formatNumber type="number" value="${productDetail.lateFee/100}" pattern="0.00"
                                          maxFractionDigits="0"/>"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">借款期限：</span>
                <input class="required"  type="text" name="p-borrowDay" id="borrowDay" value="${productDetail.borrowDay}"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <%--<div class="form-item">
                <span class="label">提额：</span>
                <select name="p-limitId" class="textInput required">
                    <option value="">全部</option>
                    <c:forEach var="limitInfo" items="${limitList}">
                        <option value="${limitInfo.id}"
                                <c:if test="${limitInfo.id eq productDetail.limitId}">selected="selected"</c:if> >${limitInfo.limitName}</option>
                    </c:forEach>
                </select>
                <label style="color: red;" class="required-label">*</label>
            </div>--%>
            <div class="form-item">
                <span class="label">展期：</span>
                <select name="p-extendId" class="textInput required">
                    <option value="">全部</option>
                    <c:forEach var="extendInfo" items="${extendList}">
                        <option value="${extendInfo.id}"
                                <c:if test="${extendInfo.id eq productDetail.extendId}">selected="selected"</c:if> >${extendInfo.extendName}</option>
                    </c:forEach>
                </select>
                <label style="color: red;" class="required-label">*</label>
            </div>
        </c:if>
        <c:if test="${empty id}">
            <div class="form-item">
                <span class="label">产品名称：</span>
                <input class="required" type="text" name="p-productName" id="productName"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">产品金额：</span>
                <input class="required" type="text" name="p-borrowAmount" id="borrowAmount"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">到账金额：</span>
                <input class="required" type="text" name="p-daozhang" id="daozhang"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">滞纳金：</span>
                <input class="required" type="text" name="p-lateFee" id="lateFee"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">借款期限：</span>
                <input class="required" type="text" name="p-borrowDay" id="borrowDay"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <%--<div class="form-item">
                <span class="label">提额：</span>
                <select name="p-limitId" class="textInput required">
                    <option value="">全部</option>
                    <c:forEach var="limitInfo" items="${limitList}">
                        <option value="${limitInfo.id}">${limitInfo.limitName}</option>
                    </c:forEach>
                </select>
                <label style="color: red;" class="required-label">*</label>
            </div>--%>
            <div class="form-item">
                <span class="label">展期：</span>
                <select name="p-extendId" class="textInput required">
                    <option value="">全部</option>
                    <c:forEach var="extendInfo" items="${extendList}">
                        <option value="${extendInfo.id}">${extendInfo.extendName}</option>
                    </c:forEach>
                </select>
                <label style="color: red;" class="required-label">*</label>
            </div>
        </c:if>
        </div>
    </form>
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
</body>
<script type="text/javascript">
    <c:if test="${not empty id}">
        var postUrl = "product/updateProduct"
    </c:if>
    <c:if test="${empty id}">
        var postUrl = "product/addProduct"
    </c:if>
    $('#p-submit').click(function () {
        var $form = $("#aup");
        if (!$form.valid()) {
            return false;
        }
        $.ajax({
            type : "post",
            data:{
                "id":$("[name='p-id']").val(),
                "productName":$("[name='p-productName']").val(),
                "borrowAmount":$("[name='p-borrowAmount']").val() * 100,
                "lateFee":$("[name='p-lateFee']").val() * 100,
                "borrowDay":$("[name='p-borrowDay']").val(),
                "totalFeeRate":($("[name='p-borrowAmount']").val() - $("[name='p-daozhang']").val()) * 100,
                "limitId":$("[name='p-limitId']").val(),
                "extendId":$("[name='p-extendId']").val()
            },
            url : postUrl,
            success : function(ret) {
                $('div[class="dialog"]').hide();
                $('div[class="shadow"]').hide();
                $('div[id="dialogBackground"]').hide();
                setTimeout(function () {
                    $('#pagerForm-p').submit()
                }, 100)
            },
            error:DWZ.ajaxError
        })
    })
</script>
</html>