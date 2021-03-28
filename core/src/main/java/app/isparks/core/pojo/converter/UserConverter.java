package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.entity.User;
import app.isparks.core.pojo.param.UserParam;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * User转换器
 *
 * @author chenghd
 * @date 2020/8/21
 */
@Mapper
public interface UserConverter {

    /**
     * params 转 user实体类
     */
    User map(UserParam source);

    /**
     * do 转 dto
     */
    UserDTO map(User source);

    /**
     * 转换多个对象
     */
    List<UserDTO> maps(List<User> sources);
}
