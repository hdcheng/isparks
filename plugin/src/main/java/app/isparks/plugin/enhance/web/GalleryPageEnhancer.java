package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import org.springframework.ui.Model;

/**
 * @author： chenghd
 * @date： 2021/3/24
 */
public final class GalleryPageEnhancer extends AbstractViewModelEnhancer {

    private static AbstractViewModelEnhancer enhancer;

    private GalleryPageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (GalleryPageEnhancer.class){
                if(enhancer == null){
                    enhancer = new GalleryPageEnhancer();
                }
            }
        }
        return enhancer;
    }

}
