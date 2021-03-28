package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Journal;
import app.isparks.dao.mybatis.mapper.JournalMapper;
import app.isparks.dao.repository.AbstractJournalCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/16
 */
@Repository
public class JournalCurdImpl extends AbstractJournalCurd {

    private JournalMapper journalMapper;

    public JournalCurdImpl(JournalMapper journalMapper) {
        this.journalMapper = journalMapper;
    }

    @Override
    public List<Journal> selectByCond(Journal journal) {
        return journalMapper.selectByCond(journal);
    }

    @Override
    public Journal insert(Journal t) {
        beforeInsert(t);
        return journalMapper.insert(t) == 1 ? t : null;
    }

    @Override
    protected int deleteBy(Journal journal) {
        return journalMapper.deleteByCond(journal);
    }

    @Override
    public Journal updateById(Journal t) {
        beforeUpdate(t);
        return journalMapper.updateById(t) == 1 ? t : null;
    }

    @Override
    public long countBy(Journal journal) {
        return journalMapper.countByCond(journal);
    }



}
