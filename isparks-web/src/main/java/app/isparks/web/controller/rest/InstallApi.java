package app.isparks.web.controller.rest;

import app.isparks.core.config.DBConfig;
import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.pojo.enums.SystemProperties;
import app.isparks.core.pojo.param.UserParam;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.ISysService;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.RegexUtils;
import app.isparks.core.web.property.WebProperties;
import app.isparks.core.web.support.Result;
import app.isparks.service.impl.OptionServiceImpl;
import app.isparks.service.impl.SysServiceImpl;
import app.isparks.service.impl.UserServiceImpl;
import app.isparks.web.pojo.param.InstallParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenghd
 */

@Api("系统安装接口")
@RequestMapping("v1/admin")
@RestController("v1_InstallApi")
public class InstallApi extends BasicApi{

    private Logger log = LoggerFactory.getLogger(InstallApi.class);

    private ISysService sysService;
    private IOptionService optionService;
    private IUserService userService;
    public InstallApi(SysServiceImpl sysService, OptionServiceImpl optionService, UserServiceImpl userService){
        this.sysService = sysService;
        this.optionService = optionService;
        this.userService = userService;
    }


    @PostMapping("init")
    @ApiOperation("Initialize system | 初始化系统")
    public Result install(@RequestBody InstallParam params){
        // 检测系统是否已经安装
        boolean isInstalled = sysService.isInstalled();

        if(isInstalled){
           return fail("系统已经安装过，请不要重复安装系统。");
        }

        // 初始化数据库
        initDB(params);

        // 初始化配置
        initSettings(params);

        // 初始化用户数据
        initUser(params);

        return build(true);
    }

    // 初始化配置
    private void initSettings(InstallParam params){
        Map<String,Object> config = new HashMap<>();

        // web配置
        config.put(WebProperties.WEBSITE_TITLE.getKey(),params.getWebName());
        config.put(WebProperties.WEBSITE_URL.getKey(),params.getWebUrl());

        // 系统配置
        config.put(SystemProperties.BIRTHDAY.getKey(), System.currentTimeMillis());
        config.put(SystemProperties.SYSTEM_LOCALE.getKey(),params.getLocale());
        config.put(SystemProperties.DATABASE_TYPE.getKey(),params.getDatabase());

        // 数据库数据
        String ip = RegexUtils.getFirstIPv4(params.getDbHostPort());
        String port = RegexUtils.getFirstPort(params.getDbHostPort());
        config.put(DBConfig.DatabaseProperties.DATABASE_IP.getKey(), ip);
        config.put(DBConfig.DatabaseProperties.DATABASE_PORT.getKey(), port);
        config.put(DBConfig.DatabaseProperties.DATABASE_USERNAME.getKey(), params.getDbUsername());
        config.put(DBConfig.DatabaseProperties.DATABASE_PASSWORD.getKey(), params.getDbPassword());
        config.put(DBConfig.DatabaseProperties.DATABASE_NAME_PREFIX.getKey(), params.getDbNamePrefix());

        sysService.createConfig(config,true);
    }

    // 初始化数据库
    private void initDB(InstallParam params){

        if (!"H2".equals(params.getDatabase())){

            String ip = RegexUtils.getFirstIPv4(params.getDbHostPort());
            String port = RegexUtils.getFirstPort(params.getDbHostPort());

            if(sysService.updateDBInfo(params.getDatabase(),ip,port,params.getDbUsername(),params.getDbPassword())){
                DBConfig.setDBPrefix(params.getDbNamePrefix());
                sysService.switchDB();
            }else{
                throw new InvalidValueException("数据库类型" + params.getDatabase() + "不支持");
            }
        }

        if(!optionService.connectable()){
            sysService.initDB();
        }


        Map<String,Object> config = new HashMap<>();

        // web配置
        config.put(WebProperties.WEBSITE_TITLE.getKey(),params.getWebName());
        config.put(WebProperties.WEBSITE_URL.getKey(),params.getWebUrl());

        // 系统配置
        config.put(SystemProperties.BIRTHDAY.getKey(), System.currentTimeMillis());
        config.put(SystemProperties.SYSTEM_LOCALE.getKey(),params.getLocale());

        config.put(SystemProperties.DATABASE_TYPE.getKey(),params.getDatabase());
        config.put(SystemProperties.IS_INSTALLED.getKey(), Boolean.TRUE.toString());
        optionService.saveOrUpdate(config);

    }

    // 初始化用户配置
    private void initUser(InstallParam params){
        UserParam userParam = new UserParam();

        userParam.setUserName(params.getUserName());
        userParam.setNickName(params.getUserNickName());
        userParam.setEmail(params.getUserEmail());
        userParam.setPassword(params.getPassword());
        userParam.setAvatar("1");

        userService.create(userParam);
    }
}
