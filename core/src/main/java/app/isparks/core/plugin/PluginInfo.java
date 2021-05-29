package app.isparks.core.plugin;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chenghd
 * @date 20201/5/25
 */
@Getter
@Setter
public class PluginInfo {


    // 插件ID
    private String id;

    // 依赖
    private List<String> dependencies ;

    // 作者
    private String provider;

    // 插件的文件路径，即下面的plugin类全路径
    private String className;

    // 版本:0.0.1
    private String version;

    // 插件状态
    private PluginStatus status;

}
