package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.entity.Category;
import app.isparks.core.pojo.param.CategoryParam;
import app.isparks.core.pojo.param.CategoryUpdateParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:01+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class CategoryConverterImpl implements CategoryConverter {

    @Override
    public Category map(CategoryParam source) {
        if ( source == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( source.getName() );
        category.setDescription( source.getDescription() );
        category.setParentId( source.getParentId() );

        return category;
    }

    @Override
    public Category map(CategoryUpdateParam source) {
        if ( source == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( source.getId() );
        category.setName( source.getName() );
        category.setDescription( source.getDescription() );
        category.setParentId( source.getParentId() );

        return category;
    }

    @Override
    public CategoryDTO map(Category source) {
        if ( source == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( source.getId() );
        categoryDTO.setModifyTime( source.getModifyTime() );
        categoryDTO.setCreateTime( source.getCreateTime() );
        categoryDTO.setName( source.getName() );
        categoryDTO.setDescription( source.getDescription() );

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> maps(List<Category> source) {
        if ( source == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( source.size() );
        for ( Category category : source ) {
            list.add( map( category ) );
        }

        return list;
    }
}
