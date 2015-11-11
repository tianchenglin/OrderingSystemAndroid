package com.utopia.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Test;
import com.utopia.Model.d_Area;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Cashier;
import com.utopia.Model.d_Contact;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_Sale;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Saleandpdt;
import com.utopia.Model.d_Staff;
import com.utopia.Model.d_Tax;
import com.utopia.Service.UpdateManager;
import com.utopia.activity.InternetBroadcastReceiver.NetworkChangeReceiver;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.InitSql;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyDialog;
import com.utopia.widget.MyProgressView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

	private IntentFilter intentFilter;
	private NetworkChangeReceiver networkChangeReceiver;
	//private boolean connection_tag;
	private int time = 0;
	private InitSql initsql;

	// 四位员工编号
	private int[] password = new int[] { R.id.passwd_1, R.id.passwd_2,
			R.id.passwd_3, R.id.passwd_4 };

	private AlertDialog.Builder builder;
	private AlertDialog dialog;

	private MyProgressView mTasksView;

	private int mTotalProgress = 100;
	private int mCurrentProgress = 0;

	private TextView tv_title;
	private TextView tv_content;
	private MyDialog mBackDialog;
   
	private BluetoothAdapter mBtAdapter;
	private StringBuilder pwd = new StringBuilder("");

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.main);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		// If the adapter is null, then Bluetooth is not supported
		if (!mBtAdapter.isEnabled()) {
			// 创建一个intent对象，该对象用于启动一个Activity，提示用户开户蓝牙设备
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(intent);
		}
		
		//Log.i("tag",DateUtils.getDateEN().substring(11, 13)+"小时");
		initEvents();
		intentFilter=new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver=new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver,intentFilter);
		// 后台读入
	//	Constant.DATABASE_PATH = getApplicationContext().getFilesDir()
//				.getAbsolutePath();
//		new Constant().copy(this);
//		try {
//			Thread.sleep(500L);
//		} catch (InterruptedException localInterruptedException) {
//			localInterruptedException.printStackTrace();
//		}
//
	//	versionUpdate();
//
//		initsql = new InitSql();
	//	update_All();
		
		
	}
public class NetworkChangeReceiver extends BroadcastReceiver{

		
		//public static boolean connection_tag=false;//网络连接的标志，连接为真
		@Override
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager connectionManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
			NetworkInfo networkInfo=connectionManager.getActiveNetworkInfo();
			if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){
				Constant.DATABASE_PATH = getApplicationContext().getFilesDir()
						.getAbsolutePath();
				new Constant().copy(LoginActivity.this);
				try {
					Thread.sleep(500L);
				} catch (InterruptedException localInterruptedException) {
					localInterruptedException.printStackTrace();
				}

				versionUpdate();

				initsql = new InitSql();
				update_All();
				
				Log.i("tag","有网络。。。。。");
			}else{
				AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
				dialogBuilder.setTitle("Warning");
				dialogBuilder.setMessage("network is unavailable");
				dialogBuilder.setCancelable(false);
				dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
						//ExitApplication.getInstance().exit();
						LoginActivity.this.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)); 
						
					}
				});
				AlertDialog alertDialog=dialogBuilder.create();
				alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				alertDialog.show();
				//connection_tag=false;
			}
		}

	}

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	unregisterReceiver(networkChangeReceiver);
}
	private void versionUpdate() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				return new JsonResolveUtils(LoginActivity.this).checkUpadte();
			}

			/*
			 * onPostExecute(Result) 相当于Handler 处理UI的方式， 在这里面可以使用在doInBackground
			 * 得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				new UpdateManager(LoginActivity.this).checkUpdate();
			}
		});
	}

	public void onClick(View paramView) {
		if (paramView.getId() == R.id.login_clear) {
			if (time > 0) {
				--time;
				this.findViewById(password[time]).setBackgroundResource(
						R.drawable.login_num);
				pwd.deleteCharAt(pwd.length() - 1); // 删除最后一个字符
			}
			return;
		}
		if (paramView.getId() == R.id.loginBtn) {
			login(); // 登陆 从后台得到数
			return;
		}
		if (paramView.getId() == R.id.but_set) {
			//new pop_setpwd(LoginActivity.this, paramView); // 改成英文
			return;
		}
		/*if (paramView.getId() == R.id.but_test) {
			mCurrentProgress = 0;
			initToast();
			return;
		}*/

		if (paramView.getId() == R.id.loginTrans) {/*
													 * Intent i = new
													 * Intent(this,
													 * MainSpellActivity.class);
													 * startActivity(i);
													 */
			return;
		}

		if (time < 4) {
			this.findViewById(password[time]).setBackgroundResource(
					R.drawable.login_num2);
			switch (paramView.getId()) {
			case R.id.loginnum0:
				//在pwd字符串后面加上后面括号里的字符串
				pwd.append("0");
				break;
			case R.id.loginnum1:
				pwd.append("1");
				break;
			case R.id.loginnum2:
				pwd.append("2");
				break;
			case R.id.loginnum3:
				pwd.append("3");
				break;
			case R.id.loginnum4:
				pwd.append("4");
				break;
			case R.id.loginnum5:
				pwd.append("5");
				break;
			case R.id.loginnum6:
				pwd.append("6");
				break;
			case R.id.loginnum7:
				pwd.append("7");
				break;
			case R.id.loginnum8:
				pwd.append("8");
				break;
			case R.id.loginnum9:
				pwd.append("9");
				break;
			}
			time++;
		}
	}
    // staffs进入的员工（包括经理）的信息
	private List<d_Staff> staffs = new ArrayList<d_Staff>();
	

	private void login() {
		if (pwd.toString().length() != 4) {
			showCustomToast("The input character length less than four characters !");
			return;
		}

		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

			/*
			 * onPreExecute()这里是最终用户调用execute时的接口， 当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			 * 
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
			}

			/*
			 * doInBackground(Params…) 后台执行， 比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
			 * 此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。
			 * 在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			 * 
			 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
			 */

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
					// staff = JsonResolveUtils.GetUserInfo(LoginActivity.this);
					// Log.e("user", user.toString());
					// 从服务器得到用户名 密码 表
					staffs = new JsonResolveUtils(LoginActivity.this)
							.getStaff();
					return new JsonResolveUtils(LoginActivity.this).CheckLogin(
							LoginActivity.this, pwd.toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}

			/*
			 * onPostExecute(Result) 相当于Handler 处理UI的方式， 在这里面可以使用在doInBackground
			 * 得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("Login password authentication failed !");
				} else {
					for (int i = 0; i < staffs.size(); i++) {
						if (Constant.currentStaff.getS_account().equals(
								staffs.get(i).getS_account())) {
							Constant.currentStaff = staffs.get(i);
						}
					}
					if (Constant.currentStaff.getPriority() == 3) {// 厨师登录
						LoginActivity.this.startActivity(new Intent(
								LoginActivity.this, CookActivity.class));

					} else if (Constant.currentStaff.getPriority() == 1
							|| Constant.currentStaff.getPriority() == 0) {
						LoginActivity.this.startActivity(new Intent(
								LoginActivity.this, ShiftReportActivity.class));
					} else {
						Constant.clockInTime = DateUtils.getDateEN();

						LoginActivity.this.startActivity(new Intent(
								LoginActivity.this, DeviceListActivity.class));
					}
				}
			}
		});
	}

	private void update_All() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Is updating, please wait...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
			
				String numbers1[] = new JsonResolveUtils(LoginActivity.this)
						.getTest().split(",");
				String numbers2[] = new sql_Test().getNumber().split(",");		
				Log.i("tag","hhhhhhhhhhhhhhhhhh "+numbers1.length+" HHHHHHHHHHHHHHH");
				for(int i=0;i<numbers1.length;i++){
					Log.i("tag","numbers1 "+numbers1[i]);
					Log.i("tag","numbers2 "+numbers2[i]);
					
				}
				
				try {//判断后台数据有没有更改
					if (!numbers1[0].equals(numbers2[0])) {
						new InitSql().clearnAllMenu();
						// ////////////////////////////////////////
						List<d_Product> menus = new JsonResolveUtils(
								LoginActivity.this).getMenus();
						for (int i = 0; i < menus.size(); i++) {
							initsql.saveMenu(menus.get(i));
							Thread.sleep(50);
						}
						Log.e("Menu", menus.size() + "");
						//Log.i("tag")
					}
					// ////////////////////////////////////////
					if (!numbers1[1].equals(numbers2[1])) {
						// 餐桌 。
						new InitSql().clearnAllDesk();
						List<d_Desk> desks = new JsonResolveUtils(
								LoginActivity.this).getDesks("");
						for (int i = 0; i < desks.size(); i++) {
							initsql.saveDesk(desks.get(i));
							Thread.sleep(50);
						}
						Log.e("desks", desks.size() + "");
					}
					// /////////////////////////////////////////
					//后台数据到本地数据库Area表中
					if (!numbers1[2].equals(numbers2[2])) {
						new InitSql().clearnAllArea();
						List<d_Area> areas = new JsonResolveUtils(
								LoginActivity.this).getAreas();
						for (int i = 0; i < areas.size(); i++) {
							initsql.saveArea(areas.get(i));
							Thread.sleep(50);
							// Log.e("areas", areas.get(i).getAreaName()+"");
						}
						Log.e("areas", areas.size() + "");
					}
					// /////////////////////////////////////////
					if (!numbers1[3].equals(numbers2[3])) {
						new InitSql().clearnAllMenutype();
						List<d_MenuType> menuTypes = new JsonResolveUtils(
								LoginActivity.this).getMenuTypes();
						for (int i = 0; i < menuTypes.size(); i++) {
							// Log.e("menuTypes",
							// menuTypes.get(i).getTypeId()+"");
							initsql.saveMenuType(menuTypes.get(i));
							Thread.sleep(50);
						}
						Log.e("menuTypes", menuTypes.size() + "");
					}
					
					// 添加用户
					/*
					 * Thread.sleep(1000); List<d_Staff> staff = new
					 * JsonResolveUtils(SettingActivity.this).getStaff();
					 * for(int i = 0; i < staff.size(); i++){
					 * initsql.saveStaff(staff.get(i)); }
					 */
					if (!numbers1[4].equals(numbers2[4])) {
						new InitSql().clearnAllTax();
						List<d_Tax> taxs = new JsonResolveUtils(
								LoginActivity.this).getTax();
						for (int i = 0; i < taxs.size(); i++) {
							initsql.saveTax(taxs.get(i));
							Thread.sleep(50);
						}
						Log.e("taxs", taxs.size() + "");
					}
					////////更新销售记录SaleRecord////////////////////////
					if (!numbers1[5].equals(numbers2[5])) {
						new InitSql().clearnAllSaleRecord();
						List<d_Sale> sales = new JsonResolveUtils(
								LoginActivity.this).getSaleRecords();
						for (int i = 0; i < sales.size(); i++) {
							initsql.saveSaleRecords(sales.get(i));
							Thread.sleep(50);
						}
						Log.e("saleRecords", sales.size() + "");
					}
					///////更新销售记录明细saleandpdt//////////////////
					if(!numbers1[9].equals(numbers2[9])){
						new InitSql().clearnAllSaleanddpt();
						List<d_Saleandpdt> saleandpdt = new JsonResolveUtils(
								LoginActivity.this).getSaleandpdt();
						for (int i = 0; i < saleandpdt.size(); i++) {
							initsql.saveSaleandpdt(saleandpdt.get(i));
							Thread.sleep(50);
						}
						Log.e("saleandpdt", saleandpdt.size() + "");
					}
					// //////////////更新账单/////////////////
					if (!numbers1[6].equals(numbers2[6])) {
						new InitSql().clearnAllBill();
						List<d_Bill> bills = new JsonResolveUtils(
								LoginActivity.this).getBills();
						for (int i = 0; i < bills.size(); i++) {
							initsql.saveBill(bills.get(i));
							// Log.e("Bill","Subtotal" +
							// bills.get(i).getSubtotal()
							// + "Tips" +bills.get(i).getTip() + "Trans" +
							// bills.size());
							Thread.sleep(50);
						}
						Log.e("bills", bills.size() + "");
					}
					// ////////////////////////////////////////
					if (!numbers1[7].equals(numbers2[7])) {
						new InitSql().clearnAllCashier();
						List<d_Cashier> cashiers = new JsonResolveUtils(
								LoginActivity.this).getCashiers();
						for (int i = 0; i < cashiers.size(); i++) {
							initsql.saveCashier(cashiers.get(i));
						}
						Log.e("cashiers", cashiers.size() + "");
					}
					// ////////////////////////////////////////
					if (!numbers1[8].equals(numbers2[8])) {
						new InitSql().clearnAllContact();
						List<d_Contact> contacts = new JsonResolveUtils(
								LoginActivity.this).getContacts();
						for (int i = 0; i < contacts.size(); i++) {
							initsql.saveContact(contacts.get(i));
						}
						Log.e("contacts", contacts.size() + "");
					}
		
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("update failed ! Please Check the network connection!");
				}
			}
		});
	}

	private void initToast() {
		LayoutInflater inflater1 = getLayoutInflater();
		View view1 = inflater1.inflate(R.layout.readytoast,
				(ViewGroup) findViewById(R.id.toast_layout));
		tv_content = (TextView) view1.findViewById(R.id.txt_context);
		tv_title = (TextView) view1.findViewById(R.id.txt_title);
		mTasksView = (MyProgressView) view1.findViewById(R.id.tasks_view);
		builder = new AlertDialog.Builder(LoginActivity.this);
		builder.setView(view1);
		dialog = builder.create();
		new Thread(new ProgressRunable()).start();
		dialog.show();

	}

	class ProgressRunable implements Runnable {

		@Override
		public void run() {
			while (mCurrentProgress < mTotalProgress) {
				mCurrentProgress++;
				Message msg = new Message();
				msg.what = mCurrentProgress;
				myHandler.sendMessage(msg);
				mTasksView.setProgress(mCurrentProgress);
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dialog.dismiss();
		}

	}

	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				tv_title.setText("Are test network connection。。。");
				tv_content.setText(tv_content.getText().toString()
						+ "\t\nAre test network connection。。。");
				break;
			case 25:
				tv_title.setText("Is testing and host connection。。。");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nThe network connection is normal。\t\nIs testing and host connection。。。");
				break;
			case 50:
				tv_title.setText("Is the test order is sent。。。");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nHost connection is normal。\t\nIs the test order is sent。。。");
				break;
			case 75:
				tv_title.setText("Updating of data information。。。");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nOrder send normal。\t\nUpdating of data information。。。");
				break;
			case 97:
				tv_title.setText("The success of the test");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nData updated successfully。\t\nThe success of the test。");
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onBackPressed() {
		initBackDialog();
		mBackDialog.show();
	}

	private void initBackDialog() {
		mBackDialog = MyDialog.getDialog(LoginActivity.this, "Hint",
				"Are you sure you want to exit the program?", "OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);

	}

	@Override
	protected void initViews() {

	}

	@Override
	protected void initEvents() {
		((Button) this.findViewById(R.id.but_set)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginBtn)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum1)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum2)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum3)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum4)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum5)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum6)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum7)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum8)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum9)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginnum0)).setOnClickListener(this);
		((Button) this.findViewById(R.id.login_clear)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginTrans)).setOnClickListener(this);
	}
}