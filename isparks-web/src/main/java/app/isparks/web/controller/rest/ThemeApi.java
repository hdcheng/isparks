package app.isparks.web.controller.rest;


import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.web.support.Result;
import app.isparks.web.service.impl.ThemeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */

@Api("主题管理接口")
@RequestMapping("v1/admin")
@RestController("v1_ThemeApi")
public class ThemeApi extends BasicApi{

    private ThemeServiceImpl themeService;

    public ThemeApi(ThemeServiceImpl themeService){
        this.themeService = themeService;
    }

    @GetMapping("themes")
    @ApiOperation("Get all themes | 获取所有主题")
    public Result listThemes(){
        return build(themeService.listThemes());
    }

    @PutMapping("theme/reset")
    @ApiOperation("reset theme | 重置主题主题")
    @Log(description = "重置主题",types = {LogType.MODIFY})
    public Result reset(){
        themeService.reset();
        return success();
    }

    @PatchMapping("theme/{themeId}")
    @ApiOperation("Update current theme | 更新主题")
    @Log(description = "更新主题",types = {LogType.MODIFY})
    public Result update(@PathVariable(value = "themeId",required = false)String themeId){
        themeService.active(themeId);
        return success();
    }

}
