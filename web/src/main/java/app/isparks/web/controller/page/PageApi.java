package app.isparks.web.controller.page;

import app.isparks.core.file.type.MediaType;
import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import app.isparks.core.plugin.PluginManager;
import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.dto.PostArchiveDTO;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.service.IFileService;
import app.isparks.core.service.ILinkService;
import app.isparks.core.util.UrlUtils;
import app.isparks.plugin.enhance.web.dto.IndexPostDTO;
import app.isparks.core.pojo.dto.PostDTO;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.page.PageData;
import app.isparks.plugin.enhance.web.vo.*;
import app.isparks.core.service.IPostService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.plugin.enhance.web.*;
import app.isparks.web.controller.api.FileApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author： chenghd
 * @date： 2021/3/18
 */
@RestController("webPageApi")
@RequestMapping("api")
public class PageApi {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPostService postService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private ILinkService linkService;

    @Autowired
    private PluginManager pluginManager;

    // 页面数据增强器
    private final static AbstractViewModelEnhancer indexPageEnhancer;
    private final static AbstractViewModelEnhancer postPageEnhancer;
    private final static AbstractViewModelEnhancer archivePageEnhancer;
    private final static AbstractViewModelEnhancer linkPageEnhancer;
    private final static AbstractViewModelEnhancer aboutPageEnhancer;
    private final static AbstractViewModelEnhancer galleryPageEnhancer;
    private final static AbstractViewModelEnhancer categoryPageEnhancer;
    private final static AbstractViewModelEnhancer tagPageEnhancer;
    private final static AbstractViewModelEnhancer otherPageEnhancer;

    static {
        postPageEnhancer = PostPageEnhancer.singleton();
        otherPageEnhancer = OtherPageEnhancer.singleton();
        indexPageEnhancer = IndexPageEnhancer.singleton();
        linkPageEnhancer = LinkPageEnhancer.singleton();
        aboutPageEnhancer = AboutPageEnhancer.singleton();
        galleryPageEnhancer = GalleryPageEnhancer.singleton();
        categoryPageEnhancer = CategoryPageEnhancer.singleton();
        tagPageEnhancer = TagPageEnhancer.singleton();
        archivePageEnhancer = ArchivePageEnhancer.singleton();
    }

    @RequestMapping(value = "index",method = {RequestMethod.GET,RequestMethod.POST})
    public Result index(@RequestParam("page")int page,@RequestParam("size")int size){

        getRequest().ifPresent(request -> indexPageEnhancer.before(request));

        PageData<PostDTO> dtoData = postService.page(page,size, DataStatus.VALID);

        PageData<IndexPostDTO> voData = dtoData.convertData((dto)->{
            IndexPostDTO vo = new IndexPostDTO();
            vo.setId(dto.getId());
            vo.setCreateTime(dto.getCreateTime());
            vo.setModifyTime(dto.getModifyTime());
            vo.setProperties(dto.getProperties());
            vo.setTitle(dto.getTitle());
            vo.setSummary(dto.getSummary());
            return vo;
        });

        IndexPageVO indexVo = new IndexPageVO();
        indexVo.setFooter("copy right ");
        indexVo.setPageData(voData);

        indexPageEnhancer.execute(indexVo);

        // 后置增强器
        getResponse().ifPresent(response -> indexPageEnhancer.after(response));

        return ResultUtils.success().setData(indexVo);
    }

    @RequestMapping(value = "post",method = {RequestMethod.GET,RequestMethod.POST})
    public Result post(@RequestParam("id")String id){

        getRequest().ifPresent(request -> postPageEnhancer.before(request));

        PostDTO dto = postService.getById(id).orElse(new PostDTO());

        PostPageVO pageData = new PostPageVO();
        pageData.setId(dto.getId());
        pageData.setCreateTime(dto.getCreateTime());
        pageData.setModifyTime(dto.getModifyTime());
        pageData.setProperties(dto.getProperties());
        pageData.setOriginContent(dto.getOriginContent());
        pageData.setTitle(dto.getTitle());

        getResponse().ifPresent(response -> postPageEnhancer.after(response));

        return ResultUtils.success().setData(pageData);

    }

    @RequestMapping(value = "archive",method = {RequestMethod.GET,RequestMethod.POST})
    public Result archive(@RequestParam("page")int page,@RequestParam("size")int size){
        getRequest().ifPresent(request -> archivePageEnhancer.before(request));

        ArchivePageVO vo = new ArchivePageVO();
        List<PostArchiveDTO> archives = postService.listYearArchives(DataStatus.VALID);
        vo.setArchives(archives);

        archivePageEnhancer.execute(vo);

        getResponse().ifPresent(response -> archivePageEnhancer.after(response));
        return ResultUtils.success().setData(vo);
    }

    @RequestMapping(value = "link",method = {RequestMethod.GET,RequestMethod.POST})
    public Result link(@RequestParam("page")int page,@RequestParam("size")int size){
        getRequest().ifPresent(request -> linkPageEnhancer.before(request));

        PageData<LinkDTO> pageData = linkService.pageByType(page,size, LinkType.OFFSITE_LINK);
        LinkPageVO vo = new LinkPageVO();
        vo.setPageData(pageData);

        getResponse().ifPresent(response -> linkPageEnhancer.after(response));
        return ResultUtils.success().setData(vo);
    }

    @RequestMapping(value = "about",method = {RequestMethod.GET,RequestMethod.POST})
    public Result about(@RequestParam("page")int page,@RequestParam("size")int size){
        getRequest().ifPresent(request -> aboutPageEnhancer.before(request));

        AboutPageVO vo = new AboutPageVO();
        vo.setTitle("Eastry的个人网站");
        vo.setSubtitle(new Date().toString());
        vo.setContact("https://www.baidu.com");
        vo.setMdContent("这是主要内容");
        vo.setHtmlContent("这是次要内容");

        aboutPageEnhancer.execute(vo);
        getResponse().ifPresent(response -> aboutPageEnhancer.after(response));
        return ResultUtils.success().setData(vo);
    }

    @RequestMapping(value = "gallery",method = {RequestMethod.GET,RequestMethod.POST})
    public Result gallery(@RequestParam("page")int page,@RequestParam("size")int size){

        getRequest().ifPresent(request -> galleryPageEnhancer.before(request));

        PageData<FileDTO> dtos = fileService.pageValidByMediaType(page <= 0 ? 1 : page,size <= 0 ? 10 : size, MediaType.IMAGE);

        FileApi.parseResourceLocationToLinks(dtos);

        GalleryPageVO vo = new GalleryPageVO();
        vo.setPageData(dtos);

        galleryPageEnhancer.execute(vo);
        getResponse().ifPresent(response -> galleryPageEnhancer.after(response));
        return ResultUtils.success().setData(vo);
    }

    @RequestMapping(value = "category",method = {RequestMethod.GET,RequestMethod.POST})
    public Result category(@RequestParam("page")int page,@RequestParam("size")int size){

        return ResultUtils.fail();
    }

    @RequestMapping(value = "tag",method = {RequestMethod.GET,RequestMethod.POST})
    public Result tag(@RequestParam("page")int page,@RequestParam("size")int size){

        return ResultUtils.fail();
    }


    private Optional<HttpServletRequest> getRequest(){
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (Exception e){
            log.error("获取 HttpServletRequest 失败",e);
        }finally {
            return Optional.ofNullable(request);
        }

    }
    private Optional<HttpServletResponse> getResponse(){
        HttpServletResponse response = null;
        try {
            response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        }catch (Exception e){
            log.error("获取 HttpServletResponse 失败",e);
        }finally {
            return Optional.ofNullable(response);
        }
    }



}
