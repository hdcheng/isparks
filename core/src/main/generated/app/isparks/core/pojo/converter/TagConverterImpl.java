package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.TagDTO;
import app.isparks.core.pojo.entity.Tag;
import app.isparks.core.pojo.param.TagParam;
import app.isparks.core.pojo.param.TagUpdateParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:00+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class TagConverterImpl implements TagConverter {

    @Override
    public Tag map(TagParam source) {
        if ( source == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setName( source.getName() );
        tag.setDescription( source.getDescription() );

        return tag;
    }

    @Override
    public Tag map(TagUpdateParam source) {
        if ( source == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setId( source.getId() );
        tag.setName( source.getName() );
        tag.setDescription( source.getDescription() );

        return tag;
    }

    @Override
    public TagDTO map(Tag source) {
        if ( source == null ) {
            return null;
        }

        TagDTO tagDTO = new TagDTO();

        tagDTO.setId( source.getId() );
        tagDTO.setModifyTime( source.getModifyTime() );
        tagDTO.setCreateTime( source.getCreateTime() );
        tagDTO.setName( source.getName() );
        tagDTO.setDescription( source.getDescription() );

        return tagDTO;
    }

    @Override
    public List<TagDTO> maps(List<Tag> source) {
        if ( source == null ) {
            return null;
        }

        List<TagDTO> list = new ArrayList<TagDTO>( source.size() );
        for ( Tag tag : source ) {
            list.add( map( tag ) );
        }

        return list;
    }
}
