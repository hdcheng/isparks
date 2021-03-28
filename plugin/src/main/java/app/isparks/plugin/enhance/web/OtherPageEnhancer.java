package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import org.springframework.ui.Model;

/**
 * @author： chenghd
 * @date： 2021/3/23
 */
public final class OtherPageEnhancer extends AbstractViewModelEnhancer {

    private static AbstractViewModelEnhancer enhancer;

    private OtherPageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (OtherPageEnhancer.class){
                if(enhancer == null){
                    enhancer = new OtherPageEnhancer();
                }
            }
        }
        return enhancer;
    }



}
