package com.utopia.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.utopia.Adapter.PayBillBillAdapter;
import com.utopia.Adapter.PayBillTotalAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Bill;
import com.utopia.Dao.sql_Customer;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dialog.pop_Inputpay;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_SaleRecord;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.utils.Md5;
import com.utopia.utils.Snippet;
import com.utopia.widget.MyDialog;

@SuppressLint("HandlerLeak")
public class CleanTableActivity extends BaseActivity implements
		View.OnClickListener {
	public PayBillTotalAdapter<d_SaleRecord> sladapter;
	private ListView localListView;
	private TextView tip; // 小费
	private boolean isSplit = false;
	// 添加按钮
	private MyDialog recoveryDialog;
	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// 获得餐桌信息
	d_Desk currentDesk = new d_Desk();
	d_Bill tBill = new d_Bill();
	private TextView sum_total, totalAndTips, tips;
	private float tax;
	private float pay_bill_tips = (float) 0.0;
	private float subtotal = (float) 0.00;
	private Context context;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	public PayBillBillAdapter<d_SaleRecord> sladapterBill;

	public List<View> views = new ArrayList<View>();
	public List<PayBillBillAdapter<d_SaleRecord>> adapters = new ArrayList<PayBillBillAdapter<d_SaleRecord>>();

	private LinearLayout add_all_linear;
	private Button pay_bill_cencal, pay_bill_clearn;
	private float payStyle[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	List<List<d_SaleRecord>> datas = new ArrayList<List<d_SaleRecord>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈

		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.pay_bill_detial);

		savedInstanceState = getIntent().getExtras();

		initViews();
		initEvents();
		// 获取餐桌订单 ， 并显示
		initlist();

		subtotal = new sql_SaleRecord().sumTotalDone(Constant.table_id);
		sum_total.setText("$" + decimalFormat.format(subtotal)); // 总价
		totalAndTips.setText("$" + decimalFormat.format(subtotal)); // 总价
	}

	@Override
	protected void initViews() {
		sum_total = (TextView) findViewById(R.id.sum_total);
		tips = (TextView) findViewById(R.id.pay_bill_tips);
		totalAndTips = (TextView) findViewById(R.id.totalAndTips);

		localListView = (ListView) findViewById(R.id.menu_list);
		add_all_linear = (LinearLayout) findViewById(R.id.add_all);

		pay_bill_clearn = (Button) findViewById(R.id.pay_bill_clearn);
		pay_bill_cencal = (Button) findViewById(R.id.pay_bill_cencal);

	}

	@Override
	protected void initEvents() {
		pay_bill_cencal.setOnClickListener(this);
		pay_bill_clearn.setOnClickListener(this);
	}

	public void initlist() {
		getTotalBills();// 母版订单视图
		int Customer = new sql_Customer().getCustomerNum();
		for (int i = 0; i <= Customer; i++)
			splitBill(i); // 子版订单视图
	}

	private void splitBill(final int number) {
		final LinearLayout lin = (LinearLayout) findViewById(R.id.ll_add);
		final View add = View.inflate(this, R.layout.pay_bill_add, null);
		views.add(add); // 把顾客添加到集合
		lin.addView(add);
		TextView title = (TextView) add.findViewById(R.id.title);
		title.setText("Bill" + views.size());
		add.findViewById(R.id.menu_list).setTag(views.size());

		// 子bill的listview
		ListView localListView2;
		localListView2 = (ListView) add.findViewById(R.id.menu_list);

		List<d_SaleRecord> data = getSaleRecordsCustomer1(views.size() - 1);
		sladapterBill = new PayBillBillAdapter<d_SaleRecord>(this, data,
				R.layout.pay_bill_orders_list_bill);
		double bill_due = 0.0;
		for (int i = 0; i < data.size(); i++)
			bill_due += data.get(i).getPrice() / data.get(i).getCustomerNo();
		((TextView) add.findViewById(R.id.bill_due)).setText("$"
				+ decimalFormat.format(bill_due));
		((EditText) add.findViewById(R.id.bill_pay))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						new pop_Inputpay(CleanTableActivity.this, add, tips,
								totalAndTips, subtotal);
					}
				});

		((Button) add.findViewById(R.id.bill_add_credit))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						payStyle[number] = 1;
						arg0.setBackgroundColor(Color.parseColor("#29b6f6"));
						((Button) add.findViewById(R.id.bill_add_cash))
								.setBackgroundColor(Color.parseColor("#ffffff"));
					}
				});
		((Button) add.findViewById(R.id.bill_add_cash))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						payStyle[number] = 0;
						arg0.setBackgroundColor(Color.parseColor("#29b6f6"));
						((Button) add.findViewById(R.id.bill_add_credit))
								.setBackgroundColor(Color.parseColor("#ffffff"));
					}
				});

		adapters.add(sladapterBill);
		localListView2.setAdapter(sladapterBill);
		datas.add(data);
	}

	public void initData(int postion) {
		float sub = 0;
		List<d_SaleRecord> data = getSaleRecordsCustomer1(postion);
		d_SaleRecord tmp;
		for (int i = 0; i < data.size(); i++) {
			tmp = data.get(i);
			sub += tmp.getPrice() * 1.0 / tmp.getCustomerNo();
		}

		sum_total.setText("$" + decimalFormat.format(sub));
		tips.setText("$" + pay_bill_tips);
		totalAndTips.setText("$" + decimalFormat.format(sub + pay_bill_tips));

	}

	public void deleteBill(int i) {
		sql_Customer sCus = new sql_Customer();
		sCus.delete("delete from Customer where customNo='" + i + "'");
	}

	/*
	 * 母版订单视图
	 */
	private void getTotalBills() {
		getSaleRecordsAll();
		if (sladapter == null)
			sladapter = new PayBillTotalAdapter<d_SaleRecord>(this,
					getSaleRecordsAll(), R.layout.pay_bill_orders_list);
		localListView.setAdapter(sladapter);
	}

	/*
	 * 获取全部菜单数据
	 */
	@SuppressLint("NewApi")
	public List<d_SaleRecord> getSaleRecordsAll() {
		List<d_SaleRecord> d_SaleRecords = new ArrayList<d_SaleRecord>();

		Cursor m_CallCursor, sub_CallCursor;
		m_CallCursor = new sql_SaleRecord()
				.recordlist3("select * from SaleRecord as a JOIN saleandpdt as b ON a.itemNo=b.salerecordId where a.desk_name=='"
						+ Constant.table_id + "' and b.status1!='Finish'");
		while (m_CallCursor.moveToNext()) {
			d_SaleRecord ds = new d_SaleRecord();
			ds.setPdtName(m_CallCursor.getString(m_CallCursor
					.getColumnIndex("pdtName")));
			ds.setPrice(Float
					.valueOf(
							m_CallCursor.getString(m_CallCursor
									.getColumnIndex("price"))).floatValue());
			ds.setNumber((int) Float.valueOf(
					m_CallCursor.getString(m_CallCursor
							.getColumnIndex("number"))).floatValue());
			ds.setItemNo(Integer.parseInt(m_CallCursor.getString(m_CallCursor
					.getColumnIndex("itemNo"))));

			try {
				sub_CallCursor = new sql_SaleRecord()
						.recordlist3("select count(*) from Customer  where  ItemNo='"
								+ ds.getItemNo() + "'");
				sub_CallCursor.moveToNext();
				ds.setCustomerNo(sub_CallCursor.getInt(0));
				if (ds.getCustomerNo() == 0 && isSplit) {// 已经分过单后，仍有没分出去的菜
					add_all_linear.setBackgroundColor(Color
							.parseColor("#DCEFFF"));
				}
			} catch (NumberFormatException e) {
				ds.setCustomerNo(0);
			}
			d_SaleRecords.add(ds);
		}
		m_CallCursor.close();
		return d_SaleRecords;

	}

	/*
	 * 获取顾客一菜单数据
	 */
	public List<d_SaleRecord> getSaleRecordsCustomer1(int i) {
		List<d_SaleRecord> d_SaleRecords = new ArrayList<d_SaleRecord>();

		Cursor m_CallCursor, sub_CallCursor;
		m_CallCursor = new sql_SaleRecord()
				.recordlist3("select a.pdtName,a.price,a.number,s.itemNo,c.customNo from SaleRecord as s join Customer as c on s.itemNo=c.ItemNo" +
						"  SaleRecord as s join  saleandpdt as a on s.itemNo=a.salerecordId where s.desk_name = '"
						+ Constant.table_id
						+ "' and a.status1!='Finish' and c.customNo='" + i + "'");
		while (m_CallCursor.moveToNext()) {
			d_SaleRecord ds = new d_SaleRecord();
			ds.setItemNo(Integer.parseInt(m_CallCursor.getString(m_CallCursor
					.getColumnIndex("itemNo"))));
			ds.setPdtName(m_CallCursor.getString(m_CallCursor
					.getColumnIndex("pdtName")));
			ds.setPrice(Float
					.valueOf(
							m_CallCursor.getString(m_CallCursor
									.getColumnIndex("price"))).floatValue());
			ds.setNumber((int) Float.valueOf(
					m_CallCursor.getString(m_CallCursor
							.getColumnIndex("number"))).floatValue());
//			ds.setItemNo(Integer.parseInt(m_CallCursor.getString(m_CallCursor
//					.getColumnIndex("ItemNo"))));

			ds.setCustomerNo(Integer.parseInt(m_CallCursor
					.getString(m_CallCursor.getColumnIndex("customNo"))));
			ds.setBILLID(i + "");// 不要删
			try {
				sub_CallCursor = new sql_SaleRecord()
						.recordlist3("select count(*) from Customer  where  ItemNo='"
								+ ds.getItemNo() + "'");
				sub_CallCursor.moveToNext();
				ds.setCustomerNo(sub_CallCursor.getInt(0));
			} catch (NumberFormatException e) {
				ds.setCustomerNo(0);
			}
			d_SaleRecords.add(ds);
		}
		m_CallCursor.close();
		return d_SaleRecords;

	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.pay_bill_cencal:
			finish();
			break;
		case R.id.pay_bill_clearn:
			clearTableConfirm();
			break;
		}
	}

	/**
	 * 确认清台
	 */
	private void clearTableConfirm() {
		recoveryDialog = MyDialog.getDialog(CleanTableActivity.this, "Hint",
				"You sure want to clear the table...", "Yse",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						saveBill();
						clearTable();
						dialog.dismiss();
					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				});
		recoveryDialog.setButton1Background(R.drawable.btn_default_popsubmit);
		recoveryDialog.show();
	}

	/*
	 * 后台清桌
	 */
	private void clearTable() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					d_Desk desk = new d_Desk(0, "", "EMPTY", "EMPTY",
							Constant.table_id, 0, "", 0, 0, 0, 0, 0);
					sql_SaleRecord ss = new sql_SaleRecord();
					ss.update("Finish", Constant.table_id);
					//new JsonResolveUtils(context).sendSaleRecords(getSaleRecordsAll());
					boolean flag =  new JsonResolveUtils(CleanTableActivity.this)
					.setDesks(desk);
					Thread.sleep(1000);
					return flag;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("clear table failed !");
				} else {
					showCustomToast("clear table finished !");
					Intent intent = new Intent(CleanTableActivity.this,
							DeskMenuActivity.class);
					startActivity(intent);
				}
			}
		});

	}

	private void saveBill() {
		// initTBill
		View t_view = null;
		for (int i = 0; i < datas.size(); i++) {
			t_view = views.get(i);
			float pay = Float.parseFloat(((TextView) t_view
					.findViewById(R.id.bill_pay)).getText().toString().replace('$', '0'));
			float due = Float.parseFloat(((TextView) t_view
					.findViewById(R.id.bill_due)).getText().toString().replace('$', '0'));
			float tips = Float.parseFloat(((TextView) t_view
					.findViewById(R.id.bill_tips)).getText().toString().replace('$', '0'));
			tBill = new d_Bill(Md5.md5(Snippet.generateID()),
					Constant.currentStaff.getS_name(), due, payStyle[i], pay,
					DateUtils.getDateEN(), datas.get(i).size(), tips);
			new sql_Bill().save(tBill);
			new RefreshAsyncTask().execute();
		}
	}

	// 构造函数AsyncTask<Params, Progress, Result>参数说明:
	// Params 启动任务执行的输入参数
	// Progress 后台任务执行的进度
	// Result 后台计算结果的类型
	private class RefreshAsyncTask extends AsyncTask<Void, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog("Just a moment, please...");
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			return new JsonResolveUtils(context).setBill(tBill);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			dismissLoadingDialog();
			if (!result) {
				showCustomToast("send Bill failed !");
			} else {
				showCustomToast("send Bill successed !");
			}
		}

	}
}