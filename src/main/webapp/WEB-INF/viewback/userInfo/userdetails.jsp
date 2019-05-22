<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <title>用户详情</title>
    <style type="text/css">
        .userTable td {
            border-bottom: 1px solid #928989;
            border-right: 1px solid #928989;
            line-height: 31px;
            overflow: hidden;
            padding: 0 3px;
            vertical-align: middle;
            font-size: 14px;
        }

        .userTable td a {
            color: #5dacd0;
        }

        .userTable {
            padding: 0;
            border: solid 1px #928989;
            width: 100%;
            line-height: 21px;
        }

        .tdGround {
            background-color: #ededed;
        }

        .detailB th {
            border: 1px solid darkgray;
            color: #555;
            background: #f5ecec none repeat scroll 0 0;
            font-weight: bold;
            width: 100px;
            line-height: 21px;
        }

        /*   图片大小设置  */
        .tdGround img {
            width: 100px;
            transition: all 0.3s
        }

        /*   图片鼠标移动放大  */
        .tdGround img:hover {
            width: 550px;
        }
    </style>
</head>
<body>
<div class="pageContent">
    <div class="pageFormContent" layoutH="50" style="overflow: auto;">
        <div class="detail-page-main" style="position: relative">
            <div class="detail-card">
                <div class="title-wrap">
                    <div id="report-btn" class="report-bth blue-click-btn">运营商报告</div>
                    <div class="title">
                        <img src="${basePath}/images/loandetail/个人信息.png" alt="">
                        <span class="main-text">个人信息</span>
                        <span class="sub-text">(账号创建时间:<fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>)</span>
                    </div>
                </div>
                <div class="block-row">
                    <div class="sub-block">
                        <div class="sub-title">
                            1）个人详情
                        </div>
                        <div class="content">
                            <table class="detail-table">
                                <tr>
                                    <th>用户ID</th>
                                    <th>姓名</th>
                                    <th>年龄</th>
                                    <th>性别</th>
                                    <th>出生日期</th>
                                    <th>身份证号</th>
                                    <th>联系方式</th>
                                    <th>婚姻</th>
                                    <th>学历</th>
                                </tr>
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.realname}</td>
                                    <td>${age}</td>
                                    <td>${user.userSex}</td>
                                    <td>${birthday}</td>
                                    <td>${user.idNumber}</td>
                                    <td>${user.userPhone}</td>
                                    <td>${user.maritalStatus}</td>
                                    <td>${user.education}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="block-row">
                    <div class="sub-block">
                        <div class="sub-title">
                            2）地址身份证
                        </div>
                        <div class="content">
                            <table class="detail-table">
                                <tr>
                                    <th>常住地址</th>
                                    <th>居住时长</th>
                                    <th>公司</th>
                                    <th>公司电话</th>
                                    <th>公司地址</th>
                                </tr>
                                <tr>
                                    <td>${user.presentAddress}
                                        ${user.presentAddressDistinct}</td>
                                    <td>${user.presentPeriod}</td>
                                    <td>${user.companyName}</td>
                                    <td>${user.companyPhone}</td>
                                    <td>${user.companyAddress} ${user.companyAddressDistinct}</td>
                                </tr>
                            </table>
                            <div class="imgs-block identity">
                                <div class="id-img-wrap">
                                    <c:if test="${user.idcardImgZ!=null}">
                                        <img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgZ}"/>
                                    </c:if>
                                </div>
                                <div class="id-img-wrap">
                                    <c:if test="${user.idcardImgF!=null}">
                                        <img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgF}"/>
                                    </c:if>
                                </div>
                                <div class="id-img-wrap">
                                    <c:if test="${user.headPortrait!=null}">
                                        <img class="imgPreview"
                                             src="${APP_IMG_URL['APP_IMG_URL']}${user.headPortrait}"/>
                                    </c:if>
                                </div>
                                <c:forEach var="image" items="${InfoImage}" varStatus="status">
                                    <div class="id-img-wrap">
                                        <img src="${APP_IMG_URL['APP_IMG_URL']}${image.url}"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="block-row">
                    <div>
                        <div class="sub-block">
                            <div class="sub-title">
                                3）联系人
                            </div>
                            <div class="content">
                                <table class="detail-table">
                                    <tr>
                                        <th>关系</th>
                                        <th>姓名</th>
                                        <th>电话</th>
                                        <th>来源</th>
                                        <th></th>
                                    </tr>
                                    <tr>
                                        <td>${user.fristContactRelation}</td>
                                        <td>${user.firstContactName}</td>
                                        <td>${user.firstContactPhone}</td>
                                        <td>系统上传</td>
                                        <td><a rel="tree161" class="blue-click-btn"
                                               href="userManage/gotoUserContacts?userId=${user.id}" target=navTab
                                               width="820" height="420" rel="jbsxBox" mask="true">手机通讯录</a></td>
                                    </tr>
                                    <tr>
                                        <td>${user.secondContactRelation}</td>
                                        <td>${user.secondContactName}</td>
                                        <td>${user.secondContactPhone}</td>
                                        <td>系统上传</td>
                                        <td></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="block-row">
                    <div class="sub-block">
                        <div class="sub-title">
                            4）银行卡信息
                        </div>
                        <div class="content">
                            <table class="detail-table">
                                <tr>
                                    <th>发卡行</th>
                                    <th>种类</th>
                                    <th>类型</th>
                                    <th>默认卡(是/否)</th>
                                    <th>卡号</th>
                                    <th>预留收号码</th>
                                    <th>添加时间</th>
                                </tr>
                                <c:forEach items="${bankCardList}" var="card">
                                    <tr>
                                        <td>${card.bankName}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${card.type==1}">信用卡</c:when>
                                                <c:when test="${card.type==3}">对公账号</c:when>
                                                <c:otherwise>借记卡</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${card.mainCard==0}">主卡</c:when>
                                                <c:otherwise>附卡${card.mainCard}</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${card.cardDefault==1}">是</c:when>
                                                <c:when test="${card.cardDefault==0}">否</c:when>
                                            </c:choose>
                                        </td>
                                        <td>${card.card_no}</td>
                                        <td>${card.phone}</td>
                                        <td><fmt:formatDate value="${card.createTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="block-row">
                    <div class="sub-block">
                        <div class="sub-title">
                            5）近期借款记录
                        </div>
                        <div class="content">
                            <table class="detail-table">
                                <tr>
                                    <th>序号</th>
                                    <th>订单号</th>
                                    <th>借款金额(元)</th>
                                    <th>借款期限</th>
                                    <th>服务费利率(万分之一)</th>
                                    <th>服务费(元)</th>
                                    <th>申请时间</th>

                                    <th>放款时间</th>
                                    <th>子类型</th>
                                    <th>状态</th>
                                </tr>
                                <c:forEach var="borrow" items="${userBorrows.items }" varStatus="status">
                                    <tr>
                                        <td>
                                                ${status.count}
                                        </td>
                                        <td>
                                                ${borrow.outTradeNo}
                                        </td>
                                        <td>
                                            <fmt:formatNumber type="number" value="${borrow.moneyAmount/100}"
                                                              pattern="0.00" maxFractionDigits="2"/>
                                        </td>
                                        <td>${borrow.loanTerm }</td>
                                        <td>
                                            <fmt:formatNumber type="number" value="${borrow.apr}" pattern="0.00"
                                                              maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber type="number" value="${borrow.loanInterests/100}"
                                                              pattern="0.00" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${borrow.orderTime }"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${borrow.loanTime }"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>${borrow.orderTypeName }</td>
                                        <td>${borrow.statusName }</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="operatorHtml">
    <iframe src="${operatorHtml}">
        <p>您的浏览器不支持  iframe 标签。</p>
    </iframe>
    <span id="operatorHtml-close" class="blue-click-btn">关闭</span>
</div>
</body>
</html>
<script>
    $('#report-btn').click(function () {
        $('#operatorHtml').show()
    })
    $('#operatorHtml-close').click(function () {
        $('#operatorHtml').hide()
    })
</script>

