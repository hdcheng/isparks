package app.isparks.web.service.impl;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.util.ResourcesUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.property.WebConstant;
import app.isparks.web.pojo.dto.FragmentDTO;
import app.isparks.web.service.IFragmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Collections;

@Service
public class FragmentServiceImpl implements IFragmentService {

    private Logger log = LoggerFactory.getLogger(FragmentServiceImpl.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Override
    public String fragment(FragmentDTO fragment) {
        if(fragment == null){
            return "";
        }
        return resolveThymeleaf(fragment.getPath(),fragment.getFragment());
    }

    private String resolveThymeleaf(String path, String fragment){
        if(path.startsWith("/admin/plugin")){
            path = path.replace("/admin/plugin", WebConstant.PLUGIN_TEMPLATE_PATH_NAME + "/sections");
        }
        else if(path.startsWith("/admin")){
            path = path.replace("/admin","partials");
        }

        StringBuilder html = new StringBuilder();
        try {
            Context context = new Context();
            if(StringUtils.isEmpty(fragment)){
                html.append(springTemplateEngine.process(path,context));
            }
            else{
                html.append(springTemplateEngine.process(path,Collections.singleton(fragment),context));
            }
        }catch (Exception e){
            log.error("获html片段异常",e);
        }finally {
            return html.toString();
        }
    }

}
