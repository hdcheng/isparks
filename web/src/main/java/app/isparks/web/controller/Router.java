package app.isparks.web.controller;

import app.isparks.core.service.ILinkService;
import app.isparks.core.service.IOptionService;
import app.isparks.core.web.property.WebProperties;
import app.isparks.web.pojo.vo.Button;
import app.isparks.web.pojo.vo.Menu;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author： chenghd
 * @date： 2021/2/2
 */
@Component
public class Router {

    // 默认模块名
    private static final String DEFAULT_PAGE_FRAGMENT = "fra";

    // 页面模块路径
    private static final String PAGE_PATH = "partials/sections/";

    // 插件文件路径
    private static final String PLUGIN_PATH = "plugin/sections/";

    // 目录
    private final static List<Menu> DEFAULT_MENUS = new ArrayList<>();

    // 插件目录
    private final static List<Button> DEFAULT_BUTTONS = new ArrayList<>();

    @Autowired
    private ILinkService linkService;

    static {

        DEFAULT_MENUS.add(new Menu("首页","/admin"));
        DEFAULT_MENUS.add(new Menu("文件管理","/admin/file"));
        DEFAULT_MENUS.add(new Menu("链接管理","/admin/link"));

        // 文章管理
        List<Menu> postManage = new ArrayList<>(3);
        postManage.add(new Menu("文章分类","/admin/post/category"));
        postManage.add(new Menu("文章标签","/admin/post/tag"));
        postManage.add(new Menu("文章管理","/admin/post/manage"));
        postManage.add(new Menu("写文章","/admin/post/edit"));
        DEFAULT_MENUS.add(new Menu("内容","#",postManage));

    }

    @Autowired
    private IOptionService optionService;

    public String navigator(String pageFileName){

        if(pageFileName.endsWith("/")){
            pageFileName.substring(0,pageFileName.length()-1);
        }

        if(pageFileName.startsWith("plugin/")){
            pageFileName = pageFileName.replace("plugin/","");
            return buildPluginsPath(pageFileName);
        }

        return PAGE_PATH + pageFileName;
    }

    public void setPageModel(Model model, String pageFileName, String content){
        before(model);
        model.addAttribute("page",navigator(pageFileName));
        model.addAttribute("pageName",pageFileName);
        model.addAttribute("content",content);
    }

    public void setPageModel(Model model, String pageFileName){
        setPageModel(model,pageFileName,DEFAULT_PAGE_FRAGMENT);
    }

    private void before(Model model){

        // 菜单
        List<Menu> menus = Lists.newArrayList(DEFAULT_MENUS.listIterator());
        linkService.listMenuType().forEach((link) -> {
            menus.add(new Menu(link.getName(),link.getUrl()));
        });
        model.addAttribute("menus",menus);

        // 插件按钮
        List<Button> buttons = Lists.newArrayList(DEFAULT_BUTTONS.listIterator());
        linkService.listPluginType().forEach((button) -> {
            buttons.add(new Button(button.getName(),button.getUrl(),button.getLogo(),button.getLogo()));
        });
        model.addAttribute("buttons",buttons);

        // 网站标题
        model.addAttribute("title",optionService.getByPropertyOrDefault(WebProperties.WEBSITE_TITLE,String.class));

        // 网站文字logo
        model.addAttribute("copy",optionService.getByPropertyOrDefault(WebProperties.WEBSITE_FOOTER_COPY,String.class));

        // logo位置
        model.addAttribute("logo",optionService.getByPropertyOrDefault(WebProperties.WEBSITE_LOGO,String.class));

        // logo text
        model.addAttribute("logoText",optionService.getByPropertyOrDefault(WebProperties.WEBSITE_LOGO_TEXT,String.class));

    }

    /**
     * 插件模板路径
     * @param fileName
     * @return
     */
    private String buildPluginsPath(String fileName){
        return PLUGIN_PATH + fileName;
    }

}
