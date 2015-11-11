package com.utopia.Dialog;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utopia.activity.OrdersAcitvity;
import com.utopia.activity.R;

public class pop_Input implements View.OnClickListener {
	PopupWindow popupWindow;
	private TextView discount;
	private String tax_rate_view = "0";
	private View add;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private double subtotal;
	private TextView tips, totalAndTips;
	private int tag = 1;

	private EditText tipAmount, PaidAmount;
	private TextView tv_money;
	private Context mContext;
	public pop_Input(Context context, EditText tipAmount, EditText PaidAmount,
			TextView tv_money, int tag) {
		mContext = context;
		this.tag = tag;
		this.tipAmount = tipAmount;
		this.PaidAmount = PaidAmount;
		this.tv_money = tv_money;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(context).inflate(
				R.layout.pop_discount, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(tipAmount, 16, 0, 0);

		discount = (TextView) localView.findViewById(R.id.discount);
		localView.findViewById(R.id.discount_Ok).setOnClickListener(this);
		localView.findViewById(R.id.btn_one).setOnClickListener(this);
		localView.findViewById(R.id.btn_two).setOnClickListener(this);
		localView.findViewById(R.id.btn_three).setOnClickListener(this);
		localView.findViewById(R.id.btn_four).setOnClickListener(this);
		localView.findViewById(R.id.btn_five).setOnClickListener(this);
		localView.findViewById(R.id.btn_six).setOnClickListener(this);
		localView.findViewById(R.id.btn_seven).setOnClickListener(this);
		localView.findViewById(R.id.btn_eight).setOnClickListener(this);
		localView.findViewById(R.id.btn_nine).setOnClickListener(this);
		localView.findViewById(R.id.btn_zero).setOnClickListener(this);
		localView.findViewById(R.id.btn_dot).setOnClickListener(this);
		localView.findViewById(R.id.btn_clear).setOnClickListener(this);
	}

	public void closePop() {
		if (popupWindow != null)
			popupWindow.dismiss();
	}

	@SuppressLint("NewApi")
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.discount_Ok:
			if(discount.getText().equals(null)||discount.getText().toString().trim().equals("")){
				discount.setText("0");
			}
			if (tag == 1) {
				tipAmount.setText(discount.getText().toString());
				Double tip = Double.parseDouble(tipAmount.getText().toString());
				Double money = Double
						.parseDouble(tv_money.getText().toString());
				PaidAmount.setText(decimalFormat.format(money + tip));
			} else if (tag == 2) {
				PaidAmount.setText(discount.getText().toString());
				Double paid = Double.parseDouble(PaidAmount.getText()
						.toString());
				Double money = Double
						.parseDouble(tv_money.getText().toString());
				tipAmount.setText(decimalFormat.format(paid - money));
			}
			((OrdersAcitvity)mContext).RefreshAllBill();
			closePop();

			break;
		case R.id.btn_one:
			tax_rate_view = discount.getText().toString() + "1";
			break;
		case R.id.btn_two:
			tax_rate_view = discount.getText().toString() + "2";
			break;
		case R.id.btn_three:
			tax_rate_view = discount.getText().toString() + "3";
			break;
		case R.id.btn_four:
			tax_rate_view = discount.getText().toString() + "4";
			break;
		case R.id.btn_five:
			tax_rate_view = discount.getText().toString() + "5";
			break;
		case R.id.btn_six:
			tax_rate_view = discount.getText().toString() + "6";
			break;
		case R.id.btn_seven:
			tax_rate_view = discount.getText().toString() + "7";
			break;
		case R.id.btn_eight:
			tax_rate_view = discount.getText().toString() + "8";
			break;
		case R.id.btn_nine:
			tax_rate_view = discount.getText().toString() + "9";
			break;
		case R.id.btn_zero:
			tax_rate_view = discount.getText().toString() + "0";
			break;
		case R.id.btn_dot:
			if (!discount.getText().toString().contains(".")) {
				if (discount.getText().toString().length() == 0) {
					discount.setText("0");
				}
				discount.setText(discount.getText().toString() + ".");
			}
			return;//
		case R.id.btn_clear:
			if (discount.getText().toString().length() > 0)
				discount.setText(discount
						.getText()
						.toString()
						.subSequence(0,
								discount.getText().toString().length() - 1));
			return;
		}

		if (Float.parseFloat(tax_rate_view) >= 0.0) {
			discount.setText(tax_rate_view);
		}

	}

}
