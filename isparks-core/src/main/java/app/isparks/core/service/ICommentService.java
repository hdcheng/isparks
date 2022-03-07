package app.isparks.core.service;

import app.isparks.core.pojo.dto.CommentDTO;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.CommentParam;

import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/3
 */
public interface ICommentService {


    /**
     * 保存
     */
    Optional<CommentDTO> create(CommentParam param);

    /**
     * 根据 id 删除
     */
    Optional<CommentDTO> delete(String commentId);

    /**
     * 根据post查找评论
     */
    PageData<CommentDTO> pageByPost(String postId, int page, int size);

    /**
     * 全部分页
     */
    PageData<CommentDTO> pageAll(int page, int size);

    /**
     * 根据post查找有效（审核过）的评论
     */
    PageData<CommentDTO> pageValidByPost(String postId, int page, int size);

    /**
     * 审核评论
     */
    Optional<CommentDTO> updateToValid(String id);

    /**
     * 取消审核
     */
    Optional<CommentDTO> updateToInvalid(String id);
}
