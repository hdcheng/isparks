package app.isparks.addons.blog.service;

import app.isparks.addons.blog.dao.PostAttachMapper;
import app.isparks.addons.blog.entity.PostAttach;
import app.isparks.core.pojo.dto.PostDTO;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.service.IPostService;
import app.isparks.core.util.IdUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/21
 */
@Service
public class BlogServiceImpl implements IBlogService{

    private PostAttachMapper postAttachMapper;

    private IPostService postService;

    public BlogServiceImpl(PostAttachMapper postAttachMapper,IPostService postService){
        this.postAttachMapper = postAttachMapper;
        this.postService = postService;
    }

    @Override
    public void increaseVisit(String postId, long visits) {
        if(visits <= 0){
            return;
        }

        Optional<PostDTO> postOPT = postService.getById(postId);

        PostAttach pa = postAttachMapper.selectByPost(postId);

        postOPT.ifPresent(postDTO -> {
            if(pa == null){
                PostAttach newpa = new PostAttach();
                newpa.setId(IdUtils.id());
                newpa.setVisits(visits);
                newpa.setLikes(0L);
                newpa.setPostId(postId);
                newpa.setCreateTime(postDTO.getCreateTime());
                newpa.setModifyTime(postDTO.getModifyTime());
                newpa.setStatus(DataStatus.VALID);
                postAttachMapper.insert(newpa);
            }else{
                postAttachMapper.updateVisits(postId,pa.getVisits() + visits,System.currentTimeMillis());
            }
        });
        if(!postOPT.isPresent() && pa != null){
            postAttachMapper.delete(pa.getId());
        }
    }

    @Override
    public void increaseLike(String postId, long likes) {
        if(likes <= 0){
            return;
        }
        postService.getById(postId).ifPresent(postDTO -> {
            PostAttach pa = postAttachMapper.selectByPost(postId);
            if(pa == null){
                pa = new PostAttach();
                pa.setId(IdUtils.id());
                pa.setVisits(0L);
                pa.setLikes(likes);
                pa.setPostId(postId);
                pa.setCreateTime(postDTO.getCreateTime());
                pa.setModifyTime(postDTO.getModifyTime());
                pa.setStatus(DataStatus.VALID);
                postAttachMapper.insert(pa);
            }else{
                postAttachMapper.updateLikes(postId,pa.getVisits() + likes,System.currentTimeMillis());
            }
        });
    }

    @Override
    public Optional<PostAttach> getByPostId(String postId) {

        if(StringUtils.isEmpty(postId)){
            return Optional.empty();
        }

        return Optional.ofNullable(postAttachMapper.selectByPost(postId));
    }

    @Override
    public List<PostDTO> listMostVisits(int number, Long ctime) {
        List<PostDTO> dtos = new ArrayList<>(number);
        try {
            PageHelper.startPage(1, number, true);
            postAttachMapper.selectSortByVisits(ctime).forEach( postAttach -> {

                Optional<PostDTO> postOPT = postService.getById(postAttach.getPostId());

                postOPT.ifPresent(dto -> {
                    dto.setOriginContent(""); // 清空源数据，减少数据传输压力
                    dtos.add(dto);
                });

                if(!postOPT.isPresent()){
                    postAttachMapper.delete(postAttach.getId());
                }

            });
        }finally {
            PageHelper.clearPage(); // 清除分页数据
        }
        return dtos;
    }
}
