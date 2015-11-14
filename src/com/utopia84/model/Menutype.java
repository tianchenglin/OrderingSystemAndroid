package com.utopia84.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractMenutype entity provides the base persistence definition of the Menutype entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Menutype{


    // Fields    

     private String typeId;
     private String typeName;
     private String typeParentId;

     private int include;
    // Constructors

    /** default constructor */
    public Menutype() {
    }
    
    public Menutype(String typeName) {
    	this.typeName = typeName;
    	this.typeParentId = "0";
    }

    
    /** full constructor */
    public Menutype(String typeId , String typeName, String typeParentId) {
    	this.typeId = typeId;
        this.typeName = typeName;
        this.typeParentId = typeParentId;
    }

   
    // Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public String getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeParentId() {
        return this.typeParentId;
    }
    
    public void setTypeParentId(String typeParentId) {
        this.typeParentId = typeParentId;
    }
   
    @Column(name="include",columnDefinition="INT(11) default 0")
	public int getInclude() {
		return include;
	}

	public void setInclude(int include) {
		this.include = include;
	}







}