package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import io.swagger.annotations.Api;
import app.isparks.core.web.support.Result;
import io.swagger.annotations.ApiOperation;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.param.JournalParam;
import app.isparks.core.service.IJournalService;
import app.isparks.service.impl.JournalServiceImpl;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */

@Api("用户随笔管理接口")
@RequestMapping("v1/admin")
@RestController("v1_JournalApi")
public class JournalApi extends BasicApi{

    private IJournalService journalService;

    public JournalApi(JournalServiceImpl journalService){
        this.journalService = journalService;
    }

    @PostMapping("journal")
    @ApiOperation("Create journal | 创建随笔")
    @Log(description = "创建随笔",types = {LogType.INSERT})
    public Result create(@RequestBody JournalParam param){
        return build(journalService.create(param, DataStatus.VALID));
    }

    @DeleteMapping("journal/{id}")
    @ApiOperation(" Delete journal by id | 删除随笔")
    @Log(description = "删除随笔",types = {LogType.DELETE})
    public Result delete(@PathVariable("id") String id){
        return build(journalService.deleteById(id));
    }

    @GetMapping("journal/page")
    @ApiOperation(" Get journals by page | 分页查找随笔")
    public Result page(@RequestParam(value = "page",defaultValue = "1") int page,
                       @RequestParam(value = "size",defaultValue = "10") int size){
        return build(journalService.page(page,size));
    }

}
