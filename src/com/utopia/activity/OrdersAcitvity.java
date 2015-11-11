package com.utopia.activity;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Adapter.DateAdapter;
import com.utopia.Adapter.MenuBillAdapter1;
import com.utopia.Adapter.OrdersSalerecordAdapter;
import com.utopia.Adapter.OrdersSalerecordAdapter.AppItem;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_Contact;
import com.utopia.Dao.sql_Customer;
import com.utopia.Dao.sql_MenuType;
import com.utopia.Dao.sql_Product;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dialog.TimeDialog;
import com.utopia.Dialog.pop_Input;
import com.utopia.Dialog.pop_discount;
import com.utopia.Dialog.pop_payment_method;
import com.utopia.Model.d_Bill;
import com.utopia.Model.d_Contact;
import com.utopia.Model.d_Customer;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_MenuType;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Model.d_Tax;
import com.utopia.Service.BluetoothService;
import com.utopia.manager.DeskManager;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyDialog;
import com.utopia.widget.MyScrollLayout;

public class OrdersAcitvity extends BaseActivity implements
		View.OnClickListener {
	private GridLayout menuTypes;
	private List<d_MenuType> lstDate_MenuType = new ArrayList<d_MenuType>();
	private List<Button> button_menuTypes = new ArrayList<Button>();
	private MyScrollLayout curPage;
	private View guloop_menu, guloop_addcontact;
	private LinearLayout lin;
	private List<d_Product> lstDate = new ArrayList<d_Product>();
	/** 总页数. */
	private int PageCount;
	/** GridView. */
	private GridView gridView;
	private DeskManager deskManager;
	/** 每页显示的数量，Adapter保持一致. */
	private static final float PAGE_SIZE = 27.0f;
	private OrdersSalerecordAdapter sladapter;
	private RadioButton table_priority;
	private TextView taxEdit;
	private TextView subTotal; // subtotal
	private Button save_and_quit, clear_table; // 保存并且退出
	private Button send; // 发送菜品至后台
	private Button schedule;//设置发送到厨房的时间
	private Button order_menu;//
	private TextView total;
	private String md5; // 该桌客人对应的MD5 ， 作为BillId
	private TextView discount, custom_name;
	d_Bill tBill = new d_Bill();

	private PopupWindow popupWindow;
	private ListView lv_group, localListView;
	private View view;
	private List<d_Tax> taxs = null;
	private MyDialog mBackDialog;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private int currentPage = 1;// 当前界面标记
	private float tax = (float) 1.0;
	private View menu;
	private PopupWindow popup;
	private GradientDrawable drawable = new GradientDrawable();
	private MyDialog recoveryDialog;
	public List<MenuBillAdapter1<d_SaleRecord>> adapters = new ArrayList<MenuBillAdapter1<d_SaleRecord>>();

	private RelativeLayout drops;
	private RelativeLayout pay_out;
	private RelativeLayout personal_report;
	private RelativeLayout purchase;
	private RelativeLayout close_shift;
	private LinearLayout menu_setting;
	private LinearLayout menu_liquors;
	private LinearLayout menu_regular_tables;
	private LinearLayout menu_sushi_bar;
	private LinearLayout menu_take_out;
	private LinearLayout menu_dilivery;
	
	private View settings;
	private View preSelectView = null;
	private d_SaleRecord preSelectItem;
	public List<View> views = new ArrayList<View>();
	// --------------------------------contact
	private AutoCompleteTextView customer_name;
	private AutoCompleteTextView customer_phone;

	private EditText add_number, add_street, add_apt, add_city, add_state,
			add_code;
	private EditText card_number, card_date, card_cvv, fname, lname;
	private EditText be_notes, not_notes;
	private Button contact_create;
	private Button discount_btn;

	private SimpleAdapter simple_adapter1;
	private List<Map<String, Object>> dataList1;
	public boolean isTel = true;
	private String addString = "-";
	private boolean isRun = false;
	private List<d_SaleRecord> saleList;
	// ---------------------------------------------payout
	private Button add_btn;
	private LinearLayout bill_layout;

	private int printer_counter = 0;

	// ////////-----------------------------------printer
	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	// Key names received from the BluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	// Intent request codes
	public static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the services
	private BluetoothService mService = null;

	private ImageView goback;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.guloop);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		currentPage = getIntent().getIntExtra("currentPage", 1);

		md5(); // 得到md5 , 使得一个桌子一个md5
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(getApplicationContext(), "打印机不可用",
					Toast.LENGTH_SHORT).show();
		}
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
		new sql_Customer().delete("delete from Customer");// 清空bill
		(new sql_SaleRecord()).updateAllCustomerNo();
		initViews();
		initEvents();

		curPage.getLayoutParams().height = this.getWindowManager()
				.getDefaultDisplay().getHeight() * 7 / 8;
		curPage.setPageListener(new MyScrollLayout.PageListener() {
			@Override
			public void page(int page) {
			}
		});

		if (currentPage == 1) {// 是menu界面
			initViews_MenuType();
		} else if (currentPage == 2) {// add contact
			initContactViews();
			initContactList();
			initContactEvents();
		}
		initlist();
		initManager();
		
	    
	}

	@Override
	public void onBackPressed() {
	}

	private void initManager() {
		deskManager = new DeskManager(getApplicationContext());
	}

	private void md5() {
		Cursor m_CallCursor;
		m_CallCursor = new sql_SaleRecord()
				.recordlist3("select BillId,itemNo,pdtCode,pdtName,number,price,otherspec1,otherspec2,status1,otherspec0,tax , rebate from "
						+ "SaleRecord as s1 join saleandpdt as s2 on s1.itemNo=s2.salerecordId" +
						"saleandpdt as s2 join Bill as b on s2.salerecordId=b.salerecordId  where deskName='"
						+ Constant.table_id
						+ "'and status1!='Finish'");
		// 若无菜单 ， 则md5使用上一个页面传过来的md5
		if (m_CallCursor.getCount() == 0) {
			
			md5 = UUID.randomUUID().toString().subSequence(0, 8).toString();
		} else // 若是有菜单， 则md5使用当前菜单的md5
		{
			m_CallCursor.moveToPosition(0);
			if (m_CallCursor.getString(m_CallCursor.getColumnIndex("BILLID"))
					.equals("")) {
				md5 = UUID.randomUUID().toString().subSequence(0, 8).toString();
			} else
				md5 = m_CallCursor.getString(m_CallCursor
						.getColumnIndex("BILLID"));
		}

		m_CallCursor.close();
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		// 结账
		case R.id.check_out:
			if (new sql_SaleRecord().getNotCheckedNum() > 0) {
				PrintDialog();
				mBackDialog.show();
			} else {
				/*
				 * Intent intent1 = new Intent();
				 * intent1.setClass(OrdersAcitvity.this, PayBillActivity.class);
				 * Bundle mbundle = new Bundle(); mbundle.putString("tax", tax +
				 * ""); mbundle.putString("discount",
				 * discount.getText().toString()); mbundle.putString("md5",
				 * md5); mbundle.putString("total", total.getText().toString());
				 * intent1.putExtras(mbundle); startActivity(intent1);
				 */
				currentPage = 3;
				initViews();
			}
			break;
		case R.id.order_menu:
			currentPage = 1;
			initViews();
			initViews_MenuType();
			break;
		// 清桌
		case R.id.clear_table:
			clearTableConfirm();
			break;
		// 保存退出
		case R.id.save_and_quit:
			openDesk();
			finish();
			break;
		//case R.id.priority:
			
		// 发送后台厨房
		case R.id.send:
//			if(!table_priority.isChecked()){
//				setPriorityDialog();
//				mBackDialog.show();
//			}
			openDesk();
			send();
			break;
	    // 设定发送到厨房的时间（当天中大于等于现在的某个时间）
		case R.id.schedule_btn:
			TextView time_tv=(TextView) findViewById(R.id.sendtime_tv);
			new TimeDialog(OrdersAcitvity.this,time_tv,schedule);
		    
			//openDesk();
		    //send();
		    break;
		case R.id.taxEdit:
			break;
		case R.id.main_btn:// add bill
			if (add_btn.getText().toString().equals("")) {
				if (preSelectItem.getStatus().equals("Not Sent")) {
					showDeleteDialog();
				} else {
					showCustomToast("Prohibit operating , only allow delete 'Not Sent item'");
				}
			} else {
				AddmenuList();
			}
			break;
		case R.id.discount_btn:
			 
			  new pop_discount(OrdersAcitvity.this, discount, Constant.table_id);
			
			break;
		}

	}
	private void setPriorityDialog() {
		mBackDialog = MyDialog.getDialog(OrdersAcitvity.this, "Hint",
				"Set Priority?", "OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						table_priority.setChecked(true);
					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);

	}

	private void showDeleteDialog() {
		mBackDialog = MyDialog.getDialog(OrdersAcitvity.this, "Hint",
				"Are you sure that you want to remove this item?", "Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						new sql_SaleRecord().delete(Constant.table_id,
								preSelectItem.getPdtName());
						Refresh();
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

	private void openDesk() {
		d_Desk tdesk = new d_Desk();
		tdesk.setPeople_num(1);
		tdesk.setDesk_name(Constant.table_id);
		tdesk.setStarttime(DateUtils.getDateEN());// 设置开桌时间
		tdesk.setS_account(Constant.currentStaff.getS_name());
		tdesk.setState("NotServed");
		String type = getIntent().getStringExtra("type");
		if (type == null)
			type = "";
		if (type.equals("0")) {
			tdesk.setType_id("05");
			tdesk.setState("Unpaid");
			tdesk.setS_account(custom_name.getText().toString());
			deskManager.openDesk(tdesk);
		} else if (type.equals("1")) {
			tdesk.setType_id("06");
			tdesk.setState("Unpaid");
			tdesk.setS_account(custom_name.getText().toString());
			deskManager.openDesk(tdesk);
		} else {
			deskManager.setDesk(tdesk);
		}

	}

	// 自定义对话框
	private void PrintDialog() {
		mBackDialog = MyDialog
				.getDialog(
						OrdersAcitvity.this,
						"Hint",
						"One or more orders has not been served. Are you sure to print?",
						"Sure", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								/*
								 * Intent intent1 = new Intent();
								 * intent1.setClass(OrdersAcitvity.this,
								 * PayBillActivity.class); Bundle mbundle = new
								 * Bundle(); mbundle.putString("tax", tax + "");
								 * mbundle.putString("discount", discount
								 * .getText().toString());
								 * mbundle.putString("md5", md5);
								 * mbundle.putString("total", total.getText()
								 * .toString()); intent1.putExtras(mbundle);
								 * startActivity(intent1);
								 */
								currentPage = 3;
								initViews();
							}
						}, "Cancel", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
		mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);

	}

	/**
	 * 初始化菜品类型
	 */
	@SuppressWarnings("deprecation")
	private void initViews_MenuType() {
		// 获取菜品类型数据
		lstDate_MenuType = new sql_MenuType(this)
				.queryMenuTypes("select * from MenuType");
		// 添加菜品类型
		for (int i = 0; i < lstDate_MenuType.size(); i++) {
			final Button button = new Button(this);
			button.setTag(lstDate_MenuType.get(i).getTypeId());
			button.setGravity(Gravity.CENTER);

			button.setBackgroundDrawable(drawable);

			if (i == 0) {
				button.setTextColor(Color.GREEN);
				initViews_Menu(lstDate_MenuType.get(0).getTypeId().toString());
			}
			button.setTextSize(18);
			button.setWidth(213);
			button.setHeight(40);
			button.setTextColor(Color.WHITE);
			button.setText(lstDate_MenuType.get(i).getTypeName());
			button.setOnClickListener(new OnClickListener() {
				@SuppressLint("ResourceAsColor")
				@Override
				public void onClick(View v) {
					// 为GridView绑定数据
					// initViews_Menu(position);
					for (int j = 0; j < button_menuTypes.size(); j++) {
						button_menuTypes.get(j).setTextColor(Color.WHITE);
						button_menuTypes.get(j).setBackgroundDrawable(drawable);
					}
					button.setTextColor(Color.GREEN);
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
			gridView.setNumColumns(5);
			gridView.setHorizontalSpacing(10); // 设置列间距
			gridView.setVerticalSpacing(10);
			curPage.addView(gridView);
		}
	}

	public void initlist() {
		localListView = (ListView) findViewById(R.id.menu_list);
		if (sladapter == null)
			sladapter = new OrdersSalerecordAdapter(this, Constant.table_id);
		localListView.setAdapter(this.sladapter);
		discount.setText(sladapter.getDiscount()+"");
		if(Constant.Area.equals("Take Out")||Constant.Area.equals("Delivery")){
		    Constant.schedule=sladapter.getCreateTime();
		    ((TextView)findViewById(R.id.sendtime_tv)).setText(Constant.schedule);
		} else{
			Constant.schedule="";
		}
		localListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (preSelectView != null)
					preSelectView.setBackgroundColor(Color.WHITE);

				AppItem app = (AppItem) arg1.getTag();
				d_SaleRecord item = (d_SaleRecord) app.menu_qty_add.getTag();
				d_Product product = (d_Product) new sql_Product()
						.recordlist(item.getPdtCODE());

				preSelectItem = item;
				preSelectView = arg1;
				if (item.getStatus().equals("Not Sent")) {
					arg1.setBackgroundColor(Color.parseColor("#f1ffe3"));
					Intent intent = new Intent(OrdersAcitvity.this,
							OrderMenuDetialActivity.class);
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("d_Product", product);
					mBundle.putString("md5", md5);
					intent.putExtras(mBundle);
					startActivity(intent);
				} else {
					showCustomToast("The operation is forbidden, and only the state is' Sent Not '.");
				}
			}
		});
		
	}

	public void Refresh() {
		Constant.sumTotal = new sql_SaleRecord().sumTotal(Constant.table_id);

	    //discount.setText("1.00");
		
		taxEdit.setText("0.025");

		subTotal.setText(decimalFormat.format(Constant.sumTotal)); // 所有菜单总价
		float a = Constant.sumTotal
				* Float.valueOf(discount.getText().toString()).floatValue();
		float b;

		if (taxEdit.getText().toString().contains("%")) {
			b = a
					+ a
					* ((Float.parseFloat(taxEdit
							.getText()
							.toString()
							.substring(0,
									taxEdit.getText().toString().length() - 1)) / 100));
			tax = a
					* ((Float.parseFloat(taxEdit
							.getText()
							.toString()
							.substring(0,
									taxEdit.getText().toString().length() - 1)) / 100));
		} else {
			b = a + a * Float.valueOf(taxEdit.getText().toString());
			tax = a * Float.valueOf(taxEdit.getText().toString());
		}

		total.setText(decimalFormat.format(b));

		sladapter.open();
		sladapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.sladapter.closedb();
	}

	@Override
	protected void initViews() {
		// 是否包含税收
		lin = (LinearLayout) findViewById(R.id.guloop_right);
		guloop_menu = View.inflate(this, R.layout.guloop_menu, null);
		guloop_addcontact = View
				.inflate(this, R.layout.guloop_addcontact, null);
		lin.removeAllViews();
		bill_layout = ((LinearLayout) findViewById(R.id.ll_bill));
		bill_layout.removeAllViews();
		discount_btn = (Button) findViewById(R.id.discount_btn);
		add_btn = (Button) findViewById(R.id.main_btn);
		custom_name = (TextView) findViewById(R.id.customer_name);
		schedule=(Button) findViewById(R.id.schedule_btn);
		table_priority=(RadioButton) findViewById(R.id.priority);
		switch (currentPage) {
		case 1:// menu
			lin.addView(guloop_menu);
			add_btn.setText("");
			add_btn.setBackgroundResource(R.drawable.menu_delete_hl);
			//
			break;
		case 2:// addcontact
			lin.addView(guloop_addcontact);
			add_btn.setText("");
			add_btn.setBackgroundResource(R.drawable.menu_delete_hl);
			break;
		case 3:// pay
			add_btn.setBackgroundColor(Color.parseColor("#4e4e4e"));
			add_btn.setText("Add Bill");
			AddmenuList();
			break;
		default:
			lin.addView(guloop_menu);
			break;
		}

		taxEdit = (TextView) findViewById(R.id.taxEdit);
		menuTypes = (GridLayout) findViewById(R.id.menuTypes);
		curPage = (MyScrollLayout) guloop_menu.findViewById(R.id.scr);
		subTotal = (TextView) findViewById(R.id.subTotal);
		save_and_quit = (Button) findViewById(R.id.save_and_quit);
		send = (Button) findViewById(R.id.send);
		clear_table = (Button) findViewById(R.id.clear_table);
		total = (TextView) findViewById(R.id.total);
		order_menu = (Button) findViewById(R.id.order_menu);
		discount = (TextView) findViewById(R.id.discount);
		drawable.setShape(GradientDrawable.RECTANGLE); // 画框
		// drawable.setStroke(1, Color.rgb(187, 187, 187)); // 边框粗细及颜色
		drawable.setColor(Color.rgb(88, 88, 88)); // 边框内部颜色

		menu = this.getLayoutInflater().inflate(R.layout.layout_menu, null);

		popup = new PopupWindow(menu, 200, 760);

		drops = (RelativeLayout) settings.findViewById(R.id.drops);
		pay_out = (RelativeLayout) settings.findViewById(R.id.pay_out);
		personal_report = (RelativeLayout) settings
				.findViewById(R.id.personal_report);
		purchase = (RelativeLayout) settings.findViewById(R.id.purchase);
		close_shift = (RelativeLayout) settings.findViewById(R.id.close_shift);
		menu_setting = (LinearLayout) menu.findViewById(R.id.menu_setting);
		menu_liquors = (LinearLayout) menu.findViewById(R.id.menu_liquors);
		menu_dilivery = (LinearLayout) menu.findViewById(R.id.menu_dilivery);
		menu_regular_tables = (LinearLayout) menu
				.findViewById(R.id.menu_regular_tables);
		goback = (ImageView) findViewById(R.id.goback);
		menu_sushi_bar = (LinearLayout) menu.findViewById(R.id.menu_sushi_bar);
		menu_take_out = (LinearLayout) menu.findViewById(R.id.menu_take_out);
		
		if (Constant.Area.equals("Tables"))
			menu_regular_tables.setBackgroundColor(Color.parseColor("#A25349"));
		else if (Constant.Area.equals("Liquor Bar"))
			menu_liquors.setBackgroundColor(Color.parseColor("#A25349"));
		else if (Constant.Area.equals("Sushi Bar"))
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#A25349"));
		else if (Constant.Area.equals("Take Out"))
			menu_take_out.setBackgroundColor(Color.parseColor("#A25349"));
		else if (Constant.Area.equals("Delivery"))
			menu_dilivery.setBackgroundColor(Color.parseColor("#A25349"));
		

	}

	@Override
	protected void initEvents() {
		findViewById(R.id.save_and_quit).setOnClickListener(this);
		findViewById(R.id.discount).setOnClickListener(this);

		((ImageView) findViewById(R.id.goback)).setOnClickListener(this);
		((Button) findViewById(R.id.check_out)).setOnClickListener(this);
		clear_table.setOnClickListener(this);
		save_and_quit.setOnClickListener(this);
		send.setOnClickListener(this);
		order_menu.setOnClickListener(this);
		taxEdit.setOnClickListener(this);
		schedule.setOnClickListener(this);
		table_priority.setOnClickListener(this);
		menu_setting.setOnClickListener(this);

		menu_liquors.setOnClickListener(this);
		menu_dilivery.setOnClickListener(this);
		menu_regular_tables.setOnClickListener(this);
		menu_sushi_bar.setOnClickListener(this);
		menu_take_out.setOnClickListener(this);

		drops.setOnClickListener(this);
		pay_out.setOnClickListener(this);
		personal_report.setOnClickListener(this);
		purchase.setOnClickListener(this);
		close_shift.setOnClickListener(this);
		add_btn.setOnClickListener(this);
		discount_btn.setOnClickListener(this);
		goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OrdersAcitvity.this.finish();
			}
		});
		findViewById(R.id.setContact).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				currentPage = 2;
				initViews();
				initContactViews();
				initContactList();
				initContactEvents();
			}
		});
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
					//if(table_priority.isChecked()){
					//	for(int i=0;i<sales.size();i++){
							//sales.get(i).setOtherSpecNo1("true");
							
				     //	}
						
              //     }
						if(Constant.schedule.equals(""))
							Constant.schedule=DateUtils.getDateEN();
						for(int i=0;i<sales.size();i++){
							sales.get(i).setCreateTime(Constant.schedule);
						}
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
					try {
						Thread.sleep(500);
						finish();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
	}

	// ////////////contact/////////////////////
	private void initContactList() {
		dataList1 = new ArrayList<Map<String, Object>>();// 创建一个数组集合
		List<d_Contact> contacts = new sql_Contact().getAllContact();
		d_Contact contact;
		Map<String, Object> map;
		for (int i = 0; i < contacts.size(); i++) {
			contact = contacts.get(i);
			map = new HashMap<String, Object>();

			map.put("phone", contact.getPhone());
			map.put("name", contact.getName());
			map.put("address", contact.getAdd_Street());
			map.put("add_Number", contact.getAdd_Number());
			map.put("add_Street", contact.getAdd_Street());
			map.put("add_Apt", contact.getAdd_Apt());
			map.put("add_City", contact.getAdd_City());
			map.put("add_State", contact.getAdd_State());
			map.put("add_Code", contact.getAdd_Code());
			map.put("card_Number", contact.getCard_Number());
			map.put("card_Date", contact.getCard_Date());
			map.put("card_Cvv", contact.getCard_Cvv());
			map.put("card_Fname", contact.getCard_Fname());
			map.put("card_Lname", contact.getCard_Lname());
			map.put("be_Notes", contact.getBe_Notes());
			map.put("not_Notes", contact.getNot_Notes());

			dataList1.add(map);
		}

		simple_adapter1 = new SimpleAdapter(this, dataList1,
				R.layout.pull_down_list_item, new String[] { "phone", "name",
						"address" }, new int[] { R.id.auto_text_phone,
						R.id.auto_text_name, R.id.auto_text_address });

		customer_name.setAdapter(simple_adapter1);
		customer_phone.setAdapter(simple_adapter1);
	}

	protected void initContactViews() {
		customer_name = (AutoCompleteTextView) findViewById(R.id.customer_name);
		customer_phone = (AutoCompleteTextView) findViewById(R.id.customer_phone);

		add_number = (EditText) findViewById(R.id.add_number);
		add_street = (EditText) findViewById(R.id.add_street);
		add_apt = (EditText) findViewById(R.id.add_apt);
		add_city = (EditText) findViewById(R.id.add_city);
		add_state = (EditText) findViewById(R.id.add_state);
		add_code = (EditText) findViewById(R.id.add_code);
		card_number = (EditText) findViewById(R.id.card_number);
		card_date = (EditText) findViewById(R.id.card_date);
		card_cvv = (EditText) findViewById(R.id.card_cvv);
		fname = (EditText) findViewById(R.id.fname);
		lname = (EditText) findViewById(R.id.lname);
		be_notes = (EditText) findViewById(R.id.be_notes);
		not_notes = (EditText) findViewById(R.id.not_notes);
		contact_create = (Button) findViewById(R.id.contact_create);
	}

	protected void initContactEvents() {
		customer_name.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				contact_create.setText("Save Changes");
				Map<String, Object> map = (Map<String, Object>) adapter
						.getItemAtPosition(position);

				// custom_name.setText(map.get("name").toString());
				customer_name.setText(map.get("name").toString());
				customer_phone.setText(map.get("phone").toString());
				add_number.setText(map.get("add_Number").toString());
				add_street.setText(map.get("add_Street").toString());
				add_apt.setText(map.get("add_Apt").toString());
				add_city.setText(map.get("add_City").toString());
				add_state.setText(map.get("add_State").toString());
				add_code.setText(map.get("add_Code").toString());
				card_number.setText(map.get("card_Number").toString());
				card_date.setText(map.get("card_Date").toString());
				card_cvv.setText(map.get("card_Cvv").toString());
				fname.setText(map.get("card_Fname").toString());
				lname.setText(map.get("card_Lname").toString());
				be_notes.setText(map.get("be_Notes").toString());
				not_notes.setText(map.get("not_Notes").toString());
			}

		});
		customer_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.i("tag", "onTextChanged()之前");
				if (isRun) {// 这几句要加，不然每输入一个值都会执行两次onTextChanged()，导致堆栈溢出，原因不明
					isRun = false;
					return;
				}
				isRun = true;
				Log.i("tag", "onTextChanged()");
				if (isTel) {
					String finalString = "";
					int index = 0;
					String telString = s.toString().replace("-", "");

					if (telString.length() > 0) {
						if (!(telString.charAt(telString.length() - 1) >= '0' && telString
								.charAt(telString.length() - 1) <= '9')) {
							customer_phone.setText(s.toString().substring(0,
									s.toString().length() - 1));
							customer_phone
									.setSelection(s.toString().length() - 1);

						} else if (telString.length() >= 10) {
							customer_phone.setText(s.toString()
									.substring(0, 12));
							customer_phone.setSelection(12);
						} else {
							for (int i = 0; i < 2; i++) {
								if ((index + 3) < telString.length()) {
									finalString += (telString.substring(index,
											index + 3) + addString);
									index += 3;
								}
							}

							if ((index + 4) < telString.length()) {
								finalString += (telString.substring(index,
										index + 4));
								// customer_phone.setEnabled(false);
								index += 4;

							}

							finalString += telString.substring(index,
									telString.length());
							customer_phone.setText(finalString);
							// 此语句不可少，否则输入的光标会出现在最左边，不会随输入的值往右移动
							customer_phone.setSelection(finalString.length());
						}
					}
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		customer_phone.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				contact_create.setText("Save Changes");
				Map<String, Object> map = (Map<String, Object>) adapter
						.getItemAtPosition(position);

				// custom_name.setText(map.get("name").toString());
				customer_name.setText(map.get("name").toString());
				customer_phone.setText(map.get("phone").toString());
				add_number.setText(map.get("add_Number").toString());
				add_street.setText(map.get("add_Street").toString());
				add_apt.setText(map.get("add_Apt").toString());
				add_city.setText(map.get("add_City").toString());
				add_state.setText(map.get("add_State").toString());
				add_code.setText(map.get("add_Code").toString());
				card_number.setText(map.get("card_Number").toString());
				card_date.setText(map.get("card_Date").toString());
				card_cvv.setText(map.get("card_Cvv").toString());
				fname.setText(map.get("card_Fname").toString());
				lname.setText(map.get("card_Lname").toString());
				be_notes.setText(map.get("be_Notes").toString());
				not_notes.setText(map.get("not_Notes").toString());
			}

		});
		contact_create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name = customer_name.getText().toString();
				((TextView) findViewById(R.id.customer_name)).setText(name);
				String phone = customer_phone.getText().toString();
				d_Contact contact = new d_Contact(name, phone, add_number
						.getText().toString(), add_street.getText().toString(),
						add_apt.getText().toString(), add_city.getText()
								.toString(), add_state.getText().toString(),
						add_code.getText().toString(), card_number.getText()
								.toString(), card_date.getText().toString(),
						card_cvv.getText().toString(), fname.getText()
								.toString(), lname.getText().toString(),
						be_notes.getText().toString(), not_notes.getText()
								.toString());

				if (contact_create.getText().toString().equals("Create")) {

					if (name.equals("") || phone.equals("")) {
						customer_name.setFocusable(true);
					} else {
						addContact(contact);
					}
				} else {
					updateContact(contact);
				}
			}
		});
	}

	protected void updateContact(d_Contact contact) {
		currentPage = 1;
		initViews();
		initViews_MenuType();
	}

	private void addContact(final d_Contact contact) {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					new sql_Contact().save(contact);
					return new JsonResolveUtils(OrdersAcitvity.this)
							.addContact(contact);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("save failed !");
				} else {
					showCustomToast("save success !");
					currentPage = 1;
					initViews();
					initViews_MenuType();
				}
			}
		});
	}

	private int cnt = 0;

	private void AddmenuList() {
		final MenuBillAdapter1 menuAdapter;
		final LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View tview = inflater.inflate(R.layout.bill, null);
		views.add(tview);

		TextView tv_title = (TextView) tview.findViewById(R.id.bill_text);
		tv_title.setText("BILL" + cnt);

		saleList = (new sql_SaleRecord()).getAllSalerecord(Constant.table_id);

		final int record[] = new int[saleList.size()];
		for (int i = 0; i < record.length; i++) {
			record[i] = 1;
		}
		final d_Customer dCus = new d_Customer();
		final sql_Customer sCustomer = new sql_Customer();

		for (int i = 0; i < saleList.size(); i++) {
			(new sql_SaleRecord()).update_customerNo(saleList.get(i)
					.getItemNo() + "", true);
			dCus.setCustomNo(cnt);
			dCus.setItemNo(saleList.get(i).getItemNo());
			sCustomer.save(dCus);
		}

		tview.setId(cnt);

		final ListView listView0 = (ListView) tview
				.findViewById(R.id.menu_list1);

		menuAdapter = new MenuBillAdapter1<d_SaleRecord>(this, saleList,
				R.layout.payout_item, cnt);
		adapters.add(menuAdapter);
        
		listView0.setAdapter(menuAdapter);
		listView0.setTag(cnt);
		listView0.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int tag = Integer.parseInt(listView0.getTag().toString());
				LinearLayout ll = (LinearLayout) view.findViewById(R.id.m_ll1);
				if (((TextView) view.findViewById(R.id.tv_qty1))
						.getTextColors() == ColorStateList.valueOf(Color.BLACK)) {
					record[position] = 0;

					dCus.setCustomNo(tag);
					dCus.setItemNo(saleList.get(position).getItemNo());
					sCustomer.delete(dCus);

					(new sql_SaleRecord()).update_customerNo(
							saleList.get(position).getItemNo() + "", false);

					delete(cnt + "", saleList.get(position).getPdtCODE());

					ll.setBackgroundResource(R.drawable.grey_bg);
					((TextView) view.findViewById(R.id.tv_qty1))
							.setTextColor(Color.parseColor("#DEE2D5"));
					((TextView) view.findViewById(R.id.tv_price1))
							.setTextColor(Color.parseColor("#DEE2D5"));
				} else {
					record[position] = 1;
					dCus.setCustomNo(tag);
					dCus.setItemNo(saleList.get(position).getItemNo());
					sCustomer.save(dCus);

					(new sql_SaleRecord()).update_customerNo(
							saleList.get(position).getItemNo() + "", true);

					ll.setBackgroundResource(R.drawable.orange_bg);
					((TextView) view.findViewById(R.id.tv_qty1))
							.setTextColor(Color.BLACK);
					((TextView) view.findViewById(R.id.tv_price1))
							.setTextColor(Color.BLACK);
				}
				RefreshAllBill();
			}

		});
		bill_layout.addView(tview);

		tview.findViewById(R.id.bt_delete_item).setTag(cnt);
		tview.findViewById(R.id.bt_delete_item).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						String position = arg0.getTag().toString();
						if (!position.equals("0")) {// 不是第一个bill
							bill_layout.removeView(tview);

							List<Integer> items = new sql_Customer()
									.getItemsId(position);
							for (int i = 0; i < items.size(); i++)
								(new sql_SaleRecord()).update_customerNo(
										items.get(i) + "", false);
							new sql_Customer().deleteCustomer(Integer
									.parseInt(position));
							RefreshAllBill();
							views.remove(tview);
						}
					}
				});
		tview.findViewById(R.id.printer_bill).setTag(cnt);
		tview.findViewById(R.id.printer_bill).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						arg0.setClickable(false);
						arg0.setBackgroundResource(R.drawable.printbtn_bg2);
						listView0.setClickable(false);
						++printer_counter;
						startActivityForResult(new Intent(OrdersAcitvity.this,
								BluetoothListActivity.class), 1);
						if (printer_counter == views.size()) {
							clear_table
									.setBackgroundResource(R.drawable.close2);
							clear_table.setEnabled(true);
						}
					}
				});
		final EditText tipAmount = (EditText) tview
				.findViewById(R.id.tipAmount);
		final EditText PaidAmount = (EditText) tview
				.findViewById(R.id.PaidAmount);
		final TextView tv_money = (TextView) tview
				.findViewById(R.id.bill_money1);
		final Button pay_method=(Button) tview
				.findViewById(R.id.tt1);

		tipAmount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new pop_Input(OrdersAcitvity.this, tipAmount, PaidAmount,
						tv_money, 1);

			}
		});
		PaidAmount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new pop_Input(OrdersAcitvity.this, tipAmount, PaidAmount,
						tv_money, 2);
			}
		});
		pay_method.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				new pop_payment_method(OrdersAcitvity.this,pay_method);
			}
		});
		cnt++;
		RefreshAllBill();
	}

	public void RefreshAllBill() {
		sql_Customer sqlCustomer = new sql_Customer();
		List<d_SaleRecord> sales;
		float tTip = 0, paid = 0;
		for (int j = 0; j < views.size(); j++) {
			sales = (new sql_SaleRecord()).getAllSalerecord(Constant.table_id);
			adapters.get(j).mDatas = sales;
			adapters.get(j).notifyDataSetChanged();

			float money = 0;
            
			for (int i = 0; i < sales.size(); i++) {
				if (sqlCustomer.vertiy(j, sales.get(i).getItemNo()))
					money += sales.get(i).getNumber() * sales.get(i).getPrice()
							/ sales.get(i).getCustomerNo();

			}
			
			money=(money*Float.valueOf(discount.getText().toString().trim()))
					*(1+Float.valueOf(taxEdit.getText().toString()));
			((TextView) views.get(j).findViewById(R.id.bill_money1))
					.setText(decimalFormat.format(money));

			EditText tipAmount = (EditText) views.get(j).findViewById(
					R.id.tipAmount);
			EditText PaidAmount = (EditText) views.get(j).findViewById(
					R.id.PaidAmount);
			if (!tipAmount.getText().toString().equals(""))
				tTip += Float.parseFloat(tipAmount.getText().toString());
			if (!PaidAmount.getText().toString().equals(""))
				paid += Float.parseFloat(PaidAmount.getText().toString());
		}
		((TextView) findViewById(R.id.total_paid_amount)).setText(decimalFormat
				.format(paid));
		((TextView) findViewById(R.id.total_tip)).setText(decimalFormat
				.format(tTip));

	}

	private void delete(String customNo, String ItemNo) {
		new sql_SaleRecord()
				.recordlist5("delete  from Customer  where  ItemNo='" + ItemNo
						+ "' and customNo='" + customNo + "'");
	}

	// ///////////////////打印////////////////////
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			BluetoothDevice device = mBluetoothAdapter
					.getRemoteDevice(Constant.printerAddress);
			if (mBluetoothAdapter == null) {
				showCustomToast("Printer is not available");
			} else {
				mService.connect(device);
				printTest();
			}
		}

	}

	public void printTest() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					int i = 0;
					while (mService.getState() != BluetoothService.STATE_CONNECTED) {
						Thread.sleep(500);
						if (i++ > 30) {
							mService.stop();
							Thread.sleep(500);
							return false;
						}
					}
				} catch (InterruptedException e) {
					mService.stop();
					e.printStackTrace();
					return false;
				}
				return true;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("Printer authentication failed !");
				} else {
					print();
				}
			}
		});

	}

	private void print() {

		if (mService == null)
			mService = new BluetoothService(this, mHandler);

		if (!fontGrayscaleSet(4)) {
			return;
		}

		// 初始化打印机
		byte[] byteInit = new byte[] { 0x1B, 0x40 };
		mService.write(byteInit);

		// 左对齐
		byte[] byteC = new byte[] { 0x1B, 0x61, (byte) 0x30 };

		// 打印标题
		byte[] byteA = new byte[] { 0x1D, 0x21, (byte) 0x0110 };
		mService.write(byteA);
		sendMessage("\n\nShangri-La\n\n");

		// 打印正文
		byte[] byteB = new byte[] { 0x1D, 0x21, (byte) 0x0000 };
		mService.write(byteB);
		for (int j = 0; j < views.size(); j++) {

			Cursor m_CallCursor;
			m_CallCursor = new sql_SaleRecord()
					.recordlist3("select s2.pdtName,s2.price,s2.number,s.itemNo,c.customNo from SaleRecord as s join Customer as c on s.itemNo=c.ItemNo " +
							"SaleRecord as s join saleandpdt as s2 on s.itemNo=s2.salerecordId " +
							"where s.deskName = '"
							+ Constant.table_id
							+ "' and s2.status1!='Finish' and c.customNo='"
							+ views.get(j).getId() + "'");
			sendMessage("\n\n");
			sendMessage("--------------Bill" + j + "-----------\n");

			while (m_CallCursor.moveToNext()) {
				mService.write(byteB);

				if (m_CallCursor.getString(
						m_CallCursor.getColumnIndex("pdtName")).length() > 12) {
					sendMessage(lpad(
							m_CallCursor.getString(
									m_CallCursor.getColumnIndex("pdtName"))
									.substring(0, 12), 17));
				} else if (m_CallCursor.getString(
						m_CallCursor.getColumnIndex("pdtName")).length() <= 12) {
					sendMessage(lpad(m_CallCursor.getString(m_CallCursor
							.getColumnIndex("pdtName")), 17));
				}

				mService.write(byteB);
				sendMessage(lpad(m_CallCursor.getString(m_CallCursor
						.getColumnIndex("price")), 9));
				mService.write(byteB);
				sendMessage(m_CallCursor.getString(m_CallCursor
						.getColumnIndex("number")) + "\n");
				mService.write(byteC);

			}
			m_CallCursor.close();
		}

		mService.write(byteB);
		openBox();
		// finish();
		deskManager.setDeliveryDeskState(Constant.table_id);
	}

	/**
	 * 补齐不足长度
	 * 
	 * @param length
	 *            长度
	 * @param str
	 *            字符串
	 * @return
	 */
	private String lpad(String str, int length) {
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() < length) {
			sb.append(" ");
		}

		return sb.toString();
	}

	@SuppressLint("NewApi")
	@Override
	public void onStart() {
		super.onStart();
		if (mService == null)
			mService = new BluetoothService(this, mHandler);

	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		Refresh();
		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mService.getState() == BluetoothService.STATE_NONE) {
				// Start the Bluetooth services
				mService.start();//
			}
		}
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	/**
	 * Set font gray scale
	 */
	private boolean fontGrayscaleSet(int ucFontGrayscale) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
			showCustomToast("Connection error");
			return false;
		}
		if (ucFontGrayscale < 1)
			ucFontGrayscale = 4;
		if (ucFontGrayscale > 8)
			ucFontGrayscale = 8;
		byte[] send = new byte[3];// ESC m n
		send[0] = 0x1B;
		send[1] = 0x6D;
		send[2] = (byte) ucFontGrayscale;
		mService.write(send);
		return true;
	}

	private void openBox() {
		byte[] byteA = new byte[] { 0x1B, 0x70, 0x00, (byte) 0xFE, (byte) 0xFE };
		mService.write(byteA);

		if (mService != null)
			mService.stop();
	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 * 
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {

			// showCustomToast("The printer connection error");
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothService to write
			byte[] send;
			try {
				send = message.getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				send = message.getBytes();
			}
			mService.write(send);
		}
	}

	// The Handler that gets information back from the BluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		}
	};

	/**
	 * 确认清台
	 */
	private void clearTableConfirm() {
		recoveryDialog = MyDialog.getDialog(OrdersAcitvity.this, "Hint",
				"You sure want to clear the table...", "Yse",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// saveBill();
						if (Constant.table_id.length() > 20) {
							deskManager
									.cleanDeliveryDeskState(Constant.table_id);
						} else {
							clearTable();
						}
						dialog.dismiss();
					}
				}, "Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				});
		recoveryDialog.setButton1Background(R.drawable.btn_default_popsubmit);
		recoveryDialog.show();
	}

	/*
	 * 后台清桌
	 */
	private void clearTable() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					d_Desk desk = new d_Desk(0, "", "EMPTY", "EMPTY",
							Constant.table_id, 0, "", 0, 0, 0, 0, 0);
					sql_SaleRecord ss = new sql_SaleRecord();
					ss.update("Finish", Constant.table_id);
					// new
					// JsonResolveUtils(context).sendSaleRecords(getSaleRecordsAll());
					boolean flag = new JsonResolveUtils(OrdersAcitvity.this)
							.setDesks(desk);
					Thread.sleep(1000);
					return flag;
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
					showCustomToast("clear table finished !");
					Intent intent = new Intent(OrdersAcitvity.this,
							DeskMenuActivity.class);
					startActivity(intent);
				}
			}
		});

	}

	/*
	 * private void saveBill() { // initTBill View t_view = null; for (int i =
	 * 0; i < datas.size(); i++) { t_view = views.get(i); float pay =
	 * Float.parseFloat(((TextView) t_view
	 * .findViewById(R.id.bill_pay)).getText().toString().replace('$', '0'));
	 * float due = Float.parseFloat(((TextView) t_view
	 * .findViewById(R.id.bill_due)).getText().toString().replace('$', '0'));
	 * float tips = Float.parseFloat(((TextView) t_view
	 * .findViewById(R.id.bill_tips)).getText().toString().replace('$', '0'));
	 * tBill = new d_Bill(Md5.md5(Snippet.generateID()),
	 * Constant.currentStaff.getS_name(), due, payStyle[i], pay,
	 * DateUtils.getDateEN(), datas.get(i).size(), tips); new
	 * sql_Bill().save(tBill); new RefreshAsyncTask().execute(); } }
	 */
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
			return new JsonResolveUtils(OrdersAcitvity.this).setBill(tBill);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			dismissLoadingDialog();
			if (!result) {
				showCustomToast("send Bill failed !");
			} else {
				showCustomToast("send Bill successed !");
			}
		}

	}
}