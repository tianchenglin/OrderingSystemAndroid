package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * AbstractSalerecord entity provides the base persistence definition of the Salerecord entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Saleandpdt {


    // Fields    

	private int id;
	private int salerecordId;
	private String pdtCode;
	private String pdtName;
	private int number;
	private float price;
	private String status;
	private int priority;
	private String otherspec;
	private String otherspec1;
	private String otherspec2;


    // Constructors

    /** default constructor */
    public Saleandpdt() {
    }

	/** minimal constructor */
    public Saleandpdt(int id) {
        this.id=id;
    }
    
    /** full constructor */
    public Saleandpdt(int id,int salerecordId,String pdtCode,String pdtName,int number,float price,String status,int priority,String otherspec,String otherspec1,String otherspec2) {
        this.id=id;
        this.salerecordId=salerecordId;
        this.pdtCode=pdtCode;
        this.pdtName=pdtName;
        this.number=number;
        this.price=price;
        this.status=status;
        this.priority=priority;
        this.otherspec=otherspec;
        this.otherspec1=otherspec1;
        this.otherspec2=otherspec2;
    }

   
    // Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
	public int getSalerecordId(){
		return this.salerecordId;
	}
	public void setSalerecordId(int salerecordId){
		this.salerecordId=salerecordId;
	}
	public String getPdtCode(){
		return this.pdtCode;
	}
	public void setPdtCode(String pdtCode){
		this.pdtCode=pdtCode;
	}
	public String getPdtName(){
		return this.pdtName;
	}
	public void setPdtName(String pdtName){
		this.pdtName=pdtName;
	}
	public int getNumber(){
		return this.number;
	}
	public void setNumber(int number){
		this.number=number;
	}
	public float getPrice(){
		return this.price;
	}
	public void setPrice(float price){
		this.price=price;
	}
	
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	
	public String getOtherspec(){
		return this.otherspec;
	}
	public void setOtherspec(String otherspec){
		this.otherspec=otherspec;
	}
	
	public String getOtherspec1(){
		return this.otherspec1;
	}
	public void setOtherspec1(String otherspec1){
		this.otherspec1=otherspec1;
	}
	
	public String getOtherspec2(){
		return this.otherspec2;
	}
	public void setOtherspec2(String otherspec2){
		this.otherspec2=otherspec2;
	}
	
	public int getPriority(){
		return this.priority;
	}
	public void setPriority(int priority){
		this.priority=priority;
	}

}