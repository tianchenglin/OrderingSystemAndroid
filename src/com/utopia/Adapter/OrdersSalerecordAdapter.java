package com.utopia.Adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dialog.pop_discount;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.OrdersAcitvity;
import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyDialog;
import com.utopia.widget.MyTextView;

public class OrdersSalerecordAdapter extends BaseAdapter implements
		View.OnClickListener {
	private Context context;
	public boolean mBusy = false;
	public Cursor m_CallCursor;
	private MyDialog mBackDialog;
	private DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private d_SaleRecord locald_SaleRecord;
   // public float mDiscount=(float) 1.00;
	// private LoadingDialog mLd ;
	public OrdersSalerecordAdapter(Context paramContext, String desk_name) {
		this.context = paramContext;
		// mLd = new LoadingDialog((OrdersAcitvity)context,
		// "In the request submission");
		open();
	}

	public void clearBill(String paramString) {
		new sql_SaleRecord().clearBill(paramString);
		open();
		update_foodnum();
	}

	public void open() {
		m_CallCursor = new sql_SaleRecord()
				.recordlist3("select b.BillId,s1.itemNo,s2.pdtCode,s2.pdtName,s2.number,s2.price,s2.otherspec1,s2.otherspec2,s2.status1,s2.otherspec0,s1.tax , s1.rebate,s2.createTime1 from "
						+ "SaleRecord as s1 join saleandpdt as s2 on s1.itemNo=s2.salerecordId" +
						"  saleandpdt as s2 join Bill as b on s2.salerecordId=b.salerecordId" +
						"  where deskName='"+ Constant.table_id
						+ "'and s2.status1!='Finish'");
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
    public float getDiscount(){
    	float mdiscount=1;
    	if(this.m_CallCursor.moveToPosition(0)){   
    	     mdiscount=Float.valueOf(this.m_CallCursor.getString(this.m_CallCursor
				.getColumnIndex("rebate"))).floatValue();
    	}
    	return mdiscount;
    }
    public String getCreateTime(){
    	String mCreateTime="";
    	if(this.m_CallCursor.moveToPosition(0)){
    		mCreateTime=this.m_CallCursor.getString(m_CallCursor
    				.getColumnIndex("createTime1"));
    	}
    	return mCreateTime;
    }
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		locald_SaleRecord = new d_SaleRecord();
		if (paramView == null) {
			View localView = LayoutInflater.from(this.context).inflate(
					R.layout.orders_list, null);
			AppItem localAppItem2 = new AppItem();

			localAppItem2.menu_delete = (ImageButton) localView
					.findViewById(R.id.menu_delete); // 删除该条目按钮
			localAppItem2.menu_qty_add = (Button) localView
					.findViewById(R.id.qty_add);
			localAppItem2.menu_qty_sub = (Button) localView
					.findViewById(R.id.qty_sub);
			localAppItem2.menu_name = ((TextView) localView
					.findViewById(R.id.menu_name));
			localAppItem2.menu_qty = ((TextView) localView
					.findViewById(R.id.menu_qty));
			localAppItem2.menu_status = ((TextView) localView
					.findViewById(R.id.menu_status));
			localAppItem2.notes = (TextView) localView.findViewById(R.id.order_note);
			localAppItem2.menu_price = ((TextView) localView
					.findViewById(R.id.menu_price));
			//localAppItem2.discount_text = (Button) localView.findViewById(R.id.discount_text);
			//localAppItem2.menu_discount = ((Button) localView.findViewById(R.id.menu_discount));
			localAppItem2.subtotal = ((TextView) localView
					.findViewById(R.id.subtotal));
             
			localView.setTag(localAppItem2);
			paramView = localView;
		}
		m_CallCursor.moveToPosition(paramInt);
        
		locald_SaleRecord.setItemNo(m_CallCursor.getColumnIndex("itemNo"));
		locald_SaleRecord.setBILLID(this.m_CallCursor
				.getString(this.m_CallCursor.getColumnIndex("BillId")));
		locald_SaleRecord.setPdtCODE(this.m_CallCursor
				.getString(this.m_CallCursor.getColumnIndex("pdtCode")));
		locald_SaleRecord.setPdtName(this.m_CallCursor
				.getString(this.m_CallCursor.getColumnIndex("pdtName")));
		locald_SaleRecord.setPrice(Float.valueOf(
				this.m_CallCursor.getString(m_CallCursor
						.getColumnIndex("price"))).floatValue());
		locald_SaleRecord.setNumber((int) Float.valueOf(
				this.m_CallCursor.getString(m_CallCursor
						.getColumnIndex("number"))).floatValue());
		locald_SaleRecord.setStatus(this.m_CallCursor
				.getString(this.m_CallCursor.getColumnIndex("status1")));
    
		locald_SaleRecord.setDiscount(Float.valueOf(
				this.m_CallCursor.getString(this.m_CallCursor
						.getColumnIndex("rebate"))).floatValue());
		
		locald_SaleRecord.setOtherSpec(this.m_CallCursor
				.getString(this.m_CallCursor.getColumnIndex("otherspec0")));
		locald_SaleRecord.setOtherSpecNo1(this.m_CallCursor
				.getString(this.m_CallCursor.getColumnIndex("otherspec1")));
		locald_SaleRecord.setOtherSpecNo2(this.m_CallCursor
				.getString(this.m_CallCursor.getColumnIndex("otherspec2")));
		
		locald_SaleRecord.setCreateTime(m_CallCursor.getString(m_CallCursor
				.getColumnIndex("createTime1")));
		locald_SaleRecord.setDesk_name(Constant.table_id);
		final AppItem localAppItem1 = (AppItem) paramView.getTag();
		localAppItem1.menu_name.setText(locald_SaleRecord.getPdtName());
		BigDecimal bg = new BigDecimal(locald_SaleRecord.getPrice());
	
		localAppItem1.menu_price.setText(bg.setScale(2,
				BigDecimal.ROUND_HALF_UP).toString());
		localAppItem1.menu_qty.setText(String.valueOf(locald_SaleRecord
				.getNumber()));

		localAppItem1.menu_status.setText(locald_SaleRecord.getStatus());
		//localAppItem1.discount_text.setText(locald_SaleRecord.getDiscount()
			//	+ "");
		// 如果状态是 Done ， check能点击
		

		// 更新总价
//		localAppItem1.subtotal.setText(decimalFormat.format((locald_SaleRecord
//				.getNumber() * locald_SaleRecord.getPrice() * locald_SaleRecord
//				.getDiscount())));
		localAppItem1.subtotal.setText(decimalFormat.format((locald_SaleRecord
				.getNumber() * locald_SaleRecord.getPrice())));
		localAppItem1.notes.setText("Size:"+locald_SaleRecord.getOtherSpecNo1()+" , Hotness:"+locald_SaleRecord.getOtherSpecNo2()+" ,Notes:"+locald_SaleRecord.getOtherSpec());
		// 获得总价

		Constant.sumTotal += locald_SaleRecord.getNumber()
				* locald_SaleRecord.getPrice();

		bg = new BigDecimal(Constant.sumTotal);
		Constant.sumTotal = bg.setScale(2, BigDecimal.ROUND_HALF_UP)
				.floatValue();

	//	localAppItem1.menu_discount.setTag(locald_SaleRecord);
	//	localAppItem1.menu_discount.setOnClickListener(this);
		
	//	localAppItem1.discount_text.setTag(locald_SaleRecord);
	//	localAppItem1.discount_text.setOnClickListener(this);
	//	localAppItem1.menu_delete.setTag(locald_SaleRecord);
	//	localAppItem1.menu_delete.setOnClickListener(this);
		localAppItem1.menu_qty_add.setTag(locald_SaleRecord);
		localAppItem1.menu_qty_sub.setTag(locald_SaleRecord);
		localAppItem1.menu_qty_add.setOnClickListener(this);
		localAppItem1.menu_qty_sub.setOnClickListener(this);
       
	//	localAppItem1.menu_check.setTag(locald_SaleRecord);
		// check点击时 ，
	/*	localAppItem1.menu_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (((CheckBox) arg0).isChecked()) {
					d_SaleRecord sr = (d_SaleRecord) arg0.getTag();
					localAppItem1.menu_check.setEnabled(false);
					new RefreshAsyncTask().execute(sr);
				}

			}
		});*/
	
		return paramView;
	}

	// 构造函数AsyncTask<Params, Progress, Result>参数说明:
	// Params 启动任务执行的输入参数
	// Progress 后台任务执行的进度
	// Result 后台计算结果的类型
	private class RefreshAsyncTask extends
			AsyncTask<d_SaleRecord, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// showLoadingDialog("Just a moment, please...");
			// mLd.setText("Is updating, please wait...");
			// mLd.show();
		}

		@Override
		protected Boolean doInBackground(d_SaleRecord... sr) {
			boolean flag = false;
			try {
				new sql_SaleRecord().update1("Doned", DateUtils.getDateEN(),
						sr[0].getPdtName(), Constant.table_id,
						sr[0].getBILLID() + "", sr[0].getPdtCODE(),
						sr[0].getCreateTime());
				sr[0].setStatus("Doned");
				flag = new JsonResolveUtils(context).setSaleRecordFinish(sr[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				// if(mLd.isShowing()){
				// mLd.dismiss();
				// }
				showCustomToast("send status failed !");
			} else {
				// if(mLd.isShowing()){
				// mLd.dismiss();
				// }

				showCustomToast("send status successed test!");
			}
			// notifyDataSetChanged();
		}

	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {

		case R.id.menu_edit:
			//new pop_order(context, paramView, true);
			break;
		case R.id.menu_delete:
			if ((((d_SaleRecord) paramView.getTag()).getStatus()
					.equals("Not Sent"))) {
				showDialog((d_SaleRecord) paramView.getTag());
			} else {
				showCustomToast("Prohibit operating");
			}
			break;

		case R.id.qty_add:
			if (((d_SaleRecord) paramView.getTag()).getStatus().equals(
					"Not Sent")) {
				new sql_SaleRecord().update_numac(
						(d_SaleRecord) paramView.getTag(), 1.0F);
				((OrdersAcitvity) context).Refresh();
			} else {
				showCustomToast("Prohibit operating");
			}

			break;
		case R.id.qty_sub:
			if (((d_SaleRecord) paramView.getTag()).getStatus().equals(
					"Not Sent")) {
				new sql_SaleRecord().update_numac(
						(d_SaleRecord) paramView.getTag(), -1.0F);
				((OrdersAcitvity) context).Refresh();
			} else {
				showCustomToast("Prohibit operating");
			}

			break;

		/* case R.id.discount:

			new pop_discount(context, paramView,
					((d_SaleRecord) paramView.getTag()));
			break;*/

		}
	}

	private void showDialog(final d_SaleRecord ds) {
		mBackDialog = MyDialog.getDialog(context, "Hint",
				"Are you sure that you want to remove this item?", "Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						open();
						new sql_SaleRecord().delete(Constant.table_id,
								ds.getPdtName());
						((OrdersAcitvity) context).Refresh();
						notifyDataSetChanged();
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

	public void update_foodnum() {
		Constant.foodnumcount = new sql_SaleRecord().getOneOrderTotalNum("0");
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

	public class AppItem {
		public TextView menu_name;
		public TextView menu_qty;
		public Button menu_qty_add;
		public Button menu_qty_sub;
		public TextView menu_status;
		public TextView menu_price;
		public Button menu_discount;
		public CheckBox menu_check;
		public ImageButton menu_delete;
		public Button discount_text;
		public TextView subtotal;
		public Button done;
		public TextView size;
		public TextView hotness;
		public TextView notes;
	}

}