package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Staff {

	// Fields

	private Integer id;
	private String SAccount;
	private String SPwd;
	private String SName;
	private Integer SSex;
	private String SIdent;
	private String SAddr;
	private String SPhone;
	private Integer SType;
	private Integer delmark;
	private Integer SOnline;
	private int priority ;
	private String typeName;

	// Constructors

	/** default constructor */
	public Staff() {
	}

	/** minimal constructor */
	public Staff(String SAccount, String SPwd, Integer SType,
			Integer SOnline) {
		this.SAccount = SAccount;
		this.SPwd = SPwd;
		this.SType = SType;
		this.SOnline = SOnline;
	}

	/** full constructor */
	public Staff(String SAccount, String SPwd, String SName,
			Integer SSex, String SIdent, String SAddr, String SPhone,
			Integer SType, Integer delmark, Integer SOnline) {
		this.SAccount = SAccount;
		this.SPwd = SPwd;
		this.SName = SName;
		this.SSex = SSex;
		this.SIdent = SIdent;
		this.SAddr = SAddr;
		this.SPhone = SPhone;
		this.SType = SType;
		this.delmark = delmark;
		this.SOnline = SOnline;
	}
 
	
	
	
	public Staff(String sAccount, String sPwd, String sName, Integer sType) {
		super();
		SAccount = sAccount;
		SPwd = sPwd;
		SName = sName;
		SType = sType;
	}

	public Staff(String sAccount, String sName, String typeName ,int priority) {
		super();
		SAccount = sAccount;
		SName = sName;
		this.priority = priority;
		this.typeName = typeName;
	}
 

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Staff(String sAccount, String sPwd, String sName,
			String typeName, int priority) {
		super();
		SAccount = sAccount;
		SPwd = sPwd;
		SName = sName;
		this.priority = priority;
		this.typeName = typeName;
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

	public String getSAccount() {
		return this.SAccount;
	}

	public void setSAccount(String SAccount) {
		this.SAccount = SAccount;
	}

	public String getSPwd() {
		return this.SPwd;
	}

	public void setSPwd(String SPwd) {
		this.SPwd = SPwd;
	}

	public String getSName() {
		return this.SName;
	}

	public void setSName(String SName) {
		this.SName = SName;
	}

	public Integer getSSex() {
		return this.SSex;
	}

	public void setSSex(Integer SSex) {
		this.SSex = SSex;
	}

	public String getSIdent() {
		return this.SIdent;
	}

	public void setSIdent(String SIdent) {
		this.SIdent = SIdent;
	}

	public String getSAddr() {
		return this.SAddr;
	}

	public void setSAddr(String SAddr) {
		this.SAddr = SAddr;
	}

	public String getSPhone() {
		return this.SPhone;
	}

	public void setSPhone(String SPhone) {
		this.SPhone = SPhone;
	}

	public Integer getSType() {
		return this.SType;
	}

	public void setSType(Integer SType) {
		this.SType = SType;
	}

	public Integer getDelmark() {
		return this.delmark;
	}

	public void setDelmark(Integer delmark) {
		this.delmark = delmark;
	}

	public Integer getSOnline() {
		return this.SOnline;
	}

	public void setSOnline(Integer SOnline) {
		this.SOnline = SOnline;
	}
}