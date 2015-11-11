package com.utopia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.utopia.Adapter.DeliveryAdapter;
import com.utopia.Adapter.DeskAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_desk;
import com.utopia.Model.d_Desk;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.widget.MyScrollLayout;
import com.utopia.widget.SlideMenu;

public class DeskMenuActivity extends BaseActivity implements OnClickListener {
	private com.utopia.widget.SlideMenu slideMenu;
	private DeskAdapter localAdapter = null;
	private List<d_Desk> desks;
	private sql_desk sqldesk;
	private GridView localGridView, deliverGridView, pickupGridView;
	private MyScrollLayout curPage;
	private List<d_Desk> lstDate = new ArrayList<d_Desk>();
	/** 每页显示的数量，Adapter保持一致. */
	private static final float PAGE_SIZE = 35.0f;
	private ImageView iv;
	private RelativeLayout drops;
	private RelativeLayout pay_out;
	private RelativeLayout personal_report;
	private RelativeLayout purchase;
	private RelativeLayout close_shift;

	/** 总页数. */
	private int PageCount;

	private LinearLayout menu_setting;
	private LinearLayout menu_liquors;
	private LinearLayout menu_regular_tables;
	private LinearLayout menu_sushi_bar;
	private LinearLayout menu_take_out;
	private LinearLayout menu_dilivery;
	private LinearLayout menu_staff_sales;//员工的销售记录
	private LinearLayout menu_current_drawer;//柜台里的钱数
	private LinearLayout menu_manager_sales;//经理的销售记录
	private LinearLayout menu_drawers_history;//柜台里的收入支出记录
	private PopupWindow popup;
	private View settings;
	private LinearLayout linearLayout;

	private int mViewCount;
	private int mCurSel;
	private ImageView[] mImageViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);

		initRegular();
	}

	public void initlist() {
		curPage.getLayoutParams().height = this.getWindowManager()
				.getDefaultDisplay().getHeight();

		linearLayout = (LinearLayout) findViewById(R.id.llayout);
		mViewCount = curPage.getChildCount();
		mImageViews = new ImageView[linearLayout.getChildCount()];

		for (int i = 0; i < mViewCount; i++) {
			mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
			mImageViews[i].setEnabled(true);
			mImageViews[i].setOnClickListener(this);
			mImageViews[i].setTag(i);
		}

		for (int i = mViewCount; i < linearLayout.getChildCount(); i++) {
			mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
			mImageViews[i].setVisibility(8);
		}

		mCurSel = 0;
		mImageViews[mCurSel].setEnabled(false);
		mImageViews[mCurSel].setImageDrawable(getResources().getDrawable(
				R.drawable.current));
	}

	public void initViews() {
		drops = (RelativeLayout) settings.findViewById(R.id.drops);
		pay_out = (RelativeLayout) settings.findViewById(R.id.pay_out);
		personal_report = (RelativeLayout) settings
				.findViewById(R.id.personal_report);
		purchase = (RelativeLayout) settings.findViewById(R.id.purchase);
		close_shift = (RelativeLayout) settings.findViewById(R.id.close_shift);

		iv = (ImageView) findViewById(R.id.title_bar_menu_btn);
		menu_setting = (LinearLayout) findViewById(R.id.menu_setting);

		menu_liquors = (LinearLayout) findViewById(R.id.menu_liquors);
		menu_dilivery = (LinearLayout) findViewById(R.id.menu_dilivery);
		menu_regular_tables = (LinearLayout) findViewById(R.id.menu_regular_tables);
		menu_sushi_bar = (LinearLayout) findViewById(R.id.menu_sushi_bar);
		menu_take_out = (LinearLayout) findViewById(R.id.menu_take_out);
		menu_staff_sales=(LinearLayout) findViewById(R.id.staff_ll1);
		menu_current_drawer=(LinearLayout) findViewById(R.id.staff_ll2);
		menu_manager_sales=(LinearLayout) findViewById(R.id.manager_ll1);
		menu_drawers_history=(LinearLayout) findViewById(R.id.manager_ll2);
		
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
		else if(Constant.Area.equals("MY SALES REPORT"))
			menu_staff_sales.setBackgroundColor(Color.parseColor("#A25349"));
		else if(Constant.Area.equals("CURRENT DRAWER"))
			menu_current_drawer.setBackgroundColor(Color.parseColor("#A25349"));
		else if(Constant.Area.equals("SALES REPORT"))
			menu_manager_sales.setBackgroundColor(Color.parseColor("#A25349"));
		else if(Constant.Area.equals("DRAWERS HISTORY"))
			menu_drawers_history.setBackgroundColor(Color.parseColor("#A25349"));
		lstDate = new sql_desk(this).queryMenus(Constant.Area);
		PageCount = (int) Math.ceil(lstDate.size() / PAGE_SIZE);
		if (localGridView != null && curPage != null) {
			curPage.removeAllViews();
		}
		for (int i = 0; i < PageCount; i++) {
			localGridView = new GridView(DeskMenuActivity.this);
			localAdapter = new DeskAdapter(this, lstDate, i);
			localGridView.setAdapter(localAdapter);
			localGridView.setNumColumns(7);
			localGridView.setHorizontalSpacing(20);
			localGridView.setVerticalSpacing(20);
			curPage.addView(localGridView);
		}
	}

	public void Refresh() {
		// localAdapter.open();
		new RefreshAsyncTask().execute();
		localAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		handler.post(task);// 立即调用刷新代码
		// 此处可以更新UI
		if (Constant.Area.equals("Delivery")) {
			initDeliver();
		} else if(Constant.Area.equals("Take Out")){
			initTakeOut();
		}else {
			initViews();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacks(task);
	}

	private Runnable task = new Runnable() {
		public void run() {
			handler.postDelayed(this, 10 * 1000);// 设置延迟时间，此处是5秒
			// 需要执行的代码
			new RefreshAsyncTask().execute();
		}
	};
	private Handler handler = new Handler() {
		/**
		 * 子类必须重写此方法,接受数据 接收消息并更新界面信息
		 */
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 此处可以更新UI
			if (Constant.Area.equals("Delivery")) {
				initDeliver();
			} else if(Constant.Area.equals("Take Out")){
				initTakeOut();
			}else {
				initViews();
			}

		}
	};

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
			desks = new JsonResolveUtils(DeskMenuActivity.this)
					.getDesks(Constant.Area); // 从后台根据桌子所在区域返回值。
			return null;
		}

		// onPostExecute()方法用于异步任务执行完成后,在主线程中执行的操作
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("调用onPostExecute()方法--->异步任务执行完毕");
			for (int i = 0; i < desks.size(); i++) {
				sqldesk.opendesk(desks.get(i));
			
				
			}
			handler.sendEmptyMessage(0);
		}

		// onCancelled()方法用于异步任务被取消时,在主线程中执行相关的操作
		@Override
		protected void onCancelled() {
			super.onCancelled();
			System.out.println("调用onCancelled()方法--->异步任务被取消");
		}
	}

	@Override
	public void onBackPressed() {
		if (Constant.pop) {
			Constant.pop = false;
			getSlideMenu().closeMenu();
			//curPage.setAlpha(1.0f);
			iv.setBackgroundColor(Color.parseColor("#00FFFFFF"));
			if (popup.isShowing()) {
				popup.dismiss();

			}
		}
	}

	@Override
	protected void initEvents() {
		iv.setOnClickListener(this);
		menu_setting.setOnClickListener(this);

		menu_liquors.setOnClickListener(this);
		menu_dilivery.setOnClickListener(this);
		menu_regular_tables.setOnClickListener(this);
		menu_sushi_bar.setOnClickListener(this);
		menu_take_out.setOnClickListener(this);
		menu_staff_sales.setOnClickListener(this);
		menu_current_drawer.setOnClickListener(this);
		menu_manager_sales.setOnClickListener(this);
		menu_drawers_history.setOnClickListener(this);
		drops.setOnClickListener(this);
		pay_out.setOnClickListener(this);
		personal_report.setOnClickListener(this);
		purchase.setOnClickListener(this);
		close_shift.setOnClickListener(this);

		curPage.setPageListener(new MyScrollLayout.PageListener() {
			@Override
			public void page(int page) {
				// Log.e("Desk",page+"");
				setCurPoint(page);
			}
		});
	}

	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index) {
			return;
		}
		mImageViews[mCurSel].setEnabled(true);
		mImageViews[index].setEnabled(false);
		mImageViews[mCurSel].setImageDrawable(getResources().getDrawable(
				R.drawable.notcurrent));
		mImageViews[index].setImageDrawable(getResources().getDrawable(
				R.drawable.current));
		mCurSel = index;

	}

	public com.utopia.widget.SlideMenu getSlideMenu() {
		return slideMenu;
	}

	public void setSlideMenu(com.utopia.widget.SlideMenu slideMenu) {
		this.slideMenu = slideMenu;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_menu_btn:
			Constant.pop = true;
			iv.setBackgroundColor(Color.parseColor("#65696A"));
			if (getSlideMenu().isMainScreenShowing()) {
				getSlideMenu().openMenu();
				// curPage.setAlpha(0.5f);

			} else {
				closePopMenu();
			}
			break;
		case R.id.menu_setting:

			if (popup.isShowing()) {
				menu_setting.setBackgroundColor(Color.parseColor("#7B0000"));
				popup.dismiss();
			} else {
				int[] location = new int[2];
				menu_setting.getLocationOnScreen(location);
				menu_setting.setBackgroundColor(Color.parseColor("#A25349"));
				popup.showAtLocation(menu_setting, Gravity.NO_GRAVITY,
						location[0] + menu_setting.getWidth(), location[1] + 2);
			}
			break;

		case R.id.drops:
			startActivity(new Intent(DeskMenuActivity.this, DropsActivity.class));
			break;

		case R.id.pay_out:
			startActivity(new Intent(DeskMenuActivity.this,
					PayOutActivity.class));
			break;

		case R.id.personal_report:
			startActivity(new Intent(DeskMenuActivity.this,
					PersonalDailyReportActivity.class));
			break;

		case R.id.purchase:
			startActivity(new Intent(DeskMenuActivity.this,
					PurchaseActivity.class));
			break;

		case R.id.close_shift:
			startActivity(new Intent(DeskMenuActivity.this,
					CloseReportActivity.class));
			break;

		case R.id.menu_liquors:
			closePopMenu();
			initRegular();
			Constant.Area = "Liquor Bar";
			changeColor();
			initlist();
			Refresh();
			break;
		// Tables \ Liquor Bar \ Sushi Bar \ Take Out \ Delivery
		case R.id.menu_dilivery:
			closePopMenu();
			Constant.Area = "Delivery";
			changeColor();
			// initlist();
			// Refresh();
			initDeliver();
			break;
		case R.id.menu_regular_tables:
			closePopMenu();
			initRegular();
			Constant.Area = "Tables";
			changeColor();
			initlist();
			Refresh();
			break;
		case R.id.menu_sushi_bar:
			closePopMenu();
			initRegular();
			Constant.Area = "Sushi Bar";
			changeColor();
			initlist();
			Refresh();
			break;
		case R.id.menu_take_out:
			closePopMenu();
			initRegular();
			Constant.Area = "Take Out";
			changeColor();
			//initlist();
			initTakeOut();
			//Refresh();
            
			break;
		case R.id.staff_ll1:
			closePopMenu();
			Constant.Area="MY SALES REPORT";
			initStaff1();
			changeColor();
			break;
		case R.id.staff_ll2:
			closePopMenu();
			Constant.Area="CURRENT DRAWER";
			initStaff2();
			changeColor();
			break;
		case R.id.manager_ll1:
			closePopMenu();
			Constant.Area="SALES REPORT";
			initManager1();
			changeColor();
			break;
		case R.id.manager_ll2:
			closePopMenu();
			Constant.Area="DRAWERS HISTORY";
			initManager2();
			changeColor();
			break;
			

		}
	}
   
	private void closePopMenu() {
		Constant.pop = false;
		getSlideMenu().closeMenu();
		// curPage.setAlpha(1.0f);
		iv.setBackgroundColor(Color.parseColor("#00FFFFFF"));
		if (popup.isShowing()) {
			popup.dismiss();

		}
	}
    private void initManager1(){
    	setContentView(R.layout.manager_sales_report);
    	setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
		popup = new PopupWindow(settings, 250, 490);
		initStaffView();
		initStaffEvents();
		initManagerView1();
		initManagerEvent1();
    }
    private void initManager2(){
    	setContentView(R.layout.drawers_history);
    	setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
		popup = new PopupWindow(settings, 250, 490);
		
		initStaffView();
		initStaffEvents();
		initManagerView2();
		initManagerEvent2();
    }
    private void initManagerView1(){
    	
    }
    private void initManagerEvent1(){
    	
    }
    private void initManagerView2(){
    	
    }
    private void initManagerEvent2(){
    	
    }
    private void initStaff1(){
    	setContentView(R.layout.staff_sales_report);
    	setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
		popup = new PopupWindow(settings, 250, 490);
		if(Constant.currentStaff.getPriority()!=0)
		    findViewById(R.id.menu_manager).setVisibility(View.GONE);
		initStaffView();
		initStaffEvents();
    }
    private void initStaff2(){
    	setContentView(R.layout.staff_current_drawer);
    	setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
		popup = new PopupWindow(settings, 250, 490);
		if(Constant.currentStaff.getPriority()!=0)
		    findViewById(R.id.menu_manager).setVisibility(View.GONE);
		initStaffView();
		initStaffEvents();
    }
    private void initStaffView(){
    	drops = (RelativeLayout) settings.findViewById(R.id.drops);
		pay_out = (RelativeLayout) settings.findViewById(R.id.pay_out);
		personal_report = (RelativeLayout) settings
				.findViewById(R.id.personal_report);
		purchase = (RelativeLayout) settings.findViewById(R.id.purchase);
		close_shift = (RelativeLayout) settings.findViewById(R.id.close_shift);

		iv = (ImageView) findViewById(R.id.title_bar_menu_btn);
		iv.setBackgroundResource(R.drawable.ic_top_bar_category);
		
		menu_setting = (LinearLayout) findViewById(R.id.menu_setting);
		menu_liquors = (LinearLayout) findViewById(R.id.menu_liquors);
		menu_dilivery = (LinearLayout) findViewById(R.id.menu_dilivery);
		menu_regular_tables = (LinearLayout) findViewById(R.id.menu_regular_tables);
		menu_sushi_bar = (LinearLayout) findViewById(R.id.menu_sushi_bar);
		menu_take_out = (LinearLayout) findViewById(R.id.menu_take_out);
		menu_staff_sales=(LinearLayout) findViewById(R.id.staff_ll1);
		menu_current_drawer=(LinearLayout) findViewById(R.id.staff_ll2);
		menu_manager_sales=(LinearLayout) findViewById(R.id.manager_ll1);
		menu_drawers_history=(LinearLayout) findViewById(R.id.manager_ll2);
		
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
		else if(Constant.Area.equals("MY SALES REPORT"))
			menu_staff_sales.setBackgroundColor(Color.parseColor("#A25349"));
		else if(Constant.Area.equals("CURRENT DRAWER"))
			menu_current_drawer.setBackgroundColor(Color.parseColor("#A25349"));
		else if(Constant.Area.equals("SALES REPORT"))
			menu_manager_sales.setBackgroundColor(Color.parseColor("#A25349"));
		else if(Constant.Area.equals("DRAWERS HISTORY"))
			menu_drawers_history.setBackgroundColor(Color.parseColor("#A25349"));
    }
    private void initStaffEvents(){
    	iv.setOnClickListener(this);
		menu_setting.setOnClickListener(this);
		menu_liquors.setOnClickListener(this);
		menu_dilivery.setOnClickListener(this);
		menu_regular_tables.setOnClickListener(this);
		menu_sushi_bar.setOnClickListener(this);
		menu_take_out.setOnClickListener(this);
		menu_staff_sales.setOnClickListener(this);
		menu_current_drawer.setOnClickListener(this);
		menu_manager_sales.setOnClickListener(this);
		menu_drawers_history.setOnClickListener(this);
		drops.setOnClickListener(this);
		pay_out.setOnClickListener(this);
		personal_report.setOnClickListener(this);
		purchase.setOnClickListener(this);
		close_shift.setOnClickListener(this);
    }
	private void initRegular() {

		setContentView(R.layout.activity_main);
		setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
		popup = new PopupWindow(settings, 250, 490);
		if(Constant.currentStaff.getPriority()!=0)
		    findViewById(R.id.menu_manager).setVisibility(View.GONE);
		sqldesk = new sql_desk();
        
		initViews();
		initlist();
		initEvents();
	}

	private void initDeliver() {
		setContentView(R.layout.delivery);
		setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
	    popup = new PopupWindow(settings, 250, 490);
	    if(Constant.currentStaff.getPriority()!=0)
		    findViewById(R.id.menu_manager).setVisibility(View.GONE);
		sqldesk = new sql_desk();

		initDeliverViews();
		initDeliverlist();
		initDeliverEvents();
	}
	private void initTakeOut() {
		setContentView(R.layout.takeout);
		setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);
		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);
	    popup = new PopupWindow(settings, 250, 490);
	    if(Constant.currentStaff.getPriority()!=0)
		    findViewById(R.id.menu_manager).setVisibility(View.GONE);
		sqldesk = new sql_desk();

		initDeliverViews();
		initTakeoutlist();
		initDeliverEvents();
	}
	private void initDeliverEvents() {

		iv.setOnClickListener(this);
		menu_setting.setOnClickListener(this);

		menu_liquors.setOnClickListener(this);
		menu_dilivery.setOnClickListener(this);
		menu_regular_tables.setOnClickListener(this);
		menu_sushi_bar.setOnClickListener(this);
		menu_take_out.setOnClickListener(this);
		menu_staff_sales.setOnClickListener(this);
		menu_current_drawer.setOnClickListener(this);
		menu_manager_sales.setOnClickListener(this);
		menu_drawers_history.setOnClickListener(this);
		drops.setOnClickListener(this);
		pay_out.setOnClickListener(this);
		personal_report.setOnClickListener(this);
		purchase.setOnClickListener(this);
		close_shift.setOnClickListener(this);

	}

    private void initTakeoutlist(){
		lstDate = new sql_desk(this).queryMenus("Pick Up");
		lstDate.add(0, new d_Desk(0, "0", "0", "0", "0", -1, "2014-12-12 12:12:12", 0, 0, 0, 0, 0));
		DeliveryAdapter pickupAdapter = new DeliveryAdapter(this, lstDate, 1);
		pickupGridView.setAdapter(pickupAdapter);
    }
	private void initDeliverlist() {
//		lstDate = new sql_desk(this).queryMenus("Pick Up");
//		lstDate.add(0, new d_Desk(0, "0", "0", "0", "0", -1, "2014-12-12 12:12:12", 0, 0, 0, 0, 0));
//		DeliveryAdapter pickupAdapter = new DeliveryAdapter(this, lstDate, 1);
//		pickupGridView.setAdapter(pickupAdapter);
		
		lstDate = new sql_desk(this).queryMenus("Delivery");
		lstDate.add(0, new d_Desk(0, "0", "0", "0", "0", -1, "2014-12-12 12:12:12", 0, 0, 0, 0, 0));
		DeliveryAdapter deliverAdapter = new DeliveryAdapter(this, lstDate, 0);
		deliverGridView.setAdapter(deliverAdapter);

		
	}

	private void initDeliverViews() {

		drops = (RelativeLayout) settings.findViewById(R.id.drops);
		pay_out = (RelativeLayout) settings.findViewById(R.id.pay_out);
		personal_report = (RelativeLayout) settings
				.findViewById(R.id.personal_report);
		purchase = (RelativeLayout) settings.findViewById(R.id.purchase);
		close_shift = (RelativeLayout) settings.findViewById(R.id.close_shift);

		iv = (ImageView) findViewById(R.id.title_bar_menu_btn);
		iv.setBackgroundResource(R.drawable.ic_top_bar_category);
		menu_setting = (LinearLayout) findViewById(R.id.menu_setting);

		menu_liquors = (LinearLayout) findViewById(R.id.menu_liquors);
		menu_dilivery = (LinearLayout) findViewById(R.id.menu_dilivery);
		menu_regular_tables = (LinearLayout) findViewById(R.id.menu_regular_tables);
		menu_sushi_bar = (LinearLayout) findViewById(R.id.menu_sushi_bar);
		menu_take_out = (LinearLayout) findViewById(R.id.menu_take_out);
		menu_staff_sales=(LinearLayout) findViewById(R.id.staff_ll1);
		menu_current_drawer=(LinearLayout) findViewById(R.id.staff_ll2);
		menu_manager_sales=(LinearLayout) findViewById(R.id.manager_ll1);
		menu_drawers_history=(LinearLayout) findViewById(R.id.manager_ll2);
		deliverGridView = (GridView) this.findViewById(R.id.delivery_grid);
		pickupGridView = (GridView) this.findViewById(R.id.pickup_grid);

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
		else if (Constant.Area.equals("MY SALES REPORT"))
			menu_staff_sales.setBackgroundColor(Color.parseColor("#A25349"));
		else if (Constant.Area.equals("CURRENT DRAWER"))
			menu_current_drawer.setBackgroundColor(Color.parseColor("#A25349"));
		else if (Constant.Area.equals("SALES REPORT"))
			menu_manager_sales.setBackgroundColor(Color.parseColor("#A25349"));
		else if (Constant.Area.equals("DRAWERS　HISTORY"))
			menu_drawers_history.setBackgroundColor(Color.parseColor("#A25349"));
	}

	// 改变侧边栏的颜色
	private void changeColor() {
		if (Constant.Area.equals("Tables")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#A25349"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		} else if (Constant.Area.equals("Liquor Bar")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#A25349"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		} else if (Constant.Area.equals("Sushi Bar")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#A25349"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		} else if (Constant.Area.equals("Take Out")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#A25349"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		} else if (Constant.Area.equals("Delivery")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#A25349"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		}else if (Constant.Area.equals("MY SALES REPORT")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#A25349"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		}else if (Constant.Area.equals("CURRENT DRAWER")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#A25349"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		}else if (Constant.Area.equals("SALES REPORT")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#A25349"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#7B0000"));
		}else if (Constant.Area.equals("DRAWERS HISTORY")) {
			menu_regular_tables.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_liquors.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_sushi_bar.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_take_out.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_dilivery.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_staff_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_current_drawer.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_manager_sales.setBackgroundColor(Color.parseColor("#7B0000"));
			menu_drawers_history.setBackgroundColor(Color.parseColor("#A25349"));
		}
	}
}
