package net.yasite.fragment;

import java.util.ArrayList;
import java.util.List;

import net.yasite.adapter.ClassifyAdapter;
import net.yasite.entity.ClassifyDataEntity;
import net.yasite.entity.ClassifyEntity;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.test.R;
import net.yasite.view.XListView;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ClassiFragment extends Fragment{
	private Context context;
	private GoodsListModel model;
	private ClassifyDataEntity dataEntity,dataEntity1;
	private List<ClassifyEntity> entity,entity1;
	private XListView listView,listView2;
	private int c = 0;
	private ClassifyAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.classify_fragment, null);
		listView = (XListView) view.findViewById(R.id.list_view);
		listView2 = (XListView) view.findViewById(R.id.list_vi);
		context = getActivity();
		model = new GoodsListModel(context);
		TextView text_title = (TextView) view.findViewById(R.id.title_all);
		text_title.setText("  分类");
		listView.setOnItemClickListener(listener);
		adapter = new ClassifyAdapter(context);
		new MyHelper(context).execute();
		return view;
	}
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			
			c = Integer.parseInt(entity.get(position-1).getCat_id());
			new MyHelper1(context).execute();
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
			entity = dataEntity.getData();
			List<String> list = new ArrayList<String>();
				c++;
				new MyHelper1(context).execute();
		
				for(ClassifyEntity en:entity){
					list.add(en.getCat_name());
				}
				adapter = new ClassifyAdapter(context);
				listView.setAdapter(adapter);
				adapter.setList(list);
				adapter.notifyDataSetChanged();
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			dataEntity = model.getClassify(c);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
	}
	class MyHelper1 extends HandlerHelp{

		public MyHelper1(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void updateUI() {
			// TODO Auto-generated method stub
			entity1 = dataEntity1.getData();
			List<String> list = new ArrayList<String>();
				for(ClassifyEntity en:entity1){
					list.add(en.getCat_name());
				}
				adapter = new ClassifyAdapter(context);
				listView2.setAdapter(adapter);
				adapter.setList(list);
				adapter.notifyDataSetChanged();
		}

		@Override
		public void doTask(Message msg) throws Exception {
			// TODO Auto-generated method stub
			dataEntity1 = model.getClassify(c);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
