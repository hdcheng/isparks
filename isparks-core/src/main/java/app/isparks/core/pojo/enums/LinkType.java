package app.isparks.core.pojo.enums;

/**
 * @author： chenghd
 * @date： 2021/3/13
 */
public enum LinkType implements IEnum<Integer> {

    SITE_LINK(1), // 站内链接
    MENU_LINK(2), // 菜单链接
    PLUGIN(3),    // 插件链接
    OFFSITE_LINK(4) // 站外链接
    ;

    private int code;

    LinkType(int code){
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
