package com.utopia.Model;

import java.io.Serializable;

public class d_Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String DepartId;
	private String PdtAccType;
	private int PdtAutoInc;
	private int PdtCanUsed;
	private int PdtCanZk;
	private int PdtChangePrice;
	private String PdtCode;
	private String PdtGg;
	private String PdtID;
	private int PdtInMix;
	private double PdtInPrice;
	private String PdtMCode;
	private int PdtMakeTime;
	private String PdtName;
	private int PdtNoShow;
	private double PdtPayType;
	private String PdtPy;
	private Float PdtSalePrice1;
	private double PdtSalePrice2;
	private String PdtUnit;
	private int PdtisSet;
	private String TypeId;
	private int minrebate;
	private int notout;
	private int notshow;
	private int notshowonbill;
	private int pdtchgNumber;
	private double pdtdownprice1;
	private double pdtdownprice2;

	public d_Product(){}
	
	
	public d_Product(String departId, String pdtAccType, int pdtAutoInc,
			int pdtCanUsed, int pdtCanZk, int pdtChangePrice,
			String pdtCode, String pdtGg, String pdtID, int pdtInMix,
			double pdtInPrice, String pdtMCode, int pdtMakeTime,
			String pdtName, int pdtNoShow, double pdtPayType, String pdtPy,
			float pdtSalePrice1, double pdtSalePrice2, String pdtUnit,
			int pdtisSet, String typeId, int minrebate, int notout,
			int notshow, int notshowonbill, int pdtchgNumber,
			double pdtdownprice1, double pdtdownprice2) {
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

	public int getPdtCanUsed() {
		return this.PdtCanUsed;
	}

	public int getPdtCanZk() {
		return this.PdtCanZk;
	}

	public int getPdtChangePrice() {
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

	public int getPdtInMix() {
		return this.PdtInMix;
	}

	public double getPdtInPrice() {
		return this.PdtInPrice;
	}

	public String getPdtMCode() {
		return this.PdtMCode;
	}

	public int getPdtMakeTime() {
		return this.PdtMakeTime;
	}

	public String getPdtName() {
		return this.PdtName;
	}

	public int getPdtNoShow() {
		return this.PdtNoShow;
	}

	public double getPdtPayType() {
		return this.PdtPayType;
	}

	public String getPdtPy() {
		return this.PdtPy;
	}

	public double getPdtSalePrice1() {
		return this.PdtSalePrice1;
	}

	public double getPdtSalePrice2() {
		return this.PdtSalePrice2;
	}

	public String getPdtUnit() {
		return this.PdtUnit;
	}

	public int getPdtisSet() {
		return this.PdtisSet;
	}

	public int getPdtminrebate() {
		return this.minrebate;
	}

	public String getTypeId() {
		return this.TypeId;
	}

	public int getnotout() {
		return this.notout;
	}

	public int getnotshow() {
		return this.notshow;
	}

	public int getnotshowonbill() {
		return this.notshowonbill;
	}

	public int getpdtchgNumber() {
		return this.pdtchgNumber;
	}

	public double getpdtdownprice1() {
		return this.pdtdownprice1;
	}

	public double getpdtdownprice2() {
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

	public void setPdtCanUsed(int paramShort) {
		this.PdtCanUsed = paramShort;
	}

	public void setPdtCanZk(int paramShort) {
		this.PdtCanZk = paramShort;
	}

	public void setPdtChangePrice(int paramShort) {
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

	public void setPdtInMix(int paramShort) {
		this.PdtInMix = paramShort;
	}

	public void setPdtInPrice(double paramFloat) {
		this.PdtInPrice = paramFloat;
	}

	public void setPdtMCode(String paramString) {
		this.PdtMCode = paramString;
	}

	public void setPdtMakeTime(int paramShort) {
		this.PdtMakeTime = paramShort;
	}

	public void setPdtName(String paramString) {
		this.PdtName = paramString;
	}

	public void setPdtNoShow(int paramShort) {
		this.PdtNoShow = paramShort;
	}

	public void setPdtPayType(double paramFloat) {
		this.PdtPayType = paramFloat;
	}

	public void setPdtPy(String paramString) {
		this.PdtPy = paramString;
	}

	public void setPdtSalePrice1(float paramFloat) {
		this.PdtSalePrice1 = paramFloat;
	}

	public void setPdtSalePrice2(double paramFloat) {
		this.PdtSalePrice2 = paramFloat;
	}

	public void setPdtUnit(String paramString) {
		this.PdtUnit = paramString;
	}

	public void setPdtisSet(int paramShort) {
		this.PdtisSet = paramShort;
	}

	public void setTypeId(String paramString) {
		this.TypeId = paramString;
	}

	public void setminrebate(int paramInt) {
		this.minrebate = paramInt;
	}

	public void setnotout(int paramShort) {
		this.notout = paramShort;
	}

	public void setnotshow(int paramShort) {
		this.notshow = paramShort;
	}

	public void setnotshowonbill(int paramShort) {
		this.notshowonbill = paramShort;
	}

	public void setpdtchgNumber(int paramShort) {
		this.pdtchgNumber = paramShort;
	}

	public void setpdtdownprice1(double paramFloat) {
		this.pdtdownprice1 = paramFloat;
	}

	public void setpdtdownprice2(double paramFloat) {
		this.pdtdownprice2 = paramFloat;
	}
}