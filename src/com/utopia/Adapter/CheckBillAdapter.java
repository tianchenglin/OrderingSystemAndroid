package com.utopia.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView; 
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.R;

public class CheckBillAdapter extends BaseAdapter implements
		View.OnClickListener {
	private Context context;
	public boolean mBusy = false;
	public Cursor m_CallCursor;

	public CheckBillAdapter(Context paramContext) {
		this.context = paramContext;
		open();
	}

	public void additem(AbsListView paramAbsListView, int paramInt1,
			int paramInt2) {
		for (int i = 0;; i++) {
			if (i >= paramInt2)
				return;
			AppItem localAppItem = (AppItem) paramAbsListView.getChildAt(i)
					.getTag();
			if (localAppItem != null) {
				this.m_CallCursor.moveToPosition(paramInt1 + i);
				d_SaleRecord locald_SaleRecord = new d_SaleRecord();
				locald_SaleRecord.setBILLID(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("BILLID")));
				locald_SaleRecord.setPdtCODE(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtCODE")));
				locald_SaleRecord.setPdtName(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtName")));
				locald_SaleRecord.setPrice(Float.valueOf(
						this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("price"))).floatValue());
				localAppItem.m_check_list_no.setText(locald_SaleRecord
						.getPdtName());
				localAppItem.m_check_list_tableno.setText(String
						.valueOf(locald_SaleRecord.getPrice()));
				localAppItem.m_check_list_operator.setTag(locald_SaleRecord);
				localAppItem.m_check_list_detail.setTag(locald_SaleRecord);
				localAppItem.m_check_list_operator.setOnClickListener(this);
				localAppItem.m_check_list_detail.setOnClickListener(this);
			}
		}
	}

	public void closedb() {
		this.m_CallCursor.close();
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
					R.layout.checkbill_list, null);
			AppItem localAppItem2 = new AppItem();
			//编号
			localAppItem2.m_check_list_no = ((TextView) localView
					.findViewById(R.id.m_check_list_no));
			//订单号
			localAppItem2.m_check_list_order_no = ((TextView) localView
					.findViewById(R.id.m_check_list_order_no));
			//台号
			localAppItem2.m_check_list_tableno = ((TextView) localView
					.findViewById(R.id.m_check_list_tableno));
			//开台时间
			localAppItem2.m_check_list_time = ((TextView) localView
					.findViewById(R.id.m_check_list_time));
			
			//总价
			localAppItem2.m_check_list_sumprice = ((TextView) localView
					.findViewById(R.id.m_check_list_sumprice));
			//状态
			localAppItem2.m_check_list_state = ((TextView) localView
					.findViewById(R.id.m_check_list_state));
			
			//操作
			localAppItem2.m_check_list_lable = ((TextView) localView
					.findViewById(R.id.m_check_list_lable));
			//编辑
			localAppItem2.m_check_list_operator = ((Button) localView
					.findViewById(R.id.m_check_list_operator));
			//详情
			localAppItem2.m_check_list_detail = ((Button) localView
					.findViewById(R.id.m_check_list_detail));
			localView.setTag(localAppItem2);
			paramView = localView;
		}
		this.m_CallCursor.moveToPosition(paramInt);
		d_SaleRecord locald_SaleRecord = new d_SaleRecord();
		locald_SaleRecord.setBILLID(this.m_CallCursor.getString(m_CallCursor.getColumnIndex("BILLID")));
		locald_SaleRecord.setPdtCODE(this.m_CallCursor.getString(m_CallCursor.getColumnIndex("PdtCODE")));
		locald_SaleRecord.setPdtName(this.m_CallCursor.getString(m_CallCursor.getColumnIndex("PdtName")));
		locald_SaleRecord.setPrice(Float
				.valueOf(this.m_CallCursor.getString(m_CallCursor.getColumnIndex("Price"))).floatValue());
		AppItem localAppItem1 = (AppItem) paramView.getTag();
	//编号
		localAppItem1.m_check_list_no.setText(locald_SaleRecord.getBILLID());
	//订单号
		localAppItem1.m_check_list_order_no.setText(locald_SaleRecord.getBILLID());
	//台号
		localAppItem1.m_check_list_tableno.setText(String
				.valueOf(locald_SaleRecord.getDesk_name()));
	//开台时间
		localAppItem1.m_check_list_time.setText(locald_SaleRecord.getCreateTime()+"");
	//总价
		localAppItem1.m_check_list_sumprice.setText(locald_SaleRecord.getPrice()+"");
	//状态
		localAppItem1.m_check_list_state.setText(locald_SaleRecord.getStatus());
		localAppItem1.m_check_list_operator.setTag(locald_SaleRecord);
		localAppItem1.m_check_list_detail.setTag(locald_SaleRecord);
		localAppItem1.m_check_list_operator.setOnClickListener(this);
		localAppItem1.m_check_list_detail.setOnClickListener(this);
		return paramView;
	}

	public void onClick(View paramView) {
		paramView.getId();
	}

	public void open() {
		this.m_CallCursor = new sql_SaleRecord()
				.recordlist3("select c.BillId,s2.salerecordId,s2.pdtCode,s2.pdtName,s2.price,s2.number,s2.status1,s2.otherspec0" +
						" from saleandpdt as s2 left JOIN Product as b ON s2.pdtCode=b.PdtCode " +
						" saleandpdt as s2 join Bill as c on s2.salerecordId=c.salerecordId " +
						" order by s2.salerecordId");
	}

	public void scroll(AbsListView paramAbsListView, int paramInt) {
		switch (paramInt) {
		default:
			return;
		case 0:
			this.mBusy = false;
			additem(paramAbsListView,
					paramAbsListView.getFirstVisiblePosition(),
					paramAbsListView.getChildCount());
			return;
		case 1:
			this.mBusy = true;
			return;
		case 2:
		}
		this.mBusy = true;
	}

	class AppItem {
		Button m_check_list_detail;
		TextView m_check_list_lable;
		TextView m_check_list_no;
		Button m_check_list_operator;
		TextView m_check_list_order_no;
		TextView m_check_list_state;
		TextView m_check_list_sumprice;
		TextView m_check_list_tableno;
		TextView m_check_list_time;

		AppItem() {
		}
	}
}