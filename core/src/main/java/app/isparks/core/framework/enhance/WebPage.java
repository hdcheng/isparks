package app.isparks.core.framework.enhance;

/**
 * 增强器类型
 *
 * @author： chenghd
 * @date： 2021/3/23
 */
public enum WebPage {

    INDEX("web/index"),
    POST("web/post"),
    ARCHIVE("web/archive"),
    LINK("web/link"),
    ABOUT("web/about"),
    GALLERY("web/gallery"),
    CATEGORY("web/category"),
    TAG("web/tag"),
    UNKNOWN("web/");


    WebPage(String path){
        this.path = path;
    }

    private String path;

    public String file(){
        return path;
    }

}
