/*
 * @author zhujunyan
 */
/******************************* 优信拍 op后台 用到的js **************************************/
/****body 追加class ****/
$(function(){
	$('body').addClass('body_bg');
})
function $selectF(select,selectTop,selectUl,selectA){
    var $defineSelect=$(select),$selectTop=$(selectTop),$option=$(selectUl),$subOption=$(selectA);
        $defineSelect.each(function(){
        		$(this).click(function(e){        			
        			$(this).parents('table').find('.inpSelect').removeClass('z-index1');
        			$defineSelect.not(this).children(selectUl).hide();     			
        			$(this).children(selectUl).toggle();
        			if($(this).children(selectUl).is(':visible')&&$(this).children(selectUl).height()>=200){
        				$(this).children(selectUl).css({'height':'200px','overflow-y':'scroll'});
        			}else{
        				$(this).children(selectUl).css({'height':'auto','scroll-y':'hidden'});
        			};//if
        			        			
        			$(this).addClass('z-index1');
        			
        			e.stopPropagation();
        		});
        });
        $subOption.each(function(){
            var $this=$(this);
                $this.click(function(){
                	$this.parents(select).children(selectTop).text($this.text());
                    $this.parents(select).children('input').val($this.attr('rel'));
                    $this.parents(select).children('input').trigger("change");
                });
        });
        $(document).click(function(){
        	$option.hide();
        }); 
};//over
$(function(){
	$selectF(".inpSelect",".inpSelect .selectTop",".inpSelect ul",".inpSelect ul li a");
});
/*******  input里面有值，并且当input获取焦点的时候，默认值消失  ***/
$(function(){
	$('.inputTextJs').each(function(){
		var $inputTextVal=$(this).val();
		$('.inputTextJs').css({'color':'#999'});
		$(this).focus(function(){
			if($(this).val()== $inputTextVal){
				$(this).val('');
				$(this).css({'color':'#333'});
			}else{
				$(this).css({'color':'#333'});
			}			
		});//focus
		
		$(this).blur(function(){
			if(!$(this).val()){
				$(this).val($inputTextVal);
				$(this).css({'color':'#999'});
			}else if($(this).val() == $inputTextVal){
				$(this).css({'color':'#999'});
			}
		});//blur
	});
});//over

/*二级导航菜单的显示隐藏*/
$(function(){
	$('.nav-li li').each(function(){
		$(this).mouseover(function(){
			$(this).addClass('li-click');
		});	
		$(this).mouseout(function(){
			$(this).removeClass('li-click');
		});			
	});
	$navUl3=$('.nav-li-2Li');
	$navUl3.each(function(index){
		$this=$(this);
		$this.hover(function(){
			$this.find('ul').show();
		},function(){
			$this.find('ul').hide();
		});
	});
})//over
/*tab切换*/
$.fn.extend({
	//tabsNav 包裹切换手柄ul类名
	//tabsPanel 切换内容标签
	//current 选中当前标签类名
	//num 初始状态显示第几个标签内容
	//eventsth 切换表现 默认为click
	'tabs':function(tabsNav,tabsPanel,current,num,eventsth){
		var $tabsNavList = $(tabsNav).find('a'), $panel = $(tabsPanel); 
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
/* table 切 成2 个 */
$(function(){
	var $tableNum1 = $('<table class="dataTable tableData1" width="100%"></table>'),
		$theadNum1 = $('<thead></thead>'),
		$tbodyNum1 = $('<tbody></tbody>'),
		$theadTR1 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum2 = $('<table class="dataTable tableData2" width="100%"></table>'),
		$theadNum2 = $('<thead></thead>'),
		$tbodyNum2 = $('<tbody></tbody>'),
		$theadTR2 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR1.append($('.tableData-js>thead').find('th:lt(6)'));
		$theadNum1.append($theadTR1);
		$tableNum1.append($theadNum1);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col1-js').append($tableNum1);// 将新表格tableNum1追加到col1中
		
		$theadTR2.append($('.tableData-js>thead').find('th'));
		$theadNum2.append($theadTR2);
		$tableNum2.append($theadNum2);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col2-inner-js').append($tableNum2);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR1 = $('<tr></tr>'), $tbodyTR2 = $('<tr></tr>');
		$tbodyTR1.append($this.find('td:lt(6)'));
		$tbodyTR2.append($this.find('td'));
		$tableNum1.append($tbodyTR1);
		$tableNum2.append($tbodyTR2);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum3 = $('<table class="dataTable tableData3" width="100%"></table>'),
		$theadNum3 = $('<thead></thead>'),
		$tbodyNum3 = $('<tbody></tbody>'),
		$theadTR3 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum4 = $('<table class="dataTable tableData4" width="100%"></table>'),
		$theadNum4 = $('<thead></thead>'),
		$tbodyNum4 = $('<tbody></tbody>'),
		$theadTR4 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR3.append($('.tableData-js3>thead').find('th:lt(7)'));
		$theadNum3.append($theadTR3);
		$tableNum3.append($theadNum3);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col3-js').append($tableNum3);// 将新表格tableNum1追加到col1中
		
		$theadTR4.append($('.tableData-js3>thead').find('th'));
		$theadNum4.append($theadTR4);
		$tableNum4.append($theadNum4);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col4-inner-js').append($tableNum4);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js3>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR3 = $('<tr></tr>'), $tbodyTR4 = $('<tr></tr>');
		$tbodyTR3.append($this.find('td:lt(7)'));
		$tbodyTR4.append($this.find('td'));
		$tableNum3.append($tbodyTR3);
		$tableNum4.append($tbodyTR4);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js3').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum5 = $('<table class="dataTable tableData5" width="100%"></table>'),
		$theadNum5 = $('<thead></thead>'),
		$tbodyNum5 = $('<tbody></tbody>'),
		$theadTR5 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum6 = $('<table class="dataTable tableData6" width="100%"></table>'),
		$theadNum6 = $('<thead></thead>'),
		$tbodyNum6 = $('<tbody></tbody>'),
		$theadTR6 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR5.append($('.tableData-js5>thead').find('th:lt(7)'));
		$theadNum5.append($theadTR5);
		$tableNum5.append($theadNum5);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col5-js').append($tableNum5);// 将新表格tableNum1追加到col1中
		
		$theadTR6.append($('.tableData-js5>thead').find('th'));
		$theadNum6.append($theadTR6);
		$tableNum6.append($theadNum6);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col6-inner-js').append($tableNum6);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js5>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR5 = $('<tr></tr>'), $tbodyTR6 = $('<tr></tr>');
		$tbodyTR5.append($this.find('td:lt(7)'));
		$tbodyTR6.append($this.find('td'));
		$tableNum5.append($tbodyTR5);
		$tableNum6.append($tbodyTR6);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js5').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum101 = $('<table class="dataTable tableData101" width="100%"></table>'),
		$theadNum101 = $('<thead></thead>'),
		$tbodyNum101 = $('<tbody></tbody>'),
		$theadTR101 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum102 = $('<table class="dataTable tableData102" width="100%"></table>'),
		$theadNum102 = $('<thead></thead>'),
		$tbodyNum102 = $('<tbody></tbody>'),
		$theadTR102 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR101.append($('.tableData-js101>thead').find('th:lt(8)'));
		$theadNum101.append($theadTR101);
		$tableNum101.append($theadNum101);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col101-js').append($tableNum101);// 将新表格tableNum1追加到col1中
		
		$theadTR102.append($('.tableData-js101>thead').find('th'));
		$theadNum102.append($theadTR102);
		$tableNum102.append($theadNum102);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col102-inner-js').append($tableNum102);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js101>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR101 = $('<tr></tr>'), $tbodyTR102 = $('<tr></tr>');
		$tbodyTR101.append($this.find('td:lt(8)'));
		$tbodyTR102.append($this.find('td'));
		$tableNum101.append($tbodyTR101);
		$tableNum102.append($tbodyTR102);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js101').remove();
	});
});//over

/* td 隔行变色 
$(function(){
	$('.dataTable').each(function(){
		$(this).find('tr:even').has('td').css('background-color','#f7f7f7');
		$(this).find('tr:odd').has('td').css('background-color','#fff');
	});//each
});//over
/* td 隔行变色 没有th 
$(function(){
	$('#rulesStandard').each(function(){
		$(this).find('tr:odd').has('td').css('background-color','#f7f7f7');
		$(this).find('tr:even').has('td').css('background-color','#fff');
	});//each
});//over*/
/* 对应切换 id切换  */
function tabCorrectId(crrectA,correctDiv,crrectLast){
	/*点击对应的div显示，其它隐藏*/
	$(crrectA).click(function(){
		$(correctDiv).show();
		$(correctDiv).siblings().hide();
	});	
	/*点击对应的div隐藏，其它隐藏*/
	$(crrectLast).click(function(){
		$(correctDiv).hide();
		$(correctDiv).siblings().hide();
	});	
};
/* 对应切换-值- 切换  */
function tabCorrectVal(crrectId,crrectVal,correctDiv,crrectLast){
	/*一个val值对应一个div，其它隐藏*/
	$(crrectId).click(function(){
		if($(crrectId).next('input').val() == crrectVal){
			$(correctDiv).show();
			$(correctDiv).siblings().hide();
		}else if($(crrectId).next('input').val() == crrectLast){
			$(correctDiv).hide();
			$(correctDiv).siblings().hide();
		};
	});//click	
};
/* select 点击的时候 td的级别调到最高  
function tdZindexHight(selectDiv,zIndexClass,vTableTDPr){
	$(selectDiv).each(function(){
		$(this).parents('table').find('td').removeClass(zIndexClass);
		$(this).toggle(function(){
			$(this).parents('td').addClass(zIndexClass);
			$(this).parents('table').addClass(vTableTDPr);
		},function(){
				$(this).parents('td').removeClass(zIndexClass);
				$(this).parents('table').removeClass(vTableTDPr);
			});					

	});//each
	
	$(document).click(function(){
        	$(selectDiv).parents('td').removeClass(zIndexClass);
        	$(selectDiv).parents('table').removeClass(vTableTDPr);
     }); //click
};//function

$(function(){
	tdZindexHight('.inpSelect','tdRative','tableTDPr');
});
新增会员类型 -编辑  begin*/
$(function(){		
	$('#editMembers .ico-del').each(function(){
		$(this).click(function(){
			$(this).parents('tr').remove();
			$('.dataTable').each(function(){
				var $this = $(this);
				$this.find('tr:even').has('td').css('background-color','#f7f7f7');
				$this.find('tr:odd').has('td').css('background-color','#fff');
			});
			return false;
		});
		
	});
	$('#editMembers .edit-js').each(function(index){
		$('#editMembers .input-text').hide();
		$(this).toggle(function(){			
			$(this).addClass('ico-save');
			
			var $nameTypeVal=$(this).parent('td').prev('td').children('span').text();
				$(this).parent('td').prev().children('input').val($nameTypeVal);
				$(this).parent('td').prev().children('span').hide();
				$(this).parent('td').prev().children('input').show();	
				return false;					
		},function(){
			$(this).removeClass('ico-save');
			
			var $nameTypeVal=$(this).parents('td').prev('td').children('span').text();
				$(this).parent('td').prev().children('input').val($nameTypeVal);
				$(this).parent('td').prev().children('span').show();
				$(this).parent('td').prev().children('input').hide();	
				return false;	
		});
	});
});
/*新增会员类型 -编辑  over*/
/*下拉菜单滚动条 begin*/
$(function(){
	$selectUl=$('.defineSelect ul');
	
	$selectUl.each(function(index){
		var $this = $(this), selectUlH = $this.height();
		if(selectUlH>200){
			$this.addClass('secetScl');
		}else{
			$this.removeClass('secetScl');
		};	
	});	
});
/*下拉菜单滚动条 over*/
/*新增会员信息  增加角色 弹出层  begin*/
$(function(){
	$('#addRoleChosCL-js li a').each(function(){
		$(this).click(function(){
			$('#addRoleChosCL-js li a').removeClass('clickNow');
			$(this).addClass('clickNow');
			
		});//click
	});
});
/*新增会员信息  增加角色 弹出层  over*/
/*争议上诉 弹出层 添加图片*/
function controversialAaddImg(){
	//alert($('#ejectAddPicConUl li').size())
	if($('#ejectAddPicCon li').size() > 3){
		$('#ejectAddPicCon').css({'overflow-y':'scroll','height':'100px','width':'346px','position':'relative'});
	}else{
		$('#ejectAddPicCon').css({'overflow-y':'hidden','height':'auto','width':'324px'});
	}
	$('#ejectAddPicCon .del').each(function(){
		$(this).click(function(){
			$(this).parents('li').remove();
		});
	});
};
/*仲裁内部仲裁-下来菜单控制*/
$(function(){
	$('#transferStatusIng').attr('disabled',true);
	$('#transferStatus').change(function(){
		if($(this).val()==1){
			$('#transferStatusIng').attr('disabled',false);
		}else{
			$('#transferStatusIng').attr('disabled',true)
		}//if-else
	})//change
});
/*仲裁内部仲裁-查看进度*/
function MethodStructureComment(){
	$('.structureComment dl:odd').css({'background-color':'#f9f9f9'});
	if($('.structureComment').height() >= 400){
		$('.structureComment').css({'height':'400px','overflow-y':'scroll'});
	}else{
		$('.structureComment').css({'height':'auto','overflow-y':'hidden'});
	};//if-else
};//over
/*财务管理-当会员状态为禁用时，整行灰色 文字*/
$(function(){
	$(".stateMemberJs:contains('禁用')").css('color','#999').siblings('td').css('color','#999');
});
/*财务管理-规则标准滚动*/
$(function(){
	if($('#rulesStandard').height() >= 190){
		$('#rulesStandard').css({'height':'190px','overflow-y':'scroll','overflow-x':'hidden'});
	}
});
/*系统管理-系统公告 start*/
$(function(){
	$(this).click(function(){
		if($("#fNotice").is(':checked')){
			$("#noticeTitle").show();		
			$("#closeBtn").show();
			$("#ejectNum").show();
		}else{
			$("#noticeTitle").hide();		
			$("#closeBtn").hide();
			$("#ejectNum").hide();
		};		
	});
});
/*
$(function(){
	$('.inner').find('td').hover(function(){
		$(this).parent('tr').css({'background-color':'#f2f2f2'})
	},function(){
		$(this).parent('tr').css({'background-color':'#fff'})
	});
})


$(function(){
	$('.tableData5').find('tr').has('td').each(function(){
		var x=$(this).index();
		$(this).hover(function(){
			$(this).css({'background-color':'#f2f2f2'});
			$('.tableData6').find('tr').has('td').eq(x).css({'background-color':'#f2f2f2'});
		},function(){
			var index=$(this).index();
			$(this).css({'background-color':'#fff'});
			$('.tableData6').find('tr').has('td').eq(x).css({'background-color':'#fff'});
		})
	})
})
*/
function tdhover(tb1,tb2){
	
	$(tb1).find('tr').has('td').each(function(){
		
		var x=$(this).index();
		//alert(x)
		$(this).hover(function(){
			$(this).css({'background-color':'#f2f2f2'});
			$(tb2).find('tr').has('td').eq(x).css({'background-color':'#f2f2f2'});
		},function(){
			var index=$(this).index();
			$(this).css({'background-color':'#fff'});
			$(tb2).find('tr').has('td').eq(x).css({'background-color':'#fff'});
		})
	})
}
$(function(){
	tdhover('.tableData1','.tableData2');
	tdhover('.tableData3','.tableData4');
	tdhover('.tableData5','.tableData6');
	tdhover('.tableData7','.tableData8');
	tdhover('.tableData9','.tableData10');
	tdhover('.tableData11','.tableData12');
	tdhover('.tableData13','.tableData14');
	tdhover('.tableData15','.tableData16');
	
	tdhover('.tableData2','.tableData1');
	tdhover('.tableData4','.tableData3');
	tdhover('.tableData6','.tableData5');
	tdhover('.tableData8','.tableData7');
	tdhover('.tableData10','.tableData9');
	tdhover('.tableData12','.tableData11');
	tdhover('.tableData14','.tableData13');
	tdhover('.tableData16','.tableData15');
})

$(function(){
	$('.dataTable,.dataTable-new').find('td').hover(function(){
		$(this).parent('tr').css({'background-color':'#f2f2f2'})
	},function(){
		$(this).parent('tr').css({'background-color':'#fff'})
	});
})
/******页面滚动的时候 固定选项卡 ******/
function tabDivScroll(){
    $(window).scroll(function(){
        var $scrollTop = $(window).scrollTop();
       // alert($scrollTop)
        //把tab固定在顶部
        if ($scrollTop != 0) {
            $('#top_showdataandmanage').addClass('nav-p');
        }else{
            $('#top_showdataandmanage').removeClass('nav-p');
        };
    }); //scroll
};
// 返回顶部
function goTop(){
	var goTop = $('#goTop');
	goTop.click(function(){
		$('html,body').animate({'scrollTop':0},'slow');
		goTop.hide();
		return false;
	});
	$(window).scroll(function(){
		if($(window).scrollTop() == 0){
			goTop.hide();
		}else{
			goTop.show();
		};
	});
};
$(function(){
	tabDivScroll();
	goTop();
})
$(function(){
	$('.code-js').each(function(){		
		$(this).hover(function(){
			$(this).css({'position':'relative'}).children('.code-span-js').addClass('river-dlta-h').next('.sequ').show();			
		},function(){
			$(this).css({'position':''}).children('.code-span-js').removeClass('river-dlta-h').next('.sequ').hide();	
		})
	})
})








