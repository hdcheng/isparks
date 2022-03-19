package app.isparks.web.controller.plugin;

import app.isparks.core.util.IOCUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.plugin.enhance.IRequestEnhancer;
import app.isparks.plugin.service.IRequestPlugin;
import app.isparks.web.controller.open.AbstractOpenApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RequestPlugin implements IRequestPlugin {

    private Logger log = LoggerFactory.getLogger(RequestPlugin.class);

    @Override
    public synchronized boolean addRequest(String method, String path, IRequestEnhancer enhancer) {
        if(StringUtils.hasEmpty(method,path) || enhancer == null){
            log.warn("参数不能为空");
            return false;
        }

        AbstractOpenApi openApi = IOCUtils.getBeanByClass(AbstractOpenApi.class).orElse(null);
        if(openApi == null){
            log.warn("AbstractOpenApi 不存在！");
            return false;
        }
        return openApi.setService(method.toUpperCase() , path.startsWith("/") ? path.substring(1) : path , enhancer);
    }

    @Override
    public synchronized boolean removeRequest(String method, String path) {
        if(StringUtils.hasEmpty(method,path) ){
            log.warn("参数不能为空");
            return false;
        }

        AbstractOpenApi openApi = IOCUtils.getBeanByClass(AbstractOpenApi.class).orElse(null);

        if(openApi == null){
            log.warn("AbstractOpenApi 不存在！");
            return false;
        }

        return openApi.removeService(method.toUpperCase(), path.startsWith("/") ? path.substring(1) : path);
    }

}
