package app.isparks.web.controller.api;

import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.param.UpdateUserParam;
import app.isparks.core.service.IUserService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 *
 * @author： chenghd
 * @date： 2021/2/5
 */
@RestController
@RequestMapping("api/admin/user")
public class UserApi {

    private IUserService userService;

    public UserApi(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("get/login/info")
    public Result<UserDTO> getLoginUser(){
        // todo : 修改成当前登录的用户
        Optional<UserDTO> userDTO = userService.listTos().stream().findFirst();
        return ResultUtils.build(userDTO);
    }

    @GetMapping("get/all/info")
    public Result user(){
        return ResultUtils.success("success",userService.listTos());
    }

    @RequestMapping(value = "update/info",method = {RequestMethod.POST})
    public Result updateUserInfo(@RequestBody UpdateUserParam param){
        return ResultUtils.build(userService.updateUserInfo(param));
    }

    @RequestMapping(value = "update/pwd",method = {RequestMethod.GET})
    public Result updatePassword(@RequestParam("userName")String userName,@RequestParam("oldPwd")String oldPwd,@RequestParam("newPwd")String newPwd){
        return ResultUtils.build(userService.updatePwd(userName,oldPwd,newPwd));
    }

}
