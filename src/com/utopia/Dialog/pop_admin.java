package com.utopia.Dialog;

import com.utopia.activity.R;
import com.utopia.activity.StatisticActivity;
import com.utopia.activity.R.id;
import com.utopia.activity.R.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

public class pop_admin implements View.OnClickListener {
	private Context context;
	private EditText password;
	PopupWindow popupWindow;
	private EditText userName;

	@SuppressLint("NewApi")
	public pop_admin(Context paramContext, View paramView) {
		this.context = paramContext;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_login, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
		this.popupWindow.setFocusable(true);
		this.popupWindow.showAsDropDown(paramView, 0, 0);
		this.popupWindow.update();
		this.password = ((EditText) localView.findViewById(R.id.login_pwd));
		this.password.setOnTouchListener(new View.OnTouchListener() {
			@SuppressLint("NewApi")
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				pop_admin.this.password.setInputType(0);
				pop_admin.this.password
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
				return false;
			}
		});
		((Button) localView.findViewById(R.id.loginnum1))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum2))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum3))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum4))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum5))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum6))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum7))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum8))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum9))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginnum0))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.login_back))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.login_clear))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginBtn))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginCancleBtn))
				.setOnClickListener(this);
	}

	private void closeKeyboard() {
		InputMethodManager m = ((InputMethodManager) this.context
				.getSystemService("input_method"));
	}

	@SuppressLint("NewApi")
	private void openKeyboard() {
		((InputMethodManager) this.context.getSystemService("input_method"))
				.toggleSoftInput(0, 2);
	}

	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	public void onClick(View paramView) {
		if (paramView.getId() == R.id.login_back) {
			this.password.dispatchKeyEvent(new KeyEvent(0L, 0L, 0, 67, 0, 0, 0,
					0, 6));
			return;
		}
		if (paramView.getId() == R.id.login_clear) {
			this.password.setText("");
			return;
		}
		if (paramView.getId() == R.id.loginBtn) {
			closePop();
			com.utopia.utils.Constant.managerId = "admin";
			Intent localIntent = new Intent(this.context, StatisticActivity.class);
			this.context.startActivity(localIntent);
			((Activity) this.context).finish();
			return;
		}
		if (paramView.getId() == R.id.loginCancleBtn) {
			closePop();
			return;
		}
		this.password.append(paramView.getTag().toString());
	}
}