package com.utopia.Adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.PayBillActivity;
import com.utopia.activity.R;
import com.utopia.baseadapter.utils.CommomAdapter;
import com.utopia.baseadapter.utils.ViewHolder;

public class PayBillBillAdapter<T> extends CommomAdapter<T> {
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	DecimalFormat decimalFormat1 = new DecimalFormat("0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	private Context mContext;

	public PayBillBillAdapter(Context context, List<T> datas, int layoutId) {
		super(context, datas, layoutId);
		this.mContext = context;
	}

	private d_SaleRecord ds;

	@Override
	public void convert(final ViewHolder holder, T t) {
		ds = (d_SaleRecord) t;
		holder.setText(R.id.qty_item, "1/"+ds.getCustomerNo()).setText(R.id.dish_item, ds.getPdtName()).setText(
				R.id.dish_price,
				(decimalFormat.format(ds.getPrice() * ds.getNumber()
						/ ds.getCustomerNo()))).setTag(R.id.bt_delete_item, ds.getItemNo()+","+ds.getBILLID());

		// 删除当前菜
		holder.getView(R.id.bt_delete_item).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String value[] =  arg0.getTag().toString().split(",");
						delete(value[1],value[0]);
						for(int j=0 ; j < ((PayBillActivity) mContext).views.size() ; j++){
							((PayBillActivity) mContext).adapters.get(j).mDatas = (List<d_SaleRecord>)((PayBillActivity) mContext) .getSaleRecordsCustomer1(j);
							((PayBillActivity) mContext).adapters.get(j).notifyDataSetChanged();
							((PayBillActivity) mContext).initData(j);
						}
						((PayBillActivity)mContext).sladapter.mDatas= (List<d_SaleRecord>)((PayBillActivity) mContext).getSaleRecordsAll();
						((PayBillActivity)mContext).sladapter.notifyDataSetChanged();
					}
				});
	} 

	private void delete(String customNo, String ItemNo) {
		new sql_SaleRecord()
				.recordlist5("delete  from Customer  where  ItemNo='" + ItemNo
						+ "' and customNo='" + customNo + "'");
	}
}
