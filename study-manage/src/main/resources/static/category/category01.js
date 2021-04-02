
$(function () {

})
/*分页*/
$(document).on("click",".tbpage-item",function (){
    var pageIndex = $(this).attr("pageIndex");

    var pageSize = $('.tbpage-size option:selected').val();
    // 判断所选元素是否为当前页面
    // 若不是当前页面才需要处理
    console.log("pageIndex:"+pageIndex+",pageSize:"+pageSize);
    if($(this).parent().attr("class").indexOf("active")>1){
        console.log("为当前页面");
    }else{
        getCategoryList(pageIndex, pageSize);
    }
})
$(document).on("click",".tbpage-size",function (){
    var pageIndex = $(this).attr("pageIndex");

    var pageSize = $('.tbpage-size option:selected').val();

    getCategoryList(pageIndex, pageSize);
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

//查询子类别

// $(document).on("click","#listChildCategory03",function () {
//     let parentId = $(this).attr("cateId");
//     console.log("cateId="+parentId);
//     getCategoryList02(0,10,parentId,false);
// })


//一级类别页面查询
$(document).on("click","#searchBtn",function () {
    getCategoryList();
})
//二级类别页面查询
$(document).on("click","#searchBtn02",function () {

})
//三级类别页面查询
$(document).on("click","#searchBtn03",function () {

})

/*================================*/

//批量删除
$(document).on("click", "#multiDelete", function () {
    let parentId = $("#parentId_select option:selected").val();
    let child02Id = $("#child02Id_select option:selected").val();
    console.log("parentId="+parentId+",child02Id="+child02Id);
    let idStr = "";
    $(":checkbox[name='check5-td1']:checked").each(function () {
        let id = $(this).val();
        idStr = idStr+ id+",";
    });
    let ids = idStr.slice(0,idStr.length-1);
    layer.confirm('确定删除？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: true,
            type: 'POST',  //提交方法是GET
            url: '/category/delete', //请求的路径
            data: {
                // async:true,
                // pageIndex: pageIndex,
                // pageSize: pageSize,
                categoryIds: ids,
                // courseId:$('input:radio[name="courseRadio"]:checked').val(),
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
                    if(parentId.length>0&&child02Id.length>0){//三级
                        getCategoryList03()
                    }else if(parentId.length>0&&child02Id.length<=0){//二级
                        getCategoryList02()
                    }else{//一级
                        getCategoryList();
                    }
                }else{
                    alert("删除失败");
                }
            },
            complete: function () {

            }
        });
    layer.msg('删除成功', { icon: 1 });
}, function () { });
})


//编辑,获取信息
$(document).on("click", "#editName", function () {
    $("#categoryId").val("");
    $("#title").val("");
    var cateId = $(this).attr("cateId");
    var cateName = $(this).attr("cateName");
    // alert("cate="+cateId+",cateName="+cateName);
    $("#categoryId").val(cateId);
    $("#title").val(cateName);
})
//添加,获取信息
$(document).on("click", "#addName", function () {
    let parentId = $("#addParentId").val();
    if(parentId===0){一级
        $("#categoryId").val("");
        $("#title").val("");
    }else if(parentId===1){二级
        $("#categoryId").val("");
        $("#title").val("");
    }else if(parentId===2){//三级
        $("#categoryId").val("");
        $("#title").val("");
    }
    // let parentId = $("#parentId_select option:selected").val();
    // let child02Id = $("#child02Id_select option:selected").val();
    // if(parentId.length>0&&child02Id.length>0){//三级
    //     $("#addParentId").val("parentId");
    // }else if(parentId.length>0&&child02Id.length<=0){//二级
    //     getCategoryList02()
    // }else{//一级
    //     getCategoryList();
    // }
})
//编辑和添加请求
$(document).on("click","#editSubmitBtn",function (){
    let cateId = $("#categoryId").val();
    let cateTitle = $("#title").val();
    let parentId = $("#addParentId").val();
    console.log("parentId="+parentId);
    if(parentId==0){//一级
        parentId=0;
    }else if(parentId==1){//二级
        parentId = $("#add_parentId_select option:selected").val();
        console.log("parentId="+parentId);
    }else if(parentId==2){//三级
        parentId = $("#add_parentId_select option:selected").val();
    }
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/category/update', //请求的路径
        data: {
            // async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            categoryId: cateId,
            title: cateTitle,
            parentId: parentId,
            // courseId:$('input:radio[name="courseRadio"]:checked').val(),
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
                if(!(cateId.length===0)){
                    layer.msg('更新成功', { icon: 1 });
                }else{
                    layer.msg('添加成功', { icon: 1 });
                }
                getCategoryList();
            }else if(!(cateId.length===0)){
                alert("更新失败");
            }else{
                alert("添加失败");
            }
        },
        complete: function () {

        }
    });
})

//删除
$(document).on("click", "#deleteCategory", function () {
    let parentId = $("#parentId_select option:selected").val();
    let child02Id = $("#child02Id_select option:selected").val();
    let $this = $(this);
    layer.confirm('确定删除？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var cateId = $this.attr("cateId");
        $.ajax({
            async: true,
            type: 'POST',  //提交方法是GET
            url: '/category/delete', //请求的路径
            data: {
                // async:true,
                // pageIndex: pageIndex,
                // pageSize: pageSize,
                categoryIds: cateId,
                // courseId:$('input:radio[name="courseRadio"]:checked').val(),
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
                    if(parentId.length>0&&child02Id.length>0){//三级
                        getCategoryList03()
                    }else if(parentId.length>0&&child02Id.length<=0){//二级
                        getCategoryList02()
                    }else{//一级
                        getCategoryList();
                    }
                }else{
                    alert("删除失败");
                }
            },
            complete: function () {

            }
        });
        layer.msg('删除成功', { icon: 1 });
    }, function () { });
})

function getCategoryList(pageIndex, pageSize) {
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/category/listParent', //请求的路径
        data: {
            async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            // userName: $('#conditionUserName').val(),
            pageIndex:pageIndex,
            pageSize:pageSize,
            title: $('#searchCondition').val(),
            // courseId:$('input:radio[name="courseRadio"]:checked').val(),
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('类别查询失败！请重试。');
        },
        success: function (data) {
            //alert("success");
            // $("#categoryTable").remove();
            $("#tableBox").html(data);
        },
        complete: function () {

        }
    });
}


function getCategoryList02(pageIndex, pageSize) {
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/category/listChild2', //请求的路径
        data: {
            async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            // userName: $('#conditionUserName').val(),
            pageIndex:pageIndex,
            pageSize:pageSize,
            parentId:$("#parentId_select option:selected").val(),
            title: $('#searchCondition').val(),
            // courseId:$('input:radio[name="courseRadio"]:checked').val(),
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('类别查询失败！请重试。');
        },
        success: function (data) {
            alert("success");
            // $("#categoryTable").remove();
                $("#tableBox").html(data);
        },
        complete: function () {

        }
    });
}


function getCategoryList03(pageIndex, pageSize,parentId,async) {
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/category/listChild3', //请求的路径
        data: {
            async:async,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            // userName: $('#conditionUserName').val(),
            pageIndex:pageIndex,
            pageSize:pageSize,
            parentId:parentId,
            title: $('#searchCondition').val(),
            // courseId:$('input:radio[name="courseRadio"]:checked').val(),
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('类别查询失败！请重试。');
        },
        success: function (data) {
            //alert("success");
            // $("#categoryTable").remove();
            $("#tableBox").html(data);
        },
        complete: function () {

        }
    });
}
