<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
                  <form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="channel/getChannelInfoPage?myId=${params.myId}" method="post">
						<sys:message content="${message}"/>
                                <div class="row">
                                	<div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label">当前期数：</label>
                                            <div class="col-sm-7">
                                            	<form:input path="periods" readonly="true" htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>     
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">当前奖金：</label>
                                            <div class="col-sm-7">
	                                            <form:input path="awardMoney" readonly="true" htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>    
                                    </div>
                                </div>
                                <div style=" height:20px;"></div>
                                 <div class="row">
                                	<div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label">中奖用户：</label>
                                            <div class="col-sm-7">
                                            	<form:input path="userName" readonly="true" htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>     
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">中奖号码：</label>
                                            <div class="col-sm-7">
	                                           <form:input path="drawrollsNumber" readonly="true" htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>    
                                    </div>
                                </div>
                                <div style=" height:20px;"></div>
                                 <div class="row">
                                	<div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label"><font color="red">*</font>第三方名称：</label>
                                            <div class="col-sm-7">
                                            	<form:input path="threeName" htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>     
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label"><font color="red">*</font>第三方指数：</label>
                                            <div class="col-sm-7">
	                                          <form:input path="threeIndex" htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>    
                                    </div>
                                </div>
                                <div style=" height:20px;"></div>
                                 <div class="row">
                                	<div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label">奖金更新时间：</label>
                                            
                                            <div class="col-sm-7">
                                             <c:if test="${not empty jsAwardRecord.id}">
                                            	<fmt:formatDate value="${jsAwardRecord.moneyUpdateTime}" type="both" dateStyle="medium"/>
                                            	 </c:if>
                                            </div>
                                           
                                        </div>     
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">开奖时间：</label>
                                            <div class="col-sm-7">
	                                            <c:if test="${not empty jsAwardRecord.id}">
                                            	<fmt:formatDate value="${jsAwardRecord.darwTime}" type="both" dateStyle="medium"/>
                                            	 </c:if>
                                            </div>
                                        </div>    
                                    </div>
                                </div>
                                <div style=" height:20px;"></div>
                                <div class="row">
                                	<div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label">发奖时间：</label>
                                            
                                            <div class="col-sm-7">
                                             <c:if test="${not empty jsAwardRecord.id}">
                                            	<fmt:formatDate value="${jsAwardRecord.awardTime}" type="both" dateStyle="medium"/>
                                            	 </c:if>
                                            </div>
                                           
                                        </div>     
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">开奖结果：</label>
                                            <div class="col-sm-7">	                                           
<%--                                             		<form:input path="status" readonly="true" htmlEscape="false" maxlength="100" class="form-control"/> --%>
<%-- 												<c:if test="${jsAwardRecord.status=='0'}">未开奖</c:if> --%>
<%-- 												<c:if test="${jsAwardRecord.status=='1'}">已开奖</c:if> --%>
<%-- 												<c:if test="${jsAwardRecord.status=='2'}">已发放</c:if>                                      	 --%>

												<select name ="status" class="form-control m-b">
													<option value="0" label="未开奖" <c:if test="${jsAwardRecord.status=='0'}">selected</c:if>></option>
													<option value="1" label="已开奖" <c:if test="${jsAwardRecord.status=='1'}">selected</c:if>></option>
													<option value="2" label="已发放" <c:if test="${jsAwardRecord.status=='2'}">selected</c:if>></option>
												</select>

										</div>
                                        </div>    
                                    </div>
                                </div>
                                <div style=" height:20px;"></div>
                                <div class="form-group row">
                                	<label class="col-sm-2 control-label" style="min-width:122px;">备注：</label>
                                    <div class="col-md-8">
                                        <form:textarea path="remark" htmlEscape="false" rows="3" maxlength="200" class="form-control"/>
                                    </div>
                                </div>
                          <div style="height:20px;"></div>
                          <div class="from-group clearfix">
                         	<div class="form-actions col-sm-4 col-sm-offset-1">
								<shiro:hasPermission name="xjCard:jsAwardRecordEditor:view"><input id="btnSubmit" class="btn btn-sn btn-success" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
								<!-- <input id="btnCancel" class="btn btn-white " type="button" value="返 回" onclick="history.go(-1)"/> -->
							</div>
						</div>
                      

                 </div>
                </div>
            </div>
          </div>
        </div>
</body>
</html>