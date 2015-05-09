package com.utopia.Adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.utopia.Model.d_Tax;
import com.utopia.activity.R;

public class GroupAdapter extends BaseAdapter {
	private Context context;
	private List<d_Tax> taxs = null;

	public GroupAdapter(Context context, List<d_Tax> taxs) {
		 
        this.context = context;
        this.taxs = taxs;
 
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return taxs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return taxs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position	, View v, ViewGroup viewGroup) {
		ViewHolder holder;
        if (v==null) {
            v=LayoutInflater.from(context).inflate(R.layout.group_item_view, null);
            holder=new ViewHolder();
             
            v.setTag(holder);
             
            holder.groupItem=(TextView) v.findViewById(R.id.groupItem);
             
        }
        else{
            holder=(ViewHolder) v.getTag();
        }
        holder.groupItem.setTextColor(Color.WHITE);
        holder.groupItem.setText(taxs.get(position).getTax_value()+"");
         
        return v;
	}
	
	static class ViewHolder {
        TextView groupItem;
    }

}
