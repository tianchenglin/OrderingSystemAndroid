package com.utopia.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.utopia.Base.BaseActivity;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;

@SuppressLint("ResourceAsColor")
public class ChoosePrinterCashierActivity extends BaseActivity implements
		OnClickListener {

	private Button btn_Cashier1;
	private Button btn_Cashier2;
	private Button btn_Cashier3;
	private Button btn_Cashier4;
	private HomeKeyLocker mHomeKeyLocker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_printer_cashier);
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(ChoosePrinterCashierActivity.this);
		
		ExitApplication.getInstance().addActivity(this);// �����ラ����烘��
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
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.next:
			if (btn_Cashier1.isEnabled() && btn_Cashier2.isEnabled()
					&& btn_Cashier3.isEnabled() && btn_Cashier4.isEnabled()) {
				showCustomToast("please choose a cashier...");

			} else {
				ChoosePrinterCashierActivity.this.startActivity(new Intent(
						ChoosePrinterCashierActivity.this,
						CashierActivity.class));
			}
			break;
		case R.id.btn_Cashier1:
			Constant.printerAddress = "98:D3:31:80:26:2F";
			btn_Cashier1.setEnabled(false);
			btn_Cashier2.setEnabled(true);
			btn_Cashier3.setEnabled(true);
			btn_Cashier4.setEnabled(true);
			break;

		case R.id.btn_Cashier2:
			Constant.printerAddress = "98:D3:31:70:31:1C";
			btn_Cashier2.setEnabled(false);
			btn_Cashier1.setEnabled(true);
			btn_Cashier3.setEnabled(true);
			btn_Cashier4.setEnabled(true);
			break;
		case R.id.btn_Cashier3:
			btn_Cashier3.setEnabled(false);
			btn_Cashier2.setEnabled(true);
			btn_Cashier1.setEnabled(true);
			btn_Cashier4.setEnabled(true);
			break;
		case R.id.btn_Cashier4:
			btn_Cashier4.setEnabled(false);
			btn_Cashier2.setEnabled(true);
			btn_Cashier3.setEnabled(true);
			btn_Cashier1.setEnabled(true);
			break;
		case R.id.goback:
			startActivity(new Intent(this, LoginActivity.class));
			break;
		}
	}

	@Override
	protected void initViews() {
		btn_Cashier1 = (Button) findViewById(R.id.btn_Cashier1);
		btn_Cashier2 = (Button) findViewById(R.id.btn_Cashier2);
		btn_Cashier3 = (Button) findViewById(R.id.btn_Cashier3);
		btn_Cashier4 = (Button) findViewById(R.id.btn_Cashier4);

	}

	@Override
	protected void initEvents() {
		btn_Cashier1.setOnClickListener(this);
		btn_Cashier2.setOnClickListener(this);
		btn_Cashier3.setOnClickListener(this);
		btn_Cashier4.setOnClickListener(this);

		findViewById(R.id.next).setOnClickListener(this);
	}
}
