<%--
  Created by IntelliJ IDEA.
  User: Bouger
  Date: 2018/5/8
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${inflexibleAdvice ne 'null' and inflexibleAdvice ne ''}">
        <c:choose>
            <c:when test="${inflexibleAdvice eq 'REVIEW'}">
                人工复审
            </c:when>
            <c:when test="${inflexibleAdvice eq 'REJECT'}">
                拒绝
            </c:when>
            <c:when test="${inflexibleAdvice eq 'PASS'}">
                通过
            </c:when>
        </c:choose>
    </c:when>
</c:choose>
