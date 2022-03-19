package app.isparks.dao.repository;


import app.isparks.core.pojo.entity.Log;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/12
 */
public abstract class LogCurd extends AbstractCurd<Log> {

    @Override
    public Log newEntity() {
        return new Log();
    }

    /**
     * 根据类型查找
     */
    public abstract PageData<Log> pageByType(PageInfo info, String type);

}
