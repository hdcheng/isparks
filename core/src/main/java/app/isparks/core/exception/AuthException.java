package app.isparks.core.exception;

/**
 * Permission exception.
 * 权限异常
 *
 * @author： chenghd
 * @date： 2021/1/3
 */
public class AuthException extends ISparksException {

    private String subject;

    public AuthException(String subject, String msg) {
        super(msg);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
