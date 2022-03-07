package app.isparks.core.file.util;


import app.isparks.core.file.type.FileType;
import app.isparks.core.file.type.MediaType;
import app.isparks.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenghd
 * @date 2020/9/28
 */
public class FileTypeUtils {

    /**
     * 获取文件类型
     */
    public static FileType getFileType(String type){

        if(StringUtils.isEmpty(type)){
            return FileType.UNKNOWN;
        }

        type = type.toLowerCase();

        if(type.contains(".")){
            type = type.replaceFirst(".","");
        }

        FileType[] types = FileType.values();

        String suffix ;

        for(FileType t : types){
            suffix = t.getSuffix();

            if(suffix.equals(type)){
                return t;
            }

        }

        return FileType.UNKNOWN;
    }

    /**
     * 根据文件名获取文件类型
     */
    public static FileType getFileTypeByFileName(String fileName){

        if( fileName == null || "".equals(fileName)){
            return FileType.UNKNOWN;
        }

        int p = fileName.lastIndexOf(".");

        if(p < 0 || p >= fileName.length() - 1){
            return FileType.UNKNOWN;
        }

        String suffix = fileName.substring(p+1);

        return getFileType(suffix);

    }


    /**
     * 根据媒体类型获取所有文本类型
     */
    public FileType[] getFileType(MediaType mediaType){
        List<FileType> result = new ArrayList<>();
        FileType[] types = FileType.values();
        for(FileType type : types){
            if(mediaType == type.getMediaType()){
                result.add(type);
            }
        }
        return result.toArray(new FileType[]{});
    }

}
