package com.utopia.activity;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Bill;
import com.utopia.Dao.sql_Cashier;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_Cashier;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;

public class EndActivity extends BaseActivity implements OnClickListener {

	private TextView tv_time;
	private TextView tv_name;
	private TextView tv_acc1;
	private String money = "";
	private TextView tv_exp1;
	private TextView tv_ope1;
	private TextView tv_dro1;
	private TextView tv_pay1;
	private TextView tv_pur1;
	private TextView tv_sho1;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.close_shift_2);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈 
		
		Bundle bundle = this.getIntent().getExtras();
		money = bundle.getString("currentMoney");

		initViews();
		initEvents();
	} 
	@Override
	protected void initViews() {
 
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time.setText("From:"+Constant.clockInTime);

		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name.setText(Constant.currentStaff.getS_account());

		//当前的金额
		tv_acc1 = (TextView) findViewById(R.id.tv_acc1);
		tv_acc1.setText(money);
		//期望的金额
		tv_exp1 = (TextView) findViewById(R.id.tv_exp1);
		tv_exp1.setText(expAmount());	
		tv_sho1 = (TextView) findViewById(R.id.tv_sho1);
		tv_sho1.setText(Constant.decimalFormat.format((Float.valueOf(tv_acc1.getText().toString()) - Float
				.valueOf(tv_exp1.getText().toString()))));
		
		tv_ope1 = (TextView) findViewById(R.id.tv_ope1);
		tv_ope1.setText(new sql_Cashier().getInitMoney());

		tv_dro1 = (TextView) findViewById(R.id.tv_dro1);
		tv_dro1.setText(new sql_Cashier().getDrops());

		tv_pay1 = (TextView) findViewById(R.id.tv_pay1);
		tv_pay1.setText(new sql_Cashier().getPayout());

		tv_pur1 = (TextView) findViewById(R.id.tv_pur1);
		tv_pur1.setText(new sql_Cashier().getPutchase());


	}

	/*
	 * 计算得到现在应该有多少钱
	 */
	private String expAmount() {
		float exp = 0;
		//初始金额 + total + tip + drops - payout - putchase 
		exp = Float.valueOf(new sql_Cashier().getInitMoney())
				+ Float.valueOf(new sql_Bill().sum_cash())
				+ Float.valueOf(new sql_Bill().getTip())
				+ Float.valueOf(new sql_Cashier().getDrops())
				- Float.valueOf(new sql_Cashier().getPayout())
				- Float.valueOf(new sql_Cashier().getPutchase());
		return exp + "";

	}

	@Override
	protected void initEvents() { 
		findViewById(R.id.endBtn).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) { 
		if (view.getId() == R.id.endBtn) {
			//System.exit(0);
			ExitApplication.getInstance().exit();
		}

	}
	
 
}
