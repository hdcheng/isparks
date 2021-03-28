package app.isparks.service.async.listener;

import app.isparks.core.service.ILogService;
import app.isparks.service.async.event.LogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author： chenghd
 * @date： 2021/3/25
 */
@Component
public class LogEventListener {

    private ILogService logService;

    public LogEventListener(ILogService logService){
        this.logService = logService;
    }

    @EventListener
    @Async
    public void record(LogEvent logEvent){
        // 记录日志
        logService.create(logEvent.getLog());
    }

}
