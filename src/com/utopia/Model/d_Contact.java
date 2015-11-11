package com.utopia.Model;


public class d_Contact {
	private Integer id;
	private String Name;
	private String Phone;
	private String Add_Number;
	private String Add_Street;
	private String Add_Apt;
	private String Add_City;
	private String Add_State;
	private String Add_Code;
	private String Card_Number;
	private String Card_Date;
	private String Card_Cvv;
	private String Card_Fname;
	private String Card_Lname;
	private String Be_Notes;
	private String Not_Notes;
	// Constructors

	/** default constructor */
	public d_Contact() {
	}

	/** minimal constructor */
	public d_Contact(String Name, String Phone) {
		this.Name = Name;
		this.Phone = Phone;
	}

	/** full constructor */
	

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public d_Contact(Integer id, String name, String phone, String add_Number,
			String add_Street, String add_Apt, String add_City,
			String add_State, String add_Code, String card_Number,
			String card_Date, String card_Cvv, String card_Fname,
			String card_Lname, String be_Notes, String not_Notes) {
		super();
		this.id = id;
		Name = name;
		Phone = phone;
		Add_Number = add_Number;
		Add_Street = add_Street;
		Add_Apt = add_Apt;
		Add_City = add_City;
		Add_State = add_State;
		Add_Code = add_Code;
		Card_Number = card_Number;
		Card_Date = card_Date;
		Card_Cvv = card_Cvv;
		Card_Fname = card_Fname;
		Card_Lname = card_Lname;
		Be_Notes = be_Notes;
		Not_Notes = not_Notes;
	}
	
	public d_Contact(String name, String phone, String add_Number,
			String add_Street, String add_Apt, String add_City,
			String add_State, String add_Code, String card_Number,
			String card_Date, String card_Cvv, String card_Fname,
			String card_Lname, String be_Notes, String not_Notes) {
		super();
		Name = name;
		Phone = phone;
		Add_Number = add_Number;
		Add_Street = add_Street;
		Add_Apt = add_Apt;
		Add_City = add_City;
		Add_State = add_State;
		Add_Code = add_Code;
		Card_Number = card_Number;
		Card_Date = card_Date;
		Card_Cvv = card_Cvv;
		Card_Fname = card_Fname;
		Card_Lname = card_Lname;
		Be_Notes = be_Notes;
		Not_Notes = not_Notes;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAdd_State() {
		return Add_State;
	}

	public void setAdd_State(String add_State) {
		Add_State = add_State;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getAdd_Number() {
		return Add_Number;
	}

	public void setAdd_Number(String add_Number) {
		Add_Number = add_Number;
	}

	public String getAdd_Apt() {
		return Add_Apt;
	}

	public void setAdd_Apt(String add_Apt) {
		Add_Apt = add_Apt;
	}

	public String getAdd_City() {
		return Add_City;
	}

	public void setAdd_City(String add_City) {
		Add_City = add_City;
	}

	public String getAdd_Street() {
		return Add_Street;
	}

	public void setAdd_Street(String add_Street) {
		Add_Street = add_Street;
	}

	public String getAdd_Code() {
		return Add_Code;
	}

	public void setAdd_Code(String add_Code) {
		Add_Code = add_Code;
	}

	public String getCard_Number() {
		return Card_Number;
	}

	public void setCard_Number(String card_Number) {
		Card_Number = card_Number;
	}

	public String getCard_Date() {
		return Card_Date;
	}

	public void setCard_Date(String card_Date) {
		Card_Date = card_Date;
	}

	public String getCard_Cvv() {
		return Card_Cvv;
	}

	public void setCard_Cvv(String card_Cvv) {
		Card_Cvv = card_Cvv;
	}

	public String getCard_Fname() {
		return Card_Fname;
	}

	public void setCard_Fname(String card_Fname) {
		Card_Fname = card_Fname;
	}

	public String getCard_Lname() {
		return Card_Lname;
	}

	public void setCard_Lname(String card_Lname) {
		Card_Lname = card_Lname;
	}

	public String getBe_Notes() {
		return Be_Notes;
	}

	public void setBe_Notes(String be_Notes) {
		Be_Notes = be_Notes;
	}

	public String getNot_Notes() {
		return Not_Notes;
	}

	public void setNot_Notes(String not_Notes) {
		Not_Notes = not_Notes;
	}


}