package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import app.isparks.core.pojo.base.BaseVO;

/**
 * @author： chenghd
 * @date： 2021/3/23
 */
public final class IndexPageEnhancer extends AbstractViewModelEnhancer<BaseVO> {

    private static AbstractViewModelEnhancer enhancer;

    private IndexPageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (IndexPageEnhancer.class){
                if(enhancer == null){
                    enhancer = new IndexPageEnhancer();
                }
            }
        }
        return enhancer;
    }


}
