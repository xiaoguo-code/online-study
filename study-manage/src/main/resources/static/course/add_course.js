

$(function () {
    //获取一级类别
    getEditCategoryOne(0,0);

})

function initTag() {
    // 初始化标签控件
    $('.form-control-tag').tagEditor({
        initialTags: [],
        maxTags: 20,
        delimiter: ', ',
        forceLowercase: false,
        removeWithBackspace : true,
        animateDelete: 0,
        placeholder: '请输入标签'
    });
    //初始化标签
    // $('.form-control-tag').tagsInput({
    //     'defaultTest':'请输入路线'
    // })
}
$(document).on("click","#sectionCourseTagClick",function () {
    $("#course_section_tag_module").html("");
    let size = $("#course_section_module").find("div[class='inputbox']").size();
    for (i=0;i<size;i++){
        data2="<div class=\"inputbox\">\n" +
            "                    <span class=\"title\">第"+(i+1)+"章标签</span>\n" +
            "                    <div class=\"inputright\">\n" +
            "                        <input type=\"text\" name=\"courseSectionTag\" class=\"form-control  form-control-tag\"  >\n" +
            "                    </div>\n" +
            "                </div>";
        $("#course_section_tag_module").append(data2);
    }
    //初始化标签
     initTag();
})
/*添加章节*/
$(document).on("click","#addCourseSectionBtn",function () {
    // let obj = $("#course_section_module").find("div[class='inputbox']:last");
    let obj = $("#course_section_module").find("input[name='courseSectionValue']:last");

    // let sectionFlag = obj.attr("section-flag");
    let sectionFlag = obj.val();
    let number = parseInt(sectionFlag)+1;
    data="<div class=\"inputbox\"  id=\"courseSection-"+number+"\">\n" +
        "                    <span class=\"title\" >第"+number+"章</span>\n" +
        "                    <input type=\"hidden\" name=\"courseSectionValue\" value=\""+number+"\" class=\"form-control input-sm\">\n" +
        "                    <div class=\"inputright\">\n" +
        "                        <input type=\"text\" name=\"courseSection\" value=\"\" class=\"form-control input-sm\">\n" +
        "                    </div>\n" +
        "<span class=\"title\" >描述</span>\n" +
        "                    <div class=\"inputright\">\n" +
        "                        <input type=\"text\" name=\"courseSectionDescribe\" value=\"\" class=\"form-control input-sm\">\n" +
        "                    </div>"+
        "                </div>";
    $("#course_section_module").append(data);
    $("#course_section_tag_module").html("");
    let size = $("#course_section_module").find("div[class='inputbox']").size();
    for (i=0;i<size;i++){
        data2="<div class=\"inputbox\">\n" +
            "                    <span class=\"title\">第"+(i+1)+"章标签</span>\n" +
            "                    <div class=\"inputright\">\n" +
            "                        <input type=\"text\" name=\"courseSectionTag\" class=\"form-control  form-control-tag\"  >\n" +
            "                    </div>\n" +
            "                </div>";
        $("#course_section_tag_module").append(data2);
    }


    let size2 = $("#course_section_module").find("div[class='inputbox']").size();
    if(size2==1){
        $("#deleteSection").attr("disabled",true);
    }else{
        $("#deleteSection").attr("disabled",false);
    }
})

/*删除章节*/
$(document).on("click","#deleteSection",function () {
    // let courseSectionValue = $(this).prev().prev().val();

    let size = $("#course_section_module").find("div[class='inputbox']").size();
    if(size==1){

    }else{
        let obj = $("#course_section_module").find("div[class='inputbox']:last");
        obj.remove();
        let obj2 = $("#course_section_tag_module").find("div[class='inputbox']:last");
        obj2.remove();
    }
    let size2 = $("#course_section_module").find("div[class='inputbox']").size();
    if(size2==1){
        $("#deleteSection").attr("disabled",true);
    }else{
        $("#deleteSection").attr("disabled",false);
    }
})

function sectionCheck() {
    let sectionObjs = $("#course_section_module").find("input[name='courseSection']");
    let flag = 0;
    let sectionValues ="";
    sectionObjs.each(function () {
        let sectionValue = $(this).val();
        if(sectionValue.length===0){
            flag ++ ;
        }
        sectionValues = sectionValues + sectionValue +",";
    });
    if(flag>0){
        return "";
    }
    let ids = sectionValues.slice(0,sectionValues.length-1);
    return ids;
}
function sectionDescribeCheck() {
    let sectionObjs = $("#course_section_module").find("input[name='courseSectionDescribe']");
    let flag = 0;
    let sectionValues ="";
    sectionObjs.each(function () {
        let sectionValue = $(this).val();
        if(sectionValue.length===0){
            flag ++ ;
        }
        sectionValues = sectionValues + sectionValue +",";
    });
    if(flag>0){
        return "";
    }
    let ids = sectionValues.slice(0,sectionValues.length-1);
    return ids;
}
function sectionTagCheck() {
    let sectionTagObjs = $("#course_section_tag_module").find("input[name='courseSectionTag']");
    let flag = 0;
    let sectionTagValues ="";
    sectionTagObjs.each(function () {
        let sectionTagValue = $(this).val();
        if(sectionTagValue.length===0){
            flag ++ ;
        }
        sectionTagValues = sectionTagValues + sectionTagValue +"-";
    });
    if(flag>0){
        return "";
    }
    let ids = sectionTagValues.slice(0,sectionTagValues.length-1);
    return ids;
}

/*新增课程*/
function saveCourse(){
    let parentId = $("#example-select option:selected").val();
    let parentId2 = $("#example-select2 option:selected").val();
    let parentId3 = $("#example-select3 option:selected").val();
    let categoryIds = parentId+","+parentId2+","+parentId3;
    let courseName = $("#courseName").val();
    let priority = $("#priority").val();
    if(parentId.length===0||parentId2.length===0||parentId3.length===0||courseName.length===0||priority.length===0){
        layer.msg('请完善课程信息', { icon: 1 });
        return;
    }
    //章节信息校验
    let sectionValues = sectionCheck();
    if(sectionValues.length===0){
        layer.msg('请完善章节信息', { icon: 1 });
        return;
    }
    //检查描述
    let sectionDescribes = sectionDescribeCheck();
    if(sectionDescribes.length===0){
        layer.msg('请完善章节描述信息', { icon: 1 });
        return;
    }
    //章节对应的标签校验
    let sectionTagValues = sectionTagCheck();
    if(sectionTagValues.length===0){
        layer.msg('请完善标签信息', { icon: 1 });
        return;
    }
    // alert("categoryIds="+categoryIds+",courseName="+courseName+",priority="+priority+",sectionValues="+sectionValues+",tag="+sectionTagValues);
    $.ajax({
        async: true,
        type: 'POST',  //提交方法是GET
        url: '/admin/course/save', //请求的路径
        data: {
            courseName:courseName,
            priority:priority,
            categoryIds:categoryIds,
            courseSectionNames:sectionValues,
            courseSectionTagNames:sectionTagValues,
            sectionDescribes:sectionDescribes
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            layer.msg("课程添加失败", { icon: 1 });
        },
        success: function (data) {
            if(data.code===200){
                layer.msg('添加课程成功', { icon: 1 });
                //跳转到课程列表页面
                window.location.href="/admin/course/list";
            }else{
                layer.msg(data.msg, { icon: 1 });
            }
        },
        complete: function () {

        }
    });
}



/*下拉框改变触发查询*/
//获取二级类别
$(document).on("change","#example-select",function () {
    let parentId = $("#example-select option:selected").val();
    console.log("parentId_select下拉框改变出发ParentId="+parentId);
    if(parentId.length===0){
        $("#parentChildSelect2").html("<select id=\"example-select2\" name=\"\" class=\"form-control input-sm\">\n" +
            "                            <option>二级分类</option>\n" +
            "\n" +
            "                        </select>");
        $("#parentChildSelect3").html("<select id=\"example-select3\" name=\"\" class=\"form-control input-sm\">\n" +
            "                            <option>三级分类</option>\n" +
            "\n" +
            "                        </select>");
    }else{
        getEditCategoryOne(1,parentId);
    }
})
//获取三级类别
$(document).on("change","#example-select2",function () {
    let parentId = $("#example-select2 option:selected").val();
    console.log("parentId_select下拉框改变出发ParentId="+parentId);
    if(parentId.length===0){
        $("#parentChildSelect3").html("<select id=\"example-select3\" name=\"\" class=\"form-control input-sm\">\n" +
            "                            <option>三级分类</option>\n" +
            "\n" +
            "                        </select>");
    }else{
        getEditCategoryOne(2,parentId);
    }
})


//获取类别
function getEditCategoryOne(flag,parentId){

    $.ajax({
        async: true,
        type: 'GET',  //提交方法是GET
        url: '/admin/category/getOneCategory', //请求的路径
        data: {
            type:flag,
            parentId:parentId,
        },
        headers: {
            contentType: "application/json",
        },
        timeout: 10000,
        error: function () {
            alert('一级类别下拉框获取失败！请重试。');
        },
        success: function (data) {
            if(flag==0){
                $("#parentChildSelect").html(data);
            }else if(flag==1){
                $("#parentChildSelect2").html(data);
            }else if(flag==2){
                $("#parentChildSelect3").html(data);
            }
        },
        complete: function () {

        }
    });
}




