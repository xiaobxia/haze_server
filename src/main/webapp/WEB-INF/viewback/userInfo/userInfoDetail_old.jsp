<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
%>

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
        <!-- 个人信息 -->
        <fieldset>
            <table class="userTable">
                <tbody>
                <tr>
                    <td style="font-weight: bold">个人详情</td>
                    <td>
                        <table class="userTable">
                            <tr>
                                <td class="tdGround" style="width: 180px;">用户ID</td>
                                <td>${user.id}</td>
                                <td class="tdGround">姓名</td>
                                <td>${user.realname}</td>
                                <td class="tdGround">年龄</td>
                                <td>${age}</td>
                                <td class="tdGround">性别</td>
                                <td>${user.userSex}</td>
                            </tr>
                            <tr>
                                <td class="tdGround" style="width: 180px;">出生日期</td>
                                <td>${birthday}</td>
                                <td class="tdGround">身份证号</td>
                                <td>${user.idNumber}</td>
                                <td class="tdGround">联系方式</td>
                                <td>${user.userPhone}</td>
                                <td class="tdGround">婚姻</td>
                                <td>${user.maritalStatus}</td>
                            </tr>
                            <tr>
                                <td class="tdGround">常住地址</td>
                                <td colspan="3">${user.presentAddress}
                                    ${user.presentAddressDistinct}</td>
                                <%--<td class="tdGround">学历：</td>--%>
                                <%--<td>${user.education}</td>--%>

                                <td class="tdGround">居住时长：</td>
                                <td>${user.presentPeriod}</td>
                                <td class="tdGround">账号创建时间</td>
                                <td><fmt:formatDate value="${user.createTime}"
                                                    pattern="yyyy-MM-dd HH:mm:ss" /></td>
                            </tr>
                            <tr class="identity">
                                <%--<td class="tdGround" style="height: 100px; width: 180px;">学历证明:--%>
                                <%--</td>--%>
                                <%--<td><img src="" /></td>--%>
                                <td class="tdGround" style="height: 100px;">身份证</td>
                                <td colspan="2">
                                    <c:if test="${user.idcardImgZ!=null}">
                                        <img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgZ}"/>
                                    </c:if>
                                    <%--<img src="http://oyd863ahw.bkt.clouddn.com///mnt/img/qbm_04/04_77/04_77_868/20171026/20171026103514_1nbnx54bys_appTh.png " alt=""><br/>身份证正面--%>
                                </td>
                                <td colspan="2">
                                    <c:if test="${user.idcardImgF!=null}">
                                        <img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgF}" />
                                    </c:if>
                                    <%--<img src="http://oyd863ahw.bkt.clouddn.com///mnt/img/qbm_04/04_77/04_77_868/20171026/20171026103514_1nbnx54bys_appTh.png " alt=""><br/>身份证反面--%>
                                </td>
                                <td colspan="3">
                                    <c:if test="${user.headPortrait!=null}">
                                        <img class="imgPreview" src="${APP_IMG_URL['APP_IMG_URL']}${user.headPortrait}"/>
                                    </c:if>
                                    <%--<img src="http://oyd863ahw.bkt.clouddn.com///mnt/img/qbm_04/04_77/04_77_868/20171026/20171026103514_1nbnx54bys_appTh.png " alt=""><br/>用户活体检测照片--%>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold">银行卡信息</td>
                    <td>
                        <c:forEach items="${bankCardList}" var="card">
                            <table class="userTable">
                                <tr>
                                    <td class="tdGround">发卡行:</td>
                                    <td>${card.bankName}</td>
                                    <td class="tdGround" style="width:180px;">种类:</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${card.type==1}">信用卡</c:when>
                                            <c:when test="${card.type==3}">对公账号</c:when>
                                            <c:otherwise>借记卡</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="tdGround" >类型：</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${card.mainCard==0}">主卡</c:when>
                                            <c:otherwise>附卡${card.mainCard}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="tdGround">默认卡(是/否)</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${card.cardDefault==1}">是</c:when>
                                            <c:when test="${card.cardDefault==0}">否</c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdGround">卡号：</td>
                                    <td>${card.card_no}</td>
                                    <td class="tdGround">预留收号码:</td>
                                    <td>${card.phone}</td>
                                    <td class="tdGround">添加时间</td>
                                    <td colspan="3"><fmt:formatDate value="${card.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

                                </tr>
                            </table>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold">联系人</td>
                    <td>
                        <table class="userTable">
                            <tr>
                                <td class="tdGround" style="width: 180px;">关系</td>
                                <td>${user.fristContactRelation}</td>
                                <td class="tdGround">姓名</td>
                                <td>${user.firstContactName}</td>
                                <td class="tdGround">电话</td>
                                <td>${user.firstContactPhone}</td>
                                <td class="tdGround">来源</td>
                                <td>系统上传</td>
                            </tr>
                            <tr>
                                <td class="tdGround" style="width: 180px;">关系</td>
                                <td>${user.secondContactRelation}</td>
                                <td class="tdGround">姓名</td>
                                <td>${user.secondContactName}</td>
                                <td class="tdGround">电话</td>
                                <td>${user.secondContactPhone}</td>
                                <td class="tdGround">来源</td>
                                <td>系统上传</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </fieldset>
    </div>
</div>
</body>
</html>
