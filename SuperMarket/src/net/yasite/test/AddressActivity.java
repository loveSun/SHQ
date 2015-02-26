package net.yasite.test;

import java.util.List;

import net.yasite.adapter.AddressAdapter;
import net.yasite.entity.AddressDataEntity;
import net.yasite.entity.AddressInfoEntity;
import net.yasite.entity.MyAddressDataEntity;
import net.yasite.entity.MyAddressEntity;
import net.yasite.exit.Exit;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.sharepre.UserInfoShare;
import net.yasite.view.XListView;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddressActivity extends BaseNewActivity {
	private Button button;
	private XListView listView;
	private String user_id,user_name,token;
	private GoodsListModel model;
	private UserInfoShare infoShare;
	private MyAddressDataEntity dataEntity;
	private List<MyAddressEntity> entity;
	private MyAddressEntity entity3;
	private AddressAdapter adapter;
	private String address_id;
	private AddressInfoEntity infoEntity;
	private Intent intent,intent1;
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		listView = (XListView) findViewById(R.id.list_view);
		TextView text_title = (TextView) findViewById(R.id.title_all);
		text_title.setText("   用户地址管理");
		infoShare = new UserInfoShare(context);
		button = (Button) findViewById(R.id.add_address);
		button.setOnClickListener(lis);
		adapter = new AddressAdapter(context);
		listView.setOnItemClickListener(listener);
		registerForContextMenu(listView);
		user_id = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE)
				.get("user_id");
		token = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE)
				.get("token");
		user_name = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE)
				.get("username");
		Exit.getInstance().addActivity(this);
	}
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			address_id = entity.get(position-1).getAddress_id();
			new AddressInfoHelper(context).execute();
			intent1 = new Intent(context, CreateOrderActivity.class);
			
		}
	};
	OnClickListener lis = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, AddAddressEditActivity.class);
			intent.putExtra("user_id", user_id);
			intent.putExtra("user_name", user_name);
			intent.putExtra("token", token);
			startActivityForResult(intent, 1);
		}
	};

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.address_activity);
	}

	@Override
	public void setModel() {
		// TODO Auto-generated method stub
		model = new GoodsListModel(context);
		listView.setAdapter(adapter);
		new MyHelper(context).execute();

	}

	@Override
	public boolean getIntentValue() {
		// TODO Auto-generated method stub
		user_id = getIntent().getStringExtra("user_id");
		user_name = getIntent().getStringExtra("user_name");
		token = getIntent().getStringExtra("token");
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
			entity = dataEntity.getData();
			adapter.setList(entity);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			dataEntity = model.getAddressList(user_id, token);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	
		String id = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE)
				.get("user_id");
//		user_id = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE)
//				.get("user_id");
//		token = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE)
//				.get("token");
//		user_name = (String) infoShare.getUserInfo("userinfo", MODE_PRIVATE)
//				.get("username");
		new MyHelper(context).execute();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		 menu.add(1, 1, 1, "删除");
		 menu.add(2, 2, 2, "修改");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo(); 
		int position = menuInfo.position-1;
		Log.e("position", position+"");
		switch (item.getItemId()) {
		case 1:
			address_id = entity.get(position).getAddress_id();
			new DeleteHelper(context).execute();
			entity.remove(position);
			adapter.notifyDataSetChanged();
			break;
		case 2:
			address_id = entity.get(position).getAddress_id();
			new AddressInfoHelper(context).execute();
			intent = new Intent(context, AddAddressEditActivity.class);
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	class DeleteHelper extends HandlerHelp{

		public DeleteHelper(Context context) {
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
			model.deleteAddress(token, address_id);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
		}
	}
	class AddressInfoHelper extends HandlerHelp{

		public AddressInfoHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			entity3 = infoEntity.getData();
			if(intent!=null){
				intent.putExtra("info", entity3);
				startActivityForResult(intent, 4);
			}
			if(intent1!=null){
				intent1.putExtra("info", entity3);
				intent1.putExtra("user_id", user_id);
				startActivityForResult(intent1, 4);
			}
			
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			infoEntity = model.addressInfo(token, user_id, address_id);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
	}
}
