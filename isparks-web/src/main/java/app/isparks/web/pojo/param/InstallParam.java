package app.isparks.web.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 系统安装参数
 *
 * @author： chenghd
 * @date： 2021/1/12
 */
@Data
public class InstallParam {

    @NotEmpty(message = "用户名不能为空")
    private String userName;

    private String userNickName;

    private String userEmail;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "网站地址不能为空")
    private String webUrl;

    @NotEmpty(message = "站名不能为空")
    private String webName;

    /**
     * 语言
     */
    @NotEmpty(message = "本地语言不能为空")
    private String locale = "zh";

    /**
     * 数据库类型。默认为 H2
     */
    @NotNull(message = "数据库类型不能为空")
    private String database;

    /**
     * 数据库端口和地址。默认值为：127.0.0.1:8082
     */
    @NotNull(message = "数据库链接地址不能为空")
    private String dbHostPort;

    /**
     * 数据库用户名。默认为：sa
     */
    @NotNull(message = "数据库用户名不能为空")
    private String dbUsername;

    /**
     * 数据库密码。默认为：空。
     */
    private String dbPassword;

    /**
     * 数据库名缀名。默认为：i。
     */
    private String dbNamePrefix = "i";

}
