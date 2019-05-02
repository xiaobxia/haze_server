<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>实名认证审核</title>
		<style type="text/css">
			.userTable td {
				border-bottom: 1px solid #928989;
				border-right: 1px solid #928989;
				line-height: 31px;
				overflow: hidden;
				padding: 0 3px;
				vertical-align: middle;
				font-size: 14px;
				
			}
			.userTable td a {
				color: #5dacd0;
			}
			.userTable {
				padding: 0;
				border: solid 1px #928989;
				width: 100%;
				line-height: 21px;
			}
			.tdGround {
				background: #f5f5f5 none repeat scroll 0 0;
				border: 1px solid darkgray;
				color: #555;
				font: 12px "Lucida Grande", Verdana, Lucida, Helvetica, Arial, 'Simsun',
					sans-serif;
				font-weight: bold;
			}
			.detailB th {
				border: 1px solid darkgray;
				color: #555;
				background: #f5ecec none repeat scroll 0 0;
				font-weight: bold;
				width: 100px;
				line-height: 21px;
			}
</style>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="userH5/userAudit" onsubmit="return validateCallback(this, navTabAjaxDone);" class="pageForm required-validate">
			<input type="hidden" name="id" value="${user.id}" />
			<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<!-- 个人信息 -->
			<fieldset>
				<legend>个人信息</legend>
				<table class="userTable">
					<tbody>
						<tr>
							<td style="font-weight: bold">个人详情</td>
							<td>
								<table class="userTable">
									<tr>
										<td class="tdGround" style="width: 180px;">用户ID:</td>
										<td>${user.id}</td>
										<td class="tdGround">姓名：</td>
										<td>${user.realname}</td>
										<td class="tdGround">身份证号</td>
										<td>${user.idNumber}</td>
										<td class="tdGround">联系方式:</td>
										<td>${user.userPhone}</td>
									</tr>
									<tr>
										<td class="tdGround" style="width: 180px;">出生日期</td>
										<td>${birthday}</td>
										<td class="tdGround">年龄</td>
										<td>${age}</td>
										<td class="tdGround">性别</td>
										<td>${user.userSex}</td>
										<td class="tdGround">婚姻</td>
										<td>${user.maritalStatus}</td>
									</tr>
									<tr>
										<td class="tdGround">学历：</td>
										<td>${user.education}</td>
										<td class="tdGround">常住地址</td>
										<td>${user.presentAddress}
											${user.presentAddressDistinct}</td>
										<td class="tdGround">居住时长：</td>
										<td>${user.presentPeriod}</td>
										<td class="tdGround">账号创建时间</td>
										<td><fmt:formatDate value="${user.createTime}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
									</tr>
									<tr>
										<td class="tdGround" style="height: 100px; width: 180px;">学历证明:
										</td>
										<td><img src="" /></td>
										<td class="tdGround" style="height: 100px;">身份证:</td>
										<td>
											<c:if test="${user.idcardImgZ!=null}">
												<img src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgZ}"/>
											</c:if>
											<c:if test="${user.idcardImgF!=null}">
												<img src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgF}"/>
											</c:if>
										</td>
										<td class="tdGround" style="height: 100px;">个人名片:</td>
										<td>
											<c:if test="${user.headPortrait!=null}">
												<img src="${APP_IMG_URL['APP_IMG_URL']}${user.headPortrait}"/>
											</c:if>
										</td>
										<td class="tdGround" style="height: 100px;">财产证明:</td>
										<td><img src="" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold">银行卡信息</td>
							<td>
								<table class="userTable">
									<tr>
										<td class="tdGround" style="width: 180px;">种类:</td>
										<td><c:choose>
												<c:when test="${bankCard.type==1}">信用卡</c:when>
												<c:when test="${bankCard.type==3}">对公账号</c:when>
												<c:otherwise>借记卡</c:otherwise>
											</c:choose></td>
										<td class="tdGround">类型：</td>
										<td><c:choose>
												<c:when test="${bankCard.mainCard==0}">主卡</c:when>
												<c:otherwise>附卡${bankCard.mainCard}</c:otherwise>
											</c:choose></td>
										<td class="tdGround">卡号：</td>
										<td>${bankCard.card_no}</td>
										<td class="tdGround">发卡行:</td>
										<td>${bankCard.bankName}</td>
										<td class="tdGround">预留收号码:</td>
										<td>${bankCard.phone}</td>
									</tr>
									<tr>
										<td class="tdGround" style="width: 180px;">添加时间</td>
										<td><fmt:formatDate value="${bankCard.createTime}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td class="tdGround">影像资料</td>
										<td colspan="7"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold">联系人</td>
							<td>
								<table class="userTable">
									<tr>
										<td class="tdGround" style="width: 180px;">关系:</td>
										<td>${user.fristContactRelation}</td>
										<td class="tdGround">姓名：</td>
										<td>${user.firstContactName}</td>
										<td class="tdGround">电话：</td>
										<td>${user.firstContactPhone}</td>
										<td class="tdGround">来源:</td>
										<td>系统上传</td>
									</tr>
									<tr>
										<td class="tdGround" style="width: 180px;">关系:</td>
										<td>${user.secondContactRelation}</td>
										<td class="tdGround">姓名：</td>
										<td>${user.secondContactName}</td>
										<td class="tdGround">电话：</td>
										<td>${user.secondContactPhone}</td>
										<td class="tdGround">来源:</td>
										<td>系统上传</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold">工作信息</td>
							<td>
								<table class="userTable">
									<tr>
										<td class="tdGround" style="font-size: 14px; width: 180px;">单位名称:</td>
										<td>${user.companyName}</td>
										<td class="tdGround" style="font-size: 14px;">公司电话：</td>
										<td>${user.companyPhone}</td>
										<td class="tdGround" style="font-size: 14px;">公司地址：</td>
										<td>${user.companyAddress}${user.companyAddressDistinct}</td>
									</tr>
									<tr>
										<td class="tdGround"
											style="font-size: 14px; width: 180px; height: 100px;">工牌证照：</td>
										<td><img src="" /></td>
										<td class="tdGround" style="font-size: 14px; height: 100px;">工作证明：</td>
										<td>
											<c:forEach var="image" items="${InfoImage}" varStatus="status">
												<img src="${APP_IMG_URL['APP_IMG_URL']}${image.url}" />
											</c:forEach>
										</td>
										<td class="tdGround" style="font-size: 14px; height: 100px;">收入证明：</td>
										<td><img src="" /></td>
									</tr>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>
			<fieldset>
				<legend>审核信息</legend>
				 <dl class="nowrap">
					<dt>审核状态:</dt>
					<dd><input name="auditType"   id="borrowStatus" value="1" type="radio" />通过  
					&nbsp;&nbsp;&nbsp;&nbsp;<input name="auditType" id="borrowStatus" checked="checked" value="-1"  type="radio" />失败
					</dd> 
				</dl>
			</fieldset>
			</div>
			<div class="formBar">
					<ul>
						<li>
						<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" >
										保存
									</button>
								</div>
							</div>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
							
						</li>
					</ul>
			</div>
		</form>
	</div>
</body>
</html>
