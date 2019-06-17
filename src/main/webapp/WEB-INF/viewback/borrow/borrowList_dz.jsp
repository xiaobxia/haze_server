<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="backBorrowOrder/getBorrowPageByStatus/${params.borrowStatusStr}?bType=${params.bType}&myId=${params.myId}"
      method="post">
    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>订单号: <input type="text" name="outTradeNo"
                                    value="${params.outTradeNo }" id="outTradeNo"/>
                    </td>
                    <td>用户姓名: <input type="text" name="realname"
                                     value="${params.realname }" id="realname"/>
                    </td>
                    <td>手机: <input type="text" name="userPhone"
                                   value="${params.userPhone }" id="userPhone"/>
                    </td>

                    <td>放款日期: <input type="text" name="startloanTime"
                                     id="startloanTime" value="${params.startloanTime}"
                                     class="date textInput readonly" datefmt="yyyy-MM-dd"
                                     readonly="readonly"/>

                    </td>
                    <td>至: <input type="text" name="endloanTime" id="endloanTime"
                                  value="${params.endloanTime}" class="date textInput readonly"
                                  datefmt="yyyy-MM-dd" readonly="readonly"/>
                    </td>
                   <%-- <td>
                        放款账户：
                        <select name="capitalType" id="capitalType">
                            <option value="">全部</option>
                            <c:forEach var="capitalType" items="${LOAN_ACCOUNTMap}">

                                <option value="${capitalType.key}"
                                        <c:if test="${capitalType.key eq params.capitalType}">selected="selected"</c:if> >
                                        ${fn:substring(capitalType.value, 0, fn:indexOf(capitalType.value, ";;") )}
                                </option>
                            </c:forEach>
                        </select>
                    </td>--%>

                    <td>
                    借款金额:
                    <select id = "productAmount" name = "productAmount"></select>
                    <input type="hidden" value="${params.productAmount}" id="product_amount_choosed"/>
                </td>

                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
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
        <table class="table" style="width: 100%;" layoutH="220"
               nowrapTD="false">
            <thead>
            <tr>
                <th align="center">序号</th>
                <th align="left">订单号</th>
                <th align="left">第三方流水号</th>
                <th align="center">姓名</th>
                <th align="center">手机号</th>
                <th align="center">成功还款次数</th>
                <th align="center">借款金额(元)</th>
                <th align="center">到账金额(元)</th>
                <th align="center">天数</th>
                <th align="center">服务费利率(万分之一)</th>
                <th align="center">下单时间</th>

                <th align="center">放款时间</th>
                <th align="center" class="loanStatusTitle">状态</th>
                <!-- 						<th align="center"  > -->
                <!-- 							操作 -->
                <!-- 						</th> -->
            </tr>
            </thead>
            <tbody>
            <c:forEach var="borrow" items="${pm.items }" varStatus="status">
                <tr target="sid_support" rel="${borrow.id }">
                    <td>${status.count}</td>
                    <td>${borrow.yurref}</td>
                    <td>${borrow.outTradeNo}</td>
                    <td>${borrow.realname}</td>
                    <td>${borrow.userPhone }</td>
                    <%--<td>${borrow.customerTypeName}</td>--%>
                  <td class="loanSuccessCount">${borrow.loanCount}</td>
                    <td><fmt:formatNumber type="number"
                                          value="${borrow.moneyAmount/100}" pattern="0.00"
                                          maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number"
                                          value="${borrow.intoMoney/100}" pattern="0.00"
                                          maxFractionDigits="0"/></td>
                    <td>${borrow.loanTerm }</td>
                    <td>${borrow.apr }</td>
                    <td><fmt:formatDate value="${borrow.orderTime }"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>


                    <td><fmt:formatDate value="${borrow.loanTime }"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td class="loanStatusName">放款成功 <%--  ${borrow.statusName }	 --%>
                    </td>
                    <!-- 									<td> -->
                        <%--  	<a href="backBorrowOrder/getBorrowDetailById?selectId=${borrow.id }&parentId=${params.myId}"  target="dialog"  width="810" height="550" >查看</a> --%>
                    <!-- 							</td> -->
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <div class="divider"></div>
        <div class="pageFoot">
            <div class="searchBar">
                <table class="searchContent">
                    <tbody>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;总计：</td>

                    <td>放款总额</td>
                    <td style="color: red;">${moneyAmountSum }</td>
                    <td>元</td>
                    </tbody>
                    <tbody>
                    <td></td>
                    <td>实际放款总额</td>
                    <td style="color: red;">${intoMoneySum }</td>
                    <td>元</td>
                    </tbody>

                </table>
            </div>
        </div>
        <c:set var="page" value="${pm }"></c:set>
        <!-- 分页 -->
        <%@ include file="../page.jsp" %>
    </div>
</form>

<script type="text/javascript">
    if ("${message}") {
        alertMsg.error(${message});
    }

    function changeDzExcel(obj) {

        var href = $(obj).attr("href");
        var outTradeNo = $("#outTradeNo").val();
        var realname = $("#realname").val();
        var userPhone = $("#userPhone").val();
        var companyName = $("#companyName").val();
        var startloanTime = $("#startloanTime").val();
        var endloanTime = $("#endloanTime").val();
        var capitalType = $("#capitalType").val();

        var toHref = href + "&capitalType=" + capitalType + "&outTradeNo=" + outTradeNo + "&realname=" + realname + "&userPhone=" + userPhone + "&companyName=" + companyName + "&startloanTime=" + startloanTime + "&endloanTime=" + endloanTime;

        $(obj).attr("href", toHref);
    }
    if (renderLoanStatusName) {
        setTimeout(function () {
            renderLoanStatusName()
        }, 200)
    }
    if (renderLoanSuccessCount) {
        setTimeout(function () {
            renderLoanSuccessCount()
        }, 200)
    }
</script>