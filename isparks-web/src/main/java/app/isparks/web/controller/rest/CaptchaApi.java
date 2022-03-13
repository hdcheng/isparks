package app.isparks.web.controller.rest;

import app.isparks.core.service.ICacheService;
import app.isparks.core.service.ICaptchaService;
import app.isparks.core.util.IpUtils;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.CacheServiceImpl;
import app.isparks.service.captcha.CaptchaProducer;
import app.isparks.service.impl.CaptchaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenghd
 */
@Api("验证码接口")
@RestController("v1_captcha")
@RequestMapping("v1/captcha")
public class CaptchaApi extends BasicApi{

    private Logger log = LoggerFactory.getLogger(CaptchaApi.class);

    private ICaptchaService captchaService;

    public CaptchaApi(CaptchaServiceImpl captchaService){
        this.captchaService = captchaService;
    }

    @ApiOperation("获取验证码图片")
    @GetMapping("image")
    public void captchaImage(HttpServletRequest request, HttpServletResponse response){
        try(OutputStream os = response.getOutputStream()) {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String ip = IpUtils.obtainIp(request);
            captchaService.captchaImage(ip,os);
            os.flush();
        }catch (IOException e){
            log.error("生成验证码异常",e);
        }
    }

    @ApiOperation("验证验证码")
    @GetMapping("image/{code}")
    public Result captchaCheck(@PathVariable("code") String code, HttpServletRequest request){
        String ip = IpUtils.obtainIp(request);
        return build(captchaService.checkCaptcha(ip,code,true));
    }

}
