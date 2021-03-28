package app.isparks.core.service;

import app.isparks.core.exception.FileOperationException;
import app.isparks.core.file.handler.FileHandler;
import app.isparks.core.file.type.FileType;
import app.isparks.core.file.type.MediaType;
import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.page.PageData;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/3
 */
public interface IFileService {


    /**
     * 文件上传
     *
     * @param fileName 文件全名，如：user.jpg
     * @param is 二进制流
     * @return 储存后的文件数据
     */
    Optional<FileDTO> upload(String fileName, InputStream is) throws FileOperationException;

    /**
     * 删除文件
     */
    Optional<FileDTO> deleteById(String id);

    /**
     * 更改状态为REMOVED
     *
     * @param id
     * @return
     */
    Optional<FileDTO> removeById(String id);

    /**
     * 恢复数据
     * @param id
     * @return
     */
    Optional<FileDTO> restoreById(String id);

    /**
     * 分页查找
     * @param page
     * @param size
     */
    PageData<FileDTO> pageValidFile(int page, int size);

    /**
     * 根据文件类型查找
     * @param page
     * @param size
     * @param type
     */
    PageData<FileDTO> pageValidByFileType(int page, int size, FileType type);

    /**
     * 根据媒体类型查找
     * @param page
     * @param size
     * @param type
     */
    PageData<FileDTO> pageValidByMediaType(int page, int size, MediaType type);

    /**
     * 获取所有状态是REMOVED的文件
     */
    List<FileDTO> listAllRemoved();

    /**
     * 文件操作器
     */
    FileHandler getFileHandler();

    /**
     * 统计数量
     */
    long countAll();
}
