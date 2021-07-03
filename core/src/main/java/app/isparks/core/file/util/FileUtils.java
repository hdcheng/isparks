package app.isparks.core.file.util;

import app.isparks.core.exception.FileOperationException;
import app.isparks.core.file.type.FileType;
import app.isparks.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
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

    private final static String DEFAULT_ENCODING = "utf-8";

    public static void copy(String origin,String des) throws FileOperationException{
        try {
            File file = new File(origin);
            if(file.isFile()){
                InputStream is = new FileInputStream(file);
                copy(is,des);
            }
        }catch (Exception e){
            throw new FileOperationException("copy file exception");
        }
    }

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


    /**
     * 获取文本文件内容
     */
    public static String readText(String path ,String charset){
        try {
            return org.apache.commons.io.FileUtils.readFileToString(new File(path),charset);
        }catch (IOException e){
            log.error("Read text content exception.");
        }
        return "";
    }

    /**
     * 获取文本文件内容
     */
    public static String readText(String path ){
        return readText(path,DEFAULT_ENCODING);
    }

    /**
     * 将字符串写入到文本文件中
     * @param append   if {@code true}, then the data will be added to the
     *                 end of the file rather than overwriting
     */
    public static void writeText(String path,String content,String charset,boolean append){
        try {
            org.apache.commons.io.FileUtils.write(new File(path),content,charset,append);
        }catch (IOException e){
            log.error("Write string to file exception",e);
        }
    }

    /**
     *
     */
    public static void writeText(String path,String content,boolean append){
        writeText(path,content,DEFAULT_ENCODING,append);
    }

}
