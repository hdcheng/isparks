package app.isparks.dao.repository;



import app.isparks.core.pojo.dto.TagDTO;
import app.isparks.core.pojo.entity.Tag;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/8/12
 */
public abstract class AbstractTagCurd extends AbstractCurd<Tag> {

    @Override
    public Tag newEntity() {
        return new Tag();
    }

    /**
     * 根据标签名删除
     */
    public abstract int deleteByName(String name);

    public abstract Optional<TagDTO> queryById(String tagId);

    public abstract Optional<List<TagDTO>> findByIds(List<String> tagIds);

    /**
     * 根据标签名查找标签
     */
    public abstract Tag findByName(String name);


}
