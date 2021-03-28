package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * 应用信息
 *
 * @author chenghd
 * @date 2020/11/4
 */
@Data
public class AppInfo extends BaseDTO {

    /**
     * 应用名
     */
    private String name;

    /**
     * 启动时间
     * 用于计算服务单次运行时长
     */
    private long startTime;

    /**
     * 初始化时间
     * 用于计算服务运行时长
     */
    private long initTime;


}
