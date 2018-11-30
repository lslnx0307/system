<%@ include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>聚胜财富 - 聚胜财富信息数据管理中心</title>
	<%@ include file="../../../common/include/util.jsp" %>
</head>
<script type="text/javascript">
    function updateUserPassword(){
		var validate = $("#updateUserPasswordForm").form("validate");
			if(!validate){
				return false;
		}
		var oldPassword = $("#oldPassword").val();
		var password = $("#password").val();
		var spassword = $("#spassword").val();
		if(password != spassword){
			$.messager.alert("提示信息","两次密码不一样！");
			return false;
		}
		if(oldPassword == spassword && oldPassword == password){
			$.messager.alert("提示信息","原密码不能跟新密码一样！");
			return false;
		}
		var reg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/;
		if(!reg.test(password)){
			$.messager.alert("提示信息","密码不是至少一个字母和一个数字的6~16位的组合");
			return false;
		}
	/* 	var array = ['123456','123456789','qwerty','12345678','111111','1234567890','1234567','password','123123','987654321'];
		for (var int = 0; int < array.length; int++) {
			if(password.contains(array[int]) )
				return $.messager.alert("提示信息","密码过于简单:"+password);
		} */
		
		$.ajax({
			url:"${apppath}/user/updateUserPassword.do",
			type:"POST",
			data:$("#updateUserPasswordForm").serialize(),  
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg,"",function(){
					window.location.href ="../user/toLoginUser.do";
					});
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				};
			}
		});
    } 
    
     		
</script>
</head>
<body>
<div id="updateUserPasswordDialog" class="easyui-dialog" title="修改密码"
		data-options="iconCls:'icon-edit',closed:false,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateUserPassword'" style="width:340px;height:200px;padding:5px;">
	<form id="updateUserPasswordForm">
		<table align="center">
			<tr>
				<td align="right">
					请输入原密码:
				</td>
				<td>	
					<input id="oldPassword" name="oldPassword" class="easyui-textbox" data-options="required:true" type="password"/>
				</td>
			</tr>
			<tr>
				<td align="right"> 
					新密码:
				</td>
				<td>		
					<input id="password" class="easyui-textbox" name="password"  data-options="required:true" type="password"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					请再次输入密码:
				</td>
				<td>		
					<input id="spassword" class="easyui-textbox" data-options="required:true"  type="password"/>
				</td>
			</tr>
		</table>
	</form>
	<div id="updateUserPassword">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updateUserPassword()">修改</a>
	</div>
</div>
</body>
</html>


