package net.yasite.api;

import java.util.List;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.UserInfoParam;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class DelCarGoodsApi extends BaseAPI {

	public DelCarGoodsApi(Context context, List<NameValuePair> pm,
			UserInfoParam params) {
		super(context, pm, params);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/cartController/delGood/"+params.getToken());
		
	}

	@Override
	public Object handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return json.toString();
	}

}
