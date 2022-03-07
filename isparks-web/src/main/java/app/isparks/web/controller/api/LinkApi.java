package app.isparks.web.controller.api;

import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.enums.IEnum;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.LinkParam;
import app.isparks.core.service.ILinkService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.LinkServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/28
 */
@RestController
@RequestMapping("/api/admin/link")
public class LinkApi {

    private ILinkService linkService;

    public LinkApi(LinkServiceImpl linkService){
        this.linkService = linkService;
    }

    @ApiOperation("create new link")
    @RequestMapping(value = "save",method = {RequestMethod.POST})
    public Result saveLink(@RequestBody LinkParam param){

        LinkType type = IEnum.codeToEnum(LinkType.class,param.getType());

        Optional<LinkDTO> dto = linkService.save(param,type);

        return ResultUtils.build(dto);
    }

    @ApiOperation("update link info")
    @RequestMapping(value = "update",method = {RequestMethod.POST})
    public Result updateLink(@RequestBody LinkParam param){

        Optional<LinkDTO> dto = linkService.update(param);

        return ResultUtils.build(dto);
    }


    @ApiOperation("page links")
    @RequestMapping(value = "page/by/type",method = {RequestMethod.GET})
    public Result pageOffSiteLink(@RequestParam("type") int typeCode,@RequestParam(value = "page",required = false)Integer page,@RequestParam(value = "size",required = false)Integer size){

        LinkType type = IEnum.codeToEnum(LinkType.class,typeCode);

        int p = page == null ? 1 : page;
        int s = size == null ? 10 : size;

        PageData<LinkDTO> pageData = linkService.pageByType(p,s,type);

        return ResultUtils.build(pageData);
    }

    @ApiOperation("delete by id")
    @RequestMapping(value = "delete",method = {RequestMethod.GET})
    public Result deleteById(@RequestParam("id")String id){
        return ResultUtils.build(linkService.delete(id));
    }
}
