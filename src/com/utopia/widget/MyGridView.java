package com.utopia.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

public class MyGridView extends GridView {
	private float mLastMotionX;
	private OnPageChangeListener onPageChangeListener;

	public MyGridView(Context paramContext) {
		super(paramContext);
	}

	public MyGridView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MyGridView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		int i = paramMotionEvent.getAction();
		float f = paramMotionEvent.getX();
		paramMotionEvent.getY();
		switch (i) {
		case 2:
			this.mLastMotionX = f;
			break;
		default:
			if (this.mLastMotionX > f) {
				if (this.onPageChangeListener != null)
					this.onPageChangeListener.onpage(true);
				Log.e("abc", "event : left<");
			}
			if (this.mLastMotionX < f) {
				if (this.onPageChangeListener != null)
					this.onPageChangeListener.onpage(false);
			}
		}
		return super.onTouchEvent(paramMotionEvent);
	}

	public void setOnPageChangeListener(
			OnPageChangeListener paramOnPageChangeListener) {
		this.onPageChangeListener = paramOnPageChangeListener;
	}

	public static abstract interface OnPageChangeListener {
		public abstract void onpage(boolean paramBoolean);
	}
}

/*
 * Location: D:\android-reverse-trinea\classes_dex2jar.jar Qualified Name:
 * com.etf.food.MyGridView JD-Core Version: 0.6.2
 */