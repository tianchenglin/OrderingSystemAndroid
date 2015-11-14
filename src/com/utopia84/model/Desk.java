package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * AbstractDesk entity provides the base persistence definition of the Desk entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Desk {


    // Fields    

     private Integer id;
     private String typeId;
     private String state;
     private String SAccount;
     private String deskName;
     private Integer statetime;
     private String starttime;
     private Integer peopleNum;
     private Integer row;
     private Integer col;
     private Integer delmark;
     private Integer message;


    // Constructors

    /** default constructor */
    public Desk() {
    }

	/** minimal constructor */
    public Desk(String typeId) {
        this.typeId = typeId;
    }
    
    /** full constructor */
    public Desk(String typeId, String state, String SAccount, String deskName, Integer statetime, String starttime, Integer peopleNum, Integer row, Integer col, Integer delmark, Integer message) {
        this.typeId = typeId;
        this.state = state;
        this.SAccount = SAccount;
        this.deskName = deskName;
        this.statetime = statetime;
        this.starttime = starttime;
        this.peopleNum = peopleNum;
        this.row = row;
        this.col = col;
        this.delmark = delmark;
        this.message = message;
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

    public String getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public String getSAccount() {
        return this.SAccount;
    }
    
    public void setSAccount(String SAccount) {
        this.SAccount = SAccount;
    }

    public String getDeskName() {
        return this.deskName;
    }
    
    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public Integer getStatetime() {
        return this.statetime;
    }
    
    public void setStatetime(Integer statetime) {
        this.statetime = statetime;
    }

    public String getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public Integer getPeopleNum() {
        return this.peopleNum;
    }
    
    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getRow() {
        return this.row;
    }
    
    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return this.col;
    }
    
    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getDelmark() {
        return this.delmark;
    }
    
    public void setDelmark(Integer delmark) {
        this.delmark = delmark;
    }

    public Integer getMessage() {
        return this.message;
    }
    
    public void setMessage(Integer message) {
        this.message = message;
    }
   








}