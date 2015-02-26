package net.yasite.fragment;

import net.yasite.entity.UserInfoData;
import net.yasite.entity.UserInfoEntity;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.sharepre.UserInfoShare;
import net.yasite.test.DetailActivity;
import net.yasite.test.HomeActivity;
import net.yasite.test.R;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistFragment extends Fragment {
	private View view;
	private EditText regist_name, regist_pass, enter_pass;
	private Button button;
	private GoodsListModel model;
	private TextView name_check, pass_check, enterpass_check;
	private String name, pass;
	private UserInfoData data;
	private UserInfoEntity entity;
	private Context context;
	private UserInfoShare infoShare;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.regist_fragmeng, null);
		context = getActivity();
		TextView text_title = (TextView) view.findViewById(R.id.title_all);
		text_title.setText("  注册");
		infoShare = new UserInfoShare(context);
		name_check = (TextView) view.findViewById(R.id.name_check);
		pass_check = (TextView) view.findViewById(R.id.pass_check);
		enterpass_check = (TextView) view.findViewById(R.id.enterpass_check);
		regist_name = (EditText) view.findViewById(R.id.regist_name);
		button = (Button) view.findViewById(R.id.register_button);
		regist_pass = (EditText) view.findViewById(R.id.regist_pass);
		enter_pass = (EditText) view.findViewById(R.id.enter_pass);
		model = new GoodsListModel(getActivity());

		button.setOnClickListener(li);
		return view;
	}

	OnClickListener li = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			name = regist_name.getText().toString();
			pass = regist_pass.getText().toString();
			String enterPass = enter_pass.getText().toString();
			Log.e(name, pass);
			if (name.length() > 3 && pass.length() > 3
					&& pass.equals(enterPass)) {
				new MyHelper(getActivity()).execute();

			} else {
				Toast.makeText(context, "请根据提示填写想过信息", 1).show();
			}
		}
	};

	class MyHelper extends HandlerHelp {

		public MyHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			String va = infoShare.getCheck();
			entity = data.getData();
			if (entity != null) {
				int id = infoShare.getGoodsId("goods", context.MODE_PRIVATE);
				infoShare.insertUserInfo("userinfo", context.MODE_PRIVATE,
						entity.getUser_name(), entity.getPassword(),
						entity.getUser_id(), entity.getToken());
				
				if(id!=-1){
					Intent intent = new Intent(getActivity(), DetailActivity.class);
					intent.putExtra("id", Integer.toString(id));
					startActivity(intent);
				} else{
					Intent intent = new Intent(getActivity(), HomeActivity.class);
//					intent.putExtra("regist", va);
					startActivity(intent);
				}
			}else {
				Toast.makeText(context, "注册失败，请稍后重试", 1).show();
			}
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			data = model.getRegist(name, pass);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
		}
	}
}
