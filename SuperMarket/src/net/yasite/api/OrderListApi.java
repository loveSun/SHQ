package net.yasite.api;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.UserInfoParam;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class OrderListApi extends BaseAPI {

	public OrderListApi(Context context, UserInfoParam pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/orderController/getOrderList/"+pm.getUser_id());
	}

	@Override
	public Object handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
