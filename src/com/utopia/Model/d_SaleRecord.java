package com.utopia.Model;

//
public class d_SaleRecord {
	
	private String PayId;
	private String BILLID;
	private String PdtCODE;
	private String PdtName;
	private int number;
	private double Price;
	private int rebate;
	private String CreateTime;
	private String closeTime;
	private String status;    //状态
	private String desk_name;
	private String OtherSpecNo1;	//size
	private String OtherSpecNo2;	//hotness
	private String OtherSpec;	//备注
	private String Waiter ;
	private float tax ; 		//税收
	private float tip ; 		//小费
	private float discount ;    //折扣
	private int ItemNo;
	private int customerNo ;    //顾客数量
	private int priority;
	
	public d_SaleRecord(){
		
	}
	
	public d_SaleRecord(String payId, String bILLID, String pdtCODE,
			String pdtName, int number, double price, int rebate,
			String createTime, String closeTime, String status, String desk_name,
			String otherSpecNo1, String otherSpecNo2, String otherSpec,
			String waiter, float tax,float tip,float discount,int ItemNo,int customerNo,int priority) {
		super();
		this.PayId = payId;
		this.BILLID = bILLID;
		this.PdtCODE = pdtCODE;
		this.PdtName = pdtName;
		this.number = number;
		this.Price = price;
		this.rebate = rebate;
		this.CreateTime = createTime;
		this.closeTime = closeTime;
		this.status = status;
		this.desk_name = desk_name;
		this.OtherSpecNo1 = otherSpecNo1;
		this.OtherSpecNo2 = otherSpecNo2;
		this.OtherSpec = otherSpec;
		this.Waiter = waiter;
		this.tax = tax;
		this.tip = tip;
		this.discount = discount ;
		this.ItemNo = ItemNo;
		this.customerNo = customerNo ;
		this.priority=priority;
	}


	public int getCustomerNo() {
		return customerNo;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}

	public int getItemNo() {
		return ItemNo;
	}

	public void setItemNo(int itemNo) {
		ItemNo = itemNo;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getTip() {
		return tip;
	}

	public void setTip(float tip) {
		this.tip = tip;
	}

	public String getPayId() {
		return PayId;
	}
	public void setPayId(String payId) {
		PayId = payId;
	}
	public String getBILLID() {
		return BILLID;
	}
	public void setBILLID(String bILLID) {
		BILLID = bILLID;
	}
	
	public String getPdtCODE() {
		return PdtCODE;
	}
	public void setPdtCODE(String pdtCODE) {
		PdtCODE = pdtCODE;
	}
	public String getPdtName() {
		return PdtName;
	}
	public void setPdtName(String pdtName) {
		PdtName = pdtName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double d) {
		Price = d;
	}
	public int getRebate() {
		return rebate;
	}
	public void setRebate(int f) {
		this.rebate = f;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesk_name() {
		return desk_name;
	}
	public void setDesk_name(String desk_name) {
		this.desk_name = desk_name;
	}
	public String getOtherSpecNo1() {
		return OtherSpecNo1;
	}
	public void setOtherSpecNo1(String otherSpecNo1) {
		OtherSpecNo1 = otherSpecNo1;
	}
	public String getOtherSpecNo2() {
		return OtherSpecNo2;
	}
	public void setOtherSpecNo2(String otherSpecNo2) {
		OtherSpecNo2 = otherSpecNo2;
	}
	public String getOtherSpec() {
		return OtherSpec;
	}
	public void setOtherSpec(String otherSpec) {
		OtherSpec = otherSpec;
	}
	public String getWaiter() {
		return Waiter;
	}
	public void setWaiter(String waiter) {
		Waiter = waiter;
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}

	@Override
	public String toString() {
		return "d_SaleRecord [PayId=" + PayId + ", BILLID=" + BILLID
				+ ", PdtCODE=" + PdtCODE + ", PdtName=" + PdtName + ", number="
				+ number + ", Price=" + Price + ", rebate=" + rebate
				+ ", CreateTime=" + CreateTime + ", closeTime=" + closeTime
				+ ", status=" + status + ", desk_name=" + desk_name
				+ ", OtherSpecNo1=" + OtherSpecNo1 + ", OtherSpecNo2="
				+ OtherSpecNo2 + ", OtherSpec=" + OtherSpec + ", Waiter="
				+ Waiter + ", tax=" + tax + ", tip=" + tip + ", discount="
				+ discount + "]";
	}

	public String getString() {
		return "{\"PayId\":\"" + PayId + "\", \"BILLID\":\"" + BILLID
				+ "\", \"PdtCODE\":\"" + PdtCODE + "\", \"PdtName\":\"" + PdtName + "\", \"number\":\""
				+ number + "\", \"Price\":\"" + Price + "\", \"rebate\":\"" + rebate
				+ "\", \"CreateTime\":\"" + CreateTime + "\", \"closeTime\":\"" + closeTime
				+ "\", \"status\":\"" + status + "\", \"desk_name\":\"" + desk_name
				+ "\", \"OtherSpec\":\"" + OtherSpec 
				+ "\", \"OtherSpecNo1\":\"" + OtherSpecNo1 + "\", \"OtherSpecNo2\":\""
				
				+ OtherSpecNo2 + "\", \"ItemNo\":\"" + ItemNo + "\", \"Waiter\":\""
				+ Waiter + "\", \"tax\":\"" + tax + "\"}";
	}
	
}