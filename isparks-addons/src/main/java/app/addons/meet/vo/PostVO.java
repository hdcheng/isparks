package app.addons.meet.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class PostVO {

    private String content;

    private String summary;

    private String title;

    private Long createTime;

    private Map<String, Object> properties;

}
