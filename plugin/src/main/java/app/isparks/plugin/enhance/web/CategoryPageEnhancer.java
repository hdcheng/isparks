package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import org.springframework.ui.Model;

/**
 * @author： chenghd
 * @date： 2021/3/24
 */
public final class CategoryPageEnhancer extends AbstractViewModelEnhancer {

    private static AbstractViewModelEnhancer enhancer;

    private CategoryPageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (CategoryPageEnhancer.class){
                if(enhancer == null){
                    enhancer = new CategoryPageEnhancer();
                }
            }
        }
        return enhancer;
    }

}
