package com.utopia.Dialog;

import com.utopia.activity.R;
import com.utopia.activity.R.id;
import com.utopia.activity.R.layout;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class pop_spell implements View.OnClickListener {
	private Context context;
	private View itemView;
	private String key_name;
	private ListView listdesk;
	private ListView listtype;
	private TextView mtableid;
	PopupWindow popupWindow;
	private TextView spellfind;

	public pop_spell(Context paramContext, View paramView) {
		this.context = paramContext;
		if (this.popupWindow != null)
			return;
		View localView = LayoutInflater.from(paramContext).inflate(
				R.layout.pop_spell, null);
		this.popupWindow = new PopupWindow(localView, -1, -1);
		this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
		this.popupWindow.setFocusable(true);
		this.popupWindow.update();
		this.popupWindow.showAtLocation(paramView, 16, 0, 0);
		this.spellfind = ((TextView) paramView);
		((ImageButton) localView.findViewById(R.id.keya))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyb))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyc))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyd))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keye))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyf))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyg))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyh))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyi))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyj))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyk))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyl))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keym))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyn))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyo))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyp))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyq))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyr))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keys))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyt))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyu))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyv))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyw))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyx))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyy))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyz))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyback))
				.setOnClickListener(this);
		((ImageButton) localView.findViewById(R.id.keyclose))
				.setOnClickListener(this);
	}

	public void closePop() {
		if (this.popupWindow == null)
			return;
		this.popupWindow.dismiss();
	}

	@Override
	public void onClick(View paramView) {
		if (paramView.getId() == R.id.keyback) {
			if (this.spellfind.getText().toString().length() > 0)
				this.spellfind.setText(this.spellfind
						.getText()
						.toString()
						.substring(
								0,
								-1
										+ this.spellfind.getText().toString()
												.length()));
			this.spellfind.setTag(Integer.valueOf(1));
			this.spellfind.performClick();
			return;
		}
		if (paramView.getId() == R.id.keyclose) {
			closePop();
			return;
		}
		this.spellfind.append(paramView.getTag().toString());
		this.spellfind.setTag(Integer.valueOf(1));
		this.spellfind.performClick();
	}
}

/*
 * Location: D:\android-reverse-trinea\classes_dex2jar.jar Qualified Name:
 * com.etf.food.pop_spell JD-Core Version: 0.6.2
 */