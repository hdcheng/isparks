package app.isparks.core.service;



import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.param.LoginParam;

import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/8/3
 */
public interface IAdminService {

    /**
     * 登录权限验证
     */
    Optional<UserDTO> authenticate(LoginParam loginParam);

    /**
     * 注销
     */
    boolean logout(String username);


}
