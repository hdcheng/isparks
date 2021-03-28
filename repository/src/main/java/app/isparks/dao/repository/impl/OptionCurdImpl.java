package app.isparks.dao.repository.impl;


import app.isparks.core.pojo.entity.Option;
import app.isparks.dao.mybatis.mapper.OptionMapper;
import app.isparks.dao.repository.AbstractOptionCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptionCurdImpl extends AbstractOptionCurd {

    private OptionMapper mapper;

    public OptionCurdImpl(OptionMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public Option insert(Option t) {
        beforeInsert(t);
        return mapper.insert(t) == 1 ? t : null;
    }

    @Override
    protected int deleteBy(Option option) {
        return mapper.deleteByCond(option);
    }

    @Override
    public long countBy(Option option) {
        return mapper.countByCond(option);
    }

    @Override
    public Option updateById(Option t) {
        return mapper.updateById(t) == 1 ? t : null;
    }

    @Override
    public List<Option> selectByCond(Option t) {
        return mapper.selectByCond(t);
    }
}
