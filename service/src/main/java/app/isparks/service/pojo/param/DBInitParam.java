package app.isparks.service.pojo.param;

import lombok.Data;

/**
 * 数据库初始化
 *
 * @author： chenghd
 * @date： 2021/1/13
 */
@Data
public class DBInitParam {

    // 数据库类型
    private String databaseName;

    // 地址
    private String host;

    // 端口
    private int port;

    // 编码格式
    private String charset;

    // 用户名
    private String userName;

    // 密码
    private String password;

}
