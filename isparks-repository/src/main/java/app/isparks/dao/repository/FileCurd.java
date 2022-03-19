package app.isparks.dao.repository;


import app.isparks.core.pojo.entity.FFile;
import app.isparks.dao.template.AbstractCurd;

/**
 * @author chenghd
 * @date 2020/9/29
 */
public abstract class FileCurd extends AbstractCurd<FFile> {

    @Override
    public FFile newEntity() {
        return new FFile();
    }
}
