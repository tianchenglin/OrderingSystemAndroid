package com.utopia.activity;

import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message; 
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.utopia.Adapter.CookOrderSaleRecordAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Service.BluetoothService;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;

public class CookActivity extends BaseActivity implements View.OnClickListener {

	public static CookOrderSaleRecordAdapter sladapter;

	private Button current;
	private Button history;
	private List<d_SaleRecord> saleRecord;
	private ListView localListView;
	private HomeKeyLocker mHomeKeyLocker;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.cook);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		//mHomeKeyLocker.lock(CookActivity.this);
		
		new sql_SaleRecord().clear_all();
		// 得到数据库数据 ， 并且显示在GridView中
		initViews();
		initEvents();
		initlist();
		
	}
	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}
	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacks(task);
	}
	/*
	 * 得到数据库中数据
	 */
	public void initlist() {
		ListView localListView = (ListView) findViewById(R.id.cook_menu_list);
		sladapter = new CookOrderSaleRecordAdapter(this, Constant.table_id,false);
		
		// 显示数据
		localListView.setAdapter(sladapter);
	}
	
	/*
	 * 得到历史数据库中数据
	 */
	public void initHistoryList() {
		sladapter = new CookOrderSaleRecordAdapter(this, Constant.table_id,true);
		// 显示数据
		localListView.setAdapter(sladapter);
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		default:
			break;
		case R.id.goback:
			ExitApplication.getInstance().exit();
			break;

		case R.id.current:
			initlist();
			break;
		case R.id.history:
			initHistoryList();
			break;

		}
	}

	public void Refresh() {
		sladapter.open();
		sladapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Refresh();
		handler.post(task);// 立即调用刷新代码
	}

	@Override
	protected void initViews() {
		localListView = (ListView) findViewById(R.id.cook_menu_list);
		current = (Button) findViewById(R.id.current);
		history = (Button) findViewById(R.id.history);
	}

	@Override
	protected void initEvents() {
		// 返回
		((Button) findViewById(R.id.goback)).setOnClickListener(this);

		// current
		findViewById(R.id.current).setOnClickListener(this);
		// history
		findViewById(R.id.history).setOnClickListener(this);

	}
	
	// 构造函数AsyncTask<Params, Progress, Result>参数说明:
	// Params 启动任务执行的输入参数
	// Progress 后台任务执行的进度
	// Result 后台计算结果的类型
	private class RefreshAsyncTask extends AsyncTask<String, Integer, String> {
		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// doInBackground()方法用于在执行异步任务,不可以更改主线程中UI
		@Override
		protected String doInBackground(String... params) {
			System.out.println("调用doInBackground()方法--->开始执行异步任务");
			saleRecord = new JsonResolveUtils(CookActivity.this).getSaleRecords();
			return null;
		}

		// onPostExecute()方法用于异步任务执行完成后,在主线程中执行的操作
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("调用onPostExecute()方法--->异步任务执行完毕");
			handler.sendEmptyMessage(0);
		}

		// onCancelled()方法用于异步任务被取消时,在主线程中执行相关的操作
		@Override
		protected void onCancelled() {
			super.onCancelled();
			System.out.println("调用onCancelled()方法--->异步任务被取消");
		}
	}

	private Handler handler = new Handler() {
		/**
		 * 子类必须重写此方法,接受数据 接收消息并更新界面信息
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// 此处可以更新UI
			if(saleRecord.size()>0){
				for(int i = 0 ; i < saleRecord.size() ; i++)
					new sql_SaleRecord().save(saleRecord.get(i));
				Constant.lastTime = new sql_SaleRecord().getLastTime();
				Refresh();
			}
		}
	};

	private Runnable task = new Runnable() {
		public void run() {
			handler.postDelayed(this, 10* 1000);// 设置延迟时间，此处是5秒
			// 需要执行的代码
			new RefreshAsyncTask().execute();
		}
	};
}
