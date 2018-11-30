﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
<style type="text/css">
.onediv{width:350px; height:300px;float:left;margin:0 0 0 5px;  }
.twodiv{width:200px; height:300px;float:left;margin:0 0 0 5px;  }
</style>
</head>
<body>
<div>
	<form id="updateRoleForm" >
		<div class="onediv">
	    	<table cellpadding="5">
	     		<tr>
	    			<td>角色名称:</td>
	    			<td>
	    				<input id="updateRoleName" name="roleName" value="${sysRoleVo.roleName }" class="easyui-textbox" type="text"  data-options="required:true"></input>
	    				<input id="updateRoleKy" name="roleKy" value="${sysRoleVo.roleKy }" readonly="readonly" type="hidden"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>角色编码:</td>
	    			<td>
	    				<input id="updateRoleCode" name="roleCode" value="${sysRoleVo.roleCode }" class="easyui-textbox" type="text"  data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>角色描述:</td>
	    			<td>
	    				<input value="${sysRoleVo.roleRemarks }" class="easyui-textbox" type="text" name="roleRemarks" data-options="required:true" ></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>状态:</td>
	    			<td>
	    				<input type="radio" name="status" value="1" <c:if test="${sysRoleVo.status eq 1 }">checked</c:if> ><span>正常</span>
	    				<input type="radio" name="status" value="0" <c:if test="${sysRoleVo.status eq 0 }">checked</c:if> ><span>禁用</span>
	    			</td>
	    		</tr>
			</table>
			<div style="text-align:center;">
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateRole()">修改</a>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeRoleWindow()">取消</a>
    		</div>
		</div>
	<div class="twodiv">
		<ul id="updateRoletree" class="easyui-tree" data-options="url:'../role/menuTree.do',method:'get',animate:true,checkbox:true" ></ul>
   	</div>
	</form>
</div>
<script type="text/javascript">
	//加载树
	 $(function() {
		 var roleKy = $("#updateRoleKy").val();
		 $("#updateRoletree").tree({  
			 onLoadSuccess:function(node,data){  
				 $.ajax({  
		             url:"../role/queryRoleMenuByRoleKy.do?roleKy="+roleKy,  
		              cache:false,  
		              dataType:'json',  
		              success:function(data){   
		              		//关闭所有节点
							//$("#updateRoletree").tree("collapseAll");
							//根据后台数据选中节点
		                  for(var i=0;i<data.length;i++)  
		                  {  
		                  		 var node = $('#updateRoletree').tree('find',data[i]); 
		                  		 var parentNode = $("#updateRoletree").tree('getParent', node.target);
		                         if (parentNode != null) {//子节点
		                    		$('#updateRoletree').tree('check',node.target);  
		                         }
		                    	//展开选中的节点
								//$('#updateRoletree').tree('expand', node.target);
		                  }  
		              } 
		             
		       	}) ;
			 }
			}) 
	 })
	//修改按钮
	 function updateRole(){ 
	  	var validate = $("#updateRoleForm").form("validate");
		if(!validate){
			return false;
		}
		 var selectedRightsList="";		
		 var node = $("#updateRoletree").tree('getChecked');
		 var arr = [];
		 for(var i=0;i<node.length;i++)  
	     {  
			 var parentNode = $("#updateRoletree").tree('getParent', node[i].target);
             if (parentNode != null) {//子节点
           	  if(!contains(arr, parentNode.id)){//id不存在arr数组内
           		  arr.push(parentNode.id);
           		  selectedRightsList += "&selectedRights=" + parentNode.id;
           	  }
           	  selectedRightsList += "&selectedRights=" + node[i].id;
             }
	     }
		$(function(){
				var data = $("#updateRoleForm").serialize() + selectedRightsList;
				$.ajax({
				url:"../role/updateRoleMenu.do",
				type : "post",
				data : data,
				dataType:"json",
				success: function(d){
				var msg = d.message;
					if(msg == "ok"){
						$.messager.alert("操作提示", "修改成功！");
						$("#updateRole").window("close");
						$('#roleList').datagrid('reload');
						
					}else{
						if (msg) {
							$.messager.alert("操作提示", msg);
						}else{
							$.messager.alert("操作提示", "修改失败，请重试！");
						}
					}
					
				},
				error:function(args){
				}
			});
		});
	} 
	
	//清空按钮
	function closeRoleWindow(){
		$("#updateRole").window("close");
	}
</script>
</body>
</html>