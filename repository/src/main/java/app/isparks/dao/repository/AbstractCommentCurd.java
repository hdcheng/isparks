package app.isparks.dao.repository;

import app.isparks.core.pojo.entity.Comment;
import app.isparks.core.pojo.page.PageData;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;

public abstract class AbstractCommentCurd extends AbstractCurd<Comment> {

    @Override
    public Comment newEntity() {
        return new Comment();
    }


    public abstract List<Comment> selectByIds(List<String> ids);


}
