package app.isparks.web.controller.api;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.CategoryParam;
import app.isparks.core.service.ICategoryService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理接口
 *
 * @author： chenghd
 * @date： 2021/2/25
 */
@Api("分类管理")
@RestController
@RequestMapping("/api/admin/category")
public class CategoryApi {

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation("添加分类")
    @RequestMapping(value = "create",method = {RequestMethod.POST})
    @Log(description = "创建分类", types = {LogType.INSERT})
    public Result create(@RequestBody CategoryParam param){

        return ResultUtils.build(categoryService.create(param));

    }

    @ApiOperation("删除分类")
    @RequestMapping(value = "delete/by/name",method = {RequestMethod.GET})
    @Log(description = "删除分类", types = {LogType.DELETE})
    public Result delete(@RequestParam("name")String name){

        return ResultUtils.build(categoryService.delete(name));

    }

    @ApiOperation("分页查找")
    @RequestMapping(value = "page",method = {RequestMethod.GET})
    public Result page(@RequestParam("page")String page,@RequestParam("size")String size){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        PageData<CategoryDTO> pageData = categoryService.page(p <= 0 ? 1 : p,s <= 0 ? 10 : s);

        return ResultUtils.build(pageData);
    }

    @ApiOperation("所有分类")
    @RequestMapping(value = "list",method = {RequestMethod.GET})
    public Result listAll(){
        return ResultUtils.success().setData(categoryService.listAll());
    }


    @ApiOperation("更新分类")
    @RequestMapping(value = "update",method = {RequestMethod.GET})
    @Log(description = "更新分类", types = {LogType.MODIFY})
    public Result update(@RequestParam("id") String categoryId,@RequestParam(value = "name",required = false)String name, @RequestParam(value = "description",required = false) String des){

        CategoryParam param = new CategoryParam(name,des);

        return ResultUtils.build(categoryService.update(categoryId,param));
    }

}
