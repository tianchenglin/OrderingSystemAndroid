package com.utopia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView; 
import com.utopia.Adapter.OrdersmgAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;

public class StatisticActivity extends BaseActivity{
	private GridView localGridView = null;
	private HomeKeyLocker mHomeKeyLocker;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.ordersmg);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(StatisticActivity.this);
		
		initViews();
		initEvents();
	}
	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}
	@Override
	protected void initViews() {
		localGridView = (GridView) findViewById(R.id.starting_grid);
		localGridView.setAdapter(new OrdersmgAdapter(this));
	}

	@Override
	protected void initEvents() {
		localGridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong) {
						switch (paramAnonymousInt) {
						default:
							return;
						case 0:
							startActivity(new Intent(StatisticActivity.this,
									PersonalDailyReportActivity.class));
							break;
						case 1:
							startActivity(new Intent(StatisticActivity.this,
									DropsActivity.class));
							break;
						case 2:
							startActivity(new Intent(StatisticActivity.this,
									PayOutActivity.class));
							break;
						case 3:
							startActivity(new Intent(StatisticActivity.this,
									PurchaseActivity.class));
							break;
						case 4:
							if(Constant.currentStaff.getPriority() == 0 || Constant.currentStaff.getPriority() == 1){
								startActivity(new Intent(StatisticActivity.this,
										ShiftReportActivity.class));
							}else{
								showCustomToast("Don't have priority !");
							}
								
							
							break;
						case 5:
							startActivity(new Intent(StatisticActivity.this,
									CloseReportActivity.class));
							break;
						}
					}
				});
	}
}