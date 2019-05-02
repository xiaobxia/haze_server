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
        .buttonActive {
            margin-right: 10px;
        }

        .pageFormContent .textInput {
            float: none;
        }
    </style>
</head>
<body>
<div class="pageContent">
    <form id="frm" method="post"
          action="review/distribute_switch"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" id="status" name="status" value=""/>
        <div class="pageFormContent" layoutH="50" style="overflow: auto;">
            时间：<input name="startHour" id="startHour" value="${startHour}" type="text"/> - <input name="endHour" id="endHour" value="${endHour}" type="text"/>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <c:if test="${'close' == status}">
                        <div class="buttonActive">
                            <button class="buttonContent" onclick="onSubPost('open')">
                                开始派单
                            </button>
                        </div>
                    </c:if>

                    <c:if test="${'open' == status}">
                        <div class="buttonActive">
                            <button class="buttonContent" onclick="onSubPost('open')">
                                修改派单
                            </button>
                        </div>
                        <div class="buttonActive">
                            <button class="buttonContent" onclick="onSubPost('close')">
                                关闭派单
                            </button>
                        </div>
                    </c:if>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">
                                取消
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
</body>
</html>
<script type="text/javascript">
    function onSubPost(status) {
        $("input[name=status]").val(status);
    }

    function isTime(str)
    {
        var reg=/^([0,1]\d|2[0-3]):[0-5]\d$/;
        if(reg.test(str))
           return true;
        else{
            return false;
        }
    }

    function checkHour(startHour,endHour)
    {
        var arrStartTime = startHour.split(":");
        var arrEndTime = endHour.split(":");
        if(arrStartTime[0]>arrEndTime[0]){
            alert("起始时间不能大于结束时间");
            return false;
        }
        if(arrStartTime[0]==arrEndTime[0]){
            if(arrStartTime[1]>arrEndTime[1]){
                alert("起始时间不能大于结束时间");
                return false;
            }
        }
        return true;

    }

    function checkEmpty(str){
        if(str == "" || str == null || str == undefined){
            return true;
        }
        return false;
    }
</script>
