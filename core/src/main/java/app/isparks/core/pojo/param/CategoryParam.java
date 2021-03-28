package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 分类
 *
 * @author chenghd
 * @date 2020/8/10
 */
@Data
public class CategoryParam extends BaseParam {

    public CategoryParam(){}

    public CategoryParam(String name,String description){
        this.name = name;
        this.description = description;
    }

    /**
     * category name
     */
    @NotEmpty(message = "添加失败：分类名不能为空")
    private String name;

    /**
     * category description
     */
    private String description;

    /**
     * category parent id
     */
    private String parentId;

}
