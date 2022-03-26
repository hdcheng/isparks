package app.isparks.core.file.header;


import app.isparks.core.file.type.FileLocationType;
import app.isparks.core.file.type.FileType;

/**
 * 本地文件
 *
 * @author chenghd
 * @date 2020/9/28
 */
public class DefaultFileHeader extends AbstractFileHeader {

    public DefaultFileHeader(String name, FileType fileType){
        super(name,FileLocationType.LOCAL,fileType);
    }


}
