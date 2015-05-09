package com.utopia.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.activity.R;
import com.utopia.activity.OrderFormActivity;
import com.utopia.activity.R.id;
import com.utopia.activity.R.layout;
import com.utopia.utils.Constant;
import com.utopia.utils.InitSql;

public class pop_send implements View.OnClickListener {
	DialogInterface.OnClickListener canclebut_clickListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface paramAnonymousDialogInterface,
				int paramAnonymousInt) {
		}
	};
	private String card_id;
	private Context context;
	private ProgressDialog dialog;
	private Dialog dlg;
	private String i_num;
	private int i_seltxt = 0;
	private String key_name;
	private Handler mhandler;
	DialogInterface.OnClickListener okbut_clickListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface paramAnonymousDialogInterface,
				int paramAnonymousInt) {
		}
	};
	private EditText password;
	private EditText tvnum;
	private TextView tvtableid;

	@SuppressLint("NewApi")
	public pop_send(Context paramContext) {
		this.context = paramContext;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_send, null);
		this.password = ((EditText) localView.findViewById(R.id.send_pwd));
		this.tvnum = ((EditText) localView.findViewById(R.id.send_num));
		this.tvtableid = ((TextView) localView.findViewById(R.id.send_tableid));
		this.password.setText("");
		this.tvnum.setText("1");
		this.tvtableid.setText("");
		this.password.setOnClickListener(this);
		this.tvnum.setOnClickListener(this);
		this.tvtableid.setOnClickListener(this);
		this.password.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				pop_send.this.i_seltxt = 0;
				pop_send.this.password.setInputType(0);
				pop_send.this.password
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
				return false;
			}
		});
		this.tvnum.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				pop_send.this.i_seltxt = 1;
				pop_send.this.tvnum.setInputType(0);
				pop_send.this.tvnum
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
				return false;
			}
		});
		((Button) localView.findViewById(R.id.sendreadBtn3))
				.setOnClickListener(this);
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
		sethandler();
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
		localBuilder.setCancelable(false);
		localBuilder.setView(localView);
		this.dlg = localBuilder.show();
	}

	private void sethandler() {
		this.mhandler = new Handler() {
			public void handleMessage(Message paramAnonymousMessage) {
				switch (paramAnonymousMessage.what) {
				default:
					return;
				case 1:
				}
				String str = (String) paramAnonymousMessage.obj;
				Toast.makeText(pop_send.this.context, str, 1000).show();
				pop_send.this.dialog.dismiss();
				((OrderFormActivity) pop_send.this.context).Refresh_send();
			}
		};
	}

	public void closedlg() {
		this.dlg.dismiss();
	}

	public void onClick(View paramView) {
		if (paramView.getId() == R.id.send_pwd)
			this.i_seltxt = 0;
		else {
			if (paramView.getId() == R.id.send_num) {
				this.i_seltxt = 1;
				return;
			}
		}
		do {
			if (paramView.getId() == R.id.sendreadBtn3) {
				//new pop_selectdesk(this.context, this.tvtableid);
				return;
			}
			if (paramView.getId() == R.id.login_back) {
				switch (this.i_seltxt) {
				default:
					return;
				case 0:
					this.password.dispatchKeyEvent(new KeyEvent(0L, 0L, 0, 67,
							0, 0, 0, 0, 6));
					return;
				case 1:
				}
				this.tvnum.dispatchKeyEvent(new KeyEvent(0L, 0L, 0, 67, 0, 0,
						0, 0, 6));
				return;
			}
			if (paramView.getId() == R.id.login_clear) {
				switch (this.i_seltxt) {
				default:
					return;
				case 0:
					this.password.setText("");
					return;
				case 1:
				}
				this.tvnum.setText("");
				return;
			}
			if (paramView.getId() != R.id.loginBtn)
				break;
		} while (this.password.getText() == null);
		if (this.password.getText().toString().equals("")) {
			Toast.makeText(this.context, "Please enter password", 1000).show();
			return;
		}
		this.key_name = this.tvtableid.getText().toString().trim();
		if (this.key_name.equals("")) {
			Toast.makeText(this.context, "Select tables", 1000).show();
			return;
		}
		Constant.managerId = this.password.getText().toString();
		this.card_id = Constant.managerId;
		this.i_num = this.tvnum.getText().toString();
		if (this.i_num.equals(""))
			this.i_num = "1";
		else {
			if (paramView.getId() == R.id.loginCancleBtn) {
				closedlg();
				return;
			}
			switch (this.i_seltxt) {
			default:
				return;
			case 0:
				this.password.append(paramView.getTag().toString());
				return;
			case 1:
			}
			this.tvnum.append(paramView.getTag().toString());
		}
		closedlg();
		return;

	}
}