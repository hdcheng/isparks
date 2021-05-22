package app.isparks.service.impl;

import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.enums.DataType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.isparks.core.pojo.dto.CommentDTO;
import app.isparks.core.pojo.entity.Comment;
import app.isparks.core.service.IPostService;
import org.springframework.stereotype.Service;
import app.isparks.core.pojo.param.CommentParam;
import app.isparks.core.service.ICommentService;
import app.isparks.service.base.AbstractService;
import app.isparks.dao.repository.AbstractCommentCurd;
import app.isparks.core.pojo.converter.CommentConverter;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.entity.relation.PostCommentRL;
import org.springframework.beans.factory.annotation.Autowired;
import app.isparks.dao.repository.AbstractPostCommentRLCurd;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl extends AbstractService<Comment> implements ICommentService {

    @Autowired
    private IPostService postService;

    private Logger log = LoggerFactory.getLogger(getClass());

    private CommentConverter commentConverter = ConverterFactory.get(CommentConverter.class);

    private AbstractPostCommentRLCurd pcRLCurd;

    private AbstractCommentCurd commentCurd;

    public CommentServiceImpl(AbstractCommentCurd commentCurd,AbstractPostCommentRLCurd pcRLCurd) {
        super(commentCurd);
        this.commentCurd = commentCurd;
        this.pcRLCurd = pcRLCurd;
    }

    @Override
    @Transactional
    public Optional<CommentDTO> create(CommentParam param) {
        notNull(param,"comment must not be null");

        if(postService.existId(param.getPostId())){

            Comment comment = commentConverter.map(param);
            // 未审核
            comment.setStatus(DataStatus.INVALID);

            abstractInsert(comment);

            pcRLCurd.insert(new PostCommentRL(param.getPostId(),comment.getId()));

            CommentDTO dto = commentConverter.map(comment);

            return Optional.of(dto);

        }else {
            log.error("帖子不存在");
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<CommentDTO> delete(String commentId) {
        notEmpty(commentId,"comment id must not be empty");

        Optional<Comment> result = abstractDelete(commentId);
        if(result.isPresent()){
            pcRLCurd.deleteByComment(commentId);
            return Optional.ofNullable(commentConverter.map(result.get()));
        }

        return Optional.empty();
    }

    @Override
    public PageData<CommentDTO> pageByPost(String postId, int page, int size) {
        notEmpty(postId,"post id must not be empty");

        PageData<CommentDTO> pageData = null;
        List<CommentDTO> dtos = new LinkedList<>();

        PostCommentRL rl = new PostCommentRL();
        rl.setPostId(postId);
        PageData<PostCommentRL> rlPageData = pcRLCurd.pageByCond(new PageInfo(page,size),rl);

        pageData = rlPageData.convertData((pageRL)->{
            Optional<Comment> comment = abstractGetById(pageRL.getCommentId());
            if(comment.isPresent()){
                return commentConverter.map(comment.get());
            }else{
                return null;
            }
        });

        return pageData == null ? new PageData<>() : pageData;

    }

    @Override
    public PageData<CommentDTO> pageAll(int page, int size) {

        PageData<CommentDTO> res = abstractPageAll(page,size).convertData(comment -> {
            CommentDTO dto = commentConverter.map(comment);
            dto.withProperty("rl", DataType.POST);
            dto.withProperty("status",comment.getStatus());
            return dto;
        });

        return res;
    }

    @Override
    public PageData<CommentDTO> pageValidByPost(String postId, int page, int size) {
        notEmpty(postId,"post id must not be empty");
        PageData<CommentDTO> pageData = null;

        PostCommentRL rl = new PostCommentRL();
        rl.setPostId(postId);
        PageData<PostCommentRL> rlPageData = pcRLCurd.pageByCond(new PageInfo(page,size),rl);

        pageData = rlPageData.convertData((pageRL)->{
            Optional<Comment> comment = abstractGetById(pageRL.getCommentId());
            if(comment.isPresent() && comment.get().getStatus() == DataStatus.VALID.getCode()){
                return commentConverter.map(comment.get());
            }else{
                return null;
            }
        });

        return pageData == null ? new PageData<>() : pageData;
    }

    @Override
    public Optional<CommentDTO> updateToValid(String id) {
        notEmpty(id,"comment id must not be empty");

        Comment comment = (Comment)new Comment().withId(id).withStatus(DataStatus.VALID);
        Optional<Comment> result = abstractUpdate(comment);

        return result.isPresent() ? Optional.of(commentConverter.map(result.get())) : Optional.empty();
    }

    @Override
    public Optional<CommentDTO> updateToInvalid(String id) {
        notEmpty(id,"comment id must not be empty");

        Comment comment = (Comment)new Comment().withId(id).withStatus(DataStatus.INVALID);
        Optional<Comment> result = abstractUpdate(comment);

        return result.isPresent() ? Optional.of(commentConverter.map(result.get())) : Optional.empty();
    }
}
