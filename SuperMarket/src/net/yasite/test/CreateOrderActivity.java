package net.yasite.test;

import com.google.gson.Gson;

import net.yasite.api.params.Address;
import net.yasite.entity.MyAddressEntity;
import net.yasite.exit.Exit;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.IInterface;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CreateOrderActivity extends BaseNewActivity {
	private Intent intent;
	private MyAddressEntity entity;
	private Address address;
	private Button button;
	private TextView textView;
	private GoodsListModel model;
	private String user_id;
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		textView = (TextView) findViewById(R.id.con);
		button = (Button) findViewById(R.id.create_order);
		button.setOnClickListener(lis);
		Exit.getInstance().addActivity(this);
	}
	OnClickListener lis = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new Myhelper(context).execute();
		}
	};

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.createorder_layout);
	}

	@Override
	public void setModel() {
		// TODO Auto-generated method stub
		model = new GoodsListModel(context);
	}

	@Override
	public boolean getIntentValue() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		intent = getIntent();
		if(intent!=null){
			if(intent.getSerializableExtra("info") != null){
				entity = (MyAddressEntity) intent.getSerializableExtra("info");
				textView.setText("wwwwww");
				user_id = intent.getStringExtra("user_id");
				address = new Address(entity.getConsignee(), Integer.parseInt(entity.getCountry()), Integer.parseInt(entity.getProvince()), Integer.parseInt(entity.getCity()), Integer.parseInt(entity.getDistrict()), entity.getAddress(), entity.getTel(), entity.getMobile(), entity.getEmail());
			}
		}
	}
	class Myhelper extends HandlerHelp{

		public Myhelper(Context context) {
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
			Log.e(user_id, new Gson().toJson(address));
			model.createOrder(user_id, address);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}

}
