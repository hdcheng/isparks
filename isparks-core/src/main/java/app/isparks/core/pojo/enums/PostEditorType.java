package app.isparks.core.pojo.enums;

/**
 * 文章编辑类型
 *
 * @author chenghd
 * @date 2020/8/20
 */
public enum PostEditorType implements IEnum<Integer>{
    //纯文本       markdown文本      富文本
    TEXT(0), MARKDOWN(1), RICHTEXT(2);

    PostEditorType(Integer code) {
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

}
