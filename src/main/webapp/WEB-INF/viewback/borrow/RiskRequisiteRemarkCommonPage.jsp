<%--
  Created by IntelliJ IDEA.
  User: Bouger
  Date: 2018/5/8
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${inflexibleAdviceReason ne 'null' and inflexibleAdviceReason ne ''}">
        ${inflexibleAdviceReason }
    </c:when>
</c:choose>