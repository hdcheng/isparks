package app.isparks.web.controller.rest;

import app.isparks.core.pojo.page.PageData;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * base api
 * @author chenghd
 */
@RequestMapping("rest/admin")
public abstract class BasicApi {

    protected <T> Result<PageData<T>> build(PageData<T> pageData){
        before();
        return ResultUtils.build(pageData);
    }

    protected <T> Result<T> build(Optional<T> opt){
        before();
        return ResultUtils.build(opt);
    }

    protected Result<Map> build(Map map){
        before();
        if(map == null){
            return fail("result is null").withData(new HashMap<>());
        }
        return map.isEmpty() ? success("data is empty").withData(map) : success().withData(map);
    }

    protected Result build(boolean res){
        return res ? fail():ResultUtils.success();
    }

    protected Result success(String msg){
        return ResultUtils.success(msg);
    }

    protected Result success(){
        return ResultUtils.success();
    }

    protected Result fail(String msg){
        // todo:获取失败原因
        return ResultUtils.fail(msg);
    }

    protected Result fail(){
        // todo:获取失败原因
        return ResultUtils.fail();
    }

    protected Result error(){
        // todo:获取异常信息
        return ResultUtils.error();
    }

    private final void before(){

    }


}
