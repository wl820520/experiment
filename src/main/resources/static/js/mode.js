/**
 * @author haojuan
 */
/*tab切换*/
$.fn.extend({
	//tabsNav 包裹切换手柄ul类名
	//tabsPanel 切换内容标签
	//current 选中当前标签类名
	//num 初始状态显示第几个标签内容
	//eventsth 切换表现 默认为click
	'navtabs':function(tabsNav,tabsTag,tabsPanel,current,num,eventsth){
		var $tabsNavList = $(tabsNav).find(tabsTag), $panel = $(tabsPanel); 
		num ?  $tabsNavList.eq(num).addClass(current) : $tabsNavList.eq(num).removeClass(current);
		eventsth = eventsth || 'click';
		$tabsNavList.each(function(index){
			var $this = $(this);
			$this[eventsth](function(){
				$tabsNavList.removeClass(current);
				$panel.hide();
				$this.addClass(current);
				$panel.eq(index).show();
				return false;
			});
		});
	}
});





