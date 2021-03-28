package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import org.springframework.ui.Model;

/**
 * @author： chenghd
 * @date： 2021/3/24
 */
public final class LinkPageEnhancer extends AbstractViewModelEnhancer {

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
