package app.isparks.web.controller.api;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.PostParam;
import app.isparks.core.pojo.param.UpdatePostParam;
import app.isparks.core.service.IPostService;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.web.config.WebProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 帖子
 *
 * @author： chenghd
 * @date： 2021/2/28
 */
@Api("文章管理")
@RestController
@RequestMapping("/api/admin/post")
public class PostApi {

    @Autowired
    private IPostService postService;

    @ApiOperation("创建新文章")
    @RequestMapping(value = "create",method = {RequestMethod.POST})
    @Log(description = "新增文章", types = {LogType.INSERT})
    public Result create(@RequestBody PostParam param){
        if(StringUtils.isEmpty(param.getSummary())){
            param.setSummary("文章简介");
        }

        return ResultUtils.build(postService.create(param, DataStatus.VALID));
    }

    @ApiOperation("创建草稿")
    @RequestMapping(value = "create/temp",method = {RequestMethod.POST})
    @Log(description = "新增草稿", types = {LogType.INSERT})
    public Result createTemp(@RequestBody PostParam param){
        if(StringUtils.isEmpty(param.getSummary())){
            param.setSummary("文章简介");
        }
        return ResultUtils.build(postService.create(param,DataStatus.TEMP));
    }

    @ApiOperation("创建临时链接")
    @RequestMapping(value = "create/temp/link",method = {RequestMethod.GET})
    @Log(description = "创建临时链接", types = {LogType.QUERY})
    public Result generateTempLink(@RequestParam("id") String id,@RequestParam("minutes")int minutes){
        String cacheKey = postService.createTempLinkKey(id,minutes * 60000);

        if(StringUtils.isEmpty(cacheKey)){
            return ResultUtils.fail("生成临时链接失败");
        }else{
            String link = WebProperties.HOST +"/post/temp/" + cacheKey;
            return ResultUtils.success("success",link);
        }

    }

    @ApiOperation("删除数据")
    @RequestMapping(value = "delete",method = {RequestMethod.GET})
    @Log(description = "删除文章", types = {LogType.DELETE})
    public Result delete(@RequestParam("id") String id){
        return ResultUtils.build(postService.delete(id));
    }

    @ApiOperation("恢复数据")
    @RequestMapping(value = "restore",method = {RequestMethod.GET})
    public Result restore(@RequestParam("id") String id){
        return ResultUtils.build(postService.restore(id));
    }

    @ApiOperation("移除数据")
    @RequestMapping(value = "remove",method = {RequestMethod.GET})
    public Result remove(@RequestParam("id") String id){
        return ResultUtils.build(postService.remove(id));
    }

    @ApiOperation("更新")
    @RequestMapping(value = "update",method = {RequestMethod.POST})
    @Log(description = "更新文章", types = {LogType.MODIFY})
    public Result update(@RequestBody UpdatePostParam param){
        return ResultUtils.build(postService.update(param));
    }


    @ApiOperation("分页查询所有数据")
    @RequestMapping(value = "page/all",method = {RequestMethod.GET})
    public Result pageAll(@RequestParam("page") String page,@RequestParam("size") String size){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        return ResultUtils.build(postService.page(p > 0 ? p : 1, s > 0 ? s : 10 , null));
    }

    @ApiOperation("分页查询常规数据数据")
    @RequestMapping(value = "page/valid",method = {RequestMethod.GET})
    public Result pageValid(@RequestParam("page") String page,@RequestParam("size") String size){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        return ResultUtils.build(postService.page(p > 0 ? p : 1, s > 0 ? s : 10 , DataStatus.VALID));
    }

    @ApiOperation("分页查询草稿数据")
    @RequestMapping(value = "page/temp",method = {RequestMethod.GET})
    public Result pageTemp(@RequestParam("page") String page,@RequestParam("size") String size){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        return ResultUtils.build(postService.page(p > 0 ? p : 1, s > 0 ? s : 10 , DataStatus.TEMP));
    }

    @ApiOperation("分页查询已经移除的数据")
    @RequestMapping(value = "page/removed",method = {RequestMethod.GET})
    public Result pageRemove(@RequestParam("page") String page,@RequestParam("size") String size){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        return ResultUtils.build(postService.page(p > 0 ? p : 1, s > 0 ? s : 10 , DataStatus.REMOVE));
    }

    @ApiOperation("根据 id 查找数据")
    @RequestMapping(value = "get/by/id",method = {RequestMethod.GET})
    public Result pageTemp(@RequestParam("id") String id){

        return ResultUtils.build(postService.getById(id));

    }

}
