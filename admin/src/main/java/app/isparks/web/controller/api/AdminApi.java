package app.isparks.web.controller.api;

import app.isparks.core.anotation.Log;
import app.isparks.core.exception.AuthException;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.LoginParam;
import app.isparks.core.service.IAdminService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.AdminServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author chenghd
 * @date 2020/8/3
 */
@Api("后台管理")
@RestController
@RequestMapping("/api/admin")
public class AdminApi {

    private IAdminService adminService;

    public AdminApi(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }


    @ApiOperation(value = "登录", notes = "用户登录/权限验证")
    @RequestMapping(value = "authenticate", method = {RequestMethod.POST})
    @Log(description = "登录", types = {LogType.SYS})
    public Result login(@RequestBody @Valid LoginParam params) {
        UserDTO result = adminService.authenticate(params).orElseThrow(() -> new AuthException("登录失败", "密码错误"));
        return ResultUtils.success("登录成功", result.getToken());
    }


    @ApiOperation(value = "注销", notes = "")
    @RequestMapping(value = "logout", method = {RequestMethod.GET})
    @Log(description = "用户注销", types = {LogType.SYS})
    public Result logout(@RequestParam(value = "user") String userName) {
        return ResultUtils.build(adminService.logout(userName), "注销成功", "注销失败");
    }

}
