package app.isparks.addons.blog.api;

import app.isparks.addons.blog.event.PostVisitEvent;
import app.isparks.addons.blog.service.BlogServiceImpl;
import app.isparks.addons.blog.service.IBlogService;
import app.isparks.core.web.support.BaseWebApi;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author： chenghd
 * @date： 2021/3/20
 */
@RestController
public class BlogWebApi implements BaseWebApi, ApplicationEventPublisherAware{

    private IBlogService blogService;

    private ApplicationEventPublisher publisher;

    public BlogWebApi(BlogServiceImpl blogService){
        this.blogService = blogService;
    }

    /**
     * id post id
     */
    @RequestMapping(value = "/api/plugin/post/visit",method = {RequestMethod.GET})
    public Result visits(@RequestParam("id") String postId, @RequestParam(value = "visits",required = false) Integer newVisits , HttpServletRequest request){

        long visits = newVisits == null ? 1 : (long)newVisits;

        if (publisher != null) {
            publisher.publishEvent(new PostVisitEvent(this,postId,visits));
        }

        return ResultUtils.success();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
