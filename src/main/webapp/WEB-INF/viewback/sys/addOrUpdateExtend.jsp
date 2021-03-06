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
    <form id="aue" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent form-wrap" layoutH="60" style="overflow: auto;">
        <c:if test="${not empty id}">
            <input type="hidden" name="p-id" id="p-id" value="${id}"/>
            <div class="form-item">
                <span class="label">续期类型：</span>
                <input class="required" type="text" name="p-extendName" id="extendName" value="${backExtend.extendName}"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">续期次数：</span>
                <input class="required" type="text" name="p-extendCount" id="extendCount" value="${backExtend.extendCount}"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">续期费用：</span>
                <input class="required" type="text" name="p-extendMoney" id="extendMoney" value="${backExtend.extendMoney/100}"/>元
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">续期天数：</span>
                <input class="required" type="text" name="p-extendDay" id="extendDay" value="${backExtend.extendDay}"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">备注：</span>
                <input type="text" name="p-remark" id="remark" value="${backExtend.remark}"/>
            </div>
        </c:if>
        <c:if test="${empty id}">
            <div class="form-item">
                <span class="label">续期类型：</span>
                <input class="required" type="text" name="p-extendName" id="extendName"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">续期次数：</span>
                <input class="required" type="text" name="p-extendCount" id="extendCount"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">续期费用：</span>
                <input class="required" type="text" name="p-extendMoney" id="extendMoney"/>元
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">续期天数：</span>
                <input class="required" type="text" name="p-extendDay" id="extendDay"/>
                <label style="color: red;" class="required-label">*</label>
            </div>
            <div class="form-item">
                <span class="label">备注：</span>
                <input type="text" name="p-remark" id="remark"/>
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
    var postUrl = "product/updateExtend"
    </c:if>
    <c:if test="${empty id}">
    var postUrl = "product/addExtend"
    </c:if>
    $('#p-submit').click(function () {
        var $form = $("#aue");
        if (!$form.valid()) {
            return false;
        }
        $.ajax({
            type : "post",
            data:{
                "id":$("[name='p-id']").val(),
                "extendName":$("[name='p-extendName']").val(),
                "extendCount":$("[name='p-extendCount']").val(),
                "extendMoney":$("[name='p-extendMoney']").val() * 100,
                "extendDay":$("[name='p-extendDay']").val(),
                "remark":$("[name='p-remark']").val()
            },
            url : postUrl,
            success : function(ret) {
                $('div[class="dialog"]').hide();
                $('div[class="shadow"]').hide();
                $('div[id="dialogBackground"]').hide();
                setTimeout(function () {
                    $('#pagerForm-e').submit()
                }, 100)
            },
            error:DWZ.ajaxError
        })
    })
</script>
</html>