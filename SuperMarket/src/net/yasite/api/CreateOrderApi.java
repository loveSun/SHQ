 package net.yasite.api;

import java.util.List;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.UserInfoParam;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class CreateOrderApi extends BaseAPI {

//	public CreateOrderApi(Context context, List<NameValuePair> pm) {
//		super(context, pm);
//		// TODO Auto-generated constructor stub
//		setMethod("http://www.yasite.net/shopapi/index.php/orderController/createOrder");
//	}

	public CreateOrderApi(Context context, List<NameValuePair> pm,
			UserInfoParam params) {
		super(context, pm, params);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/orderController/createOrder/"+params.getUser_id());
	}

	@Override
	public Object handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
