<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="${apppath}/js/common/jquery.form.js"></script>
</head>
<body>
	<div id="salesAchievementbtn" style="padding:5px;height:750">
		<form id="salesAchievementbtnForm"> 
	  		部门: <select  id="dept" name="dept" style="width:100px;" class="easyui-combobox">
	  		
	  				<c:if test="${dept == 1 }">
					 	<option value="1"  >电销一部</option>
	  				</c:if>
	  				<c:if test="${dept == 2 }">
					 	<option value="2"  >电销二部</option>
	  				</c:if>
	  				<c:if test="${dept != 2 && dept !=1 }">
	  					<option value=''>全部</option>
					 	<option value="1"  >电销一部</option>
					 	<option value="2"  >电销二部</option>
	  				</c:if>
				 	
	           	 </select>
	    	  月份:<input class="easyui-datebox" id="startDate"  name="startDate" style="width:100px" />
	
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resets()">重置</a>
	    	<a id="exportSalesAchievement" href="javascript:exportSalesAchievement();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>    	
	    </form>
	</div>
	<table id="salesAchievement"  style="height:99%;width:99.9%"></table>


<script type="text/javascript">
		var type="";
		//客户列表
		var salesAchievements = $('#salesAchievement');	
		
		salesAchievements.datagrid({  
			title : '会员分配表',
			fitColumns : true,
			pagination : true,
			checkbox:true,
			pageSize:75,
			pageList:[75,100],
			autoRowHeight : true,
			toolbar:"#salesAchievementbtn",
			fit:true,
		    border : 2,  
		    nowrap : false,  
		        frozenColumns: [[    
		                             { title: '', field: 'userKy', hidden:true},
		                             { title: '理财师', field: 'name', width: 50, sortable: false},
		                             { title: '电销部门', field: 'dept', width: 60, sortable: true,align :"center",
		                            	 formatter:function(value,rows){
		         							if(value=="1"){
		         								return "一部";
		         							}
		         							else if(value=="2"){
		         								return "二部";
		         							}
		                            	 }
		                             }
		                         ]],   
		        columns: [  
		        [{"title":"CPS首投","colspan":10},  
		         {"title":"非CPS首投","colspan":10},
		         {"title":"复投","colspan":10}	,
		         {"field":"total","title":"合计","rowspan":2,align :"right",
		        	 formatter:function(value, row, index){
								if(value>0){
									return '<font color="red">' + fmoney(value,2) + '</font>';
								}else{
									return '<font color="red">' + value + '</font>';
								}
							}
		         },
		         {"field":"realverifys","title":"绑卡数量","rowspan":2,
		        	 formatter: function(value, row, index){
							if(value>0){
									return "<a href='#' style='color:red;' class='easyui-linkbutton' onclick=\"selectBangkaData(\'"+row.userKy+"')\">"+value+"</a>";
								}else{
									return '<font color="red">' + value + '</font>';
								}
							}		 
		         }
		        ],  
		        [
		         {"field":"sc30","title":"30","rowspan":1},  
		         {"field":"sc35","title":"35","rowspan":1},  
		         {"field":"sc45","title":"45","rowspan":1},  
		         {"field":"sc60","title":"60","rowspan":1},  
		         {"field":"sc70","title":"70","rowspan":1},  
		         {"field":"sc75","title":"75","rowspan":1},  
		         {"field":"sc90","title":"90","rowspan":1},  
		         {"field":"sc150","title":"150","rowspan":1},  
		         {"field":"sc180","title":"180","rowspan":1},  
		         {"field":"schz","title":"汇总","rowspan":1,align :"right",
		        	 formatter: function(value, row, index){
							if(value>0){
									return "<a href='#' style='color:red;' class='easyui-linkbutton' onclick=\"selectShouTouData(\'"+row.userKy+","+1+"')\">"+fmoney(value,2)+"</a>";
								}else{
									return '<font color="red">' + value + '</font>';
								}
							}
		         },  
		         {"field":"s30","title":"30","rowspan":1},  
		         {"field":"s35","title":"35","rowspan":1},  
		         {"field":"s45","title":"45","rowspan":1},  
		         {"field":"s60","title":"60","rowspan":1},  
		         {"field":"s70","title":"70","rowspan":1},  
		         {"field":"s75","title":"75","rowspan":1},  
		         {"field":"s90","title":"90","rowspan":1},  
		         {"field":"s150","title":"150","rowspan":1},  
		         {"field":"s180","title":"180","rowspan":1},  
		         {"field":"shz","title":"汇总","rowspan":1,align: "right",
		        	 formatter: function(value, row, index){
							if(value>0){
									return "<a href='#' style='color:red;' class='easyui-linkbutton' onclick=\"selectShouTouData(\'"+row.userKy+","+0+"')\">"+fmoney(value,2)+"</a>";
								}else{
									return '<font color="red">' + value + '</font>';
								}
							
							}
		         },  
		         {"field":"f30","title":"30","rowspan":1},  
		         {"field":"f35","title":"35","rowspan":1},  
		         {"field":"f45","title":"45","rowspan":1},  
		         {"field":"f60","title":"60","rowspan":1},  
		         {"field":"f70","title":"70","rowspan":1},  
		         {"field":"f75","title":"75","rowspan":1},  
		         {"field":"f90","title":"90","rowspan":1},  
		         {"field":"f150","title":"150","rowspan":1},  
		         {"field":"f180","title":"180","rowspan":1},  
		         {"field":"fhz","title":"汇总","rowspan":1,align :"right",
		        	 formatter: function(value, row, index){
							if(value>0){
									return "<a href='#' style='color:red;' class='easyui-linkbutton' onclick=\"selectFuTouData(\'"+row.userKy+"')\">"+fmoney(value,2)+"</a>";
								}else{
									return '<font color="red">' + value + '</font>';
								}
							}
		         }
		         
		         ]
		        ],    
		        rownumbers: true   
		});  
		
		
		//查询按钮
		function query(){
			salesAchievements.datagrid({
	 			url : "../membercall/salesCallsAchievement.do",
				queryParams: {
					dept: $('#dept').combobox("getValue"),
					startDate: $('#startDate').combobox("getValue")
				}
			});
		}
		
	
		//重置按钮
		function resets(){
// 			$("#selcutomer").form("reset");
			salesAchievements.datagrid("load",{});
		};
		
		//首投cps 和首投非cps
		function selectShouTouData(userKy){
			var param = userKy.split(',');
			var strTitle = '非CPS首投明细';
			if(param[1] == 1){
				strTitle = 'CPS首投明细'
			}
			var startDate = $('#startDate').combobox("getValue");
			var mainTab = parent.$('#main-center');
			var detailTab = {
					title : strTitle,
					content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/membercall/toFirstShotInfo.do?userKy="+param[0]+"&isCps="+param[1]+"&startDate="+startDate+"' ></iframe>",
					closable : true
			};
			mainTab.tabs("add",detailTab);
		}
		//复投
		function selectFuTouData(userKy){
			var startDate = $('#startDate').combobox("getValue");
			var mainTab = parent.$('#main-center');
			var detailTab = {
					title : "复投明细",
					content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/membercall/toDoubleShotInfo.do?userKy="+userKy+"&startDate="+startDate+"' ></iframe>",
					closable : true
			};
			mainTab.tabs("add",detailTab);
		}
		//绑卡
		function selectBangkaData(userKy){
			var startDate = $('#startDate').combobox("getValue");
			var mainTab = parent.$('#main-center');
			var detailTab = {
					title : "绑卡明细",
					content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/membercall/toTiedCard.do?userKy="+userKy+"&startDate="+startDate+"' ></iframe>",
					closable : true
			};
			mainTab.tabs("add",detailTab);
		}
		
		//导出
		function exportSalesAchievement(){
			
			var win = $.messager.progress({
				title:'Please waiting',
				msg:'Loading data...'
				});
			
			var url= "../membercall/exportSalesAchievement.do"; 
			$('#salesAchievementbtnForm').form('submit',{
				url:url,
				success:function(data){
					var d = $.parseJSON(data);
					if(!d.success){
						$.messager.alert("提示信息",d.msg);
					}
				},
				error:function(){
					$.messager.progress('close');
				}
			});
			setTimeout(function(){
				$.messager.progress('close');
			},25000);
		}
		
		//时间控件只显示月份
		$(function() {
			   $('#startDate').datebox({
			       //显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
			       onShowPanel: function () {
			          //触发click事件弹出月份层
			          span.trigger('click'); 
			          if (!tds)
			            //延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
			            setTimeout(function() { 
			                tds = p.find('div.calendar-menu-month-inner td');
			                tds.click(function(e) {
			                   //禁止冒泡执行easyui给月份绑定的事件
			                   e.stopPropagation(); 
			                   //得到年份
			                   var year = /\d{4}/.exec(span.html())[0] ,
			                   //月份
			                   //之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1; 
			                   month = parseInt($(this).attr('abbr'), 10);  

			         //隐藏日期对象                     
			         $('#startDate').datebox('hidePanel') 
			           //设置日期的值
			           .datebox('setValue', year + '-' + month); 
			                        });
			                    }, 0);
			            },
			            //配置parser，返回选择的日期
			            parser: function (s) {
			                if (!s) return new Date();
			                var arr = s.split('-');
			                return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
			            },
			            //配置formatter，只返回年月 之前是这样的d.getFullYear() + '-' +(d.getMonth()); 
			            formatter: function (d) { 
			                var currentMonth = (d.getMonth()+1);
			                var currentMonthStr = currentMonth < 10 ? ('0' + currentMonth) : (currentMonth + '');
			                return d.getFullYear() + '-' + currentMonthStr; 
			            }
			        });

			        //日期选择对象
			        var p = $('#startDate').datebox('panel'), 
			        //日期选择对象中月份
			        tds = false, 
			        //显示月份层的触发控件
			        span = p.find('span.calendar-text'); 
			        var curr_time = new Date();

			        //设置前当月
			        $("#startDate").datebox("setValue", myformatter(curr_time));
			});
		
		//格式化日期
		function myformatter(date) {
		    //获取年份
		    var y = date.getFullYear();
		    //获取月份
		    var m = date.getMonth() + 1;
		    return y + '-' + m;
		}

</script>
</body>
</html>

