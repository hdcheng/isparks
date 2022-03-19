package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.ISysService;
import app.isparks.web.service.impl.AbstractThemeService;
import app.isparks.core.web.property.WebProperties;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.OptionServiceImpl;
import app.isparks.service.impl.SysServiceImpl;
import app.isparks.web.pojo.param.WebSettingParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenghd
 */

@Api("系统管理接口")
@RequestMapping("v1/admin")
@RestController("v1_SysApi")
public class SysApi extends BasicApi{

    private ISysService sysService;

    private IOptionService optionService;

    private AbstractThemeService themeService;

    public SysApi(SysServiceImpl sysService, OptionServiceImpl optionService, AbstractThemeService themeService) {
        this.sysService = sysService;
        this.optionService = optionService;
        this.themeService = themeService;
    }

    @GetMapping("installed")
    @ApiOperation("The system is installed | 系统是否已经安装过")
    public Result isInstalled(){
        return success().withData(sysService.isInstalled());
    }

    @GetMapping("web/settings")
    @ApiOperation("Get web settings | 获取网站设置")
    public Result<Map<String,String>> getWebSetting(){
        String copy = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_FOOTER_COPY,String.class);
        String domain = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_URL,String.class);
        String logoText = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_LOGO_TEXT,String.class);
        String logo = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_LOGO,String.class);
        String name = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_TITLE,String.class);
        String description = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_DESCRIPTION,String.class);
        String themeId = themeService.themeId();

        Map<String,String> webConfig = new HashMap<>();
        webConfig.put("copy",copy);
        webConfig.put("domain",domain);
        webConfig.put("name",name);
        webConfig.put("logText",logoText);
        webConfig.put("logo",logo);
        webConfig.put("description",description);
        webConfig.put("themeId",themeId);

        return ResultUtils.success("success",webConfig);
    }

    @PatchMapping("web/settings")
    @ApiOperation("Update web settings | 更新网站配置")
    @Log(description = "更新网站配置",types = {LogType.SYS,LogType.MODIFY})
    public Result update(@RequestBody WebSettingParam param){

        Map<String,Object> config = new HashMap<>();

        config.put(WebProperties.WEBSITE_TITLE.getKey(),param.getName());
        config.put(WebProperties.WEBSITE_FOOTER_COPY.getKey(), param.getCopy());
        config.put(WebProperties.WEBSITE_DESCRIPTION.getKey(), param.getDescription());
        config.put(WebProperties.WEBSITE_LOGO_TEXT.getKey(),param.getLogText());
        config.put(WebProperties.WEBSITE_LOGO.getKey(), param.getLogo());
        config.put(WebProperties.WEBSITE_URL.getKey(), param.getDomain());

        optionService.saveOrUpdate(config);

        sysService.syncToConfigFile();

        return ResultUtils.success();
    }

}
