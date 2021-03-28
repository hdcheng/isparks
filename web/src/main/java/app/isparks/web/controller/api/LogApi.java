package app.isparks.web.controller.api;

import app.isparks.core.pojo.dto.LogDTO;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.service.ILogService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.LogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/16
 */
@Api("日志管理")
@RestController
@RequestMapping("api/admin/log")
public class LogApi {

    private ILogService logService;

    public LogApi(LogServiceImpl logService) {
        this.logService = logService;
    }

    @ApiOperation(value = "日志")
    @RequestMapping(value = "find", method = {RequestMethod.GET})
    public Result<PageData<LogDTO>> all(@RequestParam("page") int page, @RequestParam("size") int size) {
        PageData<LogDTO> result = logService.find(page, size);
        return ResultUtils.success("查询成功", result);
    }

    @ApiOperation(value = "查找指定几个日志")
    @RequestMapping(value = "find/{number}", method = {RequestMethod.GET})
    public Result<List<LogDTO>> all(@PathVariable("number") int num) {
        return ResultUtils.success("查询成功", logService.findNumber(num).orElse(new ArrayList<>()));
    }

    @ApiOperation(value = "分页查询日志")
    @RequestMapping(value = "page",method = {RequestMethod.GET})
    public Result page(@RequestParam("page")int page,@RequestParam("size")int size){
        return ResultUtils.build(logService.pageAll(page,size));
    }
}
