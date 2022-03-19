package app.isparks.web.controller.page;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.service.IPostService;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.property.WebConstant;
import app.isparks.web.controller.Router;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author： chenghd
 * @date： 2021/3/12
 */
@Controller
@RequestMapping
public class PageController {

    @Autowired
    private IPostService postService;

    @Autowired
    private AdminController adminController;

    @ApiOperation("预览文章")
    @RequestMapping(value = "post/temp/{key}",method = {RequestMethod.GET} , produces  = {MediaType.TEXT_HTML_VALUE})
    public String postTemp(@PathVariable("key")String key, Model model){

        String postId = postService.getTempLinkPostIdByKey(key);

        if(StringUtils.isEmpty(postId)){
            return "404";
        }

        return adminController.postReview(postId,model);
    }

    @ApiOperation("网站首页")
    @GetMapping(value = {"","index"} )
    @Log(description = "访问首页", types = {LogType.VISIT})
    public String index(Model model){
        return "web/index";
    }

    @ApiOperation("访问指定页面（一层）")
    @GetMapping(value = "page/{page}" )
    public String page(@PathVariable("page")String page){
        return "web/" + page;
    }

    @ApiOperation("访问指定页面（两层）")
    @GetMapping(value = "page/{page}/{page1}" )
    public String page(@PathVariable("page")String page,@PathVariable("page1")String page1){
        return "web/" + page + "/" + page1;
    }

    @ApiOperation("访问指定页面（三层）")
    @GetMapping(value = "page/{page}/{page1}/{page2}" )
    public String page(@PathVariable("page")String page,@PathVariable("page1")String page1,@PathVariable("page2")String page2){
        return "web/" + page + "/" + page1 + "/" + page2;
    }

}
