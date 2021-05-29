package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Comment;
import app.isparks.core.pojo.entity.relation.PostCommentRL;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.dao.mybatis.mapper.PostCommentRLMapper;
import app.isparks.dao.repository.AbstractPostCommentRLCurd;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostCommentRLCurdImpl extends AbstractPostCommentRLCurd {

    private Logger log = LoggerFactory.getLogger(PostCommentRLCurdImpl.class);

    private PostCommentRLMapper mapper;

    public PostCommentRLCurdImpl(PostCommentRLMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public boolean deleteByPost(String postId) {
        hasLength(postId,"post id must not be empty.");
        return mapper.deleteByPost(postId) == 1;
    }

    @Override
    public boolean deleteByComment(String commentId) {
        hasLength(commentId,"comment id must not be empty");

        return mapper.deleteByComment(commentId) == 1;
    }

    @Override
    public List<PostCommentRL> selectByPost(String postId) {
        hasLength(postId,"post id must not be empty");
        return mapper.selectByPost(postId);
    }

    @Override
    public PageData<Comment> pageCommentByPost(PageInfo pageInfo, Comment cond, String postId) {
        PageData<Comment> result = new PageData();

        Page page = PageHelper.startPage(pageInfo.getPage(), pageInfo.getSize(), false);
        page.setOrderBy("create_time DESC"); // 默认排序方式

        List<Comment> pageData;

        try {
            pageData = mapper.selectCommentByPost(cond,postId);
        }finally {
            PageHelper.clearPage(); // 清除分页数据
        }

        if(pageData != null){
            result.setData(pageData);//分页数据
        }else {
            result.setData(new ArrayList<>());
        }

        result.setPage(pageInfo.getPage());     //当前页码
        result.setSize(pageInfo.getSize());     //每页大小
        result.setTotalPage(page.getPages());   //共多少页
        result.setTotalData(page.getTotal());   //共有多少数据

        return result;
    }

    @Override
    public int countByPost(String postId) {
        hasLength(postId,"post id must not be empty");

        return mapper.countByPost(postId);
    }

    @Override
    protected int deleteBy(PostCommentRL postCommentRL) {
        notNull(postCommentRL,"post comment must not be null");

        return mapper.deleteBy(postCommentRL);
    }

    @Override
    public PostCommentRL insert(final PostCommentRL rl) {
        notNull(rl,"post comment rl must not be null.");
        String postId = rl.getPostId();
        hasLength(postId,"post id must have value");
        String commentId = rl.getCommentId();
        hasLength(commentId,"comment id must have value");

        ensure(rl);

        return mapper.insert(rl) == 1 ? rl : null;
    }

    @Override
    public List<PostCommentRL> selectByCond(PostCommentRL postCommentRL) {
        notNull(postCommentRL,"post comment rl must not be null");

        return mapper.selectByCond(postCommentRL);
    }

    @Override
    public PostCommentRL updateById(final PostCommentRL postCommentRL) {
        notNull(postCommentRL,"post comment rl must not be null");
        hasLength(postCommentRL.getId(),"post comment rl id must not be empty");

        return mapper.updateById(postCommentRL) == 1 ? postCommentRL : null;
    }

    @Override
    public long countBy(PostCommentRL postCommentRL) {
        notNull(postCommentRL,"post comment rl must not be null");

        return mapper.countBy(postCommentRL);
    }
}
