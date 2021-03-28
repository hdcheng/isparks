package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.relation.PostTagRL;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.dao.mybatis.mapper.PostTagRLMapper;
import app.isparks.dao.repository.AbstractPostCurd;
import app.isparks.dao.repository.AbstractPostTagRLCurd;
import app.isparks.dao.repository.AbstractTagCurd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/20
 */
@Repository
public class PostTagCurdRLImpl extends AbstractPostTagRLCurd {

    private Logger log = LoggerFactory.getLogger(PostTagCurdRLImpl.class);

    @Autowired
    private AbstractPostCurd postCurd;

    @Autowired
    private AbstractTagCurd tagCurd;

    private PostTagRLMapper mapper;

    public PostTagCurdRLImpl(PostTagRLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<PostTagRL> selectByCond(PostTagRL postTagRL) {
        return mapper.selectByCond(postTagRL);
    }

    @Override
    public PostTagRL insert(PostTagRL t) {
        notNull(t, "post tag relation must not be null ");
        String postId = t.getPostId();
        String tagId = t.getTagId();
        hasLength(postId, "post id must have value");
        hasLength(tagId, "tag id must have value");

        if (postCurd.select(postId) == null) { //检测post
            log.error("新增post标签失败，不存id为{}的post数据", t.getPostId());
            return null;
        }

        if (tagCurd.select(tagId) == null) { //检测 tag
            log.error("新增post标签失败，不存id为{}的tag数据", t.getTagId());
            return null;
        }

        if (findByPostAndTag(postId, tagId) != null) {   //检测是否重复
            return null;
        }

        beforeInsert(t);
        return mapper.insert(t) == 1 ? t : null;
    }

    @Override
    protected int deleteBy(PostTagRL rl) {
        return mapper.deleteByCond(rl);
    }

    @Override
    public PostTagRL updateById(PostTagRL postTagRL) {
        return mapper.updateById(postTagRL) == 1 ? postTagRL : null;
    }

    @Override
    public long countBy(PostTagRL postTagRL) {
        return mapper.countByCond(postTagRL);
    }

    @Override
    public List<PostTagRL> query() {
        return select();
    }

    @Override
    public PageData<PostTagRL> query(PageInfo info) {
        return pageAll(info);
    }


    @Override
    public boolean deleteByPost(String postId) {
        hasLength(postId, "post id must not be empty");
        return mapper.deleteByPost(postId) > 0;
    }

    @Override
    public boolean deleteByTag(String tagId) {
        hasLength(tagId, "tag id must not be empty");
        return mapper.deleteByTag(tagId) > 0;
    }

    @Override
    public List<PostTagRL> findByPost(String postId) {
        return mapper.selectByPost(postId);
    }

    @Override
    public List<PostTagRL> findByTag(String tagId) {
        return mapper.selectByTag(tagId);
    }

    @Override
    public PostTagRL findByPostAndTag(String postId, String tagId) {
        return mapper.selectByPostAndTag(postId, tagId);
    }

    @Override
    public int countByPost(String postId) {
        hasLength(postId, "post id must not be empty");
        List<PostTagRL> list = mapper.selectByPost(postId);
        return list == null ? 0 : list.size();
    }

    @Override
    public int countByTag(String tagId) {
        hasLength(tagId, "tag id must not be empty");
        List<PostTagRL> list = mapper.selectByTag(tagId);
        return list == null ? 0 : list.size();
    }

    @Override
    public List<PostTagRL> saveIfAbsent(List<PostTagRL> postTagRLS) {

        List<PostTagRL> rls = new ArrayList<>(postTagRLS.size());

        postTagRLS.stream().forEach((rl) -> {

            PostTagRL ptRl = findByPostAndTag(rl.getPostId(),rl.getTagId());

            if(ptRl == null){
                ptRl = new PostTagRL().withPostId(rl.getPostId()).withTagId(rl.getTagId());
                insert(ptRl);
            }

            rls.add(ptRl);
        });

        return rls;
    }
}
