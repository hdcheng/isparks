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
import app.isparks.dao.repository.CategoryCurd;
import app.isparks.dao.repository.PostCategoryRLCurd;
import app.isparks.dao.repository.impl.CategoryCurdImpl;
import app.isparks.dao.repository.impl.PostCategoryRLCurdImpl;
import app.isparks.service.plugin.AbstractEnhancerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author： chenghd
 * @date： 2021/2/26
 */
@Service
public class CategoryServiceImpl extends AbstractEnhancerService<Category, CategoryDTO> implements ICategoryService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private CategoryConverter CONVERTER = ConverterFactory.get(CategoryConverter.class);

    // DTO TO DO
    private final BeanCopier DO_COPIER = BeanCopier.create(CategoryDTO.class,Category.class,false);

    // DO TO DTO
    private final BeanCopier DTO_COPIER = BeanCopier.create(Category.class,CategoryDTO.class,false);

    private CategoryCurd categoryCurd;

    private PostCategoryRLCurd pcRLCurd;

    public CategoryServiceImpl(CategoryCurdImpl categoryCurd, PostCategoryRLCurdImpl pcRLCurd){
        super(categoryCurd);
        notNull(pcRLCurd,"PostCategoryRLCurdImpl must not be null");

        this.categoryCurd = categoryCurd;
        this.pcRLCurd = pcRLCurd;
    }

    @Override
    @Deprecated
    public Optional<CategoryDTO> create(CategoryParam param) {
        notNull(param,"param must not be null.");

        if(categoryCurd.findByName(param.getName()) != null){
            return Optional.empty();
        }

        Category category = CONVERTER.map(param);

        categoryCurd.insert(category);

        return Optional.ofNullable(converter(category));

    }

    @Override
    public Optional<CategoryDTO> create(CategoryDTO dto) {
        notNull(dto,"category dto must not be null");

        if(categoryCurd.findByName(dto.getName()) != null){
            String msg = "Category name already exists";
            log.warn(msg);
            resultMessage(msg);
            return Optional.empty();
        }

        Category category = new Category();

        DO_COPIER.copy(dto,category,null);
        abstractInsert(category);
        DTO_COPIER.copy(category,dto,null);

        return Optional.of(dto);
    }

    @Override
    public Optional<CategoryDTO> delete(String categoryName) {
        notEmpty(categoryName,"category name must not be empty.");

        Category category = categoryCurd.findByName(categoryName);

        if(category == null){
            resultMessage("不存在分类：" + categoryName);
            return Optional.empty();
        }

        if(pcRLCurd.countByCategory(category.getId()) > 0){
            resultMessage("分类：" + categoryName + " 下数据不为空");
            return Optional.empty();
        }

        category = categoryCurd.delete(category.getId());

        return Optional.ofNullable(converter(category));
    }

    @Override
    public PageData<CategoryDTO> page(int page, int size) {

        PageData<Category> pageData = categoryCurd.pageAll(new PageInfo(page,size));

        PageData<CategoryDTO> result = pageData.convertData((c) -> converter(c) );

        return result;
    }

    @Override
    public Optional<CategoryDTO> selectById(String id) {

        if(StringUtils.isEmpty(id)){
            return Optional.empty();
        }

        Category category= categoryCurd.select(id);

        return Optional.ofNullable(converter(category));
    }

    @Override
    public List<CategoryDTO> listAll() {

        List<Category> categories = categoryCurd.select();

        return converter(categories);
    }

    @Override
    public Optional<CategoryDTO> update(String categoryId,CategoryParam param) {
        notEmpty(categoryId,"category id must not be empty.");
        notNull(param,"category param must not be null.");

        Category category = categoryCurd.select(categoryId);

        if(category == null){
            resultMessage("不存在分类：" + param.getName());
            return Optional.empty();
        }

        BeanUtils.updateProperties(param,category);

        categoryCurd.updateById(category);

        return Optional.ofNullable(converter(category));
    }

    /**
     * 从 category 类型转换成 categoryDto
     * @param category
     * @return
     */
    @Override
    public CategoryDTO toDTO(Category category){

        CategoryDTO dto = CONVERTER.map(category);

        dto.setPostNumber(pcRLCurd.countByCategory(category.getId()));

        execute(dto);

        return dto;
    }


}
