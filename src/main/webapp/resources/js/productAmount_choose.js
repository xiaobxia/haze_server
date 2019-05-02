var currentPage = navTab.getCurrentPanel();

function selectOptionAjax(id_val){
    $.ajax({
        type: "post",
        dataType: "json",
        url: "backBorrowOrder/getProductConfig",
        async:false,
        success: function (data) {
            $("#" + id_val, currentPage).append("<option value=''>全部</option>");
            var str = "";
            $.each(data.data, function(index,value){
                str += "<option value='" + value.borrowAmount + "'>" + value.borrowAmount/100 + "</option>";
            });
            $("#" + id_val, currentPage).append(str);
        },
        error: function(){
            alert("系统异常，请稍后重试!");
        }
    });
}

$(function () {
    $("#productAmount", currentPage).empty();
    selectOptionAjax("productAmount");
    var chooseVal = $('#product_amount_choosed', currentPage).val();
    if(chooseVal.length > 0) {
        $(" #productAmount option[value='"+chooseVal+"']", currentPage).attr("selected","selected");
    }
});