package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractDiscounts entity provides the base persistence definition of the Discounts entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Discounts{


    // Fields    

     private Integer id;
     private String discountName;
     private Float amount;
     private Integer type;


    // Constructors

    /** default constructor */
    public Discounts() {
    }

    
    /** full constructor */
    public Discounts(String discountName, Float amount, Integer type) {
        this.discountName = discountName;
        this.amount = amount;
        this.type = type;
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

    public String getDiscountName() {
        return this.discountName;
    }
    
    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Float getAmount() {
        return this.amount;
    }
    
    public void setAmount(Float amount) {
        this.amount = amount;
    }


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
}