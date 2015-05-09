package com.utopia.activity;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.utopia.Adapter.PayBillAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Bill;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dao.sql_desk;
import com.utopia.Dialog.popMoneyCharged;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Desk;
import com.utopia.Service.BluetoothService;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;

@SuppressLint("HandlerLeak")
public class PayBillActivity extends BaseActivity implements
		View.OnClickListener {
	private TextView waiter;
	private TextView desk_name;
	private TextView distance_2; // 开台时间
	private PayBillAdapter sladapter;
	private ListView localListView;
	private TextView tip; // 小费
	private TextView tip1;
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
	private TextView distance;
	private TextView sum_total;
	private TextView distance_3;
	private String str;
	private TextView tax_id;
	private TextView totalText; // 税收+菜单总价
	private float tax;
	private float discount;
	private TextView discountText;
	private String total = "";
	private float subtotal = (float) 0.00;
	private String md5 = "";
	private TextView give_change;
	private Context context;
	private HomeKeyLocker mHomeKeyLocker;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private float b ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(PayBillActivity.this);

		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.check_bill_detial);

		savedInstanceState = getIntent().getExtras();		
		str = savedInstanceState.getString("tax");
		if (getIntent().getExtras().getString("discount").equals(""))
			discount = (float) 1.0;
		else
			discount = Float.valueOf(
					getIntent().getExtras().getString("discount")).floatValue();

		md5 = getIntent().getExtras().getString("md5");
		initViews();
		initEvents();
		// 获取餐桌订单 ， 并显示
		initlist();

		subtotal = new sql_SaleRecord().sumTotalDone(Constant.table_id);
		discountText.setText(decimalFormat.format(discount)); // 折扣
		sum_total.setText("$" +decimalFormat.format(subtotal)); // 总价

		if (str.equals("")) {
			str = "0.00";
		}
		
		if (str.equals("0.0")) {
			str = "0.00";
		}

		tax_id = (TextView) findViewById(R.id.tax_id);

		tax_id.setText(decimalFormat.format(Float.valueOf(str)));
		float a = subtotal* Float.valueOf(discountText.getText().toString()).floatValue();
		b = a + Float.valueOf(str);
		total=decimalFormat.format(b);
		
		totalText.setText("$" + total);
		// Get local Bluetooth adapter
		// 通过BluetoothAdapter类的getDefaultAdapter() 方法获得一个系统默认可用的蓝牙设备
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			showCustomToast("Bluetooth is not available");
			finish();
			return;
		}

	}

	/*
	 * 获得当前餐桌的属性
	 */
	public void initDesk() {
		Cursor cursor = new sql_desk().select(Constant.table_id);
		while (cursor.moveToNext()) {
			currentDesk.setPeople_num(Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("people_num"))));
			currentDesk.setStarttime(cursor.getString(cursor
					.getColumnIndex("starttime")));
			currentDesk.setType_id(cursor.getString(cursor
					.getColumnIndex("type_id")));
			currentDesk.setS_account(cursor.getString(cursor
					.getColumnIndex("s_account")));
		}

		cursor.close();
	}

	public void initlist() {
		if (this.sladapter == null)
			this.sladapter = new PayBillAdapter(this, Constant.table_id);
		localListView.setAdapter(this.sladapter);
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {

		// 服务员输入金额
		case R.id.distance_3:
			// 服务员输入金额
			new popMoneyCharged(this, paramView);
			break;

		case R.id.close0:
			this.finish();
			break;
			
		case R.id.table_pop_cash:
			// 更新时间 ， 该餐桌的每一条订单都更新时间
			new sql_SaleRecord().update_time(desk_name.getText().toString());
			if (distance_3.getText().toString().equals("")) {
				distance_3.setText("0.00");
				showCustomToast("Please input Money charged…");
				break;
			}
			new sql_SaleRecord().update_tip(desk_name.getText().toString(),Constant.tip);
			new sql_SaleRecord().update("Finish", Constant.table_id);
			if (tip.getText().equals("")) {
				tip.setText("0.00");
			}
			
			saveBill();
			
			fontGrayscaleSet(4);
			
			// 初始化打印机
			byte[] byteInit = new byte[] { 0x1B, 0x40 };
			mService.write(byteInit);

			// 左对齐
			byte[] byteC = new byte[] { 0x1B, 0x61, (byte) 0x30 };
			// 居中对齐
			byte[] byteD = new byte[] { 0x1B, 0x61, (byte) 0x31 };
			// 右对齐
			byte[] byteE = new byte[] { 0x1B, 0x61, (byte) 0x32 };

			// 打印标题
			byte[] byteA = new byte[] { 0x1D, 0x21, (byte) 0x0110 };
			mService.write(byteA);
			sendMessage("Shangri-La\n\n");

			// 打印正文
			byte[] byteB = new byte[] { 0x1D, 0x21, (byte) 0x0000 };
			mService.write(byteB);
			sendMessage("Table No ：" + desk_name.getText().toString()
					+ "\nPeople No:" + distance.getText().toString()
					+ "\nTime：" + distance_2.getText().toString()
					+ " \nWaiter No.：" + waiter.getText().toString() + " \n\n");
			sendMessage("------------------------------\n");

			// 打印菜单
			sendMessage("Dish name   Unit price    Qty\n");
			Cursor menu = new sql_SaleRecord()
					.recordlist3("select * from SaleRecord where desk_name = '"
							+ desk_name.getText().toString()
							+ "'and status='Finish'");
			while (menu.moveToNext()) {
				mService.write(byteB);
				
				if(menu.getString(menu.getColumnIndex("PdtName")).length()>12){
					sendMessage(lpad(menu.getString(menu.getColumnIndex("PdtName")).substring(0, 12),17));
				}else if(menu.getString(menu.getColumnIndex("PdtName")).length()<=12){
					sendMessage(lpad(menu.getString(menu.getColumnIndex("PdtName")),17));
				}				
				
				mService.write(byteB);
				sendMessage(lpad(menu.getString(menu.getColumnIndex("Price")),
						9));
				mService.write(byteB);
				sendMessage(menu.getString(menu.getColumnIndex("number"))
						+ "\n");
				mService.write(byteC);
				// sendMessage("------------------------------\n");
			}
			menu.close();

			sendMessage("\n");

			

			mService.write(byteB);
			sendMessage("Total consumptlon：$"
					+ decimalFormat.format(Constant.sumTotal) + "\nDistount:"
					+ decimalFormat.format(discount) + "	Tax：$"
					+ decimalFormat.format(Float.valueOf(tax_id.getText().toString()))
					+ "\nTotal:$" + decimalFormat.format(Float.valueOf(total)) + "	Tip:$"
					+ decimalFormat.format(Float.valueOf(tip.getText().toString()))
					+ "\nMoney charged：$"
					+ decimalFormat.format(Float.valueOf(distance_3.getText().toString()))
					+ "" + "\nGive Charge:$"
					+ decimalFormat.format(Float.valueOf(give_change.getText().toString()))
					+ "\n");
			sendMessage("\n\n\n");
			openBox();

			// finish printer clear table
			// ((OrdersAcitvity) context).clearTableCheck();
			break;

		case R.id.bt_add:

			final LinearLayout lin = (LinearLayout) findViewById(R.id.ll_add);
			final View add = View.inflate(this, R.layout.add, null);
			lin.addView(add);
			add.findViewById(R.id.close).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							lin.removeView(add);
						}
					});

			add.findViewById(R.id.due).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View view) {
							new popMoneyCharged(PayBillActivity.this, view);
						}
					});

			add.findViewById(R.id.paid).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View view) {
							new popMoneyCharged(PayBillActivity.this, view);
						}
					});
			tip1 = (TextView) findViewById(R.id.tip1);
			tip1.setText("$0.0");
			break;

		case R.id.tip:
			new popMoneyCharged(this, paramView);

			break;
		}
	}

	private void saveBill() {
		localBill.setBillId(md5);
		localBill.setCreateTime(DateUtils.getDateEN());
		localBill.setDistant(discount);
		localBill.setSubtotal(subtotal); 
		localBill.setTax(tax);
		localBill.setTotal(b);
		localBill.setWaiter(Constant.userCode); 
		localBill.setTip(Float.valueOf(tip.getText().toString())); 
		new sql_Bill().save(localBill);
		new RefreshAsyncTask().execute();
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
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new JsonResolveUtils(context).setBill(localBill);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("send Bill failed !");
				} else {
					showCustomToast("send Bill successed !");
				}
			}

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
		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the session
		} else {
			if (mService == null)
				setupChat();
		}
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

	private void setupChat() {
		String address = "";
		// Initialize the BluetoothService to perform bluetooth connections
		mService = new BluetoothService(this, mHandler);
		if (Constant.printerAddress.equals("")) {
			showCustomToast("Bluetooth is not available");
			return;
		} else {
			address = Constant.printerAddress;
		}
		// Get the BLuetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mService.connect(device);
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mService != null)
			mService.stop();
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
	}

	/**
	 * Set font gray scale
	 */
	private void fontGrayscaleSet(int ucFontGrayscale) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
			showCustomToast("Connection error");
			return;
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
	}

	private void openBox() {
		byte[] byteA = new byte[] { 0x1B, 0x70, 0x00, (byte) 0xFE, (byte) 0xFE };
		mService.write(byteA);
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
		if (distance_3.getText().toString().equals("")) {
			distance_3.setText("0.00");
		}
		if (tip.getText().toString().equals("")) {
			tip.setText("0.00");
		}

		give_change.setText(decimalFormat.format(Float.valueOf(distance_3
				.getText().toString())
				- Float.valueOf(total.toString())
				- Float.valueOf(tip.getText().toString())));

	}


	@Override
	protected void initViews() {
		// 台号
		desk_name = ((TextView) findViewById(R.id.table_popNum));
		desk_name.setText(Constant.table_id);
		give_change = (TextView) findViewById(R.id.give_change);
		initDesk(); // 获得当前桌信息

		distance = (TextView) findViewById(R.id.distance);
		distance.setText(currentDesk.getPeople_num() + ""); // 餐桌人数 。。。。。

		distance_2 = (TextView) findViewById(R.id.distance_2);
		distance_2.setText(DateUtils.getDateEN()); // 当前时间 

		distance_3 = (TextView) findViewById(R.id.distance_3);
		findViewById(R.id.distance_3).setOnClickListener(this);
		// 服务员编号
		this.waiter = ((TextView) findViewById(R.id.waiter));
		waiter.setText(currentDesk.getS_account());

		sum_total = (TextView) findViewById(R.id.sum_total);
		localListView = (ListView) findViewById(R.id.menu_list);
		bt_add = (Button) findViewById(R.id.bt_add);
		totalText = (TextView) findViewById(R.id.total);
		tip = (TextView) findViewById(R.id.tip);

		discountText = (TextView) findViewById(R.id.discount);
	}

	@Override
	protected void initEvents() {
		((Button) findViewById(R.id.table_pop_cash)).setOnClickListener(this);
		((Button) findViewById(R.id.table_pop_credit)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.close0)).setOnClickListener(this);
		bt_add.setOnClickListener(this);
		tip.setOnClickListener(this);
	}
}