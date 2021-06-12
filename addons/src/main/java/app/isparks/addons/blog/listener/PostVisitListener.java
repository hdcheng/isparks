package app.isparks.addons.blog.listener;

import app.isparks.addons.blog.event.PostVisitEvent;
import app.isparks.addons.blog.service.IBlogService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author： chenghd
 * @date： 2021/3/25
 */
@Component
public class PostVisitListener  {

    private IBlogService blogService;

    public PostVisitListener(IBlogService blogService){
        this.blogService = blogService;
    }

    @EventListener
    @Async
    protected void onEvent(PostVisitEvent visitEvent) {
        String postId = visitEvent.getPostId();
        long visits = visitEvent.getVisits();
        blogService.increaseVisit(postId,visits);
    }
}
