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
	<table id="memberFundsLogList" title="客户收支记录  <span style='color: #0015FF;'>收入合计</span>：<span id='memberFundsLogIncome' style='color: red;'></span>
	 <span style='color: #0015FF;'>支出合计</span>：<span id='memberFundsLogPay' style='color: red;'></span>" 
	style="height:99%;width:99.9%">
		<thead>
	    <tr>
	        <th data-options="field:'oneType'" width="10%" >一级分类</th>
	        <th data-options="field:'twoType'" width="10%" >二级分类</th>
	        <th data-options="field:'fundTypeName'" width="10%" >三级分类</th>
	        <th data-options="field:'typeName'" width="8%">收支类型</th>
	       	<th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">交易金额</th>
	       	<th data-options="field:'mobilephone'" width="10%">用户手机</th>
	       	<th data-options="field:'level'" width="6%" >会员等级</th>	   
	        <th data-options="field:'realName'" width="6%">用户姓名</th>
	        <th data-options="field:'recommCodes'" width="6%">推荐码</th>
	       	<th data-options="field:'idCards'" width="12%">身份证号</th>	     
	        <th data-options="field:'remark'" width="25%">备注</th>
			<th data-options="field:'addTime'" width="10%" formatter="formatDateBoxFull">交易时间</th>
	    </tr>
	    </thead>
	</table>
	<div id="memberFundsLogTools" style="padding:5px;height:750">
	  	<form id="memberFundsLogForm">
	  		交易时间:<input id="searchMemberFundsLogStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchMemberFundsLogEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
<!-- 	  		用户姓名:<input id="searchMemberFundsLogRealName" name="realName" class="easyui-textbox"  size="15" style="width:100px"/> -->
	  		推荐码:<input id="searchMemberFundsLogRecommCodes" name="recommCodes"  value='${recommCodes }' class="easyui-textbox"  size="15" style="width:100px"/>
	  		用户手机:<input id="searchMemberFundsLogMobilephone" name="mobilephone" value='${mobilePhone}' class="easyui-textbox"  size="11" style="width:100px"/>
	  		会员等级:<select id="mgid" name="mgid" style="width: 172px" class="easyui-combobox">
							<option value=''>全部</option>
							<option value='100'>普通用户</option>
							<c:forEach items="${grade}" var="map">
									<option value='${map.id}'>${map.level}</option>
							</c:forEach>
					</select>
		         一级分类 : <select id="oneType" name="oneType" class="easyui-combobox" style="width:100px" data-options="
						  	onSelect:function(res){
						  		$('#twoType').combobox({    
								    url:'../hierarchicalStructure/getActivityByFid.do?fid='+res.value+'&grade='+2,    
								    valueField:'id',    
								    textField:'name',
								    onLoadSuccess:function(){
								    	var data = $('#twoType').combobox('getData');
							            if (data.length > 0) {
								            $.each(data, function (n, v) {
							            		if(v.id == ''){
							            			 $('#twoType').combobox('select', '');
							            		}
						               		});
							            		
							            }
								    }
								});
  								}
							 ">
	  	        	<option value=''>全部</option>
	  	           	<c:forEach items="${oneType}" var="map">
						<option value='${map.id }'>${map.name}</option>
					</c:forEach>
	  	           </select>
	  	        二级分类 : <select id="twoType" name="twoType" class="easyui-combobox" style="width:100px" data-options="
						  	onSelect:function(res){
						      	var fid='';
						  		if(res.id){
						  			fid=res.id;
						  		}else if(res.value){
						  			fid=res.value;
						  		}
						  		$('#threeType').combobox({    
								    url:'../hierarchicalStructure/getActivityByFid.do?fid='+fid+'&grade='+3,    
								    valueField:'id',    
								    textField:'name',
								    onLoadSuccess:function(){
								    	var data = $('#threeType').combobox('getData');
							            if (data.length > 0) {
							            	 $.each(data, function (n, v) {
							            		if(v.id == ''){
							            			 $('#threeType').combobox('select', '');
							            		}
						               		});
							            }
								    }
								});
  								}
							 ">
	  	           		<option value=''>全部</option>
		  	           	<c:forEach items="${twoType}" var="map">
							<option value='${map.id }'>${map.name}</option>
						</c:forEach>
	  	           </select>
	  	        三级分类 : <select id="threeType" name="threeType" class="easyui-combobox" style="width:150px">
	  	           		<option value=''>全部</option>
		  	           	<c:forEach items="${threeType}" var="map">
							<option value='${map.id }'>${map.name}</option>
						</c:forEach>
	  	           </select>
	  	    <a id="searchMemberFundsLogBtn" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMemberFundsLogBtn()">查询</a>
	    	<a id=resetMemberFundsLogBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<%-- <br>
	    	<label><input type="checkbox" value=""/> 全部</label>
			<c:forEach var="chooseOption" items="${memberFundsType}">
				<label>
				<input type="checkbox" name="fundTypes" value="${chooseOption.code}" id="${chooseOption.code}" />${chooseOption.cnvalue}
				</label>
		    </c:forEach> --%>
	    </form>
	</div>
<script type="text/javascript">
	$(document).ready(function () {
		$('#searchMemberFundsLogStartDate').datebox('setValue',getDateBefore(1));
		$('#searchMemberFundsLogEndDate').datebox('setValue',getdate());
		searchMemberFundsLogBtn();
	});
	//重置按钮
	$('#resetMemberFundsLogBtn').click(function(){
		$("#memberFundsLogForm").form("reset");
		$("#memberFundsLogList").datagrid("load", {});
	});
	//查询按钮
	function searchMemberFundsLogBtn(){
 		$('#memberFundsLogList').datagrid({
 			url:'../memberFundsLog/memberFundsLogList.do',
 			toolbar:'#memberFundsLogTools',
 			singleSelect:true,
 			fitColumns : true,
 			showFooter:true,
 			pagination : true,
 			rownumbers:true,
 			pageSize:25,
 			pageList:[25,50,100],
 			autoRowHeight : false,
 			fit:true,
			queryParams: {
				startDate: $('#searchMemberFundsLogStartDate').datebox('getValue'),
				endDate: $('#searchMemberFundsLogEndDate').datebox('getValue'),
				mobilephone: $('#searchMemberFundsLogMobilephone').val(),
// 				realName: $('#searchMemberFundsLogRealName').val(),
				recommCodes: $('#searchMemberFundsLogRecommCodes').val(),
				idCards: $('#searchMemberFundsLogIdCards').val(),
				fundTypes:memberFundsLogCBAll(),
				mgid: $('#mgid').combobox('getValue'),
				oneType:$('#oneType').combobox('getValue'),
 				twoType:$('#twoType').combobox('getValue'),
 				threeType:$('#threeType').combobox('getValue')
			},
			onBeforeLoad: function (d) {
				    $.ajax({
					url:"${apppath}/memberFundsLog/memberFundsLogSum.do",
					type:"POST",
					data:$("#memberFundsLogForm").serialize(),  
					success:function(result){
						$("#memberFundsLogIncome").text(fmoney(result.memberFundsLogIncome,2));
						$("#memberFundsLogPay").text(fmoney(result.memberFundsLogPay,2));
					}
					});
			} 
		}); 
	};

	function memberFundsLogCBAll(){//输出选中的值
		var checks="";  
		$('input[name="fundTypes"]:checked').each(function(){
        	checks +=$(this).val()+",";
		});  
		return checks;  
	} 
	

</script>
</body>
</html>

