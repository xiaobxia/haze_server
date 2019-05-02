<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="l" uri="/tld/gyh-tags.tld"%>
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
<title>添加通知发布内容</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="noticeRelease/save?parentId=${params.parentId }"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${noticeRelease.id}">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>渠道类型：</label>
					<select name="source">
						<option value="-1">请选择</option>
						<option value="1"<c:if test="${'1' eq noticeRelease.source}">selected</c:if>>APP</option>
						<option value="2"<c:if test="${'2' eq noticeRelease.source}">selected</c:if>>微信</option>
						<option value="3"<c:if test="${'3' eq noticeRelease.source}">selected</c:if>>PC端</option>
						<option value="4"<c:if test="${'4' eq noticeRelease.source}">selected</c:if>>其他</option>
					</select>
				</p>
				<div class="divider"></div>
				<p>
					<label>频道类型：</label>
					<select name="channel">
						<option value="-1">请选择</option>
						<option value="1"<c:if test="${'1' eq noticeRelease.channel}">selected</c:if>>首页</option>
						<option value="2"<c:if test="${'2' eq noticeRelease.channel}">selected</c:if>>关于我们</option>
						<option value="3"<c:if test="${'3' eq noticeRelease.channel}">selected</c:if>>其他</option>
					</select>
				</p>
				<div class="divider"></div>
				<p>
					<label>栏目类型：</label>
					<select name="column_type">
						<option value="-1">请选择</option>
						<option value="1"<c:if test="${'1' eq noticeRelease.column_type}">selected</c:if>>消息滚动设置</option>
						<option value="2"<c:if test="${'2' eq noticeRelease.column_type}">selected</c:if>>banner轮播页</option>
						<option value="3"<c:if test="${'3' eq noticeRelease.column_type}">selected</c:if>>消息中心</option>
					</select>
				</p>
				<div class="divider"></div>
				<p>
					<label>内容标题：</label>
					<input name="title" class="required" type="text" alt="请输入内容标题" size="30" value="${noticeRelease.title }" maxlength="50"/>
				</p>
				<div class="divider"></div>
				<p>
					<label>动态链接：</label>
					<input type="radio" name="dynamic_link" value="0"<c:if test="${'0' eq noticeRelease.dynamic_link}">selected</c:if>>否
					<input type="radio" name="dynamic_link" value="1"<c:if test="${'1' eq noticeRelease.dynamic_link}">selected</c:if>>根据需要进行动态生成
				</p>
				<div class="divider"></div>
				<p style="height: 100px;">
					<label>发送条件：</label>
					<select name="send_condition">
						<option value="-1">请选择</option>
						<option value="1"<c:if test="${'1' eq noticeRelease.send_condition}">selected</c:if>>注册成功</option>
						<option value="2"<c:if test="${'2' eq noticeRelease.send_condition}">selected</c:if>>申请成功</option>
						<option value="3"<c:if test="${'3' eq noticeRelease.send_condition}">selected</c:if>>放款成功</option>
						<option value="4"<c:if test="${'4' eq noticeRelease.send_condition}">selected</c:if>>还款成功</option>
						<option value="5"<c:if test="${'5' eq noticeRelease.send_condition}">selected</c:if>>逾期还款</option>
						<option value="6"<c:if test="${'6' eq noticeRelease.send_condition}">selected</c:if>>其他</option>
					</select>
				</p>
				<div class="divider"></div>
				<p>
					<label>序号：</label> <input name="sort" class="required digits " min="0" type="text" value="${noticeRelease.sort}"/>
				</p>				
				<div class="divider"></div>
				<p style="height: 340px; width: 950px;">
					<label>内容编辑：</label>
					<textarea name="send_content" class="editor"
						upImgUrl="upload/editorImg" cols="110" rows="15" tools="Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Link,Unlink,Anchor,Img,Flash,Media,Hr,Table,|,Source,Preview,Print,Fullscreen">${noticeRelease.send_content}</textarea>
				</p>
				<div class="divider"></div>
				<p>
					<label>推送方式：</label>
					<input name="send_type" type="radio" value="1"<c:if test="${'1' eq noticeRelease.send_type}">selected</c:if>/>站内信
					<input name="send_type" type="radio" value="2"<c:if test="${'2' eq noticeRelease.send_type}">selected</c:if>/>PUSH
					<input name="send_type" type="radio" value="3"<c:if test="${'3' eq noticeRelease.send_type}">selected</c:if>/>第三方接口
				</p>
			</div>

			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
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
