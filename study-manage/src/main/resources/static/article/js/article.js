// Tags Input
$('#tags').tagsInput({
    width: '100%',
    height: '35px',
    defaultText: '请输入文章标签'
});

$('.toggle').toggles({
    on: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});

$(".select2").select2({
    width: '100%'
});

var tale = new $.tale();

/**
 * 保存文章
 * @param status
 */
function subArticle(status) {
    var title = $('#articleForm input[name=title]').val();
    var content = $('#text').val();
    if (title == '') {
        tale.alertWarn('请输入文章标题');
        return;
    }
    if (content == '') {
        tale.alertWarn('请输入文章内容');
        return;
    }
    let val = $("#allow_comment").val();

    $('#content-editor').val(content);
    $("#articleForm #status").val(status);
    $("#articleForm #categories").val($('#multiple-sel').val());
    $("#articleForm #type").val($('#multiple-type').val());
    var params = $("#articleForm").serialize();
    console.log(JSON.stringify(params));
    var url = $('#articleForm #cid').val() != '' ? '/admin/article/modify' : '/admin/article/publish';
    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.code == '200') {
                tale.alertOk({
                    text:'文章保存成功',
                    then: function () {
                        setTimeout(function () {
                            console.log("请求成功");
                            window.location.href = '/admin/article/list';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '保存文章失败');
            }
        }
    });
}


function allow_comment(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_comment').val(true);
    }
    if (off == 1) {
        $('#allow_comment').val(false);
    }
}

function allow_ping(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_ping').val(false);
    }
    if (off == 1) {
        $('#allow_ping').val(true);
    }
}


function allow_feed(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_feed').val(false);
    }
    if (off == 1) {
        $('#allow_feed').val(true);
    }
}

$('div.allow-false').toggles({
    off: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});

$('#multiple-type').change(function () {
    var categoryIds = $('#multiple-type').val();
    console.log("归属分类："+categoryIds);
    // $.ajax({
    //     async: true,
    //     type: 'GET',  //提交方法是GET
    //     url: '/admin/article/getSection', //请求的路径
    //     data: {
    //         categoryIds:categoryIds,
    //     },
    //     headers: {
    //         contentType: "application/json",
    //     },
    //     timeout: 10000,
    //     error: function () {
    //         alert('查询章节失败！请重试。');
    //     },
    //     success: function (data) {
    //         //操作后进行查询
    //         console.log("归属分类："+data);
    //         $("#sectionSelect").html(data);
    //     },
    //     complete: function () {
    //
    //     }
    // });

    // var tags = $('#tags');
    // var categories = $('#multiple-sel');
    // if(postType == 'post'){
    //     $('#tags_tagsinput').show(500);
    //     $('#s2id_multiple-sel').show(500);
    //     $('#comment-div').attr("style","display:block;");
    // }else {
    //     $('#tags_tagsinput').hide(500);
    //     $('#s2id_multiple-sel').hide(500);
    //     $('#comment-div').attr("style","display:none;");
    //
    // }
});





$(function () {
    // var postType = $('#multiple-type').val();
    // var tags = $('#tags');
    // var categories = $('#multiple-sel');
    // if(postType == 'post'){
    //     $('#tags_tagsinput').show();
    //     $('#s2id_multiple-sel').show();
    //     $('#comment-div').attr("style","display:block;");
    // }else {
    //     $('#tags_tagsinput').hide();
    //     $('#s2id_multiple-sel').hide();
    //     $('#comment-div').attr("style","display:none;");
    //
    // }
});


