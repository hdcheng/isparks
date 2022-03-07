package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;


/**
 * 附件实体类
 *
 * @author chenghd
 * @date 2020/8/18
 */
public class Attachment extends BaseEntity {

    /**
     * 关联的帖子
     */
    private String postId;

    /**
     * 文件
     */
    private String fileId;


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
