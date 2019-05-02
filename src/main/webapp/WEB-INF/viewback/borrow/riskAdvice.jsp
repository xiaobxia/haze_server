<%--
  Created by IntelliJ IDEA.
  User: xiefei
  Date: 2018/05/23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tomcat.apache.org/myfunction-taglib" prefix="myFunction" %>
<fieldset>
    <legend class="riskTitle">审核建议</legend>
    <table class="userTable riskTable">
        <tr>
            <td>cutoff线</td>
            <td>${riskModelOrder.cutoff}</td>
            <td>复审范围</td>
            <td><span>上限：</span><span>${riskModelOrder.reviewUp}</span>   <span>下限：</span><span>${riskModelOrder.reviewDown}</span></td>
        </tr>
        <tr>
            <td>当前模型</td>
            <td colspan="3">
                <c:if test="${riskModelOrder.modelCode != null && riskModelOrder.variableVersion != null}">
                    <c:choose>
                        <c:when test="${riskModelOrder.modelCode == 0 && riskModelOrder.variableVersion == 0}">
                            人工
                        </c:when>
                        <c:otherwise>
                            评分卡 ${riskModelOrder.modelCode}_${riskModelOrder.variableVersion}
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </td>
        </tr>
        <tr>
            <%--硬指标建议 备注--%>
        <tr>
            <td style="width: 398px;">硬指标建议：</td>
            <td><%@include file="RiskRequisiteCommonPage.jsp"%></td>
            <td style="width: 398px;">备注：</td>
            <td><%@include file="RiskRequisiteRemarkCommonPage.jsp"%></td>
        </tr>
        </tr>
        <tr>
            <td>老用户建议</td>
            <td>
                <c:choose>
                    <c:when test="${oldCustomerAdvice ne 'null' and oldCustomerAdvice ne ''}">
                        <c:choose>
                            <c:when test="${oldCustomerAdvice eq 'REVIEW'}">
                                人工复审
                            </c:when>
                            <c:when test="${oldCustomerAdvice eq 'REJECT'}">
                                拒绝
                            </c:when>
                            <c:when test="${oldCustomerAdvice eq 'PASS'}">
                                通过
                            </c:when>
                        </c:choose>
                    </c:when>
                </c:choose>
            </td>
            <td>备注</td>
            <td>
                <c:choose>
                    <c:when test="${oldCustomerAdviceReason ne 'null' and oldCustomerAdviceReason ne ''}">
                        ${oldCustomerAdviceReason }
                    </c:when>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>评分卡建议</td>
            <td>
                <c:choose>
                    <c:when test="${riskModelOrder.modelAdvice==0}">
                        通过
                    </c:when>
                    <c:when test="${riskModelOrder.modelAdvice==1}">
                        人工复审
                    </c:when>
                    <c:when test="${riskModelOrder.modelAdvice==2}">
                        拒绝
                    </c:when>
                </c:choose>
            </td>
            <td>备注</td>
            <td>${riskModelOrder.modelScore}</td>
        </tr>
        <tr>
            <td>人工复审建议</td>
            <td>
                <c:choose>
                    <c:when test="${riskModelOrder.personReviewAdvice==0}">
                        通过
                    </c:when>
                    <c:when test="${riskModelOrder.personReviewAdvice==1}">
                        人工复审
                    </c:when>
                    <c:when test="${riskModelOrder.personReviewAdvice==2}">
                        拒绝
                    </c:when>
                </c:choose>
            </td>
            <td>备注</td>
            <td>${borrow.verifyReviewRemark}</td>
        </tr>
    </table>
</fieldset>