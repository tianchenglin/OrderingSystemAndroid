package com.utopia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Bill;
import com.utopia.Dao.sql_Cashier;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Cashier;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;

public class ShiftReportActivity extends BaseActivity implements
		OnClickListener {

	private TextView tv_time; // 时间
	private TextView tv_cas1; // 该服务员从今天Clock in到目前为止的现金总额
	private TextView tv_cas2; // 该服务员从今天Clock in到目前为止的交易总数
	private TextView tv_tot1; // 现金和信用卡的总金额
	private TextView tv_cre1; // 信用卡金额
	private TextView tv_tot2;
	private TextView tv_cre2;
	private TextView tv_cas3; // 小费
	private TextView tv_tot3;
	private TextView tv_cre3;
	private TextView tv_pay1; // payout
	private TextView tv_pur1; // purchase
	private TextView tv_drops1; // drops
	private HomeKeyLocker mHomeKeyLocker;
	private List<d_Bill> bills;
	private List<d_Cashier> cashiers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shift_report_1);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(ShiftReportActivity.this);

		initViews();
		initEvents();
	}

	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}

	/*
	 * 屏蔽返回按键(non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.goback:
			startActivity(new Intent(this, MainActivity.class));
			break;
		}

	}

	@Override
	protected void initViews() {
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time.setText("From" + DateUtils.getDateWN());

		// 现金交易
		tv_cas1 = (TextView) findViewById(R.id.tv_cas1);
		tv_cas1.setText(new sql_Bill().sum_cash() + "");
		tv_cas2 = (TextView) findViewById(R.id.tv_cas2);
		tv_cas2.setText(new sql_SaleRecord().getTransactionsAll());
		tv_cas3 = (TextView) findViewById(R.id.tv_cas3);
		tv_cas3.setText(new sql_SaleRecord().getTipAll());

		// 信用卡交易
		tv_cre1 = (TextView) findViewById(R.id.tv_cre1);
		tv_cre1.setText("0");
		tv_cre2 = (TextView) findViewById(R.id.tv_cre2);
		tv_cre2.setText("0");
		tv_cre3 = (TextView) findViewById(R.id.tv_cre3);
		tv_cre3.setText("0");

		// 现金 和 信用卡的和
		tv_tot1 = (TextView) findViewById(R.id.tv_tot1);
		tv_tot1.setText(Constant.decimalFormat.format(Float.valueOf(tv_cas1
				.getText().toString())
				+ Float.valueOf(tv_cre1.getText().toString())));
		tv_tot2 = (TextView) findViewById(R.id.tv_tot2);
		tv_tot2.setText(Constant.decimalFormat.format(Float.valueOf(tv_cas2
				.getText().toString())
				+ Float.valueOf(tv_cre2.getText().toString())));
		tv_tot3 = (TextView) findViewById(R.id.tv_tot3);
		tv_tot3.setText(Constant.decimalFormat.format((Float.valueOf(tv_cre3
				.getText().toString()) + (Float.valueOf(tv_cas3.getText()
				.toString())))));

		tv_drops1 = (TextView) findViewById(R.id.tv_drops1);
		tv_drops1.setText(new sql_Cashier().getDrops());
		tv_pay1 = (TextView) findViewById(R.id.tv_pay1);
		tv_pay1.setText(new sql_Cashier().getPayout());
		tv_pur1 = (TextView) findViewById(R.id.tv_pur1);
		tv_pur1.setText(new sql_Cashier().getPutchase());
	}

	@Override
	protected void initEvents() {
		// findViewById(R.id.goback).setOnClickListener(this);
		new RefreshAsyncTask().execute();
	}

	private class RefreshAsyncTask extends AsyncTask<String, Integer, String> {
		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// doInBackground()方法用于在执行异步任务,不可以更改主线程中UI
		@Override
		protected String doInBackground(String... params) {
			System.out.println("调用doInBackground()方法--->开始执行异步任务");
			bills = new JsonResolveUtils(ShiftReportActivity.this).getBills();
			new sql_Bill().delete();
			if (bills.size() > 0) {
				for (int i = 0; i < bills.size(); i++) {
					new sql_Bill().saveOnline(bills.get(i));
				}
				new sql_Bill().select();
			}
			
			cashiers = new JsonResolveUtils(ShiftReportActivity.this).getCashiers();
			new sql_Cashier().delete();
			if(cashiers.size()>0){
				for(int i = 0 ; i < cashiers.size();i++){
					new sql_Cashier().saveOnline(cashiers.get(i));
				}
				
				new sql_Cashier().select();
			}
			return null;
		}

		// onPostExecute()方法用于异步任务执行完成后,在主线程中执行的操作
		@Override
		protected void onPostExecute(String result) {
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
}
