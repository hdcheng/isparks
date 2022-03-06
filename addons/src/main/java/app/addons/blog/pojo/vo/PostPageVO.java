package app.addons.blog.pojo.vo;

import app.isparks.core.pojo.base.BaseVO;
import lombok.Data;

@Data
public class PostPageVO extends BaseVO {

    private String id;

    private Long createTime;

    private Long modifyTime;

    private String originContent;

    private String title;
}
