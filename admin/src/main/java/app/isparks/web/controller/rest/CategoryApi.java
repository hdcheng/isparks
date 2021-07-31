package app.isparks.web.controller.rest;

import app.isparks.core.pojo.converter.CategoryConverter;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.param.CategoryParam;
import app.isparks.core.service.ICategoryService;
import app.isparks.core.web.support.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api("分类管理接口")
@RestController("RestCategoryApi")
public class CategoryApi extends BasicApi{

    private ICategoryService categoryService;

    public CategoryApi(ICategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping()
    @ApiOperation("Create category | 创建分类")
    public Result createCategory(@RequestBody CategoryParam param){
        CategoryConverter converter= ConverterFactory.get(CategoryConverter.class);
        CategoryDTO dto = converter.mapDTO(param);
        return build(categoryService.create(dto));
    }


}
