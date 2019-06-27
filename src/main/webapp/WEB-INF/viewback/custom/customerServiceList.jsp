<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>


<style>
    .popLayer {
        display: none;
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        z-index: 100;
        width: 500px;
        background: grey;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 1px 1px 50px rgba(0,0,0,.3);
    }
    .popLayer .layer-title {
        position: relative;
        padding: 0 80px 0 20px;
        height: 42px;
        line-height: 42px;
        border-bottom: 1px solid #eee;
        font-size: 14px;
        color: #333;
        overflow: hidden;
        background-color: #F8F8F8;
        border-radius: 2px 2px 0 0;
    }
    .popLayer .layer-title .layer-close {
        position: absolute;
        right: 8px;
        top: 8px;
        font-size: 20px;
        padding: 2px 7px;
        color: #666;
        font-weight: 100;
        text-decoration: none;
    }
    .popLayer .layer-title .layer-close:hover {
        color: #999;
    }
    .popLayer .layer-content {
        min-height: 200px;
    }
    /*  转派内容样式  */
    .send-custom {
        padding: 60px 50px;
        text-align: center;
    }
    .send-custom span {
        font-size: 15px;
        margin-right: 10px;
    }
    /*  添加备注内容样式  */
    .add-remark {
        padding: 20px;
    }
    .add-remark ul li {
        margin-bottom: 20px;
    }
    .add-remark ul li>label {
        font-size: 16px;
        vertical-align: top;
        margin-right: 10px;
    }
    .add-remark ul li>textarea {
        vertical-align: top;
    }
    .add-remark ul li span >label {
        font-size: 14px;
    }
    .add-remark ul li > p >span {
        display: inline-block;
        margin-bottom: 8px;
    }
    .buttonActive {
        margin-right: 20px;
    }
    .add-remark table {
        width: 100%;
    }
    .add-remark table {
        border: 1px solid #e0e0e0;
    }
    .add-remark table tr th:last-child,
    .add-remark table tr td:last-child{
        border-right: 0;
    }
    .add-remark table tr:last-child td {
        border-bottom: 0;
    }
    .add-remark table tr td,
    .add-remark table tr th{
        border-right: 1px solid #e0e0e0;
        border-bottom: 1px solid #e0e0e0;
    }
    .add-remark table tr th {
        background: #f8f8f8;
    }
    .add-remark table tr td,
    .add-remark table tr th{
        padding: 10px 5px;
    }
</style>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="customService/queryCustomerList?bType=${bType}&myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        手机号:
                        <input type="text" name="userMobileLike" id="userMobileLike" value="${params.userMobileLike}" />
                    </td>
                   <td>
                       真实姓名:
                        <input type="text" name="userName" id="userName" value="${params.userName}" />
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
        <table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
            <thead>
            <tr >
                <th align="center"  >
                    编号
                </th>
                <th align="center"  >
                    用户名
                </th>
                <th align="center"  >
                    真实姓名
                </th>
                <th align="center"  >
                    手机号
                </th>
                <th align="center" >
                    邮箱
                </th>
                <th align="center"  >
                    状态
                </th>
                <th align="center" >
                    添加时间
                </th>
                <th align="center">
                    QQ
                </th>
                <th align="center">
                    派单上限
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pm.items }" var="user" varStatus="varStatus">
                <tr target="sid_support" rel="${user.id}">
                    <td>${varStatus.index + 1}</td><!--序号-->
                    <td>
                            ${user.userAccount}
                    </td>
                    <td>
                            ${user.userName}
                    </td>
                    <td>
                            ${user.userMobile}
                    </td>
                    <td>
                            ${user.userEmail}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${user.status == '1'}">
                                启用
                            </c:when>
                            <c:otherwise>
                                禁用
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                            ${user.createDate}
                    </td>
                    <td>
                            ${user.userQq}
                    </td>
                    <td>
                        <c:if test="${user.orderLimitFlag ==1}">
                            早:${user.orderLimitMor}\晚:${user.orderLimitNig}
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp"%>
    </div>
</form>


<script type="text/javascript">
    $(function(){

    })
</script>