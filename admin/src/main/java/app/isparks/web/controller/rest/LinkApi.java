package app.isparks.web.controller.rest;


import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.enums.IEnum;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.LinkParam;
import app.isparks.core.service.ILinkService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.LinkServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author chenghd
 */

@Api("链接管理接口")
@RequestMapping("v1/admin")
@RestController("v1_LinkApi")
public class LinkApi extends BasicApi{

    private ILinkService linkService;

    public LinkApi(LinkServiceImpl linkService){
        this.linkService = linkService;
    }

    @PostMapping("link")
    @ApiOperation("Create new link | 创建链接")
    public Result create(@RequestBody LinkParam param){

        LinkType type = IEnum.codeToEnum(LinkType.class,param.getType());

        Optional<LinkDTO> dto = linkService.save(param,type);

        return build(dto);
    }

    @DeleteMapping("link/{id}")
    @ApiOperation("Delete link by id | 根据 ID 删除链接")
    public Result deleteById(@PathVariable("id") String id){
        return build(linkService.delete(id));
    }

    @GetMapping("link/page")
    @ApiOperation("Get links | 分页获取链接")
    public Result pageOffSiteLink(@RequestParam("type") int typeCode,
                                  @RequestParam(value = "page",defaultValue = "1") int page,
                                  @RequestParam(value = "size",defaultValue = "10") int size){
        LinkType type = IEnum.codeToEnum(LinkType.class,typeCode);

        return build(linkService.pageByType(page,size,type));
    }


    @PutMapping("link")
    @ApiOperation("update link info | 更新链接")
    public Result updateLink(@RequestBody LinkParam param){

        Optional<LinkDTO> dto = linkService.update(param);

        return build(dto);
    }




}
