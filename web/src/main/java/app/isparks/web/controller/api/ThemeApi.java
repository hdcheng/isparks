package app.isparks.web.controller.api;

import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.web.service.impl.ThemeServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api("主题管理")
@RestController
@RequestMapping("/api/admin/theme")
public class ThemeApi {

    @Autowired
    private ThemeServiceImpl themeService;

    @RequestMapping(value = "list",method = {RequestMethod.GET})
    public Result listThemes(){
        Map<String,String> themes = themeService.listThemes();

        return ResultUtils.success().setData(themes);
    }

    @RequestMapping(value = "update",method = {RequestMethod.GET})
    public Result update(@RequestParam(value = "themeId",required = false)String themeId){
        themeService.active(themeId);
        return ResultUtils.success();
    }

    @RequestMapping(value = "menus",method = {RequestMethod.GET})
    public Result menus(){

        return ResultUtils.success();
    }

}
