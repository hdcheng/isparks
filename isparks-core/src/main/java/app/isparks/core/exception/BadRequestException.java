package app.isparks.core.exception;

/**
 * bad request exception.
 * 请求异常
 *
 * @author chenghd
 * @date 2020/7/30
 */
public class BadRequestException extends ISparksException {

    public BadRequestException(String msg) {
        super(msg);
    }

}
