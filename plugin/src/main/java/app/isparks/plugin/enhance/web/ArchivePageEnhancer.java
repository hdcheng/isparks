package app.isparks.plugin.enhance.web;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;

/**
 * @author： chenghd
 * @date： 2021/3/23
 */
public final class ArchivePageEnhancer extends AbstractViewModelEnhancer {

    private static AbstractViewModelEnhancer enhancer;

    private ArchivePageEnhancer(){}

    public static AbstractViewModelEnhancer singleton() {
        if(enhancer == null){
            synchronized (ArchivePageEnhancer.class){
                if(enhancer == null){
                    enhancer = new ArchivePageEnhancer();
                }
            }
        }
        return enhancer;
    }

}
