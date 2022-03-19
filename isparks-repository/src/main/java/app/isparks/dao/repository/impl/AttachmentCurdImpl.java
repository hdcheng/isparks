package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Attachment;
import app.isparks.dao.mybatis.mapper.AttachmentMapper;
import app.isparks.dao.repository.AttachmentCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/9/29
 */
@Repository
public class AttachmentCurdImpl extends AttachmentCurd {

    private AttachmentMapper attachmentMapper;

    public AttachmentCurdImpl(AttachmentMapper attachmentMapper){
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public Attachment insert(Attachment a) {
        beforeInsert(a);
        return attachmentMapper.insert(a) == 1 ? a : null;
    }

    @Override
    protected int deleteBy(Attachment attachment) {
        return attachmentMapper.deleteByCond(attachment);
    }

    @Override
    public List<Attachment> selectByCond(Attachment a) {
        return attachmentMapper.selectByCond(a);
    }

    @Override
    public Attachment updateById(Attachment a) {
        checkId(a);
        beforeUpdate(a);
        return attachmentMapper.updateById(a) == 1 ? a : null;
    }

    @Override
    public long countBy(Attachment attachment) {
        return attachmentMapper.countByCond(attachment);
    }
}
