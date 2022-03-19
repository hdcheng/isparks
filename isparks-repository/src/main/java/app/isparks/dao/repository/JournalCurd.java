package app.isparks.dao.repository;


import app.isparks.core.pojo.entity.Journal;
import app.isparks.dao.template.AbstractCurd;

/**
 * @author chenghd
 * @date 2020/8/16
 */
public abstract class JournalCurd extends AbstractCurd<Journal> {

    @Override
    public Journal newEntity() {
        return new Journal();
    }

}
