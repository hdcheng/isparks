package app.isparks.addons.blog.api;

import app.isparks.addons.blog.service.BlogServiceImpl;
import app.isparks.addons.blog.service.IBlogService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

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


}
