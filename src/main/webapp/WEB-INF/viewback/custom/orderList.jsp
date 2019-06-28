<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<%--
<script type="text/javascript" src="${path}/resources/js/productAmount_choose.js"></script>
--%>
<style>
	.popLayer {
		display: none;
		display: none;
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%,-50%);
		z-index: 100;
		width: 500px;
		background: grey;
		background-color: #fff;
		border-radius: 2px;
		box-shadow: 1px 1px 50px rgba(0,0,0,.3);
	}
	.popLayer .layer-title {
		position: relative;
		padding: 0 80px 0 20px;
		height: 42px;
		line-height: 42px;
		border-bottom: 1px solid #eee;
		font-size: 14px;
		color: #333;
		overflow: hidden;
		background-color: #F8F8F8;
		border-radius: 2px 2px 0 0;
	}
	.popLayer .layer-title .layer-close {
		position: absolute;
		right: 8px;
		top: 8px;
		font-size: 20px;
		padding: 2px 7px;
		color: #666;
		font-weight: 100;
		text-decoration: none;
	}
	.popLayer .layer-title .layer-close:hover {
		color: #999;
	}
	.popLayer .layer-content {
		min-height: 200px;
	}
	/*  转派内容样式  */
	.send-custom {
		padding: 60px 50px;
		text-align: center;
	}
	.send-custom span {
		font-size: 15px;
		margin-right: 10px;
	}
	/*  添加备注内容样式  */
	.add-remark {
		padding: 20px;
	}
	.add-remark ul li {
		margin-bottom: 20px;
	}
	.add-remark ul li>label {
		font-size: 16px;
		vertical-align: top;
		margin-right: 10px;
	}
	.add-remark ul li>textarea {
		vertical-align: top;
	}
	.add-remark ul li span >label {
		font-size: 14px;
	}
	.add-remark ul li > p >span {
		display: inline-block;
		margin-bottom: 8px;
	}
	.buttonActive {
		margin-right: 20px;
	}
	.add-remark table {
		width: 100%;
	}
	.add-remark table {
		border: 1px solid #e0e0e0;
	}
	.add-remark table tr th:last-child,
	.add-remark table tr td:last-child{
		border-right: 0;
	}
	.add-remark table tr:last-child td {
		border-bottom: 0;
	}
	.add-remark table tr td,
	.add-remark table tr th{
		border-right: 1px solid #e0e0e0;
		border-bottom: 1px solid #e0e0e0;
	}
	.add-remark table tr th {
		background: #f8f8f8;
	}
	.add-remark table tr td,
	.add-remark table tr th{
		padding: 10px 5px;
	}
</style>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="customService/orderList?bType=${bType}&myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						客服:<input type="text" name="kefuName" value="${params.kefuName }" />
					</td>
					<td>
						用户手机号：
						<input type="text" name="userPhone" value="${params.userPhone}">
					</td>
					<td>
						用户类型：
						<select name="customerType" calss="textInput">
							<option value=""
								<c:if test="${params.get('customerType') != null and params.get('customerType') != null}">
									selected="selected"
								</c:if>>全部
							</option>
							<option value="0"
								<c:if test="${params.get('customerType') != null and params.get('customerType') != null}">
									selected="selected"
								</c:if>>新用户
							</option>
							<option value="1"
							<c:if test="${params.get('customerType') != null and params.get('customerType') != null}">
								selected="selected"
							</c:if>>老用户
						</option>
						</select>
					</td>

					<%--<td>
						借款金额:
						<select id = "productAmount" name = "productAmount"></select>
					<input type="hidden" value="${params.productAmount}" id="product_amount_choosed"/>
					<%--</td>--%>
					<td>
						状态：
						<select name="status" class="textInput">
							<option value=""
									<c:if test="${params.get('status') != null and params.get('status') != null}">
										selected="selected"
									</c:if>
							>全部</option>
							</option>
							<option value="21"
									<c:if test="${params.get('status') != null and params.get('status') == 21}">
										selected="selected"
									</c:if>
							>当日待还</option>
							<option value="30"
									<c:if test="${params.get('status') != null and params.get('status') == 30}">
										selected="selected"
									</c:if>
							>已逾期</option>
							<option value="34"
									<c:if test="${params.get('status') != null and params.get('status') == 34}">
										selected="selected"
									</c:if>
							>逾期还款</option>
						</select>
					</td>
					<%--<td>
						派单时间：
						<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  />
						到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd" />
					</td>--%>
					<%--<td>
						<span style="display: inline-block;vertical-align: -1px;">标签：</span>
						<select name="labelType" class="textInput" id="firstclassify" style="width:100px;vertical-align: bottom;">
							<option value="">----请选择----</option>
							<option value="user_remark_online">在线客服</option>
							<option value="user_remark">电话客服</option>
						</select>
					</td>--%>
					<%--<td>
						用户来源
						<select name="browerType" class="textInput">
							<option value=""
									<c:if test="${params.get('browerType') != null}">
										selected = "selected"
									</c:if>
							>全部</option>
							<option value="1"
									<c:if test="${params.get('browerType') == 1}">
										selected="selected"
									</c:if>
							>安卓端</option>

							<option value="2"
									<c:if test="${params.get('browerType') == 2}">
										selected="selected"
									</c:if>
							>苹果端</option>
							<option value="3"
									<c:if test="${params.get('browerType') == 3}">
										selected="selected"
									</c:if>
							>电脑端</option>
						</select>
					</td>--%>



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
			<tr >
				<th align="center"  >
					<input type="checkbox" id="checkAlls" onclick="checkAll(this);"/>
				</th>
				<th align="center"  >
					序号
				</th>
				<th align="center"  >
					姓名
				</th>
				<th align="left"  >
					性别
				</th>
				<th align="center"  >
					手机号
				</th>
				<th align="center" >
					是否为老用户
				</th>
				<th align="center">
					借款金额
				</th>
				<th align="center"  >
					总需还款金额
				</th>
				<th align="center">
					放款时间
				</th>
				<th align="center">
					应还款时间
				</th>
				<th align="center">
					续期次数
				</th>
				<%--<th align="center">
					用户来源
				</th>--%>
				<%--<th align="center" >
					预期还款时间
				</th>--%>
				<th align="center"  >
					状态
				</th>
				<th align="center">
					逾期天数
				</th>
				<th align="center" >
					客服
				</th>
				<th align="center" >
					派单时间
				</th>
				<%--<th align="center">
					标签
				</th>
				<th align="center">
					备注
				</th>--%>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${pm.items }" var="in" varStatus="varStatus">
				<tr target="sid_support" rel="${in.borrowAssignId}">
					<td>
						<c:choose>
							<c:when test="${in.status == 34 || in.status == 23}">

							</c:when>
							<c:otherwise>
								<input type="checkbox" name="checkItem" value="${in.borrowAssignId}"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${varStatus.count}</td>
					<td>
							<%--${in.userName}--%>
						${in.realName}
					</td>
					<td>
							<%--${in.sex}--%>
						${in.userSex}
					</td>
					<td>
							${in.userPhone}
					</td>
					<td>
						<c:choose>
							<c:when test="${in.customerType == '1'}">
								老用户
							</c:when>
							<c:otherwise>
								新用户
							</c:otherwise>
						</c:choose>
					</td>
					<td>
								<fmt:formatNumber type="number" value="${in.moneyAmount/100}" pattern="0.00"
												  maxFractionDigits="0"/>元
					</td>
					<td>
							<fmt:formatNumber type="number" value="${in.repaymentAmount/100}" pattern="0.00"
											  maxFractionDigits="0"/>元
					</td>
					<td>
							<fmt:formatDate value="${in.loanTime }" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
							<fmt:formatDate value="${in.repaymentTime }" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						${in.renewalCount}
					</td>
					<td>
						<c:if test="${in.status == 21}">
							待还款
						</c:if>
						<c:if test="${in.status == -11}">
							已逾期
						</c:if>
						<c:if test="${in.status == 34}">
							逾期还款
						</c:if>
						<%--<c:choose>
							<c:when test="${in.status == 30}">
								已还款
							</c:when>
							<c:when test="${in.status == -11}">
								已逾期
							</c:when>
							<c:otherwise>
								未还款
							</c:otherwise>
						</c:choose>--%>
					</td>
					<Td>
						${in.lateDay}
					</Td>
					<td>
						${in.kefuName}
					</td>
					<td> <fmt:formatDate value="${in.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<%--<td>
					        <c:choose>
							<c:when test="${in.remarkFlag == 1}">
								在通话中
							</c:when>
							<c:when test="${in.remarkFlag == 2}">
								立即处理还款
							</c:when>
							<c:when test="${in.remarkFlag == 3}">
							   明天还款
						   </c:when>
							<c:when test="${in.remarkFlag == 4}">
								晚上12点前处理还款
							</c:when>
							<c:when test="${in.remarkFlag == -1}">
								已关机
							</c:when>
							<c:when test="${in.remarkFlag == -2}">
								用户按掉
							</c:when>
							<c:when test="${in.remarkFlag == -3}">
								用户未接
							</c:when>
							<c:when test="${in.remarkFlag == -4}">
								投诉贷后，还款意愿不强
							</c:when>
							<c:when test="${in.remarkFlag == -5}">
								不想处理还款(追问利息等)
							</c:when>
							<c:when test="${in.remarkFlag == -6}">
								后续还款
							</c:when>
							<c:when test="${in.remarkFlag == -7}">
								只想app内还款(只想自动划款)
							</c:when>
							<c:when test="${in.remarkFlag == -8}">
								协商还款
							</c:when>
							<c:when test="${in.remarkFlag == -1}">
								已关机
							</c:when>
							<c:when test="${in.remarkFlag == -11}">
								未逾期要求协商
							</c:when>
							<c:when test="${in.remarkFlag == -12}">
								逾期要求协商
							</c:when>
							<c:when test="${in.remarkFlag == -13}">
								非本人借款
							</c:when>
							<c:when test="${in.remarkFlag == -14}">
								中介借款
							</c:when>
							<c:when test="${in.remarkFlag == -15}">
								用户去世
							</c:when>
							<c:when test="${in.remarkFlag == -16}">
								投诉平台
						    </c:when>
							<c:when test="${in.remarkFlag == -17}">
								投诉客服
							</c:when>
							<c:when test="${in.remarkFlag == -18}">
								空号
							</c:when>
							<c:when test="${in.remarkFlag == -19}">
								呼叫转移
							</c:when>
							<c:when test="${in.remarkFlag == -20}">
								非本人借款
							</c:when>
							<c:when test="${in.remarkFlag == -21}">
								中介借款
							</c:when>
							<c:when test="${in.remarkFlag == -22}">
								用户去世
							</c:when>
						</c:choose>--%>
						<%--${remark.get(in.remarkFlag)}
					</td>
					<td>
							${in.remarkContent}
					</td>--%>
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
    function openPopLayer(obj) {
        obj.on('click',function () {
            var arrs=[];  //用来存储value数组
            $.each($('input[name="assign[]"]:checked'),function(){
                if(this.checked){
                    arrs.push($(this).val());
                }
            });
            if (arrs != []) {
                $("#uid").attr('value',arrs.toString());
                $('#to-send-pop').fadeIn();
            }else {
                alert("请先选择订单");
                return;
            }
        });
    }
    function closePopLayer(closeObj) {
        $('.layer-close').on('click',function () {
            closeObj.fadeOut();
        });
    }
    $(function () {
        openPopLayer($('#to-send'));
        closePopLayer($('#to-send-pop'));
        openPopLayer($('#add-remark'));
        closePopLayer($('#add-remark-pop'));
        openPopLayer($('#look-remark'));
        closePopLayer($('#look-remark-pop'));
    });
</script>

<script type="text/javascript">
    function getOrderIds(obj) {
        var href = $(obj).attr("href");
        if (href.indexOf('&ids') > -1) {
            href = href.substring(0, href.indexOf('&ids'));
        }
        var hasDifferentGroup = '0';
        var selectedGroup = "";
        $("input[name='checkItem']:checked").each(function () {
            var group = $(this).attr("group");
            if (group != undefined && group != '') {
                if (selectedGroup == '') {
                    selectedGroup = group;//第一次赋值
                } else if (selectedGroup != group) {// 之后和第一次的值比较，有不同就GG
                    hasDifferentGroup = '1';
                }
            }
        })
        if (hasDifferentGroup) {
            var ids = "";
            $("input[name='checkItem']:checked").each(function () {
                ids = ids + "," + $(this).val();
            });
            var toHref = href + "&ids=" + ids.substring(1) + "&groupStatus=" + hasDifferentGroup;
            $(obj).attr("href", toHref);
        } else {
            return;
        }
    }


    function checkAll(obj){
        var bol = $(obj).is(':checked');
        $("input[name='checkItem']").attr("checked",bol);
    }
</script>

<script type="text/javascript">
    if("${message}"){
        alertMsg.error(${message});
    }
    //派单提醒
    function sendAlert() {
        var msg = "当前时间还没到18点，您确定派单!";
        var date = new Date();
        var hour = zeroFill(date.getHours());//时
        var limitHour = "18";
        if (hour < limitHour) {
            alertMsg.confirm(msg, {
                okCall: function(){
                    return true;
                },
                cancelCall : function() {
                    $('div[class="dialog"]').hide();
                    $('div[class="shadow"]').hide();
                    $('div[id="dialogBackground"]').hide();
                    return false;
                }
            });
        }

    }
    function zeroFill(i) {
        if (i >= 0 && i <= 9) {
            return "0" + i;
        } else {
            return i;
        }
    }

        function selectOptionAjax(choosed, classify, type){
            $.ajax({
                type: "post",
                data:  {"labelType": choosed, "type": type},
                dataType: "json",
                url: "customService/getLabels",
                async:false,
                success: function (data) {
                    var str = "";
                    $.each(data.data, function(index,value){
                        if("secondclassify" == classify) {
                            str += "<option value='" + value.dictName + "'>" + value.dictName + "</option>";
						} else {
                            str += "<option value='" + value.dataValue + "'>" + value.dataName + "</option>";
                        }
                    });
                    if("secondclassify" == classify) {
                        $("#secondclassify").empty();
                        $("#thirdclassify").empty();
                        $("#secondclassify").append("<option>----请选择----</option>");
					}
                    if ("thirdclassify" == classify){
                        $("#thirdclassify").empty();
                        $("#thirdclassify").append("<option>----请选择----</option>");
                    }
                    $("#" + classify).append(str);
                },
                error: function(){
                    alert("系统异常，请稍后重试!");
                }
            });
        }
    $(document).ready(function(){
        var label_type = '${params.get('labelType')}';
        var label_Group = '${params.get('labelGroup')}';
        var tem='${params.get('remarkFlag')}';
        if(label_type.length > 0) {
            $(" #firstclassify option[value='"+label_type+"']").attr("selected","selected");
        }
        if(label_Group.length > 0) {
            selectOptionAjax(label_type, "secondclassify", "");
            $(" #secondclassify option[value='"+label_Group+"']").attr("selected","selected");
        }
        if(tem.length > 0) {
            selectOptionAjax(label_type, "thirdclassify", label_Group);
            $(" #thirdclassify option[value='"+tem+"']").attr("selected","selected");
        }

        $("#firstclassify").change(function(){
            var val = $("#firstclassify option:selected").val();
            if ("----请选择----" != val){
                selectOptionAjax(val, "secondclassify", "");
            }
        });

        $("#secondclassify").change(function(){
            var label_type = $("#firstclassify option:selected").val();
            var val = $("#secondclassify option:selected").val();
            if ("----请选择----" != val){
                selectOptionAjax(label_type, "thirdclassify", val);
            }
        });
    });
</script>