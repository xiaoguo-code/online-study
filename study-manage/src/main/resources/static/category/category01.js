
$(function () {
    getCategory01();
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
        getCategoryList(pageIndex, pageSize);
    }
})
$(document).on("click",".tbpage-size",function (){
    var pageIndex = $(this).attr("pageIndex");

    var pageSize = $('.tbpage-size option:selected').val();
    _pageSize = pageSize;
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
    getCategoryList(0,_pageSize);
})


/*================================*/

/*下拉框改变触发查询*/
$(document).on("change","#parentId_select",function () {
    let parentId = $("#parentId_select option:selected").val();
    console.log("parentId_select下拉框改变出发ParentId="+parentId);
    if(parentId.length<=0){
        parentId = 0;
    }
    getCategory02();
    getCategoryList(0,_pageSize);
})
$(document).on("change","#childId_select",function () {
    let parentId = $("#childId_select option:selected").val();
    console.log("childId_select下拉框改变出发ParentId="+parentId);
    getCategoryList(0,_pageSize);
})
//新增下拉框
$(document).on("change","#add_parentId_select",function () {
    let parentId = $("#add_parentId_select option:selected").val();
    console.log("add_parentId_select下拉框改变出发ParentId="+parentId);
    if(parentId.length<=0){
        parentId = 0;
    }
    getCategory022(parentId);
    getCategoryList(0,_pageSize);
})
$(document).on("change","#add_childId_select",function () {
    let parentId = $("#add_childId_select option:selected").val();
    console.log("add_childId_select下拉框改变出发ParentId="+parentId);
    getCategoryList(0,_pageSize);
})

//编辑,获取信息
$(document).on("click", "#editName", function () {
    $("#editCategoryId").val("");
    $("#editTitle").val("");
    $("#editTitleDescribe").val("");
    let cateId = $(this).attr("cateId");
    let cateName = $(this).attr("cateName");
    let cateTitleDescribe = $(this).attr("cateTitleDescribe");

    $("#editCategoryId").val(cateId);
    $("#editTitle").val(cateName);
    $("#editTitleDescribe").val(cateTitleDescribe);

})

$(document).on("click","#editSubmitBtn02",function () {
    let cateId = $("#editCategoryId").val();
    let cateTitle = $("#editTitle").val();
    let editTitleDescribe = $("#editTitleDescribe").val();
    // alert("da"+editTitleDescribe);
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/admin/category/update', //请求的路径
        data: {
            // async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            categoryId: cateId,
            title: cateTitle,
            titleDescribe: editTitleDescribe,
            // courseId:$('input:radio[name="courseRadio"]:checked').val(),
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('操作失败。');
        },
        success: function (data) {
            if(data.code===200){
                if(!(cateId.length===0)){
                    layer.msg('更新成功', { icon: 1 });
                }else{
                    layer.msg('删除成功', { icon: 1 });
                }
                getCategoryList(0,_pageSize);
                getCategory01();//更新下拉框类别信息
                getCategory02();
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

//添加,获取信息
$(document).on("click", "#addName", function () {
    $("#categoryId").val("");
    $("#title").val("");
    $("#titleDescribe").val("");
    getCategory011();
    data = "<select id=\"add_childId_select\" name=\"val_skill\" class=\"form-control \">\n" +
        "                                            <option value=\"\" >二级类别...</option>\n" +
        "                                        </select>"
    $("#add_childId").html(data);
})
$(document).keyup(function (e){
    console.log(e.which);
    if(e.keyCode===13){

    }
})


//编辑和添加请求
$(document).on("click","#editSubmitBtn",function (){
    let cateId = $("#categoryId").val();
    let cateTitle = $("#title").val();
    let titleDescribe = $("#titleDescribe").val();
    let parentId =  $("#add_parentId_select option:selected").val();
    let childId =  $("#add_childId_select option:selected").val();
    let _layer =2;
    // alert("title="+titleDescribe);
    console.log("1.parentId="+parentId);
    console.log("childId="+childId);
    if(parentId.length>0&&childId.length>0){
        parentId = childId;
        _layer=3;
    }else if(parentId.length<=0&&childId.length<=0){
        parentId = 0;
        _layer =1;
    }
    console.log("2.parentId="+parentId);
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/admin/category/update', //请求的路径
        data: {
            // async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            categoryId: cateId,
            title: cateTitle,
            parentId: parentId,
            layer: _layer,
            titleDescribe: titleDescribe,
            // courseId:$('input:radio[name="courseRadio"]:checked').val(),
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('操作失败。');
        },
        success: function (data) {
            if(data.code===200){
                if(!(cateId.length===0)){
                    layer.msg('更新成功', { icon: 1 });
                }else{
                    layer.msg('添加成功', { icon: 1 });
                }
                getCategoryList(0,_pageSize);
                getCategory01();//更新下拉框类别信息
                getCategory02();
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
//批量删除
$(document).on("click", "#multiDelete", function () {
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
            url: '/admin/category/delete', //请求的路径
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
                    getCategoryList(0,_pageSize);
                    getCategory01();
                    getCategory02();
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
//删除
$(document).on("click", "#deleteCategory", function () {
    // let parentId = $("#parentId_select option:selected").val();
    // let child02Id = $("#child02Id_select option:selected").val();
    let $this = $(this);
    layer.confirm('确定删除？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var cateId = $this.attr("cateId");
        $.ajax({
            async: true,
            type: 'POST',  //提交方法是GET
            url: '/admin/category/delete', //请求的路径
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
                    getCategoryList(0,_pageSize);
                    getCategory01();
                    getCategory02();
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
    let parentId =  $("#parentId_select option:selected").val();
    let childId =  $("#childId_select option:selected").val();
    if(parentId.length>0&&childId>0){
        parentId = childId;
    }else if(parentId.length<=0&&childId.length<=0){
        parentId = 0;
    }
    console.log("getCategoryList::parentId="+parentId);
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/category/listParent', //请求的路径
        data: {
            async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            // userName: $('#conditionUserName').val(),
            pageIndex:pageIndex,
            pageSize:pageSize,
            title: $('#searchCondition').val(),
            parentId:parentId,
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

//获取一级类别
function getCategory01(){
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/category/getParentCategory', //请求的路径
        data: {
            parentId:$("#parentId_select").val(),
            type:1,
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

//获取二级类别
function getCategory02(){
    let parentId = $("#parentId_select option:selected").val();
    console.log("parentId_select下拉框改变出发ParentId="+parentId);
    if(parentId.length<=0){
        parentId = 0;
    }
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/category/getchildCategory', //请求的路径
        data: {
            parentId:parentId,
            type:1,
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
            $("#ChildSelect").html(data);
            // $("#add_childId").html(data);
        },
        complete: function () {

        }
    });
}
//add获取一级类别
function getCategory011(parentId){
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/category/getParentCategory', //请求的路径
        data: {
            parentId:parentId,
            type:2,
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
            // $("#parentChildSelect").html(data);
            $("#add_parentId").html(data);
        },
        complete: function () {

        }
    });
}

//add获取二级类别
function getCategory022(parentId){
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/category/getchildCategory', //请求的路径
        data: {
            parentId:parentId,
            type:2,
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
            // $("#ChildSelect").html(data);
            $("#add_childId").html(data);
        },
        complete: function () {

        }
    });
}


