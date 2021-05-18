

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
        getArticleList(pageIndex, pageSize);
    }
})
$(document).on("click",".tbpage-size",function (){
    var pageIndex = $(this).attr("pageIndex");

    var pageSize = $('.tbpage-size option:selected').val();
    _pageSize = pageSize;
    getArticleList(pageIndex, pageSize);
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

/*搜索*/
$(document).on("click","#searchBtn",function () {
    let keyword = $("#searchCondition").val();
    // alert("路线查询关键字："+keyword);
    _pageSize=10;
    getArticleList(1,_pageSize);
})

/*保存和修改路线*/
$(document).on("click","#saveLineBtn",function () {
    let studyLineId = $(this).attr("studyLineId");
    let levelId = $(this).attr("levelId");
    let courseNames = $(this).parent().parent().prev()
        .prev().prev().find(".form-control-tag").val();
    // alert("studyLineId="+studyLineId+",levelId="+levelId+",courseNames="+courseNames);
    layer.confirm('确定保存？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: true,
            type: 'POST',  //提交方法是GET
            url: '/admin/studyLine/save', //请求的路径
            data: {
                async:true,
                lineId:studyLineId,
                courseNames:courseNames,
                categoryId:levelId,
            },
            headers: {
                contentType: "application/json",
            },
            timeout: 10000,
            error: function () {
                alert('路线保存失败！请重试。');
            },
            success: function (data) {
                //操作后进行查询
                getArticleList(1,_pageSize);
            },
            complete: function () {

            }
        });
        layer.msg('保存成功', { icon: 1 });
    }, function () { });
})

/*删除路线*/
$(document).on("click","#deleteLineBtn",function () {
    let blogId = $(this).attr("blogId");
    // alert("studyLineId="+studyLineId);
    deletes(blogId);
})
/*批量删除路线*/
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
function deletes(blogIds){
    layer.confirm('确定删除？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: true,
            type: 'POST',  //提交方法是GET
            url: '/admin/article/delete', //请求的路径
            data: {
                blogIds: blogIds,
            },
            headers: {
                contentType: "application/json",
            },
            timeout: 10000,
            error: function () {
                alert('删除失败。');
            },
            success: function (data) {
                if(data.code===200){
                    getArticleList(1,_pageSize);
                }else{
                    alert("删除失败");
                }
            },
            complete: function () {

            }
        });
        layer.msg('删除成功', { icon: 1 });
    }, function () { });
}

//查询学习路线
function getArticleList(pageIndex, pageSize) {
    let keyword = $("#searchCondition").val();
    // alert("parentId："+parentId+"keyword："+keyword);
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/article/list', //请求的路径
        data: {
            async:true,
            pageIndex:pageIndex,
            pageSize:pageSize,
            title: keyword,
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('查询失败！请重试。');
        },
        success: function (data) {
            // alert("success");
            // $("#categoryTable").remove();
            $("#tableBox").html(data);
        },
        complete: function () {

        }
    });
}




















