<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>工商信息</title>
</head>
<body>
	<div class="pageContent">
		<form id="issueForm" name="issueForm" method="post"	action="zbUser/findGsList?parentId=${parentId }" onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" name="id" value="${id }"/>
			<input type="hidden" name="type" value="update"/>
			<div class="pageFormContent" id="bbd" layoutH="55">
				<fieldset>
					<legend>基本信息</legend>
					<dl>
						<dt>名称：${bbd.results[0].jbxx.company_name }</dt>
					</dl>
					<dl>
						<dt>注册号或统一社会信用代码：${bbd.results[0].jbxx.regno_or_creditcode }</dt>
					</dl>
					<dl>
						<dt>统一社会信用代码：${bbd.results[0].jbxx.credit_code }</dt>
					</dl>
					<dl>
						<dt>注册号：${bbd.results[0].jbxx.regno }</dt>
					</dl>
					<dl>
						<dt>经度：${bbd.results[0].jbxx.company_gis_lon }</dt>
					</dl>
					<dl>
						<dt>纬度：${bbd.results[0].jbxx.company_gis_lat }</dt>
					</dl>
					<dl>
						<dt>类型：${bbd.results[0].jbxx.company_type }</dt>
					</dl>
					<dl>
						<dt>法人：${bbd.results[0].jbxx.frname}</dt>
					</dl>
					<dl>
						<dt>注册资本：${bbd.results[0].jbxx.regcap }</dt>
					</dl>
					<dl>
						<dt>实收资本：${bbd.results[0].jbxx.realcap }</dt>
					</dl>
					<dl>
						<dt>行业：${bbd.results[0].jbxx.company_industry}</dt>
					</dl>
					<dl>
						<dt>成立日期：${bbd.results[0].jbxx.esdate }</dt>
					</dl>
					<dl>
						<dt>核准日期：${bbd.results[0].jbxx.approval_date }</dt>
					</dl>
					<dl>
						<dt>营业期限自：${bbd.results[0].jbxx.openfrom}</dt>
					</dl>
					<dl>
						<dt>营业期限至：${bbd.results[0].jbxx.opento }</dt>
					</dl>
					<dl>
						<dt>住所：${bbd.results[0].jbxx.address }</dt>
					</dl>
					<dl class="nowrap">
						<dt>经营范围：${bbd.results[0].jbxx.operate_scope}</dt>
					</dl>
					<dl>
						<dt>登记机关：${bbd.results[0].jbxx.regorg }</dt>
					</dl>
					<dl>
						<dt>登记状态：${bbd.results[0].jbxx.enterprise_status }</dt>
					</dl>
					<dl>
						<dt>吊销日期：${bbd.results[0].jbxx.revoke_date}</dt>
					</dl>
					<dl>
						<dt>投资总额：${bbd.results[0].jbxx.invest_cap }</dt>
					</dl>
					<dl>
						<dt>注册币种：${bbd.results[0].jbxx.regcapcur }</dt>
					</dl>
					<dl>
						<dt>注销日期：${bbd.results[0].jbxx.cancel_date}</dt>
					</dl>
					<dl>
						<dt>经营期限：${bbd.results[0].jbxx.operating_period }</dt>
					</dl>
					<dl>
						<dt>组成形式：${bbd.results[0].jbxx.form }</dt>
					</dl>
					<dl>
						<dt>实收资本：${bbd.results[0].jbxx.regcap_amount }</dt>
					</dl>
					<dl>
						<dt>注册币种：${bbd.results[0].jbxx.realcap_currency }</dt>
					</dl>
					<dl>
						<dt>所属省份：${bbd.results[0].jbxx.company_province }</dt>
					</dl>
					<dl>
						<dt>添加时间：${bbd.results[0].jbxx.create_time }</dt>
					</dl>
					<dl>
						<dt>企业区县：${bbd.results[0].jbxx.company_county }</dt>
					</dl>
					<dl>
						<dt>更新时间：${bbd.results[0].jbxx.bbd_uptime }</dt>
					</dl>
					<dl>
						<dt>是否上市：${bbd.results[0].jbxx.ipo_company }</dt>
					</dl>
					</fieldset>
					<fieldset>
						<legend>股东信息</legend>
						<c:forEach items="${bbd.results[0].gdxx}" var="gd">
							<dl>
								<dt>序号：${gd.no }</dt>
							</dl>
							<dl>
								<dt>股东：${gd.shareholder_name }</dt>
							</dl>
							<dl>
								<dt>股东类型：${gd.shareholder_type }</dt>
							</dl>
							<dl>
								<dt>证件类型：${gd.idtype }</dt>
							</dl>
							<dl>
								<dt>证件号：${gd.idno }</dt>
							</dl>
							<dl>
								<dt></dt>
							</dl>
							<div class="divider"></div>
						</c:forEach>
					</fieldset>
					<fieldset>
						<legend>备案信息</legend>
						<c:forEach items="${bbd.results[0].baxx}" var="ba">
							<dl>
								<dt>序号：${ba.no }</dt>
							</dl>
							<dl>
								<dt>姓名：${ba.name }</dt>
							</dl>
							<dl>
								<dt>职务：${ba.position }</dt>
							</dl>
							<dl>
								<dt>公司名称：${ba.company_name }</dt>
							</dl>
							<dl>
								<dt>类型：${ba.type }</dt>
							</dl>
							<dl>
								<dt></dt>
							</dl>
							<div class="divider"></div>
						</c:forEach>
					</fieldset>
					<fieldset>
						<legend>变更信息</legend>
						<c:forEach items="${bbd.results[0].bgxx}" var="bg">
							<dl>
								<dt>变更事项：${bg.change_items }</dt>
							</dl>
							<dl>
								<dt>变更日期：${bg.change_date }</dt>
							</dl>
							<dl  class="nowrap">
								<dt>变更前内容：${bg.content_before_change }</dt>
							</dl>
							<dl  class="nowrap">
								<dt>变更后内容：${bg.content_after_change }</dt>
							</dl>
							<div class="divider"></div>
						</c:forEach>
					</fieldset>
					<fieldset>
						<legend>分支结构</legend>
						<c:forEach items="${bbd.results[0].fzjg}" var="fz">
							<dl>
								<dt>序号：${fz.no }</dt>
							</dl>
							<dl>
								<dt>注册号：${fz.regno }</dt>
							</dl>
							<dl>
								<dt>名称：${fz.name }</dt>
							</dl>
							<dl  class="nowrap">
								<dt>注册机关：${fz.regorg }</dt>
							</dl>
							<div class="divider"></div>
						</c:forEach>
					</fieldset>
					<fieldset>
						<legend>行政处罚</legend>
						<c:forEach items="${bbd.results[0].xzcf}" var="xz">
							<dl>
								<dt>序号：${xz.no }</dt>
							</dl>
							<dl>
								<dt>名称：${xz.name }</dt>
							</dl>
							<dl>
								<dt>法人：${xz.frname }</dt>
							</dl>
							<dl  class="nowrap">
								<dt>行政处罚内容：${xz.punish_content }</dt>
							</dl>
							<dl>
								<dt>作出行政处罚决定机关名称：${xz.punish_org }</dt>
							</dl>
							<dl>
								<dt>违法行为类型：${xz.punish_type }</dt>
							</dl>
							<dl>
								<dt>作出行政处罚决定日期：${xz.punish_date }</dt>
							</dl>
							<dl>
								<dt>行政处罚决定书文号：${xz.punish_code }</dt>
							</dl>
							<dl>
								<dt>公示日期：${xz.public_date }</dt>
							</dl>
							<div class="divider"></div>
						</c:forEach>
					</fieldset>

			</div>
			<div class="formBar">
				<ul>
					<c:if test="${companyMessage.status ne 2}">
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">认证</button>
							</div>
						</div>
					</li>
					</c:if>
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
</body>
<style type="text/css">
#bbd dl {
	width: 33%;
}
#bbd dl dt {
	width: 100%;
}
#bbd .nowrap{
	width: 100%!important;
}
</style>
</html>