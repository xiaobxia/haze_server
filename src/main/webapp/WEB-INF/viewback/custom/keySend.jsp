<%--
  Created by IntelliJ IDEA.
  User: tql
  Date: 2018/3/2
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
</head>

<body>
<div class="pageContent">
    <form method="get"  action="customService/keySend"
          onsubmit="return validateCallback(this, dialogAjaxDone);"
          class="pageForm required-validate">
        <input type="hidden" name="parentId" value="${params.parentId}" />
        <input type="hidden" name="id" id="id" value="${params.ids}">
        <input type="hidden" name="groupStatus" id="groupStatus" value="${params.groupStatus}">
        <div class="pageFormContent" layoutH="50" style="overflow: auto;">
            <dl>
                <dt style="width: 80px;">
                    <label>
                        客服:
                    </label>
                </dt>
                <dd>
                    <input name="keySendFlag" type="hidden"  value="1" />
                    <input name="ids" type="hidden" value="${ids}">
                    <c:forEach items="${backUserList}" var="in">
                        ${in.userName}<input name="kefuId" type="checkbox" value="${in.id}">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                    </c:forEach>
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





<script type="text/javascript">

    function getOrderIds(obj) {
        var href = $(obj).attr("href");
        if (href.indexOf('&ids') > -1) {
            href = href.substring(0, href.indexOf('&ids'));
        }
        var hasDifferentGroup = '0';
        var selectedGroup = "";
        $("input[name='checkItem']:checked").each(function () {
            var group = $(this).attr("group");
            if (group != undefined && group != '') {
                if (selectedGroup == '') {
                    selectedGroup = group;//第一次赋值
                } else if (selectedGroup != group) {// 之后和第一次的值比较，有不同就GG
                    hasDifferentGroup = '1';
                }
            }
        })
        if (hasDifferentGroup) {
            var ids = "";
            $("input[name='checkItem']:checked").each(function () {
                ids = ids + "," + $(this).val();
            });
            var toHref = href + "&ids=" + ids.substring(1) + "&groupStatus=" + hasDifferentGroup;
            $(obj).attr("href", toHref);
        } else {
            return;
        }
    }
</script>


</body>
<c:if test="${not empty message}">
    <script type="text/javascript">
        alertMsg.error("${message}");
    </script>
</c:if>
</html>
