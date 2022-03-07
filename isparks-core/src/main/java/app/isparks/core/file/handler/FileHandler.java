package app.isparks.core.file.handler;


import app.isparks.core.exception.FileOperationException;
import app.isparks.core.file.UploadResult;
import app.isparks.core.file.header.FileHeader;
import app.isparks.core.file.type.FileLocationType;

import java.io.InputStream;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author chenghd
 * @date 2020/10/27
 */
public interface FileHandler {


    /**
     * 上传
     *
     * @param is
     * @param fileHeader
     * @return
     * @throws FileOperationException
     */
    UploadResult upload(InputStream is, FileHeader fileHeader) throws FileOperationException;

    /**
     * 删除文件
     * @param result
     * @return
     */
    boolean delete(UploadResult result);
}
