package com.utopia.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dialog.pop_setpwd;
import com.utopia.Model.d_Area;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Staff;
import com.utopia.Model.d_Tax;
import com.utopia.Service.UpdateManager;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.InitSql;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyDialog;
import com.utopia.widget.MyProgressView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

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
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		// If the adapter is null, then Bluetooth is not supported
		if (!mBtAdapter.isEnabled()) {
			// 创建一个intent对象，该对象用于启动一个Activity，提示用户开户蓝牙设备
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(intent);
		}

		initEvents();
		// 后台读入
		Constant.DATABASE_PATH = getApplicationContext().getFilesDir()
				.getAbsolutePath();
		new Constant().copy(this);
		try {
			Thread.sleep(500L);
		} catch (InterruptedException localInterruptedException) {
			localInterruptedException.printStackTrace();
		}

		versionUpdate();

		// initsql = new InitSql();
		// update_All();
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
			login(); // 登陆 从后台得到数据
			// update_All();
			return;
		}
		if (paramView.getId() == R.id.but_set) {
			new pop_setpwd(LoginActivity.this, paramView); // 改成英文
			return;
		}
		if (paramView.getId() == R.id.but_test) {
			mCurrentProgress = 0;
			initToast();
			return;
		}

		if (paramView.getId() == R.id.loginTrans) {
			Intent i = new Intent(this, MainSpellActivity.class);
			startActivity(i);
			return;
		}

		if (time < 4) {
			this.findViewById(password[time]).setBackgroundResource(
					R.drawable.login_num2);
			switch (paramView.getId()) {
			case R.id.loginnum0:
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

	private List<d_Staff> staffs = new ArrayList<d_Staff>();
	private int color1[] = new int[] {
			R.drawable.desk_bg6, 
			R.drawable.desk_bg7,
			R.drawable.desk_bg8, 
			R.drawable.desk_bg3, 
			R.drawable.desk_bg4,
			R.drawable.desk_bg5 };
	
	private int color2[] = new int[] {
			R.drawable.badge_ifaux6, 
			R.drawable.badge_ifaux7,
			R.drawable.badge_ifaux8 };

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
						staffs.get(i).setColor(color1[i%6]);
						staffs.get(i).setColor2(color2[i%3]);
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
				try {
					new InitSql().clearnAllData();
					// ////////////////////////////////////////
					List<d_Product> menus = new JsonResolveUtils(
							LoginActivity.this).getMenus();
					// Log.e("Setting", menus.toString());

					for (int i = 0; i < menus.size(); i++) {
						initsql.saveMenu(menus.get(i));
						Thread.sleep(50);
					}

					// ////////////////////////////////////////

					List<d_Desk> desks = new JsonResolveUtils(
							LoginActivity.this).getDesks("");
					for (int i = 0; i < desks.size(); i++) {
						initsql.saveDesk(desks.get(i));
						Thread.sleep(50);
					}

					// /////////////////////////////////////////

					List<d_Area> areas = new JsonResolveUtils(
							LoginActivity.this).getAreas();
					for (int i = 0; i < areas.size(); i++) {
						initsql.saveArea(areas.get(i));
						Thread.sleep(50);
					}
					// /////////////////////////////////////////

					List<d_MenuType> menuTypes = new JsonResolveUtils(
							LoginActivity.this).getMenuTypes();
					for (int i = 0; i < menuTypes.size(); i++) {
						initsql.saveMenuType(menuTypes.get(i));
						Thread.sleep(50);
					}

					// 添加用户
					/*
					 * Thread.sleep(1000); List<d_Staff> staff = new
					 * JsonResolveUtils(SettingActivity.this).getStaff();
					 * for(int i = 0; i < staff.size(); i++){
					 * initsql.saveStaff(staff.get(i)); }
					 */

					List<d_Tax> taxs = new JsonResolveUtils(LoginActivity.this)
							.getTax();
					for (int i = 0; i < taxs.size(); i++) {
						initsql.saveTax(taxs.get(i));
					}

					List<d_SaleRecord> saleRecords = new JsonResolveUtils(
							LoginActivity.this).getSaleRecords();
					for (int i = 0; i < saleRecords.size(); i++) {
						initsql.saveSaleRecords(saleRecords.get(i));
					}
					return true;
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
					showCustomToast("update failed !");
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