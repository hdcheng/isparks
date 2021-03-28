package app.isparks.service.impl;

import app.isparks.core.pojo.converter.CategoryConverter;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.entity.Category;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.core.pojo.param.CategoryParam;
import app.isparks.core.service.ICategoryService;
import app.isparks.core.util.BeanUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.core.util.thread.LocalThreadUtils;
import app.isparks.dao.repository.AbstractCategoryCurd;
import app.isparks.dao.repository.AbstractPostCategoryRLCurd;
import app.isparks.dao.repository.impl.CategoryCurdImpl;
import app.isparks.dao.repository.impl.PostCategoryRLCurdImpl;
import app.isparks.core.service.support.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author： chenghd
 * @date： 2021/2/26
 */
@Service
public class CategoryServiceImpl extends BaseService implements ICategoryService {

    private CategoryConverter CONVERTER = ConverterFactory.get(CategoryConverter.class);

    private AbstractCategoryCurd categoryCurd;

    private AbstractPostCategoryRLCurd pcRLCurd;

    public CategoryServiceImpl(CategoryCurdImpl categoryCurd, PostCategoryRLCurdImpl pcRLCurd){
        this.categoryCurd = categoryCurd;
        this.pcRLCurd = pcRLCurd;
    }

    @Override
    public Optional<CategoryDTO> create(CategoryParam param) {
        notNull(param,"param must not be null.");

        if(categoryCurd.findByName(param.getName()) != null){
            return Optional.empty();
        }

        Category category = CONVERTER.map(param);

        categoryCurd.insert(category);

        CategoryDTO dto = CONVERTER.map(category);

        return Optional.ofNullable(dto);

    }

    @Override
    public Optional<CategoryDTO> delete(String categoryName) {
        notEmpty(categoryName,"category name must not be empty.");

        Category category = categoryCurd.findByName(categoryName);

        if(category == null){
            LocalThreadUtils.setMessage("不存在分类：" + categoryName);
            return Optional.empty();
        }

        if(pcRLCurd.countByCategory(category.getId()) > 0){
            LocalThreadUtils.setMessage("分类：" + categoryName + " 下数据不为空");
            return Optional.empty();
        }

        category = categoryCurd.delete(category.getId());

        return Optional.ofNullable(CONVERTER.map(category));
    }

    @Override
    public PageData<CategoryDTO> page(int page, int size) {

        PageData<Category> pageData = categoryCurd.pageAll(new PageInfo(page,size));

        PageData<CategoryDTO> result = pageData.convertData((c)->toDTO(c));

        return result;
    }

    @Override
    public Optional<CategoryDTO> selectById(String id) {

        if(StringUtils.isEmpty(id)){
            return Optional.empty();
        }

        Category category= categoryCurd.select(id);

        return Optional.ofNullable(toDTO(category));
    }

    @Override
    public List<CategoryDTO> listAll() {

        List<Category> categories = categoryCurd.select();

        List<CategoryDTO> dtos = CONVERTER.maps(categories);

        return dtos;
    }

    @Override
    public Optional<CategoryDTO> update(String categoryId,CategoryParam param) {
        notEmpty(categoryId,"category id must not be empty.");
        notNull(param,"category param must not be null.");

        Category category = categoryCurd.select(categoryId);

        if(category == null){
            LocalThreadUtils.setMessage("不存在分类：" + param.getName());
            return Optional.empty();
        }

        BeanUtils.updateProperties(param,category);

        categoryCurd.updateById(category);

        return Optional.ofNullable(CONVERTER.map(category));
    }

    /**
     * 从 category 类型转换成 categoryDto
     * @param category
     * @return
     */
    private CategoryDTO toDTO(Category category){

        CategoryDTO temp = CONVERTER.map(category);

        temp.setPostNumber(pcRLCurd.countByCategory(category.getId()));

        return temp;
    }


}
