package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractItemandmodi entity provides the base persistence definition of the Itemandmodi entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Itemandmodi{


    // Fields    

     private Integer id;
     private Integer itemId;
     private Integer modiId;


    // Constructors

    /** default constructor */
    public Itemandmodi() {
    }

    
    /** full constructor */
    public Itemandmodi(Integer itemId, Integer modiId) {
        this.itemId = itemId;
        this.modiId = modiId;
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

    public Integer getItemId() {
        return this.itemId;
    }
    
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getModiId() {
        return this.modiId;
    }
    
    public void setModiId(Integer modiId) {
        this.modiId = modiId;
    }
   








}