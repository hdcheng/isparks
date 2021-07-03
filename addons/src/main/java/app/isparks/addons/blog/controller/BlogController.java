package app.isparks.addons.blog.controller;


import app.isparks.addons.blog.api.BlogApi;
import app.isparks.addons.blog.api.BlogConstant;
import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.plugin.enhance.web.WebPage;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BlogController {

    @Autowired
    private BlogApi blogApi;

    @RequestMapping(value = "post/{id}",method = {RequestMethod.GET})
    @Log(description = "访问博客-浏览文章", types = {LogType.VISIT})
    public String post(@PathVariable("id")String id, Model model, HttpServletRequest request, HttpServletResponse response){

        if(StringUtils.isEmpty(id)){
            return "404";
        }

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.post(id).getData());

        return WebPage.POST.file();
    }

    @RequestMapping(value = "archive",method = {RequestMethod.GET})
    @Log(description = "访问博客页面-归档", types = {LogType.VISIT})
    public String archive(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model){

        int p = page == null ? 1 : page;
        int s = size == null ? 10: size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.archive(p,s).getData());

        return WebPage.ARCHIVE.file();
    }

    @RequestMapping(value = "link",method = {RequestMethod.GET})
    @Log(description = "访问博客页面-友链", types = {LogType.VISIT})
    public String link(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){

        int p = page == null ? 1 : page;
        int s = size == null ? 10:size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.link(p,s).getData());

        return WebPage.LINK.file();
    }

    @RequestMapping(value = "about",method = {RequestMethod.GET})
    @Log(description = "访问博客页面-关于", types = {LogType.VISIT})
    public String about(Model model){

        model.addAttribute(BlogConstant.PAGE_DATA_KEY,blogApi.about().getData());

        return WebPage.ABOUT.file();

    }

    @RequestMapping(value = "gallery",method = {RequestMethod.GET})
    @Log(description = "访问博客页面-图库", types = {LogType.VISIT})
    public String gallery(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){

        int p = page == null ? 1 : page;
        int s = size == null ? 10 : size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.gallery(p,s).getData());

        return WebPage.GALLERY.file();
    }

    @RequestMapping(value = "category",method = {RequestMethod.GET,RequestMethod.POST})
    @Log(description = "访问博客页面-分类", types = {LogType.VISIT})
    public String category(@RequestParam(value = "page",required = false)Integer page ,
                           @RequestParam(value = "size",required = false)Integer size , Model model){

        int p = page == null ? 1 : page;
        int s = size == null ? 10 : size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.category(p,s).getData());

        return WebPage.CATEGORY.file();
    }

    @RequestMapping(value = "tag",method = {RequestMethod.GET,RequestMethod.POST})
    @Log(description = "访问博客页面-标签", types = {LogType.VISIT})
    public Result tag(@RequestParam("page")int page,@RequestParam("size")int size){

        return ResultUtils.fail();
    }

}
