package com.utopia.activity;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Cashier;
import com.utopia.Model.d_Cashier;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;

public class CloseReportActivity extends BaseActivity implements OnClickListener{
	
	private TextView editText;
	private String curMoney = "0";
	
	private HomeKeyLocker mHomeKeyLocker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.close_shift_1);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(CloseReportActivity.this);
		
		initViews();
		initEvents();
	}
	
	
	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.goback:
			startActivity(new Intent(this, MainActivity.class));
			break;
		case R.id.et_OK:
			//存入收银机表 , 操作员id和操作时间 插入  ， 当前现金为初始现金 + 放入现金 ，状态为 drop 
			//c
			d_Cashier localCashier = new d_Cashier();

			if (curMoney.equals("")) {
				showCustomToast("Please Input Number");
				break;
			}

			localCashier.setCurrentMoney(Float.parseFloat(curMoney));
			localCashier.setCreateTime(DateUtils.getDateEN());
			localCashier.setUserCode(Constant.userCode);
			localCashier.setCashierId(Constant.cashierId);
			localCashier.setStatus("endMoney");

			// 插入收银机表数据
			new sql_Cashier(this).saveCurrentMoney(localCashier);

			Intent intent = new Intent();
			intent.putExtra("currentMoney", curMoney);
			intent.setClass(this, EndActivity.class);
			startActivity(intent);
			
			//进入最后一个界面
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
				if(editText.getText().toString().length()==0){
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
		editText = (TextView) findViewById(R.id.end_Money);
	}



	@Override
	protected void initEvents() {
		findViewById(R.id.et_OK).setOnClickListener(this);
		findViewById(R.id.goback).setOnClickListener(this);
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
