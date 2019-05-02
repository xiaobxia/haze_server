<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
String basePath = path + "/common/web/zmxy";
%>

<c:set var="path" value="<%=path%>"></c:set>
<c:set var="basePath" value="<%=basePath%>"></c:set>
<script src="${path}/common/back/js/channel/highcharts.js"></script>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="channel/getChannelReportChar?myId=${params.myId}" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td>渠道商： <select name="channelid" id="channelid">
							<option value="">全部</option>
							<c:forEach var="channelList" items="${channelList}">
								<option value="${channelList.id}"
									<c:if test="${channelList.id eq params.channelid}">selected="selected"</c:if>>${channelList.channelName}</option>
							</c:forEach>
					</select>
					</td>
					<td>添加时间： <input type="text" name="beginTime" id="beginTime"
						value="${params.beginTime}" class="date textInput readonly"
						datefmt="yyyy-MM-dd" readonly="readonly" /> 到<input type="text"
						name="endTime" id="endTime" value="${params.endTime}"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						readonly="readonly" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="createChart();">查询</button>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>

			</table>
		</div>
	</div>
	<div id="channelChar"
		style="width: 80%; height: 150%; margin: 100px auto; "></div>

</form>
<script language="JavaScript">
$(function() {
	createChart();
});
function createChart(){
    var beginTime=document.getElementById('beginTime').value;
    var endTime =document.getElementById('endTime').value;
    var channelid = document.getElementById('channelid').value;

	//alert(beginTime);
	//alert(channelid);
	var options = {
			    chart: {
			        type: 'column'
			    },
			    title: {
			        text: '渠道统计分析图'
			    },
			    subtitle: {
			        text: 'www.xiangjie.com'
			    },
			    xAxis: [],
			    yAxis: {
			        min: 0,
			        title: {
			            text: '量级'
			        }
			    },
			    tooltip: {
			        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
			        footerFormat: '</table>',
			        shared: true,
			        useHTML: true
			    },
			    plotOptions: {
			        column: {
			            pointPadding: 0.2,
			            borderWidth: 0
			        }
			    },
			    series: []
			};
 
		$.ajax({
			type : "post",
			url : 'channel/getChannelChar?type=${type}' ,
			data :{beginTime:beginTime,endTime:endTime,channelid:channelid},
			dataType : "json",
			success : function(data) {
				options.xAxis.push({
//	 				lineWidth : 2,
//	 				labels : {
//	 					rotation : -30
//	 				},
					categories : data.categories
				});
				var isOpposite = false;
				var space = null;
				for ( var i = 0; i < data.seriesObj.length; i++) {
					var tmp = data.seriesObj[i];
					var name = tmp.name;
					if (i % 2 == 0) {
						isOpposite = false;
						space = 10;
					} else {
						isOpposite = true;
						space = 20;
					}
					options.series.push({
						name : name,
						data : tmp.data
					});
				}
 				$('#channelChar').highcharts(options);
			}
		});
};




</script>