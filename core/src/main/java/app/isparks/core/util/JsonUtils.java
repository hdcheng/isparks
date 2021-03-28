package app.isparks.core.util;


import com.google.gson.Gson;

import java.util.Map;

/**
 * @author chenghd
 * @date 2020/9/21
 */
public class JsonUtils {

    private static Gson gson;

    static {
        if(gson == null){
            gson = new Gson();
        }
    }

    /**
     * 转换成 JSON 字符串
     */
    public static String toJson(Object target) {
        return gson.toJson(target);
    }

    /**
     * 将 json 字符串转换成 Object
     */
    public static  <T> T toObject(String json,Class<T> tClass){
        return gson.fromJson(json,tClass);
    }

    /**
     * 将json转换成map
     */
    public static Map<String,Object> toMap(String jsonStr){
        return gson.fromJson(jsonStr,Map.class);
    }

    public static void main(String[] args) {
        String j = null;
        Map map = toMap(j);
        System.out.println(map);

    }

}
