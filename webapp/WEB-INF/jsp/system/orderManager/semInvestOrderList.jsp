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
	<table id="drSemInvestOrderList" title="投资订单 "
	style="height:99%;">
		<thead>
	    <tr>
	        <th data-options="field:'recommCodes'" width="5%" >推荐号</th>
	        <th data-options="field:'realname'" width="4%">用户姓名</th>
	        <th data-options="field:'mobilePhone'" width="5%" >手机号码</th>
	        <th data-options="field:'fullName'" width="8%">产品名称</th>
	        <th data-options="field:'deadline'" width="5%">项目周期(天)</th>
	        <th data-options="field:'investTime'" width="8%">订单时间</th>
	        <th data-options="field:'amount'" width="5%" styler="styleColor" formatter="formatAmount">订单金额</th>
	        <th data-options="field:'interest'" width="5%" styler="styleColor" formatter="formatAmount">预计收益</th>
	        <th data-options="field:'couponamt'" width="5%" styler="styleColor" formatter="formatAmount">优惠券返利</th>
	        <th data-options="field:'joinType'" width="4%" formatter="formatJoinType">投资终端</th>
	       	<th data-options="field:'repayType'" width="8%" formatter="formatRepayType">还款方式</th>
	        <th data-options="field:'channelName'" width="5%">注册渠道</th>
	        <th data-options="field:'regfrom'" width="5%" formatter="formatRegfrom">注册终端</th>
	        <th data-options="field:'regdate'" width="8%">注册日期</th>
	        
	        <th data-options="field:'bankName'" width="8%">绑卡银行</th>
	        <th data-options="field:'bankAddTime'" width="8%">绑卡时间</th>
	        
	        <th data-options="field:'keyword'" width="8%">注册关键字</th>
	        <th data-options="field:'inRegSource'" width="8%">进入注册页来源</th>
	        <th data-options="field:'pageUrl'" width="8%">入站链接</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	  		产品名称:<input id="searchSemInvestFullName" name="fullName" class="easyui-textbox" value="" size="30" style="width:100px"/>
	  		用户手机:<input id="searchSemInvestMobilephone" name="mobilephone" class="easyui-textbox"  size="30" style="width:100px"/>
	  		关键字:<input id="searchSemKeyword" name="keyword" class="easyui-textbox"  size="30" style="width:100px"/>
	  		还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
	  						<option value=''>全部</option>
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
								<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
	  		项目周期:<select name="deadline" style="width:150px" class="easyui-combobox" id="searchSemInvestDeadline">
			  			<option value="" selected="selected">全部</option>
			  			<option value="1">1</option>
			  			<option value="7">7</option>
			  			<option value="15">15</option>
			  			<option value="30">30</option>
			  			<option value="35">35</option>
			  			<option value="60">60</option>
			  			<option value="70">70</option>
			  			<option value="90">90</option>
			  			<option value="150">150</option>
			  			<option value="180">180</option>
	  				</select>
	  		订单日期:<input id="searchSemInvestStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  			  <input id="searchSemInvestEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		
  		         注册日期:<input id="searchSemInvestStartRegDate" name="startRegDate" class="easyui-datebox" style="width:100px"/>到
  			  <input id="searchSemInvestEndRegDate" name="endRegDate" class="easyui-datebox" style="width:100px"/>
	  		渠道名称:<select class="easyui-combogrid" id="cid" name="cid" style="width:240px;padding-bottom: 3px;" data-options="
							panelWidth: 240,
							multiple: true,
							multiline:true,
							editable:false,
							idField: 'id',
							textField: 'name',
							url: '../channel/drAllChannelInfoList.do?orders=1',
							method: 'get',
							columns: [[
								{field:'id',checkbox:true},
								{field:'code',title:'渠道号',width:80},
								{field:'name',title:'渠道名称',width:80},
							]],
							fitColumns: true
						">
					</select>
	    	<a id="searchSemInvestOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchSemInvestOrder()">查询</a>
	    	<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
	    	<a id="exportDrProductInvest" href="javascript:exportSemInvestOrder();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetBtn').click(function(){
		$("#drProductInvestForm").form("reset");
		$("#drSemInvestOrderList").datagrid("load", {});
	});
	//查询按钮
	function searchSemInvestOrder(){
		var cids = $('#cid').combogrid('getValues')+"";
 		$('#drSemInvestOrderList').datagrid({
 			url: '../investOrder/semInvestOrderList.do',
			queryParams: {				
				fullName:$('#searchSemInvestFullName').val(),
				keyword:$('#searchSemKeyword').val(),
				mobilephone:$('#searchSemInvestMobilephone').val(),
				startDate:$('#searchSemInvestStartDate').combobox('getValue'),
				endDate:$('#searchSemInvestEndDate').combobox('getValue'),				
				startRegDate:$('#searchSemInvestStartRegDate').combobox('getValue'),
				endRegDate:$('#searchSemInvestEndRegDate').combobox('getValue'),
				cids:cids,
				deadline:$("#searchSemInvestDeadline").combobox("getValue"),
				repayType:$("#repayType").combobox("getValue")
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#drProductInvestTools'
		});
	};
	
	//导出
	function exportSemInvestOrder(){
		window.location.href="../investOrder/exportSemInvestOrderInfo.do?fullName="+encodeURIComponent(encodeURIComponent($('#searchSemInvestFullName').val()))+
						"&mobilephone="+$('#searchSemInvestMobilephone').val()+
						"&keyword="+$('#searchSemKeyword').val()+
						"&startDate="+$('#searchSemInvestStartDate').combobox('getValue')+
						"&endDate="+$('#searchSemInvestEndDate').combobox('getValue')+
						"&cids="+$('#cid').combogrid('getValues')+						
						"&startRegDate="+$('#searchSemInvestStartRegDate').combobox('getValue')+
						"&endRegDate="+$('#searchSemInvestEndRegDate').combobox('getValue')+
						"&deadline="+$('#searchSemInvestDeadline').combobox('getValue')+
						"&repayType="+$("#repayType").combobox("getValue");
	}

	//获取当前时间
	function getdate(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate 
	return currentdate; 
	} 
	
	//首次查询
	window.onload = function(){
		$('#searchSemInvestStartRegDate').datebox('setValue',getdate());
		$('#searchSemInvestEndRegDate').datebox('setValue',getdate());
		searchSemInvestOrder();
	}
	
	
	
</script>
</body>
</html>