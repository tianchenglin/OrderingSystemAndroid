package com.utopia.baseadapter.utils;

import java.util.List; 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter; 

public abstract  class CommomAdapter<T> extends BaseAdapter {

	protected Context mContext ; 
	public List<T> mDatas;
	protected LayoutInflater mInflater ; 
	private int layoutId;
	public CommomAdapter(Context context, List<T> datas , int layoutId){
		this.mContext = context;
		this.mDatas = datas;
		mInflater = LayoutInflater.from(context);
		this.layoutId = layoutId;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public  View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
				layoutId, position);
		
		convert(holder , getItem(position));
		
		return holder.getmConvertView();
	}
	
	public abstract void convert(ViewHolder holder , T t);

}
