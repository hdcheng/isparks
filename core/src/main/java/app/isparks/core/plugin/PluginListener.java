package app.isparks.core.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author： chenghd
 * @date： 2021/3/26
 */
public abstract class PluginListener<E extends ApplicationEvent> implements ApplicationListener<E> {
    
    private Logger log = LoggerFactory.getLogger(getClass());
    
    private final ExecutorService executor ;

    protected PluginListener(){
        executor = Executors.newFixedThreadPool(3);
    }

    @Override
    public final void onApplicationEvent(E event) {
        executor.submit(()->{
            log.info("执行线程任务："+ Thread.currentThread().getName());
            onEvent(event);
        });
    }

    protected abstract void onEvent(E e);

}
