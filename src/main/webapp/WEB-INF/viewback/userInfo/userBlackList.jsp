<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm-ubl" onsubmit="return navTabSearch(this);" action="userManage/userBlackList?myId=${searchParams.myId}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>姓名: <input type="text" name="userName"
						value="${params.userName }" />
					</td>
					<td>证件号码: <input type="text" name="idNumber"
						value="${params.idNumber }" />
					</td>
					<td>手机: <input type="text" name="userPhone"
								   value="${params.userPhone }" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
					<td>
						<div class="buttonActive-blue">
							<div class="buttonContent-blue">
								<a href="https://fully-online.oss-cn-hangzhou.aliyuncs.com/excel/blacklist.xlsx">下载模板</a>
							</div>
						</div>
					</td>
					<td>
						<div class="buttonActive-blue">
							<div class="buttonContent-blue">
								<a href="userManage/importBlackUser?type=tojsp&myId=${searchParams.myId}&userType=0" class="add" target="dialog" width="820" height="420" mask="true">导入黑名单</a>
							</div>
						</div>
					</td>
					<%--<td>开始时间: <input type="text" name="createTime"
                    class="date textInput readonly" datefmt="yyyy-MM-dd"
                    value="${params.createTime }" readonly="readonly" />
                </td>
                <td>结束时间: <input type="text" name="beginTime"
                    class="date textInput readonly" datefmt="yyyy-MM-dd"
                    value="${searchParams.beginTime }" readonly="readonly" />
                </td>--%>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">
		<table class="table" width="100%" layoutH="114">
			<thead>
				<tr>
					<th align="center">姓名</th>
					<th align="center">联系方式</th>
					<th align="center">身份证号</th>
					<th align="center">创建时间</th>
					<th align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="userBlack" items="${pm.items }" varStatus="status" >
					<tr>
						<td align="center">${userBlack.userName}</td>
						<td align="center">${userBlack.userPhone}</td>
						<td align="center">${userBlack.idNumber}</td>
						<td align="center">
						<fmt:formatDate value="${userBlack.createTime}" pattern="yyyy-MM-dd HH:mm" />
						</td>
						<td align="center"><span class="deleteBtn" onclick="setPDelete('${userBlack.id }')">删除</span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<script type="text/javascript">
        function setPDelete(id) {
            if(confirm("确定要删除吗？")) {
                $.ajax({
                    type : "post",
                    data:{
                        "id":id,
                    },
                    url : "userManage/updateUserBlackStatus",
                    success : function(ret) {
                        setTimeout(function () {
                            $('#pagerForm-ubl').submit()
                        }, 200)
                    },
                    error:function(ret){
                    }
                })
            }
        }
	 </script>
</form>
