package app.isparks.web.controller.api;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.JournalParam;
import app.isparks.core.service.IJournalService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.JournalServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api("随笔管理")
@RestController
@RequestMapping("/api/admin/journal")
public class JournalApi {

    private IJournalService journalService;

    public JournalApi(JournalServiceImpl journalService){
        this.journalService = journalService;
    }

    @ApiOperation("创建随笔")
    @RequestMapping(value = "create",method = {RequestMethod.POST})
    @Log(description = "新增随笔", types = {LogType.INSERT})
    public Result create(@RequestBody JournalParam param){
        return ResultUtils.build(journalService.create(param, DataStatus.VALID));
    }

    @ApiOperation("删除随笔")
    @RequestMapping(value = "delete",method = {RequestMethod.GET})
    @Log(description = "删除随笔", types = {LogType.DELETE})
    public Result delete(@RequestParam("id") String id){
        return ResultUtils.build(journalService.deleteById(id));
    }

    @ApiOperation("分页查找随笔")
    @RequestMapping(value = "page/all",method = {RequestMethod.GET})
    public Result page(@RequestParam(value = "page",defaultValue = "1") int page,
                       @RequestParam(value = "size",defaultValue = "10") int size){
        return ResultUtils.build(journalService.page(page,size));
    }

}
