package app.isparks.dao.repository.impl;

import app.isparks.core.exception.RepositoryException;
import app.isparks.core.pojo.entity.User;
import app.isparks.dao.mybatis.mapper.UserMapper;
import app.isparks.dao.repository.AbstractUserCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/12
 */
@Repository
public class UserCurdImpl extends AbstractUserCurd {

    private UserMapper userMapper;

    public UserCurdImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User insert(User user) {
        beforeInsert(user);
        if(selectByName(user.getUserName()) != null){
            throw new RepositoryException("用户名已存在："+user.getUserName());
        }

        if(selectByEmail(user.getEmail()) != null){
            throw new RepositoryException("邮箱已被使用："+user.getUserName());
        }
        
        return userMapper.insert(user) == 1 ? user : null;
    }

    @Override
    protected int deleteBy(User user) {
        return userMapper.deleteBy(user);
    }

    @Override
    public long countBy(User user) {
        return userMapper.countBy(user);
    }

    @Override
    public User updateById(User user) {
        return userMapper.updateById(user) == 1 ? user : null;
    }

    @Override
    public List<User> selectByCond(User user) {
        return userMapper.selectByCondition(user);
    }

    @Override
    public long count() {
        return userMapper.count();
    }

    @Override
    public User selectByName(String name) {
        List<User> users = userMapper.selectByCondition(new User().withUserName(name));
        return users != null && users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public User selectByEmail(String email) {
        List<User> users = userMapper.selectByCondition(new User().withEmail(email));
        return users != null && users.size() > 0 ? users.get(0) : null;
    }

}
