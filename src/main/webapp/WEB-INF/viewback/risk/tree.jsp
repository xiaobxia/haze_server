<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/common/back";
%>
<!DOCTYPE html>
<head>
 <meta charset="utf-8">
    <title>${rule.ruleName}依赖关系</title>
<script type="text/javascript" src="<%=path %>/resources/js/echarts.js"></script>
</head>
<body>
		<!-- 图表显示区 -->
		<div id="main" style="width: 3500px; height: 8000px"></div>
<!--		<div id="main" style="width: auto; height: auto"></div>-->
  <script type="text/javascript">
        // 路径配置
         require.config({
            paths: {
                echarts: '<%=path %>/resources/js/build/dist'
            }
        });
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/tree'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                
                var option = {
				    title : {
				        text: '${rule.ruleName}依赖关系'
				    },
				    tooltip : {
				        trigger: 'item',
				       formatter: function (params,ticket,callback){
				        	return  params[1]+'<br>'+params[2];
				        }
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            restore : {show: true}
				        }
				    },
				    calculable : false,

    series : [
        {
            name:'树图',
            type:'tree',
            orient: 'horizontal',  // vertical horizontal
            rootLocation: {x: '5%', y: 'middle'}, // 根节点位置  {x: 'center',y: 10}
            nodePadding: 20,
            symbol: 'circle',
            symbolSize: 40,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        textStyle: {
                            color: '#000',
                            fontSize: 15,
                            fontWeight:  'bolder'
                        }
                    },
                    lineStyle: {
                        color: '#000',
                        width: 1,
                        type: 'broken' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
                    }
                },
                emphasis: {
                    label: {
                        show: true
                    }
                }
            },
            data: ${data}
        }
    ]
};
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
    </script>
</body>