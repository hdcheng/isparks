package app.isparks.core.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具包
 *
 * @author： chenghd
 * @date： 2021/1/21
 */
public final class RegexUtils {

    private RegexUtils(){}


    /**
     * 获取字符串中第一符合IPv4规则的字符串
     *
     * @param s
     * @return ipv4 string or ""
     */
    private final static String IPV4_REGEXS = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
    public static String getFirstIPv4(String s){

        if(StringUtils.isEmpty(s)){
            return "";
        }

        Matcher matcher = Pattern.compile(IPV4_REGEXS).matcher(s);

        return matcher.find() ? matcher.group() : "";
    }

    /**
     * 获取最后一个匹配的 IPv4 字符串
     *
     * @param s
     * @return
     */
    public static String getLastIPv4(String s){
        if(StringUtils.isEmpty(s)){
            return "";
        }

        Matcher matcher = Pattern.compile(IPV4_REGEXS).matcher(s);

        String ip = "";
        while (matcher.find()){
            ip = matcher.group();
        }

        return ip;
    }

    /**
     * 获取所有匹配的字符串
     *
     * @param s
     * @return
     */
    public static List<String> listIPv4s(String s){
        List<String> ips = new LinkedList<>();
        if(StringUtils.isEmpty(s)){
            return ips;
        }

        Pattern pattern = Pattern.compile(IPV4_REGEXS);
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()){
            ips.add(matcher.group());
        }
        return ips;
    }

}
