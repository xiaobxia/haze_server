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
    <div class="pageForm">
        <form id="file-form" enctype="multipart/form-data">
            <div style="margin: 30px;">
                <span style="margin-right:30px;">导入文件</span>
                <input id="excel_file" type="file" name="filename" accept="xlsx" size="80"/>
            </div>
            <div class="formBar">
                <ul>
                    <li><div class="buttonActive">
                        <div class="buttonContent">
                            <button id="excel_button" type="button" >导入Excel</button>
                        </div>
                    </div></li>
                    <li><div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div></li>
                </ul>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    function check() {
        var excel_file = $("#excel_file").val();
        if (excel_file == "" || excel_file.length == 0) {
            alert("请选择文件路径！");
            return false;
        } else {
            return true;
        }
    }
    $("#excel_button").on("click", function(){
        if (check()) {
            var formData = new FormData();
            console.log('in aaa')
            formData.append("filename",$("#excel_file")[0].files[0]);
            console.log($("#excel_file")[0].files[0])
            $.ajax({
                url: "userManage/batchimport",
                type: 'POST',
                cache: false,
                processData: false,
                contentType: false,
                data: formData,
                success : function(data) {
                    $('div[class="dialog"]').hide();
                    $('div[class="shadow"]').hide();
                    $('div[id="dialogBackground"]').hide();
                    setTimeout(function () {
                        $('#pagerForm-ubl').submit()
                    }, 100)
                }
            });
        }
    });
</script>
</html>