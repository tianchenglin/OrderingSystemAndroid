package com.utopia.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.utopia.Dao.sql_desk;
import com.utopia.Model.d_Desk;
import com.utopia.activity.DeskMenuActivity;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.JsonResolveUtils;

public class DeskManager {
	private Context context;
	private d_Desk desk;
	public DeskManager(Context paramContext) {
		context = paramContext;
	}

	public boolean openDesk(d_Desk tdesk){
		this.desk = tdesk;
		new RefreshAsyncTask().execute();
		return true;
	}
	
	public boolean setDesk(d_Desk tdesk){
		this.desk = tdesk;
		new RefreshAsyncTask1().execute();
		return true;
	}
	public boolean cleanDesk() {
		return true;
	}

	
	private class RefreshAsyncTask extends AsyncTask<Void, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			return new JsonResolveUtils(context).addDesks(desk);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				new sql_desk().opendesk(desk);
			}
		}

	}
	
	private class RefreshAsyncTask1 extends AsyncTask<Void, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			return new JsonResolveUtils(context).setDesks(desk);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				new sql_desk().opendesk(desk);
			}
		}

	}
	
	
	private class RefreshAsyncTask2 extends AsyncTask<Void, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			return new JsonResolveUtils(context).setDesks(desk);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				new sql_desk().update("Paid",desk.getDesk_name());
			}
		}

	}
	
	private class RefreshAsyncTask3 extends AsyncTask<Void, Void, Boolean> {

		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			return new JsonResolveUtils(context).deleteDeliverDesks(desk);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				new sql_desk().delete(desk.getDesk_name());
			}
		}

	}
	
	public boolean setDeliveryDeskState(String deskName){
		desk = new d_Desk();
		desk.setDesk_name(deskName);
		new RefreshAsyncTask2().execute();
		return true;
	}

	public void cleanDeliveryDeskState(String deskName) {
		desk = new d_Desk();
		desk.setDesk_name(deskName);
		new RefreshAsyncTask3().execute();
	}
}