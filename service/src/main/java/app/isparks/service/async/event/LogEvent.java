package app.isparks.service.async.event;

import app.isparks.core.pojo.entity.Log;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.util.IdUtils;
import app.isparks.core.util.IpUtils;
import app.isparks.core.util.StringUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author： chenghd
 * @date： 2021/3/25
 */
public class LogEvent extends ApplicationEvent {

    private final Log log;

    private String ip = "unknown";

    private LogType[] types = {};

    private String content = "";

    private Long time;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LogEvent(Object source, Log log) {
        super(source);
        this.log = log;
        this.time = System.currentTimeMillis();
    }

    public LogEvent(Object source,String content, LogType[] types, HttpServletRequest request){
        this(source,new Log());
        this.types = types;
        this.content = content;
        this.ip = IpUtils.obtainIp(request);
        this.time = System.currentTimeMillis();
    }
    public LogEvent(Object source , String content, LogType[] types){
        this(source,content,types,null);
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        }catch (NullPointerException|ClassCastException e){
            e.printStackTrace();
        }finally {
            this.ip = IpUtils.obtainIp(request);
        }
    }


    public Log getLog(){

        if(StringUtils.isEmpty(log.getContent())){
            log.setContent(this.content);
        }

        if(StringUtils.isEmpty(log.getType())){
            StringJoiner joiner = new StringJoiner(",","[","]");
            Arrays.stream(types).forEach(type -> {
                joiner.add(type.getDescription());
            });
            log.setType(joiner.toString());
        }

        if(StringUtils.isEmpty(log.getDate())){
            log.setDate(new Date(this.time).toString());
        }

        if(StringUtils.isEmpty(log.getIp())){
            log.setIp(this.ip);
        }

        return this.log;
    }
}
