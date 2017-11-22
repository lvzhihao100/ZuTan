package com.eqdd.common.utils;



import com.eqdd.common.bean.BaseBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtils {

	

	public static String createGsonString(Object object) {
		Gson gson = new Gson();
		String gsonString = gson.toJson(object);
		return gsonString;
	}

	public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
		T t = null;
		try {
			if (cls==String.class){
                return (T)gsonString;
            }
			Gson gson = new Gson();
			t = gson.fromJson(gsonString, cls);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			System.out.println("changeGsonToBean异常");
		}
		return t;
	}

	public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
		Gson gson = new Gson();

		List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
		}.getType());
		ArrayList<T> ts = new ArrayList<>();
		for (int i=0;i<list.size();i++){
			T t = GsonUtils.changeGsonToBean(GsonUtils.createGsonString(list.get(i)), cls);
			ts.add(t);
		}
		return ts;
	}

	public static <T> BaseBean<T> changeGsonToBaseBean(String gsonString, Class<T> cls) {
		Gson gson = new Gson();
		BaseBean<T> list = gson.fromJson(gsonString, new TypeToken<BaseBean<T>>() {
		}.getType());
		ArrayList<T> ts = new ArrayList<>();
		for (int i=0;i<list.getItems().size();i++){
			T t = GsonUtils.changeGsonToBean(GsonUtils.createGsonString(list.getItems().get(i)), cls);
			ts.add(t);
		}
		list.setItems(ts);
		return list;
	}


	public static <T> List<Map<String, T>> changeGsonToListMaps(
			String gsonString) {
		List<Map<String, T>> list = null;
		Gson gson = new Gson();
		list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
		return list;
	}

	public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
		Map<String, T> map = null;
		Gson gson = new Gson();
		map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}

	
	
	
	
}
