package app.isparks.plugin.enhance;

import java.util.Map;

/**
 * 接口增强器
 */
@FunctionalInterface
public interface IRequestEnhancer {

    Object process(Map<String,Object> params);

}
