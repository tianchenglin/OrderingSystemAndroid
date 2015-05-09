package com.utopia.Model;

public class d_MenuType {
	private String TypeId;
	private String TypeName;
	private String TypeParentId;

	public d_MenuType() {
	}

	public d_MenuType(String typeId, String typeName, String typeParentId) {
		super();
		TypeId = typeId;
		TypeName = typeName;
		TypeParentId = typeParentId;
	}

	public String getTypeId() {
		return this.TypeId;
	}

	public String getTypeName() {
		return this.TypeName;
	}

	public String getTypeParentId() {
		return this.TypeParentId;
	}

	public void setTypeId(String paramString) {
		this.TypeId = paramString;
	}

	public void setTypeName(String paramString) {
		this.TypeName = paramString;
	}

	public void setTypeParentId(String paramString) {
		this.TypeParentId = paramString;
	}
}