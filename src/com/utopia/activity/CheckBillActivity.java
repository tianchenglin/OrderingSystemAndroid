package com.utopia.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.utopia.Adapter.CheckBillAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.ExitApplication;

public class CheckBillActivity extends BaseActivity implements
		View.OnClickListener {
	CheckBillAdapter cbadapter;
	private HomeKeyLocker mHomeKeyLocker;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.checkbill);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(CheckBillActivity.this);
		
		initViews();
		initViews();
	}
	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}
	public void initlist() {
		ListView localListView = (ListView) findViewById(R.id.mCheckList_list);
		this.cbadapter = new CheckBillAdapter(this);
		localListView.setAdapter(this.cbadapter);
	}

	public void onClick(View paramView) {
	}

	protected void onResume() {
		super.onResume();
		initlist();
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}
}