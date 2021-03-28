package app.isparks.dao.repository;

import app.isparks.core.pojo.entity.Link;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;

/**
 * @author： chenghd
 * @date： 2021/3/13
 */
public abstract class AbstractLinkCurd  extends AbstractCurd<Link> {

    @Override
    public Link newEntity() {
        return new Link();
    }

    public abstract Link selectByUrl(String url);

}
