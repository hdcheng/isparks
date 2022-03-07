package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.UpdateUserParam;
import app.isparks.core.service.IUserService;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * @author chenghd
 */

@Api("用户管理接口")
@RequestMapping("v1/admin")
@RestController("v1_UserApi")
public class UserApi extends BasicApi{

    private IUserService userService;

    public UserApi(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("user/login")
    @ApiOperation("Get login user info | 获取登录用户的信息")
    public Result<UserDTO> getLoginUser(){
        // todo : 修改成当前登录的用户
        return build(userService.listTos().stream().findFirst());
    }

    @GetMapping("users")
    @ApiOperation("Get all users info | 获取所有用户的信息")
    public Result user(){
        return build(userService.listTos());
    }

    @PatchMapping("user/{id}")
    @ApiOperation("Update user info | 更新用户信息")
    @Log(description = "更新用户信息",types = {LogType.MODIFY})
    public Result updateUserInfo(@PathVariable("id")String  id, @RequestBody UpdateUserParam param){
        return build(userService.updateUserInfo(param));
    }

    @PatchMapping("user/pwd")
    @ApiOperation("Update user password | 更新密码")
    @Log(description = "更新密码",types = {LogType.MODIFY})
    public Result updatePassword(@RequestParam("userName")String userName,
                                 @RequestParam("oldPwd")String oldPwd,
                                 @RequestParam("newPwd")String newPwd){
        return build(userService.updatePwd(userName,oldPwd,newPwd));
    }

}
