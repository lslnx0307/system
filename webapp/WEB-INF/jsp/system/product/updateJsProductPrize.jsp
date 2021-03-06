﻿﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
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
<div>
	<form id="addJsProductPrizeForm" enctype="multipart/form-data">
		<div title="商品信息" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px">
			<table id="productPrize" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">商品名称：<input name="name" type="text" class="easyui-textbox" value="${drProductPrize.name}" />
					权重：<input id="sort" name="sort" type="text" value="${drProductPrize.sort}" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					<input type="hidden" id="id" name="id" value="${drProductPrize.id}"/>
					</td>
				</tr>
				<tr>
					<td align="left">产品简称：<input id="simpleName" name="simpleName" value="${drProductPrize.simpleName}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
						分类：<select name="type" style="width: 172px" class="easyui-combobox" id="type">
								<option value='1' >实物商品</option>
								<option value='0' <c:if test="${drProductPrize.type == 0}"> selected="selected" </c:if>>非实物商品</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">商品原价：<input name="price" name="price" type="text" class="easyui-numberbox" value="${drProductPrize.price}" data-options="required:true,min:0,max:5000000,precision:2" />
					投资金额：<input id="amount" name="amount" type="text" class="easyui-numberbox" value="${drProductPrize.amount}" class="easyui-numberbox" data-options="required:true,min:0,max:5000000,precision:2" validType="trad"/>
					</td>
				</tr>
				<tr>
					<td align="left">商品状态：<select name="status" style="width: 172px" class="easyui-combobox" id="status">
							<c:forEach items="${status}" var="map">
								<option value='${map.key }' <c:if test="${drProductPrize.status == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
						类别：<select name="category" style="width: 172px" class="easyui-combobox" id="category">
							<option value='0'>请选择</option>
							<c:forEach items="${category}" var="map">
								<option value='${map.key }' <c:if test="${drProductPrize.category == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
						</td>
				</tr>
				<tr>
				<td>商品图片1:<input type="file" name="pcV" id="pcImgUrlV" onchange="PreviewPcImage(this)" /></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
							</br><img src="${drProductPrize.pcImgUrlV}" id="pcImgUrlV1"  height="160" width="300" onclick="openPic(this)" v="pcImgUrlV" lsl="${drProductPrize.pcImgUrlV}"/>竖版
				</td>
				<td>商品图片2:<input type="file" name="pcH" id="pcImgUrlH" onchange="PreviewPcImage(this)"/></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
						</br><img src="${drProductPrize.pcImgUrlH}" id="pcImgUrlH1"  height="160" width="300" onclick="openPic(this)" v="pcImgUrlH" lsl="${drProductPrize.pcImgUrlH}"/>横版</td>
				</tr>
				<tr>
					<td>商品图片3:<input type="file" name="h5V" id="h5ImgUrlV" onchange="PreviewPcImage(this)"/></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
					</br><img src="${drProductPrize.h5ImgUrlV}" id="h5ImgUrlV1"  height="160" width="300" onclick="openPic(this)" v="h5ImgUrlV" lsl="${drProductPrize.h5ImgUrlV}"/>H5竖版
					</td>
					<td>商品图片4:<input type="file" name="h5H" id="h5ImgUrlH" onchange="PreviewPcImage(this)"/></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font></br>
					<img src="${drProductPrize.h5ImgUrlH}" id="h5ImgUrlH1"  height="160" width="300" onclick="openPic(this)" v="h5ImgUrlH" lsl="${drProductPrize.h5ImgUrlH}"/>H5横版
					</td>
				</tr>
				<tr>
					<td>商品图片5:<input type="file" name="pcDetail" id="pcDetailImgUrl" onchange="PreviewPcImage(this)"/></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
					</br><img src="${drProductPrize.pcDetailImgUrl}" id="pcDetailImgUrl1"  height="160" width="300" onclick="openPic(this)" v="pcDetailImgUrl" lsl="${drProductPrize.pcDetailImgUrl}"/>PC详情图片
					</td>
					<td>商品图片6:<input type="file" name="h5Detail" id="h5DetailImgUrl" onchange="PreviewPcImage(this)"/></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font></br>
					<img src="${drProductPrize.h5DetailImgUrl}" id="h5DetailImgUrl1"  height="160" width="300" onclick="openPic(this)" v="h5DetailImgUrl" lsl="${drProductPrize.h5DetailImgUrl}"/>H5详情图片
					</td>
				</tr>
				<tr>
					<td align="left">备注：<textarea rows="5" cols="50" id="remark" name="remark" style="resize: none">${drProductPrize.remark}</textarea>
				</tr>
			</table>
						<!-- 显示图片 -->
		<div id="addProductPicShow" class="easyui-dialog" title="查看图片" style="height:auto;width:700px;padding:10px;top:20%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
			<div id="imgbig">
				<img id="imglook" src="" width="650px" height="auto" />
			</div>
			<!-- 显示图片 -->
		</div>
		<div style="text-align:center;padding:30px 60px 30px 0px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfo();">提交</a>
		</div>
	</form>
</div>	
<div id="queryDrSubjectInfoWindow" class="easyui-window"
	data-options="closed:true,modal:true,minimizable:false,maximizable:false" style="padding:10px;">
</div>
<script type="text/javascript">
	var j=0;
	
 	function addDrProductInfo(){
 	var flag = $("#addJsProductPrizeForm").form('enableValidation').form('validate');
		if(flag){
			$("#addJsProductPrizeForm").ajaxSubmit({
				url:"${apppath}/productPrize/updateDrProductPrize.do",
				type:"POST",
				data:$("#addJsProductPrizeForm").serialize(),
	      		success:function(data){
	      		    var resultJson = jQuery.parseJSON(data);
					var resultJson = eval(resultJson);
					if(resultJson.success){
						$.messager.alert("提示信息",resultJson.msg,"",function(){
							var currTab = parent.$('#main-center').tabs('getTab', "商品管理");
							console.log("currTab"+currTab);
							if(currTab != null){
								var content = '<iframe scrolling="no" frameborder="0"  src="../productPrize/toJsProductPrizeList.do" style="width:100%;height:100%;"></iframe>';  
								parent.$('#main-center').tabs('update', {
									tab: currTab,
									options: {
										content: content  // 新内容的URL
									}
								});
							}
							parent.$('#main-center').tabs('close','商品修改');
						});
						$("#addJsProductPrizeForm").resetForm(); // 提交后重置表单
					}else{
						$.messager.alert("提示信息",resultJson.errorMsg);
					}            	
				}
	        });
	        return false; // 阻止表单自动提交事件
		}
}
	
	function PreviewPcImage(pcPutImg){
			if (pcPutImg.value == "") {  
				$.messager.alert("提示信息","请上传图片");
		        return false;  
		    }  
	     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(pcPutImg.value)) {  
	        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
	            pcPutImg.value = "";  
	            return false;  
	        }
		    if(pcPutImg){  
				if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
					pcPutImg.select();
					var imgSrc = document.selection.createRange().text;
					var localImagId = document.getElementById("showArticleAddPic");
					localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
					document.selection.empty();
	      		}else{ 
	                if(pcPutImg.files)  
	                {  
	                	var id = pcPutImg.id;
	                	if("pcImgUrlV" == id){
	                		$("#pcImgUrlV1").attr("src",window.URL.createObjectURL(pcPutImg.files[0]));  
	                	}else if("pcImgUrlH" == id){
	                		$("#pcImgUrlH1").attr("src",window.URL.createObjectURL(pcPutImg.files[0]));  
	                	}else if("h5ImgUrlV" == id){
	                		$("#h5ImgUrlV1").attr("src",window.URL.createObjectURL(pcPutImg.files[0]));  
	                	}else if("h5ImgUrlH" == id){
	                		$("#h5ImgUrlH1").attr("src",window.URL.createObjectURL(pcPutImg.files[0]));  
	                	}else if("pcDetailImgUrl" == id){
	                		$("#pcDetailImgUrl1").attr("src",window.URL.createObjectURL(pcPutImg.files[0]));
	                	}else if("h5DetailImgUrl"==id){
	                		$("#h5DetailImgUrl1").attr("src",window.URL.createObjectURL(pcPutImg.files[0]));
	                	}
	                }  
	             }
	         }  
			return true;
		}
		//放大图片
	function openPic(ths){
		if($(ths).attr("lsl") == ""){
			return false;
		}
		if(typeof($(ths).prop("src")) == "undefined" || $(ths).prop("src") == "undefined"){
		   	var fileId = $(ths).attr("v");
		   	var dFile = $("#"+fileId);
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
			$('#addProductPicShow').dialog('open');
			return true;
			}else{
				$("#imglook").attr("src",$(ths).prop("src"));
				$('#addProductPicShow').dialog('open');
			}
  }
</script>

</body>
</html>
