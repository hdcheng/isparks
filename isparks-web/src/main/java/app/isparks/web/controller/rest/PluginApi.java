package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.plugin.IPlugin;
import app.isparks.plugin.IPluginManager;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.plugin.PluginManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenghd
 */


@Api("插件管理接口")
@RequestMapping("v1/admin")
@RestController("v1_PluginApi")
public class PluginApi extends BasicApi{


    @DeleteMapping("plugin/{id}")
    @ApiOperation("Delete a plugin which is stopped | 删除一个插件（只有已经停止的插件才能删除）")
    @Log(description = "删除一个插件",types = {LogType.DELETE})
    public Result deletePlugin(@PathVariable("id") String id){
        PluginManager.singleton().reject(id);
        return success();
    }

    @GetMapping("plugin")
    @ApiOperation("Get all plugins | 获取所有插件")
    public Result plugins(){
        List<IPlugin> plugins = PluginManager.singleton().plugins();
        return build(plugins);
    }

    @PatchMapping("plugin/{id}/start")
    @ApiOperation("Start a plugin | 启动一个插件")
    @Log(description = "启动一个插件",types = {LogType.MODIFY})
    public Result startPlugin(@PathVariable("id") String id){
        IPluginManager pluginManager = PluginManager.singleton();
        pluginManager.startPlugin(id);
        return success();
    }

    @PatchMapping("plugin/{id}")
    @ApiOperation("Stop plugin by id | 停用插件")
    @Log(description = "停用插件",types = {LogType.MODIFY})
    public Result removePlugin(@PathVariable("id") String id){
        IPluginManager pluginManager = PluginManager.singleton();

        pluginManager.stopPlugin(id);

        return success();
    }

}
