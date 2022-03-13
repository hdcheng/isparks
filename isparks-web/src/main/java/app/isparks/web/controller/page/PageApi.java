package app.isparks.web.controller.page;


import app.isparks.plugin.enhance.AbstractViewModelEnhancer;
import app.isparks.core.pojo.base.BaseVO;
import app.isparks.core.pojo.param.CommentParam;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.plugin.enhance.web.IndexPageEnhancer;
import app.isparks.web.controller.api.CommentApi;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Api("网页页面")
@RestController("page_api")
@RequestMapping("api")
public class PageApi {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final static AbstractViewModelEnhancer indexPageEnhancer;

    @Autowired
    private CommentApi commentApi;

    static {
        indexPageEnhancer = IndexPageEnhancer.singleton();
    }

    @RequestMapping(value = "index",method = {RequestMethod.GET,RequestMethod.POST})
    public Result<BaseVO> index(){

        //getRequest().ifPresent(request -> indexPageEnhancer.before(request));

        BaseVO vo = new BaseVO();

        //indexPageEnhancer.execute(vo);

        // 后置增强器
        //getResponse().ifPresent(response -> indexPageEnhancer.after(response));

        return ResultUtils.success().setData(vo);
    }


    @RequestMapping(value = "comment/create",method = {RequestMethod.POST})
    public Result comment(@RequestBody CommentParam param){
        return ResultUtils.success().setData(commentApi.comment(param).getData());
    }

    @RequestMapping(value = "page/comment/by/post",method = {RequestMethod.GET})
    public Result listComment(@RequestParam("id") String postId,
                              @RequestParam(value = "page",required = false) Integer page ,
                              @RequestParam(value = "size",required = false) Integer size){
        int p = page == null || page <= 0 ? 1 : page;
        int s = size == null || size <= 0 ? 5 : size;

        return ResultUtils.success().setData(commentApi.pageValidComments(postId,p,s).getData());
    }

}
