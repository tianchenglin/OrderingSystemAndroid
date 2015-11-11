package com.utopia.Dialog;

import com.utopia.activity.PayBillActivity;
import com.utopia.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
 
/*
 * 输入现金
 */

public class popMoneyCharged implements View.OnClickListener{
	
	private Context context;
	PopupWindow popupWindow;
	
	private TextView money ;
	private String money_view = "0";
	private String money_charged ;
	private TextView distance_3 ; 
	public popMoneyCharged(Context context , View view) {
		
		super();
		this.context = context ; 
		
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(context).inflate(
				R.layout.pop_money_charged, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(view, 17, 0, 0);
		
		distance_3 = (TextView) view;
		money = (TextView) localView.findViewById(R.id.money);
		localView.findViewById(R.id.MoneyCharged_Ok).setOnClickListener(this);
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
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.MoneyCharged_Ok:
			money_charged = money.getText().toString();		
			distance_3.setText(money_charged);
			
			((PayBillActivity) context).Refresh();
			closePop();
			break;
		case R.id.btn_one:
			money_view = money.getText().toString() + "1";
			break;
		case R.id.btn_two:
			money_view = money.getText().toString() + "2";
			break;
		case R.id.btn_three:
			money_view = money.getText().toString() + "3";
			break;
		case R.id.btn_four:
			money_view = money.getText().toString() + "4";
			break;
		case R.id.btn_five:
			money_view = money.getText().toString() + "5";
			break;
		case R.id.btn_six:
			money_view = money.getText().toString() + "6";
			break;
		case R.id.btn_seven:
			money_view = money.getText().toString() + "7";
			break;
		case R.id.btn_eight:
			money_view = money.getText().toString() + "8";
			break;
		case R.id.btn_nine:
			money_view = money.getText().toString() + "9";
			break;
		case R.id.btn_zero:
			money_view = money.getText().toString() + "0";
			break;
		case R.id.btn_dot:
			if (!money.getText().toString().contains(".")) {
				if(money.getText().toString().length()==0){
					money.setText("0");
				}
				money.setText(money.getText().toString() + ".");
			}
			return;
		case R.id.btn_clear:
			if (money.getText().toString().length() > 0)
				money.setText(money
						.getText()
						.toString()
						.subSequence(0,
								money.getText().toString().length() - 1));
			return;
		}

		if (Float.parseFloat(money_view) >= 0.0) {
			money.setText(money_view);
		}
		
	}
	
}
