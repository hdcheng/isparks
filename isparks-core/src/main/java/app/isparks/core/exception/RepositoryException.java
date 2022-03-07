package app.isparks.core.exception;

/**
 * 持久层异常
 *
 * @author chenghd
 * @date 2020/10/13
 */
public class RepositoryException extends ISparksException{
    public RepositoryException(String msg) {
        super(msg);
    }

    public RepositoryException(ReflectiveOperationException e){
        super("");

    }

}
