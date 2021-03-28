package app.isparks.addons.blog.service;

import app.isparks.addons.blog.entity.PostAttach;
import app.isparks.core.pojo.dto.PostDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/21
 */
public interface IBlogService {

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

}
