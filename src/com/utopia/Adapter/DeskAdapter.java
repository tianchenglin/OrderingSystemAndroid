package com.utopia.Adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List; 
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView; 
import com.utopia.Dao.sql_Product;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Dao.sql_desk;
import com.utopia.Dialog.pop_open;
import com.utopia.Model.d_Desk;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.DeskMenuActivity;
import com.utopia.activity.OrdersAcitvity;
import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.JsonResolveUtils;
import com.utopia.utils.Md5;
import com.utopia.utils.Snippet;
import com.utopia.widget.MyBadgeView;

public class DeskAdapter extends BaseAdapter implements View.OnClickListener {
	private Context context;
	public boolean mBusy = false;
	public Cursor m_CallCursor, m_CallCursor2;
	private String m_sql;
	private int prerow = 0, precol = 0, status = 0;
	List<d_SaleRecord> saleRecords = new ArrayList<d_SaleRecord>();
	List<d_Desk> lstDate = new ArrayList<d_Desk>();
	private String md5 = Md5.md5(Snippet.generateID());
	public static final int SIZE = 35;
	private int page = 0 ;
	public DeskAdapter(Context paramContext) {
		this.context = paramContext;
		execsql(Constant.Area);
	}

	public DeskAdapter(Context mContext, List<d_Desk> list, int page) {
		this.context = mContext;
		lstDate = new ArrayList<d_Desk>();
		this.page = page;
			
		int i = 0;
		int iEnd = 0;
		int count = 0;
		
		if(page == 0){
			i = 0;
		}else{
			for(int j = 0 ; j < list.size() ; j++){
				if(list.get(j).getRow() <= ((page-1)*5+4) && list.get(j).getCol()<=7 ){
					count++;
				}
			}
			i = count;
		}
		count=0;
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).getRow() <= (page * 5 + 4)
					&& list.get(j).getCol() <= 7) {
				count++;
			}
		}
		iEnd = count++;

		while ((i < list.size()) && (i < iEnd)) {
			lstDate.add(list.get(i));
			i++;
		}
		
		if(page==0){
			prerow = 0;
		}
		else{
			prerow=page*4+page;
		}
	}

	public void closedb() {
		m_CallCursor.close();
	}

	public void execsql(String paramString) {
		m_sql = ("select d.id,d.type_id,d.state,d.s_account,d.desk_name,d.statetime,d.starttime,d.people_num,d.row,d.col,d.message from desk as d JOIN Area as a ON trim(a.AreaId) = trim(d.type_id) where a.AreaName='"
				+ paramString + "' order by d.row,d.col");
		m_CallCursor = new sql_desk().recordlist(m_sql);
		prerow = precol = status = 0;
		notifyDataSetChanged();
	}

	public int getCount() {
		/*
		 * if (this.m_CallCursor != null && m_CallCursor.getCount() > 0) {
		 * m_CallCursor.moveToPosition(m_CallCursor.getCount() - 1); return
		 * m_CallCursor.getInt(8) * 7 + m_CallCursor.getInt(9) - 1; } return 0;
		 */
		return lstDate.get(lstDate.size()-1).getRow()*7 + lstDate.get(lstDate.size()-1).getCol();

	}

	public Object getItem(int position) {/*
										 * return Integer.valueOf(position);
										 */
		return lstDate.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		AppItem localAppItem2 = new AppItem();
		if (paramView == null) {
			paramView = LayoutInflater.from(this.context).inflate(
					R.layout.desk_ltem, null);

			localAppItem2.waiter_name = ((Button) paramView
					.findViewById(R.id.waiter_name));
			localAppItem2.desk_money = ((TextView) paramView
					.findViewById(R.id.desk_money));
			localAppItem2.desk_state = ((TextView) paramView
					.findViewById(R.id.desk_state));
			localAppItem2.desk_time = ((TextView) paramView
					.findViewById(R.id.desk_time));
			localAppItem2.ll = (LinearLayout) paramView
					.findViewById(R.id.desk_content);
			paramView.setTag(localAppItem2);
		} else {
			try {
				localAppItem2 = (AppItem) paramView.getTag();
			} catch (Exception e) {
				return paramView;
			}
		}
		if (lstDate.size()  == status) {

			return paramView;
		}
		//lstDate.get(paramInt);
		// m_CallCursor.moveToPosition(status);
		d_Desk locald_keycode = new d_Desk();
		locald_keycode = lstDate.get(status);
		/*
		 * locald_keycode.setId(lstDate.get(paramInt).getId());
		 * locald_keycode.setType_id(lstDate.get(paramInt).getType_id());
		 * locald_keycode.setState(lstDate.get(paramInt).getState());
		 * locald_keycode.setS_account(lstDate.get(paramInt).getS_account());
		 * locald_keycode.setDesk_name(lstDate.get(paramInt).getDesk_name());
		 * locald_keycode.setStatetime(lstDate.get(paramInt).getStatetime());
		 * locald_keycode.setStarttime(lstDate.get(paramInt).getStarttime());
		 * locald_keycode.setPeople_num(lstDate.get(paramInt).getPeople_num());
		 * locald_keycode.setRow(lstDate.get(paramInt).getRow());
		 * locald_keycode.setCol(lstDate.get(paramInt).getCol());
		 * locald_keycode.setDelmark(0);
		 * locald_keycode.setMessage(lstDate.get(paramInt).getMessage());
		 */
		
		
		
		if (precol < 7) {
			precol++;
		} else {
			precol = 1;
			prerow++;
		}
		localAppItem2.ll.setTag(locald_keycode);

		if (locald_keycode.getRow() * 7 + locald_keycode.getCol() != prerow * 7
				+ precol ) {
			return paramView;
		} else {
			++status;
			m_sql = "select Price,number,status from SaleRecord where status!='Finish' and desk_name='"
					+ locald_keycode.getDesk_name() + "'";
			m_CallCursor2 = new sql_Product().recordlist3(m_sql);
		}

		double money = 0.0;
		int flag = 0;
		while (m_CallCursor2.moveToNext()) {
			locald_keycode.setState("Ordered");
			flag++;
			money += m_CallCursor2.getDouble(m_CallCursor2
					.getColumnIndex("Price"))
					* m_CallCursor2.getInt(m_CallCursor2
							.getColumnIndex("number"));
		}

		m_sql = "select * from SaleRecord where status=='Doned' and desk_name='"
				+ locald_keycode.getDesk_name() + "'";
		m_CallCursor2 = new sql_Product().recordlist3(m_sql);

		if (flag == m_CallCursor2.getCount() && flag != 0) {
			locald_keycode.setState("Delivered");
		}
		m_CallCursor2.close();
		localAppItem2.ll.setOnClickListener(this);
		localAppItem2.waiter_name.setText(locald_keycode.getS_account());
		localAppItem2.waiter_name.setTextSize(15);
		((LinearLayout) paramView.findViewById(R.id.desk_content))
				.setBackgroundResource(R.drawable.desk_bg);

		if (locald_keycode.getState().equals("EMPTY")) {
			localAppItem2.waiter_name.setText(locald_keycode.getS_account());
			localAppItem2.waiter_name.setTextSize(15);
			localAppItem2.desk_state.setText(locald_keycode.getDesk_name());
			localAppItem2.desk_state.setTextSize(17);
		}

		if (!locald_keycode.getState().equals("EMPTY")) {
			BigDecimal bg = new BigDecimal(money);
			localAppItem2.desk_money.setText("$"
					+ bg.setScale(2, BigDecimal.ROUND_HALF_UP));
			localAppItem2.desk_money.setTextSize(15);
			localAppItem2.desk_state.setText(locald_keycode.getState());
			localAppItem2.desk_state.setTextSize(17);
			if (locald_keycode.getStarttime().equals("")) {
				localAppItem2.desk_time.setText(DateUtils.getDateWN());
				localAppItem2.desk_time.setTextSize(15);
			} else {
				localAppItem2.desk_time.setText(locald_keycode.getStarttime());
				localAppItem2.desk_time.setTextSize(15);
			}
			if (Constant.currentStaff.getS_account().equals(
					locald_keycode.getS_account())) {
				((LinearLayout) paramView.findViewById(R.id.desk_content))
						.setBackgroundResource(Constant.currentStaff.getColor());
			} else {
				((LinearLayout) paramView.findViewById(R.id.desk_content))
						.setBackgroundResource(R.drawable.desk_bg2);
				localAppItem2.ll.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						((DeskMenuActivity) context)
								.showCustomToast("Sorry, the system automatically denial of service...");
					}
				});
			}
		}
		if (locald_keycode.getMessage() > 0) {
			MyBadgeView badge = new MyBadgeView(context,
					localAppItem2.waiter_name);
			badge.setText(locald_keycode.getMessage() + "");
			badge.setBackgroundResource(Constant.currentStaff.getColor2());
			badge.setBadgePosition(MyBadgeView.POSITION_TOP_RIGHT);
			badge.setTextSize(16);
			badge.toggle(true);
		}
		return paramView;
	}

	public void onClick(View paramView) {
		d_Desk locald_keycode = (d_Desk) paramView.getTag();
		if (paramView.getId() == R.id.desk_content) {
			Constant.table_id = locald_keycode.getDesk_name().toString();
			if (locald_keycode.getState().equals("EMPTY")) {
				new pop_open(this.context, paramView);
			} else {
				if (locald_keycode.getMessage() > 0) {
					updateDone();
				} else {
					Intent intent = new Intent(context, OrdersAcitvity.class);
					Bundle bundle = new Bundle();
					bundle.putString("Md5", md5);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			}
		}
	}

	private sql_SaleRecord ssr;

	private void updateDone() {
		ssr = new sql_SaleRecord();
		new RefreshAsyncTask().execute();
	}

	public void open() {
		try {
			m_CallCursor.close();
		} catch (Exception e) {
		}
		m_CallCursor = new sql_desk().recordlist(this.m_sql);
		notifyDataSetChanged();
	}

	// 构造函数AsyncTask<Params, Progress, Result>参数说明:
	// Params 启动任务执行的输入参数
	// Progress 后台任务执行的进度
	// Result 后台计算结果的类型
	private class RefreshAsyncTask extends AsyncTask<String, Integer, String> {
		// onPreExecute()方法用于在执行异步任务前,主线程做一些准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// doInBackground()方法用于在执行异步任务,不可以更改主线程中UI
		@Override
		protected String doInBackground(String... params) {
			System.out.println("调用doInBackground()方法--->开始执行异步任务");
			saleRecords = new JsonResolveUtils(context)
					.getSaleRecordDone(Constant.table_id);
			return null;
		}

		// onPostExecute()方法用于异步任务执行完成后,在主线程中执行的操作
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("调用onPostExecute()方法--->异步任务执行完毕");
			for (int i = 0; i < saleRecords.size(); i++) {
				ssr.updateDone(saleRecords.get(i));
				System.out.println(saleRecords.get(i).toString());
			}
			Intent intent = new Intent(context, OrdersAcitvity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Md5", md5);
			intent.putExtras(bundle);
			context.startActivity(intent);
		}

		// onCancelled()方法用于异步任务被取消时,在主线程中执行相关的操作
		@Override
		protected void onCancelled() {
			super.onCancelled();
			System.out.println("调用onCancelled()方法--->异步任务被取消");
		}
	}

	class AppItem {
		LinearLayout ll;
		Button waiter_name;
		TextView desk_money;
		TextView desk_state;
		TextView desk_time;
	}
}