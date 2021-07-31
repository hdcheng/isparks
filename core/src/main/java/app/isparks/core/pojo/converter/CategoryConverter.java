package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.entity.Category;
import app.isparks.core.pojo.param.CategoryParam;
import app.isparks.core.pojo.param.CategoryUpdateParam;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/22
 */
@Mapper
public interface CategoryConverter {

    @Deprecated
    Category map(CategoryParam source);

    @Deprecated
    CategoryDTO mapDTO(CategoryParam source);

    Category map(CategoryDTO source);

    @Deprecated
    Category map(CategoryUpdateParam source);

    CategoryDTO map(Category source);

    List<CategoryDTO> maps(List<Category> source);

}
