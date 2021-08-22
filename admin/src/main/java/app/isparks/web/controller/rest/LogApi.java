package app.isparks.web.controller.rest;

import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.service.ILogService;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.LogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */

@Api("系统日志管理接口")
@RequestMapping("v1/admin")
@RestController("v1_LogApi")
public class LogApi extends BasicApi {

    private ILogService logService;

    public LogApi(LogServiceImpl logService) {
        this.logService = logService;
    }

    @GetMapping("log/page")
    @ApiOperation("Get logs | 分页获取日志数据")
    public Result get(@RequestParam(name = "page",defaultValue = "1") int page ,
                      @RequestParam(name = "size",defaultValue = "10") int size,
                      @RequestParam(name = "types",defaultValue = "",required = false) String types){
        return build(types.isEmpty() ? logService.page(page,size) : logService.pageByTypes(page,size,LogType.types(types)));
    }

    @GetMapping("log/types")
    @ApiOperation("Get log types | 获取日志类型")
    public Result getTypes(){
        return build(logService.logTypes());
    }


    @DeleteMapping("log/{id}")
    @ApiOperation("Delete by log id | 根据日志id删除日志数据")
    public Result delete(@PathVariable("id") String id){
        return build(logService.delete(id));
    }

}
