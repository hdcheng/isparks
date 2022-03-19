package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
import app.isparks.core.exception.AuthException;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.param.LoginParam;
import app.isparks.core.pojo.param.UserParam;
import app.isparks.core.service.IAdminService;
import app.isparks.core.service.ICaptchaService;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.IpUtils;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import app.isparks.service.impl.AdminServiceImpl;
import app.isparks.service.impl.CaptchaServiceImpl;
import app.isparks.service.impl.UserServiceImpl;
import app.isparks.web.pojo.dto.FragmentDTO;
import app.isparks.web.pojo.param.AuthParam;
import app.isparks.web.service.IFragmentService;
import app.isparks.web.service.impl.FragmentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author chenghd
 */
@Api("后台管理接口")
@RequestMapping("v1/admin")
@RestController("v1_AdminApi")
public class AdminApi extends BasicApi{

    private IAdminService adminService;

    private IUserService userService;

    private ICaptchaService captchaService;

    private IFragmentService fragmentService;

    public AdminApi(AdminServiceImpl adminService, UserServiceImpl userService, CaptchaServiceImpl captchaService, FragmentServiceImpl fragmentService) {
        this.adminService = adminService;this.userService = userService;
        this.captchaService = captchaService;
        this.fragmentService = fragmentService;
    }

    @GetMapping("authenticate")
    @ApiOperation(value = "Authenticate | 权限认证")
    @Log(description = "用户登录",types = {LogType.LOGIN})
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("code") String code,
                        @RequestParam(value = "remember" , required = false , defaultValue = "false") Boolean remember,
                        HttpServletRequest request){
        String ip = IpUtils.obtainIp(request);

//        if(!captchaService.checkCaptcha(ip,code,true)){
//            return fail("验证码错误");
//        }

        TimeUnit timeUnit = TimeUnit.HOURS;
        long time ;
        if(remember == null || !remember){
            time = 8 ; // 缓存 8 小时密码
        }else{
            time = 24 * 30 ; // 缓存一个月的密码
        }

        UserDTO result = adminService.authenticate(username,password,time,timeUnit).orElseThrow(() -> new AuthException("登录失败", "密码错误"));

        return build(result);
    }

    @PostMapping("authenticate")
    @ApiOperation(value = "Verify token | 认证token有效性")
    @Log(description = "Token验证",types = {LogType.LOGIN})
    public Result verifyToken(@RequestBody AuthParam param){
        return build(userService.verifyJwtToken(param.getToken()));
    }

    @GetMapping("logout/{username}")
    @ApiOperation(value = "Logout | 注销登录状态")
    @Log(description = "用户注销",types = {LogType.LOGOUT})
    public Result logout(@PathVariable("username") String username){
        return build(adminService.logout(username));
    }


    @ApiOperation(value = "",notes = "")
    @RequestMapping(value = "fragment",method = {RequestMethod.POST})
    public Result fragment(@RequestBody FragmentDTO fragment){
        return ResultUtils.success().withData(fragmentService.fragment(fragment));
    }

}
