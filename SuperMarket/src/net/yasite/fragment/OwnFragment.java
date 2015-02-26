package net.yasite.fragment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.yasite.fragment.CarFragment.MyHelper;
import net.yasite.model.GoodsListModel;
import net.yasite.net.HandlerHelp;
import net.yasite.sharepre.UserInfoShare;
import net.yasite.test.AddressActivity;
import net.yasite.test.LoginActivity;
import net.yasite.test.OrderActivity;
import net.yasite.test.OwnDetailActivity;
import net.yasite.test.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class OwnFragment extends Fragment {
	private TextView text_order,text_menu,text_address,text_own,images,cemare;
	private ImageView imageView;
	private String token,user_name;
	private String user_id,path;
	private Context context;
	private UserInfoShare infoShare;
	private GoodsListModel model;
	private View pop_layout;
	private PopupWindow popupWindow;
	String bu;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity();
		View view = inflater.inflate(R.layout.own_fragment, null);
		TextView text_title = (TextView) view.findViewById(R.id.title_all);
		text_title.setText("  我的淘宝");
		model = new GoodsListModel(context);
		infoShare = new UserInfoShare(context);
		user_name = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("username");
		token = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("token");
		text_menu = (TextView) view.findViewById(R.id.menu);
		text_address = (TextView) view.findViewById(R.id.address);
		text_own = (TextView) view.findViewById(R.id.own);
		imageView = (ImageView) view.findViewById(R.id.head);
		text_order = (TextView) view.findViewById(R.id.order);
		if(token==null||token.equals("")){
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("login", "own");
			getActivity().startActivityForResult(intent, 2);
		}else{
			user_id = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("user_id");
			user_name = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("username");
			token = (String) infoShare.getUserInfo("userinfo", context.MODE_PRIVATE).get("token");
			
		}
		text_own.setOnClickListener(lis);
		text_address.setOnClickListener(lis);
		imageView.setOnClickListener(lis);
		pop_layout = inflater.inflate(R.layout.pop_layout, null);
		images = (TextView) pop_layout.findViewById(R.id.images);
		cemare = (TextView) pop_layout.findViewById(R.id.cemare);
		images.setOnClickListener(lis);
		cemare.setOnClickListener(lis);
		text_order.setOnClickListener(lis);
		popupWindow = new PopupWindow(pop_layout, 200, 200);
		return view;
	}
	OnClickListener lis = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.own:
				Intent intent = new Intent(context, OwnDetailActivity.class);
				intent.putExtra("user_id", user_id);
				intent.putExtra("user_name", user_name);
				intent.putExtra("token", token);
				startActivity(intent);
				break;
			case R.id.address:
				Intent intent1 = new Intent(context, AddressActivity.class);
				intent1.putExtra("user_id", user_id);
				intent1.putExtra("user_name", user_name);
				intent1.putExtra("token", token);
				startActivity(intent1);
				break;
			case R.id.head:
				popupWindow.showAsDropDown(v);
				break;
			case R.id.images:
				Intent intent_images = new Intent(Intent.ACTION_PICK);
				intent_images.setType("image/*");// 相片类型
				startActivityForResult(intent_images, 2);
				break;
			case R.id.cemare:
				Intent intent_cemare = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent_cemare, 1);
				break;
			case R.id.order:
				Intent intent2 = new Intent(context, OrderActivity.class);
				intent2.putExtra("user_id", user_id);
				startActivity(intent2);
				break;
			default:
				break;
			}
		}
	};
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1){
			if(data!=null){
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HHmmss");
				String fileName = format.format(date);
				File file = new File("/mnt/sdcard/"+fileName+".jpg");
				path = file.getAbsolutePath();
				BufferedOutputStream stream = null;
				try {
					stream = new BufferedOutputStream(new FileOutputStream(file));
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				if(bitmap!=null){
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					try {
						stream.flush();
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					imageView.setImageBitmap(bitmap);
					try {
						FileInputStream inputStream = new FileInputStream(file);
						byte[] bs = new byte[2048];
						int len = 0;
						try {
							while((len=inputStream.read(bs))!=-1){
								bu = new String(bs, 0, len);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					new HeadHelper(context).execute();
				}
			}
			
		}else if(requestCode==2){
			Uri uri = data.getData();
			Bitmap bitmap = null;
			try {
				bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
				path = getRealPathFromURI(uri);
				FileInputStream inputStream = new FileInputStream(new File(path));
				byte[] bs = new byte[2048];
				int len = 0;
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				while((len=inputStream.read(bs))!=-1){
					outputStream.write(bs, 0, len);
					bu = new String(outputStream.toByteArray());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imageView.setImageBitmap(bitmap);
			new HeadHelper(context).execute();
		}
	}
	public String getRealPathFromURI(Uri contentUri) {

		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null,
				null, null);
		if (cursor.moveToFirst()) {
			;
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		
		return res;
	}
	class HeadHelper extends HandlerHelp{

		public HeadHelper(Context context) {
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
			
			model.head(user_id, token, path);
		}

		@Override
		public void doTaskAsNoNetWork(Message msg) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
