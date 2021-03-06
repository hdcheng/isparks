package app.isparks.addons.blog.api;

import app.isparks.addons.blog.service.BlogServiceImpl;
import app.isparks.addons.blog.service.IBlogService;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("博客管理接口")
@RestController
@RequestMapping("api/admin/blog")
public class BlogAdminApi {

    private IBlogService blogService;

    public BlogAdminApi(BlogServiceImpl blogService){
        this.blogService = blogService;
    }

    @PostMapping("about/update")
    public Result updateAbout(@RequestBody String md){

        blogService.updateAboutPageMDContent(md);
        return ResultUtils.success();
    }

    @GetMapping(value = "index/update")
    public Result updateIndexConfig(@RequestParam(value = "title",required = false)String title,
                                    @RequestParam(value = "description",required = false)String description){
        if(!StringUtils.isEmpty(title)){
            blogService.updateOrSaveConfig("title",title);
        }

        if(!StringUtils.isEmpty(description)){
            blogService.updateOrSaveConfig("description",description);
        }

        return ResultUtils.success();
    }

    @GetMapping("configs/by/string")
    public Result getConfigsByString(@RequestParam("keyStr") String keys){
        String[] keysArr = keys.split(";");
        return ResultUtils.success().setData(blogService.listConfigByKeys(keysArr));
    }

}
