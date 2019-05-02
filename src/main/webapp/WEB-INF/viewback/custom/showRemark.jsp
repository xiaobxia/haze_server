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
    <title>详细信息</title>
</head>
<body>
<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
    <thead>
    <tr >
        <th align="center"  >
            序号
        </th>
        <th align="center"  >
            添加时间
        </th>
        <th align="center"  >
            操作人
        </th>
        <th align="left"  >
            标签
        </th>
        <th align="center"  >
            备注
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="in" items="${remarkList}" varStatus="status">
        <tr>
            <td>
                ${status.count}
            </td>
            <td>
                <fmt:formatDate value="${in.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                ${in.jobName}
            </td>
            <td>
                ${remark.get(in.remarkFlag.toString())}
            </td>
            <td>
                ${in.remarkContent }
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
