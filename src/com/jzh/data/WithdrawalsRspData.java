package com.jzh.data;

import net.sf.json.JSONObject;

public class WithdrawalsRspData extends BaseRspdata{

	private String amt;
	private String login_id;

	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	
	public WithdrawalsRspData(JSONObject message) {
		super();
		if (message.containsKey("login_id")) {this.login_id  = (String)message.get("login_id");}
		if (message.containsKey("amt")) {this.amt  = (String)message.get("amt");}
		
		
		if (message.containsKey("signature")) {this.setSignature((String)message.get("signature"));}		
		if (message.containsKey("mchnt_cd")) {super.setMchnt_cd((String)message.get("mchnt_cd"));}
		if (message.containsKey("mchnt_txn_ssn")) {super.setMchnt_txn_ssn((String)message.get("mchnt_txn_ssn")) ;}
		if (message.containsKey("resp_code")) {super.setResp_code((String)message.get("resp_code"));}
		
	}
}