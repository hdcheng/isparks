package app.isparks.core.service;

import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.entity.Link;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.LinkParam;

import java.util.List;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/13
 */
public interface ILinkService {

    /**
     * 储存链接
     */
    Optional<LinkDTO> save(LinkParam param,LinkType type);


    Optional<LinkDTO> update(LinkParam param);

    /**
     * 根据ID删除
     */
    Optional<LinkDTO> delete(String id);

    /**
     * 根据类型获取链接数据
     */
    List<LinkDTO> listByType(LinkType type);

    /**
     * 根据类型分页查询
     */
    PageData<LinkDTO> pageByType(int page,int size,LinkType type);

    /**
     * 获取 MENU_LINK 类型的 link
     */
    List<LinkDTO> listMenuType();

    /**
     * 获取 PLUGIN 类型的 link
     */
    List<LinkDTO> listPluginType();

    /**
     * 如果 url 不存在 则插入新值
     */
    Optional<LinkDTO> saveIfUrlAbsent(Link link);

}
