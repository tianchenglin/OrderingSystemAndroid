package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractSizeandprice entity provides the base persistence definition of the Sizeandprice entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Sizeandprice{


    // Fields    

     private Integer id;
     private String sizeName;
     private Float sizePrice;
     private Integer itemId;


    // Constructors

    @Override
	public String toString() {
		return "Sizeandprice [id=" + id + ", sizeName=" + sizeName
				+ ", sizePrice=" + sizePrice + ", itemId=" + itemId + "]";
	}


	/** default constructor */
    public Sizeandprice() {
    }

    
    /** full constructor */
    public Sizeandprice(String sizeName, Float sizePrice, Integer itemId) {
        this.sizeName = sizeName;
        this.sizePrice = sizePrice;
        this.itemId = itemId;
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

    public String getSizeName() {
        return this.sizeName;
    }
    
    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Float getSizePrice() {
        return this.sizePrice;
    }
    
    public void setSizePrice(Float sizePrice) {
        this.sizePrice = sizePrice;
    }

    public Integer getItemId() {
        return this.itemId;
    }
    
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}