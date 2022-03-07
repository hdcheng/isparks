package app.isparks.core.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 系统初始化参数
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Data
public class InitParam extends UserParam {

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    private String title;

    /**
     * 数据库类型，如：postgresQL,H2,
     */
    private String DBType;

    /**
     * 数据库url
     */
    @NotEmpty(message = "数据库链接不能为空")
    private String DBHostPort;

    /**
     * 数据库登录名
     */
    @NotEmpty(message = "用户名不能为空")
    private String DBUserName;

    /**
     * 数据库密码
     */
    @NotEmpty(message = "密码不能为空")
    private String DBPassword;

    /**
     * 数据库名,如：fence
     */
    private String DBName;

    /**
     * 表名前缀,如：f_
     */
    private String DBTablePrefix;

    /**
     * 站名，如：xx的个人博客
     */
    private String name;
    /**
     * 网站描述，如：这是xx的个人博客，记录生活，记录学习，留住回忆。
     */
    private String description;
    /**
     * 地址，如：dbwos.com
     */
    private String address;
    /**
     * 作者,如：金木、利威尔·阿克曼
     */
    private String author;


    public InitParam withTitle(String title) {
        this.title = title;
        return this;
    }

    public InitParam withDBUserName(String DBUserName) {
        this.DBUserName = DBUserName;
        return this;
    }

    public InitParam withDBPassword(String DBPassword) {
        this.DBPassword = DBPassword;
        return this;
    }


}
