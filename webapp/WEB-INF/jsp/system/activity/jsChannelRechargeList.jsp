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
	
	<table id="list" title="渠道充值"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	        <th data-options="field:'recharge_date'" width="20%" >充值时间</th>
	        <th data-options="field:'channel_name'" width="20%">渠道大类</th>
	        <th data-options="field:'recharge_amount'" width="20%" styler="styleColor" formatter="formatAmount">充值金额(元)</th>
	        <th data-options="field:'balance'" width="20%" styler="styleColor" formatter="formatAmount">余额(元)</th>
	    </tr>
	    </thead>
	</table>
	<div id="tools" style="padding:5px;height:750">
	  	<form id="form">
	  		 充值时间:<input id="ssearchDrChannelInfoOrderStartDate" name="startDate" class="easyui-datebox" style="width:100px" data-options="editable:false"/>
	  		至<input id="searchDrChannelInfoOrderEndDate" name="endDate" class="easyui-datebox" style="width:100px" data-options="editable:false"/>
			渠道大类:
				<select class="easyui-combobox" id="channel_id" name="channel_id" style="width:240px;padding-bottom: 3px;" data-options="editable:false">
					<option value=''>全部</option>
					<c:forEach items='${categoryList}' var='map'>
						<option value='${map.id}'>${map.name}</option>
					</c:forEach>
					</select>
	    	<a id="searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchList()">查询</a>
	    	<a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<c:if test="${menuType ==1}"><a href="javascript:void(0)" id="openDialog" class="easyui-linkbutton" iconCls="icon-add" onclick="openDialog()" >充值</a>	</c:if>
	    </form>
	</div>
	<!-- 添加记录 -->
	<div id="addDialog" class="easyui-dialog" title="渠道充值"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addBtn'"
		style="width:550px;height:320px;padding:20px;">
		<form id="addFrom">
			<table align="center">
				<tr>
					<td>渠道大类:</td>
					<td>
						<select class="easyui-combobox" id="channel" name="channel_id" style="width:150px;padding-bottom: 3px;" data-options="required:true,editable:false">
							<c:forEach items='${categoryList}' var='map'>
								<option value='${map.id}'>${map.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>充值时间:</td>
    				<td>
    					<input  id="rechargeDate" name="recharge_date" style="width: 150px" class="easyui-datebox" data-options="required:true,editable:false"/>
    				</td>
				</tr>
				<tr>
					<td>充值金额:</td>
					<td>
						<input class="easyui-numberbox" id="rechargeAmount" name="recharge_amount" style="width:150px;" data-options="required:true,min:0,max:10000000,precision:2"/>元
					</td>
				</tr>
			</table>
		</form>
		<div id="#addBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="insert()">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="closeDialog()">取消</a>
		</div>
	</div>
<script type="text/javascript">
 	$(document).ready(function () {
		/* $('#ssearchDrChannelInfoOrderStartDate').datebox('setValue',getdate());
		$('#searchDrChannelInfoOrderEndDate').datebox('setValue',getdate()); */
		searchList();
	}); 
	//重置按钮
	$('#resetBtn').click(function(){
		$("#form").form("reset");
		$("#list").datagrid("reload", {});
	});
	
	//查询按钮
	function searchList(){
		$('#list').datagrid({
			url:"../jsChannelRechargeController/jsChannelRechargeList.do",
			singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter:true, 
		    pagination:true,
		    toolbar:'#tools',
			queryParams: {
				startDate: $('#ssearchDrChannelInfoOrderStartDate').datebox('getValue'),
				endDate: $('#searchDrChannelInfoOrderEndDate').datebox('getValue'),
				channel_id:$('#channel_id').combobox('getValue')
			}
		}); 
	}
	
	function insert(){
		var flag = $("#addDrProductInfoForm").form('enableValidation').form('validate');
		if($("#channel").combobox("getValue") == ''){
			$.messager.alert("提示信息","渠道名称未选择");
			return false;
		}
		if($("#rechargeDate").datebox("getValue") == ''){
			$.messager.alert("提示信息","充值时间未选择");
			return false;
		}
		if($("#rechargeAmount").numberbox("getValue") == ''){
			$.messager.alert("提示信息","充值金额未填写");
			return false;
		}
		if($("#rechargeAmount").numberbox("getValue") <= 0){
			$.messager.alert("提示信息","充值金额必须大于0");
			return false;
		}
		if(flag){
			$.ajax({
	          	url: "../jsChannelRechargeController/insert.do",
	            type: 'POST',
	            data:{"channel_id":$("#channel").combobox("getValue"),"channel_name":$("#channel").combobox("getText"),
	            		"recharge_date":$("#rechargeDate").datebox("getValue"),"recharge_amount":$("#rechargeAmount").numberbox('getValue')
	            },
	            dataType:'json',
	            success:function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						closeDialog();
						searchList();
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}	
	        });
			 return false; // 阻止表单自动提交事件
		}
	}
	
	//打开添加框
	function openDialog(){
		$("#addFrom").form("reset");
		$("#addDialog").dialog("open");
		$('#rechargeDate').datebox({ disabled: true });
		$('#rechargeDate').datebox('setValue',getdate());
		/* $('#channel').combobox({   
		    url:'../jsChannelRechargeController/getChannelInfoCategory.do',  //这里设置你的后台链接，后滩返回的json数据会自己解析写进下拉框
            //插件引用的也是ajax异步
		   valueField:'id',   
		   textField:'name',
		   onLoadSuccess:function(){
		    	var data = $('#channel').combobox('getData');
	            if (data.length > 0) {
	            	 $.each(data, function (n, v) {
	            		if(v.id == ''){
	            			 $('#channel').combobox('select', '');
	            		}
            		});
	            }
		    }
		}); */
	}
	//关闭添加渠道窗口
	function closeDialog(){  
		$("#addDialog").dialog("close");
	}
</script>
</body>
</html>

