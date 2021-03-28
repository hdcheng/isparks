package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * 日志/随笔
 *
 * @author chenghd
 * @date 2020/8/16
 */
@Data
public class Journal extends BaseEntity {

    /**
     * 日志/随笔内容
     */
    private String content;


}
