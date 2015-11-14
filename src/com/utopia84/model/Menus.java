package com.utopia84.model;

public class Menus {
	private String DepartId;
	private String PdtAccType;
	private int PdtAutoInc;
	private short PdtCanUsed;
	private short PdtCanZk;
	private short PdtChangePrice;
	private String PdtCode;
	private String PdtGg;
	private String PdtID;
	private short PdtInMix;
	private String PdtInPrice;
	private String PdtMCode;
	private short PdtMakeTime;
	private String PdtName;
	private short PdtNoShow;
	private float PdtPayType;
	private String PdtPy;
	private String PdtSalePrice1;
	private String PdtSalePrice2;
	private String PdtUnit;
	private short PdtisSet;
	private int TypeId;
	private int minrebate;
	private short notout;
	private short notshow;
	private short notshowonbill;
	private short pdtchgNumber;
	private String pdtdownprice1;
	private String pdtdownprice2;

	public Menus() {
	}

	public Menus(String departId, String pdtAccType, int pdtAutoInc,
			short pdtCanUsed, short pdtCanZk, short pdtChangePrice,
			String pdtCode, String pdtGg, String pdtID, short pdtInMix,
			String pdtInPrice, String pdtMCode, short pdtMakeTime,
			String pdtName, short pdtNoShow, float pdtPayType, String pdtPy,
			String pdtSalePrice1, String pdtSalePrice2, String pdtUnit,
			short pdtisSet, int typeId, int minrebate, short notout,
			short notshow, short notshowonbill, short pdtchgNumber,
			String pdtdownprice1, String pdtdownprice2) {
		super();
		DepartId = departId;
		PdtAccType = pdtAccType;
		PdtAutoInc = pdtAutoInc;
		PdtCanUsed = pdtCanUsed;
		PdtCanZk = pdtCanZk;
		PdtChangePrice = pdtChangePrice;
		PdtCode = pdtCode;
		PdtGg = pdtGg;
		PdtID = pdtID;
		PdtInMix = pdtInMix;
		PdtInPrice = pdtInPrice;
		PdtMCode = pdtMCode;
		PdtMakeTime = pdtMakeTime;
		PdtName = pdtName;
		PdtNoShow = pdtNoShow;
		PdtPayType = pdtPayType;
		PdtPy = pdtPy;
		PdtSalePrice1 = pdtSalePrice1;
		PdtSalePrice2 = pdtSalePrice2;
		PdtUnit = pdtUnit;
		PdtisSet = pdtisSet;
		TypeId = typeId;
		this.minrebate = minrebate;
		this.notout = notout;
		this.notshow = notshow;
		this.notshowonbill = notshowonbill;
		this.pdtchgNumber = pdtchgNumber;
		this.pdtdownprice1 = pdtdownprice1;
		this.pdtdownprice2 = pdtdownprice2;
	}

	public String getDepartId() {
		return this.DepartId;
	}

	public String getPdtAccType() {
		return this.PdtAccType;
	}

	public int getPdtAutoInc() {
		return this.PdtAutoInc;
	}

	public short getPdtCanUsed() {
		return this.PdtCanUsed;
	}

	public short getPdtCanZk() {
		return this.PdtCanZk;
	}

	public short getPdtChangePrice() {
		return this.PdtChangePrice;
	}

	public String getPdtCode() {
		return this.PdtCode;
	}

	public String getPdtGg() {
		return this.PdtGg;
	}

	public String getPdtID() {
		return this.PdtID;
	}

	public short getPdtInMix() {
		return this.PdtInMix;
	}

	public String getPdtInPrice() {
		return this.PdtInPrice;
	}

	public String getPdtMCode() {
		return this.PdtMCode;
	}

	public short getPdtMakeTime() {
		return this.PdtMakeTime;
	}

	public String getPdtName() {
		return this.PdtName;
	}

	public short getPdtNoShow() {
		return this.PdtNoShow;
	}

	public float getPdtPayType() {
		return this.PdtPayType;
	}

	public String getPdtPy() {
		return this.PdtPy;
	}

	public String getPdtSalePrice1() {
		return this.PdtSalePrice1;
	}

	public String getPdtSalePrice2() {
		return this.PdtSalePrice2;
	}

	public String getPdtUnit() {
		return this.PdtUnit;
	}

	public short getPdtisSet() {
		return this.PdtisSet;
	}

	public int getPdtminrebate() {
		return this.minrebate;
	}

	public int getTypeId() {
		return this.TypeId;
	}

	public short getnotout() {
		return this.notout;
	}

	public short getnotshow() {
		return this.notshow;
	}

	public short getnotshowonbill() {
		return this.notshowonbill;
	}

	public short getpdtchgNumber() {
		return this.pdtchgNumber;
	}

	public String getpdtdownprice1() {
		return this.pdtdownprice1;
	}

	public String getpdtdownprice2() {
		return this.pdtdownprice2;
	}

	public void setDepartId(String paramString) {
		this.DepartId = paramString;
	}

	public void setPdtAccType(String paramString) {
		this.PdtAccType = paramString;
	}

	public void setPdtAutoInc(int paramInt) {
		this.PdtAutoInc = paramInt;
	}

	public void setPdtCanUsed(short paramShort) {
		this.PdtCanUsed = paramShort;
	}

	public void setPdtCanZk(short paramShort) {
		this.PdtCanZk = paramShort;
	}

	public void setPdtChangePrice(short paramShort) {
		this.PdtChangePrice = paramShort;
	}

	public void setPdtCode(String paramString) {
		this.PdtCode = paramString;
	}

	public void setPdtGg(String paramString) {
		this.PdtGg = paramString;
	}

	public void setPdtID(String paramString) {
		this.PdtID = paramString;
	}

	public void setPdtInMix(short paramShort) {
		this.PdtInMix = paramShort;
	}

	public void setPdtInPrice(String paramFloat) {
		this.PdtInPrice = paramFloat;
	}

	public void setPdtMCode(String paramString) {
		this.PdtMCode = paramString;
	}

	public void setPdtMakeTime(short paramShort) {
		this.PdtMakeTime = paramShort;
	}

	public void setPdtName(String paramString) {
		this.PdtName = paramString;
	}

	public void setPdtNoShow(short paramShort) {
		this.PdtNoShow = paramShort;
	}

	public void setPdtPayType(float paramFloat) {
		this.PdtPayType = paramFloat;
	}

	public void setPdtPy(String paramString) {
		this.PdtPy = paramString;
	}

	public void setPdtSalePrice1(String paramFloat) {
		this.PdtSalePrice1 = paramFloat;
	}

	public void setPdtSalePrice2(String paramFloat) {
		this.PdtSalePrice2 = paramFloat;
	}

	public void setPdtUnit(String paramString) {
		this.PdtUnit = paramString;
	}

	public void setPdtisSet(short paramShort) {
		this.PdtisSet = paramShort;
	}

	public void setTypeId(int paramString) {
		this.TypeId = paramString;
	}

	public void setminrebate(int paramInt) {
		this.minrebate = paramInt;
	}

	public void setnotout(short paramShort) {
		this.notout = paramShort;
	}

	public void setnotshow(short paramShort) {
		this.notshow = paramShort;
	}

	public void setnotshowonbill(short paramShort) {
		this.notshowonbill = paramShort;
	}

	public void setpdtchgNumber(short paramShort) {
		this.pdtchgNumber = paramShort;
	}

	public void setpdtdownprice1(String paramFloat) {
		this.pdtdownprice1 = paramFloat;
	}
	
	public void setpdtdownprice2(String paramFloat) {
		this.pdtdownprice2 = paramFloat;
	}

	@Override
	public String toString() {
		return "Menus [DepartId=" + DepartId + ", PdtAccType=" + PdtAccType
				+ ", PdtAutoInc=" + PdtAutoInc + ", PdtCanUsed=" + PdtCanUsed
				+ ", PdtCanZk=" + PdtCanZk + ", PdtChangePrice="
				+ PdtChangePrice + ", PdtCode=" + PdtCode + ", PdtGg=" + PdtGg
				+ ", PdtID=" + PdtID + ", PdtInMix=" + PdtInMix
				+ ", PdtInPrice=" + PdtInPrice + ", PdtMCode=" + PdtMCode
				+ ", PdtMakeTime=" + PdtMakeTime + ", PdtName=" + PdtName
				+ ", PdtNoShow=" + PdtNoShow + ", PdtPayType=" + PdtPayType
				+ ", PdtPy=" + PdtPy + ", PdtSalePrice1=" + PdtSalePrice1
				+ ", PdtSalePrice2=" + PdtSalePrice2 + ", PdtUnit=" + PdtUnit
				+ ", PdtisSet=" + PdtisSet + ", TypeId=" + TypeId
				+ ", minrebate=" + minrebate + ", notout=" + notout
				+ ", notshow=" + notshow + ", notshowonbill=" + notshowonbill
				+ ", pdtchgNumber=" + pdtchgNumber + ", pdtdownprice1="
				+ pdtdownprice1 + ", pdtdownprice2=" + pdtdownprice2 + "]";
	}
	
	
}
