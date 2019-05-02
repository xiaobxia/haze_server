<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <%
String path = request.getContextPath()+"";
%>
<head>
    <meta charset="utf-8">
    <title>推广统计查看码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="<%=path %>/common/jquery-easyui-1.4.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/common/jquery-easyui-1.4.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/common/jquery-easyui-1.4.4/themes/icon.css">
    <script type="text/javascript" src="<%=path %>/common/back/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${path }/common/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
</head>

<script type="text/javascript">
    Date.prototype.format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    $(document)
        .ready(
            function () {
                var _toolbar = [{
                    id: 'btnRefresh',
                    text: '刷新',
                    iconCls: 'icon-reload',
                    handler: function () {
                        refresh();
                    }
                }];
                $('#datagrid')
                    .datagrid(
                        {
                            url: 'channelCodesData',
                            toolbar: _toolbar,
                            columns: [[
                                {
                                    field: 'name',
                                    title: '渠道商',
                                    width: 120,
                                    align: 'center'
                                },
                                {
                                    field: 'aesCodes',
                                    title: 'code',
                                    width: 300,
                                    align: 'center'
                                }
                            ]],

                            pagination: true,
                            rownumbers: true,
                            pageSize: 10,
                            singleSelect: true

                        });

            });
    function refresh() {
        $('#datagrid').datagrid('reload');
    }

    function search() {
//        var start_time = $('#start_time').datebox('getValue');
//        var end_time = $('#end_time').datebox('getValue');
        var channelName = $('#channelName').val();
        $('#datagrid').datagrid('load', {
            "channelName":channelName
        });
    }

</script>


<div id="p1" class="easyui-panel" title="查询"
     style="padding: 10px; background: #fafafa; margin-bottom: 10px;"
     iconCls="icon-sum" closable="false" collapsible="false"
     minimizable="false" maximizable="false">
    <%--时间：from&nbsp;<input class="easyui-datebox " name="start_time" id="start_time"--%>
                        <%--data-options="required:false,showSeconds:false" style="width:150px">--%>
    <%--to&nbsp;<input class="easyui-datebox" name="end_time" id="end_time"--%>
                   <%--data-options="required:false,showSeconds:false"  style="width:150px">&nbsp;--%>
      渠道商:  <input type="text" id="channelName"/>
    <a href="javascript:search();" class="easyui-linkbutton" plain="true"
       iconCls="icon-search">搜索</a>
</div>
<di>
    <table id="datagrid"></table>
</di>

