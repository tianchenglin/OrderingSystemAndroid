package com.utopia.Dialog;

import java.text.DecimalFormat;

import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;

import android.R.string;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class TimeDialog {
	private PopupWindow popupWindow;
	private Context context;
	private TimePicker timePicker;
    private TextView sendtime_tv;
    private Button schedule_btn;
    private Button sure_btn;
    private boolean change; //标志变量，看时间有没有人为更改
    DecimalFormat decimalFormat1 = new DecimalFormat("00");// 构造方法的字符格式
	public TimeDialog(Context context,final TextView sendtime_tv,Button schedule_btn){
		super();
		change=false;
		this.context=context;
		this.sendtime_tv=sendtime_tv;
		this.schedule_btn=schedule_btn;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(context).inflate(
				R.layout.time_layout, null);
		this.popupWindow = new PopupWindow(localView, 150, 250);//设置窗体的大小
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
	    this.popupWindow.showAsDropDown(schedule_btn,0,0);//设置弹出窗体的位置（相对于payment_btn控件）
	    sure_btn=(Button) localView.findViewById(R.id.sure_btn);
	    timePicker=(TimePicker)localView.findViewById(R.id.timePicker1);
	    timePicker.setIs24HourView(true);
	    timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){

			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				// TODO Auto-generated method stub
				String date=DateUtils.getDateWN();
				date=date.substring(9,date.length()-1);
				int current_hour=DateUtils.getHour();
				int current_minute=DateUtils.getminute();
				if(hour<current_hour){
						hour=current_hour;
						minute=current_minute;
				}else{
					if(hour==current_hour&&minute<current_minute){
						minute=current_minute;
					}
				}
				sendtime_tv.setText(date+", "+decimalFormat1.format(hour)+':'+decimalFormat1.format(minute));
				change=true;
			}
	    	
	    });
	    sure_btn.setOnClickListener(new OnClickListener(){
	    	
	    	@Override
	    	public void onClick(View v){
	    		if(change==false)
	    		{
	    			String date=DateUtils.getDateWN();
					date=date.substring(9,date.length()-1);
	    			sendtime_tv.setText(date+", "+decimalFormat1.format(timePicker.getCurrentHour())
	    			   +":"+decimalFormat1.format(timePicker.getCurrentMinute()));
	    		}
	    		closePop();
	    	}
	    });
	}
	public void closePop() {
		if (popupWindow != null){
			  //时间格式：hh:mm mm-dd-yyyy
			String year=sendtime_tv.getText().toString().trim().substring(6,10);
			String mounth=sendtime_tv.getText().toString().trim().substring(0,5);
			String time=sendtime_tv.getText().toString().trim().substring(12,17);
			String date_time=year+'-'+mounth+' '+time+':'+"00";
			Constant.schedule=date_time;
			//Log.i("tag",date_time);
			//Log.i("tag",DateUtils.getDateEN());
			popupWindow.dismiss();
		}
			
	}
	

}
