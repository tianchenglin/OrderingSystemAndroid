package com.utopia.Dialog;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Adapter.DesklistAdapter;
import com.utopia.Adapter.DesklistareaAdapter;
import com.utopia.activity.R;
import com.utopia.activity.OrderFormActivity;
import com.utopia.activity.R.id;
import com.utopia.activity.R.layout;
import com.utopia.utils.InitSql;

public class pop_desk implements View.OnClickListener {
	AdapterView.OnItemClickListener ItemClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
				View paramAnonymousView, int paramAnonymousInt,
				long paramAnonymousLong) {
			((DesklistAdapter) pop_desk.this.listdesk.getAdapter())
					.execsql(((ListView) paramAnonymousAdapterView)
							.getItemAtPosition(paramAnonymousInt).toString()
							.trim());
		}
	};
	private Context context;
	AdapterView.OnItemClickListener deskItemClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
				View paramAnonymousView, int paramAnonymousInt,
				long paramAnonymousLong) {
			pop_desk.this.mtableid
					.setText(((ListView) paramAnonymousAdapterView)
							.getItemAtPosition(paramAnonymousInt).toString()
							.trim());
		}
	};
	private ProgressDialog dialog;
	private View itemView;
	private String key_name;
	private ListView listdesk;
	private ListView listtype;
	private Handler mhandler;
	private TextView mtableid;
	PopupWindow popupWindow;

	@SuppressLint("NewApi")
	public pop_desk(Context paramContext, View paramView) {
		this.context = paramContext;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_desk, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		this.mtableid = ((TextView) localView.findViewById(R.id.mtable_id));
		this.mtableid.setText("");
		this.listtype = ((ListView) localView.findViewById(R.id.list_type));
		this.listdesk = ((ListView) localView.findViewById(R.id.list_desk));
		initlisttype();
		initlistdesk();
		((Button) localView.findViewById(R.id.loginBtn))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginCancleBtn))
				.setOnClickListener(this);
		sethandler();
	}

	private void itemBackChanged(View paramView) {
		((TextView) paramView.findViewById(R.id.content_group))
				.setTextColor(-1);
		if (this.itemView == null)
			this.itemView = paramView;
		if (this.itemView == paramView)
			return;
		((TextView) this.itemView.findViewById(R.id.content_group))
				.setTextColor(-16777216);
		this.itemView = paramView;
	}

	private void sethandler() {
		this.mhandler = new Handler() {
			public void handleMessage(Message paramAnonymousMessage) {
				switch (paramAnonymousMessage.what) {
				default:
					return;
				case 0:
					String str4 = pop_desk.this.key_name + "��̨�������ϴ����. ";
					Toast.makeText(pop_desk.this.context, str4, 1000).show();
					pop_desk.this.dialog.dismiss();
					((OrderFormActivity) pop_desk.this.context).Refresh_send();
					return;
				case 1:
					String str3 = pop_desk.this.key_name + "��̨�������ϴ�ʧ��. ";
					Toast.makeText(pop_desk.this.context, str3, 1000).show();
					pop_desk.this.dialog.dismiss();
					((OrderFormActivity) pop_desk.this.context).Refresh_send();
				case 2:
					String str2 = pop_desk.this.key_name
							+ "�����ǵ㵥״̬�����ܷ���. ";
					Toast.makeText(pop_desk.this.context, str2, 1000).show();
					pop_desk.this.dialog.dismiss();
				case 3:
				}
				String str1 = pop_desk.this.key_name
						+ "������������ʹ�ã����ܷ���";
				Toast.makeText(pop_desk.this.context, str1, 1000).show();
				pop_desk.this.dialog.dismiss();
			}
		};
	}

	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	public void initlistdesk() {
		DesklistAdapter localdesklistAdapter = new DesklistAdapter(this.context);
		this.listdesk.setAdapter(localdesklistAdapter);
		this.listdesk.setOnItemClickListener(this.deskItemClick);
	}

	public void initlisttype() {
		DesklistareaAdapter localdesklistareaAdapter = new DesklistareaAdapter(
				this.context);
		this.listtype.setAdapter(localdesklistareaAdapter);
		this.listtype.setOnItemClickListener(this.ItemClick);
	}

	public void onClick(View paramView) {
		if (paramView.getId() == R.id.loginBtn) {
			this.key_name = this.mtableid.getText().toString().trim();
			if (this.key_name.equals(""))
				Toast.makeText(this.context, "ѡ����̨", 1000).show();
		} else {
			closePop();
		}
		closePop();
	}
}