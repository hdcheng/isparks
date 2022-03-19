package app.isparks.dao.repository;



import app.isparks.core.pojo.entity.Post;
import app.isparks.core.pojo.entity.relation.PostCategoryRL;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/8/13
 */
public abstract class PostCategoryRLCurd extends AbstractCurd<PostCategoryRL> {

    @Override
    public PostCategoryRL newEntity() {
        return new PostCategoryRL();
    }

    /**
     * delete by post
     */
    public abstract boolean deleteByPost(String postId);

    /**
     * delete by tag
     */
    public abstract boolean deleteByCategory(String categoryId);

    /**
     *
     */
    public abstract List<PostCategoryRL> findByPost(String postId);

    /**
     *
     */
    public abstract List<PostCategoryRL> findByCategory(String categoryId);

    /**
     * 根据分类查找数据
     */
    public abstract PageData<Post> pagePostByCategory(PageInfo pageInfo,Post post,String categoryId);

    /**
     * 根据 post id 查找分类
     */
    public abstract String findCategoryByPost(String postId);

    /**
     *
     */
    public abstract int countByPost(String postId);

    /**
     *
     */
    public abstract int countByCategory(String categoryId);


}
