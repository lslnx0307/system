<%@ include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>信息数据管理中心</title>
	<%@ include file="../../common/include/util.jsp" %>
	<link rel="stylesheet" type="text/css" href="../css/common.css" />
	<link rel="stylesheet" type="text/css" href="../css/work.css" />
</head>
<body>
	<div class="easyui-layout" id="main" fit="true" >
		<div data-options="region:'north'"  style="BACKGROUND: #41506E; height: 100px; padding: 1px; overflow: hidden;">
		<div class="top-wrap" >
				<a class="logo" href="http://jushengcaifu.com" target="_Blank"><img src="../images/work-logo.png" /></a>
   			<div class="info">
    			<p>用户姓名：<span>${sessionScope.adminLoginUser.name}</span></p>
       		 	<p class="change-wrap"><a class="change" href="javascript:;" onclick="updateUserPasswordBtn()">修改密码</a>
       		 	<span>|</span><a class="exit" href="javascript:;" onclick="loginout()">退出登录</a></p>
   			</div>
		</div>
		<div id="updateUserPasswordDialog" class="easyui-dialog" title="修改密码"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateUserPassword'" style="width:340px;height:200px;padding:5px;">
			<form id="updateUserPasswordForm">
				<table align="center">
					<tr>
						<td align="right">
							请输入原密码:
						</td>
						<td>	
							<input id="oldPassword" name="oldPassword"  class="easyui-textbox" data-options="required:true" type="password"/>
						</td>
					</tr>
					<tr>
						<td align="right"> 
							新密码:
						</td>
						<td>		
							<input id="password" class="easyui-textbox"  name="password" data-options="required:true" type="password"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							请再次输入密码:
						</td>
						<td>		
							<input id="spassword" class="easyui-textbox"  data-options="required:true" type="password"/>
						</td>
					</tr>
				</table>
			</form>
			<div id="updateUserPassword">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updateUserPassword()">修改</a>
			</div>
		</div>
		</div>
		<div data-options="region:'south',split:true,disabled:true" style="height:25px;">
			<div style="color: #0E2D5F; padding-top: 2px" align="center">
	        Copyright Reserved © 聚胜财富
	    	</div>
		</div>
		<div data-options="region:'west',split:true" title="导航菜单" style="width:150px;">
			<ul class="easyui-tree">
				<c:forEach items="${sessionScope.sysMenuvo1 }" var="menu1">
					<li data-options="state:'closed'">
						<span>${menu1.name }</span>
						<ul>
							<c:forEach items="${sessionScope.sysMenuvo23 }" var="menu2">
							
							<c:if test="${menu1.menuKy == menu2.parent }">
			            	  	<c:if test="${menu2.url != '0'}">
			         				  <li>
				         				  <c:if test="${fn:contains(menu2.url, '?') != true }">
				         				  		<a href="javascript:void(0);" onclick="javascript:addTab('${menu2.name }','${menu2.url }')">${ menu2.name}</a> 
				         				  </c:if>
				         				  <c:if test="${fn:contains(menu2.url, '?') == true }">
				         				  		<a href="javascript:void(0);" onclick="javascript:addTab('${menu2.name }','${menu2.url }')">${ menu2.name}</a>   
				         				  </c:if>
			         				  </li>  
			         		   	</c:if>  
			         		   	<c:if test="${menu2.url eq '0'}">
						           	<li>
						            	<a href="javascript:void(0);" onclick="javascript:addTab('${menu2.name }','${menu2.url }')">${menu2.name }</a>
					            	</li>
				            	</c:if>
		         		   </c:if>
		         		   </c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div data-options="region:'center',iconCls:'icon-ok'">
		  <div id="main-center" class="easyui-tabs" fit="true" border="false">
			<div title="首页">
				<!-- <iframe src="../index.jsp" frameborder="0" height="100%"
					width="100%" name="mainframe"> </iframe> -->
				<div class="home-wrap" >
					<div class="data-mode">
						<div class="data">
					 	<ul>
					 	
					 	</ul>
						</div>
					</div>
					<h1>欢迎访问聚胜财富管理系统</h1>
    				<p class="welcome">welcome to visit</p>
    				<div class="show-wrap">
       				 <ul>
            			<li><img src="../images/work-img1.jpg" /></li>
            			<li><img src="../images/work-img2.jpg" /></li>
            			<li><img src="../images/work-img3.jpg" /></li>
            			<li><img src="../images/work-img4.jpg" /></li>
            			<li><img src="../images/work-img5.jpg" /></li>
       				 </ul>
    				</div>
    				<p class="intro">高品质、专业互联网P2P金融平台</p>
				</div>
			</div>
		  </div>
		</div>
	</div>
<script type="text/javascript">
	


	
	/* var dateArray = [
		{ 
		  	"dt": "2017-08-31 18:00 ~ 20:00",
		  	"text":[
		  		"1.债权新增页面字段修改"
		  	]
		 },{ 
		  	"dt": "2017-08-24 18:00 ~ 20:00",
		  	"text":[
		  		"1.转账报备功能开发",				
		  		"2.平台营销报备",				
		  		"3.渠道充值",				
		  		"4.渠道管理",				
		  		"5.渠道大类管理",				
		  		"6.渠道结算",	
		  		
		  	]
		 },{
		  	"dt": "2017-08-17 18:00 ~ 20:00",
		  	"text":[
		  		"1.还款手动同步选",				
		  		"2.活动预算选",				
		  		"3.优惠券返利明细选",				
		  		"4.平台贴息明细选",				
		  		"5.邀请奖励明细选",				
		  		"6.还款逻辑优化选",				
		  		"7.注册用户添加银行卡绑卡筛选选",		
		  	]
		 },{
		  	"dt": "2017-08-10 18:00 ~ 20:00",
		  	"text":[
		  		"1.注册用户添加银行卡绑卡筛选",				
				"2.商品订单管理页面添加2列字段",
				"3.活动管理-红包管理优化"				
		  	]
		 },{
		  	"dt": "2017-08-03 18:00 ~ 20:00",
		  	"text":[
		  		"1.企业网银充值异步通知接口",				
				"2.委托提现退票",
				"3.客户分配界面和冻结客户查询界面添加“推荐码”查询",
				"4.0元享页面自动上标功能",
				"5.感恩回馈活动后台",
				"6.后台系统：渠道优惠券发放上传格式修改",
				"7.活动聚合页管理增加状态筛选",
				"8.渠道订单查询增加7天标"
		  	]
		 },
		  {
		  	"dt": "2017-07-20 18:00 ~ 20:00",
		  	"text":[
		  		"1.提现退票接口（优化）",
				"2.开通客户端还款手机短信提醒需求",
				"3.商品分类",
				"4.用户晒单",
				"5.后台系统中与前台用户相关的关键字显示优化"
		  	]
		  },		
			{
			  	"dt": "2017-07-13 18:00 ~ 20:00",
			  	"text":[
			  		"1、当存管借款企业账户余额不足时,发邮件提醒聚胜员工",
			  		"2、活动聚合页区分终端",
			  		"3、APPPush后台增加推送终端选择"
			  	]
			}
		 
		  
	];
	for(var i = 0;i<dateArray.length;i++){
		var innerStr = '';
		for (var j=0;j<dateArray[i].text.length;j++) {
			innerStr += "<p>"+dateArray[i].text[j]+"</p>";
		}
		$(".data-mode .data ul").append("<li><span>"+dateArray[i].dt+"</span>"+innerStr+"</li>");
	}
	
	if($('.data-mode ul').height() > $('.data-mode .data').height()) {
		setInterval(function() {
			var height = Number($('.data-mode ul').find('li').eq(0).height());
			$('.data-mode ul').animate({'margin-top':-height},1000,function() {
				$('.data-mode ul').find('li').eq(0).appendTo('.data-mode ul');
				$('.data-mode ul').css('margin-top',0);
			});
		},2000);
	} */

	function addTab(title,href){
		var tt = $("#main-center");
		if(tt.tabs("exists",title)){
			tt.tabs("select",title);   
			//再次点击时，页面内容重新加载
		    //var currTab = $('#main-center').tabs('getTab', title);
			//var iframe = $(currTab.panel('options').content);
			//var content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';  
			//var src = iframe.attr('src');
			//$('#main-center').tabs('update', { tab: currTab, options: { content:content }});
		}else
		{
			var content;
			if(href){
		    	content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';   
			}else{
				content='建设中';
			}
			tt.tabs('add',{   
			    title:title,   
				closable:true,   
				content:content   
			});
		}
    }
    
    function loginout(){
    	window.location.href ="../user/exit.do";
    }
    
    function updateUserPasswordBtn(){
    	$("#updateUserPasswordDialog").dialog("open");
    }  
    
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
		/* var array = ['123456','123456789','qwerty','12345678','111111','1234567890','1234567','password','123123','987654321'];
		for (var i = 0; i < array.length; i++) {
			if(password.indexOf(array[i])>=0)
				return $.messager.alert("提示信息","密码过于简单:"+password);
		} */
		
		$.ajax({
			url:"${apppath}/user/updateUserPassword.do",
			type:"POST",
			data:$("#updateUserPasswordForm").serialize(),  
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg,"",function(){
					window.location.href ="../user/exit.do";
					});
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				};
			}
		});
    } 
    
     		
</script>
</body>
</html>