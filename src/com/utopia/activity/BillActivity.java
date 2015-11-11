package com.utopia.activity;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Bill;
import com.utopia.Model.d_Bill;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.JsonResolveUtils;

public class BillActivity extends BaseActivity implements OnClickListener {

	private TextView bill_total;
	private EditText bill_tips;
	private TextView bill_total_tips;
	private String total = "0.00";
	private Button ok;
	private Context mContext;
	d_Bill localBill = new d_Bill();
	private String md5;
	private String subTotal ; 
	private String tax ; 
	private String discount ; 
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill);
		if (!getIntent().getExtras().getString("total").equals("")) {

			total = getIntent().getExtras().getString("total");
		} else {
			total = "0.00";
		}

		md5 = getIntent().getExtras().getString("md5");
		subTotal = getIntent().getExtras().getString("subtotal");
		tax = getIntent().getExtras().getString("tax");
		discount = getIntent().getExtras().getString("discount");
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		bill_total = (TextView) findViewById(R.id.bill_total);
		bill_tips = (EditText) findViewById(R.id.bill_tips);
		bill_total_tips = (TextView) findViewById(R.id.bill_total_tips);
		ok = (Button) findViewById(R.id.bill_ok);

		bill_tips.addTextChangedListener(new EditChangedListener());
		bill_tips.setOnClickListener(this);

	}

	class EditChangedListener implements TextWatcher {
 

		@Override
		public void afterTextChanged(Editable arg0) {
			if (bill_tips.getText().toString().equals("")) {
				bill_total_tips.setText(decimalFormat.format((Float
						.parseFloat(bill_total.getText().toString()) + 0.00)));
			} else
				bill_total_tips.setText(decimalFormat.format((Float
						.parseFloat(bill_total.getText().toString()) + Float
						.parseFloat(bill_tips.getText().toString()))));
		}

		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

	};

	@Override
	protected void initEvents() {
		bill_total.setText(total);
		if (bill_tips.getText().toString().equals("")) {
			bill_tips.setText("0.00");
		}
		bill_total_tips.setText((Float.parseFloat(total) + Float
				.parseFloat(bill_tips.getText().toString())) + "");
		ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bill_ok:
			saveBill();
			break;
		case R.id.bill_tips:
			bill_tips.setText("");
		default:
			break;
		}
	}

	private void saveBill() {
		localBill.setBillId(md5);
		localBill.setWaiter(Constant.userCode);
		localBill.setSubtotal(Float.parseFloat(subTotal));
		localBill.setTax(Integer.parseInt(tax));
		localBill.setTotal(Float.parseFloat(total));
		localBill.setCreateTime(DateUtils.getDateEN());
		localBill.setDistant(Float.parseFloat(discount)); 
		
		if (bill_tips.getText().toString().equals("")) {
			bill_tips.setText("0.00");
		}
		localBill.setTip(Float.valueOf(bill_tips.getText().toString()));

		new sql_Bill().save(localBill);
		new RefreshAsyncTask().execute();
	}

	// 构造函数AsyncTask<Params, Progress, Result>参数说明:
	// Params 启动任务执行的输入参数
	// Progress 后台任务执行的进度
	// Result 后台计算结果的类型
	private class RefreshAsyncTask extends AsyncTask<Void, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog("Just a moment, please...");
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return new JsonResolveUtils(mContext).setBill(localBill);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dismissLoadingDialog();
			if (!result) {
				showCustomToast("send Bill failed !");
				Intent intent = new Intent(BillActivity.this,
						OrdersAcitvity.class);
				BillActivity.this.setResult(RESULT_CANCELED, intent);
				BillActivity.this.finish();
			} else {
				showCustomToast("send Bill successed !");
				Intent intent = new Intent(BillActivity.this,
						OrdersAcitvity.class);
				BillActivity.this.setResult(RESULT_OK, intent);
				BillActivity.this.finish();
			}
		}
	}
}
