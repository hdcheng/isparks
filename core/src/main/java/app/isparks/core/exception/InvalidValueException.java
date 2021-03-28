package app.isparks.core.exception;


/**
 * 值无效异常
 *
 * @author chenghd
 * @date 2020/7/24
 */
public class InvalidValueException extends ISparksException {

    public InvalidValueException(String info) {
        super(info);
    }

    public InvalidValueException(String info,Object ... args){
        super(String.format(info,args));
    }

}
