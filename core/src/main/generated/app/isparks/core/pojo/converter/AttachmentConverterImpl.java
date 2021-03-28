package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.AttachmentDTO;
import app.isparks.core.pojo.entity.Attachment;
import app.isparks.core.pojo.param.AttachmentParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:01+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class AttachmentConverterImpl implements AttachmentConverter {

    @Override
    public Attachment map(AttachmentParam source) {
        if ( source == null ) {
            return null;
        }

        Attachment attachment = new Attachment();

        attachment.setPostId( source.getPostId() );
        attachment.setFileId( source.getFileId() );

        return attachment;
    }

    @Override
    public AttachmentDTO map(Attachment source) {
        if ( source == null ) {
            return null;
        }

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setModifyTime( source.getModifyTime() );
        attachmentDTO.setCreateTime( source.getCreateTime() );
        attachmentDTO.setId( source.getId() );
        attachmentDTO.setPostId( source.getPostId() );
        attachmentDTO.setFileId( source.getFileId() );

        return attachmentDTO;
    }

    @Override
    public List<AttachmentDTO> maps(List<Attachment> sources) {
        if ( sources == null ) {
            return null;
        }

        List<AttachmentDTO> list = new ArrayList<AttachmentDTO>( sources.size() );
        for ( Attachment attachment : sources ) {
            list.add( map( attachment ) );
        }

        return list;
    }
}
