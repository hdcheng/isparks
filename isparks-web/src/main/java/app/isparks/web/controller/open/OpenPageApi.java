package app.isparks.web.controller.open;

import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.plugin.enhance.IRequestEnhancer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("open_api")
@RequestMapping("api")
@Api("开发接口")
public class OpenPageApi extends AbstractOpenApi {

    @ApiOperation("开放接口 GET ")
    @GetMapping(value = "{path}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public Result get(@PathVariable("path")String path,@RequestParam Map<String,Object> params){
        IRequestEnhancer requestEnhancer = getService("GET",path);
        if(requestEnhancer == null){
            return ResultUtils.error(path + "不存在");
        }
        else {
            return ResultUtils.success().withData(requestEnhancer.process(params));
        }
    }

    @ApiOperation("开放接口 GET ")
    @GetMapping(value = "{path}/{path1}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public Result get(@PathVariable("path")String path,@PathVariable("path1")String path1,@RequestParam Map<String,Object> params){
        return get(path + "/" + path1 ,params);
    }

    @ApiOperation("开放接口 GET ")
    @GetMapping(value = "{path}/{path1}/{path2}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public Result get(@PathVariable("path")String path , @PathVariable("path1")String path1 ,@PathVariable("path2")String path2 , @RequestParam Map<String,Object> params){
        return get(path + "/" + path1 + "/" + path2,params);
    }


    @ApiOperation("开放接口 POST ")
    @PostMapping(value = "{path}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public Result post(@PathVariable("path")String path,@RequestBody Map<String, Object> body) {
        IRequestEnhancer requestEnhancer = getService("POST",path);
        if(requestEnhancer == null){
            return ResultUtils.error(path + "不存在");
        }
        else {
            return ResultUtils.success().withData(requestEnhancer.process(body));
        }
    }

    @ApiOperation("开放接口 POST ")
    @PostMapping(value = "{path}/{path1}/{path2}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public Result post(@PathVariable("path")String path , @PathVariable("path1")String path1 , @PathVariable("path2")String path2,@RequestBody Map<String, Object> body) {
        return post(path + "/" + path1 + "/" + path2,body);
    }

}
