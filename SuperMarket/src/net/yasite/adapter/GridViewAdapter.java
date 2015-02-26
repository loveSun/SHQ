package net.yasite.adapter;

import java.util.Random;

import net.yasite.test.R;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private int[] colors = new int[] { Color.rgb(189, 202, 188),
			Color.rgb(222, 203, 161), Color.rgb(244, 107, 65) };
	private int[] images;
	private Context context;

	public GridViewAdapter(Context context, int images[]) {
		this.images = images;
		this.context = context;
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HotGridViewHolder holder = null;
		if (convertView == null) {
			Random ran = new Random();
			int n = ran.nextInt(4);
			if (n % 2 == 0) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.grid_item1, null);
			} else {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.grid_item2, null);
			}
			holder = new HotGridViewHolder(convertView);
			Random ran2 = new Random();
			int n2 = ran2.nextInt(3);
			holder.getBG().setBackgroundColor(colors[n2]);
			holder.getImageView().setBackgroundResource(images[position]);
			convertView.setTag(holder);
		} else {
			holder = (HotGridViewHolder) convertView.getTag();
		}
		return convertView;
	}

	private class HotGridViewHolder {
		private ImageView imageview;
		private TextView shopname;
		private TextView text;
		private View view;

		private RelativeLayout bg;

		private HotGridViewHolder(View view) {
			this.view = view;
		}

		ImageView getImageView() {
			if (imageview == null) {
				imageview = (ImageView) view.findViewById(R.id.name);
			}
			return imageview;
		}

		TextView getShopName() {
			if (shopname == null) {
				shopname = (TextView) view.findViewById(R.id.shopname);
			}
			return shopname;
		}

		TextView getText() {
			if (text == null) {
				text = (TextView) view.findViewById(R.id.text);
			}
			return text;
		}

		RelativeLayout getBG() {
			if (bg == null) {
				bg = (RelativeLayout) view.findViewById(R.id.bg);
			}
			return bg;
		}
	}
}
