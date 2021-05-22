package app.isparks.core.pojo.entity.relation;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCommentRL extends BaseEntity {

    public PostCommentRL(){}

    public PostCommentRL(String postId,String commentId){
        this.postId = postId;
        this.commentId = commentId;
    }

    /**
     * 文章id
     */
    private String postId;

    /**
     * comment id
     */
    private String commentId;

}
