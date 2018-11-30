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
	<div style="height:100%;width:100%;">
	<table id="product" title="产品列表"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	    	<th data-options="field:'fullName'" width="10%">产品全称</th>
	        <th data-options="field:'code'" width="10%">产品编号</th>
	       	<th data-options="field:'fullDate'" width="10%" formatter="formatDateBoxFull">满标时间</th>  
	       	<th data-options="field:'remitMchntTxnSsn'" width="30%">失败流水号</th>    
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	</div>
<script type="text/javascript">

		//产品列表
		var product = $('#product');
		$(function() {
			product.datagrid({
				url : "../product/getFailProductList.do",
				title : '产品列表',
				fitColumns : true,
				autoRowHeight : false,
				singleSelect:true,
				fit:true
			});
		});
		
		//操作链接
		function formatOper(val,row,index){  
				var oper = '<a href="#" onclick="updatefailproductRepay('+row.id+')">同步</a>';
				return	oper;
	    }
		
		function updatefailproductRepay(id){
			 $.messager.confirm('操作提示', '确定同步吗！', function (data) {
				 if(data){
					$.ajax({
						type: 'post',
						url:"../product/failproductRepay.do",
						data : {
							id : id
						},
						success : function(result) {
								if(result=="success"){
									$.messager.alert("提示信息","操作成功");
								}else{
									$.messager.alert("提示信息","操作失败");
								}
								product.datagrid('reload',{});
					    }
					 });
				 }
			 });
		}
</script>
</body>
</html>

