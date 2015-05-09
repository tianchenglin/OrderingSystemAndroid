package com.utopia.Dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.utopia.Adapter.DeskgridAdapter;
import com.utopia.Adapter.DesklistareaAdapter;
import com.utopia.activity.R;
import com.utopia.activity.OrderFormActivity;
import com.utopia.utils.Constant;

public class pop_menu_select_desk implements View.OnClickListener {
	AdapterView.OnItemClickListener ItemClick = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
				View paramAnonymousView, int paramAnonymousInt,
				long paramAnonymousLong) {
			((DeskgridAdapter) pop_menu_select_desk.this.listdesk.getAdapter())
					.execsql(((ListView) paramAnonymousAdapterView)
							.getItemAtPosition(paramAnonymousInt).toString()
							.trim());
		}
	};
	private Context context;
	AdapterView.OnItemClickListener deskItemClick = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
				View paramAnonymousView, int paramAnonymousInt,
				long paramAnonymousLong) {
			pop_menu_select_desk.this.mtableid
					.setText(((GridView) paramAnonymousAdapterView)
							.getItemAtPosition(paramAnonymousInt).toString()
							.trim());
			Constant.table_id = (String) mtableid.getText();
		}
	};
	private GridView listdesk;
	private ListView listtype;
	private TextView mtableid;
	private TextView mtableid2;
	private TextView menu_sum_no;
	PopupWindow popupWindow;
	private String state;
	
	public pop_menu_select_desk(Context paramContext, View paramView , String state) {
		this.state = state;
		this.context = paramContext;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_seldesk, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, Gravity.CENTER, 0, 0);
		this.mtableid2 = ((TextView) paramView);
		this.menu_sum_no = ((TextView) paramView);
		this.mtableid = ((TextView) localView.findViewById(R.id.mtable_id));
		this.mtableid.setText("");
		this.listtype = ((ListView) localView.findViewById(R.id.list_type));
		this.listdesk = ((GridView) localView.findViewById(R.id.Grid_desk));
		((Button) localView.findViewById(R.id.loginBtn))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginCancleBtn))
				.setOnClickListener(this);
		initlisttype();
		initgriddesk();
	}
	
	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	public void initgriddesk() {
		DeskgridAdapter localdeskgridAdapter = new DeskgridAdapter(this.context,state);
		this.listdesk.setAdapter(localdeskgridAdapter);
		this.listdesk.setOnItemClickListener(this.deskItemClick);
	}

	public void initlisttype() {
		DesklistareaAdapter localdesklistareaAdapter = new DesklistareaAdapter(
				this.context);
		this.listtype.setAdapter(localdesklistareaAdapter);
		this.listtype.setOnItemClickListener(this.ItemClick);
	}

	@Override
	public void onClick(View paramView) {
		if (paramView.getId() == R.id.loginBtn) {
			this.mtableid2.setText(this.mtableid.getText());
			Constant.table_id = (String) this.mtableid.getText();
			//mMyordersActivity.sladapter.open1();
			//mMyordersActivity.sladapter.notifyDataSetChanged();
			//((OrderFormActivity)context).Refresh();
			closePop();
		}
		if (paramView.getId() == R.id.loginCancleBtn)
			closePop();
	}
}