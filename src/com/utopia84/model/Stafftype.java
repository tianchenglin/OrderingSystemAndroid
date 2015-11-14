package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractStafftype entity provides the base persistence definition of the Stafftype entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Stafftype {


    // Fields    

     private Integer id;
     private String typeName;
     private Integer priority;
     private Integer delmark;


    // Constructors

    /** default constructor */
    public Stafftype() {
    }

    
    /** full constructor */
    public Stafftype(String typeName, Integer priority, Integer delmark) {
        this.typeName = typeName;
        this.priority = priority;
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

    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getPriority() {
        return this.priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getDelmark() {
        return this.delmark;
    }
    
    public void setDelmark(Integer delmark) {
        this.delmark = delmark;
    }
   








}