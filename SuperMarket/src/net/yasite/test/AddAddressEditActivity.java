package net.yasite.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.yasite.entity.AddressDataEntity;
import net.yasite.entity.AddressEntity;
import net.yasite.entity.MyAddressEntity;
import net.yasite.exit.Exit;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.sharepre.UserInfoShare;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class AddAddressEditActivity extends BaseNewActivity {
	private ListView listView;
	private TextView province, city, district;
	private EditText address, tel, phone, name;
	private Button button;
	private GoodsListModel model;
	private UserInfoShare infoShare;
	private AddressDataEntity dataEntity;
	private List<AddressEntity> entity;
	private MyAddressEntity myAddressEntity;
	int re_id = 1, country, user_id;
	List<String> list = null;
	private Intent intent;
	String tels, mobile, addre, province_parent_id, city_parent_id,
			district_parent_id, id, token, consignee, email;
	private String address_id;

	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.list_view);
		province = (TextView) findViewById(R.id.province);
		city = (TextView) findViewById(R.id.city);
		TextView text_title = (TextView) findViewById(R.id.title_all);
		text_title.setText("   添加用户地址");
		district = (TextView) findViewById(R.id.district);
		address = (EditText) findViewById(R.id.address);
		tel = (EditText) findViewById(R.id.tel);
		phone = (EditText) findViewById(R.id.phone);
		button = (Button) findViewById(R.id.add);
		name = (EditText) findViewById(R.id.name);
		infoShare = new UserInfoShare(context);
		listView.setOnItemClickListener(listener);
		province.setOnClickListener(lis);
		city.setOnClickListener(lis);
		district.setOnClickListener(lis);
		button.setOnClickListener(lis);
		Exit.getInstance().addActivity(this);
	}

	protected void onResume() {
		super.onResume();
		intent = getIntent();
		if (intent != null) {
			if (intent.getSerializableExtra("info") != null) {
				button.setText("提交修改");
				listView.setVisibility(View.GONE);
				myAddressEntity = (MyAddressEntity) intent
						.getSerializableExtra("info");
				address_id = myAddressEntity.getAddress_id();
				province.setVisibility(View.VISIBLE);
				province.setText(infoShare.getAddress(myAddressEntity.getProvince()));
				province_parent_id = myAddressEntity.getProvince();
				city_parent_id = myAddressEntity.getCity();
				district_parent_id = myAddressEntity.getDistrict();
				city.setVisibility(View.VISIBLE);
				city.setText(infoShare.getAddress(myAddressEntity.getCity()));
				district.setVisibility(View.VISIBLE);
				district.setText(infoShare.getAddress(myAddressEntity.getDistrict()));
				address.setVisibility(View.VISIBLE);
				address.setText(myAddressEntity.getAddress() + "");
				tel.setVisibility(View.VISIBLE);
				tel.setText(myAddressEntity.getTel() + "");
				phone.setVisibility(View.VISIBLE);
				phone.setText(myAddressEntity.getMobile() + "");
				name.setVisibility(View.VISIBLE);
				name.setText(myAddressEntity.getConsignee());
			}
		}
	};

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.province:
				re_id = 1;
				new MyHelper(context).execute();
				province.setVisibility(View.GONE);
				city.setVisibility(View.GONE);
				district.setVisibility(View.GONE);
				address.setVisibility(View.GONE);
				tel.setVisibility(View.GONE);
				phone.setVisibility(View.GONE);
				name.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				break;
			case R.id.city:
				re_id = Integer.parseInt(province_parent_id);
				new MyHelper(context).execute();
				city.setVisibility(View.GONE);
				district.setVisibility(View.GONE);
				address.setVisibility(View.GONE);
				tel.setVisibility(View.GONE);
				phone.setVisibility(View.GONE);
				name.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				break;
			case R.id.district:
				re_id = Integer.parseInt(city_parent_id);
				new MyHelper(context).execute();
				district.setVisibility(View.GONE);
				address.setVisibility(View.GONE);
				tel.setVisibility(View.GONE);
				phone.setVisibility(View.GONE);
				name.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				break;
			case R.id.add:
				if (button.getText().toString().equals("添加")) {
					add();
				} else if (button.getText().toString().equals("提交修改")) {
					update();
				}
				finish();
				break;
			default:
				break;
			}
		}
	};

	private void update() {
		// TODO Auto-generated method stub
		id = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE).get(
				"user_id");
		user_id = Integer.parseInt(id);
		token = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE).get(
				"token");
		consignee = name.getText().toString();
		email = "";
		country = 0;
		
//		if(province.getText().toString().trim().equals(infoShare.getAddress(myAddressEntity.getProvince()))){
//			province_parent_id = myAddressEntity.getProvince();
//		}
//		if(city.getText().toString().trim().equals(infoShare.getAddress(myAddressEntity.getCity()))){
//			city_parent_id = myAddressEntity.getCity();
//		}
//		if(district.getText().toString().trim().equals(infoShare.getAddress(myAddressEntity.getDistrict()))){
//			district_parent_id = myAddressEntity.getDistrict();
//		}
		province.setOnClickListener(ll);
		city.setOnClickListener(ll);
		district.setOnClickListener(ll);
		addre = address.getText().toString();
		tels = tel.getText().toString();
		mobile = phone.getText().toString();
		new UpdateAddressHelper(context).execute();
	}
	OnClickListener ll = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.city:
				re_id = Integer.parseInt(province_parent_id);
				new MyHelper(context).execute();
				break;

			default:
				break;
			}
		}
	};

	private void add() {
		// TODO Auto-generated method stub
		id = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE).get(
				"user_id");
		user_id = Integer.parseInt(id);
		token = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE).get(
				"token");
		consignee = name.getText().toString();
		email = "";
		country = 0;
		addre = address.getText().toString();
		tels = tel.getText().toString();
		mobile = phone.getText().toString();
		new MyAddHelper(context).execute();
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (province.getVisibility() == View.GONE
					&& city.getVisibility() == View.GONE
					&& district.getVisibility() == View.GONE) {
				province.setVisibility(View.VISIBLE);
				province.setText(list.get(position));
				province_parent_id = entity.get(position).getRegion_id();
				re_id = Integer.parseInt(province_parent_id);
				new MyHelper(context).execute();
			} else if (province.getVisibility() == View.VISIBLE
					&& city.getVisibility() == View.GONE
					&& district.getVisibility() == View.GONE) {
				city.setVisibility(View.VISIBLE);
				city.setText(list.get(position));
				city_parent_id = entity.get(position).getRegion_id();
				re_id = Integer.parseInt(city_parent_id);
				new MyHelper(context).execute();
			} else if (province.getVisibility() == View.VISIBLE
					&& city.getVisibility() == View.VISIBLE
					&& district.getVisibility() == View.GONE) {
				district.setVisibility(View.VISIBLE);
				district.setText(list.get(position));
				district_parent_id = entity.get(position).getRegion_id();
				re_id = Integer.parseInt(district_parent_id);
				new MyHelper(context).execute();
				address.setVisibility(View.VISIBLE);
				tel.setVisibility(View.VISIBLE);
				phone.setVisibility(View.VISIBLE);
				name.setVisibility(View.VISIBLE);
			}
		}
	};

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.addaddressedit);
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

		return true;
	}

	class MyHelper extends HandlerHelp {

		public MyHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			entity = dataEntity.getData();
			Map<String, String> map = new HashMap<String, String>();
			for(AddressEntity ad:entity){
				map.put(ad.getRegion_id(), ad.getRegion_name());
			}
			infoShare.insertAddress(map);
			list = new ArrayList<String>();
			for (AddressEntity en : entity) {
				list.add(en.getRegion_name());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_expandable_list_item_1, list);
			listView.setAdapter(adapter);
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			dataEntity = model.getAddress(Integer.toString(re_id));
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	class MyAddHelper extends HandlerHelp {

		public MyAddHelper(Context context) {
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
			model.addAddress(token, consignee, email, user_id, country,
					Integer.parseInt(province_parent_id),
					Integer.parseInt(city_parent_id),
					Integer.parseInt(district_parent_id), addre, tels, mobile);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub

		}
	}

	class UpdateAddressHelper extends HandlerHelp {

		public UpdateAddressHelper(Context context) {
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

			model.updateAddress(token, address_id, consignee, email,
					user_id, country,Integer.parseInt(province_parent_id),
					Integer.parseInt(city_parent_id),
					Integer.parseInt(district_parent_id), addre, tels, mobile);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub

		}
	}
}
