package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * AbstractBillId entity provides the base persistence definition of the BillId entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Bill{


    // Fields    
	
	private String billId;
	private String createTime;
	private float distant;
	private float subTotal;
	private float taxTotal;
	private float tip;
	private float total;
	private String waiter;
	private int salerecordId;
	private float rebate;
	private float initTotal;
	private String tipPayment;
	private String payment;
	private int cashierId;


    // Constructors

    /** default constructor */
    public Bill() {
    }

	/** minimal constructor */
    public Bill(String billId) {
        this.billId = billId;
    }
   
    /** full constructor */
    public Bill(String billId,String createTime,float distant,float subTotal,float tax,float tip,float total,String waiter,int salerecordId,
	 float rebate,float initTotal,String tipPayment,String payment,int cashierId) {
        this.billId=billId;
    	this.createTime=createTime;
    	this.distant=distant;
    	this.subTotal=subTotal;
    	this.taxTotal=tax;
    	this.tip=tip;
    	this.total=total;
    	this.waiter=waiter;
    	this.salerecordId=salerecordId;
    	this.rebate=rebate;
    	this.initTotal=initTotal;
    	this.tipPayment=tipPayment;
    	this.payment=payment;
    	this.cashierId=cashierId;
    }

   
    // Property accessors
    @Id
    public String getBillId(){
		return this.billId;
	}
	public void setBillId(String billId){
		this.billId=billId;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public float getDistant(){
		return this.distant;
	}
	public void setDistant(float distant){
		this.distant=distant;
	}
	public float getSubtotal(){
		return this.subTotal;
	}
	public void setSubtotal(float subTotal){
		this.subTotal=subTotal;
	}
	public float getTax(){
		return this.taxTotal;
	}
	public void setTax(float tax){
		this.taxTotal=tax;
	}
	public float getTip(){
		return this.tip;
	}
	public void setTip(float tip){
		this.tip=tip;
	}
	public float getTotal(){
		return this.total;
	}
	public void setTotal(float total){
		this.total=total;
	}
	public String getWaiter(){
		return this.waiter;
	}
	public void setWaiter(String waiter){
		this.waiter=waiter;
	}
	public int getSalerecordId(){
		return this.salerecordId;
	}
	public void setSalerecordId(int salerecordId){
		this.salerecordId=salerecordId;
	}
	public float getRebate(){
		return this.rebate;
	}
	public void setRebate(float rebate){
		this.rebate=rebate;
	}
	public float getInitTotal(){
		return this.initTotal;
	}
	public void setInitTotal(float initTotal){
		this.initTotal=initTotal;
	}
	public String getTipPayment(){
		return this.tipPayment;
	}
	public void setTipPayment(String tipPayment){
		this.tipPayment=tipPayment;
	}
	public String getPayment(){
		return this.payment;
	}
	public void setPayment(String payment){
		this.payment=payment;
	}
	public int getCashierId(){
		return this.cashierId;
	}
	public void setCashierId(int cashierId){
		this.cashierId=cashierId;
	}
}