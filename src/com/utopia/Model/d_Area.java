package com.utopia.Model;

public class d_Area {
	private String AreaId;
	private String AreaName;

	public d_Area() {
	}

	public d_Area(String areaId, String areaName) {
		super();
		AreaId = areaId;
		AreaName = areaName;
	}

	public String getAreaId() {
		return this.AreaId;
	}

	public String getAreaName() {
		return this.AreaName;
	}

	public void setAreaId(String paramString) {
		this.AreaId = paramString;
	}

	public void setAreaName(String paramString) {
		this.AreaName = paramString;
	}
}