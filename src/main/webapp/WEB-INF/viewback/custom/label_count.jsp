<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = path + "/common/back";
%>
<c:set var="basePath" value="<%=basePath%>"></c:set>
<!DOCTYPE html >
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <link href="${basePath }/css/pop_page.css" rel="stylesheet" type="text/css" media="screen"/>
    <title>标签统计</title>
    <style type="text/css">
        .label_content {
            border-right: 1px solid #804040;
            border-bottom: 1px solid #804040;
            border-collapse: collapse;
            line-height: 52px;
            font-size: 12px;
        }

        .label_content td {
            border-left: 1px solid #804040;
            border-top: 1px solid #804040;
            line-height: 30px;
        }

        .label_content th {
            line-height: 38px;
            font-size: 16px;
        }

        .label_content .lcount-btn {
            background: #fff;
            border: 1px solid #b7b7b7;
            padding: 5px 15px;
            margin: 8px auto;
            display: block;
            color: #1a9fdf;
            border-radius: 3px;
            font-size: 12px;
        }
    </style>
</head>
<body>
<div>
    <form id="pagerForm" onsubmit="return navTabSearch(this);"
          action="customService/getLabelCountPage?myId=${params.myId}"
          method="post">
        <div class="pageHeader">
            <div class="searchBar">
                <table class="searchContent">
                    <tr>
                        <td>日期：
                            <input type="text" name="startDate" id="startDate" value="${params.startDate}"
                                   class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"/>
                            <span id="starttips" style="color:#F00"></span>
                            至 <input type="text" name="endDate" id="endDate" value="${params.endDate}"
                                     class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly"/>
                        </td>
                        <td>
                            用户来源:
                            <select id="userFrom" name="userFrom">
                                <option value=""
                                        <c:if test="${params.userFrom eq ''}">selected="selected"</c:if>>
                                </option>
                                <option value="0"
                                        <c:if test="${params.userFrom eq '0'}">selected="selected"</c:if>>全部
                                </option>
                                <option value="1"
                                        <c:if test="${params.userFrom eq '1'}">selected="selected"</c:if>>Android
                                </option>
                                <option value="2"
                                        <c:if test="${params.userFrom eq '2'}">selected="selected"</c:if>>ios
                                </option>
                                <option value="3"
                                        <c:if test="${params.userFrom eq '3'}">selected="selected"</c:if>>其它
                                </option>
                            </select>
                        </td>

                        <td>
                            新老用户:
                            <select id="userType" name="userType">
                                <option value=""
                                        <c:if test="${params.userType eq ''}">selected="selected"</c:if>>
                                </option>
                                <option value="0"
                                        <c:if test="${params.userType eq '0'}">selected="selected"</c:if>>所有用户
                                </option>
                                <option value="1"
                                        <c:if test="${params.userType eq '1'}">selected="selected"</c:if>>新用户
                                </option>
                                <option value="2"
                                        <c:if test="${params.userType eq '2'}">selected="selected"</c:if>>老用户
                                </option>
                            </select>
                        </td>

                        <td>
                            客服类型:
                            <select id="customerType" name="customerType">
                                <option value=""
                                        <c:if test="${params.customerType eq ''}">selected="selected"</c:if>>全部
                                </option>
                                <option value="0"
                                        <c:if test="${params.customerType eq '0'}">selected="selected"</c:if>>在线客服
                                </option>
                                <option value="1"
                                        <c:if test="${params.customerType eq '1'}">selected="selected"</c:if>>电话客服
                                </option>
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
                    <tr></tr>
                </table>
            </div>
        </div>
        <div class="pageContent">
            <jsp:include page="${BACK_URL}/rightSubList">
                <jsp:param value="${params.myId}" name="parentId"/>
            </jsp:include>
            <table class="label_content" style="text-align:center;" width="100%" border="1px" layoutH="112"
                   nowrapTD="false">
                <thead style="background: #EEEE00">
                <tr>
                    <th>序号</th>
                    <th>日期</th>
                    <th>客服类型</th>
                    <th>用户来源</th>
                    <th>新老用户</th>
                    <th>用户总数</th>
                    <th>标签类型</th>
                    <th>占比</th>
                    <th>详情</th>
                </tr>
                </thead>


                <tbody>

                <c:forEach var="labelCount" items="${pm.items }" varStatus="status">
                    <tr target="id" rel="${labelCount.id}">
                    <tr id = lcount_tr>
                            <%--<c:if test="${labelCount.customerType eq }"></c:if>--%>
                        <td rowspan="${fn:length(labelCount.pageResults) + 1}">${status.count}</td>
                        <td rowspan="${fn:length(labelCount.pageResults) + 1}"><fmt:formatDate
                                value="${labelCount.countDate}" pattern="yyyy-MM-dd hh:mm:ss" type="date"
                                dateStyle="long"/></td>
                        <td rowspan="${fn:length(labelCount.pageResults) + 1}">
                            <c:choose>
                                <c:when test="${labelCount.customerType eq 0}">在线客服</c:when>
                                <c:otherwise>电话客服</c:otherwise>
                            </c:choose>
                        </td>
                        <td rowspan="${fn:length(labelCount.pageResults) + 1}">
                            <c:choose>
                                <c:when test="${labelCount.userFrom eq 0}">全部</c:when>
                                <c:when test="${labelCount.userFrom eq 1}">安卓</c:when>
                                <c:when test="${labelCount.userFrom eq 2}">苹果</c:when>
                                <c:otherwise>其它</c:otherwise>
                            </c:choose>
                        </td>
                        <td rowspan="${fn:length(labelCount.pageResults) + 1}">
                            <c:choose>
                                <c:when test="${labelCount.userType eq 0}">全部</c:when>
                                <c:when test="${labelCount.userType eq 1}">新用户</c:when>
                                <c:when test="${labelCount.userType eq 2}">老用户</c:when>
                            </c:choose>
                        </td>
                        <td rowspan="${fn:length(labelCount.pageResults) + 1}">${labelCount.userNum}</td>
                    </tr>
                    <c:forEach var="countResult" items="${labelCount.pageResults}" varStatus="status">
                        <tr>
                            <td>${countResult.name}</td>
                            <td>${countResult.rate}%</td>
                            <td>
                                <div class="buttonContent">
                                    <button class="lcount-btn" type="button"
                                            onclick="getLabelDetail('${labelCount.id}', '${countResult.name}', '${labelCount.customerType}', '${labelCount.userNum}');">
                                        详情
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
            <c:set var="page" value="${pm }"></c:set>
            <!-- 分页 -->
            <%@ include file="../page.jsp" %>
        </div>
    </form>

    <section class="new-popup" id="label_count_detail_pop" style="display: none;overflow: auto;">
        <div class="popContent remarks-content">
            <div id="ww" class="common-popContent">
                <span id="label_type"></span>
                <table border="1">
                    <thead style="background: #EEEE00">
                    <tr id="pop_tr">
                        <th id="label_detail">详情标签</th>
                        <th id="label_rate">占比</th>
                        <th id="label_detail_s">详细分类</th>
                        <th id="moning_rate">早班占比</th>
                        <th id="night_rate">晚班占比</th>
                    </tr>
                    </thead>
                    <tbody id="pop_body"></tbody>
                </table>
                <%--<button value="确认" onclick="close_remark_pop()"/>--%>
            </div>
            <a id="bb" href="javascript:" class="closeBtn" onclick="close_remark_pop()"> <span>确认</span></a>
        </div>
    </section>

</div>
<script type="text/javascript">
    $(function () {
        var pop_th = $('#pop_tr th');
        pop_th.attr("hidden", 'hidden');
        pop_th.addClass('class_detail');
    })

    function getLabelDetail(base_id, label_name, customer_type, user_num) {
        $.ajax({
            type: "post",
            data: {"baseId": base_id, "labelName": label_name, "customerType": customer_type, "userNum": user_num},
            dataType: "json",
            url: "customService/getLabelCountDetail",
            async: false,
            success: function (data) {
                if (customer_type === "0") {
                    $("#label_detail").removeAttr("hidden");
                    $("#label_rate").removeAttr("hidden");
                    $.each(data.data, function (index, value) {
                        if (data.data.length > 0) {
                            $("#pop_body").append('<tr><td>' + value.name + '</td>' + '<td>' + value.rate + '%' + '</td></tr>');
                        }
                    });

                } else if (customer_type === "1") {
                    $("#label_detail_s").removeAttr("hidden");
                    $("#moning_rate").removeAttr("hidden");
                    $("#night_rate").removeAttr("hidden");
                    $.each(data.data, function (index, value) {
                        if (data.data.length > 0) {
                            $("#pop_body").append('<tr><td>' + value.name + '</td>' + '<td>' + value.morningRate + '%' + '</td><td>' + value.nightRate + '%' + '</td></tr>');
                        }
                    });
                }
            },
            error: function () {
                alert("系统异常，请稍后重试!");
            }
        });
        show_all();
    }

    function show_all() {
        $('#label_count_detail_pop').fadeIn();
    }


    function close_remark_pop() {
        $('.class_detail').attr("hidden", 'hidden');
        $('#label_count_detail_pop').fadeOut();
        $("#pop_body").empty();
    }

</script>
</body>
</html>