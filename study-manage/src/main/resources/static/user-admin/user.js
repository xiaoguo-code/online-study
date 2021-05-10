

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
        getStudyLineList(pageIndex, pageSize);
    }
})
$(document).on("click",".tbpage-size",function (){
    var pageIndex = $(this).attr("pageIndex");

    var pageSize = $('.tbpage-size option:selected').val();
    _pageSize = pageSize;
    getStudyLineList(pageIndex, pageSize);
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
    getUserList(1,_pageSize);
})

/*新增课程*/
function saveUser(){

    let userName = $("#userName").val();
    let realName = $("#realName").val();
    let email = $("#email").val();
    let telPhone = $("#telPhone").val();
    let password = $("#password").val();
    let rePassword = $("#rePassword").val();
    if(userName.length===0||realName.length===0||email.length===0||telPhone.length===0){
        layer.msg('请完善信息', { icon: 1 });
        return;
    }
    if(password==rePassword){

    }else{
        layer.msg('两次密码不一致', { icon: 1 });
        return;
    }

    // alert("categoryIds="+categoryIds+",courseName="+courseName+",priority="+priority+",sectionValues="+sectionValues+",tag="+sectionTagValues);
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/admin/user/save', //请求的路径
        data: {
            userName:userName,
            realName:realName,
            email:email,
            telPhone:telPhone,
            password:password
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            layer.msg("添加失败", { icon: 1 });
        },
        success: function (data) {
            if(data.code===200){
                layer.msg('添加成功', { icon: 1 });
                //跳转到课程列表页面
                window.location.href="/admin/user/listUser";
            }else{
                layer.msg(data.msg, { icon: 1 });
            }
        },
        complete: function () {

        }
    });
}

function editUser(){

    let userId = $("#userId").val();
    let userName = $("#userName").val();
    let realName = $("#realName").val();
    let email = $("#email").val();
    let telPhone = $("#telPhone").val();
    let password = $("#password").val();
    let rePassword = $("#rePassword").val();
    if(userName.length===0||realName.length===0||email.length===0||telPhone.length===0){
        layer.msg('请完善信息', { icon: 1 });
        return;
    }
    if(password==rePassword){

    }else{
        layer.msg('两次密码不一致', { icon: 1 });
        return;
    }

    // alert("categoryIds="+categoryIds+",courseName="+courseName+",priority="+priority+",sectionValues="+sectionValues+",tag="+sectionTagValues);
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/admin/user/update', //请求的路径
        data: {
            userId:userId,
            userName:userName,
            realName:realName,
            email:email,
            telPhone:telPhone,
            password:password
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            layer.msg("修改失败", { icon: 1 });
        },
        success: function (data) {
            if(data.code===200){
                layer.msg('修改成功', { icon: 1 });
                //跳转到课程列表页面
                window.location.href="/admin/user/listUser";
            }else{
                layer.msg(data.msg, { icon: 1 });
            }
        },
        complete: function () {

        }
    });
}

/*删除路线*/
$(document).on("click","#deleteLineBtn",function () {
    let userId = $(this).attr("userId");
    // alert("studyLineId="+studyLineId);
    deletes(userId);
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
function deletes(userIds){
    layer.confirm('确定删除？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: true,
            type: 'POST',  //提交方法是GET
            url: '/admin/user/delete', //请求的路径
            data: {
                userIds: userIds,
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
                    getUserList(1,_pageSize);
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

//查询
function getUserList(pageIndex, pageSize) {
    let keyword = $("#searchCondition").val();
    // alert("parentId："+parentId+"keyword："+keyword);
    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/user/listUser', //请求的路径
        data: {
            async:true,
            pageIndex:pageIndex,
            pageSize:pageSize,
            userName: keyword,
            IDCard:keyword,
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('查询失败！请重试。');
        },
        success: function (data) {
            $("#tableBox").html(data);
        },
        complete: function () {

        }
    });
}




















