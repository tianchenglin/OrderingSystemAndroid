package com.utopia.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Service.ActivityViewManager;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup implements View.OnClickListener {
	private TextView foodNum;
	private ActivityViewManager mViewManager;
	private HomeKeyLocker mHomeKeyLocker;
	protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		ExitApplication.getInstance().addActivity(this);
		
		setContentView(R.layout.mainmg);
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(MainActivity.this);
		
		
		this.foodNum = ((TextView) findViewById(R.id.foodNum));
		this.mViewManager = new ActivityViewManager(this);

		this.mViewManager.setBtnindex(Constant.mainmgindex);
		Constant.foodnumhandler = this.handler;
		update_foodnum();
	}

	public void onClick(View paramView) {
		if ((paramView.getId() == R.id.btn1)
				|| (paramView.getId() == R.id.btn2)
				|| (paramView.getId() == R.id.btn3)
				|| (paramView.getId() == R.id.btn4)
				|| (paramView.getId() == R.id.btn5))
			this.mViewManager.setCurBtnPos(paramView.getId());
		
		
		
		if(paramView.getId() == R.id.left_button){
			
		}
		
	}

	private void logout(){
		startActivity(new Intent(MainActivity.this, LoginActivity.class));
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

			/*
			 * onPreExecute()这里是最终用户调用execute时的接口， 当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			 * 
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			/*
			 * doInBackground(Params…) 后台执行， 比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
			 * 此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。
			 * 在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			 * 
			 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
			 */

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
					// staff = JsonResolveUtils.GetUserInfo(LoginActivity.this);
					// Log.e("user", user.toString());
					// 从服务器得到用户名 密码 表
					return new JsonResolveUtils(MainActivity.this).LogOut(
							MainActivity.this, Constant.currentStaff.getS_account());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}

			/*
			 * onPostExecute(Result) 相当于Handler 处理UI的方式， 在这里面可以使用在doInBackground
			 * 得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (!result) {
					//showCustomToast("LogOut password authentication failed !");
				} else {
					
					}
				}
			
		});
	}
	
	private void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
		// TODO Auto-generated method stub
		mAsyncTasks.add(asyncTask.execute());
	}

	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}

	protected void update_foodnum() {
		Constant.foodnumcount = new sql_SaleRecord().getOneOrderTotalNum("0");
		if (Constant.foodnumhandler != null)
			Constant.foodnumhandler.sendEmptyMessage(1);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			if (paramAnonymousMessage.what == 1)
				MainActivity.this.foodNum.setText(String
						.valueOf(Constant.foodnumcount));
			super.handleMessage(paramAnonymousMessage);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
}