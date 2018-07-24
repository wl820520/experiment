/**
 * @author zhujunyan
 */
/******************************* 追加table begin **************************************/
$(function(){
   var $addTableA=$('.addTable');
   $addTableA.each(function(){
   		$(this).click(function(){   			
   			$(this).parents('#addTableWrap').append($(this).parents('.tabComplex').clone(true));
   			$(this).hide();
   		});//click
   });//each
});
/******************************* 追加table  over **************************************/
/******************************* 仲裁详情 页面的滚动 begin **************************************/
$(function(){
	var $rollCodeH=$('#rollCode .tabComplex').height();
	if($rollCodeH > 150){
		$('#rollCode').css({'height':'150px','overflow-y':'scroll'});
	}else{
		$('#rollCode').css({'height':'auto','overflow':'hidden'});
	};
});
/******************************* 仲裁详情页面的滚动 over **************************************/
/*******************************  select 2   **************************************/
$(function(){
    var $defineSelect=$(".inpSelect"),$selectTop=$(".inpSelect .selectTop"),$option=$(".inpSelect ul"),$subOption=$(".inpSelect ul li a");
        $defineSelect.each(function(){
        		$(this).click(function(e){
        			$(this).children('.inpSelect ul').toggle();
        			e.stopPropagation();
        		});
        });      
        $subOption.each(function(){
            var $this=$(this);
                $this.click(function(){
                	$this.parents('.inpSelect').children('.selectTop').text($this.text());
                    $this.parents('.inpSelect').children('input').val($this.attr('rel'));
                    $this.parents('.inpSelect').children('input').trigger("change");
                });
        });
        $(document).click(function(){
        	$option.hide();
        }); 
});
function $selectF(select,selectTop,selectUl,selectA){
    var $defineSelect=$(select),$selectTop=$(selectTop),$option=$(selectUl),$subOption=$(selectA);
        $defineSelect.each(function(){
        		$(this).click(function(e){
        			
        			$(this).parents('div').find(select).removeClass('z-index1');
        			
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
	$selectF(".defineSelect",".defineSelect .selectTop",".defineSelect ul",".defineSelect ul li a");
});
/* 新版input 点击 begin*/
$(function(){
	$('.inpInput').focus(function(){
		$(this).addClass('inpClick')		
	})//focus
	$('.inpInput').blur(function(){
		$(this).removeClass('inpClick')		
	})//focus	
})//over
/*新版input 点击 over*/
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
/*下拉菜单滚动条  begin*/
$(function(){
	$addRoleChosLUl=$('.addRoleChosCL ul ,.addRoleChosCR ul');
	
	$addRoleChosLUl.each(function(index){
		var $this = $(this), $addRoleChosLUlH = $this.height();
		if($addRoleChosLUlH>150){
			$this.addClass('addRoleChosLUlH');
		}else{
			$this.removeClass('addRoleChosLUlH');
		};	
	});	
	//黑名单设置 高度 超过255后 出现滚动条
	$twoSetListJs=$('.twoSetListJs ul');
	$twoSetListJsLi=$('.twoSetListJs ul li');
	
	$twoSetListJs.each(function(index){
		var $this=$(this);
		var $twoSetListJsH=$this.height();
		if($twoSetListJsH > 255){
			$(this).css({'overflow-y':'scroll','height':'255px'})
		}
	});//over
	$twoSetListJsLi.click(function(){
		$(this).addClass('clickNow').siblings().removeClass('clickNow');
		return false;
	})
});
/*下拉菜单滚动条  over*/
/*用户管理 弹出层 高度控制 超过210出现滚动条*/
$(function(){
		var $ul1=$('#cust-wrap-js1 .cust-ul1');
		var $ulH1=$('#cust-wrap-js1 .cust-ul1').height();
		var $ul2=$('#cust-wrap-js2 .cust-ul1');
		var $ulH2=$('#cust-wrap-js2 .cust-ul1').height();
		var $ul3=$('#cust-wrap-js3 .cust-ul1');
		var $ulH3=$('#cust-wrap-js3 .cust-ul1').height();
		if($ulH1>210){
			$ul1.css({'height':'210px','overflow-y':'scroll'});
		}else{
			$ul1.css({'height':'auto','overflow':'hidden'});
		}//1
		if($ulH2>210){
			$ul2.css({'height':'210px','overflow-y':'scroll'});
		}else{
			$ul2.css({'height':'auto','overflow':'hidden'});
		}//2
		if($ulH3>210){
			$ul3.css({'height':'210px','overflow-y':'scroll'});
		}else{
			$ul3.css({'height':'auto','overflow':'hidden'});
		}//3

})//over
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
/*vin-seach vin查询 input 中的label 判断*/
$(function(){
	$('.juese-manage-search').prepend('<label class="search-lab" for="inp">请输入角色名称...</label>');
	$('.vin-search').prepend('<label class="search-lab" for="inp">VIN码/车牌号/订单号/拍品编号</label>');
	$('.vin-search-dis').prepend('<label class="search-lab" for="inp">请输入客户名称...</label>');
	$('.gen-excel').prepend('<label class="search-lab" for="inp">请输入销售人员姓名或会员名..</label>');
	$('.codeid').prepend('<label class="codeid-lb" for="codeid-in">请输入订单号码...</label>');
	$('.sys-manage-search').prepend('<label class="search-lab" for="inp">请输入用户姓名..</label>');
	$('.inputMemName').prepend('<label class="inputMemName-lab" for="inp">请输入会员名称..</label>');

	$('.vin-search-inp,.search-lab').click(function(){
		$('.search-lab').css({'display':'none'});
	})//click
	$('.vin-search-inp').blur(function(){	
		var $val=$(this).val();
		if(!$val){
			$(this).siblings('.search-lab').show();
		}//if
	})//blur
	$('.codeid-lb,.codeid-in').click(function(){
		$('.codeid-lb').css({'display':'none'});
	})//click
	$('.codeid-in').blur(function(){	
		var $val=$(this).val();
		if(!$val){
			$(this).siblings('.codeid-lb').show();
		}//if
	})//blur
	$('.inputMemName-lab,.inputMemName-inp').click(function(){
		$('.inputMemName-lab').css({'display':'none'});
	})//click
	$('.inputMemName-inp').blur(function(){	
		var $val=$(this).val();
		if(!$val){
			$(this).siblings('.inputMemName-lab').show();
		}//if
	})//blur

	
})//over vin-search-dis

/* 一个table  分裂成两个 */
$(function(){
	var $tableNum1 = $('<table class="table-num1"></table>'),
		$theadNum1 = $('<thead></thead>'),
		$tbodyNum1 = $('<tbody></tbody>'),
		$theadTR1 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum2 = $('<table class="table-num2"></table>'),
		$theadNum2 = $('<thead></thead>'),
		$tbodyNum2 = $('<tbody></tbody>'),
		$theadTR2 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR1.append($('.dataTable>thead').find('th:lt(9)'));
		$theadNum1.append($theadTR1);
		$tableNum1.append($theadNum1);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col1').append($tableNum1);// 将新表格tableNum1追加到col1中
		
		$theadTR2.append($('.dataTable>thead').find('th'));
		$theadNum2.append($theadTR2);
		$tableNum2.append($theadNum2);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col2-inner').append($tableNum2);// 将tableNum2追加到col2-inner中
		
		$('.dataTable>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR1 = $('<tr></tr>'), $tbodyTR2 = $('<tr></tr>');
		$tbodyTR1.append($this.find('td:lt(9)'));
		$tbodyTR2.append($this.find('td'));
		$tableNum1.append($tbodyTR1);
		$tableNum2.append($tbodyTR2);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num1>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num1>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num2>tbody').find('tr:odd').addClass('b_fb');
		$('.col1, .col2').find('th').addClass('theadBg c_000');

	});
	//2 begin
	var $tableNum3 = $('<table class="table-num3"></table>'),
		$theadNum3 = $('<thead></thead>'),
		$tbodyNum3 = $('<tbody></tbody>'),
		$theadTR3 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum4 = $('<table class="table-num4"></table>'),
		$theadNum4 = $('<thead></thead>'),
		$tbodyNum4 = $('<tbody></tbody>'),
		$theadTR4 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR3.append($('.dataTable2>thead').find('th:lt(9)'));
		$theadNum3.append($theadTR3);
		$tableNum3.append($theadNum3);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col3').append($tableNum3);// 将新表格tableNum1追加到col1中
		
		$theadTR4.append($('.dataTable2>thead').find('th'));
		$theadNum4.append($theadTR4);
		$tableNum4.append($theadNum4);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col4-inner').append($tableNum4);// 将tableNum2追加到col2-inner中
		
		$('.dataTable2>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR3 = $('<tr></tr>'), $tbodyTR4 = $('<tr></tr>');
		$tbodyTR3.append($this.find('td:lt(9)'));
		$tbodyTR4.append($this.find('td'));
		$tableNum3.append($tbodyTR3);
		$tableNum4.append($tbodyTR4);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num3>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num3>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num4>tbody').find('tr:odd').addClass('b_fb');
		$('.col3, .col4').find('th').addClass('theadBg c_000');

	});	
	//2 over
	
	//3 begin
	var $tableNum5 = $('<table class="table-num5"></table>'),
		$theadNum5 = $('<thead></thead>'),
		$tbodyNum5 = $('<tbody></tbody>'),
		$theadTR5 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum6 = $('<table class="table-num6"></table>'),
		$theadNum6 = $('<thead></thead>'),
		$tbodyNum6 = $('<tbody></tbody>'),
		$theadTR6 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR5.append($('.dataTable3>thead').find('th:lt(9)'));
		$theadNum5.append($theadTR5);
		$tableNum5.append($theadNum5);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col5').append($tableNum5);// 将新表格tableNum1追加到col1中
		
		$theadTR6.append($('.dataTable3>thead').find('th'));
		$theadNum6.append($theadTR6);
		$tableNum6.append($theadNum6);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col6-inner').append($tableNum6);// 将tableNum2追加到col2-inner中
		
		$('.dataTable3>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR5 = $('<tr></tr>'), $tbodyTR6 = $('<tr></tr>');
		$tbodyTR5.append($this.find('td:lt(9)'));
		$tbodyTR6.append($this.find('td'));
		$tableNum5.append($tbodyTR5);
		$tableNum6.append($tbodyTR6);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num5>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num5>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num6>tbody').find('tr:odd').addClass('b_fb');
		$('.col5, .col6').find('th').addClass('theadBg c_000');

	});	
	//3 over
	
	//4 begin
	var $tableNum7 = $('<table class="table-num7"></table>'),
		$theadNum7 = $('<thead></thead>'),
		$tbodyNum7 = $('<tbody></tbody>'),
		$theadTR7 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum8 = $('<table class="table-num8"></table>'),
		$theadNum8 = $('<thead></thead>'),
		$tbodyNum8 = $('<tbody></tbody>'),
		$theadTR8 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR7.append($('.dataTable4>thead').find('th:lt(9)'));
		$theadNum7.append($theadTR7);
		$tableNum7.append($theadNum7);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col7').append($tableNum7);// 将新表格tableNum1追加到col1中
		
		$theadTR8.append($('.dataTable4>thead').find('th'));
		$theadNum8.append($theadTR8);
		$tableNum8.append($theadNum8);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col8-inner').append($tableNum8);// 将tableNum2追加到col2-inner中
		
		$('.dataTable4>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR7 = $('<tr></tr>'), $tbodyTR8 = $('<tr></tr>');
		$tbodyTR7.append($this.find('td:lt(9)'));
		$tbodyTR8.append($this.find('td'));
		$tableNum7.append($tbodyTR7);
		$tableNum8.append($tbodyTR8);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num7>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num7>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num8>tbody').find('tr:odd').addClass('b_fb');
		$('.col7, .col8').find('th').addClass('theadBg c_000');

	});	
	//4 over	
	
	//5 begin
	var $tableNum9 = $('<table class="table-num9"></table>'),
		$theadNum9 = $('<thead></thead>'),
		$tbodyNum9 = $('<tbody></tbody>'),
		$theadTR9 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum10 = $('<table class="table-num10"></table>'),
		$theadNum10 = $('<thead></thead>'),
		$tbodyNum10 = $('<tbody></tbody>'),
		$theadTR10 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR9.append($('.dataTable5>thead').find('th:lt(9)'));
		$theadNum9.append($theadTR9);
		$tableNum9.append($theadNum9);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col9').append($tableNum9);// 将新表格tableNum1追加到col1中
		
		$theadTR10.append($('.dataTable5>thead').find('th'));
		$theadNum10.append($theadTR10);
		$tableNum10.append($theadNum10);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col10-inner').append($tableNum10);// 将tableNum2追加到col2-inner中
		
		$('.dataTable5>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR9 = $('<tr></tr>'), $tbodyTR10 = $('<tr></tr>');
		$tbodyTR9.append($this.find('td:lt(9)'));
		$tbodyTR10.append($this.find('td'));
		$tableNum9.append($tbodyTR9);
		$tableNum10.append($tbodyTR10);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num9>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num9>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num10>tbody').find('tr:odd').addClass('b_fb');
		$('.col9, .col10').find('th').addClass('theadBg c_000');

	});	
	//5 over		
	
	//6 begin
	var $tableNum11 = $('<table class="table-num11"></table>'),
		$theadNum11 = $('<thead></thead>'),
		$tbodyNum11 = $('<tbody></tbody>'),
		$theadTR11 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum12 = $('<table class="table-num12"></table>'),
		$theadNum12 = $('<thead></thead>'),
		$tbodyNum12 = $('<tbody></tbody>'),
		$theadTR12 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR11.append($('.dataTable6>thead').find('th:lt(9)'));
		$theadNum11.append($theadTR11);
		$tableNum11.append($theadNum11);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col11').append($tableNum11);// 将新表格tableNum1追加到col1中
		
		$theadTR12.append($('.dataTable6>thead').find('th'));
		$theadNum12.append($theadTR12);
		$tableNum12.append($theadNum12);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col12-inner').append($tableNum12);// 将tableNum2追加到col2-inner中
		
		$('.dataTable6>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR11 = $('<tr></tr>'), $tbodyTR12 = $('<tr></tr>');
		$tbodyTR11.append($this.find('td:lt(9)'));
		$tbodyTR12.append($this.find('td'));
		$tableNum11.append($tbodyTR11);
		$tableNum12.append($tbodyTR12);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num11>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num11>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num12>tbody').find('tr:odd').addClass('b_fb');
		$('.col11, .col12').find('th').addClass('theadBg c_000');

	});	
	//6 over
	
	//经销商 7  begin
	var $tableNum13 = $('<table class="table-num13"></table>'),
		$theadNum13 = $('<thead></thead>'),
		$tbodyNum13 = $('<tbody></tbody>'),
		$theadTR13 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum14 = $('<table class="table-num14"></table>'),
		$theadNum14 = $('<thead></thead>'),
		$tbodyNum14 = $('<tbody></tbody>'),
		$theadTR14 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR13.append($('.dataTable7>thead').find('th:lt(9)'));
		$theadNum13.append($theadTR13);
		$tableNum13.append($theadNum13);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col13').append($tableNum13);// 将新表格tableNum1追加到col1中
		
		$theadTR14.append($('.dataTable7>thead').find('th'));
		$theadNum14.append($theadTR14);
		$tableNum14.append($theadNum14);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col14-inner').append($tableNum14);// 将tableNum2追加到col2-inner中
		
		$('.dataTable7>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR13 = $('<tr></tr>'), $tbodyTR14 = $('<tr></tr>');
		$tbodyTR13.append($this.find('td:lt(11)'));
		$tbodyTR14.append($this.find('td'));
		$tableNum13.append($tbodyTR13);
		$tableNum14.append($tbodyTR14);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num13>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num13>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num14>tbody').find('tr:odd').addClass('b_fb');
		$('.col13, .col14').find('th').addClass('theadBg-c c_000');

	});	
	//经销商  7 over
	
	//合理性登记8  begin
	var $tableNum15 = $('<table class="table-num15"></table>'),
		$theadNum15 = $('<thead></thead>'),
		$tbodyNum15 = $('<tbody></tbody>'),
		$theadTR15 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum16 = $('<table class="table-num16"></table>'),
		$theadNum16 = $('<thead></thead>'),
		$tbodyNum16 = $('<tbody></tbody>'),
		$theadTR16 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR15.append($('.dataTable8>thead').find('th:lt(7)'));
		$theadNum15.append($theadTR15);
		$tableNum15.append($theadNum15);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col15').append($tableNum15);// 将新表格tableNum1追加到col1中
		
		$theadTR16.append($('.dataTable8>thead').find('th'));
		$theadNum16.append($theadTR16);
		$tableNum16.append($theadNum16);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col16-inner').append($tableNum16);// 将tableNum2追加到col2-inner中
		
		$('.dataTable8>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR15 = $('<tr></tr>'), $tbodyTR16 = $('<tr></tr>');
		$tbodyTR15.append($this.find('td:lt(7)'));
		$tbodyTR16.append($this.find('td'));
		$tableNum15.append($tbodyTR15);
		$tableNum16.append($tbodyTR16);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num15>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num15>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num16>tbody').find('tr:odd').addClass('b_fb');
		$('.col15, .col16').find('th').addClass('theadBg-c c_000');

	});	
	//合理性登记8 over		
		
	//合理性登记9  begin
	var $tableNum17 = $('<table class="table-num17"></table>'),
		$theadNum17 = $('<thead></thead>'),
		$tbodyNum17 = $('<tbody></tbody>'),
		$theadTR17 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum18 = $('<table class="table-num18"></table>'),
		$theadNum18 = $('<thead></thead>'),
		$tbodyNum18 = $('<tbody></tbody>'),
		$theadTR18 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR17.append($('.dataTable9>thead').find('th:lt(7)'));
		$theadNum17.append($theadTR17);
		$tableNum17.append($theadNum17);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col17').append($tableNum17);// 将新表格tableNum1追加到col1中
		
		$theadTR18.append($('.dataTable9>thead').find('th'));
		$theadNum18.append($theadTR18);
		$tableNum18.append($theadNum18);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col18-inner').append($tableNum18);// 将tableNum2追加到col2-inner中
		
		$('.dataTable9>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR17 = $('<tr></tr>'), $tbodyTR18 = $('<tr></tr>');
		$tbodyTR17.append($this.find('td:lt(7)'));
		$tbodyTR18.append($this.find('td'));
		$tableNum17.append($tbodyTR17);
		$tableNum18.append($tbodyTR18);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num17>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num17>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num18>tbody').find('tr:odd').addClass('b_fb');
		$('.col17, .col18').find('th').addClass('theadBg-c c_000');

	});	
	//合理性登记9 over
	
	//合理性登记10  begin
	var $tableNum19 = $('<table class="table-num19"></table>'),
		$theadNum19 = $('<thead></thead>'),
		$tbodyNum19 = $('<tbody></tbody>'),
		$theadTR19 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum20 = $('<table class="table-num20"></table>'),
		$theadNum20 = $('<thead></thead>'),
		$tbodyNum20 = $('<tbody></tbody>'),
		$theadTR20 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR19.append($('.dataTable10>thead').find('th:lt(7)'));
		$theadNum19.append($theadTR19);
		$tableNum19.append($theadNum19);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col19').append($tableNum19);// 将新表格tableNum1追加到col1中
		
		$theadTR20.append($('.dataTable10>thead').find('th'));
		$theadNum20.append($theadTR20);
		$tableNum20.append($theadNum20);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col20-inner').append($tableNum20);// 将tableNum2追加到col2-inner中
		
		$('.dataTable10>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR19 = $('<tr></tr>'), $tbodyTR20 = $('<tr></tr>');
		$tbodyTR19.append($this.find('td:lt(7)'));
		$tbodyTR20.append($this.find('td'));
		$tableNum19.append($tbodyTR19);
		$tableNum20.append($tbodyTR20);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num19>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num19>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num20>tbody').find('tr:odd').addClass('b_fb');
		$('.col19, .col20').find('th').addClass('theadBg-c c_000');

	});	
	//合理性登记10 over		
	
	//合理性登记11  begin
	var $tableNum21 = $('<table class="table-num21"></table>'),
		$theadNum21 = $('<thead></thead>'),
		$tbodyNum21 = $('<tbody></tbody>'),
		$theadTR21 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum22 = $('<table class="table-num22"></table>'),
		$theadNum22 = $('<thead></thead>'),
		$tbodyNum22 = $('<tbody></tbody>'),
		$theadTR22 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR21.append($('.dataTable11>thead').find('th:lt(7)'));
		$theadNum21.append($theadTR21);
		$tableNum21.append($theadNum21);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col21').append($tableNum21);// 将新表格tableNum1追加到col1中
		
		$theadTR22.append($('.dataTable11>thead').find('th'));
		$theadNum22.append($theadTR22);
		$tableNum22.append($theadNum22);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col22-inner').append($tableNum22);// 将tableNum2追加到col2-inner中
		
		$('.dataTable11>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR21 = $('<tr></tr>'), $tbodyTR22 = $('<tr></tr>');
		$tbodyTR21.append($this.find('td:lt(7)'));
		$tbodyTR22.append($this.find('td'));
		$tableNum21.append($tbodyTR21);
		$tableNum22.append($tbodyTR22);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num21>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num21>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num22>tbody').find('tr:odd').addClass('b_fb');
		$('.col21, .col22').find('th').addClass('theadBg-c c_000');

	});	
	//合理性登记11 over	
	
	//合理性登记12  begin
	var $tableNum23 = $('<table class="table-num23"></table>'),
		$theadNum23 = $('<thead></thead>'),
		$tbodyNum23 = $('<tbody></tbody>'),
		$theadTR23 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum24 = $('<table class="table-num24"></table>'),
		$theadNum24 = $('<thead></thead>'),
		$tbodyNum24 = $('<tbody></tbody>'),
		$theadTR24 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR23.append($('.dataTable12>thead').find('th:lt(7)'));
		$theadNum23.append($theadTR23);
		$tableNum23.append($theadNum23);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col23').append($tableNum23);// 将新表格tableNum1追加到col1中
		
		$theadTR24.append($('.dataTable12>thead').find('th'));
		$theadNum24.append($theadTR24);
		$tableNum24.append($theadNum24);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col24-inner').append($tableNum24);// 将tableNum2追加到col2-inner中
		
		$('.dataTable12>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR23 = $('<tr></tr>'), $tbodyTR24 = $('<tr></tr>');
		$tbodyTR23.append($this.find('td:lt(7)'));
		$tbodyTR24.append($this.find('td'));
		$tableNum23.append($tbodyTR23);
		$tableNum24.append($tbodyTR24);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num23>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num23>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num24>tbody').find('tr:odd').addClass('b_fb');
		$('.col23, .col24').find('th').addClass('theadBg-c c_000');

	});	
	//合理性登记12 over	
	//会员管理  begin
	var $tableNum25 = $('<table class="table-num25"></table>'),
		$theadNum25 = $('<thead></thead>'),
		$tbodyNum25 = $('<tbody></tbody>'),
		$theadTR25 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum26 = $('<table class="table-num26"></table>'),
		$theadNum26 = $('<thead></thead>'),
		$tbodyNum26 = $('<tbody></tbody>'),
		$theadTR26 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR25.append($('.dataTable13>thead').find('th:lt(7)'));
		$theadNum25.append($theadTR25);
		$tableNum25.append($theadNum25);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col25').append($tableNum25);// 将新表格tableNum1追加到col1中
		
		$theadTR26.append($('.dataTable13>thead').find('th'));
		$theadNum26.append($theadTR26);
		$tableNum26.append($theadNum26);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col26-inner').append($tableNum26);// 将tableNum2追加到col2-inner中
		
		$('.dataTable13>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR25 = $('<tr></tr>'), $tbodyTR26 = $('<tr></tr>');
		$tbodyTR25.append($this.find('td:lt(7)'));
		$tbodyTR26.append($this.find('td'));
		$tableNum25.append($tbodyTR25);
		$tableNum26.append($tbodyTR26);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		//给表格的第一列增加宽度
		$('.table-num25>thead').find('th:first').addClass('carName');
		// 给新表格的偶数行增加样式
		$('.table-num25>tbody').find('tr:odd').addClass('b_fb');
		$('.table-num26>tbody').find('tr:odd').addClass('b_fb');
		$('.col25, .col26').find('th').addClass('theadBg-c c_000');

	});	
	//会员管理 over	
	
			
});
/* td的隔行换色*/
$(function(){
	$('.col1,.col2,.col3,.col4,.col5,.col6,.col7,.col8,.col9,.col10,.col11,.col12,.col13,.col14,.col15,.col16,.col17,.col18,.col19,.col20,.col21,.col22,.dataTable-tanchu,.col23,.col24,.col25,.col26,.dataTable0').each(function(){
		var $this = $(this);
		$this.find('tr:even').has('td').css('background-color','#f7f7f7');
		$this.find('tr:odd').has('td').css('background-color','#fff');
	});
});//over 

/******************************* 经销商页面 th 鼠标经过出现下拉选择   **************************************/
$(function(){
	$(".code-js").each(function(){
		$(this).mouseover(function(){
	    	$(this).children(".sequ").show();
	    	$(this).children('.code-span-js').addClass('river-dlta-h');    	
	  	});
		$(this).mouseout(function(){
	    	$(this).children(".sequ").hide();
	    	$(this).children('.code-span-js').removeClass('river-dlta-h');    	
	  	}); 		
	})	
})//over	
/******************************* 合理性登记  保留价设置合理性两种状态切换 begin   **************************************/
$(function(){
	$('.ratio-td').each(function(){
		$(this).children('.ratio-td-a').click(function(){
			$(this).hide();
			$(this).next().show();
		})
	});//each
})//over
$(function(){
	$('.a-td').each(function(){
		$(this).children('.a-td-a').click(function(){
			$(this).hide();
			$(this).next().show();
		})
	});//each
})//over

/*******************************  登陆页面 input 获得焦点和失去焦点 的状态 begin   **************************************/

$(function(){
	$('.input-text').focus(function(){
		$(this).addClass('input-focus')		
	})//focus
	$('.input-text').blur(function(){
		$(this).removeClass('input-focus')		
	})//focus	
})//over

/*******************************  select 1   *************************************
$(function(){
    var $defineSelect=$(".defineSelect"),$selectTop=$(".defineSelect .selectTop"),$option=$(".defineSelect ul"),$subOption=$(".defineSelect ul li a");
        $defineSelect.each(function(){
        		$(this).click(function(e){
        			$(this).children('.defineSelect ul').toggle();
        			e.stopPropagation();
        		});
        });      
        $subOption.each(function(){
            var $this=$(this);
                $this.click(function(){
                	$this.parents('.defineSelect').children('.selectTop').text($this.text());
                    $this.sublings('input').val($this.attr("rel"));
                });
        });
        $(document).click(function(){
        	$option.hide();
        })  
});
*/
/*******************************  选项卡切换   **************************************/
/*$(function() {
	var $liItem = $('.area-ul1 li a'), $content = $('.area-div1');
	var $liItem1 = $('.area-ul2 li a'), $content1 = $('.area-div2');
	$liItem.each(function(index){
		$this = $(this);		
		$this.click(function(){
			$liItem.removeClass('click-css');
			$(this).addClass('click-css');
			$content.hide();
			$content.eq(index).show();
			return false;
			});
		});
		
	$liItem1.each(function(index){
		$this = $(this);		
		$this.click(function(){
			$liItem1.removeClass('click-css');
			$(this).addClass('click-css');
			$content1.hide();
			$content1.eq(index).show();
			return false;
			});
		});		
		
});
*/

/*******************************  系统日志-输入名字 input   **************************************/
$(function(){
	$('.log-en-name').append('<label for="log-name" class="lab-en-name" >请输入用户名...</label>');
	
	$('.lab-en-name,.input-en-name').click(function(){
		$('.lab-en-name').css({'display':'none'});
	})//click
	$('.input-en-name').blur(function(){	
		var $val=$(this).val();
		if(!$val){
			$(this).siblings('.lab-en-name').show();
		}//if
	})//blur
})//over vin-search-dis


/* input 输入框提示  begin*/
function inputTip(Inp, ht){
		if(!$(Inp).val()){
			$(Inp).val(ht).css({'color':'#999'});	
		};
		
		$(Inp).focus(function(){
			if($(this).val() == ht){
				$(this).val('').css({'color':'#333'});
			}
		});
		$(Inp).blur(function(){
			if($(this).val() == ''){
				$(this).val(ht).css({'color':'#999'});
			}
		});
};

/* input 输入框提示 over */

/* input  切换  table*/
function subTableUpDown(subTableUDInp,subTableUDDiv){
		$(subTableUDInp).click(function(){
			if(($(this).attr('checked')) == 'checked'){
				$(subTableUDDiv).show();
				$(subTableUDDiv).siblings('.subTableDiv').hide();
			}else{
				alert(1)					
			}
		})//click
	};//over

/* radio 切换table*/
function subTableUpDownRadio(subTableUDInp,subTableUDDiv){
		$(subTableUDInp).click(function(){
			if(($(this).attr('checked')) == 'checked'){
				$(subTableUDDiv).show();
				$(subTableUDDiv).siblings('.subTableDiv').hide();
			}else{
				alert(1)					
			}
		})//click
};//over
/* selected 切换table*/
function subTableUpDowSelect(subTableUDSSelect,subTableUDSInp,subTableUDSDiv) {
	 $(subTableUDSSelect).click(function(){
	 	var $indexOption=$(subTableUDSInp).val();
		 var $optionAll=$(subTableUDSDiv).children('div');
		 		
		$optionAll.hide();
		$optionAll.eq($indexOption-1).show();
	 })					

};//over

/*新增会员类型 -编辑  begin*/
$(function(){	
	
	$('#editMembers .ico-del').each(function(){
		$(this).click(function(){
			$(this).parents('tr').remove();
			$('.dataTable0').each(function(){
				var $this = $(this);
				$this.find('tr:even').has('td').css('background-color','#f7f7f7');
				$this.find('tr:odd').has('td').css('background-color','#fff');
			});
		});
		
	});
	$('#editMembers .edit-js').each(function(index){
		$('#editMembers .input-text').hide();
		$(this).toggle(function(){			
			$(this).removeClass('ico-edit').addClass('ico-save');
			
			var $nameTypeVal=$(this).parent('td').prev('td').children('span').text();
				$(this).parent('td').prev().children('input').val($nameTypeVal);
				$(this).parent('td').prev().children('span').hide();
				$(this).parent('td').prev().children('input').show();	
				return false;					
		},function(){
			$(this).removeClass('ico-save').addClass('ico-edit');
			
			var $nameTypeVal=$(this).parents('td').prev('td').children('span').text();
				$(this).parent('td').prev().children('input').val($nameTypeVal);
				$(this).parent('td').prev().children('span').show();
				$(this).parent('td').prev().children('input').hide();	
				return false;	
		});
	});
});
/*新增会员类型 -编辑  over*/
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
$(function(){
	var $topPJsLi=$('#top-p-border-js-li');
	var $topPJs=$('.top-p-border-js');

	$topPJsLi.find('input').each(function(index){
		$(this).click(function(){
			$topPJs.hide();
			$topPJs.eq(index).show();
		})
	})
})






























