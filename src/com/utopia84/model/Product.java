package com.utopia84.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * AbstractProduct entity provides the base persistence definition of the Product entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Product{
     private Integer pdtId;//id
     private String pdtCode;//缂栫爜
     private String pdtName;//鍚嶇О
     private String pdtPy;//缂╁啓
     private String pdtUnit;//鍗曚綅
     private String pdtGg;//棰滆壊
     private String pdtInPrice;//鏈夊灏戜釜涓嶅悓鐨勪环鏍硷紝榛樿涓�
     private String pdtSalePrice1;//瀹氫环
     private String pdtSalePrice2;//淇グ鍚庝环鏍�     private int typeId;//鍒嗙被
     private Boolean pdtChangePrice;
     private Boolean pdtCanZk;
     private Float pdtPayType;
     private String departId;
     private Integer pdtAutoInc;
     private Boolean pdtCanUsed;
     private Boolean pdtInMix;
     private Boolean pdtisSet;
     private String pdtMcode;//鏆傚瓨绫诲瀷鍚�     private Integer pdtMakeTime;
     private String pdtAccType;//鏆傚瓨modifier鍚�     private Boolean pdtchgNumber;
     private String pdtdownprice1;
     private String pdtdownprice2;
     private Integer minrebate;
     private Boolean notout;
     private Boolean notshowonbill;
     private Boolean notshow;
     private Boolean pdtNoShow;
     private int typeId;
     private Integer pdtMakeTime;
     private Boolean pdtchgNumber;


    // Constructors

    /** default constructor */
    public Product() {
    }

	/** minimal constructor */
    public Product(String pdtCode, String pdtName, String pdtInPrice, String pdtSalePrice1, String pdtSalePrice2, String departId, String pdtdownprice1, String pdtdownprice2) {
        this.pdtCode = pdtCode;
        this.pdtName = pdtName;
        this.pdtInPrice = pdtInPrice;
        this.pdtSalePrice1 = pdtSalePrice1;
        this.pdtSalePrice2 = pdtSalePrice2;
        this.departId = departId;
        this.pdtdownprice1 = pdtdownprice1;
        this.pdtdownprice2 = pdtdownprice2;
    }
    
    /** full constructor */
    public Product(String pdtCode, String pdtName, String pdtPy, String pdtUnit, String pdtGg, String pdtInPrice, String pdtSalePrice1, String pdtSalePrice2, int typeId, Boolean pdtChangePrice, Boolean pdtCanZk, Float pdtPayType, String departId, Integer pdtAutoInc, Boolean pdtCanUsed, Boolean pdtInMix, Boolean pdtisSet, String pdtMcode, Integer pdtMakeTime, String pdtAccType, Boolean pdtchgNumber, String pdtdownprice1, String pdtdownprice2, Integer minrebate, Boolean notout, Boolean notshowonbill, Boolean notshow, Boolean pdtNoShow) {
        this.pdtCode = pdtCode;
        this.pdtName = pdtName;
        this.pdtPy = pdtPy;
        this.pdtUnit = pdtUnit;
        this.pdtGg = pdtGg;
        this.pdtInPrice = pdtInPrice;
        this.pdtSalePrice1 = pdtSalePrice1;
        this.pdtSalePrice2 = pdtSalePrice2;
        this.typeId = typeId;
        this.pdtChangePrice = pdtChangePrice;
        this.pdtCanZk = pdtCanZk;
        this.pdtPayType = pdtPayType;
        this.departId = departId;
        this.pdtAutoInc = pdtAutoInc;
        this.pdtCanUsed = pdtCanUsed;
        this.pdtInMix = pdtInMix;
        this.pdtisSet = pdtisSet;
        this.pdtMcode = pdtMcode;
        this.pdtMakeTime = pdtMakeTime;
        this.pdtAccType = pdtAccType;
        this.pdtchgNumber = pdtchgNumber;
        this.pdtdownprice1 = pdtdownprice1;
        this.pdtdownprice2 = pdtdownprice2;
        this.minrebate = minrebate;
        this.notout = notout;
        this.notshowonbill = notshowonbill;
        this.notshow = notshow;
        this.pdtNoShow = pdtNoShow;
    }
    // Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getPdtId() {
        return this.pdtId;
    }
    
    public void setPdtId(Integer pdtId) {
        this.pdtId = pdtId;
    }

    public String getPdtCode() {
        return this.pdtCode;
    }
    
    public void setPdtCode(String pdtCode) {
        this.pdtCode = pdtCode;
    }

    public String getPdtName() {
        return this.pdtName;
    }
    
    public void setPdtName(String pdtName) {
        this.pdtName = pdtName;
    }

    public String getPdtPy() {
        return this.pdtPy;
    }
    
    public void setPdtPy(String pdtPy) {
        this.pdtPy = pdtPy;
    }

    public String getPdtUnit() {
        return this.pdtUnit;
    }
    
    public void setPdtUnit(String pdtUnit) {
        this.pdtUnit = pdtUnit;
    }

    public String getPdtGg() {
        return this.pdtGg;
    }
    
    public void setPdtGg(String pdtGg) {
        this.pdtGg = pdtGg;
    }

    public String getPdtInPrice() {
        return this.pdtInPrice;
    }
    
    public void setPdtInPrice(String pdtInPrice) {
        this.pdtInPrice = pdtInPrice;
    }

    public String getPdtSalePrice1() {
        return this.pdtSalePrice1;
    }
    
    public void setPdtSalePrice1(String pdtSalePrice1) {
        this.pdtSalePrice1 = pdtSalePrice1;
    }

    public String getPdtSalePrice2() {
        return this.pdtSalePrice2;
    }
    
    public void setPdtSalePrice2(String pdtSalePrice2) {
        this.pdtSalePrice2 = pdtSalePrice2;
    }

    public int getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Boolean getPdtChangePrice() {
        return this.pdtChangePrice;
    }
    
    public void setPdtChangePrice(Boolean pdtChangePrice) {
        this.pdtChangePrice = pdtChangePrice;
    }

    public Boolean getPdtCanZk() {
        return this.pdtCanZk;
    }
    
    public void setPdtCanZk(Boolean pdtCanZk) {
        this.pdtCanZk = pdtCanZk;
    }

    public Float getPdtPayType() {
        return this.pdtPayType;
    }
    
    public void setPdtPayType(Float pdtPayType) {
        this.pdtPayType = pdtPayType;
    }

    public String getDepartId() {
        return this.departId;
    }
    
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public Integer getPdtAutoInc() {
        return this.pdtAutoInc;
    }
    
    public void setPdtAutoInc(Integer pdtAutoInc) {
        this.pdtAutoInc = pdtAutoInc;
    }

    public Boolean getPdtCanUsed() {
        return this.pdtCanUsed;
    }
    
    public void setPdtCanUsed(Boolean pdtCanUsed) {
        this.pdtCanUsed = pdtCanUsed;
    }

    public Boolean getPdtInMix() {
        return this.pdtInMix;
    }
    
    public void setPdtInMix(Boolean pdtInMix) {
        this.pdtInMix = pdtInMix;
    }

    public Boolean getPdtisSet() {
        return this.pdtisSet;
    }
    
    public void setPdtisSet(Boolean pdtisSet) {
        this.pdtisSet = pdtisSet;
    }

    public String getPdtMcode() {
        return this.pdtMcode;
    }
    
    public void setPdtMcode(String pdtMcode) {
        this.pdtMcode = pdtMcode;
    }

    public Integer getPdtMakeTime() {
        return this.pdtMakeTime;
    }
    
    public void setPdtMakeTime(Integer pdtMakeTime) {
        this.pdtMakeTime = pdtMakeTime;
    }

    public String getPdtAccType() {
        return this.pdtAccType;
    }
    
    public void setPdtAccType(String pdtAccType) {
        this.pdtAccType = pdtAccType;
    }

    public Boolean getPdtchgNumber() {
        return this.pdtchgNumber;
    }
    
    public void setPdtchgNumber(Boolean pdtchgNumber) {
        this.pdtchgNumber = pdtchgNumber;
    }

    public String getPdtdownprice1() {
        return this.pdtdownprice1;
    }
    
    public void setPdtdownprice1(String pdtdownprice1) {
        this.pdtdownprice1 = pdtdownprice1;
    }

    public String getPdtdownprice2() {
        return this.pdtdownprice2;
    }
    
    public void setPdtdownprice2(String pdtdownprice2) {
        this.pdtdownprice2 = pdtdownprice2;
    }

    public Integer getMinrebate() {
        return this.minrebate;
    }
    
    public void setMinrebate(Integer minrebate) {
        this.minrebate = minrebate;
    }

    public Boolean getNotout() {
        return this.notout;
    }
    
    public void setNotout(Boolean notout) {
        this.notout = notout;
    }

    public Boolean getNotshowonbill() {
        return this.notshowonbill;
    }
    
    public void setNotshowonbill(Boolean notshowonbill) {
        this.notshowonbill = notshowonbill;
    }

    public Boolean getNotshow() {
        return this.notshow;
    }
    
    public void setNotshow(Boolean notshow) {
        this.notshow = notshow;
    }

    public Boolean getPdtNoShow() {
        return this.pdtNoShow;
    }
    
    public void setPdtNoShow(Boolean pdtNoShow) {
        this.pdtNoShow = pdtNoShow;
    }
   








}