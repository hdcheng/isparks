package app.isparks.addons.image;

import app.isparks.addons.image.controller.ImageController;
import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.exception.SystemException;
import app.isparks.core.plugin.IPlugin;
import app.isparks.core.service.IFileService;
import app.isparks.core.service.ILinkService;
import app.isparks.core.util.IOCUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * 图片插件，用户实现图片管理和预览
 *
 * @author： chenghd
 * @date： 2021/3/13
 */
public class ImagePlugin implements IPlugin {


    @Override
    public void activate(Object var) {
//        ISparksApplication.instance().getPluginManager().ifPresent((pm)->{
//            pm.setLinkButton("image","图片管理","<span uk-icon='image'></span>");
//        });

        IOCUtils.getBeanByClass(ILinkService.class).ifPresent(linkService -> {
            registerApi(linkService);
        });
    }

    @Override
    public void deactivate() {

    }

    private void registerApi(ILinkService linkService){

        RequestMappingHandlerMapping rmm = (RequestMappingHandlerMapping) IOCUtils
                .getBeanByName("requestMappingHandlerMapping")
                .orElseThrow( () ->new SystemException("bean no find"));

        Map<RequestMappingInfo, HandlerMethod> methods = rmm.getHandlerMethods();

        RequestMappingInfo info = RequestMappingInfo.paths("/api/plugin/image/page").methods(RequestMethod.GET).build();

        IFileService fileService = IOCUtils.getBeanByClass(IFileService.class).orElseThrow(() -> new InvalidValueException(""));

        ImageController controller = new ImageController(fileService);
        try {
            rmm.registerMapping(info,controller,controller.getClass().getDeclaredMethod("page",Integer.class,Integer.class));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
