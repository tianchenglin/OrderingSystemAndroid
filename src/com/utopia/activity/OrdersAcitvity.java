package com.utopia.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle; 
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utopia.Adapter.DateAdapter;
import com.utopia.Adapter.GroupAdapter;
import com.utopia.Adapter.OrdersSalerecordAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_MenuType;
import com.utopia.Dao.sql_Product;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dao.sql_Tax;
import com.utopia.Dialog.pop_discountAll;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Tax;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyDialog;
import com.utopia.widget.MyScrollLayout;

public class OrdersAcitvity extends BaseActivity implements
		View.OnClickListener {
	private LinearLayout menuTypes;
	private List<d_MenuType> lstDate_MenuType = new ArrayList<d_MenuType>();
	private List<Button> button_menuTypes = new ArrayList<Button>();
	private MyScrollLayout curPage;
	private List<d_Product> lstDate = new ArrayList<d_Product>();
	/** 总页数. */
	private int PageCount;
	/** GridView. */
	private GridView gridView;
	/** 每页显示的数量，Adapter保持一致. */
	private static final float PAGE_SIZE = 27.0f;
	private OrdersSalerecordAdapter sladapter;
	private TextView taxEdit;
	private CheckBox tax_cb;
	private TextView about_date; // subtotal
	private Button save_and_quit; // 保存并且退出
	private Button send; // 发送菜品至后台
	private TextView total;
	private String md5; // 该桌客人对应的MD5 ， 作为BillId
	private TextView discount;
	private MyDialog recoveryDialog;
	private PopupWindow popupWindow;
	private ListView lv_group;
	private View view;
	private List<d_Tax> taxs = null;
	private MyDialog mBackDialog;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private TextView imgDiscount;
	private float tax = (float) 1.0;
	GradientDrawable drawable = new GradientDrawable();

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.guloop);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		md5(); // 得到md5 , 使得一个桌子一个md5
		initViews();
		initEvents();

		curPage.getLayoutParams().height = this.getWindowManager()
				.getDefaultDisplay().getHeight() * 7 / 8;
		curPage.setPageListener(new MyScrollLayout.PageListener() {
			@Override
			public void page(int page) {
			}
		});
		initViews_MenuType();
		initlist();

	}

	private void md5() {
		Cursor m_CallCursor;
		m_CallCursor = new sql_SaleRecord()
				.recordlist3("select BILLID,ItemNo,PdtCODE,PdtName,number,Price,OtherSpecNo1,OtherSpecNo2,status,OtherSpec,tax , Discount from "
						+ "SaleRecord  where desk_name='"
						+ Constant.table_id
						+ "'and status!='Finish'");
		// 若无菜单 ， 则md5使用上一个页面传过来的md5
		if (m_CallCursor.getCount() == 0) {
			md5 = this.getIntent().getStringExtra("Md5");
		} else // 若是有菜单， 则md5使用当前菜单的md5
		{
			m_CallCursor.moveToPosition(0);
			if (m_CallCursor.getString(m_CallCursor.getColumnIndex("BILLID"))
					.equals("")) {
				md5 = this.getIntent().getStringExtra("Md5");
			} else
				md5 = m_CallCursor.getString(m_CallCursor
						.getColumnIndex("BILLID"));
		}

		m_CallCursor.close();
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		default:
			break;
		case R.id.goback:
			if (new sql_SaleRecord().getStatus(Constant.table_id)) {
				initBackDialog();
				mBackDialog.show();
			} else
				this.finish();
			break;
		// 结账
		case R.id.check_out:

			Intent intent = new Intent();
			intent.setClass(OrdersAcitvity.this, PayBillActivity.class);
			Bundle mbundle = new Bundle();
			mbundle.putString("tax", tax + "");
			mbundle.putString("discount", discount.getText().toString());
			mbundle.putString("md5", md5);
			intent.putExtras(mbundle);
			startActivity(intent);
			break;

		case R.id.about_constellation:
			// new pop_discountAll(this, paramView);
			break;
		// 输入折扣
		case R.id.discount_modify:
			new pop_discountAll(this, paramView, md5);
			break;

		// 清桌
		case R.id.clear_table:
			clearTableCheck();
			recoveryDialog.show();
			break;

		// 保存退出
		case R.id.save_and_quit:
			this.finish();
			break;
		// 发送后台
		case R.id.send:
			send();
			break;
		case R.id.taxEdit:
			showWindow(paramView);
			break;
		}

	}

	// 自定义对话框
	private void initBackDialog() {
		mBackDialog = MyDialog.getDialog(OrdersAcitvity.this, "Hint",
				"You have Not Sent information, whether to continue to exit?",
				"OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						new sql_SaleRecord().deleteNotSent(Constant.table_id,
								"Not Sent");
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

	/*
	 * 显示提示框 ， 两个税选其一
	 */
	private void showWindow(View parent) {

		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.group_list, null);
			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			// 加载数据
			taxs = new sql_Tax().select();
			GroupAdapter groupAdapter = new GroupAdapter(this, taxs);
			lv_group.setAdapter(groupAdapter);
			// 创建一个PopuWidow对象
			popupWindow = new PopupWindow(view, 160, 120);
		}
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		/*
		 * WindowManager windowManager = (WindowManager)
		 * getSystemService(Context.WINDOW_SERVICE); //
		 * 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半 int xPos =
		 * windowManager.getDefaultDisplay().getWidth() / 2 -
		 * popupWindow.getWidth() / 2;
		 */
		popupWindow.showAsDropDown(parent, 10, 0);
		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {

				taxEdit.setText(taxs.get(position).getTax_value() + "");
				Refresh();
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}

	/**
	 * 检查桌子在后台厨师那是否还有未完成订单
	 */
	public void clearTableCheck() {
		if (new sql_SaleRecord().checkDesk(Constant.table_id)) {
			clearTableConfirm();
		} else {
			recoveryDialog = MyDialog.getDialog(OrdersAcitvity.this, "Hint",
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
		recoveryDialog = MyDialog.getDialog(OrdersAcitvity.this, "Hint",
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
				// showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
					d_Desk desk = new d_Desk(0, "", "EMPTY", "EMPTY",
							Constant.table_id, 0, "", 0, 0, 0, 0, 0);
					new sql_SaleRecord().update("Finish", Constant.table_id);
					return new JsonResolveUtils(OrdersAcitvity.this)
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
					OrdersAcitvity.this.finish();
				}
			}
		});

	}

	/**
	 * 初始化菜品类型
	 */
	@SuppressWarnings("deprecation")
	private void initViews_MenuType() {
		// 获取菜品类型数据
		lstDate_MenuType = new sql_MenuType(this)
				.queryMenuTypes("select * from MenuType where TypeParentId <> '0000'");
		// 添加菜品类型
		for (int i = 0; i < lstDate_MenuType.size(); i++) {
			final Button button = new Button(this);
			button.setTag(lstDate_MenuType.get(i).getTypeId());
			button.setGravity(Gravity.CENTER);

			button.setBackgroundDrawable(drawable);

			if (i == 0) {
				button.setTextColor(Color.WHITE);
				initViews_Menu(lstDate_MenuType.get(0).getTypeId().toString());
			}
			button.setTextSize(18);
			button.setWidth(213);
			button.setHeight(40);
			button.setText(lstDate_MenuType.get(i).getTypeName());
			button.setOnClickListener(new OnClickListener() {
				@SuppressLint("ResourceAsColor")
				@Override
				public void onClick(View v) {
					// 为GridView绑定数据
					// initViews_Menu(position);
					for (int j = 0; j < button_menuTypes.size(); j++) {
						button_menuTypes.get(j).setTextColor(Color.BLACK);
						button_menuTypes.get(j).setBackgroundDrawable(drawable);
					}
					button.setBackgroundColor(Color.WHITE);
					initViews_Menu(button.getTag().toString());
				}
			});

			button_menuTypes.add(button);
			// 将组件对象放置在布局管理器中
			menuTypes.addView(button, i);
		}
	}

	/**
	 * 添加GridView
	 */
	private void initViews_Menu(String type_id) {
		lstDate = new sql_Product(this).queryMenus(type_id);
		PageCount = (int) Math.ceil(lstDate.size() / PAGE_SIZE);
		if (gridView != null) {
			curPage.removeAllViews();
		}
		for (int i = 0; i < PageCount; i++) {
			gridView = new GridView(OrdersAcitvity.this);
			gridView.setAdapter(new DateAdapter(OrdersAcitvity.this, lstDate,
					i, null, md5));
			gridView.setNumColumns(9);
			gridView.setHorizontalSpacing(28); // 设置列间距
			gridView.setVerticalSpacing(40);
			curPage.addView(gridView);
		}
	}

	public void initlist() {
		ListView localListView = (ListView) findViewById(R.id.menu_list);
		if (sladapter == null)
			sladapter = new OrdersSalerecordAdapter(this, Constant.table_id);
		localListView.setAdapter(this.sladapter);

	}

	public void RefreshDiscount() {
		discount.setText(imgDiscount.getText().toString());
		imgDiscount.setText("");
		if (discount.getText().toString().equals("")) {
			discount.setText("1");
		}
	}

	public void Refresh() {
		Constant.sumTotal = new sql_SaleRecord().sumTotal(Constant.table_id);
		/*
		 * discount.setText(imgDiscount.getText().toString());
		 * imgDiscount.setText("");
		 */
		if (discount.getText().toString().equals("")) {
			discount.setText("1");
		}
		about_date.setText(decimalFormat.format(Constant.sumTotal)); // 右边所有菜单总价
		float a = Constant.sumTotal
				* Float.valueOf(discount.getText().toString()).floatValue();
		if (taxEdit.getText().equals("")) {
			taxEdit.setText("6.25%");
		}
		float b = a
				+ a
				* Float.valueOf(taxEdit
						.getText()
						.toString()
						.substring(0, taxEdit.getText().toString().length() - 1));

		tax = a
				* Float.valueOf(taxEdit
						.getText()
						.toString()
						.substring(0, taxEdit.getText().toString().length() - 1));
		total.setText(decimalFormat.format(b));

		this.sladapter.open();
		this.sladapter.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.sladapter.closedb();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.sladapter.closedb();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Refresh();
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	protected void initViews() {
		// 是否包含税收
		taxEdit = (TextView) findViewById(R.id.taxEdit);
		tax_cb = (CheckBox) findViewById(R.id.tax_cb);
		menuTypes = (LinearLayout) findViewById(R.id.menuTypes);
		curPage = (MyScrollLayout) findViewById(R.id.scr);
		about_date = (TextView) findViewById(R.id.about_date);
		save_and_quit = (Button) findViewById(R.id.save_and_quit);
		send = (Button) findViewById(R.id.send);
		total = (TextView) findViewById(R.id.total);
		discount = (TextView) findViewById(R.id.about_constellation);
		imgDiscount = (TextView) findViewById(R.id.discount_modify);
		drawable.setShape(GradientDrawable.RECTANGLE); // 画框
		drawable.setStroke(1, Color.rgb(187, 187, 187)); // 边框粗细及颜色
		drawable.setColor(Color.rgb(173, 173, 173)); // 边框内部颜色
		RefreshDiscount();
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.save_and_quit).setOnClickListener(this);
		findViewById(R.id.about_constellation).setOnClickListener(this);
		findViewById(R.id.discount_modify).setOnClickListener(this);

		// 是否添加税收
		tax_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
					taxEdit.setText("0.00");
					taxEdit.setEnabled(false);
					Refresh();
				} else {
					taxEdit.setText("0.00");
					taxEdit.setEnabled(true);
					Refresh();
				}
			}
		});

		((ImageView) findViewById(R.id.goback)).setOnClickListener(this);
		((Button) findViewById(R.id.check_out)).setOnClickListener(this);
		((Button) findViewById(R.id.clear_table)).setOnClickListener(this);
		save_and_quit.setOnClickListener(this);
		send.setOnClickListener(this);
		taxEdit.setOnClickListener(this);
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
					return new JsonResolveUtils(OrdersAcitvity.this)
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
					OrdersAcitvity.this.Refresh();
				}
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// Constant.sumTotal = new sql_SaleRecord().sumTotal(Constant.table_id);

	}

}