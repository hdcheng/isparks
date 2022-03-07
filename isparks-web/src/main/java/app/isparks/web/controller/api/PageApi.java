package app.isparks.web.controller.api;

import app.isparks.core.pojo.enums.SystemProperties;
import app.isparks.core.service.IFileService;
import app.isparks.core.service.ILogService;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.IPostService;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： chenghd
 * @date： 2021/3/9
 */
@Api("页面数据管理")
@RestController
@RequestMapping("/api/admin/page")
public class PageApi {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPostService postService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IOptionService optionService;

    @Autowired
    private ILogService logService;

    @RequestMapping(value = "/{pageName}",method = {RequestMethod.GET})
    public Result page(@PathVariable("pageName") String pageName){

        Result result ;

        switch (pageName){
            case "dash":
                result = dash();break;
            default:
                result = null;
        }

        return result == null ? absent(pageName) : exist(pageName,result);
    }

    /**
     * 页面不存在
     */
    public Result absent(String pageName){
        return ResultUtils.fail("页面不支持");
    }

    /**
     * 页面存在
     */
    public Result exist(String pageName , Result result){

        return result;
    }


    // dash页面
    private Result dash(){

        // panel数据
        List<Map> panel = new ArrayList<>();
        // post
        Map<String,Object> post = new HashMap<>(3);
        post.put("title",postService.countAll());
        post.put("content","文章");
        post.put("textIcon","📃");
        panel.add(post);

        // 文件
        Map<String,Object> files = new HashMap<>(3);
        files.put("title",fileService.countAll());
        files.put("content","文件");
        files.put("textIcon","💾");
        panel.add(files);

        // 收藏
        Map<String,Object> likes = new HashMap<>(3);
        likes.put("title",0);
        likes.put("content","收藏");
        likes.put("textIcon","❤");
        panel.add(likes);

        // 日志
        Map<String,Object> logs = new HashMap<>(3);
        logs.put("title",logService.countAll());
        logs.put("content","日志");
        logs.put("textIcon","📋");
        panel.add(logs);

        // 日志
        Map<String,Object> plugins = new HashMap<>(3);
        plugins.put("title","2");
        plugins.put("content","插件");
        plugins.put("textIcon","📍");
        panel.add(plugins);


        Map<String,Object> data = new HashMap<>();
        data.put("panel",panel); // 添加panel数据
        data.put("installTime",optionService.getByPropertyOrDefault(SystemProperties.BIRTHDAY,Long.class));

        return ResultUtils.success("success",data);
    }

}
