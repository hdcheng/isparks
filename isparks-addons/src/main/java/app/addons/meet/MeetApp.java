package app.addons.meet;

import app.addons.meet.service.IMeetRequestService;
import app.addons.meet.service.impl.MeetRequestServiceImpl;
import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.util.IOCUtils;
import app.isparks.core.util.ResourcesUtils;
import app.isparks.plugin.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MeetApp {

    private Logger log = LoggerFactory.getLogger(MeetApp.class);

    public void run(){
        IOCUtils.registerBean(MeetRequestServiceImpl.class);

        IOCUtils.getBeanByClass(IMeetRequestService.class).ifPresent(service -> {
            PluginManager.singleton().addRequest("GET", "meet/index", params -> service.index(params));
            PluginManager.singleton().addRequest("GET", "meet/links", params -> service.links());
            PluginManager.singleton().addRequest("GET", "meet/post", params -> service.post(params));
            PluginManager.singleton().addRequest("GET", "meet/post/hots", params -> service.hots());
            PluginManager.singleton().addRequest("GET", "meet/post/category", params -> service.categoryPost(params));
            PluginManager.singleton().addRequest("GET", "meet/category", params -> service.category(params));
            PluginManager.singleton().addRequest("GET", "meet/archive", params -> service.archive(params));
            PluginManager.singleton().addRequest("GET", "meet/post/id", params -> service.post(params.getOrDefault("postId","") + ""));
            PluginManager.singleton().addRequest("GET", "meet/about", params -> service.about(params));
        });


        try {
            copyResource();
        }catch (IOException e){
            log.warn("复制主题资源失败",e);
        }
    }

    private void copyResource() throws IOException {
        String THEME_PATH = ISparksProperties.THEME_FILE_PATH + ISparksConstant.URL_SEPARATOR + "meet";
        String MEET_PATH = "web" + ISparksConstant.URL_SEPARATOR + "meet";
        String[] files = new String[]{
                "theme.config","index.html", "about.html", "archive.html", "blog.html",
                "category.html","gallery.html","include.html","link.html",
                "manual.html","post.html","partials/footer.html","partials/head.html",
                "partials/header.html","partials/script.html"};

        String s = ResourcesUtils.readResources(MEET_PATH + ISparksConstant.URL_SEPARATOR + "about.html");

        for(String file : files){
            ResourcesUtils.copyResource(MEET_PATH + ISparksConstant.URL_SEPARATOR + file,new File(THEME_PATH,file),false);
        }
    }

}
