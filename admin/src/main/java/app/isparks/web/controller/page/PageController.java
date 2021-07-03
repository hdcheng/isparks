package app.isparks.web.controller.page;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.plugin.enhance.AbstractViewModelEnhancer;
import app.isparks.core.pojo.base.BaseVO;
import app.isparks.core.service.IPostService;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.property.WebConstant;
import app.isparks.core.web.support.Result;
import app.isparks.plugin.enhance.web.IndexPageEnhancer;
import app.isparks.web.controller.Router;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private final static String PAGE_DATA_MODEL_KEY = WebConstant.PAGE_DATA_KEY;

    @Autowired
    private Router router;

    @Autowired
    private IPostService postService;

    @Autowired
    private PageApi pageApi;

    @Autowired
    private AdminController adminController;

    private final static AbstractViewModelEnhancer indexPageEnhancer;

    static {
        indexPageEnhancer = IndexPageEnhancer.singleton();
    }

    @ApiOperation("预览文章")
    @RequestMapping(value = "post/temp/{key}",method = {RequestMethod.GET})
    public String postTemp(@PathVariable("key")String key, Model model){

        String postId = postService.getTempLinkPostIdByKey(key);

        if(StringUtils.isEmpty(postId)){
            return "404";
        }

        return adminController.postReview(postId,model);
    }

    @ApiOperation("网站首页")
    @RequestMapping(value = {"","index"},method = {RequestMethod.GET})
    @Log(description = "访问首页", types = {LogType.VISIT})
    public String index(Model model){

        Result<BaseVO> result = pageApi.index();

        model.addAttribute(PAGE_DATA_MODEL_KEY,result.getData());

        indexPageEnhancer.execute(model);

        return "web/index";
    }



}
