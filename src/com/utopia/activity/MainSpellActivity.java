package com.utopia.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Dialog.pop_setpwd;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;
import com.utopia.widget.MyDialog;
import com.utopia.widget.MyProgressView;

public class MainSpellActivity extends BaseActivity implements
		View.OnClickListener {

	private int time = 0;
	private int[] password = new int[] { R.id.passwd_1, R.id.passwd_2,
			R.id.passwd_3, R.id.passwd_4 };
	private AlertDialog.Builder builder;
	private AlertDialog dialog;

	private MyProgressView mTasksView;

	private int mTotalProgress = 100;
	private int mCurrentProgress = 0;

	private TextView tv_title;
	private TextView tv_content;
	private MyDialog mBackDialog;

	private StringBuilder pwd = new StringBuilder("");
	private HomeKeyLocker mHomeKeyLocker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
		setContentView(R.layout.main_spell);
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(MainSpellActivity.this);
		
		initEvents();
		Constant.DATABASE_PATH = getApplicationContext().getFilesDir()
				.getAbsolutePath();
		new Constant().copy(this);
		try {
			Thread.sleep(500L);
		} catch (InterruptedException localInterruptedException) {
			localInterruptedException.printStackTrace();
		}
	}
	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}
	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub
		if (paramView.getId() == R.id.login_clear) {
			if (time > 0) {
				--time;
				this.findViewById(password[time]).setBackgroundResource(
						R.drawable.login_num);
				pwd.deleteCharAt(pwd.length() - 1);
			}
			return;
		}
		if (paramView.getId() == R.id.loginBtn) {
			if (pwd.toString().equals("6666")) {
				MainSpellActivity.this.startActivity(new Intent(
						MainSpellActivity.this, MainActivity.class));
				this.finish();
			} else {
				showCustomToast("Login password authentication failed !");
			}
			return;
		}
		if (paramView.getId() == R.id.but_set) {
			new pop_setpwd(MainSpellActivity.this, paramView);
			return;
		}
		if (paramView.getId() == R.id.but_test) {
			mCurrentProgress = 0;
			initToast();
			return;
		}

		if (paramView.getId() == R.id.loginTrans) {
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			return;
		}

		if (time < 4) {
			this.findViewById(password[time]).setBackgroundResource(
					R.drawable.login_num2);
			switch (paramView.getId()) {
			case R.id.keyq:
				pwd.append("q");
				break;
			case R.id.keyw:
				pwd.append("w");
				break;
			case R.id.keye:
				pwd.append("e");
				break;
			case R.id.keyr:
				pwd.append("r");
				break;
			case R.id.keyt:
				pwd.append("t");
				break;
			case R.id.keyy:
				pwd.append("y");
				break;
			case R.id.keyu:
				pwd.append("u");
				break;
			case R.id.keyi:
				pwd.append("i");
				break;
			case R.id.keyo:
				pwd.append("o");
				break;
			case R.id.keyp:
				pwd.append("p");
				break;
			case R.id.keya:
				pwd.append("a");
				break;
			case R.id.keys:
				pwd.append("s");
				break;
			case R.id.keyd:
				pwd.append("d");
				break;
			case R.id.keyf:
				pwd.append("f");
				break;
			case R.id.keyg:
				pwd.append("g");
				break;
			case R.id.keyh:
				pwd.append("h");
				break;
			case R.id.keyj:
				pwd.append("j");
				break;
			case R.id.keyk:
				pwd.append("k");
				break;
			case R.id.keyl:
				pwd.append("l");
				break;
			case R.id.keyz:
				pwd.append("z");
				break;
			case R.id.keyx:
				pwd.append("x");
				break;
			case R.id.keyc:
				pwd.append("c");
				break;
			case R.id.keyv:
				pwd.append("v");
				break;
			case R.id.keyb:
				pwd.append("b");
				break;
			case R.id.keyn:
				pwd.append("n");
				break;
			case R.id.keym:
				pwd.append("m");
				break;
			}
			time++;
		}
	}

	private void initToast() {
		LayoutInflater inflater1 = getLayoutInflater();
		View view1 = inflater1.inflate(R.layout.readytoast,
				(ViewGroup) findViewById(R.id.toast_layout));
		tv_content = (TextView) view1.findViewById(R.id.txt_context);
		tv_title = (TextView) view1.findViewById(R.id.txt_title);
		mTasksView = (MyProgressView) view1.findViewById(R.id.tasks_view);
		builder = new AlertDialog.Builder(MainSpellActivity.this);
		builder.setView(view1);
		dialog = builder.create();
		new Thread(new ProgressRunable()).start();
		dialog.show();

	}

	class ProgressRunable implements Runnable {

		@Override
		public void run() {
			while (mCurrentProgress < mTotalProgress) {
				mCurrentProgress++;
				Message msg = new Message();
				msg.what = mCurrentProgress;
				myHandler.sendMessage(msg);
				mTasksView.setProgress(mCurrentProgress);
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dialog.dismiss();
		}

	}

	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				tv_title.setText("Are test network connection。。。");
				tv_content.setText(tv_content.getText().toString()
						+ "\t\nAre test network connection。。。");
				break;
			case 25:
				tv_title.setText("Is testing and host connection。。。");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nThe network connection is normal。\t\nIs testing and host connection。。。");
				break;
			case 50:
				tv_title.setText("Is the test order is sent。。。");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nHost connection is normal。\t\nIs the test order is sent。。。");
				break;
			case 75:
				tv_title.setText("Updating of data information。。。");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nOrder send normal。\t\nUpdating of data information。。。");
				break;
			case 97:
				tv_title.setText("The success of the test");
				tv_content
						.setText(tv_content.getText().toString()
								+ "\t\nData updated successfully。\t\nThe success of the test。");
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onBackPressed() {
		initBackDialog();
		mBackDialog.show();
	}

	private void initBackDialog() {
		mBackDialog = MyDialog.getDialog(MainSpellActivity.this, "Hint",
				"Are you sure you want to exit the program?", "OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvents() {
		((ImageButton) this.findViewById(R.id.keyq)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyw)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keye)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyr)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyt)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyy)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyu)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyi)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyo)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyp)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keya)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyd)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keys)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyf)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyg)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyh)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyj)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyk)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyl)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyz)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyx)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyc)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyv)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyb)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keyn)).setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.keym)).setOnClickListener(this);
		((Button) this.findViewById(R.id.login_clear)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginTrans)).setOnClickListener(this);
		((Button) this.findViewById(R.id.but_test)).setOnClickListener(this);
		((Button) this.findViewById(R.id.but_set)).setOnClickListener(this);
		((Button) this.findViewById(R.id.loginBtn)).setOnClickListener(this);

	}
}
