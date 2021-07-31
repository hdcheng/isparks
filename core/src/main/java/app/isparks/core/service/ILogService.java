package app.isparks.core.service;



import app.isparks.core.pojo.dto.LogDTO;
import app.isparks.core.pojo.entity.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.page.PageData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/7/25
 */
public interface ILogService {

    /**
     * save
     * 异步
     */
    void create(Log log);

    /**
     * 删除日志
     */
    boolean delete(String id);

    /**
     * 分页查询
     */
    PageData<LogDTO> page(int page, int size);


    /**
     * Page log dto by types
     */
    PageData<LogDTO> pageByTypes(int page, int size, LogType ... types);

    /**
     * 查找指定数量的日志
     */
    Optional<List<LogDTO>> findNumber(int num);

    /**
     * log types
     */
    Map<String,String> logTypes();

    /**
     * 统计日志数量
     * @return long
     */
    long countAll();

    /**
     * 分页查询
     */
    PageData<LogDTO> pageAll(int page,int size);
}
