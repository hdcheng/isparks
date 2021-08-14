package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.converter.CategoryConverter;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.CategoryParam;
import app.isparks.core.service.ICategoryService;
import app.isparks.core.web.support.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */

@Api("分类管理接口")
@RequestMapping("v1/admin")
@RestController("v1_CategoryApi")
public class CategoryApi extends BasicApi{

    private ICategoryService categoryService;

    public CategoryApi(ICategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("category")
    @ApiOperation("Create category | 创建分类")
    @Log(description = "创建分类",types = {LogType.INSERT})
    public Result create(@RequestBody CategoryParam param){
        CategoryConverter converter= ConverterFactory.get(CategoryConverter.class);
        CategoryDTO dto = converter.mapDTO(param);
        return build(categoryService.create(dto));
    }
    
    @DeleteMapping("category")
    @ApiOperation("Delete category by name | 根据分类名删除分类")
    @Log(description = "根据分类名删除分类",types = {LogType.DELETE})
    public Result deleteByName(@RequestParam("name") String name){

        return build(categoryService.delete(name));
    }

    @GetMapping("category/page")
    @ApiOperation("Get category by page | 分页查找分类（根据时间倒叙排序）")
    public Result page(@RequestParam(value = "page",defaultValue = "1") int page,
                       @RequestParam(value = "size" ,defaultValue = "10") int size){
        return build(categoryService.page(page,size));
    }

    @GetMapping("category/all")
    @ApiOperation("Get all categories | 获取所有分类")
    public Result all(){
        return build(categoryService.listAll());
    }

    @PatchMapping("category")
    @ApiOperation("Update category | 修改分类")
    @Log(description = "修改分类",types = {LogType.MODIFY})
    public Result update(@RequestParam("id") String categoryId,@RequestParam(value = "name",required = false)String name, @RequestParam(value = "description",required = false) String des){
        CategoryParam param = new CategoryParam(name,des);
        return build(categoryService.update(categoryId,param));
    }

}
