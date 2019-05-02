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
    <form method="get"  action="customService/sendToOrder"
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
                    <input name="uids" type="hidden" value="${uIds}" />
                    <select id="userId" name="userId">
                        <option name="userId" value="" selected="selected">全部</option>
                        <c:forEach items="${backUserList}" var="in">
                            <option name="userId" value="${in.id}">${in.userName}</option>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <div class="divider"></div>

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
    function ajax() {
        var userId = $('#userId').val();
        $.ajax({
            url:"${pageContext.request.contextPath}/back/customService/sendToOrder",
            method:'get',
            data:{
                "uids":"${uIds}",
                "userId":userId
            },
            success:function(status,data){
                $('#to-send-pop').fadeOut();
                alert("派送成功");
                //location.reload();
            }
        });
    }

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
        $('div[class="dialog"]').hide();
        $('div[class="shadow"]').hide();
        $('div[id="dialogBackground"]').hide();
        alertMsg.error('${message}');
    </script>
</c:if>
</html>
