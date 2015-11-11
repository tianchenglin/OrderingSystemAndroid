package com.utopia.Adapter;

import java.io.File;
import java.text.DecimalFormat;

import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utopia.Dao.sql_Product;
import com.utopia.Model.d_Product;
import com.utopia.activity.R;
import com.utopia.utils.Constant;
import com.utopia.widget.MyTextView;

public class MenusAdapter extends BaseAdapter implements
		View.OnClickListener {
	private Context context;
	private boolean mBusy = false;
	public Cursor m_CallCursor;
	private String m_sql = "";
	private String table ;
	DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
	public MenusAdapter(Context paramContext , String table) {
		this.context = paramContext;
		this.table = table ;
		this.m_sql = "select PdtID,PdtCode,PdtName,PdtUnit,PdtSalePrice1,PdtSalePrice2 from Product limit 1,20";
		this.m_CallCursor = new sql_Product().recordlist3(this.m_sql);
	}

	public void additem(AbsListView paramAbsListView, int paramInt1,
			int paramInt2) {
		int i = 0;
		if (i >= paramInt2)
			return;
		AppItem localAppItem = (AppItem) paramAbsListView.getChildAt(i)
				.getTag();
		d_Product locald_Product = null;
		if (localAppItem != null) {
			this.m_CallCursor.moveToPosition(paramInt1 + i);
			locald_Product = new d_Product();
			locald_Product.setPdtID(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtID")));
			locald_Product.setPdtCode(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtCode")));
			locald_Product.setPdtName(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtName")));
			locald_Product.setPdtUnit(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtUnit")));
			locald_Product.setPdtSalePrice1(this.m_CallCursor.getFloat(this.m_CallCursor.getColumnIndex("PdtSalePrice1")));
			locald_Product.setPdtSalePrice2(this.m_CallCursor.getFloat(this.m_CallCursor.getColumnIndex("PdtSalePrice2")));
			String str = this.context.getApplicationContext().getFilesDir()
					.getParent()
					+ "/pic/z_" + locald_Product.getPdtCode().trim() + ".jpg";
			if (!new File(str).exists())
				localAppItem.m_pic.setBackgroundResource(R.drawable.s1);
			else
				localAppItem.m_pic.setBackgroundDrawable(Constant
						.readBitMapFile(this.context, str));
		}
		localAppItem.m_name.setText(locald_Product.getPdtName());
		localAppItem.m_price.setText(decimalFormat.format(locald_Product
				.getPdtSalePrice1()));
		localAppItem.m_pic.setTag(locald_Product);
		localAppItem.m_pic.setOnClickListener(this);
		localAppItem.m_mask.setTag(locald_Product);
		localAppItem.m_mask.setOnClickListener(this);
		i++;
	}

	public void closedb() {
		this.m_CallCursor.close();
	}

	public void execsql(String paramString) {
		this.m_sql = ("select PdtID,PdtCode,PdtName,PdtUnit,PdtSalePrice1,PdtSalePrice2 from Product JOIN MenuType ON Product.TypeId = MenuType.TypeId where trim(MenuType.TypeName)='"
				+ paramString + "'");
		this.m_CallCursor = new sql_Product().recordlist3(this.m_sql);
		notifyDataSetChanged();
	}

	public void execsql_spell(String paramString) {
		this.m_sql = ("select PdtID,PdtCode,PdtName,PdtUnit,PdtSalePrice1,PdtSalePrice2 from Product where PdtPy like '"
				+ paramString + "%'");
		this.m_CallCursor = new sql_Product().recordlist3(this.m_sql);
		notifyDataSetChanged();
	}

	public int getCount() {
		if (this.m_CallCursor != null)
			return this.m_CallCursor.getCount();
		return 0;
	}

	public Object getItem(int paramInt) {
		return Integer.valueOf(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {

		if (paramView == null) {
			View localView = LayoutInflater.from(this.context).inflate(
					R.layout.mdishes_item, null);
			AppItem localAppItem2 = new AppItem();
			localAppItem2.m_pic = ((ImageView) localView
					.findViewById(R.id.dishImgRecom));
			localAppItem2.m_name = ((TextView) localView
					.findViewById(R.id.dishNameRecom));
			localAppItem2.m_price = ((TextView) localView
					.findViewById(R.id.DishPriceRecom));
			localAppItem2.m_mask = ((LinearLayout) localView
					.findViewById(R.id.grid_mask_but));
			localAppItem2.m_pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
			localAppItem2.add = (ImageButton) localView.findViewById(R.id.addBtnRecom);
			localAppItem2.sub = (ImageButton) localView.findViewById(R.id.cutBtnRecom);
			localView.setTag(localAppItem2);
			paramView = localView;
		}

		this.m_CallCursor.moveToPosition(paramInt);
		d_Product locald_Product = new d_Product();
		locald_Product.setPdtID(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtID")));
		locald_Product.setPdtCode(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtCode")));
		locald_Product.setPdtName(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtName")));
		locald_Product.setPdtUnit(this.m_CallCursor.getString(this.m_CallCursor.getColumnIndex("PdtUnit")));
		locald_Product.setPdtSalePrice1(this.m_CallCursor.getFloat(this.m_CallCursor.getColumnIndex("PdtSalePrice1")));
		locald_Product.setPdtSalePrice2(this.m_CallCursor.getFloat(this.m_CallCursor.getColumnIndex("PdtSalePrice2")));
		AppItem localAppItem1 = (AppItem) paramView.getTag();
		localAppItem1.m_name.setText(locald_Product.getPdtName());
		localAppItem1.m_price.setText(decimalFormat.format(locald_Product
				.getPdtSalePrice1())); 
		if (!this.mBusy) {
			localAppItem1.m_name.setTag(locald_Product);
			localAppItem1.m_pic.setTag(locald_Product);
			localAppItem1.m_pic.setOnClickListener(this);

			localAppItem1.add.setTag(locald_Product);
			localAppItem1.sub.setTag(locald_Product);
			localAppItem1.add.setOnClickListener(this);
			localAppItem1.sub.setOnClickListener(this);
			
			localAppItem1.m_mask.setTag(locald_Product);
			localAppItem1.m_mask.setOnClickListener(this);
		}
		return paramView;
	}

	public void onClick(View paramView) {
		if (paramView.getId() == R.id.dishImgRecom) {
			d_Product locald_Product = (d_Product) paramView.getTag();
			// Intent localIntent = new Intent(this.context, Details.class);
			// localIntent.putExtra("menuSql", this.m_sql);
			// localIntent.putExtra("menuDef", locald_Product);
			// this.context.startActivity(localIntent);
			Constant.mainmgpic = true;
			if(table.equals("Select Tables")){
				showCustomToast("Please select tables");
			}else{
				//new pop_order(this.context, paramView, false);				
			}
		}
		
		if (paramView.getId() == R.id.addBtnRecom) {
			d_Product locald_Product = (d_Product) paramView.getTag();
			// Intent localIntent = new Intent(this.context, Details.class);
			// localIntent.putExtra("menuSql", this.m_sql);
			// localIntent.putExtra("menuDef", locald_Product);
			// this.context.startActivity(localIntent);
			Constant.mainmgpic = true;
			if(table.equals("Select Tables")){
				showCustomToast("Please select tables");
			}else{
				//new pop_order(this.context, paramView, false);				
			}
		}
		if (paramView.getId() == R.id.cutBtnRecom) {
			d_Product locald_Product = (d_Product) paramView.getTag();
			// Intent localIntent = new Intent(this.context, Details.class);
			// localIntent.putExtra("menuSql", this.m_sql);
			// localIntent.putExtra("menuDef", locald_Product);
			// this.context.startActivity(localIntent);
			Constant.mainmgpic = true;
			if(table.equals("Select Tables")){
				showCustomToast("Please select tables");
			}else{
				//new pop_order(this.context, paramView, false);				
			}
		}
		
		if (paramView.getId() == R.id.grid_mask_but)
			if(table.equals("Select Tables")){
				showCustomToast("Please select tables");
			}else{
				//new pop_order(this.context, paramView, false);				
			}
	}

	public void scroll(AbsListView paramAbsListView, int paramInt) {
		switch (paramInt) {
		default:
			return;
		case 0:
			this.mBusy = false;
			additem(paramAbsListView,
					paramAbsListView.getFirstVisiblePosition(),
					paramAbsListView.getChildCount());
			return;
		case 1:
			this.mBusy = true;
			return;
		case 2:
			this.mBusy = true;
		}

	}

	/** 显示自定义Toast提示(来自String) **/
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
	
	class AppItem {
		LinearLayout m_mask;
		TextView m_name;
		ImageView m_pic;
		TextView m_price;
		ImageButton add ; 
		ImageButton sub ; 
		AppItem() {
		}
	}
}