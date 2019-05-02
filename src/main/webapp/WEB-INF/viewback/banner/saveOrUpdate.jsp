<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
img {
	width: 700px;
	height: 320px;
	margin-right:600px;
}

/* #preview {
	border: 1px solid #000;
} */
</style>
<script type="text/javascript">
	function preview(file) {
		var prevDiv = document.getElementById('preview');
		if (file.files && file.files[0]) {
			var reader = new FileReader();
			reader.onload = function(evt) {
				prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';
			}
			reader.readAsDataURL(file.files[0]);
		} else {
			prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
		}
	}
</script>
<div class="pageContent">
	<form method="post"
		action="${pageContext.request.contextPath }/back/banner/saveOrUpdateBanner"
		enctype="multipart/form-data" class="pageForm required-validate"
		onsubmit="return iframeCallback(this);">
		<c:if test="${banner.id != null }">
			<input type="hidden" name="id" value="${banner.id }" />
		</c:if>
		<c:if test="${params.parentId != null }">
			<input type="hidden" name="parentId" value="${params.parentId}" />
		</c:if>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>渠道：</label> <select name="showType">
					<option value="0"
						<c:if test='${banner.showType==0}'>selected="selected"</c:if>>PC端</option>
					<option value="1"
						<c:if test='${banner.showType==1}'>selected="selected"</c:if>>APP端</option>
					<option value="2"
						<c:if test='${banner.showType==2}'>selected="selected"</c:if>>其他</option>
				</select>
			</p>
			<%-- <div class="divider"></div>
			<p>
				<label>'${banner.channelType}频道：</label> <select name="channelType">
					<option value="0"
						<c:if test='${banner.channelType==0 }'>selected="selected"</c:if>>首页</option>
					<option value="1"
						<c:if test='${banner.channelType==1 }'>selected="selected"</c:if>>关于我们</option>
					<option value="2"
						<c:if test='${banner.channelType==2 }'>selected="selected"</c:if>>帮助中心</option>
				</select>
			</p>
			<div class="divider"></div>
			<p>
				<label>栏目：</label> <select name="columnType">
					<option value="0"
						<c:if test='${banner.columnType==0 }'>selected="selected"</c:if>>banner轮播页面</option>
					<option value="1"
						<c:if test='${banner.columnType==1 }'>selected="selected"</c:if>>动态弹层</option>
				</select>
			</p> --%>
			<div class="divider"></div>
			<p>
				<label>标题：</label><input name="title" class="required" type="text"
					alt="请输入标题" size="30" value="${banner.title }"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>链接：</label><input name="reurl" type="text" alt="请输入链接"
					size="30" value="${banner.reurl }" class="required">
			</p>
			<div class="divider"></div>
			<p>
				<label>序号：</label><input name="sort" class="required digits textInput valid" type="text"
					alt="请输入序号" size="30" value="${banner.sort }"/>
			</p>
			<div class="divider"></div>
			<%-- <p>
				<label>发布方式：</label> 立即发布<input type="radio" name="presentWay"
					value="0" <c:if test="${banner.presentWay == 0 || banner == null }">checked='checked'</c:if> />&nbsp;&nbsp;&nbsp;&nbsp; 定时发布<input
					type="radio" name="presentWay" value="1" <c:if test="${banner.presentWay == 1 }">checked='checked'</c:if>/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="date" name="startTime" value="${banner.startTime }"/>&nbsp;-- <input type="date"
					name="endTime" value="${banner.endTime }" />
			</p>
			<div class="divider"></div> --%>
			<p>
				<label>状态：</label> 有效<input type="radio" name="status"
					value="1" <c:if test="${banner.status == 1 || banner == null }">checked='checked'</c:if> />&nbsp;&nbsp;&nbsp;&nbsp; 无效<input
					type="radio" name="status" value="0" <c:if test="${banner.status == 0 }">checked='checked'</c:if>/>
			</p>
			<div class="divider"></div>
			<p>
				<label>图片：</label><input type="file" accept="image/png,image/gif" name="picture"
					onchange="preview(this)"/>
			</p>
			<%-- <p>
				<label>缩略图：</label><input name="url" class="required" type="text"
					alt="请输入序号" size="30" value="${banner.sort }" maxlength="50" />
			</p> --%>
			<div class="divider"></div>
			<p>
				<label>预览图：</label>
			<div id="preview">
				<img alt="图片" src="${banner.url }">
			</div>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>