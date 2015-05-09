package com.utopia.Model;

public class d_Setting {
	private String SettingId;
	private String serverip ; 
	private String serialNumber ; 
	private Boolean GraphicalOrder;
	private Boolean ListOrder;
	private Boolean CodeOrder;
	private Boolean FastOrder;
	private Boolean Custom;
	private Boolean	Waiter;
	
	public String getSettingId() {
		return SettingId;
	}
	public void setSettingId(String settingId) {
		SettingId = settingId;
	}
	public String getServerip() {
		return serverip;
	}
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Boolean getGraphicalOrder() {
		return GraphicalOrder;
	}
	public void setGraphicalOrder(Boolean graphicalOrder) {
		GraphicalOrder = graphicalOrder;
	}
	public Boolean getListOrder() {
		return ListOrder;
	}
	public void setListOrder(Boolean listOrder) {
		ListOrder = listOrder;
	}
	public Boolean getCodeOrder() {
		return CodeOrder;
	}
	public void setCodeOrder(Boolean codeOrder) {
		CodeOrder = codeOrder;
	}
	public Boolean getFastOrder() {
		return FastOrder;
	}
	public void setFastOrder(Boolean fastOrder) {
		FastOrder = fastOrder;
	}
	public Boolean getCustom() {
		return Custom;
	}
	public void setCustom(Boolean custom) {
		Custom = custom;
	}
	public Boolean getWaiter() {
		return Waiter;
	}
	public void setWaiter(Boolean waiter) {
		Waiter = waiter;
	}
	
	
}
