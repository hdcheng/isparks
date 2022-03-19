package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.dto.TagDTO;
import app.isparks.core.pojo.entity.Tag;
import app.isparks.core.util.BeanUtils;
import app.isparks.dao.mybatis.mapper.TagMapper;
import app.isparks.dao.repository.TagCurd;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/8/12
 */
@Repository
public class TagCurdImpl extends TagCurd {

    private TagMapper mapper;

    public TagCurdImpl(TagMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Tag> selectByCond(Tag tag) {
        return mapper.selectByCond(tag);
    }

    @Override
    public Tag insert(Tag t) {
        beforeInsert(t);
        return mapper.insert(t) == 1 ? t : null;
    }

    @Override
    protected int deleteBy(Tag tag) {
        return mapper.deleteByCond(tag);
    }

    @Override
    public long countBy(Tag tag) {
        return mapper.countByCond(tag);
    }

    @Override
    public Optional<TagDTO> queryById(String tagId) {
        hasLength(tagId, "tag id must not be empty");
        Tag tag = mapper.selectById(tagId);
        return BeanUtils.copyPropertiesOptional(tag, TagDTO.class);
    }

    @Override
    public Optional<List<TagDTO>> findByIds(List<String> tagIds) {
        List<Tag> list = mapper.selectByIds(tagIds);
        return BeanUtils.copyPropertiesOptional(list, Tag.class, TagDTO.class);
    }

    @Override
    public Tag updateById(Tag t) {
        beforeUpdate(t);
        return mapper.updateById(t) == 1 ? t : null;
    }

    @Override
    public int deleteByName(String name) {
        return mapper.deleteByName(name);
    }


    @Override
    public Tag findByName(String name) {
        List<Tag> tags = mapper.selectByName(name);

        if (tags == null || tags.size() <= 0)
            return null;

        return tags.get(0);
    }
}
