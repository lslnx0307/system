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
	<table id="product"  style="height:99%;width:99.9%"></table>
	<div id="productbtn" style="padding:5px;height:750">
		<form id="selproduct">
			产品编号:<input id="code" name="code" class="easyui-textbox"  size="15" style="width:100px"/>
			项目名称:<input id="fullName" name="fullName" class="easyui-textbox"  size="15" style="width:100px"/>
			借款方:<input id="loanName" name="loanName" class="easyui-textbox"  size="15" style="width:100px"/>
			项目合同:<input id="contractCode" name="contractCode" class="easyui-textbox"  size="15" style="width:100px"/>
			满标日期:<input id="startfullDate" name="startfullDate" class="easyui-datebox"  size="15" style="width:100px"/>至
				  <input id="endfullDate" name="endfullDate" class="easyui-datebox"  size="15" style="width:100px"/>
			付款日期:<input id="startloanTime" name="startloanTime" class="easyui-datebox"  size="15" style="width:100px"/>至
				  <input id="endloanTime" name="endloanTime" class="easyui-datebox"  size="15" style="width:100px"/>
			预计还款日期:<input id="startexpireDate" name="startexpireDate" class="easyui-datebox"  size="15" style="width:100px"/>至
				  <input id="endexpireDate" name="endexpireDate" class="easyui-datebox"  size="15" style="width:100px"/>
			还款状态：<select  id="isReimbursement" name="isReimbursement" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="9">已还款</option>
				 	<option value="0">未还款</option>
	           	 </select>
            <br/><!-- 服务费收款日期: --><input id="startserviceTime" name="startserviceTime" size="15" hidden="true"/><!-- 至 -->
			<input id="endserviceTime" name="endserviceTime"  size="15" style="width:100px;" hidden="true"/>
			<!-- 服务费收取状态： --><select  id="isService" name="isService" style="width:100px;" hidden="true">
				 	<option value=''>全部</option>
				 	<option value="1">已收</option>
				 	<option value="2">未收</option>
	           	   </select>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selproduct()">查询</a>
	       	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetproduct()">重置</a>
	       	<c:if test="${menuType==1}">
	       	 	<a id="exportproduct" href="javascript:exportproduct();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	        </c:if>        
		</form>
	</div>
<script type="text/javascript">
	var product = $('#product');
	product.datagrid({
		url : "../product/getProductInfoDetail.do",
		title : '项目数据情况',
		fitColumns : true,
		pagination : true,
		checkbox:true,
		pageSize:25,
		pageList:[25,50,100],
		autoRowHeight : false,
		toolbar:"#productbtn", 
		fit:true,
		columns : [ [ 
			{
			field : 'code',
			title : '项目产品编号',
			align : "center"
			},{
			field : 'contractCode',
			title : '项目合同编号',
			align : "center"
			},{
			field : 'fullName',
			title : '项目名称',
			align : "center"
			},{
			field : 'loanName',
			title : '借款方',
			align : "center"
			},{
			field : 'loanAmount',
			title : '授信额度',
			align : "center"
			},{
			field : 'amount',
			title : '项目借款金额',
			align : "center"
			},{
			field : 'rate',
			title : '年化利率',
			align : "center"
			},{
			field : 'activityRate',
			title : '活动利率',
			align : "center"
			},{
			field : 'deadline',
			title : '期限',
			align : "center"
			},{
			field : 'basicprofit',
			title : '基本利息',
			align : "center"
			},{
			field : 'platformInterest',
			title : '活动利息',
			align : "center"
			},{
			field : 'interestRateCoupon',
			title : '加息券利息',
			align : "center"
			},{
			field : 'interestDoubleCoupons',
			title : '翻倍券利息',
			align : "center"
			},{
			field : 'interestSubsidy',
			title : '未满标贴息',
			align : "center"
			},{
			field : 'principalInterest',
			title : '应收本息',
			align : "center"
			},{
			field : 'fullDate',
			title : '满标日期',
			align : "center"
			},{
			field : 'establish',
			title : '成立日期',
			align : "center"
			},{
			field : 'loanTime',
			title : '放款日期',
			align : "center"
			},{
			field : 'lendingAmount',
			title : '放款金额',
			align : "center"
			},{
			field : 'factTime',
			title : '实际回款日',
			align : "center"
			},{
			field : 'expireDate',
			title : '预计收款日',
			align : "center"
			},{
			field : 'amount',
			title : '还款本金',
			align : "center"
			},{
			field : 'basicprofit',
			title : '还款利息',
			align : "center"
			},{
			field : 'repayType',
			title : '还款方式',
			align : "center"
			},{
			field : 'projectNo',
			title : '通道类型',
			align : "center",
			formatter:function(value,row,index){
				if(value!=null && value!=''){
					return "存管";
				}else{
					return "金运通";
				}				
			}
			},{
			field : 'isReimbursement',
			title : '已还/未还',
			align : "center"
			}/*,{
			field : 'a',
			title : '预计服务费',
			align : "center",
			formatter:function(value,row,index){
				 if(row.establish<'2017-10-01'){ 
					return serviceAmountCount(row)-vouchAmount(row);
				 }else{
					return serviceAmount(row);
				} 
			}
			} ,{
			field : 'serviceTime',
			title : '实际收到日期',
			align : "center"
			},{
			field : 'serviceCharge',
			title : '已收服务费',
			align : "center"
			},{
			field : 'b',
			title : '已收/未收',
			align : "center",
			formatter:function(value,row,index){
				if(row.serviceCharge!=null && row.serviceCharge!=''){
					return "已收";
				}else{
					return "未收";
				}				
			}
			},{
			field : 'c',
			title : '是否首笔',
			align : "center",
			formatter:function(value,row,index){
				if(row.countAmount<=row.sumamount){//首笔
					return "是";
				}else{
					return "否";
				}				
			}	
			} */
		] ]
	});
	
	//查询按钮
	function selproduct(){
		product.datagrid('reload', {
				code: $('#code').textbox("getValue"),
				fullName:$('#fullName').textbox("getValue"),
				loanName: $('#loanName').textbox("getValue"),
				contractCode: $('#contractCode').textbox("getValue"),
				startfullDate: $('#startfullDate').datebox("getValue"),
				endfullDate: $('#endfullDate').datebox("getValue"),
				startloanTime: $('#startloanTime').datebox("getValue"),
				endloanTime: $('#endloanTime').datebox("getValue"),
				startexpireDate: $('#startexpireDate').datebox("getValue"),
				endexpireDate: $('#endexpireDate').datebox("getValue"),
				isReimbursement: $('#isReimbursement').combobox("getValue"),
				startserviceTime:$('#startserviceTime').val(),
				endserviceTime:$('#endserviceTime').val(),
				isService: $('#isService').val()
           	});
	}
	
	function exportproduct(){
		var map = {
				code: $('#code').textbox("getValue"),
				fullName:$('#fullName').textbox("getValue"),
				loanName: $('#loanName').textbox("getValue"),
				contractCode: $('#contractCode').textbox("getValue"),
				startfullDate: $('#startfullDate').datebox("getValue"),
				endfullDate: $('#endfullDate').datebox("getValue"),
				startloanTime: $('#startloanTime').datebox("getValue"),
				endloanTime: $('#endloanTime').datebox("getValue"),
				startexpireDate: $('#startexpireDate').datebox("getValue"),
				endexpireDate: $('#endexpireDate').datebox("getValue"),
				isReimbursement: $('#isReimbursement').combobox("getValue"),
				startserviceTime:$('#startserviceTime').val(),
				endserviceTime:$('#endserviceTime').val(),
				isService: $('#isService').val()
		};
		window.location.href="../product/exportProductInfoDetail.do?"+$.param(map);
	}
	
	function resetproduct(){
		$("#selproduct").form("reset");
		selproduct();
	}
</script>
</body>
</html>

