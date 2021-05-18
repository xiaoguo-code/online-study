



$(function () {

})



let _pageSize;
/*分页*/
$(document).on("click",".tbpage-item",function (){
    var pageIndex = $(this).attr("pageIndex");

    var pageSize = $('.tbpage-size option:selected').val();
    // 判断所选元素是否为当前页面
    // 若不是当前页面才需要处理
    console.log("pageIndex:"+pageIndex+",pageSize:"+pageSize);
    _pageSize = pageSize;
    if($(this).parent().attr("class").indexOf("active")>1){
        console.log("为当前页面");
    }else{
        getCommentList(pageIndex, pageSize);
    }
})
$(document).on("click",".tbpage-size",function (){
    var pageIndex = $(this).attr("pageIndex");

    var pageSize = $('.tbpage-size option:selected').val();
    _pageSize = pageSize;
    getCommentList(pageIndex, pageSize);
})
/*分页-end*/



/*全选全不选start*/
$(document).on("click", "#checkBoxAll", function () {
    $(":checkbox[name='check5-td1']").prop("checked", this.checked); // this指代的你当前选择的这个元素的JS对象
})
$(document).on("click", "#check5-td1", function () {
    let len = $(":checkbox[name='check5-td1']").length;
    var sum = $(":checkbox[name='check5-td1']:checked").length;
    if (len==sum){
        $("#checkBoxAll").prop("checked",true);
    }else{
        $("#checkBoxAll").prop("checked",false);
    }
})
/*全选全不选end*/

/*根据课程名查询-搜索框*/
/*搜索*/
$(document).on("click","#searchBtn",function () {
    let keyword = $("#searchCondition").val();
    // alert("路线查询关键字："+keyword);
    getCommentList(1,_pageSize);
})

/*下拉框改变触发查询*/
$(document).on("change","#parentId_select",function () {
    let parentId = $("#parentId_select option:selected").val();
    console.log("parentId_select下拉框改变出发ParentId="+parentId);
    getCommentList(1,_pageSize);
})



/*删除课程*/
$(document).on("click","#deleteLineBtn",function () {
    let commentId = $(this).attr("commentId");
    //  alert("courseId="+courseId);
     deletes(commentId);
})
/*批量删除课程*/
$(document).on("click","#multiDeleteBtn",function () {
    let idStr = "";
    $(":checkbox[name='check5-td1']:checked").each(function () {
        let id = $(this).val();
        if(id.length!=0){
            idStr = idStr+ id+",";
        }
    });
    let ids = idStr.slice(0,idStr.length-1);
    deletes(ids);
})
function deletes(commentIds){
    layer.confirm('确定删除？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: true,
            type: 'POST',  //提交方法是GET
            url: '/admin/comment/delete', //请求的路径
            data: {
                commentIds: commentIds,
            },
            headers: {
                contentType: "application/json",
            },
            timeout: 10000,
            error: function () {
                layer.msg('删除失败', { icon: 1 });
            },
            success: function (data) {
                if(data.code===200){
                    layer.msg('删除成功', { icon: 1 });
                    getCommentList(1,_pageSize);
                }else{
                    layer.msg('删除失败', { icon: 1 });
                }
            },
            complete: function () {

            }
        });

    }, function () { });
}

function getCommentList(pageIndex,pageSize) {

    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/comment/list', //请求的路径
        data: {
            async:true,
            pageIndex:pageIndex,
            pageSize:pageSize,
            content: $("#searchCondition").val(),
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('课程查询失败！请重试。');
        },
        success: function (data) {
            // alert("success");
            // $("#categoryTable").remove();
            $("#tableBox").html(data);
            // getCategoryOne();
            initTag();

        },
        complete: function () {

        }
    });
}
//获取一级类别
function getCategoryOne(){

    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/category/getOneCategory', //请求的路径
        data: {
            parentId:0,
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('一级类别下拉框获取失败！请重试。');
        },
        success: function (data) {
            //alert("success");
            // $("#categoryTable").remove();
            $("#parentChildSelect").html(data);
            // $("#add_parentId").html(data);
        },
        complete: function () {

        }
    });
}
