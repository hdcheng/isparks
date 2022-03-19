package app.isparks.plugin.enhance;

/**
 * 方法增强器
 * @author： chenghd
 * @date： 2021/3/23
 */
@FunctionalInterface
public interface IServiceEnhancer<O> {

    void enhance(O o);

}
