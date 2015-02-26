package net.yasite.test;


import java.util.ArrayList;
import java.util.List;



import cache.loader.ImageWorker;

import net.yasite.adapter.GoodsAdapter;
import net.yasite.entity.GoodEntity;
import net.yasite.entity.GoodListEntity;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.view.XListView;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AbsListView.OnScrollListener;

public class MainActivity extends BaseNewActivity implements OnScrollListener{
	private XListView listView;
	private GoodsListModel goodsListModel;
	private GoodsAdapter adapter;
	private GoodListEntity entity;
	private View guang;
	private Context context;
	private ViewFlipper vf = null;
	private ImageButton rightBtn, leftBtn;
	ImageView im1,im2,im3,im4,im5,im6;
	private boolean isLast;
	private int page = 1;
	private List<GoodEntity> list,list1 = new ArrayList<GoodEntity>();
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		context = this;
		listView = (XListView) findViewById(R.id.list_view);
		vf.startFlipping();
		listView.addHeaderView(guang);
	}	
	

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public void setModel() {
		
		goodsListModel = new GoodsListModel(context);
		adapter = new GoodsAdapter(context);
		new MyHandler(context).execute();
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean getIntentValue() {
		// TODO Auto-generated method stub
		return true;
	}
	public class MyHandler extends HandlerHelp{

		public MyHandler(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			list = entity.getData();
			
			list1.addAll(list);
			adapter.setList(list1);
			listView.setOnScrollListener(MainActivity.this);
			Log.e("--------------", list1.size()+"]]");
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"+list1.get(0).getGoods_img(), im1, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"+list1.get(1).getGoods_img(), im2, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"+list1.get(2).getGoods_img(), im3, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"+list1.get(3).getGoods_img(), im4, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"+list1.get(4).getGoods_img(), im5, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"+list1.get(5).getGoods_img(), im6, R.drawable.ic_launcher);
			adapter.notifyDataSetChanged();
			
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			entity = goodsListModel.getGoodList(page);
			if(entity.getData().size()>0){
				page++;
			}
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		Log.e("========================",totalItemCount+"totalItemCount0");
		if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
			isLast = true;
			}
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
		if (isLast && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
			new MyHandler(context).execute();
			isLast = false;
		}
	}
}
