package com.utopia.Adapter;

import java.util.ArrayList;
import java.util.List;
import com.utopia.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySpinnerAdapter extends BaseAdapter {

	private Context myContext = null;
	  List<String> myList = new ArrayList();
	  int myScore = 0;

	  public MySpinnerAdapter(Context context, List objects, int score) {
		   myContext = context;
		   myList = objects;
		   myScore = score;
		  }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();

	}

	@Override
	public Object getItem(int position) {
		return myList.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(myContext);
		   if (convertView == null) {
			   convertView = inflater.inflate(R.layout.choosespinner, null);
		   }
		   ImageView iv = (ImageView) convertView
		     .findViewById(R.id.spinner_pic);
		   TextView tv = (TextView) convertView
		     .findViewById(R.id.spinner_tvGift);
		   

		    //获取到控件iv和tv后可对控件进行操作
		   tv.setText(myList.get((int) getItemId(position)));
		    //......

		   return convertView;
		  }


	}


