package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * AbstractSalerecord entity provides the base persistence definition of the Salerecord entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Salerecord {


    // Fields    

	private int itemNo;
	private String closeTime;
	private String createTime;
	private String deskName;
	private String otherSpec;
	
	private String otherSpecNo1;
	private String otherSpecNo2;
	private String status;
	private String waiter;
	private String dept;
	
	private float subTotal;
	private float tipTotal;
	private float total;
	private float initTotal;
	private float rebate;
	
	private float taxTotal;


    // Constructors

    /** default constructor */
    public Salerecord() {
    }

	/** minimal constructor */
    public Salerecord(int itemNo) {
        this.itemNo = itemNo;
    }
    
    /** full constructor */
    public Salerecord(int itemNo,String closeTime,String createTime,String deskName,String otherSpec,String otherSpecNo1,String otherSpecNo2,String status,String waiter,String dept,float subTotal,float tipTotal,float total,float initTotal,float rebateTotal,float taxTotal) {
    	this.itemNo=itemNo;
    	this.closeTime=closeTime;
    	this.createTime=createTime;
    	this.deskName=deskName;
    	this.otherSpec=otherSpec;
    	this.otherSpecNo1=otherSpecNo1;
    	this.otherSpecNo2=otherSpecNo2;
    	this.status=status;
    	this.waiter=waiter;
    	this.dept=dept;
    	this.subTotal=subTotal;
    	this.tipTotal=tipTotal;
    	this.total=total;
    	this.initTotal=initTotal;
    	this.rebate=rebateTotal;
    	this.taxTotal=taxTotal;
    }

   
    // Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getItemNo(){
		return this.itemNo;
	}
	public void setItemNo(int itemNo){
		this.itemNo=itemNo;
	}
	public String getCloseTime(){
		return this.closeTime;
	}
	public void setCloseTime(String closeTime){
		this.closeTime=closeTime;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public String getDeskName(){
		return this.deskName;
	}
	public void setDeskName(String deskName){
		this.deskName=deskName;
	}
	public String getOtherSpec(){
		return this.otherSpec;
	}
	public void setOtherSpec(String otherSpec){
		this.otherSpec=otherSpec;
	}
	public String getOtherSpecNo1(){
		return this.otherSpecNo1;
	}
	public void setOtherSpecNo1(String otherSpecNo1){
		this.otherSpecNo1=otherSpecNo1;
	}
	public String getOtherSpecNo2(){
		return this.otherSpecNo2;
	}
	public void setOtherSpecNo2(String otherSpecNo2){
		this.otherSpecNo2=otherSpecNo2;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getWaiter(){
		return this.waiter;
	}
	public void setWaiter(String waiter){
		this.waiter=waiter;
	}
	public String getDept(){
		return this.dept;
	}
	public void setDept(String dept){
		this.dept=dept;
	}
	public float getSubTotal(){
		return this.subTotal;
	}
	public void setSubTotal(float subTotal){
		this.subTotal=subTotal;
	}
	public float getTipTotal(){
		return this.tipTotal;
	}
	public void setTipTotal(float tipTotal){
		this.tipTotal=tipTotal;
	}
	public float getTotal(){
		return this.total;
	}
	public void setTotal(float total){
		this.total=total;
	}
	public float getInitTotal(){
		return this.initTotal;
	}
	public void setInitTotal(float initTotal){
		this.initTotal=initTotal;
	}
	public float getRebateTotal(){
		return this.rebate;
	}
	public void setRebateTotal(float rebateTotal){
		this.rebate=rebateTotal;
	}
	public float getTaxTotal(){
		return this.taxTotal;
	}
	public void setTaxTotal(float taxTotal){
		this.taxTotal=taxTotal;
	}
}