package app.isparks.web.controller.api;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.TagParam;
import app.isparks.core.service.ITagService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签
 *
 * @author： chenghd
 * @date： 2021/2/26
 */
@Api("标签管理")
@RestController
@RequestMapping("/api/admin/tag")
public class TagApi {

    @Autowired
    private ITagService tagService;

    @ApiOperation("创建新标签")
    @RequestMapping(value = "create",method = {RequestMethod.POST})
    @Log(description = "创建标签", types = {LogType.INSERT})
    public Result create(@RequestBody TagParam param){
        return ResultUtils.build(tagService.create(param));
    }

    @ApiOperation("分页查找标签")
    @RequestMapping(value = "page",method = {RequestMethod.GET})
    public Result page(@RequestParam("page") String page,@RequestParam("size") String size){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        return ResultUtils.build(tagService.page(p <= 0 ? 1 : p , s <= 0 ? 10 : s));
    }


    @ApiOperation("删除标签")
    @RequestMapping(value = "delete/by/name",method = {RequestMethod.GET})
    @Log(description = "删除标签", types = {LogType.DELETE})
    public Result deleteByName(@RequestParam("name") String tagName){
        return ResultUtils.build(tagService.deleteByName(tagName));
    }

}

