<%--
  Created by IntelliJ IDEA.
  User: tql
  Date: 2017/12/22
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${riskFlag eq 'autoRiskFlag'}">
    <tr>
        <td>老用户风控建议</td>
        <td>
            <c:choose>
                <c:when test="${showRiskJxOcModel ne 'null' and showRiskJxOcModel ne ''}">
                    <c:choose>
                        <c:when test="${showRiskJxOcModel.advice eq 'REVIEW'}">
                            建议人工复审 <br/>
                        </c:when>
                        <c:when test="${showRiskJxOcModel.advice eq 'REJECT'}">
                            拒绝 <br/>
                        </c:when>
                        <c:when test="${showRiskJxOcModel.advice eq 'PASS'}">
                            通过 <br/>
                        </c:when>
                        <c:otherwise>
                            暂无数据
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
        </td>
        <td colspan="2">老用户风控信息：
            <c:choose>
                <c:when test="${showRiskJxOcModel ne 'null' and showRiskJxOcModel ne ''}">
                    <c:forEach items="${showRiskJxOcModel.itemsList}" var="in">
                        ${in} |
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    暂无数据
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>风控数据库配置信息</td>
        <c:choose>
            <c:when test="${backConfigParamsMap ne 'null' and backConfigParamsMap ne '' }">
                <td>当前机审自动放款状态:
                <c:choose>
                    <c:when test="${backConfigParamsMap['AUTO_SWITCH'] eq '1'}">
                        已开启
                    </c:when>
                    <c:when test="${backConfigParamsMap['AUTO_SWITCH'] eq '0'}">
                        已关闭
                    </c:when>
                </c:choose>
                </td>
                <td>机审放款比例:${backConfigParamsMap['AUTO_LOAN_RATE']}%</td>
                <td></td>
            </c:when>
            <c:otherwise>
                <td colspan="3">无数据信息</td>
            </c:otherwise>
        </c:choose>
    </tr>
    <tr>
        <td>机审复审建议</td>
        <td>
            <c:choose>
                <c:when test="${showRiskAutoAndAutoLoanModel ne 'null' and showRiskAutoAndAutoLoanModel ne ''}">
                    <c:choose>
                        <c:when test="${showRiskAutoAndAutoLoanModel.advice eq 'REVIEW'}">
                            建议人工复审 <br/>
                        </c:when>
                        <c:when test="${showRiskAutoAndAutoLoanModel.advice eq 'REJECT'}">
                            建议人工复审 <br/>
                        </c:when>
                        <c:when test="${showRiskAutoAndAutoLoanModel.advice eq 'PASS'}">
                            通过 <br/>
                        </c:when>
                        <c:otherwise>
                            暂无数据
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
        </td>
        <td colspan="2">机审复审信息：
            <c:choose>
                <c:when test="${showRiskAutoAndAutoLoanModel ne 'null' and showRiskAutoAndAutoLoanModel ne ''}">
                   <c:forEach items="${showRiskAutoAndAutoLoanModel.itemsList}" var="in">
                       ${in} |
                   </c:forEach>
                </c:when>
                <c:otherwise>
                    暂无数据
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <c:choose>
        <c:when test="${borrow.autoLoanFlag ne '0' and borrow.autoLoanFlag ne null}">
            <tr>
                <td>机审放款审核信息</td>
                <c:choose>
                    <c:when test="${borrow.autoLoanFlag eq '1'}">
                        <td>机审放款成功</td>
                        <td colspan="2">

                        </td>
                    </c:when>
                    <c:when test="${borrow.autoLoanFlag eq '2'}">
                        <td>机审放款失败(走人工放款流程)</td>
                        <td colspan="2">

                        </td>
                    </c:when>
                </c:choose>
            </tr>
        </c:when>
        <c:when test="${borrow.autoLoanFlag eq '0' and borrow.autoLoanFlag ne null}">
            <tr>
                <td>机审放款审核信息</td>
                <td>机审放款审核建议：
                  <c:choose>
                      <c:when test="${autoLoanLastAdvice ne 'null' and autoLoanLastAdvice ne ''}">
                          <c:choose>
                              <c:when test="${autoLoanLastAdvice eq 'REVIEW'}">
                                  建议人工复审
                              </c:when>
                              <c:when test="${autoLoanLastAdvice eq 'REJECT'}">
                                  建议拒绝
                              </c:when>
                              <c:when test="${autoLoanLastAdvice eq 'PASS'}">
                                  建议放款
                              </c:when>
                          </c:choose>
                      </c:when>
                  </c:choose>
                </td>
                <td colspan="2">机审复审建议信息：
                    <c:choose>
                        <c:when test="${autoLoanMessage ne 'null' and autoLoanMessage ne ''}">
                            ${autoLoanMessage }
                        </c:when>
                        <c:otherwise>
                            暂无数据
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:when>
    </c:choose>
    <tr>
        <td>机审评估</td>
        <td>
            <c:choose>
                <c:when test="${showRiskAutoModel.advice eq 'REVIEW'}">
                    建议复审
                </c:when>
                <c:when test="${showRiskAutoModel.advice eq 'REJECT'}">
                    建议拒绝
                </c:when>
                <c:when test="${showRiskAutoModel.advice eq 'PASS'}">
                    建议通过
                </c:when>
                <c:otherwise>
                    未获取数据（建议复审）
                </c:otherwise>
            </c:choose>
        </td>
        <td  colspan="2">
            <c:choose>
                <c:when test="${fn:length(showRiskAutoModel.itemsList) > 0}">
                    <c:forEach items="${showRiskAutoModel.itemsList}" var="in">
                        ${in}
                    </c:forEach>
                </c:when>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>同盾</td>
        <c:choose>
            <c:when test="${TDModel eq 'null' or empty TDModel}">
                <td>未获取数据源（建议人工复审）</td>
                <td>暂无数据</td>
            </c:when>
            <c:otherwise>
                <td>
                    <c:forEach items="${TDModel }" var="map">
                        <c:choose>
                            <c:when test="${map.key == 'checkResult'}">
                                <c:choose>
                                    <c:when test="${map.value eq 'REVIEW'}">
                                        建议人工复审 <br/>
                                    </c:when>
                                    <c:when test="${map.value eq 'REJECT'}">
                                        拒绝 <br/>
                                    </c:when>
                                    <c:when test="${map.value eq 'PASS'}">
                                        通过 <br/>
                                    </c:when>
                                    <c:otherwise>
                                        暂无数据
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${TDModel }" var="map">
                        <c:choose>
                            <c:when test="${map.key eq '3个月内申请人在多个平台申请借款次数'}">
                                3个月内申请人在多个平台申请借款次数：&nbsp;${map.value } <br/>
                            </c:when>
                            <c:when test="${map.key eq '3个月内申请人在多个平台被放款_不包含本合作方次数'}">
                                3个月内申请人在多个平台被放款_不包含本合作方次数:&nbsp;${map.value } <br/>
                            </c:when>
                            <c:when test="${map.key eq 'tdFqzList'}">
                                <if test="${fn:length(TDModel['tdFqzList']) > 0 and !empty TDModel['tdFqzList'][0]}">
                                    命中反欺诈列表：${TDModel['tdFqzList'][0] } ...
                                </if>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </td>
            </c:otherwise>
        </c:choose>
        <td><a href="javascript:;" id="tdfqzContactBtn">查看详情</a></td>
    </tr>
    <tr>
        <td>淘宝认证</td>
        <td><c:choose>
            <c:when test="${gongXinBaoUrl eq 'null' or empty gongXinBaoUrl}">
                暂无报告
            </c:when>
            <c:otherwise><a target='_blank' href='${gongXinBaoUrl}'>报告展示</a></c:otherwise>
        </c:choose>
        </td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>白骑士</td>
        <c:choose>
            <c:when test="${BQSModel == 'null' or empty BQSModel}">
                <td>暂无数据</td>
                <td>暂无数据</td>
            </c:when>
            <c:otherwise>
                <td>
                    <c:forEach items="${BQSModel }" var="map">
                        <c:choose>
                            <c:when test="${map.key == 'checkResult'}">
                                <c:choose>
                                    <c:when test="${map.value eq 'REVIEW'}">
                                        建议人工复审
                                    </c:when>
                                    <c:when test="${map.value eq 'REJECT'}">
                                        拒绝
                                    </c:when>
                                    <c:when test="${map.value eq 'PASS'}">
                                        通过
                                    </c:when>
                                    <c:otherwise>
                                        暂无数据
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </td>
                <td>
                    <c:set var="tuoDouData" value=""/>
                    <c:choose>
                        <c:when test="${fn:length(BQSModel['detailMap']) >0 }">
                            <c:forEach items="${BQSModel['detailMap']}" var="map">
                                <c:set value="${map.key } : ${map.value }" var="tuoDouData" />
                            </c:forEach>
                            ${fn:substring(tuoDouData,0,75) } ...
                        </c:when>
                        <c:otherwise>
                            暂无数据
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:otherwise>
        </c:choose>
        <td class="textnum100">
            <a href="javascript:;" id="bqs-btn">查看详情</a>
        </td>
    </tr>
    <tr>
        <td class="tdGround" style="width: 180px;">宜信</td>
        <c:choose>
            <c:when test="${ZCAFModel eq 'null' or empty ZCAFModel}">
                <td>(未获取到数据)建议人工复审</td>
                <td>暂无数据</td>
            </c:when>
            <c:otherwise>
                <td>
                    <c:choose>
                        <c:when test="${ZCAFModel['score'] == 'null' or empty ZCAFModel['score']}">
                            <c:choose>
                                <c:when test="${ZCAFModel['checkResult'] eq 'REJECT'}">
                                    建议拒绝 <br/>
                                </c:when>
                                <c:when test="${ZCAFModel['checkResult'] eq 'REVIEW'}">
                                    建议人工复审 <br/>
                                </c:when>
                                <c:when test="${ZCAFModel['checkResult'] eq 'PASS'}">
                                    建议通过 <br/>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                            <c:when test="${ZCAFModel['checkResult'] eq 'REJECT'}">
                                建议拒绝 <br/>
                            </c:when>
                            <c:when test="${ZCAFModel['checkResult'] eq 'REVIEW'}">
                                建议人工复审 <br/>
                            </c:when>
                            <c:when test="${ZCAFModel['checkResult'] eq 'PASS'}">
                                建议通过 <br/>
                            </c:when>
                        </c:choose><br/>
                            ${ZCAFModel['score'] }
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:forEach items="${ZCAFModel }" var="map">
                        <c:if test="${map.key != 'checkResult'}">
                            <c:if test="${map.key != 'score' }">
                                ${map.value}<br/>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </td>
            </c:otherwise>
        </c:choose>
        <td></td>
    </tr>
    <tr>
        <td>中智诚</td>
        <c:choose>
            <c:when test="${ZZCFQZModel eq 'null' or empty ZZCFQZModel}">
                <td>未获取到数据源（建议复审）</td>
                <td>暂无数据</td>
            </c:when>
            <c:otherwise>
                <td>
                    <c:forEach items="${ZZCFQZModel }" var="map">
                        <c:choose>
                            <c:when test="${map.key == 'checkResult'}">
                                <c:choose>
                                    <c:when test="${map.value eq 'REVIEW'}">
                                        建议人工复审  <br/>
                                    </c:when>
                                    <c:when test="${map.value eq 'REJECT'}">
                                        拒绝   <br/>
                                    </c:when>
                                    <c:when test="${map.value eq 'PASS'}">
                                        通过  <br/>
                                    </c:when>
                                    <c:otherwise>
                                        暂无数据
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </c:forEach>

                </td>
                <td id="zzcfqz-btn">
                    <c:choose>
                        <c:when test="${ZZCHMDModel eq 'null' or empty ZZCHMDModel}">
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${ZZCHMDModel}" var="map">
                                <c:if test="${map.key eq '命中的黑名单所属的上报机构的数量' or map.key eq '命中的黑名单的数量'}">
                                    ${map.key}:${map.value} <br/>
                                </c:if>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${fn:length(ZZCFQZModel['riskList']) > 0}">
                            中智诚反欺诈:${ZZCFQZModel['riskList'][0]} ...
                        </c:when>
                    </c:choose>
                </td>
            </c:otherwise>
        </c:choose>
        <td  style="width: 280px;">
            <a href="javascript:;" id="zzfqz-btn">查看详情</a>
        </td>
    </tr>
</c:if>

