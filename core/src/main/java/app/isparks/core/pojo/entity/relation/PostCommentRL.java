package app.isparks.core.pojo.entity.relation;

import app.isparks.core.pojo.base.BaseEntity;

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


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
