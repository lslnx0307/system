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
	<table id="drProductLoanList" title="分期产品信息" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../product/installmentRepaymentProductInfo.do?id=${id}',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:false,toolbar:'#drProductLoanTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'pdId'">明细id</th>
	    	<th data-options="field:'phone'" hidden="true">手机号</th>
	    	<th data-options="field:'code'" width="10%">产品编号</th>
	        <th data-options="field:'periods'" width="15%" formatter="periodsTransform">当前期数</th>
	        <th data-options="field:'simpleName'" width="15%">产品名称</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">金额(元)</th>
	        <th data-options="field:'status'" width="5%" formatter="statusTransform">还款状态</th>
	        <th data-options="field:'loanStatus'" width="5%" formatter="loanStatusTransform">回款状态</th>
	       	<th data-options="field:'shouldTime'" width="10%" formatter="iFormatDateTimeBoxFull">实际还款日期</th>
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<form id="storageForm" id="storageForm" method="POST" target="_blank">
	   <input type="hidden" id ="storageJson" name="json" value="" />			
	</form>
<script type="text/javascript">
	function periodsTransform(val){
		return "第"+val+"期";
	}
	
	//还款状态变中文
	function statusTransform(val){
		if(val == '1'){
			return "未还款";
		}
		if(val == '2'){
			return "已还款";
		}
		if(val =='3'){
			return "已逾期";
		}
	}
	//回款状态转中文
	function loanStatusTransform(val){
		if(val == '1'){
			return "未回款";
		}
		if(val == "2"){
			return "已回款";
		}
		if(val =='3'){
			return "逾期";
		}
	}
	//添加放款基本操作链接 /* '<a href="#" class="btn l-btn l-btn-small" onclick="toProductInfoBtn('+index+')">查看</a>'+"   "+ */
	var flag = true;
	function formatOper(val,row,index){
		if(row.loanStatus == '1' && flag == true){
			flag = false;
				return	'<a href="#" class="btn l-btn l-btn-small" onclick="updateRefundDrProductLoanBtn('+index+')">回款</a>';
		}
    }
	
		//回款操作
	function updateRefundDrProductLoanBtn(index){
		$('#drProductLoanList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductLoanList').datagrid('getSelected'); 
		$.messager.confirm("操作提示", "确定回款？", function(ensure){
			if(ensure){
				var url = "../product/updateReFundDrProduct.do?phone="+row.phone+"&heji="+row.amount+"&code="+row.code+"&id="+row.id+"&pdId="+row.pdId;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							if(result.map!=null){
								getStorageForm(result.map.signature);
								$("#storageForm")[0].action=result.map.fuiouUrl;
								$("#storageForm")[0].submit();
								$.messager.confirm("操作提示", result.msg, function(data){
									flag = true;
									searchDrProductLoan();
								})
							}else{
								$.messager.confirm("操作提示", result.msg, function(data){
									flag = true;
									searchDrProductLoan();
								})
							}
						}else{
							$.messager.confirm("操作提示", result.msg, function(data){
								flag = true;
								searchDrProductLoan();
							})
						}
					}
			  	});
			}
		});
	}
	function getStorageForm(json) {
		json = JSON.parse(json);
		for(var key in json.message){
			if(key !="signature") {
				$("#storageForm").prepend('<input type="hidden" name="'+key+'" value="'+json.message[key]+'" /><br/>');
			}
		}
		$("#storageForm").prepend('<input type="hidden" name="signature" value="'+json.signature+'" /><br/>');
	}
	function searchDrProductLoan(){
		$('#drProductLoanList').datagrid({});
	}
	
	//修改时间的显示样式，只显示到年月日
	function iFormatDateBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
	
	//修改时间的显示样式，只显示到年月日时分秒
	function iFormatDateTimeBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd hh:mm:ss");  
	} 
	//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}
	
//回款弹窗信息
function addLend(index){
	$('#drProductLoanList').datagrid('selectRow',index);// 关键在这里 
	var row = $('#drProductLoanList').datagrid('getSelected');
	//去后台根据选中产品的id查询出对应的信息
	var url = "../product/drProductInfoByidSQ.do?id="+row.id;
	$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#loanNameH").val(result.map.info.loanName);
					$("#bankNameH").val(result.map.info.bankName);
					$("#bankNoH").val(result.map.info.bankNO);
					$("#alreadyRaiseAmountH").val(result.map.info.alreadyRaiseAmount);
				}else{
					$('#drProductLoanList').datagrid('reload');
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	$('#line').val(index);
	$('#addLend').dialog('open').dialog('setTitle', '回款');
}

function closeDialog(){
	$("#dd").dialog("close");
}
</script>
</body>
</html>

