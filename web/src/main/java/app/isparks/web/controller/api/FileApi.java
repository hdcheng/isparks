package app.isparks.web.controller.api;

import app.isparks.core.anotation.Log;
import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.file.type.FileType;
import app.isparks.core.file.type.MediaType;
import app.isparks.core.file.util.FileTypeUtils;
import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.service.IFileService;
import app.isparks.core.util.UrlUtils;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author： chenghd
 * @date： 2021/3/3
 */
@RestController
@RequestMapping("/api/admin/file")
public class FileApi {


    @Autowired
    private IFileService fileService;

    @ApiOperation("上传文件")
    @RequestMapping(value = "upload",method = {RequestMethod.POST})
    @Log(description = "上传文件", types = {LogType.SYS,LogType.INSERT})
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        Optional<FileDTO> result = fileService.upload(file.getOriginalFilename(),file.getInputStream());

        if(!result.isPresent()){
            return ResultUtils.fail("上传失败");
        }

        //String link = parseToUrlLink(result.get().getLocation());
        String link = UrlUtils.parseStaticResourceToUrlLink(result.get().getLocation());

        result.get().setLocation(link);

        return ResultUtils.success("上传成功").setData(result.get());

    }

    @ApiOperation("移除文件")
    @RequestMapping(value = "remove",method = {RequestMethod.GET})
    public Result removeById(@RequestParam("id")String id){
        return ResultUtils.build(fileService.removeById(id));
    }


    @ApiOperation("恢复文件")
    @RequestMapping(value = "restore",method = {RequestMethod.GET})
    public Result restoreById(@RequestParam("id")String id){
        return ResultUtils.build(fileService.restoreById(id));
    }

    @ApiOperation("列出所有状态是REMOVED的文件")
    @RequestMapping(value = "list/all/removed",method = {RequestMethod.GET})
    public Result listAllRemoved(){
        return ResultUtils.success().setData(fileService.listAllRemoved());
    }

    @ApiOperation("列出所有文件")
    @RequestMapping(value = "page",method = {RequestMethod.GET})
    public Result pageAll(@RequestParam("page")String page,@RequestParam("size")String size){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        PageData<FileDTO> dtoPage = fileService.pageValidFile(p <= 0 ? 1 : p,s <= 0 ? 10 : s);

        parseResourceLocationToLinks(dtoPage);

        return ResultUtils.build(dtoPage);
    }

    @ApiOperation("列出指定类型的文件")
    @RequestMapping(value = "page/by/filetype",method = {RequestMethod.GET})
    public Result pageByFileType(@RequestParam("page")String page,@RequestParam("size")String size,@RequestParam("type")String fileType){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        FileType type = FileTypeUtils.getFileType(fileType);

        PageData<FileDTO> dtos = fileService.pageValidByFileType(p <= 0 ? 1 : p,s <= 0 ? 10 : s,type);

        parseResourceLocationToLinks(dtos);

        return ResultUtils.build(dtos);
    }

    @ApiOperation("列出指定媒体类型的文件")
    @RequestMapping(value = "page/by/mediatype",method = {RequestMethod.GET})
    public Result pageByMediaType(@RequestParam("page")String page,@RequestParam("size")String size,@RequestParam("type")String mediaType){

        int p = Integer.valueOf(page);
        int s = Integer.valueOf(size);

        MediaType type = MediaType.UNKNOWN;
        for(MediaType t : MediaType.values()){
                if(t.getType().equals(mediaType)){
                    type = t;
                    break;
                }
        }

        PageData<FileDTO> dtoPage = fileService.pageValidByMediaType(p <= 0 ? 1 : p,s <= 0 ? 10 : s,type);

        parseResourceLocationToLinks(dtoPage);

        return ResultUtils.build(dtoPage);
    }

    /**
     * 更新 FileDTO 的链接
     */
    public static void parseResourceLocationToLinks(PageData<FileDTO> pageData){
        UrlUtils.parseStaticResourceToUrlLinks(pageData.getData(),dto -> dto.getLocation(), (dto,location) -> dto.setLocation(location));
    }

}
