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
		<a href="https://fully-online.oss-cn-hangzhou.aliyuncs.com/excel/black.xlsx"><input type="button" value="下载模板" class="bt2" onclick="down();" /></a>
		<div class="searchBar">
			<%--<table class="searchContent">
				<tr>
					<td>姓名: <input type="text" name="realname"
						value="${searchParams.realname }" />
					</td>
					<td>证件号码: <input type="text" name="idNumber"
						value="${searchParams.idNumber }" />
					</td>
				</tr>
				<tr>
					<td>手机: <input type="text" name="userPhone"
						value="${searchParams.userPhone }" />
					</td>
					<td>开始时间: <input type="text" name="createTime"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						value="${searchParams.createTime }" readonly="readonly" />
					</td>
					<td>结束时间: <input type="text" name="beginTime"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						value="${searchParams.beginTime }" readonly="readonly" />
					</td>
					<!-- <td>&nbsp;&nbsp;&nbsp;&nbsp;</td> -->
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>--%>
		</div>
	</div>
	<div class="pageContent">
		<jsp:include page="${BACK_URL}/rightSubList">
			<jsp:param value="${searchParams.myId}" name="parentId"/>
		</jsp:include>
		<table class="list" width="100%" layoutH="114">
			<thead>
				<tr>
					<th align="center">姓名</th>
					<th align="center">联系方式</th>
					<th align="center">身份证号</th>
					<th align="center">创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${pm.items }" varStatus="status" >
						<td align="center">${status.userName}</td>
						<td align="center">${status.userPhone}</td>
						<td align="center">${status.idNumber}</td>
						<td align="center">
						<fmt:formatDate value="${userBlack.createTime}" pattern="yyyy-MM-dd HH:mm" />
						</td>
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

        $(document).ready(function () {
            var msg="";
            if($("#importMsg").text()!=null){
                msg=$("#importMsg").text();
            }
            if(msg!=""){
                alert(msg);
            }
        });
	 </script>
</form>
