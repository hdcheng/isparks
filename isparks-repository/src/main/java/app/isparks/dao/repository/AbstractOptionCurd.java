package app.isparks.dao.repository;


import app.isparks.core.pojo.entity.Option;
import app.isparks.dao.template.AbstractCurd;

public abstract class AbstractOptionCurd extends AbstractCurd<Option> {

    @Override
    public Option newEntity() {
        return new Option();
    }

}
