package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import org.springframework.ui.Model;

/**
 * @author： chenghd
 * @date： 2021/3/24
 */
public final class AboutPageEnhancer extends AbstractViewModelEnhancer {

    private static AbstractViewModelEnhancer enhancer;

    private AboutPageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (AboutPageEnhancer.class){
                if(enhancer == null){
                    enhancer = new AboutPageEnhancer();
                }
            }
        }
        return enhancer;
    }

}
