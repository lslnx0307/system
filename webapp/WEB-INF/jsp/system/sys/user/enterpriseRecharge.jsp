﻿﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
	<%@ include file="../../../common/include/util.jsp" %>



</head>
<body style="width:100%;height:100%">

	流水号:<input id="str" type="text"  name="str" />

	<br>
	<br>
	<button type="button" onclick="queryRecharge()">确定</button>
</body>

<script type="text/javascript">
	
	function queryRecharge(){
		$.ajax({
				url:"${apppath}/gukaimingTest/queryRecharge.do",
				type:"POST",
				data:{"str":$("#str").val()},
				success:function(result){
					if(result.success)
						$.messager.alert("提示信息",result.msg?result.msg:'操作成功');
					else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
			});
	
	}
	
	
</script>
</html>
