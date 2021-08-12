package app.isparks.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 封装集合工具包
 *
 * @author： chenghd
 * @date： 2021/1/14
 */
public class CollectionUtils {

    private CollectionUtils (){}

    /**
     * 判断集合是否为空
     * Null为空
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection<?> c){
        return c == null ? true : c.isEmpty();
    }

    public static boolean isEmpty(Map<?,?> m){
        return m == null ? true : m.isEmpty();
    }

    /**
     * 将List转换成Map
     *
     * @param vs
     * @param mapKeyGetFunction 生成key的表达式
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> Map<K,V> convertToMap(List<V> vs,Function<V,K> mapKeyGetFunction){

        if(CollectionUtils.isEmpty(vs)){
            return new HashMap<>();
        }

        Map<K,V> resultMap = new HashMap<>(vs.size());

        vs.forEach(d -> resultMap.putIfAbsent(mapKeyGetFunction.apply(d),d));

        return resultMap;
    }

}
