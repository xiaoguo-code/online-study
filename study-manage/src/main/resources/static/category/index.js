

$(function () {
    getCategoryMenu();
    // getCategoryMenu2();
})

function getCategoryMenu() {
    console.log("获取类别菜单。");
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/admin/category/listCategoryMenu', //请求的路径
        data: {
            // async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            // categoryId: cateId,
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
            console.log("ajax返回成功");
            $("#parentCategoryMenu").html(data);
        },
        complete: function () {

        }
    });
}
function getCategoryMenu2() {
    console.log("获取类别菜单。");
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/admin/category/listCategoryMenu2', //请求的路径
        data: {

        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('操作失败。');
        },
        success: function (data) {
            console.log("ajax返回成功");
            $("#parentCategoryMenu2").html(data);
        },
        complete: function () {

        }
    });
}

$(document).on("click","#listChildCategory02",function () {
    let parentId = $(this).attr("cateId");
    console.log("cateId="+parentId);
    console.log(window.parent);
    window.parent.$("#listParent").removeClass("activelinks");
    window.parent.$("#listChild2").addClass("activelinks");

    // $("#listParent").removeClass("activelinks");
    // $("#listChild2").addClass("activelinks");


})
