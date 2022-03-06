package app.addons.blog.service;

import app.addons.blog.mapper.PostAttachMapper;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.IPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： chenghd
 * @date： 2021/3/21
 */
//@Service
//public class BlogServiceImpl implements IBlogService{
public class BlogServiceImpl{

    private Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final static String ABOUT_MD_FILE_NAME = "about.md";

    private Map<String,String> config_map = new ConcurrentHashMap<>();

    private final String blog_config_key_prefix = "blog-config-";

    private IOptionService optionService;

    private PostAttachMapper postAttachMapper;

    private IPostService postService;

    public BlogServiceImpl(PostAttachMapper postAttachMapper,IPostService postService,IOptionService optionService){
        this.postAttachMapper = postAttachMapper;
        this.postService = postService;
        this.optionService = optionService;
    }

//    @Override
//    public String aboutPageMDContent() {
//
//        File file = new File(ISparksProperties.MARKDOWN_FILE_PATH,ABOUT_MD_FILE_NAME);
//
//        String md = FileUtils.readText(file.getPath());
//
//        return md;
//    }
//
//    @Override
//    public void updateAboutPageMDContent(String mdContent) {
//        if(mdContent == null || mdContent.isEmpty()){
//            return;
//        }
//        File file = new File(ISparksProperties.MARKDOWN_FILE_PATH,ABOUT_MD_FILE_NAME);
//
//        FileUtils.writeText(file.getPath(),mdContent,false);
//
//    }

//    @Override
//    public void increaseVisit(String postId, long visits) {
//        if(visits <= 0){
//            return;
//        }
//
//        Optional<PostDTO> postOPT = postService.getById(postId);
//
//        PostAttach pa = postAttachMapper.selectByPost(postId);
//
//        postOPT.ifPresent(postDTO -> {
//            if(pa == null){
//                PostAttach newpa = new PostAttach();
//                newpa.setId(IdUtils.id());
//                newpa.setVisits(visits);
//                newpa.setLikes(0L);
//                newpa.setPostId(postId);
//                newpa.setCreateTime(postDTO.getCreateTime());
//                newpa.setModifyTime(postDTO.getModifyTime());
//                newpa.setStatus(DataStatus.VALID);
//                postAttachMapper.insert(newpa);
//            }else{
//                postAttachMapper.updateVisits(postId,pa.getVisits() + visits,System.currentTimeMillis());
//            }
//        });
//        if(!postOPT.isPresent() && pa != null){
//            postAttachMapper.delete(pa.getId());
//        }
//    }
//
//    @Override
//    public void increaseLike(String postId, long likes) {
//        if(likes <= 0){
//            return;
//        }
//        postService.getById(postId).ifPresent(postDTO -> {
//            PostAttach pa = postAttachMapper.selectByPost(postId);
//            if(pa == null){
//                pa = new PostAttach();
//                pa.setId(IdUtils.id());
//                pa.setVisits(0L);
//                pa.setLikes(likes);
//                pa.setPostId(postId);
//                pa.setCreateTime(postDTO.getCreateTime());
//                pa.setModifyTime(postDTO.getModifyTime());
//                pa.setStatus(DataStatus.VALID);
//                postAttachMapper.insert(pa);
//            }else{
//                postAttachMapper.updateLikes(postId,pa.getVisits() + likes,System.currentTimeMillis());
//            }
//        });
//    }
//
//    @Override
//    public Optional<PostAttach> getByPostId(String postId) {
//
//        if(StringUtils.isEmpty(postId)){
//            return Optional.empty();
//        }
//
//        return Optional.ofNullable(postAttachMapper.selectByPost(postId));
//    }
//
//    @Override
//    public List<PostDTO> listMostVisits(int number, Long ctime) {
//        List<PostDTO> dtos = new ArrayList<>(number);
//        try {
//            //PageHelper.startPage(1, number, true);
//            postAttachMapper.selectSortByVisits(ctime).forEach( postAttach -> {
//
//                Optional<PostDTO> postOPT = postService.getById(postAttach.getPostId());
//
//                postOPT.ifPresent(dto -> {
//                    dto.setOriginContent(""); // 清空源数据，减少数据传输压力
//                    dtos.add(dto);
//                });
//
//                if(!postOPT.isPresent()){
//                    postAttachMapper.delete(postAttach.getId());
//                }
//
//            });
//        }finally {
//            //PageHelper.clearPage(); // 清除分页数据
//        }
//        return dtos;
//    }

//    @Override
//    public Optional<String> getConfigByKey(String key) {
//        if(StringUtils.isEmpty(key)){
//            return Optional.empty();
//        }
//        String blog_config_key = blog_config_key_prefix + key;
//        String value = config_map.get(blog_config_key);
//        if(value == null){
//            Optional<Option> option = optionService.getOptionByKey(blog_config_key);
//            if(option.isPresent()){
//                config_map.put(blog_config_key,option.get().getValue());
//                return Optional.of(option.get().getValue());
//            }else{
//                return Optional.empty();
//            }
//        }else{
//            return Optional.of(value);
//        }
//    }

//    @Override
//    public Map<String,String> listConfigByKeys(String[] keys) {
//        if(keys == null || keys.length == 0){
//            return new HashMap<>();
//        }
//        Map<String,String> configs = new HashMap<>(keys.length);
//        for(String key : keys){
//            getConfigByKey(key).ifPresent(config -> configs.put(key,config));
//        }
//        return configs;
//    }
//
//    @Override
//    public void updateOrSaveConfig(String key, String value) {
//        if(StringUtils.isEmpty(key) || value == null){
//            return;
//        }
//        String blog_config_key = blog_config_key_prefix + key;
//
//        Map<String,Object> config = new HashMap<>();
//
//        config.put(blog_config_key,value);
//
//        optionService.saveOrUpdate(config);
//        config_map.put(blog_config_key,value);
//    }
}
