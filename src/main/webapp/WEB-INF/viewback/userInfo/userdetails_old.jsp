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
		<title>用户详情</title>
		<style type="text/css">
			.userTable td {
			    border-bottom: 1px solid #928989;
			    border-right: 1px solid #928989;
			    line-height: 31px;
			    overflow: hidden;
			    padding: 0 3px;
			    vertical-align: middle;
			    font-size:14px;
			}
			.userTable td a{
				color:#5dacd0;
			}
			.userTable{
				padding:0;
				border:solid 1px #928989;
				width:100%;
				line-height:21px;
			}
			.tdGround{background-color:#ededed;}
			.detailB th {
				border: 1px solid darkgray;
				color: #555;
				background: #f5ecec none repeat scroll 0 0;
				font-weight: bold;
				width: 100px;
				line-height: 21px;
			}
			
			/*   图片大小设置  */ 
			.tdGround img{
			width: 100px;
			transition:all 0.3s
			}
			
			/*   图片鼠标移动放大  */ 
			.tdGround img:hover{
			width:550px;
			}
		</style>
	</head>
	<body>
	<div class="pageContent">
		<div class="pageFormContent" layoutH="50" style="overflow: auto;">
			<!-- 借款信息 -->
			<!-- 个人信息 -->
			<fieldset>
				<legend>个人信息</legend>
				<table class="userTable">
					<tbody>
						<tr>
							<td style="font-weight:bold">个人详情</td>
							<td>
								<table class="userTable">
									<tr>
										<td class="tdGround" style="width:180px;">用户ID:</td>
										<td>${user.id}</td>
										<td class="tdGround">姓名：</td>
										<td >${user.realname}</td>
										<td class="tdGround">身份证号</td>
										<td>${user.idNumber}</td>
										<td class="tdGround">联系方式:</td>
										<td>${user.userPhone}</td>
									</tr>
									<tr>
										<td class="tdGround" style="width:180px;">出生日期</td>
										<td >${birthday}</td>
										<td class="tdGround">年龄</td>
										<td>${age}</td>
										<td class="tdGround" >性别</td>
										<td>${user.userSex}</td>
										<td class="tdGround">婚姻</td>
										<td>${user.maritalStatus}</td>
									</tr>
									<tr>
										<td class="tdGround">学历：</td>
										<td >${user.education}</td>
										<td class="tdGround">常住地址</td>
										<td >${user.presentAddress} ${user.presentAddressDistinct}</td>
										<td class="tdGround">居住时长：</td>
										<td>${user.presentPeriod}</td>
										<td class="tdGround">账号创建时间</td>
										<td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
									<tr>
										<td class="tdGround" style="height: 100px;width:180px;">学历证明:
										</td>
										<td ><img src=""/></td>
										<td class="tdGround" style="height: 100px;">身份证:</td>
										<td>
											<c:if test="${user.idcardImgZ!=null}">
												<img src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgZ}" style="width: 300px;"/>
											</c:if>
											<c:if test="${user.idcardImgF!=null}">
												<img src="${APP_IMG_URL['APP_IMG_URL']}${user.idcardImgF}" style="width: 300px;"/>
											</c:if>
											
										</td>
										<td class="tdGround" style="height: 100px;">个人名片:</td>
										<td>
										<c:if test="${user.headPortrait!=null}">
											<img src="${APP_IMG_URL['APP_IMG_URL']}${user.headPortrait}" style="width: 200px;"/>
										</c:if>
										</td>
										<td class="tdGround" style="height: 100px;">财产证明:</td>
										<td><img src=""/></td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td style="font-weight:bold">银行卡信息</td>
							<td>
								<c:forEach items="${bankCardList}" var="card">
								<table class="userTable">
									<tr>
										<td class="tdGround">发卡行:</td>
										<td>${card.bankName}</td>
										<td class="tdGround" style="width:180px;">种类:</td>
										<td>
											<c:choose>
												<c:when test="${card.type==1}">信用卡</c:when>
												<c:when test="${card.type==3}">对公账号</c:when>
												<c:otherwise>借记卡</c:otherwise>
											</c:choose>
										</td>
										<td class="tdGround" >类型：</td>
										<td>
											<c:choose>
												<c:when test="${card.mainCard==0}">主卡</c:when>
												<c:otherwise>附卡${card.mainCard}</c:otherwise>
											</c:choose>
										</td>
										<td class="tdGround">默认卡(是/否)</td>
										<td>
											<c:choose>
												<c:when test="${card.cardDefault==1}">是</c:when>
												<c:when test="${card.cardDefault==0}">否</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="tdGround">卡号：</td>
										<td>${card.card_no}</td>
										<td class="tdGround">预留收号码:</td>
										<td>${card.phone}</td>
										<td class="tdGround">添加时间</td>
										<td colspan="3"><fmt:formatDate value="${card.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

									</tr>
								</table>
								</c:forEach>
							</td>
						</tr>

						<tr>
							<td style="font-weight:bold">联系人</td>
							<td>
								<table class="userTable">
									<tr>
										<td class="tdGround" style="width:180px;">关系:</td>
										<td >${user.fristContactRelation}</td>
										<td class="tdGround">姓名：</td>
										<td>${user.firstContactName}</td>
										<td class="tdGround">电话：</td>
										<td>${user.firstContactPhone}</td>
										<td class="tdGround">来源:</td>
										<td>系统上传</td>
									</tr>
									<tr>
										<td class="tdGround" style="width:180px;">关系:</td>
										<td>${user.secondContactRelation}</td>
										<td class="tdGround">姓名：</td>
										<td >${user.secondContactName}</td>
										<td class="tdGround">电话：</td>
										<td>${user.secondContactPhone}</td>
										<td class="tdGround">来源:</td>
										<td>系统上传</td>
									</tr>
									<tr>
										<td class="tdGround" style="width:180px;">全部联系人:</td>
										<td colspan="11"><a rel="tree161" href="userManage/gotoUserContacts?userId=${user.id}"  target=navTab  width="820" height="420" rel="jbsxBox" mask="true">手机通讯录</a></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td style="font-weight:bold">工作信息</td>
							<td>
								<table class="userTable">
									<tr>
										<td class="tdGround" style="font-size:14px;width:180px;">单位名称:</td>
										<td>${user.companyName}</td>
										<td class="tdGround" style="font-size:14px;">公司电话：</td>
										<td >${user.companyPhone}</td>
										<td class="tdGround" style="font-size:14px;">公司地址：</td>
										<td>${user.companyAddress} ${user.companyAddressDistinct}</td>
									</tr>
									<tr>
										<td class="tdGround" style="font-size:14px;width:180px;height: 100px;">工牌证照：</td>
										<td><img src="" /></td>
										<td class="tdGround" style="font-size:14px;height: 100px;">工作证明：</td>
										<td >
										<c:forEach var="image" items="${InfoImage}" varStatus="status">
											<img src="${APP_IMG_URL['APP_IMG_URL']}${image.url}" />
										</c:forEach>
										</td>
										<td class="tdGround" style="font-size:14px;height: 100px;">收入证明：</td>
										<td><img src="" /></td>
									</tr>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>
			<fieldset>
				<legend>其它信息</legend>
				<table class="userTable">
					<tbody>
						<tr>
							<td style="font-weight:bold">近期借款记录</td>
							<c:choose>
								<c:when test="${userBorrows.items!=null}">
									<td>
										<table class="detailB" width="100%">
											<tr>
												<th align="center">序号</th>
												<th align="left">订单号</th>
												<th align="center">借款金额(元)</th>
												<th align="center">借款期限</th>
												<th align="center">服务费利率(万分之一)</th>
												<th align="center">服务费(元)</th>
												<th align="center">申请时间</th>
												 
												<th align="center">放款时间	</th>
												<th align="center">子类型	</th>
												<th align="center">状态</th>
											</tr>
											<c:forEach var="borrow" items="${userBorrows.items }" varStatus="status">
											<tr>
												<td>
													${status.count}
												</td>
												<td>
													${borrow.outTradeNo}
												</td>							 
												<td>
					 							<fmt:formatNumber type="number" value="${borrow.moneyAmount/100}" pattern="0.00" maxFractionDigits="2"/>
												</td>
												<td>${borrow.loanTerm }</td>
												<td>
												<fmt:formatNumber type="number" value="${borrow.apr}" pattern="0.00" maxFractionDigits="2"/>
												</td>
		 										<td>
													<fmt:formatNumber type="number" value="${borrow.loanInterests/100}" pattern="0.00" maxFractionDigits="2"/>
												</td>
												<td>
		 											<fmt:formatDate value="${borrow.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td>
		 											<fmt:formatDate value="${borrow.loanTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td>${borrow.orderTypeName }</td>
												<td>${borrow.statusName }</td>
												</tr>
										</c:forEach>
										</table>
									</td>
								</c:when>
								<c:otherwise>
									<td>暂无记录</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</tbody>
				</table>
			</fieldset>
		</div>
	</div>
</body>
</html>
