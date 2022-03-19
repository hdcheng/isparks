package app.addons.meet;

import app.addons.meet.service.IMeetRequestService;
import app.addons.meet.service.impl.MeetRequestServiceImpl;
import app.isparks.core.util.IOCUtils;
import app.isparks.plugin.PluginManager;

public class MeetApp {

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
    }

}
