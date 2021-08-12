package app.isparks.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具包
 *
 * @author chenghd
 * @date 2020/7/28
 */
public class ValidateUtils {

    private ValidateUtils () {}

    /**
     * 检测邮箱格式
     */
    public static boolean isEmail(String email) {
        if (email == null || "".equals(email)) return false;
        String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Matcher matcher = Pattern.compile(regex).matcher(email);
        return matcher.matches();
    }


}
