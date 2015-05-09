package com.utopia.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message; 
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Setting;
import com.utopia.Dialog.pop_adduser;
import com.utopia.Dialog.pop_setpwd2;
import com.utopia.Model.d_Area;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Setting;
import com.utopia.Model.d_Tax;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.InitSql;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.utils.NetWorkState;
import com.utopia.widget.MyDialog;

public class SettingActivity extends BaseActivity implements
		View.OnClickListener {
	private ProgressDialog dialog;
	private InitSql initsql;

	private TextView m_ip1;
	private TextView m_ip2;
	private TextView m_ip3;
	private TextView m_ip4;
	private EditText m_padname;
	private MyDialog mBackDialog;
	private MyDialog mClearDateDialog;

	private RadioGroup mRadioGroup;
	private RadioGroup mRadioGroup1;

	private Boolean mGraphicalOrder = false;
	private Boolean mListOrder = true;
	private Boolean mCodeOrder = false;
	private Boolean mFastOrder = false;
	private Boolean mCustom = false;
	private Boolean mWaiter = false;

	private RadioButton mRadioButton1;
	private RadioButton mRadioButton2;
	private RadioButton mRadioButton3;
	private RadioButton mRadioButton4;
	private RadioButton mRadioButton5;
	private RadioButton mRadioButton6;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.msetting);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		
		((TextView) findViewById(R.id.mlocalip)).setText(new NetWorkState(this)
				.getLocalIPAddress());
		initsql = new InitSql();
		initViews();
		initEvents();
		init();
	}

	@SuppressLint("HandlerLeak")
	private Handler mhandler = new Handler() {
		@Override
		public void handleMessage(Message paramAnonymousMessage) {
			switch (paramAnonymousMessage.what) {
			case 0:
				SettingActivity.this.dialog.dismiss();
				SettingActivity.this.finish();
				return;
			case 1:
				SettingActivity.this.dialog.dismiss();
				SettingActivity.this.finish();
				return;
			}
		}
	};

	public void init() {
		d_Setting mySetting = new sql_Setting().getDate();

		String[] ip = mySetting.getServerip().split("\\.");
		this.m_ip1.setText(ip[0]);
		this.m_ip2.setText(ip[1]);
		this.m_ip3.setText(ip[2]);
		this.m_ip4.setText(ip[3]);

		this.m_padname.setText(mySetting.getSerialNumber());
		this.mRadioButton1.setChecked(mySetting.getGraphicalOrder());
		this.mRadioButton2.setChecked(mySetting.getListOrder());
		this.mRadioButton3.setChecked(mySetting.getCodeOrder());
		this.mRadioButton4.setChecked(mySetting.getFastOrder());
		this.mRadioButton5.setChecked(mySetting.getCustom());
		this.mRadioButton6.setChecked(mySetting.getWaiter());

	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.goback:
			initBackDialog();
			mBackDialog.show();
			break;
		case R.id.set_save:
			d_Setting mSetting = new d_Setting();
			mSetting.setServerip(getServiceIP());
			mSetting.setSerialNumber(m_padname.getText().toString());
			mSetting.setGraphicalOrder(mGraphicalOrder);
			mSetting.setListOrder(mListOrder);
			mSetting.setCodeOrder(mCodeOrder);
			mSetting.setFastOrder(mFastOrder);
			mSetting.setCustom(mCustom);
			mSetting.setWaiter(mWaiter);

			if(mSetting.equals("")){
				
			}else{
				new sql_Setting().save(mSetting);
			}
			

			SettingActivity.this.finish();
			break;
		case R.id.m_serverupdata:
			update_All();
			break;
		case R.id.m_servertest:
			svrtest();
			break;
		case R.id.m_dateclear:
			initCleardateDialog();
			mClearDateDialog.show();
			break;
		case R.id.m_setpwd:
			new pop_setpwd2(SettingActivity.this, paramView);
			break;
		case R.id.m_adduser:
			new pop_adduser(SettingActivity.this, paramView);
			break;
		case R.id.m_serversave:
			new sql_Setting().updateIP(getServiceIP());
			break;
		}

	}



	/**
	 * 与服务器连接测试
	 */
	private void svrtest() {
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
					Thread.sleep(3000);
					// staff = JsonResolveUtils.GetUserInfo(LoginActivity.this);
					// Log.e("user", user.toString());
					// 从服务器得到用户名 密码 表
					return new JsonResolveUtils(SettingActivity.this)
							.CheckService(getApplicationContext());
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
					showCustomToast("The address of the server error, please check the terminal is connected with the server network...");
				} else {
					showCustomToast("The success of the test!");
				}
			}
		});

	}

	private  void update_All() {
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
							SettingActivity.this).getMenus();
					//Log.e("Setting", menus.toString());
					
					for (int i = 0; i < menus.size(); i++){
						initsql.saveMenu(menus.get(i));
						Thread.sleep(50);
					}

					// ////////////////////////////////////////
					
					List<d_Desk> desks = new JsonResolveUtils(
							SettingActivity.this).getDesks("");
					for (int i = 0; i < desks.size(); i++){
						initsql.saveDesk(desks.get(i));
						Thread.sleep(50);
					}

					// /////////////////////////////////////////
					
					List<d_Area> areas = new JsonResolveUtils(
							SettingActivity.this).getAreas();
					for (int i = 0; i < areas.size(); i++){
						initsql.saveArea(areas.get(i));
						Thread.sleep(50);
					}
					// /////////////////////////////////////////

					
					List<d_MenuType> menuTypes = new JsonResolveUtils(
							SettingActivity.this).getMenuTypes();
					for (int i = 0; i < menuTypes.size(); i++){
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
					
					
					List<d_Tax> taxs = new JsonResolveUtils(
							SettingActivity.this).getTax();
					for (int i = 0; i < taxs.size(); i++){
						initsql.saveTax(taxs.get(i));
					}	
					
					List<d_SaleRecord> saleRecords = new JsonResolveUtils(SettingActivity.this).getSaleRecords();
					for(int i = 0 ; i < saleRecords.size() ; i++){
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

	/*
	 * 得到ServerIP 和 Serial number
	 */
	private String getServiceIP() {
		String str1 = this.m_ip1.getText().toString();
		String str2 = this.m_ip2.getText().toString();
		String str3 = this.m_ip3.getText().toString();
		String str4 = this.m_ip4.getText().toString();
		return str1 + "." + str2 + "." + str3 + "." + str4;
	}



	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onBackPressed() {
	}

	private void initCleardateDialog(){
		mClearDateDialog = MyDialog.getDialog(SettingActivity.this, "Hint", "clear data ?","OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				new  InitSql().clearnAllData();
				showCustomToast("clear-all");
				dialog.dismiss();
			}
		},"Cancel" , new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		mClearDateDialog.setButton1Background(R.drawable.btn_default_popsubmit);
	}
	
	// 自定义对话框
	private void initBackDialog() {
		mBackDialog = MyDialog.getDialog(SettingActivity.this, "Hint",
				"You haven't saved information, whether to continue to exit?",
				"OK", new DialogInterface.OnClickListener() {

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
		mRadioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		mRadioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		mRadioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		mRadioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		mRadioButton5 = (RadioButton) findViewById(R.id.radioButton5);
		mRadioButton6 = (RadioButton) findViewById(R.id.radioButton6);
		this.m_padname = ((EditText) findViewById(R.id.m_padname));
		this.m_ip1 = ((TextView) findViewById(R.id.m_iptext1));
		this.m_ip2 = ((TextView) findViewById(R.id.m_iptext2));
		this.m_ip3 = ((TextView) findViewById(R.id.m_iptext3));
		this.m_ip4 = ((TextView) findViewById(R.id.m_iptext4));

		mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup1);
		mRadioGroup1 = (RadioGroup) findViewById(R.id.radiogroup2);
	}

	@Override
	protected void initEvents() {
		// 左上角返回
		((Button) findViewById(R.id.goback)).setOnClickListener(this);

		// Save Ip
		((Button) findViewById(R.id.m_serversave)).setOnClickListener(this);

		((Button) findViewById(R.id.m_servertest)).setOnClickListener(this);
		((Button) findViewById(R.id.m_dateclear)).setOnClickListener(this);
		((Button) findViewById(R.id.m_serverupdata)).setOnClickListener(this);
		((Button) findViewById(R.id.m_setpwd)).setOnClickListener(this);
		((Button) findViewById(R.id.m_adduser)).setOnClickListener(this);
		// Save
		((Button) findViewById(R.id.set_save)).setOnClickListener(this);

		mRadioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						if (checkedId == mRadioButton1.getId()) {
							mGraphicalOrder = true;
							mListOrder = false;
							mCodeOrder = false;
							mFastOrder = false;
						}
						if (checkedId == mRadioButton2.getId()) {
							mGraphicalOrder = false;
							mListOrder = true;
							mCodeOrder = false;
							mFastOrder = false;
						}
						if (checkedId == mRadioButton3.getId()) {
							mGraphicalOrder = false;
							mListOrder = false;
							mCodeOrder = true;
							mFastOrder = false;
						}
						if (checkedId == mRadioButton4.getId()) {
							mGraphicalOrder = false;
							mListOrder = false;
							mCodeOrder = false;
							mFastOrder = true;
						}

					}
				});

		mRadioGroup1
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						if (checkedId == mRadioButton5.getId()) {
							mCustom = true;
							mWaiter = false;
						}
						if (checkedId == mRadioButton6.getId()) {
							mCustom = false;
							mWaiter = true;
						}
					}
				});

	}

}