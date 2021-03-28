package app.isparks.addons.blog.listener;

import app.isparks.addons.blog.event.PostVisitEvent;
import app.isparks.addons.blog.service.IBlogService;

/**
 * @author： chenghd
 * @date： 2021/3/25
 */
public class PostVisitListener extends AbstractPostVisitListener {

    private IBlogService blogService;

    public PostVisitListener(IBlogService blogService){
        this.blogService = blogService;
    }

    @Override
    protected void onEvent(PostVisitEvent visitEvent) {
        String postId = visitEvent.getPostId();
        long visits = visitEvent.getVisits();
        blogService.increaseVisit(postId,visits);
    }
}
