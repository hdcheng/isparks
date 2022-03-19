package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Log;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.dao.mybatis.mapper.LogMapper;
import app.isparks.dao.repository.LogCurd;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/12
 */
@Repository
public class LogCurdImpl extends LogCurd {

    private LogMapper logMapper;

    public LogCurdImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public List<Log> selectByCond(Log log) {
        return logMapper.selectByCond(log);
    }

    @Override
    public PageData<Log> pageByType(PageInfo info, String type) {

        Page page = PageHelper.startPage(info.getPage(), info.getSize(), true);
        page.setOrderBy("create_time DESC");

        PageData<Log> result = new PageData();

        try {
            List<Log> logs =logMapper.selectByType(type);
            result.setData(logs);
            result.setPage(info.getPage());
            result.setSize(info.getSize());
            result.setTotalPage(page.getPages());
            result.setTotalData(page.getTotal());
        }finally {
            PageHelper.clearPage(); // 清除分页数据
        }
        return result;
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
