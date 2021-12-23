$(function(){
    $(window).load(function(){
       $('.loading').fadeOut(1000);
    });

    $('.comment').summernote({
        placeholder: "내용을 입력하세요.",
        tabsize: 2,
        height:100
    });
    $('.contents').summernote({
        placeholder: "내용을 입력하세요.",
        tabsize: 2,
        height:300
    });
 });