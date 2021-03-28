package app.isparks.core.file.header;


import app.isparks.core.file.type.FileType;

/**
 * @author chenghd
 * @date 2020/9/28
 */
public interface FileHeader {

    /**
     * 文件名
     */
    String getName();

    /**
     * 文件类型
     */
    FileType getFileType();

}
