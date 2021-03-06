<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<table id="couponsIssuedRulesList" title="系统发放优惠券规则设置" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../activity/couponsIssuedRulesManager.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#couponsIssuedRulesTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">规则ID</th>
				<th data-options="field:'type'" width="5%" formatter="formatType">赠送类型</th>
				<th data-options="field:'name'" width="5%">活动名称</th>
				<th data-options="field:'startTime'" width="8%" formatter="formatDateBoxFull">开始时间</th>
				<th data-options="field:'endTime'" width="8%" formatter="formatDateBoxFull">结束时间</th>
				<th data-options="field:'couponTypeName'" width="5%" >优惠券类型</th>
				<th data-options="field:'isCps'" width="3%" formatter="formatIsCps"  >cps使用</th>
				<th data-options="field:'coupons'" hidden="hidden" >优惠券ids</th>
				<th data-options="field:'couponNames'" width="10%" >优惠券名称</th>
				<th data-options="field:'status'" width="3%" formatter="formatStatus">状态</th>
				<th data-options="field:'message'" width="10%" >站内信</th>
				<th data-options="field:'addName'" width="8%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDateBoxFull" width="8%">添加时间</th>
				<th data-options="field:'updateName'" width="5%">修改人</th>
				<th data-options="field:'updateTime'" formatter="formatDateBoxFull" width="8%">修改时间</th>
				<th data-options="field:'_operate',width:150,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="couponsIssuedRulesTools" style="padding:5px;height:750">
		<form id="couponsIssuedRulesForm">
			赠送类型：<select id="searchType" name="type" style="width:150px;" class="easyui-combobox">
				<option value=''>全部</option>
				<option value='0'>注册赠送</option>
				<option value='1'>投资后赠送</option>
				<option value='2'>手动发送</option>
				<option value='3'>绑卡发送</option>
				<option value='4'>邀请好友三重好礼</option>
				<option value='5'>感恩活动</option>
				<option value='7'>开通存管</option>
				<option value='8'>待流失唤回</option>
				<option value='9'>已流失唤回</option>
			</select> 
			
			状态：<select id="searchStatus" name="status" style="width:100px;" class="easyui-combobox">
				<option value=''>全部</option>
				<option value='0'>新增</option>
				<option value='1'>生效</option>
				<option value='2'>失效</option>
			</select> 
			
			<a id="searchIssuedRulesBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetIssuedRulesBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addIssuedRulesDialog()">新增</a>
		</form>
	</div>


	<!-- 新增系统发放优惠券规则 -->
	<div id="addIssuedRulesDialog" class="easyui-dialog" title="系统优惠券发放规则-新增"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addIssuedRulesBtn'"
		style="width:550px;height:320px;padding:20px;">
		<form id="addIssuedRulesForm">
			<table align="center">
				<tr>
					<td>赠送类型:</td>
					<td>
						<select id="addType" name="type" style="width:150px;" class="easyui-combobox" data-options="required:true">
							<option value='0'>注册赠送</option>
							<option value='1' selected="selected">投资后赠送</option>
							<option value='2'>手动发送</option>
							<option value='3'>绑卡发送</option>
							<option value='4'>邀请好友三重好礼</option>
							<option value='5'>感恩活动</option>
							<option value='7'>开通存管</option>
							<option value='8'>待流失唤回</option>
							<option value='9'>已流失唤回</option>
						</select>
					</td>
					<td>活动名称:</td>
					<td>
						<input id="addName" name="name" style="width:150px" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>开始时间≥:</td>
    				<td>
    					<input id="addStartTime" name="startTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true"/>
    				</td>
    				<td>结束时间&lt:</td>
    				<td>
    					<input id="addEndTime" name="endTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true">
    				</td>
				</tr>
				<tr>
					<td>使用场景:</td>
					<td>
						<select id="addScene" name="scene" style="width:150px;" class="easyui-combobox" data-options="required:true">
							<option value='' selected="selected">--全部--</option>
							<option value='0' >电销使用</option>
							<option value='1' >活动使用</option>
							<option value='2'>运营使用</option>
						</select>
					</td>
					<td>优惠券类型:</td>
					<td>
						<select id="addCouponType" name="couponType" style="width:150px;" class="easyui-combobox">
							<c:forEach items="${couponType}" var="map">
								<option value='${map.key }'>${map.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>cps使用:</td>
					<td>
						<select id="addIsCps" name="isCps" style="width:150px;" class="easyui-combobox" data-options="required:true">
							<option value='2' selected="selected">--全部--</option>
							<option value='0' >非cps</option>
							<option value='1' >是cps</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>优惠券:</td>
					<td colspan="3">
						<input class="easyui-combogrid"  style="width:400px;" id="addCoupons" name="coupons" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>站内信:</td>
					<td colspan="3">
						<input class="easyui-textbox" name="message" style="height:100px;width:400px;" data-options="multiline:true,validType:'maxLength[2000]'"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="#addIssuedRulesBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addIssuedRules()">新增</a>
		</div>
	</div>
	
	<div id="updateIssuedRulesWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:550px;height:320px;padding:10px;">
	</div>
	
	<div id="uploadFileDialog" class="easyui-dialog" title="导入发券用户"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#uploadFileDialogBtn'" 
		style="width:420px;height:200px;padding:15px;">
		<form id="uploadFileDialogForm" enctype="multipart/form-data">
			<input type="hidden" id="uploadFileCoupons"  name="coupons"/>
			<input type="hidden" id="uploadFileCirId" name="cirId"> 
			<table align="center">
				<tr>
					<td style="width:120px">
						活动名称：
					</td>
					<td>
						<input id="uploadName" name="name" style="width:280px;" readonly="readonly">
					</td>
				</tr>
				<tr>
					<td style="width:120px">
						优惠券名称：
					</td>
					<td>
						<input id="uploadCouponNames" name="couponNames" style="width:280px;" readonly="readonly">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input id="uploadFile" type="file" name="file" onchange="verifyFile(this)"/>
					</td>
				</tr>
				
			</table>
		</form>
		<div id="uploadFileDialogBtn">
			<a id="uploadMemberInfo" href="javascript:void(0)" class="easyui-linkbutton" >导入</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	
	<div id="ManuallySendCouponListDialog" class="easyui-dialog" title="查看用户并发券"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false" style="width:80%;height:70%;padding:5px;">
		<table id="drManuallySendCouponLis"
			class="easyui-datagrid" style="height:99%;width:99.9%"
			data-options="singleSelect:true,
			    fitColumns:true,
			    method:'post',rownumbers:true, 
			    pagination:true,toolbar:'#drManuallySendCouponTools'">
				<thead>
			    <tr>
			    	<th data-options="field:'id'" hidden="true">序号</th>
			    	<th data-options="field:'couponNames'" width="25%">券名称</th>
			        <th data-options="field:'mobilePhone'" width="15%">用户手机号</th>
			        <th data-options="field:'status'" formatter="formatSendStatus" width="10%">发送状态</th>
			        <th data-options="field:'addName'" width="10%" >添加人</th>
			        <th data-options="field:'addTime'" width="12%" formatter="formatDateBoxFull">添加时间</th>
			   		<th data-options="field:'sendName'" width="10%" >发券人</th>
			        <th data-options="field:'sendTime'" width="12%" formatter="formatDateBoxFull">发券时间</th>
			    </tr>
			    </thead>
			</table>
	</div>
	<div id="drManuallySendCouponTools" style="padding:5px;height:750">
	  	<form id="drManuallySendCouponForm">
	  		<div>
		  		<input type="hidden" id="searcheCirId"  name="cirId"/>
		  		活动名称:<input id="issuedRulesName" type="text" class="easyui-text" style="width:150px" readonly="readonly">
		  		导入日期:<input id="searchAddTime" name="addTime" class="easyui-datetimebox" style="width:150px" data-options="required:true"/>
		    	<a id="searchDrManuallySendCoupon" href="javascript:searcheManuallySendCouponBtn()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		    	<a id="sendCoupon" href="javascript:sendCouponBtn()" class="easyui-linkbutton" iconCls="icon-ok">发送优惠券</a>
	  		</div>
	  		<div>
	  			站内信内容:<input id="issuedRulesMessage" name="message" type="text" class="easyui-text" style="width:350px" readonly="readonly">
	  		</div>
	    </form>
	</div>
		
<script type="text/javascript">

	//重置按钮
	$('#resetIssuedRulesBtn').click(function(){
		$("#couponsIssuedRulesForm").form("reset");
		$("#couponsIssuedRulesList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchIssuedRulesBtn').click(function(){
 		$('#couponsIssuedRulesList').datagrid({
			queryParams: {
				type: $('#searchType').combobox('getValue'),
				status: $('#searchStatus').combobox('getValue'),
			}
		}); 
	});
	
	//跳转到添加页面
	function addIssuedRulesDialog(){  
		$("#addIssuedRulesForm").form("reset");
		$("#addIssuedRulesDialog").dialog("open");
		//$("#addCoupons").combogrid("setValue",'');//清空优惠券
	}
	
	//根据使用场景获取优惠券
	$('#addScene').combobox({
		onSelect:function(record){
           var type = $('#addCouponType').combobox('getValue');
           var scene = $('#addScene').combobox('getValue');
           $("#addCoupons").combogrid("setValue",'');//清空优惠券
           $("#addCoupons").combogrid({
           	 	panelWidth:380,
				multiple: true,
				multiline:true,
				editable:false,
				idField: 'id',
				textField: 'name',
				url: '../activity/drActivityByCouponTypeList.do?scene='+scene+'&types='+(type==0?"1,2,3,4,5":type),
				method: 'get',
				columns: [[
					{field:'id',checkbox:true},
					{field:'name',title:'券名称',width:80},
					{field:'enableAmount',title:'启用金额',width:50},
					{field:'productDeadline',title:'产品期限',width:50},
				]],
				fitColumns: true 
           });
        }
	});
	
	//根据优惠券类型获取优惠券
	$('#addCouponType').combobox({
        onSelect:function(record){
           var type = $('#addCouponType').combobox('getValue');
           var scene = $('#addScene').combobox('getValue');
           $("#addCoupons").combogrid("setValue",'');//清空优惠券
           $("#addCoupons").combogrid({
           	 	panelWidth:380,
				multiple: true,
				multiline:true,
				editable:false,
				idField: 'id',
				textField: 'name',
				url: '../activity/drActivityByCouponTypeList.do?scene='+scene+'&types='+(type==0?"1,2,3,4,5":type),
				method: 'get',
				columns: [[
					{field:'id',checkbox:true},
					{field:'name',title:'券名称',width:80},
					{field:'enableAmount',title:'启用金额',width:50},
					{field:'productDeadline',title:'产品期限',width:50},
				]],
				fitColumns: true 
           });
        }
     });
	
	//添加规则
	function addIssuedRules(){
		var validate = $("#addIssuedRulesForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/activity/addIssuedRules.do",
            type: 'POST',
            data:$("#addIssuedRulesForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#couponsIssuedRulesList").datagrid("reload");
					$("#addIssuedRulesDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
        });
        return false; // 阻止表单自动提交事件
	}
	
	//跳转修改页面
	function updateIssuedRulesWindow(index){
		$('#couponsIssuedRulesList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#couponsIssuedRulesList').datagrid('getSelected'); 
		$("#updateIssuedRulesWindow").window({
			title:"修改规则",
			href:"../activity/toCouponsIssuedRulesModify.do?id="+row.id
		});
		$("#updateIssuedRulesWindow").window("open");
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.status =="0"){
	    	return '<a href="#" class="btn l-btn l-btn-small" onclick="updateIssuedRulesStatus('+index+')">生效</a>'+"   "
	    	+'<a href="#" class="btn l-btn l-btn-small" onclick="updateIssuedRulesWindow('+index+')">修改信息</a>';  
		}else if(row.status=="1" && row.type=="2"){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateIssuedRulesStatus('+index+')">关闭</a>'+"   "
			+'<a href="#" class="btn l-btn l-btn-small" onclick="updateIssuedRulesWindow('+index+')">修改</a>'+"   "
			+'<a href="#" class="btn l-btn l-btn-small" onclick="importManuallySend('+index+')">导入</a>'+"   "
			+'<a href="#" class="btn l-btn l-btn-small" onclick="showManuallySend('+index+')">查看并发送</a>';
		}else if(row.status=="1" && row.type!="2"){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateIssuedRulesStatus('+index+')">关闭</a>'+"   "
			+'<a href="#" class="btn l-btn l-btn-small" onclick="updateIssuedRulesWindow('+index+')">修改信息</a>';
		}
		return '';
	} 
	
	//更改是否显示
	function updateIssuedRulesStatus(index){  
		$('#couponsIssuedRulesList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#couponsIssuedRulesList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定更改吗？', function(r){
			if(r){
				var url = "../activity/updateIssuedRules.do?sid="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#couponsIssuedRulesList').datagrid("reload");
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
	
	//导入文件窗口
	function importManuallySend(index){
		$("#uploadFileDialogForm").form("reset");
		$('#couponsIssuedRulesList').datagrid('selectRow',index);
		var row = $('#couponsIssuedRulesList').datagrid('getSelected');
		$("#uploadFileCoupons").val(row.coupons);
		$("#uploadCouponNames").val(row.couponNames);
		$("#uploadName").val(row.name);
		$("#uploadFileCirId").val(row.id);
		$("#uploadFileDialog").dialog("open");
		$("#uploadMemberInfo").attr("onclick","uploadMemberInfo("+index+")");
	}
	
	//验证文件
	function verifyFile(obj) {
		if (obj.value == "") {  
			$.messager.alert("提示信息","请上传文件");
	        return false;  
	    } 
        if (! /\.(xls)$/.test(obj.value)) {  
        	$.messager.alert("提示信息","文件类型必须是.xls");
            obj.value = "";  
            return false;  
        }
    	return true;  
	}
	
	//关闭添加渠道窗口
	function closeDialog(){  
		$("#couponsIssuedRulesList").datagrid("reload");
		$("#uploadFileDialog").dialog("close");
		$("#addIssuedRulesDialog").dialog("close");
	}
	
	//上传用户并发送优惠券
	function uploadMemberInfo(index){
		$("#uploadFileDialogForm").ajaxSubmit({
			url:"${apppath}/activity/uploadMemberInfo.do",
			type:"POST",
			data:$("#uploadFileDialogForm").serialize(),
			success: function(result){
				if(result.success){
					$.messager.confirm('操作提示', result.msg+'\r\n确定查看导入用户并发送优惠券', function(r){
						if(r){
							$("#uploadFileDialog").dialog("close");
							showManuallySend(index);
						}
					});
					return false;
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		});
	}

	//查看导入用户
	function showManuallySend(index){
		$("#drManuallySendCouponForm").form("reset");
		$("#ManuallySendCouponListDialog").dialog("open");
		$('#couponsIssuedRulesList').datagrid('selectRow',index);
		var row = $('#couponsIssuedRulesList').datagrid('getSelected');
		$("#searcheCirId").val(row.id);
		$("#issuedRulesName").val(row.name);
		$("#issuedRulesMessage").val(row.message);
		var curr_time = new Date();
	    var strDate = curr_time.getFullYear()+"-";
	    strDate += curr_time.getMonth()+1+"-";
	    strDate += curr_time.getDate()+"-";
	    strDate += curr_time.getHours()+":";
	    strDate += curr_time.getMinutes()+":";
	    strDate += curr_time.getSeconds();
	   	$("#searchAddTime").datetimebox("setValue", strDate); 
	   	
	   	$("#drManuallySendCouponLis").datagrid({
		    url: "${apppath}/activity/queryManuallySendCouponLis.do",
		    queryParams:{
		        cirId:$("#searcheCirId").val(),
		        addTime:$("#searchAddTime").datetimebox('getValue')
		    }
		});
	}
	
	function searcheManuallySendCouponBtn(){
		$("#drManuallySendCouponLis").datagrid({
		    url: "${apppath}/activity/queryManuallySendCouponLis.do",
		    queryParams:{
		        cirId:$("#searcheCirId").val(),
		        addTime:$("#searchAddTime").datetimebox('getValue')
		    }
		});
	}
	
	//发券
	function sendCouponBtn(){
		var validate = $("#drManuallySendCouponForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/activity/manuallySendCoupon.do",
            type: 'POST',
            data:$("#drManuallySendCouponForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					searcheManuallySendCouponBtn();
				}else{
					$.messager.alert("提示信息",result.errorMsg);
					searcheManuallySendCouponBtn();
				}
			}	
        });
        return false; // 阻止表单自动提交事件
	}
	
	//修改显示状态信息
	function formatStatus(value,row,index){
		if(row.status=="0"){
			return '新建';
		}else if(row.status=="1"){
			return '生效';
		}else{
			return '已失效';
		}
	}
	//修改显示状态信息
	function formatIsCps(value,row,index){
		if(row.isCps=="0"){
			return '否';
		}else if(row.isCps=="1"){
			return '是';
		}else{
			return '全部';
		}
	}
	
	function formatType(value,row,index){
		if(value=="0"){
			return '注册赠送';
		}else if(value=="1"){
			return '投资赠送';
		}else if(value=="2"){
			return '手动发送';
		}else if(value=="3"){
			return '绑卡发送';
		}else if(value=="4"){
			return '邀请好友三重好礼';
		}else if(value=="5"){
			return '感恩活动';
		}else if(value=="7"){
			return '开通存管';
		}else if(value=="8"){
			return '待流失唤回';
		}else if(value=="9"){
			return '已流失唤回';
		}else{
			return '';
		}
	}
	
	function formatSendStatus(value,row,index){
		if(row.status=="1"){
			return '未发放';
		}else{
			return '已发放';
		}
	}
</script>
</body>
</html>