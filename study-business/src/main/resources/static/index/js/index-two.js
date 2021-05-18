

// $(function () {
//     _categoryId = $("#example-select-one option:selected").val();
// })
// let _categoryId;

// function chooseSection(courseId) {
//     $(".moduleItemLeft").removeClass("active");
//     $(this).addClass("active");
// }
$(document).on("click",".moduleItemLeft",function () {
    let courseId = $(this).attr("courseId");
    // alert("courseId="+courseId);
    console.log("courseId="+courseId);
    $(".moduleItemLeft").removeClass("active");
    $(this).addClass("active");
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/business/main/chooseCourse/'+courseId, //请求的路径
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
            console.log(data);
            $("#courseContainer").html(data);
        },
        complete: function () {

        }
    });

});

$(document).on("change","#example-select-one",function () {
    let parentId = $("#example-select-one option:selected").val();
    console.log("parentId_select下拉框改变出发ParentId="+parentId);
    if(parentId.length<=0){
        parentId = 0;
    }
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/business/main/chooseThreeCategory', //请求的路径
        data: {
            // async:true,
            // pageIndex: pageIndex,
            // pageSize: pageSize,
            threeId: parentId,
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
            $("#showTableContainer").html(data);
        },
        complete: function () {

        }
    });
})


// $(document).on("change","#example-select-one",function () {
//     let parentId = $("#example-select-one option:selected").val();
//     console.log("parentId_select下拉框改变出发ParentId="+parentId);
//     if(parentId.length<=0){
//         parentId = 0;
//     }
//     $.ajax({
//         async: true,
//         type: 'POST',  //提交方法是GET
//         url: '/business/main/chooseThreeCategory', //请求的路径
//         data: {
//             // async:true,
//             // pageIndex: pageIndex,
//             // pageSize: pageSize,
//             threeId: parentId,
//             // title: cateTitle,
//             // parentId: parentId,
//             // layer: _layer,
//             // courseId:$('input:radio[name="courseRadio"]:checked').val(),
//         },
//         headers: {
//             contentType: "application/json",
//         },
//         timeout: 10000,
//         error: function () {
//             alert('操作失败。');
//         },
//         success: function (data) {
//             $("#showTableContainer").html(data);
//         },
//         complete: function () {
//
//         }
//     });
// })
