package app.isparks.service.impl;

import app.isparks.core.service.ICacheService;
import app.isparks.core.service.ICaptchaService;
import app.isparks.service.captcha.CaptchaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenghd
 */
@Service
public class CaptchaServiceImpl implements ICaptchaService {

    private Logger log = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    private final String CAPTCHA_CODE_KE_PREFIX = "captcha";

    private final long DEFAULT_CAPTCHA_CACHE_TIME = 1000 * 60 * 5; // 验证码有效时间

    private ICacheService cacheService;

    public CaptchaServiceImpl(CacheServiceImpl cacheService){
        this.cacheService = cacheService;
    }

    @Override
    public String captchaImage(OutputStream os) {
        if( os != null ){
            try {
                return CaptchaProducer.randomCaptcha(os);
            }catch (IOException e){
                log.error("Create captcha exception",e);
            }
        }
        return "";
    }

    @Override
    public void captchaImage(String key, OutputStream os) {
        if(key == null || key.isEmpty() || os == null){
            return;
        }
        String code = captchaImage(os);
        cacheService.saveStringWithExpires(CAPTCHA_CODE_KE_PREFIX + key , code , DEFAULT_CAPTCHA_CACHE_TIME);
    }

    @Override
    public boolean checkCaptcha(String key, String code ,  boolean clear) {
        if(key == null || key.isEmpty() || code == null){
            return false;
        }else{
            key = CAPTCHA_CODE_KE_PREFIX + key;
        }

        String cacheCode= cacheService.getString(key).toLowerCase();

        if(code.toLowerCase().equals(cacheCode)){
            if(clear){
                cacheService.invalidate(key);
            }
            return true;
        }
        return false;
    }

}
