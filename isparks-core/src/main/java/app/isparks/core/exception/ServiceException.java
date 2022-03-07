package app.isparks.core.exception;

/**
 * 逻辑层异常
 *
 * @author chenghd
 * @date 2020/10/13
 */
public class ServiceException extends ISparksException{
    public ServiceException(String msg) {
        super(msg);
    }
}
