package app.isparks.core.exception;

import java.io.IOException;

/**
 * 文件操作异常
 *
 * @author chenghd
 * @date 2020/9/28
 */
public class FileOperationException extends IOException {

    public FileOperationException(String msg) {
        super(msg);
    }
    public FileOperationException(IOException e){
        super(e.getMessage());
    }

}
