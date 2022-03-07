package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * @author chenghd
 * @date 2020/7/24
 */
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginContent() {
        return originContent;
    }

    public void setOriginContent(String originContent) {
        this.originContent = originContent;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
