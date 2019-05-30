<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = path + "/common/back";
%>
<c:set var="basePath" value="<%=basePath%>"></c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=7" />
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
        .userTable  table {
            border: none;
        }
        .userTable td  table {
            border-bottom: 0;
        }
        .userTable td a {
            color: #5dacd0;
        }
        .userTable tr.identity {
            text-align: center;
        }
        .userTable tr.identity img {
            margin: 10px 0 0;
            width: 320px;
        }
        .userTable tr.identity td:last-child img{
            height: 210px;
            width: auto;
        }
        .userTable {
            padding: 0;
            border: solid 1px #928989;
            width: 100%;
            line-height: 21px;
        }
        .tdGround {
            background: #f5f5f5 none repeat scroll 0 0;
            border: 1px solid darkgray;
            color: #555;
            font: 12px "Lucida Grande", Verdana, Lucida, Helvetica, Arial, 'Simsun',
            sans-serif;
            font-weight: bold;
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
        .tdGround img{
            width: 100px;
            transition:all 0.3s
        }
    </style>
</head>
<body>
<div class="pageContent">
    <div class="pageFormContent" layoutH="50" style="overflow: auto;">
        <div class="detail-page-main" style="position: relative">
            <div class="detail-card">
                <div class="title-wrap">
                    <div class="title">
                        <img src="${basePath}/images/loandetail/个人信息.png" alt="">
                        <span class="main-text">个人信息</span>
                        <span class="sub-text">(账号创建时间:<fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />)</span>
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
                                    <th>公司地址</th>
                                </tr>
                                <tr>
                                    <td>${user.presentAddress}
                                        ${user.presentAddressDistinct}</td>
                                    <td>${user.presentPeriod}</td>
                                    <td>${user.companyName}</td>
                                    <td>${user.companyAddress}</td>
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
                                        <img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgF}" />
                                    </c:if>
                                </div>
                                <div class="id-img-wrap">
                                    <c:if test="${user.headPortrait!=null}">
                                        <img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.headPortrait}"/>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="block-row">
                    <div class="sub-block">
                        <div class="sub-title">
                            3）银行卡信息
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
                                        <td><fmt:formatDate value="${card.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="block-row">
                    <div class="sub-block">
                        <div class="sub-title">
                            4）联系人
                        </div>
                        <div class="content">
                            <table class="detail-table">
                                <tr>
                                    <th>关系</th>
                                    <th>姓名</th>
                                    <th>电话</th>
                                    <th>来源</th>
                                </tr>
                                <tr>
                                    <td>${user.fristContactRelation}</td>
                                    <td>${user.firstContactName}</td>
                                    <td>${user.firstContactPhone}</td>
                                    <td>系统上传</td>
                                </tr>
                                <tr>
                                    <td>${user.secondContactRelation}</td>
                                    <td>${user.secondContactName}</td>
                                    <td>${user.secondContactPhone}</td>
                                    <td>系统上传</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
