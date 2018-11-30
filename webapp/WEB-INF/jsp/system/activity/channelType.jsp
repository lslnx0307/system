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
	<table id="channelTypeList" title="渠道大类"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" width="8%" hidden="true">渠道大类</th>
	        <th data-options="field:'name'" width="10%">渠道大类</th>
	       	<th data-options="field:'type'" width="10%" formatter="formatStatus">结算类型</th>
	       	<th data-options="field:'roi'" width="10%">ROI</th>  
			<th data-options="field:'_operate',align:'center'" width="20%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="channelTypeTools" style="padding:5px;height:750">
	  	<form id="channelTypeForm" target="_blank" method="post">
	                   渠道大类:<input class="easyui-textbox" id="name" style="width:100px"/>
	  		<a id="searchChannelType" href="javascript:void(0)" class="easyui-linkbutton" onclick="searchChannelType()" iconCls="icon-search">查询</a>
	    	<a id="resetChannelType" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="insertChannelTypeBtn" href="javascript:void(0)" class="easyui-linkbutton" onclick="insertChannelTypeBtn()" iconCls="icon-add">新增</a>
 	    </form>
	</div>
	
	<!-- 新增 -->
	<div id="addChannelTypeDialog" class="easyui-dialog" title="渠道大类添加"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addChannelTypeBtn'" style="width:400px;height:250px;padding:5px;">
		<form id="addChannelTypeForm">
			<table align="center">
				<tr>
					<td>
						渠道大类：
					</td>
					<td>
						<input type="text" id="addname" name="name" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						结算类型：
					</td>
					<td>
						<select  id="addtype" name="type" style="width:150px;" class="easyui-combobox">
							<option value="1">常规标首投</option>
							<option value="2">常规标首投+7天标</option>
							<option value="3">注册一个月内投资</option>
							<option value="4">CPA+CPS首投</option>
							<option value="5">结算所有投资额（含复投）</option>
							<option value="6">CPS首投</option>
							<option value="7">CPA实名注册用户数</option>
							<option value="0">其它</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						ROI：
					</td>
					<td>
						<input type="text" id="addroi" name ="roi" class="easyui-numberbox" data-options="precision:3" />
					</td>
				</tr>
			</table>
		</form>
		<div id="addChannelTypeBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addChannelType()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	
	<!-- 修改 -->
	<div id="updateChannelTypeDialog" class="easyui-dialog" title="渠道大类修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateChannelTypeBtn'" style="width:400px;height:250px;padding:5px;">
		<form id="updateChannelTypeForm">
			<input type="hidden" id="channelTypeId"  name="id"/>
			<table align="center">
				<tr>
					<td>
						渠道大类：
					</td>
					<td>
						<input type="text" id="updatename" name ="name" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						结算类型：
					</td>
					<td>
						<select  id="updatetype" name="type" style="width:150px;" class="easyui-combobox">
							<option value="1">常规标首投</option>
							<option value="2">常规标首投+7天标</option>
							<option value="3">注册一个月内投资</option>
							<option value="4">CPA+CPS首投</option>
							<option value="5">结算所有投资额（含复投）</option>
							<option value="6">CPS首投</option>
							<option value="7">CPA实名注册用户数</option>
							<option value="0">其它</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						ROI：
					</td>
					<td>
						<input type="text" id="updateroi"  name ="roi" class="easyui-numberbox" data-options="precision:3" />
					</td>
				</tr>
			</table>
		</form>
		<div id="updateChannelTypeBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateChannelType()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
<script type="text/javascript">

	$(document).ready(function () {
		searchChannelType();
	});
	//重置按钮
	$('#resetChannelType').click(function(){
		$("#channelTypeForm").form("reset");
		searchChannelType();
	});
	//查询按钮
	function searchChannelType(){
 		$('#channelTypeList').datagrid({
 			url:"../hierarchicalStructure/getChannelTypeList.do",
 			fitColumns : true,
 			rownumbers:true,
 			showFooter: true,
 			pagination:true,
 			nowrap: false,
 			pageSize:25,
 			pageList:[25,50,100],
 			toolbar:"#channelTypeTools", 
 			queryParams: {
 				name:$('#name').textbox('getValue')
				}
		});
	};
	
	//添加状态链接
	function formatStatus(value,row,index){
		if(row.type==0){
			return "其它"
		}else if (row.type == "1"){
			return "常规标首投"
		}else if(row.type == "2"){
			return "常规标首投+7天标"
		}else if(row.type == "3"){
			return "注册一个月内投资"
		}else if(row.type == "4"){
			return "CPA+CPS首投"
		}else if(row.type == "5"){
			return "结算所有投资额（含复投）"
		}else if(row.type == "6"){
			return "CPS首投"
		}else if(row.type == "7"){
			return "CPA实名注册用户数"
		}
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		return	'<a href="#"  onclick="updateChannelTypeBtn('+index+')">修改</a>';
	} 
	
	//跳转新增页面
	function insertChannelTypeBtn(){
		$("#addChannelTypeForm").form("reset");
		$("#addChannelTypeDialog").dialog("open");
	}
	
	//关闭
	function closeDialog(){  
		$("#addChannelTypeDialog").datagrid("close");
		$("#updateChannelTypeDialog").dialog("close");
		
	}
	
	//跳转修改页面
	function updateChannelTypeBtn(index){
		$("#updateChannelTypeForm").form("reset");
		$('#channelTypeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#channelTypeList').datagrid('getSelected'); 
		$('#channelTypeId').val(row.id);
		$('#updatename').textbox('setValue',row.name);
		$('#updatetype').combobox('setValue',row.type);
		$('#updateroi').numberbox('setValue',row.roi);
		$("#updateChannelTypeDialog").dialog("open");
	}
	
	//添加渠道大类
	function addChannelType(){  
		var validate = $("#addChannelTypeForm").form("validate");
		if(!validate){
			return false;
		}
		if($('#addtype').combobox("getValue").trim()==""){
			$.messager.alert("提示信息","渠道大类信息不能为空");
			return false;
		}
   		 $.ajax({
          	url: "${apppath}/hierarchicalStructure/insertChannelType.do",
            type: 'POST',
            data:decodeURIComponent($("#addChannelTypeForm").serialize().replace(/\+/g,"")),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					searchChannelType();
					$("#addChannelTypeDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });   
	}
	
	//修改渠道大类
	function updateChannelType(){  
		var validate = $("#updateChannelTypeForm").form("validate");
		if(!validate){
			return false;
		}
		if($('#updatetype').combobox("getValue").trim()==""){
			$.messager.alert("提示信息","渠道大类信息不能为空");
			return false;
		}
   		 $.ajax({
          	url: "${apppath}/hierarchicalStructure/updateChannelType.do",
            type: 'POST',
            data:decodeURIComponent($("#updateChannelTypeForm").serialize().replace(/\+/g,"")),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					searchChannelType();
					$("#updateChannelTypeDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });   
	}
</script>
</body>
</html>

