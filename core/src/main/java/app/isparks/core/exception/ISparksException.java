package app.isparks.core.exception;

/**
 * exception.
 *
 * @author： chenghd
 * @date： 2021/1/3
 */
public abstract class ISparksException extends RuntimeException {

    private String msg;

    public ISparksException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
