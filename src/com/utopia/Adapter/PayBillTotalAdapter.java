package com.utopia.Adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import com.utopia.Model.d_SaleRecord;
import com.utopia.activity.R;
import com.utopia.baseadapter.utils.CommomAdapter;
import com.utopia.baseadapter.utils.ViewHolder;

public class PayBillTotalAdapter<T> extends CommomAdapter<T> {
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.

	public PayBillTotalAdapter(Context context, List<T> datas, int layoutId) {
		super(context, datas, layoutId);
		mContext = context;
	}

	private d_SaleRecord ds;

	@SuppressLint("NewApi")
	@Override
	public void convert(final ViewHolder holder, T t) {
		ds = (d_SaleRecord) t;
		holder.setText(R.id.dish_item, ds.getPdtName())
				.setText(R.id.dish_price,(decimalFormat.format(ds.getPrice() * ds.getNumber())))
				.setText(R.id.shared, ds.getCustomerNo()+"");

		if (ds.getCustomerNo() > 0) {
			holder.getView(R.id.dish).setBackgroundColor(
					Color.parseColor("#e6e6e6"));
		} else {
			holder.getView(R.id.dish).setBackgroundColor(Color.WHITE);
		}
	}
}
