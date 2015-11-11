package com.utopia.Dialog;

import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
import android.content.Context;

import com.utopia.Base.BaseDialog;
import com.utopia.activity.R;
import com.utopia.widget.FlippingImageView;
import com.utopia.widget.MyTextView;

public class LoadingDialog extends BaseDialog {

	private FlippingImageView mFivIcon;
	private MyTextView mHtvText;
	private String mText;

	public LoadingDialog(Context context, String text) {
		super(context);
		mText = text;
		init();
	}

	private void init() {
		setContentView(R.layout.flipping_loading_diloag);
		setCanceledOnTouchOutside(false);	//禁用框外点击
		mFivIcon = (FlippingImageView) findViewById(R.id.loadingdialog_fiv_icon);
		mHtvText = (MyTextView) findViewById(R.id.loadingdialog_htv_text);
		mFivIcon.startAnimation();
		mHtvText.setText(mText);
	}

	public void setText(String text) {
		mText = text;
		mHtvText.setText(mText);
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
	}
}
