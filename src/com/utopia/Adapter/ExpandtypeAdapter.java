package com.utopia.Adapter;

import java.util.ArrayList;
import java.util.List; 
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView; 
import com.utopia.Dao.sql_MenuType;
import com.utopia.Model.d_MenuType;
import com.utopia.activity.R;

public class ExpandtypeAdapter extends BaseExpandableListAdapter {
	ArrayList<List> child = new ArrayList<List>();
	private Context context;
	ArrayList<String> group = new ArrayList<String>();

	public ExpandtypeAdapter(Context paramContext) {
		this.context = paramContext;
		addItemByList();
	}

	public void addItemByList() {
		sql_MenuType localsql_TypeName = new sql_MenuType();
		ArrayList<d_MenuType> localArrayList1 = localsql_TypeName
				.queryMenuTypes("select * from MenuType where cast(TypeParentId as int)=0  order by TypeId");
		for (int i = 0 ; i < localArrayList1.size(); i++) {
			ArrayList<String> localArrayList2 = localsql_TypeName
					.recordlist2("select TypeName from MenuType where cast(TypeParentId as int)=cast("
							+ ((d_MenuType) localArrayList1.get(i)).getTypeId()
							+ " as int) order by TypeId");
			this.group.add(((d_MenuType) localArrayList1.get(i)).getTypeName());
			this.child.add(localArrayList2);
		}
	}

	public void addItemByValue(String paramString,
			ArrayList<d_MenuType> paramArrayList) {
		this.group.add(paramString);
		ArrayList<String> localArrayList = new ArrayList<String>();
		for (int i = 0;; i++) {
			if (i >= paramArrayList.size()) {
				this.child.add(localArrayList);
				return;
			}
			localArrayList.add(((d_MenuType) paramArrayList.get(i))
					.getTypeName());
		}
	}

	public Object getChild(int paramInt1, int paramInt2) {
		return ((List) this.child.get(paramInt1)).get(paramInt2);
	}

	public long getChildId(int paramInt1, int paramInt2) {
		return paramInt2;
	}

	public View getChildView(int paramInt1, int paramInt2,
			boolean paramBoolean, View paramView, ViewGroup paramViewGroup) {
		List localList = (List) this.child.get(paramInt1);
		View localView = ((LayoutInflater) this.context
				.getSystemService("layout_inflater")).inflate(R.layout.type_children, null);
		TextView localTextView = (TextView) localView.findViewById(R.id.content_child);
		localTextView.setTextColor(Color.argb(255, 128, 5, 0));
		localTextView.setText(localList.get(paramInt2).toString().trim());
		return localView;
	}

	public int getChildrenCount(int paramInt) {
		return ((List) this.child.get(paramInt)).size();
	}

	public Object getGroup(int paramInt) {
		return this.group.get(paramInt);
	}

	public int getGroupCount() {
		return this.group.size();
	}

	public long getGroupId(int paramInt) {
		return paramInt;
	}

	public View getGroupView(int paramInt, boolean paramBoolean,
			View paramView, ViewGroup paramViewGroup) {
		View localView = ((LayoutInflater) this.context
				.getSystemService("layout_inflater")).inflate(
				R.layout.type_group, null);
		TextView localTextView = (TextView) localView
				.findViewById(R.id.content_group);
		ImageView localImageView = (ImageView) localView
				.findViewById(R.id.groupImg);
		localTextView.setText(((String) this.group.get(paramInt)).toString()
				.trim());
		if (paramBoolean) {
			localImageView.setBackgroundResource(R.drawable.dish_tree_open);
			localTextView.setTextColor(-16777216);
		}
		return localView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int paramInt1, int paramInt2) {
		return true;
	}

	public void onGroupCollapsed(int paramInt) {
	}

	public void onGroupExpanded(int paramInt) {
	}
}