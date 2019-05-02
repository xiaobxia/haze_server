<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
 
                   <form:form id="inputForm" modelAttribute="jsLoanPerson" action="${ctx}/xianjin/sysXianjinCard/jsLoanPersonSave"  method="post" class="form-horizontal">
						<form:hidden path="id"/>
						<sys:message content="${message}"/>
                                <div class="row">
                                	<div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label">用户名称：</label>
                                            <div class="col-sm-7">
                                            	<form:input path="name"   htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>     
                                    </div>
                                    <div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label">身份证号：</label>
                                            <div class="col-sm-7">
                                            	<form:input path="idNumber" htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>     
                                    </div>
                                   
                                </div>
                                <div style=" height:20px;"></div>
                                 <div class="row">
                                	 <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">手机号码：</label>
                                            <div class="col-sm-7">
	                                            <form:input path="phone"  readonly="true"  htmlEscape="false" maxlength="100" class="form-control"/>
                                            </div>
                                        </div>    
                                    </div> 
                                    <div class="col-md-6">
                                       <div class="form-group">
                                            <label class="col-sm-4 control-label">添加时间：</label>
                                            
                                            <div class="col-sm-7">
                                             <c:if test="${not empty jsLoanPerson.id}">
                                            	<fmt:formatDate value="${jsLoanPerson.createdAt}" type="both" dateStyle="medium"/>
                                            	 </c:if>
                                            </div>
                                           
                                        </div>     
                                    </div>                                   
                                </div>
                                
                               
                                
                          <div style="height:20px;"></div>
                          <div class="from-group clearfix">
                         	<div class="form-actions col-sm-4 col-sm-offset-1">
<%-- 								<shiro:hasPermission name="xjCard:jsAwardRecordEditor:view"> --%>
								<input id="btnSubmit" class="btn btn-sn btn-success" type="submit" value="保 存"/>&nbsp;
<%-- 								</shiro:hasPermission> --%>
								<!-- <input id="btnCancel" class="btn btn-white " type="button" value="返 回" onclick="history.go(-1)"/> -->
							</div>
						</div>
                        </form:form>

                 </div>
                </div>
            </div>
          </div>
        </div>
</body>
</html>