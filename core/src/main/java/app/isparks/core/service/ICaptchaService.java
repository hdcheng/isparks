package app.isparks.core.service;

import java.io.OutputStream;

/**
 * @author chenghd
 */
public interface ICaptchaService {

    /**
     * Create captcha image | 创建验证码图片
     * @return 返回验证码图片内容
     */
    String captchaImage(OutputStream os);

    /**
     * Cache captcha key - code | 根据 KEY 缓存验证码
     */
    void captchaImage(String key , OutputStream os);

    /**
     * Check code | 验证验证码
     * @param clear 如果验证通过是否清除缓存
     */
    boolean checkCaptcha(String key , String code , boolean clear);

}
