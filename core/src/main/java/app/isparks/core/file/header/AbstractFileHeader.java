package app.isparks.core.file.header;

import app.isparks.core.file.type.FileLocationType;
import app.isparks.core.file.type.FileType;
import app.isparks.core.file.type.MediaType;

/**
 * abstract file header
 *
 * @author chenghd
 * @date 2020/9/28
 */
public abstract class  AbstractFileHeader implements FileHeader {

    /**
     * 文件名
     */
    private String name ;

    /**
     * 文件储存位置
     */
    private FileLocationType locationType;

    /**
     * 文件类型
     */
    private FileType fileType;

    /**
     * 媒体类型
     */
    private MediaType mediaType;

    public AbstractFileHeader(String name, FileLocationType locationType, FileType fileType){
        this.name = name;
        this.locationType = locationType;
        this.fileType = fileType;
        this.mediaType = fileType.getMediaType();
    }

    public String getName() {
        return name;
    }

    public FileLocationType getLocationType() {
        return locationType;
    }

    public FileType getFileType() {
        return fileType;
    }

}
