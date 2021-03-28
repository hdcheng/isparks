package app.isparks.plugin.enhance.web.vo;

import app.isparks.core.pojo.base.WebPageVO;
import lombok.Data;

/**
 * @author： chenghd
 * @date： 2021/3/27
 */
@Data
public class AboutPageVO extends WebPageVO {

    private String title;

    private String subtitle;

    private String contact;

    private String mdContent;

    private String htmlContent;

}
