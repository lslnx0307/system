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
	<table id="channelClearingList" title="渠道结算"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'channelName'" width="8%"><font style="font-weight:bold;">渠道大类</font></th>
	        <th data-options="field:'clearingDate'" width="7%" formatter="formatAmount"> <font style="font-weight:bold;">结算日期</font> </th>
	       	<th data-options="field:'firstAmountNormal'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">首投金额(元)</font> </th>
	       	<th data-options="field:'rechargeAmount'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">充值金额(元)</font> </th>
	       	<th data-options="field:'sevenDay'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">7天新手标</font> </th>
	       	<th data-options="field:'thirtyDay'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">30天标</font> </th>
	       	<th data-options="field:'thirtyFiveDay'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">35天标</font> </th>   
	       	<th data-options="field:'sixtyDay'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">60天标</font> </th>  
	       	<th data-options="field:'seventyDay'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">70天标</font> </th>   
	       	<th data-options="field:'oneHundredEightyDay'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">180天标</font> </th>     
	       	<th data-options="field:'roi'" width="5%"> <font style="font-weight:bold;">ROI</font> </th> 
	       	<th data-options="field:'roiAmount'" width="8%" formatter="formatAmount"> <font style="font-weight:bold;">ROI结算(元)</font> </th>       
	       	<th data-options="field:'balanceAmount'" width="8%" formatter="formatOper"> <font style="font-weight:bold;">余额(元)</font> </th>           
	    </tr>
	    </thead>
	</table>
	<div id="channelClearingTools" style="padding:5px;height:750">
	  	<form id="channelClearingForm" target="_blank" method="post">
	                   结算时间:<input class="easyui-datebox" id="startClearingDate" style="width:100px"/>至
	                 <input class="easyui-datebox" id="endClearingDate" style="width:100px"/>
	  	         渠道大类:<select id="channelName" name="channelName" class="easyui-combobox" style="width:100px">
	  	        	<option value=''>全部</option>
	  	           	<c:forEach items="${nameType}" var="map">
						<option value='${map.id }'>${map.name}</option>
					</c:forEach>
	  	           </select>
	  	      
	  		<a id="searchChannelClearing" href="javascript:void(0)" class="easyui-linkbutton" onclick="searchChannelClearing()" iconCls="icon-search">查询</a>
	    	<a id="resetChannelClearing" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
 	    </form>
	</div>
<script type="text/javascript">

	$(document).ready(function () {
		$('#startClearingDate').datebox('setValue',getdateOne()); 
		$('#endClearingDate').datebox('setValue',getdate()); 
		searchChannelClearing();
	});
	//重置按钮
	$('#resetChannelClearing').click(function(){
		$("#channelClearingForm").form("reset");
		searchChannelClearing();
	});
	//查询按钮
	function searchChannelClearing(){
 		$('#channelClearingList').datagrid({
 			url:"../hierarchicalStructure/getChannelClearingList.do",
 			fitColumns : true,
 			rownumbers:true,
 			showFooter: true,
 			pagination:true,
 			nowrap: false,
 			pageSize:25,
 			pageList:[25,50,100],
 			toolbar:"#channelClearingTools", 
 			showFooter: true,
 			queryParams: {
 				startClearingDate:$('#startClearingDate').datebox('getValue'),
 				endClearingDate:$('#endClearingDate').datebox('getValue'),
 				channelName:$('#channelName').combobox('getValue')
				}
		});
	};
	
	function formatOper(value,row,index){
		if(value!=null){
			if(value<0){
				return '<font style="font-weight:bold;" color="#FF0000">'+value+'</font>';
			}else{
				return '<font style="font-weight:bold;">'+value+'</font>';
			}
		}
	}
	
	function formatAmount(value,row,index){
		if(row.id!=null){
			return value;
		}else{
			return '<font style="font-weight:bold;">'+value+'</font>';
		}
	}
</script>
</body>
</html>

