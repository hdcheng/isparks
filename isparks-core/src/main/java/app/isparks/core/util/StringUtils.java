package app.isparks.core.util;

/**
 * String Utils
 *
 * @author chenghd
 * @date 2020/7/22
 */
public abstract class StringUtils {

    /**
     * 字符串是否为""或者null
     *
     * @param s
     * @return 是/否
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * 是否有空值
     *
     * @param texts
     * @return
     */
    public static boolean hasEmpty(String... texts) {
        for (String text : texts)
            if (text == null || text.isEmpty())
                return true;
        return false;
    }


    /**
     * 拼接字符串
     *
     * @param ss
     * @return
     */
    public static String concat(String... ss) {
        StringBuilder sb = new StringBuilder();
        for (String s : ss) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 对比两个(非空)字符串是否相等，如果字符串为空，则返回false。
     *
     * @param s1
     * @param s2
     * @return 是/否
     */
    public static boolean equals(String s1,String s2){
        if(s1 == s2)
            return true;
        if(s1 == null || s2 == null)
            return false;
        if(s1.length() != s2.length())
            return false;
        if(s1 instanceof String && s2 instanceof String)
            return s1.equals(s2);

        final int length = s1.length();
        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

}
