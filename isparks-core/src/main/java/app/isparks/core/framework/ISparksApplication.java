package app.isparks.core.framework;


import app.isparks.core.util.IOCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： chenghd
 * @date： 2021/3/13
 */
public class ISparksApplication implements IBoot{

    private static Logger log = LoggerFactory.getLogger(ISparksApplication.class);

    private static Map<String,IBoot> boots = new LinkedHashMap<>();

    private volatile static ISparksApplication application = null;

    private ISparksApplication(){}

    public static ISparksApplication instance(){
        if(application == null){
            synchronized (ISparksApplication.class){
                if(application == null){
                    application = new ISparksApplication();
                }
            }
        }
        return application;
    }


    public static ISparksContext run(ApplicationContext context,String ... args){
        IOCUtils.applicationContext = context;

        instance().boot(args);

        return null;
    }


    public ISparksApplication register(String name,IBoot boot){
        if(boots.get(name) == null){
            boots.put(name,boot);
        }
        return instance();
    }


    @Override
    public void boot(Object ... args) {
        boots.values().forEach((ib)->{
            ib.boot(args);
        });
    }

}
