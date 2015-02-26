package net.yasite.adapter;

import java.util.ArrayList;
import java.util.List;

import net.yasite.entity.AddressEntity;
import net.yasite.entity.MyAddressEntity;
import net.yasite.sharepre.UserInfoShare;
import net.yasite.test.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class AddressAdapter extends YasiteAdapter {
	private List<MyAddressEntity> list = new ArrayList<MyAddressEntity>();
	private UserInfoShare infoShare = null;
	public AddressAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		infoShare = new UserInfoShare(context);
	}
	
	public List<MyAddressEntity> getList() {
		return list;
	}

	public void setList(List<MyAddressEntity> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	protected void setupChildViews(View convertView, ViewHolder holder) {
		// TODO Auto-generated method stub
		AddressViewHolder holder2 = (AddressViewHolder) holder;
		holder2.text_name = (TextView) convertView.findViewById(R.id.name);
		holder2.text_mobile = (TextView) convertView.findViewById(R.id.mobile);
		holder2.text_address = (TextView) convertView.findViewById(R.id.address);
	}

	@Override
	protected void setChildViewData(ViewHolder holder, int position, Object obj) {
		// TODO Auto-generated method stub
		AddressViewHolder holder2 = (AddressViewHolder) holder;
		if(obj instanceof MyAddressEntity){
			MyAddressEntity entity = (MyAddressEntity) obj;
			holder2.text_name.setText(entity.getConsignee());
			holder2.text_mobile.setText(entity.getMobile());
			holder2.text_address.setText(infoShare.getAddress(entity.getProvince())+""+infoShare.getAddress(entity.getCity())+""+infoShare.getAddress(entity.getDistrict())+""+entity.getAddress());
		}
	}

	@Override
	protected ViewHolder setHolder() {
		// TODO Auto-generated method stub
		return new AddressViewHolder();
	}

	@Override
	protected void setLayoutResource(int position) {
		// TODO Auto-generated method stub
		layoutId = R.layout.address_item;
	}
	class AddressViewHolder extends ViewHolder{
		TextView text_name,text_mobile,text_address;
	}

}
