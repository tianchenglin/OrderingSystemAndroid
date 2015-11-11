package com.utopia.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.utopia.Model.d_Product;
import com.utopia.activity.OrderMenuDetialActivity;
import com.utopia.activity.R;

public class DateAdapter extends BaseAdapter {

	private Context context;
	/** 列表. */
	private List<d_Product> lstDate;
	public static final int SIZE = 27;

	private String md5;

	public DateAdapter(Context mContext, List<d_Product> list, int page,
			TextView textview_menuDesc, String md5) {
		this.md5 = md5;
		this.context = mContext;
		lstDate = new ArrayList<d_Product>();
		int i = page * SIZE;
		int iEnd = i + SIZE;
		while ((i < list.size()) && (i < iEnd)) {
			lstDate.add(list.get(i));
			i++;
		}
	}

	@Override
	public int getCount() {
		return lstDate.size();

	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 实例化图片视图
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_menu, null);
		}
		/** 菜品图片. */
		final ImageView imageView;
		final TextView menuTitle;
		// 获取d_Product
		final d_Product menu = lstDate.get(position);

		// 实例化控件
		menuTitle = (TextView) convertView.findViewById(R.id.menu_title);
		imageView = (ImageView) convertView.findViewById(R.id.menu_bg);
		//imageView.setBackgroundResource(R.drawable.a);
		imageView.setTag(menu);
		// 显示菜品描叙
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						// 松开事件发生后执行代码的区域
						Intent intent = new Intent(context,
								OrderMenuDetialActivity.class);
						Bundle mBundle = new Bundle();
						mBundle.putSerializable("d_Product",
								(d_Product) v.getTag());
						mBundle.putString("md5", md5);
						intent.putExtras(mBundle);
						context.startActivity(intent);

					}
				return false;
			}
		});
		// 为TextView设置菜名
		menuTitle.setText(menu.getPdtName());

		// 给下单按钮绑定单击事件
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						OrderMenuDetialActivity.class);
				context.startActivity(intent);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
}