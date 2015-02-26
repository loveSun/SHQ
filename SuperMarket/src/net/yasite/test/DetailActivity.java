package net.yasite.test;

import java.util.List;
import java.util.Map;

import cache.loader.ImageWorker;

import net.yasite.dao.GoodsDao;
import net.yasite.entity.GoodEntity;
import net.yasite.entity.GoodListEntity;
import net.yasite.entity.UserCarDataEntity;
import net.yasite.entity.UserCarDatasEntity;
import net.yasite.entity.UserCarEntity;
import net.yasite.exit.Exit;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.sharepre.UserInfoShare;
import android.R.interpolator;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends BaseNewActivity{
	private String id;
	private ImageView good_image;
	private TextView text_name,text_price,market_price;
	private EditText editText;
	private Button jian_btn,add_btn,add_car,buy;
	private GoodsListModel model;
	private GoodListEntity entity;
	private List<GoodEntity> list;
	private UserCarEntity carEntity;
	private UserCarDatasEntity carDatasEntity;
	private int num = 1;
	private GoodsDao dao;
	private UserInfoShare infoShare;
	private String user_id,token,goods_sn,goods_name;
	private float market_price1,goods_price;
	private int goods_number,goods_id;
	
	private String image,username;
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		
		good_image = (ImageView) findViewById(R.id.image_goods);
		text_name = (TextView) findViewById(R.id.text_name);
		text_price = (TextView) findViewById(R.id.text_prices);
		market_price = (TextView) findViewById(R.id.market_prices);
		editText = (EditText) findViewById(R.id.paynum);
		add_btn = (Button) findViewById(R.id.add_button);
		jian_btn = (Button) findViewById(R.id.jian_button);
		buy = (Button) findViewById(R.id.buy);
		add_car = (Button) findViewById(R.id.add_car);
		TextView title_all = (TextView) findViewById(R.id.title_all);
		title_all.setText("  商品详情");
		add_btn.setOnClickListener(lis);
		jian_btn.setOnClickListener(lis);
		add_car.setOnClickListener(lis);
		buy.setOnClickListener(lis);
		Exit.getInstance().addActivity(this);
	}
	OnClickListener lis = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.add_button:
				num++;
				editText.setText(num+"");
				break;
			case R.id.jian_button:
				if(num == 1){
					jian_btn.setVisibility(View.GONE);
				}else{
					num --;
					editText.setText(num+"");			
				}
				break;
			case R.id.add_car:
				insertCar();
				
				break;

			default:
				break;
			}
		}
	};
	private void insertCar() {
		// TODO Auto-generated method stub
		dao = new GoodsDao(context);
		infoShare = new UserInfoShare(context);
		Map<String, ?> map = infoShare.getUserInfo("userinfo", MODE_PRIVATE);
		username = (String) map.get("username");
		token = (String) map.get("token");
		user_id =(String) map.get("user_id");
		if(username==null||username.equals("")){
			Toast.makeText(context, "请您先登录", 1).show();
			Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
			intent.putExtra("login", "detail");
			infoShare.idsertGoodsid("goods", MODE_PRIVATE, Integer.parseInt(id));
			startActivity(intent);
		}else{
			goods_number = num;
			image = list.get(0).getGoods_img();
			new MyHelper(context).execute();
			Toast.makeText(context, "成功加入购物车", 1).show();
		}
	}

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
	}

	@Override
	public void setModel() {
		// TODO Auto-generated method stub
		model = new GoodsListModel(context);
		new MyHelper(context).execute();
		
	}

	@Override
	public boolean getIntentValue() {
		// TODO Auto-generated method stub
		id = getIntent().getStringExtra("id");
		return true;
	}
	class MyHelper extends HandlerHelp{

		public MyHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			list = entity.getData();
			goods_sn = list.get(0).getGoods_sn();
			goods_id = Integer.parseInt(list.get(0).getGoods_id());
			goods_price = Float.parseFloat(list.get(0).getShop_price());
			market_price1 = Float.parseFloat(list.get(0).getMarket_price());
			goods_name = list.get(0).getGoods_name();
			if(list.size()>0){
				ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"+list.get(0).getGoods_img(), good_image, R.drawable.default_photo);
				text_name.setText(list.get(0).getGoods_name());
				text_price.setText("本店价： "+list.get(0).getShop_price());
				market_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
				market_price.setText("市场价："+list.get(0).getMarket_price());
			}
		}
		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			if(entity==null){
				entity = model.getGoodsDetail(Integer.parseInt(id));
			}else{
				carDatasEntity = model.getCar(token, user_id, goods_id, goods_sn, goods_name, market_price1, goods_price, goods_number);
				carEntity = carDatasEntity.getData();
				dao.insertToCar(carEntity.getGoods_id(), image, username, carEntity.getGoods_name(), carEntity.getGoods_price(),carEntity.getRec_id());
				
			}
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
