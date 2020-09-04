package com.example.utilities;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * 可以自己再封装一层工具类，或者使用初始包函数
 * Created by Administrator on 2020/9/4.
 */
public class JacksonUtil {
    private static ObjectMapper mapper = null;
    static{
        if (mapper == null){
            mapper = new ObjectMapper();
        }
    }
    private JacksonUtil(){

    }


    /**
     *  功能描述：把java对象转成JSON数据
     * @param object
     * @return jsonData
     */
    public static String getJacksonJsonString(Object object){
        String jsonData = null;
        if (mapper != null){
            try{
                jsonData = mapper.writeValueAsString(object);
            }catch(JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return jsonData;
    }
    /**
     * 功能描述：把JSON数据转换成指定的对象list
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return 指定的java对象
     */
    public static <T> T getJacksonJsonToBean(String jsonData, Class<T> clazz) {
        T t = null;
        try{
            t = mapper.readValue(jsonData,clazz);
        }catch(IOException e){
            e.printStackTrace();
        }
        return t;
    }


    /**
     * 功能描述：把JSON数据转换成指定的java对象map
     * @param jsonData JSON数据
     * @return List<T>
     */
    public static <T> Map<String,T> getJacksonJsonToMap(String jsonData,Class<T> clazz){
        Map<String, T> map = null;
        try{
            map = mapper.readValue(jsonData,Map.class);
        }catch(IOException e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     * @param jsonData JSON数据
     * @return List<T>
     */
    public static <T> List<T> getJacksonJsonToList(String jsonData,Class<T> clazz){
        List<T> list = null;
        //method 2
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        if ( mapper != null){
            try{
                //method 2
                list = mapper.readValue(jsonData,javaType);
                //method 1
                //list = mapper.readValue(jsonData, new TypeReference<List<T>>(){/**/});
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return list;
    }
    /**
     * 功能描述：把JSON数据转换成较为复杂的List<Map<String, T>>
     * @param jsonData JSON数据
     * @return List<Map<String, Object>>
     */
    public static <T> List<Map<String,T>> getJacksonJsonToListMap(String jsonData,Class<T> clazz) {
        List<Map<String,T>> listMap = null;
        JavaType map_string_t = mapper.getTypeFactory().constructParametricType(Map.class,String.class, clazz);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, map_string_t);
        if(mapper != null){
            try{
                listMap = mapper.readValue(jsonData,javaType);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return listMap;
    }
}
