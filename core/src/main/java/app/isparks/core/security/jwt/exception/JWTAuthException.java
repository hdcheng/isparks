package app.isparks.core.security.jwt.exception;

/**
 *
 * @author： chenghd
 * @date： 2021/2/6
 */
public class JWTAuthException extends JWTException{

    private String reason ;

    public JWTAuthException(){
        super("jwt auth exception");
    }

    public JWTAuthException(String reson){
        super(reson);
        this.reason = reson;
    }

    /**
     * 获取异常原因
     */
    public String getReason(){
        return this.reason;
    }

    public JWTAuthException withReason(String reason){
        this.reason = reason;
        return this;
    }
}
