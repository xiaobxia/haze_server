<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <%
String path = request.getContextPath()+"";
%>
<head>
    <meta charset="utf-8">
    <title>推广统计</title>
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
                            title: '${channelName}'+'推广统计',
                            url: 'getChannelReportData?channelCode='+'${channelCode}',
                            toolbar: _toolbar,
                            columns: [[
                                // {
                                //     field: 'channelSuperName',
                                //     title: '渠道商',
                                //     width: 120,
                                //     align: 'center'
                                // },
                                {
                                    field: 'channelName',
                                    title: '渠道商名称',
                                    width: 120,
                                    align: 'center'
                                },
                                {
                                    field: 'reportDate',
                                    title: '日期',
                                    width: 120,
                                    align: 'center',
                                    formatter:function(value,row,index){
                                        if(value == null){
                                            return '';
                                        }else{
                                            return new Date(value).format('yyyy-MM-dd');
                                        }
                                    }},
                                {
                                    field: 'uvCount',
                                    title: 'uv数量',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'registerCountResult',
                                    title: '注册量',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'borrowApplyCount',
                                    title: '申请笔数',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'loanCount',
                                    title: '放款笔数',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'repaymentCount',
                                    title: '还款笔数',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'registRatio',
                                    title: '注册率',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'applyRatio',
                                    title: '申请率',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'loanRatio',
                                    title: '下款率',
                                    width: 170,
                                    align: 'center'
                                },
                                {
                                    field: 'repayRatio',
                                    title: '回款率',
                                    width: 170,
                                    align: 'center'
                                }
                               // {
                                    //field: 'attestationRealnameCount',
                                    //title: '实名认证',
                                    //width: 120,
                                   // align: 'center'
                               // }
                                // ,
                                // {
                                //     field: 'attestationBankCount',
                                //     title: '绑卡人数',
                                //     width: 120,
                                //     align: 'center'
                                // },
                                // {
                                //     field: 'contactCount',
                                //     title: '紧急联系人',
                                //     width: 120,
                                //     align: 'center'
                                // },{
                                //     field: 'jxlCount',
                                //     title: '运营商认证',
                                //     width: 120,
                                //     align: 'center'
                                // },{
                                //     field: 'alipayCount',
                                //     title: '支付宝认证人数',
                                //     width: 120,
                                //     align: 'center'
                                // },{
                                //     field: 'zhimaCount',
                                //     title: '芝麻认证人数',
                                //     width: 120,
                                //     align: 'center'
                                // },{
                                //     field: 'companyCount',
                                //     title: '工作信息',
                                //     width: 120,
                                //     align: 'center'
                                // },{
                                //     field: 'blackUserCount',
                                //     title: '黑名单人数',
                                //     width: 120,
                                //     align: 'center'
                                // },
                                // {
                                //     field: 'borrowApplyOutCount',
                                //     title: '申请借款人数',
                                //     width: 120,
                                //     align: 'center'
                                // },
                                // {
                                //     field: 'borrowSucOutCount',
                                //     title: '申请成功人数',
                                //     width: 120,
                                //     align: 'center'
                                // },
                                // {
                                //     field: 'borrowRateOut',
                                //     title: '借款率%',
                                //     width: 120,
                                //     align: 'center'
                                // },
                                // {
                                //     field: 'passRateOut',
                                //     title: '通过率%',
                                //     width: 120,
                                //     align: 'center'
                                // },
                                // {
                                //     field: 'channelid',
                                //     title: '用户注册详情',
                                //     width: 120,
                                //     align: 'center',
                                //     formatter: function(value,rec){
                                //         var btn = "<a name='details' onclick='detailsRow(" + value + "," + rec.reportDate + ")' href='javascript:void(0)'>查看</a>";
                                //         return btn;
                                //     }
                                // }
                            ]],
                            onLoadSuccess:function(data){
                                /*$("a[name='details']").linkbutton({text:'查看',plain:true,iconCls:'icon-search'});
                                $("a[name='details']").bind('click')*/
                            },
                            pagination: true,
                            rownumbers: true,
                            pageSize: 20,
                            singleSelect: true

                        });

            });
    function refresh() {
        $('#datagrid').datagrid('reload');
    }

    function detailsRow(value,reportDate){
        reportDate = getDate(reportDate);
        $('#dd').dialog({
            title: '用户注册详情',
            width: 520,
            height: 600,
            fitCoulms:true,
            closed: false,
            cache: false,
            modal: true
        });
        /*$('#dg2').datagrid({
            url:'getRegisterUser?dateTime='+reportDate+'&channelid='+value,
            // rownumbers:true,
            columns:[[
                {
                    field:'userName',
                     title:'手机号',
                     align:'center',
                     width:200,
                    formatter: function(data){
                        return data.substring(0,3) + '****' + data.substring(7,11);

                    }
                },{
                    field:'createTime',
                    title:'注册时间',
                    align:'center',
                    width:200,
                    formatter: function(data){
                        return getDate(data);
                    }
                },{
                    field:'browerType',
                    title:'来源',
                    align:'center',
                    width:100,
                    formatter: function(data){
                        var browerType = '';
                        if(data == 1)browerType = '安卓';
                        if(data == 2)browerType = 'IOS';
                        if(data == 3)browerType = 'PC';
                        return browerType;
                    }
                }

            ]],
            // pagination:true,
            // pageSize:20,
            // pageList:[20,40,60,80]
        });*/
        //$('#dg2').datagrid('load');
    }

    function search() {
        var start_time = $('#start_time').datebox('getValue');
        var end_time = $('#end_time').datebox('getValue');
        $('#datagrid').datagrid('load', {
            "beginTime":start_time,
            "endTime":end_time
        });
    }

    jQuery(function(){
        $('.textbox-text').tooltip({
            position: 'top',
            content: '<span style="color:#fff">输入日期需大于2017-12-22</span>',
            onShow: function(){
                $(this).tooltip('tip').css({
                    backgroundColor: '#666',
                    borderColor: '#666'
                });
            }
        });
    });
    $.fn.datebox.defaults.formatter = function(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }
    $.fn.datebox.defaults.parser = function(s){
        var t = Date.parse(s);
        if (!isNaN(t)){
            return new Date(t);
        } else {
            return new Date();
        }
    }

    function getDate(timestamp){
        var date = new Date(timestamp);
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate()+1 < 11 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours()+1 < 11 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes()+1 < 11 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds()+1 < 11 ? '0'+(date.getSeconds()) : date.getSeconds());
        var dateTime = Y+M+D+h+m+s;
        // console.log(dateTime);
        return dateTime;
    }

</script>


<div id="p1" class="easyui-panel" title="查询"
     style="padding: 10px; background: #fafafa; margin-bottom: 10px;"
     iconCls="icon-sum" closable="false" collapsible="false"
     minimizable="false" maximizable="false">

    时间：from&nbsp;<input class="easyui-datebox easyui-tooltip" title="输入日期需大于2017-12-22" name="start_time" id="start_time"
                        data-options="required:false,showSeconds:false" style="width:150px">
    to&nbsp;<input class="easyui-datebox" name="end_time" id="end_time"
                   data-options="required:false,showSeconds:false"  style="width:150px">&nbsp;
    <a href="javascript:search();" class="easyui-linkbutton" plain="true"
       iconCls="icon-search">搜索</a>
</div>
<div>
    <table id="datagrid"></table>
</div>
<%--
<div id="dd"><div id="dg2"></div></div>
--%>
