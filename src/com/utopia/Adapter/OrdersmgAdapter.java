package com.utopia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView; 
import com.utopia.activity.R;
import com.utopia.utils.Constant;

public class OrdersmgAdapter extends BaseAdapter implements
		View.OnClickListener {
	int[] butbgs = { R.drawable.pdr_bg, R.drawable.drops_bg, R.drawable.payout_bg,R.drawable.purchase_bg,
			R.drawable.sr_bg, R.drawable.cs_bg };
	int[] buttxts = { R.string.pdr, R.string.drops, R.string.payout,
			R.string.purchase, R.string.sr, R.string.cs };
	private Context context;

	public OrdersmgAdapter(Context paramContext) {
		this.context = paramContext;
	}

	public int getCount() {
		return this.butbgs.length;
	}

	public Object getItem(int paramInt) {
		return Integer.valueOf(this.butbgs[paramInt]);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		if (paramView == null) {
			View localView = LayoutInflater.from(this.context).inflate(
					R.layout.ordersmg_item, null);
			MyView localMyView2 = new MyView();
			localMyView2.imageView = ((ImageView) localView
					.findViewById(R.id.mlist_itemImage));
			localMyView2.textView = ((TextView) localView
					.findViewById(R.id.mlist_itemText));
			localView.setTag(localMyView2);
			paramView = localView;
		}
		MyView localMyView1 = (MyView) paramView.getTag();
		localMyView1.textView.setText(this.context
				.getString(this.buttxts[paramInt]));
		localMyView1.imageView.setBackgroundDrawable(Constant
				.readBitMapDrawable(this.context, this.butbgs[paramInt]));
		return paramView;
	}

	public void onClick(View paramView) {
	}

	class MyView {
		ImageView imageView;
		TextView textView;

		MyView() {
		}
	}
}