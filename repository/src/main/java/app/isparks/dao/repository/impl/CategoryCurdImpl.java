package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Category;
import app.isparks.dao.mybatis.mapper.CategoryMapper;
import app.isparks.dao.repository.AbstractCategoryCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/12
 */
@Repository
public class CategoryCurdImpl extends AbstractCategoryCurd {


    private CategoryMapper categoryMapper;

    public CategoryCurdImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> selectByCond(Category category) {
        return categoryMapper.selectByCond(category);
    }

    @Override
    public Category insert(Category c) {
        beforeInsert(c);
        return categoryMapper.insert(c) == 1 ? c : null;
    }

    @Override
    public Category insertIfNameAbsent(Category c) {
        return findByName(c.getName()) == null ? null : insert(c);
    }

    @Override
    protected int deleteBy(Category category) {
        return categoryMapper.deleteByCond(category);
    }

    @Override
    public Category updateById(Category t) {
        beforeUpdate(t);
        return categoryMapper.updateById(t) == 1 ? t : null;
    }

    @Override
    public int deleteByName(String name) {
        return categoryMapper.deleteByName(name);
    }

    @Override
    public Category findById(String id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public Category findByName(String name) {
        return selectByCond(newEntity().withName(name)).stream().findFirst().orElse(null);
    }

    @Override
    public long countBy(Category category) {
        return categoryMapper.countByCond(category);
    }


}
