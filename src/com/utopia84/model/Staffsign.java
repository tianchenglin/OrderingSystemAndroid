package com.utopia84.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * AbstractStaffsign entity provides the base persistence definition of the Staffsign entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Staffsign {


    // Fields    

     private Integer id;
     private String SAccount;
     private Timestamp signIn;
     private Timestamp signOut;
     private String signTerm;
     private String signIp;
     private String remark;
     private Integer delmark;


    // Constructors

    /** default constructor */
    public Staffsign() {
    }

	/** minimal constructor */
    public Staffsign(String SAccount, Timestamp signIn, Timestamp signOut) {
        this.SAccount = SAccount;
        this.signIn = signIn;
        this.signOut = signOut;
    }
    
    /** full constructor */
    public Staffsign(String SAccount, Timestamp signIn, Timestamp signOut, String signTerm, String signIp, String remark, Integer delmark) {
        this.SAccount = SAccount;
        this.signIn = signIn;
        this.signOut = signOut;
        this.signTerm = signTerm;
        this.signIp = signIp;
        this.remark = remark;
        this.delmark = delmark;
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

    public String getSAccount() {
        return this.SAccount;
    }
    
    public void setSAccount(String SAccount) {
        this.SAccount = SAccount;
    }

    public Timestamp getSignIn() {
        return this.signIn;
    }
    
    public void setSignIn(Timestamp signIn) {
        this.signIn = signIn;
    }

    public Timestamp getSignOut() {
        return this.signOut;
    }
    
    public void setSignOut(Timestamp signOut) {
        this.signOut = signOut;
    }

    public String getSignTerm() {
        return this.signTerm;
    }
    
    public void setSignTerm(String signTerm) {
        this.signTerm = signTerm;
    }

    public String getSignIp() {
        return this.signIp;
    }
    
    public void setSignIp(String signIp) {
        this.signIp = signIp;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelmark() {
        return this.delmark;
    }
    
    public void setDelmark(Integer delmark) {
        this.delmark = delmark;
    }
   








}