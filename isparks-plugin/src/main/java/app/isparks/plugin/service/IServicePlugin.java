package app.isparks.plugin.service;

import app.isparks.core.pojo.base.BaseDTO;
import app.isparks.plugin.enhance.AbstractEnhancer;

public interface IServicePlugin {

    <T , D extends BaseDTO> boolean addEnhancer(Class<T> tClass , AbstractEnhancer<D> enhancer);

    <T , D extends BaseDTO> boolean removeEnhancer(Class<T> tClass , AbstractEnhancer<D> enhancer);
}
