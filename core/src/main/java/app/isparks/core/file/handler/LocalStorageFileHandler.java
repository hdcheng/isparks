package app.isparks.core.file.handler;


import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.exception.FileOperationException;
import app.isparks.core.file.UploadResult;
import app.isparks.core.file.header.FileHeader;
import app.isparks.core.file.type.FileType;
import app.isparks.core.file.util.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本地储存
 *
 * @author chenghd
 * @date 2020/10/27
 */
public class LocalStorageFileHandler implements FileHandler {


    @Override
    public UploadResult upload(InputStream is, FileHeader fileHeader) throws FileOperationException {

        String datePath = new SimpleDateFormat("yyyyMMdd").format(new Date());

        StringBuffer path = new StringBuffer(ISparksProperties.RESOURCES_FILE_PATH).append(ISparksConstant.PATH_SEPARATOR);
        path.append(fileHeader.getFileType().getMediaType().getType()).append(ISparksConstant.PATH_SEPARATOR);
        path.append(datePath);

        String fileName = String.valueOf(System.currentTimeMillis());
        if(fileHeader.getFileType() == FileType.UNKNOWN && fileHeader.getName().contains(".")){
            String originFileName = fileHeader.getName();
            fileName += (originFileName.substring(originFileName.indexOf(".")));
        }else{
            fileName += ("." + fileHeader.getFileType().getSuffix());
        }

        File des = new File(path.toString(),fileName);

        FileUtils.copy(is,des);

        UploadResult result = new UploadResult();

        result.setName(fileName);
        result.setFileType(fileHeader.getFileType().getSuffix());
        result.setMediaType(fileHeader.getFileType().getMediaType().getType());
        result.setPosition(path + ISparksConstant.PATH_SEPARATOR + fileName);

        return result;
    }

    @Override
    public boolean delete(UploadResult result) {

        String path = result.getPosition();

        File f = new File(path);

        return f.delete();
    }
}
