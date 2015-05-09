package com.utopia.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.utopia.Dao.sql_OtherSpec;
import com.utopia.activity.R;

public class SpecAdapter extends BaseAdapter {
	private Context context;
	public boolean mBusy = false;
	public ArrayList mlist;

	public SpecAdapter(Context paramContext) {
		this.context = paramContext;
		sqlexec(1);
	}

	@Override
	public int getCount() {
		return this.mlist.size();
	}

	@Override
	public Object getItem(int paramInt) {
		return Integer.valueOf(paramInt);
	}

	@Override
	public long getItemId(int paramInt) {
		return paramInt;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		/*TextView localTextView;
		if (paramView == null) {
			localTextView = new TextView(this.context);
			localTextView.setBackgroundResource(R.drawable.addfood_buth);
			localTextView.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
			localTextView.setGravity(17);
			localTextView.setTextColor(this.context.getResources().getColor(
					R.color.white));
		} else {
			localTextView = (TextView) paramView;
		}
		localTextView.setText(this.mlist.get(paramInt).toString().trim());
		return localTextView;*/
		
		 ViewHolder holder = null;  
		 if (paramView == null) {//convertView 存放item的  
             holder=new ViewHolder();              
             paramView = LayoutInflater.from(context).inflate(R.layout.specadapter, null);  
             holder.title = (TextView)paramView.findViewById(R.id.title2);  
             holder.viewBtn = (RadioButton)paramView.findViewById(R.id.listview2_radiobutton);  
             paramView.setTag(holder);                 
         }else {                 
             holder = (ViewHolder)paramView.getTag();  
         }  
           
           
         holder.title.setText(this.mlist.get(paramInt).toString().trim());  
            
         //让子控件button失去焦点  这样不会覆盖掉item的焦点  否则点击item  不会触发响应即onItemClick失效  
         holder.viewBtn.setFocusable(false);//无此句点击item无响应的  
         holder.viewBtn.setId(paramInt);     
         holder.viewBtn.setChecked(true);    
           
        /* holder.viewBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {    
             @Override    
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    
                 if(isChecked){    
                     //set pre radio button    
                     if(checkedIndex != -1)    
                     {    
                         int childId = checkedIndex - listView.getFirstVisiblePosition();    
                         if(childId >= 0){    
                             View item = listView.getChildAt(childId);    
                             if(item != null){    
                                 RadioButton rb = (RadioButton)item.findViewById(checkedIndex);    
                                 if(rb != null)    
                                 rb.setChecked(false);    
                             }    
                         }      
                     }    
                     //set cur radio button    
                     checkedIndex = buttonView.getId();    
                 }    
             }    
         });  */  
         return paramView;  
     }  
       
	
  
	  
	public void sqlexec(int paramInt) {
		mlist = new sql_OtherSpec()
				.recordlist2("select OtherSpecName from OtherSpec where OtherSpecTypeID='"
						+ paramInt + "' order by OtherSpecNo");
		notifyDataSetChanged();
	}
	
	 public final class ViewHolder{  
	        public TextView title;  
	        public RadioButton viewBtn;  
	    }
}