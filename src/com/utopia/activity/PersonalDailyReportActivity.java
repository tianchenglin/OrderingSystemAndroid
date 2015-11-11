package com.utopia.activity;

import java.text.DecimalFormat;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Bill;
import com.utopia.Model.d_Bill;
import com.utopia.utils.Constant;
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
	DecimalFormat d = new DecimalFormat("0.00");
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_daily_report);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈 

		initViews();
		initEvents();
		initList();
	}
 
 

	private void initList() {
		
		List<d_Bill> bills = new sql_Bill().getTodayDetail();
		d_Bill t_bill;
		float net_sales_credit = 0;
		float net_sales_cash = 0;
		
		int transactions_credit = 0;
		int transactions_cash = 0;
		
		float tips_credit = 0;
		float tips_cash = 0;
		
		for(int i = 0 ; i < bills.size(); i++){
			t_bill = bills.get(i);
			if(t_bill.getTax()==1){//credit
				net_sales_credit+=t_bill.getTotal();
				transactions_credit += t_bill.getDistant();
				tips_credit += t_bill.getTip();
			}else{//cash
				net_sales_cash+=t_bill.getTotal();
				transactions_cash += t_bill.getDistant();
				tips_cash += t_bill.getTip();
			}
			Constant.clockInTime = bills.get(0).getCreateTime();
		}
		
		tv_cas1.setText(d.format(net_sales_cash)); 
		tv_cas2.setText(transactions_cash+""); 
		tv_cas3.setText(d.format(tips_cash));
		
		tv_cre1.setText(d.format(net_sales_credit));
		tv_cre2.setText(transactions_credit+"");
		tv_cre3.setText(d.format(tips_credit));
		
		int sum = Integer.parseInt(tv_cas2.getText().toString())	+ Integer.parseInt(tv_cre2.getText().toString());
		tv_tot1.setText(d.format(Float.valueOf(tv_cas1.getText().toString())	+ Float.valueOf(tv_cre1.getText().toString())));
		tv_tot2.setText(sum+"");
		tv_tot3.setText(d.format(Float.valueOf(tv_cre3.getText().toString())    + Float.valueOf(tv_cas3.getText().toString())));
		

	}



	@Override
	public void onClick(View view) { 
	}

	@Override
	protected void initViews() {
		// 现金交易
		tv_cas1 = (TextView) findViewById(R.id.tv_cas1);
		tv_cas2 = (TextView) findViewById(R.id.tv_cas2);
		tv_cas3 = (TextView) findViewById(R.id.tv_cas3);
		
		// 信用卡交易
		tv_cre1 = (TextView) findViewById(R.id.tv_cre1);
		tv_cre2 = (TextView) findViewById(R.id.tv_cre2);
		tv_cre3 = (TextView) findViewById(R.id.tv_cre3);
		
		
		// 现金交易与信用卡交易之和
		tv_tot1 = (TextView) findViewById(R.id.tv_tot1);
		tv_tot2 = (TextView) findViewById(R.id.tv_tot2);
		tv_tot3 = (TextView) findViewById(R.id.tv_tot3);
		
		
		// 设置当前时间
		date = (TextView) findViewById(R.id.tv_time);
		date.setText("From:"+Constant.clockInTime);

		// 设置操作人员
		user = (TextView) findViewById(R.id.tv_name);
		user.setText(Constant.currentStaff.getS_name());		
	}

	@Override
	protected void initEvents() { 
	}
}
