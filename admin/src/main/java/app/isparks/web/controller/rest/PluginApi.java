package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.plugin.PluginManager;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.web.support.Result;
import app.isparks.plugin.DefaultPluginManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
        PluginManager pluginManager = DefaultPluginManager.instance();
        pluginManager.deletePlugin(id);
        return success();
    }

    @GetMapping("plugin")
    @ApiOperation("Get all plugins | 获取所有插件")
    public Result plugins(){
        PluginManager pluginManager = DefaultPluginManager.instance();
        return build(pluginManager.plugins());
    }

    @PatchMapping("plugin/{id}/start")
    @ApiOperation("Start a plugin | 启动一个插件")
    @Log(description = "启动一个插件",types = {LogType.MODIFY})
    public Result startPlugin(@PathVariable("id") String id){
        PluginManager pluginManager = DefaultPluginManager.instance();
        pluginManager.startPlugin(id);
        return success();
    }

    @PatchMapping("plugin/{id}")
    @ApiOperation("Stop plugin by id | 停用插件")
    @Log(description = "停用插件",types = {LogType.MODIFY})
    public Result removePlugin(@PathVariable("id") String id){
        PluginManager pluginManager = DefaultPluginManager.instance();

        pluginManager.stopPlugin(id);

        return success();
    }

}
