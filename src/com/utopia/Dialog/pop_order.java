package com.utopia.Dialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Adapter.SpecAdapter;
import com.utopia.Adapter.SpecAdapter.ViewHolder;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_OtherSpec;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.OrderFormActivity;
import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.widget.MyTextView;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class pop_order implements View.OnClickListener {
	private SpecAdapter adapter;
	private boolean b_decnum = false;
	private Context context;
	private float i_num;
	private boolean is_edit;
	private TextView localmemo;
	private TextView localnum;
	private TextView localprice;
	PopupWindow popupWindow;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private ListView localGridView;
	
	private int checkedIndex = -1;

	public pop_order(Context paramContext, View paramView, boolean paramBoolean) {
		this.context = paramContext;
		this.is_edit = paramBoolean;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_order, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		TextView localTextView = (TextView) localView
				.findViewById(R.id.detail_name);
		this.localnum = ((TextView) localView.findViewById(R.id.detail_num));
		this.localmemo = ((TextView) localView.findViewById(R.id.detail_memo));
		this.localprice = ((TextView) localView.findViewById(R.id.detail_price));
		((Button) localView.findViewById(R.id.spectype1))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.spectype2))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.spectype3))
				.setOnClickListener(this);

		
		localGridView = (ListView) localView.findViewById(R.id.gridView_spec);
		this.adapter = new SpecAdapter(paramContext);
		localGridView.setAdapter(this.adapter);
		// localGridView.setOnItemClickListener(this);
		localGridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong) {
						// pop_order.this.localmemo.append("," +
						// ((TextView)paramAnonymousView).getText().toString());
						// size_hotness.append(((TextView)paramAnonymousView).getText().toString());
						ListView lv = (ListView) paramAnonymousAdapterView;
						if (checkedIndex != paramAnonymousInt) {
							{ // 定位到现在处于点击状态的radio
								int childId = checkedIndex
										- lv.getFirstVisiblePosition();
								if (childId >= 0) { // 如果checked=true的radio在显示的窗口内，改变其状态为false
									View item = lv.getChildAt(childId);
									if (item != null) {
										RadioButton rb = (RadioButton) item
												.findViewById(checkedIndex);
										if (rb != null)
											rb.setChecked(false);
									}
								}
								// 将当前点击的radio的checked变为true
								RadioButton rb1 = (RadioButton) paramAnonymousView
										.findViewById(paramAnonymousInt);
								if (rb1 != null) {
									rb1.setChecked(true);
								}
								checkedIndex = paramAnonymousInt;
							}
						}
					}
				});
		((ImageButton) localView.findViewById(R.id.cutBtnRecom))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.addBtnRecom))
				.setOnClickListener(this);
		((Button) localView.findViewById(R.id.loginCancleBtn))
				.setOnClickListener(this);
		Button localButton = (Button) localView.findViewById(R.id.loginBtn);
		localButton.setOnClickListener(this);
		if (!this.is_edit) {
			d_Product locald_Product = (d_Product) paramView.getTag();
			localTextView.setText(locald_Product.getPdtName().trim());
			this.i_num = 1.0F;
			this.localnum.setText(String.valueOf(this.i_num));
			this.localprice.setText(decimalFormat.format(locald_Product
					.getPdtSalePrice1()));
			this.localmemo.setText("");
			localButton.setTag(locald_Product);
		} else {
			d_SaleRecord locald_SaleRecord = (d_SaleRecord) paramView.getTag();
			localTextView.setText(locald_SaleRecord.getPdtName().trim());
			this.i_num = locald_SaleRecord.getNumber();
			this.localnum.setText(String.valueOf(this.i_num));
			this.localmemo.append(locald_SaleRecord.getOtherSpec());
			this.localprice.setText(decimalFormat.format(locald_SaleRecord
					.getPrice()));
			localButton.setTag(locald_SaleRecord);
		}
	}

	public void closePop() {
		if (this.popupWindow != null)
			this.popupWindow.dismiss();
	}

	public void onClick(View paramView) {

		switch (paramView.getId()) {
		case R.id.loginCancleBtn:
			closePop();
			break;
		case R.id.spectype1:
			this.adapter.sqlexec(1);
			break;
		case R.id.spectype2:
			this.adapter.sqlexec(2);
			break;
		case R.id.spectype3:
			this.localmemo.setText("");
			break;
		case R.id.cutBtnRecom:
			if (this.i_num <= 1.0F) {
				showCustomToast("Illegal operation.");
			} else {
				i_num = BigDecimal.valueOf(i_num)
						.subtract(new BigDecimal(1.0D)).setScale(2, 4)
						.floatValue();
				localnum.setText(String.valueOf(i_num));
			}
			break;
		case R.id.addBtnRecom:
			if (!this.b_decnum)
				i_num = BigDecimal.valueOf(i_num).add(new BigDecimal(1.0D))
						.setScale(2, 4).floatValue();
			localnum.setText(String.valueOf(i_num));
			try {
				((OrderFormActivity) this.context).startOnResume();
			} catch (Exception e) {
			}
			break;
		case R.id.loginBtn:
			if (i_num <= 0.0F) {
				showCustomToast("Not a negative and zero");
				return;
			}
			closePop();

			d_SaleRecord locald_SaleRecord2 = new d_SaleRecord();
			if (!this.is_edit) {
				d_Product locald_Product = (d_Product) paramView.getTag();
				locald_SaleRecord2.setPdtCODE(locald_Product.getPdtCode());
				locald_SaleRecord2.setPdtName(locald_Product.getPdtName());
				locald_SaleRecord2.setPrice(locald_Product.getPdtSalePrice1());
			} else {
				d_SaleRecord locald_Product = (d_SaleRecord) paramView.getTag();
				locald_SaleRecord2.setPdtCODE(locald_Product.getPdtCODE());
				locald_SaleRecord2.setPdtName(locald_Product.getPdtName());
				locald_SaleRecord2.setPrice(locald_Product.getPrice());
			}

			locald_SaleRecord2.setOtherSpecNo1(" ");
			locald_SaleRecord2.setOtherSpecNo2(" ");
			locald_SaleRecord2.setBILLID("0");
			locald_SaleRecord2.setNumber((int) this.i_num);
			locald_SaleRecord2
					.setOtherSpec(this.localmemo.getText().toString());
			locald_SaleRecord2.setStatus("Not Sent");
			locald_SaleRecord2.setDesk_name(Constant.table_id);
			new sql_SaleRecord().save(locald_SaleRecord2);
			update_foodnum();

			try {
				((OrderFormActivity) this.context).startOnResume();
			} catch (Exception e) {
			}
		}
	}

	protected void update_foodnum() {
		Constant.foodnumcount = new sql_SaleRecord().getOneOrderTotalNum("0");
		if (Constant.foodnumhandler != null)
			Constant.foodnumhandler.sendEmptyMessage(1);
	}

	/** 显示自定义Toast提示(来自res) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(context).inflate(
				R.layout.common_toast, null);
		((MyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

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
		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			/*
			 * TextView localTextView; if (paramView == null) { localTextView =
			 * new TextView(this.context);
			 * localTextView.setBackgroundResource(R.drawable.addfood_buth);
			 * localTextView.setLayoutParams(new AbsListView.LayoutParams(-2,
			 * -2)); localTextView.setGravity(17);
			 * localTextView.setTextColor(this.context.getResources().getColor(
			 * R.color.white)); } else { localTextView = (TextView) paramView; }
			 * localTextView
			 * .setText(this.mlist.get(paramInt).toString().trim()); return
			 * localTextView;
			 */

			ViewHolder holder = null;
			if (paramView == null) {// convertView 存放item的
				holder = new ViewHolder();
				paramView = LayoutInflater.from(context).inflate(
						R.layout.specadapter, null);
				holder.title = (TextView) paramView.findViewById(R.id.title2);
				holder.viewBtn = (RadioButton) paramView
						.findViewById(R.id.listview2_radiobutton);
				paramView.setTag(holder);
			} else {
				holder = (ViewHolder) paramView.getTag();
			}

			holder.title.setText(this.mlist.get(paramInt).toString().trim());

			// 让子控件button失去焦点 这样不会覆盖掉item的焦点 否则点击item 不会触发响应即onItemClick失效
			holder.viewBtn.setFocusable(false);// 无此句点击item无响应的
			holder.viewBtn.setId(paramInt);
			holder.viewBtn.setChecked(false);

			holder.viewBtn
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								// set pre radio button
								if (checkedIndex != -1) {
									int childId = checkedIndex
											- localGridView
													.getFirstVisiblePosition();
									if (childId >= 0) {
										View item = localGridView
												.getChildAt(childId);
										if (item != null) {
											RadioButton rb = (RadioButton) item
													.findViewById(checkedIndex);
											if (rb != null) {
												rb.setChecked(false);
											}
										}
									}
								}
								// set cur radio button
								checkedIndex = buttonView.getId();
								pop_order.this.localmemo.append(mlist
										.get(checkedIndex).toString().trim()
										+ ",");
								
							}
						}
					});
			return paramView;
		}

		public void sqlexec(int paramInt) {
			mlist = new sql_OtherSpec()
					.recordlist2("select OtherSpecName from OtherSpec where OtherSpecTypeID='"
							+ paramInt + "' order by OtherSpecNo");
			notifyDataSetChanged();
		}

		public final class ViewHolder {
			public TextView title;
			public RadioButton viewBtn;
		}
	}
}