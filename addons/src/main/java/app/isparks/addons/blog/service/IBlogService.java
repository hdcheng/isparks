package app.isparks.addons.blog.service;

import app.isparks.addons.blog.entity.PostAttach;
import app.isparks.core.pojo.dto.PostDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/21
 */
public interface IBlogService {


    /**
     * about page content(markdown)
     */
    String aboutPageMDContent();

    /**
     *
     */
    void updateAboutPageMDContent(String mdContent);

    /**
     * 增加文章访问量
     *
     * @param postId
     * @param visits
     */
    void increaseVisit(String postId,long visits);

    /**
     * 增加文章访喜欢量
     *
     * @param postId
     * @param likes
     */
    void increaseLike(String postId,long likes);

    /**
     * 根据 id 获取数据
     *
     * @param postId
     */
    Optional<PostAttach> getByPostId(String postId);

    /**
     * 获取从指定时间开始浏览最多的几个值
     */
    List<PostDTO> listMostVisits(int number, Long time);

    /**
     * 获取配置
     */
    Optional<String> getConfigByKey(String key);

    /**
     * 获取配置
     */
    Map<String,String> listConfigByKeys(String[] keys);

    /**
     * 保存或者更新配置
     */
    void updateOrSaveConfig(String key,String value);

}
