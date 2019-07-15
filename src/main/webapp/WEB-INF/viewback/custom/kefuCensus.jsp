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
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="customService/kefuCensus?bType=${bType}&myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                 <td>
                     客服名字： <input type="text" name="userName" id = "userName" value="${userName}">
                </td>
                    </td>
                    <td>
                        日期:
                        <input type="text" name="beginTime" id="beginTime"
                               class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
                               value="${beginTime}" />
                    </td>
                    <td>
                        至:
                        <input type="text" name="endTime" id="endTime"
                               class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"
                               value="${endTime}" />
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
        <div class="panelBar">
            <ul class="toolBar">
                <%--<li class="">
                    <a href="customService/toBackCensusLoan?myId=682&parentId=${params.myId}" class="edit" target="dialog" width="410" height="210" rel="jbsxBox" mask="true">
                        <span>回算统计</span> </a>
                </li>--%>
                <li class="">
                    <a id="a-l-c-r-btn"><span>刷新</span> </a>
                </li>
            </ul>
        </div>
        <table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
            <thead>
            <tr>
                <th align="center"  >
                    序号
                </th>
                <th align="center"  >
                    日期
                </th>
                <th align="center"  >
                    客服
                </th>
               <th align="center" >
                    当日派单数
                </th>
                <th align="center">
                    当日派单回款数
                </th>
                <th align="center">
                    当日总回款数
                </th>
                <th align="center">
                    当前总派单数
                </th>
                <th align="center"  >
                    当前总回款数
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pm.items }" var="item" varStatus="varStatus">
                <tr>
                    <td>
                            ${varStatus.index + 1}
                    </td>
                    <td>
                            ${item.createTime}
                    </td>
                    <td>
                            ${item.userName}
                    </td>
                    <td>
                            ${item.dayCount}
                    </td>
                    <td>
                           ${item.dayRepayCount}
                    </td>
                    <td>
                        ${item.realDayRepay}
                    </td>
                    <td>
                           ${item.allCount}
                    </td>
                    <td>
                           ${item.allRepayCount}
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
    $('#a-l-c-r-btn').click(function () {
        var lastDo = localStorage.getItem('lastDoFreshenLoanCensusResult')
        if (lastDo) {
            lastDo = parseInt(lastDo)
            if (Date.now() - lastDo < 1000 * 60 * 60) {
                var m = parseInt((1000 * 60 * 60 - Date.now() + lastDo)/(1000 * 60))
                alertMsg && alertMsg.info('请'+(m || 1)+'分钟后再尝试刷新')
                return
            }
        }
        $.ajax({
            type : "post",
            url : 'customService/freshKefuCensus',
            success : function(ret) {
                localStorage.setItem('lastDoFreshenLoanCensusResult', Date.now())
                alertMsg && alertMsg.correct('操作成功，请一小时后再尝试刷新')
                setTimeout(function () {
                    $('#pagerForm-alc').submit()
                }, 100)
            },
            error:DWZ.ajaxError
        })
    })
</script>
