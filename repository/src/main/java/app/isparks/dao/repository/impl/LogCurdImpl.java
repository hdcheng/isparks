package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Log;
import app.isparks.dao.mybatis.mapper.LogMapper;
import app.isparks.dao.repository.AbstractLogCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/12
 */
@Repository
public class LogCurdImpl extends AbstractLogCurd {

    private LogMapper logMapper;

    public LogCurdImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public List<Log> selectByCond(Log log) {
        return logMapper.selectByCond(log);
    }

    @Override
    public Log insert(final Log t) {
        beforeInsert(t);
        return logMapper.insert(t) == 1 ? t : null;
    }

    @Override
    protected int deleteBy(Log log) {
        return logMapper.deleteByCond(log);
    }

    @Override
    public Log updateById(Log t) {
        return logMapper.updateByCond(t) == 1 ? t : null;
    }

    @Override
    public long countBy(Log log) {
        return logMapper.countByCond(log);
    }
}
