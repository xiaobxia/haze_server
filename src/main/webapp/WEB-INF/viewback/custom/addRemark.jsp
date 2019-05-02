<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <title>详细信息</title>
    <style>
        .beizhu-line {
            margin: 15px 0;
        }
        .beizhu-line .beizhu-radio {
            vertical-align: bottom;
        }
        .beizhu-line .beizhu-radio:not(:first-of-type) {
            margin-left: 20px;
            verticalalign: bottom;
        }
    </style>
</head>
<body>

<div class="pageContent">
    <form method="post" action="customService/saveBorrowRemark"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="id" value="${id}"/>
        <input type="hidden" name="parentId" value="${params.parentId}"/>
        <input type="hidden" name="bType" value="${params.bType}"/>
        <input type="hidden" name="type" value="${params.type}"/>
        <input type="hidden" name="userPhone" value="${params.userPhone}"/>
        <div class="pageFormContent" layoutH="60" style="overflow: auto;">
            <fieldset>
                <legend>添加备注</legend>
                <dl class="nowrap">
                    <dd style="width: auto;">
                        <c:forEach items="${remark}" var="item">
                            <div class="beizhu-line">
                                <span style="color:#66CDAA">${item.key}：&nbsp</span>
                                <c:forEach items="${item.value }" var="remarkObj">
                                    <input class="beizhu-radio" name="remarkFlag" id="borrowStatus" value="${remarkObj.dataValue }"
                                           type="radio"/>${remarkObj.dataName}
                                </c:forEach>
                                <br/>
                            </div>
                        </c:forEach>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>备注:</dt>
                    <dd class="unit">
                        <textarea rows="10" cols="75" name="remarkContent"></textarea>
                    </dd>
                </dl>
            </fieldset>
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
