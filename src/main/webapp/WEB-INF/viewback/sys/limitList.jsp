<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="channel/goLimitList?myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        提额类型:
                        <select name="limitName" class="textInput">
                            <option value="">全部</option>
                            <c:forEach var="limitInfo" items="${limitList}">
                                <option value="${limitInfo.limitName}"
                                        <c:if test="${limitInfo.limitName eq params.limitName}">selected="selected"</c:if> >${limitInfo.limitName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        提额次数:
                        <select name="limitCount" class="textInput">
                            <option value="">全部</option>
                            <c:forEach var="limitInfo" items="${limitList}">
                                <option value="${limitInfo.limitCount}"
                                        <c:if test="${limitInfo.limitCount eq params.limitCount}">selected="selected"</c:if> >${limitInfo.limitCount}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">
                                    查询
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
                    提额类型
                </th>
                <th>
                    还款几次可提额
                </th>
                <th>
                    备注
                </th>
                <th>
                    状态
                </th>
                <th>
                    状态管理
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="backLimit" items="${pm.items }" varStatus="status">
                <tr target="id" rel="${backLimit.id }">
                    <td>
                            ${backLimit.id}
                    </td>
                    <td>
                            ${backLimit.limitName}
                    </td>
                    <td>
                            ${backLimit.limitCount}
                    </td>
                    <td>
                            ${backLimit.limitRemark}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${backLimit.limitStatus == 0}">
                                <span class="trueBtn">开</span>
                            </c:when>
                            <c:otherwise>
                                <span class="falseBtn">关</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${backLimit.limitStatus == 0}">
                                <span class="setBtn" onclick="setPStatus('${backLimit.id }', 1)">关闭</span>
                            </c:when>
                            <c:otherwise>
                                <span class="setBtn" onclick="setPStatus('${backLimit.id }', 0)">开启</span>
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
    function setPStatus(id, status) {
        $.ajax({
            type : "post",
            dataType: 'json',
            contentType:"application/json;charset=utf-8",
            data:{
                "id":id,
                "limitStatus":status
            },
            url : "channel/updateBackLimit",
            success : function(ret) {
                setTimeout(function () {
                    $('#pagerForm').submit()
                }, 100)
            },
            error:function(ret){
            }
        })
    }
</script>