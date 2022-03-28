package app.isparks.service.impl;

import app.isparks.core.config.ISparksProperties;
import app.isparks.core.exception.FileOperationException;
import app.isparks.core.file.UploadResult;
import app.isparks.core.file.handler.FileHandler;
import app.isparks.core.file.handler.LocalStorageFileHandler;
import app.isparks.core.file.header.DefaultFileHeader;
import app.isparks.core.file.header.FileHeader;
import app.isparks.core.file.type.FileType;
import app.isparks.core.file.type.MediaType;
import app.isparks.core.file.util.FileTypeUtils;
import app.isparks.core.util.FileUtils;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.FileConverter;
import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.entity.FFile;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.core.service.IFileService;
import app.isparks.dao.repository.FileCurd;
import app.isparks.service.base.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author： chenghd
 * @date： 2021/3/3
 */
@Service
public class FileServiceImpl extends AbstractService<FFile> implements IFileService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private FileConverter CONVERTER = ConverterFactory.get(FileConverter.class);

    private volatile FileHandler handler;

    private FileCurd fileCurd;

    public FileServiceImpl(FileCurd fileCurd){
        super(fileCurd);
        this.fileCurd = fileCurd;
    }

    @Override
    @Transactional
    public Optional<FileDTO> upload(String fileName, InputStream is) throws FileOperationException {

        notEmpty(fileName,"file name must not be null.");
        notNull(is,"file input stream must not be null.");

        FileType type = FileTypeUtils.getFileTypeByFileName(fileName);

        FFile file = new FFile();
        file.setName(fileName);
        file.setOrigin(fileName);
        file.setFileType(type.getSuffix());
        file.setMediaType(type.getMediaType().getType());

        FileHeader header = new DefaultFileHeader(fileName,type);

        FileHandler handler = getFileHandler();
        UploadResult result = handler.upload(is,header);

        file.setName(result.getName());
        file.setLocation(result.getPosition());

        fileCurd.insert(file);

        FileDTO dto = converter(file);

        return Optional.of(dto);
    }

    @Override
    public Optional<FileDTO> deleteById(String id) {

        Optional<FFile> fileOptional = abstractDelete(id);


        if(fileOptional.isPresent()){
            FFile file = fileOptional.get();

            UploadResult result = new UploadResult();
            result.setName(file.getName());
            result.setPosition(file.getLocation());
            result.setMediaType(file.getMediaType());
            result.setFileType(file.getFileType());


            FileHandler handler = getFileHandler();
            handler.delete(result);

            return Optional.of(converter(file));

        }else{
            return Optional.empty();
        }

    }

    @Override
    public Optional<FileDTO> removeById(String id) {
        Optional<FFile> file = abstractRemove(id);
        if(!file.isPresent()){
            return Optional.empty();
        }
        return Optional.of(converter(file.get()));
    }

    @Override
    public Optional<FileDTO> restoreById(String id) {
        Optional<FFile> file = abstractRestore(id);
        if(!file.isPresent()){
            return Optional.empty();
        }
        return Optional.of(converter(file.get()));
    }

    /**
     * 获取 file handler
     */
    @Override
    public FileHandler getFileHandler(){

        if(handler == null){
            synchronized (this){
                if(handler == null){
                    handler = new LocalStorageFileHandler();
                }
            }
        }

        return handler;
    }

    @Override
    public PageData<FileDTO> pageValidFile(int page, int size) {
        PageData<FFile> file = abstractPageValidStatus(page,size);
        return file.convertData((f) -> converter(f));
    }

    @Override
    public PageData<FileDTO> pageValidByFileType(int page, int size, FileType type) {
        notNull(type,"file type must not be null");

        FFile cond = new FFile();
        cond.setFileType(type.getSuffix());
        PageData<FFile> file = abstractPageValidStatusBy(new PageInfo(page,size),cond);
        return file.convertData((f) -> converter(f));
    }

    @Override
    public PageData<FileDTO> pageValidByMediaType(int page, int size, MediaType type) {
        notNull(type,"media must not be null.");

        FFile cond = new FFile();
        cond.setMediaType(type.getType());
        PageData<FFile> file = abstractPageValidStatusBy(new PageInfo(page,size),cond);
        return file.convertData((f) -> converter(f));
    }

    @Override
    public List<FileDTO> listAllRemoved() {
        List<FFile> files = abstractListRemovedStatus();
        List<FileDTO> dtos = new ArrayList<>(files.size());
        files.stream().forEach((f) -> {
            dtos.add(converter(f));
        });
        return dtos;
    }

    @Override
    public long countAll() {
        return abstractCount();
    }

    @Override
    @Transactional(rollbackFor = FileOperationException.class)
    public void privateById(String id) throws FileOperationException{
        Optional<FFile> fileOptional = abstractDelete(id);
        if(fileOptional.isPresent()){
            FFile fFile = fileOptional.get();

            if(fFile.getStatus() == DataStatus.PRIVATE.getCode()){
                return;
            }

            String location = fFile.getLocation();
            String privateLocation = location.replace(ISparksProperties.RESOURCES_FILE_PATH,ISparksProperties.PRIVATE_RESOURCES_FILE_PATH);
            fFile.setLocation(privateLocation);
            fFile.setStatus(DataStatus.PRIVATE);

            abstractUpdate(fFile);

            FileUtils.copy(location,privateLocation);
        }

    }

    @Override
    @Transactional(rollbackFor = FileOperationException.class)
    public void publicById(String id) throws FileOperationException{
        Optional<FFile> fileOptional = abstractDelete(id);
        if(fileOptional.isPresent()){
            FFile fFile = fileOptional.get();

            if(fFile.getStatus() != DataStatus.PRIVATE.getCode()){
                return;
            }

            String privateLocation = fFile.getLocation();
            String publicLocation = privateLocation.replace(ISparksProperties.PRIVATE_RESOURCES_FILE_PATH,ISparksProperties.RESOURCES_FILE_PATH);

            fFile.setLocation(publicLocation);

            fFile.setStatus(DataStatus.VALID);

            abstractUpdate(fFile);

            FileUtils.copy(publicLocation,privateLocation);
        }
    }

    /**
     * 转换
     *
     * @param file
     * @return
     */
    private FileDTO converter(FFile file){
        FileDTO dto = CONVERTER.map(file);
        return dto;
    }

}
