package app.isparks.addons.blog.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author： chenghd
 * @date： 2021/3/25
 */
public class PostVisitEvent extends ApplicationEvent{

    private String postId;
    private long visits;

    public PostVisitEvent(Object o,String postId,long visits){
        super(o);
        this.postId = postId;
        this.visits = visits;
    }


    public String getPostId(){
        return postId;
    }
    public long getVisits(){
        return visits;
    }

}
