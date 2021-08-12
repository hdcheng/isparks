package app.isparks.web.controller.rest;

import app.isparks.core.pojo.param.CommentParam;
import app.isparks.core.service.ICommentService;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.CommentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */

@Api("评论管理接口")
@RequestMapping("v1/admin")
@RestController("v1_CommentApi")
public class CommentApi extends BasicApi{

    private ICommentService commentService;

    public CommentApi(CommentServiceImpl commentService){
         this.commentService = commentService;
    }

    @PostMapping("comment")
    @ApiOperation("Create comment | 创建评论")
    public Result create(@RequestBody CommentParam param){
        return build(commentService.create(param));
    }

    @DeleteMapping("comment/{id}")
    @ApiOperation("Delete comment by id | 根据 id 删除评论")
    public Result deleteById(@PathVariable("id") String id){
        return build(commentService.delete(id));
    }

    @GetMapping("comment/page/valid/post/{id}")
    @ApiOperation("Page valid status comments | 查找 valid 状态的帖子的评论")
    public Result pageValidByPost(@PathVariable(value = "id") String postId,
                            @RequestParam(value = "page",defaultValue = "1") int page ,
                            @RequestParam(value = "size" , defaultValue = "10") int size){
        return build(commentService.pageValidByPost(postId,page,size));
    }

    @GetMapping("comment/page/all/post/{id}")
    @ApiOperation("Page all comments | 分页查找所有帖子的评论")
    public Result pageAllByPost(@PathVariable(value = "id") String postId,
                            @RequestParam(value = "page",defaultValue = "1") int page ,
                            @RequestParam(value = "size" , defaultValue = "10") int size){
        return build(commentService.pageByPost(postId,page,size));
    }

    @GetMapping("comment/page")
    @ApiOperation("Page all comments | 分页查找所有评论")
    public Result page(@RequestParam(value = "page",defaultValue = "1") int page ,
                       @RequestParam(value = "size" , defaultValue = "10") int size){
        return build(commentService.pageAll(page,size));
    }

    @PatchMapping("comment/valid/{id}")
    public Result valid(@PathVariable("id") String commentId){
        return build(commentService.updateToValid(commentId));
    }

    @PatchMapping("comment/invalid/{id}")
    public Result invalid(@PathVariable("id") String commentId){
        return build(commentService.updateToInvalid(commentId));
    }

}
