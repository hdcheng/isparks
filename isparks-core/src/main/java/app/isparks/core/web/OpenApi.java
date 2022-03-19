package app.isparks.core.web;

import app.isparks.core.web.support.Result;
import java.util.Map;

public interface OpenApi {

    /**
     * GET 请求
     * @param path 类型，如：page、index/page
     * @param params 请求参数
     * @return Result
     */
    Result get(String path, Map<String,Object> params);

    /**
     * GET 请求
     * @param path 请求路径，如：page、index/page
     * @param body 请求参数
     * @return Result
     */
    Result post(String path, Map<String,Object> body);

}
