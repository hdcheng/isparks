package app.isparks.dao.repository;



import app.isparks.core.pojo.entity.Category;
import app.isparks.dao.template.AbstractCurd;

import java.util.Optional;


/**
 * @author chenghd
 * @date 2020/8/12
 */
public abstract class AbstractCategoryCurd extends AbstractCurd<Category> {


    @Override
    public Category newEntity() {
        return new Category();
    }

    /**
     * insert if name absent
     */
    public abstract Category insertIfNameAbsent(Category c);

    /**
     * delete by name
     */
    public abstract int deleteByName(String name);

    /**
     * find by id
     */
    public abstract Category findById(String id);

    /**
     * find by name
     */
    public abstract Category findByName(String name);


}
