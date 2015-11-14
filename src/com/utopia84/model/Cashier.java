package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * AbstractCashier entity provides the base persistence definition of the Cashier entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Cashier {


    // Fields    

     private Integer id;
     private Float currentMoney;
     private Float initMoney;
     private Float changeMoney;
     private String createTime;
     private String userCode;
     private String cashierId;
     private String status;


    // Constructors

    /** default constructor */
    public Cashier() {
    }

	/** minimal constructor */
    public Cashier(String userCode) {
        this.userCode = userCode;
    }
    
    /** full constructor */
    public Cashier(int id , Float currentMoney, Float initMoney, Float changeMoney, String createTime, String userCode, String cashierId, String status) {
    	this.id = id ; 
    	this.currentMoney = currentMoney;
        this.initMoney = initMoney;
        this.changeMoney = changeMoney;
        this.createTime = createTime;
        this.userCode = userCode;
        this.cashierId = cashierId;
        this.status = status;
    }

   
    // Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCurrentMoney() {
        return this.currentMoney;
    }
    
    public void setCurrentMoney(Float currentMoney) {
        this.currentMoney = currentMoney;
    }

    public Float getInitMoney() {
        return this.initMoney;
    }
    
    public void setInitMoney(Float initMoney) {
        this.initMoney = initMoney;
    }

    public Float getChangeMoney() {
        return this.changeMoney;
    }
    
    public void setChangeMoney(Float changeMoney) {
        this.changeMoney = changeMoney;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserCode() {
        return this.userCode;
    }
    
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCashierId() {
        return this.cashierId;
    }
    
    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   








}