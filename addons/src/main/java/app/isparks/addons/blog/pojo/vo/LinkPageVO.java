package app.isparks.addons.blog.pojo.vo;

import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.page.PageData;
import lombok.Data;

@Data
public class LinkPageVO extends PageData<LinkDTO> {

    public LinkPageVO(){

    }
    public LinkPageVO(PageData<LinkDTO> pageData){
        setData(pageData.getData());
        setPage(pageData.getPage());
        setSize(pageData.getSize());
        setTotalData(pageData.getTotalData());
        setTotalPage(pageData.getTotalPage());
    }

}
