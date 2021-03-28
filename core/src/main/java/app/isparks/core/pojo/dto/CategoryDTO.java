package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * 分类
 *
 * @author chenghd
 * @date 2020/8/10
 */
@Data
public class CategoryDTO extends BaseDTO {

    /**
     * category name
     */
    private String name;

    /**
     * category description
     */
    private String description;

    /**
     * 帖子数量
     */
    private long postNumber;

}
