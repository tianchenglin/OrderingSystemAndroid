package com.utopia.Model;


public class d_Bill {
	private String BillId ; 
	private String Waiter ; 
	private float Subtotal ;
	private float Tax ; 
	private float Total ; 
	private String CreateTime;
	private float Distant ;
	private float tip;
	
	
	
	public d_Bill(String billId, String waiter, float subtotal, float tax,
			float total, String createTime, float distant, float tip) {
		super();
		this.BillId = billId;
		this.Waiter = waiter;
		this.Subtotal = subtotal;
		this.Tax = tax;
		this.Total = total;
		this.CreateTime = createTime;
		this.Distant = distant;
		this.tip = tip;
	}

	public d_Bill() {
		// TODO Auto-generated constructor stub
	}

	public String getBillId() {
		return BillId;
	}
	
	public float getTip() {
		return tip;
	}

	public void setTip(float tip) {
		this.tip = tip;
	}

	public void setBillId(String billId) {
		BillId = billId;
	}
	public String getWaiter() {
		return Waiter;
	}
	public void setWaiter(String waiter) {
		Waiter = waiter;
	}
	public float getSubtotal() {
		return Subtotal;
	}
	public void setSubtotal(float subtotal) {
		Subtotal = subtotal;
	}
	public float getTax() {
		return Tax;
	}
	public void setTax(float tax) {
		Tax = tax;
	}
	public float getTotal() {
		return Total;
	}
	public void setTotal(float total) {
		Total = total;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public float getDistant() {
		return Distant;
	}
	public void setDistant(float distant) {
		Distant = distant;
	}
	
	
}
