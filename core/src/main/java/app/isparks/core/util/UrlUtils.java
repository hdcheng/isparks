package app.isparks.core.util;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.exception.SystemException;
import app.isparks.core.function.TowArgsFunction;
import app.isparks.core.service.IOptionService;
import app.isparks.core.web.property.WebConstant;
import app.isparks.core.web.property.WebProperties;

import java.util.List;
import java.util.function.Function;


/**
 * Url工具包
 * @author chenghd
 * @date 2021/4/10
 */
public final class UrlUtils {

    private static IOptionService optionService;

    private static void checkOptionService(){
        if(optionService == null){
            synchronized (UrlUtils.class){
                if(optionService == null){
                    IOCUtils.getBeanByClass(IOptionService.class).ifPresent(impl -> {
                        optionService = impl;
                    });

                    if(optionService == null){
                        throw new SystemException("get optionService exception .");
                    }

                }
            }
        }
    }

    /**
     * 静态文件路径转换成URL链接。
     *
     * 如果批量转换使用 parseStaticResourceToUrlLinks 效率更高。
     *
     */
    public static String parseStaticResourceToUrlLink(String localFilePath){
        if(StringUtils.isEmpty(localFilePath)){
            return "";
        }

        if(localFilePath.startsWith(ISparksConstant.PROTOCOL_HTTPS) || localFilePath.startsWith(ISparksConstant.PROTOCOL_HTTP)){
            return localFilePath;
        }

        localFilePath = localFilePath.replace(ISparksProperties.RESOURCES_FILE_PATH,"");
        localFilePath = localFilePath.replace(ISparksConstant.PATH_SEPARATOR,ISparksConstant.URL_SEPARATOR);

        checkOptionService();

        return  optionService.getByPropertyOrDefault(WebProperties.WEBSITE_URL,String.class) + WebConstant.STATIC_REQUEST_URL.replace(WebConstant.WEB_URL_WILDCARD,localFilePath);
    }

    /**
     * 批量将静态文件路径转换成URL链接
     */
    public static <R> void parseStaticResourceToUrlLinks(List<R> resources, Function<R,String> getLocation, TowArgsFunction<R,String> setLocation){
        if(resources == null || resources.size() == 0){
            return;
        }

        checkOptionService();

        String weburl = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_URL,String.class);

        resources.stream().forEach(r -> {
           String localFilePath = getLocation.apply(r);
           if(!StringUtils.isEmpty(localFilePath) && !localFilePath.startsWith(ISparksConstant.PROTOCOL_HTTPS) && !localFilePath.startsWith(ISparksConstant.PROTOCOL_HTTP)){

               localFilePath = localFilePath.replace(ISparksProperties.RESOURCES_FILE_PATH,"");
               localFilePath = localFilePath.replace(ISparksConstant.PATH_SEPARATOR,ISparksConstant.URL_SEPARATOR);

               localFilePath = weburl + WebConstant.STATIC_REQUEST_URL.replace(WebConstant.WEB_URL_WILDCARD,localFilePath);

           }

           setLocation.handler(r,localFilePath);

        });

    }



    public static String trimSuffix(String url){
        return url.endsWith(ISparksConstant.URL_SEPARATOR) ? url.substring(0,url.length()-1) : url;
    }


}
