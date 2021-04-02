




$(document).on("click","#listChildCategory02",function () {
    let parentId = $(this).attr("cateId");
    console.log("cateId="+parentId);
    console.log(window.parent);
    window.parent.$("#listParent").removeClass("activelinks");
    window.parent.$("#listChild2").addClass("activelinks");

    // $("#listParent").removeClass("activelinks");
    // $("#listChild2").addClass("activelinks");


})
