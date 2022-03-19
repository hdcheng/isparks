package app.isparks.web.controller.page;


import app.isparks.core.pojo.param.CommentParam;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.web.controller.rest.CommentApi;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("网页页面")
@RestController("page_api")
@RequestMapping("api")
public class PageApi {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentApi commentApi;

    @RequestMapping(value = "comment/create",method = {RequestMethod.POST})
    public Result comment(@RequestBody CommentParam param){
        return ResultUtils.success().setData(commentApi.create(param).getData());
    }

    @RequestMapping(value = "page/comment/by/post",method = {RequestMethod.GET})
    public Result listComment(@RequestParam("id") String postId,
                              @RequestParam(value = "page",required = false) Integer page ,
                              @RequestParam(value = "size",required = false) Integer size){
        int p = page == null || page <= 0 ? 1 : page;
        int s = size == null || size <= 0 ? 5 : size;

        return ResultUtils.success().setData(commentApi.pageAllByPost(postId,p,s).getData());
    }

}
