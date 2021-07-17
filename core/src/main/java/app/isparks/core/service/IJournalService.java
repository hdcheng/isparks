package app.isparks.core.service;

import app.isparks.core.pojo.dto.JournalDTO;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.JournalParam;

import java.util.Optional;

/**
 * @author chenghd
 */
public interface IJournalService {

    /**
     * 创建随笔
     */
    Optional<JournalDTO> create(JournalParam param, DataStatus status);

    /**
     * 删除随笔
     */
    Optional<JournalDTO> deleteById(String id);

    /**
     * 分页查找
     */
    PageData<JournalDTO> page(int page,int size);

}
