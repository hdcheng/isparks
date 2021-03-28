package app.isparks.core.util.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 本地线程工具包
 *
 * @author chenghd
 * @date 2020/8/28
 */
public abstract class LocalThreadUtils {

    private final static String THREAD_KEY_SUFFIX = System.currentTimeMillis() + "";
    private final static String THREAD_MESSAGE_KEY = "thread-message-key-" + THREAD_KEY_SUFFIX;
    private final static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

    /**
     * 设置线程执行过程中产生的消息，如果已存在则覆盖之前的。
     *
     * @param message
     */
    public static void setMessage(String message){
        // todo: 解决内存泄漏的危险
        addValue(THREAD_MESSAGE_KEY,message);
    }

    /**
     * 获取线程执行过程中产生的消息
     *
     * @return message
     */
    public static Optional<String> getMessage(){
       return Optional.ofNullable((String)getValue(THREAD_MESSAGE_KEY));
    }

    public static Object getValue(String key){
        Map<String,Object> map = threadLocal.get();
        if(map == null){
            return null;
        }
        return map.get(key);
    }

    public static void addValue(String key,Object value){
        Map<String,Object> map = threadLocal.get();
        if(map == null){
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key,value);
    }

}
