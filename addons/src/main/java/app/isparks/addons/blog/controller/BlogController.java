package app.isparks.addons.blog.controller;


import app.isparks.addons.blog.api.BlogApi;
import app.isparks.addons.blog.api.BlogConstant;
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
    public String post(@PathVariable("id")String id, Model model, HttpServletRequest request, HttpServletResponse response){

        if(StringUtils.isEmpty(id)){
            return "404";
        }

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.post(id).getData());

        return WebPage.POST.file();
    }

    @RequestMapping(value = "archive",method = {RequestMethod.GET})
    public String archive(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model){

        int p = page == null ? 1 : page;
        int s = size == null ? 10: size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.archive(p,s).getData());

        return WebPage.ARCHIVE.file();
    }

    @RequestMapping(value = "link",method = {RequestMethod.GET})
    public String link(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){

        int p = page == null ? 1 : page;
        int s = size == null ? 10:size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.link(p,s).getData());

        return WebPage.LINK.file();
    }

    @RequestMapping(value = "about",method = {RequestMethod.GET})
    public String about(Model model){

        model.addAttribute(BlogConstant.PAGE_DATA_KEY,blogApi.about().getData());

        return WebPage.ABOUT.file();

    }

    @RequestMapping(value = "gallery",method = {RequestMethod.GET})
    public String gallery(@RequestParam(value = "page",required = false)Integer page , @RequestParam(value = "size",required = false)Integer size , Model model,HttpServletRequest request, HttpServletResponse response){

        int p = page == null ? 1 : page;
        int s = size == null ? 10 : size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.gallery(p,s).getData());

        return WebPage.GALLERY.file();
    }

    @RequestMapping(value = "category",method = {RequestMethod.GET,RequestMethod.POST})
    public String category(@RequestParam(value = "page",required = false)Integer page ,
                           @RequestParam(value = "size",required = false)Integer size , Model model){

        int p = page == null ? 1 : page;
        int s = size == null ? 10 : size;

        model.addAttribute(BlogConstant.PAGE_DATA_KEY, blogApi.category(p,s).getData());

        return WebPage.CATEGORY.file();
    }

    @RequestMapping(value = "tag",method = {RequestMethod.GET,RequestMethod.POST})
    public Result tag(@RequestParam("page")int page,@RequestParam("size")int size){

        return ResultUtils.fail();
    }

}
