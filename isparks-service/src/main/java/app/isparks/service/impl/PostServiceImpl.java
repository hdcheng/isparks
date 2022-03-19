package app.isparks.service.impl;

import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.PostConverter;
import app.isparks.core.pojo.dto.*;
import app.isparks.core.pojo.entity.Post;
import app.isparks.core.pojo.entity.relation.PostCategoryRL;
import app.isparks.core.pojo.entity.relation.PostTagRL;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.core.pojo.param.PostParam;
import app.isparks.core.pojo.param.UpdatePostParam;
import app.isparks.core.service.ICacheService;
import app.isparks.core.service.ICategoryService;
import app.isparks.core.service.IPostService;
import app.isparks.core.service.ITagService;
import app.isparks.core.util.DateUtils;
import app.isparks.core.util.IdUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.dao.repository.PostCategoryRLCurd;
import app.isparks.dao.repository.PostCurd;
import app.isparks.dao.repository.PostTagRLCurd;
import app.isparks.dao.repository.impl.PostCurdImpl;
import app.isparks.service.plugin.AbstractEnhancerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author： chenghd
 * @date： 2021/2/28
 */
@Service
public class PostServiceImpl extends AbstractEnhancerService<Post,PostDTO> implements IPostService {

    private PostConverter CONVERTER = ConverterFactory.get(PostConverter.class);

    private static final String TEMP_LINK_KEY_PREFIX = "TEMP-POST-KEY-";

    private PostCurd postCurd;

    private PostCategoryRLCurd pcRLCurd;

    private PostTagRLCurd ptRLCurd;

    private ITagService tagService;

    private ICategoryService categoryService;

    private ICacheService cacheService;

    public PostServiceImpl(PostCurdImpl postCurd, PostCategoryRLCurd pcRLCurd, PostTagRLCurd ptRLCurd, TagServiceImpl tagService, CacheServiceImpl cacheService, CategoryServiceImpl categoryService){
        super(postCurd);
        this.postCurd = postCurd;

        notNull(tagService,"tag service must not be null");
        notNull(cacheService,"cache service must not be null");
        notNull(pcRLCurd,"post category curd object must not be null");
        notNull(ptRLCurd,"post tag curd object must not be null");

        this.tagService = tagService;
        this.cacheService = cacheService;
        this.categoryService = categoryService;
        this.pcRLCurd = pcRLCurd;
        this.ptRLCurd = ptRLCurd;
    }

    @Override
    @Transactional
    public Optional<PostDTO> create(PostParam param,DataStatus dataStatus) {
        notNull(param,"post param must not be null.");
        notNull(dataStatus,"post status must not be null.");

        // 创建文章
        Post post = CONVERTER.map(param);

        post.setStatus(dataStatus);

        abstractInsert(post);

        PostDTO postDTO = CONVERTER.map(post);
        String postId = post.getId();

        // 分类
        String categoryId = param.getCategoryId();

        Optional<CategoryDTO> category = categoryService.selectById(categoryId);

        if(!category.isPresent()){
            throw new InvalidValueException("分类不存在");
        }

        PostCategoryRL pcRL = new PostCategoryRL().withPostId(postId).withCategoryId(categoryId);

        pcRLCurd.insert(pcRL);

        postDTO.withProperty("category",category.get());

        // 标签
        List<String> tagNames = param.getTagNames();
        List<TagDTO> tags = tagService.createTagsByNamesIfAbsent(tagNames);

        List<PostTagRL> ptrls = new ArrayList<>(tags.size());
        tags.stream().forEach((t) -> {
            ptrls.add(new PostTagRL().withPostId(postId).withTagId(t.getId()));
        });
        ptRLCurd.saveIfAbsent(ptrls);

        postDTO.withProperty("tags",tags);

        return Optional.ofNullable(postDTO);
    }

    @Override
    public String createTempLinkKey(String id, long mills) {
        notEmpty(id,"post id must not be empty.");

        if(abstractGetById(id).isPresent()){
            String uuid = IdUtils.id();
            cacheService.saveStringWithExpires(TEMP_LINK_KEY_PREFIX.concat(uuid),id,mills);
            return uuid;
        }else{
            return "";
        }
    }

    @Override
    public String getTempLinkPostIdByKey(String cacheKey) {
        notEmpty(cacheKey,"cache key must not be empty.");

        return cacheService.getString(TEMP_LINK_KEY_PREFIX.concat(cacheKey));
    }

    @Override
    public PageData<PostDTO> page(int page, int size, DataStatus dataStatus) {

        Post post = new Post();

        if(dataStatus != null){
            post.setStatus(dataStatus);
        }

        PageData<Post> pageData = postCurd.pageByCond(new PageInfo(page,size),post);

        PageData<PostDTO> pageDTO = pageData.convertData((p) -> converter(p));

        return pageDTO;
    }

    @Override
    public PageData<PostDTO> pageByCategory(int page, int size, DataStatus dataStatus, String categoryId) {

        if(StringUtils.isEmpty(categoryId)){
            return new PageData<>();
        }

        Post cond = new Post();
        cond.withStatus(dataStatus);
        PageData<Post> post = pcRLCurd.pagePostByCategory(new PageInfo(page <= 0 ? 1 : page,size <= 0 ? size : 10),cond,categoryId);

        PageData<PostDTO> dto = post.convertData((p) -> converter(p));

        return dto;
    }

    @Override
    public Optional<PostDTO> getById(String id) {
        notEmpty(id,"post id must not be null.");

        Post post = postCurd.select(id);

        return Optional.ofNullable(converter(post));
    }

    @Override
    @Transactional
    public Optional<PostDTO> delete(String id) {
        notEmpty(id,"post id must not be empty.");

        Post post = postCurd.delete(id);

        pcRLCurd.deleteByPost(id);

        return Optional.of(converter(post));
    }


    @Override
    public Optional<PostDTO> restore(String id) {
        notEmpty(id,"post id must not be empty.");

        Post post = new Post();
        post.setId(id);
        post.setStatus(DataStatus.VALID);

        postCurd.updateById(post);

        return Optional.of(converter(post));
    }

    @Override
    public Optional<PostDTO> remove(String id) {
        notEmpty(id,"post id must not be empty.");

        Post post = new Post();
        post.setId(id);
        post.setStatus(DataStatus.REMOVE);

        postCurd.updateById(post);

        return Optional.of(converter(post));
    }

    @Override
    @Transactional
    public Optional<PostDTO> update(UpdatePostParam param) {
        notNull(param,"post param must not be null.");
        notEmpty(param.getId(),"post id must not be empty.");

        Post post = postCurd.select(param.getId());
        post.setOriginContent(param.getOriginContent());
        post.setSummary(param.getSummary());
        post.setTitle(param.getTitle());
        post.setUrl(param.getUrl());

        postCurd.updateById(post);

        String postId = post.getId();

        // 分类
        PostCategoryRL pcrl = pcRLCurd.findByPost(postId).stream().findFirst().orElse(null);
        if(pcrl == null){
            throw new InvalidValueException("分类不存在");
        }
        pcrl.setCategoryId(param.getCategoryId());
        pcRLCurd.updateById(pcrl);

        // 标签
        ptRLCurd.deleteByPost(post.getId());

        List<String> tagNames = param.getTagNames();
        List<TagDTO> tags = tagService.createTagsByNamesIfAbsent(tagNames);

        List<PostTagRL> ptrls = new ArrayList<>(tags.size());
        tags.stream().forEach((t) -> {
            ptrls.add(new PostTagRL().withPostId(postId).withTagId(t.getId()));
        });
        ptRLCurd.saveIfAbsent(ptrls);

        return Optional.of(converter(post));
    }

    @Override
    public List<PostArchiveDTO> listYearArchives(DataStatus status) {
        List<Post> posts = abstractListByStatus(status);

        return convertToArchiveDTO(posts);
    }

    @Override
    public boolean existId(String postId) {
        if(StringUtils.isEmpty(postId)){
            return false;
        }
        return abstractGetById(postId).isPresent();
    }

    /**
     * post 转换成 postDTO
     *
     * @param post
     */
    @Override
    protected PostDTO toDTO(Post post) {

        PostDTO dto = CONVERTER.map(post);

        String id = post.getId();

        // 分类
        pcRLCurd.findByPost(id).stream().findFirst().ifPresent((t)->{

            categoryService.selectById(t.getCategoryId()).ifPresent((c) -> {
                dto.withProperty("category",c);
            });

        });

        // 标签
        List<PostTagRL> ptRLs = ptRLCurd.findByPost(id);
        List<String> tagIds = new ArrayList<>(ptRLs.size());

        ptRLs.stream().forEach((pt) -> {
            tagIds.add(pt.getTagId());
        });

        List<TagDTO> tagDTOs = tagService.selectByIds(tagIds);

        dto.withProperty("tags",tagDTOs);

        return dto;
    }

    /**
     * 转换程归档对象
     */
    private List<PostArchiveDTO> convertToArchiveDTO(List<Post> posts){
        notNull(posts,"posts must not be null.");

        Map<Integer,List<SimplePostDTO>> archiveMap = new HashMap<>(posts.size() > 10 ? 10 : 6);

        posts.forEach(post -> {

            Calendar calendar = DateUtils.getCalendar(post.getCreateTime());
            SimplePostDTO dto = new SimplePostDTO();
            String id = post.getId();

            dto.setId(id);
            dto.setModifyTime(post.getModifyTime());
            dto.setCreateTime(post.getCreateTime());
            dto.setUrl(post.getUrl());
            dto.setTitle(post.getTitle());
            dto.setSummary(post.getSummary());

            Map<String,Object> properties = dto.getProperties();
            //setCategories(id,properties);
            //setTags(id,properties);

            archiveMap.computeIfAbsent(calendar.get(Calendar.YEAR),d -> new ArrayList<>()).add(dto);

        });

        List<PostArchiveDTO> archives = new ArrayList<>(archiveMap.size());

        archiveMap.forEach((key,value) -> {
            PostArchiveDTO archive = new PostArchiveDTO();
            archive.setDate(key);
            archive.setPosts(value);
            archives.add(archive);
        });

        archives.sort((o1, o2) -> o2.getDate() - o1.getDate());

        return archives;
    }

    /**
     * 设置分类
     */
    private void setCategories(String id ,final Map<String,Object> properties){
        pcRLCurd.findByPost(id).stream().findFirst().ifPresent((t)->{

            categoryService.selectById(t.getCategoryId()).ifPresent((c) -> {
                properties.put("category",c);
            });

        });
    }

    /**
     * 设置标签
     */
    private void setTags(String id,final Map<String,Object> properties){
        List<PostTagRL> ptRLs = ptRLCurd.findByPost(id);
        List<String> tagIds = new ArrayList<>(ptRLs.size());

        ptRLs.stream().forEach((pt) -> {
            tagIds.add(pt.getTagId());
        });
        List<TagDTO> tagDTOs = tagService.selectByIds(tagIds);

        properties.put("tags",tagDTOs);
    }

    @Override
    public long countAll() {

        return abstractCount();

    }

}
