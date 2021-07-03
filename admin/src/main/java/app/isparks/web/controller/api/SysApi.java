package app.isparks.web.controller.api;


import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.ISysService;
import app.isparks.core.service.inter.AbstractThemeService;
import app.isparks.core.web.property.WebProperties;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.OptionServiceImpl;
import app.isparks.service.impl.SysServiceImpl;
import app.isparks.web.pojo.param.WebSettingParam;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统 API
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Api("系统管理")
@RestController
@RequestMapping("api/sys")
public class SysApi {

    private ISysService sysService;

    private IOptionService optionService;

    private AbstractThemeService themeService;

    public SysApi(SysServiceImpl sysService, OptionServiceImpl optionService, AbstractThemeService themeService) {
        this.sysService = sysService;
        this.optionService = optionService;
        this.themeService = themeService;
    }

    @RequestMapping(value = "installed",method = {RequestMethod.GET})
    public Result isInstalled(){
        boolean isInstalled = sysService.isInstalled();
        return ResultUtils.success().setData(isInstalled);
    }

    @RequestMapping(value = "update/web/settings",method = {RequestMethod.POST})
    @Log(description = "更新WEB设置", types = {LogType.MODIFY})
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

    @RequestMapping(value = "get/web/settings",method = {RequestMethod.GET})
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

}
