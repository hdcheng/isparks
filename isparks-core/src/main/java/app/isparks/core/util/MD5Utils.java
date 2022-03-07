package app.isparks.core.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * MD5加密工具包
 *
 * @author chenghd
 * @date 2020/7/31
 */
@Slf4j
public class MD5Utils {

    private final static String SALT = "ISPARKS257068AE8948410C825";

    /**
     * MD5加密
     */
    private static byte[] md5(String plain) {
        MessageDigest algrithm;
        try {
            algrithm = MessageDigest.getInstance("MD5");
            algrithm.reset();
            algrithm.update(plain.getBytes());
            byte[] messageDigest = algrithm.digest();
            return messageDigest;
        } catch (NoSuchAlgorithmException e) {
            log.error("Encrypt utils error", e);
        }
        return null;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        if (s == null || "".equals(s)) {
            return "";
        }
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            log.error("not supported charset...{}", e);
            return s;
        }
    }

    /**
     * 加盐加密
     * @return
     */
    public static String hashWithSalt(String s){
        return hash(s + SALT);
    }

}
