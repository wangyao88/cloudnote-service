function gotoPage(pageUrl) {
    $.ajax({
        url : pageUrl,
        type : "get",
        success : function(result) {
            $("#main_content").html(result);
        },
        error : function() {
            alert("获取页面信息失败，请稍候重试！");
        }
    });
}