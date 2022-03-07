package app.isparks.plugin.enhance.web;

import app.isparks.plugin.enhance.AbstractViewModelEnhancer;
import app.isparks.core.pojo.base.BaseVO;

/**
 * @author chenghd
 */
public class LinkPageEnhancer extends AbstractViewModelEnhancer<BaseVO> {

    private static AbstractViewModelEnhancer enhancer;

    private LinkPageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (LinkPageEnhancer.class){
                if(enhancer == null){
                    enhancer = new LinkPageEnhancer();
                }
            }
        }
        return enhancer;
    }

}
