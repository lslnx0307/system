<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
<div id="${tabid}">
	<form id="updateDrClaimsInfoForm" enctype="multipart/form-data">
		<input type="hidden" value="${drClaimsLoan.id }" name="id"/>
		<div id="qykhjbxx" title="<center>企业客户基本信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
			<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">企业名称：</td>
					<td colspan="5">
						<input name="drClaimsCustomer.companyName" value="${drClaimsCustomer.companyName}" style="width: 655px" type="text" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">注册资金（万元）：</td>
					<td>
						<input name="drClaimsCustomer.registerMoney" value="${drClaimsCustomer.registerMoney}" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
					</td>
					<td align="left">是否存续：</td>
					<td>
						<select name="drClaimsCustomer.isExistence" value="${drClaimsCustomer.isExistence}" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${whether}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsCustomer.isExistence== map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">经营期限(年)：</td>
					<td>
						<input name="drClaimsCustomer.operatingPeriod" value="${drClaimsCustomer.operatingPeriod}" type="text" class="easyui-numberbox" data-options="required:false,min:0,max:100000000"/>
					</td>
				</tr>
				<tr>
					<td align="left">企业融资协议名称显示：</td>
					<td colspan="3" >
						<input name="drClaimsCustomer.companyNameProtocolShow" value="${drClaimsCustomer.companyNameProtocolShow}" style="width: 415px" type="text" class="easyui-textbox " data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">法定代表人：</td>
					<td class="isEdit">
						<input name="drClaimsCustomer.id"  value="${drClaimsCustomer.id}" type="hidden"/>
						<input name="drClaimsCustomer.name" value="${drClaimsCustomer.name}" type="text" class="easyui-textbox " data-options="required:true,validType:'maxLength[10]'"/>
					</td>
					<td align="left">性别：</td>
					<td class="isEdit">
						<select name="drClaimsCustomer.sex" style="width: 175px" class="easyui-combobox ">
							<c:forEach items="${sex}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsCustomer.sex== map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">手机号码：</td>
					<td class="isEdit">
						<input name="drClaimsCustomer.phone" value="${drClaimsCustomer.phone}" type="text" class="easyui-textbox" data-options="required:true,validType:'maxLength[11]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">证件类型：</td>
					<td >
						<select name="drClaimsCustomer.certificateType" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${certificateType}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsCustomer.certificateType== map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">证件号码：</td>
					<td colspan="3" class="isEdit">
						<input name="drClaimsCustomer.certificateNo" value="${drClaimsCustomer.certificateNo}" style="width: 415px" type="text" class="easyui-textbox " data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">营业执照号码：</td>
					<td colspan="3" class="isEdit">
						<input name="drClaimsCustomer.businessNo" value="${drClaimsCustomer.businessNo}" style="width: 415px" type="text" class="easyui-textbox " data-options="validType:'maxLength[100]'"/>
					</td>
					<td align="left">行业类别：</td>
					<td>
						<input name="drClaimsCustomer.industryType" value="${drClaimsCustomer.industryType}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">企业联系号码：</td>
					<td>
						<input name="drClaimsCustomer.companyPhone" value="${drClaimsCustomer.companyPhone}" type="text" class="easyui-textbox" data-options="validType:'maxLength[20]'"/>
					</td>
					<td align="left">企业邮箱：</td>
					<td colspan="3">
						<input name="drClaimsCustomer.companyMail" value="${drClaimsCustomer.companyMail}" style="width: 410px" type="text" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					</td>
				</tr>
				<tr>
				<td align="left">主营业务：</td>
				<td colspan="5" class="isEdit">
					<input name="drClaimsCustomer.mainBusiness" value="${drClaimsCustomer.mainBusiness}" style="width: 655px" type="text" class="easyui-textbox" data-options="validType:'maxLength[200]'"/>
				</td>
			</tr>
				<tr>
					<td align="left">通讯地址：</td>
					<td colspan="5" class="isEdit">
						<input name="drClaimsCustomer.address" value="${drClaimsCustomer.address}" style="width: 655px" type="text" class="easyui-textbox " data-options="validType:'maxLength[200]'"/>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="shareholderTable" title="<center>主要股东信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" 
		data-options="collapsible:true,tools:'#shareholderBtn'">
			<c:if test="${drClaimsShareholder.size() == 0}">
			<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
				<tr>
					<td align="left">名称：</td>
					<td class="isEdit">
						<input name="drClaimsShareholder[0].name" type="text" class="easyui-textbox " data-options="validType:'maxLength[50]'"/>
					</td>
					<td align="left">性别：</td>
					<td class="isEdit">
						<select name="drClaimsShareholder[0].sex" style="width: 175px" class="easyui-combobox ">
							<c:forEach items="${sex}" var="map">
								<option value='${map.key }'>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">手机号码：</td>
					<td>
						<input name="drClaimsShareholder[0].phone" type="text" class="easyui-textbox" data-options="validType:'maxLength[11]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">股东类别：</td>
					<td>
						<select name="drClaimsShareholder[0].type" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${shareholderType}" var="map">
								<option value='${map.key }'>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">证件类型：</td>
					<td>
						<select name="drClaimsShareholder[0].certificateType" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${certificateType}" var="map">
								<option value='${map.key }'>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">证件号码：</td>
					<td class="isEdit">
						<input name="drClaimsShareholder[0].certificateNo" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">出资额度(万元)：</td>
					<td>
						<input name="drClaimsShareholder[0].contributionLines" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
					</td>
					<td align="left">出资方式：</td>
					<td>
						<input name="drClaimsShareholder[0].contributionType" type="text" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					</td>
					<td align="left">投资比例：</td>
					<td>
						<input name="drClaimsShareholder[0].investRate" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2"/>
					</td>
				</tr>
				<tr>
					<td align="left">实缴金额（万元）：</td>
					<td>
						<input name="drClaimsShareholder[0].amountPaidIn" value="${v.amountPaidIn}" type="text" class="easyui-numberbox" data-options="required:false,min:0,max:100000000,precision:6"/>
					</td>
				</tr>
			</table>
			</c:if>
			<c:forEach items="${drClaimsShareholder}" var="v" varStatus="i">
			<c:if test="${i.index != 0}">
				<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>
			</c:if>
			<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
				<tr>
					<td align="left" >名称：</td>
					<td class="isEdit">
						<input name="drClaimsShareholder[${i.index}].id"  value="${v.id}" type="hidden"/>
						<input name="drClaimsShareholder[${i.index}].name" value="${v.name}" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
					<td align="left">性别：</td>
					<td class="isEdit">
						<select name="drClaimsShareholder[${i.index}].sex" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${sex}" var="map">
								<option value='${map.key }' <c:if test="${v.sex == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">手机号码：</td>
					<td>
						<input name="drClaimsShareholder[${i.index}].phone" value="${v.phone}" type="text" class="easyui-textbox" data-options="validType:'maxLength[11]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">股东类别：</td>
					<td>
						<select name="drClaimsShareholder[${i.index}].type" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${shareholderType}" var="map">
								<option value='${map.key }' <c:if test="${v.type == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">证件类型：</td>
					<td>
						<select name="drClaimsShareholder[${i.index}].certificateType" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${certificateType}" var="map">
								<option value='${map.key }' <c:if test="${v.certificateType == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">证件号码：</td>
					<td class="isEdit">
						<input name="drClaimsShareholder[${i.index}].certificateNo" value="${v.certificateNo}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">出资额度（万元）：</td>
					<td>
						<input name="drClaimsShareholder[${i.index}].contributionLines" value="${v.contributionLines}" type="text" class="easyui-textbox" data-options="min:0,max:100000000,precision:6"/>
					</td>
					<td align="left">出资方式：</td>
					<td>
						<input name="drClaimsShareholder[${i.index}].contributionType" value="${v.contributionType}" type="text" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					</td>
					<td align="left">投资比例：</td>
					<td>
						<input name="drClaimsShareholder[${i.index}].investRate" value="${v.investRate}" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2"/>
					</td>
				<tr>
					<td align="left">实缴金额（万元）：</td>
					<td>
						<input name="drClaimsShareholder[${i.index}].amountPaidIn" value="${v.amountPaidIn}" type="text" class="easyui-numberbox" data-options="required:false,min:0,max:100000000,precision:6"/>
					</td>
				</tr>
				</tr>
				<c:if test="${i.index != 0}">
				<tr>
					<td colspan='6' align='right'>
					<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delShareholder(this);'>删除</a>
					</td>
				</tr>
				</c:if>
			</table>
			</c:forEach>
		</div>
		
		<div title="<center>融资项目基本信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
			<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					
					<td align="left">产品名称：</td>
					<td class="isEdit">
						<input name="isAuditEdit" value="${drClaimsLoan.isAuditEdit}" type="hidden" />
						<input name="id" value="${drClaimsLoan.id}" type="hidden" />
						<input name="name" value="${drClaimsLoan.name}" type="text" class="easyui-textbox " data-options="required:true,validType:'maxLength[50]'"/>
					</td>
					<td align="left">融资合同编号：</td>
					<td class="isEdit">
						<input id="drClaimsLoanNo" name="no" value="${drClaimsLoan.no}" type="text" class="easyui-textbox " data-value="${drClaimsLoan.no}" data-options="required:true,validType:'updateProductNo[250,drClaimsLoanNo]'"/>
					</td>
					<td align="left">贷款用途：</td>
					<td class="isEdit">
						<input name="loanUse" value="${drClaimsLoan.loanUse}" type="text" class="easyui-textbox" data-options="required:true,validType:'maxLength[250]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">贷款金额（万元）：</td>
					<td>
						<input id="loanAmount" name="loanAmount" value="${drClaimsLoan.loanAmount}" class="easyui-textbox"/>
					</td>
					<td align="left">项目融资服务费率(%)：</td>
					<td>
						<input id="serviceRate" name="serviceRate" value="${drClaimsLoan.serviceRate}" class="easyui-numberbox" data-options="min:0,max:100,precision:2"/>
					</td>
					<td align="left">项目融资服务费（万元）：</td>
					<td>
						<input id="serviceCharge" name="serviceCharge" value="${drClaimsLoan.serviceCharge}" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
					</td>
				</tr>
				<tr>
					<td align="left">年利率(%)：</td>
					<td>
						<input name="rate" value="${drClaimsLoan.rate}" class="easyui-numberbox" data-options="required:true,min:0,max:20,precision:2"/>
					</td>
					<td align="left">还款方式：</td>
					<td>
						<select name="repayType" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1}">
								<option value='${map.key }' <c:if test="${drClaimsLoan.repayType == map.key}"> selected="selected" </c:if>>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td align="left">还款周期：</td>
					<td>
						<select name="repayDeadline" style="width: 175px" class="easyui-combobox" disabled="disabled">
							<c:forEach items="${repayDeadline}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsLoan.repayDeadline == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">币种：</td>
					<td>
						<input name="currency" value="${drClaimsLoan.currency}" class="easyui-textbox"/>
					</td>
					<td align="left">项目融资担保费率（代）(%)：</td>
					<td>
						<input name="advisoryRate" value="${drClaimsLoan.advisoryRate}" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
					</td>
					<td align="left">到期日期：</td>
					<td>
						<input name="endDate" class="easyui-datebox" value="${endDate }" data-options="required:true"/>
					</td>
				</tr>
				<tr>
						<td align="left">融资顾问时间开始：</td>
						<td>
							<input id="creditDate" name="creditDate" value="${creditDate }" class="easyui-datebox" data-options="required:true"/>
						</td>
						<td align="left">融资顾问时间结束：</td>
						<td>
							<input id="overDate" name="overDate" value="${overDate }" class="easyui-datebox" data-options="required:true"/>
						</td>
						<td align="left">融资顾问期限（天）：</td>
						<td>
							<input id='financingDeadline' name="financingDeadline" value="${drClaimsLoan.financingDeadline }" class="easyui-numberbox" data-options="min:0,max:100000000" data-options="required:true" readonly="readonly"/>
						</td>
					</tr>
				<tr>
				<tr>
					<td align="left">贷款户名：</td>
					<td class="isEdit">
						<input name="loanName" value="${drClaimsLoan.loanName}" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					</td>
					<td align="left">银行名称：</td>
					<td class="isEdit">
						<input name="bankName" value="${drClaimsLoan.bankName}" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					</td>

				</tr>
				<tr>
					<td align="left">银行账号：</td>
					<td colspan="3" class="isEdit">
						<input name="bankNo" value="${drClaimsLoan.bankNo}" style="width: 500px" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
					<td align="left">开户行地址：</td>
					<td  class="isEdit">
						<input name="bankAddress" value="${drClaimsLoan.bankAddress}" class="easyui-textbox"  data-options="validType:'maxLength[100]'"/>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="guaranteeTable" title="<center>担保情况</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" 
		data-options="collapsible:true,tools:'#guaranteeBtn'">
			<c:if test="${drClaimsGuarantee.size() == 0}">
			<table class="isEdit" align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
				<tr align="right">
					<td align="right">担保方式：</td>
					<td>
						<select id="pawnWay" name="drClaimsGuarantee[0].pawnWay" style="width: 175px" class="easyui-combobox" >
							<c:forEach items="${pawnWay}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsGuarantee[0].pawnWay == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="isPawn">
					<td align="left">是否有抵/质押物：</td>
					<td>
						<select id="isPawn" name="drClaimsGuarantee[0].isPawn" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuarantee(rec);
						        }">
							<c:forEach items="${whether}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsGuarantee[0].isPawn == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">抵/质押物：</td>
					<td >
						<input id="pawnMatter" name="drClaimsGuarantee[0].pawnMatter" type="text" class="easyui-textbox " data-options="validType:'maxLength[10]'"/>
					</td>
					<td align="left">抵/质押物评估值（万元）：</td>
					<td>
						<input id="assessAmount" name="drClaimsGuarantee[0].assessAmount" type="text" class="easyui-numberbox" data-options="min:0,max:10000,precision:6"/>
					</td>
				</tr>
				<tr class="isGuarantee">
				<td align="right">是否有个人担保：</td>
				<td>
					<select id="isGuarantee" name="drClaimsGuarantee[0].isGuarantee" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuarantee(rec);
						        }">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsGuarantee[0].isGuarantee == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">个人担保人名称：</td>
				<td>
					<input id="guarantee" name="drClaimsGuarantee[0].guarantee" type="text" class="easyui-textbox"/>
				</td>
				<td align="right">个人保证金额（万元）:</td>
				<td>
					<input id="singleGuaranteeAmount" name="drClaimsGuarantee[0].singleGuaranteeAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有公司保证：</td>
				<td>
					<select id="isCompanyGuarantee" name="drClaimsGuarantee[0].isCompanyGuarantee" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuarantee(rec);
						        }">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsGuarantee[0].isCompanyGuarantee == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				
				<td align="right">公司保证名称：</td>
				<td>
					<input id="companyGuarantee" name="drClaimsGuarantee[0].companyGuarantee" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
				</td>
				<td align="right">公司保证金额（万元）:</td>
				<td>
					<input id="companyGuaranteeAmount" name="drClaimsGuarantee[0].companyGuaranteeAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr class="isFinancA">
				<td align="right">是否有融资性担保：</td>
				<td>
					<select id="isFinanc" name="drClaimsGuarantee[0].isFinanc" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuarantee(rec);
						        }">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsGuarantee[0].isFinanc == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">融资性担保公司名称：</td>
					<td>
						<input id="finanCompanyguarantee" name="drClaimsGuarantee[0].finanCompanyguarantee" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
				<td align="right">担保公司保证金额（万元）：</td>
				<td>
					<input id="guaranteeAmount" name="drClaimsGuarantee[0].guaranteeAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			</table>
			</c:if>
			<c:forEach items="${drClaimsGuarantee}" var="v" varStatus="i">
			<c:if test="${i.index != 0}">
				<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>
			</c:if>
			<table class="isEdit" align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
				<tr align="right">
					<td align="right">担保方式：</td>
					<td>
						<select name="drClaimsGuarantee[${i.index}].pawnWay" value="${v.pawnWay }" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${pawnWay}" var="map">
								<option value='${map.key }' <c:if test="${v.pawnWay == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="aaa">
					<td align="left">是否有抵/质押物：</td>
					<td>
						<select id="isPawnS" name="drClaimsGuarantee[${i.index}].isPawn" value="${v.isPawn }" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuaranteeS(rec);
						        }">
							<c:forEach items="${whether}" var="map">
								<option value='${map.key }' <c:if test="${v.isPawn == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">抵/质押物：</td>
					<td>
						<input id="pawnMatterS" name="drClaimsGuarantee[${i.index}].pawnMatter" value="${v.pawnMatter }" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
					<td align="left">抵/质押物评估值（万元）：</td>
					<td>
						<input id="assessAmountS" name="drClaimsGuarantee[${i.index}].assessAmount" value="${v.assessAmount }" type="text" class="easyui-numberbox" data-options="min:0,max:10000,precision:6"/>
					</td>
				</tr>
				<tr>
				<td align="right">是否有个人担保：</td>
				<td>
					<select id="isGuaranteeS" name="drClaimsGuarantee[${i.index}].isGuarantee" value="${v.isGuarantee }" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuaranteeS(rec);
						        }">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${v.isGuarantee == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">个人担保人名称：</td>
				<td>
					<input id="guaranteeS" name="drClaimsGuarantee[${i.index}].guarantee" value="${v.guarantee }" type="text" class="easyui-textbox"/>
				</td>
				<td align="right">个人保证金额（万元）:</td>
				<td>
					<input id="singleGuaranteeAmountS" name="drClaimsGuarantee[${i.index}].singleGuaranteeAmount" value="${v.singleGuaranteeAmount }" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有公司保证：</td>
				<td>
					<select id="isCompanyGuaranteeS" name="drClaimsGuarantee[${i.index}].isCompanyGuarantee" value="${v.isCompanyGuarantee }" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuaranteeS(rec);
						        }">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${v.isCompanyGuarantee == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				
				<td align="right">公司保证名称：</td>
				<td>
					<input id="companyGuaranteeS" name="drClaimsGuarantee[${i.index}].companyGuarantee" value="${v.companyGuarantee }" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
				</td>
				<td align="right">公司保证金额（万元）:</td>
				<td>
					<input id="companyGuaranteeAmountS" name="drClaimsGuarantee[${i.index}].companyGuaranteeAmount" value="${v.companyGuaranteeAmount }" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有融资性担保：</td>
				<td>
					<select id="isFinancS" name="drClaimsGuarantee[${i.index}].isFinanc" value="${v.isFinanc }" style="width: 175px" class="easyui-combobox" data-options="    
						        onSelect: function(rec){
									judgeGuaranteeS(rec);
						        }">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${v.isFinanc == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">融资性担保公司名称：</td>
					<td>
						<input id="finanCompanyguaranteeS" name="drClaimsGuarantee[${i.index}].finanCompanyguarantee" value="${v.finanCompanyguarantee }" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
				<td align="right">担保公司保证金额（万元）：</td>
				<td>
					<input id="guaranteeAmountS" name="drClaimsGuarantee[${i.index}].guaranteeAmount" value="${v.guaranteeAmount }" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			</table>
			</c:forEach>
		</div>
		
		<div title="<center>票据信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true"> 
			<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">类别：</td>
					<td>
						<select name="drClaimsBill.type" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${billType}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsBill.type == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">开票人：</td>
					<td>
						<input name="drClaimsBill.drawer" value="${drClaimsBill.drawer}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
					<td align="left">收款人：</td>
					<td>
						<input name="drClaimsBill.payee" value="${drClaimsBill.payee}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">承兑人：</td>
					<td>
						<input name="drClaimsBill.acceptor" value="${drClaimsBill.acceptor}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
					<td align="left">票据金额（万元）：</td>
					<td>
						<input name="drClaimsBill.amount" value="${drClaimsBill.amount}" type="text" class="easyui-numberbox" data-options="min:0,max:10000,precision:6"/>
					</td>
					<td align="left">票据号码：</td>
					<td>
						<input name="drClaimsBill.number" value="${drClaimsBill.number}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">被背书人：</td>
					<td colspan="5">
						<input name="drClaimsBill.endorsee" value="${drClaimsBill.endorsee}" style="width: 700px" type="text" class="easyui-textbox" 
						data-options="validType:'maxLength[255]'" placeholder="示例:a/b/c/d"/>
					</td>
				</tr>
				<tr>
					<td align="left">开票日期：</td>
					<td>
						<input name="drClaimsBill.startDate" class="easyui-datebox" value="${billStartDate}"/>
					</td>
					<td align="left">票据到期日：</td>
					<td>
						<input name="drClaimsBill.endDate" class="easyui-datebox" value="${billEndDate}"/>
					</td>
				</tr>
			</table>
		</div>
		
		<div title="<center>融资性担保企业信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
			<table  class="isEdit" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">企业名称：</td>
					<td colspan="5">
						<input name="drClaimsFinanc.id" value="${drClaimsFinanc.id}" type="hidden">
						<input name="drClaimsFinanc.companyName" value="${drClaimsFinanc.companyName}" style="width: 700px" type="text" class="easyui-textbox" data-options="validType:'maxLength[255]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">法定代表人：</td>
					<td>
						<input name="drClaimsFinanc.name" value="${drClaimsFinanc.name}" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
					<td align="left">性别：</td>
					<td>
						<select name="drClaimsFinanc.sex" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${sex}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsFinanc.sex == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">手机号码：</td>
					<td>
						<input name="drClaimsFinanc.phone" value="${drClaimsFinanc.phone}" type="text" class="easyui-textbox" data-options="validType:'maxLength[11]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">证件类型：</td>
					<td>
						<select name="drClaimsFinanc.certificateType" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${certificateType}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsFinanc.certificateType == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
					<td align="left">证件号码：</td>
					<td colspan="3">
						<input name="drClaimsFinanc.certificateNo" value="${drClaimsFinanc.certificateNo}" style="width: 415px" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">行业类别：</td>
					<td>
						<input name="drClaimsFinanc.industryType" value="${drClaimsFinanc.industryType}" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">营业执照号码：</td>
					<td>
						<input name="drClaimsFinanc.businessNo" value="${drClaimsFinanc.businessNo}" type="text" class="easyui-textbox" data-options="validType:'maxLength[100]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">企业联系号码：</td>
					<td>
						<input name="drClaimsFinanc.companyPhone" value="${drClaimsFinanc.companyPhone}" type="text" class="easyui-textbox" data-options="validType:'maxLength[20]'"/>
					</td>
					<td align="left">企业邮箱：</td>
					<td colspan="3">
						<input name="drClaimsFinanc.companyMail" value="${drClaimsFinanc.companyMail}" style="width: 415px" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">注册资金：</td>
					<td>
						<input name="drClaimsFinanc.registerFund" value="${drClaimsFinanc.registerFund}" type="text" class="easyui-numberbox" data-options="min:0,max:1000000000,precision:2"/>
					</td>
					<td align="left">实缴金额：</td>
					<td>
						<input name="drClaimsFinanc.registerCurrency" value="${drClaimsFinanc.registerCurrency}" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
					<td align="left">成立日期：</td>
					<td>
						<input name="drClaimsFinanc.establishDate" value="${establishDate}" class="easyui-datebox" />
					</td>
				</tr>
				<tr>
					<td align="left">通讯地址：</td>
					<td colspan="5">
						<input name="drClaimsFinanc.address" value="${drClaimsFinanc.address}" style="width: 700px" type="text" class="easyui-textbox" data-options="validType:'maxLength[255]'"/>
					</td>
				</tr>
			</table>
		</div>
		<div title="<center>审核项目</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center',tools:'#projectUpdBtn'">
			<table id="updProjectTable" align="center" cellspacing="1" cellpadding="1" style="width: auto;">
				<c:forEach items="${drClaimsProject}" var="v" varStatus="i">
				<tr>
					<td>
						审核内容：<input name='drClaimsProject[${i.index}].name' value="${v.name}" type='text' class='easyui-textbox' data-options="validType:'maxLength[50]'"/>
					</td>
					<td>
			   	   		<div class='btn-group'>
				   	   		<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delProject(this)'>删除</a>
			   	   		</div>
	   	   			</td>
		   	   	</tr>
				</c:forEach>
			</table>
		</div>
		<div title="<center>风控图片</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;"data-options="collapsible:true,region:'center',tools:'#picAddBtn'">
			<table id="picTable" style="width:auto;" align="center" >
				<tr>
					<th width="10%">图片名称</th>
					<th width="10%">修改图片</th>
					<th width="10%">操作</th>
				</tr>
				<c:forEach items="${drClaimsPic}" var="v" varStatus="i">
				<tr>
					<td>
						<input name='drClaimsPic[${i.index}].id' value="${v.id}" type="hidden"/>
						<input name='drClaimsPic[${i.index}].name' value="${v.name}" type='text' class='easyui-textbox' data-options="validType:'maxLength[50]'"/>
					</td>
					<td>
						<input name='drClaimsPic[${i.index}].bigUrl' value="${v.bigUrl}" type="hidden"/>
						<input type='file' name='claimsFiles' onchange="updateVerifyImage(this)" style='width:240px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>
					</td>
		   	   		<td>
			   	   		<div class='btn-group'>
				   	   		<a href='javascript:void(0)' data-value="${v.bigUrl}" class='easyui-linkbutton' iconCls='icon-search' onclick="updatePreviewImage(this)">查看</a>
				   	   		<a href='javascript:void(0)' data-value="${v.id}" class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'>删除</a>
			   	   		</div>
		   	   		</td>
		   	   	</tr>
				</c:forEach>
			</table>
		</div>
		<div id="projectUpdBtn">
			<a href="javascript:void(0)" class="icon-add" <c:if test="${drClaimsLoan.isAuditEdit != 1}">onclick="updProject();"</c:if>></a>
		</div>
		<div id="picAddBtn">
			<a href="javascript:void(0)" class="icon-add" <c:if test="${drClaimsLoan.isAuditEdit != 1}">onclick="addPic();"</c:if> ></a>
		</div>
		<div id="shareholderBtn">
			<a href="javascript:void(0)" class="icon-add" <c:if test="${drClaimsLoan.isAuditEdit != 1}">onclick="addShareholder();"</c:if> ></a>
		</div>
		<div id="guaranteeBtn">
			<a href="javascript:void(0)" class="icon-add" <c:if test="${drClaimsLoan.isAuditEdit != 1}">onclick="addGuarantee();"</c:if> ></a>
		</div>
		
		
		<div style="text-align:center;padding:30px 60px 10px 0px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrBillInfo();">提交审核</a>
		</div>
		
		<!-- 显示图片 -->
		<div id="picShow" class="easyui-dialog" title="查看图片" style="text-align:center;height:auto;width:625px;padding:10px;;top:40%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:false,maximizable:false">
			<div id="imgbig">
				<img id="imglook" src="" width="600px" height="400px" />
			</div>
		</div>
		<!-- 显示图片 -->
	</form>
</div>
<script type="text/javascript">
	
		//判断担保
		function judgeGuarantee(rec){
			var isPawn = $("#isPawn").combobox('getValue');
			var isGuarantee = $("#isGuarantee").combobox('getValue');
			var isCompanyGuarantee = $("#isCompanyGuarantee").combobox('getValue');
			var isFinanc = $("#isFinanc").combobox('getValue');
			if(isPawn == '1'){//是否有抵/质押物
				$("#pawnMatter").textbox({required:true});
				$("#assessAmount").numberbox({required:true});
			}else{
				$("#pawnMatter").textbox({required:false});
				$("#assessAmount").numberbox({required:false});
			}
			
			if(isGuarantee=='1'){//是否有个人担保
				$('#guarantee').textbox({required:true});
				$('#singleGuaranteeAmount').numberbox({required:true});
			}else{
				$('#guarantee').textbox({required:false});
				$('#singleGuaranteeAmount').numberbox({required:false});
			}
			
			if(isCompanyGuarantee=='1'){//是否有公司保证
				$('#companyGuarantee').textbox({required:true});
				$('#companyGuaranteeAmount').numberbox({required:true});
			}else{
				$('#companyGuarantee').textbox({required:false});
				$('#companyGuaranteeAmount').numberbox({required:false});
			}
			
			if(isFinanc=='1'){//是否有融资性担保
				$('#finanCompanyguarantee').textbox({required:true});
				$('#guaranteeAmount').numberbox({required:true});
			}else{
				$('#finanCompanyguarantee').textbox({required:false});
				$('#guaranteeAmount').numberbox({required:false});
			}
		}
		 //判断担保
		function judgeGuaranteeS(rec){
			var isPawnS = $("#isPawnS").combobox('getValue');
			var isGuaranteeS = $("#isGuaranteeS").combobox('getValue');
			var isCompanyGuaranteeS = $("#isCompanyGuaranteeS").combobox('getValue');
			var isFinancS = $("#isFinancS").combobox('getValue');
			if(isPawnS == '1'){//是否有抵/质押物
				$("#pawnMatterS").textbox({required:true});
				$("#assessAmountS").numberbox({required:true});
			}else{
				$("#pawnMatterS").textbox({required:false});
				$("#assessAmountS").numberbox({required:false});
			}
			
			if(isGuaranteeS=='1'){//是否有个人担保
				$('#guaranteeS').textbox({required:true});
				$('#singleGuaranteeAmountS').numberbox({required:true});
			}else{
				$('#guaranteeS').textbox({required:false});
				$('#singleGuaranteeAmountS').numberbox({required:false});
			}
			
			if(isCompanyGuaranteeS=='1'){//是否有公司保证
				$('#companyGuaranteeS').textbox({required:true});
				$('#companyGuaranteeAmountS').numberbox({required:true});
			}else{
				$('#companyGuaranteeS').textbox({required:false});
				$('#companyGuaranteeAmountS').numberbox({required:false});
			}
			
			if(isFinancS=='1'){//是否有融资性担保
				$('#finanCompanyguaranteeS').textbox({required:true});
				$('#guaranteeAmountS').numberbox({required:true});
			}else{
				$('#finanCompanyguaranteeS').textbox({required:false});
				$('#guaranteeAmountS').numberbox({required:false});
			}
		} 
		
		
		$('#loanAmount').numberbox({
			onChange: function(newValue,oldValue){//当数值发生改变是 鼠标离开函数执行
				addMoney();
			}
		});
		$('#serviceRate').numberbox({
			onChange: function(newValue,oldValue){//当数值发生改变是 鼠标离开函数执行
				addMoney();
			}
		});
		function addMoney(){
			var amount = $('#loanAmount').numberbox('getValue');
			var serviceRate=$('#serviceRate').numberbox('getValue');
			var serviceCharge = amount *  serviceRate / 100;
			$('#serviceCharge').numberbox('setValue',serviceCharge);
		}
		
		$('#creditDate').datebox({//当选中一个日期时触发函数
			onChange: function(date){
		    	DateDiff();
		    }
		});
		$('#overDate').datebox({//当选中一个日期时触发函数
			onChange: function(date){
		    	DateDiff();
		    }
		});;
		function DateDiff(){//量日期相减
			var dc = $('#creditDate').datebox('getValue');
			var oc = $('#overDate').datebox('getValue');
			  var day = 24 * 60 * 60 *1000;
			try{  
			    var dateArr = dc.split("-");
			  var checkDate = new Date();
			    checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);
			  var checkTime = checkDate.getTime();
			  var dateArr2 = oc.split("-");
			  var checkDate2 = new Date();
			    checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);
			  var checkTime2 = checkDate2.getTime();
			  var cha = (checkTime2 - checkTime)/day +1; 
			$('#financingDeadline').numberbox('setValue',cha);
			}catch(e){
			  return false;
			}
		}
	if('${drClaimsLoan.isAuditEdit}'=='1'){
		$(document).ready(function(){
	 		$("input").attr("disabled",true);
	 		$(".easyui-combobox").combobox('disable');
	 		$(".easyui-datebox").datebox('disable');
	 		$(".easyui-textbox").datebox('disable');
	 		$("#picTable input").attr("disabled",true);
	 		
	 			 		
	 		$(".isEdit input").attr("disabled",false);
	 		$(".isEdit .easyui-combobox").combobox("enable");
	 		$(".isEdit .easyui-datebox").datebox("enable"); 
	 		$(".isEdit .easyui-textbox").datebox('enable');
	 	});
	}
	
	
 	function addDrBillInfo(){
 		//judgeGuarantee();
		var flag = $("#updateDrClaimsInfoForm").form('enableValidation').form('validate');
		debugger;
		if(flag){
			$("#updateDrClaimsInfoForm").ajaxSubmit({
				url:"${apppath}/claims/updateClaimsInfo.do",
				type:"POST",
				data:$("#updateDrClaimsInfoForm").serialize(),
	      		success:function(data){
	      		    var resultJson = jQuery.parseJSON(data);
					var resultJson = eval(resultJson);
					if(resultJson.success){
						$.messager.alert("提示信息",resultJson.msg,"",function(){
							var currTab = parent.$('#main-center').tabs('getTab', "债权管理");
							if(currTab != null){
								var content = '<iframe scrolling="no" frameborder="0"  src="../claims/toDrClaimsLoanList.do" style="width:100%;height:100%;"></iframe>';  
								parent.$('#main-center').tabs('update', {
									tab: currTab,
									options: {
										content: content  // 新内容的URL
									}
								});
							}
							parent.$('#main-center').tabs('close','债权修改');
						});
						$("#updateDrClaimsInfoForm").resetForm(); // 提交后重置表单
					}else{
						$.messager.alert("提示信息",resultJson.msg);
					}            	

				}
	        });
	        return false; // 阻止表单自动提交事件

		}
	} 
	var k = ${drClaimsPic.size()};
	//添加图片
	function addPic(){
		var tr = "<tr><td>"+
			"<input name=\"drClaimsPic["+k+"].name\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"+
			"</td>" +
			"<td>" +
			"<input name=\"drClaimsPic["+k+"].bigUrl\"  type='hidden'/>"+
			"<input type='file' name='claimsFiles' onchange=\"updateVerifyImage(this)\" style='width:240px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>"+
			"</td>" +
   	   		"<td><div class='btn-group'>" +
   	   		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-search' onclick=\"updatePreviewImage(this)\">"+
   	   		"查看</a>"+" "+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'> " +
   	   		"删除</a></div></td></tr>"; 
		$("#picTable").append(tr);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		}); 
		k++;
	}
	
	var i = ${drClaimsShareholder.size()}+1;
	//添加股东情况
	function addShareholder(){
   	   	var table ="<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>"+
   	   	"<table align='center' cellspacing='1' cellpadding='1' style='width:auto;margin-bottom: 10px'>"+
		"<tr><td align='left'>名称：</td>"+
		"<td><input name=\"drClaimsShareholder["+i+"].name\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/></td>"+
		"<td align='left'>性别：</td>"+
		"<td>"+
		"<select name=\"drClaimsShareholder["+i+"].sex\" style='width: 175px' class='easyui-combobox'>"+
		"<c:forEach items='${sex}' var='map'><option value='${map.key }'>${map.value }</option></c:forEach>"+
		"</select>"+
		"</td>"+
		"<td align='left'>手机号码：</td>"+
		"<td><input name=\"drClaimsShareholder["+i+"].phone\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[11]'\"/></td>"+
		"</tr>"+
		"<tr>"+
		"<td align='left'>股东类别：</td>"+
		"<td>"+
		"<select name=\"drClaimsShareholder["+i+"].type\" style='width: 175px' class='easyui-combobox'>"+
		"<c:forEach items='${shareholderType}' var='map'>"+
		"<option value='${map.key }'>${map.value }</option>"+
		"</c:forEach>"+
		"</select>"+
		"</td>"+
		"<td align='left'>证件类型：</td>"+
		"<td>"+
		"<select name=\"drClaimsShareholder["+i+"].certificateType\" style='width: 175px' class='easyui-combobox'>"+
		"<c:forEach items='${certificateType}' var='map'>"+
		"<option value='${map.key }'>${map.value }</option>"+
		"</c:forEach>"+
		"</select>"+
		"</td>"+
		"<td align='left'>证件号码：</td>"+
		"<td class='isEdit'>"+
		"<input name=\"drClaimsShareholder["+i+"].certificateNo\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"+
		"</td>"+
		"</tr>"+
		"<tr>"+
		"<td align='left'>出资额度(万元)：</td>"+
		"<td>"+
		"<input name=\"drClaimsShareholder["+i+"].contributionLines\" type='text' class='easyui-numberbox' data-options='min:0,max:100000000,precision:6'/>"+
		"</td>"+
		"<td align='left'>出资方式：</td>"+
		"<td>"+
		"<input name=\"drClaimsShareholder["+i+"].contributionType\" type='text' class='easyui-numberbox' data-options=\"validType:'maxLength[100]'\"/>"+
		"</td>"+
		"<td align='left'>投资比例：</td>"+
		"<td>"+
		"<input name=\"drClaimsShareholder["+i+"].investRate\" type='text' class='easyui-numberbox' data-options='min:0,max:100,precision:2'/>"+
		"</td>"+
		"</tr>"+
		"<tr>"+
		"<td align='left'>实缴金额（万元）：</td>"+
		"<td>"+
		"<input name=\"drClaimsShareholder["+i+"].amountPaidIn\"  type='text' class='easyui-numberbox' data-options='required:false,min:0,max:100000000,precision:6'/>"+
		"</td>"+
		"</tr>"+
		"<tr>"+
		"<td colspan='6' align='right'>"+
		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delShareholder(this);'>删除</a>"+
		"</td>"+
		"</tr></table>" ; 
		/*var table ="<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>"+
   	   	 "<table align='center' cellspacing='1' cellpadding='1' style='width:auto;'>"+
			"<tr><td align='left'>法人姓名：</td>"+
				"<td><input name=\"drClaimsShareholder["+i+"].name\" data-options=\"validType:'maxLength[10]'\" type='text' class='easyui-textbox'/></td>"+
				"<td align='left'>性别：</td><td>"+
				"<select name=\"drClaimsShareholder["+i+"].sex\" style='width: 175px' class='easyui-combobox'>"+
				"<c:forEach items='${sex}' var='map'><option value='${map.key }'>${map.value }</option></c:forEach>"+
				"</select>"+
				"</td><td align='left'>手机号码：</td>"+
				"<td><input name=\"drClaimsShareholder["+i+"].phone\" data-options=\"validType:'maxLength[11]'\" type='text' class='easyui-textbox'/></td>"+
			"</tr>"+
			"<tr><td align='left'>股东类别：</td>"+
				"<td><select name=\"drClaimsShareholder["+i+"].type\" style='width: 175px' class='easyui-combobox'>"+
				"<c:forEach items='${shareholderType}' var='map'><option value='${map.key }'>${map.value }</option></c:forEach>"+
				"</select>"+
				"</td>"+
				"<td align='left'>证件类型：</td>"+
				"<td>"+
				"<select name=\"drClaimsShareholder["+i+"].certificateType\" style='width: 175px' class='easyui-combobox'>"+
				"<c:forEach items='${certificateType}' var='map'><option value='${map.key }'>${map.value }</option>"+
				"</c:forEach></select>"+
				"</td>"+
				"<td align='left'>证件号码：</td>"+
				"<td><input name=\"drClaimsShareholder["+i+"].certificateNo\" data-options=\"validType:'maxLength[50]'\" type='text' class='easyui-textbox'/></td>"+
			"</tr>"+
			"<tr><td align='left'>出资额度：</td>"+
				"<td><input name=\"drClaimsShareholder["+i+"].contributionLines\" data-options=\"min:0,max:100000000,precision:2\" type='text' class='easyui-numberbox'/></td>"+
				"<td align='left'>出资方式：</td>"+
				"<td><input name=\"drClaimsShareholder["+i+"].contributionType\" data-options=\"validType:'maxLength[100]'\" type='text' class='easyui-textbox'/></td>"+
				"<td align='left'>投资比例：</td>"+
				"<td><input name=\"drClaimsShareholder["+i+"].investRate\" data-options=\"min:0,max:100,precision:2\" type='text' class='easyui-numberbox'/></td>"+
			"</tr>"+
			"<tr>"+
				"<td colspan='6' align='right'>"+
				"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delShareholder(this);'>删除</a>"+
				"</td>"+
			"</tr></table>" ;*/
		$("#shareholderTable").append(table);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		}); 
/* 		$('#shareholder').combobox({    
		});  */
		i++;
	}
	
	var j = ${drClaimsGuarantee.size()}+1;
	//添加担保情况
	function addGuarantee(){
	 	var table ="<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>"+
			"<table class='isEdit' align='center' cellspacing='1' cellpadding='1' style='width:auto;margin-bottom: 10px'>"+
			"<tr align='right'>"+
			"<td align='right'>担保方式：</td>"+
			"<td>"+
			"<select name=\"drClaimsGuarantee["+j+"].pawnWay\" style='width: 175px' class='easyui-combobox'>"+
			"<c:forEach items='${pawnWay}' var='map'>"+
			"<option value='${map.key }'>${map.value }</option>"+
			"</c:forEach>"+
			"</select>"+
			"</td>"+
			"</tr>"+
			"<tr class='isPawn'>"+
			"<td align='left'>是否有抵/质押物：</td>"+
			"<td>"+
			"<select name=\"drClaimsGuarantee["+j+"].isPawn\" style='width: 175px' class='easyui-combobox isPawnbox'>"+
			"<c:forEach items='${whether}' var='map'>"+
			"<option value='${map.key }'>${map.value }</option>"+
			"</c:forEach>"+
			"</select>"+
			"</td>"+
			"<td align='right'>抵/质押物：</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].pawnMatter\" type='text' class='easyui-textbox myinputT' data-options=\"validType:'maxLength[10]'\"/>"+
			"</td>"+
			"<td align='left'>抵/质押物评估值（万元）：</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].assessAmount\" type='text' class='easyui-numberbox myinputN' data-options='min:0,max:10000,precision:6'/>"+
			"</td>"+
			"</tr>"+
			"<tr class='isGuarantee'>"+
			"<td align='right'>是否有个人担保：</td>"+
			"<td>"+
			"<select name=\"drClaimsGuarantee["+j+"].isGuarantee\" style='width: 175px' class='easyui-combobox isGuaranteebox'>"+
			"<c:forEach items='${whether}' var='map'>"+
			"<option value='${map.key }'>${map.value }</option>"+
			"</c:forEach>"+
			"</select>"+
			"</td>"+
			"<td align='right'>个人担保人名称：</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].guarantee\" type='text' class='easyui-textbox myinputT'/>"+
			"</td>"+
			"<td align='right'>个人保证金额（万元）:</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].singleGuaranteeAmount\" type='text' class='easyui-numberbox myinputN' data-options='min:0,max:100000000,precision:6'/>"+
			"</td>"+
			"</tr>"+
			"<tr class='isCompanyGuarantee'>"+
			"<td align='right'>是否有公司保证：</td>"+
			"<td>"+
			"<select name=\"drClaimsGuarantee["+j+"].isCompanyGuarantee\" style='width: 175px' class='easyui-combobox isCompanyGuaranteebox'>"+
			"<c:forEach items='${whether}' var='map'>"+
			"<option value='${map.key }'>${map.value }</option>"+
			"</c:forEach>"+
			"</select>"+
			"<td align='right'>公司保证名称：</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].companyGuarantee\" type='text' class='easyui-textbox myinputT' data-options=\"validType:'maxLength[10]'\"/>"+
			"</td>"+
			"<td align='right'>公司保证金额（万元）:</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].companyGuaranteeAmount\" type='text' class='easyui-numberbox myinputN' data-options='min:0,max:100000000,precision:6'/>"+
			"</td>"+
			"</tr>"+
			"<tr class='isFinanc'>"+
			"<td align='right'>是否有融资性担保：</td>"+
			"<td>"+
			"<select name=\"drClaimsGuarantee["+j+"].isFinanc\" style='width: 175px' class='easyui-combobox isFinancbox'>"+
			"<c:forEach items='${whether}' var='map'>"+
			"<option value='${map.key }'>${map.value }</option>"+
			"</c:forEach>"+
			"</select>"+
			"</td>"+
			"<td align='right'>融资性担保公司名称：</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].finanCompanyguarantee\" type='text' class='easyui-textbox myinputT' data-options=\"validType:'maxLength[10]'\"/>"+
			"</td>"+
			"<td align='right'>担保公司保证金额（万元）：</td>"+
			"<td>"+
			"<input name=\"drClaimsGuarantee["+j+"].guaranteeAmount\" type='text' class='easyui-numberbox myinputN' data-options='min:0,max:100000000,precision:6'/>"+
			"</td>"+
			"</tr>"+
			"<tr>"+
			"<td colspan='6' align='right'>"+
			"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delGuarantee(this);'>删除</a>"+
			"</td>"+
			"</tr></table>";
   	   /* 	var table ="<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>"+
   	   	"<table align='center' cellspacing='1' cellpadding='1' style='width:auto;margin-bottom: 10px'>"+
			"<tr><td align='left'>是否有抵/质押物：</td>"+
				"<td><select name=\"drClaimsGuarantee["+j+"].isPawn\" style='width: 175px' class='easyui-combobox'>"+
				"<option value='0'>否</option><option value='1'>是</option></select></td>"+
				"<td align='left'>抵/质押物类别：</td>"+
				"<td><select name=\"drClaimsGuarantee["+j+"].pawnType\" style='width: 175px' class='easyui-combobox'>"+
				"<c:forEach items='${pawnType}' var='map'>"+
				"<option value='${map.key }'>${map.value }</option></c:forEach></select></td>"+
				"<td align='left'>评估价值（万元）：</td>"+
				"<td><input name=\"drClaimsGuarantee["+j+"].assessAmount\" data-options=\"min:0,max:10000,precision:6\" type='text' class='easyui-numberbox'/></td>"+
			"</tr>"+
			"<tr><td align='left'>是否有承兑保证：</td>"+
				"<td><select name=\"drClaimsGuarantee["+j+"].isAcceptance\" style='width: 175px' class='easyui-combobox'>"+
				"<option value='0'>否</option><option value='1'>是</option></select></td>"+
				"<td align='left'>保证人名称：</td>"+
				"<td><input name=\"drClaimsGuarantee["+j+"].guarantor\" data-options=\"validType:'maxLength[100]'\" type='text' class='easyui-textbox' /></td>"+
			"</tr>"+
			"<tr><td align='left'>是否有个人担保：</td>"+
				"<td><select name=\"drClaimsGuarantee["+j+"].isGuarantee\" style='width: 175px' class='easyui-combobox'>"+
				"<option value='0'>否</option><option value='1'>是</option></select></td>"+
				"<td align='left'>担保类别：</td>"+
				"<td><select name=\"drClaimsGuarantee["+j+"].guaranteeType\" style='width: 175px' class='easyui-combobox'>"+
				"<c:forEach items='${guaranteeType}' var='map'><option value='${map.key }'>${map.value }</option>"+
				"</c:forEach></select>"+
				"</td>"+
				"<td align='left'>个人担保人名称：</td>"+
				"<td><input name=\"drClaimsGuarantee["+j+"].guarantee\" type='text' data-options=\"validType:'maxLength[10]'\" class='easyui-textbox'/></td>"+
			"</tr>"+
			"<tr>"+
				"<td align='left'>是否有融资性担保：</td>"+
				"<td><select name=\"drClaimsGuarantee["+j+"].isFinanc\" style='width: 175px' class='easyui-combobox'>"+
				"<option value='0'>否</option><option value='1'>是</option></select>"+
				"</td>"+
				"<td align='left'>担保类别：</td>"+
				"<td><select name=\"drClaimsGuarantee["+j+"].financType\" style='width: 175px' class='easyui-combobox'>"+
				"<c:forEach items='${guaranteeType}' var='map'><option value='${map.key }'>${map.value }</option></c:forEach></select>"+
				"</td>"+
				"<td align='left'>担保金额：</td>"+
				"<td><input name=\"drClaimsGuarantee["+j+"].guaranteeAmount\" data-options=\"min:0,max:100000000,precision:2\" type='text' class='easyui-numberbox'/>"+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td colspan='6' align='right'>"+
				"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delGuarantee(this);'>删除</a>"+
				"</td>"+
		"</tr></table>"; */
		$("#guaranteeTable").append(table);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		}); 
/*  		$('.easyui-combobox').combobox({    
		});   */
		j++;
		
		$('.isPawnbox').on('click',function(){
			var $this = $(this);
			if ($this.val() == 1) {
				$this.parents('.isPawn').find('.myinputT').textbox({required:true});
				$this.parents('.isPawn').find('.myinputN').numberbox({required:true});
			} else {
				$this.parents('.isPawn').find('.myinputT').textbox({required:false});
				$this.parents('.isPawn').find('.myinputN').numberbox({required:false});
			}
		});
		$('.isGuaranteebox').on('click',function(){
			var $this = $(this);
			if($this.val() == 1){
				$this.parents('.isGuarantee').find('.myinputT').textbox({required:true});
				$this.parents('.isGuarantee').find('.myinputN').numberbox({required:true});
			} else {
				$this.parents('.isGuarantee').find('.myinputT').textbox({required:false});
				$this.parents('.isGuarantee').find('.myinputN').numberbox({required:false});
			}
		});
		$('.isCompanyGuaranteebox').on('click',function(){
			var $this = $(this);
			if($this.val() == 1){
				$this.parents('.isCompanyGuarantee').find('.myinputT').textbox({required:true});
				$this.parents('.isCompanyGuarantee').find('.myinputN').numberbox({required:true});
			} else {
				$this.parents('.isCompanyGuarantee').find('.myinput').textbox({required:false});
				$this.parents('.isCompanyGuarantee').find('.myinputN').numberbox({required:false});
			}
		});
		$('.isFinancbox').on('click',function(){
			var $this = $(this);
			if($this.val() == 1){
				$this.parents('.isFinanc').find('.myinputT').textbox({required:true});
				$this.parents('.isFinanc').find('.myinputN').numberbox({required:true});
			} else {
				$this.parents('.isFinanc').find('.myinputT').textbox({required:false});
				$this.parents('.isFinanc').find('.myinputN').numberbox({required:false});
			}
		});
	}
	
	var z=${drClaimsProject.size()}+1;
	//审核项目
	function updProject(){
		var tr = "<tr><td>"+
			"审核内容：<input name=\"drClaimsProject["+z+"].name\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"+
			"</td>" +
   	   		"<td><div class='btn-group'>" +
   	   		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delProject(this)'> " +
   	   		"删除</a></div></td></tr>"; 
		$("#updProjectTable").append(tr);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		});
		z++;
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
        $("div").data("blah", "hello");  // blah设置为hello
        $(ths).parents('tr').find('a').eq(0).data("value", "undefined");
    	return true;  
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
			$('#picShow').dialog('open');
			return true;
		}else{
			$("#imglook").attr("src",$(ths).data("value"));
			$('#picShow').dialog('open');
		}
	}	
	
	function delProject(obj){
		$(obj).parent().parent().parent().remove();
	}
	function delPic(obj){
		$(obj).parent().parent().parent().remove();
	}
	function delShareholder(obj){
		$(obj).parent().parent().parent().remove();
	}
	function delGuarantee(obj){
		$(obj).parent().parent().parent().remove();
	}
	
	//显示placeholder
	jQuery('input[placeholder]').placeholder();
</script>

</body>
</html>
