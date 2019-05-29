<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
        <div class="form-item">
        <span class="label">提额类型：</span>
        <input type="text" name="l-limitName" id="limitName" value="${backLimit.limitName}" readonly/>
    </div>
        <div class="form-item">
            <span class="label">还款几次可提额：</span>
            <input type="text" name="l-limitCount" id="limitCount" value="${backLimit.limitCount}" readonly/>
        </div>
        <div class="form-item">
            <span class="label">备注：</span>
            <input type="text" name="l-limitRemark" id="limitRemark" value="${backLimit.limitRemark}" readonly/>
        </div>
        <div class="form-item">
            <span class="label">提额至产品：</span>
            <input type="text" name="l-productName" id="productName" value="${backLimit.limitProductId}---${productDetail.productName}" readonly/>
        </div>
    </div>
    <div class="formBar">
        <ul>
            <li><div class="button">
                <div class="buttonContent">
                    <button type="button" class="close">取消</button>
                </div>
            </div></li>
        </ul>
    </div>
</div>
</body>
</html>