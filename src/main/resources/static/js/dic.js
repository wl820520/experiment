// JavaScript Document
///2013-1-16
//dcm
//op -dic
var obj_c={};
function SetMenubBaseDataObj(a,b,menuarr){
	var ismenuopen=true;
	if(menuarr!="")
	{
		var meunushijiindex=menuarr.split(';');
		this.tree1 = new Array();
		for(var h=0;h<meunushijiindex.length;h++)
		{		
			this.tree1[h]={"name":tree[Number(meunushijiindex[h])].name,"link_list":tree[Number(meunushijiindex[h])].link_list};
			
		}
	}else
	{this.tree1=tree;}
	this.resetshow= function()
	{
		$("#left_tree").html("");
		$("#top_menu").html("");
		$("#top_menu_obj").html('<div  id="top_menu" class="main-nav"  style="display:none;"></div>');
		this.showmenu();
	}
	this.showmenu = function() 
	{ 		
	    var htmltxt="";
	     var quickhtmltxt="";
		var tophtmltxt="<ul>";
		for(var i=0;i<this.tree1.length;i++)
		{
			 //快速导航
			  quickhtmltxt+='<dl><dt><a href="javascript:void(0)">'+this.tree1[i].name+'</a></dt><dd>';
				 	//二级导航
				for(var c=0;c<this.tree1[i].link_list.length;c++)
				{
					quickhtmltxt+='<em><a href="'+this.tree1[i].link_list[c].upload_like +'">'+this.tree1[i].link_list[c].name+'</a></em>';
			    }			
				quickhtmltxt+='</dd></dl>';	
				
			//树形一级导航和二级导航
			if(i==a){
				//一级导航
			        htmltxt+='<li class="bdt"><a onclick="op_menuobj.treeulliclick(this)" ><span class="arrow-dropMenu">'+this.tree1[i].name+'</span></a><div class="dropMenu"><ul>';	
						//二级导航
					for(var c=0;c<this.tree1[i].link_list.length;c++)
					{
						if(c==b)
						htmltxt+='<li><a   href="'+this.tree1[i].link_list[c].upload_like +'" class="cur"><span>'+this.tree1[i].link_list[c].name+'</span></a></li>';
						else
					    htmltxt+='<li><a href="'+this.tree1[i].link_list[c].upload_like +'"><span>'+this.tree1[i].link_list[c].name+'</span></a></li>'; 
				    }			
					htmltxt+='</ul></div></li>';								
			}else
			{
				//一级导航
				//htmltxt+='<li><a href="#"  onclick="op_menuobj.treeulliclick(this)" ><span>'+this.tree1[i].name+'</span></a>'
				htmltxt+='<li><a  onclick="op_menuobj.treeulliclick(this)" ><span>'+this.tree1[i].name+'</span></a>'
				htmltxt+='<div class="dropMenu" style="display: none;"><ul>'
				//二级导航
			    for(var d=0;d<this.tree1[i].link_list.length;d++)				
					htmltxt+='<li><a  href="' + this.tree1[i].link_list[d].upload_like +'"><span>'+this.tree1[i].link_list[d].name+'</span></a></li>'; 
				
				htmltxt+='</ul></div></li>';			
			}
			//顶部一级导航和二级导航
			if(i==a)
			{
				//top_menu_obj
				var top_menu_li='<div class="sub-nav"><ul>';
				for(var e=0;e<this.tree1[i].link_list.length;e++)	
				{
					if(e==b)
					top_menu_li+='<li class="cur"><a href="'+this.tree1[i].link_list[e].upload_like+'">'+this.tree1[i].link_list[e].name+'</a></li>';
					else
					top_menu_li+='<li><a href="'+this.tree1[i].link_list[e].upload_like+'">'+this.tree1[i].link_list[e].name+'</a></li>';
				}
					top_menu_li+='</ul></div>';
					$("#top_menu_obj").append(top_menu_li);
				if(a<7)
				{
					tophtmltxt+='<li class="current">'+this.tree1[i].name+'</li>';
				}			
			}
			if(i!=a&&i<7)
			{
				var top_menu_li2='<div class="sub-nav" style="display:none"><ul>';
				for(var f=0;f<this.tree1[i].link_list.length;f++)	
				{					
					top_menu_li2+='<li><a href="'+this.tree1[i].link_list[f].upload_like+'">'+this.tree1[i].link_list[f].name+'</a></li>';
				}
					top_menu_li2+='</ul></div>';
					$("#top_menu_obj").append(top_menu_li2);
				tophtmltxt+='<li >'+this.tree1[i].name+'</li>';
			}
		}
		tophtmltxt+="</ul>";
		if(this.tree1.length>7)
		{
			tophtmltxt+='<a href="javascript:void(0)" class="op-more" onclick="op_menuobj.moreonclick();">更多>></a>';
		}
		$("#left_tree").html(htmltxt+"<div><img src='http://img2.youxinpai.com/image/chengxp/yxp-op/mode/blank.gif'/></div>");
		$("#quickCon").html(quickhtmltxt);
		$("#top_menu").html(tophtmltxt);
		if(a>=7)
		{
			$("#top_menu ul li:last-child").html(this.tree1[a].name).addClass("current");
		}
		$("#top_menu_obj").hide();	
		$('#inforAdv').show();
		//头部导航属性
		$('#top_showdataandmanage').show();		
	};
	//
	this.quickmenu_show=function()
	{
		show_popup('#quickMenuDiv','#quickMenuDiv .closeJs');
		if($('#quickCon').height() > 520)
		{		
			$("#quickCon").css({"height":520,'overflow-y':'scroll'});
		}else{
			$("#quickCon").css({"height":'auto','overflow-y':'hidden'});
		}
		return false;	
	}
	
	this.moreonclick=function(){		
		$(".fold").removeClass("packUp");
		$(".fold").css("left","160px");		
		$(".tree-menu").show();
		$(".main-nav").hide();		
		$(".op-con").css("margin-left","173px")
		$(".sub-nav ul").css("margin-left","173px");
		this.resetshow();
		$('body').addClass('body_bg').removeClass('body_bg1');	
	}
	this.treeulliclick=function (e){
	
		if($(e).next().is(":visible"))
		{
			$(e).next().hide();
			$(e).find('span').removeClass('arrow-dropMenu');
			
		}
		else{
			$(e).next().show();
			$(e).find('span').addClass('arrow-dropMenu');
		}
	
	}
	this.setlefttreeopen=function()
	{
		if($('.tree-menu').is(':visible'))
		{
			//ismenuopen=false;
			$(".fold").addClass("packUp");
			$(".fold").css("left","0px");
			$(".tree-menu").hide();
			$(".main-nav").show();
			$(".op-con").css("margin-left","13px")
			$(".sub-nav ul").css("margin-left",0);
			$("#top_menu_obj").show();
			$('#inforAdv').hide();
			//$('#top_showdataandmanage').show();				
			$(".fold").attr({title:"展开"});
			$('body').removeClass("body_bg").addClass('body_bg1');
		}else
		{
			//ismenuopen=true;
			$(".fold").removeClass("packUp");
			$(".fold").css("left","160px");		
			$(".tree-menu").show();
			$(".main-nav").hide();		
			$(".op-con").css("margin-left","173px")
			$(".sub-nav ul").css("margin-left","173px");
			op_menuobj.resetshow();
			$('.mode-nav').navtabs('.main-nav','li','.sub-nav','current');
			$("#top_menu_obj").hide();
			$('#inforAdv').show();
			//$('#top_showdataandmanage').hide();			
			$(".fold").attr({title:"收起"});
			$('body').addClass("body_bg").removeClass('body_bg1');
		}
	}
    this.settimeshowdate=function ()
	{
			var myDate = new Date();
			var month =myDate.getMonth()+1;
			var time=myDate.getFullYear()+"-"+month+"-"+myDate.getDate()+" "+myDate.toLocaleTimeString();
		    $('.span_showdata').html(time);   
	        setTimeout(op_menuobj.settimeshowdate,1000)	
	}	
}
//侧边栏的收起和展开
		/*
		$(".fold").toggle(function(){
			$(".fold").addClass("packUp");
			$(".fold").css("left","0px");
			$(".tree-menu").hide();
			$(".main-nav").show();
			$(".op-con").css("margin-left","13px")
			$(".sub-nav ul").css("margin-left",0);
			$("#top_menu_obj").show();
			$('#inforAdv').hide();
			$('#top_showdataandmanage').show();				
			$(".fold").attr({title:"展开"});
		},function(){
			$(".fold").removeClass("packUp");
			$(".fold").css("left","160px");		
			$(".tree-menu").show();
			$(".main-nav").hide();		
			$(".op-con").css("margin-left","173px")
			$(".sub-nav ul").css("margin-left","173px");
			start.resetshow();
			$('.mode-nav').navtabs('.main-nav','li','.sub-nav','current');
			$("#top_menu_obj").hide();
			$('#inforAdv').show();
			$('#top_showdataandmanage').hide();			
			$(".fold").attr({title:"收起"});
		});	*/
	//$('.dropMenu').hide();
		//$('.tree-menu ul li span').removeClass('arrow-dropMenu');
		//$(e).next().show();
		//$(e).find('span').addClass('arrow-dropMenu');
		

