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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
	private GridView localGridView;
	private MyScrollLayout curPage;
	private List<d_Desk> lstDate = new ArrayList<d_Desk>();
	/** 每页显示的数量，Adapter保持一致. */
	private static final float PAGE_SIZE = 35.0f;
	private ImageView iv;
	private TextView clock_out;
	private TextView drops;
	private TextView pay_out;
	private TextView personal_report;

	/** 总页数. */
	private int PageCount;

	private ImageView menu_setting;
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

		setContentView(R.layout.activity_main);

		setSlideMenu((SlideMenu) findViewById(R.id.slide_menu));

		sqldesk = new sql_desk();
		initlist();
		curPage = (MyScrollLayout) findViewById(R.id.gv_desk);

		curPage.getLayoutParams().height = this.getWindowManager()
				.getDefaultDisplay().getHeight();

		settings = this.getLayoutInflater().inflate(
				R.layout.layout_menu_setting, null);

		popup = new PopupWindow(settings, 250, 280);

		initViews();
		initEvents();

		linearLayout = (LinearLayout) findViewById(R.id.llayout);
		mViewCount = curPage.getChildCount();
		mImageViews = new ImageView[linearLayout.getChildCount()];
		
		for (int i = 0; i < mViewCount; i++) {
			mImageViews[i] = (ImageView) linearLayout.getChildAt(i);			
			mImageViews[i].setEnabled(true);
			mImageViews[i].setOnClickListener(this);
			mImageViews[i].setTag(i);
		} 

		for(int i = mViewCount ; i < linearLayout.getChildCount(); i++){
			mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
			mImageViews[i].setVisibility(8);
		}
		
		mCurSel = 0;
		mImageViews[mCurSel].setEnabled(false);
		mImageViews[mCurSel].setImageDrawable(getResources().getDrawable(
				R.drawable.current));
	}

	public void initlist() {
		/*
		 * ListView localListView = (ListView) findViewById(R.id.listviewgroup);
		 * localListView.setAdapter(new DeskareaAdapter(this)); localListView
		 * .setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick( AdapterView<?>
		 * paramAnonymousAdapterView, View paramAnonymousView, int
		 * paramAnonymousInt, long paramAnonymousLong) {
		 * itemBackChanged(paramAnonymousView); Constant.Area = ((ListView)
		 * paramAnonymousAdapterView) .getItemAtPosition(paramAnonymousInt)
		 * .toString(); initViews(); } });
		 */
	}

	/*
	 * private void itemBackChanged(View paramView) { ((TextView)
	 * paramView.findViewById(R.id.content_group)) .setTextColor(-1); if
	 * (itemView == null) itemView = paramView; if (itemView == paramView)
	 * return; ((TextView) itemView.findViewById(R.id.content_group))
	 * .setTextColor(-16777216); itemView = paramView; }
	 */

	public void initViews() {

		iv = (ImageView) findViewById(R.id.title_bar_menu_btn);
		menu_setting = (ImageView) findViewById(R.id.menu_setting);

		clock_out = (TextView) settings.findViewById(R.id.clock_out);
		drops = (TextView) settings.findViewById(R.id.drops);
		pay_out = (TextView) settings.findViewById(R.id.pay_out);
		personal_report = (TextView) settings
				.findViewById(R.id.personal_report);

		lstDate = new sql_desk(this).queryMenus(Constant.Area);
		PageCount = (int) Math.ceil(lstDate.size() / PAGE_SIZE);
		if (localGridView != null) {
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
		localAdapter.open();
		localAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		handler.post(task);// 立即调用刷新代码
		initViews();
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
			initViews();
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
	protected void initEvents() {
		iv.setOnClickListener(this);
		menu_setting.setOnClickListener(this);
		clock_out.setOnClickListener(this);
		drops.setOnClickListener(this);
		pay_out.setOnClickListener(this);
		personal_report.setOnClickListener(this);

		curPage.setPageListener(new MyScrollLayout.PageListener() {
			@Override
			public void page(int page) {
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
			iv.setBackgroundColor(Color.parseColor("#65696A"));
			if (getSlideMenu().isMainScreenShowing()) {
				getSlideMenu().openMenu();
			} else {
				getSlideMenu().closeMenu();
				iv.setBackgroundColor(Color.parseColor("#00FFFFFF"));
				if (popup.isShowing()) {
					popup.dismiss();
				}
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
		case R.id.clock_out:
			break;
		}
	}

}
