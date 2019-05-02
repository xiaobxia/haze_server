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
		action="${pageContext.request.contextPath }/back/ad/saveOrUpdateAd"
		enctype="multipart/form-data" class="pageForm required-validate"
		onsubmit="return iframeCallback(this);">
		<c:if test="${ad.id != null }">
			<input type="hidden" name="id" value="${ad.id }" />
		</c:if>
		<c:if test="${params.parentId != null }">
			<input type="hidden" name="parentId" value="${params.parentId}" />
		</c:if>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>渠道：</label> <select name="showType">
					<option value="0"
						<c:if test='${ad.showType==0}'>selected="selected"</c:if>>PC端</option>
					<option value="1"
						<c:if test='${ad.showType==1}'>selected="selected"</c:if>>APP端</option>
					<option value="2"
						<c:if test='${ad.showType==2}'>selected="selected"</c:if>>其他</option>
				</select>
			</p>
			<%-- <div class="divider"></div>
			<p>
				<label>频道：</label> <select name="channelType">
					<option value="0"
						<c:if test='${ad.channelType==0 }'>selected="selected"</c:if>>首页</option>
					<option value="1"
						<c:if test='${ad.channelType==1 }'>selected="selected"</c:if>>关于我们</option>
					<option value="2"
						<c:if test='${ad.channelType==2 }'>selected="selected"</c:if>>帮助中心</option>
				</select>
			</p> --%>
			<%-- <div class="divider"></div>
			<p>
				<label>栏目：</label> <select name="columnType">
					<option value="0"
						<c:if test='${ad.columnType==0 }'>selected="selected"</c:if>>banner轮播页面</option>
					<option value="1"
						<c:if test='${ad.columnType==1 }'>selected="selected"</c:if>>动态弹层</option>
				</select>
			</p> --%>
			<div class="divider"></div>
			<p>
				<label>标题：</label><input name="title" class="required" type="text"
					alt="请输入标题" size="30" value="${ad.title }"/>
			</p>
			<div class="divider"></div>
			<%-- <p>
				<label>用户群体：</label>
				<select name="userLevel">
					<option value="0" <c:if test="${ad.userLevel == 0 }">selected='selected'</c:if>>全部用户</option>
					<option value="1" <c:if test="${ad.userLevel == 1 }">selected='selected'</c:if>>V1</option>
					<option value="2" <c:if test="${ad.userLevel == 2 }">selected='selected'</c:if>>V2</option>
					<option value="3" <c:if test="${ad.userLevel == 3 }">selected='selected'</c:if>>V3</option>
				</select>
			</p> --%>
			<!-- <div class="divider"></div> -->
			<p>
				<label>链接：</label><input name="reurl" type="text" alt="请输入链接"
					size="30" value="${ad.reurl }" class="required"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>排序：</label><input name="sort" type="text" alt="请输入排序"
					size="30" value="${ad.sort }" class="required digits textInput valid"/>
			</p>
			<div class="divider"></div>
		<%-- 	<p>
				<label>发布：</label> 立即发布<input type="radio" name="presentWay"
					value="0" <c:if test="${ad.presentWay == 0 || ad == null}">checked='checked'</c:if>/>&nbsp;&nbsp;&nbsp;&nbsp; 定时发布<input
					type="radio" name="presentWay" value="1" <c:if test="${ad.presentWay == 1 }">checked='checked'</c:if>/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" name="startTime" value="${ad.startTime }" class="date textInput valid"/>&nbsp;-- <input type="text"
					name="endTime" value="${ad.endTime }" class="date textInput valid"/>
			</p>
			<div class="divider"></div> --%>
			<p>
				<label>弹窗：</label> 弹窗<input type="radio" name="status"
					value="0" <c:if test="${ad.status == 0 || ad == null}">checked='checked'</c:if>/>&nbsp;&nbsp;&nbsp;&nbsp; 不弹窗<input
					type="radio" name="status" value="1" <c:if test="${ad.status == 1 }">checked='checked'</c:if>/>
			</p>
			<div class="divider"></div>
			<p>
				<label>图片：</label><input type="file" name="picture"
					onchange="preview(this)"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>预览图：</label>
			<div id="preview">
				<img alt="图片" src="${ad.url }">
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