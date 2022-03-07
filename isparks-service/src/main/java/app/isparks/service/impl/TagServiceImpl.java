package app.isparks.service.impl;

import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.TagConverter;
import app.isparks.core.pojo.dto.TagDTO;
import app.isparks.core.pojo.entity.Tag;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.core.pojo.param.TagParam;
import app.isparks.core.service.ITagService;
import app.isparks.core.util.thread.LocalThreadUtils;
import app.isparks.dao.repository.AbstractPostTagRLCurd;
import app.isparks.dao.repository.AbstractTagCurd;
import app.isparks.core.service.support.BaseService;
import app.isparks.service.base.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/2/26
 */
@Service
public class TagServiceImpl extends AbstractService<Tag> implements ITagService {

    private Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    private TagConverter CONVERTER = ConverterFactory.get(TagConverter.class);

    private AbstractTagCurd tagCurd;

    private AbstractPostTagRLCurd ptRLCurd;

    public TagServiceImpl(AbstractTagCurd tagCurd,AbstractPostTagRLCurd ptRLCurd){
        super(tagCurd);
        this.tagCurd = tagCurd;

        notNull(ptRLCurd,"AbstractPostTagRLCurd must not be null");

        this.ptRLCurd = ptRLCurd;
    }

    @Override
    public Optional<TagDTO> create(TagParam param) {
        notNull(param,"tag param must not be null.");

        if(tagCurd.findByName(param.getName()) != null){

            resultMessage("标签["+param.getName()+"]不存在");

            return Optional.empty();
        }

        Tag tag = CONVERTER.map(param);

        tagCurd.insert(tag);

        return Optional.ofNullable(CONVERTER.map(tag));
    }


    @Override
    public List<TagDTO> createTagsByNamesIfAbsent(List<String> names) {
        notNull(names,"tag names must not be null.");

        List<TagDTO> tags = new ArrayList<>(names.size());

        names.stream().forEach((n) -> {
            TagDTO dto = createByNameIfAbsent(n);
            tags.add(dto);
        });

        return tags;
    }

    @Override
    public TagDTO createByNameIfAbsent(String name) {
        notEmpty(name,"tag name must not be empty.");

        Tag tag = tagCurd.findByName(name);

        if(tag != null){
            return CONVERTER.map(tag);
        }

        tag = new Tag().withName(name);

        tagCurd.insert(tag);

        return CONVERTER.map(tag);
    }

    @Override
    public PageData<TagDTO> page(int page, int size) {

        PageData<Tag> tags = tagCurd.pageAll(new PageInfo(page,size));

        PageData<TagDTO> result = tags.convertData((t)->toDTO(t));

        return result;
    }

    @Override
    public List<TagDTO> selectByIds(List<String> ids) {
        notNull(ids,"tags id must not be null.");

        List<Tag> tags = tagCurd.selectBatchByIds(ids);

        return toDTOs(tags);
    }

    @Override
    public Optional<TagDTO> deleteByName(String tagName) {
        notEmpty(tagName,"tag name must not be empty.");

        Tag tag = tagCurd.findByName(tagName);

        if(tag == null){
            resultMessage("标签["+tagName+"]不存在");
            return Optional.empty();
        }

        if(ptRLCurd.countByTag(tag.getId()) > 0){
            ptRLCurd.deleteByTag(tag.getId());
        }

        tag = tagCurd.delete(tag.getId());

        return Optional.ofNullable(CONVERTER.map(tag));
    }

    /**
     * do 转 dto
     *
     * @param tag
     */
    private TagDTO toDTO(Tag tag){

        TagDTO tagDTO = CONVERTER.map(tag);

        tagDTO.withProperty("postNumber",ptRLCurd.countByTag(tag.getId()));

        return tagDTO;
    }

    /**
     * 转换多个 tag
     *
     * @param tags
     */
    private List<TagDTO> toDTOs(List<Tag> tags){
        List<TagDTO> dtos = new ArrayList<>(tags.size());
        tags.stream().forEach((t) -> {
            dtos.add(toDTO(t));
        });
        return dtos;
    }

}
