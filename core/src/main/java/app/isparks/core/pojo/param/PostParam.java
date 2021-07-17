package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import app.isparks.core.pojo.base.InputConverter;
import app.isparks.core.pojo.entity.Post;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/10
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PostParam extends BaseParam implements InputConverter<Post> {

    /**
     * post title
     */
    @NotEmpty(message = "文章标题不能为空")
    private String title;

    /**
     * post url
     */
    private String url;

    /**
     * post origin content
     * 没有任何格式处理，原文本内容
     */
    @NotEmpty(message = "文章内容不能为空")
    private String originContent;

    /**
     * post summary
     * 摘要
     */
    private String summary;

    /**
     * 分类
     */
    @NotEmpty(message = "文章分类不能为空")
    private String categoryId;

    /**
     * 标签
     */
    @NotEmpty(message = "文章标签不能为空")
    private List<String> tagNames;


}
