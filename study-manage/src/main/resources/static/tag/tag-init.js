$(function () {
    // 初始化标签控件
    $('.form-control-tag').tagEditor({
        initialTags: [],
        maxTags: 20,
        delimiter: ', ',
        forceLowercase: false,
        removeWithBackspace : true,
        animateDelete: 0,
        placeholder: '请输入路线'
    });
    //初始化标签
    // $('.form-control-tag').tagsInput({
    //     'defaultTest':'请输入路线'
    // })
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
        placeholder: '请输入路线'
    });
    //初始化标签
    // $('.form-control-tag').tagsInput({
    //     'defaultTest':'请输入路线'
    // })
}
