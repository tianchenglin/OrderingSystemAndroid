package com.utopia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Bill;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_Staff;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;

public class PersonalDailyReportActivity extends BaseActivity implements
		OnClickListener {

	private TextView date;
	private TextView user; // 操作员
	private TextView tv_cas1; // 该服务员从今天Clock in到目前为止的现金总额
	private TextView tv_cas2; // 该服务员从今天Clock in到目前为止的交易总数
	private TextView tv_tot1; // 现金和信用卡的总金额
	private TextView tv_cre1; // 信用卡金额
	private TextView tv_tot2;
	private TextView tv_cre2;
	private TextView tv_cas3; // 小费
	private TextView tv_tot3;
	private TextView tv_cre3;
	private HomeKeyLocker mHomeKeyLocker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_daily_report);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(PersonalDailyReportActivity.this);

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
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.goback) {
			startActivity(new Intent(this, DeskMenuActivity.class));
		}
	}

	@Override
	protected void initViews() {
		// 现金交易
		tv_cas1 = (TextView) findViewById(R.id.tv_cas1);
		tv_cas1.setText(new sql_Bill().sum_cash() + "");
		tv_cas2 = (TextView) findViewById(R.id.tv_cas2);
		tv_cas2.setText(new sql_Bill().getTransactions());
		tv_cas3 = (TextView) findViewById(R.id.tv_cas3);
		tv_cas3.setText(new sql_Bill().getTip());

		// 信用卡交易
		tv_cre1 = (TextView) findViewById(R.id.tv_cre1);
		tv_cre1.setText("0");
		tv_cre2 = (TextView) findViewById(R.id.tv_cre2);
		tv_cre2.setText("0");
		tv_cre3 = (TextView) findViewById(R.id.tv_cre3);
		tv_cre3.setText("0");
		
		// 现金交易与信用卡交易之和
		tv_tot1 = (TextView) findViewById(R.id.tv_tot1);
		tv_tot1.setText(Float.valueOf(tv_cas1.getText().toString())
				+ Float.valueOf(tv_cre1.getText().toString()) + "");
		tv_tot2 = (TextView) findViewById(R.id.tv_tot2);
		tv_tot2.setText(Float.valueOf(tv_cas2.getText().toString())
				+ Float.valueOf(tv_cre2.getText().toString()) + "");
		tv_tot3 = (TextView) findViewById(R.id.tv_tot3);
		tv_tot3.setText(""
				+ (Float.valueOf(tv_cre3.getText().toString()) + Float
						.valueOf(tv_cas3.getText().toString())));
		
		// 设置当前时间
		date = (TextView) findViewById(R.id.tv_time);
		date.setText(DateUtils.getDateEN());

		// 设置操作人员
		user = (TextView) findViewById(R.id.tv_name);
		user.setText(Constant.currentStaff.getS_account());		
	}

	@Override
	protected void initEvents() {
		// 返回
		findViewById(R.id.goback).setOnClickListener(this);
	}
}
