$(function(){
    $('select').each(function (index, item) {
        $(this).on('change', function () {
            var $that = $(this);
            $that.css('color', '#333333')
        })
    });
    //
    // $('.upimg-div').viewer({
    // });
});