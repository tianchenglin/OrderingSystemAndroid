package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractArea entity provides the base persistence definition of the Area entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Area {


    // Fields    

     private String areaId;
     private String areaName;


    // Constructors

    /** default constructor */
    public Area() {
    }

    
    /** full constructor */
    public Area(String areaName) {
        this.areaName = areaName;
    }



	public Area(String areaId, String areaName) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
	}


	// Property accessors
    @Id
    public String getAreaId() {
        return this.areaId;
    }
    
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return this.areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}