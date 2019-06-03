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
            <span class="label">续期类型：</span>
            <input type="text" name="p-extendName" id="extendName" value="${backExtend.extendName}" readonly/>
        </div>
        <div class="form-item">
            <span class="label">续期次数：</span>
            <input type="text" name="p-extendCount" id="extendCount" value="${backExtend.extendCount}" readonly/>
        </div>
        <div class="form-item">
            <span class="label">续期费用：</span>
            <input type="text" name="p-extendMoney" id="extendMoney" value="${backExtend.extendMoney/100}" readonly />元
        </div>
        <div class="form-item">
            <span class="label">续期天数：</span>
            <input type="text" name="p-extendDay" id="extendDay" value="${backExtend.extendDay}" readonly />
        </div>
        <div class="form-item">
            <span class="label">备注：</span>
            <input type="text" name="p-remark" id="remark" value="${backExtend.remark}" readonly />
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