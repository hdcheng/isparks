package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.PostDTO;
import app.isparks.core.pojo.entity.Post;
import app.isparks.core.pojo.param.PostParam;
import app.isparks.core.pojo.param.PostUpdateParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:01+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class PostConverterImpl implements PostConverter {

    @Override
    public Post map(PostParam source) {
        if ( source == null ) {
            return null;
        }

        Post post = new Post();

        post.setTitle( source.getTitle() );
        post.setUrl( source.getUrl() );
        post.setOriginContent( source.getOriginContent() );
        post.setSummary( source.getSummary() );

        return post;
    }

    @Override
    public Post map(PostUpdateParam source) {
        if ( source == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( source.getId() );
        post.setTitle( source.getTitle() );
        post.setUrl( source.getUrl() );
        post.setOriginContent( source.getOriginContent() );
        post.setSummary( source.getSummary() );

        return post;
    }

    @Override
    public PostDTO map(Post source) {
        if ( source == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setId( source.getId() );
        postDTO.setModifyTime( source.getModifyTime() );
        postDTO.setCreateTime( source.getCreateTime() );
        postDTO.setTitle( source.getTitle() );
        postDTO.setUrl( source.getUrl() );
        postDTO.setOriginContent( source.getOriginContent() );
        postDTO.setSummary( source.getSummary() );
        if ( source.getStatus() != null ) {
            postDTO.setStatus( String.valueOf( source.getStatus() ) );
        }

        return postDTO;
    }

    @Override
    public List<PostDTO> maps(List<Post> source) {
        if ( source == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( source.size() );
        for ( Post post : source ) {
            list.add( map( post ) );
        }

        return list;
    }
}
