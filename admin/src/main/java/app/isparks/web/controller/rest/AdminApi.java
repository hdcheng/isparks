package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.exception.AuthException;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.LoginParam;
import app.isparks.core.service.IAdminService;
import app.isparks.core.service.IUserService;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.AdminServiceImpl;
import app.isparks.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenghd
 */
@Api("后台管理接口")
@RequestMapping("v1/admin")
@RestController("v1_AdminApi")
public class AdminApi extends BasicApi{

    private IAdminService adminService;

    private IUserService userService;

    public AdminApi(AdminServiceImpl adminService, UserServiceImpl userService) {
        this.adminService = adminService;this.userService = userService;
    }

    @GetMapping("authenticate")
    @ApiOperation(value = "Authenticate | 权限认证")
    @Log(description = "用户登录",types = {LogType.LOGIN})
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "authCode",required = false) String authCode){

        LoginParam params = new LoginParam();
        params.setLoginName(username);
        params.setPassword(password);
        params.setAuthCode(authCode);
        UserDTO result = adminService.authenticate(params).orElseThrow(() -> new AuthException("登录失败", "密码错误"));

        return build(result);
    }

    @PostMapping("authenticate")
    @ApiOperation(value = "Verify token | 认证token有效性")
    @Log(description = "Token验证",types = {LogType.LOGIN})
    public Result verifyToken(@RequestBody String token){
        return build(userService.verifyJwtToken(token));
    }

    @GetMapping("logout/{username}")
    @ApiOperation(value = "Logout | 注销登录状态")
    @Log(description = "用户注销",types = {LogType.LOGOUT})
    public Result logout(@PathVariable("username") String username){
        return build(adminService.logout(username));
    }

}
