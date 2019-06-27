<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String backbasePath = path + "/common/back";
%>
<c:set var="backbasePath" value="<%=backbasePath%>"></c:set>
<c:set var="path" value="<%=path%>"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<script src="${backbasePath }/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${backbasePath }/js/crm.upload.js" type="text/javascript"></script>
<script type="text/javascript" src="${backbasePath }/uploadify/scripts/swfobject.js"></script>
<script type="text/javascript" src="${backbasePath}/uploadify/scripts/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${backbasePath }/uploadify/scripts/jquery.uploadify.v2.1.0.js"></script>
<title>修改/添加内容</title>
	<style>
		p select {
			height: 36px;
			width: 368px;
		}
	</style>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="content/saveUpdate?parentId=${params.parentId }"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${content.id}">
			<div class="pageFormContent" layoutH="106">
				<p>
					<label>所属栏目：</label>
					<select name="channelType">
						<c:forEach items="${CHANNEL}" var="channel">
							<option value="${channel.key }" 
							<c:if test="${content.channelType eq channel.key}">selected="selected"</c:if>>${channel.value }</option>
						</c:forEach>
					</select>
				</p>
				<div class="divider"></div>
				<p>
					<label>内容标题：</label><input name="contentTitle" class="required"
						type="text" alt="请输入内容标题" size="50" value="${content.contentTitle }" maxlength="50"/>
				</p>
				<div class="divider"></div>
				<p style="height: auto;">
					<label>内容摘要：</label>
					<textarea name="contentSummary" cols="50" rows="5" class="required" maxlength="200">${content.contentSummary }</textarea>
				</p>
				<div class="divider"></div>
				<p>
					<label>排序：</label> <input name="orderNum" size="50" class="required digits " min="0" type="text" value="${content.orderNum}"/>
				</p>
				<p>
					<label>外部链接：</label> <input name="externalUrl" size="50" maxlength="200" type="text" value="${content.externalUrl}"/>
				</p>
				<div class="divider"></div>
				<p style="height: 340px; width: 950px;">
					<label>内容：</label>
					<textarea name="contentTxt" class="editor"
						upImgUrl="upload/editorImg" cols="110" rows="15" tools="Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Link,Unlink,Anchor,Img,Flash,Media,Hr,Table,|,Source,Preview,Print,Fullscreen">${content.contentTxt }</textarea>
				</p>
				<p style="height: 340px; width: 950px;">
					<input type="file" name="uploader" id="uploader" />
					 <input type="hidden" name="imgUrl" id="imgUrl" value=""/>
					 <c:choose>
					 	<c:when test="${content.imgUrl!=null&&content.imgUrl!=''}">
					 		<img src="${content.imgUrl}" id="qualificationPhoto1_img" style="width: 160px;height: 100px;display: block" />
					 	</c:when>
					 	<c:otherwise>
					 		<img src="" id="qualificationPhoto1_img" style="width: 160px;height: 100px;display: none" />
					 	</c:otherwise>
					 </c:choose>
				</p>
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
	
</body>
</html>
<script>
	 $(function(){
	 		//上传按钮事件
			$("#uploader").uploadify({  
		           'uploader'       : '<%=backbasePath%>/uploadify/scripts/uploadify.swf', 
		           'script'         : '<%=path %>/back/upload/uploadFiles;jsessionid=<%=session.getId()%>', 
		           'cancelImg'      : '<%=backbasePath%>/uploadify/cancel.png',  
		           'buttonImg'      : '<%=backbasePath%>/uploadify/buttonImg.png',
					'folder' : '<%=path%>/upload/uploadFiles',
					//'queueID' : 'fileQueue',
					'auto' : true,
					'multi' : true,
					'wmode' : 'transparent',
					'simUploadLimit' : 10,
					'fileExt' : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',
					'fileDesc' : '请上传合法文件(*.jpg;*.jpeg;*.gif;*.png)',
					'sizeLimit' : 20000000,
					'onComplete' : function(event, queueId, fileObj, response, data) {
						var retJson = eval(response)[0];
						//alert(retJson["filepath"]);
						$("#imgUrl").val(retJson["filepath"]);
						$("#qualificationPhoto1_img").show();
						$("#qualificationPhoto1_img").attr("src","${path}"+retJson["filepath"]);
						
					}
			});
	 })
</script>
