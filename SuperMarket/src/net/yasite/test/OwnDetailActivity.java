package net.yasite.test;

import net.yasite.entity.UserInfoData;
import net.yasite.entity.UserInfoEntity;
import net.yasite.exit.Exit;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OwnDetailActivity extends BaseNewActivity {
	private GoodsListModel model;
	private Context context;
	private String user_id,user_name,token;
	private EditText user_names,user_qq,user_email,user_sex,user_phone;
	private UserInfoData data;
	private UserInfoEntity entity;
	private Button update;
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		context = this;
		TextView textView = (TextView) findViewById(R.id.title_all);
		textView.setText("用户详情");
		user_email = (EditText) findViewById(R.id.user_email);
		user_names = (EditText) findViewById(R.id.user_name);
		user_phone = (EditText) findViewById(R.id.user_phone);
		user_sex = (EditText) findViewById(R.id.user_sex);
		user_qq = (EditText) findViewById(R.id.user_qq);
		update = (Button) findViewById(R.id.update);
		update.setOnClickListener(l);
		Exit.getInstance().addActivity(this);
	}
	OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new MyHelper(context).execute();
		}
	};

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.owndetail_activity);
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
			entity = data.getData();
			user_email.setText(entity.getEmail());
			user_names.setText(entity.getUser_name());
			user_phone.setText(entity.getMobile_phone());
			user_sex.setText(entity.getSex());
			user_qq.setText(entity.getQq());
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			if(data==null){
				data = model.ownMsg(Integer.parseInt(user_id), user_name, token);
			}else{
				model.updateUserInfo(user_id, token, entity.getUser_name(), entity.getSex(), entity.getQq(), entity.getMobile_phone(), entity.getEmail());
			}
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
		}
	}
}
