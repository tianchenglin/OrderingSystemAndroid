package com.utopia.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.utopia.Base.BaseActivity;
import com.utopia.utils.ExitApplication;

public class InternetBroadcastReceiver extends BaseActivity{

	private IntentFilter intentFilter;
	private NetworkChangeReceiver networkChangeReceiver;
	private boolean connection_tag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		intentFilter=new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver=new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver,intentFilter);
		
	}
	public  boolean isStart(){
		return connection_tag;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(networkChangeReceiver);
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}
	public class NetworkChangeReceiver extends BroadcastReceiver{

		
		//public static boolean connection_tag=false;//网络连接的标志，连接为真
		@Override
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager connectionManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
			NetworkInfo networkInfo=connectionManager.getActiveNetworkInfo();
			if(networkInfo!=null && networkInfo.isAvailable()){
				InternetBroadcastReceiver.this.finish();
				connection_tag=true;
				Log.i("tag","有网络。。。。。");
			}else{
				AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
				dialogBuilder.setTitle("Warning");
				dialogBuilder.setMessage("network is unavailable");
				dialogBuilder.setCancelable(false);
				dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
						ExitApplication.getInstance().exit();
						//InternetBroadcastReceiver.this.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)); 
						InternetBroadcastReceiver.this.finish();
					}
				});
				AlertDialog alertDialog=dialogBuilder.create();
				alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				alertDialog.show();
				connection_tag=false;
			}
		}

	}

}
