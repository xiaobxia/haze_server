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
</head>
<body>
<div class="pageContent">
    <form id="frm" method="post"
          action="review/saveUpdate"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="id" id="id" value="${backUser.id }">
        <div class="pageFormContent" layoutH="50" style="overflow: auto;">
            <dl>
                <dt style="width: 80px;">
                    <label>
                        复审人员:
                    </label>
                </dt>
                <dd>
                    <input value="${backUser.userName }"
                           type="text" size="30" disabled/>
                </dd>
            </dl>

            <div class="divider"></div>
            <dl>
                <dt style="width: 80px;">
                    <label>
                        派单比例:
                    </label>
                </dt>
                <dd>
                    <input name="distributionRate" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" value="${backUser.distributionRate }"
                           class="required" type="text" alt="请输入派单比例" size="30"/>
                </dd>
            </dl>

        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">
                                保存
                            </button>
                        </div>
                    </div>
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