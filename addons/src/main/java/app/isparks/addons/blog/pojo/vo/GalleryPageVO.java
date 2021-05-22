package app.isparks.addons.blog.pojo.vo;

import app.isparks.core.pojo.dto.FileDTO;
import app.isparks.core.pojo.page.PageData;
import lombok.Data;

@Data
public class GalleryPageVO extends PageData<FileDTO> {

    public GalleryPageVO(PageData<FileDTO> pageData){
        setData(pageData.getData());
        setPage(pageData.getPage());
        setSize(pageData.getSize());
        setTotalData(pageData.getTotalData());
        setTotalPage(pageData.getTotalPage());
    }
}
