package app.addons.meet.service;

import app.addons.meet.vo.AboutVO;
import app.addons.meet.vo.IndexVO;
import app.addons.meet.vo.PostVO;
import app.isparks.core.pojo.dto.CategoryDTO;
import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.dto.PostArchiveDTO;
import app.isparks.core.pojo.dto.PostDTO;
import app.isparks.core.pojo.page.PageData;

import java.util.List;
import java.util.Map;

public interface IMeetRequestService {

    /**
     * 首页接口
     */
    IndexVO index(Map<String,Object> params);

    PageData<PostDTO> post(Map<String,Object> params);

    /**
     * 热门
     */
    List<PostDTO> hots();

    /**
     * 友链接接
     */
    List<LinkDTO> links();

    PageData<PostDTO> categoryPost(Map<String,Object> params);

    List<CategoryDTO> category(Map<String,Object> params);

    List<PostArchiveDTO> archive(Map<String,Object> params);

    PostVO post(String id);

    AboutVO about(Map<String,Object> params);

}
