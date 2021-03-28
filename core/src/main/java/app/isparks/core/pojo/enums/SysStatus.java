package app.isparks.core.pojo.enums;

/**
 * 系统状态
 *
 * @author chenghd
 * @date 2020/7/28
 */
public enum SysStatus {
    UNINITIALIZED("系统未初始化"), INITIALIZED("系统已初始化");

    SysStatus(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
