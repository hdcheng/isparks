package app.isparks.core.framework.enhance;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.web.property.WebConstant;

/**
 * 增强器类型
 *
 * @author： chenghd
 * @date： 2021/3/23
 */
public enum WebPage {
    INDEX(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "index"),
    POST(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "post"),
    ARCHIVE(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "archive"),
    LINK(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "link"),
    ABOUT(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "about"),
    GALLERY(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "gallery"),
    CATEGORY(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "category"),
    TAG(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR + "tag"),
    UNKNOWN(WebConstant.WEB_TEMPLATE_PATH_NAME + ISparksConstant.URL_SEPARATOR );


    WebPage(String path){
        this.path = path;
    }

    private String path;

    public String file(){
        return path;
    }


}
