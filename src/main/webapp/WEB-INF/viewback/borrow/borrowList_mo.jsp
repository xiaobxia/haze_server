<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
%>

<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>


<form id="pagerForm" onsubmit="return navTabSearch(this);"
      action="backBorrowOrder/getBorrowPage?bType=${bType}&myId=${params.myId}" method="post">
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
                        状态：
                        <select name="borrowStatus" class="textInput ">
                            <option value="">全部</option>
                            <c:forEach var="borrowStatus" items="${BORROW_STATUS_ALL}">
                                <c:choose>
                                    <c:when test="${bType=='fengk_Trial'}">
                                        <c:if test="${borrowStatus.key eq STATUS_CSTG or borrowStatus.key eq STATUS_DCS or borrowStatus.key eq STATUS_CSBH}">
                                            <option value="${borrowStatus.key}"
                                                    <c:if test="${borrowStatus.key eq params.borrowStatus}">selected="selected"</c:if> >${borrowStatus.value}</option>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${bType=='fengk_Review'||bType=='fengk_order'}">
                                        <c:if test="${borrowStatus.key eq STATUS_FSTG or borrowStatus.key eq STATUS_CSTG or borrowStatus.key eq STATUS_FSBH or borrowStatus.key eq STATUS_FSTG}">
                                            <option value="${borrowStatus.key}"
                                                    <c:if test="${borrowStatus.key eq params.borrowStatus}">selected="selected"</c:if> >${borrowStatus.value}</option>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${borrowStatus.key}"
                                                <c:if test="${borrowStatus.key eq params.borrowStatus}">selected="selected"</c:if> >${borrowStatus.value}</option>
                                    </c:otherwise>
                                </c:choose>
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
                   <%-- <td>
                        审核员:
                        <select name="reviewUser" id="reviewUser" class="textInput" onchange="">
                            <option value="">全部</option>
                            <option value="all_review" <c:if test="${params.reviewUser eq 'all_review'}">selected="selected"</c:if>>全部信审人员</option>
                            <option value="not_review" <c:if test="${params.reviewUser eq 'not_review'}">selected="selected"</c:if>>非信审人员</option>
                            <c:forEach var="backUser" items="${reviewUserInfos}">
                                <option value="${backUser.telephone}"  <c:if test="${backUser.telephone eq params.reviewUser}">selected="selected"</c:if> >${backUser.userName}</option>
                            </c:forEach>
                        </select>
                    </td>--%>
                    <td>审核时间：
                        <input type="text" name="reviewStartTime" id="reviewStartTime" value="${params.reviewStartTime}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
                        到<input type="text" name="reviewEndTime" id="reviewEndTime" value="${params.reviewEndTime}" class="date textInput readonly" datefmt="yyyy-MM-dd" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <td>
                        用户属性：
                        <select name="customerTypeStatus" class="textInput">
                            <option value="">全部</option>
                            <c:forEach var="customer" items="${customerType}">
                                <option value="${customer.key}"
                                        <c:if test="${customer.key eq 	params.customerTypeStatus}">selected="selected"</c:if> >${customer.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        用户流量属性：
                        <select name="channelInfoId" class="textInput">
                            <option value="">全部</option>
                            <c:forEach var="channelInfo" items="${channelInfoList}">
                                <option value="${channelInfo.id}"
                                        <c:if test="${channelInfo.id eq params.channelInfoId}">selected="selected"</c:if> >${channelInfo.channelName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        省份：
                        <select name="provinceId" id="province" class="textInput"
                                onchange="queryCitys(this.options[this.options.selectedIndex].value)">
                            <option value="">全部</option>
                            <c:forEach var="pro" items="${provinces}">
                                <option value="${pro.provinceId}"
                                        <c:if test="${pro.provinceId eq params.provinceId}">selected="selected"</c:if> >${pro.province}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        市区：
                        <select name="cityId" id="citymo" class="textInput">
                            <option value="">全部</option>
                        </select>
                    </td>
                    <td>
                        评分卡编号:
                        <select name="scoreCard" id="scoreCard" class="textInput" onchange="">
                            <option value="">全部</option>
                            <option value="all_scorecard" <c:if test="${params.scoreCard eq 'all_scorecard'}">selected="selected"</c:if>>全部评分卡</option>
                            <c:forEach var="code" items="${scoreCards}">
                                <option value="${code}"  <c:if test="${code eq params.scoreCard}">selected="selected"</c:if> >${code}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        借款金额:
                        <select id = "productAmount" name = "productAmount"></select>
                        <input type="hidden" value="${params.productAmount}" id="product_amount_choosed"/>
                    </td>

                </tr>
            </table>
        </div>
    </div>
    <div class="pageContent">
        <jsp:include page="${BACK_URL}/rightSubList">
            <jsp:param value="${params.myId}" name="parentId"/>
        </jsp:include>
        <table class="table" style="width: 100%;" layoutH="210" nowrapTD="false">
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
                    地域
                </th>
                <th align="center">
                    是否是老用户
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
                <th align="center" class="loanStatusTitle">
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
                    <td>
                            ${borrow.customerTypeName}
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
                    <td class="loanStatusName">
                            ${borrow.statusName }
                    </td>
                    <td>${borrow.channelName}</td>
                    <c:if test="${borrow.reviwRiskLabel == 1}">
                        <td>评分卡</td>
                    </c:if>
                    <c:if test="${borrow.reviwRiskLabel == 2}">
                        <td>人工否决：${borrow.reBackUserName}</td>
                    </c:if>
                    <c:if test="${borrow.reviwRiskLabel == 3}">
                        <td>${borrow.reBackUserName}</td>
                    </c:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="divider"></div>
        <div class="pageFoot">
            <div class="searchBar">
                <table class="searchContent">

                    <tbody>
                    <%--总计：&nbsp;&nbsp;待机审&nbsp;&nbsp;<span style="color:red;">${statisticMap['toAutoReview']}</span>&nbsp;&nbsp;笔&nbsp;&nbsp;&nbsp;&nbsp;待复审&nbsp;&nbsp;<span style="color:red;">${statisticMap['autoPassed']}</span>&nbsp;&nbsp;笔--%>
                    <td>
                        &nbsp;&nbsp;&nbsp;&nbsp;总计：
                    </td>
                    <td>待机审</td>
                    <td style="color:red;">
                        ${statisticMap['toAutoReview']}
                    </td>
                    <td>
                        笔
                    </td>
                    <td>
                        待复审
                    </td>
                    <td style="color:red;">
                        ${statisticMap['autoPassed']}
                    </td>
                    <td>
                        笔
                    </td>
                    <td>延迟未放款笔数：</td><td style="color:red;">${statisticMap['delayNoLoanCunt']}</td><td>笔</td>
                    <td>延迟待机审笔数：</td><td style="color:red;">${statisticMap['delayAutoReviewCunt']}</td><td>笔</td>
                    <td>放款失败笔数：</td><td style="color:red;">${statisticMap['loanFailedCunt']}</td><td>笔</td>
                    </tbody>
                    <tbody>
                    <%--&nbsp;&nbsp;&nbsp;&nbsp;今日：&nbsp;&nbsp;已审&nbsp;&nbsp;<span style="color:red;">${statisticMap['reviewedToday']}</span>&nbsp;&nbsp;笔&nbsp;&nbsp;&nbsp;&nbsp;已通过&nbsp;&nbsp;<span style="color:red;">${statisticMap['passedToday']}</span>&nbsp;&nbsp;笔&nbsp;&nbsp;&nbsp;&nbsp;通过率&nbsp;&nbsp;<span style="color:red;">${statisticMap['passedRateToday']}</span>--%>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;今日：</td>
                    <td>
                        已审
                    </td>通过率
                    <td style="color:red;">
                        ${statisticMap['reviewedToday']}
                    </td>
                    <td>
                        笔
                    </td>
                    <td>
                        已通过
                    </td>
                    <td style="color:red;">
                        ${statisticMap['passedToday']}
                    </td>
                    <td>
                        笔
                    </td>
                    <td>
                        通过率
                    </td>
                    <td style="color:red;">
                        ${statisticMap['passedRateToday']}
                    </td>
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

    showOptions();

    function isAllScoreCard(value){
        if(value=='all_scorecard'){
            $("#modelCode").val("all_scorecard");
            $("#variableVersion").val("all_scorecard");
        }
    }

    function queryCitys(provinceId) {
        if (null == provinceId || '' == provinceId) {
            return;
        }
        $("#citymo").empty();
        $("#citymo").append("<option value=''>全部</option>");
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
                        $("#citymo").append("<option value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
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
                                $("#citymo").append("<option selected='selected' value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
                            } else {
                                $("#citymo").append("<option value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
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
    if (renderLoanStatusName) {
        setTimeout(function () {
            renderLoanStatusName()
        }, 200)
    }
</script>
