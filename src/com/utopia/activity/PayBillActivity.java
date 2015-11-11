package com.utopia.activity;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.utopia.Adapter.PayBillBillAdapter;
import com.utopia.Adapter.PayBillTotalAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Customer;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Customer;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Service.BluetoothService;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;

@SuppressLint("HandlerLeak")
public class PayBillActivity extends BaseActivity implements
		View.OnClickListener {
	public PayBillTotalAdapter<d_SaleRecord> sladapter;
	private ListView localListView;
	private TextView tip; // 小费
	private boolean isSplit = false;
	// 添加按钮
	private Button bt_add;
	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	// Key names received from the BluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	// Intent request codes
	public static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the services
	private BluetoothService mService = null;
	// 获得餐桌信息
	d_Desk currentDesk = new d_Desk();
	d_Bill localBill = new d_Bill();
	private TextView sum_total;
	private String str;
	private TextView tax_id;
	private TextView totalText; // 税收+菜单总价
	private float tax;
	private float discount;
	private String total = "";
	private float subtotal = (float) 0.00;
	private String md5 = "";
	private Context context;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	public PayBillBillAdapter<d_SaleRecord> sladapterBill;
	private Button printbill;
	public List<View> views = new ArrayList<View>();
	public List<PayBillBillAdapter<d_SaleRecord>> adapters = new ArrayList<PayBillBillAdapter<d_SaleRecord>>();

	private TextView add_all;
	private LinearLayout add_all_linear;
	private Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈

		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.check_bill_detial);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			finish();
		}

		savedInstanceState = getIntent().getExtras();

		if (savedInstanceState.getString("tax").equals("")) {
			str = "0.00";
		} else {
			str = savedInstanceState.getString("tax");
		}

		if (str.equals("")) {
			str = "0.00";
		}

		if (str.equals("0.0")) {
			str = "0.00";
		}

		tax_id = (TextView) findViewById(R.id.tax_id);

		tax_id.setText("$" + decimalFormat.format(Float.valueOf(str)));

		if (getIntent().getExtras().getString("discount").equals(""))
			discount = (float) 1.0;
		else
			discount = Float.valueOf(
					getIntent().getExtras().getString("discount")).floatValue();

		md5 = getIntent().getExtras().getString("md5");

		total = getIntent().getExtras().getString("total");
		new sql_Customer().delete("delete from Customer");// 清空bill
		initViews();
		initEvents();
		// 获取餐桌订单 ， 并显示
		initlist();

		subtotal = new sql_SaleRecord().sumTotalDone(Constant.table_id);

		sum_total.setText("$" + decimalFormat.format(subtotal)); // 总价

		totalText.setText("$" + total);

	}

	@Override
	protected void initViews() {
		sum_total = (TextView) findViewById(R.id.sum_total);
		localListView = (ListView) findViewById(R.id.menu_list);
		bt_add = (Button) findViewById(R.id.bt_add);
		totalText = (TextView) findViewById(R.id.total);
		add_all = (TextView) findViewById(R.id.table_pop_credit);
		add_all_linear = (LinearLayout) findViewById(R.id.add_all);
		cancel = (Button) findViewById(R.id.cancel);
		printbill = (Button) findViewById(R.id.print_bill);

	}

	@Override
	protected void initEvents() {
		bt_add.setOnClickListener(this);
		printbill.setOnClickListener(this);
		add_all.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	public void initlist() {
		getTotalBills();// 母版订单视图
		getBill(); // 子版订单视图
	}

	private void getBill() {

		final LinearLayout lin = (LinearLayout) findViewById(R.id.ll_add);
		final View add = View.inflate(this, R.layout.add, null);
		views.add(add); // 把顾客添加到集合

		// 去掉当前子bill
		add.findViewById(R.id.bt_sub).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				lin.removeView(add);
				int position = Integer.parseInt(((TextView) add
						.findViewById(R.id.title)).getText().toString()
						.substring(4));

				deleteBill(position - 1);
				views.remove(position - 1);
				if (views.size() > 1)// 第一个bill不做处理
					views.get(views.size() - 1).findViewById(R.id.bt_sub)
							.setVisibility(View.VISIBLE);
				Refresh();
			}
		});

		lin.addView(add);

		TextView title = (TextView) add.findViewById(R.id.title);
		title.setText("Bill" + views.size());
		add.findViewById(R.id.menu_list).setTag(views.size());
		if (views.size() != 1) {// 除去第一个bill其它的都可删除
			if (views.size() >= 2)
				views.get(views.size() - 2).findViewById(R.id.bt_sub)
						.setVisibility(View.GONE);
			add.findViewById(R.id.bt_sub).setVisibility(View.VISIBLE);
		}
		// 子bill的listview
		ListView localListView2;
		localListView2 = (ListView) add.findViewById(R.id.menu_list);

		List<d_SaleRecord> data = getSaleRecordsCustomer1(views.size());
		sladapterBill = new PayBillBillAdapter<d_SaleRecord>(this, data,
				R.layout.pay_bill_orders_list_bill);

		adapters.add(sladapterBill);
		localListView2.setAdapter(sladapterBill);
	}

	public void initData(int postion) {
		float sub = 0;
		View add = views.get(postion);// 要更新的ui
		List<d_SaleRecord> data = getSaleRecordsCustomer1(postion);
		d_SaleRecord tmp;
		for (int i = 0; i < data.size(); i++) {
			tmp = data.get(i);
			sub += tmp.getPrice() * 1.0 / tmp.getCustomerNo();
		}

		TextView subtotal1 = (TextView) (add.findViewById(R.id.sum_total));
		subtotal1.setText("$" + decimalFormat.format(sub));

		TextView tv_discount = (TextView) (add.findViewById(R.id.discount));
		tv_discount.setText(discount + "");

		TextView tax = (TextView) (add.findViewById(R.id.tax_id));
		tax.setText("$" + decimalFormat.format(sub * 0.0625));

		TextView total = (TextView) (add.findViewById(R.id.total));
		total.setText("$"
				+ decimalFormat.format((sub * discount + sub * 0.0625)));

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
				.recordlist3("select * from SaleRecord as s join saleandpdt as s2 on s2.salerecordId=s.itemNo" +
						"where deskName = '"
						+ Constant.table_id + "' and s2.status1!='Finish'");
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
					add_all.setEnabled(true);
					printbill.setClickable(false);
					printbill.setBackground(getResources().getDrawable(
							R.drawable.yuanjiao3));

					add_all_linear.setBackgroundColor(Color
							.parseColor("#DCEFFF"));
					if (views.size() <= 1)
						add_all.setText("Add the rest");
					else
						add_all.setText("Split the rest");
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
	public void delSaleRecordsCustomer1(int customNo, int ItemNo) {
		new sql_SaleRecord()
				.recordlist5("delete  from Customer  where  ItemNo='" + ItemNo
						+ "' and customNo='" + customNo + "'");
	}

	/*
	 * 获取顾客一菜单数据
	 */
	public List<d_SaleRecord> getSaleRecordsCustomer1(int i) {
		List<d_SaleRecord> d_SaleRecords = new ArrayList<d_SaleRecord>();

		Cursor m_CallCursor, sub_CallCursor;
		m_CallCursor = new sql_SaleRecord()
				.recordlist3("select s2.pdtName,s2.price,s2.number,s.itemNo,c.customNo from " +
						"SaleRecord as s join Customer as c on s.itemNo=c.ItemNo " +
						"SaleRecord as s join saleandpdt as s2 on s.itemNo=s2.salerecordId " +
						"where s.deskName = '"+ Constant.table_id
						+ "' and s2.status1!='Finish' and c.customNo='" + i + "'");
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
			ds.setItemNo(Integer.parseInt(m_CallCursor.getString(m_CallCursor
					.getColumnIndex("ItemNo"))));

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

	@SuppressLint("NewApi")
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.bt_add:
			if (views.size() >= 1) {
				add_all.setText("Split");
			}
			getBill();
			break;
		// 打印订单
		case R.id.print_bill:
			startActivityForResult(new Intent(PayBillActivity.this,
					BluetoothListActivity.class), 1);
			break;
		// add_all
		case R.id.table_pop_credit:
			splitAll();
			if (isSplit) {
				printbill.setClickable(true);
				printbill.setBackground(getResources().getDrawable(
						R.drawable.yuanjiao2));
			}
			for (int i = 0; i < views.size(); i++) {
				adapters.get(i).mDatas = getSaleRecordsCustomer1(i);
				adapters.get(i).notifyDataSetChanged();
				initData(i);
			}
			isSplit = true;// 分单完成

			sladapter.mDatas = getSaleRecordsAll();
			sladapter.notifyDataSetChanged();

			add_all.setEnabled(false);
			add_all_linear.setBackgroundColor(Color.parseColor("#AFAFAF"));
			bt_add.setVisibility(View.GONE);
			break;

		case R.id.cancel:
			if (views.size() == 1 && vilaitdSaleRecords())
				finish();
			new sql_Customer().delete("delete from Customer");// 清空bill
			adapters = new ArrayList<PayBillBillAdapter<d_SaleRecord>>();
			views = new ArrayList<View>();// 清空views
			((LinearLayout) findViewById(R.id.ll_add)).removeAllViews();// 清空bill
																		// view
			isSplit = false;
			getBill();
			Refresh();

			add_all.setText("Add all");
			add_all.setEnabled(true);
			add_all_linear.setBackgroundColor(Color.parseColor("#DCEFFF"));
			bt_add.setEnabled(true);
			bt_add.setVisibility(View.VISIBLE);
			break;

		}
	}

	public boolean vilaitdSaleRecords() {
		String ItemNo;
		Cursor m_CallCursor, sub_CallCursor;
		m_CallCursor = new sql_SaleRecord()
				.recordlist3("select * from SaleRecord as s1 join saleandpdt as s2 " +
						"on s1.itemNo=s2.salerecordId where s1.deskName = '"
						+ Constant.table_id + "' and s2.status1!='Finish'");
		while (m_CallCursor.moveToNext()) {
			ItemNo = m_CallCursor.getString(m_CallCursor
					.getColumnIndex("itemNo"));
			sub_CallCursor = new sql_SaleRecord()
					.recordlist3("select count(*) from Customer  where  ItemNo='"
							+ ItemNo + "'");
			sub_CallCursor.moveToNext();
			if (sub_CallCursor.getInt(0) > 0)
				return false;
		}
		m_CallCursor.close();
		return true;

	}

	private void splitAll() {
		List<d_SaleRecord> dSaleRecords = getSaleRecordsAll();

		d_Customer dCus = new d_Customer();

		sql_Customer sCustomer = new sql_Customer();
		for (int i = 0; i < views.size(); i++)
			for (int j = 0; j < dSaleRecords.size(); j++) {
				if (dSaleRecords.get(j).getCustomerNo() == 0) {
					dCus.setCustomNo(i);
					dCus.setItemNo(dSaleRecords.get(j).getItemNo());
					sCustomer.save(dCus);
				}
			}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			BluetoothDevice device = mBluetoothAdapter
					.getRemoteDevice(Constant.printerAddress);
			if (mBluetoothAdapter == null) {
				showCustomToast("Printer is not available");
			} else {
				mService.connect(device);
				printTest();
			}
		}

	}

	public void printTest() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					int i = 0;
					while (mService.getState() != BluetoothService.STATE_CONNECTED) {
						Thread.sleep(500);
						if (i++ > 30) {
							mService.stop();
							Thread.sleep(500);
							return false;
						}
					}
				} catch (InterruptedException e) {
					mService.stop();
					e.printStackTrace();
					return false;
				}
				return true;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("Printer authentication failed !");
				} else
					print();
			}
		});

	}

	private void print() {

		if (mService == null)
			mService = new BluetoothService(this, mHandler);

		if (!fontGrayscaleSet(4)) {
			return;
		}

		// 初始化打印机
		byte[] byteInit = new byte[] { 0x1B, 0x40 };
		mService.write(byteInit);

		// 左对齐
		byte[] byteC = new byte[] { 0x1B, 0x61, (byte) 0x30 };

		// 打印标题
		byte[] byteA = new byte[] { 0x1D, 0x21, (byte) 0x0110 };
		mService.write(byteA);
		sendMessage("\n\nShangri-La\n\n");

		// 打印正文
		byte[] byteB = new byte[] { 0x1D, 0x21, (byte) 0x0000 };
		mService.write(byteB);
		for (int j = 0; j < views.size(); j++) {
			sendMessage("\n\n");
			sendMessage("--------------Bill" + j + "-----------\n");
			Cursor m_CallCursor;
			m_CallCursor = new sql_SaleRecord()
					.recordlist3("select s2.pdtName,s2.price,s2.number,s.itemNo,c.customNo from " +
			"SaleRecord as s join Customer as c on s.itemNo=c.ItemNo " +
			"SaleRecord as s join saleandpdt as s2 on s.itemNo=s2.salerecordId " +
			"where s.deskName = '"+ Constant.table_id
			+ "' and s2.status1!='Finish' and c.customNo='" + j + "'");
			
			while (m_CallCursor.moveToNext()) {
				mService.write(byteB);

				if (m_CallCursor.getString(
						m_CallCursor.getColumnIndex("pdtName")).length() > 12) {
					sendMessage(lpad(
							m_CallCursor.getString(
									m_CallCursor.getColumnIndex("pdtName"))
									.substring(0, 12), 17));
				} else if (m_CallCursor.getString(
						m_CallCursor.getColumnIndex("pdtName")).length() <= 12) {
					sendMessage(lpad(m_CallCursor.getString(m_CallCursor
							.getColumnIndex("pdtName")), 17));
				}

				mService.write(byteB);
				sendMessage(lpad(m_CallCursor.getString(m_CallCursor
						.getColumnIndex("price")), 9));
				mService.write(byteB);
				sendMessage(m_CallCursor.getString(m_CallCursor
						.getColumnIndex("number")) + "\n");
				mService.write(byteC);

			}
			m_CallCursor.close();
		}

		mService.write(byteB);
		openBox();
		finish();

	}


	/**
	 * 补齐不足长度
	 * 
	 * @param length
	 *            长度
	 * @param str
	 *            字符串
	 * @return
	 */
	private String lpad(String str, int length) {
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() < length) {
			sb.append(" ");
		}

		return sb.toString();
	}

	@SuppressLint("NewApi")
	@Override
	public void onStart() {
		super.onStart();
		if (mService == null)
			mService = new BluetoothService(this, mHandler);

	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mService.getState() == BluetoothService.STATE_NONE) {
				// Start the Bluetooth services
				mService.start();//
			}
		}
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	/**
	 * Set font gray scale
	 */
	private boolean fontGrayscaleSet(int ucFontGrayscale) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
			showCustomToast("Connection error");
			return false;
		}
		if (ucFontGrayscale < 1)
			ucFontGrayscale = 4;
		if (ucFontGrayscale > 8)
			ucFontGrayscale = 8;
		byte[] send = new byte[3];// ESC m n
		send[0] = 0x1B;
		send[1] = 0x6D;
		send[2] = (byte) ucFontGrayscale;
		mService.write(send);
		return true;
	}

	private void openBox() {
		byte[] byteA = new byte[] { 0x1B, 0x70, 0x00, (byte) 0xFE, (byte) 0xFE };
		mService.write(byteA);
		
		if (mService != null)
			mService.stop();
	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 * 
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {

			// showCustomToast("The printer connection error");
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothService to write
			byte[] send;
			try {
				send = message.getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				send = message.getBytes();
			}
			mService.write(send);
		}
	}

	// The Handler that gets information back from the BluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		}
	};

	public void Refresh() {
		for (int j = 0; j < views.size(); j++) {
			adapters.get(j).mDatas = (List<d_SaleRecord>) getSaleRecordsCustomer1(j);
			adapters.get(j).notifyDataSetChanged();
			initData(j);
		}
		sladapter.mDatas = (List<d_SaleRecord>) getSaleRecordsAll();
		sladapter.notifyDataSetChanged();
	}

}