package net.yasite.api;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.GoodsListParam;
import net.yasite.entity.GoodListEntity;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class SelectByNameApi extends BaseAPI {

	public SelectByNameApi(Context context, GoodsListParam pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/goodController/searchGoodList/"+pm.getName()+"/"+pm.getPage());
		
	}

	@Override
	public GoodListEntity handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		return new Gson().fromJson(json.toString(), GoodListEntity.class);
	}

}
