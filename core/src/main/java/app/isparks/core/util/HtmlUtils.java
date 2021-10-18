package app.isparks.core.util;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HtmlUtils {


    /**
     * 处理 html 源码
     */
    public static String htmlEscape(String input){

        return org.springframework.web.util.HtmlUtils.htmlEscape(input, StandardCharsets.UTF_8.displayName());

    }

    /**
     * 过滤指定标签
     */
    public static String htmlEscapeAndRemoveByTags(String input,String ... tagNames){
        String html = "";
        if(input ==  null || input.isEmpty()){
            return html;
        }
        html = input;
        for(String tagName : tagNames){
            String tagRegex = "<\\s*" + tagName + "[^>]*?>[\\s\\S]*?<\\/[\\s]*" + tagName + "[\\s]*>";
            Matcher matcher = Pattern.compile(tagRegex,Pattern.CASE_INSENSITIVE).matcher(html);
            html = matcher.replaceAll("");
        }
        return htmlEscape(html);
    }

    /**
     * 初级 html
     */
    public static String htmlUnescape(String input){

        String content =  org.springframework.web.util.HtmlUtils.htmlUnescape(input);

        return content;
    }

}
