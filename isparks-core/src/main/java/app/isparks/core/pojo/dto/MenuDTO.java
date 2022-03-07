package app.isparks.core.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 菜单
 *
 * @author chenghd
 * @date 2020/9/14
 */
@Getter
@Setter
public class MenuDTO {

    /**
     * 显示名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 链接
     */
    private String url;

    /**
     * 子类菜单
     */
    List<MenuDTO> children;
}
