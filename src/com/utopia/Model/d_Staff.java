package com.utopia.Model;

public class d_Staff {
	private String s_account;
	private String s_pwd;
	private String s_name;
	private String type_name;
	private int priority;

	public d_Staff() {

	}

	public d_Staff(String s_account, String s_pwd, String s_name,
			String type_name, int priority) {
		super();
		this.s_account = s_account;
		this.s_pwd = s_pwd;
		this.s_name = s_name;
		this.type_name = type_name;
		this.priority = priority;
	}

	public String getS_account() {
		return s_account;
	}

	public void setS_account(String s_account) {
		this.s_account = s_account;
	}

	public String getS_pwd() {
		return s_pwd;
	}

	public void setS_pwd(String s_pwd) {
		this.s_pwd = s_pwd;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "d_Staff [s_account=" + s_account + ", s_pwd=" + s_pwd
				+ ", s_name=" + s_name + ", type_name=" + type_name
				+ ", priority=" + priority + "]";
	}

}
