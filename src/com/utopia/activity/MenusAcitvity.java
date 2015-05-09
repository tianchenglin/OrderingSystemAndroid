package com.utopia.activity;

import android.graphics.Color;
import android.os.Bundle; 
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.utopia.Adapter.ExpandtypeAdapter;
import com.utopia.Adapter.MenusAdapter;
import com.utopia.Base.BaseActivity;
import com.utopia.Dao.sql_desk;
import com.utopia.Dialog.pop_menu_select_desk;
import com.utopia.Dialog.pop_spell;
import com.utopia.Service.HomeKeyLocker;
import com.utopia.utils.Constant;
import com.utopia.utils.ExitApplication;

public class MenusAcitvity extends BaseActivity implements View.OnClickListener {
	private ExpandtypeAdapter adapter = null;
	private MenusAdapter dbAdapter = null;
	private View itemView = null;
	private TextView spellfind;
	private ExpandableListView typelist = null;
	private TextView tv_postion ; 
	private String table ; 
	private HomeKeyLocker mHomeKeyLocker;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.mdishes);
		ExitApplication.getInstance().addActivity(this);
		mHomeKeyLocker = new HomeKeyLocker();
		mHomeKeyLocker.lock(MenusAcitvity.this);
		
		initViews();
		initEvents();
		initlist();
	}
	protected void onDestroy() {
		mHomeKeyLocker.unlock();
		mHomeKeyLocker = null;
		super.onDestroy();
	}
	protected void onResume() {
		super.onResume();
		initmygridview();
	}

	protected void onPause() {
		super.onPause();
		if (!Constant.mainmgpic)
			dbAdapter.closedb();
	}

	private void itemBackChanged(View paramView) {
		((TextView) paramView.findViewById(R.id.content_child))
				.setTextColor(-16777216);
		if (itemView == null)
			itemView = paramView;
		if (itemView != paramView)
			((TextView) this.itemView.findViewById(R.id.content_child))
					.setTextColor(Color.argb(255, 128, 5, 0));
		this.itemView = paramView;
	}

	public void initlist() {
		typelist = ((ExpandableListView) findViewById(R.id.listviewgroup));
		adapter = new ExpandtypeAdapter(this);
		typelist.setAdapter(this.adapter);
		typelist.setDivider(null);
		typelist.setGroupIndicator(null);
		
		tv_postion = (TextView) findViewById(R.id.tv_postion);
		//tv_postion.setText(Constant.table_id);
		//tv_postion.setText(new sql_desk().select());
		table = tv_postion.getText().toString();
		if (typelist.getCount() > 0)
			typelist.expandGroup(0);
		typelist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			public boolean onChildClick(
					ExpandableListView paramAnonymousExpandableListView,
					View paramAnonymousView, int paramAnonymousInt1,
					int paramAnonymousInt2, long paramAnonymousLong) {
				MenusAcitvity.this.itemBackChanged(paramAnonymousView);
				MenusAcitvity.this.dbAdapter.execsql(MenusAcitvity.this.adapter
						.getChild(paramAnonymousInt1, paramAnonymousInt2)
						.toString().trim());
				return false;
			}
		});
		typelist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			public void onGroupExpand(int paramAnonymousInt) {
				for (int i = 0;; i++) {
					if (i >= MenusAcitvity.this.adapter.getGroupCount())
						return;
					if (paramAnonymousInt != i)
						MenusAcitvity.this.typelist.collapseGroup(i);
				}
			}
		});
	}

	protected void initmygridview() {
		GridView localGridView = (GridView) findViewById(R.id.gridView1);
		dbAdapter = new MenusAdapter(this,table);
		localGridView.setAdapter(this.dbAdapter);
		localGridView.setNumColumns(4);
		localGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView paramAnonymousAbsListView,
					int paramAnonymousInt1, int paramAnonymousInt2,
					int paramAnonymousInt3) {
			}

			public void onScrollStateChanged(
					AbsListView paramAnonymousAbsListView, int paramAnonymousInt) {
				MenusAcitvity.this.dbAdapter.scroll(paramAnonymousAbsListView,
						paramAnonymousInt);
			}
		});
	}

	public void onClick(View paramView) {
		if (paramView.getId() == R.id.keyspell)
			new pop_spell(this, spellfind);
		if ((paramView.getId() == R.id.spellfind)
				&& (this.spellfind.getTag() != null)) {
			dbAdapter.execsql_spell(spellfind.getText().toString());
			spellfind.setTag(null);
		}
		if (paramView.getId() == R.id.keyspellclear) {
			spellfind.setText("");
			spellfind.setTag(Integer.valueOf(1));
			spellfind.performClick();
		}

		if (paramView.getId() == R.id.tv_postion) {
			new pop_menu_select_desk(this, paramView, "EMPTY");
		}
	}

	@Override
	protected void initViews() {
		spellfind = ((TextView) findViewById(R.id.keyspell));
	}

	@Override
	protected void initEvents() {
		((ImageButton) findViewById(R.id.spellfind)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.keyspellclear))
				.setOnClickListener(this);
		this.spellfind.setOnClickListener(this);

		findViewById(R.id.tv_postion).setOnClickListener(this);

	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		table = tv_postion.getText().toString();
		onResume();
	}
}