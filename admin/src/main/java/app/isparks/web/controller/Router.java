package app.isparks.web.controller;

import app.isparks.core.service.ILinkService;
import app.isparks.core.service.IOptionService;
import app.isparks.core.web.property.WebProperties;
import app.isparks.plugin.vo.Button;
import app.isparks.plugin.vo.Menu;
import app.isparks.service.impl.LinkServiceImpl;
import app.isparks.service.impl.OptionServiceImpl;
import com.google.common.collect.Lists;
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

    private IOptionService optionService;

    private ILinkService linkService;

    static {

        DEFAULT_MENUS.add(new Menu("首页","/admin"));
        DEFAULT_MENUS.add(new Menu("文件管理","/admin/file"));
        DEFAULT_MENUS.add(new Menu("链接管理","/admin/link"));
        DEFAULT_MENUS.add(new Menu("评论管理","/admin/comment"));

        // 文章管理
        List<Menu> postManage = new ArrayList<>(3);
        postManage.add(new Menu("文章分类","/admin/post/category"));
        postManage.add(new Menu("文章标签","/admin/post/tag"));
        postManage.add(new Menu("文章管理","/admin/post/manage"));
        postManage.add(new Menu("写文章","/admin/post/edit"));
        DEFAULT_MENUS.add(new Menu("内容管理","#",postManage));

        // 默认插件：插件管理
        Button pluginManager = new Button();
        pluginManager.setLink("/admin/plugin/pluginmanager");
        pluginManager.setName("插件管理");
        pluginManager.setSvg("<svg t=\"1621950986359\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2119\" width=\"200\" height=\"200\"><path d=\"M810.666667 597.333333h-85.333334v-64a21.333333 21.333333 0 0 0-21.333333-21.333333h-85.333333a21.333333 21.333333 0 0 0-21.333334 21.333333V597.333333h-170.666666v-64a21.333333 21.333333 0 0 0-21.333334-21.333333h-85.333333a21.333333 21.333333 0 0 0-21.333333 21.333333V597.333333H213.333333a42.666667 42.666667 0 0 0-42.666666 42.666667v256a42.666667 42.666667 0 0 0 42.666666 42.666667h597.333334a42.666667 42.666667 0 0 0 42.666666-42.666667v-256a42.666667 42.666667 0 0 0-42.666666-42.666667z m-42.666667 256H256v-170.666666h512z m42.666667-768H213.333333a42.666667 42.666667 0 0 0-42.666666 42.666667v256a42.666667 42.666667 0 0 0 42.666666 42.666667h597.333334a42.666667 42.666667 0 0 0 42.666666-42.666667V128a42.666667 42.666667 0 0 0-42.666666-42.666667z m-42.666667 256H256V170.666667h512z\" p-id=\"2120\" fill=\"#e6e6e6\"></path></svg>");
        DEFAULT_BUTTONS.add(pluginManager);

    }


    public Router(OptionServiceImpl optionService, LinkServiceImpl linkService){
        this.optionService = optionService;
        this.linkService = linkService;
    }

    public String navigator(String pageFileName){

        if(pageFileName.endsWith("/")){
            pageFileName.substring(0,pageFileName.length()-1);
        }

        // 插件前端文件路径
        if(pageFileName.startsWith("plugin/")){
            pageFileName = pageFileName.replace("plugin/","");
            return buildPluginsPath(pageFileName);
        }

        return PAGE_PATH + pageFileName;
    }

    /**
     * 插件模板路径
     * @param fileName
     * @return
     */
    private String buildPluginsPath(String fileName){
        return PLUGIN_PATH + fileName;
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

}
