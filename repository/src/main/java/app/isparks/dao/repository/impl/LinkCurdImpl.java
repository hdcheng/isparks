package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Link;
import app.isparks.dao.mybatis.mapper.LinkMapper;
import app.isparks.dao.repository.AbstractLinkCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author： chenghd
 * @date： 2021/3/13
 */
@Repository
public class LinkCurdImpl extends AbstractLinkCurd {

    private LinkMapper linkMapper;

    public LinkCurdImpl(LinkMapper linkMapper){
        this.linkMapper = linkMapper;
    }

    @Override
    protected int deleteBy(Link link) {
        return linkMapper.deleteByCond(link);
    }

    @Override
    public Link insert(Link link) {
        beforeInsert(link);
        return linkMapper.insert(link) == 1 ? link : null;
    }

    @Override
    public List<Link> selectByCond(Link link) {
        return linkMapper.selectByCond(link);
    }

    @Override
    public Link updateById(Link link) {
        return linkMapper.updateById(link) == 1 ? link : null;
    }

    @Override
    public long countBy(Link link) {
        return linkMapper.countByCond(link);
    }

    @Override
    public Link selectByUrl(String url) {
        return linkMapper.selectByUrl(url);
    }
}
