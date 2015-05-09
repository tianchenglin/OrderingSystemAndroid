package com.utopia.Service;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
 

import com.utopia.activity.DeskMenuActivity;
import com.utopia.activity.MenusAcitvity;
import com.utopia.activity.R;
import com.utopia.activity.OrderFormActivity;
import com.utopia.activity.StatisticActivity;

public class ActivityViewManager {
	//底部按钮对应的四个类
	Class[] ActivityClasses = { DeskMenuActivity.class, MenusAcitvity.class,
			OrderFormActivity.class, StatisticActivity.class};
	//底部按钮对应id
	private int[] butIds = { R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4};
	
	//点击之后的ImageButton ， 为了显示不同背景
	private ImageButton[] chooseBtns;
	//ImageButton的id
	private int[] chooseIds = { R.id.choose1, R.id.choose2, R.id.choose3,
			R.id.choose4};
	private Context context;
	private int index = -1;
	private RelativeLayout mViewContainer;
	private Handler mhandler; 

	public ActivityViewManager(Context paramContext) {
		this.context = paramContext;
		this.mViewContainer = ((RelativeLayout) ((Activity) paramContext)
				.findViewById(R.id.container_activity));		//主界面中间部位 ， mainmg.xml
		this.mViewContainer.removeAllViews();					//移除子view的功能
		
		ImageButton[] arrayOfImageButton = new ImageButton[this.chooseIds.length];
		for (int i = 0;; i++) {
			if (i >= this.chooseIds.length) {
				this.chooseBtns = arrayOfImageButton;
				sethandler();
				return;
			}
			arrayOfImageButton[i] = ((ImageButton) ((Activity) paramContext)
					.findViewById(this.chooseIds[i]));
			((LinearLayout) ((Activity) paramContext)
					.findViewById(this.butIds[i]))
					.setOnClickListener((View.OnClickListener) paramContext);
		}
	}

	private void sethandler() {
		this.mhandler = new Handler() {
			public void handleMessage(Message paramAnonymousMessage) {
				ActivityViewManager.this.switchViews();
			}
		};
	}

	public int getCurBtnindex() {
		return this.index;
	}

	public void setBtnindex(int paramInt) {
		if (paramInt != this.index) {
			if (paramInt < 0)
				paramInt = 0;
			if (paramInt >= this.chooseBtns.length)
				paramInt = -1 + this.chooseBtns.length;
			if ((this.index > -1) && (this.index < this.chooseBtns.length))
				this.chooseBtns[this.index].setBackgroundDrawable(null);
		}
		this.chooseBtns[paramInt].setBackgroundResource(R.drawable.choose);
		this.index = paramInt;
		com.utopia.utils.Constant.mainmgindex = paramInt;
		new Thread() {
			public void run() {
				ActivityViewManager.this.mhandler.sendEmptyMessage(0);
			}
		}.start();
	}

	public int setCurBtnPos(int paramInt) {
		int[] arrayOfInt = this.butIds;
		int i = -1;
		for (int j = 0;j < this.butIds.length; j++) {
			if (paramInt == arrayOfInt[j])
				 i = j;
		}
		setBtnindex(i);
		return i;
	}

	public void switchViews() {
		this.mViewContainer.removeAllViews();
		Intent localIntent = new Intent(this.context,
				this.ActivityClasses[this.index]).addFlags(67108864);
		View localView = ((ActivityGroup) this.context)
				.getLocalActivityManager()
				.startActivity(
						this.ActivityClasses[this.index].getSimpleName()
								.toString(), localIntent).getDecorView();
		this.mViewContainer.addView(localView);
	}
}