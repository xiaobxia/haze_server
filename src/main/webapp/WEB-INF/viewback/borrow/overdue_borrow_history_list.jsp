<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>逾期记录</title>
</head>
<body>
<div class="pageHeader">
</div>
<div class="pageContent">
    <jsp:include page="${BACK_URL}/rightSubList">
        <jsp:param value="${params.myId}" name="parentId"/>
    </jsp:include>
    <table class="table"
           style="width: 100%;display: table;border-collapse: separate;border-spacing: 2px;border-color: grey;"
           layouth="115" nowraptd="false" border="1">
        <thead>
        <tr>
            <th align="center" width="50">
                序号
            </th>
            <th align="center" width="60">
                借款编号
            </th>
            <th align="center" width="100">
                联系人姓名
            </th>
            <th align="center" width="100">
                联系人类型
            </th>
            <th align="center" width="100">
                关系
            </th>
            <th align="center" width="100">
                联系人电话
            </th>
            <th align="center" width="100">
                跟进等级
            </th>
            <th align="center" width="100">
                操作时催收状态
            </th>
            <th align="center" width="100">
                催收类型
            </th>
            <th align="center" width="100">
                催收内容
            </th>
            <th align="center" width="100">
                催收员
            </th>
            <th align="center" width="100">
                创建时间
            </th>
            <th align="center" width="100">
                逾期天数
            </th>
            <th align="center" width="100">
                还款时间
            </th>
            <th align="center" width="100">
                减免记录
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="borrow" items="${items}" varStatus="status">
            <tr target="recordId" rel="${borrow.id}">
                <td>
                        ${status.count}
                </td>
                <td>
                        ${borrow.loanId}
                </td>
                <td>
                        ${borrow.contactName}
                </td>
                <td>
                    <c:if test="${borrow.contactType == 1 }">
                        紧急联系人
                    </c:if>
                    <c:if test="${borrow.contactType == 2 }">
                        通讯录联系人
                    </c:if>
                </td>
                <td>
                        ${borrow.relation}
                </td>
                <td>
                        ${borrow.contactPhone}
                </td>
                <td>
                        ${borrow.stressLevel}
                </td>
                <td>
                    <c:if test="${borrow.orderState == 0 }">
                        待催收
                    </c:if>
                    <c:if test="${borrow.orderState == 1 }">
                        催收中
                    </c:if>
                    <c:if test="${borrow.orderState == 2 }">
                        承诺还款
                    </c:if>
                    <c:if test="${borrow.orderState == 3 }">
                        委外中
                    </c:if>
                    <c:if test="${borrow.orderState == 4 }">
                        委外成功
                    </c:if>
                    <c:if test="${borrow.orderState == 5 }">
                        催收成功
                    </c:if>
                </td>
                <td>
                    <c:if test="${borrow.collectionType == 1 }">
                        电话催收
                    </c:if>
                    <c:if test="${borrow.collectionType == 2 }">
                        短信催收
                    </c:if>
                </td>
                <td>
                        ${borrow.content}
                </td>
                <td>
                        ${borrow.collectionPerson}
                </td>
                <td>
                        <fmt:formatDate value="${borrow.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                        ${borrow.overdueDays}
                </td>
                <td>
                        ${borrow.repaymentDescript}
                </td>
                <td>
                        ${borrow.reductionDescript}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>