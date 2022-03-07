package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.CommentDTO;
import app.isparks.core.pojo.entity.Comment;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:01+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class CommentConverterImpl implements CommentConverter {

    @Override
    public CommentDTO map(Comment source) {
        if ( source == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( source.getId() );
        commentDTO.setModifyTime( source.getModifyTime() );
        commentDTO.setCreateTime( source.getCreateTime() );
        commentDTO.setContent( source.getContent() );
        commentDTO.setName( source.getName() );
        commentDTO.setEmail( source.getEmail() );
        commentDTO.setAddress( source.getAddress() );

        return commentDTO;
    }
}
