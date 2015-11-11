package com.utopia.Adapter;

import java.text.DecimalFormat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;  
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.widget.MyDialog;

public class PayBillAdapter extends BaseAdapter implements
		View.OnClickListener {
	private Context context;
	public boolean mBusy = false;
	public Cursor m_CallCursor;
	private MyDialog mBackDialog;
	private String desk_name ;
	//DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	
	public PayBillAdapter(Context paramContext , String desk_name) {
		this.context = paramContext;
		this.desk_name = desk_name;
		open();
	}

	public void clearBill(String paramString) {
		new sql_SaleRecord().clearBill(paramString);
		open();
		update_foodnum();
	}

	public void closedb() {
		this.m_CallCursor.close();
	}

	public void execsql(String paramString) {
		notifyDataSetChanged();
	}

	public int getCount() {
		if (this.m_CallCursor != null)
			return this.m_CallCursor.getCount();
		return 0;
	}

	public Object getItem(int paramInt) {
		return Integer.valueOf(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		if (paramView == null) {
			View localView = LayoutInflater.from(this.context).inflate(
					R.layout.pay_bill_orders_list, null);
			AppItem localAppItem2 = new AppItem();
			localAppItem2.menu_name = ((TextView) localView
					.findViewById(R.id.menu_name));
			localAppItem2.menu_qty = ((TextView) localView
					.findViewById(R.id.menu_qty));
			localAppItem2.menu_price = ((TextView) localView
					.findViewById(R.id.menu_price));
			localView.setTag(localAppItem2);
			paramView = localView;
		}
		this.m_CallCursor.moveToPosition(paramInt);
		d_SaleRecord locald_SaleRecord = new d_SaleRecord();
		locald_SaleRecord.setBILLID(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("BILLID")));
		locald_SaleRecord.setPdtCODE(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtCODE")));
		locald_SaleRecord.setPdtName(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtName")));
		locald_SaleRecord.setPrice(Float
				.valueOf(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("Price"))).floatValue());
		locald_SaleRecord.setNumber((int) Float.valueOf(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("number"))).floatValue());
		locald_SaleRecord.setStatus(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("status")));
		locald_SaleRecord.setOtherSpec(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("OtherSpec")));
		AppItem localAppItem1 = (AppItem) paramView.getTag();
		localAppItem1.menu_name.setText(locald_SaleRecord.getPdtName());
		localAppItem1.menu_price.setText(Constant.decimalFormat.format( (locald_SaleRecord
				.getPrice())));
		localAppItem1.menu_qty.setText(String.valueOf(locald_SaleRecord
				.getNumber()));
		return paramView;
	}

	public void onClick(View paramView) {}

	public void open() {
		this.m_CallCursor = new sql_SaleRecord()
				.recordlist3("select * from SaleRecord as s1 join saleandpdt as s2 on s1.itemNo=s2.salerecordId where s1.deskName = '"+ desk_name+"' and s2.status1!='Finish'");
	}

	public void update_foodnum() {
		Constant.foodnumcount = new sql_SaleRecord().getOneOrderTotalNum("0");
		if (Constant.foodnumhandler != null)
			Constant.foodnumhandler.sendEmptyMessage(1);
	}

	class AppItem {
		TextView menu_name;
		TextView menu_qty;
		TextView menu_price;
	}
}