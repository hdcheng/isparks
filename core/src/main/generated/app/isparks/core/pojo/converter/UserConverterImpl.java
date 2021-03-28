package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.entity.User;
import app.isparks.core.pojo.param.UserParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-10T21:43:00+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Amazon.com Inc.)"
)
public class UserConverterImpl implements UserConverter {

    @Override
    public User map(UserParam source) {
        if ( source == null ) {
            return null;
        }

        User user = new User();

        user.setNickName( source.getNickName() );
        user.setUserName( source.getUserName() );
        user.setEmail( source.getEmail() );
        user.setPassword( source.getPassword() );
        user.setAvatar( source.getAvatar() );

        return user;
    }

    @Override
    public UserDTO map(User source) {
        if ( source == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( source.getId() );
        userDTO.setModifyTime( source.getModifyTime() );
        userDTO.setCreateTime( source.getCreateTime() );
        userDTO.setNickName( source.getNickName() );
        userDTO.setUserName( source.getUserName() );
        userDTO.setEmail( source.getEmail() );
        userDTO.setAvatar( source.getAvatar() );

        return userDTO;
    }

    @Override
    public List<UserDTO> maps(List<User> sources) {
        if ( sources == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( sources.size() );
        for ( User user : sources ) {
            list.add( map( user ) );
        }

        return list;
    }
}
