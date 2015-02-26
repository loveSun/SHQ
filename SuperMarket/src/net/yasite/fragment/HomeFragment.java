package net.yasite.fragment;

import java.util.ArrayList;
import java.util.List;

import cache.loader.ImageWorker;

import net.yasite.adapter.GoodsAdapter;
import net.yasite.entity.GoodEntity;
import net.yasite.entity.GoodListEntity;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.test.DetailActivity;
import net.yasite.test.MainActivity;
import net.yasite.test.ProductListActivity;
import net.yasite.test.R;
import net.yasite.test.MainActivity.MyHandler;
import net.yasite.test.R.id;
import net.yasite.view.XListView;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HomeFragment extends Fragment implements OnScrollListener {
	private XListView listView;
	private GoodsListModel goodsListModel;
	private GoodsAdapter adapter;
	private GoodListEntity entity;
	private Context context;
	private boolean isLast;
	private int page = 1;
	ImageView im1, im2, im3, im4, im5;
	private List<GoodEntity> list, list1 = new ArrayList<GoodEntity>();
	private ViewFlipper vf = null;
	private ImageButton rightBtn, leftBtn;
	private EditText searchEditText;
	private RadioGroup group;
	private Intent intent;
	private ProgressBar bar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity();
		View view = inflater.inflate(R.layout.activity_main, null);
		View view2 = inflater.inflate(R.layout.fragment_home, null);
		View footer = inflater.inflate(R.layout.xlist_foot_progress, null);
		TextView text_title = (TextView) view.findViewById(R.id.title_all);
		text_title.setText("  首页");
		bar = (ProgressBar) footer.findViewById(R.id.progressBar1);
		searchEditText = (EditText) view
				.findViewById(R.id.home_search_edittext);
		group = (RadioGroup) view.findViewById(R.id.radiogroup);
		vf = (ViewFlipper) view2.findViewById(R.id.vf);
		leftBtn = (ImageButton) view2.findViewById(R.id.leftBtn);
		rightBtn = (ImageButton) view2.findViewById(R.id.rightBtn);

		im1 = (ImageView) view2.findViewById(R.id.im1);
		im2 = (ImageView) view2.findViewById(R.id.im2);
		im3 = (ImageView) view2.findViewById(R.id.im3);
		im4 = (ImageView) view2.findViewById(R.id.im4);
		im5 = (ImageView) view2.findViewById(R.id.im5);
		listView = (XListView) view.findViewById(R.id.list_view);
		listView.addHeaderView(view2);
		goodsListModel = new GoodsListModel(context);
		adapter = new GoodsAdapter(context);
		new MyHandler(context).execute();
		listView.setAdapter(adapter);
		vf.startFlipping();
		searchEditText.setOnClickListener(l);
		group.setOnCheckedChangeListener(listener);
		intent = new Intent(getActivity(), ProductListActivity.class);
		listView.setOnItemClickListener(listener1);
		bar = new SeekBar(getActivity());
		listView.addFooterView(footer);

		bar.setVisibility(View.GONE);
		return view;
	}

	OnItemClickListener listener1 = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), DetailActivity.class);
			intent.putExtra("id", list1.get(position - 2).getGoods_id());
			startActivity(intent);
		}
	};
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radio_goods:
				intent.putExtra("tag", "goods");
				break;
			case R.id.radio_dian:
				intent.putExtra("tag", "dian");
				break;
			default:
				break;
			}
		}
	};
	OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.home_search_edittext:
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};

	public class MyHandler extends HandlerHelp {

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
			listView.setOnScrollListener(HomeFragment.this);
			adapter.notifyDataSetChanged();

			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"
					+ list.get(0).getGoods_img(), im1, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"
					+ list.get(1).getGoods_img(), im2, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"
					+ list.get(2).getGoods_img(), im3, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"
					+ list.get(3).getGoods_img(), im4, R.drawable.ic_launcher);
			ImageWorker.getImage(context, "http://www.yasite.net/ecshop/"
					+ list.get(4).getGoods_img(), im5, R.drawable.ic_launcher);
			leftBtn.setOnClickListener(l);
			rightBtn.setOnClickListener(l);
			vf.setOnClickListener(l);
		}

		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.leftBtn:
					vf.setOutAnimation(getActivity().getApplicationContext(),
							R.anim.left);
					vf.setInAnimation(getActivity().getApplicationContext(),
							R.anim.right);
					vf.showNext();
					vf.stopFlipping();
					break;
				case R.id.rightBtn:
					vf.setOutAnimation(getActivity().getApplicationContext(),
							R.anim.right1);
					vf.setInAnimation(getActivity().getApplicationContext(),
							R.anim.left1);
					vf.showPrevious();
					vf.stopFlipping();
					break;
				case R.id.vf:
					System.out.println(AnimationUtils
							.loadAnimation(getActivity()
									.getApplicationContext(), R.anim.right)
							+ "-"
							+ AnimationUtils.loadAnimation(getActivity()
									.getApplicationContext(), R.anim.left)
							+ AnimationUtils.loadAnimation(getActivity()
									.getApplicationContext(), R.anim.right1)
							+ "-"
							+ AnimationUtils.loadAnimation(getActivity()
									.getApplicationContext(), R.anim.left1)
							+ "-"
							+ vf.getInAnimation()
							+ "-"
							+ vf.getOutAnimation());
					if (vf.getInAnimation() == AnimationUtils
							.loadAnimation(getActivity()
									.getApplicationContext(), R.anim.right)) {
						vf.setOutAnimation(getActivity()
								.getApplicationContext(), R.anim.right1);
						vf.setInAnimation(
								getActivity().getApplicationContext(),
								R.anim.left1);
						System.out.println(AnimationUtils.loadAnimation(
								getActivity().getApplicationContext(),
								R.anim.right));
						vf.showPrevious();
					} else {
						vf.setOutAnimation(getActivity()
								.getApplicationContext(), R.anim.left);
						vf.setInAnimation(
								getActivity().getApplicationContext(),
								R.anim.right);
						vf.showPrevious();
					}
					if (!vf.isFlipping())
						vf.startFlipping();
					break;

				}
			}
		};

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			entity = goodsListModel.getGoodList(page);
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
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (firstVisibleItem + visibleItemCount == totalItemCount
				&& totalItemCount > 0) {
			isLast = true;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

		if (isLast
				&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			bar.setVisibility(View.VISIBLE);
			handler.sendEmptyMessageDelayed(1, 2500);

			isLast = false;
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			new MyHandler(context).execute();
			bar.setVisibility(View.GONE);
		}

	};
}
