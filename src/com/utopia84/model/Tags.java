package com.utopia84.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * AbstractTags entity provides the base persistence definition of the Tags
 * entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Tags {

	// Fields

	private Integer id;
	private String tagName;
	
	private int include;

	// Constructors

	/** default constructor */
	public Tags() {
	}

	/** full constructor */
	public Tags(String tagName) {
		this.tagName = tagName;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	@Column(name="include",columnDefinition="INT(11) default 0")
	public int getInclude() {
		return include;
	}

	public void setInclude(int include) {
		this.include = include;
	}
}