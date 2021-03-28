package app.isparks.dao.repository;


import app.isparks.core.pojo.entity.Log;
import app.isparks.dao.template.AbstractCurd;

/**
 * @author chenghd
 * @date 2020/8/12
 */
public abstract class AbstractLogCurd extends AbstractCurd<Log> {

    @Override
    public Log newEntity() {
        return new Log();
    }

}
