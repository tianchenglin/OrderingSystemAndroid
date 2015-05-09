package com.utopia.Dialog;

import com.utopia.activity.R;
import com.utopia.activity.SettingActivity;
import com.utopia.activity.R.id;
import com.utopia.activity.R.layout;

import android.content.Context;
import android.content.Intent;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class pop_setpwd implements View.OnClickListener {
	private Context context;
	private EditText password;
	PopupWindow popupWindow;

	public pop_setpwd(Context paramContext, View paramView) {
		this.context = paramContext;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_setpwd, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		this.password = ((EditText) localView.findViewById(R.id.login_pwd));
		this.password.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				pop_setpwd.this.password.setInputType(0);
				pop_setpwd.this.password
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
		if (popupWindow != null)
			popupWindow.dismiss();
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
			if ("6666".equals(this.password.getText().toString().trim())) {
				this.context.startActivity(new Intent(this.context,
						SettingActivity.class));
				closePop();
				return;
			}
			
			Toast.makeText(this.context, "Wrong password !", Toast.LENGTH_SHORT).show();
			return;
		}
		if (paramView.getId() == R.id.loginCancleBtn) {
			closePop();
			return;
		}
		this.password.append(paramView.getTag().toString());
	}
}