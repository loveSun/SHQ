package net.yasite.api;

import net.yasite.api.params.BaseHttpParam;
import net.yasite.api.params.ClassParam;
import net.yasite.entity.ClassifyDataEntity;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;

public class ClassApi extends BaseAPI {

	public ClassApi(Context context, ClassParam pm) {
		super(context, pm);
		// TODO Auto-generated constructor stub
		setMethod("http://www.yasite.net/shopapi/index.php/goodController/getCategory/"+pm.getId());
	}

	@Override
	public ClassifyDataEntity handlerResult(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		
		return new Gson().fromJson(json.toString(), ClassifyDataEntity.class);
	}

}
