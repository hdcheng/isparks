package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.TagParam;
import app.isparks.core.service.ITagService;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.TagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */

@Api("标签管理接口")
@RequestMapping("v1/admin")
@RestController("v1_TagApi")
public class TagApi extends BasicApi{

    private ITagService tagService;

    public TagApi(TagServiceImpl tagService){
        this.tagService = tagService;
    }

    @PostMapping("tag")
    @ApiOperation("Create tag | 创建标签")
    @Log(description = "创建标签",types = {LogType.INSERT})
    public Result create(@RequestBody TagParam param){
        return build(tagService.create(param));
    }

    @DeleteMapping("tag/name/{name}")
    @ApiOperation("Delete tag | 删除标签")
    @Log(description = "删除标签",types = {LogType.DELETE})
    public Result deleteByName(@PathVariable(value = "name") String name){
        return build(tagService.deleteByName(name));
    }

    @GetMapping("tag/page")
    @ApiOperation("Page get tag | 分页查找标签")
    public Result page(@RequestParam(value = "page",defaultValue = "1") int page,
                       @RequestParam(value = "size" ,defaultValue = "10") int size){
        return build(tagService.page(page,size));
    }

}
