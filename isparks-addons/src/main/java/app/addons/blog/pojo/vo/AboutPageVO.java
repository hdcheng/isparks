package app.addons.blog.pojo.vo;

import app.isparks.core.pojo.base.BaseVO;
import lombok.Data;

@Data
public class AboutPageVO extends BaseVO {

    private String title;

    private String subtitle;

    private String contact;

    private String mdContent;

    private String htmlContent;

}
