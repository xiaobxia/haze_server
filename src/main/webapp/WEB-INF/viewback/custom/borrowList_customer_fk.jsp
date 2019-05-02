<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="customService/searchBorrowList?bType=fangk&myId=${params.myId}"
	method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>订单号: <input type="text" name="outTradeNo" value="${params.outTradeNo }" id="outTradeNo" />
					</td>
					<td>用户姓名: <input type="text" name="realname" value="${params.realname }" id="realname"/>
					</td>
					<td>手机: <input type="text" name="userPhone" value="${params.userPhone }" id="userPhone"/>
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">


		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${params.myId}" name="parentId" />
		</jsp:include>
		<table class="table" style="width: 100%;" layoutH="138"
			nowrapTD="false">
			<thead>
				<tr>
				<th align="center" width="30">
							<input type="checkbox" group="idsBorrow" id="checkedAllBorrow" onclick="selectAllMerchant();">
						</th>
					<th align="center">序号</th>
					<th align="left">订单号</th>
					<th align="left">第三方流水号</th>
					<th align="center">姓名</th>
					<th align="center">手机号</th>
					<th align="center">地域</th>
					<th align="center">是否是老用户</th>
					<th align="center">借款金额(元)</th>
					<th align="center">天数</th>
					<th align="center">服务费利率(万分之一)</th>
					<th align="center">手续费</th>
					<th align="center">到账金额</th>
					<th align="center">下单时间</th>

					<th align="center">放款时间</th>
					<th align="center">更新时间</th>
					<th align="center">子类型</th>
					<th align="center">状态</th>
					<th align="center">放款状态</th>
					<th align="center">放款备注</th>
					<!-- 						<th align="center"  > -->
					<!-- 							操作 -->
					<!-- 						</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach var="borrow" items="${pm.items }" varStatus="status">
				 
					<tr target="sid_support" rel="${borrow.id }"  onclick="rowClickMerchant(${borrow.id })">
					<td>
								<input name="idsBorrow" id="borrow${borrow.id }" value="${borrow.id }" 	onclick="rowClickMerchant(${borrow.id })" type="checkbox">
							</td>
						<td>${status.count}</td>
						<td>${borrow.yurref}</td>
						<td>${borrow.outTradeNo}</td>
						<td>${borrow.realname}</td>
						<td>${borrow.userPhone }</td>
						<td>${borrow.area }</td>
						<td>${borrow.customerTypeName}</td>
						<td><fmt:formatNumber type="number"
								value="${borrow.moneyAmount/100}" pattern="0.00"
								maxFractionDigits="2" /></td>
						<td>${borrow.loanTerm }</td>
						<td>${borrow.apr }</td>


						<td><fmt:formatNumber type="number"
								value="${borrow.loanInterests/100}" pattern="0.00"
								maxFractionDigits="2" /></td>
						<td><fmt:formatNumber type="number"
								value="${borrow.intoMoney/100}" pattern="0.00"
								maxFractionDigits="2" /></td>
						<td><fmt:formatDate value="${borrow.orderTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>

						<td><fmt:formatDate value="${borrow.loanTime }"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${borrow.updatedAt }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>

						<td>${appName }</td>
						<td>${borrow.statusName }</td>
						<td>${borrow.paystatusRemark }</td>
						<td>${borrow.payRemark }</td>
						<!-- 									<td> -->
						<%--  	<a href="backBorrowOrder/getBorrowDetailById?selectId=${borrow.id }&parentId=${params.myId}"  target="dialog"  width="810" height="550" >查看</a> --%>
						<!-- 							</td> -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
</form>

<script type="text/javascript">
    showOptions();

	if("${message}"){
		alertMsg.error(${message});
	}
	
	
	function selectAllMerchant(){
		var isAll = $("#checkedAllBorrow").attr("checked");
		$("input[name=idsBorrow]:not(:disabled)").each(function(){
			if(isAll=="checked"){
				$(this).attr("checked","checked");
			}else{
				$(this).removeAttr("checked");
			}
		});
	}
	function rowClickMerchant(id){
	
		if($("#borrow"+id).attr("disabled")!="disabled"){
			var cked = $("#borrow"+id).attr("checked");
			if(cked == "checked"){
				$("#borrow"+id).removeAttr("checked");
			}else{
				$("#borrow"+id).attr("checked","checked");
			}
		}
	}
	

	function changeFkExcel(obj){
		var href=$(obj).attr("href");
		var outTradeNo=$("#outTradeNo").val();
		var realname=$("#realname").val();
		var userPhone=$("#userPhone").val();
		var companyName=$("#companyName").val();
		var borrowStatus=$("#borrowStatus option:selected").val();
		var startloanTime=$("#startloanTime").val();
		var endloanTime=$("#endloanTime").val();
		var toHref=href+"&outTradeNo="+outTradeNo+"&realname="+realname+"&userPhone="+userPhone+"&companyName="+companyName+"&startloanTime="+startloanTime+"&endloanTime="+endloanTime+"&borrowStatus="+borrowStatus;
		$(obj).attr("href",toHref);
	}

    function queryCitys(provinceId) {
        if(null==provinceId||''==provinceId){
            return;
        }
        console.log(provinceId);
        $(".citys").empty();
        $(".citys").append("<option value=''>全部</option>");
        $.ajax({
            global:false,
            type : "POST",
            url : "backBorrowOrder/queryCity?provinceId="+provinceId,
            dataType: "json",
            success : function(data) {
                var obj = data;
                if(0 == obj.code){
                    var citys = obj.data;
                    for(var i in citys){
                        $(".citys").append("<option value='"+citys[i].cityId+"'>"+citys[i].city+"</option>");
                    }
                }else{
                    alert(obj.message);
                }
            },
            error : function() {
                alert("加载失败！");
            }
        });

    }

    function showOptions(){
        var provinceId = '${params.provinceId}';
        if (null != provinceId && '' != provinceId) {
            $.ajax({
                type: "POST",
                url: "backBorrowOrder/queryCity?provinceId=" + provinceId,
                dataType: "json",
                success: function (data) {
                    var obj = data;
                    if (0 == obj.code) {
                        var citys = obj.data;
                        for (var i in citys) {
                            if ('${params.cityId}' == citys[i].cityId) {
                                $(".citys").append("<option selected='selected' value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
                            } else {
                                $(".citys").append("<option value='" + citys[i].cityId + "'>" + citys[i].city + "</option>");
                            }

                        }
                    } else {
                        alert(obj.message);
                    }
                },
                error: function () {
                    alert("加载失败！");
                }
            });
        }
    }
</script>