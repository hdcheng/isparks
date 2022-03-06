package app.isparks.web.controller.api;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.CommentParam;
import app.isparks.core.service.ICommentService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("评论管理")
@RestController
@RequestMapping("/api/admin/comment")
public class CommentApi {

    @Autowired
    private ICommentService commentService;

    @Log(description = "创建评论", types = {LogType.INSERT})
    @RequestMapping(value = "create",method = {RequestMethod.POST})
    public Result comment(@RequestBody CommentParam param){
        return ResultUtils.build(commentService.create(param));
    }


    @Log(description = "创建评论", types = {LogType.INSERT})
    @RequestMapping(value = "delete",method = {RequestMethod.GET})
    public Result delete(@RequestParam("id") String commentId){
        return ResultUtils.build(commentService.delete(commentId));
    }

    @RequestMapping(value = "page/valid/by/post",method = {RequestMethod.GET})
    public Result pageValidComments(@RequestParam("id") String postId, @RequestParam("page") int page , @RequestParam("size") int size){
        return ResultUtils.success().setData(commentService.pageValidByPost(postId,page,size));
    }

    @RequestMapping(value = "page/all/by/post",method = {RequestMethod.GET})
    public Result pageAllComments(@RequestParam("id") String postId, @RequestParam("page") int page , @RequestParam("size") int size){
        return ResultUtils.success().setData(commentService.pageByPost(postId,page,size));
    }

    @RequestMapping(value = "page/all",method = {RequestMethod.GET})
    public Result pageAll(@RequestParam("page") int page , @RequestParam("size") int size){
        return ResultUtils.build(commentService.pageAll(page,size));
    }

    @Log(description = "审核评论", types = {LogType.MODIFY})
    @RequestMapping(value = "valid",method = {RequestMethod.GET})
    public Result validComment(@RequestParam("id") String commentId){
        return ResultUtils.build(commentService.updateToValid(commentId));
    }

    @Log(description = "取消审核评论", types = {LogType.MODIFY})
    @RequestMapping(value = "invalid",method = {RequestMethod.GET})
    public Result invalidComment(@RequestParam("id") String commentId){
        return ResultUtils.build(commentService.updateToInvalid(commentId));
    }

}
