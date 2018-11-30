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
	<table id="borrowInformationList" title="借款信息"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'borrowingTime'" width="10%" formatter="formatDate">借款时间</th>
	       	<th data-options="field:'amount'" width="15%" styler="styleColor" formatter="formatAmount">借款金额(元)</th>
	        <th data-options="field:'period'" width="8%">借款期限(天)</th>
	        <th data-options="field:'function'" width="15%" >借款用途</th>
	        <th data-options="field:'no'" width="10%">关联债权编号</th>
	        <th data-options="field:'borrowerName'"  width="5%">姓名</th>
	        <th data-options="field:'sex'"width="5%">性别</th>
	        <th data-options="field:'idCardNo'"width="10%">身份证号码</th>
	        <th data-options="field:'phoneNo'"width="10%">手机号</th>
	        <th data-options="field:'address'"width="5%">现居住地</th>
	        <th data-options="field:'birthday'"width="5%">出生日期</th>
	        <th data-options="field:'marriage'"width="5%">婚姻状况</th>
	        <th data-options="field:'educationalBg'"width="5%">教育程度</th>
	    </tr>
	    </thead>
	</table>
	<div id="borrowPoolTools" style="padding:5px;height:750">
	  	<form id="jsBorrowForm">
	  		借款时间<input id="searchBorrowStartDate" name="startDate" class="easyui-datebox" style="width:100px">
	  		到<input id="searchBorrowEndDate" name="endDate"  class="easyui-datebox" style="width:100px">
	  		债权期限:<input id="searchBorrowPeriod" name="period" class="easyui-numberbox"  size="15" style="width:80px"/>
	  		债权金额:<input id="searchBorrowAmountStart" name="startAmount" class="easyui-numberbox"  size="15" style="width:100px"/>
	  		-:<input id="searchBorrowAmountEnd" name="endAmount" class="easyui-numberbox"  size="15" style="width:100px"/>
	  		关联债权编号:<input id="searchBorrowInfoLname" name="no" class="easyui-textbox"  size="15" style="width:200px"/>
	  		是否关联债权:<select id="isRelevanceLoan" name="isRelevanceLoan" class="easyui-combobox" style="width:80px" data-options="editable:false">
	  					<option value="">全部</option>
	  					<option value="1">是</option>
	  					<option value="2">否</option>
	    			  </select>
	    	<a id="searchBorrowInfo" class="easyui-linkbutton" iconCls="icon-search" onclick="searchList()">查询</a>
	    	<a id="resetBorrowInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
<script type="text/javascript">
	//首次查询
	$(document).ready(function () {
		$('#searchBorrowStartDate').datebox('setValue',getdate());
		$('#searchBorrowEndDate').datebox('setValue',getdate());
		searchList();
	});
	
	//重置按钮
	$('#resetBorrowInfo').click(function(){
		$("#jsBorrowForm").form("reset");
		/* $("#drProductInfoList").datagrid("load", {}); */
		$('#searchBorrowStartDate').datebox('setValue',getdate());
		$('#searchBorrowEndDate').datebox('setValue',getdate());
		searchList();
	});
	
	//查询按钮
	function searchList(){
 		$('#borrowInformationList').datagrid({
 			url: '../borrowingInformation/borrowInformationList.do',
			queryParams: {
				period:$('#searchBorrowPeriod').numberbox('getValue'),
				no: $('#searchBorrowInfoLname').textbox('getValue'),
				startDate:$('#searchBorrowStartDate').datebox('getValue'),
				endDate:$('#searchBorrowEndDate').datebox('getValue'),
				startAmount:$('#searchBorrowAmountStart').numberbox('getValue'),
				endAmount:$('#searchBorrowAmountEnd').numberbox('getValue'),
				isRelevanceLoan:$('#isRelevanceLoan').combobox('getValue'),
			},
 		singleSelect:true,
	    fitColumns:true,
	    method:'post',rownumbers:true,showFooter: true,
	    pagination:true,toolbar:'#borrowPoolTools'
		}); 
	};
	
	function formatAmount(value,row,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	function formatDate(value,row,index){
		if (value == null || value == '') {  
	        return '';  
	    }else if(value == '总计'){
	    	return value;
	    }
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	}
	
	
	
</script>
</body>
</html>

