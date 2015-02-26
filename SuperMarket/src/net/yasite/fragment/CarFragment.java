package net.yasite.fragment;

import java.util.List;

import com.google.gson.Gson;

import net.yasite.adapter.GoodsCarAdapter;
import net.yasite.dao.GoodsDao;
import net.yasite.entity.GoodsCarEntity;
import net.yasite.entity.UserCarDataEntity;
import net.yasite.entity.UserCarEntity;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.sharepre.UserInfoShare;
import net.yasite.test.AddressActivity;
import net.yasite.test.LoginActivity;
import net.yasite.test.R;
import net.yasite.view.XListView;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarFragment extends Fragment{
	private UserInfoShare infoShare;
	private Context context;
	private String userName,token,userId;
	private GoodsListModel model;
	private UserCarDataEntity carDataEntity;
	private List<UserCarEntity> list;
	private GoodsCarAdapter adapter;
	private XListView listView;
	private TextView edit,show;
	private CheckBox box;
	private Button button;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity();
		View view = inflater.inflate(R.layout.car_frgment, null);
		listView = (XListView) view.findViewById(R.id.list_view);
		edit = (TextView) view.findViewById(R.id.edit);
		show = (TextView) view.findViewById(R.id.price);
		box = (CheckBox) view.findViewById(R.id.all_check);
		button = (Button) view.findViewById(R.id.jie);
		TextView text_title = (TextView) view.findViewById(R.id.title_all);
		text_title.setText("  购物车");
		infoShare = new UserInfoShare(context);
		userName = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("username");
		token = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("token");
		model = new GoodsListModel(context);
		if(token==null||token.equals("")){
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("login", "car");
			getActivity().startActivityForResult(intent, 1);
		}else{
			userId = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("user_id");
			new MyHelper(context).execute();
		}
		adapter = new GoodsCarAdapter(context);
		listView.setAdapter(adapter);
		adapter.setEdit(edit);
		adapter.setPrin(show);
		adapter.setCheckBox(box);
		adapter.setButton(button);
		box.setOnCheckedChangeListener(listener);
		button.setOnClickListener(l);
		return view;
	}
	OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, AddressActivity.class);
			startActivity(intent);
		}
	};
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			for(UserCarEntity carEntity:list){
				carEntity.setCheck(isChecked);
			}
			adapter.notifyDataSetChanged();
			
		}
	};
	
	
	
	 class MyHelper extends HandlerHelp{

		public MyHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			list = carDataEntity.getData();
			adapter.setToken(token);
			adapter.setList(list);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			carDataEntity = model.getCarGoodsList(userId, token);
			
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		 
	 }

}
