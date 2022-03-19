package app.isparks.web.controller.open;

import app.isparks.core.web.OpenApi;
import app.isparks.plugin.enhance.IRequestEnhancer;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractOpenApi implements OpenApi {

    private Map<String, IRequestEnhancer> GET_SERVICES = new HashMap<>();

    private Map<String, IRequestEnhancer> POST_SERVICES = new HashMap<>();

    public final IRequestEnhancer getService(String method,String path){
        switch (method){
            case "GET"  : return GET_SERVICES.get(path);
            case "POST" : return POST_SERVICES.get(path);
            default : return null;
        }
    }

    public final synchronized boolean setService(String method ,String path , IRequestEnhancer enhancer){
        if(path == null || path.isEmpty()){
            return false;
        }

        if(path.startsWith("/")){
            path = path.substring(1);
        }

        if(path.endsWith("/")){
            path = path.substring(0,path.length() - 1);
        }

        Map<String,IRequestEnhancer> NEW_SERVICE_MAP;
        switch (method){
            case "GET"  : NEW_SERVICE_MAP = new HashMap<>(GET_SERVICES);break;
            case "POST" : NEW_SERVICE_MAP = new HashMap<>(POST_SERVICES);break;
            default     : return false;
        }

        if(enhancer == null || NEW_SERVICE_MAP.containsKey(path)){
            return false;
        }

        NEW_SERVICE_MAP.put(path,enhancer);

        switch (method){
            case "GET"  : GET_SERVICES = NEW_SERVICE_MAP;break;
            case "POST" : POST_SERVICES = NEW_SERVICE_MAP;break;
        }

        return true;
    }

    public final synchronized boolean removeService(String method ,String path ){
        Map<String,IRequestEnhancer> NEW_SERVICE_MAP;

        switch (method){
            case "GET"  : NEW_SERVICE_MAP = new HashMap<>(GET_SERVICES);break;
            case "POST" : NEW_SERVICE_MAP = new HashMap<>(POST_SERVICES);break;
            default     : return false;
        }

        if(path == null || path.isEmpty() || !NEW_SERVICE_MAP.containsKey(path)){
            return false;
        }

        NEW_SERVICE_MAP.remove(path);

        switch (method){
            case "GET"  : GET_SERVICES = NEW_SERVICE_MAP;break;
            case "POST" : POST_SERVICES = NEW_SERVICE_MAP;break;
        }

        return true;
    }

}
