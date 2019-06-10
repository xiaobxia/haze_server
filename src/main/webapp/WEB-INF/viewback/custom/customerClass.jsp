<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="customService/customerClass?bType=${bType}&myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        日期:
                        <input type="text" name="startDate" id="startDate"
                               class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
                               value="${params.startDate}" />
                    </td>
                    <td>
                        至:
                        <input type="text" name="endDate" id="endDate"
                               class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
                               value="${params.endDate}" />
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
    <jsp:include page="${BACK_URL}/rightSubList">
        <jsp:param value="${params.myId}" name="parentId"/>
    </jsp:include>
    <div class="pageContent">
        <table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
            <thead>
            <tr>
                <th align="center"  >
                    <input type="checkbox" id="checkAlls" onclick="checkAll(this);"/>
                <th align="center"  >
                    序号
                </th>
                <th align="center"  >
                    日期
                </th>
                <th align="center"  >
                    早班值班人数
                </th>
                <th align="center" >
                    晚班值班人数
                </th>
                <th align="center"  >
                    更新时间
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pm.items }" var="item" varStatus="varStatus">
                <tr target="sid_support" rel="${item.id}">
                    <td>
                        <input type="checkbox" name="checkItem" value="${item.id}"/>
                    </td>
                    <td>
                            ${varStatus.index + 1}
                    </td>
                    <td>
                            ${item.classDate}
                    </td>
                    <td>
                            ${item.classMorCustomers}
                    </td>
                    <td>
                            ${item.classNigCustomers}
                    </td>
                    <td>
                        <fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
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
    function checkAll(obj){
        var bool = $(obj).is(':checked');
        $("input[name='checkItem']").attr("checked",bool);
    }
    function exportCustomerExcel(obj) {
        var customerIds = "";

        $("input[name='checkItem']:checked").each(function () {
            customerIds = customerIds + "," + $(this).val();
        });
        if(customerIds.length ==0){
            alert("请选择导出内容");
        }else{
            var href=$(obj).attr("href");
            var toHref = href + "&ids="+customerIds;
            $(obj).attr("href",toHref);
        }
    }

</script>
