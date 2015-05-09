package com.utopia.Adapter;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor; 
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dialog.pop_order;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.R;
import com.utopia.activity.OrderFormActivity;
import com.utopia.utils.Constant;
import com.utopia.widget.MyDialog;
import com.utopia.widget.MyTextView;

public class SalerecordAdapter extends BaseAdapter implements
		View.OnClickListener {
	private Context context;
	public boolean mBusy = false;
	public Cursor m_CallCursor;
	private MyDialog mBackDialog;
	private String desk_name;
	DecimalFormat df = new DecimalFormat("0.00");
	public SalerecordAdapter(Context paramContext,String desk_name) {
		this.desk_name = desk_name;
		this.context = paramContext;
		open();
	}

	public void additem(AbsListView paramAbsListView, int paramInt1,
			int paramInt2) {
		for (int i = 0; i < paramInt2; i++) {
			AppItem localAppItem = (AppItem) paramAbsListView.getChildAt(i)
					.getTag();
			if (localAppItem != null) {
				this.m_CallCursor.moveToPosition(paramInt1 + i);
				d_SaleRecord locald_SaleRecord = new d_SaleRecord();
				locald_SaleRecord.setBILLID(this.m_CallCursor.getString(0));
				locald_SaleRecord.setPdtCODE(this.m_CallCursor.getString(1));
				locald_SaleRecord.setPdtName(this.m_CallCursor.getString(2));
				locald_SaleRecord.setNumber((int) Float.valueOf(
						this.m_CallCursor.getString(3)).floatValue());
				locald_SaleRecord.setPrice(Float.valueOf(
						this.m_CallCursor.getString(4)).floatValue());
				locald_SaleRecord.setStatus(this.m_CallCursor.getString(5));
				locald_SaleRecord.setOtherSpec(this.m_CallCursor.getString(6));
				localAppItem.menu_dishName.setText(locald_SaleRecord
						.getPdtName());
				localAppItem.menu_price.setText(df.format((locald_SaleRecord.getPrice())));
				localAppItem.menuNum.setText(String.valueOf(locald_SaleRecord
						.getNumber()));
				localAppItem.menu_state_tv.setText(locald_SaleRecord
						.getStatus());
				localAppItem.menu_cutBtn.setTag(locald_SaleRecord);
				localAppItem.menu_addBtn.setTag(locald_SaleRecord);
				localAppItem.menu_edit.setTag(locald_SaleRecord);
				localAppItem.menu_delete.setTag(locald_SaleRecord);
				localAppItem.menu_cutBtn.setOnClickListener(this);
				localAppItem.menu_addBtn.setOnClickListener(this);
				localAppItem.menu_edit.setOnClickListener(this);
				localAppItem.menu_delete.setOnClickListener(this);
			}
		}
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
					R.layout.mmyorders_list, null);
			AppItem localAppItem2 = new AppItem();
			localAppItem2.menu_dishImage = ((ImageView) localView
					.findViewById(R.id.menu_dishImage));
			localAppItem2.menu_dishName = ((TextView) localView
					.findViewById(R.id.menu_dishName));
			localAppItem2.menu_unit = ((TextView) localView
					.findViewById(R.id.menu_unit));
			localAppItem2.menu_price = ((TextView) localView
					.findViewById(R.id.menu_price));
			localAppItem2.menuNum = ((TextView) localView
					.findViewById(R.id.menuNum));
			localAppItem2.menu_state_tv = ((TextView) localView
					.findViewById(R.id.menu_state_tv));
			localAppItem2.menu_cutBtn = ((Button) localView
					.findViewById(R.id.menu_cutBtn));
			localAppItem2.menu_addBtn = ((Button) localView
					.findViewById(R.id.menu_addBtn));
			localAppItem2.menu_edit = ((Button) localView
					.findViewById(R.id.menu_edit));
			localAppItem2.menu_delete = ((Button) localView
					.findViewById(R.id.menu_delete));
			localAppItem2.menu_dishImage.setImageResource(R.drawable.mdish1);
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
		locald_SaleRecord.setNumber((int) Float.valueOf(
				this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("number"))).floatValue());
		locald_SaleRecord.setStatus(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("status")));
		locald_SaleRecord.setOtherSpec(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("OtherSpec")));
		locald_SaleRecord.setItemNo(m_CallCursor.getInt(m_CallCursor.getColumnIndex("ItemNo")));
		//locald_SaleRecord.setDesk_name(m_CallCursor.getString(m_CallCursor.getColumnIndex("desk_name")));
		AppItem localAppItem1 = (AppItem) paramView.getTag();
		localAppItem1.menu_dishName.setText(locald_SaleRecord.getPdtName());
		localAppItem1.menu_price.setText(df.format((locald_SaleRecord
				.getPrice())));
		localAppItem1.menuNum.setText(String.valueOf(locald_SaleRecord
				.getNumber()));
		localAppItem1.menu_state_tv.setText(locald_SaleRecord.getStatus());
		localAppItem1.menu_cutBtn.setTag(locald_SaleRecord);
		localAppItem1.menu_addBtn.setTag(locald_SaleRecord);
		localAppItem1.menu_edit.setTag(locald_SaleRecord);
		localAppItem1.menu_delete.setTag(locald_SaleRecord);
		localAppItem1.menu_cutBtn.setOnClickListener(this);
		localAppItem1.menu_addBtn.setOnClickListener(this);
		localAppItem1.menu_edit.setOnClickListener(this);
		localAppItem1.menu_delete.setOnClickListener(this);
		return paramView;
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.menu_cutBtn:
			new sql_SaleRecord().update_numac(
					(d_SaleRecord) paramView.getTag(), -1.0F);
			open();
			update_foodnum();
			((OrderFormActivity) this.context).startOnResume();
			notifyDataSetChanged();
			break;
		case R.id.menu_addBtn:
			new sql_SaleRecord().update_numac(
					(d_SaleRecord) paramView.getTag(), 1.0F);
			open();
			update_foodnum();
			((OrderFormActivity) this.context).startOnResume();
			notifyDataSetChanged();
			break;
		case R.id.menu_edit:
			new pop_order(this.context, paramView, true);
			open();
			update_foodnum();
			((OrderFormActivity) this.context).startOnResume();
			notifyDataSetChanged();
			break;
		case R.id.menu_delete:
			if ((((d_SaleRecord) paramView.getTag()).getStatus().equals("Not Sent"))) {
				showDialog((d_SaleRecord) paramView.getTag());

			} else {
				showCustomToast("Prohibit operating");
				}
			break;
		}
	}

	private void showDialog(final d_SaleRecord ds) {
		mBackDialog = MyDialog.getDialog(context, "Hint", "Are you sure the you want to remove this item?", "Yes",
				new DialogInterface.OnClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new sql_SaleRecord().delete(Constant.table_id, ds.getItemNo());
						open();
						update_foodnum();
						((OrderFormActivity) context).startOnResume();
						notifyDataSetChanged();
						dialog.dismiss();
					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);
		mBackDialog.show();

	}

	public void open() {
		this.m_CallCursor = new sql_SaleRecord()
				.recordlist3("select BILLID,ItemNo,PdtCODE,PdtName,"
						+ "Price,number,status,OtherSpec from SaleRecord where desk_name='"+Constant.table_id+"' and status<>'Finish' order by ItemNo");
	}
	
	

	public void update_foodnum() {
		Constant.foodnumcount = new sql_SaleRecord().getOneOrderTotalNum(Constant.table_id);
		if (Constant.foodnumhandler != null)
			Constant.foodnumhandler.sendEmptyMessage(1);
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
	class AppItem {
		TextView menuNum;
		Button menu_addBtn;
		Button menu_cutBtn;
		Button menu_delete;
		ImageView menu_dishImage;
		TextView menu_dishName;
		Button menu_edit;
		TextView menu_price;
		TextView menu_state_tv;
		TextView menu_unit;

		AppItem() {
		}
	}
}