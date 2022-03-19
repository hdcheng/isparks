package app.isparks.plugin.service;

import app.isparks.plugin.enhance.IRequestEnhancer;

public interface IRequestPlugin {

    boolean addRequest(String method , String path, IRequestEnhancer enhancer);

    boolean removeRequest(String method , String path);

}
