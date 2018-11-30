<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>聚胜财富 - 上海聚胜投资管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
<div id="${tabid }">
	<div title="<center>企业客户基本信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">企业名称：</td>
				<td colspan="5">
					<input name="drClaimsCustomer.companyName" value="${drClaimsCustomer.companyName}" style="width: 655px" type="text" class="easyui-textbox"/>
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
				<td align="left">企业名称协议显示：</td>
				<td colspan="5">
					<input name="companyNameProtocolShow" value="${drClaimsCustomer.companyNameProtocolShow}" style="width: 655px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">法定代表人：</td>
				<td>
					<input name="drClaimsCustomer.name" value="${drClaimsCustomer.name}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsCustomer.sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsCustomer.sex== map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsCustomer.phone" value="${drClaimsCustomer.phone}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">证件类型：</td>
				<td>
					<select name="drClaimsCustomer.certificateType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${certificateType}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsCustomer.certificateType== map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件号码：</td>
				<td colspan="3">
					<input name="drClaimsCustomer.certificateNo" value="${drClaimsCustomer.certificateNo}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">营业执照号码：</td>
				<td colspan="3">
					<input name="drClaimsCustomer.businessNo" value="${drClaimsCustomer.businessNo}" style="width: 500px" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">行业类别：</td>
				<td>
					<input name="drClaimsCustomer.industryType" value="${drClaimsCustomer.industryType}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">企业联系号码：</td>
				<td>
					<input name="drClaimsCustomer.companyPhone" value="${drClaimsCustomer.companyPhone}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">企业邮箱：</td>
				<td colspan="3">
					<input name="drClaimsCustomer.companyMail" value="${drClaimsCustomer.companyMail}" style="width: 410px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">主营业务：</td>
				<td colspan="5">
					<input name="drClaimsCustomer.mainBusiness" value="${drClaimsCustomer.mainBusiness}" style="width: 655px" type="text" class="easyui-textbox" data-options="validType:'maxLength[200]'"/>
				</td>
			</tr>
			<tr>
				<td align="left">通讯地址：</td>
				<td colspan="5">
					<input name="drClaimsCustomer.address" value="${drClaimsCustomer.address}" style="width: 655px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="shareholderTable" title="<center>主要股东信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" 
	data-options="collapsible:true">
		<c:if test="${drClaimsShareholder.size() == 0}">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
			<tr>
				<td align="left">名称：</td>
				<td>
					<input name="drClaimsShareholder[0].name" type="text" class="easyui-textbox"/>
				</td>
				
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsShareholder[0].sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsShareholder[0].phone" type="text" class="easyui-textbox"/>
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
				<td>
					<input name="drClaimsShareholder[0].certificateNo" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">出资额度（万元）：</td>
				<td>
					<input name="drClaimsShareholder[0].contributionLines" type="text" class="easyui-textbox" data-options="required:false,min:0,max:100000000,precision:2"/>
				</td>
				<td align="left">出资方式：</td>
				<td>
					<input name="drClaimsShareholder[0].contributionType" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">投资比例：</td>
				<td>
					<input name="drClaimsShareholder[0].investRate" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">实缴金额（万元）：</td>
				<td>
					<input name="drClaimsShareholder[0].amountPaidIn" value="${drClaimsShareholder[0].amountPaidIn}" type="text" class="easyui-numberbox" data-options="required:false,min:0,max:100000000,precision:6"/>
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
				<td align="left">名称：</td>
				<td>
					<input name="drClaimsShareholder[${i.index }].name" value="${v.name}" type="text" class="easyui-textbox"/>
				</td>
				
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsShareholder[${i.index }].sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }' <c:if test="${map.key ==v.sex }">selected='selected'</c:if> >${map.value }</option> 
						</c:forEach>
					</select>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsShareholder[${i.index }].phone" value="${v.phone}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">股东类别：</td>
				<td>
					<select name="drClaimsShareholder[${i.index }].type" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${shareholderType}" var="map">
							<option value='${map.key }'  <c:if test="${map.key ==v.type }">selected='selected'</c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件类型：</td>
				<td>
					<select name="drClaimsShareholder[${i.index }].certificateType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${certificateType}" var="map">
							<option value='${map.key }' <c:if test="${map.key ==v.certificateType }">selected='selected'</c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件号码：</td>
				<td>
					<input name="drClaimsShareholder[${i.index }].certificateNo" 	value="${v.certificateNo}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">出资额度（万元）：</td>
				<td>
					<input name="drClaimsShareholder[${i.index }].contributionLines" value="${v.contributionLines}" type="text" class="easyui-textbox" data-options="required:false,min:0,max:100000000,precision:2"/>
				</td>
				<td align="left">出资方式：</td>
				<td>
					<input name="drClaimsShareholder[${i.index }].contributionType"	 value="${v.contributionType}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">投资比例：</td>
				<td>
					<input name="drClaimsShareholder[${i.index }].investRate" value="${v.investRate}"  type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">实缴金额（万元）：</td>
				<td>
					<input name="drClaimsShareholder[${i.index }].amountPaidIn" value="${v.amountPaidIn}" type="text" class="easyui-numberbox" data-options="required:false,min:0,max:100000000,precision:6"/>
				</td>
			</tr>
		</table>
		</c:forEach>
	</div>
	
	<div title="<center>融资项目基本信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">产品名称：</td>
				<td>
					<input name="name" value="${drClaimsLoan.name}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">融资合同编号：</td>
				<td>
					<input name="no" value="${drClaimsLoan.no}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">贷款用途：</td>
				<td>
					<input name="loanUse" value="${drClaimsLoan.loanUse}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">贷款金额(万元)：</td>
				<td>
					<input name="loanAmount" value="${drClaimsLoan.loanAmount}" class="easyui-textbox"/>
				</td>
				<td align="left">项目融资服务费率(%)：</td>
				<td>
					<input name="serviceRate" value="${drClaimsLoan.serviceRate}" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
				<td align="left">项目融资服务费(万元)：</td>
				<td>
					<input name="serviceCharge" value="${drClaimsLoan.serviceCharge}" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="left">年利率(%)：</td>
				<td>
					<input name="rate" value="${drClaimsLoan.rate}" class="easyui-textbox"/>
				</td>
				<td align="left">还款方式：</td>
				<td>
					<select name="repayType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${repayType}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsLoan.repayType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">还款周期：</td>
				<td>
					<select name="repayDeadline" style="width: 175px" class="easyui-combobox">
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
						<input name="endDate" value="${endDate}" class="easyui-datebox" />
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
				<td align="left">贷款户名：</td>
				<td>
					<input name="loanName" value="${drClaimsLoan.loanName}" class="easyui-textbox"/>
				</td>
				<td align="left">开户行名称：</td>
				<td>
					<input name="bankName" value="${drClaimsLoan.bankName}" class="easyui-textbox"/>
				</td>
				<td align="left">银行账号：</td>
				<td>
					<input name="bankNo" value="${drClaimsLoan.bankNo}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">开户行地址：</td>
				<td>
					<input name="bankAddress" value="${drClaimsLoan.bankAddress}" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="guaranteeTable" title="<center>担保情况</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" 
	data-options="collapsible:true">
		<c:if test="${drClaimsGuarantee.size() == 0}">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
			<tr align="right">
				<td align="right">担保方式：</td>
				<td>
					<select name="drClaimsGuarantee[0].pawnWay" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${pawnWay}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td align="right">是否有抵/质押物：</td>
				<td>
					<select name="drClaimsGuarantee[0].isPawn" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">抵/质押物：</td>
				<td>
					<input name="drClaimsGuarantee[0].pawnMatter" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
				</td>
				<td align="right">抵/质押物评估值（万元）：</td>
				<td>
					<input name="drClaimsGuarantee[0].assessAmount" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有个人担保：</td>
				<td>
					<select name="drClaimsGuarantee[0].isGuarantee" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">个人担保人名称：</td>
				<td>
					<input name="drClaimsGuarantee[0].guarantee" type="text" class="easyui-textbox"/>
				</td>
				<td align="right">个人保证金额（万元）:</td>
				<td>
					<input name="drClaimsGuarantee[0].singleGuaranteeAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有公司保证：</td>
				<td>
					<select name="drClaimsGuarantee[0].isCompanyGuarantee" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				
				<td align="right">公司保证名称：</td>
				<td>
					<input name="drClaimsGuarantee[0].companyGuarantee" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
				</td>
				<td align="right">公司保证金额（万元）:</td>
				<td>
					<input name="drClaimsGuarantee[0].companyGuaranteeAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有融资性担保：</td>
				<td>
					<select name="drClaimsGuarantee[0].isFinanc" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">融资性担保公司名称：</td>
					<td>
						<input name="drClaimsGuarantee[0].finanCompanyguarantee" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
				<td align="right">担保公司保证金额（万元）：</td>
				<td>
					<input name="drClaimsGuarantee[0].guaranteeAmount" type="text" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
		</c:if>
		<c:forEach items="${drClaimsGuarantee}" var="v" varStatus="i">
		<c:if test="${i.index != 0}">
			<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>
		</c:if>
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
			<tr align="right">
				<td align="right">担保方式：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index }].pawnWay"   style="width: 175px" class="easyui-combobox">
						<c:forEach items="${pawnWay}" var="map">
							<option value='${map.key }'  <c:if test="${map.key ==v.pawnWay }">selected='selected'</c:if>  >${map.value }</option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td align="right">是否有抵/质押物：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index }].isPawn" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${map.key ==v.isPawn }">selected='selected'</c:if>   >${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">抵/质押物：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index }].pawnMatter" value="${v.pawnMatter }" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
				</td>
				<td align="right">抵/质押物评估值（万元）：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index }].assessAmount" value="${v.assessAmount }" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有个人担保：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index }].isGuarantee"  style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${map.key ==v.isGuarantee }">selected='selected'</c:if>   >${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">个人担保人名称：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index }].guarantee"  value="${v.guarantee }" type="text" class="easyui-textbox"/>
				</td>
				<td align="right">个人保证金额（万元）:</td>
				<td>
					<input name="drClaimsGuarantee[${i.index }].singleGuaranteeAmount"  value="${v.singleGuaranteeAmount }" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有公司保证：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index }].isCompanyGuarantee" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${map.key ==v.isCompanyGuarantee }">selected='selected'</c:if>  >${map.value }</option>
						</c:forEach>
					</select>
				
				<td align="right">公司保证名称：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index }].companyGuarantee" value="${v.companyGuarantee }" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
				</td>
				<td align="right">公司保证金额（万元）:</td>
				<td>
					<input name="drClaimsGuarantee[${i.index }].companyGuaranteeAmount" value="${v.companyGuaranteeAmount }" type="text" class="easyui-numberbox" data-options="min:0,max:100000000,precision:6"/>
				</td>
			</tr>
			<tr>
				<td align="right">是否有融资性担保：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index }].isFinanc" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'  <c:if test="${map.key ==v.isFinanc }">selected='selected'</c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">融资性担保公司名称：</td>
					<td>
						<input name="drClaimsGuarantee[${i.index }].finanCompanyguarantee" value="${v.finanCompanyguarantee }" type="text" class="easyui-textbox" data-options="validType:'maxLength[10]'"/>
					</td>
				<td align="right">担保公司保证金额（万元）：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index }].guaranteeAmount" value="${v.guaranteeAmount }" type="text" class="easyui-textbox"/>
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
					<input name="drClaimsBill.drawer" value="${drClaimsBill.drawer}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">收款人：</td>
				<td>
					<input name="drClaimsBill.payee" value="${drClaimsBill.payee}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">承兑人：</td>
				<td>
					<input name="drClaimsBill.acceptor" value="${drClaimsBill.acceptor}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">票据金额（万元）：</td>
				<td>
					<input name="drClaimsBill.amount" value="${drClaimsBill.amount}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">票据号码：</td>
				<td>
					<input name="drClaimsBill.number" value="${drClaimsBill.number}" type="text" class="easyui-textbox"/>
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
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">企业名称：</td>
				<td colspan="5">
					<input name="drClaimsFinanc.companyName" value="${drClaimsFinanc.companyName}" style="width: 700px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">法定代表人：</td>
				<td>
					<input name="drClaimsFinanc.name" value="${drClaimsFinanc.name}" type="text" class="easyui-textbox" />
				</td>
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsFinanc.sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsFinanc.sex == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsFinanc.phone" value="${drClaimsFinanc.phone}" type="text" class="easyui-textbox"/>
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
					<input name="drClaimsFinanc.certificateNo" value="${drClaimsFinanc.certificateNo}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">行业类别：</td>
				<td>
					<input name="drClaimsFinanc.industryType" value="${drClaimsFinanc.industryType}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">营业执照号码：</td>
				<td>
					<input name="drClaimsFinanc.businessNo" value="${drClaimsFinanc.businessNo}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">企业联系号码：</td>
				<td>
					<input name="drClaimsFinanc.companyPhone" value="${drClaimsFinanc.companyPhone}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">企业邮箱：</td>
				<td colspan="3">
					<input name="drClaimsFinanc.companyMail" value="${drClaimsFinanc.companyMail}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">注册资金：</td>
				<td>
					<input name="drClaimsFinanc.registerFund" value="${drClaimsFinanc.registerFund}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">实缴金额：</td>
				<td>
					<input name="drClaimsFinanc.registerCurrency" value="${drClaimsFinanc.registerCurrency}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">成立日期：</td>
				<td>
					<input name="drClaimsFinanc.establishDate" value="${establishDate}" class="easyui-datebox" />
				</td>
			</tr>
			<tr>
				<td align="left">通讯地址：</td>
				<td colspan="5">
					<input name="drClaimsFinanc.address" value="${drClaimsFinanc.address}" style="width: 700px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
	</div>
	<div title="<center>审核项目</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center'">
		<table align="center" cellspacing="1" cellpadding="1" style="width: auto;">
           	<c:forEach items="${drClaimsProject}" var="v">
           	<tr>
           		<td>
           			审核内容：<input type='text' value="${v.name}" class='easyui-textbox' disabled="disabled"/>
           		</td>
           	</tr>
			</c:forEach>
		</table>
	</div>
	<div title="<center>审核项目</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center'">
		<table align="center" cellspacing="1" cellpadding="1" style="width: auto;">
           	<c:forEach items="${drClaimsProject}" var="v">
           	<tr>
           		<td>
           			审核内容：<input type='text' value="${v.name}" class='easyui-textbox' disabled="disabled"/>
           		</td>
           	</tr>
			</c:forEach>
		</table>
	</div>
	<div title="<center>债权图片</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px"data-options="collapsible:true,region:'center'">
		<table id="picTable" style="width:auto;" align="center" >
			<tr>
				<td>
	                <div id="imgs" class="imgs" style="overflow-x: auto;width: ${drClaimsPic.size()*210}px;">
	            		<c:forEach items="${drClaimsPic}" var="v">
	            		<div style="text-align: center;display: inline-block;">
				        	<img src="${v.bigUrl}" width="200px" height="200px">
				        	<p>图片名称：${v.name}</p>
				        </div>
	  					</c:forEach>
				    </div>
				</td>
			</tr>
		</table>
	</div>
	
	<div title="<center>债权审核</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px"data-options="collapsible:true,region:'center'">
		<table style="width:auto;" align="center" >
			<c:forEach items="${drAuditInfo}" var="v" varStatus="i">
			<tr>
				<td align="left">审核人员：</td>
				<td>
					${v.name}
				</td>
				<td align="left">审核时间：</td>
				<td>
					<fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
	   	   	</tr>
	   	   	<tr>
				<td align="left">审核结果：</td>
				<td colspan="3">
					<c:forEach items="${auditType}" var="map">
						<c:if test="${v.status == map.key}">${map.value }</c:if>
					</c:forEach>
				</td>
	   	   	</tr>
	   	   	<tr>
				<td align="left">审核意见：</td>
				<td colspan="3">
					<input class="easyui-textbox" data-options="multiline:true" value="${v.opinion}" style="height:123px;width: 500px;" disabled="disabled"/>
				</td>
	   	   	</tr>
	   	   	<tr><td colspan="4"><div style="border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;"></div></td></tr>
			</c:forEach>
		</table>
		
		<form id="auditDrAuditInfoForm">
		<input id="DrAuditInfoLid" type="hidden" value="${drClaimsLoan.id }" name="lid"/>
		<table style="width:auto;" align="center" >
	   	   	<tr>
				<td align="left">审核意见：</td>
				<td colspan="3">
					<input id="DrAuditInfoOpinion" class="easyui-textbox" name="opinion" style="height:123px;width: 500px;" data-options="multiline:true,required:true,validType:'maxLength[255]'"/>
				</td>
	   	   	</tr>
	   	   	<tr>
	   	   		<td></td>
	   	   		<td align="center"><a href="javascript:void(0)" class="easyui-linkbutton" data-value="1" onclick="addDrAuditInfo(this);">通过</a></td>
	   	   		<td align="center"><a href="javascript:void(0)" class="easyui-linkbutton" data-value="2" onclick="addDrAuditInfo(this);">驳回</a></td>
	   	   		<c:if test="${drClaimsLoan.isAuditEdit != 1 }">	   	   			
		   	   		<td align="center"><a href="javascript:void(0)" class="easyui-linkbutton" data-value="3" onclick="addDrAuditInfo(this);">作废</a></td>
	   	   		</c:if>
	   	   	</tr>
		</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	layer.use('extend/layer.ext.js', function(){
	    //初始加载即调用，所以需放在ext回调里
	    layer.ext = function(){
	        layer.photosPage({
	            id: 100, //相册id，可选
	            parent:'#imgs'
	        });
	    };
	});
	$(document).ready(function(){
 		$("input").attr("disabled",true);
 		$(".easyui-combobox").combobox('disable');
 		$(".easyui-datebox").datebox('disable');
 	});

	//审核
	function addDrAuditInfo(obj){   	
		var url = "../claims/toAuditDrClaimsLoanList.do";
		var validate = $("#auditDrAuditInfoForm").form("validate");
		if(!validate){
			return false;
		}
		var status =$(obj).data("value");
		var title = "";
		if(status == 1){
			title="确认债权审核通过？";
		}
		if(status == 2){
			title="确认债权审核驳回？";
		}
		if(status == 3){
			title="债权作废后将无法使用，是否确认作废？";
		}
		$.messager.confirm("审核提示", title, function(ensure){
			if(ensure){
		   		$.ajax({
		          	url: "${apppath}/claims/addDrAuditInfo.do?opinion="+$("#DrAuditInfoOpinion").val()+"&lid="+$("#DrAuditInfoLid").val()+"&status="+$(obj).data("value"),
		            type: 'POST',
		      		success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg,"",function(){
								var currTab = parent.$('#main-center').tabs('getTab', "债权审核");
								if(currTab != null){
									var content = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';  
									parent.$('#main-center').tabs('update', {
										tab: currTab,
										options: {
											content: content  // 新内容的URL
										}
									});
								}
								parent.$('#main-center').tabs('close','债权管理-审核');
							});
							$("#auditDrAuditInfoForm").resetForm(); // 提交后重置表单
						}else{
							$.messager.alert("提示信息",result.msg);
						}            	
					}
		        });
		    }
		});
	}
</script>
</body>
</html>
