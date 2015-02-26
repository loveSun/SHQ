package net.yasite.test;

import net.yasite.entity.UserInfoData;
import net.yasite.entity.UserInfoEntity;
import net.yasite.exit.Exit;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.sharepre.UserInfoShare;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseNewActivity {
	private EditText login_name,login_pass;
	private Button login_button;
	private TextView textView;
	private UserInfoData data;
	private UserInfoEntity entity;
	private GoodsListModel model;
	private String name,pass;
	private UserInfoShare infoShare;
	private Intent intent;
	private String extra;
	
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		
		login_name = (EditText) findViewById(R.id.login_name);
		login_pass = (EditText) findViewById(R.id.login_pass);
		login_button = (Button)findViewById(R.id.login_button);
		textView = (TextView) findViewById(R.id.regist);
		TextView text_title = (TextView) findViewById(R.id.title_all);
		text_title.setText("  登录");
		login_button.setOnClickListener(lis);
		textView.setOnClickListener(lis);
		intent = getIntent();
		Exit.getInstance().addActivity(this);
		if(intent!=null){
			if(intent.getStringExtra("login")!=null)
			extra = intent.getStringExtra("login");
		}
	}
	OnClickListener lis = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.regist:
				Intent intent = new Intent(context, HomeActivity.class);
				intent.putExtra("regist", "regist");
				startActivity(intent);
				finish();
				break;
			case R.id.login_button:
				login();
				break;
			default:
				break;
			}
		}

	};
	
	
	private void login() {
		// TODO Auto-generated method stub
		name = login_name.getText().toString();
		pass = login_pass.getText().toString();
		if(name.length()>3&&pass.length()>3){
			new MyHelper(context).execute();
			
		}else{
			Toast.makeText(context, "请输入合法的信息", 1).show();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		intent = getIntent();
		if(intent!=null){
			if(intent.getStringExtra("login")!=null)
			extra = intent.getStringExtra("login");
		}
	}
	
	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_activity);
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
	class MyHelper extends HandlerHelp{

		public MyHelper(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			entity = data.getData();
			
			
			if(entity!=null){
				if(extra!=null){
					if(extra.equals("detail")){
						Intent intent = new Intent(context, DetailActivity.class);
						infoShare = new UserInfoShare(context);
						infoShare.insertUserInfo("userinfo", context.MODE_PRIVATE,
								entity.getUser_name(), entity.getPassword(),
								entity.getUser_id(), entity.getToken());
						int goodsid = infoShare.getGoodsId("goods", MODE_PRIVATE);
						intent.putExtra("id", Integer.toString(goodsid));
						startActivity(intent);
						finish();
					}else if(extra.equals("car")){
						infoShare = new UserInfoShare(context);
						infoShare.insertUserInfo("userinfo", context.MODE_PRIVATE,
								entity.getUser_name(), entity.getPassword(),
								entity.getUser_id(), entity.getToken());
						Intent intent = new Intent(context, HomeActivity.class);
						setResult(1, intent);
						finish();
					}else if(extra.equals("own")){
						infoShare = new UserInfoShare(context);
						infoShare.insertUserInfo("userinfo", context.MODE_PRIVATE,
								entity.getUser_name(), entity.getPassword(),
								entity.getUser_id(), entity.getToken());
						Intent intent = new Intent(context, HomeActivity.class);
						intent.putExtra("own", "own");
						setResult(2, intent);
						finish();
					}
				}
			}else{
				Toast.makeText(context, "您的用户信息有误，请重新输入", 1).show();
			}
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			data = model.getLogin(name, pass);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}

}
