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
<div class="pageContent new">
    <div class="pageForm required-validate">
        <c:if test="${not empty id}">
            <input type="hidden" name="p-id" id="p-id" value="${id}"/>
            <div class="form-item">
                <span class="label">分发名称：</span>
                <input type="text" name="p-name" value="${appConfigDetail.name}"/>
            </div>
            <div class="form-item">
                <span class="label">安卓下载链接：</span>
                <input type="text" name="p-androidUrl" value="${appConfigDetail.androidUrl}"/>
            </div>
            <div class="form-item">
                <span class="label">IOS下载链接：</span>
                <input type="text" name="p-iosUrl" value="${appConfigDetail.iosUrl}"/>
            </div>
        </c:if>
        <c:if test="${empty id}">
            <input type="hidden" name="p-id" id="p-id" value="${id}"/>
            <div class="form-item">
                <span class="label">分发名称：</span>
                <input type="text" name="p-name" value="${appConfigDetail.name}"/>
            </div>
            <div class="form-item">
                <span class="label">安卓下载链接：</span>
                <input type="text" name="p-androidUrl" value="${appConfigDetail.androidUrl}"/>
            </div>
            <div class="form-item">
                <span class="label">IOS下载链接：</span>
                <input type="text" name="p-iosUrl" value="${appConfigDetail.iosUrl}"/>
            </div>
        </c:if>
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
</body>
<script type="text/javascript">
    <c:if test="${not empty id}">
        var postUrl = "appConfig/editConfig"
    </c:if>
    <c:if test="${empty id}">
        var postUrl = "appConfig/addConfig"
    </c:if>
    $('#p-submit').click(function () {
        $.ajax({
            type : "post",
            data:{
                "id":$("[name='p-id']").val(),
                "name":$("[name='p-name']").val(),
                "androidUrl":$("[name='p-androidUrl']").val(),
                "iosUrl":$("[name='p-iosUrl']").val()
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
            error:function(ret){
            }
        })
    })
</script>
</html>