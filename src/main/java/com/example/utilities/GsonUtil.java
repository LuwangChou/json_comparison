package com.example.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * 可以自己再封装一层工具类，或者使用初始包函数
 * Created by Administrator on 2020/9/4.
 */
public class GsonUtil {
    private static Gson gson = null;
    static {
        if (gson == null){
            gson = new Gson();
        }
    }

    private GsonUtil(){

    }
    /**
     * 对象转成json
     *
     * @param object
     * @return json
     */
    public static String gsonString(Object object){
        String gsonString = null;
        if(gson != null){
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * JSON转成对象
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls){
        T t = null;
        if (gson != null){
            t = gson.fromJson(gsonString,cls);
        }
        return t;
    }

    /**
     * JSON 转成list中有map的
     * @param gsonString
     * @param <T>
     * @return List<Map<String,T>>
     */
    public static <T> List<Map<String,T>> gsonToListMaps(String gsonString){
        List<Map<String,T>> list = null;
        if (gson != null){
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String,T>>>(){}.getType());
        }
        return list;
    }

    /**
     * JSON 转成map
     * @param gsonString
     * @param <T>
     * @return Map<String,T>
     */
    public static <T> Map<String,T> gsonToMaps(String gsonString){
        Map<String,T> map = null;
        if (gson != null){
            map = gson.fromJson(gsonString, new TypeToken<T>(){}.getType());
        }
        return map;
    }
}
