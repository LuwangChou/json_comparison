import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.*;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
结论：
序列化：
小量数据 FASTJSON > GSON  > JACKSON (100k以下)
大量数据：JACKSON > FASTJSON > GSON

Result 测试结果(三次）
----------------------100 start--------------------------
gson time used = 21ms
fastjson serilize time used  = 14ms
jackson serialize time used = 151ms

----------------------100 end----------------------------
----------------------10K start--------------------------
gson time used = 50ms
fastjson serilize time used = 36ms
jackson serialize time used = 179ms
----------------------10K end----------------------------

----------------------1M start--------------------------
gson time used = 1332ms
fastjson serilize time used = 736ms
jackson serialize time used = 548ms
----------------------1M end----------------------------
----------------------100M start--------------------------
内存爆了
----------------------100M end----------------------------

============================================================
反序列化 小量数据 1M一下 效率类似，大量数据 FASTJSON > JACKSON > GSON
Start: test user number100
gson deserialize time used: 4ms
fastjson deserialize time used: 7ms
jackson deserialize time used: 17ms

----------------------------------------------------------
10K
gson deserialize time used: 55ms
fastjson deserialize time used: 46ms
jackson deserialize time used: 39ms


----------------------------------------------------------
1M
gson deserialize time used: 1496ms
fastjson deserialize time used: 557ms
jackson deserialize time used: 687ms
============================================================
 */
/**
 * Created by Administrator on 2020/9/4.
 */
public class JsonComparisonDemo {

    public static void testSerializeJsonLoop(int number) throws JsonProcessingException{
        List<User> users = new ArrayList<User>();
        if (number <= 0){
            return;
        }
        for (int i = 0; i < 100; i++){
            User user = new User();
            user.setId(1000000+i);
            user.setName("zhangsan"+i);
            user.setBirthday((new Date()).toString());
            users.add(user);
        }
        System.out.println("Start: test user number"+number);
        long sum = 0L;
        long start = 0L;
        long end = 0L;
        String gsonString = "";
        for(int k = 0; k < 3 ; k++) {
            //Test GSON serialize
            Gson gson = new Gson();
            start = System.currentTimeMillis();
            gsonString = gson.toJson(users);
            end = System.currentTimeMillis();
            sum = sum + end - start;
        }
        System.out.println("gson time used" + sum/3 + "ms");

        sum = 0L;
        for(int k = 0; k < 3 ; k++) {
            //Test GSON deserialize
            start = System.currentTimeMillis();
            List<User> retGsonUsers = JSON.parseArray(gsonString, User.class);
            end = System.currentTimeMillis();
            sum = sum + end - start;
        }
        System.out.println("gson deserilize time used " + sum/3 + "ms");

        sum = 0L;
        String jsonString = "";
        for(int k = 0; k < 3 ; k++) {
            //FastJson serilize
            start = System.currentTimeMillis();
            jsonString = JSON.toJSONString(users);
            end = System.currentTimeMillis();
            sum = sum + end - start;
        }
        System.out.println("fastjson serilize time used " + sum/3 + "ms");

        sum = 0L;
        for (int k = 0; k < 3; k++) {
            //FastJson deserialize
            start = System.currentTimeMillis();
            List<User> retJsonUsers = JSON.parseArray(jsonString, User.class);
            end = System.currentTimeMillis();
            sum = sum + end - start;
        }
        System.out.println("fastjson deserilize time used " + sum/3 + "ms");

        sum = 0L;
        String jacksonString = "";
        for (int k = 0; k < 3; k++) {
            //Jackson
            start = System.currentTimeMillis();
            ObjectMapper mapper = new ObjectMapper();
            jacksonString = mapper.writeValueAsString(users);
            end = System.currentTimeMillis();
            sum = sum + end - start;
        }
        System.out.println("jackson serialize time used" + sum/3 + "ms");
        sum = 0L;
        for (int k = 0; k < 3; k++) {
            //Jackson deserialize
            start = System.currentTimeMillis();
            ObjectMapper deMapper = new ObjectMapper();
            deMapper.writeValueAsString(users);
            try {
                List<User> retJacksonUser;
                retJacksonUser = deMapper.readValue(jacksonString, List.class);
                retJacksonUser.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            end = System.currentTimeMillis();
            sum = sum + end - start;
        }
        System.out.println("jackson deserialize time used" + sum/3 + "ms");
        System.out.println("Done: test user number"+number);
    }

    public static void testSerializeJson(int number) throws JsonProcessingException{
        List<User> users = new ArrayList<User>();
        if (number <= 0){
            return;
        }
        for (int i = 0; i < number; i++){
            User user = new User();
            user.setId(1000000+i);
            user.setName("zhangsan"+i);
            user.setBirthday((new Date()).toString());
            users.add(user);
        }
        System.out.println("Start: test user number"+number);
        long sum = 0L;
        long start = 0L;
        long end = 0L;
        String gsonString = "";
        //Test GSON serialize
        Gson gson = new Gson();
        start = System.currentTimeMillis();
        gsonString = gson.toJson(users);
        end = System.currentTimeMillis();
        sum = sum + end - start;
        System.out.println("gson time used" + sum + "ms");

        /*
        sum = 0L;
        //Test GSON deserialize
        start = System.currentTimeMillis();
        List<User> retGsonUsers = JSON.parseArray(gsonString, User.class);
        end = System.currentTimeMillis();
        sum = sum + end - start;
        System.out.println("gson deserilize time used " + sum + "ms");
        */
        sum = 0L;
        String jsonString = "";
        //FastJson serilize
        start = System.currentTimeMillis();
        jsonString = JSON.toJSONString(users);
        end = System.currentTimeMillis();
        sum = sum + end - start;
        System.out.println("fastjson serilize time used " + sum + "ms");
        try{
            FileWriter fileWriter = new FileWriter("./testNew.json");
            fileWriter.write(jsonString,0,jsonString.length());
            fileWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
/*
        sum = 0L;
        //FastJson deserialize
        start = System.currentTimeMillis();
        List<User> retJsonUsers = JSON.parseArray(jsonString, User.class);
        end = System.currentTimeMillis();
        sum = sum + end - start;
        System.out.println("fastjson deserilize time used " + sum + "ms");
*/


        sum = 0L;
        String jacksonString = "";
        //Jackson
        start = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper();
        jacksonString = mapper.writeValueAsString(users);
        end = System.currentTimeMillis();
        sum = sum + end - start;
        System.out.println("jackson serialize time used: " + sum + "ms");
        sum = 0L;
        /*
        //Jackson deserialize
        start = System.currentTimeMillis();
        ObjectMapper deMapper = new ObjectMapper();
        deMapper.writeValueAsString(users);
        try {
            List<User> retJacksonUser;
            retJacksonUser = deMapper.readValue(jacksonString, List.class);
            retJacksonUser.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        sum = sum + end - start;
        System.out.println("jackson deserialize time used: " + sum + "ms");
        */
        System.out.println("Done: test user number"+number);
    }

    public static void testGsonDeserializeJson(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./testNew.json"));
            String json = reader.readLine();
            Gson gson = new Gson();
            long start = System.currentTimeMillis();
            List<User> list = gson.fromJson(json,List.class);
            long end = System.currentTimeMillis();
            System.out.println("gson deserialize time used: " + (end - start)+ "ms");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void testFastJsonDeserializeJson(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./testNew.json"));
            String jsonString = reader.readLine();
            long start = System.currentTimeMillis();
            List<User> list = (List<User>)JSON.parse(jsonString);
            long end = System.currentTimeMillis();
            System.out.println("fastjson deserialize time used: " + (end - start)+ "ms");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void testJacksonDeserializeJson(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./testNew.json"));
            String jsonString = reader.readLine();
            long start = System.currentTimeMillis();
            ObjectMapper mapper = new ObjectMapper();

            List<User> list = mapper.readValue(jsonString,List.class);
            long end = System.currentTimeMillis();
            System.out.println("jackson deserialize time used: " + (end - start)+ "ms");
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args){
        System.out.println("JsonComparisonDemo");
        try{
//            testSerializeJson(100);//0.1k
//            testSerializeJson(10000);//10k
            //1M
//            testSerializeJson(1000000);
            testSerializeJson(1000000);
            testGsonDeserializeJson();
            testFastJsonDeserializeJson();
            testJacksonDeserializeJson();
//            testSerializeJson(100000000);//100M  内存所限爆内存了

        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

    }
}


