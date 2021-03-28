package app.isparks.core.file.util;

import app.isparks.core.exception.FileOperationException;
import app.isparks.core.file.type.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.UUID;

/**
 *
 * @author chenghd
 * @date 2020/7/25
 */
public class FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 将文件复制到指定目录
     * @param is
     * @param des
     */
    public static void copy(InputStream is,String des) throws FileOperationException {
        copy(is,new File(des));
    }

    /**
     * 文件复制
     */
    public static void copy(InputStream is, File destination) throws FileOperationException {
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(is,destination);
        }catch (IOException e){
            throw new FileOperationException(e);
        }
    }

    /**
     * 生成随机文件名。如：as21d61gd561s3d1f.jpg
     */
    public static String randomFileNameByType(FileType type){
        String fileName = randomFileName();
        if(type == null || type == FileType.UNKNOWN){
            return fileName;
        }
        fileName.concat(".").contains(type.getSuffix());
        return fileName;
    }
    /**
     * 生成随机文件名。如：as21d61gd561s3d1f
     */
    public static String randomFileName(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    /**
     * 将文件路径转换程url路径
     */
    public static String parseToUrl(String path){
        File file = new File(path);
        try {
            String result = file.toURI().toURL().toString();
            return result;
        }catch (MalformedURLException e){
            log.error("文件路径转换url路径异常",e);
            return "";
        }
    }

}
