package app.addons.blog.api;

import app.addons.blog.service.IBlogService;
import app.isparks.core.service.ICategoryService;
import app.isparks.core.service.IFileService;
import app.isparks.core.service.ILinkService;
import app.isparks.core.service.IPostService;
import app.isparks.plugin.enhance.web.LinkPageEnhancer;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

@Api("博客接口")
//@RestController("web_page_Api")
//@RequestMapping("api/blog")
public class BlogApi{
//public class BlogApi implements ApplicationEventPublisherAware {

    private Logger log = LoggerFactory.getLogger(getClass());

    private IPostService postService;

    private IBlogService blogService;

    private ILinkService linkService;

    private IFileService fileService;

    private ICategoryService categoryService;

    private ApplicationEventPublisher publisher;

    private final static LinkPageEnhancer linkPageEnhancer;

    static {
        linkPageEnhancer = (LinkPageEnhancer)LinkPageEnhancer.singleton();
    }

//    public BlogApi(BlogServiceImpl blogService,IPostService postService,ILinkService linkService,IFileService fileService,ICategoryService categoryService){
//            this.blogService = blogService;
//            this.postService = postService;
//            this.linkService = linkService;
//            this.fileService = fileService;
//            this.categoryService = categoryService;
//    }
//
//    /**
//     * id post id
//     */
//    @RequestMapping(value = "post/visit",method = {RequestMethod.GET})
//    public Result visits(@RequestParam("id") String postId, @RequestParam(value = "visits",required = false) Integer newVisits , HttpServletRequest request){
//
//        long visits = newVisits == null ? 1 : (long)newVisits;
//
//        if (publisher != null) {
//            publisher.publishEvent(new PostVisitEvent(this,postId,visits));
//        }
//
//        return ResultUtils.success();
//    }
//
//    @RequestMapping(value = "post",method = {RequestMethod.GET,RequestMethod.POST})
//    public Result post(@RequestParam("id")String id){
//
//
//        PostDTO dto = postService.getById(id).orElse(new PostDTO());
//
//        PostPageVO pageData = new PostPageVO();
//        pageData.setId(dto.getId());
//        pageData.setCreateTime(dto.getCreateTime());
//        pageData.setModifyTime(dto.getModifyTime());
//        pageData.setProperties(dto.getProperties());
//        pageData.setOriginContent(dto.getOriginContent());
//        pageData.setTitle(dto.getTitle());
//
//        return ResultUtils.success().setData(pageData);
//
//    }
//
//    @RequestMapping(value = "archive",method = {RequestMethod.GET,RequestMethod.POST})
//    public Result archive(@RequestParam("page")int page,@RequestParam("size")int size){
//
//        ArchivePageVO vo = new ArchivePageVO();
//        List<PostArchiveDTO> archives = postService.listYearArchives(DataStatus.VALID);
//        vo.setArchives(archives);
//
//        return ResultUtils.success().setData(vo);
//    }
//
//    @RequestMapping(value = "link",method = {RequestMethod.GET,RequestMethod.POST})
//    public Result link(@RequestParam("page")int page,@RequestParam("size")int size){
//
//        PageData<LinkDTO> pageData = linkService.pageByType(page,size, LinkType.OFFSITE_LINK);
//        LinkPageVO vo = new LinkPageVO(pageData);
//
//        return ResultUtils.success().setData(vo);
//    }
//
//    @RequestMapping(value = "about",method = {RequestMethod.GET,RequestMethod.POST})
//    public Result about(){
//
//        return ResultUtils.success().setData(blogService.aboutPageMDContent());
//    }
//
//
//    @RequestMapping(value = "gallery",method = {RequestMethod.GET,RequestMethod.POST})
//    public Result gallery(@RequestParam("page")int page,@RequestParam("size")int size){
//
//
//        PageData<FileDTO> dtos = fileService.pageValidByMediaType(page <= 0 ? 1 : page,size <= 0 ? 10 : size, MediaType.IMAGE);
//
//        //FileApi.parseResourceLocationToLinks(dtos);
//        parseResourceLocationToLinks(dtos);
//
//        GalleryPageVO vo = new GalleryPageVO(dtos);
//
//        return ResultUtils.success().setData(vo);
//    }
//
//    @RequestMapping(value = "categories",method = {RequestMethod.GET,RequestMethod.POST})
//    public Result<PageData> category(@RequestParam("page")int page,@RequestParam("size")int size){
//
//
//        return ResultUtils.build(categoryService.page(page,size));
//    }
//
//    @GetMapping("page/post/by/category")
//    public Result<PageData> pagePostByCategory(@RequestParam("page")int page,@RequestParam("size")int size,@RequestParam(value = "id",required = false) String categoryId){
//        return ResultUtils.build(categoryId == null ? postService.page(page,size,DataStatus.VALID) : postService.pageByCategory(page,size,DataStatus.VALID,categoryId));
//    }
//
//    @RequestMapping(value = "tag",method = {RequestMethod.GET,RequestMethod.POST})
//    public Result tag(@RequestParam("page")int page,@RequestParam("size")int size){
//
//        return ResultUtils.fail();
//    }
//
//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.publisher = applicationEventPublisher;
//    }
//
//    private Optional<HttpServletRequest> getRequest(){
//        HttpServletRequest request = null;
//        try {
//            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        }catch (Exception e){
//            log.error("获取 HttpServletRequest 失败",e);
//        }finally {
//            return Optional.ofNullable(request);
//        }
//
//    }
//    private Optional<HttpServletResponse> getResponse(){
//        HttpServletResponse response = null;
//        try {
//            response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        }catch (Exception e){
//            log.error("获取 HttpServletResponse 失败",e);
//        }finally {
//            return Optional.ofNullable(response);
//        }
//    }
//
//
//    public static void parseResourceLocationToLinks(PageData<FileDTO> pageData){
//        UrlUtils.parseStaticResourceToUrlLinks(pageData.getData(), dto -> dto.getLocation(), (dto, location) -> dto.setLocation(location));
//    }

}
