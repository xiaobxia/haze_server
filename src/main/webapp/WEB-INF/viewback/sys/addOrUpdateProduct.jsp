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
    <form id="frm" method="post" enctype="multipart/form-data"
            <c:if test="${not empty id}">
                action="configParams/updateProduct"
            </c:if>
            <c:if test="${empty id}">
                action="configParams/addProduct"
            </c:if>
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <div class="form-item">
            <span class="label">产品名称：</span>
            <input type="text" name="productName"/>
        </div>
        <div class="form-item">
            <span class="label">还款几次可提额：</span>
            <input type="text" name="limitCount"/>
        </div>
        <div class="form-item">
            <span class="label">提额状态：</span>
            <select name="limitStatus" class="textInput">
                <option value="0">开启</option>
                <option value="1">关闭</option>
            </select>
        </div>
        <div class="form-item">
            <span class="label">提额产品：</span>
            <input type="text" name="limiitProductName"/>
        </div>
        <div class="form-item">
            <span class="label">提额产品：</span>
            <textarea  rows="10" cols="80" name="limiitProductName"/>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">

</script>
</html>