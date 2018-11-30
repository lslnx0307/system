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
	<table id="drProductInfoList" title="优惠券层次配置"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" width="10%" hidden="true"></th>
	    	<th data-options="field:'oneTypeName'" width="10%">一级分类</th>
	       	<th data-options="field:'twoTypeName'" width="10%">二级分类</th>
	       	<th data-options="field:'threeTypeName'" width="10%">三级分类</th>
	       	<th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="tools" style="padding:5px;height:750">
	  	<form id="drProductInfoForm">
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
	  		二级分类: <select  id="twoType" name="twoType" style="width:172px;" class="easyui-combobox">
						<option value=''>全部</option>
	           		</select>
	    	<a id="searchDrProductInfo" class="easyui-linkbutton" iconCls="icon-search" onclick="searchList()">查询</a>
	    	<a id="resetDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="addcouponHierarchy" href="javascript:void(0)" class="easyui-linkbutton" onclick="addCouponHierarchyDialogBtn()" iconCls="icon-add">新增</a>
	    </form>
	</div>
	
	<div id="addCouponHierarchyDialog" class="easyui-dialog" title="新增优惠券" 
		data-options="iconCls:'icon-add',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addCouponHierarchyBtn'"
		style="width:550px;height:200px;padding:20px;">
		<form id="addCouponHierarchyFrom">
			<table align="center">
			<tr>
				<td>
					 一级分类: <select id="oneTypeAdd" name="oneType" class="easyui-combobox" style="width:172px" data-options="
							  	onSelect:function(res){
							  		$('#twoTypeAdd').combobox({    
									    url:'../hierarchicalStructure/getActivityByFid.do?fid='+res.value+'&grade='+2,    
									    valueField:'id',    
									    textField:'name',
									    onLoadSuccess:function(){
									    	var data = $('#twoTypeAdd').combobox('getData');
								            if (data.length > 0) {
									            $.each(data, function (n, v) {
								            		if(v.id == ''){
								            			 $('#twoTypeAdd').combobox('select', '');
								            		}
							               		});
								            		
								            }
									    }
									});
	  								},editable:false
								 " >
									<option value="">全部</option>
									<option value="3">优惠券返利</option>
		  	            	</select>
	  	           	 </td>
	  	           </tr>
	  	           <tr> 
		  	            <td>
			  			二级分类: <select  id="twoTypeAdd" name="twoType" style="width:172px;" class="easyui-combobox" data-options="editable:false"/>
			           			<option value="">全部</option>
			           			</select>
			           	</td>	
		           </tr>
		           <tr>
		           		<td>
		           			三级分类: <input id="threeTypeAdd" name="threeType" style="width:172px;" class="easyui-textbox" type="text" data-options="required:true">
		           		</td>
		           </tr>	
			</table>
		</form>
		<div id="#addCouponHierarchyBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addCouponHierarchyBT()">添加</a>
		</div>
	</div>
	<div id="updateCouponHierarchyDialog" class="easyui-dialog" title="新增优惠券" 
		data-options="iconCls:'icon-add',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addCouponHierarchyBtn'"
		style="width:550px;height:200px;padding:20px;">
		<form id="updateCouponHierarchyFrom">
			<table align="center">
			<tr>
				<td>
					 一级分类: <select id="oneTypeUpdate" name="oneType" class="easyui-combobox" style="width:172px" data-options="
							  	onSelect:function(res){
							  		$('#twoTypeAdd').combobox({    
									    url:'../hierarchicalStructure/getActivityByFid.do?fid='+res.value+'&grade='+2,    
									    valueField:'id',    
									    textField:'name',
									    onLoadSuccess:function(){
									    	var data = $('#twoTypeAdd').combobox('getData');
								            if (data.length > 0) {
									            $.each(data, function (n, v) {
								            		if(v.id == ''){
								            			 $('#twoTypeAdd').combobox('select', '');
								            		}
							               		});
								            		
								            }
									    }
									});
	  								},disabled:true
								 " >
		  	            	</select>
	  	           	 </td>
	  	           </tr>
	  	           <tr> 
		  	            <td>
			  			二级分类: <select  id="twoTypeUpdate" name="twoType" style="width:172px;" class="easyui-combobox" data-options="disabled:true">
			           			</select>
			           	</td>	
		           </tr>
		           <tr>
		           		<td>
		           			三级分类: <input id="threeTypeUpdate" name="threeType" style="width:172px;" class="easyui-textbox" type="text" data-options="required:true">
		           		</td>
		           </tr>	
			</table>
		</form>
		<div id="#addCouponHierarchyBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateCouponHierarchySetUp()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
<script type="text/javascript">

	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.isYes == 1){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateCouponHierarchyUpWindow('+index+')">修改</a>'+' '
		}else{
			return '-';
		}
		
	} 
	//跳转到添加页面
	function addCouponHierarchyDialogBtn(){  
		$("#addCouponHierarchyFrom").form("reset");
		$("#addCouponHierarchyDialog").dialog("open");
	}
	//优惠券添加
	function addCouponHierarchyBT(){
		var validate = $("#addCouponHierarchyFrom").form("validate");
		if(!validate){
			return false;
		}
		var threeType=$("#threeTypeAdd").textbox("getValue");
		if(threeType=='' || threeType == "undefined"){
			$.messager.alert("提示信息","请输入优惠券名");
			return false;
		}else if(threeType.length>20){
			$.messager.alert("提示信息","优惠券名长度不得超过20");
			return false;
		}
		var oneTypeAdd=$("#oneTypeAdd").combobox("getValue");
		if(oneTypeAdd=='' || oneTypeAdd == "undefined" ){
			$.messager.alert("提示信息","请选择第一层级");
			return false;
		}
		var twoTypeAdd=$("#twoTypeAdd").combobox("getValue");
		if(twoTypeAdd=='' || twoTypeAdd == "undefined"){
			$.messager.alert("提示信息","请选择第二层级");
			return false;
		}
 		$.ajax({
         	url: "../hierarchicalStructure/addCouponHierarchy.do",
           	type: 'POST',
           	data:$("#addCouponHierarchyFrom").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductInfoList").datagrid("reload");
					$("#addCouponHierarchyDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
	}
	
	//首次查询
	$(document).ready(function () {
		searchList();
	});
	//重置按钮
	$('#resetDrProductInfo').click(function(){
		$("#drProductInfoForm").form("reset");
		/* $("#drProductInfoList").datagrid("load", {}); */
		searchList();
	});
	
	//查询按钮
	function searchList(){
 		$('#drProductInfoList').datagrid({
 			url: '../hierarchicalStructure/couponHierarchyDetail.do',
			queryParams: {
				twoType:$("#twoType").combobox('getValue'),
				oneType:$("#oneType").combobox('getValue'),
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#tools'
		}); 
	}

	//打开修改窗口
	function updateCouponHierarchyUpWindow(index){  
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
		var url = "../hierarchicalStructure/selectHierarchyByid.do?id="+row.id;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$('#threeTypeUpdate').textbox('setValue',result.map.threeTypeName);
					$('#twoTypeUpdate').combobox('setValue',result.map.twoTypeName);
					$('#oneTypeUpdate').combobox('setValue',result.map.oneTypeName);
					$("#updateCouponHierarchyDialog").dialog("open");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	//修改
	function updateCouponHierarchySetUp(){
		var validate = $("#updateCouponHierarchyFrom").form("validate");
		 var row = $('#drProductInfoList').datagrid('getSelected');
		if(!validate){
			return false;
		}
		var threeType=$("#threeTypeUpdate").textbox("getValue");
		if(threeType=='' || threeType == "undefined"){
			$.messager.alert("提示信息","请输入优惠券名");
			return false;
		}else if(threeType.length>20){
			$.messager.alert("提示信息","优惠券名长度不得超过20");
			return false;
		}
		
		$.ajax({
         	url: "../hierarchicalStructure/updateCouponHierarchy.do?id="+row.id,
           	type: 'POST',
           	data:$("#updateCouponHierarchyFrom").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductInfoList").datagrid("reload");
					$("#updateCouponHierarchyDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
	}
	//关闭添加渠道窗口
	function closeDialog(){  
		$("#drProductInfoList").datagrid("reload");
		$("#updateCouponHierarchyDialog").dialog("close");
		$("#addCouponHierarchyDialog").dialog("close");
	}
	
	
	
</script>
</body>
</html>

