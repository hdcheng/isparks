package app.isparks.dao.repository;



import app.isparks.core.pojo.entity.User;
import app.isparks.dao.template.AbstractCurd;

/**
 * @author chenghd
 * @date 2020/8/12
 */
public abstract class UserCurd extends AbstractCurd<User> {

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
