package app.isparks.core.web.property;

import app.isparks.core.pojo.enums.PropertyEnum;

/**
 *
 * @author： chenghd
 * @date： 2021/1/23
 */
public enum  WebProperties implements PropertyEnum {

    /**
     * web name
     */
    WEBSITE_TITLE("website_name","ISparks",String.class),

    /**
     * web description
     */
    WEBSITE_DESCRIPTION("website_description","This is a website building system for individuals.",String.class),

    /**
     * web domain
     */
    WEBSITE_URL("website_domain","http://127.0.0.1:8174",String.class),

    /**
     * web logo
     */
    WEBSITE_LOGO("logo","/static/images/logo.png",String.class),

    /**
     * web logo text
     */
    WEBSITE_LOGO_TEXT("logo_text","I Sparks",String.class),

    /**
     * web favicon
     */
    WEBSITE_FAVICON("favicon","/static/images/favicon.ico",String.class),

    /**
     * web footer info
     */
    WEBSITE_FOOTER_INFO("website_footer_info","Powered by iSparks",String.class),

    /**
     * web footer
     */
    WEBSITE_FOOTER_COPY("website_footer_copy","Copyright © CHENGHD",String.class),

    /**
     * theme
     */
    WEBSITE_THEME_NAME("web_theme_name","default",String.class)
    ;


    private final String key;
    private final String value;
    private final Class<?> valueType;


    WebProperties(String key,String defaultValue,Class<?> valueType){
        this.key = key;
        this.value = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public String getCode() {
        return key;
    }

    @Override
    public String getKey() {
        return getCode();
    }

    @Override
    public Class<?> getValueType() {
        return valueType;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getDefaultValue() {
        return value;
    }

}
