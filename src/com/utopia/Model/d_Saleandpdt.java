package com.utopia.Model;

public class d_Saleandpdt {
	 private int id;//编号
     private int salerecordId;//交易记录编号
     private String pdtCode;//菜单编码
     private String pdtName;//菜单名称
     private int number;//菜的数量
     private float price;//单价
     private String otherspec0;//备注
     private String otherspec1;//备注1
     private String otherspec2;//备注2
     private int priority;//优先级（1 代表先做的菜 0 普通）
     private String status1;//状态
     private String createTime1;//开始的时间
     private String closeTime1;//结束的时间
     
     public d_Saleandpdt(){
    	 
     }
     public d_Saleandpdt(int id,int salerecordId,String pdtCode,String pdtName,
    		 int number,float price,String otherspec,String otherspec1,String otherspec2,
    		 int priority,String status,String createTime,String closeTime){
    	     this.id=id;
    	     this.salerecordId=salerecordId;
    	     this.pdtCode=pdtCode;
    	     this.pdtName=pdtName;
    	     this.number=number;
    	     this.price=price;
    	     this.otherspec0=otherspec;
    	     this.otherspec1=otherspec1;
    	     this.otherspec2=otherspec2;
    	     this.priority=priority;
    	     this.status1=status;
    	     this.createTime1=createTime;
    	     this.closeTime1=closeTime;
    	     
    	 
     }
     public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalerecordId() {
		return salerecordId;
	}

	public void setSalerecordId(int salerecordId) {
		this.salerecordId = salerecordId;
	}

	public String getPdtCode() {
		return pdtCode;
	}

	public void setPdtCode(String pdtCode) {
		this.pdtCode = pdtCode;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getOtherspec() {
		return otherspec0;
	}

	public void setOtherspec(String otherspec) {
		this.otherspec0 = otherspec;
	}

	public String getOtherspec1() {
		return otherspec1;
	}

	public void setOtherspec1(String otherspec1) {
		this.otherspec1 = otherspec1;
	}

	public String getOtherspec2() {
		return otherspec2;
	}

	public void setOtherspec2(String otherspec2) {
		this.otherspec2 = otherspec2;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status1;
	}

	public void setStatus(String status) {
		this.status1 = status;
	}

	public String getCreateTime() {
		return createTime1;
	}

	public void setCreateTime(String createTime) {
		this.createTime1 = createTime;
	}

	public String getCloseTime() {
		return closeTime1;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime1 = closeTime;
	}
	@Override
	public String toString() {
		return "sql_saleandpdt [id=" + id + ", salerecordId=" + salerecordId
				+ ", pdtCode=" + pdtCode + ", pdtName=" + pdtName + ", number="
				+ number + ", price=" + price + ", otherspec=" + otherspec0
				+ ", otherspec1=" + otherspec1 + ", otherspec2=" + otherspec2
				+ ", priority=" + priority + ", status=" + status1
				+ ", createTime=" + createTime1 + ", closeTime=" + closeTime1
				+ ", getId()=" + getId() + ", getSalerecordId()="
				+ getSalerecordId() + ", getPdtCode()=" + getPdtCode()
				+ ", getPdtName()=" + getPdtName() + ", getNumber()="
				+ getNumber() + ", getPrice()=" + getPrice()
				+ ", getOtherspec()=" + getOtherspec() + ", getOtherspec1()="
				+ getOtherspec1() + ", getOtherspec2()=" + getOtherspec2()
				+ ", getPriority()=" + getPriority() + ", getStatus()="
				+ getStatus() + ", getCreateTime()=" + getCreateTime()
				+ ", getCloseTime()=" + getCloseTime() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
	
}
