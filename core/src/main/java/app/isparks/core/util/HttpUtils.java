package app.isparks.core.util;

import app.isparks.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http utils
 * 用户获取当前线程下的 request/response/session 等
 *
 * @author chenghd
 * @date 2020/7/30
 */
@Slf4j
public abstract class HttpUtils {

    /**
     * 获取 Request
     */
    public static HttpServletRequest getHttpServletRequest() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request;
        } catch (NullPointerException e) {
            log.error("get request exception", e);
            throw new SystemException("get request exception");
        }
    }

    /**
     * 获取 Response
     */
    public static HttpServletResponse getHttpServletResponse() {

        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            return response;
        } catch (NullPointerException e) {
            log.error("get response exception", e);
            throw new SystemException("get response exception");
        }
    }

}
