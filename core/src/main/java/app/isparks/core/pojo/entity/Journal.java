package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * 日志/随笔
 *
 * @author chenghd
 * @date 2020/8/16
 */
public class Journal extends BaseEntity {

    /**
     * 日志/随笔内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
