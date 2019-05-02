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
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="assign/statisticsDetail?bType=${bType}&myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        客服:<input type="text" name="jobName" value="${params.jobName}"/>
                        <input type="hidden" name="createDate" value="${params.createDate}"/>
                    </td>
                    <td>
                        班次:
                        <select name="className" class="textInput">
                            <option value="">全部</option>
                            <option value="系统派单" <c:if test="${'系统派单' eq params.className}">selected="selected"</c:if>>早班 </option>
                            <option value="人工派单" <c:if test="${'人工派单' eq params.className}">selected="selected"</c:if>>晚班</option>
                            <%--<c:forEach var="channelInfo" items="${channelInfoList}">--%>
                            <%--<option value="${channelInfo.id}"--%>
                            <%--<c:if test="${channelInfo.id eq params.channelInfoId}">selected="selected"</c:if> >${channelInfo.channelName}</option>--%>
                            <%--</c:forEach>--%>

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
        <table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
            <thead>
            <tr >
                <th align="center"  >
                    序号
                </th>
                <th align="center"  >
                    日期
                </th>
                <th align="center"  >
                    派单时间
                </th>
                <th align="center"  >
                    截止时间
                </th>
                <th align="center" >
                    班次
                </th>
                <th align="center"  >
                    派单数
                </th>
                <th align="center" >
                    已还订单
                </th>
                <th align="center"  >
                    客服
                </th>
                <th align="center" >
                    回款率
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${assign }" var="in" varStatus="varStatus">
                <tr>
                    <td>${varStatus.count}</td>
                    <td>${in.createDate}</td>
                    <td>${in.createTime}</td>
                    <td>${in.endTime}</td>
                    <td>${in.assignType}</td>
                    <td>${in.assignCount}</td>
                    <td>${in.payBackCount}</td>
                    <td>${in.jobName} </td>
                   <td>
                       <fmt:formatNumber value="${in.payBackCount/in.assignCount*100}" pattern="#,#0.0"/>%
                   </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>


<script type="text/javascript">
</script>