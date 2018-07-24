/**
 * @author zhujunyan
 */
/* table 切 成2 个 */
$(function(){
	var $tableNum7 = $('<table class="dataTable tableData7" width="100%"></table>'),
		$theadNum7 = $('<thead></thead>'),
		$tbodyNum7 = $('<tbody></tbody>'),
		$theadTR7 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum8 = $('<table class="dataTable tableData8" width="100%"></table>'),
		$theadNum8 = $('<thead></thead>'),
		$tbodyNum8 = $('<tbody></tbody>'),
		$theadTR8 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR7.append($('.tableData-js7>thead').find('th:lt(7)'));
		$theadNum7.append($theadTR7);
		$tableNum7.append($theadNum7);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col7-js').append($tableNum7);// 将新表格tableNum1追加到col1中
		
		$theadTR8.append($('.tableData-js7>thead').find('th'));
		$theadNum8.append($theadTR8);
		$tableNum8.append($theadNum8);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col8-inner-js').append($tableNum8);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js7>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR7 = $('<tr></tr>'), $tbodyTR8 = $('<tr></tr>');
		$tbodyTR7.append($this.find('td:lt(7)'));
		$tbodyTR8.append($this.find('td'));
		$tableNum7.append($tbodyTR7);
		$tableNum8.append($tbodyTR8);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js7').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum9 = $('<table class="dataTable tableData9" width="100%"></table>'),
		$theadNum9 = $('<thead></thead>'),
		$tbodyNum9 = $('<tbody></tbody>'),
		$theadTR9 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum10 = $('<table class="dataTable tableData10" width="100%"></table>'),
		$theadNum10 = $('<thead></thead>'),
		$tbodyNum10 = $('<tbody></tbody>'),
		$theadTR10 = $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR9.append($('.tableData-js9>thead').find('th:lt(7)'));
		$theadNum9.append($theadTR9);
		$tableNum9.append($theadNum9);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col9-js').append($tableNum9);// 将新表格tableNum1追加到col1中
		
		$theadTR10.append($('.tableData-js9>thead').find('th'));
		$theadNum10.append($theadTR10);
		$tableNum10.append($theadNum10);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col10-inner-js').append($tableNum10);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js9>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR9 = $('<tr></tr>'), $tbodyTR10 = $('<tr></tr>');
		$tbodyTR9.append($this.find('td:lt(7)'));
		$tbodyTR10.append($this.find('td'));
		$tableNum9.append($tbodyTR9);
		$tableNum10.append($tbodyTR10);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js9').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum11 = $('<table class="dataTable tableData11" width="100%"></table>'),
		$theadNum11 = $('<thead></thead>'),
		$tbodyNum11 = $('<tbody></tbody>'),
		$theadTR11 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum12 = $('<table class="dataTable tableData12" width="100%"></table>'),
		$theadNum12 = $('<thead></thead>'),
		$tbodyNum12 = $('<tbody></tbody>'),
		$theadTR12= $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR11.append($('.tableData-js11>thead').find('th:lt(7)'));
		$theadNum11.append($theadTR11);
		$tableNum11.append($theadNum11);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col11-js').append($tableNum11);// 将新表格tableNum1追加到col1中
		
		$theadTR12.append($('.tableData-js11>thead').find('th'));
		$theadNum12.append($theadTR12);
		$tableNum12.append($theadNum12);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col12-inner-js').append($tableNum12);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js11>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR11 = $('<tr></tr>'), $tbodyTR12 = $('<tr></tr>');
		$tbodyTR11.append($this.find('td:lt(7)'));
		$tbodyTR12.append($this.find('td'));
		$tableNum11.append($tbodyTR11);
		$tableNum12.append($tbodyTR12);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js11').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum13 = $('<table class="dataTable tableData13" width="100%"></table>'),
		$theadNum13 = $('<thead></thead>'),
		$tbodyNum13 = $('<tbody></tbody>'),
		$theadTR13 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum14 = $('<table class="dataTable tableData14" width="100%"></table>'),
		$theadNum14 = $('<thead></thead>'),
		$tbodyNum14 = $('<tbody></tbody>'),
		$theadTR14= $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR13.append($('.tableData-js13>thead').find('th:lt(7)'));
		$theadNum13.append($theadTR13);
		$tableNum13.append($theadNum13);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col13-js').append($tableNum13);// 将新表格tableNum1追加到col1中
		
		$theadTR14.append($('.tableData-js13>thead').find('th'));
		$theadNum14.append($theadTR14);
		$tableNum14.append($theadNum14);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col14-inner-js').append($tableNum14);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js13>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR13 = $('<tr></tr>'), $tbodyTR14 = $('<tr></tr>');
		$tbodyTR13.append($this.find('td:lt(7)'));
		$tbodyTR14.append($this.find('td'));
		$tableNum13.append($tbodyTR13);
		$tableNum14.append($tbodyTR14);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js13').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum15 = $('<table class="dataTable tableData15" width="100%"></table>'),
		$theadNum15 = $('<thead></thead>'),
		$tbodyNum15 = $('<tbody></tbody>'),
		$theadTR15 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum16 = $('<table class="dataTable tableData16" width="100%"></table>'),
		$theadNum16 = $('<thead></thead>'),
		$tbodyNum16 = $('<tbody></tbody>'),
		$theadTR16= $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR15.append($('.tableData-js15>thead').find('th:lt(7)'));
		$theadNum15.append($theadTR15);
		$tableNum15.append($theadNum15);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col15-js').append($tableNum15);// 将新表格tableNum1追加到col1中
		
		$theadTR16.append($('.tableData-js15>thead').find('th'));
		$theadNum16.append($theadTR16);
		$tableNum16.append($theadNum16);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col16-inner-js').append($tableNum16);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js15>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR15 = $('<tr></tr>'), $tbodyTR16 = $('<tr></tr>');
		$tbodyTR15.append($this.find('td:lt(7)'));
		$tbodyTR16.append($this.find('td'));
		$tableNum15.append($tbodyTR15);
		$tableNum16.append($tbodyTR16);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js15').remove();
	});
});//over
/* table 切 成2 个 */
$(function(){
	var $tableNum17 = $('<table class="dataTable tableData17" width="100%"></table>'),
		$theadNum17 = $('<thead></thead>'),
		$tbodyNum17 = $('<tbody></tbody>'),
		$theadTR17 = $('<tr></tr>'),// 以上创建一个新表格用于放置原始表格的前四列数据
		
		$tableNum18 = $('<table class="dataTable tableData18" width="100%"></table>'),
		$theadNum18 = $('<thead></thead>'),
		$tbodyNum18 = $('<tbody></tbody>'),
		$theadTR18= $('<tr></tr>');//以上创建另一个新表格用于放置原始表格的剩余数据
		
		$theadTR17.append($('.tableData-js17>thead').find('th:lt(7)'));
		$theadNum17.append($theadTR17);
		$tableNum17.append($theadNum17);// 将原始表格表头的前9列追加到新表格tableNum1中
		$('.col17-js').append($tableNum17);// 将新表格tableNum1追加到col1中
		
		$theadTR18.append($('.tableData-js17>thead').find('th'));
		$theadNum18.append($theadTR18);
		$tableNum18.append($theadNum18);// 将原始表格表头的剩余列数追加到新表格tableNum2中
		$('.col18-inner-js').append($tableNum18);// 将tableNum2追加到col2-inner中
		
		$('.tableData-js17>tbody').find('tr').each(function(){
		var $this = $(this), $tbodyTR17 = $('<tr></tr>'), $tbodyTR18 = $('<tr></tr>');
		$tbodyTR17.append($this.find('td:lt(7)'));
		$tbodyTR18.append($this.find('td'));
		$tableNum17.append($tbodyTR17);
		$tableNum18.append($tbodyTR18);// 将原始表格表体的前9列和剩余列数分别追加到新表格的每个表体中
		
		$('.tableData-js17').remove();
	});
});//over





















