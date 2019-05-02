<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="/resources/js/jquery.ajaxfileupload.js"></script>
<style type="text/css">
    .title-peizhi {
        font-size: 14px;
        padding: 30px 0 8px;
        margin-bottom: 10px;
        border-bottom: 1px solid #bbb;
        text-indent: 20px;
    }

    /*.title-peizhi:not(:first-child) {*/
    /*paddingd-top: 30px;*/
    /*}*/
    /* #preview {
        border: 1px solid #000;
    } */
    .f-cb:after {
        display: block;
        content: '.';
        clear: both;
        overflow: hidden;
        height: 0;
    }

    .account-table {
        float: left;
        width: 45%;
        border: 1px solid #ddd;
    }

    .account-table tr td {
        line-height: 38px;
        border-bottom: 1px solid #ddd;
        border-right: 1px solid #ddd;
    }

    .account-table tr:last-child td {
        border-bottom: none;

    }

    .account-table tr td:last-child {
        border-right: none;
    }

    .account-table tr td:first-child {
        width: 20%;
        text-align: center;
        font-size: 16px;
    }

    .pageFormContent .account-table tr td .textInput.text-input {
        border: none;
        width: 100%;
        display: inline-block;
        height: 45px;
        line-height: 45px;
        padding: 0;
        text-indent: 20px;
        font-size: 14px;
        background: none;
    }

    .suolue-box {
        width: 15%;
        float: left;
        padding: 5px 0;
        padding-left: 10px;
        border: 1px solid #ddd;
        margin-left: 25px;
    }

    .suolue-box .preview img {
        width: 120px;
    }

</style>
<script type="text/javascript">

    function preview(file) {
        var prevDiv = document.getElementById('preview');
        if (file.files && file.files[0]) {
            var reader = new FileReader();
            reader.onload = function (evt) {
                prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';
            }
            reader.readAsDataURL(file.files[0]);
        } else {
            prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
        }
    }

    $("#img_upload_btn").ajaxfileupload({
        action: 'oss/upload/picture',
        onStart: function () {
            $("#submit_btn").attr('disabled', true);
            $('.upload-loading').css({
                display:'block'
            })
        },
        onComplete: function (data) {
            if (data.result == "success") {
                var img_url = $('#pictureUrl').val(data.data);
                $('#imagePath').attr('src', img_url);
                $('.upload-loading').css({
                    display:'none'
                })
                alertMsg.correct('上传成功！')
            } else {
                alert(data.message);
            }
            $("#submit_btn").removeAttr("disabled");
        },
    });

    function previewWe(file) {
        var prevDiv = document.getElementById('previewWe');
        if (file.files && file.files[0]) {
            var reader = new FileReader();
            reader.onload = function (evt) {
                prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';
            }
            reader.readAsDataURL(file.files[0]);
        } else {
            prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
        }
    }

    $("#img_upload_btn_we").ajaxfileupload({
        action: 'oss/upload/picture',
        onStart: function () {
            $("#submit_btn").attr('disabled', true);
            $('.upload-loading').css({
                display:'block'
            })
        },
        onComplete: function (data) {
            if (data.result == "success") {
                var img_url = $('#wechatPictureUrl').val(data.data);
                $('#imagePathWe').attr('src', img_url);
                $('.upload-loading').css({
                    display:'none'
                })
                alertMsg.correct('上传成功！')
            } else {
                alert(data.message);
            }
            $("#submit_btn").removeAttr("disabled");
        },
    });

</script>
<div class="pageContent">
    <form method="post" action="/back/banner/accountAndTelManage"
          enctype="multipart/form-data" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <c:if test="${params.parentId != null }">
            <input type="hidden" name="parentId" value="${params.parentId}"/>
        </c:if>
        <input type="hidden" id="pictureUrl" name="pictureUrl" value="${qrCode}"/>
        <input type="hidden" id="wechatPictureUrl" name="wechatPictureUrl" value="${wechatCode}"/>
        <div class="pageFormContent" layoutH="56">
            <div class="peizhi-pannel f-cb">
                <div class="title-peizhi">支付宝账号配置</div>
                <table class="account-table">
                    <tr>
                        <td>支付宝账号</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="payAccount"
                                   value="${payAccount}">
                        </td>
                    </tr>
                    <tr>
                        <td> 收款人</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="accountName"
                                   value="${accountName}">
                        </td>
                    </tr>
                </table>
                <div class="suolue-box">
                    <div class="preview">
                        <input type="file" accept="image/png,image/gif" name="file" onchange="preview(this)"
                               id="img_upload_btn"/>
                    </div>

                    <div class="preview" id="preview">
                        <div style="width: 120px;padding: 10px 0;display: none;" class="upload-loading">
                            <img style="width: 40px;display: block;margin: 0 auto;" src="<%=request.getContextPath()%>/back/files/loading.gif" alt="">
                        </div>
                        <img alt="" src="${qrCode }" id="imagePath">
                    </div>
                    <p>二维码: (500 x 500 以内)</p>
                </div>
            </div>
            <div class="peizhi-pannel f-cb">
                <div class="title-peizhi">微信账号配置</div>
                <table class="account-table">
                    <tr>
                        <td>微信账号</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="payAccountWe"
                                   value="${payAccount}">
                        </td>
                    </tr>
                    <tr>
                        <td> 收款人</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="weAccountName"
                                   value="${weAccountName}">
                        </td>
                    </tr>
                </table>
                <div class="suolue-box">
                    <div class="preview">
                        <input type="file" accept="image/png,image/gif" name="file" onchange="previewWe(this)"
                               id="img_upload_btn_we"/>
                    </div>

                    <div class="preview" id="previewWe">
                        <div style="width: 120px;padding: 10px 0;display: none;" class="upload-loading">
                            <img style="width: 40px;display: block;margin: 0 auto;" src="<%=request.getContextPath()%>/back/files/loading.gif" alt="">
                        </div>
                        <img alt="" src="${wechatCode }" id="imagePathWe">
                    </div>
                    <p>二维码: (500 x 500 以内)</p>
                </div>
            </div>
            <div class="peizhi-pannel f-cb">
                <div class="title-peizhi">客服电话配置</div>
                <table class="account-table">

                    <tr>
                        <td>客服qq</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="serviceQQ"
                                   value="${serviceQQ}">
                        </td>
                    </tr>

                    <tr>
                        <td>客服电话</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="serviceTel"
                                   value="${serviceTel}">
                        </td>
                    </tr>

                    <tr>
                        <td>QQ/在线客服<br/>工作时间</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="onlineTime"
                                   value="${onlineTime}">
                        </td>
                    </tr>

                    <tr>
                        <td>电话客服工作时间</td>
                        <td>
                            <input class="text-input" type="text" placeholder="请输入" name="telTime" value="${telTime}">
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit" id="submit_btn">提交</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>

</div>