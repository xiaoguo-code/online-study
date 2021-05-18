

$(function () {
    _categoryId = $("#example-select-one option:selected").val();
})
let _categoryId;



$(document).on("change","#example-select-one",function () {
    let parentId = $("#example-select-one option:selected").val();
    _categoryId=parentId;
    console.log("parentId_select下拉框改变出发ParentId="+parentId);
    // alert("parentId_select下拉框改变出发ParentId="+parentId);
    if(parentId.length<=0){
        parentId = 0;
    }
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/business/main/chooseOneCategory', //请求的路径
        data: {
            // async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            categoryId: parentId,
            // title: cateTitle,
            // parentId: parentId,
            // layer: _layer,
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
            $("#courseContainer").html(data);
        },
        complete: function () {

        }
    });
})
