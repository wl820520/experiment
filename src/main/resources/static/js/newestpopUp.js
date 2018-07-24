/**
* @author songjie
*/
function show_popup(popupname, closename) {
   var sh = $(window).scrollTop(), dw = $(window).width(),
	dy = $(window).height(), w = $(popupname).width(),
	y = $(popupname).height(), bh = $('body').height();

    var overlay = $('<div class="shade"></div>');
    $('body').append(overlay);
    overlay.css({'height':  bh > dy ? bh:dy + 'px','z-index':'300', opacity: .5}).fadeIn(300);
    $(window).resize(function(){
    	overlay.css('height', bh > $(window).height() ? bh : $(window).height() + 'px');
    });
    $(popupname).is(':visible') ? $(popupname).hide() : $(popupname).show();
    $(popupname).show().css({
       'left': '50%','top': '50%','marginLeft':'-'+ (w/2)+'px','marginTop':'-'+ (y/2)+'px'
    });
    if ($.browser.msie && $.browser.version == '6.0') {
        var timeout = false;
        $(window).scroll(function () {
            if (timeout) {
                clearTimeout(timeout);
            }
            function t(){
                //do   
                var scroll_sh = $(window).scrollTop(), scroll_bh = $('body').height();
                $(popupname).css({ 'position': 'absolute', 'top': (scroll_bh / 2 - y / 2 - scroll_sh) + 'px' });
            };
            timeout = setTimeout(t, 100);
        });
    }
    $(popupname).click(function (e) {
        e.stopImmediatePropagation();
    });
    $(closename).click(function () {
        $('.shade').remove();
        $(popupname).hide();
        return false;
    });
};
function show_popup_noShade(popupname1, closename1) {
   var sh = $(window).scrollTop(), dw = $(window).width(),
	dy = $(window).height(), w = $(popupname1).width(),
	y = $(popupname1).height(), bh = $('body').height();

    $(popupname1).is(':visible') ? $(popupname1).hide() : $(popupname1).show();
    $(popupname1).show().css({
       'left': '50%','top': '50%','marginLeft':'-'+ (w/2)+'px','marginTop':'-'+ (y/2)+'px'
    });
    if ($.browser.msie && $.browser.version == '6.0') {
        var timeout = false;
        $(window).scroll(function () {
            if (timeout) {
                clearTimeout(timeout);
            }
            function t(){
                //do   
                var scroll_sh = $(window).scrollTop(), scroll_bh = $('body').height();
                $(popupname1).css({ 'position': 'absolute', 'top': (scroll_bh / 2 - y / 2 - scroll_sh) + 'px' });
            };
            timeout = setTimeout(t, 100);
        });
    }
    $(popupname1).click(function (e) {
        e.stopImmediatePropagation();
    });
    $(closename1).click(function () {
        $(popupname1).hide();
        return false;
    });
};