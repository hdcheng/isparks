package app.isparks.dao.repository;



import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.entity.User;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;
import java.util.Optional;


/**
 * @author chenghd
 * @date 2020/8/12
 */
public abstract class AbstractUserCurd extends AbstractCurd<User> {

    @Override
    public User newEntity() {
        return new User();
    }

    /**
     * 根据用户名查找用户
     */
    public abstract User selectByName(String name);

    /**
     * 根据邮箱查找用户
     */
    public abstract User selectByEmail(String email);


}
