package app.isparks.addons.image.controller;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.file.type.MediaType;
import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.service.IFileService;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.property.WebProperties;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author： chenghd
 * @date： 2021/3/16
 */
public class ImageController {

    private IFileService fileService;

    public ImageController(IFileService fileService){
        this.fileService = fileService;
    }

    @ResponseBody
    public Result page(@RequestParam("page") Integer page, @RequestParam("size") Integer size){

        PageData<FileDTO> pageData = fileService.pageValidByMediaType(page <= 0 ? 1:page,size<=0 ? 10 :size, MediaType.IMAGE);

        pageData.getData().stream().forEach((image) -> {

            image.setLocation(parseToUrlLink(image.getLocation()));

        });

        return ResultUtils.build(pageData);
    }


    /**
     * 将本地文件转换成当地
     * @param location
     * @return
     */
    private String parseToUrlLink(String location){

        if(StringUtils.isEmpty(location)){
            return "";
        }

        if(location.startsWith("http://") || location.startsWith("https://")){
            return location;
        }

        location = location.replace(ISparksProperties.RESOURCES_FILE_PATH,"");
        location = location.replace(ISparksConstant.PATH_SEPARATOR,ISparksConstant.URL_SEPARATOR);

        return WebProperties.WEBSITE_URL.getDefaultValue() + "/static" + location;
    }

}
