package net.yasite.adapter;

import java.util.ArrayList;
import java.util.List;

import net.yasite.entity.GoodEntity;
import net.yasite.entity.UserCarEntity;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.test.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsCarAdapter extends YasiteAdapter {
	private List<UserCarEntity> list = new ArrayList<UserCarEntity>();
	private GoodsListModel model;
	private String token;
	private TextView edit, prin;
	private String goods_sn, goods_name;
	private int user_id, rec_id, goods_number, goods_id;
	private float market_price, goods_price;
	private StringBuffer buffer;
	private Button button;
	
	private CheckBox checkBox;

	public GoodsCarAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		model = new GoodsListModel(context);
	}

	public GoodsCarAdapter(Context context, List<UserCarEntity> list) {
		super(context);
	}
	
	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public List<UserCarEntity> getList() {
		return list;
	}

	public TextView getEdit() {
		return edit;
	}

	public TextView getPrin() {
		return prin;
	}

	public void setPrin(TextView prin) {
		this.prin = prin;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public void setEdit(TextView edit) {
		this.edit = edit;
	}

	public void setList(List<UserCarEntity> list) {
		this.list = list;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
		CarViewHolder holder2 = (CarViewHolder) holder;
		holder2.text_name = (TextView) convertView
				.findViewById(R.id.goods_name);
		holder2.text_price = (TextView) convertView
				.findViewById(R.id.goods_price);
		holder2.text_market = (TextView) convertView
				.findViewById(R.id.market_price);
		holder2.jian = (Button) convertView.findViewById(R.id.jian_button);
		holder2.add = (Button) convertView.findViewById(R.id.add_button);
		holder2.number = (EditText) convertView.findViewById(R.id.paynum);
		holder2.box = (CheckBox) convertView.findViewById(R.id.check);
		holder2.del = (TextView) convertView.findViewById(R.id.del);
	}

	@Override
	protected void setChildViewData(ViewHolder holder, final int position,
			Object obj) {
		// TODO Auto-generated method stub
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edit.getText().toString().equals("编辑")) {
					edit.setText("完成");
					prin.setText("删除");
				} else {
					edit.setText("编辑");
					prin.setText("总价：");
				}
			}

		});

		final CarViewHolder holder2 = (CarViewHolder) holder;
		if (obj instanceof UserCarEntity) {
			final UserCarEntity entity = (UserCarEntity) obj;
			holder2.text_name.setText(entity.getGoods_name());
			holder2.text_price.setText("￥" + entity.getGoods_price());
			holder2.text_market.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			holder2.text_market.setText("￥" + entity.getMarket_price());
			holder2.number.setText(entity.getGoods_number() + "");
			holder2.box
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							float sum = 0;
							
							list.get(position).setCheck(isChecked);
							if (edit.getText().toString().equals("编辑")) {
								for (UserCarEntity entity : list) {
									if (entity.isCheck()) {
										int num = Integer.parseInt(entity.getGoods_number());
										sum += Float.parseFloat(entity
												.getGoods_price())*num;
									}
									prin.setText("总价：" + sum);
								}
							} else {
								buffer = new StringBuffer("[");
								for (UserCarEntity entity : list) {
									if (entity.isCheck()) {
										buffer.append(entity.getRec_id())
												.append(",");
									}
									buffer.deleteCharAt(buffer.length() - 1);
									buffer.append("]");
								}
							}
						}
					});
			prin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (prin.getText().toString().equals("删除")) {
						if (buffer.length() > 0) {
							new MyDelHelper(context).execute();
							list.remove(position);
							notifyDataSetChanged();
						} else {
							Toast.makeText(context, "请选择要删除的商品", 1).show();
						}
					}
				}
			});
			holder2.add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int numbers = Integer.parseInt(entity.getGoods_number());
					numbers++;
					holder2.number.setText(numbers + "");
					rec_id = Integer.parseInt(list.get(position).getRec_id());
					user_id = Integer.parseInt(list.get(position).getUser_id());
					goods_id = Integer
							.parseInt(list.get(position).getUser_id());
					goods_sn = list.get(position).getGoods_sn();
					goods_name = list.get(position).getGoods_name();
					market_price = Float.parseFloat(list.get(position)
							.getMarket_price());
					goods_price = Float.parseFloat(list.get(position)
							.getGoods_price());
					goods_number = numbers;
					new MyUpdateHelper(context).execute();

				}
			});

			holder2.jian.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int numbers = Integer.parseInt(entity.getGoods_number());
					numbers--;
					holder2.number.setText(numbers + "");
					rec_id = Integer.parseInt(list.get(position).getRec_id());
					user_id = Integer.parseInt(list.get(position).getUser_id());
					goods_id = Integer
							.parseInt(list.get(position).getUser_id());
					goods_sn = list.get(position).getGoods_sn();
					goods_name = list.get(position).getGoods_name();
					market_price = Float.parseFloat(list.get(position)
							.getMarket_price());
					goods_price = Float.parseFloat(list.get(position)
							.getGoods_price());
					goods_number = numbers;
					new MyUpdateHelper(context).execute();
				}
			});
			holder2.del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					buffer = new StringBuffer("[");
					buffer.append(list.get(position).getRec_id());
					buffer.append("]");
					user_id = Integer.parseInt(list.get(position).getUser_id());
					new MyDelHelper(context).execute();
					list.remove(position);
					notifyDataSetChanged();
				}
			});
		}
		holder2.box.setChecked(list.get(position).isCheck());
	}

	@Override
	protected ViewHolder setHolder() {
		// TODO Auto-generated method stub
		return new CarViewHolder();
	}

	@Override
	protected void setLayoutResource(int position) {
		// TODO Auto-generated method stub
		layoutId = R.layout.car_item;
	}

	class CarViewHolder extends ViewHolder {
		TextView del, text_name, text_price, text_market;
		Button add, jian;
		EditText number;
		CheckBox box;
	}

	class MyUpdateHelper extends HandlerHelp {

		public MyUpdateHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub

		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			model.updateCar(token, rec_id, Integer.toString(user_id), goods_id,
					goods_sn, goods_name, market_price, goods_price,
					goods_number);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	class MyDelHelper extends HandlerHelp {

		public MyDelHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {

		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub

			model.delCarGoods(token, buffer.toString(),
					Integer.toString(user_id));
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub

		}

	}
}
