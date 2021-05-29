package app.isparks.plugin.vo;

import lombok.Data;

/**
 * 工具
 *
 * @author： chenghd
 * @date： 2021/2/3
 */
@Data
public class Button {

    public Button(){

    }

    public Button(String name, String link, String html, String svg) {
        this.name = name;
        this.link = link;
        this.html = html;
        this.svg = svg;
    }

    private String name;

    private String link;

    private String html;

    private String svg;

}
