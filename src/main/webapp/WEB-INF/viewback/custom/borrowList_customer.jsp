<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="customService/searchBorrowList?bType=${bType}&myId=${params.myId}" method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        订单号:
                        <input type="text" name="outTradeNo"
                               value="${params.outTradeNo }"/>
                    </td>
                    <td>
                        用户姓名:
                        <input type="text" name="realname"
                               value="${params.realname }"/>
                    </td>
                    <td>
                        手机:
                        <input type="text" name="userPhone"
                               value="${params.userPhone }"/>
                    </td>
                    <!-- 					<td> -->
                    <!-- 						公司名称: -->
                    <!-- 						<input type="text" name="companyName" -->
                    <%-- 							value="${params.companyName }" /> --%>
                    <!-- 					</td> -->
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
        <table class="table" style="width: 100%;" layoutH="138" nowrapTD="false">
            <thead>
            <tr>
                <th align="center">
                    序号
                </th>
                <th align="center">
                    订单ID
                </th>
                <th align="left">
                    订单号
                </th>
                <th align="center">
                    姓名
                </th>
                <th align="center">
                    手机号
                </th>
                <th align="center">
                    区域
                </th>
                <%--<th align="center">
                    是否是老用户
                </th>--%>
                <th align="center">
                    成功还款次数
                </th>
                <th align="center">
                    借款金额(元)
                </th>
                <th align="center">
                    借款期限
                </th>
                <th align="center">
                    服务费利率(万分之一)
                </th>
                <th align="center">
                    服务费(元)
                </th>
                <th align="center">
                    申请时间
                </th>
                <th align="center">
                    放款时间
                </th>

                <th align="center">更新时间</th>
                <th align="center">
                    子类型
                </th>
                <th align="center">
                    状态
                </th>
                <th align="center">
                    渠道商名称
                </th>
                <th align="center">
                    复审人员姓名
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="borrow" items="${pm.items }" varStatus="status">
                <tr target="sid_support" rel="${borrow.id }">
                    <td>
                            ${status.count}
                    </td>
                    <td>
                            ${borrow.id}
                    </td>
                    <td>
                            ${borrow.yurref}
                    </td>
                    <td>
                            ${borrow.realname}
                    </td>
                    <td>
                            ${borrow.userPhone }

                    </td>
                    <td>
                            ${borrow.area }

                    </td>
                   <%-- <td>
                            ${borrow.customerTypeName}
                    </td>--%>
                    <td class="loanSuccessCount">
                        ${borrow.loanCount}
                    </td>
                    <td>
                            <%--  							${borrow.moneyAmount/100 } --%>
                        <fmt:formatNumber type="number" value="${borrow.moneyAmount/100}" pattern="0.00"
                                          maxFractionDigits="0"/>
                    </td>
                    <td>

                            ${borrow.loanTerm }

                    </td>
                    <td>

                        <fmt:formatNumber type="number" value="${borrow.apr}" pattern="0.00" maxFractionDigits="0"/>
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${borrow.loanInterests/100}" pattern="0.00"
                                          maxFractionDigits="2"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${borrow.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>


                    <td>
                        <fmt:formatDate value="${borrow.loanTime }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td><fmt:formatDate value="${borrow.updatedAt }"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>

                    <td>
                            ${appName}
                    </td>
                    <td>
                            ${borrow.statusName }
                    </td>
                    <td>${borrow.channelName}</td>
                    <td>${borrow.reviewBackUserName}</td>
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

    showOptions();

    if ("${message}") {
        alertMsg.error(${message});
    }

    function queryCitys(provinceId) {
        if (null == provinceId || '' == provinceId) {
            return;
        }
        console.log(provinceId);
        $(".city").empty();
        $(".city").append("<option value=''>全部</option>");
        $.ajax({
            global: false,
            type: "POST",
            url: "backBorrowOrder/queryCity?provinceId=" + provinceId,
            dataType: "json",
            success: function (data) {
                var obj = data;
                if (0 == obj.code) {
                    var citys = obj.data;
                    for (var i in citys) {
                        $(".city").append("<option value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
                    }
                } else {
                    alert(obj.message);
                }
            },
            error: function () {
                alert("加载失败！");
            }
        });

    }

    function showOptions() {
        var provinceId = '${params.provinceId}';
        if (null != provinceId && '' != provinceId) {
            $.ajax({
                type: "POST",
                url: "backBorrowOrder/queryCity?provinceId=" + provinceId,
                dataType: "json",
                success: function (data) {
                    var obj = data;
                    if (0 == obj.code) {
                        var citys = obj.data;
                        for (var i in citys) {
                            if ('${params.cityId}' == citys[i].cityId) {
                                $(".city").append("<option selected='selected' value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
                            } else {
                                $(".city").append("<option value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
                            }

                        }
                    } else {
                        alert(obj.message);
                    }
                },
                error: function () {
                    alert("加载失败！");
                }
            });
        }
    }
    if (renderLoanSuccessCount) {
        renderLoanSuccessCount()
    }
</script>