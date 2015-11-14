package server.phone.dbhelper;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.faces.application.Application;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.validator.Var;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.utopia84.model.Contact;
import com.utopia84.model.Staff;

public class JsonFromDataBase {

	private Connection conn = null;
	private CallableStatement cs = null;
	private ResultSet rs = null;
	private String sqlstr = "";

	public String getSP(String itemId)
			throws JSONException {
		sqlstr = "select * from Sizeandprice where itemId='"+itemId+"'";
		JSONObject sps = new JSONObject();
		JSONArray spArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("itemId", rs.getString("itemId"));
				sp.put("sizeName", rs.getString("sizeName"));
				sp.put("sizePrice", rs.getString("sizePrice"));
				sp.put("id", rs.getString("id"));
				spArray.put(sp);
				System.out.println(sp.toString());
			}
			sps.put("sps", spArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return sps.toString();
	
	}

	
	public String getIAM(String itemId)
			throws JSONException {
		sqlstr = "select m.modiName,m.choiceType,i.id from Itemandmodi as i join Modifier as m on i.modiId=m.id where itemId='"+itemId+"';";
		JSONObject iam = new JSONObject();
		JSONArray spArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("modiName", rs.getString("modiName"));
				sp.put("choiceType", rs.getString("choiceType"));
				sp.put("id", rs.getInt("id"));
				spArray.put(sp);
			}
			iam.put("iam", spArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return iam.toString();
	
	}
	
	public String getIAT(String itemId)
			throws JSONException {
		sqlstr = "select t.taxeName,i.id,t.rate from Itemandtax as i join Taxes as t on i.taxId=t.id  where itemId='"+itemId+"'";
		JSONObject iat = new JSONObject();
		JSONArray spArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("taxeName", rs.getString("taxeName"));
				sp.put("rate", rs.getString("rate"));
				sp.put("id", rs.getInt("id"));
				spArray.put(sp);
			}
			iat.put("iat", spArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return iat.toString();
	
	}


	public String getIiteandTags(String tagid) {
		sqlstr = "select  itemId from Itemandtags where tagid='"+tagid+"'";
		JSONObject iat = new JSONObject();
		JSONArray spArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("itemId", rs.getString("itemId"));
				spArray.put(sp);
			}
			iat.put("iat", spArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return iat.toString();
	}

	public String getIiteandTaxes(String taxesid) {
		sqlstr = "select  itemId from Itemandtax where taxId='"+taxesid+"'";
		JSONObject iat = new JSONObject();
		JSONArray spArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("itemId", rs.getString("itemId"));
				spArray.put(sp);
			}
			iat.put("iat", spArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return iat.toString();
	}
	
	public String getIiteandModi(String modiid) {
		sqlstr = "select  itemId from Itemandmodi where modiId='"+modiid+"'";
		JSONObject iat = new JSONObject();
		JSONArray spArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("itemId", rs.getString("itemId"));
				spArray.put(sp);
			}
			iat.put("iat", spArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return iat.toString();
	}
	
	public String getIiteandMenutype( ) {
		sqlstr = "select menutypeId, itemId from Itemandmenutype";
		JSONObject iat = new JSONObject();
		JSONArray spArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("itemId", rs.getString("itemId"));
				sp.put("menutypeId", rs.getInt("menutypeId"));
				spArray.put(sp);
			}
			iat.put("iat", spArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return iat.toString();
	}

	public String getModiAndOptions(String modiId) {
		sqlstr = "select  * from Options where modifierId='"+modiId+"'";
		JSONObject opt = new JSONObject();
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("id", rs.getString("id"));
				sp.put("modifierId", rs.getString("modifierId"));
				sp.put("optionName", rs.getString("optionName"));
				sp.put("optionPrice", rs.getString("optionPrice"));
				optArray.put(sp);
			}
			opt.put("opt", optArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return opt.toString();
	}	
	
	//得到所有员工的信息
	public String GetAllStaff(){
		sqlstr = "SELECT * FROM  `Staff`";
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("id", rs.getString("id"));
				sp.put("SName", rs.getString("SName"));
				sp.put("SAccount", rs.getString("SAccount"));
				sp.put("SAddr", rs.getString("SAddr"));
				sp.put("SPhone", rs.getString("SPhone"));
				sp.put("SSex", rs.getString("SSex"));
				sp.put("SType", rs.getString("SType"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}
	
	
	//得到菜单
	public String GetPdtByBillid(String salerecordId){
		sqlstr = "SELECT `pdtName`,`number`,`price` from `Saleandpdt` where `salerecordId`='"+salerecordId+"'";
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("pdtName", rs.getString("pdtName"));
				sp.put("number", rs.getString("number"));
				sp.put("price", rs.getString("price"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}	
	
	//根据条件得到消费记录
	public String GetTransactionsBySql(String sql) {
		sqlstr = "SELECT  * FROM `Salerecord`,`Staff` "+ sql;
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("itemNo", rs.getString("itemNo"));
				sp.put("dateAndTime", rs.getString("closeTime"));
				sp.put("staff", rs.getString("SName"));
				sp.put("dept", rs.getString("dept"));
				sp.put("due", rs.getString("subTotal"));
				sp.put("tip", rs.getString("tipTotal"));
				sp.put("collected", rs.getString("total"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}
	
	//得到所有交易的支付方式
	public String GetAllPayment() {
		sqlstr = "SELECT distinct  `payment` FROM `Bill`";
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("payment", rs.getString("payment"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}
	
	//得到所有交易的隶属部门
	public String GetAllDept() {
		sqlstr = "SELECT distinct  `dept` FROM `Salerecord`";
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("dept", rs.getString("dept"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}		
	
	/**
	 * 得到当前项目所在的根目录
	 */
	 public String getPorjectPath(){  
		   String nowpath;             //当前tomcat的bin目录的路径 如 D:\java\software\apache-tomcat-6.0.14\bin  
		   String tempdir;  
		   nowpath=System.getProperty("user.dir");  
		   tempdir=nowpath.replace("bin", "webapps");  //把bin 文件夹变到 webapps文件里面   
		   tempdir+="\\"+"Backoffice";  //拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro   
		   return tempdir;    
	 } 
	
	/**
	 * 尝试导出excel文件
	 */
	public String ExportExcel(String[] itemNo) {
		String path="";
		JSONArray optArray = new JSONArray();
		try {
			
			System.out.println("ExportExcelTest");
			
			String u=this.getClass().getResource("/").getPath();
			//str会得到这个函数所在类的路径
			  String str = u.toString();
			//截去一些前面6个无用的字符
			str=str.substring(1,str.length());
			//将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）
			  str=str.replaceAll("%20", " ");
			//查找“WEB-INF”在该字符串的位置
			  int num = str.indexOf("WEB-INF");
			//截取即可
			  str=str.substring(0, num)+"download/";
			//打开文件
			  System.out.println(str);
			  
			  Date date = new Date(System.currentTimeMillis());
			  SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss"); 
			  String dt = df.format(date);
			  System.out.println(dt);
			  
			  date = new Date(System.currentTimeMillis()); //半小时以后的时间
			  dt = df.format(date);
			
			path=str+dt.toString()+"_Transactions.xls";
			WritableWorkbook book=Workbook.createWorkbook(new File(path));
			//生成名为“第一页”的工作表，参数0表示这是第一页 
			WritableSheet sheet=book.createSheet("第一页", 0);
			Label label=new Label(0,0,"Date and Time"); 
			sheet.addCell(label);
			label=new Label(1,0,"Staff"); 
			sheet.addCell(label);
			label=new Label(2,0,"Payment"); 
			sheet.addCell(label);
			label=new Label(3,0,"Dept"); 
			sheet.addCell(label);
			label=new Label(4,0,"Due"); 
			sheet.addCell(label);
			label=new Label(5,0,"Collected"); 
			sheet.addCell(label);
			label=new Label(6,0,"Tip"); 
			sheet.addCell(label);
			int i,j;
			j=1;
			for(i=0;i<itemNo.length;i++){
				sqlstr = "SELECT `tipTotal`,`subTotal`,`closeTime`,`dept`,`SName`, `total` "+
						 "FROM `Salerecord`,`Staff` "+
						 "WHERE "+
						 "Staff.SAccount = Salerecord.waiter "+
						 "AND itemNo="+itemNo[i];
				System.out.println(sqlstr);
				try {
					conn = DBUtil.getConnForMySql();
					cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					rs = cs.executeQuery();
					
					while (rs.next()) {
						label=new Label(0,j,rs.getString("closeTime")); 
						sheet.addCell(label);
						label=new Label(1,j,rs.getString("SName")); 
						sheet.addCell(label);
						label=new Label(2,j,""); 
						sheet.addCell(label);
						label=new Label(3,j,rs.getString("dept")); 
						sheet.addCell(label);	
						float due,tip,collected;
						due=Float.parseFloat(rs.getString("subTotal"));
						tip=Float.parseFloat(rs.getString("tipTotal"));
						collected=Float.parseFloat(rs.getString("total"));
						
						NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
						ddf1.setMaximumFractionDigits(2); 
						String co= ddf1.format(collected); 
						String du= ddf1.format(due); 
						String ti= ddf1.format(tip); 
						
						label=new Label(4,j,du);
						sheet.addCell(label);
						label=new Label(5,j,co); 
						sheet.addCell(label);
						label=new Label(6,j,ti); 
						sheet.addCell(label);
						j++;
					}	
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
					DBUtil.CloseResources();
				}
			}
			book.write(); 
			book.close(); 
			JSONObject sp = new JSONObject();
			
			sp.put("path", dt.toString()+"_Transactions.xls");
			optArray.put(sp);
			return optArray.toString();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return optArray.toString();
	}

	/**
	 * 得到最近七天的交易记录
	 */
	public String GetSevenTransactions(String[] date) {
		JSONArray optArray = new JSONArray();
		for(int i=0;i<7;i++){
			sqlstr = "SELECT SUM( total ) AS collected FROM `Salerecord` "+
					 "where `closeTime`>='"+date[i]+" 00:00:00' and `closeTime`<='"+date[i]+" 23:59:59'";
			//System.out.println(sqlstr);
			try {
				conn = DBUtil.getConnForMySql();
				cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = cs.executeQuery();
				int flag=0;
				while(rs.next()){
					JSONObject sp = new JSONObject();
					sp.put("date", date[i]);
					sp.put("collected", rs.getString("collected"));
					optArray.put(sp);
					flag=1;
				}
				if(flag==0){
					JSONObject sp = new JSONObject();
					sp.put("date", date[i]);
					sp.put("collected", "0");
					optArray.put(sp);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtil.CloseResources();
			}
		}
		return optArray.toString();
	}
	
	/**
	 * 得到刷卡和现金的比值
	 */
	public String getBZ(String[] date){	
		JSONObject iat = new JSONObject();
		JSONArray optArray1 = new JSONArray();
		for(int i=0;i<7;i++){
			String sqlstrcash = "SELECT SUM(cashTotal) as cashTotal,SUM(cardTotal) as cardTotal FROM `Salerecord` "+
					 "where `closeTime`>='"+date[i]+" 00:00:00' and `closeTime`<='"+date[i]+" 23:59:59'";
			
			try {
				conn = DBUtil.getConnForMySql();
				cs = conn.prepareCall(sqlstrcash, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = cs.executeQuery();
				int flag=0;
				while(rs.next()){
					JSONObject sp = new JSONObject();
					sp.put("date", date[i]);
					sp.put("cashTotal", rs.getString("cashTotal"));
					sp.put("cardTotal", rs.getString("cardTotal"));
					optArray1.put(sp);
					flag=1;
				}
				if(flag==0){
					JSONObject sp = new JSONObject();
					sp.put("date", date[i]);
					sp.put("cashTotal", 0);
					sp.put("cardTotal", 0);
					optArray1.put(sp);
				}
				iat.put("result", optArray1);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtil.CloseResources();
			}
		}
		return iat.toString();
	}
	
	//根据ID得到员工详细信息
	public String GetStaffById(String id){
		sqlstr = "SELECT * FROM `Staff` where id='"+id+"'";
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("SName", rs.getString("SName"));
				sp.put("SAccount", rs.getString("SAccount"));
				sp.put("SAddr", rs.getString("SAddr"));
				sp.put("SPhone", rs.getString("SPhone"));
				sp.put("SIdent", rs.getString("SIdent"));
				sp.put("SType", rs.getString("SType"));
				sp.put("SSex", rs.getString("SSex"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}	
	
	//修改员工信息
	public String UpdateStaffMessage(Staff staff){
		sqlstr = "UPDATE `Staff` SET `SIdent`='"+staff.getSIdent()+"',`SType`='"+staff.getSType()+"',`SAccount`='"+staff.getSAccount()+"',`SAddr`='"+staff.getSAddr()+"',`SName`='"+staff.getSName()+"',`SPhone`='"+staff.getSPhone()+"',`SSex`='"+staff.getSSex()+"' WHERE `id`='"+staff.getId()+"'";
		System.out.println(sqlstr);
		JSONObject sp = new JSONObject();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int result = cs.executeUpdate();
			if(result>0){
				sp.put("updateResult", "1");
			}
			else{
				sp.put("updateResult", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return sp.toString();
	}
	
	//删除员工信息
	public String DeleteStaffById(String SAccount){
		sqlstr = "DELETE FROM `Staff` WHERE `id`='"+SAccount+"'";
		System.out.println(sqlstr);
		JSONObject sp = new JSONObject();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int result = cs.executeUpdate();
			if(result>0){
				sp.put("deleteResult", "1");
			}
			else{
				sp.put("deleteResult", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return sp.toString();
	}
	
	//修改员工信息
	public String AddStaffMessage(Staff staff){
		sqlstr = "INSERT INTO `Staff`(`SAccount`, `SAddr`, `SIdent`, `SName`, `SPhone`,`SSex`, `SType`) "+
				 "VALUES ('"+staff.getSAccount()+"','"+staff.getSAddr()+"','"+staff.getSIdent()+"','"+staff.getSName()+"','"+staff.getSPhone()+
				 "','"+staff.getSSex()+"','"+staff.getSType()+"')";
		System.out.println(sqlstr);
		JSONObject sp = new JSONObject();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int result = cs.executeUpdate();
			if(result>0){
				sp.put("addResult", "1");
			}
			else{
				sp.put("addResult", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return sp.toString();
	}	
	

	//得到所有顾客的信息
	public String GetAllCustomers(){
		sqlstr = "SELECT * FROM  `Contact`";
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("id", rs.getString("id"));
				sp.put("Name", rs.getString("Name"));
				sp.put("Phone", rs.getString("Phone"));
				sp.put("Add_Street", rs.getString("Add_Street"));
				sp.put("Add_City", rs.getString("Add_City"));
				sp.put("Add_Apt", rs.getString("Add_Apt"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}
	
	//修改顾客信息
	public String AddCustomersMessage(Contact customer){
		sqlstr = "INSERT INTO `Contact`(`Name`, `Phone`, `Add_Street`, `Add_Apt`, `Add_City`) VALUES ('"+
				 customer.getName()+"','"+customer.getPhone()+"','"+customer.getAdd_Street()+"','"+customer.getAdd_Apt()+"','"+customer.getAdd_City()+"')";
		System.out.println(sqlstr);
		JSONObject sp = new JSONObject();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int result = cs.executeUpdate();
			if(result>0){
				sp.put("addResult", "1");
			}
			else{
				sp.put("addResult", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return sp.toString();
	}
	
	//删除顾客信息
	public String DeleteCustomersById(String id){
		sqlstr = "DELETE FROM `Contact` WHERE `id`="+id;
		System.out.println(sqlstr);
		JSONObject sp = new JSONObject();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int result = cs.executeUpdate();
			if(result>0){
				sp.put("deleteResult", "1");
			}
			else{
				sp.put("deleteResult", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return sp.toString();
	}
	
	//修改顾客信息
	public String UpdateCustomersMessage(Contact customer){
		sqlstr = "UPDATE `Contact` SET `Name`='"+customer.getName()+"',`Phone`='"+customer.getPhone()+"',`Add_Street`='"+
				 customer.getAdd_Street()+"',`Add_Apt`='"+customer.getAdd_Apt()+"',`Add_City`='"+customer.getAdd_City()+"' WHERE id="+customer.getId();
		System.out.println(sqlstr);
		JSONObject sp = new JSONObject();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int result = cs.executeUpdate();
			if(result>0){
				sp.put("updateResult", "1");
			}
			else{
				sp.put("updateResult", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return sp.toString();
	}
	
	//根据ID得到顾客详细信息
	public String GetCustomersById(int id){
		sqlstr = "SELECT * FROM `Contact` where id="+id;
		System.out.println(sqlstr);
		JSONArray optArray = new JSONArray();
		try {
			conn = DBUtil.getConnForMySql();
			cs = conn.prepareCall(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = cs.executeQuery();
			
			while (rs.next()) {
				JSONObject sp = new JSONObject();
				sp.put("id", rs.getString("id"));
				sp.put("Name", rs.getString("Name"));
				sp.put("Phone", rs.getString("Phone"));
				sp.put("Add_Street", rs.getString("Add_Street"));
				sp.put("Add_Apt", rs.getString("Add_Apt"));
				sp.put("Add_City", rs.getString("Add_City"));
				optArray.put(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseResources();
		}
		return optArray.toString();
	}	
}
