package app.isparks.web.controller.api;

import app.isparks.core.plugin.PluginManager;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.plugin.DefaultPluginManager;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("插件管理管理")
@RestController
@RequestMapping("/api/admin/plugin")
public class PluginApi {

    @GetMapping("all")
    public Result plugins(){
        PluginManager pluginManager = DefaultPluginManager.instance();
        return ResultUtils.success().setData(pluginManager.plugins());

    }

}
