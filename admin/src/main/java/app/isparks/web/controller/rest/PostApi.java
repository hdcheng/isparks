package app.isparks.web.controller.rest;

import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.param.PostParam;
import app.isparks.core.pojo.param.UpdatePostParam;
import app.isparks.core.service.IPostService;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.PostServiceImpl;
import app.isparks.web.config.WebProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */

@Api("文章管理接口")
@RequestMapping("v1/admin")
@RestController("v1_PostApi")
public class PostApi extends BasicApi{

    private IPostService postService;

    public PostApi(PostServiceImpl postService){
        this.postService = postService;
    }

    @PostMapping("post")
    @ApiOperation("Create post | 创建新文章")
    public Result create(@RequestBody PostParam param){
        return build(postService.create(param, DataStatus.VALID));
    }

    @PostMapping("post/temp")
    @ApiOperation("Create temporary post | 创建草稿")
    public Result createTemp(@RequestBody PostParam param){
        return build(postService.create(param,DataStatus.TEMP));
    }

    @DeleteMapping("post/{id}")
    @ApiOperation(" Delete post by id | 删除数据")
    public Result delete(@PathVariable("id") String id){
        return build(postService.delete(id));
    }

    @GetMapping("post/page")
    @ApiOperation("Get posts by page |  分页查询所有数据")
    public Result pageAll(@RequestParam(value = "page",defaultValue = "1") int page,
                          @RequestParam(value = "size",defaultValue = "10") int size){
        return build(postService.page(page,size,null));
    }

    @GetMapping("post/page/valid")
    @ApiOperation("Get valid posts by page | 分页查询已发表的文章")
    public Result pageValid(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size){
        return build(postService.page(page,size, DataStatus.VALID));
    }

    @GetMapping("post/page/temp")
    @ApiOperation("Get temporary posts by page | 分页查询已草稿箱中的文章")
    public Result pageTemp(@RequestParam(value = "page",defaultValue = "1") int page,
                           @RequestParam(value = "size",defaultValue = "10") int size){
        return build(postService.page(page,size, DataStatus.TEMP));
    }

    @GetMapping("post/page/remove")
    @ApiOperation("Get removed posts by page | 分页查询已移除的文章")
    public Result pageRemove(@RequestParam(value = "page",defaultValue = "1") int page,
                             @RequestParam(value = "size",defaultValue = "10") int size){
        return build(postService.page(page,size, DataStatus.REMOVE));
    }

    @GetMapping("post/{id}")
    @ApiOperation("Get post by id | 根据 ID 查找文章")
    public Result getById(@PathVariable("id") String id){
        return build(postService.getById(id));
    }

    @GetMapping("post/{id}/temp")
    @ApiOperation(" Get temporary post link | 获取文章的临时链接")
    public Result generateTempLink(@PathVariable("id") String postId ,
                                   @RequestParam("minutes") int minutes){

        String cacheKey = postService.createTempLinkKey(postId,minutes * 60000);

        if(StringUtils.isEmpty(cacheKey)){
            return fail("生成临时链接失败");
        }else{
            String link = WebProperties.HOST +"/post/temp/" + cacheKey;
            return success().withData(link);
        }

    }

    @PatchMapping("post/{id}/remove")
    @ApiOperation("Update post status to remove | 将文章状态改为 remove")
    public Result remove(@PathVariable("id") String id){
        return build(postService.remove(id));
    }

    @PatchMapping("post/{id}/valid")
    @ApiOperation("Update post status to valid | 将文章状态改为 valid")
    public Result restore(@PathVariable("id") String id){
        return build(postService.restore(id));
    }

    @PutMapping("post/{id}")
    @ApiOperation("Update post | 更新文章内容")
    public Result update(@PathVariable("id") String id,@RequestBody UpdatePostParam param){
        param.setId(id);
        return build(postService.update(param));
    }


}
