package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/7/24
 */
@Data
public class Post extends BaseEntity {


    /**
     * post title
     */
    private String title;

    /**
     * post url
     */
    private String url;

    /**
     * post origin content
     * 没有任何格式处理，原文本内容
     */
    private String originContent;

    /**
     * post summary
     * 摘要
     */
    private String summary;


    public Post withTitle(String tile) {
        this.title = tile;
        return this;
    }

    public Post withUrl(String url) {
        this.url = url;
        return this;
    }

    public Post withOriginContent(String originContent) {
        this.originContent = originContent;
        return this;
    }

    public Post withSummary(String summary) {
        this.summary = summary;
        return this;
    }


}
