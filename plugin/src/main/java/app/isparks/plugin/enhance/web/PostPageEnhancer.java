package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import org.springframework.ui.Model;

/**
 * post 页面增强器
 *
 * @author： chenghd
 * @date： 2021/3/23
 */
public final class PostPageEnhancer extends AbstractViewModelEnhancer {

    private static AbstractViewModelEnhancer enhancer;

    private PostPageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (PostPageEnhancer.class){
                if(enhancer == null){
                    enhancer = new PostPageEnhancer();
                }
            }
        }
        return enhancer;
    }


}
