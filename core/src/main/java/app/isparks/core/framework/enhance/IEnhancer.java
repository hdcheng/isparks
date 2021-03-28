package app.isparks.core.framework.enhance;

/**
 * VIEW结果增强
 *
 * @author： chenghd
 * @date： 2021/3/23
 */
public interface IEnhancer<O> {

   <E extends IEnhancer> void setNextEnhancer(E enhancer);

    void execute(O o);

    void enhance(O o);

}
