package net.yasite.test;

import android.content.Context;
import android.os.Message;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;

public class OrderActivity extends BaseNewActivity {
	private String user_id;
	private GoodsListModel model;
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		new MyHelper(context).execute();
	}

	@Override
	public void setContent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModel() {
		// TODO Auto-generated method stub
		model = new GoodsListModel(context);
	}

	@Override
	public boolean getIntentValue() {
		// TODO Auto-generated method stub
		user_id = getIntent().getStringExtra("user_id");
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
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			model.getOrderList(user_id);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
	}
}
