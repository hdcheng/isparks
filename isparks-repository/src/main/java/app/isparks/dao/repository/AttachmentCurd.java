package app.isparks.dao.repository;


import app.isparks.core.pojo.entity.Attachment;
import app.isparks.dao.template.AbstractCurd;

/**
 * @author chenghd
 * @date 2020/9/29
 */
public abstract class AttachmentCurd extends AbstractCurd<Attachment> {

    @Override
    public Attachment newEntity() {
        return new Attachment();
    }

}
