<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
</head>
<body>
	<table id="activityBudgetList" title="活动预算"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	    	<th data-options="field:'budgetTime'" width="10%">预算时间段</th>
	        <th data-options="field:'computeTime'" width="10%">计算时间段</th>
	       	<th data-options="field:'oneTypeName'" width="10%">一级分类</th>
	       	<th data-options="field:'twoTypeName'" width="10%">二级分类</th>
	       	<th data-options="field:'threeTypeName'" width="10%">三级分类</th>
	       	<th data-options="field:'type'" width="10%">发放方式</th>   
	       	<th data-options="field:'amount'" width="10%">实际支出金额</th>       
	       	<th data-options="field:'budgetAmount'" width="10%">预算支出金额</th>           
	    </tr>
	    </thead>
	</table>
	<div id="activityBudgetTools" style="padding:5px;height:750">
	  	<form id="activityBudgetForm" target="_blank" method="post">
	                   预算开始时间:<input class="easyui-datebox" id="starttime" style="width:100px"/>
	  	         预算方式：<select id="isShow" name="isShow" class="easyui-combobox" style="width:100px">
	  	           		<option value="1">按周</option>
	  	           </select>
	  	         一级分类:<select id="oneType" name="oneType" class="easyui-combobox" style="width:100px" data-options="
						  	onSelect:function(res){
						  		$('#twoType').combobox({    
								    url:'../hierarchicalStructure/getActivityByFid.do?fid='+res.value+'&grade='+2,    
								    valueField:'id',    
								    textField:'name',
								    onLoadSuccess:function(){
								    	var data = $('#twoType').combobox('getData');
							            if (data.length > 0) {
								            $.each(data, function (n, v) {
							            		if(v.id == ''){
							            			 $('#twoType').combobox('select', '');
							            		}
						               		});
							            		
							            }
								    }
								});
  								}
							 ">
	  	        	<option value=''>全部</option>
	  	           	<c:forEach items="${oneType}" var="map">
						<option value='${map.id }'>${map.name}</option>
					</c:forEach>
	  	           </select>
	  	        二级分类:<select id="twoType" name="twoType" class="easyui-combobox" style="width:100px" data-options="
						  	onSelect:function(res){
						      	var fid='';
						  		if(res.id){
						  			fid=res.id;
						  		}else if(res.value){
						  			fid=res.value;
						  		}
						  		$('#threeType').combobox({    
								    url:'../hierarchicalStructure/getActivityByFid.do?fid='+fid+'&grade='+3,    
								    valueField:'id',    
								    textField:'name',
								    onLoadSuccess:function(){
								    	var data = $('#threeType').combobox('getData');
							            if (data.length > 0) {
							            	 $.each(data, function (n, v) {
							            		if(v.id == ''){
							            			 $('#threeType').combobox('select', '');
							            		}
						               		});
							            }
								    }
								});
  								}
							 ">
	  	           		<option value=''>全部</option>
		  	           	<c:forEach items="${twoType}" var="map">
							<option value='${map.id }'>${map.name}</option>
						</c:forEach>
	  	           </select>
	  	        三级分类:<select id="threeType" name="threeType" class="easyui-combobox" style="width:150px">
	  	           		<option value=''>全部</option>
		  	           	<c:forEach items="${threeType}" var="map">
							<option value='${map.id }'>${map.name}</option>
						</c:forEach>
	  	           </select>	  	
	  		<a id="searchActivityBudget" href="javascript:void(0)" class="easyui-linkbutton" onclick="searchActivityBudget()" iconCls="icon-search">查询</a>
	    	<a id="resetActivityBudget" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
<!-- 	    	<a id="exportActivityBudget" href="javascript:void(0)" class="easyui-linkbutton" onclick="exportActivityBudget()" iconCls="icon-redo">导出</a>
 -->	    </form>
	</div>
<script type="text/javascript">

	$(document).ready(function () {
		$('#starttime').datebox('setValue',getdate());
		searchActivityBudget();
	});
	//重置按钮
	$('#resetActivityBudget').click(function(){
		$("#activityBudgetForm").form("reset");
		$('#starttime').datebox('setValue',getdate());
		searchActivityBudget();
	});
	//查询按钮
	function searchActivityBudget(){
 		$('#activityBudgetList').datagrid({
 			url:"../hierarchicalStructure/getActivityBudget.do",
 			fitColumns : true,
 			rownumbers:true,
 			showFooter: true,
 			pagination:true,
 			nowrap: false,
 			pageSize:25,
 			pageList:[25,50,100],
 			toolbar:"#activityBudgetTools", 
 			showFooter: true,
 			queryParams: {
 				starttime:$('#starttime').combobox('getValue'),
 				oneType:$('#oneType').combobox('getValue'),
 				twoType:$('#twoType').combobox('getValue'),
 				threeType:$('#threeType').combobox('getValue')
				}
		});
	};
	
	function exportActivityBudget(){
		window.location.href="../hierarchicalStructure/exportActivityBudget.do?starttime="+$('#starttime').datebox('getValue')+
 				"&oneType="+$('#oneType').combobox('getValue')+
 				"&twoType="+$('#twoType').combobox('getValue')+
 				"&threeType="+$('#threeType').combobox('getValue');
	}
</script>
</body>
</html>

