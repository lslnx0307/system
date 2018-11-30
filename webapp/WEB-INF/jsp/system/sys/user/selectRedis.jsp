﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
	<%@ include file="../../../common/include/util.jsp" %>



</head>
<body style="width:100%;height:100%">
	<table id="redisList" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, 
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#redisTools'">
		<thead>
			<tr>
				<th data-options="field:'key',width:'10%'" >key</th>	
				<th data-options="field:'value',width:'80%'" >value</th>	
			</tr>
		</thead>
	</table>
	<div id="redisTools" style="margin-bottom:5px">
			<form id="redisForm">		
				redisKey:<input id="redisKey" name="redisKey" size="30" class="easyui-textbox"  />
				type:<select id="type" name="type" style="width:100px;" class="easyui-combobox">
						<option value="1">List</option>
						<option value="0">String</option>
						<option value="2">hash</option>
						<option value="3">set</option>
					</select>
				valueType:<select id="valueType" name="valueType" style="width:100px;" class="easyui-combobox" >
						<option value="0">binary</option>
						<option value="1">String</option>
					</select>
			</form>
	    	<a id="searchBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="searchBtn" href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addRedisBtn()">添加</a>
	</div>
	
	<div id="openAddRedisBtn" class="easyui-dialog" title="redis添加"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false" style="width:600px;height:350px;padding:5px;">
		<div id="keyAddExtendBtn">
			<a href='javascript:void(0)' class='icon-add' onclick='keyAddExtend();'></a>
		</div>
		<form id="addRedisForm">
			<table align="center" id="addRedisTable">
				<tr>
					<td>
						json：<input  class="easyui-textbox" data-options="multiline:true" name ="json" style="width:300px;height:200px">
					</td>
				</tr>
			</table>
					<div id="addRedisBtn" align ="center" valign="bottom">
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addRedisInfo()">添加</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
					</div>
		</form>
	</div>
	
</body>

<script type="text/javascript">
	//查询按钮
	$('#searchBtn').click(function(){
 		$('#redisList').datagrid({
 			url:'../gukaimingTest/selectRedis.do',
			queryParams: {
				redisKey: $('#redisKey').textbox('getValue'),
				type: $('#type').combobox('getValue'),
				valueType: $('#valueType').combobox('getValue'),
			}
		}); 
	});
	
	function addRedisInfo(){
		$.ajax({
			type: 'post',
			url : "../gukaimingTest/setRedis.do",
			cache : false,
			data : $("#addRedisForm").serialize(),
			cache : false,
			async : false,
			success : function(message) {
				if (message=='success') { 	
					$.messager.alert('操作提示','成功');
					$('#openAddRedisBtn').dialog('close');					
				}  
				else {  
					$.messager.alert('操作提示',message);
					return false;  
				} 
		    },  
		     error : function(message) {
			 $.messager.alert('操作提示','失败');
			 return false; 
		}
		 });
		
	}
	
	function closeDialog(){
		$("#openAddRedisBtn").dialog("close");
		$("#addRedisForm").form("reset");
		keyAddExtendIndex=1;
	}
	
	//跳转添加页面
	function addRedisBtn(){  
		$("#addRedisForm").form("reset");
		$("#openAddRedisBtn").dialog("open");
		
	}
	
	
	
	
	function keyAddExtend(){
		var tr = '<tr><td>key：<input type="text" name ="paramList['+keyAddExtendIndex+'].key"> ----</td><td>value: <input type="text" name ="paramList['+keyAddExtendIndex+'].value"></td></tr>';
		
		keyAddExtendIndex++;		
		$("#addRedisTable").append(tr);
		
		$('.easyui-textbox').textbox({    
		}); 
	}
	
</script>
</html>
