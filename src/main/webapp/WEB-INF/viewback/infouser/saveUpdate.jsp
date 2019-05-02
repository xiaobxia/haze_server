<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/common/back";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>修改</title>
<style type="text/css">
	.pageFormContent dl{
		width: 50%!important;
	}
	.pageFormContent dl dt{
		width:20%;
	}
	.pageFormContent dl dd{
	width:30%;
	}
	.pageFormContent dl dd input{
		width:100%;
	}
</style>
</head>
<body>
	<div class="pageContent">
		<form name="issueForm" method="post"	action="zbUser/update?parentId=${params.parentId }&type=${params.type}" onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${companyMessage.user.id }"/>
			<div class="pageFormContent" layoutH="55">
				<fieldset>
					<legend>账户信息</legend>
					<dl>
						<dt>用户名:</dt>
						<dd>
							<input name="userAccount" value="${companyMessage.user.userAccount }"
								minlength="6" maxlength="16" class="required" type="text"
								alt="请输入用户名" size="30" />
						</dd>
					</dl>
					<dl>
						<dt>手机:</dt>
						<dd>
							<c:if test="${companyMessage.id != null}">
								<input name="userMobile" value="${companyMessage.user.userMobile }" type="text"	
								class="required phone" alt="请输入手机" size="30" />
							</c:if>
							<c:if test="${companyMessage.id == null}">
								<input name="userMobile" value="${companyMessage.user.userMobile }" type="text"	
								class="phone" alt="请输入手机" size="30" />
							</c:if>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>邮箱:</dt>
						<dd>
						<c:if test="${companyMessage.id != null}">
							<input name="userEmail" value="${companyMessage.user.userEmail }" type="text" 
							class="required email" alt="请输入邮箱" size="30" />
						</c:if>
						<c:if test="${companyMessage.id == null}">
							<input name="userEmail" value="${companyMessage.user.userEmail }" type="text" 
							class="email" alt="请输入邮箱" size="30" />
						</c:if>
						</dd>
					</dl>
					<dl>
						<dt>电话:</dt>
						<dd>
						<c:if test="${companyMessage.id != null}">
							<input name="userTelephone" value="${companyMessage.user.userTelephone }" 
							type="text" class="required isTel" alt="请输入电话" size="30" />
						</c:if>
						<c:if test="${companyMessage.id == null}">
							<input name="userTelephone" value="${companyMessage.user.userTelephone }" 
							type="text" class="isTel" alt="请输入电话" size="30" />
						</c:if>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>QQ:</dt>
						<dd>
						<input name="userQq" value="${companyMessage.user.userQq }" class="digits"	type="text" alt="请输入qq" size="30" />
						</dd>
					</dl>
					<dl>
						<dt>性别:</dt>
						<dd>
						<input name="userSex"  type="radio" checked="checked"	value="男" style="width:20px;"/>男
							<input name="userSex"  type="radio" value="女" style="width:20px;"/>女
							<c:if test="${not empty companyMessage.user}">
							<script type="text/javascript">
								 $("input[name='userSex'][value='${companyMessage.user.userSex}']").attr("checked",true);
							</script>
						</c:if>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>真实姓名:</dt>
						<dd>
						<input name="userName" value="${companyMessage.user.userName }"	
						class="chcharacter" type="text" alt="请输入用户真实姓名" size="30" />
						</dd>
					</dl>
					<dl>
						<dt>身份证号码:</dt>
						<dd>
						<input name="userCardNum" value="${companyMessage.user.userCardNum }" 
						class="isIdCardNo"	type="text" alt="请输入身份证号码" size="30" />
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>地址:</dt>
						<dd>
						<input name="userAddress" value="${companyMessage.user.userAddress }" type="text" alt="请输入地址" size="30" value="" />
						</dd>
					</dl>
					<dl  class="nowrap">
						<dt>备注:</dt>
						<dd>
						<textarea  name="remark" rows="5" cols="60" maxlength="50">${companyMessage.user.remark }</textarea>
						</dd>
					</dl>
				</fieldset>
				<fieldset>
					<legend>企业信息</legend>
					<dl>
						<dt>企业/机构法人</dt>
						<c:if test="${companyMessage.id != null}">
							<dd><input class="textInput required chcharacter" type="text" maxlength="20"
							 name="companyUserName" value="${companyMessage.companyUserName }"/></dd>
						</c:if>
						<c:if test="${companyMessage.id == null}">
							<dd><input class="textInput chcharacter" type="text" maxlength="20"
							 name="companyUserName" value="${companyMessage.companyUserName }"/></dd>
						</c:if>
					</dl>
					<dl>
						<dt>企业/机构名称</dt>
						<dd><input class="textInput required" type="text" maxlength="20"
						name="companyName" value="${companyMessage.companyName }"/></dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>注册时间:</dt>
						<dd>
						<input name="registerTime1"  value="<fmt:formatDate value="${companyMessage.registerTime }" pattern="yyyy-MM-dd"/>" type="text"
							<c:if test="${companyMessage.id != null}">
								class="required"  
							</c:if>
								onclick="WdatePicker({isShowClear:false,readOnly:true })" 
								title="请输注册日期" size="30" />
					</dl>
					<dl>
						<dt>注册资金(元):</dt>
						<c:if test="${companyMessage.id != null}">
							<dd><input class="number required" type="text" maxlength="14"
							 name="registerCapital" value="${companyMessage.registerCapital }"/></dd>
						</c:if>
						<c:if test="${companyMessage.id == null}">
							<dd><input class="number" type="text" maxlength="14"
							 name="registerCapital" value="${companyMessage.registerCapital }"/></dd>
						</c:if>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>注册地址:</dt>
						<dd><input 
						<c:if test="${companyMessage.id != null}">
							class="required" 
						</c:if>
						type="text" maxlength="70" value="${companyMessage.registerAddress }"
						 name="registerAddress"/></dd>
					</dl>
					<dl>
						<dt>现地址:</dt>
						<dd><input 
						<c:if test="${companyMessage.id != null}">
							class="required" 
						</c:if>
						type="text" maxlength="70" value="${companyMessage.presentAddress }"
						 name="presentAddress" /></dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>别名:</dt>
						<dd><input class="" type="text" maxlength="70" value="${companyMessage.otherName }"
						 name="otherName" /></dd>
					</dl>
					<dl>
						<dt>企业/机构类型:</dt>
						<dd><input class="" type="text" maxlength="70" value="${companyMessage.companyType }"
						 name="companyType" /></dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>经营范围:</dt>
						<dd><input class="" type="text" maxlength="70" value="${companyMessage.businessScope }"
						 name="businessScope" /></dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>组织机构代码:</dt>
						<dd><input class="" type="text" 
						 name="organizationCode" value="${companyMessage.organizationCode }"/></dd>
					</dl>
					<dl class="nowrap">
						<dt>组织机构代码证:</dt>
						<dd><input class="readonly" type="text" 
						id="uploadifyUrl${params.type}1" name="organizationPic" value="${companyMessage.organizationPic }"/>
						<input type="file" name="uploadify1" id="uploadify${params.type}1" />
						<a id="showLink${params.type}1" target="_Blank" href="<%=path %>${companyMessage.organizationPic }">
						<img style='width:100px;height:100px;' src="<%=path %>${companyMessage.organizationPic }" />
						</a>
						</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>营业执照号:</dt>
						<dd><input class="" type="text" 
						 name="businessLicenseId" value="${companyMessage.businessLicenseId }"/></dd>
					</dl>
					<dl class="nowrap">
						<dt>营业执照:</dt>
						<dd><input class="readonly" type="text"
						id="uploadifyUrl${params.type}2" name="businessLicensePic" value="${companyMessage.businessLicensePic }"/>
					<input type="file" name="uploadify2" id="uploadify${params.type}2" />
					<a id="showLink${params.type}2" target="_Blank" href="<%=path %>${companyMessage.businessLicensePic }">
						<img style='width:100px;height:100px;' src="<%=path %>${companyMessage.businessLicensePic }" />
					</a>
					</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>税务登记号:</dt>
						<dd><input class="" type="text" 
						 name="taxId" value="${companyMessage.taxId }"/></dd>
					</dl>
					<dl class="nowrap">
						<dt>税务登记证:</dt>
						<dd><input class="readonly" type="text"
						id="uploadifyUrl${params.type}3" name="taxPic" value="${companyMessage.taxPic }"/>
					<input type="file" name="uploadify3" id="uploadify${params.type}3" />
						<a id="showLink${params.type}3" target="_Blank" href="<%=path %>${companyMessage.taxPic }">
						<img style='width:100px;height:100px;' src="<%=path %>${companyMessage.taxPic }" />
					</a>
					</dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt>公司网址:</dt>
						<dd><input class="isUrl" type="text" name="linkUrl" value="${companyMessage.linkUrl}"/></dd>
					</dl>
					<dl class="nowrap">
						<dt>Logo(上传图片):</dt>
						<dd>	<input class="readonly" type="text" id="uploadifyUrl${params.type}4" name="logoPic" value="${companyMessage.logoPic}"/>
						<input type="file" name="uploadify4" id="uploadify${params.type}4" />
						<a id="showLink${params.type}4" target="_Blank" href="<%=path %>${companyMessage.logoPic}">
						<img style='width:100px;height:100px;' src="<%=path %>${companyMessage.logoPic}" />
					</a>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>公司介绍:</dt>
						<dd><textarea name="introduction" class="editor" upImgUrl="upload/editorImg" cols="110" 
						rows="15">${companyMessage.introduction}</textarea></dd>
					</dl>
					<div class="divider"></div>
					<dl>
						<dt></dt>
						<dd></dd>
					</dl>
					<div class="divider"></div>
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>

		</form>
	</div>
<c:if test="${not empty message}">
		<script type="text/javascript">
			alertMsg.error("${message}");
		</script>
</c:if>
<script type="text/javascript">
var imgSuffix = ".jpg,.png,.jpeg,.gif";
$(document).ready(function() { 
	for(var i=1;i<=4;i++){
		$("#uploadify${params.type}"+i).uploadify({  
	        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
	        'script'         : 'upload/uploadFiles;jsessionid=<%=session.getId()%>',
	        'scriptData'     : {'flag':i},//参数，method=get
			'method'         : 'get',	        
	        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
	       	'buttonImg'      : '<%=basePath %>/uploadify/buttonImg.png',
	        'folder'         : '/jxdBlog/photos',  
	        'queueID'        : 'fileQueue',  
	        'auto'           : true,  
	        'multi'          : true,  
	        'wmode'          : 'transparent',  
	        'simUploadLimit' : 999,  
	        'fileExt'        : '*',  
	        'fileDesc'       : '*',  
	        'onComplete'  :function(event,queueId,fileObj,response,data){
	        	var retJson = eval(response)[0];
	            var img = "";
	            if(imgSuffix.indexOf(retJson.suffix) != -1){
	            	img = "<img style='width:100px;height:100px;' src=\"<%=path %>"+retJson.filepath+"\" />";
	            	$("#showLink${params.type}"+retJson.flag).attr("href","<%=path %>"+retJson.filepath).html(img);
	            }
	            $("#uploadifyUrl${params.type}"+retJson.flag).val(retJson.filepath);
	            indx++;
	        }  
	    }); 
	}
    
});  
</script>
</body>
</html>