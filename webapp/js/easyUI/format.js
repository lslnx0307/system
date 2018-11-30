function isDepository(value){
	if(value != null){
		return "是";
	}
	if(value == null || value == ''){
		return "否";
	}
}

//提现通道
function withdrawalTD(value){
	if(value == 1){
		return "连连";
	}
	if(value == 2){
		return "金运通";
	}
	if(value == 3){
		return "存管";
	}
}
//提现渠道
function withdrawalQD(value){
	if(value == 0){
		return "PC";
	}
	if(value == 1){
		return "苹果";
	}
	if(value == 2){
		return "安卓";
	}
	if(value == 3){
		return "H5";
	}
	if(value == 4){
		return "后台";
	}
	if(value == 5){
		return "微信";
	}
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
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate ;
	return currentdate; 
} 

//获取当月1号
function getdateOne(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	/*var strDate = date.getDate(); */
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	/*if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} */
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + '01' ;
	return currentdate; 
} 

//获取当前时间前一天
function getDateBefore(num){
	console.log(num);
	var date ; 
	if(num){
		date = new Date(new Date().getTime() - num*60*60*24*1000);
	}else{
		date = new Date(); 
	}
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate ;
	return currentdate; 
}

//获取当天最早时间
function getstartdate(){
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
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+" 00:00:00";
	return currentdate; 
} 

//获取当天最晚时间
function getenddate(){
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
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+" 23:59:59" ;
	return currentdate; 
}

//促复投红包是否可拆
function iSSplit(value){
	if(value == 1){
		return "是";
	}
	if(value == 0){
		return "否";
	}
}

//计算服务费
function serviceAmount(row){
	if(row.deadline==1){
		return 0;
	}else if(row.deadline==7){
		return parseFloat((100*0.07*row.amount/10000).toPrecision(12));
	}else if(row.deadline==14){
		return 100*0.1*row.amount/10000;
	}else if(row.deadline==30){
		return 100*1.63*row.amount/10000;
	}else if(row.deadline==35){
		return 100*1.9*row.amount/10000;
	}else if(row.deadline==45){
		return 100*2.31*row.amount/10000;
	}else if(row.deadline==60){
		return 100*3*row.amount/10000;
	}else if(row.deadline==70){
		return 100*4.08*row.amount/10000;
	}else if(row.deadline==75){
		return 100*3.65*row.amount/10000;
	}else if(row.deadline==90){
		return 100*4.25*row.amount/10000;
	}else if(row.deadline==150){
		return 100*6.88*row.amount/10000;
	}else if(row.deadline==180){
		return 100*7.5*row.amount/10000;
	} 
}

//担保费
function vouchAmount(row){
	var amount=0;
	if(row.countAmount<=row.loanAmount){//首笔 产品金额*1.5%
		amount=100*1.5*row.amount/10000;
	}else{
		var a=row.countAmount-row.loanAmount;//计算超出多少
		if(a<row.amount){//超出部分
			var b=row.amount-a;//非部分
			amount=100*1.5*b/10000;//超出部分
		}
	}
	return amount;
}

//服务费总额 10.1之前算法
function serviceAmountCount(row){
    var amount=0;
	if(row.countAmount<=row.loanAmount){//首笔 预计费用总额=产品金额*8%
		amount=row.amount*8/100;
	}else{
		var a=row.countAmount-row.loanAmount;//计算超出多少
		if(a>=row.amount){
			amount=row.amount*1*row.deadline/30/100;//全部超出
		}else{
			var b=row.amount-a;//非部分
			amount=b*8/100;
			amount=(a*1*row.deadline/30/100)+amount;//超出部分	
		}	
	}
	return amount; 
}

