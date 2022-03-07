package app.isparks.core.service;

import app.isparks.core.pojo.dto.TagDTO;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.TagParam;

import java.util.List;
import java.util.Optional;

/**
 * 标签管理逻辑层
 *
 * @author： chenghd
 * @date： 2021/2/26
 */
public interface ITagService {

    /**
     * 创建标签
     *
     * @param param
     * @return
     */
    Optional<TagDTO> create(TagParam param);

    /**
     * 根据标签名创建标签
     *
     * @param names
     * @return
     */
    List<TagDTO> createTagsByNamesIfAbsent(List<String> names);

    /**
     * 创建标签
     *
     * @param name
     * @return
     */
    TagDTO createByNameIfAbsent(String name);

    /**
     * 分页查找标签
     *
     * @param page
     * @param size
     * @return
     */
    PageData<TagDTO> page(int page,int size);

    /**
     * 根据id查找标签
     *
     * @param ids
     * @return
     */
    List<TagDTO> selectByIds(List<String> ids);

    /**
     * 根据标签名删除标签
     *
     * @param tagName
     * @return
     */
    Optional<TagDTO> deleteByName(String tagName);

}
