package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractOptions entity provides the base persistence definition of the Options entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Options{


    // Fields    

     private Integer id;
     private String optionName;
     private Float optionPrice;
     private Integer modifierId;


    // Constructors

    /** default constructor */
    public Options() {
    }

    
    /** full constructor */
    public Options(String optionName, Float optionPrice, Integer modifierId) {
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.modifierId = modifierId;
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

    public String getOptionName() {
        return this.optionName;
    }
    
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Float getOptionPrice() {
        return this.optionPrice;
    }
    
    public void setOptionPrice(Float optionPrice) {
        this.optionPrice = optionPrice;
    }

    public Integer getModifierId() {
        return this.modifierId;
    }
    
    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }
   








}