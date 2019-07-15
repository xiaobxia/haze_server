<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            width: 120px;
        }
    </style>
</head>
<body>
<div class="pageContent">
    <form id="aul" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent form-wrap" layoutH="60" style="overflow: auto;">
            <c:if test="${not empty id}">
                <input type="hidden" name="l-id" id="l-id" value="${id}"/>
                <div class="form-item">
                    <span class="label">提额类型：</span>
                    <input class="required" type="text" name="l-limitName" id="limitName"
                           value="${backLimit.limitName}"/>
                    <label style="color: red;" class="required-label">*</label>
                </div>
                <div class="form-item">
                    <span class="label">还款几次可提额：</span>
                    <input class="required" type="text" name="l-limitCount" id="limitCount"
                           value="${backLimit.limitCount}"/>
                    <label style="color: red;" class="required-label">*</label>
                </div>
                <div class="form-item">
                    <span class="label">备注：</span>
                    <input type="text" name="l-limitRemark" id="limitRemark" value="${backLimit.limitRemark}"/>
                </div>
                <div class="form-item">
                    <span class="label">提额前产品：</span>
                    <select class="required" name="l-beforeLimitProductId" class="textInput">
                        <option value="">---请选择---</option>
                        <c:forEach var="productInfo" items="${list}">
                            <option value="${productInfo.productId}"
                                    <c:if test="${productInfo.limitId eq backLimit.id}">selected="selected"</c:if> >${productInfo.productId}---${productInfo.productName}</option>
                        </c:forEach>
                    </select>
                    <label style="color: red;" class="required-label">*</label>
                </div>
                <div class="form-item">
                    <span class="label">提额至产品：</span>
                    <select class="required" name="l-limitProductId" class="textInput">
                        <option value="">---请选择---</option>
                        <c:forEach var="productInfo" items="${list}">
                            <option value="${productInfo.productId}"
                                    <c:if test="${productInfo.productId eq backLimit.limitProductId}">selected="selected"</c:if> >${productInfo.productId}---${productInfo.productName}</option>
                        </c:forEach>

                    </select>
                    <label style="color: red;" class="required-label">*</label>
                </div>
            </c:if>
            <c:if test="${empty id}">
                <div class="form-item">
                    <span class="label">提额类型：</span>
                    <input class="required" type="text" name="l-limitName" id="limitName"/>
                    <label style="color: red;" class="required-label">*</label>
                </div>
                <div class="form-item">
                    <span class="label">还款几次可提额：</span>
                    <input class="required" type="text" name="l-limitCount" id="limitCount"/>
                    <label style="color: red;" class="required-label">*</label>
                </div>
                <div class="form-item">
                    <span class="label">备注：</span>
                    <input type="text" name="l-limitRemark" id="limitRemark"/>
                </div>
                <div class="form-item">
                    <span class="label">提额前产品：</span>
                    <select class="required" name="l-beforeLimitProductId" class="textInput">
                        <option value="">---请选择---</option>
                        <c:forEach var="productInfo" items="${list}">
                            <option value="${productInfo.productId}">${productInfo.productId}---${productInfo.productName}</option>
                        </c:forEach>
                    </select>
                    <label style="color: red;" class="required-label">*</label>
                </div>
                <div class="form-item">
                    <span class="label">提额至产品：</span>
                    <select class="required" name="l-limitProductId" class="textInput">
                        <option value="">---请选择---</option>
                        <c:forEach var="productInfo" items="${list}">
                            <option value="${productInfo.productId}">${productInfo.productId}---${productInfo.productName}</option>
                        </c:forEach>
                    </select>
                    <label style="color: red;" class="required-label">*</label>
                </div>
            </c:if>
        </div>
    </form>
    <div class="formBar">
        <ul>
            <li>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button type="submit" id="l-submit">提交</button>
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
</div>
</body>
<script type="text/javascript">
    <c:if test="${not empty id}">
    var postUrl = "product/updateBackLimit"
    </c:if>
    <c:if test="${empty id}">
    var postUrl = "product/addBackLimit"
    </c:if>
    $('#l-submit').click(function () {
        var $form = $("#aul");
        if (!$form.valid()) {
            return false;
        }
        $.ajax({
            type: "post",
            data: {
                "id": $("[name='l-id']").val(),
                "limitName": $("[name='l-limitName']").val(),
                "limitCount": $("[name='l-limitCount']").val(),
                "limitRemark": $("[name='l-limitRemark']").val(),
                "limitProductId": $("[name='l-limitProductId']").val(),
                "beforeLimitProductId": $("[name='l-beforeLimitProductId']").val()
            },
            url: postUrl,
            success: function (ret) {
                $('div[class="dialog"]').hide();
                $('div[class="shadow"]').hide();
                $('div[id="dialogBackground"]').hide();
                setTimeout(function () {
                    $('#pagerForm-l').submit()
                }, 100)
            },
            error: DWZ.ajaxError
        })
    })
</script>
</html>