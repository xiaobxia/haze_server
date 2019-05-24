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
                <span class="label">提额类型：</span>
                <input type="text" name="p-limitName" id="limitName" value="${backLimit.limitName}"/>
            </div>
            <div class="form-item">
                <span class="label">还款几次可提额：</span>
                <input type="text" name="p-limitCount" id="limitCount" value="${backLimit.limitCount}"/>
            </div>
            <div class="form-item">
                <span class="label">产品名称：</span>
                <select name="limitProductId" class="textInput">
                    <option value="">全部</option>
                    <c:forEach var="productInfo" items="${list}">
                        <option value="${productInfo.productId}"
                                <c:if test="${productInfo.productId eq backLimit.limitProductId}">selected="selected"</c:if> >${productInfo.productId}</option>
                    </c:forEach>
                </select>
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
                <span class="label">提额类型：</span>
                <input type="text" name="p-limitName" id="limitName"/>
            </div>
            <div class="form-item">
                <span class="label">还款几次可提额：</span>
                <input type="text" name="p-limitCount" id="limitCount"/>
            </div>
            <div class="form-item">
                <span class="label">备注：</span>
                <textarea  rows="10" cols="80" name="p-limitRemark" id="limitRemark"/>
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
    var postUrl = "channel/updateBackLimit"
    </c:if>
    <c:if test="${empty id}">
    var postUrl = "channel/addBackLimit"
    </c:if>
    $('#p-submit').click(function () {
        $.ajax({
            type : "post",
            data:{
                "id":$("[name='p-id']").val(),
                "limitName":$("[name='p-limitName']").val(),
                "limitCount":$("[name='p-limitCount']").val(),
                "limitRemark":$("[name='p-limitRemark']").val()
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