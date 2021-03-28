package app.isparks.core.security.level;

/**
 * 权限等级
 *
 * @author： chenghd
 * @date： 2021/2/5
 */
public enum Level {

    // 超级管理员，拥有所有权限，30s 过期。
    SUPER_ADMIN(0,30000),
    // 一般管理员，拥有常用权限，1day 过期。
    ADMIN(1,12*60*1000)
    ;

    Level(int level,long expires){
        this.level = level;
        this.expires = expires;
    }


    // 权限等级
    private int level;

    // 过期时间 ms
    private long expires;

}
