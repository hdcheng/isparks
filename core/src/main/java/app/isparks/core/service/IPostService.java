package app.isparks.core.service;

import app.isparks.core.pojo.dto.PostArchiveDTO;
import app.isparks.core.pojo.dto.PostDTO;
import app.isparks.core.pojo.enums.DataStatus;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.PostParam;
import app.isparks.core.pojo.param.UpdatePostParam;

import java.util.List;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/2/28
 */
public interface IPostService {

    /**
     * 新建文章
     *
     * @param param
     * @param dataStatus 数据状态
     * @return post
     */
    Optional<PostDTO> create(PostParam param, DataStatus dataStatus);

    /**
     * 创建临时文件链接
     *
     * @param id
     * @param mills
     * @return 缓存key
     */
    String createTempLinkKey(String id,long mills);

    /**
     * 根据缓存key值获取postid
     *
     * @param cacheKey
     * @return
     */
    String getTempLinkPostIdByKey(String cacheKey);

    /**
     * 分页查询数据
     *
     * @param page
     * @param size
     * @param dataStatus
     * @return
     */
    PageData<PostDTO> page(int page,int size,DataStatus dataStatus);

    /**
     * page by category
     * @param page
     * @param size
     * @param categoryId
     * @param dataStatus
     */
    PageData<PostDTO> pageByCategory(int page,int size,DataStatus dataStatus,String categoryId);

    /**
     * 根据 id 查找
     *
     * @param id
     * @return
     */
    Optional<PostDTO> getById(String id);

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    Optional<PostDTO> delete(String id);

    /**
     * 恢复状态
     *
     * @param id
     * @return
     */
    Optional<PostDTO> restore(String id);

    /**
     * 移除文章
     *
     * @param id
     * @return
     */
    Optional<PostDTO> remove(String id);

    /**
     * 更新数据
     *
     * @param param
     * @return
     */
    Optional<PostDTO> update(UpdatePostParam param);


    /**
     * 根据年归档文章
     */
    List<PostArchiveDTO> listYearArchives(DataStatus status);


    /**
     * 文章是否存在
     */
    boolean existId(String postId);

    /**
     * 统计所有帖子数量
     *
     * @return long
     */
    long countAll();
}
