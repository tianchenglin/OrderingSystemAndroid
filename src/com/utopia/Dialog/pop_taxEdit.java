package com.utopia.Dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utopia.activity.R;

/*
 * 输入税率
 * 
 */

public class pop_taxEdit implements View.OnClickListener {
	private Context context;
	PopupWindow popupWindow;
	private TextView tax ;
	private String tax_rate_view = "0";
	private String tax_rate ;
	private TextView taxtEdit;
	public pop_taxEdit(Context context, View paramView) {
		this.context = context;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(context).inflate(
				R.layout.pop_tax, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		//this.password = ((EditText) localView.findViewById(R.id.login_pwd));
		
		taxtEdit = (TextView) paramView.findViewById(R.id.taxEdit);		//父页面税率
		
		
		tax = (TextView) localView.findViewById(R.id.tax);
		localView.findViewById(R.id.tax_Ok).setOnClickListener(this);
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
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tax_Ok:
			tax_rate = tax.getText().toString();		//得到税率
			if(tax_rate.equals("")){
				tax_rate = "0.0";
			}
			taxtEdit.setText(tax_rate);   				//更新父页面税率
			closePop();
			break;
		case R.id.btn_one:
			tax_rate_view = tax.getText().toString() + "1";
			break;
		case R.id.btn_two:
			tax_rate_view = tax.getText().toString() + "2";
			break;
		case R.id.btn_three:
			tax_rate_view = tax.getText().toString() + "3";
			break;
		case R.id.btn_four:
			tax_rate_view = tax.getText().toString() + "4";
			break;
		case R.id.btn_five:
			tax_rate_view = tax.getText().toString() + "5";
			break;
		case R.id.btn_six:
			tax_rate_view = tax.getText().toString() + "6";
			break;
		case R.id.btn_seven:
			tax_rate_view = tax.getText().toString() + "7";
			break;
		case R.id.btn_eight:
			tax_rate_view = tax.getText().toString() + "8";
			break;
		case R.id.btn_nine:
			tax_rate_view = tax.getText().toString() + "9";
			break;
		case R.id.btn_zero:
			tax_rate_view = tax.getText().toString() + "0";
			break;
		case R.id.btn_dot:
			if (!tax.getText().toString().contains(".")) {
				if(tax.getText().toString().length()==0){
					tax.setText("0");
				}
				tax.setText(tax.getText().toString() + ".");
			}
			return;
		case R.id.btn_clear:
			if (tax.getText().toString().length() > 0)
				tax.setText(tax
						.getText()
						.toString()
						.subSequence(0,
								tax.getText().toString().length() - 1));
			return;
		}

		if (Float.parseFloat(tax_rate_view) >= 0.0) {
			tax.setText(tax_rate_view);
		}
		
	}

}
