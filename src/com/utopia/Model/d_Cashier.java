package com.utopia.Model;

import java.util.Date;

public class d_Cashier {
	
	private int id ; 
	private float currentMoney;		//当前现金 ， 计算得到
	private float initMoney;		//初始现金 ， 早晨现金
	private float changeMoney;		//交易现金
	private String createTime;		//操作时间
	private String userCode;		//员工id
	private String cashierId;		//收银机id
	private String status;			//五个状态 ， 放入in 、 支出 out、 早晨初始化 init、 晚上最后结算check 、 老板purchase
	
	
	public d_Cashier() {
		super();
	}
	public d_Cashier(int id, float currentMoney, float initMoney,
			float changeMoney, String createTime, String userCode,
			String cashierId, String status) {
		super();
		this.id = id;
		this.currentMoney = currentMoney;
		this.initMoney = initMoney;
		this.changeMoney = changeMoney;
		this.createTime = createTime;
		this.userCode = userCode;
		this.cashierId = cashierId;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getCurrentMoney() {
		return currentMoney;
	}
	public void setCurrentMoney(float currentMoney) {
		this.currentMoney = currentMoney;
	}
	public float getInitMoney() {
		return initMoney;
	}
	public void setInitMoney(float initMoney) {
		this.initMoney = initMoney;
	}
	public float getChangeMoney() {
		return changeMoney;
	}
	public void setChangeMoney(float changeMoney) {
		this.changeMoney = changeMoney;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String string) {
		this.createTime = string;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCashierId() {
		return cashierId;
	}
	public void setCashierId(String cashierId) {
		this.cashierId = cashierId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
