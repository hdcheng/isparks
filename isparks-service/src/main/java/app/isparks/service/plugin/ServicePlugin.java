package app.isparks.service.plugin;

import app.isparks.core.pojo.base.BaseDTO;
import app.isparks.core.util.IOCUtils;
import app.isparks.plugin.enhance.AbstractEnhancer;
import app.isparks.plugin.enhance.IDecorator;
import app.isparks.plugin.service.IServicePlugin;

public class ServicePlugin implements IServicePlugin {

    @Override
    public <T,D extends BaseDTO> boolean addEnhancer(Class<T> tClass, AbstractEnhancer<D> enhancer) {
        if(tClass == null || enhancer == null){
            return false;
        }
        T service = IOCUtils.getBeanByClass(tClass).orElse(null);
        if(service != null && service instanceof IDecorator){
            ((IDecorator<D>)service).decorate(enhancer);
            return true;
        }
        return false;
    }

    @Override
    public <T , D extends BaseDTO> boolean removeEnhancer(Class<T> tClass, AbstractEnhancer<D> enhancer) {
        if(tClass == null || enhancer == null){
            return false;
        }
        T service = IOCUtils.getBeanByClass(tClass).orElse(null);
        if(service != null && service instanceof IDecorator){
            ((IDecorator<D>)service).unload(enhancer);
            return true;
        }
        return false;
    }
}
