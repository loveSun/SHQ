package net.yasite.adapter;

import java.util.ArrayList;
import java.util.List;

import net.yasite.test.R;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class ClassifyAdapter extends YasiteAdapter {
	List<String> list = new ArrayList<String>();

	public ClassifyAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	protected void setupChildViews(View convertView, ViewHolder holder) {
		// TODO Auto-generated method stub
		ClassifyViewHolder holder2 = (ClassifyViewHolder) holder;
		holder2.textView = (TextView) convertView.findViewById(R.id.class_it);
		
	}

	@Override
	protected void setChildViewData(ViewHolder holder, int position, Object obj) {
		// TODO Auto-generated method stub
		ClassifyViewHolder holder2 = (ClassifyViewHolder) holder;
		if (obj instanceof String) {
			String str = (String) obj;
			holder2.textView.setText(str);
		}

	}

	@Override
	protected ViewHolder setHolder() {
		// TODO Auto-generated method stub
		return new ClassifyViewHolder();
	}

	@Override
	protected void setLayoutResource(int position) {
		// TODO Auto-generated method stub
		layoutId = R.layout.classify_item;
	}

	class ClassifyViewHolder extends ViewHolder {
		TextView textView;
	}
}
