package com.utopia.activity;

import java.util.Date;

import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_SaleRecord;
import com.utopia.Model.d_Product;
import com.utopia.Model.d_SaleRecord;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.DateUtils;
import com.utopia.utils.ExitApplication;

public class OrderMenuDetialActivity extends BaseActivity implements
		OnClickListener {

	private Button done;
	private Button size_regular, size_small, size_large;
	private Button hotness_regular, hotness_none, hotness_strong, hotness_extra;
	private EditText notes;
	private String size = "regular", hotness = "regular", note = "none";
	private d_Product product;
	private String md5 = "12";
	private HomeKeyLocker mHomeKeyLocker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detial_dialog);
		ExitApplication.getInstance().addActivity(this);// 加入退出栈
		mHomeKeyLocker = new HomeKeyLocker();
		// mHomeKeyLocker.lock(OrderMenuDetialActivity.this);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		product = (d_Product) getIntent().getSerializableExtra("d_Product");
		md5 = getIntent().getStringExtra("md5");
		initViews();
		initEvents();
	}

	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.order_done:
			note = notes.getText().toString();

			d_SaleRecord locald_SaleRecord2 = new d_SaleRecord();

			locald_SaleRecord2.setDesk_name(Constant.table_id);
			locald_SaleRecord2.setPdtCODE(product.getPdtCode());
			locald_SaleRecord2.setPdtName(product.getPdtName());
			locald_SaleRecord2.setPrice(product.getPdtSalePrice1());
			locald_SaleRecord2.setBILLID(md5);
			locald_SaleRecord2.setNumber(1);
			locald_SaleRecord2.setOtherSpec(note);
			locald_SaleRecord2.setStatus("Not Sent");
			locald_SaleRecord2.setOtherSpecNo1(size);
			locald_SaleRecord2.setOtherSpecNo2(hotness);
			// 时间
			locald_SaleRecord2.setCreateTime(DateUtils.getDateEN());
			locald_SaleRecord2.setWaiter(Constant.currentStaff.getS_account());
			// 默认税收为0 折扣为 1
			locald_SaleRecord2.setTax(0);
			locald_SaleRecord2.setDiscount(1);
			new sql_SaleRecord().save(locald_SaleRecord2);
			this.finish();
			break;
		case R.id.size_large:
			size_large.setBackgroundResource(R.drawable.b);
			size_regular.setBackgroundResource(R.drawable.a);
			size_small.setBackgroundResource(R.drawable.a);
			size = "large";
			break;
		case R.id.size_regular:
			size_large.setBackgroundResource(R.drawable.a);
			size_regular.setBackgroundResource(R.drawable.b);
			size_small.setBackgroundResource(R.drawable.a);
			size = "regular";
			break;
		case R.id.size_small:
			size_large.setBackgroundResource(R.drawable.a);
			size_regular.setBackgroundResource(R.drawable.a);
			size_small.setBackgroundResource(R.drawable.b);
			size = "small";
			break;
		case R.id.hotness_extra:
			hotness_strong.setBackgroundResource(R.drawable.a);
			hotness_regular.setBackgroundResource(R.drawable.a);
			hotness_none.setBackgroundResource(R.drawable.a);
			hotness_extra.setBackgroundResource(R.drawable.b);
			hotness = "extra";
			break;
		case R.id.hotness_None:
			hotness_strong.setBackgroundResource(R.drawable.a);
			hotness_regular.setBackgroundResource(R.drawable.a);
			hotness_none.setBackgroundResource(R.drawable.b);
			hotness_extra.setBackgroundResource(R.drawable.a);
			hotness = "none";
			break;
		case R.id.hotness_regular:
			hotness_strong.setBackgroundResource(R.drawable.a);
			hotness_regular.setBackgroundResource(R.drawable.b);
			hotness_none.setBackgroundResource(R.drawable.a);
			hotness_extra.setBackgroundResource(R.drawable.a);
			hotness = "regular";
			break;
		case R.id.hotness_strong:
			hotness_strong.setBackgroundResource(R.drawable.b);
			hotness_regular.setBackgroundResource(R.drawable.a);
			hotness_none.setBackgroundResource(R.drawable.a);
			hotness_extra.setBackgroundResource(R.drawable.a);
			hotness = "strong";
			break;

		}

	}

	@Override
	protected void initViews() {

		// TODO Auto-generated method stub
		done = (Button) findViewById(R.id.order_done);
		size_regular = (Button) this.findViewById(R.id.size_regular);
		size_small = (Button) this.findViewById(R.id.size_small);
		size_large = (Button) this.findViewById(R.id.size_large);

		hotness_regular = (Button) this.findViewById(R.id.hotness_regular);
		hotness_none = (Button) this.findViewById(R.id.hotness_None);
		hotness_strong = (Button) this.findViewById(R.id.hotness_strong);
		hotness_extra = (Button) this.findViewById(R.id.hotness_extra);

		notes = (EditText) this.findViewById(R.id.order_notes);

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		done.setOnClickListener(this);
		size_regular.setOnClickListener(this);
		size_small.setOnClickListener(this);
		size_large.setOnClickListener(this);

		hotness_regular.setOnClickListener(this);
		hotness_none.setOnClickListener(this);
		hotness_strong.setOnClickListener(this);
		hotness_extra.setOnClickListener(this);
	}
}
