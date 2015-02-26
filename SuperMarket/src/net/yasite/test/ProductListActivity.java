package net.yasite.test;

import java.util.ArrayList;
import java.util.List;

import net.yasite.adapter.GoodsAdapter;
import net.yasite.dao.GoodsDao;
import net.yasite.entity.GoodEntity;
import net.yasite.entity.GoodListEntity;
import net.yasite.exit.Exit;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.view.XListView;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ProductListActivity extends BaseNewActivity implements OnScrollListener {
	private AutoCompleteTextView autoCompleteTextView;
	int zhong = 1;
	private ImageButton button;
	private GoodsDao dao = new GoodsDao(ProductListActivity.this);
	private List<String> list;
//	private SelectModel model;
	GoodsListModel model;
	private GoodListEntity entity;
	String naem;
	int page = 1;
	private List<GoodEntity> list2,list1 = new ArrayList<GoodEntity>();
	private XListView listView;
	private GoodsAdapter adapter;
	private Context context;
	boolean isLast = false;
	private String goods_id;
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		context = this;
		listView = (XListView) findViewById(R.id.list_view);
		listView.setOnItemClickListener(listener);
		Exit.getInstance().addActivity(this);
	}
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			goods_id = list1.get(position-1).getGoods_id();
			Intent intent = new Intent(context, DetailActivity.class);
			intent.putExtra("id", goods_id);
			startActivity(intent);
		}
	};
	OnClickListener lis = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "=============", 1).show();
			 naem = autoCompleteTextView.getText().toString();
			if(naem.length()>0){
				model = new GoodsListModel(context);
//				model = new SelectModel(context);
				adapter = new GoodsAdapter(context);
				new MyHelper(context).execute();
				listView.setAdapter(adapter);
				if(!list.contains(naem)){
					dao.insert(naem, zhong);
				}
			}else if(naem.length()<1){
				Toast.makeText(context, "请输入商品的关键字", 1).show();
			}
		}
	};

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_list);
	}

	@Override
	public void setModel() {
		// TODO Auto-generated method stub
		list = dao.select(zhong);
		Log.e("=======", list+"");
		if(list.size()>0){
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1,list);
			autoCompleteTextView.setAdapter(adapter);
		}
		
	}

	@Override
	public boolean getIntentValue() {
		// TODO Auto-generated method stub
	
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		button = (ImageButton) findViewById(R.id.imageButton1);
		button.setOnClickListener(lis);
		if(getIntent().getExtras()!=null){
			if(getIntent().getStringExtra("tag").equals("dian")){
				autoCompleteTextView.setHint("请输入要搜索的店铺名称");
				zhong = 2;
			}
		}else{
			autoCompleteTextView.setHint("请输入要查询的商品名称");
		}
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
			list2 = entity.getData();
			list1.addAll(list2);
			adapter.setList(list1);
			adapter.notifyDataSetChanged();
			listView.setOnScrollListener(ProductListActivity.this);
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			entity = model.getData(naem, page);
			if (entity.getData().size() > 0) {
				page++;
			}
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (isLast
				&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			new MyHelper(context).execute();
			isLast = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (firstVisibleItem + visibleItemCount == totalItemCount
				&& totalItemCount > 0) {
			isLast = true;
		}
	}
}
