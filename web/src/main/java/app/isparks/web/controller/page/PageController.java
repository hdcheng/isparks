package app.isparks.web.controller.page;

import app.isparks.core.service.IPostService;
import app.isparks.core.util.StringUtils;
import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import app.isparks.core.framework.enhance.WebPage;
import app.isparks.core.web.property.WebConstant;
import app.isparks.plugin.enhance.web.*;
import app.isparks.web.controller.Router;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author： chenghd
 * @date： 2021/3/12
 */
@Api("")
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



    @ApiOperation("预览")
    @RequestMapping(value = "post/temp/{key}",method = {RequestMethod.GET})
    public String postTemp(@PathVariable("key")String key, Model model){

        String postId = postService.getTempLinkPostIdByKey(key);

        if(StringUtils.isEmpty(postId)){
            return "404";
        }

        return adminController.postReview(postId,model);
    }

    @ApiOperation("首页")
    @RequestMapping(value = {"","index"},method = {RequestMethod.GET})
    public String index(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){
        Object pageData = pageApi.index(pageFilter(page),sizeFilter(size)).getData();
        model.addAttribute(PAGE_DATA_MODEL_KEY,pageData);
        return WebPage.INDEX.file();
    }

    @ApiOperation("post页面")
    @RequestMapping(value = "post/{id}",method = {RequestMethod.GET})
    public String post(@PathVariable("id")String id, Model model, HttpServletRequest request, HttpServletResponse response){

        if(StringUtils.isEmpty(id)){
            return "404";
        }

        model.addAttribute(PAGE_DATA_MODEL_KEY,pageApi.post(id).getData());

        return WebPage.POST.file();
    }

    @ApiOperation("归档")
    @RequestMapping(value = "archive",method = {RequestMethod.GET})
    public String archive(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model){

        int p = page == null ? 1 : page;
        int s = size == null ? 10:size;

        model.addAttribute(PAGE_DATA_MODEL_KEY,pageApi.archive(p,s).getData());

        return WebPage.ARCHIVE.file();
    }

    @ApiOperation("相关链接")
    @RequestMapping(value = "link",method = {RequestMethod.GET})
    public String link(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){

        int p = page == null ? 1 : page;
        int s = size == null ? 10:size;

        model.addAttribute(PAGE_DATA_MODEL_KEY,pageApi.link(p,s).getData());

        return WebPage.LINK.file();
    }

    @ApiOperation("关于")
    @RequestMapping(value = "about",method = {RequestMethod.GET})
    public String about(Model model){

        model.addAttribute(PAGE_DATA_MODEL_KEY,pageApi.about(1,10).getData());

        return WebPage.ABOUT.file();
    }

    @ApiOperation("图片预览")
    @RequestMapping(value = "gallery",method = {RequestMethod.GET})
    public String gallery(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){

        int p = page == null ? 1 : page;
        int s = size == null ? 10 : size;

        model.addAttribute(PAGE_DATA_MODEL_KEY,pageApi.gallery(p,s).getData());

        return WebPage.GALLERY.file();
    }

    @ApiOperation("分类")
    @RequestMapping(value = "category",method = {RequestMethod.GET})
    public String category(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){


        return WebPage.CATEGORY.file();
    }

    @ApiOperation("标签")
    @RequestMapping(value = "tag",method = {RequestMethod.GET})
    public String tag(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){


        return WebPage.TAG.file();
    }

    @ApiOperation("标签")
    @RequestMapping(value = "web/{path}",method = {RequestMethod.GET})
    public String other(@PathVariable("path")String path,Model model,HttpServletRequest request, HttpServletResponse response){


        return WebPage.UNKNOWN.file() + path;
    }

    private int pageFilter(Integer page){
        return page == null ? 1 : (page <= 0 ? 1 : page);
    }

    private int sizeFilter(Integer size){
        return size == null ? 10 : (size <= 0 ? 10 : size);
    }
}
