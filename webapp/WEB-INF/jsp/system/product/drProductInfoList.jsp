﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
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
	<table id="drProductInfoList" title="产品管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../product/drProductInfoList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drProductInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'isSid'" hidden="true"></th>	    	
	    	<th data-options="field:'renewal'" hidden="true">判断是否续发</th>	
	    	<th data-options="field:'bespoke'" hidden="true">取消预约</th>		    	
	    	<th data-options="field:'code'" width="10%">产品编号</th>
	        <th data-options="field:'simpleName'" width="15%">产品名称</th>
	        <th data-options="field:'type'" width="5%" formatter="formatType">产品类型</th>
	        <th data-options="field:'productMarking'" width="5%" formatter="formatProductMarkingType">产品标识</th>
	       	<th data-options="field:'rate'" width="5%">产品利率</th>
	       	<th data-options="field:'activityRate'" width="5%">活动利率</th>
	       	<th data-options="field:'deadline'" width="5%">期限(天)</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">产品金额(元)</th>
	       	<th data-options="field:'alreadyRaiseAmount'" width="10%" styler="styleColor" formatter="formatAmount">已募集金额(元)</th>
	       	<th data-options="field:'borrowing_type'" width="8%"formatter="formatBorrwoingType">债权类型</th>
	       	<th data-options="field:'prizeName'" width="10%">奖品名称</th>
	        <th data-options="field:'statusName'" width="5%">产品状态</th>
	        <th data-options="field:'status'" hidden="true">状态</th>
	        <th data-options="field:'isShowName'" formatter="formatOperIsShow">是否显示</th>
	        <th data-options="field:'isShow'" hidden="true">是否显示</th>
	        <th data-options="field:'isHot'" formatter="formaterOperisHot">是否热推</th>
	        <th data-options="field:'fid'" formatter="formaterOperisSecondaryProducts">是否续发</th>
	       	<th data-options="field:'startDate'" width="10%" formatter="formatDateBoxFull">上架日期</th>
	       	<th data-options="field:'repayTypeName'" width="8%" >还款方式</th>
	        <th data-options="field:'surplusDay'" width="6%">剩余到期天数</th>
	        <th data-options="field:'remainRaiseDay'" width="6%" formatter="formatRemainDays">剩余募集天数</th>
			<th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInfoTools" style="padding:5px;height:750">
	  	<form id="drProductInfoForm">
	  		产品编号:<input id="searchDrProductInfoCode" name="code" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品简称:<input id="searchDrProductInfoSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
	  						<option value=''>全部</option>
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
								<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
	  		产品状态: <select  id="searchDrProductInfoStatus" name="status" style="width:100px;" class="easyui-combobox">
						<option value=''>全部</option>
						<c:forEach items="${status }" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
	           		</select>
	    	<a id="searchDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoBtn()">产品新增</a>	
	    	<a id="exportDrProductInfo" href="javascript:exportDrProductInfo();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
	<div id="updateDrProductInfoDialog" class="easyui-dialog" title="产品修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateDrProductInfoBtn'" style="width:700px;height:400px;padding:5px;">
		<form id="updateDrProductInfoForm" enctype="multipart/form-data" >
			<input type="hidden" id="drProductId"  name="id"/>	
			<table align="center" id = "updateDrProductInfoDialogTab">
				<tr>
					<td align="left">产品编号：</td>
					<td>
						<input id="drProductCode" name="code" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
					<td align="left">产品全称：</td>
					<td>
						<input id="drProductFullName" name="fullName" type="text" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">起投金额：</td>
					<td>
						<input id="drProductLeastaAmount" name="leastaAmount" type="text" class="easyui-numberbox" data-options="required:true,min:0,max:1000000"/>元
					</td>
					
					<td align="left"  id="raiseDeadline1">募集期限：</td>
					<td  >
						<input id="raiseDeadline" name="raiseDeadline" type="text"  class="easyui-textbox" data-options="editable:false"/>
					</td>
					<!--td align="left">成立时间：</td>
					<td>
						<input id="drProductEstablish" name="establish" type="text" class="easyui-datebox" data-options="editable:false"/>预估
					</td-->
				</td>
				</tr>
				<tr>
					<td align="left">承兑方概况：</td>
					<td colspan="3">
						<input id="drProductIntroduce" name="introduce" class="easyui-textbox" style="height:120px;width: 415px;" data-options="multiline:true"/>
					</td>
				</tr>
				<tr>	
					<td align="left">债务人概况：</td>
					<td colspan="3">
						<input id="drProductBorrower" name="borrower" class="easyui-textbox" style="height:120px;width: 415px;" data-options="multiline:true"/>
					</td>
				</tr>
				<tr>		
					<td align="left">还款来源：</td>
					<td colspan="3">
						<input id="drProductRepaySource" name="repaySource" class="easyui-textbox" style="height:120px;width: 415px;" data-options="multiline:true"/>
					</td>
				</tr>
				<tr>		
					<td align="left">风险控制：</td>
					<td colspan="3">
						<input id="drProductWindMeasure" name="windMeasure" class="easyui-textbox" style="height:120px;width: 415px;" data-options="multiline:true"/>
					</td>
				</tr>
				
			</table>
					<div title="产品图片" class="easyui-panel" style="width:100%;height:auto;padding:10px;"data-options="collapsible:true,region:'center',tools:'#productPicAddBtn'">
					<table id="addProductPicTable" align="center" cellspacing="1" cellpadding="1" style="width: auto;">
					<tr>
					<th>图片名称</th>
					<th>选择图片</th>
					<th>是否显示</th>
					<th>显示顺序(先显示数字小的)</th>
					<th>操作</th>
					</tr>
					</table>	
					</div>
			<div id="productPicAddBtn">
			<a href="javascript:void(0)" class="icon-add" onclick="addProductPic();"></a>
			</div>
			<!-- 显示图片 -->
			<div id="updProductPicShow" class="easyui-dialog" title="查看图片" style="text-align:center;height:auto;width:625px;padding:10px;;top:40%" 
			data-options="closed:true,modal:true,minimizable:false,resizable:false,maximizable:false">
			<div id="imgbig">
				<img id="imglook" src="" width="600px" height="400px" />
			</div>
			</div>
		</form>
		<div id="updateDrProductInfoBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateDrProductInfoForElse()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	
	<div id="addDrProductInfoStartDateDialog" class="easyui-dialog" title="上架提示"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductStartDateDialogBtn'" style="width:300px;height:200px;padding:5px;">
		<form id="addDrProductInfoStartDateForm">
			<input type="hidden" id="drProductStartDateId"  name="id"/>
			<table align="center">
				<tr>
					<td align="left">上架时间：</td>
					<td>
						<input id="addStartDate" name="startDate" type="text" class="easyui-datetimebox" data-options="required:true"/>
					</td>
				</tr>

			</table>
		</form>
		<div id="addDrProductStartDateDialogBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addDrProductStartDate()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDrProductStartDateDialog()">取消</a>
		</div>
	</div>
<script type="text/javascript">
var picIndex = 0;
//添加图片
function addProductPic(){
	var tr = "<tr><td>"+
		"<input name=\"drProductPic["+picIndex+"].name\" type='text' style='width:100px;' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"+
		"</td>" +
		"<td>" +
		"<input name=\"drProductPic["+picIndex+"].bigUrl\"  type='hidden'/>"+
		"<input type='file' name='productFiles' onchange=\"updateVerifyImage(this)\" style='width:100px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>"+
		"</td>" +
		"<td>"+
			"<input class='check-box checked' type='checkbox' name=\"drProductPic["+picIndex+"].isShow\" value='1' checked='checked' onclick='changeOptions(this)'/>"+
		"</td>"+
   	   	"<td><input name=\"drProductPic["+picIndex+"].showSort\" type='text' class='easyui-numberbox' style='width: 100px' data-options='min:0,max:20,required:true'/>" +
   	   	"</td>"+
	   		"<td><div class='btn-group'>" +
	   		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-search' onclick=\"addPreviewImage(this)\">"+
	   		"查看</a>"+" "+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'> " +
	   		"删除</a></div></td></tr>"; 
	$("#addProductPicTable").append(tr);
	$('.easyui-textbox').textbox({    
	}); 
	$('.easyui-numberbox').numberbox({    
	}); 
	$('.easyui-linkbutton').linkbutton({    
	});
	picIndex ++;
}
function formatType(value,row,index){
	if(value==1){
		return "新手标"
	}else if(value==2){
		return "票据安选"
	}else if(value==3){
		return "存管新手标"
	}else if(value==4){
		return "活动产品"
	}else if(value==5){
		return "体验标"
	}else{
		return "V标专属"
	}
}
//验证图片
function updateVerifyImage(ths) {
	if (ths.value == "") {  
		$.messager.alert("提示信息","请上传图片");
        return false;  
    } 
    if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(ths.value)) {  
    	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
        ths.value = "";  
        return false;  
    }
    $(ths).prev('input').val('use');
    $(ths).parents('tr').find('a').eq(0).data("value", "undefined");
	return true;  
}

	//重置按钮
	$('#resetDrProductInfo').click(function(){
		$("#drProductInfoForm").form("reset");
		$("#drProductInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrProductInfo').click(function(){
 		$('#drProductInfoList').datagrid({
			queryParams: {
				code: $('#searchDrProductInfoCode').val(),
				simpleName: $('#searchDrProductInfoSimpleName').val(),
				status: $('#searchDrProductInfoStatus').combobox('getValue'),
				repayType:$('#repayType').combobox('getValue')
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.status=="1" || row.status=="3"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowProductInfoBtn('+index+')">查看</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoBtn('+index+')">修改</a>';
		}else if(row.status=="2"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowProductInfoBtn('+index+')">查看</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoBtn('+index+')">修改</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductStartDateBtn('+index+')">上架</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductDeleteBtn('+index+')">作废</a>';
		}else{
			var oper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoForElseBtn('+index+')">修改</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="toShowProductInfoBtn('+index+')">查看</a>';
					
// 			if(0<row.surplusDay && row.surplusDay<=5 && row.isSid == 1 && row.status=="8"){
// 				if(null == row.renewal){
// 					oper +="   "+'<a href="#" class="btn l-btn l-btn-small" onclick="addProductInfoRenewalBtn('+index+')">续发</a>';
// 				}
// 			}
			if(row.bespoke == 1 && row.status=="5"){
				oper +="   "+'<a href="#" class="btn l-btn l-btn-small" onclick="productCancelBespokeBtn('+index+')">取消预约</a>';
			}
			return oper;
		}
	} 
	
	//跳转到产品添加页面
	function addDrProductInfoBtn(){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "产品新增",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/product/toAddDrProductInfo.do' ></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	//跳转到产品修改页面
	function updateDrProductInfoBtn(index){
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
	    $.ajax({
			url: "${apppath}/product/isOperate.do?id="+row.id+"&operate=update",
			dataType:"json",
			success:function(result){
				if(result.success){
					var mainTab = parent.$('#main-center');
					var detailTab = {
							title : "产品修改",
							content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/product/toUpdateProductInfo.do?id="+row.id+"'></iframe>",
							closable : true
					};
					mainTab.tabs("add",detailTab);
				}else{
					$.messager.alert("提示信息",result.errorMsg,function(){
						$('#drProductInfoList').datagrid('reload');
					});
				}
			}
 		});
	}
	
	//跳转到产品续发页面
	function addProductInfoRenewalBtn(index){
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
	    $.ajax({
			url: "${apppath}/product/validatorAddDrProductInfoRenewal.do?id="+row.id,
			dataType:"json",
			success:function(result){
				if(result.success){
					var mainTab = parent.$('#main-center');
					var detailTab = {
							title : "产品续发",
							content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/product/toAddProductInfoRenewal.do?id="+row.id+"'></iframe>",
							closable : true
					};
					mainTab.tabs("add",detailTab);
				}else{
					$.messager.alert("提示信息",result.errorMsg,function(){
						$('#drProductInfoList').datagrid('reload');
					});
				}
			}
 		});
	}
	
		//上架操作
	function updateDrProductStartDateBtn(index){
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
	    $('#drProductStartDateId').val(row.id);
	    $("#addStartDate").datetimebox("setValue",getNowFormatDate());  
		$("#addDrProductInfoStartDateDialog").dialog("open");
	}
	
	//上架操作
	function addDrProductStartDate(index){
		var validate = $("#addDrProductInfoStartDateForm").form("validate");
		if(!validate){
			return false;
		}
		var url = "${apppath}/product/updateDrProductStatus.do?id="+$('#drProductStartDateId').val()+"&startDate="+$('#addStartDate').datetimebox('getValue');
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$.messager.alert("操作提示", result.msg);
					$('#drProductInfoList').datagrid('reload');
					$("#addDrProductInfoStartDateDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
					$('#drProductInfoList').datagrid('reload');
					$("#addDrProductInfoStartDateDialog").dialog("close");
				}
			}
	  	});
	}
	
	//取消预约操作
	function productCancelBespokeBtn(index){
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
		$.messager.confirm("取消预约提示", "您取消上架时间为 "+formatDateBoxFull(row.startDate), function(ensure){
			if(ensure){
				var url = "${apppath}/product/updateDrProductCancelBespoke.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#drProductInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$('#drProductInfoList').datagrid('reload');
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	}
	
	//作废操作
	function updateDrProductDeleteBtn(index){
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
		$.messager.confirm("操作提示", "是否作废这个产品？", function(ensure){
			if(ensure){
				var url = "${apppath}/product/updateDrProductDelete.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#drProductInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$('#drProductInfoList').datagrid('reload');
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	}
	
	//跳转到产品显示页面
	function toShowProductInfoBtn(index){
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "产品显示",
				content:"<iframe scrolling='yes' width='100%' height='100%' frameborder='0' src='${apppath}/product/toShowDrProductInfo.do?id="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	//更改是否显示
	function updateDrProductIsShowBtn(index){  
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定更改吗？', function(r){
			if(r){
				var url = "../product/updateDrProductIsShow.do?id="+row.id+"&isShow="+row.isShow;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(){
						$('#drProductInfoList').datagrid('reload');
						$.messager.alert("操作提示", "操作成功");
					}
			  	});
			}
		});
	} 
	//更改是否热推
	function updateDrProductIsHotBtn(index){
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定更改吗？', function(r){
			if(r){
				var url = "../product/updateDrProductIsHot.do?id="+row.id+"&isHot="+row.isHot;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(){
						$('#drProductInfoList').datagrid('reload');
						$.messager.alert("操作提示", "操作成功");
					}
			  	});
			}
		});
	}
	
	//打开dialog
	function updateDrProductInfoForElseBtn(index){  
		$('#drProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductInfoList').datagrid('getSelected'); 
		var url = "${apppath}/product/queryDrProductInfo.do?id="+row.id;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#drProductId").val(result.map.drProductInfo.id);
					$("#drProductCode").textbox('setValue',result.map.drProductInfo.code);
					$("#drProductFullName").textbox('setValue',result.map.drProductInfo.fullName);
					$("#drProductLeastaAmount").numberbox('setValue',result.map.drProductInfo.leastaAmount);
					//$("#drProductEstablish").datebox('setValue',result.map.establish);
					$("#raiseDeadline").textbox('setValue',result.map.drProductInfo.raiseDeadline);
					$("#drProductIntroduce").textbox('setValue',result.map.drProductInfo.introduce);
					$("#drProductBorrower").textbox('setValue',result.map.drProductInfo.borrower);
					$("#drProductRepaySource").textbox('setValue',result.map.drProductInfo.repaySource);
					$("#drProductWindMeasure").textbox('setValue',result.map.drProductInfo.windMeasure);
					
					picIndex = result.map.drProductInfo.drProductPic.length;
					var trslength= $("#addProductPicTable").find("tr").length;
					for(var i=trslength;i>=1;i--) 
					{
					    $("#addProductPicTable").find("tr").eq(i).remove();
					}
					for (var i = 0; i < result.map.drProductInfo.drProductPic.length; i++) {
						var pic = result.map.drProductInfo.drProductPic[i];
						var bigUrl = pic.bigUrl;
						var picName = pic.name;
						var picId = pic.id;
						var isShow = pic.isShow;
						var showSort = pic.showSort;
						var index = i;
						picInit(index,picId,picName,bigUrl,isShow,showSort);
					}
					if(result.map.drProductInfo.status == 5 && result.map.drProductInfo.type != 1) {						
						$("#raiseDeadline").textbox({'editable':true});
					}else{			
						$("#raiseDeadline").textbox({'editable':false});
					}
					$("#updateDrProductInfoDialog").dialog("open");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
	//修改时显示图片
	function picInit(picIndex,picId,picName,bigUrl,isShow,showSort){
		var tr = "<tr><td>"+
			"<input name=\"drProductPic["+picIndex+"].name\" value=\""+picName+"\"  type='text' style='width:100px;' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"+
			"</td>" +
			"<input name=\"drProductPic["+picIndex+"].id\" value=\""+picId+"\"  type='hidden'/>"+
			"<td>" +
			"<input name=\"drProductPic["+picIndex+"].bigUrl\" value=\""+bigUrl+"\"  type='hidden'/>"+
			"<input type='file' name='productFiles' onchange=\"updateVerifyImage(this)\" style='width:100px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>"+
			"</td>" +
			"<td>"+
				"<input class='check-box checked' type='checkbox' name=\"drProductPic["+picIndex+"].isShow\" value=\""+isShow+"\" onclick='changeOptions(this)'/>"+
			"</td>"+
	   	   	"<td><input name=\"drProductPic["+picIndex+"].showSort\" value =\""+showSort+"\" type='text' class='easyui-numberbox' style='width: 100px' data-options='min:0,max:20,required:true'/>" +
	   	   	"</td>"+
		   		"<td><div class='btn-group'>" +
		   		"<a href='javascript:void(0)' data-value=\""+bigUrl+"\" class='easyui-linkbutton' iconCls='icon-search' onclick=\"updatePreviewImage(this)\">"+
		   		"查看</a>"+" "+"<a href='javascript:void(0)' data-value=\""+picId+"\" class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'> " +
		   		"删除</a></div></td></tr>"; 
		$("#addProductPicTable").append(tr);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-numberbox').numberbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		});
	}
	
	//图片预览
	function updatePreviewImage(ths) {
		if(typeof($(ths).data("value")) == "undefined" || $(ths).data("value") == "undefined"){
			var dFile = $(ths).parents('tr').find('input[type="file"]');
		    if (dFile.val() == "" ) {
		        $.messager.alert("提示信息","请上传图片");
		        return false;
		    }
			var type = dFile.val().substring(dFile.val().lastIndexOf(".") + 1, dFile.val().length).toLowerCase();
		    if (type != "jpg" && type != "bmp" && type != "gif" && type != "png") {
		        $.messager.alert("提示信息","请上传正确的图片格式");
		        return false;
		    }
	        if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    
	      	{    
				dFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("imglook");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
	      	}else{
				$("#imglook").attr("src",window.URL.createObjectURL(dFile.prop('files')[0]));
	      	}  
			$('#updProductPicShow').dialog('open');
			return true;
		}else{
			$("#imglook").attr("src",$(ths).data("value"));
			$('#updProductPicShow').dialog('open');
		}
	}	
	function delPic(obj){
		var id= $(obj).data("value");
		console.log(obj);
		if(typeof(id) == "undefined"){
			$(obj).parent().parent().parent().remove();
		}else{
			$.ajax({
				url: '${apppath}/product/deleteDrProductPic.do?id='+id,
				dataType:'json',
				success:function(result){
					if(result.success){
						$(obj).parent().parent().parent().remove();
					}else{
						$.messager.alert("提示信息",resultJson.errorMsg);
					}
				}
	 		});
		}
	}
	
	//上架后的修改[需求比较变态-在心情糟糕的情况下完成的，估计有BUG]
	function updateDrProductInfoForElse(){  
		var validate = $("#updateDrProductInfoForm").form("validate");
		if(!validate){
			return false;
		}
		$("#updateDrProductInfoForm").ajaxSubmit({
          	url: "${apppath}/product/updateDrProductInfoForElse.do",
            type: 'POST',
            data:$("#updateDrProductInfoForm").serialize(),  
            success:function(result){
            	var resultJson = jQuery.parseJSON(result);
				var resultJson = eval(resultJson);
				if(resultJson.success){
					$.messager.alert("提示信息",resultJson.msg);
					$("#drProductInfoList").datagrid("reload");
					$("#updateDrProductInfoDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",resultJson.errorMsg);
				}
			}
        });
	}
	
	//添加状态链接
	function formatOperIsShow(value,row,index){
		if(row.isShow == "1"){
			return '<a href="#" onclick="updateDrProductIsShowBtn('+index+')">是</a>';
		}else{
			return '<a href="#" onclick="updateDrProductIsShowBtn('+index+')">否</a>';
		}
	}
	//添加热推状态
	function formaterOperisHot(value,row,index){
		if(row.isHot == "1"){
			return '<a href="#" onclick="updateDrProductIsHotBtn('+index+')">是</a>';
		}else{
			return '<a href="#" onclick="updateDrProductIsHotBtn('+index+')">否</a>';
		}
	}
	
	//是否是续发产品
	function formaterOperisSecondaryProducts(value,row,index){
		if(row.fid==null || row.fid=='undefined'){
			return '否';
		}else{
			return '是';
		}
	}
	
	//产品标识
	function formatProductMarkingType(value,row,index){
		if(value == '1'){
			return '秒杀产品';
		}else{
			return '';
		}
	}
	
	//关闭Dialog
	function closeDialog(){  
		$("#drProductInfoList").datagrid("reload");
		$("#updateDrProductInfoDialog").dialog("close");
	}
	
	//关闭Dialog
	function closeDrProductStartDateDialog(){  
		$("#drProductInfoList").datagrid("reload");
		$("#addDrProductInfoStartDateDialog").dialog("close");
	}
	
	//导出
	function exportDrProductInfo(){
		window.location.href="../product/exportDrProductInfo.do?code="+$('#searchDrProductInfoCode').val()+
						"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductInfoSimpleName').val()))+
						"&status="+$('#searchDrProductInfoStatus').combobox('getValue')+"&repayType="+$('#repayType').combobox('getValue')+
						"&repayType="+$('#repayType').combobox('getValue');
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
	function formatRemainDays(value,row,index){
		if(row.status=='5'){
			if(value=='1'){
				return value+'<img src="../images/bell.png"/>';
			}else
				return value;
		}else
			return '';
	}
	
	//formatter债权类型
	function formatBorrwoingType(val,row,index){
		if(val == '2'){
			return '个人贷款';
		}else{
			return '企业贷款';
		}
	}
</script>
</body>
</html>

