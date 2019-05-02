<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="infos/findInfoList/${params.type }?myId=${params.myId}" method="post">
	
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>
						公司名称:
						<input type="text" name="companyName"
							value="${params.companyName }" />
					</td>
					<td>
						资产类型:
						<input type="text" name="assetsType"
							value="${params.assetsType }" />
					</td>
					<td>
						担保方式:
						<input type="text" name="vouchWay"
							value="${params.vouchWay }" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									查询
								</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId"/>
		</jsp:include>
			<table class="table" style="width: 100%;" layoutH="112" nowrapTD="false">
				<thead>
					<tr>
						<th align="center" width="30">
							<input type="checkbox" group="ids${params.type}" id="checkedAll${params.type}" onclick="selectAll${params.type}();">
						</th>
						<th align="center" width="50">
							序号
						</th>
						<th align="center" width="100">
							用户名
						</th>
						<th align="center" width="250">
							公司名
						</th>
						<th align="center" width="200">
							资产类型
						</th>
						<th align="center" width="150">
							担保方式
						</th>
						<th align="center" width="200">
							资金范围(元)
						</th>
						<th align="center" width="150">
							利率范围(%)
						</th>
						<th align="center" width="150">
							期限范围(月)
						</th>
						<th align="center" width="100">
							联系人
						</th>
						<th align="center" width="100">
							性别
						</th>
						<th align="center" width="100">
							联系电话
						</th>
						<th align="center" width="120">
							邮箱
						</th>
						<th align="center" width="120">
							qq
						</th>
						<th align="center" width="120">
							发布时间
						</th>
						<th align="center" width="120">
							IP
						</th>
						<th align="center" width="120">
							备注
						</th>
						<th align="center" width="120">
							更新时间
						</th>
						<th align="center" width="120">
							收藏量
						</th>
						<th align="center" width="120">
							浏览量
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="assets" items="${pm.items }" varStatus="status">
						<tr target="id" rel="${assets.id }"  onclick="rowClick${params.type}(${assets.id })">
							<td>
								<input name="ids${params.type}" id="assets${assets.id }" value="${assets.id}" 	onclick="rowClick${params.type}(${assets.id })" type="checkbox">
							</td>
							<td>
								${status.count}
							</td>
							<td>
								${assets.user.userAccount}
							</td>
							<td>
								${assets.companyMessage.companyName }
							</td>
							<td>
								${assets.assetsType }
							</td>
							<td>
								${assets.vouchWay }
							</td>
							<td>
								${assets.moneyLow }-${assets.moneyHigh }
							</td>
							<td>
								${assets.rateLow }-${assets.rateHigh }
							</td>
							<td>
								${assets.limitLow }-${assets.limitHigh }
							</td>
							<td>
								${assets.linkPerson }
							</td>
							<td>
								${assets.sex }
							</td>
							<td>
								${assets.mobile }
							</td>
							<td>
								${assets.email }
							</td>
							<td>
								${assets.qq }
							</td>
							<td>
								<fmt:formatDate value="${assets.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${assets.addIp }
							</td>
							<td>
								${assets.remark }
							</td>
							<td>
								<fmt:formatDate value="${assets.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${assets.collectCount }
							</td>
							<td>
								${assets.viewCount }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
		<script type="text/javascript">
		function changeUrl${params.type}(obj){
			var href = $(obj).attr("href")+"&type=${params.type}";
			$(obj).attr("href",href);
		}
		function selectAll${params.type}(){
			var isAll = $("#checkedAll${params.type}").attr("checked");
			$("input[name=ids${params.type}]:not(:disabled)").each(function(){
				if(isAll=="checked"){
					$(this).attr("checked","checked");
				}else{
					$(this).removeAttr("checked");
				}
			});
		}
		function rowClick${params.type}(id){
			if($("#assets"+id).attr("disabled")!="disabled"){
				var cked = $("#assets"+id).attr("checked");
				if(cked == "checked"){
					$("#assets"+id).removeAttr("checked");
				}else{
					$("#assets"+id).attr("checked","checked");
				}
			}
		}
		</script>
	</div>
</form>