package com.utopia.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.utopia.Model.d_Area;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Cashier;
import com.utopia.Model.d_Contact;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_Sale;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Staff;
import com.utopia.Model.d_Tax;
import com.utopia.activity.R;
import com.utopia.widget.MyTextView;

public class JsonResolveUtils {
	public static  String BASEURL = "";
	private Context context;

	public JsonResolveUtils(Context context) {
		this.context = context;
		//sql_Setting ss = new sql_Setting(context);
		BASEURL = "http://192.168.1.249:8080";
		//BASEURL = "http://104.131.173.202";
		 //BASEURL = "http://192.168.1.100:8080";
		//BASEURL = "http://192.168.1.123:8080";
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public boolean CheckLogin(Context context, String s_account) {
		boolean ret = false;
		String url = BASEURL + "/Backoffice/UserLogin";
		String params = "s_account=" + s_account;
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");

			if (jsonObject.getString("ret").equals("online")) {
				showCustomToast("Online!");
			}
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONObject object = dataObject.getJSONObject("staff");
				if (object.length() > 0) {
					String s_pwd = object.getString("SPwd");
					String s_name = object.getString("SName");
					String type_name = object.getString("typeName");
					int priority = object.optInt("priority");
					Constant.currentStaff = new d_Staff(s_account, s_pwd,
							s_name, type_name, priority);
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(context).inflate(
				R.layout.common_toast, null);
		((MyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	public List<d_Tax> getTax() {
		List<d_Tax> taxs = new ArrayList<d_Tax>();
		d_Tax tax = new d_Tax();

		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetTax";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				JSONArray objectArr = dataObject.getJSONArray("taxs");

				JSONObject rs = null;
				for (int i = 0; i < objectArr.length(); i++) {
					rs = (JSONObject) objectArr.opt(i);
					tax = new d_Tax(rs.optInt("id"),
							rs.optString("taxeName"), rs.optDouble("rate"));

					taxs.add(tax);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taxs;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public boolean sendSaleRecords(List<d_SaleRecord> sales) {
		boolean ret = false;
		String result = "[";
		for (int i = 0; i < sales.size(); i++) {
			if (!result.equals("["))
				result += ",";
			result += sales.get(i).getString();

		}
		result += "]";
		List<Parameter> params = new ArrayList<Parameter>();
		String url = BASEURL + "/Backoffice/SetSaleRecord";
		params.add(new Parameter("saleRecords", result));
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, params);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<d_Sale> getSaleRecords() {
		List<d_Sale> sales = new ArrayList<d_Sale>();
		d_Sale sale = new d_Sale();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetSaleRecord";
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("createTime", Constant.lastTime));
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, params);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");

			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("saleRecord");
				JSONObject rs = null;
				for (int i = 0; i < objectArr.length(); i++) {
					rs = (JSONObject) objectArr.opt(i);

					sale = new d_Sale(rs.getInt("itemNo"),
							rs.getString("closeTime"),
							rs.getString("createTime"),
							rs.getString("deskName"),
							rs.getString("otherSpec"), 
							rs.getString("otherSpecNo1"),
							rs.getString("otherSpecNo2"),
							rs.getString("status"),
							rs.getString("dept"),
							(float)rs.getDouble("subtotal"),
							(float)rs.getDouble("tiptotal"),
							(float)rs.getDouble("total"),
							(float)rs.getDouble("initTotal"),
							(float)rs.getDouble("rebate"),
							(float)rs.getDouble("tax"),
							rs.getString("waiter"),
							(float)rs.getDouble("cashTotal"),
							(float)rs.getDouble("cardTotal")
							);
					sales.add(sale);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sales;
	}

	/**
	 * 网络测试
	 * 
	 * @param context
	 * @param s_account
	 * @return
	 */
	public boolean CheckService(Context context) {
		boolean ret = false;
		String url = BASEURL + "/Backoffice/Test";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<d_Product> getMenus() {
		List<d_Product> menus = new ArrayList<d_Product>();
		d_Product menu = new d_Product();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetMenus";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");

			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("menus");
				JSONObject obj = null;

				for (int i = 0; i < objectArr.length(); i++) {
					obj = (JSONObject) objectArr.opt(i);

					menu = new d_Product(obj.getString("departId"),
							obj.getString("pdtAccType"),
							obj.optInt("pdtAutoInc"), obj.optInt("pdtCanUsed"),
							obj.optInt("pdtCanZk"),
							obj.optInt("pdtChangePrice"),
							obj.getString("pdtCode"), obj.getString("pdtGg"),
							obj.getString("pdtID"), obj.optInt("pdtInMix"),
							Double.parseDouble(obj.optString("pdtInPrice")
									+ "0"), obj.getString("pdtMCode"),
							obj.optInt("pdtMakeTime"),
							obj.optString("pdtName"), obj.optInt("pdtNoShow"),
							obj.getDouble("pdtPayType"),
							obj.getString("pdtPy"), Float.parseFloat(obj
									.optString("pdtSalePrice1") + "0"),
							Double.parseDouble(obj.optString("pdtSalePrice2")
									+ "0"), obj.getString("pdtUnit"),
							obj.optInt("pdtisSet"), obj.getString("typeId"),
							obj.optInt("minrebate"), obj.optInt("notout"),
							obj.optInt("notshow"), obj.optInt("notshowonbill"),
							obj.optInt("pdtchgNumber"), Double.parseDouble(obj
									.optString("pdtdownprice1") + "0"),
							Double.parseDouble(obj.optString("pdtdownprice2")
									+ "0"));
					if (menu.getTypeId().equals("05")) {
					}
					menus.add(menu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<d_MenuType> getMenuTypes() {
		List<d_MenuType> menuTypes = new ArrayList<d_MenuType>();
		d_MenuType menuType = new d_MenuType();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetMenuTypes";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("menuTypes");
				JSONObject obj = null;
				for (int i = 0; i < objectArr.length(); i++) {
					obj = (JSONObject) objectArr.opt(i);
					menuType = new d_MenuType(obj.getString("typeId"),
							obj.getString("typeName"),
							obj.getString("typeParentId"));
					menuTypes.add(menuType);
					//Log.e("Json",menuType.getTypeId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuTypes;
	}

	public List<d_Staff> getStaff() {
		List<d_Staff> staffs = new ArrayList<d_Staff>();
		d_Staff staff = new d_Staff();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetStaff";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			//Log.e("js",jsonObject+"");
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("staffs");
				JSONObject obj = null;
				for (int i = 0; i < objectArr.length(); i++) {
					obj = (JSONObject) objectArr.opt(i);
					staff = new d_Staff(obj.getString("SAccount"),
							obj.getString("SPwd"), obj.getString("SName"),
							obj.getString("typeName"), obj.getInt("priority"));
					//Log.e("J",staff.getS_account());
					staffs.add(staff);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staffs;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<d_Area> getAreas() {
		List<d_Area> areas = new ArrayList<d_Area>();
		d_Area area = new d_Area();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetAreas";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("Areas");
				JSONObject obj = null;
				for (int i = 0; i < objectArr.length(); i++) {
					obj = (JSONObject) objectArr.opt(i);

					area = new d_Area(obj.optString("areaId"),
							obj.optString("areaName"));
					areas.add(area);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areas;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<d_Desk> getDesks(String area) {
		List<d_Desk> desks = new ArrayList<d_Desk>();
		d_Desk desk = new d_Desk();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetDesks";
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("area", area));

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, params);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("Desks");
				JSONObject obj = null;
				for (int i = 0; i < objectArr.length(); i++) {
					obj = (JSONObject) objectArr.opt(i);

					desk = new d_Desk(obj.optInt("id"),//桌子的编号
							obj.optString("typeId"), //桌子所属区域
							obj.optString("state"),  //桌子的状态
							obj.optString("SAccount"), //服务员账号
							obj.optString("deskName"), //桌子的名字
							obj.optInt("statetime"),   //标记外卖状态
							obj.optString("starttime"), //桌子开始的时间
							obj.optInt("peopleNum"),  //桌子坐的人数
							obj.optInt("row"),   //桌子所在的列
							obj.optInt("col"),    //桌子所在的行
							obj.optInt("delmark"),  //标记是否删除
							obj.optInt("message"));  //已做好菜单的数量
					desks.add(desk);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desks;
	}
    ////修改桌子上的信息
	public Boolean setDesks(d_Desk desk) {
		String url = BASEURL + "/Backoffice/SetDesk";
		Parameter para0 = new Parameter("state", desk.getState());
		Parameter para1 = new Parameter("s_account", desk.getS_account());//服务员账号
		Parameter para2 = new Parameter("people_num", desk.getPeople_num()+"");//桌子坐的人数
		Parameter para3 = new Parameter("id",desk.getDesk_name());//桌子的名字
		Parameter para4 = new Parameter("message", desk.getMessage()+"");//已做好菜单的数量
		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para0);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);
		paras.add(para4);
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}
    //添加一张新桌子
	public Boolean addDesks(d_Desk desk) {
		String url = BASEURL + "/Backoffice/AddDesk";
		Parameter para0 = new Parameter("state", desk.getState());//桌子的状态
		Parameter para1 = new Parameter("SAccount", desk.getS_account());//服务员账号
		Parameter para2 = new Parameter("typeId",desk.getType_id());  //桌子所属区域
		Parameter para3 = new Parameter("deskName",desk.getDesk_name());//桌子名
		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para0);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}
	public Boolean setSaleRecordDone(d_SaleRecord current_sr) {

		String url = BASEURL + "/Backoffice/SetSaleRecordDone";
		Parameter para0 = new Parameter("PdtCODE", current_sr.getPdtCODE());
		Parameter para1 = new Parameter("BILLID", current_sr.getBILLID());
		Parameter para2 = new Parameter("DeskName", current_sr.getDesk_name());
		Parameter para3 = new Parameter("closeTime", DateUtils.getDateEN());
        
		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para0);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/*
	 * 从后台得到厨师完成情况
	 */
	public List<d_SaleRecord> getSaleRecordDone(String table_id) {
		List<d_SaleRecord> saleRecords = new ArrayList<d_SaleRecord>();
		d_SaleRecord saleRecord = new d_SaleRecord();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetSaleRecordDone?deskName="
				+ table_id;
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("srs");
				JSONObject obj = null;
				for (int i = 0; i < objectArr.length(); i++) {
					obj = (JSONObject) objectArr.opt(i);

					saleRecord = new d_SaleRecord("", obj.optString("billid"),
							obj.optString("pdtCode"), "", 0, 0, 0, "", "",
							obj.optString("status"),
							obj.optString("deskName"), "", "", "", "", 0, 0,
							0, obj.optInt("itemNo"),1,obj.optInt("priority"));
					saleRecords.add(saleRecord);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saleRecords;
	}

	public Boolean setSaleRecordFinish(d_SaleRecord sr) {
		String url = BASEURL + "/Backoffice/SetSaleRecordFinish";
		Parameter para0 = new Parameter("billid", sr.getBILLID());
		Parameter para1 = new Parameter("pdtcode", sr.getPdtCODE());
		Parameter para2 = new Parameter("closetime", DateUtils.getDateEN());
		Parameter para3 = new Parameter("deskname", sr.getDesk_name());

		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para0);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public Boolean LogOut(Context context, String s_account) {
		boolean ret = false;
		String url = BASEURL + "/Backoffice/Logout";
		String params = "s_account=" + s_account;
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			if (ret) {
				//
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean addUser(int i, String account, String password, String name) {

		String url = BASEURL + "/Backoffice/AddUser";
		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(new Parameter("type", i + ""));
		paras.add(new Parameter("account", account));
		paras.add(new Parameter("password", password));
		paras.add(new Parameter("name", name));

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

	}

	public Boolean addContact(d_Contact contact) {

		String url = BASEURL + "/Backoffice/AddContact";
		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(new Parameter("Name",contact.getName() ));
		paras.add(new Parameter("Phone",contact.getPhone()));
		paras.add(new Parameter("Add_Number",contact.getAdd_Number()));
		paras.add(new Parameter("Add_Street",contact.getAdd_Street()));
		paras.add(new Parameter("Add_Apt",contact.getAdd_Apt()));
		paras.add(new Parameter("Add_City",contact.getAdd_City()));
		paras.add(new Parameter("Add_State",contact.getAdd_State()));
		paras.add(new Parameter("Add_Code",contact.getAdd_Code()));
		paras.add(new Parameter("Card_Number",contact.getCard_Number()));
		paras.add(new Parameter("Card_Date",contact.getCard_Date()));
		paras.add(new Parameter("Card_Cvv",contact.getCard_Cvv()));
		paras.add(new Parameter("Card_Fname",contact.getCard_Fname()));
		paras.add(new Parameter("Card_Lname",contact.getCard_Lname()));
		paras.add(new Parameter("Be_Notes",contact.getBe_Notes()));
		paras.add(new Parameter("Not_Notes",contact.getNot_Notes()));
		

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

	}
	
	public Boolean setBill(d_Bill localBill) {
		String url = BASEURL + "/Backoffice/SetBill";
		Parameter para0 = new Parameter("billid", localBill.getBillId());
		Parameter para1 = new Parameter("waiter", localBill.getWaiter());
		Parameter para2 = new Parameter("subtotal", String.valueOf(localBill
				.getSubtotal()));
		Parameter para3 = new Parameter("createtime", localBill.getCreateTime());
		Parameter para4 = new Parameter("distant", String.valueOf(localBill
				.getDistant()));
		Parameter para5 = new Parameter("tax", String.valueOf(localBill
				.getTax()));
		Parameter para6 = new Parameter("tip", String.valueOf(localBill
				.getTip()));
		Parameter para7 = new Parameter("total", String.valueOf(localBill
				.getTotal()));

		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para0);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);
		paras.add(para4);
		paras.add(para5);
		paras.add(para6);
		paras.add(para7);

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public List<d_Bill> getBills() {
		List<d_Bill> bills = new ArrayList<d_Bill>();
		d_Bill bill = new d_Bill();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetBill";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("Bills");
				JSONObject rs = null;
				for (int i = 0; i < objectArr.length(); i++) {
					rs = (JSONObject) objectArr.opt(i);
					bill = new d_Bill(rs.getString("billId"),
							rs.getString("waiter"), Float.valueOf(rs
									.getString("subtotal")),Integer.valueOf(rs
									.getString("tax")), Float.valueOf(rs
									.getString("total")),
							rs.getString("createTime"), Float.valueOf(rs
									.getString("distant")), Float.valueOf(rs
									.getString("tip")));
					bills.add(bill);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bills;
	}

	public Boolean setCashier(d_Cashier cashier) {
		String url = BASEURL + "/Backoffice/SetChangeMoney";
		Parameter para0 = new Parameter("changemoney", cashier.getChangeMoney()
				+ "");
		Parameter para1 = new Parameter("createtime", cashier.getCreateTime());
		Parameter para2 = new Parameter("usercode", cashier.getUserCode());
		Parameter para3 = new Parameter("cashierid", cashier.getCashierId());
		Parameter para4 = new Parameter("status", cashier.getStatus());

		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para0);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);
		paras.add(para4);

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	} 
    
	public Boolean setCashierInitMoney(d_Cashier cashier) {
		String url = BASEURL + "/Backoffice/SetInitMoney";
		Parameter para0 = new Parameter("initmoney", cashier.getInitMoney()
				+ "");
		Parameter para1 = new Parameter("createtime", cashier.getCreateTime());
		Parameter para2 = new Parameter("usercode", cashier.getUserCode());
		Parameter para3 = new Parameter("cashierid", cashier.getCashierId());
		Parameter para4 = new Parameter("status", cashier.getStatus());

		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para0);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);
		paras.add(para4);

		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public List<d_Cashier> getCashiers() {
		List<d_Cashier> cashiers = new ArrayList<d_Cashier>();
		d_Cashier cashier = new d_Cashier();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetCashiers";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");

			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("Cashiers");
				JSONObject rs = null;
				for (int i = 0; i < objectArr.length(); i++) {
					rs = (JSONObject) objectArr.opt(i);
					cashier = new d_Cashier(rs.getInt("id"), Float.valueOf(rs
							.getString("currentMoney")), Float.valueOf(rs
							.getString("initMoney")), Float.valueOf(rs
							.getString("changeMoney")),
							rs.getString("createTime"),
							rs.getString("userCode"),
							rs.getString("cashierId"), rs.getString("status"));
					cashiers.add(cashier);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cashiers;
	}

	public Boolean checkUpadte() {
		boolean ret = false;
		String url = BASEURL + "/Backoffice/VersionUpdateServlet";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");
			Constant.versionCode = jsonObject.optInt("versionCode");
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getTest() {
		String ret ="";
		String url = BASEURL + "/Backoffice/Test";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public List<d_Contact> getContacts() {
		List<d_Contact> contacts = new ArrayList<d_Contact>();
		d_Contact contact = new d_Contact();
		boolean ret = false;
		String url = BASEURL + "/Backoffice/GetContact";
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(json);
			// 获取返回码
			ret = jsonObject.getString("ret").equals("success");

			if (ret) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				// 获取返回用户集合
				JSONArray objectArr = dataObject.getJSONArray("contacts");
				JSONObject rs = null;
				for (int i = 0; i < objectArr.length(); i++) {
					rs = (JSONObject) objectArr.opt(i);
					contact = new d_Contact(rs.getString("name"), rs.getString("phone"), rs.getString("add_Number") ,
							rs.getString("add_Street") , rs.getString("add_Apt") , rs.getString("add_City") ,
							rs.getString("add_State") , rs.getString("add_Code") , rs.getString("card_Number") ,
							rs.getString("card_Date") , rs.getString("card_Cvv") , rs.getString("card_Fname") ,
							rs.getString("card_Lname") , rs.getString("be_Notes") , rs.getString("not_Notes") );
					contacts.add(contact);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contacts;
	}

	public Boolean deleteDeliverDesks(d_Desk desk) {

		String url = BASEURL + "/Backoffice/cleanDesk";
		Parameter para = new Parameter("deskName",desk.getDesk_name());
		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(para);
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpPost(url, paras);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}
}
