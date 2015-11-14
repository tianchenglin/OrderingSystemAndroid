package com.utopia84.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractModifier entity provides the base persistence definition of the Modifier entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Modifier{


    // Fields    

     private Integer id;
     private String modiName;
     private String choiceType;

     private int include;
    // Constructors

    /** default constructor */
    public Modifier() {
    }

    
    /** full constructor */
    public Modifier(String modiName, String choiceType) {
        this.modiName = modiName;
        this.choiceType = choiceType;
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

    public String getModiName() {
        return this.modiName;
    }
    
    public void setModiName(String modiName) {
        this.modiName = modiName;
    }

    public String getChoiceType() {
        return this.choiceType;
    }
    
    public void setChoiceType(String choiceType) {
        this.choiceType = choiceType;
    }
   
    @Column(name="include",columnDefinition="INT(11) default 0")
	public int getInclude() {
		return include;
	}

	public void setInclude(int include) {
		this.include = include;
	}







}