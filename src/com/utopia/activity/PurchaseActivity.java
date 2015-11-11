package com.utopia.activity;

import java.util.Date;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Cashier;
import com.utopia.Model.d_Cashier;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;

public class PurchaseActivity extends BaseActivity implements OnClickListener {

	private TextView editText;
	private String curMoney = "0";
	private String purchaseMoney; 

	d_Cashier localCashier = new d_Cashier();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchase);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈 
		
		initViews();
		initEvents();
	}  
 

	@Override
	public void onClick(View view) {
		switch (view.getId()) { 
		case R.id.et_OK:
			// 存入收银机表 , 操作员id和操作时间 插入 ， 当前现金为初始现金 + 放入现金 ，状态为 purchase
			purchaseMoney = editText.getText().toString();

			if (purchaseMoney.equals("")) {
				showCustomToast("Please input number…");
				break;
			}
			insert();
			new RefreshAsyncTask().execute();			
			

			// 插入收银机表数据
			new sql_Cashier(this).saveChangeMoney(localCashier);
			this.finish(); 
			break;
		case R.id.btn_one:
			curMoney = editText.getText().toString() + "1";
			break;
		case R.id.btn_two:
			curMoney = editText.getText().toString() + "2";
			break;
		case R.id.btn_three:
			curMoney = editText.getText().toString() + "3";
			break;
		case R.id.btn_four:
			curMoney = editText.getText().toString() + "4";
			break;
		case R.id.btn_five:
			curMoney = editText.getText().toString() + "5";
			break;
		case R.id.btn_six:
			curMoney = editText.getText().toString() + "6";
			break;
		case R.id.btn_seven:
			curMoney = editText.getText().toString() + "7";
			break;
		case R.id.btn_eight:
			curMoney = editText.getText().toString() + "8";
			break;
		case R.id.btn_nine:
			curMoney = editText.getText().toString() + "9";
			break;
		case R.id.btn_zero:
			curMoney = editText.getText().toString() + "0";
			break;
		case R.id.btn_dot:
			if (!editText.getText().toString().contains(".")) {
				if (editText.getText().toString().length() == 0) {
					editText.setText("0");
				}
				editText.setText(editText.getText().toString() + ".");
			}
			return;
		case R.id.btn_clear:
			if (editText.getText().toString().length() > 0)
				editText.setText(editText
						.getText()
						.toString()
						.subSequence(0,
								editText.getText().toString().length() - 1));
			return;
		}

		if (Float.parseFloat(curMoney) >= 0.0) {
			editText.setText(curMoney);
		}

	}

	@Override
	protected void initViews() {
		editText = (TextView) findViewById(R.id.purchase_Money);
	}
	private void insert() {
		localCashier.setChangeMoney(Float.parseFloat(purchaseMoney));
		localCashier.setCreateTime(DateUtils.getDateEN());
		localCashier.setUserCode(Constant.userCode);
		localCashier.setCashierId(Constant.cashierId);
		localCashier.setStatus("purchase");

		// 插入收银机表数据
		new sql_Cashier(this).saveChangeMoney(localCashier);

	}
	private class RefreshAsyncTask extends AsyncTask<Void, Void, Boolean> {
		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// doInBackground()方法用于在执行异步任务,不可以更改主线程中UI
		@Override
		protected Boolean doInBackground(Void... params) {
			System.out.println("调用doInBackground()方法--->开始执行异步任务");

			return new JsonResolveUtils(PurchaseActivity.this)
					.setCashier(localCashier);
		}

		// onPostExecute()方法用于异步任务执行完成后,在主线程中执行的操作
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			System.out.println("调用onPostExecute()方法--->异步任务执行完毕");

		}

		// onCancelled()方法用于异步任务被取消时,在主线程中执行相关的操作
		@Override
		protected void onCancelled() {
			super.onCancelled();
			System.out.println("调用onCancelled()方法--->异步任务被取消");
		}
	}
	
	@Override
	protected void initEvents() {
		findViewById(R.id.btn_one).setOnClickListener(this);
		findViewById(R.id.btn_two).setOnClickListener(this);
		findViewById(R.id.btn_three).setOnClickListener(this);
		findViewById(R.id.btn_four).setOnClickListener(this);
		findViewById(R.id.btn_five).setOnClickListener(this);
		findViewById(R.id.btn_six).setOnClickListener(this);
		findViewById(R.id.btn_seven).setOnClickListener(this);
		findViewById(R.id.btn_eight).setOnClickListener(this);
		findViewById(R.id.btn_nine).setOnClickListener(this);
		findViewById(R.id.btn_zero).setOnClickListener(this);
		findViewById(R.id.btn_dot).setOnClickListener(this);
		findViewById(R.id.btn_clear).setOnClickListener(this);
		findViewById(R.id.et_OK).setOnClickListener(this);
 
	}
}
