package com.utopia.activity;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.utopia.Base.BaseActivity;
import com.utopia.Service.BluetoothService;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;
import com.utopia.utils.JsonResolveUtils;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class DeviceListActivity extends BaseActivity {
	// Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);

		// Set result CANCELED incase the user backs out
		setResult(Activity.RESULT_CANCELED);

		Button next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(DeviceListActivity.this,
						CashierActivity.class));
			}
		});
		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		// Find and set up the ListView for paired devices
		ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
	
		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		// If there are paired devices, add each one to the ArrayAdapter
		int count = pairedDevices.size();

		if (count > 0) {
			findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);

			for (BluetoothDevice device : pairedDevices) {
				if (device.getName().startsWith("Gprinter")) {
					mPairedDevicesArrayAdapter.add("Cashier" + i);
							//+ "\n" + device.getAddress());
					i++;
				}

			}
		} else {
			String noDevices = getResources().getText(R.string.none_paired)
					.toString();
			mPairedDevicesArrayAdapter.add(noDevices + "\nUnable to use");
		}
		// doDiscovery();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mReceiver);
	}

	// The on-click listener for all devices in the ListViews
	private TextView preView = null;
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			if (v.isEnabled()) {
				String info = ((TextView) v).getText().toString();
				v.setEnabled(false);
				if (preView != null) {
					preView.setEnabled(true);
					preView.setWidth(348);
					preView.setHeight(60);
					preView.setPadding(0, 0, 0, 50);
				}
				preView = (TextView) v;
				Constant.printerAddress = info.substring(info.length() - 17);
				printTest();
			}
		}
	};

	public void printTest() {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("Just a moment, please...");
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					int i = 0;
					BluetoothService mService = new BluetoothService(
							DeviceListActivity.this, mHandler);
					BluetoothDevice device = mBtAdapter
							.getRemoteDevice(Constant.printerAddress);
					mService.connect(device);
					while (mService.getState() != BluetoothService.STATE_CONNECTED) {
						Thread.sleep(500);
						if (i++ > 30) {
							mService.stop();
							Thread.sleep(1500);
							return false;
						}
					}
					byte[] byteA = new byte[] { 0x1D, 0x21, (byte) 0x0110 };
					mService.write(byteA);
					byte[] send;
					String message = Constant.currentStaff.getS_account()
							+ " using now...\n\n\n";
					try {
						send = message.getBytes("GB2312");
					} catch (UnsupportedEncodingException e) {
						send = message.getBytes();
					}
					mService.write(send);
					mService.stop();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (!result) {
					showCustomToast("Printer authentication failed !");
				}
			}
		});

	}

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					if (device.getName().startsWith("Gprinter"))
						mPairedDevicesArrayAdapter.add("Cashier" + i);
								//+ "\n" + device.getAddress());
				}
				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				if (mPairedDevicesArrayAdapter.getCount() == 0) {
					String noDevices = getResources().getText(
							R.string.none_found).toString();
					mPairedDevicesArrayAdapter.add(noDevices);
				}
			}
		}
	};
	// The Handler that gets information back from the BluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		}
	};

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}
}
