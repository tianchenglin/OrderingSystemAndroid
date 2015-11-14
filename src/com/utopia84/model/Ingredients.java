package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractIngredients entity provides the base persistence definition of the Ingredients entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Ingredients{


    // Fields    

     private Integer id;
     private String ingreName;
     private String ingreCount;


    // Constructors

    /** default constructor */
    public Ingredients() {
    }

    
    /** full constructor */
    public Ingredients(String ingreName, String ingreCount) {
        this.ingreName = ingreName;
        this.ingreCount = ingreCount;
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

    public String getIngreName() {
        return this.ingreName;
    }
    
    public void setIngreName(String ingreName) {
        this.ingreName = ingreName;
    }

    public String getIngreCount() {
        return this.ingreCount;
    }
    
    public void setIngreCount(String ingreCount) {
        this.ingreCount = ingreCount;
    }
}