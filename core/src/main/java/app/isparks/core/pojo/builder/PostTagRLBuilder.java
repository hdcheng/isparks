package app.isparks.core.pojo.builder;



import app.isparks.core.pojo.entity.relation.PostTagRL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/20
 */
public class PostTagRLBuilder {

    public static List<PostTagRL> build(String postId, List<String> tagIds) {
        int len = tagIds.size();
        List<PostTagRL> list = new ArrayList<>(len);
        for (int i = 0; i < len; ++i) {
            list.add(build(postId, tagIds.get(i)));
        }
        return list;
    }

    public static PostTagRL build(String post, String tagId) {
        return new PostTagRL().withPostId(post).withTagId(tagId);
    }

}
