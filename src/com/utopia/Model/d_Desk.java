package com.utopia.Model;

public class d_Desk {
	private int id;
	private String type_id;
	private String state;
	private String s_account;
	private String desk_name;
	private int statetime;
	private String starttime;
	private int people_num;
	private int row;
	private int col;
	private int delmark;
	private int message ; 
    
	public d_Desk() {
	}

	public d_Desk(int id, String type_id, String state, String s_account,
			String desk_name, int statetime, String starttime, int people_num,
			int row, int col, int delmark,int message) {
		super();
		this.id = id;
		this.type_id = type_id;
		this.state = state;
		this.s_account = s_account;
		this.desk_name = desk_name;
		this.statetime = statetime;
		this.starttime = starttime;
		this.people_num = people_num;
		this.row = row;
		this.col = col;
		this.delmark = delmark;
		this.message = message;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getS_account() {
		return s_account;
	}

	public void setS_account(String s_account) {
		this.s_account = s_account;
	}

	public String getDesk_name() {
		return desk_name;
	}

	public void setDesk_name(String desk_name) {
		this.desk_name = desk_name;
	}

	public int getStatetime() {
		return statetime;
	}

	public void setStatetime(int statetime) {
		this.statetime = statetime;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
    
	public int getPeople_num() {
		return people_num;
	}

	public void setPeople_num(int people_num) {
		this.people_num = people_num;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getDelmark() {
		return delmark;
	}

	public void setDelmark(int delmark) {
		this.delmark = delmark;
	}
   
	@Override
	public String toString() {
		return "d_Desk [id=" + id + ", desk_name=" + desk_name + ", message="
				+ message + "]";
	}

}