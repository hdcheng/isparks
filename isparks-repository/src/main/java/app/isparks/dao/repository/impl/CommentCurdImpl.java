package app.isparks.dao.repository.impl;

import app.isparks.core.util.HtmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.isparks.core.pojo.entity.Comment;
import org.springframework.stereotype.Repository;
import app.isparks.dao.mybatis.mapper.CommentMapper;
import app.isparks.dao.repository.CommentCurd;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentCurdImpl extends CommentCurd {

    private Logger log = LoggerFactory.getLogger(CommentCurdImpl.class);

    private CommentMapper mapper;

    public CommentCurdImpl(CommentMapper mapper){
        this.mapper = mapper;
    }

    @Override
    protected int deleteBy(Comment comment) {
        notNull(comment,"comment not be null");
        return mapper.deleteBy(comment);
    }

    @Override
    public Comment insert(Comment comment) {
        notNull(comment,"comment not be null");
        ensure(comment);
        comment.setContent(HtmlUtils.htmlEscapeAndRemoveByTags(comment.getContent(),"script","a"));
        return mapper.insert(comment) == 1 ? comment : null;
    }

    @Override
    public List<Comment> selectByCond(Comment comment) {
        notNull(comment,"comment not be null");
        List<Comment> comments = mapper.selectByCond(comment);
        comments.forEach(c -> c.setContent(HtmlUtils.htmlUnescape(c.getContent())));
        return comments;
    }

    @Override
    public Comment updateById(Comment comment) {
        notNull(comment,"comment not be null");
        hasLength(comment.getId(),"update comment id must not be empty");
        ensureModifyTime(comment);
        comment.setContent(HtmlUtils.htmlEscapeAndRemoveByTags(comment.getContent(),"script","a"));
        return mapper.updateById(comment) == 1 ? comment : null;
    }

    @Override
    public long countBy(Comment comment) {
        notNull(comment,"comment not be null");
        return mapper.countBy(comment);
    }

    @Override
    public List<Comment> selectByIds(List<String> ids) {
        notNull(ids,"comment ids list must not be null");
        List<Comment> comments = new ArrayList<>(ids.size());
        ids.forEach(id -> {
            comments.add(select(id));
        });
        return comments;
    }
}
