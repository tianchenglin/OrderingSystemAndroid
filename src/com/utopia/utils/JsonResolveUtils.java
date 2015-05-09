package com.utopia.utils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.utopia.Dao.sql_Setting;
import com.utopia.Model.d_Area;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Cashier;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
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
		sql_Setting ss = new sql_Setting(context);
		BASEURL = "http://192.168.1.249:8080";
		//BASEURL = "http://104.131.173.202";
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public boolean CheckLogin(Context context, String s_account) {
		boolean ret = false;
		String url = BASEURL + "/RestaurantServer/UserLogin";
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
					String s_pwd = object.getString("s_pwd");
					String s_name = object.getString("s_name");
					String type_name = object.getString("type_name");
					int priority = object.optInt("priority");
					Constant.currentStaff = new d_Staff(s_account, s_pwd,
							s_name, type_name, priority,R.drawable.desk_bg,R.drawable.badge_ifaux);
					
					
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
		String url = BASEURL + "/RestaurantServer/GetTax";
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
					tax = new d_Tax(rs.optInt("tax_id"),
							rs.optString("tax_name"), rs.optDouble("tax_value"));

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
		String url = BASEURL + "/RestaurantServer/SetSaleRecord";
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
	public List<d_SaleRecord> getSaleRecords() {
		List<d_SaleRecord> saleRecords = new ArrayList<d_SaleRecord>();
		d_SaleRecord sale = new d_SaleRecord();
		boolean ret = false;
		String url = BASEURL + "/RestaurantServer/GetSaleRecord";
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

					sale = new d_SaleRecord("", rs.getString("BILLID"),
							rs.getString("pdtCODE"), rs.getString("pdtName"),
							rs.getInt("number"), (float) 0, 0,
							rs.getString("createTime"),
							rs.getString("closeTime"), rs.getString("status"),
							rs.getString("desk_name"),
							rs.getString("otherSpecNo1"),
							rs.getString("otherSpecNo2"),
							rs.getString("otherSpec"), "", (float) 0,
							(float) 0, (float) 0, rs.getInt("itemNo"));
					saleRecords.add(sale);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saleRecords;
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
		String url = BASEURL + "/RestaurantServer/Test";
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
		String url = BASEURL + "/RestaurantServer/GetMenus";
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
		String url = BASEURL + "/RestaurantServer/GetMenuTypes";
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
		String url = BASEURL + "/RestaurantServer/GetStaff";
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
					staff = new d_Staff(obj.getString("s_account"),
							obj.getString("s_pwd"), obj.getString("s_name"),
							obj.getString("type_name"), obj.getInt("priority"),R.drawable.desk_bg,R.drawable.badge_ifaux);
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
		String url = BASEURL + "/RestaurantServer/GetAreas";
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
		String url = BASEURL + "/RestaurantServer/GetDesks";
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

					desk = new d_Desk(obj.optInt("id"),
							obj.optString("type_id"), obj.optString("state"),
							obj.optString("s_account"),
							obj.optString("desk_name"),
							obj.optInt("statetime"),
							obj.optString("starttime"),
							obj.optInt("people_num"), obj.optInt("row"),
							obj.optInt("col"), obj.optInt("delmark"),
							obj.optInt("message"));
					desks.add(desk);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desks;
	}

	public Boolean setDesks(d_Desk desk) {
		String url = BASEURL + "/RestaurantServer/SetDesk";
		String params = "state=" + desk.getState() + "&s_account="
				+ desk.getS_account() + "&people_num=" + desk.getPeople_num()
				+ "&id=" + desk.getDesk_name() + "&message="
				+ desk.getMessage();
		SyncHttp syncHttp = new SyncHttp();
		String json = null;
		try {
			json = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString("ret").equals("success");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public Boolean setSaleRecordDone(d_SaleRecord current_sr) {

		String url = BASEURL + "/RestaurantServer/SetSaleRecordDone";
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
		String url = BASEURL + "/RestaurantServer/GetSaleRecordDone?deskName="
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

					saleRecord = new d_SaleRecord("", obj.optString("BILLID"),
							obj.optString("pdtCODE"), "", 0, 0, 0, "", "",
							obj.optString("status"),
							obj.optString("desk_name"), "", "", "", "", 0, 0,
							0, obj.optInt("ItemNo"));
					saleRecords.add(saleRecord);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saleRecords;
	}

	public Boolean setSaleRecordFinish(d_SaleRecord[] sr) {
		String url = BASEURL + "/RestaurantServer/SetSaleRecordFinish";
		Parameter para0 = new Parameter("billid", sr[0].getBILLID());
		Parameter para1 = new Parameter("pdtcode", sr[0].getPdtCODE());
		Parameter para2 = new Parameter("closetime", DateUtils.getDateEN());
		Parameter para3 = new Parameter("deskname", sr[0].getDesk_name());

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
		String url = BASEURL + "/RestaurantServer/Logout";
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

		String url = BASEURL + "/RestaurantServer/AddUser";
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

	public Boolean setBill(d_Bill localBill) {
		String url = BASEURL + "/RestaurantServer/SetBill";
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
		String url = BASEURL + "/RestaurantServer/GetBill";
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
					bill = new d_Bill(rs.getString("BIllId"),
							rs.getString("waiter"), Float.valueOf(rs
									.getString("subtotal")), Float.valueOf(rs
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
		String url = BASEURL + "/RestaurantServer/SetChangeMoney";
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
		String url = BASEURL + "/RestaurantServer/SetInitMoney";
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
		String url = BASEURL + "/RestaurantServer/GetCashiers";
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
		String url = BASEURL + "/RestaurantServer/VersionUpdateServlet";
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
}
