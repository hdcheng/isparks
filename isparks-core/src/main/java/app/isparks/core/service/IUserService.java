package app.isparks.core.service;

import app.isparks.core.exception.AuthException;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.entity.User;
import app.isparks.core.pojo.param.UpdateUserParam;
import app.isparks.core.pojo.param.UserParam;

import java.util.List;
import java.util.Optional;

/**
 * User Service
 *
 * @author chenghd
 * @date 2021/1/02
 */
public interface IUserService {


    /**
     * 创建用户
     *
     * @param userParam
     * @return boolean
     */
    boolean create(UserParam userParam);

    /**
     * 重置密码
     *
     * @param userId 用户数据的ID值
     * @return 是否重置成功
     */
    boolean resetPwd(String userId);


    /**
     * 更新密码
     *
     * @param userName
     * @param oldPwd
     * @param newPwd
     * @return 更新密码
     */
    boolean updatePwd(String userName,String oldPwd,String newPwd);

    /**
     * 更新数据
     * @param param
     */
    Optional<UserDTO> updateUserInfo(UpdateUserParam param);

    /**
     * 根据 email 获取 UserDTO实例
     *
     * @param email
     * @return
     */
    Optional<User> getByEmail(String email);

    /**
     * 根据 Username 获取 UserDTO 实例
     *
     * @param username
     * @return 一个 User 类型的 Optional
     */
    Optional<User> getByName(String username);

    /**
     * 获取所有UserDTO数据
     *
     * @return 一个 UserDTO 类型的 List
     */
    List<UserDTO> listTos();

    /**
     * 检测密码是否正确
     *
     * @param user user实体
     * @param plainPwd 密码原文
     * @return 匹配结果
     */
    boolean passwordMatch(User user, String plainPwd);

    /**
     * 获取当前登录的用户
     */
    Optional<UserDTO> getCurrentUser();

    /**
     * 获取当前登录的用户名
     */
    Optional<String> getCurrentUserName();


    /**
     * 验证jwt
     */
    boolean verifyJwtToken(String jwtToken) throws AuthException;

}
