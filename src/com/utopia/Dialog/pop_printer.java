package com.utopia.Dialog;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.utopia.Adapter.PrinterAdapter;
import com.utopia.Service.BluetoothService;
import com.utopia.activity.R;
import com.utopia.activity.R.id;
import com.utopia.activity.R.layout;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class pop_printer implements View.OnClickListener {
	AdapterView.OnItemClickListener ItemClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
				View paramAnonymousView, int paramAnonymousInt,
				long paramAnonymousLong) {
			Toast.makeText(
					pop_printer.this.context,
					((ListView) paramAnonymousAdapterView)
							.getItemAtPosition(paramAnonymousInt) + "",
					Toast.LENGTH_SHORT).show();
			pop_printer.this.closePop();
		}
	};
	private ListView ListView;
	private Context context;
	PopupWindow popupWindow;

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	public static BluetoothService mService = null;
	public static BluetoothAdapter mBluetoothAdapter = null;
	
	public pop_printer(Context paramContext, View paramView) {
		this.context = paramContext;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_printer, null);
		this.popupWindow = new PopupWindow(localView, 206, 340);
		this.popupWindow.setFocusable(true);
		this.popupWindow.showAsDropDown(paramView, 0, 0);
		this.popupWindow.update();
		this.ListView = ((ListView) localView.findViewById(R.id.pop_call_list));
		initcalllist();
	}

	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	public void initcalllist() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Printer/Cashier1");
		list.add("Printer/Cashier2");
		list.add("Printer/Cashier3");
		list.add("Printer/Cashier4");
		PrinterAdapter localcallAdapter = new PrinterAdapter(this.context, list);
		this.ListView.setAdapter(localcallAdapter);
		this.ListView.setOnItemClickListener(this.ItemClick);
	}

	public void onClick(View paramView) {
	}
}
