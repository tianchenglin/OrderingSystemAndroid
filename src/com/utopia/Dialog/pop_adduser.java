package com.utopia.Dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.utopia.Adapter.MySpinnerAdapter;
import com.utopia.activity.R;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyTextView;

public class pop_adduser implements View.OnClickListener {
	private Context context;
	
	PopupWindow popupWindow;
	private Spinner spinner;
	private EditText account;
	private EditText password;
	private EditText name;
	private ArrayAdapter adapter;
	private List<String> myList = new ArrayList<String>();
	
	public pop_adduser(Context paramContext, View paramView) {
		context = paramContext;
		mLoadingDialog = new LoadingDialog(context,
				"In the request submission");
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_adduser, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		
		localView.findViewById(R.id.loginCancleBtn).setOnClickListener(this);
		localView.findViewById(R.id.loginBtn).setOnClickListener(this);
		account = (EditText)localView.findViewById(R.id.account);
		password = (EditText)localView.findViewById(R.id.password);
		name = (EditText)localView.findViewById(R.id.name);
		
		myList.add("The manager");
		myList.add("Waiter");
		myList.add("The cook");
		
		spinner = (Spinner)localView.findViewById(R.id.Spinner01);
		//将可选内容与ArrayAdapter连接起来
		spinner.setAdapter(new MySpinnerAdapter(context,myList,3));
        
        //设置默认值
		spinner.setVisibility(View.VISIBLE);
      		
    }
	
	
	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	public void onClick(View paramView) {
		if(paramView.getId() == R.id.loginCancleBtn){
			closePop();
		}
		
		// OK按钮
		if(paramView.getId() == R.id.loginBtn){
			new RefreshAsyncTask().execute();
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
				boolean flag = false;
				try {
					flag = new JsonResolveUtils(context).addUser(spinner.getSelectedItemPosition()+1,account.getText().toString(),password.getText().toString(),name.getText().toString());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return flag;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("Add operator failed !");
				} else {
					closePop();
					showCustomToast("Add operator successed !");
				}
			}

		}
		LoadingDialog mLoadingDialog ;
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