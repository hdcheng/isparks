package app.isparks.web.controller.api;

import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.plugin.PluginManager;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("插件管理管理")
@RestController
@RequestMapping("/api/admin/plugin")
public class PluginApi {

    @GetMapping("all")
    public Result plugins(){
        return ResultUtils.success().setData(PluginManager.singleton().plugins());
    }

    @GetMapping("stop")
    public Result removePlugin(@RequestParam("id")String id){
        PluginManager.singleton().stopPlugin(id);
        return ResultUtils.success();
    }

    @GetMapping("start")
    public Result startPlugin(@RequestParam("id")String id){
        PluginManager.singleton().startPlugin(id);
        return ResultUtils.success();
    }

    @GetMapping("delete")
    public Result deletePlugin(@RequestParam("id")String id){
        PluginManager.singleton().reject(id);
        return ResultUtils.success();
    }



}


