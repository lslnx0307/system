<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
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
	<table id="drProductInfoList" title="优惠券返利明细"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'investTime'"  width="10%" sortable="true">投资时间</th>
	    	<th data-options="field:'simpleName'"  width="10%">产品名称</th>	
	    	<th data-options="field:'two_flag'"  width="10%">二级分类</th>		    	
	    	<th data-options="field:'three_flag'" width="10%">三级分类</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount" >投资金额(元)</th>
	       	<th data-options="field:'spend_amount'" width="10%" styler="styleColor" formatter="formatAmount">支出金额(元)</th>
	       	<th data-options="field:'toFromType'" width="8%">CPS渠道</th>
	        <th data-options="field:'project_no'"  width="8%">渠道</th>
	    </tr>
	    </thead>
	</table>
	<div id="tools" style="padding:5px;height:750">
	  	<form id="drProductInfoForm">
	  	 	投资时间:<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="endDate" name="endDate" class="easyui-datebox" style="width:100px"/>
			渠道:<select  id="project_no" name="project_no" style="width:100px;" class="easyui-combobox">
					<option value='3'>全部</option>
					<option value='1'>金运通</option>
					<option value='2'>存管</option>
         		</select>
	  		CPS渠道:<select  id="channel" name="channel" style="width:100px;" class="easyui-combobox">
						<option value=''>全部</option>
						<option value='1'>CPS</option>
						<option value='0'>非CPS</option>
	           		</select>
	  		二级分类:<select name="two_flag" style="width: 172px" class="easyui-combobox" id="two_flag" data-options="
							  	onSelect:function(res){
							  		$('#three_flag').combobox({    
									    url:'../hierarchicalStructure/getFlag.do?fid='+res.value,    
									    valueField:'id',    
									    textField:'name',
									    onLoadSuccess:function(){
									    	var data = $('#three_flag').combobox('getData');
								            if (data.length > 0) {
								            $.each(data,function(n,v){
								            	if(v.id == ''){
									                $('#three_flag').combobox('select', '');
								            	}
								            });
								            }
									    }
									});
	  								}">
	  						<option value=''>全部</option>
							<c:forEach items="${two_flag}" var="map">
								<option value='${map.id }'>${map.name}</option>
							</c:forEach>
						</select> 
	  		三级分类: <select  id="three_flag" name="three_flag" style="width:172px;" class="easyui-combobox">
						<option value=''>全部</option>
	           		</select>
	           		
	                     产品名称:<input id="searchDrProductInfoSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	    	<a id="searchDrProductInfo" class="easyui-linkbutton" iconCls="icon-search" onclick="searchList()">查询</a>
	    	<a id="resetDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exportDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" onclick="exportInfo()" iconCls="icon-redo">导出</a>
	    </form>
	</div>
<script type="text/javascript">
	//首次查询
	$(document).ready(function () {
		$('#startDate').datebox('setValue',getdate());
		$('#endDate').datebox('setValue',getdate());
		searchList();
	});
	//重置按钮
	$('#resetDrProductInfo').click(function(){
		$("#drProductInfoForm").form("reset");
		/* $("#drProductInfoList").datagrid("load", {}); */
		$('#startDate').datebox('setValue',getdate());
		$('#endDate').datebox('setValue',getdate());
		searchList();
	});
	
	//查询按钮
	function searchList(){
 		$('#drProductInfoList').datagrid({
 			url: '../hierarchicalStructure/couponDetail.do',
			queryParams: {
				startDate:$("#startDate").datebox('getValue'),
				endDate:$("#endDate").datebox('getValue'),
				channel:$("#channel").combobox('getValue'),
				project_no:$("#project_no").combobox('getValue'),
				three_flag:$("#three_flag").combobox('getValue'),
				two_flag:$("#two_flag").combobox('getValue'),
				simpleName: $('#searchDrProductInfoSimpleName').val()
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#tools'
		}); 
	}
	
	
	//导出
	function exportInfo(){
		window.location.href="../hierarchicalStructure/exportCouponDetail.do?startDate="+$("#startDate").datebox('getValue')+
						"&endDate="+$("#endDate").datebox('getValue')+"&channel="+$("#channel").combobox('getValue')+
						"&project_no="+$("#project_no").combobox('getValue')+"&three_flag="+$("#three_flag").combobox('getValue')+
						"&two_flag="+$("#two_flag").combobox('getValue')+
						"&simpleName="+$('#searchDrProductInfoSimpleName').val();
	}
	
	
</script>
</body>
</html>

