package com.utopia.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle; 
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Adapter.SalerecordAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dialog.pop_selectdesk;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.utils.Md5;
import com.utopia.utils.Snippet;
import com.utopia.widget.MyDialog;

public class OrderFormActivity extends BaseActivity implements
		View.OnClickListener {
	private MyDialog recoveryDialog;
	private TextView menu_sum_no;
	private TextView menu_sum_price;
	private TextView menu_sum_promotion;
	private TextView menu_sum_real_price;
	private TextView menu_table_number;
	public static SalerecordAdapter sladapter;
	private MyDialog mBackDialog;
	private HomeKeyLocker mHomeKeyLocker;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private sql_SaleRecord ssr;
	List<d_SaleRecord> saleRecords = new ArrayList<d_SaleRecord>();

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.mmyorders);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(OrderFormActivity.this);
		
		initViews();
		initEvents();
	}

	protected void onResume() {
		super.onResume();
		Refresh_num(); // 更新总数和总价
		initlist(); // 显示菜单
	}

	/*
	 * 显示总数和总价
	 */
	public void Refresh_num() {
		// sql_SaleRecord执行查询
		this.menu_sum_no.setText(decimalFormat.format(new sql_SaleRecord()
				.getOneOrderTotalNum(Constant.table_id)));
		this.menu_sum_price.setText(decimalFormat.format(new sql_SaleRecord()
				.getOneOrderTotalMoney(Constant.table_id)));
	}

	/*
	 * 适配器显示菜单
	 */
	public void initlist() {
		ListView localListView = (ListView) findViewById(R.id.menu_list);
		sladapter = new SalerecordAdapter(this, Constant.table_id);
		localListView.setAdapter(sladapter);
	}

	/*
	 * 点击事件
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		 case R.id.menu_send:
			 send();
			 break;
		case R.id.menu_clear: // 清空
			clearTableCheck();
			recoveryDialog.show(); 
			break;
		case R.id.menu_table_number: // 选择桌号
			new pop_selectdesk(this, paramView, "EMPTY");
			break;
		}
	}
	
	private void send() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

			/*
			 * onPreExecute()这里是最终用户调用execute时的接口， 当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			 * 
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
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
				List<d_SaleRecord> sales = (new sql_SaleRecord())
						.recordlist(Constant.table_id);
				if (sales.size() < 1)
					return false;
				try {
					Thread.sleep(1000);
					return new JsonResolveUtils(OrderFormActivity.this)
							.sendSaleRecords(sales);
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
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("Abnormal operation is prohibited!");
				} else {
					new sql_SaleRecord().update_send(Constant.table_id, "Sent");
					showCustomToast("Successed to place an order !");
					OrderFormActivity.this.Refresh();
				}
			}
		});
	}
	
	/**
	 * 检查桌子在后台厨师那是否还有未完成订单
	 */
	public void clearTableCheck() {
		if (!menu_table_number.getText().toString().equals("Select Tables") && new sql_SaleRecord().checkDesk(Constant.table_id)) {
			clearTableConfirm();
		} else {
			recoveryDialog = MyDialog.getDialog(OrderFormActivity.this, "Hint",
					"There are unfilled orders operation is prohibited...",
					"Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							recoveryDialog.dismiss();
						}
					});
			recoveryDialog
					.setButton1Background(R.drawable.btn_default_popsubmit);
		}
	}
	/**
	 * 确认清台
	 */
	private void clearTableConfirm() {
		recoveryDialog = MyDialog.getDialog(OrderFormActivity.this, "Hint",
				"You sure want to clear the table...", "Yse",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						clearTable();
						Refresh();
						dialog.dismiss();
					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				});
		recoveryDialog.setButton1Background(R.drawable.btn_default_popsubmit);
	}

	/*
	 * 后台清桌
	 */
	private void clearTable() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				//showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
					d_Desk desk = new d_Desk(0, "", "EMPTY", "EMPTY",
							Constant.table_id, 0, "", 0, 0, 0, 0, 0);
					new sql_SaleRecord().update("Finish", Constant.table_id);
					return new JsonResolveUtils(OrderFormActivity.this)
							.setDesks(desk);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("clear table failed !");
				} else {
					sql_SaleRecord ss = new sql_SaleRecord();
					ss.update("Finish", Constant.table_id);
					showCustomToast("clear table finished !");
					Refresh();
					Refresh_num();
				}
			}
		});

	}
	
	/*
	 * 点击清空按钮
	 */
	public void showclearDialog(Context paramContext) {
		mBackDialog = MyDialog.getDialog(OrderFormActivity.this, "Hint",
				"Do you really want to empty the food list?", "Ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						sladapter.clearBill(Constant.table_id); // 清空数据库
						Refresh_num(); // 更新总数和总价
						Toast.makeText(
								OrderFormActivity.this.getApplicationContext(),
								"The success of the operation.",
								Toast.LENGTH_SHORT).show();

					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);

		mBackDialog.show();
	}

	public void Refresh() {
		updateDone();
		sladapter.open();
		sladapter.notifyDataSetChanged();

		// this.menu_sum_no.setText(String.valueOf(new sql_SaleRecord()
		// .getOneOrderTotalNum(Constant.table_id)));
		//
		// this.menu_sum_price.setText(String.valueOf(new sql_SaleRecord()
		// .getOneOrderTotalMoney(Constant.table_id)));
	}

	public void Refresh_send() {
		Refresh();
		Refresh_num();
		sladapter.update_foodnum();
	}

	protected void onPause() {
		super.onPause();
		// this.sladapter.closedb();
		updateDone();
	}

	/*
	 * 更新 ：关闭数据库在onDestroy 更新：不关闭数据在onDestroy
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
		// this.sladapter.closedb();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Refresh_num(); // 更新总数和总价
		initlist(); // 显示菜单
	}
	

	
	public void startOnResume() {
		this.menu_sum_no.setText(decimalFormat.format(new sql_SaleRecord()
				.getOneOrderTotalNum(Constant.table_id)));
		this.menu_sum_price.setText(decimalFormat.format(new sql_SaleRecord()
				.getOneOrderTotalMoney(Constant.table_id)));
		updateDone();
		Refresh();

	}

	private void updateDone() {
		ssr = new sql_SaleRecord();
		new RefreshAsyncTask().execute();
	}
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
			saleRecords = new JsonResolveUtils(OrderFormActivity.this)
					.getSaleRecordDone(Constant.table_id);
			return null;
		}

		// onPostExecute()方法用于异步任务执行完成后,在主线程中执行的操作
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("调用onPostExecute()方法--->异步任务执行完毕");
			for (int i = 0; i < saleRecords.size(); i++) {
				ssr.updateDone(saleRecords.get(i));
				System.out.println(saleRecords.get(i).toString());
			}

		}

		// onCancelled()方法用于异步任务被取消时,在主线程中执行相关的操作
		@Override
		protected void onCancelled() {
			super.onCancelled();
			System.out.println("调用onCancelled()方法--->异步任务被取消");
		}
	}
	

	@Override
	protected void initViews() {
		// 桌号
		this.menu_table_number = ((TextView) findViewById(R.id.menu_table_number));
		//this.menu_table_number.setText(Constant.table_id);

		// 菜品类别总数
		this.menu_sum_no = ((TextView) findViewById(R.id.menu_sum_no));

		// 总价
		this.menu_sum_price = ((TextView) findViewById(R.id.menu_sum_price));
		// 优惠
		this.menu_sum_promotion = ((TextView) findViewById(R.id.menu_sum_promotion));
		// 实付
		this.menu_sum_real_price = ((TextView) findViewById(R.id.menu_sum_real_price));
		this.menu_sum_no.setText("0");
		this.menu_sum_price.setText("0.00");
		this.menu_sum_promotion.setText("0");
		this.menu_sum_real_price.setText("0.00");
	}

	@Override
	protected void initEvents() {
		// 清空
		((Button) findViewById(R.id.menu_clear)).setOnClickListener(this);
		// //发送
		 ((Button) findViewById(R.id.menu_send)).setOnClickListener(this);
		// 选择餐桌
		((TextView) findViewById(R.id.menu_table_number))
				.setOnClickListener(this);
	}
}