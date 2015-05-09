package com.utopia.Dialog;

import com.utopia.activity.R;
import com.utopia.activity.R.id;
import com.utopia.activity.R.layout;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class pop_setpwd2 implements View.OnClickListener {
	private Context context;
	private int i_seltxt = 0;
	private EditText password;
	private EditText password2;
	private EditText passwordold;
	PopupWindow popupWindow;
	private EditText userName;

	public pop_setpwd2(Context paramContext, View paramView) {
		this.context = paramContext;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_setpwd2, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		this.passwordold = ((EditText) localView
				.findViewById(R.id.login_oldpwd));
		this.password = ((EditText) localView.findViewById(R.id.login_pwd));
		this.password2 = ((EditText) localView.findViewById(R.id.login_pwd2));
		this.passwordold.setOnClickListener(this);
		this.password.setOnClickListener(this);
		this.password2.setOnClickListener(this);
		this.passwordold.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				pop_setpwd2.this.i_seltxt = 0;
				pop_setpwd2.this.passwordold.setInputType(0);
				pop_setpwd2.this.passwordold
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
				return false;
			}
		});
		this.password.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				pop_setpwd2.this.i_seltxt = 1;
				pop_setpwd2.this.password.setInputType(0);
				pop_setpwd2.this.password
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
				return false;
			}
		});
		this.password2.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				pop_setpwd2.this.i_seltxt = 2;
				pop_setpwd2.this.password2.setInputType(0);
				pop_setpwd2.this.password2
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

	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	public void onClick(View paramView) {
		if (paramView.getId() == R.id.login_oldpwd) {
			this.i_seltxt = 0;
			return;
		}
		if (paramView.getId() == R.id.login_pwd) {
			this.i_seltxt = 1;
			return;
		}
		if (paramView.getId() == R.id.login_pwd2) {
			this.i_seltxt = 2;
			return;
		}
		if (paramView.getId() == R.id.login_back) {
			switch (this.i_seltxt) {
			case 0:
				this.passwordold.dispatchKeyEvent(new KeyEvent(0L, 0L, 0, 67,
						0, 0, 0, 0, 6));
				return;
			case 1:
				this.password.dispatchKeyEvent(new KeyEvent(0L, 0L, 0, 67, 0,
						0, 0, 0, 6));
				return;
			case 2:
				this.password2.dispatchKeyEvent(new KeyEvent(0L, 0L, 0, 67, 0,
						0, 0, 0, 6));
				return;
			}
		}
		if (paramView.getId() == R.id.login_clear) {
			switch (this.i_seltxt) {
			case 0:
				this.passwordold.setText("");
				return;
			case 1:
				this.password.setText("");
				return;
			case 2:
				this.password2.setText("");
				return;
			}
		}
		if (paramView.getId() == R.id.loginBtn) {
			closePop();
			Toast.makeText(this.context, "old password check error !", 0)
					.show();
			return;
		}
		if (paramView.getId() == R.id.loginCancleBtn) {
			closePop();
			return;
		}
		switch (this.i_seltxt) {
		case 0:
			this.passwordold.append(paramView.getTag().toString());
			break;
		case 1:
			this.password.append(paramView.getTag().toString());
			break;
		case 2:
			this.password2.append(paramView.getTag().toString());
		}
	}
}