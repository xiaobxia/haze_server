<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<div><font color="bule">批量导入</font></div>
<form action="userManage/batchimport" method="post" enctype="multipart/form-data" onsubmit="return check();">
	<div style="margin: 30px;"><input id="excel_file" type="file" name="filename" accept="xlsx" size="80"/>
		<input id="excel_button" type="submit" value="导入Excel"/></div>
</form>
<form id="pagerForm" onsubmit="return navTabSearch(this);" action="userManage/userBlackList?myId=${searchParams.myId}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<a href="https://fully-online.oss-cn-hangzhou.aliyuncs.com/excel/black.xlsx"><input type="button" value="下载模板" class="bt2"/></a>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>姓名: <input type="text" name="userName"
						value="${params.userName }" />
					</td>
					<td>证件号码: <input type="text" name="idNumber"
						value="${params.idNumber }" />
					</td>
				</tr>
				<tr>
					<td>手机: <input type="text" name="userPhone"
						value="${params.userPhone }" />
					</td>
					<%--<td>开始时间: <input type="text" name="createTime"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						value="${params.createTime }" readonly="readonly" />
					</td>
					<td>结束时间: <input type="text" name="beginTime"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						value="${searchParams.beginTime }" readonly="readonly" />
					</td>--%>
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
		<table class="list" width="100%" layoutH="114">
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
					    <td align="center"><input type="button" value="删除" onclick="setPDelete('${userBlack.id }')"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="page" value="${pm }"></c:set>
		<!-- 分页 -->
		<%@ include file="../page.jsp"%>
	</div>
	<script type="text/javascript">
        function check() {
            var excel_file = $("#excel_file").val();
            if (excel_file == "" || excel_file.length == 0) {
                alert("请选择文件路径！");
                return false;
            } else {
                return true;
            }
        }
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
                            $('#pagerForm-p').submit()
                        }, 200)
                    },
                    error:function(ret){
                    }
                })
            }
        }
	 </script>
</form>
