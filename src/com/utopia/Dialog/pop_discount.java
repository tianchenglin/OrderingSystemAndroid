package com.utopia.Dialog;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Adapter.OrdersSalerecordAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.OrdersAcitvity;
import com.utopia.activity.R;
import com.utopia.widget.MyTextView;

/*
 * 输入税率
 * 
 */

public class pop_discount implements View.OnClickListener {
	private Context context;
	PopupWindow popupWindow;
	private TextView discount ;
	private String tax_rate_view = "0";
	private String tax_rate ;
	private TextView menu_discountEdit;
	private CheckBox tax_cb;
	private TextView discount_text ; 
	private String desk_name;
	private d_SaleRecord localSaleRecord ; 
	public pop_discount(Context context, View paramView,String desk_name) {
   
	   // this.localSaleRecord = localSaleRecord;
		this.context = context;
		this.desk_name=desk_name;
		if (this.popupWindow != null)	
			return;
		View localView = LayoutInflater.from(context).inflate(
				R.layout.pop_discount, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		//this.password = ((EditText) localView.findViewById(R.id.login_pwd));
		menu_discountEdit = (TextView) paramView;//.findViewById(R.id.menu_discount);		
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
			tax_rate = discount.getText().toString();	
			menu_discountEdit.setText(tax_rate);  
			
			if(menu_discountEdit.getText().toString().equals(""))
				menu_discountEdit.setText("1.00");
			if(Float.valueOf(menu_discountEdit.getText().toString().trim())<0 ||
					Float.valueOf(menu_discountEdit.getText().toString().trim())>1){
				    showCustomToast("sorry,discount must between 0 and 1.");
			}else{
			Log.i("tag",menu_discountEdit.getText().toString().trim()+"    nnnnnnnnnn");
//			
			//localSaleRecord.setDiscount(Float.valueOf(menu_discountEdit.getText().toString().trim()));
//			}
			//在数据库中更新折扣  
			//Log.i("tag",m_SaleRecord.getDiscount()+"");
		    new sql_SaleRecord().update_discountAll(menu_discountEdit.getText().toString().trim(),desk_name);
			//new sql_SaleRecord().update_discount(localSaleRecord);
			Log.i("tag","设置discount、、、、");
			
			((OrdersAcitvity)context).Refresh();
			closePop();
			}
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
				if(discount.getText().toString().length()==0){
					discount.setText("0");
				}
				discount.setText(discount.getText().toString() + ".");
			}
			return;
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
	public void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(context).inflate(
				R.layout.common_toast, null);
		((MyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

}
