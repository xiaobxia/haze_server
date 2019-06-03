<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm-ac" onsubmit="return navTabSearch(this);"
      action="appConfig/list?myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">
                                    刷新
                                </button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="pageContent">
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>
        <table class="table" style="width: 100%;" layoutH="155" nowrapTD="false">
            <thead>
            <tr>
                <th>
                    ID
                </th>
                <th>
                    分发名称
                </th>
                <th>
                    安卓下载链接
                </th>
                <th>
                    IOS下载链接
                </th>
                <th>
                    开关
                </th>
                <th>
                    操作
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="appconfig" items="${pm.items }" varStatus="status">
                <tr target="id" rel="${appconfig.id }">
                    <td>
                        ${appconfig.id}
                    </td>
                    <td>
                        ${appconfig.name}
                    </td>
                    <td>
                        ${appconfig.androidUrl}
                    </td>
                    <td>
                        ${appconfig.iosUrl}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${appconfig.status == 1}">
                                <div class="slide-btn open-s disabled"></div>
                            </c:when>
                            <c:otherwise>
                                <div class="slide-btn close-s" onclick="setPStatus('${appconfig.id}')"></div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${appconfig.status == 1}">
                                <span class="deleteBtn disabled">删除</span>
                            </c:when>
                            <c:otherwise>
                                <span class="deleteBtn" onclick="setPDelete('${appconfig.id }')">删除</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <c:set var="page" value="${pm }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>

<script type="text/javascript">
    function setPStatus(id) {
        $.ajax({
            type : "post",
            data:{
                "id":id
            },
            url : "appConfig/openOrCloseConfig",
            success : function(ret) {
                setTimeout(function () {
                    $('#pagerForm-ac').submit()
                }, 100)
            },
            error:function(ret){
            }
        })
    }

    function setPDelete(id) {
        if(confirm("确定要删除吗？")) {
            $.ajax({
                type : "post",
                data:{
                    "id":id,
                    "isDelete": '1'
                },
                url : "appConfig/editConfig",
                success : function(ret) {
                    setTimeout(function () {
                        $('#pagerForm-ac').submit()
                    }, 200)
                },
                error:function(ret){
                }
            })
        }
    }

</script>