<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);" action="channel/getChannelUserInfos?myId=${params.myId}&channelReportId=${channelReportId}" method="post">
    <%--<div class="pageHeader">--%>
       <%-- <div class="searchBar">
            <div class="buttonActive">
                    &lt;%&ndash;&lt;%&ndash;<a href="userManage/userdetails?id={id}&isLess=Y" class="edit" target="dialog" width="760" height="420" rel="jbsxBox" mask="true">&ndash;%&gt;&ndash;%&gt;
                        &lt;%&ndash;<span>详情列表</span>&ndash;%&gt;
                    &lt;%&ndash;</a>&ndash;%&gt;
                </div>
        </div>--%>
    <%--</div>--%>

    <div class="pageHeader">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        手机号:
                        <input type="text" name="userPhone"
                               value="${params.userPhone }" />
                    </td>
                    <td>
                        注册时间：
                        <input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
                        到<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
                    </td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit" id="query_btn">
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
        <table class="table" style="width: 100% !important;" layoutH="112" nowrapTD="false">
            <thead>
            <tr>
                <th align="center"  >
                    序列号
                </th>
                <th align="center"  >
                    用户ID
                </th>
                <th align="center" >
                    用户名
                </th>
                <th align="center"  >
                    手机号
                </th>
                <th align="center"  >
                    是否完善个人信息
                </th>
                <th align="center"  >
                    借款中金额
                </th>
                <th align="center"  >
                    续借次数
                </th>
                <th align="center"  >
                    注册时间
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="one" items="${pm.items  }" varStatus="status">
                <tr target="id" rel="${one.userId }">
                    <td>
                            ${status.index+1}
                    </td>
                    <td>
                            ${one.userId}
                    </td>
                    <td>
                            ${one.realName }
                    </td>
                    <td>
                            ${one.mobile }
                    </td>
                    <td>
                            ${one.isCompleteInfo}
                    </td>
                    <td>
                            ${one.borrowTotal}
                    </td>
                    <td>
                            ${one.borrowCount}
                    </td>
                    <td>
                            ${one.registTime}
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="page" value="${pm }"></c:set>
        <%--<c:set var="targetTypeParam" value="dialog"></c:set>--%>
        <%@ include file="../page.jsp"%>
    </div>
</form>
<script type="text/javascript">
//    $(function () {
//       $.ajax({
//           type: "post",
//           dataType: "json",
//           url: "ac/order/queryCheatOrder",
//           data: {
//               hotelSeq: hotelSeq,
//               orderNo: orderNo,
//               sortFiled: sortFiled,
//               checkDate: checkDate
//           },
//       })
//    });
//    $('#query_btn').click(function(){
//        top.location.reload();
//    });
</script>