<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
<script src="../js/layer/layer.min.js"></script>
<script src="../js/layer/extend/layer.ext.js"></script>
</head>
<body>
	<table id="drProductInfoList" title="邀请奖励明细" style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'investTime'"  width="10%">投资时间</th>
	    	<th data-options="field:'fullName'"  width="10%">产品名称</th>	
	    	<th data-options="field:'typeTwo'"  width="10%">二级分类</th>		    	
	    	<th data-options="field:'typeThree'" width="10%">三级分类</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount" >投资金额(元)</th>
	       	<th data-options="field:'payAmount'" width="10%" styler="styleColor" formatter="formatAmount">支出金额(元)</th>
	        <th data-options="field:'project_no'"  width="8%">渠道</th>
	    </tr>
	    </thead>
	</table>
	<div id="tools" style="padding:5px;height:750">
	  	<form id="drProductInfoForm">
	  	 	投资时间:<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="endDate" name="endDate" class="easyui-datebox" style="width:100px"/>
			渠道:<select  id="projectNo" name="projectNo" style="width:100px;" class="easyui-combobox">
					<option value=''>全部</option>
					<option value='1'>金运通</option>
					<option value='2'>存管</option>
         		</select>
	  		
	  		三级分类: <select  id="three_flag" name="three_flag" style="width:172px;" class="easyui-combobox">
						<option value=''>全部</option>
						<option value='0'>邀请首投奖励</option>
						<option value='1'>邀请复投奖励</option>
	           		</select>
	           		
	                     产品名称:<input id="searchDrProductInfoSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	    	<a id="searchDrProductInfo" class="easyui-linkbutton" iconCls="icon-search" onclick="searchList()">查询</a>
	    	<a id="resetDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exportDrProductInfo" href="javascript:exportDrProductInfo();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
<script type="text/javascript">
	//首次查询
	window.onload = function(){
		$('#startDate').datebox('setValue',getstartdate());
		$('#endDate').datebox('setValue',getenddate());
		searchList();
	}
	//重置按钮
	$('#resetDrProductInfo').click(function(){
		$("#drProductInfoForm").form("reset");
		$('#startDate').datebox('setValue',getstartdate());
		$('#endDate').datebox('setValue',getenddate());
		searchList();
	});
	
	//查询按钮
	function searchList(){
 		$('#drProductInfoList').datagrid({
 			url: '../hierarchicalStructure/inviteAwarDetailList.do',
			queryParams: {
				startDate:$("#startDate").datebox('getValue'),
				endDate:$("#endDate").datebox('getValue'),
				projectNo:$("#projectNo").combobox('getValue'),
				three_flag:$("#three_flag").combobox('getValue'),
				simpleName: $('#searchDrProductInfoSimpleName').val()
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#tools',nowrap: false
		}); 
	}
	
	
	//导出
	function exportDrProductInfo(){
		window.location.href="../hierarchicalStructure/exportInviteAwarDetail.do?startDate="+$("#startDate").datebox('getValue')+
		"&endDate="+$("#endDate").datebox('getValue')+
		"&projectNo="+$("#projectNo").combobox('getValue')+"&three_flag="+$("#three_flag").combobox('getValue')+
		"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductInfoSimpleName').val()));
	}
	
	
</script>
</body>
</html>

