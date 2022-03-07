package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Comment;
import app.isparks.core.pojo.entity.Post;
import app.isparks.core.pojo.entity.relation.PostCategoryRL;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.dao.mybatis.mapper.PostCategoryRLMapper;
import app.isparks.dao.repository.AbstractCategoryCurd;
import app.isparks.dao.repository.AbstractPostCategoryRLCurd;
import app.isparks.dao.repository.AbstractPostCurd;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/8/13
 */
@Repository
public class PostCategoryRLCurdImpl extends AbstractPostCategoryRLCurd {

    private Logger log = LoggerFactory.getLogger(PostCategoryRLCurdImpl.class);

    @Autowired
    private AbstractCategoryCurd categoryCurd;

    @Autowired
    private AbstractPostCurd postCurd;

    private PostCategoryRLMapper mapper;

    public PostCategoryRLCurdImpl(PostCategoryRLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<PostCategoryRL> selectByCond(PostCategoryRL rl) {
        return mapper.selectByCond(rl);
    }

    @Override
    public PostCategoryRL insert(final PostCategoryRL t) {
        notNull(t, "post category relation must not be null");
        String postId = t.getPostId();
        hasLength(postId, "post id must have value");
        String categoryId = t.getCategoryId();
        hasLength(categoryId, "category id must have value");

        if (postCurd.select(postId) == null) {
            log.warn("新增文章分类失败，不存在id为{}的文章", postId);
            return null;
        }

        if (categoryCurd.select(categoryId) == null) {
            log.warn("新增文章分类失败，不存在id为{}的分类", categoryId);
            return null;
        }

        ensure(t);
        return mapper.insert(t) == 1 ? t : null;
    }

    @Override
    protected int deleteBy(PostCategoryRL postCategoryRL) {
        return mapper.deleteByCond(postCategoryRL);
    }

    @Override
    public PostCategoryRL updateById(PostCategoryRL rl) {
        return mapper.updateById(rl) == 1 ? rl : null;
    }

    @Override
    public long countBy(PostCategoryRL postCategoryRL) {
        return mapper.countByCond(postCategoryRL);
    }

    @Override
    public boolean deleteByPost(String postId) {
        hasLength(postId, "post id must not be empty");
        return mapper.deleteByPost(postId) > 0;
    }

    @Override
    public boolean deleteByCategory(String categoryId) {
        hasLength(categoryId, "category id must not be empty");
        return mapper.deleteByCategory(categoryId) > 0;
    }

    @Override
    public List<PostCategoryRL> findByPost(String postId) {
        hasLength(postId, "post id must not be empty");
        return mapper.selectByPost(postId);
    }

    @Override
    public List<PostCategoryRL> findByCategory(String categoryId) {
        hasLength(categoryId, "category id must not be empty");
        return mapper.selectByCategory(categoryId);
    }

    @Override
    public String findCategoryByPost(String postId) {
        hasLength(postId, "post id must not be empty");
        List<PostCategoryRL> rls = mapper.selectByPost(postId);
        PostCategoryRL rl =  rls.stream().findFirst().orElse(null);
        return rl == null ? null : rl.getCategoryId();
    }

    @Override
    public PageData<Post> pagePostByCategory(PageInfo pageInfo, Post post, String categoryId) {
        PageData<Post> result = new PageData();

        Page page = PageHelper.startPage(pageInfo.getPage(), pageInfo.getSize(), false);

        List<Post> pageData;

        try {
            pageData = mapper.selectPostByCategory(post,categoryId);
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
        hasLength(postId, "post id must not be empty");
        List<PostCategoryRL> list = mapper.selectByPost(postId);
        return list == null ? 0 : list.size();
    }

    @Override
    public int countByCategory(String categoryId) {
        hasLength(categoryId, "category id must not be empty");
        List<PostCategoryRL> list = mapper.selectByCategory(categoryId);
        return list == null ? 0 : list.size();
    }
}
