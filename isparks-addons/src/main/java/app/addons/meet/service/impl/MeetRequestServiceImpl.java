package app.addons.meet.service.impl;

import app.addons.meet.service.IMeetRequestService;
import app.addons.meet.vo.AboutVO;
import app.addons.meet.vo.IndexVO;
import app.addons.meet.vo.PostVO;
import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.dto.PostArchiveDTO;
import app.isparks.core.pojo.dto.PostDTO;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.service.ICategoryService;
import app.isparks.core.service.ILinkService;
import app.isparks.core.service.IPostService;

import java.util.List;
import java.util.Map;

public class MeetRequestServiceImpl implements IMeetRequestService {

    private IPostService postService;

    private ICategoryService categoryService;

    private ILinkService linkService;

    public MeetRequestServiceImpl(IPostService postService,ICategoryService categoryService,ILinkService linkService){
        this.postService = postService;
        this.categoryService = categoryService;
        this.linkService = linkService;
    }

    @Override
    public IndexVO index(Map<String, Object> params) {
        IndexVO vo = new IndexVO();
        vo.setTitle("测试标题");
        vo.setDescription("测试描述");
        return vo;
    }

    @Override
    public PageData<PostDTO> post(Map<String, Object> params) {
        int page = Integer.valueOf(params.getOrDefault("page" ,"1").toString());
        int size = Integer.valueOf(params.getOrDefault("size" ,"5").toString());
        return postService.page(page ,size, DataStatus.VALID);
    }

    @Override
    public List<PostDTO> hots() {
        List<PostDTO> posts = postService.page(1,4, DataStatus.VALID).getData();
        posts.forEach(post -> post.setOriginContent(null));
        return posts;
    }

    @Override
    public List<LinkDTO> links() {
        return linkService.listByType(LinkType.OFFSITE_LINK);
    }

    @Override
    public PageData<PostDTO> categoryPost(Map<String, Object> params) {
        int page = Integer.valueOf(params.getOrDefault("page","1") + "");
        int size = Integer.valueOf(params.getOrDefault("size","10") + "");
        String categoryId = params.getOrDefault("categoryId","") + "";
        PageData<PostDTO> pageData;
        if(categoryId.isEmpty()){
            pageData = postService.page(page,size,DataStatus.VALID);
        }else {
            pageData = postService.pageByCategory(page,size,DataStatus.VALID,categoryId);
        }
        pageData.getData().forEach(post -> {post.setOriginContent(null);});
        return pageData;
    }

    @Override
    public List<CategoryDTO> category(Map<String, Object> params) {
        return categoryService.listAll();
    }

    @Override
    public List<PostArchiveDTO> archive(Map<String, Object> params) {
        List<PostArchiveDTO> archives = postService.listYearArchives(DataStatus.VALID);
        return archives;
    }

    @Override
    public PostVO post(String id) {
        PostDTO postDTO = postService.getById(id).orElse(null);
        PostVO vo = new PostVO();
        if(postDTO == null){
            return vo;
        }
        vo.setContent(postDTO.getOriginContent());
        vo.setSummary(postDTO.getSummary());
        vo.setTitle(postDTO.getTitle());
        vo.setCreateTime(postDTO.getCreateTime());
        vo.setProperties(postDTO.getProperties());
        return vo;
    }

    @Override
    public AboutVO about(Map<String, Object> params) {
        AboutVO vo = new AboutVO();


        return vo;
    }


}
