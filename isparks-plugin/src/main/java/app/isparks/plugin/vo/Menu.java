package app.isparks.plugin.vo;

import lombok.Data;

import java.util.List;

/**
 * 菜单
 *
 * @author： chenghd
 * @date： 2021/2/2
 */
@Data
public class Menu {

    public Menu() {
    }

    public Menu(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public Menu(String name, String link, List<Menu> subdirectory) {
        this.name = name;
        this.link = link;
        this.subdirectory = subdirectory;
    }

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单链接
     */
    private String link;

    /**
     * 子菜单
     */
    private List<Menu> subdirectory;

}
