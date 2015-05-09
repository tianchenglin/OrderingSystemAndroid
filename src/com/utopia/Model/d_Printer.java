package com.utopia.Model;

public class d_Printer {
	private int id;// 在数据库中编号
	private String mac;// 物理地址
	private String alias;// 别名

	public d_Printer() {
	}

	public d_Printer(int id, String mac, String alias) {
		super();
		this.id = id;
		this.mac = mac;
		this.alias = alias;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	};

}
