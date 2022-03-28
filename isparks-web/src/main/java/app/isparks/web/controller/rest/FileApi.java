package app.isparks.web.controller.rest;

import app.isparks.core.anotation.Log;
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
import app.isparks.service.impl.FileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author chenghd
 */

@Api("文件管理接口")
@RequestMapping("v1/admin")
@RestController("v1_FileApi")
public class FileApi extends BasicApi{

    private IFileService fileService;

    public FileApi(FileServiceImpl fileService){
        this.fileService = fileService;
    }

    @PostMapping("file/upload")
    @ApiOperation(" File upload | 上传文件")
    @Log(description = "上传文件",types = {LogType.INSERT,LogType.FILE_UPLOAD})
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        Optional<FileDTO> result = fileService.upload(file.getOriginalFilename(),file.getInputStream());
        if(!result.isPresent()){
            return ResultUtils.fail("upload file fail");
        }
        String link = UrlUtils.parseStaticResourceToUrlLink(result.get().getLocation());
        result.get().setLocation(link);
        return build(result);
    }

    @DeleteMapping("file/delete/{id}")
    @ApiOperation(value = " Delete the file by id | 彻底删除文件",notes = "只能删除状态是 REMOVE 的文件")
    @Log(description = "彻底删除文件",types = {LogType.DELETE})
    public Result deleteById(@PathVariable("id")String id){
        return build(fileService.deleteById(id));
    }

    @GetMapping("file/removed")
    @ApiOperation("Get all removed files | 获取所有 remove 状态的文件")
    public Result listAllRemoved(){
        return build(fileService.listAllRemoved());
    }

    @GetMapping("file/page")
    @ApiOperation("Get all valid files | 获取所有 valid 状态的文件")
    public Result pageValid(@RequestParam( value = "page" , defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size){

        PageData<FileDTO> dtos = fileService.pageValidFile(page,size);
        parseResourceLocationToLinks(dtos);

        return build(dtos);
    }

    @GetMapping("file/page/type/{type}")
    @ApiOperation("Get files by file type | 分页查找指定文件类型")
    public Result pageByFilType(@PathVariable("type")String fileType,
                                @RequestParam( value = "page" , defaultValue = "1") int page,
                                @RequestParam(value = "size",defaultValue = "10") int size){
        FileType type = FileTypeUtils.getFileType(fileType);

        PageData<FileDTO> dtos =fileService.pageValidByFileType(page,size,type);
        parseResourceLocationToLinks(dtos);

        return build(dtos);
    }


    @GetMapping("file/page/media/{type}")
    @ApiOperation("Get files by file type | 分页查找指定文件类型")
    public Result pageByMediaType(@PathVariable("type")String mediaType,
                                @RequestParam( value = "page" , defaultValue = "1") int page,
                                @RequestParam(value = "size",defaultValue = "10") int size){

        MediaType type = MediaType.UNKNOWN;
        for(MediaType t : MediaType.values()){
            if(t.getType().equals(mediaType)){
                type = t;
                break;
            }
        }
        PageData<FileDTO> dtoPage = fileService.pageValidByMediaType(page,size,type);
        parseResourceLocationToLinks(dtoPage);

        return build(dtoPage);
    }


    @PatchMapping("file/remove/{id}")
    @ApiOperation(" Update the file status to remove | 更新文章状态为移除")
    @Log(description = "更新文章状态为移除",types = {LogType.MODIFY})
    public Result removeById(@PathVariable("id")String id){
        return ResultUtils.build(fileService.removeById(id));
    }


    @PatchMapping("file/restore/{id}")
    @ApiOperation(" Update the file status to valid | 恢复文件")
    @Log(description = "恢复文件",types = {LogType.MODIFY})
    public Result restoreById(@PathVariable("id") String id){
        return ResultUtils.build(fileService.restoreById(id));
    }

    public static void parseResourceLocationToLinks(PageData<FileDTO> pageData){
        UrlUtils.parseStaticResourceToUrlLinks(pageData.getData(),dto -> dto.getLocation(), (dto,location) -> dto.setLocation(location));
    }


}
