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
	<table id="activityBudgetList" title="复投明细 " 
	style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'allot'" width="auto">部门</th>
	        <th data-options="field:'id'" width="auto">投资序列号</th>
	       	<th data-options="field:'recommCodes',align:'center'" width="auto">唯一码</th>
	        <th data-options="field:'uid'" width="auto" >UID</th>
	        <th data-options="field:'mobilePhone',align:'center'" width="auto">用户手机号</th>	        
	        <th data-options="field:'regdate',align:'center'" width="auto" formatter="iFormatDateBoxFull">注册时间</th>
	        <th data-options="field:'investTime',align:'center'" width="auto" formatter="iFormatDateBoxFull">投资时间</th>
	        <th data-options="field:'amount',align:'center'" width="auto" formatter="formatAmount">投资金额</th>
	        <th data-options="field:'deadline',align:'center'" width="auto" >产品期限</th>
	        <th data-options="field:'calldate',align:'center'" width="auto" formatter="iFormatDateBoxFull">最近沟通时间</th>
	        <th data-options="field:'name',align:'center'" width="auto" >理财师</th>
	    </tr>
	    </thead>
	</table>
	<!-- <div id="jsProductPrizeTools" style="padding:5px;height:750">
	  	<form id="activityBudgetForm" target="_blank" method="post">
	  	<input id="userKy" name="userKy" type="text" style="width:100px"/>
	  	<input id="isCps" name="isCps" type="text" style="width:100px"/>
	  		投资时间:<input class="easyui-datetimebox" id="startInvestTime" name="startInvestTime" style="width:100px"/>
	  		至<input class="easyui-datetimebox" id="endInvestTime" name="endInvestTime" style="width:100px"/>
	                           渠道:<select id="channel" name="channel" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='存管'>存管</option>		  					
		  					<option value='金运通'>金运通</option>
	  				</select>
	  		三级分类:<select id="level3" name="level3" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='平台未满标贴息'>平台未满标贴息</option>		  					
		  					<option value='平台存管贴息'>平台存管贴息</option>
		  					<option value='平台金运通贴息'>平台金运通贴息</option>
	  				</select>	
	  		产品名称:<input id="productName" name="productName" type="text" class="easyui-textbox" style="width:100px"/>

	  		<a id="searchBudget" href="javascript:void(0)" class="easyui-linkbutton" onclick="searchBudgetList()" iconCls="icon-search">查询</a>
	    	<a id="resetBudget" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exportBudget" href="javascript:void(0)" class="easyui-linkbutton" onclick="exportActivityBudget()" iconCls="icon-redo">导出</a>
	    </form>
	</div> -->
<script type="text/javascript">
	var userKy = '${userKy}';
	var startDate = '${startDate}';
	
	$(document).ready(function () {
		//initTime();
		searchBudgetList();
	});
	//重置按钮
	$('#resetBudget').click(function(){
		$("#activityBudgetForm").form("reset");
		//$("#activityBudgetList").datagrid("load", {});
	});
	//查询按钮
	var searchBudgetList = function(){
 		$('#activityBudgetList').datagrid({
 			url:"${apppath}/membercall/doubleShotInfo.do",
 			fitColumns : true,
 			showFooter:true,
 			pagination : true,
 			rownumbers:true,
 			pageSize:10,
 			pageList:[10,25,50,100],
 			autoRowHeight : false,
 			//toolbar:"#jsProductPrizeTools", 
			queryParams: {
				userKy : userKy,
				startDate : startDate
			}
		}); 
	};
	var activityBudgetSum = function(){
		 $.ajax({
				url:"${apppath}/hierarchicalStructure/activityBudgetSum.do",
				type:"POST",
				data:$("#activityBudgetForm").serialize(),  
				success:function(result){
					$("#investAmountSum").text(fmoney(result.amount,2));
					$("#subsidyAmountSum").text(fmoney(result.subsidyAmount,2));
				}
			});
	}
	//投资日期
	function iFormatDateBoxFull(value) {
		if(value == '合计'){
			return '合计';
		}
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd hh:mm:ss");  
	} 
	//金额
	function formatAmount(value) {  
	    if (value == null || value == '') {  
	        return 0.00;  
	    }  
	    return fmoney(value,2);  
	}
	//显示当前日期
	var initTime = function(){
		var dt = parseToDate(new Date());  
		var startDate = dt.format("yyyy-MM-dd 00:00:00");
		var endDate = dt.format("yyyy-MM-dd hh:mm:ss");
		$('#startInvestTime').datetimebox('setValue',startDate);
		$('#endInvestTime').datetimebox('setValue',endDate);
  	};
	//导出
	function exportActivityBudget(){
		window.location.href="../hierarchicalStructure/exportActivityBudgetList.do?startInvestTime="+$('#startInvestTime').datebox('getValue')+
		"&endInvestTime="+$('#endInvestTime').datebox('getValue')+
		"&channel="+$('#channel').combobox('getValue')+
		"&productName="+$('#productName').textbox('getValue')+
		"&level3="+$('#level3').combobox('getValue');
	}
</script>
</body>
</html>

