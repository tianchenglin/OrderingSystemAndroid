package com.utopia.Dialog;
import com.utopia.activity.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
public class pop_payment_method implements View.OnClickListener{
	private PopupWindow popupWindow;
	private Context context;
    private Button payment_btn;
	public pop_payment_method(Context context,Button payment_btn){
		super();
		this.context=context;
		this.payment_btn=payment_btn;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(context).inflate(
				R.layout.payment_method, null);
		this.popupWindow = new PopupWindow(localView, 320, 220);//设置窗体的大小
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
	    this.popupWindow.showAsDropDown(payment_btn,-100,0);//设置弹出窗体的位置（相对于payment_btn控件）
		localView.findViewById(R.id.method1).setOnClickListener(this);
		localView.findViewById(R.id.method2).setOnClickListener(this);
		localView.findViewById(R.id.method3).setOnClickListener(this);
		localView.findViewById(R.id.method4).setOnClickListener(this);
		localView.findViewById(R.id.method5).setOnClickListener(this);
		localView.findViewById(R.id.method6).setOnClickListener(this);
	}
	public void closePop() {
		if (popupWindow != null)
			popupWindow.dismiss();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.method1:
			payment_btn.setText("");
			payment_btn.setBackgroundResource(R.drawable.pay_method1);
			closePop();
			//此处转向刷卡机
			break;
		case R.id.method2:
			payment_btn.setText("");
			payment_btn.setBackgroundResource(R.drawable.pay_method2);
			closePop();
			//此处转向刷卡机
			break;
		case R.id.method3:
			payment_btn.setText("");
			payment_btn.setBackgroundResource(R.drawable.pay_method3);
			closePop();
			//此处转向刷卡机
			break;
		case R.id.method4:
			payment_btn.setText("");
			payment_btn.setBackgroundResource(R.drawable.pay_method4);
			closePop();
			//此处转向刷卡机
			break;
		case R.id.method5:
			payment_btn.setText("");
			payment_btn.setBackgroundResource(R.drawable.pay_method5);
			closePop();
			//此处转向刷卡机
			break;
		case R.id.method6://现金支付
			payment_btn.setText("");
			payment_btn.setBackgroundResource(R.drawable.pay_method6);
			closePop();
			new chooseDrawerDialog(context).show();
			break;
		}
		
		
	}

}
