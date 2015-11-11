package com.utopia.Dialog;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Dao.sql_desk;
import com.utopia.Model.d_Desk; 
import com.utopia.activity.DeskMenuActivity;
import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyTextView;

public class pop_open implements View.OnClickListener {
	private Context context;
	private int i_num;
	private TextView localnum;
	private TextView pdaId;
	private TextView desk_name;
	private d_Desk locald_keycode;
	private PopupWindow popupWindow;

	public pop_open(Context paramContext, View paramView) {
		context = paramContext;
		 mLoadingDialog = new LoadingDialog(context,
					"In the request submission");
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_open, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);

		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		desk_name = ((TextView) localView.findViewById(R.id.table_popNum));
		desk_name.setText(((d_Desk) paramView.getTag()).getDesk_name().trim());
		this.i_num = 1;
		this.localnum = ((TextView) localView
				.findViewById(R.id.mtableCustomerTv));
		this.localnum.setText(String.valueOf(this.i_num));
		this.pdaId = ((TextView) localView.findViewById(R.id.pdaId));
		this.pdaId.setText(Constant.currentStaff.getS_name());
		((ImageButton) localView.findViewById(R.id.table_person_cut))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.table_person_add))
				.setOnClickListener(this);
		Button localButton = (Button) localView.findViewById(R.id.table_pop_ok);
		localButton.setTag(paramView.getTag());
		localButton.setOnClickListener(this);
		((Button) localView.findViewById(R.id.table_pop_cancle))
				.setOnClickListener(this);
	}

	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.table_pop_ok:
			closePop();
			locald_keycode = new d_Desk();
			locald_keycode.setDesk_name(desk_name.getText().toString());
			locald_keycode.setPeople_num(Integer.parseInt(localnum.getText()
					.toString()));
			locald_keycode.setStarttime(DateUtils.getDateEN());
			locald_keycode.setS_account(this.pdaId.getText().toString());
			locald_keycode.setState("NotServed");
			new RefreshAsyncTask().execute();

			break;
		case R.id.table_pop_cancle:
			closePop();
			break;
		case R.id.table_person_add:
			if (i_num < 12) {
				this.localnum.setText(String.valueOf(++i_num));
			}
			break;
		case R.id.table_person_cut:
			if (i_num > 1) {
				this.localnum.setText(String.valueOf(--i_num));
			}
			break;
		}
	}

	// 构造函数AsyncTask<Params, Progress, Result>参数说明:
	// Params 启动任务执行的输入参数
	// Progress 后台任务执行的进度
	// Result 后台计算结果的类型
	private class RefreshAsyncTask extends AsyncTask<Void, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog("Just a moment, please...");
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return new JsonResolveUtils(context).setDesks(locald_keycode);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dismissLoadingDialog();
			if (!result) {
				showCustomToast("send order failed !");
			} else {
				new sql_desk().opendesk(locald_keycode);
				((DeskMenuActivity) context).initViews();
				showCustomToast("send order successed !");
			}
		}

	}
	LoadingDialog mLoadingDialog;
	protected void showLoadingDialog(String text) {
		if (text != null) {
			mLoadingDialog.setText(text);
		}
		mLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
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