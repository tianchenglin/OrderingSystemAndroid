package com.utopia.Model;

public class d_Sale {
	private int itemNo;        //交易的编号
	private String closeTime;  //桌子交易开始时间
	private String createTime; //交易结束的时间
	private String deskName;   //桌子名
	private String otherSpec;   //备注
	private String otherSpecNo1;  //备注1
	private String otherSpecNo2;  //备注2
	private String status;       //状态
	private String dept;       //隶属的部门
    private float subtotal;   //该桌应收总金额
    private float tiptotal;   //该桌的总小费
    private float total;     //该桌实收的总金额
    private float initTotal; //原总额
    private float rebate;  //折扣
    private float tax;   //总税收
    private String waiter; //服务员
    private float cashTotal; //现金钱数
    private float cardTotal; //刷卡钱数
   // private int customNo;
    public d_Sale(){
    	
    }
    
    public d_Sale(int itemNo,String closeTime,String createTime,String deskName,String otherSpec,String otherSpecNo1,
    		String otherSpecNo2,String status,String dept,float subtotal,float tiptotal,float total,float initTotal,
    		float rebate,float tax,String waiter,float cashTotal,float cardTotal){
    	this.itemNo=itemNo;
    	this.closeTime=closeTime;
    	this.createTime=createTime;
    	this.deskName=deskName;
    	this.otherSpec=otherSpec;
    	this.otherSpecNo1=otherSpecNo1;
    	this.otherSpecNo2=otherSpecNo2;
    	this.status=status;
    	this.dept=dept;
    	this.subtotal=subtotal;
    	this.tiptotal=tiptotal;
    	this.total=total;
    	this.initTotal=initTotal;
    	this.rebate=rebate;
    	this.tax=tax;
    	this.waiter=waiter;
    	this.cashTotal=cashTotal;
    	this.cardTotal=cardTotal;
    	//this.customNo=customNo;
    }
    
//    public int getCustomNo() {
//		return customNo;
//	}
//
//	public void setCustomNo(int customNo) {
//		this.customNo = customNo;
//	}

	public int getItemNo() {
		return itemNo;
	}

	
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDeskName() {
		return deskName;
	}
    
	public String getOtherSpecNo1() {
		return otherSpecNo1;
	}

	public void setOtherSpecNo1(String otherSpecNo1) {
		this.otherSpecNo1 = otherSpecNo1;
	}

	public String getOtherSpecNo2() {
		return otherSpecNo2;
	}

	public void setOtherSpecNo2(String otherSpecNo2) {
		this.otherSpecNo2 = otherSpecNo2;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public void setDeskName(String deskName) {
		this.deskName = deskName;
	}

	public String getOtherSpec() {
		return otherSpec;
	}

	public void setOtherSpec(String otherSpec) {
		this.otherSpec = otherSpec;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public float getTiptotal() {
		return tiptotal;
	}

	public void setTiptotal(float tiptotal) {
		this.tiptotal = tiptotal;
	}

	public float getInitTotal() {
		return initTotal;
	}

	public void setInitTotal(float initTotal) {
		this.initTotal = initTotal;
	}

	public float getRebate() {
		return rebate;
	}

	public void setRebate(float rebate) {
		this.rebate = rebate;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public String getWaiter() {
		return waiter;
	}

	public void setWaiter(String waiter) {
		this.waiter = waiter;
	}

	public float getCashTotal() {
		return cashTotal;
	}

	public void setCashTotal(float cashTotal) {
		this.cashTotal = cashTotal;
	}

	public float getCardTotal() {
		return cardTotal;
	}

	public void setCardTotal(float cardTotal) {
		this.cardTotal = cardTotal;
	}

	@Override
	public String toString() {
		return "d_Sale [itemNo=" + itemNo + ", closeTime=" + closeTime
				+ ", createTime=" + createTime + ", deskName=" + deskName
				+ ", otherSpec=" + otherSpec + ", otherSpecNo1=" + otherSpecNo1
				+ ", otherSpecNo2=" + otherSpecNo2 + ", status=" + status
				+ ", dept=" + dept + ", subtotal=" + subtotal + ", tiptotal="
				+ tiptotal + ", total=" + total + ", initTotal=" + initTotal
				+ ", rebate=" + rebate + ", tax=" + tax + ", waiter=" + waiter
				+ ", cashTotal=" + cashTotal + ", cardTotal=" + cardTotal
				+ ", getItemNo()=" + getItemNo() + ", getCloseTime()="
				+ getCloseTime() + ", getCreateTime()=" + getCreateTime()
				+ ", getDeskName()=" + getDeskName() + ", getOtherSpecNo1()="
				+ getOtherSpecNo1() + ", getOtherSpecNo2()="
				+ getOtherSpecNo2() + ", getTotal()=" + getTotal()
				+ ", getOtherSpec()=" + getOtherSpec() + ", getStatus()="
				+ getStatus() + ", getDept()=" + getDept() + ", getSubtotal()="
				+ getSubtotal() + ", getTiptotal()=" + getTiptotal()
				+ ", getInitTotal()=" + getInitTotal() + ", getRebate()="
				+ getRebate() + ", getTax()=" + getTax() + ", getWaiter()="
				+ getWaiter() + ", getCashTotal()=" + getCashTotal()
				+ ", getCardTotal()=" + getCardTotal() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

   

}
